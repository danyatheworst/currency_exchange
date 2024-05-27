package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", urlPatterns = {"/exchangeRates"})
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRatesRequestDto exchangeRateRequestDto = new ExchangeRatesRequestDto(
                req.getParameter("baseCurrencyCode"),
                req.getParameter("targetCurrencyCode"),
                req.getParameter("rate")
        );
        ValidationUtils.validate(exchangeRateRequestDto);

        ExchangeRateResponseDto exchangeRateResponseDto =
                MappingUtils.convertToDto(this.exchangeRateService.save(exchangeRateRequestDto));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        this.gson.toJson(exchangeRateResponseDto, resp.getWriter());
    }
}
