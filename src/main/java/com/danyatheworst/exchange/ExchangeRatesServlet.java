package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", urlPatterns = {"/exchangeRates"})
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRate> exchangeRates = this.exchangeRateRepository.findAll();
        //TODO:map to response and return;
        resp.setStatus(HttpServletResponse.SC_OK);
        this.gson.toJson(exchangeRates, resp.getWriter());

    }
}
