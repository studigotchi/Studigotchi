package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

/**
 * {@inheritDoc}
 *     Called by the client after the "eat" button has been pressed<br/>
 *     This calls {@link Student#eat()}
 */
@WebServlet(name = "Eat Servlet", value = "/user/eat")
public final class EatServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
        student.eat();
    }
}
