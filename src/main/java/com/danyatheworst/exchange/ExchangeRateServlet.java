package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ExchangeRateServlet", urlPatterns = {"/exchangeRate/*"})
public class ExchangeRateServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            this.doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodes = getCurrencyCodes(req);
        String baseCurrencyCode = currencyCodes.substring(0, 3);
        String targetCurrencyCode = currencyCodes.substring(3);
        ValidationUtils.validateCurrencyCode(baseCurrencyCode);
        ValidationUtils.validateCurrencyCode(targetCurrencyCode);

        ExchangeRate exchangeRate = this.exchangeRateRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(MappingUtils.convertToDto(exchangeRate), resp.getWriter());
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodes = getCurrencyCodes(req);
        String baseCurrencyCode = currencyCodes.substring(0, 3);
        String targetCurrencyCode = currencyCodes.substring(3);
        String formString = req.getReader().readLine();
        if (formString == null || !formString.contains("rate")) {
            throw new InvalidParameterException("Missing parameter — rate");
        }
        Map<String, String> form = parseFormStringToMap(formString);

        ExchangeRatesRequestDto exchangeRateRequestDto = new ExchangeRatesRequestDto(
                baseCurrencyCode, targetCurrencyCode, form.get("rate")
        );
        ValidationUtils.validate(exchangeRateRequestDto);

        ExchangeRate exchangeRate = this.exchangeRateService.update(exchangeRateRequestDto);
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(MappingUtils.convertToDto(exchangeRate), resp.getWriter());
    }

    private static String getCurrencyCodes(HttpServletRequest req) {
        String currencyCodes = req.getPathInfo().replaceFirst("/", "");
        if (currencyCodes.length() != 6) {
            throw new InvalidParameterException("Currency codes must be in ISO 4217 format");
        }
        return currencyCodes;
    }

    private static Map<String, String> parseFormStringToMap(String formString) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            map.put(key, value);
        }
        return map;
    }
}