<%--
  Created by IntelliJ IDEA.
  User: 57266
  Date: 2019/6/28
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common/globel.jsp"%>
<%@include file="/views/index.jsp"%>
<html lang="zh-cn">
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="table-responsive">
    <table border="1" align="center" class="table table-striped table-bordered  table-hover">
        <thead>
        <tr>
            <th align="center">user_id</th>
            <th align="center">用户名</th>
            <th align="center">密码</th>
            <th align="center" >编辑</th>
<c:choose>
    <c:when test="${user.username=='admin'}">
        <th align="center" >删除</th>
    </c:when>
</c:choose>
            <%--<th>删除该图书</th>--%>
        </tr>
        </thead>
        <c:forEach items="${userlist}" var="i">
            <tbody>
            <tr >
                <td  class="userid" align="center">${i.userid}</td>
                <td align="center" class="username">${i.username}</td>
                <td class="password" align="center" >${i.password}</td>
                <td align="center" class="app"><button class="appoint btn btn-info" onclick="openModel(this)">编辑</button></td>
                <c:choose>
                    <c:when test="${user.username=='admin'}">
                <td align="center" ><a class="appoint btn btn-danger" onclick="deleteUser(this)"> 删除</a></td>
                </c:when>
                </c:choose>
            </tr>
            </tbody>
        </c:forEach>
</div>
    <div class="modal fade  myModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改用户信息
                    </h4>
                </div>
                <div id="user" class="user modal-body form-group">
                    <h5 class="form-signin-heading">用户id：</h5>
                    <input class="form-control userid"  readonly/>
                    <h5 class="form-signin-heading">用户名：</h5>
                    <input class="form-control name"  readonly/>
                    <h5 class="form-signin-heading ">密码：</h5>
                    <input class="form-control psd"/>
                </div>
                <div class="modal-footer">
                    <a class="waves-effect btn btn-danger btn-sm"
                       data-dismiss="modal"><i class="zmdi zmdi-delete"></i> 关闭</a>
                    <a class="waves-effect btn btn-info btn-sm" id="confirmdirbutton"
                       href="javascript:confirmdir(this);"><i class="zmdi zmdi-edit"></i>
                        确认</a>
                    <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭--%>
                    <%--</button>--%>
                    <%--<button type="button" class=" btn btn-primary" onclick="editUser()">--%>
                    <%--更改--%>
                    <%--</button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
    <script type="text/javascript">
         function openModel (obj) {
             $('.myModel').modal('show');
             var userid = $(obj).parents('td').parents('tr').find('.userid').text();
             var username = $(obj).parents('td').parents('tr').find('.username').text();
             var password = $(obj).parents('td').parents('tr').find('.password').text();
             // 获取每一行的数据到模态框中
             $('.userid').val(userid);
             $('.name').val(username);
             $('.psd').val(password);
         }
         function deleteUser(obj) {
             var userid= $(obj).parents('td').parents('tr').find('.userid').text();
             $.confirm({
                 type: 'grey',
                 title: false,
                 content: '确定删除该用户么？',
                 buttons: {
                     confirm: {
                         text: '确认',
                         action: function () {
                             $.post(
                                 '${pageContext.request.contextPath}/user/deleteUser',
                                 {userid:userid},
                                 function (data) {
                                     if(data==="success")
                                         window.location.reload();
                                 }
                             )
                         }
                     },
                     cancel: {
                         text: '取消',
                     }
                 }
             });
         }
    function confirmdir() {
        var userid =  $('#user').find('.userid').val();;
        var username = $('#user').find('.name').val();
        var password = $('#user').find('.psd').val();
        $.ajax({
            url: '<%=request.getContextPath()%>/user/editUser',
            type: 'post',
            data:{userid:userid,username:username,password:password},
            datatype:'text',
            success:function (data) {
                if(data==="success") {
                    $('.myModel').modal('hide');
                    window.location.reload();
                    //$(obj).parent('div').parent('div').children('div').find('.name').text(username);
                    //$(obj).parent('td').parent('tr').find('.password').text(password);
                }
            }
        })
    }
</script>
</body>
</html>
