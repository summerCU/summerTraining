<%--
  Created by IntelliJ IDEA.
  User: My
  Date: 2018/9/18
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/globel.jsp"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>main page</title>
</head>
<body>
<div class="container">
    <div>
        <span class="glyphicon glyphicon-user">${user.username},欢迎你</span>
    </div>
    <div>
        <a class="manageUser" href="<%=request.getContextPath()%>/user/manage?username=${user.username}">用户管理</a>
        &nbsp&nbsp&nbsp&nbsp
        <a class="manageBook" href="<%=request.getContextPath()%>/book/manage?username=${user.username}">图书管理</a>
        &nbsp&nbsp&nbsp&nbsp
        <a class="manageAppointment" href="<%=request.getContextPath()%>/appointment/manage?username=${user.username}">预约管理</a>
        &nbsp&nbsp&nbsp&nbsp
        <a style=" cursor:pointer;" class="logout" onclick="return logout()"><span class="glyphicon glyphicon-log-out"></span>注销</a>

    </div>


</div>
<script type="text/javascript">
    function logout() {
        $.confirm({
            type: 'grey',
            animationSpeed: 300,
            title: false,
            content: '您确认要退出登陆吗？',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        location.href = "<%=request.getContextPath()%>/user/signout";
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    }
</script>
</body>
</html>
