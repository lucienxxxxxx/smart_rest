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
            <#-- 判断当前登录用户是否拥有权限 -->
            <#if permissions??>
            <ul class="layui-nav layui-nav-tree arrow2" lay-filter="admin-side-nav" lay-shrink="all">
                <#-- 通过freemarker中的seq_contains内建指令判断菜单是否显示 -->
                <#if permissions?seq_contains("10") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-set"></i>&emsp;<cite>系统管理</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("1010")>
                                <dd><a lay-href="${ctx}/system/org/index">机构管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("1020")>
                                <dd><a lay-href="${ctx}/system/user/index">用户管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("1030")>
                                <dd><a lay-href="${ctx}/system/role/index">角色管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("1040")>
                                <dd><a lay-href="${ctx}/system/module/index">权限管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("1050")>
                                <dd><a lay-href="${ctx}/system/setting/index">系统设置</a></dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <#if permissions?seq_contains("20") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-template"></i>&emsp;<cite>店铺管理</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("2010")>
                                <dd><a lay-href="${ctx}/dianpu/terminal/index">终端管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2020")>
                                <dd><a lay-href="${ctx}/dianpu/tuopan/index">托盘管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2030") >
                            <dd><a lay-href="${ctx}/dianpu/staff/index">员工管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2040") >
                            <dd><a lay-href="${ctx}/dianpu/rfid-user/index">管理卡管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2050") >
                            <dd><a lay-href="${ctx}/system/restaurant/index">店铺参数设置</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2060") >
                                <dd><a lay-href="${ctx}/system/foodSetting1/index.html">店铺设置</a></dd>
                            </#if>
                            <#if permissions?seq_contains("2070") >
                                <dd>
                                    <a>档口管理</a>
                                    <dl class="layui-nav-child">
                                        <#if permissions?seq_contains("207010") >
                                            <dd><a lay-href="${ctx}/dianpu/food/index">菜品管理</a></dd>
                                        </#if>
                                        <#if permissions?seq_contains("207020") >
                                            <dd><a lay-href="${ctx}/dianpu/foodTag/index">菜品标签管理</a></dd>
                                        </#if>
                                    </dl>
                                </dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <#if permissions?seq_contains("30") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-component"></i>&emsp;<cite>充值活动</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("3010") >
                            <dd><a lay-href="${ctx}/system/charge1/index.html">充值优惠</a></dd>
                            </#if>
                            <#if permissions?seq_contains("3020") >
                            <dd><a lay-href="${ctx}/system/consumption1/index.html">消费设置</a></dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <#if permissions?seq_contains("40") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-component"></i>&emsp;<cite>订单管理</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("4010") >
                            <dd><a lay-href="${ctx}/dingdan/order/index">订单管理</a></dd>
                            </#if>
                            <#if permissions?seq_contains("4020") >
                            <dd><a lay-href="${ctx}/system/consumption/index.html">退款单管理</a></dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <#if permissions?seq_contains("50") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-component"></i>&emsp;<cite>统计管理</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("5010") >
                            <dd><a lay-href="${ctx}/system/charge3/index.html">餐别统计</a></dd>
                            </#if>
                            <#if permissions?seq_contains("5020") >
                            <dd><a lay-href="${ctx}/system/consumption1/index.html">充值统计</a></dd>
                            </#if>
                            <#if permissions?seq_contains("5030") >
                            <dd><a lay-href="${ctx}/system/consumption2/index.html">会员统计</a></dd>
                            </#if>
                            <#if permissions?seq_contains("5040") >
                            <dd><a lay-href="${ctx}/system/consumption3/index.html">商品统计</a></dd>
                            </#if>
                            <#if permissions?seq_contains("5060") >
                            <dd><a lay-href="${ctx}/system/consumption4/index.html">营收统计</a></dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <#if permissions?seq_contains("60") >
                    <li class="layui-nav-item">
                        <a><i class="layui-icon layui-icon-app"></i>&emsp;<cite>顾客管理</cite></a>
                        <dl class="layui-nav-child">
                            <#if permissions?seq_contains("6010") >
                            <dd>
                                <a>线下会员</a>
                                <dl class="layui-nav-child">
                                    <#if permissions?seq_contains("601010") >
                                    <dd><a lay-href="${ctx}/system/charge4/index.html">资金管理</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("601020") >
                                    <dd><a lay-href="${ctx}/system/consumption11/index.html">余额变动</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("601030") >
                                    <dd><a lay-href="${ctx}/guke/offlineMember/index">会员管理</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("601040") >
                                    <dd><a lay-href="${ctx}/system/consumption31/index.html">会员评论</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("601050") >
                                    <dd><a lay-href="${ctx}/system/consumption41/index.html">会员卡挂失</a></dd>
                                    </#if>
                                </dl>
                            </dd>
                            </#if>
                            <#if permissions?seq_contains("6020") >
                            <dd>
                                <a>线上会员</a>
                                <dl class="layui-nav-child">
                                    <#if permissions?seq_contains("602010") >
                                    <dd><a lay-href="${ctx}/guke/onlineMember/index">会员管理</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("602020") >
                                    <dd><a lay-href="${ctx}/system/consumption1/index.html">会员评论</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("602030") >
                                    <dd><a lay-href="${ctx}/system/consumption21/index.html">会员提现</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("602040") >
                                    <dd><a lay-href="${ctx}/system/consumption3/index.html">实时余额</a></dd>
                                    </#if>
                                    <#if permissions?seq_contains("602050") >
                                    <dd><a lay-href="${ctx}/system/consumption4/index.html">资金管理</a></dd>
                                    </#if>
                                </dl>
                            </dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
            </ul>
            </#if>
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
    layui.use(['index', 'jquery_cookie','admin'], function () {
        var $ = layui.jquery;
        var $ = layui.jquery_cookie($);
        var index = layui.index;
        var Index =index
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