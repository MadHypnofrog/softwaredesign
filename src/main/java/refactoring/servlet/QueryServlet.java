package refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            DBQuery.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                    new HTMLWriter(response), "<h1>Product with max price: </h1>", RSType.PAIR);
        } else if ("min".equals(command)) {
            DBQuery.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                    new HTMLWriter(response), "<h1>Product with min price: </h1>", RSType.PAIR);
        } else if ("sum".equals(command)) {
            DBQuery.executeQuery("SELECT SUM(price) FROM PRODUCT",
                    new HTMLWriter(response), "Summary price: ", RSType.SINGLE);
        } else if ("count".equals(command)) {
            DBQuery.executeQuery("SELECT COUNT(*) FROM PRODUCT",
                    new HTMLWriter(response), "Number of products: ", RSType.SINGLE);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

    }

    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
