import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Task3 {
    public static void main(String[] args) {
        int indexArgument = java.util.Arrays.asList(args).indexOf("-fileName");
        String url = "jdbc:sqlite:store.db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        String insertInvoiceSQL = "INSERT INTO \"invoice\" " +
                "(id, shipping_address, total_cost) VALUES (?, ?, ?)";

        String selectAddressSQL = "SELECT shipping_address " +
                "FROM \"invoice\" WHERE id = ?";


        try (Connection con = dataSource.getConnection()) {

            // Disable auto-commit mode
            con.setAutoCommit(false);

            try (PreparedStatement insertInvoice = con.prepareStatement(insertInvoiceSQL)) {

                // Create a savepoint
                Savepoint savepoint = con.setSavepoint();

                // Insert an invoice
                int invoiceId = 1;
                insertInvoice.setInt(1, invoiceId);
                insertInvoice.setString(2, "Dearborn, Michigan");
                insertInvoice.setInt(3, 100500);
                insertInvoice.executeUpdate();

                PreparedStatement selectAddress = con.prepareStatement(selectAddressSQL);
                selectAddress.setInt(1, invoiceId);
                ResultSet resultSet = selectAddress.executeQuery();

                if (resultSet.getString(1).equals("Dearborn, Michigan")) {
                    con.rollback(savepoint);
                }

                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
