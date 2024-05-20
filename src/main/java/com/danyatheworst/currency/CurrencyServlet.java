package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.ErrorResponse;
import main.java.com.danyatheworst.Validation;
import main.java.com.danyatheworst.exceptions.ApplicationException;
import main.java.com.danyatheworst.exceptions.UnknownException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "CurrencyServlet", urlPatterns = {"/currency/*"})
public class CurrencyServlet extends HttpServlet {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String code = req.getPathInfo().replaceAll("/", "");
            Validation.isCodeValid(code);

            Optional<Currency> currency = this.currencyRepository.getBy(code.toUpperCase());
            if (currency.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().print(gson.toJson(new ErrorResponse("Such currency has not been found")));
                return;
            }

            PrintWriter printWriter = resp.getWriter();
            //TODO: map currency to currencyResponse
            printWriter.write(this.gson.toJson(currency));
            printWriter.close();
        } catch (ApplicationException e) {
            resp.setStatus(e.status);
            resp.getWriter().print(gson.toJson(new ErrorResponse(e.getMessage())));
        }
    }
}