package com.whu.controller;

import com.whu.dao.UserMapper;
import com.whu.entity.user.User;
import com.whu.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller用于标注控制层组件
@Controller
//@RequestMapping("/xxx")  xxx  就是用户请求的xxx ，即接收用户请求地址的注解
@RequestMapping("/user")
public class userController {
    //Autowired 作用：将Spring容器中的bean注入到属性
    @Autowired
    private   UserMapper userMapper;
//@Autowired
//private userService userservice;
    @RequestMapping(value = "/register")
    private String  register(){
        return "user/register";
    }

    @RequestMapping(value = "/manage")
    private String  manage(String username,Model model){
        List<User> userList = null;
        if(!username.equals("")) {
            if (username.equals("admin"))
                userList = userMapper.selectAll("");
            else
                userList = userMapper.selectByUserName(username);
        }
        model.addAttribute("userlist",userList);
        return "user/manage";
    }
@RequestMapping(value = "editUser",method = RequestMethod.POST)
//@ResponseBody 加上注解，可以返回一个json格式的字符串返回给前台，不加这个注解将会被视图解析器解析成跳转路径
@ResponseBody
private String editUser(User user){
    int update=userMapper.updateByPrimaryKeySelective(user);
    if(update<1)
        return "error";
    return "success";
}
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    private String login(String userName,String password,HttpSession session ) throws NullPointerException{
        User user = userMapper.login(userName,password);
        session.setAttribute("user",user);
        if(user!=null)
            return "success";
        return "error";
    }
    @RequestMapping(value = "/registers",method = RequestMethod.POST)
    @ResponseBody
    private String registers(String userName,String password, HttpSession session){
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        //在注册之前判断用户是否已存在，先进行一次查询
        List<User> selectUser = userMapper.selectByUserName(userName);

        if(selectUser.size() != 0)
            return "error";

        int insert=userMapper.insert(user);
        if(insert>0) {
            session.setAttribute("user",user);
            return "success";
        }
        return "error";
    }
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    private String deleteUser(int userid){
        int delete = userMapper.deleteByPrimaryKey(userid);
        if(delete < 1)
            return  "error";
        return  "success";
    }

    @RequestMapping(value ="/signout")
    public String singout(HttpSession session){
        session.removeAttribute("user");
        return "login";
    }
}
