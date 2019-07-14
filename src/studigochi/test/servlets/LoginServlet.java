package studigochi.test.servlets;

import studigochi.test.database.DataBaseTools;
import studigochi.test.student.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/login", name = "LoginServlet", description = "Used for login and Registration, accepts POSTs")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final boolean isRegister = "register".equals(req.getParameter("type"));

        final String userName = req.getParameter("userName");
        final String password = req.getParameter("password");

        final Student student;
        if (isRegister) {
            student = DataBaseTools.register(userName, password);
        } else {
            student = DataBaseTools.login(userName, password);
        }

        if(student == null) {
            req.getRequestDispatcher("/Login_Register.jsp").forward(req, resp);
        } else {
            student.startTimer();
            req.getSession(true).setAttribute("student", student);
            resp.sendRedirect(req.getContextPath() + "/FirstImpression.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Login_Register.jsp").forward(req, resp);
    }
}
