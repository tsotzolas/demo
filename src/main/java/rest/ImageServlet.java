package rest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/reportsApi/*")
public class ImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(System.getProperty("jboss.server.temp.dir"), filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}

