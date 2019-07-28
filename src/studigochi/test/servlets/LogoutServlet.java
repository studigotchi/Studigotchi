package studigochi.test.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Called by the client to logout and invalidate the session.
 */
@WebServlet(value = "/user/logout", name = "Logout Servlet")
public class LogoutServlet extends HttpServlet {
    /**
     * Invalidate the session<br/>
     * Then, redirect to the login page
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final HttpSession session = req.getSession(false);
        if(session != null)
            session.invalidate();

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
