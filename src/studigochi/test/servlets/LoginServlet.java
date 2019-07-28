package studigochi.test.servlets;

import studigochi.test.database.DataBaseTools;
import studigochi.test.student.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet controlling the login process<br/>
 * GET serves the login page, POSTs accept the login credentials
 */
@WebServlet(value = "/login", name = "LoginServlet", description = "Used for login and Registration, accepts POSTs")
public class LoginServlet extends HttpServlet {

    /**
     * Usually called by the login form to submit a login/register request.<br/>
     * On successful login/register redirects to the game page, otherwise stay on the login page.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //The Form has a required radiobutton "type" to switch between register and login
        final boolean isRegister = "register".equals(req.getParameter("type"));

        //Read the credentials
        final String userName = req.getParameter("userName");
        final String password = req.getParameter("password");

        final Student student;
        if (isRegister) {
            student = DataBaseTools.register(userName, password);
        } else {
            student = DataBaseTools.login(userName, password);
        }

        if(student == null) {
            //Login failed, show the login page again
            req.getRequestDispatcher("/Login_Register.jsp").forward(req, resp);
        } else {
            //Login successful, start the user's timer (handles the status updates)
            student.startTimer();
            req.getSession(true).setAttribute("student", student);
            //Redirect to the main page, since we are now logged in.
            resp.sendRedirect(req.getContextPath() + "/Game");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Every call here redirects to the login jsp.
        req.getRequestDispatcher("/Login_Register.jsp").forward(req, resp);
    }
}
