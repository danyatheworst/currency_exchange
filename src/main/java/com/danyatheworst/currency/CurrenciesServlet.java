package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.ErrorResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "CurrenciesServlet", urlPatterns = {"/currencies"})
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            List<Currency> currencies = this.currencyRepository.getAll();
            String currenciesJson = this.gson.toJson(currencies);
            PrintWriter printWriter = resp.getWriter();

            printWriter.write(currenciesJson);
            printWriter.close();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //TODO: validation
        Currency newCurrency = new Currency(
                null, req.getParameter("name"),
                req.getParameter("code"),
                req.getParameter("sign")
        );

        PrintWriter printWriter = resp.getWriter();
        try {
            newCurrency.id = this.currencyRepository.create(newCurrency);
            String currenciesJson = this.gson.toJson(newCurrency);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write(currenciesJson);
            System.out.println("try");
        } catch (SQLException e) {
//            resp.setStatus(e.ge);
            printWriter.write(this.gson.toJson(new ErrorResponse(e.getMessage())));
        }  finally {
            printWriter.close();
        }
    }
}
