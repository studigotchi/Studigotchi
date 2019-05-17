package studigochi.test;

import studigochi.test.models.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "Main", value = "/main")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            final Connection connection = DBServletContextListener.getConnection();
            final PreparedStatement getOneUser = connection.prepareStatement("SELECT * from Users Where UserID=?");

            getOneUser.setInt(1, 2);
            getOneUser.execute();
            resp.getWriter().println(new UserModel(getOneUser.getResultSet()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}


