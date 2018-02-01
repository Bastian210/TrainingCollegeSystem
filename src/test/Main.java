package test;

import service.UserSerciveImpl;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserSerciveImpl();
        service.SendCode("2518740156@qq.com");
    }
}
