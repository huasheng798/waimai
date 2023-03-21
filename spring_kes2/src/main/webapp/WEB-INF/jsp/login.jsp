
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<script type="text/javascript" src="js/vue.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<html>
<head>
    <base href="<%=basePath%>">
    <title>登录页</title>
</head>
<script type="text/javascript">
    $(function () {
        //获取点击按钮
        $("#button-denglu").click(function () {
            //收集参数
            var username = $("#username").val();
            var password = $("#password").val();
            //表单验证
            if (username == "") {
                alert("用户名为空")
                return;
            }
            if (password == "") {
                alert("密码为空")
                return;
            }
            $.ajax({
                url: 'loginuser',
                Type: 'post',
                dataType: 'json',
                data: {
                    userName: username,
                    password: password
                },
                success: function (data) {
                    if (data.code == 1) {
                        window.location="bookShow"

                    } else {
                        alert(data.message);
                        return;
                    }
                }
            })


        })
    })

</script>
<body>
你好,这里是登录页
<div>
    <form>
        用户名:<input type="text" id="username"><br>
        密码:<input type="password" id="password">
    </form>
    <div>
        <button type="button" id="button-denglu">点击登录</button>
    </div>

</div>
</body>
</html>
