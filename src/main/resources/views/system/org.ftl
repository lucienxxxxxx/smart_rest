<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>组织管理</title>
    <#include "../common.ftl">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
        <div class="layui-card-body">
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">搜索：</label>
                        <div class="layui-input-inline mr0">
                            <input id="edtSearchAuth" class="layui-input" placeholder="输入关键字"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button id="btnSearchAuth" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAddAuth" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                    </div>
                </div>
            </div>

            <table id="tableAuth"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBarAuth">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加子项</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="modelAuth">
    <form id="modelAuthForm" lay-filter="modelAuthForm" class="layui-form model-form">
        <input name="id" type="hidden"/>
        <input name="parentId" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">组织名称</label>
            <div class="layui-input-block">
                <input name="orgName" placeholder="请输入组织名称" type="text" class="layui-input" maxlength="50"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">备注</label>
            <div class="layui-input-block">
                <input name="note" placeholder="请输入组织名称" type="text" class="layui-input" maxlength="50"
                      />
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitAuth" lay-submit>保存</button>
        </div>
    </form>
</script>

<script>
    layui.use(['layer', 'form', 'admin', 'treeTable', 'util'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var admin = layui.admin;
        var treeTable = layui.treeTable;
        var util = layui.util;

        // 渲染表格
        var insTb = treeTable.render({
            elem: '#tableAuth',
            tree: {
                iconIndex: 1,  // 折叠图标显示在第几列
                idName: 'id',  // 自定义id字段的名称
                pidName: 'parentId',  // 自定义标识是否还有子节点的字段名称
                isPidData: true  // 是否是pid形式数据
            },
            cellMinWidth: 100,
            cols: [
                {type: 'numbers'},
                {field: 'orgName', title: '组织名称', width: 260},
                {field: 'note', title: '备注',minWidth: 260},
                {field: 'createDate', title: '创建时间', align: 'center', width: 200},
                {field: 'updateDate', title: '更新时间', align: 'center', width: 200},
                {templet: '#tableBarAuth', title: '操作', align: 'center', minWidth: 150}
            ],
            reqData: function (data, callback) {
                $.get('${ctx}/system/org/queryAllOrgByParams', function (res) {
                    callback(res.data);
                });
            }
        });

        // 搜索
        $('#btnSearchAuth').click(function () {
            var keyword = $('#edtSearchAuth').val();
            if (keyword) {
                insTb.filterData(keyword);
            } else {
                insTb.clearFilter();
            }
        });

        // 添加按钮点击事件
        $('#btnAddAuth').click(function () {
            showEditModel();
        });

        // 工具条点击事件
        treeTable.on('tool(tableAuth)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') { // 修改
                showEditModel(data,0);
            } else if (layEvent === 'del') { // 删除
                doDel(data.id, data.orgName);
            } else if (layEvent === 'add') { // 添加子项
                showEditModel(data,1);
            }
        });

        // 显示表单弹窗
        function showEditModel(mAuth,flag) {
            var title,url;
            var pid='-1'
            if ( flag==1){
                //添加子项
                title='添加子项'
                url='${ctx}/system/org/add'
                pid=mAuth.id
            }else if( flag==0){
                //修改
                title='修改组织'
                url='${ctx}/system/org/update'
            }else{
                //添加主目录
                title='添加组织'
                url='${ctx}/system/org/add'
            }
            admin.open({
                type: 1,
                title: title,
                content: $('#modelAuth').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    if (flag==0){
                        form.val('modelAuthForm', mAuth);  // 回显数据
                    }
                    // 表单提交事件
                    form.on('submit(modelSubmitAuth)', function (data) {
                        console.log(data.field)
                        console.log(flag)
                        if (data.field.parentId == '') {
                            data.field.parentId = '-1';
                        }
                        layer.load(2);
                        if (flag==1){
                            data.field.parentId = pid
                        }

                        $.post(url, data.field, function (res) {
                            layer.closeAll('loading');
                            if (res.code == 200) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {icon: 1});
                                insTb.refresh();
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
        function doDel(id, orgName) {
            layer.confirm('确定要删除“' + orgName + '”吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (index) {
                layer.close(index);
                layer.load(2);
                $.post('${ctx}/system/org/del', {
                    orgId: id
                }, function (res) {
                    layer.closeAll('loading');
                    if (res.code == 200) {
                        layer.msg(res.msg, {icon: 1});
                        insTb.refresh();
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