<%--
  Created by IntelliJ IDEA.
  User: Summer_Cu
  Date: 2019/7/8
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common/globel.jsp"%>
<%@include file="/views/index.jsp"%>
<html>

<meta name="referrer" content="no-referrer">
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <button id="querybook" class="appoint btn btn-info"  onclick="getAppointmentList()">查看预约列表</button>
        <table border="1" align="center" class="table table-striped table-bordered  table-hover">
            <thead>
            <tr>
                <th align="center">book_id</th>
                <th align="center">书名</th>
                <th align="center">user_id</th>
                <th align="center">用户名</th>
                <th align="center">time</th>
                <th align="center" >删除</th>

                <%--<th>删除该图书</th>--%>
            </tr>
            </thead>
            <tbody id = "appointmentTable">
            <%--ajax异步方式获取列表内容--%>
            </tbody>
        </table>
    </div>
</div>






<%--js函数--%>
<script type="text/javascript">
    // 查询获得所有预约信息
    function getAppointmentList(){
        $.ajax({
            url: '<%=request.getContextPath()%>/appointment/getAppointmentList',
            type: 'post',
            success:function (data) {
                <%--alert(${user.userid});--%>
                if(data.info=="success") {
                    $("#appointmentTable").empty();
                    if(${user.username=='admin'}) {

                        for(var i = 0;i<data.list.length;i++){
                            str1 = "<tr>" +
                                "<td align='center'  class='text-nowrap bookid'>"+data.list[i].bookId+ "</td>" +
                                "<td align='center'  class='text-nowrap bookname'>"+data.books[i].name+ "</td>" +
                                "<td align='center'  class='text-nowrap studentid'>"+data.list[i].studentId + "</td>" +
                                "<td align='center'  class='text-nowrap username'>"+data.users[i].username+ "</td>" +
                                "<td align='center'  class='text-nowrap time' >"+data.list[i].appointTime + "</td>" +
                                "<td align='center' >"+"<a class='appoint btn btn-danger' onclick='deleteAppintment(this)'>"+ "删除"+"</a>"+"</td>"+
                                "</tr>";
                            $("#appointmentTable").append(str1);
                        }
                    }
                    else{   //非管理员用户，只显示自己的预约记录
                      //  alert("chenggong");
                        for(var i = 0;i<data.list.length;i++){
                            var id =  parseInt(data.list[i].studentId);
                            var uid = ${user.userid};
                            if(uid == id) {
                                str1 = "<tr>" +
                                    "<td align='center'  class='text-nowrap bookid'>" + data.list[i].bookId + "</td>" +
                                    "<td align='center'  class='text-nowrap username'>"+data.books[i].name+ "</td>" +
                                    "<td align='center'  class='text-nowrap studentid'>" + data.list[i].studentId + "</td>" +
                                    "<td align='center'  class='text-nowrap bookname'>"+data.users[i].username+ "</td>" +
                                    "<td align='center'  class='text-nowrap time' >" + data.list[i].appointTime + "</td>" +
                                    "<td align='center' >" + "<a class='appoint btn btn-danger' onclick='deleteAppintment(this)'>" + "删除" + "</a>" + "</td>" +
                                    "</tr>";
                                $("#appointmentTable").append(str1);
                            }//if判断用户id
                        }
                    }
                }

                else {    //ajax请求失败
                    alert("获取失败")
                }

            }
        })

    }

    function deleteAppintment(obj)
    {
        var bookid = $(obj).parents('td').parents('tr').find('.bookid').text();
        var studentid = $(obj).parents('td').parents('tr').find('.studentid').text();
        $.confirm({
            type: 'grey',
            title: false,
            content: '确定预约这条预约吗？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: '<%=request.getContextPath()%>/appointment/deleteAppoint',
                            type: 'post',
                            data: {bookId: bookid,studentId: studentid},
                            datatype: 'text',
                            success: function (data) {
                                if (data == "success") {
                                    alert("删除成功！");
                                    window.location.reload();
                                }
                                else {
                                    alert("删除失败！");
                                }
                            }
                        })//ajax
                    }//comfirm
                },
                cancel: {
                    text: '取消',
                }
            }
        });

    }//addAppointment function


</script>


</body>
</html>
