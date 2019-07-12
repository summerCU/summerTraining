<%--
  Created by IntelliJ IDEA.
  User: My
  Date: 2018/9/18
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/globel.jsp"%>
<%--<%@include file="../narbar.jsp"%>--%>
<link href="<%=request.getContextPath()%>/resources/css/register.css" rel="stylesheet">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册</title>
</head>
<body>
<br>
<br>
<br>
<div class="container" style="position: absolute;left: 120px;">
    <form class="form-register" id="registerForm">
        <h2 class="form-register-heading">账户注册</h2>
        <div class="form-group">
            <input type="text" class="form-control" name="userName" id="userName" placeholder="用户名"/>
        </div>
        <div class="form-group">
            <input type="password" class="form-control" id="password" placeholder="密码"/>
        </div>
        <div class="form-group">
            <button class="btn  btn-primary btn-block"   id="submit" type="button">立即注册</button>
        </div>

        <!--
        <a href="<%=request.getContextPath()%>/user/login" class="btn btn-link">返回登陆</a>
        -->
    </form>    
</div>
<script type="text/javascript">
    $("#submit").click(function () {
        var  userName = $("#userName").val();
        var  password = $("#password").val();
        if (userName == "")
            alert("用户名不能为空！");
        else if (password == "")
            alert("密码不能为空")
        else {
            $.ajax({
                url: "<%=request.getContextPath()%>/user/registers",
                type: "post",
                data: {"userName": userName, "password": password},
                dataType: "text",
                success: success_function,
                error: error_function
            });
        }
        function success_function(data){

            if(data=="success"){
                location.href = '${pageContext.request.contextPath}'+"/";
            }
            else if(data=="error"){
                alert("注册用户已存在");
            }
            else{
                location.href = '${pageContext.request.contextPath}'+"/user/register";
            }
        }
        function  error_function(){
            alert("something wrong!")
        }
    })
</script>
</body>
</html>