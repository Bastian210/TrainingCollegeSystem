package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(ModelMap model){
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(ModelMap model){
        return "register";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        return "index";
    }

    @RequestMapping(value = "/institution", method = RequestMethod.GET)
    public String getInstitution(ModelMap model){
        return "institution";
    }

    @RequestMapping(value = "/manager",method = RequestMethod.GET)
    public String getManager(ModelMap model) {
        return "manager";
    }

    @RequestMapping(value = "/accountManagement",method = RequestMethod.GET)
    public String getAccountManagement(ModelMap model){
        return "accountManagement";
    }
}
