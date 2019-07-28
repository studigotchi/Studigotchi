package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

/**
 * {@inheritDoc}
 *     Called by the client to only update the shown data<br/>
 *     Not associated with any special action.
 */
@WebServlet(name = "Data Servlet", value = "/user/data")
public final class DataServlet extends AbstractUserServlet {
    @Override
    void handleStudent(Student student) {
    }
}
