package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;



@WebServlet(name = "CurrenciesServlet", urlPatterns = {"/currencies"})
public class CurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            List<Currency> currencies = new CurrencyRepository().getAll();
            String currenciesJson = new Gson().toJson(currencies);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write(currenciesJson);
            printWriter.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Currency newCurrency = new Currency(
                null,
                req.getParameter("name"),
                req.getParameter("code"),
                req.getParameter("sign")
        );
        try {
            int createdCurrency = new CurrencyRepository().create(newCurrency);
            String currenciesJson = new Gson().toJson(createdCurrency);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write(currenciesJson);
            printWriter.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
