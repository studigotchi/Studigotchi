package studigochi.test.listeners;

import studigochi.test.database.DataBaseTools;
import studigochi.test.student.Student;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Used to save the user's {@link Student} object when the session is destroyed.
 */
@WebListener("Save user on session destroyed")
public class StudentSessionListener implements HttpSessionListener {

    /**
     * Sets the session timeout to 100s <br/>
     *     Usually an active session sends an update each second so this should only free up inactive sessions
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //Max 100s inactive before removing the session
        se.getSession().setMaxInactiveInterval(100);
    }

    /**
     * Save the student on session destroyed and stops its timer.
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        final Student student = (Student) se.getSession().getAttribute("student");
        if (student != null)
            student.stopTimer();
        DataBaseTools.saveStudent(student);
    }
}
