
import java.io.File;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;

/**
 * Main class for loading IMDb TSV data into PostgreSQL
 * 
 * Required Environment Variables:
 * - DB_URL: JDBC connection string (e.g., jdbc:postgresql://localhost:5432/filmer)
 * - DB_USER: Database username
 * - DB_PASSWORD: Database password
 * 
 * Required Files (place in data/ directory):
 * - title.basics.tsv.gz (movies data)
 * - name.basics.tsv.gz (actors data)
 * - title.principals.tsv.gz (cast relationships)
 * 
 * Download from: https://datasets.imdbws.com/
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="com.filmer.parser.DataLoader"
 */
public class DataLoader {
    
    private static final String DATA_DIR = "data";
    private static final String MOVIES_FILE = "title.basics.tsv.gz";
    private static final String ACTORS_FILE = "name.basics.tsv.gz";
    private static final String CASTS_FILE = "title.principals.tsv.gz";
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║       Filmer IMDb Data Loader v1.0               ║");
        System.out.println("║       SWE 481 - King Saud University             ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
        
        // Check environment variables
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            System.err.println("❌ ERROR: Missing database environment variables!");
            System.err.println();
            System.err.println("Required environment variables:");
            System.err.println("  DB_URL      - Database connection URL");
            System.err.println("  DB_USER     - Database username");
            System.err.println("  DB_PASSWORD - Database password");
            System.err.println();
            System.err.println("Example (Windows PowerShell):");
            System.err.println("  $env:DB_URL=\"jdbc:postgresql://localhost:5432/filmer\"");
            System.err.println("  $env:DB_USER=\"postgres\"");
            System.err.println("  $env:DB_PASSWORD=\"your_password\"");
            System.exit(1);
        }
        
        Instant startTime = Instant.now();
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("✓ Connected to database: " + dbUrl);
            System.out.println();
            
            // Set connection for performance
            conn.setAutoCommit(false);
            
            // Optimize for bulk loading
            optimizeDatabaseForLoading(conn);
            
            // 1. Load actors first (referenced by casts)
            File actorsFile = new File(DATA_DIR, ACTORS_FILE);
            if (actorsFile.exists()) {
                System.out.println("📂 Loading actors from: " + actorsFile.getAbsolutePath());
                Instant actorStart = Instant.now();
                ActorTSVParser actorParser = new ActorTSVParser(conn);
                actorParser.parse(actorsFile.getAbsolutePath());
                Duration actorDuration = Duration.between(actorStart, Instant.now());
                System.out.println("✓ Loaded " + actorParser.getActorsProcessed() + " actors in " + 
                    actorDuration.getSeconds() + "s (skipped " + actorParser.getLinesSkipped() + " non-actors)");
                System.out.println();
            } else {
                System.out.println("⚠️  Skipping actors (file not found): " + actorsFile.getAbsolutePath());
                System.out.println();
            }
            
            // 2. Load movies
            File moviesFile = new File(DATA_DIR, MOVIES_FILE);
            if (moviesFile.exists()) {
                System.out.println("📂 Loading movies from: " + moviesFile.getAbsolutePath());
                Instant movieStart = Instant.now();
                MovieTSVParser movieParser = new MovieTSVParser(conn);
                movieParser.parse(moviesFile.getAbsolutePath());
                Duration movieDuration = Duration.between(movieStart, Instant.now());
                System.out.println("✓ Loaded " + movieParser.getMoviesProcessed() + " movies with " + 
                    movieParser.getGenreLinksCreated() + " genre links in " + movieDuration.getSeconds() + 
                    "s (skipped " + movieParser.getLinesSkipped() + " non-movies)");
                System.out.println();
            } else {
                System.err.println("❌ ERROR: Movies file not found: " + moviesFile.getAbsolutePath());
                System.exit(1);
            }
            
            // 3. Load cast relationships (links movies to actors)
            File castsFile = new File(DATA_DIR, CASTS_FILE);
            if (castsFile.exists()) {
                System.out.println("📂 Loading cast relationships from: " + castsFile.getAbsolutePath());
                Instant castStart = Instant.now();
                CastTSVParser castParser = new CastTSVParser(conn);
                castParser.parse(castsFile.getAbsolutePath());
                Duration castDuration = Duration.between(castStart, Instant.now());
                System.out.println("✓ Created " + castParser.getLinksCreated() + " cast links in " + 
                    castDuration.getSeconds() + "s (skipped " + castParser.getLinesSkipped() + " non-actors)");
                System.out.println();
            } else {
                System.out.println("⚠️  Skipping casts (file not found): " + castsFile.getAbsolutePath());
                System.out.println();
            }
            
            // Restore indexes and analyze
            restoreIndexes(conn);
            
            // Verify data
            verifyData(conn);
            
            Duration duration = Duration.between(startTime, Instant.now());
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.printf("║  ✅ Data loading completed successfully!         ║%n");
            System.out.printf("║  ⏱️  Time taken: %02d:%02d:%02d                         ║%n",
                duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
            System.out.println("╚═══════════════════════════════════════════════════╝");
            
        } catch (Exception e) {
            System.err.println();
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void optimizeDatabaseForLoading(Connection conn) throws SQLException {
        System.out.println("⚙️  Optimizing database for bulk loading...");
        
        try (Statement stmt = conn.createStatement()) {
            // Drop indexes temporarily for faster inserts
            stmt.execute("DROP INDEX IF EXISTS idx_movies_title");
            stmt.execute("DROP INDEX IF EXISTS idx_movies_year");
            stmt.execute("DROP INDEX IF EXISTS idx_stars_name");
            
            // Disable constraint checking temporarily (if needed)
            // stmt.execute("SET CONSTRAINTS ALL DEFERRED");
            
            conn.commit();
            System.out.println("✓ Indexes dropped temporarily");
            System.out.println();
        }
    }
    
    private static void restoreIndexes(Connection conn) throws SQLException {
        System.out.println("🔧 Restoring indexes and analyzing tables...");
        
        try (Statement stmt = conn.createStatement()) {
            // Recreate indexes
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_movies_title ON movies(title)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_movies_year ON movies(year)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_stars_name ON stars(name)");
            
            // Update statistics for query optimizer
            stmt.execute("ANALYZE movies");
            stmt.execute("ANALYZE stars");
            stmt.execute("ANALYZE genres");
            stmt.execute("ANALYZE genres_in_movies");
            stmt.execute("ANALYZE stars_in_movies");
            
            conn.commit();
            System.out.println("✓ Indexes restored and statistics updated");
            System.out.println();
        }
    }
    
    private static void verifyData(Connection conn) throws SQLException {
        System.out.println("📊 Data Verification Results:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        try (Statement stmt = conn.createStatement()) {
            // Check movies count
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM movies");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.printf("📽️  Movies: %,d%n", count);
                
                if (count < 10000) {
                    System.out.println("   ⚠️  WARNING: Less than 10,000 movies (Requirement not met!)");
                } else {
                    System.out.println("   ✅ Meets requirement (>10,000 movies)");
                }
            }
            
            // Check stars count
            rs = stmt.executeQuery("SELECT COUNT(*) FROM stars");
            if (rs.next()) {
                System.out.printf("⭐ Stars: %,d%n", rs.getInt(1));
            }
            
            // Check genres count
            rs = stmt.executeQuery("SELECT COUNT(DISTINCT genre_id) FROM genres_in_movies");
            if (rs.next()) {
                System.out.printf("🎭 Genres: %,d%n", rs.getInt(1));
            }
            
            // Check movies with genres
            rs = stmt.executeQuery(
                "SELECT COUNT(DISTINCT movie_id) FROM genres_in_movies"
            );
            if (rs.next()) {
                System.out.printf("🏷️  Movies with genres: %,d%n", rs.getInt(1));
            }
            
            // Check movies with stars
            rs = stmt.executeQuery(
                "SELECT COUNT(DISTINCT movie_id) FROM stars_in_movies"
            );
            if (rs.next()) {
                System.out.printf("👥 Movies with cast: %,d%n", rs.getInt(1));
            }
            
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
    }
}
