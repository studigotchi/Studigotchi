package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Eat Servlet", value = "/user/eat")
public final class EatServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        student.eat();
    }
}
