package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "CurrenciesServlet", urlPatterns = {"/currencies"})
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            List<CurrencyResponseDto> currenciesResponseDto = this.currencyRepository
                    .findAll()
                    .stream()
                    .map(MappingUtils::convertToDto)
                    .collect(Collectors.toList());
            resp.setStatus(HttpServletResponse.SC_OK);
            this.gson.toJson(currenciesResponseDto, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(code, name, sign);
        ValidationUtils.validate(currencyRequestDto);
        Currency newCurrency = this.currencyRepository.save(MappingUtils.convertToEntity(currencyRequestDto));

        resp.setStatus(HttpServletResponse.SC_CREATED);
        this.gson.toJson(MappingUtils.convertToDto(newCurrency), resp.getWriter());
    }
}
