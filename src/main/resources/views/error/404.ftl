<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>404</title>
    <!-- js部分 -->
    <script type="text/javascript" src="${req.contextPath}/assets/libs/layui/layui.js"></script>
    <script type="text/javascript" src="${req.contextPath}/assets/js/common.js?v=316"></script>
    <!--css部分-->
    <link rel="stylesheet" href="${req.contextPath}/assets/libs/layui/css/layui.css"/>
    <link rel="stylesheet" href="${req.contextPath}/assets/module/admin.css?v=316"/>
    <link rel="stylesheet" href="${req.contextPath}/assets/css/error-page.css"/>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<!-- 加载动画 -->
<div class="page-loading">
    <div class="ball-loader">
        <span></span><span></span><span></span><span></span>
    </div>
</div>

<!-- 正文开始 -->
<div class="error-page">
    <img class="error-page-img" src="${req.contextPath}/assets/images/ic_404.png">
    <div class="error-page-info">
        <h1>404</h1>
        <div class="error-page-info-desc">啊哦，你访问的页面不存在(⋟﹏⋞)</div>
        <div>
            <button ew-href="${req.contextPath}/main" class="layui-btn">返回首页</button>
        </div>
    </div>
</div>


<script>
    layui.use(['admin'], function () {
        var $ = layui.jquery;
        var admin = layui.admin;

    });
</script>
</body>
</html>