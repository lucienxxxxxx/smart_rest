<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>会员管理</title>
    <#include "../../common.ftl">
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
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">会员号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="id" class="layui-input" type="text" placeholder="请输入会员号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">逻辑卡号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="logicName" class="layui-input" type="text" placeholder="请输入逻辑卡号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">手机号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="mobile" class="layui-input" type="text" placeholder="请输入手机号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">真实姓名:</label>
                        <div class="layui-input-inline mr0">
                            <input name="trueName" class="layui-input" type="text" placeholder="请输入真实姓名"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">描&emsp;述:</label>
                        <div class="layui-input-inline mr0">
                            <input name="note" class="layui-input" type="text" placeholder="请输入描述"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="state">
                                <option value="">所有</option>
                                <option value="0">启用</option>
                                <option value="1">禁用</option>
                                <option value="2">挂失</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline" style="padding-right: 110px;">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchUser" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAddUser" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                        <button id="btnAddInfo" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>信息完善</button>
                    </div>
                </div>
            </div>

            <table id="tableUser" lay-filter="tableUser"></table>
        </div>
    </div>
</div>
<!-- 表单弹窗 -->
<script type="text/html" id="editStatus">
    <form id="editStatusFrom" lay-filter="editStatusFrom" class="layui-form model-form">
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">状态:</label>
            <div class="layui-input-block">
                <select name="status" lay-verify="required" lay-verType="tips" required>
                    <option value="">请选择</option>
                    <option value="0">启用</option>
                    <option value="1">禁用</option>
                    <option value="2">挂失</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="statusSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>
<!-- 表格操作列 -->
<script type="text/html" id="tableBarUser">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="switch">切换状态</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">信息完善</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">
        <#-- 用户ID -->
        <input name="id" type="hidden" />
<#--        <div class="layui-form-item">-->
<#--            <label class="layui-form-label layui-form-required">会员号</label>-->
<#--            <div class="layui-input-block">-->
<#--                <input name="id" placeholder="请输入会员号" type="text" class="layui-input" maxlength="20"-->
<#--                       lay-verType="tips" lay-verify="required" required/>-->
<#--            </div>-->
<#--        </div>-->
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">逻辑卡号</label>
            <div class="layui-input-block">
                <input  name="logicName" placeholder="请输入逻辑卡号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">手机号</label>
            <div class="layui-input-block">
                <input name="mobile" placeholder="请输入手机号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">真实姓名</label>
            <div class="layui-input-block">
                <input name="trueName" placeholder="请输入真实姓名" type="text" class="layui-input" maxlength="20"
                />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">描述</label>
            <div class="layui-input-block">
                <input name="note" placeholder="请输入描述" type="text" class="layui-input" maxlength="20"
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
    layui.use(['layer', 'form', 'table', 'util', 'admin', 'formSelects', "xmSelect"], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;


        /**
         * ----------------------------------------渲染表格---------------------------------
         */
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/guke/offlineMember/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {fixed: 'left',field: 'id', sort: true, title: '会员号'},
                {field: 'logicName', sort: true, title: '逻辑卡号'},
                {field: 'mobile', sort: true, title: '手机号',minWidth: 150},
                {field: 'trueName', sort: true, title: '真实姓名'},
                {field: 'virtualAcc', minWidth: 120,align: 'center', sort: true, title: '虚拟账户',templet:function (d) {return d.virtualAcc+'元'} , totalRow: true},
                {field: 'giftAcc',minWidth: 120, align: 'center', sort: true, title: '赠送账户',templet:function (d) {return d.giftAcc+'元'} , totalRow: true},
                {field: 'allowanceAcc', minWidth: 120,align: 'center', sort: true, title: '补贴账户', templet:function (d) {return d.allowanceAcc+'元'} ,totalRow: true},
                {field: 'cashAcc',minWidth: 120, align: 'center', sort: true, title: '现金账户',templet:function (d) {return d.cashAcc+'元'} ,totalRow: true},
                {field: 'chargeAcc', minWidth: 120,align: 'center', sort: true, title: '充值账户',templet:function (d) {return d.chargeAcc+'元'} , totalRow: true},
                {field: 'note', sort: true, title: '备注'},
                {field: 'createDate', sort: true, title: '创建时间',minWidth: 210},
                {
                    field: 'state', title: '状态', templet: function (d) {
                        var strs = [
                            '<span style="color: #189700">启用</span>',
                            '<span style="color: #af0000">禁用</span>',
                            '<span style="color: #0e2fe5">挂失</span>'
                        ];
                        return strs[d.state];
                    }, title: '状态'
                },
                {fixed: 'right',align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 250}
            ]]
        });



        /**
         * ----------------------------------------顶部工具栏---------------------------------
         */
        // 添加
        $('#btnAddUser').click(function () {
            //添加就直接弹窗
            showEditModel();
        });
        // 添加
        $('#btnAddInfo').click(function () {
            //添加就直接弹窗
            // showEditModel();
        });
        // 搜索
        form.on('submit(formSubSearchUser)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });



        /**
         * ----------------------------------------行工具栏---------------------------------
         */
        // 工具条点击事件
        table.on('tool(tableUser)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') { // 修改
                //修改就将该用户记录传过去，显示数据
                showEditModel(data);
            } else if (layEvent === 'del') { // 删除
                doDel(data.id, data.trueName);
            }else if (layEvent === 'switch') { // 切换状态
                switchStatus(data.id);

            }
        });

        /**
         * 切换状态
         * @param id
         */
        function switchStatus(id) {
            admin.open({
                type: 1,
                title: '修改状态',
                content: $('#editStatus').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = '${ctx}/guke/offlineMember/switchStatus';
                    // 回显数据
                    form.val('editStatusFrom', null);
                    // 表单提交事件
                    form.on('submit(statusSubmit)', function (data) {
                        layer.load(2);
                        console.log(data)
                        $.post(url, {id: id, state: data.field.status}, function (res) {
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

        /**
         * 信息完善
         * @param mUser
         */
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '会员',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/guke/offlineMember/addOrUpdate?flag=1' : '${ctx}/guke/offlineMember/addOrUpdate?flag=0';
                    // 回显数据
                    form.val('modelUserForm', mUser);
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

        /**
         * 删除
         * @param id
         * @param name
         */
        function doDel(id, name) {
            layer.confirm('确定要删除“' + name + '”吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/guke/offlineMember/del', {
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