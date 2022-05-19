<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="./assets/images/favicon.ico" rel="icon">
    <title>智慧餐厅后台管理系统</title>
    <#include "common.ftl">
</head>
<body class="layui-layout-body">
<#--<body class="theme-white" data-theme="theme-white">-->
<div class="layui-layout layui-layout-admin">
    <!-- 头部 -->
    <div class="layui-header">
        <div class="layui-logo">
            <img src="${ctx}/assets/images/logo.png"/>
            <cite>智慧餐厅后台管理系统</cite>
        </div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item" lay-unselect>
                <a ew-event="refresh" title="刷新"><i class="layui-icon layui-icon-refresh-3"></i></a>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item layui-hide-xs" lay-unselect>
                <a ew-event="fullScreen" title="全屏"><i class="layui-icon layui-icon-screen-full"></i></a>
            </li>
            <li class="layui-nav-item" lay-unselect>
                <a>
                    <img src="assets/images/head.png" class="layui-nav-img">
                    <cite>${user.userName!""}</cite>
                </a>
                <dl class="layui-nav-child">
                    <dd lay-unselect><a ew-href="page/template/user-info.html">个人中心</a></dd>
                    <dd lay-unselect><a ew-href="${ctx}/system/user/toPasswordPage">修改密码</a></dd>
                    <hr>
                    <dd lay-unselect><a class="login-out">退出</a></dd>
                </dl>
            </li>
            <#--            <li class="layui-nav-item" lay-unselect>-->
            <#--                <a ew-event="theme" title="主题"><i class="layui-icon layui-icon-more-vertical"></i></a>-->
            <#--            </li>-->
        </ul>
    </div>

    <!-- 侧边栏 -->
    <div class="layui-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="admin-side-nav" lay-shrink="_all">
                <#list menus as menu>
                    <li class="layui-nav-item">

                        <#if menu.url??>
                            <a lay-href="${ctx}${menu.url!""}"><i
                                        class="${menu.icon!""}"></i>&emsp;<cite>${menu.name!""}</cite>
                            </a>
                        <#else >
                            <a lay-href="javascript:;"><i class="${menu.icon!""}"></i>&emsp;<cite>${menu.name!""}</cite>
                            </a>
                        </#if>

                        <#if menu.subMenus?? && menu.subMenus?size!=0>
                            <dl class="layui-nav-child">
                                <#list menu.subMenus as subMenu>
                                    <dd>
                                        <#if subMenu.url??>
                                            <a lay-href="${ctx}${subMenu.url!""}">${subMenu.name!""}</a>
                                        <#else >
                                            <a lay-href="javascript:;">${subMenu.name!""}</a>
                                        </#if>
                                        <#if subMenu.subMenus?? && subMenu.subMenus?size!=0>
                                            <dl class="layui-nav-child">
                                                <#list subMenu.subMenus as temp>
                                                    <dd>
                                                        <#if temp.url??>
                                                            <a lay-href="${ctx}${temp.url!""}"> ${temp.name!""}</a>
                                                        <#else >
                                                            <a lay-href="javascript:;">${temp.name!""}</a>
                                                        </#if>
                                                    </dd>
                                                </#list>
                                            </dl>
                                        </#if>
                                    </dd>
                                </#list>
                            </dl>
                        </#if>
                    </li>
                </#list>
            </ul>
        </div>
    </div>

    <!-- 主体部分 -->
    <div class="layui-body"></div>
    <!-- 底部 -->
    <div class="layui-footer layui-text">
        <a href="https://beian.miit.gov.cn/">粤ICP备2022016020号 ©2022 lvlv5g.cn 版权所有</a>
        <span class="pull-right">Version 0.0.1</span>
    </div>
</div>

<!-- 加载动画 -->
<div class="page-loading">
    <div class="ball-loader">
        <span></span><span></span><span></span><span></span>
    </div>
</div>


<script>
    layui.use(['index', 'jquery_cookie', 'admin'], function () {
        var $ = layui.jquery;
        var $ = layui.jquery_cookie($);
        var index = layui.index;
        var Index = index
        // 默认加载主页
        index.loadHome({
            menuPath: '${ctx}/welcome',
            menuName: '<i class="layui-icon layui-icon-home"></i>'
        });

        /**
         * 用户退出
         */
        $(".login-out").click(function () {

            // 弹出提示框询问用户
            layer.confirm('确定退出系统吗?', {icon: 3, title: '系统提示'}, function (index) {
                // 关闭询问框
                layer.close(index);

                // 清空cookie信息
                $.removeCookie("userIdStr", {domain: "localhost", path: "/smart_rest"});
                $.removeCookie("userName", {domain: "localhost", path: "/smart_rest"});
                $.removeCookie("trueName", {domain: "localhost", path: "/smart_rest"});

                Index.clearTabCache();
                // 跳转到登录页面（父窗口跳转）
                window.parent.location.href = ctx + "/login";
            });
        });

    });
</script>
</body>
</html>