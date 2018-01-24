package service;

public interface UserService {

    public String Login(String email, String password);

    public String Register(String username, String email, String password);
}
