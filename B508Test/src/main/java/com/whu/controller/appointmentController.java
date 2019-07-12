package com.whu.controller;
import com.alibaba.fastjson.JSONObject;
import com.whu.dao.UserMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.whu.dao.BookMapper;
import com.whu.entity.book.Book;
import com.whu.entity.book.BookExample;

import com.whu.entity.user.User;
import com.whu.entity.user.UserExample;

import com.whu.dao.AppointmentMapper;
import com.whu.entity.book.Appointment;
import com.whu.entity.book.AppointmentExample;
import com.whu.entity.book.AppointmentKey;
import com.whu.dto.*;
import com.whu.enums.*;
import com.whu.exception.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class appointmentController {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppointmentMapper AppointmentMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    private String list(Model model) {

        return "appointment/manage";// WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/getAppointmentList", method = RequestMethod.POST)
    @ResponseBody
    private JSONObject getList(Model model) {
        List<Appointment> list = AppointmentMapper.queryAll('0',10);
        List<User> users = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        JSONObject json= new JSONObject();
        for(int i=0;i<list.size();i++){
            int sid = new Long(list.get(i).getStudentId()).intValue();
            users.add(userMapper.selectByPrimaryKey(sid));
            books.add(bookMapper.selectByPrimaryKey(list.get(i).getBookId()));
        }

        json.put("list", list);
        json.put("users",users);
        json.put("books",books);
        json.put("info", "success");
        return json;// WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/insertAppoint",method = RequestMethod.POST)
    @ResponseBody
    private String insertAppoint(long bookId,long studentId){
        Appointment appointed = AppointmentMapper.queryByKeyWithBook(bookId,studentId);
        if(appointed == null){
            AppointmentMapper.insertAppointment(bookId,studentId);
            //预约成功之后，相应的书籍数量减一
//            Book appointbook = BookMapper.selectById(bookId);
//            appointbook.setNumber(appointbook.getNumber()-1);
//            BookMapper.updateByPrimaryKeySelective(appointbook);
            int a = bookMapper.reduceNumber(bookId);
            if(a>0)
                return "success";
            else
                return "error";
        }
        else {
            return "repeat_exception";
        }
    }

    @RequestMapping(value = "/deleteAppoint",method = RequestMethod.POST)
    @ResponseBody
    private String deleteAppoint(long bookId,long studentId){

        int delete = AppointmentMapper.deleteByPrimaryKey(bookId,studentId);
        if(delete >= 1) {
            int addbook = bookMapper.addNumber(bookId);
            return "success";
        }
        return  "error";
    }

/*
    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    @ResponseBody
    private String edit(Book book) {
        List<Book> list=BookMapper.selectByName(book.getName());

        if(list.size()>0)
        {
            Book newbook=new Book();
            newbook.setBookId((list.get(0)).getBookId());
            newbook.setName((list.get(0)).getName());
            newbook.setNumber((list.get(0)).getNumber());

            if(newbook.getBookId()==book.getBookId()) { //id相等，说明修改后的名字不存在重复
                BookMapper.updateByPrimaryKeySelective(book);
                return "success";
            }
            else
            {
                return "repeat";
            }
        }
        else
        {
            BookMapper.updateByPrimaryKeySelective(book);
            return "success";
        }
//        model.addAttribute("list", list);
        // list.jsp + model = ModelAndView
//        JSONObject json= new JSONObject();
//        json.put("list", list);
//        json.put("info", "success");
//        return "success";// WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/deleteBook",method = RequestMethod.POST)
    @ResponseBody
    private String deleteBook(Book book){

        int delete = BookMapper.deleteByPrimaryKey(book.getBookId());
        if(delete < 1)
            return  "error";
        return  "success";
    }


*/
}
