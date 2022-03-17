<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>活动核销</title>
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
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="state">
                                <option value="">所有</option>
                                <option value="1">已核销</option>
                                <option value="0">待核销</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">会员号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="memberId" class="layui-input" type="text" placeholder="请输入会员号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">手机号码:</label>
                        <div class="layui-input-inline mr0">
                            <input name="mobile" class="layui-input" type="text" placeholder="请输入手机号码"/>
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">活动礼品:</label>
                        <div class="layui-input-inline mr0">
                            <input name="note" class="layui-input" type="text" placeholder="请输入活动礼品"/>
                        </div>
                    </div>

                    <div class="layui-inline" style="padding-right: 110px;">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchUser" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAddUser" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                    </div>

                </div>
            </div>

            <table id="tableUser" lay-filter="tableUser"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBarUser">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="verify">核销</a>
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
        /**
         * ======================================表格=========================
         */
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/activity/memberActivity/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'numbers'},
                {field: 'memberId', sort: true, title: '会员号'},
                {field: 'mobile', sort: true, title: '手机号码'},
                {field: 'trueName', sort: true, title: '真实姓名'},
                {field: 'note', sort: true, title: '活动礼品'},
                {field: 'activityName', sort: true, title: '活动名称'},
                {
                    field: 'useDate', sort: true, title: '核销时间', templet: d => {
                        if (d.useDate == null) {
                            return '未核销'
                        } else {
                            return d.useDate
                        }
                    }
                },
                {
                    field: 'isUse', sort: true, title: '活动礼品', templet: d => {

                        if (d.isUse == 0) {
                            return '<span style="color: #1d6abf">待核销</span>'
                        } else {
                            return '<span style="color: #00B83F">已核销</span>'
                        }
                    }
                },
                {fixed: 'right', align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 70}
            ]]
        });

        // 搜索
        form.on('submit(formSubSearchUser)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });

        // 工具条点击事件
        table.on('tool(tableUser)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'verify') { // 核销
                console.log(data)
                verify(data.id);
            }
        });


        // 核销
        function verify(id) {
            layer.confirm('确定要核销该活动吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/activity/memberActivity/verify', {
                    id: id
                }, function (res) {
                    layer.closeAll('loading');
                    if (res.code == 200) {
                        layer.msg(res.msg, {icon: 1});
                        insTb.reload({}, 'data');
                    } else {
                        layer.msg(res.msg, {icon: 2});
                    }
                }, 'json');
            });
        }
    });
</script>

</body>
</html>