<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页</title>
    <script type="text/javascript" src="js/vue.js"></script>

</head>

<style>
    /*#p {*/
    /*    color: darkgray;*/
    /*}*/
</style>
<body>
<div id="root">
    <h3></h3>
    <input type="text" id="p" v-on:click="d()" v-model:value="urla"><br>
    <input type="submit" value="点击进入" v-on:click="tiaozhuan()"><br>
    <hr>
    <a href="http://localhost:8080/spring/register">点我直接进入注册页</a>
    <p>or，已有账户立即登录</p>
    <a href="${pageContext.request.contextPath}/login">点我登录,进入图书管理系统</a>
</div>

</body>
<script type="text/javascript">
    const vm = new Vue({
        el: '#root',
        data: {
            urla: '点击输入访问的路径',
        },
        methods: {
            tiaozhuan() {
                window.open("/spring/" + vm.urla, '_blank')
            },
            d() {
                console.log(vm.urla)
                if (vm.urla == "点击输入访问的路径") vm.urla = ''
            }
        }
    });
</script>
</html>