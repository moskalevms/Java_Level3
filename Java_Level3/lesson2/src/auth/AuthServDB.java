package auth;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AuthServDB implements AuthService {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Connection connection;
    private final PreparedStatement authStatement;
    private final PreparedStatement changeLoginStatement;

    public AuthServDB(String databaseUrl) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl); //не находит драйвер
            authStatement = connection.prepareStatement("SELECT * FROM Users WHERE LOGIN = ? AND PASSWORD = ?");
            changeLoginStatement = connection.prepareStatement("UPDATE Users SET LOGIN = ? WHERE LOGIN = ?");
        } catch (SQLException e) {
            throw new AuthException("Failed to connect to database", e);
        }
    }



    public void close() throws Exception {
        connection.close();
    }



    @Override
    public boolean authUser(String username, String password) {
        if (username == null &&  password == null) {
            return false;
        }
        try {
            authStatement.setString(1, username);
            authStatement.setString(2, password);
            ResultSet resultSet = authStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new AuthException(String.format("Failed to retrieve users with login %s and password %s.", username, password), e);
        }
    }

    @Override
    public User getAuthUser(String username, String password) {
        if (username == null &&  password == null) {
            return null;
        }

        try {
            authStatement.setString(1, username);
            authStatement.setString(2, password);
            ResultSet resultSet = authStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String pass = resultSet.getString(3);
                return new User(id, login, pass);
            }
        } catch (SQLException e) {
            throw new AuthException(String.format("Failed to retrieve users with login %s and password %s.", username, password), e);
        }
        return null;
    }

    @Override
    public List<String> getAllUsers() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT LOGIN FROM Users");
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeLogin(String oldLogin, String newLogin) {
        try {
            changeLoginStatement.setString(1, newLogin);
            changeLoginStatement.setString(2, oldLogin);
            changeLoginStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
