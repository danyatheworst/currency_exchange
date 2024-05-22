package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.exceptions.ApplicationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", urlPatterns = {"/exchangeRates"})
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final Gson gson = new Gson();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        try {
            List<ExchangeRate> exchangeRates = this.exchangeRateRepository.getAll();
            //TODO:map to response and return;
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(this.gson.toJson(exchangeRates));
        } catch (ApplicationException e) {
            resp.setStatus(e.status);
            printWriter.write(this.gson.toJson(e.getMessage()));
        } finally {
            printWriter.close();
        }
    }
}
