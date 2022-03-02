import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Task1 {
    public static void main(String[] args) {
        int indexArgument = java.util.Arrays.asList(args).indexOf("-fileName");
        String url = "jdbc:sqlite:" + args[indexArgument + 1];

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);


        try (Connection con = dataSource.getConnection()) {

            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS HOUSES(" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "words TEXT NOT NULL)");
//                try {
//                    statement.executeUpdate("INSERT INTO HOUSES VALUES " +
//                            "(1, 'Targaryen of King''s Landing', 'Fire and Blood')," +
//                            "(2, 'Stark of Winterfell', 'Summer is Coming')," +
//                            "(3, 'Lannister of Casterly Rock', 'Hear Me Roar!')");
//                } catch (Exception ignored) {
//
//                }
//                statement.executeUpdate("UPDATE HOUSES " +
//                        "SET words = 'Winter is coming' " +
//                        "WHERE id = 2");
//                try (ResultSet greatHouses = statement.executeQuery("SELECT * FROM HOUSES")) {
//                    while (greatHouses.next()) {
//                        // Retrieve column values
//                        int id = greatHouses.getInt("id");
//                        String name = greatHouses.getString("name");
//                        String words = greatHouses.getString("words");
//
//                        System.out.printf("House %d%n", id);
//                        System.out.printf("\tName: %s%n", name);
//                        System.out.printf("\tWords: %s%n", words);
//                    }
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String insert = "INSERT INTO artists (name, origin, songs_number) VALUES (?, ?, ?)";

            String insert1 = "INSERT INTO HOUSES VALUES (?,?,?)";


            try (PreparedStatement preparedStatement = con.prepareStatement(insert1)) {
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "Targaryen of King''s Landing");
                preparedStatement.setString(3, "Fire and Blood");
                preparedStatement.executeUpdate();
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, "Stark of Winterfell");
                preparedStatement.setString(3, "Summer is Coming");
                preparedStatement.executeUpdate();
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "Lannister of Casterly Rock");
                preparedStatement.setString(3, "Hear Me Roar!");

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
