package auth;

public class Users implements AuthService{
    private int id;
    private String username;
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean authUser(String username, String password) {
        String pwd = users.get(username); //получаем из мапы пароль с заданным именем(ключом)
        return pwd != null && pwd.equals(password); //возвращаем пароль, если пользователь нашелся и если пароль равен введенному
    }
}
