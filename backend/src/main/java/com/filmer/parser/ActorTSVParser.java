package com.filmer.parser;

import java.io.*;
import java.sql.*;
import java.util.zip.GZIPInputStream;

/**
 * TSV Parser for IMDb name.basics.tsv.gz file
 * Parses actor/actress data and performs batch inserts
 * 
 * TSV Format:
 * nconst	primaryName	birthYear	deathYear	primaryProfession	knownForTitles
 * nm0000001	Fred Astaire	1899	1987	actor,miscellaneous,producer	tt0053137,tt0031983,tt0072308,tt0050419
 * 
 * Database Table:
 * - stars(id, name, birth_year)
 * 
 * Performance Optimizations:
 * - Batch inserts (1000 records per batch)
 * - Filters only actors/actresses
 */
public class ActorTSVParser {
    
    private Connection connection;
    private PreparedStatement starStmt;
    
    private int batchCount = 0;
    private static final int BATCH_SIZE = 1000;
    
    private int actorsProcessed = 0;
    private int linesSkipped = 0;
    
    public ActorTSVParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
        
        this.starStmt = connection.prepareStatement(
            "INSERT INTO stars (id, name, birth_year) VALUES (?, ?, ?) " +
            "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, birth_year = EXCLUDED.birth_year"
        );
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
        
        if (fields.length < 6) {
            linesSkipped++;
            return;
        }
        
        String nconst = fields[0];
        String primaryName = fields[1];
        String birthYear = fields[2];
        String primaryProfession = fields[4];
        
        // Filter: only actors/actresses
        if (!primaryProfession.contains("actor") && !primaryProfession.contains("actress")) {
            linesSkipped++;
            return;
        }
        
        // Parse birth year
        Integer year = null;
        if (!"\\N".equals(birthYear)) {
            try {
                year = Integer.parseInt(birthYear);
            } catch (NumberFormatException e) {
                // Skip
            }
        }
        
        // Insert star
        starStmt.setString(1, nconst);
        starStmt.setString(2, primaryName);
        if (year != null) {
            starStmt.setInt(3, year);
        } else {
            starStmt.setNull(3, Types.INTEGER);
        }
        starStmt.addBatch();
        batchCount++;
        actorsProcessed++;
        
        // Execute batch
        if (batchCount >= BATCH_SIZE) {
            starStmt.executeBatch();
            connection.commit();
            batchCount = 0;
            System.out.println("✓ Processed " + actorsProcessed + " actors...");
        }
    }
    
    public void finalizeParsing() throws SQLException {
        if (batchCount > 0) {
            starStmt.executeBatch();
            connection.commit();
        }
        starStmt.close();
    }
    
    public int getActorsProcessed() { return actorsProcessed; }
    public int getLinesSkipped() { return linesSkipped; }
}
