package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;

@WebServlet(name = "ExchangeRateServlet", urlPatterns = {"/exchangeRate/*"})
public class ExchangeRateServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodes = req.getPathInfo().replaceFirst("/", "");
        if (currencyCodes.length() != 6) {
            throw new InvalidParameterException("Currency codes must be in ISO 4217 format");
        }
        String baseCurrencyCode = currencyCodes.substring(0, 3);
        String targetCurrencyCode = currencyCodes.substring(3, 6);
        ValidationUtils.validateCurrencyCode(baseCurrencyCode);
        ValidationUtils.validateCurrencyCode(targetCurrencyCode);

        ExchangeRate exchangeRate = this.exchangeRateRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(MappingUtils.convertToDto(exchangeRate), resp.getWriter());
    }
}