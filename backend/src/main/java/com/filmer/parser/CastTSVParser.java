package com.filmer.parser;

import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * TSV Parser for IMDb title.principals.tsv.gz file
 * Links movies to stars via the stars_in_movies junction table
 * 
 * TSV Format:
 * tconst	ordering	nconst	category	job	characters
 * tt0000001	1	nm0005690	actor	\N	["Herself"]
 * 
 * Database Table:
 * - stars_in_movies(star_id, movie_id)
 * 
 * Performance Optimizations:
 * - Batch inserts (1000 records per batch)
 * - Filters only actor/actress categories
 * - Preloads movie/star IDs into memory for fast lookups
 */
public class CastTSVParser {
    
    private Connection connection;
    private PreparedStatement castStmt;
    
    private Set<String> existingMovieIds;
    private Set<String> existingStarIds;
    
    private int batchCount = 0;
    private static final int BATCH_SIZE = 1000;
    
    private int linksCreated = 0;
    private int linesSkipped = 0;
    
    public CastTSVParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
        
        this.castStmt = connection.prepareStatement(
            "INSERT INTO stars_in_movies (star_id, movie_id) VALUES (?, ?) ON CONFLICT DO NOTHING"
        );
        
        // Preload all movie and star IDs into memory for fast lookups
        System.out.println("   Loading existing movie IDs...");
        this.existingMovieIds = loadExistingIds("SELECT id FROM movies");
        System.out.println("   ✓ Loaded " + existingMovieIds.size() + " movies");
        
        System.out.println("   Loading existing star IDs...");
        this.existingStarIds = loadExistingIds("SELECT id FROM stars");
        System.out.println("   ✓ Loaded " + existingStarIds.size() + " stars\n");
    }
    
    private Set<String> loadExistingIds(String query) throws SQLException {
        Set<String> ids = new HashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
        }
        return ids;
    }
    
    public void parse(String filePath) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new GZIPInputStream(
                        new FileInputStream(filePath)), "UTF-8"))) {
            
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                processLine(line);
            }
            
            finalizeParsing();
        }
    }
    
    private void processLine(String line) throws SQLException {
        String[] fields = line.split("\t", -1);
        
        if (fields.length < 4) {
            linesSkipped++;
            return;
        }
        
        String tconst = fields[0];
        String nconst = fields[2];
        String category = fields[3];
        
        // Filter: only actors/actresses
        if (!"actor".equals(category) && !"actress".equals(category)) {
            linesSkipped++;
            return;
        }
        
        // Check if movie exists (fast in-memory lookup)
        if (!existingMovieIds.contains(tconst)) {
            linesSkipped++;
            return;
        }
        
        // Check if star exists (fast in-memory lookup)
        if (!existingStarIds.contains(nconst)) {
            linesSkipped++;
            return;
        }
        
        // Create link
        castStmt.setString(1, nconst);
        castStmt.setString(2, tconst);
        castStmt.addBatch();
        batchCount++;
        linksCreated++;
        
        // Execute batch
        if (batchCount >= BATCH_SIZE) {
            castStmt.executeBatch();
            connection.commit();
            batchCount = 0;
            System.out.println("✓ Created " + linksCreated + " cast links...");
        }
    }
    
    public void finalizeParsing() throws SQLException {
        if (batchCount > 0) {
            castStmt.executeBatch();
            connection.commit();
        }
        castStmt.close();
    }
    
    public int getLinksCreated() { return linksCreated; }
    public int getLinesSkipped() { return linesSkipped; }
}
