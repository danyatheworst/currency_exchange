package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.Validation;
import main.java.com.danyatheworst.exceptions.ApplicationException;
import main.java.com.danyatheworst.ErrorResponse;
import main.java.com.danyatheworst.exceptions.UnknownException;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "CurrenciesServlet", urlPatterns = {"/currencies"})
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Currency> currencies = this.currencyRepository.findAll();
            List<CurrencyResponse> currenciesResponse =
                    Arrays.asList(this.modelMapper.map(currencies,  CurrencyResponse[].class));
            resp.setStatus(HttpServletResponse.SC_OK);
            this.gson.toJson(currenciesResponse, resp.getWriter());
        } catch (UnknownException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            this.gson.toJson(new ErrorResponse(e.getMessage()), resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        try {
            Validation.parameterPresence(code, "Code");
            Validation.parameterPresence(name, "Name");
            Validation.parameterPresence(sign, "Sign");
            Validation.isCodeValid(code);

            Currency newCurrency = new Currency(code, name, sign);
            newCurrency.id = this.currencyRepository.create(newCurrency);
            CurrencyResponse currencyResponse = this.modelMapper.map(newCurrency, CurrencyResponse.class);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            this.gson.toJson(currencyResponse, resp.getWriter());
        } catch (ApplicationException e) {
            resp.setStatus(e.status);
            this.gson.toJson(new ErrorResponse(e.getMessage()), resp.getWriter());
        }
    }
}
