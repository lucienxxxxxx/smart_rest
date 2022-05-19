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
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">会员类型</label>
                        <div class="layui-input-inline">
                            <select name="memberType">
                                <option value="">所有</option>
                                <option value="0">线上会员</option>
                                <option value="1">线下会员</option>
                                <option value="2">员工</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">机构:</label>
                        <div class="layui-input-inline ">
                            <input name="orgId" id="selectOrg" placeholder="请选择" class="layui-hide"/>
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
    layui.use(['layer', 'form', 'cascader', 'table', 'util', 'admin', 'formSelects', "xmSelect", 'laydate'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        var laydate = layui.laydate;
        var cascader = layui.cascader;
        // 渲染表格

        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/stats/mealTime/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {field: 'date',width: '140',align: 'center', title: '日期'},
                {field: 'breakfastTotal',align: 'center',title: '早餐总额',templet: function (d) {return d.breakfastTotal+'元';}},
                {field: 'breakfastNum',align: 'center',title: '早餐订单数'},
                {field: 'breakfastAvg',align: 'center',title: '早餐均价',templet: function (d) {return d.breakfastAvg+'元';}},
                {field: 'lunchTotal',align: 'center',title: '午餐总额',templet: function (d) {return d.lunchTotal+'元';}},
                {field: 'lunchNum',align: 'center',title: '午餐订单数'},
                {field: 'lunchAvg',align: 'center',title: '午餐均价',templet: function (d) {return d.lunchAvg+'元';}},
                {field: 'dinnerTotal',align: 'center',title: '晚餐总额',templet: function (d) {return d.dinnerTotal+'元';}},
                {field: 'dinnerNum',align: 'center',title: '晚餐订单数'},
                {field: 'dinnerAvg',align: 'center',title: '晚餐均价',templet: function (d) {return d.dinnerAvg+'元';}},
                {field: 'total',title: '当日总额',align: 'center',templet: function (d) {return d.total+'元';}},
                {title: '当日总订单数',width: '140',align: 'center',templet: function (d) {return d.breakfastNum+d.lunchNum+d.dinnerNum;}},
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

        /**
         * 渲染组织下拉菜单
         */
        $.get('${ctx}/system/org/getAllOrgList',function (orgData){
            cascader.render({
                elem: '#selectOrg',
                data: orgData,
                filterable: true,
                changeOnSelect: true,
                trigger: 'hover'
            });
        })
    });
</script>

</body>
</html>