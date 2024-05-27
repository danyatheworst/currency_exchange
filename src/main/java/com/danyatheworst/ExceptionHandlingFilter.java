package main.java.com.danyatheworst;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.danyatheworst.exceptions.EntityAlreadyExistsException;
import main.java.com.danyatheworst.exceptions.DatabaseOperationException;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;
import main.java.com.danyatheworst.exceptions.NotFoundException;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter extends HttpFilter {

    private final Gson gson = new Gson();

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            super.doFilter(servletRequest, servletResponse, filterChain);
        } catch (NotFoundException e) {
            servletResponse.setStatus(SC_NOT_FOUND);
            this.gson.toJson(new ErrorResponseDto(e.getMessage()), servletResponse.getWriter());
        } catch (EntityAlreadyExistsException e) {
            servletResponse.setStatus(SC_CONFLICT);
            this.gson.toJson(new ErrorResponseDto(e.getMessage()), servletResponse.getWriter());
        } catch (DatabaseOperationException e) {
            servletResponse.setStatus(SC_INTERNAL_SERVER_ERROR);
            this.gson.toJson(new ErrorResponseDto(e.getMessage()), servletResponse.getWriter());
        } catch (InvalidParameterException e) {
            servletResponse.setStatus(SC_BAD_REQUEST);
            this.gson.toJson(new ErrorResponseDto(e.getMessage()), servletResponse.getWriter());
        }
    }
}
