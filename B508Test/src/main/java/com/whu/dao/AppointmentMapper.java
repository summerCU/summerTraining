package com.whu.dao;

import com.whu.entity.book.Book;
import org.apache.ibatis.annotations.Param;

import com.whu.entity.book.Appointment;

import java.util.List;

public interface AppointmentMapper {

    /**
     * 插入预约图书记录
     *
     * @param bookId
     * @param studentId
     * @return 插入的行数
     */
    int insertAppointment(@Param("bookId") long bookId, @Param("studentId") long studentId);

    /**
     * 通过主键查询预约图书记录，并且携带图书实体
     *
     * @param bookId
     * @param studentId
     * @return
     */
    Appointment queryByKeyWithBook(@Param("bookId") long bookId, @Param("studentId") long studentId);

    List<Appointment> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int deleteByPrimaryKey(@Param("bookId") long bookId,  @Param("studentId") long studentId);

}
