package controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String doLogin(){
        JSONObject json = new JSONObject();
        json.put("result","success");
        String result = json.toString();
        return result;
    }
}
