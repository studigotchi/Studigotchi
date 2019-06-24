package studigochi.test.servlets;

import studigochi.test.student.Status;
import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Sleep Servlet", value = "/user/sleep")
public final class SleepServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        if (student.getStatus() == Status.SLEEP)
            student.just_be();
        else
            student.sleep();
    }
}
