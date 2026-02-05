package com.filmer.parser;

import java.sql.*;

/**
 * Standalone loader to only load cast relationships
 * Use this when movies and stars are already loaded
 */
public class CastOnlyLoader {
    
    public static void main(String[] args) {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            System.err.println("❌ ERROR: Missing environment variables");
            System.err.println("Required: DB_URL, DB_USER, DB_PASSWORD");
            System.exit(1);
        }
        
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("🎬 Loading cast relationships...\n");
            
            long startTime = System.currentTimeMillis();
            
            CastTSVParser castParser = new CastTSVParser(connection);
            castParser.parse("data/title.principals.tsv.gz");
            
            long duration = (System.currentTimeMillis() - startTime) / 1000;
            
            System.out.println("\n✅ Cast loading complete!");
            System.out.println("   Links created: " + castParser.getLinksCreated());
            System.out.println("   Lines skipped: " + castParser.getLinesSkipped());
            System.out.println("   Duration: " + duration + "s");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
