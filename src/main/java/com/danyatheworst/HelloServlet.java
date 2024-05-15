package main.java.com.danyatheworst;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.CurrencyRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "HelloServlet", urlPatterns = {"/helloworld"})
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Currency> currencies = new CurrencyRepository().get();

            currencies.forEach(currency -> {
                try {
                    resp.getWriter().print(currency.fullName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
