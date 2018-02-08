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

    @RequestMapping(value = "/insManagement",method = RequestMethod.GET)
    public String getInsManagement(ModelMap model){
        return "insManagement";
    }

    @RequestMapping(value = "/editPlan",method = RequestMethod.GET)
    public String getEditPlan(ModelMap model){
        return "editPlan";
    }

    @RequestMapping(value = "/book",method = RequestMethod.GET)
    public String getBook(ModelMap model){
        return "book";
    }

    @RequestMapping(value = "/myOrder",method = RequestMethod.GET)
    public String getMyOrder(ModelMap model){
        return "myOrder";
    }

    @RequestMapping(value = "/insOrder",method = RequestMethod.GET)
    public String getInsOrder(ModelMap model){
        return "insOrder";
    }

    @RequestMapping(value = "/myLesson",method = RequestMethod.GET)
    public String getMyLesson(ModelMap model){
        return "myLesson";
    }

    @RequestMapping(value = "/insLesson",method = RequestMethod.GET)
    public String getInsLesson(ModelMap model){
        return "insLesson";
    }
}
