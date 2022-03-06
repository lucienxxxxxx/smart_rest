<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理卡管理</title>
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
                    <div class="layui-inline">
                        <label class="layui-form-label">逻辑卡号：</label>
                        <div class="layui-input-inline">
                            <input name="logicName" class="layui-input" type="text" placeholder="输入逻辑卡号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">备注：</label>
                        <div class="layui-input-inline">
                            <input name="descriptions" class="layui-input" type="text" placeholder="输入备注"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
                        <div class="layui-input-inline">
                            <select name="stateId">
                                <option value="">所有</option>
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchTbBas" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAddUser" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                    </div>
                </div>
            </div>

            <table id="tableTbBas" lay-filter="tableTbBas"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBarTbBas">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">
        <#-- 用户ID -->
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">逻辑卡号</label>
            <div class="layui-input-block">
                <input name="logicName" placeholder="请输入逻辑卡号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">备注</label>
            <div class="layui-input-block">
                <input name="descriptions" placeholder="请输入备注" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips"/>
            </div>
        </div>

        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitUser" lay-submit>保存</button>
        </div>
    </form>
</script>

<!-- 表格状态列 -->
<script type="text/html" id="terminalTbState">
    <input type="checkbox" lay-filter="terminalTbStateCk" value="{{d.id}}" lay-skin="switch"
           lay-text="启用|停用" {{d.stateId==0?'':'checked'}} style="display: none;"/>
    <p style="display: none;">{{d.stateId==0?'启用':'停用'}}</p>
</script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'dropdown', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        // 渲染表格
        var insTb = table.render({
            elem: '#tableTbBas',
            url: '${ctx}/dianpu/rfid-user/list',
            page: true,
            cellMinWidth: 100,
            cols: [[
                {field: 'logicName', align: 'center', sort: true, title: '逻辑卡号'},
                {field: 'descriptions', align: 'center', sort: true, title: '备注'},
                {field: 'stateId', title: '状态', templet: '#terminalTbState', sort: true, width: 100},
                {align: 'center', toolbar: '#tableBarTbBas', title: '操作', minWidth: 220}
            ]]
        });



        //监听工具条
        table.on('tool(tableTbBas)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值

            if (layEvent === 'edit') { // 修改
                showEditModel(data)
            } else if (layEvent === 'del') { // 删除
                doDel(data.id, data.logicName);
            }
        });

        // 添加
        $('#btnAddUser').click(function () {
            //添加就直接弹窗
            showEditModel();
        });

        // 显示表单弹窗
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '终端',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/dianpu/rfid-user/addOrUpdate?flag=1' : '${ctx}/dianpu/rfid-user/addOrUpdate?flag=0';
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
                $.post('${ctx}/dianpu/rfid-user/del', {
                    cardId: id
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

        /* 修改状态 */
        form.on('switch(terminalTbStateCk)', function (obj) {
            var loadIndex = layer.load(2);
            $.post('${ctx}/dianpu/rfid-user/switchStatus', {
                cardId: obj.elem.value,
                state: obj.elem.checked ? 1 : 0
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
        // 搜索按钮点击事件
        form.on('submit(formSubSearchTbBas)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });


    });
</script>
</body>

</html>