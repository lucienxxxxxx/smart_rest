<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>餐厅设置</title>
    <#include "../common.ftl">
    <#setting time_format="HH:mm:ss"/>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .layui-form-item {
            margin-bottom: 25px;
        }

        label .layui-icon {
            font-size: 14px;
        }
    </style>
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

            <form class="layui-form" style="max-width: 700px;margin: 40px auto;">
                <input  type="hidden" name="id" value="${rest.id!''}">
                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-required">餐厅名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="restaurantName" placeholder="设置餐厅名称" class="layui-input"
                               value="${rest.restaurantName!''}" lay-verType="tips" lay-verify="required" required/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">餐厅地址:</label>
                    <div class="layui-input-block">
                        <input type="text" name="address" placeholder="设置餐厅地址" class="layui-input"
                               value="${rest.address!''}" lay-verType="tips"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">绑定时间:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bangdingTime" placeholder="设置绑定时间" class="layui-input"
                               value="${rest.bangdingTime!''}" lay-verType="tips"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">延长时间:</label>
                    <div class="layui-input-block">
                        <input type="text" name="delayedBangdingTime" placeholder="设置延长时间" class="layui-input"
                               value="${rest.delayedBangdingTime!''}" lay-verType="tips"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">绑定最小限额:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bangdingMinAccount" placeholder="设置绑定最小限额（默认30rmb）" class="layui-input"
                               value="${rest.bangdingMinAccount!''}" lay-verType="tips"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">多次绑定限额:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bangdingMultiAccount" placeholder="设置多次绑定限额（默认30rmb）"
                               class="layui-input"
                               value="${rest.bangdingMultiAccount!''}" lay-verType="tips"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label ">最小账号限额:</label>
                    <div class="layui-input-block">
                        <input type="text" name="minAccountQuota" placeholder="设置最小账号限额（默认0）" class="layui-input"
                               value="${rest.minAccountQuota!''}" lay-verType="tips"/>
                    </div>
                </div>
                <#--                时间设置-->
                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-required">早餐结束时间:</label>
                    <div class="layui-input-block">
                        <input id="edtBreakfastEnd" type="text" name="breakfastEnd" placeholder="请选择早餐结束时间"
                               value="${rest.breakfastEnd!''}" class="layui-input date-icon" autocomplete="off"
                               lay-verType="tips" lay-verify="required"
                               required/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-required">午餐结束时间:</label>
                    <div class="layui-input-block">
                        <input id="edtLunchEnd" type="text" name="lunchEnd" placeholder="请选择午餐结束时间"
                               value="${rest.lunchEnd!''}" class="layui-input date-icon" autocomplete="off"
                               lay-verType="tips" lay-verify="required"
                               required/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-required">晚餐结束时间:</label>
                    <div class="layui-input-block">
                        <input id="edtDinnerEnd" type="text" name="dinnerEnd" placeholder="请选择晚餐结束时间"
                               value="${rest.dinnerEnd!''}" class="layui-input date-icon" autocomplete="off"
                               lay-verType="tips" lay-verify="required"
                               required/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">需求设置:</label>
                    <div class="layui-input-inline" style="display: block;width: auto;float: none;">
                        <input type="radio" name="tpUpdate" value="0" title="不需要" checked>
                        <input type="radio" name="tpUpdate" value="1" title="需要">
                    </div>
                    <div class="layui-form-mid layui-word-aux">是否需要给本餐厅的取餐机和绑定机发送托盘逻辑卡号和编号</div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-filter="formSubmitBas" lay-submit>&emsp;设置&emsp;</button>
                        <button type="reset" class="layui-btn layui-btn-primary">&emsp;重置&emsp;</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
<script>
    layui.use(['layer', 'form', 'laydate'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;

        // 渲染laydate
        laydate.render({
            elem: '#edtBreakfastEnd',
            type: 'time',
            format: 'HH:mm:ss'
        });
        // 渲染laydate
        laydate.render({
            elem: '#edtLunchEnd',
            type: 'time',
            format: 'HH:mm:ss'

        });
        // 渲染laydate
        laydate.render({
            elem: '#edtDinnerEnd',
            type: 'time',
            format: 'HH:mm:ss'

        });

        // 监听表单提交
        form.on('submit(formSubmitBas)', function (data) {

            layer.confirm('确定要修改吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post('${ctx}/system/restaurantSetting/update', data.field, function (res) {
                    layer.closeAll('loading');
                    console.log(res)
                    if (res.code == 200) {
                        layer.msg(res.msg, {icon: 1});

                    } else {
                        layer.msg(res.msg, {icon: 2});
                    }
                }, 'json');
            });
            return false;
        });


    });
</script>
</body>
</html>