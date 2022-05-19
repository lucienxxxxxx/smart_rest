<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>用户管理</title>
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
                        <label class="layui-form-label w-auto">用户名:</label>
                        <div class="layui-input-inline mr0">
                            <input name="userName" class="layui-input" type="text" placeholder="请输入用户名"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">真实姓名:</label>
                        <div class="layui-input-inline mr0">
                            <input name="trueName" class="layui-input" type="text" placeholder="请输入真实姓名"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">电话号码:</label>
                        <div class="layui-input-inline mr0">
                            <input name="phone" class="layui-input" type="text" placeholder="请输入电话号码"/>
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
    <a class="layui-btn layui-btn-xs" lay-event="reset">重置密码</a>
</script>

<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form">
        <#-- 用户ID -->
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">用户名</label>
            <div class="layui-input-block">
                <input name="userName" placeholder="请输入账号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">真实姓名</label>
            <div class="layui-input-block">
                <input name="trueName" placeholder="请输入真实姓名" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">邮箱</label>
            <div class="layui-input-block">
                <input name="email" placeholder="请输入邮箱" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">电话号码</label>
            <div class="layui-input-block">
                <input name="phone" placeholder="请输入电话号码" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">角色</label>
            <div class="layui-input-block">
                <select name="roleIds" xm-select="selectId">
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">所属餐厅:</label>
            <div class="layui-input-block">
                <div id="resId"></div>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitUser" lay-submit>保存</button>
        </div>
    </form>
</script>
<!--------------------------------------------------- 表格状态列 ------------------------------------------------>
<script type="text/html" id="tuopanTbState">
    <input type="checkbox" lay-filter="tuopanTbStateCk" value="{{d.id}}" lay-skin="switch"
           lay-text="启用|停用" {{d.state==0?'checked':''}} style="display: none;"/>
    <p style="display: none;">{{d.state==0?'启用':'停用'}}</p>
</script>
<!-- js部分 -->
<script>
    layui.use(['layer', 'form', 'table', 'util', 'admin', 'formSelects', "xmSelect",], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        var formSelects = layui.formSelects;
        var xmSelect = layui.xmSelect;

        // 渲染表格
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/system/user/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'numbers'},
                {field: 'userName', align: 'center', title: '用户名'},
                {field: 'trueName', align: 'center', title: '真实姓名'},
                {
                    field: 'roles', align: 'center', title: '角色', width: 150, templet: function (d) {
                        let roles = d.roles.split(',');
                        let result = '';
                        roles.forEach(function (role) {
                            result = result + '<span class="layui-badge layui-badge-gray">' + role + '</span>' + '&nbsp;&nbsp;';
                        })
                        return result;
                    }
                },
                {field: 'restaurantName', align: 'center', title: '所属餐厅'},
                {field: 'phone', align: 'center', width: '140', title: '电话号码'},
                {field: 'email', align: 'center', title: '邮箱'},
                {field: 'descriptions', align: 'center', title: '描述'},
                {field: 'createDate', align: 'center', width: '180', sort: true, title: '创建时间'},
                {field: 'loginDate', align: 'center', width: '180', sort: true, title: '最后登录时间'},
                {field: 'state', title: '状态', templet: '#tuopanTbState', sort: true, width: 100},
                {fixed: 'right', align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 200}
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

        // 工具条点击事件
        table.on('tool(tableUser)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') { // 修改
                //修改就将该用户记录传过去，显示数据
                showEditModel(data);
            } else if (layEvent === 'del') { // 删除
                doDel(data.id, data.userName);
            } else if (layEvent === 'reset') { // 重置密码
                resetPsw(data.id, data.userName);
            }
        });

        // 显示表单弹窗
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '用户',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/system/user/addOrUpdate?flag=1' : '${ctx}/system/user/addOrUpdate?flag=0';
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

                    //加载餐厅下拉框
                    $.get('${ctx}/system/restaurant/getAllRestaurant',function (res) {
                        var insRoleSel = xmSelect.render({
                            el: '#resId',
                            name: 'resId',
                            radio: true,
                            clickClose: true,
                            layVerify: 'required',
                            layVerType: 'tips',
                            data:res
                        });
                        // 回显选中餐厅
                        if (mUser && mUser.restaurantName) {
                            res.forEach(function(res){
                                if (res.name ==mUser.restaurantName){
                                    insRoleSel.setValue([res.value]);
                                }
                            })

                        }
                    })


                    //加载角色下拉框
                    if (mUser) {
                        var userId = mUser.id
                        //加载下拉框
                        formSelects.config("selectId", {
                            type: "post", // 请求方式
                            searchUrl: "${ctx}/system/role/queryAllRoles?userId=" + userId, // 请求地址
                            keyName: 'roleName',  // 下拉框中的文本内容，要与返回的数据中对应key一致
                            keyVal: 'id'
                        }, true);
                    } else {
                        //加载下拉框
                        formSelects.config("selectId", {
                            type: "post", // 请求方式
                            searchUrl: "${ctx}/system/role/queryAllRoles", // 请求地址
                            keyName: 'roleName',  // 下拉框中的文本内容，要与返回的数据中对应key一致
                            keyVal: 'id'
                        }, true);
                    }


                }
            });
        }

        // 删除
        function doDel(userId, userName) {
            layer.confirm('确定要删除“' + userName + '”吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/system/user/del', {
                    userId: userId
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
        /* 修改用户状态 */
        form.on('switch(tuopanTbStateCk)', function (obj) {
            var loadIndex = layer.load(2);
            $.post('${ctx}/system/user/switchStatus', {
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

        // 重置密码
        function resetPsw(userId, userName) {
            layer.confirm('确定要重置“' + userName + '”的登录密码吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/system/user/resetPwd', {
                    userId: userId
                }, function (res) {
                    layer.closeAll('loading');
                    if (res.code == 200) {
                        layer.msg(res.msg, {icon: 1});
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