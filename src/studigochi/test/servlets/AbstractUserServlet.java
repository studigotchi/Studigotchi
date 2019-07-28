package studigochi.test.servlets;

import studigochi.test.student.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * An abstract class for every Servlet that serves as endpoint for user interaction.<br/>
 * Provides a method to handle the action, and will serialize a JSON response with the updated student stats.
 *
 *
 */
abstract class AbstractUserServlet extends HttpServlet {

    /**
     * Calls {@link #handleStudent} and then fill the response with the serialized Student.<br/>
     *     See {@link Student#toJSONString()}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter writer = resp.getWriter();

        final Student student = (Student) req.getSession().getAttribute("student");
        this.handleStudent(student);
        writer.print(student.toJSONString());
    }

    /**
     * The servlet's main action.<br/>
     *     Is permitted to modify the student object's stats.
     * @param student The student of the request's active session.
     *                It is assumed to never equal {@code null}
     */
    abstract void handleStudent(Student student);
}
