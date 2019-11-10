package refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLWriter {

    private HttpServletResponse response;

    HTMLWriter(HttpServletResponse response) {
        this.response = response;
    }

    public void writeString(String str) throws IOException {
        response.getWriter().println(str);
    }

    public void writeRS(ResultSet rs, String header, RSType type) throws IOException, SQLException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        if (type == RSType.NONE) {
            writeString(header);
            return;
        }
        writeString("<html><body>");
        writeString(header);
        switch (type) {
            case SINGLE: {
                if (rs.next()) {
                    writeString(String.valueOf(rs.getInt(1)));
                }
                break;
            }
            case PAIR: {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price  = rs.getInt("price");
                    writeString(name + "\t" + price + "</br>");
                }
            }
        }
        writeString("</body></html>");
    }

}
