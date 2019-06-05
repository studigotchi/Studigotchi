package studigochi.test.servlets;

import studigochi.test.models.UserModel;
import studigochi.test.student.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

abstract class AbstractUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();

        final HttpSession session = req.getSession(true);
        if (session.getAttribute("student") == null) {
            session.setAttribute("student", new Student(new UserModel("Studi", "", 1, 1, 5.0d, 0.0d)));
        }

        final Student student = (Student) session.getAttribute("student");
        this.handleStudent(student);
        writer.print(student.toJSONString());
    }

    abstract void handleStudent(Student student);
}
