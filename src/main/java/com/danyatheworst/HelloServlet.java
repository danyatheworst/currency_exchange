package main.java.com.danyatheworst;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet(name = "HelloServlet", urlPatterns = {"/helloworld"})
public class HelloServlet extends HttpServlet {
    protected static Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:currency_exchange.db");
            connection.close();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("<h1>123123213</h1>");
        } catch (SQLException e) {
            e.printStackTrace();
//        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
//        } catch (URISyntaxException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(123);
        }


    }

}
