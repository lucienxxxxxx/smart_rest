<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>托盘管理</title>
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
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">ID:</label>
                        <div class="layui-input-inline mr0">
                            <input name="id" class="layui-input" type="text" placeholder="请输入ID"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">逻辑号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="logicName" class="layui-input" type="text" placeholder="请输入逻辑号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">描述:</label>
                        <div class="layui-input-inline mr0">
                            <input name="descriptions" class="layui-input" type="text" placeholder="请输入描述"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="stateId">
                                <option value="">所有</option>
                                <option value="0">停用</option>
                                <option value="1">启用</option>
                            </select>
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
           lay-text="启用|停用" {{d.stateId==0?'checked':''}} style="display: none;"/>
    <p style="display: none;">{{d.stateId==0?'启用':'停用'}}</p>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">
        <#-- 用户ID -->
        <input name="id" type="hidden" />
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
    layui.use(['layer', 'form', 'table', 'util', 'admin', 'formSelects', "xmSelect"], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;

        // 渲染表格
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/dianpu/tuopan/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {field: 'id', sort: true, title: 'ID'},
                {field: 'logicName', sort: true, title: '逻辑号'},
                {field: 'descriptions', sort: true, title: '描述'},
                {field: 'stateId', title: '状态', templet:'#tuopanTbState', sort: true, width: 100},
                {align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 200}
            ]]
        });
        /* 修改状态 */
        form.on('switch(tuopanTbStateCk)', function (obj) {
            console.log(obj)
            var loadIndex = layer.load(2);
            $.post('${ctx}/dianpu/tuopan/switchStatus', {
                tuopanId: obj.elem.value,
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
                doDel(data.id, data.logicName);
            }
        });


        // 显示表单弹窗
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '托盘',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/dianpu/tuopan/addOrUpdate?flag=1' : '${ctx}/dianpu/tuopan/addOrUpdate?flag=0';
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

        // 删除
        function doDel(id, name) {
            layer.confirm('确定要删除“' + name + '”吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/dianpu/tuopan/del', {
                    tuopanId: id
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