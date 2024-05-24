package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ExchangeRatesServlet", urlPatterns = {"/exchangeRates"})
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateResponseDto> exchangeRatesResponseDto = this.exchangeRateRepository
                .findAll()
                .stream()
                .map(MappingUtils::convertToDto)
                .toList();
        resp.setStatus(HttpServletResponse.SC_OK);
        this.gson.toJson(exchangeRatesResponseDto, resp.getWriter());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(
                req.getParameter("baseCurrencyCode"),
                req.getParameter("targetCurrencyCode"),
                req.getParameter("rate")
        );
        ValidationUtils.validate(exchangeRateRequestDto);
    }
}
