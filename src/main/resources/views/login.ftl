
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登录</title>
    <link rel="stylesheet" href="${ctx}/assets/css/login.css?v=316">
    <#include "common.ftl">

    <script>
        if (window != top) {
            top.location.replace(location.href);
        }
    </script>
</head>
<body>
<div class="login-wrapper">
    <div class="login-header">
        <img src="${ctx}/assets/images/logo.png"> 智慧餐厅后台管理系统
    </div>
    <div class="login-body">
        <div class="layui-card">
            <div class="layui-card-header">
                <i class="layui-icon layui-icon-engine"></i>&nbsp;&nbsp;用户登录
            </div>
            <form class="layui-card-body layui-form layui-form-pane">
                <div class="layui-form-item">
                    <label class="layui-form-label"><i class="layui-icon layui-icon-username"></i></label>
                    <div class="layui-input-block">
                        <input name="username" type="text" placeholder="账号" class="layui-input"
                               lay-verType="tips" lay-verify="required" required/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><i class="layui-icon layui-icon-password"></i></label>
                    <div class="layui-input-block">
                        <input name="password" type="password" placeholder="密码" class="layui-input"
                               lay-verType="tips" lay-verify="required" required/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <a href="javascript:;" class="layui-link">帐号注册</a>
                    <a href="javascript:;" class="layui-link pull-right">忘记密码？</a>
                </div>
                <div class="layui-form-item">
                    <button lay-filter="login-submit" class="layui-btn layui-btn-fluid" lay-submit>登 录</button>
                </div>

            </form>
        </div>
    </div>

    <div class="login-footer">
        <p>© 2022 smartrest.com 版权所有</p>

    </div>
</div>


<script>
    layui.use(['layer', 'form','jquery_cookie'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var $ = layui.jquery_cookie($);
        // 表单提交
        form.on('submit(login-submit)', function (obj) {
            console.log(obj.field);

            $.ajax({
                type: "post",
                url: "${ctx}/toLogin",
                data:{
                    userName:obj.field.username,
                    userPwd:obj.field.password
                },
                success:function (result){
                    if (result.code==200){
                        layer.msg('登录成功', {icon: 1, time: 1500}, function () {

                            // 将用户信息设置到cookie中
                            $.cookie("userIdStr",result.result.userIdStr);
                            $.cookie("userName",result.result.userName);
                            $.cookie("trueName",result.result.trueName);

                            // 跳转
                            location.replace('${ctx}/main')
                        });
                    }else {
                        layer.msg(result.msg, {icon: 2, time: 1500});
                    }
                },
                fail:function (result){
                    layer.msg(result.msg, {icon: 2, time: 1500});
                }
            })
            return false;
        });


    });
</script>
</body>
</html>