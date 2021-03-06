<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;margin-top: 50px">
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">菜单名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   lay-verify="required" name="moduleName" id="moduleName"   placeholder="请输入菜单名">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">菜单样式</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   name="moduleStyle" id="moduleStyle" placeholder="请输入菜单样式">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                    name="orders" id="orders" placeholder="请输入排序值">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">权限码</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   lay-verify="required" name="optValue" id="optValue" placeholder="请输入菜单权限码">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">菜单级别</label>
        <div class="layui-input-block">
            <#if grade??>
                <select name="grade" >
                    <option value="0" <#if grade==0>selected="selected"</#if> >一级菜单</option>
                    <option value="1" <#if grade==1>selected="selected"</#if>>二级菜单</option>
                    <option value="2" <#if grade==2>selected="selected"</#if>>三级菜单</option>
                </select>
            </#if>
        </div>
    </div>

    <#if grade==1&&grade==2>
        <div class="layui-form-item layui-row layui-col-xs12">
            <label class="layui-form-label">菜单url</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input userName"
                       lay-verify="required" name="url" id="url" placeholder="请输入菜单url">
            </div>
        </div>
    </#if>

    <!--
       添加根级菜单
    -->
    <input name="parentId" type="hidden" value="${parentId}"/>
    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addModule">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>
</form>
<script >
    layui.use(['form', 'layer'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer,
            $ = layui.jquery;
        /**
         * 监听表单提交
         */
        form.on("submit(addModule)", function (data) {
            // 弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            // 发送ajax请求
            $.post("${ctx}/system/module/add", data.field, function (res) {
                if (res.code == 200) {
                    setTimeout(function () {
                        top.layer.close(index);
                        top.layer.msg("操作成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }, 500);
                } else {
                    layer.msg(
                        res.msg, {
                            icon: 5
                        }
                    );
                }
            });
            return false;
        });


        /**
         * 关闭弹出层
         */
        $("#closeBtn").click(function () {
            // 当你在iframe页面关闭自身时
            var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
            parent.layer.close(index); // 再执行关闭
        });
    });
</script>
</body>
</html>