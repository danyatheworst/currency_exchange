package main.java.com.danyatheworst.currency;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.ErrorResponse;
import main.java.com.danyatheworst.Validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet(name = "CurrencyServlet", urlPatterns = {"/currency/*"})
public class CurrencyServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String code = req.getPathInfo().replaceAll("/", "");

        if (!Validation.isCodeValid(code)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(gson.toJson(new ErrorResponse("Invalid currency code")));
            return;
        }

        try {
            Currency currency = new CurrencyRepository().get(code);
            PrintWriter printWriter = resp.getWriter();

            // if id == 0 {
            // resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // resp.getWriter().print(gson.toJson(new ErrorResponse("Invalid currency code")));
            // }

            printWriter.write(this.gson.toJson(currency));
            printWriter.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}