<%--
  Created by IntelliJ IDEA.
  User: Summer_Cu
  Date: 2019/7/6
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common/globel.jsp"%>
<%@include file="/views/index.jsp"%>
<html>

<script src="<%=request.getContextPath()%>/resources/js/book/bookManage.js"></script>
<meta name="referrer" content="no-referrer">
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <button id="querybook" class="appoint btn btn-info"  onclick="getBookList()">查看所有图书</button>
        <c:choose>
            <c:when test="${user.username=='admin'}">
                <button id="butto2" class="appoint btn btn-info"  onclick="addBook()">增加图书</button>
            </c:when>
        </c:choose>
        <table border="1" align="center" class="table table-striped table-bordered  table-hover">
            <thead>
            <tr>
                <th align="center">book_id</th>
                <th align="center">书名</th>
                <th align="center">数量</th>

                <c:choose>
                    <c:when test="${user.username=='admin'}">
                        <th align="center" >编辑</th>
                        <th align="center" >删除</th>
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${user.username!='admin'}">
                        <th align="center" >预约功能</th>
                    </c:when>
                </c:choose>
                <%--<th>删除该图书</th>--%>
            </tr>
            </thead>
            <tbody id = "bookTable">
                <%--ajax异步方式获取列表内容--%>
            </tbody>
        </table>
    </div>


<%--    修改图书信息，包括修改id 书名、容量功能--%>
    <div class="modal fade  updateBookModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改图书信息
                    </h4>
                </div>
                <div id="bookinfo" class="user modal-body form-group">
                    <h5 class="form-signin-heading">图书id：</h5>
                    <input class="form-control bookID"  readonly/>
                    <h5 class="form-signin-heading">图书名：</h5>
                    <input class="form-control bookName"  />
                    <h5 class="form-signin-heading ">图书数量：</h5>
                    <input class="form-control bookNum"/>
                </div>
                <div class="modal-footer">
                    <a class="waves-effect btn btn-danger btn-sm"
                       data-dismiss="modal"><i class="zmdi zmdi-delete"></i> 关闭</a>
                    <a class="waves-effect btn btn-info btn-sm" id="confirmdirbutton"
                       href="javascript:updateBook(this);"><i class="zmdi zmdi-edit"></i>
                        确认</a>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <%--    增加图书--%>
    <div class="modal fade  addBookModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">
                        新增图书
                    </h4>
                </div>
                <div id="bookinfoAdd" class="user modal-body form-group">
                    <h5 class="form-signin-heading">图书id：</h5>
                    <input class="form-control addBookID"  />
                    <h5 class="form-signin-heading">图书名：</h5>
                    <input class="form-control addBookName"  />
                    <h5 class="form-signin-heading ">图书数量：</h5>
                    <input class="form-control addBookNum"/>
                </div>
                <div class="modal-footer">
                    <a class="waves-effect btn btn-danger btn-sm"
                       data-dismiss="modal"><i class="zmdi zmdi-delete"></i> 关闭</a>
                    <a class="waves-effect btn btn-info btn-sm" id="confirmdirbutton2"
                       href="javascript:confirmAddBook(this);"><i class="zmdi zmdi-edit"></i>
                        确认</a>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>






<%--js函数--%>
<script type="text/javascript">
    // 新增图书编辑表格
    function addBook() {
        $('.addBookModel').modal('show');
    }

    // 更新图书信息编辑表格
    function openModel (obj) {
        $('.updateBookModel').modal('show');
        var bookid = $(obj).parents('td').parents('tr').find('.bookid').text();
        var bookname = $(obj).parents('td').parents('tr').find('.bookname').text();
        var number = $(obj).parents('td').parents('tr').find('.number').text();
        // 获取每一行的数据到模态框中
        $('.bookID').val(bookid);
        $('.bookName').val(bookname);
        $('.number').val(number);
    }

    // 查询获得所有图书
    function getBookList(){
        //window.location.reload();
        $.ajax({
            url: '<%=request.getContextPath()%>/book/getBookList',
            type: 'post',
            success:function (data) {
                if(data.info=="success") {
                    $("#bookTable").empty();
                    if(${user.username!='admin'}) {
                        //alert("admin");
                        for(var i = 0;i<data.list.length;i++){
                            str1 = "<tr>" +
                                "<td align='center'  class='text-nowrap bookid'>"+data.list[i].bookId+ "</td>" +
                                "<td align='center'  class='text-nowrap bookname'>"+data.list[i].name + "</td>" +
                                "<td align='center'  class='text-nowrap number' >"+data.list[i].number + "</td>" +
                                "<td align='center' >"+"<a class='appoint btn btn-info' onclick='addAppointment(this)'>"+ "预约"+"</a>"+"</td>"+
                                "</tr>";
                            $("#bookTable").append(str1);
                        }
                    }
                    else
                    {
                        for(var i = 0;i<data.list.length;i++){
                            str1 = "<tr>" +
                                "<td align='center'  class='active bookid'>"+data.list[i].bookId+ "</td>" +
                                "<td align='center'  class='active bookname'>"+data.list[i].name + "</td>" +
                                "<td align='center'  class='active number' >"+data.list[i].number + "</td>" +
                                "<td align='center'  class='app'>"+"<button class='appoint btn btn-info' onclick='openModel(this)'>"+"编辑"+"</button>"+"</td>"+
                                "<td align='center' >"+"<a class='appoint btn btn-danger' onclick='deleteBook(this)'>"+ "删除"+"</a>"+"</td>"+
                                "</tr>";
                            $("#bookTable").append(str1);
                        }
                    }
                }
                else
                {
                    alert("获取失败")
                }
            }
        })
    }


    function deleteBook(obj) {
        var bookid = $(obj).parents('td').parents('tr').find('.bookid').text();
        var bookname = $(obj).parents('td').parents('tr').find('.bookname').text();
        var number = $(obj).parents('td').parents('tr').find('.number').text();
        $.confirm({
            type: 'grey',
            title: false,
            content: '确定删除该图书么？',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        $.ajax({
                            url: '<%=request.getContextPath()%>/book/deleteBook',
                            type: 'post',
                            data:{bookId:bookid,name:bookname,number:number},
                            datatype:'text',
                            success:function (data) {
                                if(data=="success") {
                                    window.location.reload();
                                }
                                else
                                {
                                    alert("删除失败");
                                }
                            }
                        })
                    }//comfirm
                },
                cancel: {
                    text: '取消',
                }
            }
        });
    }


    function confirmAddBook(obj)
    {
        var bookid =  $('#bookinfoAdd').find('.addBookID').val();
        var bookname = $('#bookinfoAdd').find('.addBookName').val();
        var number = $('#bookinfoAdd').find('.addBookNum').val();
        if(bookid == ""){
            alert("书籍ID不能为空！");
        }
        else if(bookname == "")
            alert("书籍名称不能为空！");
        else if(number == "")
            alert("书籍数量不能为空");
        else {
            $.ajax({
                url: '<%=request.getContextPath()%>/book/insertBook',
                type: 'post',
                data: {bookId: bookid, name: bookname, number: number},
                datatype: 'text',
                success: function (data) {
                    if (data == "success") {
                        $('.addBookModel').modal('hide');
                        window.location.reload();
                    } else {
                        alert("增加失败");
                    }
                }
            })
        }
    }

    function updateBook() {
        var bookid =  $('#bookinfo').find('.bookID').val();
        var bookname = $('#bookinfo').find('.bookName').val();
        var number = $('#bookinfo').find('.bookNum').val();
        if(bookid == ""){
            alert("书籍ID不能为空！");
        }
        else if(bookname == "")
            alert("书籍名称不能为空！");
        else if(number == "")
            alert("书籍数量不能为空");
        else {
            $.ajax({
                url: '<%=request.getContextPath()%>/book/editBook',
                type: 'post',
                data: {bookId: bookid, name: bookname, number: number},
                datatype: 'text',
                success: function (data) {
                    if (data == "success") {
                        $('.updateBookModel').modal('hide');
                        window.location.reload();
                    }
                    else if (data == "repeat"){
                        alert("修改失败！修改后将产生重名冲突！");
                    }
                    else {
                        alert("修改失败");
                    }
                }
            })
        }
    }

    //增加预约，读入当前框的书籍信息，若当前书籍数量不为0，发送ajax请求。判断图书是否有预约记录，若无，增加预约记录，书籍数量减1
    function addAppointment(obj)
    {
        var bookid = $(obj).parents('td').parents('tr').find('.bookid').text();
        var bookname = $(obj).parents('td').parents('tr').find('.bookname').text();
        var number = $(obj).parents('td').parents('tr').find('.number').text();
        if(number == "0")
            alert("书籍数量为0，无法预约！");
        else {
            //alert("yuyue");
            $.confirm({
                type: 'grey',
                title: false,
                content: '确定预约'+bookname+'这本图书吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        action: function () {
                            $.ajax({
                                url: '<%=request.getContextPath()%>/appointment/insertAppoint',
                                type: 'post',
                                data: {bookId: bookid,studentId: '${user.userid}'},
                                datatype: 'text',
                                    success: function (data) {
                                        if (data == "success") {
                                            //$('.addBookModel').modal('hide');
                                            alert("增加成功！");
                                            window.location.reload();
                                        }
                                        else if(data == "repeat_exception"){
                                            alert("预约失败！您已经预约过改图书，请勿重复预约！");
                                        }
                                        else {
                                            alert("增加失败");
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
        }
    }//addAppointment function
</script>


</body>
</html>
