package studigochi.test.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import studigochi.test.DBServletContextListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    @NotNull
    private String userName;
    @NotNull
    private String PW_Hash;
    private int userId;
    private int semester;
    private double health;
    private double success;

    @Contract(pure = true)
    public UserModel(ResultSet queryResult) throws SQLException {
        userName = queryResult.getString("UserName");
        PW_Hash = queryResult.getString("PW_Hash");
        userId = queryResult.getInt("UserID");
        semester = queryResult.getInt("Semester");
        health = queryResult.getDouble("Health");
        success = queryResult.getDouble("Success");
    }

    @Contract(pure = true)
    public UserModel(@NotNull String userName, @NotNull String PW_Hash, int userId, int semester, double health, double success) {
        this.userName = userName;
        this.PW_Hash = PW_Hash;
        this.userId = userId;
        this.semester = semester;
        this.health = health;
        this.success = success;
    }

    @NotNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    @NotNull
    public String getPW_Hash() {
        return PW_Hash;
    }

    public void setPW_Hash(@NotNull String PW_Hash) {
        this.PW_Hash = PW_Hash;
    }

    public int getUserId() {
        return userId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getSuccess() {
        return success;
    }

    public void setSuccess(double success) {
        this.success = success;
    }

    public void save() {
        final Connection connection = DBServletContextListener.getConnection();
        final PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("UPDATE Users SET UserName=?, PW_Hash=?, Health=?, Success=?, Semester=? WHERE UserID LIKE ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, PW_Hash);
            preparedStatement.setDouble(3, health);
            preparedStatement.setDouble(4, success);
            preparedStatement.setInt(5, semester);
            preparedStatement.setInt(6, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (userId != userModel.userId) return false;
        if (!userName.equals(userModel.userName)) return false;
        return PW_Hash.equals(userModel.PW_Hash);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + PW_Hash.hashCode();
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return String.format("User %s with ID %d and Health %f", userName, userId, health);
    }
}
