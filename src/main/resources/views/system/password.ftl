<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改密码</title>
    <#include "../common.ftl">
    <style>
        .layui-form-item .layui-input-company {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">

        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label required">旧的密码</label>
                <div class="layui-input-block">
                    <input type="password" name="old_password" lay-verify="required" lay-reqtext="旧的密码不能为空" placeholder="请输入旧的密码"  value="" class="layui-input">
                    <tip>填写自己账号的旧的密码。</tip>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label required">新的密码</label>
                <div class="layui-input-block">
                    <input type="password" name="new_password" lay-verify="required" lay-reqtext="新的密码不能为空" placeholder="请输入新的密码"  value="" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">新的密码</label>
                <div class="layui-input-block">
                    <input type="password" name="again_password" lay-verify="required" lay-reqtext="新的密码不能为空" placeholder="请输入新的密码"  value="" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" >
    layui.use(['form','jquery','jquery_cookie'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery,
            $ = layui.jquery_cookie($);


        /**
         * 表单的submit监听
         *      form.on('submit(按钮元素的lay-filter属性值)', function (data) {

            });
         */
        form.on('submit(saveBtn)', function (data) {
            // 所有表单元素的值
            console.log(data.field);

            // 发送ajax请求
            $.ajax({
                type:"post",
                url:ctx + "/system/user/updatePwd",
                data:{
                    oldPassword:data.field.old_password,
                    newPassword:data.field.new_password,
                    repeatPassword:data.field.again_password
                },
                success:function (result) {
                    // 判断是否修改成功
                    if (result.code == 200) {

                        // 修改密码成功后，清空cookie数据，跳转到登录页面
                        layer.msg("用户密码修改成功，系统将在3秒钟后退出...", function () {
                            // 清空cookie
                            $.removeCookie("userIdStr",{domain:"localhost",path:"/smart_rest"});
                            $.removeCookie("userName",{domain:"localhost",path:"/smart_rest"});
                            $.removeCookie("trueName",{domain:"localhost",path:"/smart_rest"});


                            // 跳转到登录页面(父窗口跳转)
                            window.parent.location.href = ctx + "/login";
                        });

                    } else {
                        layer.msg(result.msg, {icon:5});
                    }
                }

            });
        });


    });
</script>

</body>
</html>