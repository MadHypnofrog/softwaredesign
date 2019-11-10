package refactoring.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBQuery {

    public static void executeQuery(String query, HTMLWriter writer, String header, RSType type) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();
            if (writer != null) {
                writer.writeRS(rs, header, type);
            }
            if (rs != null) {
                rs.close();
            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
