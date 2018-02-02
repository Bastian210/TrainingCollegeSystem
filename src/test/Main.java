package test;

import service.InstitutionService;
import service.InstitutionServiceImpl;
import service.UserSerciveImpl;
import service.UserService;

public class Main {
    public static void main(String[] args) {
//        UserService service = new UserSerciveImpl();
//        service.SendCode("2518740156@qq.com");
        InstitutionService service = new InstitutionServiceImpl();
        service.Register("机构A","XX省","13270099850","1234");
    }
}
