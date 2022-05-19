<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>订单管理</title>
    <#include "../common.ftl">
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
<!---------------------------------------- 顶部搜索---------------------------------- -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">订单号：</label>
                        <div class="layui-input-inline">
                            <input name="id" class="layui-input" type="text" placeholder="输入订单号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">会员号：</label>
                        <div class="layui-input-inline">
                            <input name="memberId" class="layui-input" type="text" placeholder="输入会员号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">手机号码：</label>
                        <div class="layui-input-inline">
                            <input name="mobile" class="layui-input" type="text" placeholder="输入手机号码"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="state">
                                <option value="">所有</option>
                                <option value="0">订单完成</option>
                                <option value="1">请求退款</option>
                                <option value="2">退款成功</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">开始时间：</label>
                        <div class="layui-input-inline">
                            <input id="edtDateTbAdv" name="orderDate" class="layui-input date-icon" type="text"
                                   placeholder="选择开始时间" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">截止时间：</label>
                        <div class="layui-input-inline">
                            <input id="edtDateTbAdv1" name="endDate" class="layui-input date-icon" type="text"
                                   placeholder="选择结束时间" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="layui-inline" style="padding-left: 20px;">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchTbAdv" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                    </div>
                </div>
            </div>
            <table id="tableTbAdv" lay-filter="tableTbAdv"></table>
        </div>
    </div>

</div>

<!-- ---------------------------------------------表格操作列-------------------------------------------- -->
<script type="text/html" id="tableBarTbAdv">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看详情</a>
    <#--    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>-->
    <#--    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>-->
</script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'laydate', 'admin',], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var laydate = layui.laydate;
        var admin = layui.admin;
        form.render('select');

        //------------------------------------------- 渲染表格---------------------------------------------
        var insTb = table.render({
            elem: '#tableTbAdv',
            url: '${ctx}/dingdan/order/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {fixed: 'left', field: 'id', align: 'center',title: '订单号', minWidth: 200},
                {field: 'memberId', align: 'center',title: '会员号'},
                {field: 'mobile', align: 'center',title: '手机号', minWidth: 150},
                {field: 'tuopanId', align: 'center',title: '托盘号'},
                {field: 'orderDate',  align: 'center', title: '创建时间', minWidth: 200},
                {field: 'total', align: 'center',title: '消费总额', templet: function (d) {
                        if (d.total == null) {
                            d.total =0
                        }
                        var str = '<span style="font-weight: bold">'+d.total +'元</span>'
                        return str}, totalRow: true},
                {field: 'refundTotal', align: 'center',title: '退款总额', templet: function (d) {if (d.refundTotal==null){return '0元'}return d.refundTotal + '元'}, totalRow: true},
                {field: 'state', title: '状态', align: 'center', templet: function (d) {
                        var strs = [
                            '<span style="color: green">订单完成</span>',
                            '<span style="color: red">请求退款</span>',
                            '<span style="color: blue">退款成功</span>'
                        ];
                        return strs[d.state];}, title: '状态'},
                {fixed: 'right', align: 'center', toolbar: '#tableBarTbAdv', title: '操作'}
            ]]
        });


        //-----------------------------------------表格工具条------------------------------
        table.on('tool(tableTbAdv)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'edit') { //查看
                layer.msg('点击了修改');
                layer.msg(data.id);
            } else if (layEvent === 'del') { //删除
                layer.msg('点击了删除');
            } else if (layEvent === 'detail') { //查看详情
                var index = layui.layer.open({
                    type: 2,
                    title: '订单详情',
                    content: '${ctx}/dingdan/order/toOrderDetailPage/' + data.id,
                    // area: ["100%", "100%"],
                    maxmin: true
                });
                layer.full(index)
            }
        });

        //-----------------------------------------顶部按钮--------------------------------
        // 搜索
        form.on('submit(formSubSearchTbAdv)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });

        // 渲染laydate
        laydate.render({
            elem: '#edtDateTbAdv',
            type: 'datetime'
        });
        // 渲染laydate
        laydate.render({
            elem: '#edtDateTbAdv1',
            type: 'datetime'
        });


    });
</script>
</body>

</html>