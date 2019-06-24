package studigochi.test.servlets;

import studigochi.test.student.Status;
import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Learn Servlet", value = "/user/learn")
public final class LearnServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        if (student.getStatus() == Status.LEARN)
            student.just_be();
        else
            student.learn();
    }
}
