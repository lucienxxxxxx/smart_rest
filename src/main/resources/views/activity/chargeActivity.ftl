<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>活动管理</title>
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
                        <label class="layui-form-label">活动类型：</label>
                        <div class="layui-input-inline">
                            <select name="activityType">
                                <option value="">所有</option>
                                <option value="0">送金额</option>
                                <option value="1">送礼品</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="state">
                                <option value="">所有</option>
                                <option value="1">停用</option>
                                <option value="0">启用</option>
                            </select>
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
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表格状态列 -->
<script type="text/html" id="tuopanTbState">
    <input type="checkbox" lay-filter="tuopanTbStateCk" value="{{d.id}}" lay-skin="switch"
           lay-text="启用|停用" {{d.state==0?'checked':''}} style="display: none;"/>
    <p style="display: none;">{{d.state==0?'启用':'停用'}}</p>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">

        <#-- 用户ID -->
        <input name="id" type="hidden" />

        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">活动名称</label>
            <div class="layui-input-block">
                <input name="activityName" placeholder="请输入活动名称" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">活动类型:</label>
            <div class="layui-input-block">
                <select name="activityType" lay-verType="tips" lay-verify="required" required>
                    <option value="0">送金额</option>
                    <option value="1">送礼品</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">是否可以多次参加:</label>
            <div class="layui-input-block">
                <select name="multi" lay-verType="tips" lay-verify="required" required>
                    <option value="0">可以</option>
                    <option value="1">不可以</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">充值金额</label>
            <div class="layui-input-block">
                <input name="chargeMomey" placeholder="请输入充值金额" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">实际到账</label>
            <div class="layui-input-block">
                <input name="discountsMomey" placeholder="请输入实际到账金额" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">活动礼品</label>
            <div class="layui-input-block">
                <input name="note" placeholder="请输入活动礼品" type="text" class="layui-input" maxlength="20"
                />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">起止日期:</label>
            <div class="layui-input-block">
                <input name="dateRange" placeholder="请选择起止日期" class="layui-input" autocomplete="off"
                       lay-verify="required" lay-verType="tips" required/>
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
        /**
         * ======================================表格=========================
         */
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/activity/chargeActivity/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'numbers'},
                {field: 'activityName', sort: true, title: '活动名称'},
                {field: 'activityType', sort: true, title: '活动类型',templet:function (d) {if (d.activityType==0){return '送金额';}else {return '送礼品'}}},
                {field: 'chargeMomey', sort: true, title: '充值金额',templet:function (d) {return d.chargeMomey+'元'}},
                {field: 'discountsMomey', sort: true, title: '实际到账金额',templet:function (d) {return d.discountsMomey+'元'}},
                {field: 'note', sort: true, title: '活动礼品'},
                {field: 'multi', sort: true, title: '是否能多次参加',templet: function (d) {if (d.multi==0){return '可以'} else{return '不可以'}}},
                {field: 'startDate', sort: true, title: '活动开始日期'},
                {field: 'endDate', sort: true, title: '活动截止日期'},
                {field: 'createDate', sort: true, title: '活动创建日期'},
                {field: 'state', title: '状态', templet:'#tuopanTbState', sort: true, width: 100},
                {fixed: 'right',align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 200}
            ]]
        });


        /**
        =======================================修改状态====================================
        */
        form.on('switch(tuopanTbStateCk)', function (obj) {
            console.log(obj)
            var loadIndex = layer.load(2);
            $.post('${ctx}/activity/chargeActivity/switchStatus', {
                id: obj.elem.value,
                state: obj.elem.checked ? 0 : 1
            }, function (res) {
                layer.close(loadIndex);
                if (res.code === 200) {
                    layer.msg(res.msg, {icon: 1});
                } else {
                    layer.msg(res.msg, {icon: 2});
                    $(obj.elem).prop('checked', !obj.elem.checked);
                    form.render('checkbox');
                }
            }, 'json');
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

        // 工具条点击事件
        table.on('tool(tableUser)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') { // 修改
                //修改就将该用户记录传过去，显示数据
                showEditModel(data);
            } else if (layEvent === 'del') { // 删除
                doDel(data.id);
            }
        });



        // 显示表单弹窗
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '活动',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/activity/chargeActivity/addOrUpdate?flag=1' : '${ctx}/activity/chargeActivity/addOrUpdate?flag=0';
                    // 回显数据
                    form.val('modelUserForm', mUser);
                    laydate.render({
                        elem: '#modelUserForm input[name="dateRange"]',
                        range: '~',
                        trigger: 'click'
                    });
                    // 表单提交事件
                    form.on('submit(modelSubmitUser)', function (data) {
                        layer.load(2);
                        $.post(url, data.field, function (res) {
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
                    });

                }
            });
        }


        // 删除
        function doDel(id) {
            layer.confirm('确定要删除该活动吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/activity/chargeActivity/del', {
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