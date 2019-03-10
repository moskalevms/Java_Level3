package auth;

import java.sql.SQLException;
import java.util.List;


public interface AuthService {

    boolean authUser(String username, String password);

    User getAuthUser(String username, String password);

    List<String> getAllUsers();

    void changeLogin(String oldLogin, String newLogin);


    void close() throws Exception;
}
