package studigochi.test.servlets;

import studigochi.test.student.Status;
import studigochi.test.student.Student;

import javax.servlet.annotation.WebServlet;

/**
 * {@inheritDoc}
 *     Called by the client after the "eat" button has been pressed<br/>
 *     This sets the student's status to either {@link Status#SLEEP} or {@link Status#JUST_BE} depending on the current status.<br/>
 *     Also calls the respective functions on {@link Student}, respective {@link Student#sleep()} or {@link Student#just_be()}
 */
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
