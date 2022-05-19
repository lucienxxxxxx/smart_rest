<#include "../../common.ftl">
<div class="layui-card">
    <div class="layui-card-body no-scrollbar">
        <div class="ew-field-group">
            <fieldset class="layui-elem-field">
                <legend>批量信息完善</legend>
                <div style="margin: 30px">
                    <button id="uploadExcel" type="button" class="layui-btn"><i class="layui-icon">&#xe67c;</i>批量上传
                    </button>
                    <button id="downloadTpl" type="button" class="layui-btn">模板下载</button>
                </div>
                <div id="view" style="text-align: center"></div>
            </fieldset>
        </div>
    </div>
</div>


<script id="demo" type="text/html">
    <div>
        <table class="layui-table">
            {{# layui.each(d.list, function(index, col) { }}
            <tr>
                {{# layui.each(col, function(col_index, item) { }}
                <td>{{item}}</td>
                {{# });}}
            </tr>
            {{# });}}
        </table>
    </div>

    <button id="add" type="button"  class="layui-btn ">确定导入</button>
</script>

<script>
    layui.use(['layer','laytpl','excel', 'upload'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var excel = layui.excel;
        var upload = layui.upload;
        var laytpl = layui.laytpl;

        //下载模板
        $('#downloadTpl').click(function () {
            excel.exportExcel([["真实姓名", "电话号码"]], '用户信息完善模板.xlsx', 'xlsx')//导出excel，模板下载
        });

        //批量导入
        upload.render({
            elem: '#uploadExcel',
            accept: 'file',
            auto: false, //选择文件后不自动上传
            bindAction: '#btnAddUser', //指向一个按钮触发上传
            before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(); //上传loading
            },
            choose: function (obj) {
                var files = obj.pushFile();
                var fileArr = Object.values(files);// 注意这里的数据需要是数组，所以需要转换一下
                // 用完就清理掉，避免多次选中相同文件时出现问题
                for (var index in files) {
                    if (files.hasOwnProperty(index)) {
                        delete files[index];
                    }
                }
                $('#uploadExcel').next().val('');
                uploadExcel(fileArr); // 如果只需要最新选择的文件，可以这样写： uploadExcel([files.pop()])
                layer.closeAll('loading'); //关闭loading
            },
            error: function (index, upload) {
                layer.closeAll('loading'); //关闭loading
                layer.msg("上传失败！", {
                    icon: 1
                });
                layer.closeAll(); //疯狂模式，关闭所有层
            }
        });

        //批量上传
        function uploadExcel(files) {
            try {
                excel.importExcel(files, {
                    // 读取数据的同时梳理数据
                    fields: {
                        'trueName': 'A',
                        'phone': 'B',
                    }
                }, function (data) {
                    var sheet = data[0].sheet1;
                    var list = new Array();
                    for (i = 0; i < sheet.length; i++) {
                        var tt = {
                            trueName: sheet[i].trueName,
                            phone: sheet[i].phone,
                        };
                        list.push(tt);
                    }

                    var gettpl = document.getElementById('demo').innerHTML;
                    laytpl(gettpl).render({list: sheet}, function (html) {
                        document.getElementById('view').innerHTML = html;
                    });

                    list.shift()//去掉头部
                    //导入按钮
                    $('#add').click(function () {
                        var loading = layer.load();
                        $.ajax({
                            async: false,
                            url: '${ctx}/guke/member/addInfoByExcel',
                            type: 'post',
                            contentType: "application/json;charset=UTF-8",
                            dataType: "json",
                            data: JSON.stringify(list),
                            success: function (res) {
                                layer.close(loading);
                                if (res.code == 200) {
                                    layer.msg(res.msg, {icon: 1},function (d){
                                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                        parent.layer.close(index); //再执行关闭
                                    });
                                }
                            }
                        })
                    });
                })
            } catch (e) {
                layer.alert(e.message);
            }
        }


    })
</script>