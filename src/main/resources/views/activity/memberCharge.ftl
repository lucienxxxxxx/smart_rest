<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>账号充值</title>
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
                        <label class="layui-form-label w-auto">搜索：</label>
                        <div class="layui-input-inline mr0">
                            <input name="keyword" class="layui-input" type="text" placeholder="输入关键字"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchTbBas" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnExportTbBas" class="layui-btn icon-btn">
                            充值
                        </button>
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
<!-- 固定底部按钮 -->
<script type="text/html" id="dialogEditDialog1">
    <form id="dialogEditForm1" lay-filter="dialogEditForm1" class="layui-form model-form no-padding">
        <input name="ptId" type="hidden"/>
        <div class="model-form-body" style="height: 310px;">
            <div class="layui-form-item">
                <label class="layui-form-label ">虚拟账户:</label>
                <div class="layui-input-block">
                    <input name="virtualAcc" placeholder="请输入虚拟账户" class="layui-input" value="0"
                    />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">赠送账号:</label>
                <div class="layui-input-block">
                    <input name="giftAcc" placeholder="请输入赠送账号" class="layui-input" value="0"
                    />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">补贴账号:</label>
                <div class="layui-input-block">
                    <input name="allowanceAcc" placeholder="请输入补贴账号" class="layui-input" value="0"
                    />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">现金账户:</label>
                <div class="layui-input-block">
                    <input name="cashAcc" placeholder="请输入现金账户" class="layui-input" value="0"
                    />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">充值账号:</label>
                <div class="layui-input-block">
                    <input name="chargeAcc" placeholder="请输入充值账号" class="layui-input" value="0"
                    />
                </div>
            </div>
        </div>
        <div class="layui-form-item text-right model-form-footer">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="dialogEditSubmit1" lay-submit>保存</button>
        </div>
    </form>
</script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'dropdown', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var admin = layui.admin;
        var table = layui.table;
        var util = layui.util;
        var dropdown = layui.dropdown;

        // 渲染表格
        var insTb = table.render({
            elem: '#tableTbBas',
            url: '${ctx}/activity/charge/list',
            page: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'checkbox'},
                {field: 'trueName', align: 'center', sort: true, title: '真实姓名'},
                {field: 'mobile', align: 'center', sort: true, title: '手机号码', minWidth: 150},
                {
                    field: 'memberType', align: 'center', sort: true, title: '类别', templet: d => {
                        if (d.memberType == 0) {
                            return '线上会员'
                        } else if (d.memberType == 1) {
                            return '线下会员'
                        } else if (d.memberType == 2) {
                            return '员工'
                        }
                    }
                },
                {
                    field: 'virtualAcc',
                    minWidth: 120,
                    align: 'center',
                    sort: true,
                    title: '虚拟账户',
                    templet: function (d) {
                        return d.virtualAcc + '元'
                    }
                },
                {
                    field: 'giftAcc', minWidth: 120, align: 'center', sort: true, title: '赠送账户', templet: function (d) {
                        return d.giftAcc + '元'
                    }
                },
                {
                    field: 'allowanceAcc',
                    minWidth: 120,
                    align: 'center',
                    sort: true,
                    title: '补贴账户',
                    templet: function (d) {
                        return d.allowanceAcc + '元'
                    }
                },
                {
                    field: 'cashAcc', minWidth: 120, align: 'center', sort: true, title: '现金账户', templet: function (d) {
                        return d.cashAcc + '元'
                    }
                },
                {
                    field: 'chargeAcc',
                    minWidth: 120,
                    align: 'center',
                    sort: true,
                    title: '充值账户',
                    templet: function (d) {
                        return d.chargeAcc + '元'
                    }
                },
                {
                    field: 'total', align: 'center', sort: true, title: '账号总额', templet: function (d) {
                        if (d.total == null) {
                            d.total = 0
                        }
                        var str = '<span style="font-weight: bold">' + d.total + '元</span>'
                        return str
                    }, totalRow: true
                },
                {
                    field: 'state', align: 'center', sort: true, templet: function (d) {
                        var strs = [
                            '<span class="text-success">启用</span>',
                            '<span class="text-danger">禁用</span>'
                        ];
                        return strs[d.state];
                    }, title: '状态'
                },
                // {align: 'center', toolbar: '#tableBarTbBas', title: '操作', minWidth: 200}
            ]]
        });

        //监听工具条
        table.on('tool(tableTbBas)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值

            if (layEvent === 'edit') { // 查看
                layer.msg('点击了修改');
            } else if (layEvent === 'del') { // 删除
                layer.msg('点击了删除');
            } else if (layEvent === 'edit2') { // 修改2
                layer.msg('点击了下拉菜单修改');
            } else if (layEvent === 'del2') { // 删除2
                layer.msg('点击了下拉菜单删除');
            } else if (layEvent === 'lock2') { // 打印2
                layer.msg('点击了下拉菜单锁定');
            }
            dropdown.hideAll();
        });

        // 充值
        $('#btnExportTbBas').click(function () {
            var checkRows = table.checkStatus('tableTbBas');
            if (checkRows.data.length == 0) {
                layer.msg('请选择要充值的用户', {icon: 2});
            } else {
                const members = checkRows.data
                const ids = []
                for (let i = 0; i < members.length; i++) {
                    const id = members[i].id
                    ids.push(id)
                }
                admin.open({
                    type: 1,
                    title: '充值',
                    fixed: true,
                    offset: 'auto',
                    content: $('#dialogEditDialog1').html(),
                    success: function (layero, dIndex) {
                        $(layero).children('.layui-layer-content').css('overflow', 'visible');
                        // 表单提交事件
                        form.on('submit(dialogEditSubmit1)', function (data) {
                            layer.load(2);
                            const d ={
                                ids:ids.toString(),
                                virtualAcc:data.field.virtualAcc,
                                giftAcc:data.field.giftAcc,
                                allowanceAcc:data.field.allowanceAcc,
                                cashAcc:data.field.cashAcc,
                                chargeAcc:data.field.chargeAcc,
                            }
                            $.post("${ctx}/activity/charge/charge",d,function (res) {

                                layer.closeAll('loading');
                                if (res.code == 200) {
                                    layer.close(dIndex);
                                    layer.msg(res.msg, {icon: 1});
                                    insTb.reload({}, 'data');
                                } else {
                                    layer.msg(res.msg, {icon: 2});
                                }
                            })

                            return false;
                        });

                    }
                });


            }
        });

        // 搜索按钮点击事件
        form.on('submit(formSubSearchTbBas)', function (data) {
            insTb.reload({where: data.field}, 'data');
        });

    });
</script>
</body>

</html>