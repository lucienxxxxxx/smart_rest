<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>权限管理</title>
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

<table id="munu-table" class="layui-table" lay-filter="munu-table"></table>
<!-- 操作列 -->
<script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加子项</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>



<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <a class="layui-btn layui-btn-normal addNews_btn" lay-event="expand">
            <i class="layui-icon">&#xe608;</i>
            全部展开
        </a>
        <a class="layui-btn layui-btn-normal addNews_btn" lay-event="fold">
            <i class="layui-icon">&#xe608;</i>
            全部折叠
        </a>
        <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
            <i class="layui-icon">&#xe608;</i>
            添加目录
        </a>
    </div>
</script>


<!-- 表单弹窗 -->
<script type="text/html" id="modelAuth">
    <form id="modelAuthForm" lay-filter="modelAuthForm" class="layui-form model-form">
        <input name="authorityId" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label">上级菜单</label>
            <div class="layui-input-block">
                <select name="parentId" lay-search>
                    <option value="">请选择上级菜单</option>
                    <option value="1">系统管理</option>
                    <option value="2">用户管理</option>
                    <option value="5">角色管理</option>
                    <option value="8">权限管理</option>
                    <option value="11">登录日志</option>
                    <option value="12">系统监控</option>
                    <option value="13">Druid监控</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">权限名称</label>
            <div class="layui-input-block">
                <input name="authorityName" placeholder="请输入权限名称" type="text" class="layui-input" maxlength="50"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">权限类型</label>
            <div class="layui-input-block">
                <input name="isMenu" type="radio" value="0" title="菜单" checked/>
                <input name="isMenu" type="radio" value="1" title="按钮"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单url</label>
            <div class="layui-input-block">
                <input name="menuUrl" placeholder="请输入菜单url" type="text" class="layui-input"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权限标识</label>
            <div class="layui-input-block">
                <input name="authority" placeholder="请输入权限标识" type="text" class="layui-input"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单图标</label>
            <div class="layui-input-block">
                <input name="menuIcon" placeholder="请输入菜单图标" type="text" class="layui-input"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">排序号</label>
            <div class="layui-input-block">
                <input name="orderNumber" placeholder="请输入排序号" type="number" class="layui-input" min="0" max="1000"
                       lay-verType="tips" lay-verify="required|number" required/>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitAuth" lay-submit>保存</button>
        </div>
    </form>
</script>
<!-- js部分 -->

<script>
    layui.use(['layer', 'form', 'admin', 'util','treetable'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var admin = layui.admin;
        var table = layui.table;
        var treeTable = layui.treetable;
        var util = layui.util;

        // 渲染表格
        treeTable.render({
            treeColIndex: 1,
            treeSpid: -1,
            treeIdName: 'id',
            treePidName: 'parentId',
            elem: '#munu-table',
            url: '${ctx}/system/module/list',
            toolbar: "#toolbarDemo",
            treeDefaultClose:true,
            page: true,
            cols: [[
                {type: 'numbers'},
                {field: 'moduleName', minWidth: 100, title: '菜单名称'},
                {field: 'optValue', title: '权限码'},
                {field: 'url', title: '菜单url'},
                {field: 'createDate', title: '创建时间'},
                {field: 'updateDate', title: '更新时间'},
                {
                    field: 'grade', width: 80, align: 'center', templet: function (d) {
                        if (d.grade == 0) {
                            return '<span class="layui-badge layui-bg-blue">目录</span>';
                        }
                        if(d.grade==1){
                            return '<span class="layui-badge-rim">菜单</span>';
                        }
                        if (d.grade == 2) {
                            return '<span class="layui-badge layui-bg-gray">按钮</span>';
                        }
                    }, title: '类型'
                },
                {templet: '#auth-state', minWidth: 180, align: 'center', title: '操作'}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });


        /**
         * 监听头部工具栏
         */
        table.on('toolbar(munu-table)', function (data) {
            // 判断lay-event属性
            if (data.event == "expand") {
                // 全部展开
                treeTable.expandAll("#munu-table");

            } else if (data.event == "fold") {
                // 全部折叠
                treeTable.foldAll("#munu-table");

            } else if (data.event == "add") {
                // 添加目录 层级=0 父菜单=-1
                openAddModuleDialog(0, -1)
            }
        });

        /**
         * 监听行工具栏
         */
        table.on('tool(munu-table)',function (data) {
            // 判断lay-event属性
            if (data.event == "add") {
                // 添加子项
                // 判断当前的层级（如果是三级菜单，就不能添加）
                if (data.data.grade == 2) {
                    layer.msg("暂不支持添加四级菜单！",{icon:5});
                    return;
                }
                // 一级|二级菜单   grade=当前层级+1，parentId=当前资源的ID
                openAddModuleDialog(data.data.grade+1, data.data.id);

            } else if (data.event == "edit") {
                // 修改资源
                openUpdateModuleDialog(data.data.id);

            } else if (data.event == "del") {
                // 删除资源
                deleteModule(data.data.id);
            }
        });


        /**
         * 打开添加资源的对话框
         * @param grade 层级
         * @param parentId 父菜单ID
         */
        function openAddModuleDialog(grade, parentId) {
            var title = "<h3>资源管理 - 添加资源</h3>";
            var url = "${ctx}/system/module/toAddModulePage?grade=" + grade + "&parentId=" + parentId;

            layui.layer.open({
                type:2,
                title:title,
                content:url,
                area:["700px","450px"],
                maxmin:true
            });
        }

        /**
         * 打开修改资源的对话框
         * @param id
         */
        function openUpdateModuleDialog(id) {
            var title = "<h3>资源管理 - 修改资源</h3>";
            var url = "${ctx}/system/module/toUpdateModulePage?id=" + id;

            layui.layer.open({
                type:2,
                title:title,
                content:url,
                area:["700px","450px"],
                maxmin:true
            });
        }

        /**
         * 删除资源
         * @param id
         */
        function deleteModule(id) {
            // 弹出确认框询问用户是否确认删除
            layer.confirm('您确认删除该记录吗？',{icon:3, title:"资源管理"}, function (data) {
                // 如果确认删除，则发送ajax请求
                $.post("${ctx}/system/module/delete",{id:id},function (result) {
                    // 判断是否成功
                    if (result.code == 200) {
                        layer.msg("删除成功！",{icon:6});
                        // 刷新页面
                        window.location.reload();
                    } else {
                        layer.msg(result.msg,{icon:5});
                    }
                });
            });
        }



    });
</script>
</body>
</html>