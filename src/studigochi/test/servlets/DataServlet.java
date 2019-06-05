package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Data Servlet", value = "/user/data")
public final class DataServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
    }
}
