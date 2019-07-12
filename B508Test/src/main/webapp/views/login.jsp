<%--
  Created by IntelliJ IDEA.
  User: Summer_Cu
  Date: 2019/7/6
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/globel.jsp"%>
<%--<%@include file="../narbar.jsp"%>--%>
<link href="<%=request.getContextPath()%>/resources/css/login.css" rel="stylesheet">

<script src="<%=request.getContextPath()%>/resources/js/slide/jquery.js"></script>
<%--<script src="<%=request.getContextPath()%>/resources/plugins/jquery-3.2.1.min.js"></script>--%>
<script src="<%=request.getContextPath()%>/resources/js/slide/slide.js"></script>
<meta name="referrer" content="no-referrer">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/slide.css">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>图书管理系统</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<%--轮播图引入图片--%>
<div>
    <div id="show" rel="autoPlay">
        <div class="img">
          <span>
              <img src="<%=request.getContextPath()%>/resources/images/1.jpg" />
              <img src="<%=request.getContextPath()%>/resources/images/2.jpg" />
              <img src="<%=request.getContextPath()%>/resources/images/3.jpg" />
              <img src="<%=request.getContextPath()%>/resources/images/4.jpg" />
              <img src="<%=request.getContextPath()%>/resources/images/5.jpg" />
          </span>
            <div class="masks mk1"></div>
            <div class="masks mk2"></div>
        </div>
    </div>
</div>

<div id="particles-js">
    <%--action="<%=request.getContextPath()%>/user/login" method="post"--%>
    <div class="container" style="position: absolute; left: 120px;">
        <form class="form-signin" id="loginForm">
            <h2 class="form-signin-heading">登录</h2>
            <div class="form-group">
                <input class="form-control" type="text" id ="userName" name="userName" placeholder="用户名"/>
            </div>
            <%--<h2 class="form-signin-heading">密码：</h2>--%>
            <div class="form-group">
                <input class="form-control" type="password" id ="passWord" name ="passWord" placeholder="密码"><br>
            </div>
            <div class="checkbox">
                <%--<label>--%>
                    <%--<input type="checkbox"  id="rememberMe" name="rememberMe" >记住我--%>
                <%--</label>--%>
                <%--<label>--%>
                    <%--<a href="<%=request.getContextPath()%>/user/forget">忘记密码</a>--%>
                <%--</label>--%>
                <button type = "button" id="submit" class="btn btn-primary btn-block">login</button><br>
            </div>
            <a href="<%=request.getContextPath()%>/user/register" class="btn btn-link">点击注册</a>
        </form>
    </div>
</div>



<script src="<%=request.getContextPath()%>/resources/js/login.js"></script>
<script type="text/javascript">
    $("#submit").click(function () {
        var  userName = $("#userName").val();
        var  password = $("#passWord").val();
        // var  rememberMe=$('#rememberMe').val()
        if (userName == "")
            alert("用户名不能为空！");
        else if (password == "")
            alert("密码不能为空")
        else {
            $.ajax({
                url: "<%=request.getContextPath()%>/user/login",
                type:"post",
                data:{"userName":userName,"password":password},
                dataType:"text",
                success : function(data) {
                    console.log(data);
                    // data = JSON.parse(data);
                    // console.log(data.status);
                    if(data=="success" ){
                        // <%--if(userEmail="admin@qq.com")--%>
                        location.href='${pageContext.request.contextPath}'+"/";
                        // <%--else--%>
                        //     <%--location.href = '${pageContext.request.contextPath}'+"/book/list?username="+username;--%>
                    }
                    else
                        alert("用户不存在或者密码错误！");
                    // sweetAlert(data.msg);
                },
                error: function(data) {
                    alert(JSON.stringify(data));
                }
            });
        }
    })
</script>

<%--<!-- 背景JS -->--%>
<%--<script src="<%=request.getContextPath()%>/resources/js/background/particles.js"></script>--%>
<%--<script src="<%=request.getContextPath()%>/resources/js/background/app.js"></script>--%>
</body>
</html>