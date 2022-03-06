<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>菜品管理</title>
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
                        <label class="layui-form-label w-auto">名称</label>
                        <div class="layui-input-inline mr0">
                            <input name="name" class="layui-input" type="text" placeholder="请输入名称"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">档口ID</label>
                        <div class="layui-input-inline mr0">
                            <input name="dangkouId" class="layui-input" type="text" placeholder="请输入档口ID"/>
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label w-auto">标签</label>
                        <div class="layui-input-inline mr0">
                            <select name="tagId" lay-verType="tips">
                                <option value="">请选择标签</option>
                                <#list tagList as tag>
                                    <option value="${tag.id}">${tag.tagName}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="layui-inline" style="padding-right: 110px;">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearchUser" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAddUser" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                        <button id="btnTag" class="layui-btn icon-btn">标签管理</button>
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

<!-- 表单弹窗 -->
<script type="text/html" id="modelUser">
    <form id="modelUserForm" lay-filter="modelUserForm" class="layui-form model-form layui-row">
        <#-- 用户ID -->
        <input name="id" type="hidden"/>
        <div class="layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label layui-form-required">名称</label>
                <div class="layui-input-block">
                    <input name="name" placeholder="请输入名称" type="text" class="layui-input" maxlength="20"
                           lay-verType="tips" lay-verify="required" required/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label layui-form-required">价格</label>
                <div class="layui-input-block">
                    <input name="price" placeholder="请输入价格" type="text" class="layui-input" maxlength="20"
                           lay-verType="tips" lay-verify="required" required/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">折扣</label>
                <div class="layui-input-block">
                    <input name="discount" placeholder="请输入折扣(默认为1)" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">计价方式</label>
                <div class="layui-input-block">
                    <input name="priceMethod" placeholder="0表示克，非0表示多少克一份" type="text" class="layui-input"
                           maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label layui-form-required">档口ID</label>
                <div class="layui-input-block">
                    <input name="dangkouId" placeholder="请输入档口ID" type="text" class="layui-input" maxlength="20"
                           lay-verify="required" required/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label layui-form-required">标签</label>
                <div class="layui-input-block">
                    <select name="tagId" lay-verType="tips" lay-verify="required" required>
                        <option value="">请选择标签</option>
                        <#list tagList as tag>
                            <option value="${tag.id}">${tag.tagName}</option>
                        </#list>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label ">热量</label>
                <div class="layui-input-block">
                    <input name="heat" placeholder="请输入热量" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">蛋白质</label>
                <div class="layui-input-block">
                    <input name="protein" placeholder="请输入蛋白质" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label d">脂肪</label>
                <div class="layui-input-block">
                    <input name="fat" placeholder="请输入脂肪" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">纤维素</label>
                <div class="layui-input-block">
                    <input name="cellulose" placeholder="请输入纤维素" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">碳水化合物</label>
                <div class="layui-input-block">
                    <input name="carbohydrate" placeholder="请输入碳水化合物" type="text" class="layui-input"
                           maxlength="20"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label ">描述</label>
                <div class="layui-input-block">
                    <input name="descriptions" placeholder="请输入描述" type="text" class="layui-input" maxlength="20"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closePageDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmitUser" lay-submit>保存</button>
        </div>

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
        var formSelects = layui.formSelects;
        var xmSelect = layui.xmSelect;

        // 渲染表格
        var insTb = table.render({
            elem: '#tableUser',
            url: '${ctx}/dianpu/food/list',
            page: true,
            toolbar: true,
            cellMinWidth: 100,
            cols: [[
                {fixed: 'left', field: 'name', sort: true, title: '名称'},
                {
                    field: 'price', sort: true, title: '价格', templet: function (d) {
                        return d.price + '元';
                    }
                },
                {field: 'discount', sort: true, title: '折扣'},
                {
                    field: 'priceMethod', sort: true, title: '计价方式', templet: function (d) {
                        if (d.priceMethod == 0) {
                            return '克';
                        } else {
                            return d.priceMethod + '克/份'
                        }

                    }
                },
                {
                    field: 'tagName', sort: true, title: '标签', templet: function (d) {
                        return '<span class="layui-badge-rim">' + d.tagName + '</span>';
                    }
                },
                {field: 'heat', sort: true, title: '热量'},
                {field: 'protein', sort: true, title: '蛋白质'},
                {field: 'fat', sort: true, title: '脂肪'},
                {field: 'cellulose', sort: true, title: '纤维素'},
                {field: 'carbohydrate', sort: true, title: '碳水化合物'},
                {field: 'descriptions', sort: true, title: '描述'},
                {field: 'dangkouId', sort: true, title: '档口ID'},
                {fixed: 'right', align: 'center', toolbar: '#tableBarUser', title: '操作', minWidth: 150}
            ]]
        });

        // 添加
        $('#btnAddUser').click(function () {
            //添加就直接弹窗
            showEditModel();
        });


        // 标签管理
        $('#btnTag').click(function () {
            //添加就直接弹窗
            showTagModel();
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
                doDel(data.id, data.name);
            }
        });

        function showTagModel() {
            layui.layer.open({
                type: 2,
                title: '标签管理',
                content: '${ctx}/dianpu/tag/index',
                area: ["700px", "430px"],
                maxmin: true
            });
        }

        // 显示表单弹窗
        function showEditModel(mUser) {
            admin.open({
                type: 1,
                title: (mUser ? '修改' : '添加') + '菜品',
                area: '700px',
                content: $('#modelUser').html(),
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    var url = mUser ? '${ctx}/dianpu/food/addOrUpdate?flag=1' : '${ctx}/dianpu/food/addOrUpdate?flag=0';
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
                $.post('${ctx}/dianpu/food/del', {
                    foodId: id
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