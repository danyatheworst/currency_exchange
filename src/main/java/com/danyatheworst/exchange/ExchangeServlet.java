package main.java.com.danyatheworst.exchange;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.exchange.dto.ExchangeDto;
import main.java.com.danyatheworst.exchange.dto.ExchangeRequestDto;
import main.java.com.danyatheworst.utils.ConvertingUtils;
import main.java.com.danyatheworst.utils.ParsingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(name = "ExchangeServlet", urlPatterns = {"/exchange"})
public class ExchangeServlet extends HttpServlet {
    private final ExchangeService exchangeService = new ExchangeService();
    private final Gson gson = new Gson();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> queryParams = ParsingUtils.parse(req.getQueryString());
            BigDecimal amount = ConvertingUtils.convert(
                    queryParams.get("amount"), "Amount to exchange must be a positive number");

        ExchangeRequestDto exchangeRequestDto = new ExchangeRequestDto(
                queryParams.get("from"),
                queryParams.get("to"),
                amount
        );
        ValidationUtils.validate(exchangeRequestDto);

        ExchangeDto exchangeDto = this.exchangeService.exchange(exchangeRequestDto);
        gson.toJson(exchangeDto, resp.getWriter());
    }
}
