package net.ml.data;

import static net.ml.data.utils.SQLTypes.*;

import net.ml.data.tools.CSVLoader;
import net.ml.data.utils.SQLTypes;
import net.ml.data.utils.Schema;

/**
 * 
 * Data loader for csv
 * @author akaliutau
 *
 */
public class DataLoader {

    private static String JDBC_CONNECTION_URL = "jdbc:postgresql://localhost:5432/postgres";
    static String bxbooks = "./../data/books.zip";
    static String bxbookrate = "./../data/book-ratings.zip";
    static String users = "./../data/users.zip";
    
    final static Schema ratingsSchema = new Schema(
            new String[] { "user_id", "item_id", "preference" }, 
            new SQLTypes[] { BIGINT, ISBN_INT, REAL });
    
    final static Schema booksSchema = new Schema(
            new String[] { "item_id", "title", "author", "year", "publisher", "image_url_s", "image_url_m", "image_url_b" }, 
            new SQLTypes[] { ISBN_INT, VARCHAR, VARCHAR, INT, VARCHAR, VARCHAR, VARCHAR, VARCHAR });
    
    final static Schema usersSchema = new Schema(
            new String[] { "user_id", "location", "age" }, 
            new SQLTypes[] { BIGINT, VARCHAR, INT });

    public static void main(String[] args) {
        try (CSVLoader loader = new CSVLoader(JDBC_CONNECTION_URL, "postgres", "postgres")) {
            loader.loadZCSV(bxbookrate, "book-ratings.csv",  "preferences", ratingsSchema, true);
            loader.loadZCSV(bxbooks, "books.csv", "books", booksSchema, true);
            loader.loadZCSV(users, "users.csv", "users", usersSchema, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
