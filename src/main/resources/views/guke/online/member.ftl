<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>会员管理</title>
    <#include "../../common.ftl">
    <style>
        #tableUser + .layui-table-view .layui-table-body tbody > tr > td {
            padding: 0;
        }

        #tableUser + .layui-table-view .layui-table-body tbody > tr > td > .layui-table-cell {
            height: 70px;
            line-height: 70px;
        }

        .tb-img-circle {
            width: 60px;
            height: 60px;
            cursor: zoom-in;
            /*border-radius: 50%;*/
            /*border: 2px solid #dddddd;*/
        }
    </style>
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
                        <label class="layui-form-label w-auto">手机号:</label>
                        <div class="layui-input-inline mr0">
                            <input name="mobile" class="layui-input" type="text" placeholder="请输入手机号"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">所属机构:</label>
                        <div class="layui-input-inline mr0">
                            <input name="orgId" id="selectOrg" placeholder="请选择" class="layui-hide"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">真实姓名:</label>
                        <div class="layui-input-inline mr0">
                            <input name="trueName" class="layui-input" type="text" placeholder="请输入真实姓名"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">描述:</label>
                        <div class="layui-input-inline mr0">
                            <input name="note" class="layui-input" type="text" placeholder="请输入描述"/>
                        </div>
                    </div>
                    <div class="layui-inline ">
                        <label class="layui-form-label">状&emsp;&emsp;态：</label>
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
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchUser" lay-submit><i
                                    class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="addInfo" type="button" class="layui-btn">批量信息完善</button>
                        <button id="memberCharge" type="button" class="layui-btn">批量充值</button>
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
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label ">会员号</label>
            <div class="layui-input-block">
                <input readonly="readonly" name="id" placeholder="请输入会员号" type="text" class="layui-input" maxlength="20"
                       lay-verType="tips"/>
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
            <label class="layui-form-label layui-form-required">描述</label>
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
    layui.use(['layer', 'form', 'table', 'cascader', 'element', 'util', 'laytpl', 'admin', 'formSelects', "xmSelect", 'excel', 'upload'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        var cascader = layui.cascader;

        /**
         * ----------------------------------------渲染表格---------------------------------
         */
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/guke/onlineMember/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {field: 'id', align: 'center', title: 'ID'},
                {field: 'mobile', align: 'center', title: '手机号', minWidth: 150},
                {field: 'trueName', align: 'center', title: '真实姓名'},
                {field: 'orgName', title: '组织'},
                {
                    title: '头像', templet: function (d) {
                        var url = 'http://175.178.5.39:8088/face_recognition/' + d.weChatId;
                        return '<img data-index="' + (d.LAY_INDEX - 1) + '" src="' + url + '" class="tb-img-circle" tb-img alt=""/>';
                    }, align: 'center', width: 90, unresize: true
                },
                {field: 'weChatId', align: 'center', title: '微信号'},
                {
                    field: 'virtualAcc', minWidth: 120, align: 'center', title: '虚拟账户', templet: function (d) {
                        return d.virtualAcc + '元'
                    }, totalRow: true
                },
                {
                    field: 'giftAcc', minWidth: 120, align: 'center', title: '赠送账户', templet: function (d) {
                        return d.giftAcc + '元'
                    }, totalRow: true
                },
                {
                    field: 'allowanceAcc', minWidth: 120, align: 'center', title: '补贴账户', templet: function (d) {
                        return d.allowanceAcc + '元'
                    }, totalRow: true
                },
                {
                    field: 'cashAcc', minWidth: 120, align: 'center', title: '现金账户', templet: function (d) {
                        return d.cashAcc + '元'
                    }, totalRow: true
                },
                {
                    field: 'chargeAcc', minWidth: 120, align: 'center', title: '充值账户', templet: function (d) {
                        return d.chargeAcc + '元'
                    }, totalRow: true
                },
                {field: 'createDate', align: 'center', width:200,title: '注册时间'},
                {field: 'note', align: 'center', title: '备注'},
                {
                    field: 'state', align: 'center', title: '状态', templet: function (d) {
                        var strs = [
                            '<span style="color: #189700">启用</span>',
                            '<span style="color: #af0000">禁用</span>',
                            '<span style="color: #0e2fe5">挂失</span>'];
                        return strs[d.state];
                    }, title: '状态'
                },
                {fixed: 'right', align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 230}
            ]]
        });


        /* 点击图片放大 */
        $(document).off('click.tbImg').on('click.tbImg', '[tb-img]', function () {
            var imgList = table.cache['tableUser'].map(function (d) {
                return {
                    src: 'http://175.178.5.39:8088/face_recognition/' + d.weChatId
                }
            });
            layer.photos({photos: {data: imgList, start: $(this).data('index')}, shade: .1, closeBtn: true});
        });

        /**
         * ----------------------------------------顶部工具栏---------------------------------
         */

        //批量完善模板
        $('#addInfo').click(function () {
            layer.open({
                type: 2,
                title: "批量导入信息",
                area: ['800px', '500px'],
                content: '${ctx}/guke/onlineMember/addInfo' //打开模板页面
            });
        });

        //批量充值
        $('#memberCharge').click(function () {
            layer.open({
                type: 2,
                title: "批量充值",
                area: ['800px', '500px'],
                content: '${ctx}/guke/onlineMember/memberChargeBatch'
            });
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
            } else if (layEvent === 'switch') { // 切换状态
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
                    var url = '${ctx}/guke/onlineMember/switchStatus';
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
                    var url = mUser ? '${ctx}/guke/onlineMember/addOrUpdate?flag=1' : '${ctx}/guke/onlineMember/addOrUpdate?flag=0';
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
                $.post('${ctx}/guke/onlineMember/del', {
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


        /**
         * 渲染组织下拉菜单
         */
        $.get('${ctx}/system/org/getAllOrgList', function (orgData) {
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