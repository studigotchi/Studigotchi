package studigochi.test;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "User", value = "/user")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();

        writer.print("{\n" +
                "  \"name\": \"Studigotchi\",\n" +
                "  \"status\": \"SLEEP\",\n" +
                "  \"time\": 12345,\n" +
                "  \"hearts\": 5.0,\n" +
                "  \"stars\": 1.0\n" +
                "}");
    }
}
