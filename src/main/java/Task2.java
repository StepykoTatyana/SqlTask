import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Task2 {
    public static void main(String[] args) {
        int indexArgument = java.util.Arrays.asList(args).indexOf("-fileName");
        String url = "jdbc:sqlite:store.db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        String insertInvoiceSQL = "INSERT INTO \"invoice\" " +
                "(id, shipping_address, total_cost) VALUES (?, ?, ?)";

        String insertOrderSQL = "INSERT INTO \"order\" " +
                "(id, invoice_id, product_name) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS \"invoice\"(" +
                        "id INTEGER PRIMARY KEY," +
                        "shipping_address TEXT NOT NULL," +
                        "total_cost INTEGER NOT NULL)");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS \"order\"(" +
                        "id INTEGER PRIMARY KEY," +
                        "invoice_id INTEGER NOT NULL," +
                        "product_name TEXT NOT NULL," +
                        " FOREIGN KEY (invoice_id) REFERENCES invoice (id))");
            }
            // Disable auto-commit mode
            con.setAutoCommit(false);

            try (PreparedStatement insertInvoice = con.prepareStatement(insertInvoiceSQL);
                 PreparedStatement insertOrder = con.prepareStatement(insertOrderSQL)) {

                // Insert an invoice
                int invoiceId = 1;
                insertInvoice.setInt(1, invoiceId);
                insertInvoice.setString(2, "Dearborn, Michigan");
                insertInvoice.setInt(3, 100500);
                insertInvoice.executeUpdate();

                // Insert an order
                int orderId = 1;
                insertOrder.setInt(1, orderId);
                insertOrder.setInt(2, invoiceId);
                insertOrder.setString(3, "Ford Model A");
                insertOrder.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        con.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
