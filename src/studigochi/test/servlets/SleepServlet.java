package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Sleep Servlet", value = "/user/sleep")
public final class SleepServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        student.sleep();
    }
}
