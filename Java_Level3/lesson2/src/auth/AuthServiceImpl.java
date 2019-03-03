package auth;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для авторизации пользователей.
 * Создаем массив пользователей
 * Создаем метод авторизации пользователей, передаем в него юзернэйм и пароль,
 * возвращаем пароль
 *
 */

public class AuthServiceImpl implements AuthService {




  public Map<String, String> users = new HashMap<>();

    public AuthServiceImpl() {
        users.put("ivan", "123");
        users.put("petr", "345");
        users.put("julia", "789");
    }



    @Override
    public boolean authUser(String username, String password) {
        String pwd = users.get(username); //получаем из мапы пароль с заданным именем(ключом)
        return pwd != null && pwd.equals(password); //возвращаем пароль, если пользователь нашелся и если пароль равен введенному
    }
}
