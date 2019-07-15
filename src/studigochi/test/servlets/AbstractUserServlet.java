package studigochi.test.servlets;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final PrintWriter writer = resp.getWriter();

        final Student student = (Student) req.getSession().getAttribute("student");
        this.handleStudent(student);
        writer.print(student.toJSONString());
    }

    abstract void handleStudent(Student student);
}
