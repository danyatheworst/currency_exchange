package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.utils.MappingUtils;
import main.java.com.danyatheworst.utils.ValidationUtils;
import main.java.com.danyatheworst.exceptions.NotFoundException;

import java.io.IOException;

@WebServlet(name = "CurrencyServlet", urlPatterns = {"/currency/*"})
public class CurrencyServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getPathInfo().replaceAll("/", "");
        ValidationUtils.validateCurrencyCode(code);

        Currency currency = this.currencyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Currency with code " + code + " not found"));

        this.gson.toJson(MappingUtils.convertToDto(currency), resp.getWriter());
    }
}