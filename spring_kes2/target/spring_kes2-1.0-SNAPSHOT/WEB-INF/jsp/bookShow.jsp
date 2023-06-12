
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>图书主页面</title>
</head>
<link rel="stylesheet" href="js/bootstrap.min.css">
<script type="text/javascript" src="js/vue.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<style>


    body {
        text-align: center;
    }

    table, tr, td {
        margin: auto;
        border: 1px solid pink;

    }


</style>


<body>
<script type="text/javascript">
    $(function () {
        //给点击新增按钮增加事件
        $("#btn-new").click(function () {
            console.log("测试:点击了新增按钮")

            //收集参数
            var newType = $("#new-type").val();
            var newName = $("#new-name").val();
            var newDescription = $("#new-description").val();

            console.log(newType);
            console.log(newName);
            console.log(newDescription);
            //使用ajax异步请求发送给后端 保存数据 刷新页面
            $.ajax({
                url: 'insertbook',
                Type: 'post',
                dataType: 'json',
                data: {
                    type: newType,
                    name: newName,
                    description: newDescription,
                },
                success: function (data) {
                    console.log("测试进入success")
                    //新增成功  刷新页面关闭模态窗口
                    if (data.code == 1) {
                        console.log("测试进入成功")
                        $('#newInsert').modal('hide')//关闭模态窗口
                        //清空列表参数
                        newType = "",
                            newName = "",
                            newDescription = "",
                            window.location = "bookShow"
                    } else {
                        //新增失败给出提示信息，页面不关闭
                        alert(data.message)
                    }
                }
            })

        })


        /*基础流程为:获取选中修改的当前行的id，根据id进行一次查找数据 查找数据为一条再通过data.xxx 获取的数据赋值给当前行的按钮*/
        // $("#updata").click(function () {
        //     console.log(this.id)
        //     //  console.log($("td").attr('value'))
        //     // console.log(obj)
        //     //  console.log(this.parent())
        // })
        <c:forEach items="${books}" var="books">
        $("#updata${books.id}").click(function () {
            var uid = ${books.id}

                console.log(uid)
            //发送请求渲染页面
            $.ajax({
                url: "selectBookById",
                data: {
                    id: uid
                },
                Type: "post",
                dataType: "json",
                success: function (data) {
                    console.log("成功" + data.type)

                    $("#up-id").val(data.id)
                    $("#up-type").val(data.type)
                    $("#up-name").val(data.name)
                    $("#up-description").val(data.description)
                    $('#upupdate').modal('show')
                }
            })
        })
        </c:forEach>

        //    修改功能 给修改按钮添加点击事件
        $("#up-update").click(function () {
            //收集新写的参数
            var uid = $("#up-id").val()
            var type = $("#up-type").val()
            var name = $("#up-name").val()
            var description = $("#up-description").val();
            if (type == "") {
                alert("请填写要修改的书籍类型")
                return
            }
            if (name == "") {
                alert("请填写要修改的书籍名称")
                return
            }
            if (description == "") {
                alert("请填写要修改的书籍价格")
                return
            }

            $.ajax({
                url: 'updateBookById',
                data: "post",
                dataType: "json",
                data: {
                    id: uid,
                    type: type,
                    name: name,
                    description: description
                },
                success: function (data) {
                    if (data.code == 1) {
                        //修改成功 关闭模态窗口刷新页面
                        $('#upupdate').modal('hide')
                        //刷新页面
                        window.location = "bookShow"
                    } else {
                        //页面不刷新 ，提示 失败信息
                        alert(data.message);
                    }
                }

            })
        });
//实现删除功能
        <c:forEach items="${books}" var="books">
        $("#delete${books.id}").click(function () {
            var uid =
            ${books.id}
            if (window.confirm("您确定要删除第" + uid + "的信息吗")) {
                $.ajax({
                    url: 'deleteBookById',
                    data: 'post',
                    dataType: 'json',
                    data: {
                        id: uid,
                    },
                    success: function (data) {
                        if (data.code) {
                            //刷新页面
                            window.location = "bookShow"
                        } else {
                            alert(data.message)
                        }
                    }
                })
            }

        })

        </c:forEach>

        //实现模糊查询的功能
        $("#sea-button").click(function () {
            var name = $("#name").val()
            var type = $("#type").val()

            $.ajax({
                url: 'selectByBook',
                type: 'post',
                datatype: 'json',
                data: {
                    name: name,
                    type: type,
                    pageNo: 1,
                    pageSize: 10,
                },
                success: function (data) {

                    console.log("页面刷新")
                    console.log(data.count)
                    console.log(data.books)
                    var htmlStr = "<tr> <th>书籍id</th>"
                    htmlStr += "<th>书籍名称</th>"
                    htmlStr += " <th>书籍类型</th>"
                    htmlStr += "  <th>书籍简介</th>"
                    htmlStr += "  <th><!-- 按钮触发新增模态窗口 -->"
                    htmlStr += '<button data-toggle="modal" data-target="#newInsert">我要再增一条</button>'
                    htmlStr += " </th>"
                    htmlStr += ' </tr>";'
                    /*index - 选择器的 index 位置
                      obj - 当前的元素（也可使用 "this" 选择器）*/
                    $.each(data.books, function (index, obj) {
                        console.log("ob" + obj)
                        console.log("ob" + obj.type)
                        htmlStr += "<tr> <td value=\"" + obj.id + "\">" + obj.id + "</td>"
                        htmlStr += "<td>" + obj.name + "</td>"
                        htmlStr += "<td>" + obj.type + "</td>"
                        htmlStr += "<td>" + obj.description + "</td>"
                        htmlStr += "<td> <button v=\"" + obj.id + "\" id=\"update" + obj.id + "\">修改</button></td>"
                        htmlStr += "<td> <button v=\"" + obj.id + "\" id=\"delete" + obj.id + "\">删除</button></td>"
                        htmlStr += '</tr>'
                    });
                    var htmlStr2 = ' 当前共' + data.count + '条数据 当前第xxboo页 <a>上一页</a> <a>下一页</a> <a>跳转首页</a> <input type="">跳转'
                    $("#tBody").html(htmlStr);
                    $("#div2").html(htmlStr2)

                    /*
                    分析分页功能
                       第一我们分页功能要在两个地方使用，一：查询之前 二：查询之后使用(可以考虑分装为函数)
                       需要接收的数据 当前页与 当前页的数量 我们现在默认为1，10 这时候需要换为动态数据

                       设置分页基本信息 分页条信息：beginPage 首页prevPage： 上一页nextPage
                      nextPage： 下一页    totalPage： 总页数/末页      totalCount/rows： 总条数
                    currentPage： 当前页
                       pageSize： 每页显示多少条数据
                    */
                }

            })

        })


    })
</script>

书籍名称:<input id="name" type="text"> 书籍类型:<input id="type" type="text">
<button id="sea-button">点击搜索</button>
<hr>
<div id="root-div">
    <table id="tBody">
        <tr>
            <th>书籍id</th>
            <th>书籍名称</th>
            <th>书籍类型</th>
            <th>书籍简介</th>
            <th><!-- 按钮触发新增模态窗口 -->
                <button data-toggle="modal" data-target="#newInsert">我要再增一条</button>
            </th>
        </tr>
        <c:forEach items="${books}" var="books">

            <tr>
                <td value="${books.id}">${books.id}</td>
                <td>${books.name}</td>
                <td>${books.type}</td>
                <td>${books.description}</td>

                <td>
                    <button v="${books.id}" id="updata${books.id}">修改</button>
                        <%--            data-toggle="modal" data-target="#updata-book"--%>
                </td>

                <td>
                    <button v="${books.id}" id="delete${books.id}">删除</button>
                </td>
            </tr>
        </c:forEach>

    </table>
    <div id="div2">
        当前共${count}条数据 当前第${pageNo}页 <a id="nextPage" href="nextPage?pageNo=${pageNo}">上一页</a>
        <a id="totalPage" href="totalPage?pageNo=${pageNo}">下一页</a> <input type="">跳转
    </div>
</div>
<!-- 模态框：修改 -->
<div class="modal fade" id="upupdate" tabindex="-1" role="dialog" aria-labelledby="upupdate" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">输入要修改的基本信息</h4>
            </div>
            <span style="display: none" id="up-id" value=""></span>
            书籍类型:<input type="text" id="up-type" value=""><br>
            书籍名字:<input type="text" id="up-name" value=""><br>
            书籍简介:<textarea id="up-description" rows="10" cols="20" value=""></textarea>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="up-update">点击修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<!-- 模态框：新增 -->
<div class="modal fade" id="newInsert" tabindex="-1" role="dialog" aria-labelledby="newInsert" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">输入要添加的书籍基本信息</h4>
            </div>
            <%--            <div class="modal-body">在这里添加一些文本</div>--%>
            书籍类型:<input type="text" id="new-type"><br>
            书籍名字:<input type="text" id="new-name"><br>
            书籍简介:<textarea id="new-description" rows="10" cols="20"></textarea>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="btn-new">点击新增</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<hr>

</body>

<script type="text/javascript">


</script>


</html>
