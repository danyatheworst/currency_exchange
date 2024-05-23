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
        PrintWriter printWriter = resp.getWriter();
        try {
            String code = req.getPathInfo().replaceAll("/", "");
            Validation.isCodeValid(code);

            Optional<Currency> currency = this.currencyRepository.findByCode(code.toUpperCase());
            if (currency.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().print(gson.toJson(new ErrorResponse("Such currency has not been found")));
                return;
            }
            CurrencyResponse currencyResponse = this.modelMapper.map(currency.get(), CurrencyResponse.class);
            printWriter.write(this.gson.toJson(currencyResponse));
        } catch (ApplicationException e) {
            resp.setStatus(e.status);
            printWriter.print(gson.toJson(new ErrorResponse(e.getMessage())));
        } finally {
            printWriter.close();
        }
    }
}