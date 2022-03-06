<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../../assets/images/favicon.ico" rel="icon">
    <title>智慧餐厅后台管理系统</title>
    <#include "common.ftl">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <!-- 头部 -->
    <div class="layui-header">
        <div class="layui-logo">
            <img src="../../assets/images/logo.png"/>
            <cite>&nbsp;EasyWeb Iframe</cite>
        </div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item" lay-unselect>
                <a ew-event="flexible" title="侧边伸缩"><i class="layui-icon layui-icon-shrink-right"></i></a>
            </li>
            <li class="layui-nav-item" lay-unselect>
                <a ew-event="refresh" title="刷新"><i class="layui-icon layui-icon-refresh-3"></i></a>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item" lay-unselect>
                <a>
                    <img src="../../assets/images/head.png" class="layui-nav-img">
                    <cite>管理员</cite>
                </a>
            </li>
        </ul>
    </div>

    <!-- 侧边栏 -->
    <div class="layui-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="admin-side-nav" style="margin: 15px 0;">
            </ul>
        </div>
    </div>

    <!-- 主体部分 -->
    <div class="layui-body"></div>
    <!-- 底部 -->
    <div class="layui-footer">Copyright © 2019 EasyWeb All rights reserved.</div>
</div>

<!-- 加载动画 -->
<div class="page-loading">
    <div class="ball-loader">
        <span></span><span></span><span></span><span></span>
    </div>
</div>

<!-- 侧边栏渲染模板 -->
<script id="sideNav" type="text/html">
    {{#  layui.each(d, function(index, item){ }}
    <li class="layui-nav-item">
        <a lay-href="${ctx}{{item.url}}"><i class="{{item.icon}}"></i>&emsp;<cite>{{item.name}}</cite></a>
        {{# if(item.subMenus&&item.subMenus.length>0){ }}
        <dl class="layui-nav-child">
            {{# layui.each(item.subMenus, function(index, subItem){ }}
            <dd>
                <a lay-href="{{ subItem.url }}">{{ subItem.name }}</a>
                {{# if(subItem.subMenus&&subItem.subMenus.length>0){ }}
                <dl class="layui-nav-child">
                    {{# layui.each(subItem.subMenus, function(index, thrItem){ }}
                    <dd><a lay-href="{{ thrItem.url }}">{{ thrItem.name }}</a></dd>
                    {{# }); }}
                </dl>
                {{# } }}
            </dd>
            {{# }); }}
        </dl>
        {{# } }}
    </li>
    {{#  }); }}
</script>
<script>
    layui.use(['layer', 'element', 'admin', 'index', 'laytpl', 'element'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var admin = layui.admin;
        var index = layui.index;
        var laytpl = layui.laytpl;
        var element = layui.element;

        // 默认加载主页
        index.loadHome({
            menuPath: '../console/console.html',
            menuName: '<i class="layui-icon layui-icon-home"></i>',
            loadSetting: false
        });

        // ajax渲染侧边栏
        $.get(ctx + '/system/module/queryModules', {userId: ${userId}}, function (res) {
            laytpl(sideNav.innerHTML).render(res.result, function (html) {
                $('*[lay-filter=admin-side-nav]').html(html);
                element.render('nav');
                admin.activeNav('../console/console.html');
            });
        }, 'json');


    });
</script>
</body>
</html>