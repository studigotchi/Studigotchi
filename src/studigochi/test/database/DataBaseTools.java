package studigochi.test.database;

import studigochi.test.listeners.DBServletContextListener;
import studigochi.test.student.Status;
import studigochi.test.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseTools {

    public static void saveStudent(Student student) {
        if(student == null)
            return;

        final Connection connection = DBServletContextListener.getConnection();
        if(connection == null)
            //TODO log?
            return;

        try {
            final PreparedStatement updateStatement = connection.prepareStatement("UPDATE Users SET (Health, Semester, Success, SemesterTimer, TotalSemesters, Status) = (?, ?, ?, ?, ?, ?) WHERE UserID IS ?");
            updateStatement.setDouble(1, student.getHealth());
            updateStatement.setInt(2, student.getSemester());
            updateStatement.setDouble(3, student.getSuccess());
            updateStatement.setLong(4, student.getRemainingSemesterTime());
            updateStatement.setInt(5, student.getTotalSemesters());
            updateStatement.setInt(6, student.getStatus().ordinal());
            updateStatement.setInt(7, student.getUserId());


            updateStatement.execute();

            System.out.println("Something");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static Student login(String userName, String password) {
        try {
            final Connection connection = DBServletContextListener.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE UserName IS ? AND PW_Hash IS ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);


            preparedStatement.execute();
            final ResultSet resultSet = preparedStatement.getResultSet();
            if (!resultSet.next()) {
                return null;
            }

            final Student student = new Student(userName, resultSet.getDouble("health"), resultSet.getDouble("success"), resultSet.getInt("semester"), resultSet.getInt("userID"), resultSet.getLong("semesterTimer"));
            student.setStatus(Status.values()[resultSet.getInt("Status")]);
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Student register(String userName, String password) {
        try {
            final Connection connection = DBServletContextListener.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT() FROM Users WHERE UserName IS ?");
            preparedStatement.setString(1, userName);

            preparedStatement.execute();
            final ResultSet resultSet = preparedStatement.getResultSet();
            if(!resultSet.next() || resultSet.getInt(1) != 0)
                return null;

            final Student student = new Student(userName, 5.0D, 0.0D, 1, -1, Student.TIME_PER_SEMESTER);

            final PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Users (UserName, PW_Hash, Health, Success, Semester) VALUES (?,?,?,?,?)");
            createStatement.setString(1, userName);
            createStatement.setString(2, password);
            createStatement.setDouble(3, student.getHealth());
            createStatement.setDouble(4, student.getSuccess());
            createStatement.setInt(5, student.getSemester());

            createStatement.execute();
            return login(userName, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
