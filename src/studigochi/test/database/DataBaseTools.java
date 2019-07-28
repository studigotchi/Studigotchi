package studigochi.test.database;

import studigochi.test.listeners.DBServletContextListener;
import studigochi.test.student.Status;
import studigochi.test.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of static methods to handle saving and loading users from the DataBase.
 */
public class DataBaseTools {

    /**
     * Writes the given student to the database
     * @param student The student to write to the DB
     */
    public static void saveStudent(Student student) {
        if (student == null)
            return;

        final Connection connection = DBServletContextListener.getConnection();
        if (connection == null)
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Attemtps to read the user from the database
     * @param userName The user name to be used (see {@link Student#getUserName()}
     * @param password The user's password (only used in connection to the Database, it's not stored in {@link Student}!)
     * @return The {@link Student} that matches the given credentials, or {@code null}
     */
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

            final Student student = new Student(userName, resultSet.getDouble("health"), resultSet.getDouble("success"), resultSet.getInt("semester"), resultSet.getInt("userID"), resultSet.getLong("semesterTimer"), resultSet.getInt("totalSemesters"));
            student.setStatus(Status.values()[resultSet.getInt("Status")]);
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Attempts to register a new {@link Student} to the database.<br/>
     *     After registering it will also attempt to return the created user.
     * @param userName The username to be created (see {@link Student#getUserName()}
     * @param password The password for the new user
     * @return The newly created user's login result (see {@link #login}) or {@code null} if any error occured or a user with such username already exists
     */
    public static Student register(String userName, String password) {
        try {
            final Connection connection = DBServletContextListener.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT() FROM Users WHERE UserName IS ?");
            preparedStatement.setString(1, userName);

            preparedStatement.execute();
            final ResultSet resultSet = preparedStatement.getResultSet();
            if (!resultSet.next() || resultSet.getInt(1) != 0)
                return null;

            final Student student = new Student(userName, 5.0D, 0.0D, 1, -1, Student.TIME_PER_SEMESTER, 1);

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
