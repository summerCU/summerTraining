package com.whu.controller;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.whu.dto.*;
import com.whu.enums.*;
import com.whu.exception.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class bookController {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookMapper BookMapper;

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    private String list(Model model) {
//        List<Book> list = BookMapper.queryAll('0',10);
//        model.addAttribute("list", list);
        // list.jsp + model = ModelAndView
        return "book/manage";// WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/getBookList", method = RequestMethod.POST)
    @ResponseBody
    private JSONObject getList(Model model) {
        List<Book> list = BookMapper.queryAll('0',10);
//        model.addAttribute("list", list);
        // list.jsp + model = ModelAndView
        JSONObject json= new JSONObject();
        json.put("list", list);
        json.put("info", "success");
        return json;// WEB-INF/jsp/"list".jsp
    }

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

    @RequestMapping(value = "/insertBook",method = RequestMethod.POST)
    @ResponseBody
    private String insertBook(Book book){

        Book list_id=BookMapper.selectByPrimaryKey(book.getBookId());
        List<Book> list=BookMapper.selectByName(book.getName());
        if(list_id== null)
        {
            if(list.size()>0) {
                return "repeat_exception";
            }
            else {
                BookMapper.insert(book);
                return "success";
            }
        }



        return  "repeat_exception";
    }

}
