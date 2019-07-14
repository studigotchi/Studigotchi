package studigochi.test.listeners;

import studigochi.test.database.DataBaseTools;
import studigochi.test.student.Student;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("Save user on session destroyed")
public class StudentSessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        final Student student = (Student) se.getSession().getAttribute("student");
        if (student != null)
            student.stopTimer();
        DataBaseTools.saveStudent(student);
    }
}
