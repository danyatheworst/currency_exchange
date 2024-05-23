package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.ErrorResponse;
import main.java.com.danyatheworst.Validation;
import main.java.com.danyatheworst.exceptions.ApplicationException;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "CurrencyServlet", urlPatterns = {"/currency/*"})
public class CurrencyServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String code = req.getPathInfo().replaceAll("/", "");
            Validation.isCodeValid(code);

            Currency currency = this.currencyRepository.findByCode(code.toUpperCase())
                    .orElseThrow(() -> new ApplicationException("Currency with code " + code + " not found", 404));

            this.gson.toJson(this.modelMapper.map(currency, CurrencyResponse.class), resp.getWriter());
        } catch (ApplicationException e) {
            resp.setStatus(e.status);
            this.gson.toJson(new ErrorResponse(e.getMessage()), resp.getWriter());
        }
    }
}