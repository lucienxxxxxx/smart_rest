<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>控制台</title>
    <#include "common.ftl">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        /** 统计快捷方式样式 */
        .console-link-block {
            font-size: 16px;
            padding: 20px 20px;
            border-radius: 4px;
            background-color: #9BC539;
            color: rgba(255, 255, 255, .8);
            box-shadow: 0 2px 3px rgba(0, 0, 0, .05);
            position: relative;
            overflow: hidden;
        }

        .console-link-block .console-link-block-num {
            font-size: 40px;
            margin-bottom: 5px;
            color: rgba(255, 255, 255, .9);
        }

        .console-link-block .console-link-block-icon {
            position: absolute;
            top: 50%;
            right: 20px;
            width: 50px;
            height: 50px;
            font-size: 50px;
            line-height: 50px;
            margin-top: -25px;
            color: rgba(255, 255, 255, .65);
        }

        .console-link-block .console-link-block-band {
            width: 80px;
            line-height: 1;
            padding: 4px 0;
            font-size: 12px;
            text-align: center;
            background-color: #E32A16;
            color: rgba(255, 255, 255, .8);
            position: absolute;
            top: 8px;
            right: -20px;
            transform: rotate(45deg);
            transform-origin: center;
            z-index: 1;
        }

        /** //统计快捷方式样式 */

        /** 设置每个快捷块的颜色 */
        .layui-row > div:nth-child(2) .console-link-block {
            background-color: rgb(85, 165, 234);
        }

        .layui-row > div:nth-child(3) .console-link-block {
            background-color: rgb(157, 175, 291);
        }

        .layui-row > div:nth-child(4) .console-link-block {
            background-color: rgb(245, 145, 162);
        }

        .layui-row > div:nth-child(5) .console-link-block {
            background-color: rgb(254, 170, 79);
        }

        .layui-row > div:last-child .console-link-block {
            background-color: rgb(64, 212, 176);
        }
    </style>
</head>
<body>
<!-- 正文开始 -->
<div class="layui-fluid ew-console-wrapper">
    <!-- 快捷方式 -->
    <div class="layui-row layui-col-space15">
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/system/user.html" ew-title="外出申请">
                <div class="console-link-block-num">15</div>
                <div class="console-link-block-text">外出申请</div>
                <i class="console-link-block-icon layui-icon layui-icon-survey"></i>
                <div class="console-link-block-band">待审批</div>
            </div>
        </div>
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/system/role.html" ew-title="请假审批">
                <div class="console-link-block-num">13</div>
                <div class="console-link-block-text">请假审批</div>
                <i class="console-link-block-icon layui-icon layui-icon-print"></i>
                <div class="console-link-block-band">待审批</div>
            </div>
        </div>
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/system/authorities.html" ew-title="研发周报">
                <div class="console-link-block-num">22</div>
                <div class="console-link-block-text">研发周报</div>
                <i class="console-link-block-icon layui-icon layui-icon-list"
                   style="font-size: 62px;padding-right: 5px;"></i>
                <div class="console-link-block-band">去查看</div>
            </div>
        </div>
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/system/loginRecord.html" ew-title="研发月报">
                <div class="console-link-block-num">18</div>
                <div class="console-link-block-text">研发月报</div>
                <i class="console-link-block-icon layui-icon layui-icon-date"></i>
                <div class="console-link-block-band">去查看</div>
            </div>
        </div>
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/plugin/basic/dialog.html" ew-title="拜访记录">
                <div class="console-link-block-num">11</div>
                <div class="console-link-block-text">拜访记录</div>
                <i class="console-link-block-icon layui-icon layui-icon-service"></i>
                <div class="console-link-block-band">去查看</div>
            </div>
        </div>
        <div class="layui-col-lg2 layui-col-sm4 layui-col-xs6">
            <div class="console-link-block" ew-href="page/plugin/basic/notice.html" ew-title="项目申报">
                <div class="console-link-block-num">26</div>
                <div class="console-link-block-text">项目申报</div>
                <i class="console-link-block-icon layui-icon layui-icon-chart-screen"></i>
                <div class="console-link-block-band">待审批</div>
            </div>
        </div>
    </div>
    <!-- 统计图表 -->
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md4 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-header">日统计</div>
                <div class="layui-card-body">
                    <div id="tjDivDay" style="height: 330px;"></div>
                    <div style="color: #1AB4E8;font-size: 18px;position: absolute;bottom: 95px;left: 0;right:0;text-align: center;cursor: pointer;">
                        签到明细<i class="layui-icon layui-icon-right" style="font-size: 16px;"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md4 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-header">周统计</div>
                <div class="layui-card-body">
                    <div id="tjDivWeek" style="height: 330px;"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-md4 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-header">月统计</div>
                <div class="layui-card-body">
                    <div id="tjDivMonth" style="height: 330px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 加载动画 -->
<div class="page-loading">
    <div class="ball-loader">
        <span></span><span></span><span></span><span></span>
    </div>
</div>
<!-- js部分 -->
<script type="text/javascript" src="../../assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="../../assets/js/common.js?v=316"></script>
<script src="../../assets/libs/echarts/echarts.min.js"></script>
<script>
    layui.use(['layer'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;

        // 渲染日签到图表
        var myCharts1 = echarts.init(document.getElementById('tjDivDay'));
        var options1 = {
            title: {
                x: 'center',
                y: '32%',
                text: '签到人数/应到人数',
                textStyle: {
                    fontSize: 18,
                    color: '#262626',
                    fontWeight: 'normal'
                },
                subtextStyle: {
                    fontSize: 56,
                    color: '#28a6d6'
                },
                itemGap: 20
            },
            color: ['#18B4E7', '#E0E0E0'],
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                right: '0px',
                top: '0px',
                data: ['已签到', '未签到'],
                textStyle: {
                    color: '#595959'
                }
            },
            series: [{
                name: '人数',
                type: 'pie',
                radius: ['75%', '80%'],
                label: {
                    normal: {
                        show: false
                    }
                }
            }]
        };
        myCharts1.setOption(options1);
        // 赋值
        myCharts1.setOption({
            title: {subtext: '38/60'},
            series: [{data: [{name: '已签到', value: 38}, {name: '未签到', value: 22}]}]
        });

        // ------------------------------------------------------------------------
        // 渲染周签到图表
        var myCharts2 = echarts.init(document.getElementById('tjDivWeek'));
        var options2 = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#E0E0E0'
                    }
                }
            },
            color: ['#10B4E8', '#FFA800'],
            legend: {
                orient: 'vertical',
                right: '0px',
                top: '0px',
                data: ['已签到', '未签到'],
                textStyle: {
                    color: '#595959'
                }
            },
            grid: {
                top: '75px',
                left: '35px',
                right: '55px',
                bottom: '40px'
            },
            xAxis: {
                name: '星期',
                nameTextStyle: {
                    color: '#595959'
                },
                type: 'category',
                data: ['周一', '周二', '周三', '周四', '周五'],
                axisLine: {
                    lineStyle: {
                        color: '#E0E0E0'
                    },
                    symbol: ['none', 'arrow'],
                    symbolOffset: [0, 10]
                },
                axisLabel: {
                    color: '#8c8c8c'
                },
                axisTick: {
                    alignWithLabel: true
                }
            },
            yAxis: {
                name: '人数',
                nameTextStyle: {
                    color: '#595959'
                },
                type: 'value',
                boundaryGap: ['0', '20%'],
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#E0E0E0'
                    },
                    symbol: ['none', 'arrow'],
                    symbolOffset: [0, 10]
                },
                axisLabel: {
                    color: '#8c8c8c'
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                },
                minInterval: 1
            },
            series: [
                {
                    name: '已签到',
                    type: 'bar',
                    stack: 'one',
                    barMaxWidth: '30px',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                },
                {
                    name: '未签到',
                    type: 'bar',
                    stack: 'one',
                    barMaxWidth: '30px',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }
            ]
        };
        myCharts2.setOption(options2);
        // 赋值
        myCharts2.setOption({
            series: [{data: [5, 9, 6, 3, 10]}, {data: [10, 6, 9, 12, 5]}]
        });

        // -------------------------------------------------------------------------
        // 渲染周签到图表
        var myCharts3 = echarts.init(document.getElementById('tjDivMonth'));
        var options3 = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#E0E0E0'
                    }
                },
                formatter: '{b}号<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#10B4E8;"></span>{a0}: {c0}<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#FFA800;"></span>{a1}: {c1}'
            },
            color: ['#10B4E8', '#FFA800'],
            legend: {
                orient: 'vertical',
                right: '0px',
                top: '0px',
                data: ['已签到', '未签到'],
                textStyle: {
                    color: '#595959'
                }
            },
            grid: {
                top: '75px',
                left: '35px',
                right: '55px',
                bottom: '40px'
            },
            xAxis: {
                name: '日期',
                nameTextStyle: {
                    color: '#595959'
                },
                type: 'category',
                data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'],
                axisLine: {
                    lineStyle: {
                        color: '#E0E0E0'
                    },
                    symbol: ['none', 'arrow'],
                    symbolOffset: [0, 10]
                },
                axisLabel: {
                    color: '#8c8c8c',
                    interval: function (index, value) {
                        if (index == 0 || ((index + 1) % 5 == 0)) {
                            return true;
                        }
                        return false;
                    }
                },
                axisTick: {
                    alignWithLabel: true
                }
            },
            yAxis: {
                name: '人数',
                nameTextStyle: {
                    color: '#595959'
                },
                type: 'value',
                boundaryGap: ['0', '20%'],
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#E0E0E0'
                    },
                    symbol: ['none', 'arrow'],
                    symbolOffset: [0, 10]
                },
                axisLabel: {
                    color: '#8c8c8c'
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                },
                minInterval: 1
            },
            series: [
                {
                    name: '已签到',
                    type: 'line',
                    smooth: false
                },
                {
                    name: '未签到',
                    type: 'line',
                    smooth: false
                }
            ]
        };
        myCharts3.setOption(options3);
        // 赋值
        myCharts3.setOption({
            series: [
                {data: [15, 14, 13, 13, 13, 14, 15, 16, 17, 18, 19, 18, 18, 19, 20, 19, 18, 16, 14, 12, 10, 10, 12, 14, 16, 16, 14, 13, 12, 11, 10]},
                {data: [24, 22, 20, 18, 16, 14, 13, 12, 11, 11, 10, 10, 11, 12, 13, 14, 15, 16, 18, 20, 22, 23, 24, 25, 26, 26, 24, 22, 20, 18, 16]}
            ]
        });
        // -------------------------------------------------------------------------

        // 窗口大小改变事件
        window.onresize = function () {
            myCharts1.resize();
            myCharts2.resize();
            myCharts3.resize();
        };

    });
</script>
</body>

</html>