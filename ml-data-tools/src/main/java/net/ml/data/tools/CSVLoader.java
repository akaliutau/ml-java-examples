package net.ml.data.tools;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lombok.extern.slf4j.Slf4j;
import net.ml.data.utils.Schema;
import net.ml.data.utils.TypeMapper;

@Slf4j
public class CSVLoader implements AutoCloseable {

    private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
    private static final String SQL_DELETE = "DELETE FROM ${table}";
    
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    private Connection connection;
    private char seprator;

    /**
     * Public constructor to build CSVLoader object with Connection details. The
     * connection is closed on success or failure.
     * @param pwd 
     * @param user 
     * @param jDBC_CONNECTION_URL
     * @throws SQLException 
     */
    public CSVLoader(String connectionURL, String user, String pwd) throws SQLException {
        this.connection =  DriverManager.getConnection(connectionURL, user, pwd);
        // Set default separator
        this.seprator = ';';
    }
    
    /**
     * Parse CSV file from archive.
     * @param csvFile            Input CSV file
     * @param tableName          Database table name to import data
     * @param truncateBeforeLoad Truncate the table before inserting new records.
     * @throws Exception
     */
    public void loadZCSV(String zipFile, String entry, String tableName, Schema schema, boolean truncateBeforeLoad) throws Exception {
        ZCSVFile zip = new ZCSVFile(zipFile);
        File f = zip.getFile(entry);
        loadCSV(f.getAbsolutePath(), tableName, schema, truncateBeforeLoad);
    }
    /**
     * Parse CSV file using OpenCSV library and load in given database table.
     * @param csvFile            Input CSV file
     * @param tableName          Database table name to import data
     * @param truncateBeforeLoad Truncate the table before inserting new records.
     * @throws Exception
     */
    public void loadCSV(String csvFile, String tableName, Schema schema, boolean truncateBeforeLoad) throws Exception {

        if (this.connection == null) {
            throw new Exception("Not a valid connection.");
        }
        
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).withCSVParser(new CSVParserBuilder()
                .withSeparator(this.seprator).build()).build()) {

            if (schema == null) {
                throw new IllegalArgumentException("No columns defined in given CSV file. Please check the CSV file format.");
            }

            String questionmarks = StringUtils.repeat("?,", schema.size());
            questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);

            String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName)
                    .replaceFirst(KEYS_REGEX, StringUtils.join(schema.getColumns(), ","))
                    .replaceFirst(VALUES_REGEX, questionmarks);

            log.info("Query: {}", query);

            String[] nextLine;
            int count = 0;
            int totalCount = 0;
            int invalidCount = 0;

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                

                if (truncateBeforeLoad) {
                    // delete data from table before loading csv
                    connection.createStatement().execute(SQL_DELETE.replaceFirst(TABLE_REGEX, tableName));
                }

                final int batchSize = 2000;
                while ((nextLine = csvReader.readNext()) != null) {
                    totalCount ++;
                    if (null != nextLine) {
                        try {
                           TypeMapper.prepare(ps, schema, nextLine);
                           ps.addBatch();
                           count ++;
                        }catch(IllegalArgumentException e) {
                            invalidCount ++;
                        }
                    }
                    if (count % batchSize == 0) {
                        ps.executeBatch();
                        log.info("saving data, batch {}", count / batchSize);
                    }
                }
                ps.executeBatch(); // insert remaining records
                connection.commit();
                log.info("total records processed: {}", totalCount);
                log.info("inserted: {}", count);
                log.info("omitted: {}", invalidCount);
            } catch (Exception e) {
                connection.rollback();
                throw new Exception("Error occured while loading data from file to database, line " + count + "," + e.getMessage());
            }

        } catch (Exception e) {
            throw new Exception("Error occured while executing file. " + e.getMessage());
        }
        
    }

    public void setSeprator(char seprator) {
        this.seprator = seprator;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

}
