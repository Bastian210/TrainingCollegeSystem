package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.Param;

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
        if(Param.getUserid()==null){
            return "login";
        }
        return "index";
    }

    @RequestMapping(value = "/institution", method = RequestMethod.GET)
    public String getInstitution(ModelMap model){
        if(Param.getInstitutionid()==null){
            return "login";
        }
        return "institution";
    }

    @RequestMapping(value = "/amaldar",method = RequestMethod.GET)
    public String getAmaldar(ModelMap model) {
        return "amaldar";
    }

    @RequestMapping(value = "/accountManagement",method = RequestMethod.GET)
    public String getAccountManagement(ModelMap model){
        if(Param.getUserid()==null){
            return "login";
        }
        return "accountManagement";
    }

    @RequestMapping(value = "/insManagement",method = RequestMethod.GET)
    public String getInsManagement(ModelMap model){
        if(Param.getInstitutionid()==null){
            return "login";
        }
        return "insManagement";
    }

    @RequestMapping(value = "/editPlan",method = RequestMethod.GET)
    public String getEditPlan(ModelMap model){
        if(Param.getInstitutionid()==null){
            return "login";
        }
        return "editPlan";
    }

    @RequestMapping(value = "/book",method = RequestMethod.GET)
    public String getBook(ModelMap model){
        if(Param.getUserid()==null){
            return "login";
        }
        return "book";
    }

    @RequestMapping(value = "/myOrder",method = RequestMethod.GET)
    public String getMyOrder(ModelMap model){
        if(Param.getUserid()==null){
            return "login";
        }
        return "myOrder";
    }

    @RequestMapping(value = "/insOrder",method = RequestMethod.GET)
    public String getInsOrder(ModelMap model){
        if(Param.getInstitutionid()==null){
            return "login";
        }
        return "insOrder";
    }

    @RequestMapping(value = "/myLesson",method = RequestMethod.GET)
    public String getMyLesson(ModelMap model){
        if(Param.getUserid()==null){
            return "login";
        }
        return "myLesson";
    }

    @RequestMapping(value = "/insLesson",method = RequestMethod.GET)
    public String getInsLesson(ModelMap model){
        if(Param.getInstitutionid()==null){
            return "login";
        }
        return "insLesson";
    }

    @RequestMapping(value = "/statistics",method = RequestMethod.GET)
    public String getStatistics(ModelMap model){
        return "statistics";
    }
}
