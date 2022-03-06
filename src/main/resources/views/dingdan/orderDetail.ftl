<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>订单详情</title>
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
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <div class="layui-inline ">
                        <label class="layui-form-label">订单号:</label>
                        <span style="font-size: larger ;line-height: 38px">${orderId}</span>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="state">
                                <option value="">所有</option>
                                <option value="0">订单完成</option>
                                <option value="1">请求退款</option>
                                <option value="2">退款成功</option>
                            </select>
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
<!-- 表格操作列 -->
<script type="text/html" id="tableBarTbAdv">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="refund">退款</a>
<#--    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>-->
<#--    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>-->
</script>
<!-- 表格状态列 -->
<script type="text/html" id="tplStateTbAdv">
    <input type="checkbox" lay-filter="ckStateTbAdv" value="{{d.userId}}" lay-skin="switch"
           lay-text="正常|锁定" {{d.state==0?'checked':''}}/>
</script>
<#--退款弹窗框-->
<script type="text/html" id="refundOpt">
    <form id="refundForm" lay-filter="refundForm" class="layui-form model-form">
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required" style="width: 50px">退款金额：</label>
            <div class="layui-input-block">
                <input name="refundMoney" placeholder="请输入退款金额" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="submitRefund" lay-submit>确定</button>
        </div>
    </form>
</script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'laydate', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var laydate = layui.laydate;
        var admin = layui.admin;
        form.render('select');
        // 渲染表格
        var insTb = table.render({
            elem: '#tableTbAdv',
            url: '${ctx}/dingdan/orderDetail/list?orderId=' + '${orderId}',
            page: true,
            cellMinWidth: 100,
            totalRow: true ,//开启合计行
            cols: [[
                {fixed: 'left',field: 'foodName', align: 'center', sort: true, title: '食物名称'},
                {field: 'weight', align: 'center', sort: true, title: '重量',templet:function (d) {return d.weight+'克'}},
                {field: 'createTime', sort: true, align: 'center', title: '创建时间',totalRowText: '合计：'},
                {field: 'virtualAcc', align: 'center', sort: true, title: '虚拟账户',templet:function (d) {return d.virtualAcc+'元'} , totalRow: true},
                {field: 'giftAcc', align: 'center', sort: true, title: '赠送账户',templet:function (d) {return d.giftAcc+'元'} , totalRow: true},
                {field: 'allowanceAcc', align: 'center', sort: true, title: '补贴账户', templet:function (d) {return d.allowanceAcc+'元'} ,totalRow: true},
                {field: 'cashAcc', align: 'center', sort: true, title: '现金账户',templet:function (d) {return d.cashAcc+'元'} ,totalRow: true},
                {field: 'chargeAcc', align: 'center', sort: true, title: '充值账户',templet:function (d) {return d.chargeAcc+'元'} , totalRow: true},
                {
                    field: 'state', title: '状态', templet: function (d) {
                        var strs = [
                            '<span style="color: #189700">订单完成</span>',
                            '<span style="color: #af0000">请求退款</span>',
                            '<span style="color: #0e2fe5">退款成功</span>'
                        ];
                        return strs[d.orderDetailState];
                    }, title: '状态'
                },
                {fixed: 'right',align: 'center', toolbar: '#tableBarTbAdv', title: '操作', minWidth: 200}
            ]]
        });

        // 搜索
        form.on('submit(formSubSearchTbAdv)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });

        //监听工具条
        table.on('tool(tableTbAdv)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'edit') { //查看
                layer.msg('点击了修改');
            } else if (layEvent === 'del') { //删除
                layer.msg('点击了删除');
            } else if (layEvent === 'refund') { //退款
                showRefund(data);
            }
        });

        //退款弹窗
        function showRefund(orderDetail) {
            admin.open({
                type: 1,
                title: '退款',
                content: $('#refundOpt').html(),
                success: function (layero, dIndex) {
                    var url = '${ctx}/dingdan/orderDetail/refund';
                    // 表单提交事件
                    form.on('submit(submitRefund)', function (data) {
                        //计算子订单总金额
                        var orderMoney = orderDetail.virtualAcc + orderDetail.giftAcc + orderDetail.allowanceAcc + orderDetail.cashAcc + orderDetail.chargeAcc
                        if (data.field.refundMoney > orderMoney) {
                            layer.open({
                                title: '退款失败'
                                , content: '退款金额不能大于订单总金额'
                            });
                            return false;
                        } else {
                            layer.load(2);
                            var refundData = {
                                id: orderDetail.id,
                                orderId: orderDetail.orderId,
                                refundMoney: data.field.refundMoney
                            }
                            $.post(url,refundData, function (res) {
                                layer.closeAll('loading');
                                if (res.code == 200) {
                                    layer.close(dIndex);
                                    layer.msg(res.msg, {icon: 1});
                                    insTb.reload({}, 'data');
                                } else {
                                    layer.msg(res.msg, {icon: 2});
                                }
                            }, 'json');
                            return false;
                        }
                    })
                }
            });
        }

        // 渲染laydate
        laydate.render({
            elem: '#edtDateTbAdv'
        });

    });
</script>
</body>

</html>