
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>注册功能页</title>
</head>
<script type="text/javascript" src="js/vue.js"></script>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        <%--   获取点击注册按钮--%>
        $("#button").click(function () {
            //收集数据
            var username = $("#username").val();
            var password = $("#password").val();
            var useremail = $("#useremail").val();
            var newpassword = $("#newpassword").val();
            console.log("测试" + username)
            //数据校验
            if (username == "") {
                alert("用户名不能为空")
                return;
            }
            if (password == "") {
                alert("密码不能为空")
                return;
            }
            if (newpassword != password) {
                alert("两次密码不同")
                return;
            }
            if (useremail == "") {
                alert("邮箱不能为空")
                return;
            }

            $.ajax({
                url: 'insertuser',
                type: 'post',
                dataType: 'json',
                data: {
                    userName: username,
                    password: password,
                    email: useremail
                },
                success: function (data) {
                    if (data.code == 1) {
                        //添加成功跳转登录页
                        console.log("注册成功")
                        document.location.href = "login";
                    } else {
                        alert(data.message);
                    }

                }
            })
        })
    })
</script>
<body>

<div id="root">
    <form>
        用户名:<input id="username" type="text" v-model:value="name"><span style="color: red">{{nameMessage}}</span><br>
        密码:<input id="password" type="text" v-model:value="password"><span style="color: red">{{passwordMessage}}</span><br>
        确认密码:<input id="newpassword" type="text" v-model:value="newpassword"><span style="color: red">{{pp}}</span><br>
        邮箱:<input id="useremail" type="text" v-model:value="email"/><span style="color: red">{{emailMessage}}</span><br>
    </form>
    <div>
        <button type="button" id="button">点击注册</button>
    </div>
</div>

</body>
<script type="text/javascript">
    const vm = new Vue({
        el: '#root',
        data: {

            name: '',
            newpassword: '',
            password: '',
            email: '',


            nameMessage: '请输入用户名',
            passwordMessage: '',
            emailMessage: '',
            pp: ''

        },
        watch: {

            name: {
                handler(newValue, oldValue) {
                    immediate: true
                    if (newValue == "") {
                        vm.nameMessage = '请输入用户名'

                    } else if (newValue != "") {
                        vm.nameMessage = ''
                    }
                }
            },
            password: {
                handler(newValue, oldValue) {
                    if (newValue == "") {
                        vm.passwordMessage = '请输入密码'

                    } else if (newValue != "") {
                        vm.passwordMessage = ''
                    }
                }
            },
            newpassword: {
                handler(newValue) {
                    if (newValue!=vm.password) {
                        vm.pp = "请输入相同的密码"
                    }else {
                        vm.pp="";
                    }
                }
            },
            email: {
                handler(newValue, oldValue) {
                    if (newValue == "") {
                        vm.emailMessage = '请输入邮箱'
                        console.log("进入判断")
                    } else if (newValue != "") {
                        vm.emailMessage = ''
                    }
                }
            },

        }
    });
</script>
</html>
