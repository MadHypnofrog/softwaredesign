package refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import refactoring.servlet.AddProductServlet;
import refactoring.servlet.DBQuery;
import refactoring.servlet.GetProductsServlet;
import refactoring.servlet.QueryServlet;
import refactoring.servlet.RSType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static PrintWriter writer;

    @BeforeEach
    void init() throws IOException {

        request = mock(HttpServletRequest.class);
        when(request.getParameter("name")).thenReturn("Product");
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        String sql = "DROP TABLE IF EXISTS PRODUCT";
        DBQuery.executeQuery(sql, null, "", RSType.NONE);
        sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        DBQuery.executeQuery(sql, null, "", RSType.NONE);
    }

    @Test
    void addAndGetTest() {

        AddProductServlet addServlet = new AddProductServlet();
        GetProductsServlet getServlet = new GetProductsServlet();

        for (int price = 0; price < 10; price++) {
            when(request.getParameter("price")).thenReturn(String.valueOf(price));
            addServlet.get(request, response);
        }

        getServlet.get(request, response);

        for (int price = 0; price < 10; price++) {
            verify(writer, times(1)).println("Product\t" + String.valueOf(price) + "</br>");
        }
        verify(writer, times(10)).println("OK");

    }

    @Test
    void addAndQueryTest() throws IOException {

        AddProductServlet addServlet = new AddProductServlet();
        QueryServlet queryServlet = new QueryServlet();

        for (int price = 0; price < 10; price++) {
            when(request.getParameter("price")).thenReturn(String.valueOf(price));
            addServlet.get(request, response);
        }

        when(request.getParameter("command")).thenReturn("max");
        queryServlet.get(request, response);
        verify(writer, times(1)).println("Product\t9</br>");

        when(request.getParameter("command")).thenReturn("min");
        queryServlet.get(request, response);
        verify(writer, times(1)).println("Product\t0</br>");

        when(request.getParameter("command")).thenReturn("sum");
        queryServlet.get(request, response);
        verify(writer, times(1)).println("45");

        when(request.getParameter("command")).thenReturn("count");
        queryServlet.get(request, response);
        verify(writer, times(1)).println("10");

        when(request.getParameter("command")).thenReturn("zzzz");
        queryServlet.get(request, response);
        verify(writer, times(1)).println("Unknown command: zzzz");

    }

}
