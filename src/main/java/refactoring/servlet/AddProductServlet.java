package refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        DBQuery.executeQuery("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")", new HTMLWriter(response), "OK", RSType.NONE);
    }

    public void get(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
