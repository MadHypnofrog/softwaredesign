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
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        DBQuery.executeQuery("SELECT * FROM PRODUCT", new HTMLWriter(response), "", RSType.PAIR);
    }

    public void get(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
