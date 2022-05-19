<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>餐别统计</title>
    <#include "../common.ftl">
</head>
<body>
<!-- 页面加载loading -->
<div class="page-loading">
    <div class="ball-loader">
        <span></span><span></span><span></span><span></span>
    </div>
</div>
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body table-tool-mini full-table">
            <div class="layui-form toolbar">
                <div class="layui-form-item">

                    <div class="layui-inline ">
                        <label class="layui-form-label">开始时间：</label>
                        <div class="layui-input-inline">
                            <input id="edtDateTbAdv" name="startDateTime" class="layui-input date-icon" type="text"
                                   placeholder="选择开始时间" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">截止时间：</label>
                        <div class="layui-input-inline">
                            <input id="edtDateTbAdv1" name="endDateTime" class="layui-input date-icon" type="text"
                                   placeholder="选择结束时间" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="layui-inline" style="padding-right: 110px;">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchUser" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>

                    </div>
                </div>
            </div>

            <table id="tableUser" lay-filter="tableUser"></table>
        </div>
    </div>
</div>

<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">
        <#-- 用户ID -->
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">ID</label>
            <div class="layui-input-block">
                <input name="id" placeholder="请输入ID" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">逻辑号</label>
            <div class="layui-input-block">
                <input name="logicName" placeholder="请输入逻辑号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">描述</label>
            <div class="layui-input-block">
                <input name="descriptions" placeholder="请输入描述" type="text" class="layui-input" maxlength="20"
                />
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitUser" lay-submit>保存</button>
        </div>
    </form>
</script>

<!-- js部分 -->
<script>
    layui.use(['layer', 'form', 'table', 'util', 'admin', 'formSelects', "xmSelect", 'laydate'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        var laydate = layui.laydate;
        // 渲染表格

        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/stats/money/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {field: 'date', width: 120, title: '日期', align: 'center'},
                {field: 'chargeVirtualAcc', width: 140, align: 'center', title: '(充值)虚拟账户', templet: function (d) {return d.chargeVirtualAcc + '元'}},
                {field: 'chargeGiftAcc', width: 140, align: 'center',  title: '(充值)赠送账户', templet: function (d) {return d.chargeGiftAcc + '元'}},
                {field: 'chargeAllowanceAcc', width: 140, align: 'center', title: '(充值)补贴账户', templet: function (d) {return d.chargeAllowanceAcc + '元'}},
                {field: 'chargeCashAcc', width: 140, align: 'center',  title: '(充值)现金账户', templet: function (d) {return d.chargeCashAcc + '元'}},
                {field: 'chargeChargeAcc', width: 140, align: 'center', title: '(充值)充值账户', templet: function (d) {return d.chargeChargeAcc + '元'}},
                {field: 'chargeTotal', width: 120,title: '充值总额',templet: function (d) {return d.chargeTotal+'元';}, align: 'center'},
                {field: 'refundVirtualAcc', width: 140, align: 'center', title: '(退款)虚拟账户', templet: function (d) {return d.refundVirtualAcc + '元'}},
                {field: 'refundGiftAcc', width: 140, align: 'center',  title: '(退款)赠送账户', templet: function (d) {return d.refundGiftAcc + '元'}},
                {field: 'refundAllowanceAcc', width: 140, align: 'center', title: '(退款)补贴账户', templet: function (d) {return d.refundAllowanceAcc + '元'}},
                {field: 'refundCashAcc', width: 140, align: 'center',  title: '(退款)现金账户', templet: function (d) {return d.refundCashAcc + '元'}},
                {field: 'refundChargeAcc', width: 140, align: 'center', title: '(退款)充值账户', templet: function (d) {return d.refundChargeAcc + '元'}},
                {field: 'refundTotal', width: 120,title: '退款总额',templet: function (d) {return d.refundTotal+'元';}, align: 'center'},
                {field: 'withdrawalTotal', width: 120,title: '提现总额',templet: function (d) {return d.withdrawalTotal+'元';}, align: 'center'},
                {field: 'orderTotal', width: 120,title: '消费总额',templet: function (d) {return d.orderTotal+'元';}, align: 'center'},
            ]]
        });

        // 添加
        $('#btnAddUser').click(function () {
            //添加就直接弹窗
            showEditModel();
        });

        // 搜索
        form.on('submit(formSubSearchUser)', function (data) {
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