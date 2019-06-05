package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Learn Servlet", value = "/user/learn")
public final class LearnServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        student.learn();
    }
}
