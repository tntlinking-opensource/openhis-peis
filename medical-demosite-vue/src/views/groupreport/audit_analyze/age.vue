<!-- 团检报告审批-综合样本分析-年龄分布  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-age">
    <!-- 表格 -->
    <div class="content-table">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="年龄组" prop="agegroup" align="center" />
        <el-table-column label="人数" align="center">
          <el-table-column label="男性" prop="boy1" align="center" />
          <el-table-column label="女性" prop="girl1" align="center" />
          <el-table-column label="合计" prop="total1" align="center" />
        </el-table-column>
        <el-table-column label="百分比" align="center">
          <el-table-column label="男性" prop="boy2" align="center" />
          <el-table-column label="女性" prop="girl2" align="center" />
          <el-table-column label="合计" prop="total2" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-left">
        <div class="chart-box" id="age1"></div>
      </div>
      <div class="chart-right">
        <div class="chart-box" id="age2"></div>
        <div class="chart-box" id="age3"></div>
        <div class="chart-box" id="age4"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { getAgeList } from "@/api/groupreport/audit_analyze";
import * as echarts from "echarts";
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      getAgeList(this.$route.query.id).then(response => {
        this.tableList = response.data
        this.initCharts()
        this.loading = false;
      });
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts() {
      var data = this.tableList
      var name = new Array(data.length);
      var att1 = new Array(data.length);
      for (var i = 0; i < data.length; i++) {
        name[i] = data[i]["agegroup"];
        att1[i] = data[i]["boy1"];
      }
      var att2 = new Array(data.length);
      for (var i = 0; i < data.length; i++) {
        att2[i] = data[i]["girl1"];
      }
      var att3 = new Array(data.length);
      for (var i = 0; i < data.length; i++) {
        att3[i] = data[i]["total1"];
      }
      var age1 = echarts.init(document.getElementById('age1'));
      var age2 = echarts.init(document.getElementById('age2'));
      var age3 = echarts.init(document.getElementById('age3'));
      var age4 = echarts.init(document.getElementById('age4'));
      var option1 = {
        title: {
          text: '男女合计各年龄段构成',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          top: "18%",
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        legend: {
          data: ['人数统计']
        },
        xAxis: [
          {
            type: 'category',
            data: name
          }
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1
          }
        ],
        series: [
          {
            type: 'bar',
            data: att3,
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24
                }
              }
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return "#" + ("00000" + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6);
                }
              }
            }
          }
        ]
      };
      age1.setOption(option1);

      var option2 = {
        animation: false,
        title: {
          left: 'center',
          text: '男性',
        },
        tooltip: {
          show: true
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          bottom: "6%",
          data: (function () {
            var res = [];
            var len = name.length;
            while (len--) {
              res.push({
                name: name[len]
              });
            }
            return res;
          })()
        },
        series: [
          {
            type: 'pie',
            radius: '60%',
            data: (function () {
              var res = [];
              var len = name.length;
              while (len--) {
                res.push({
                  name: name[len],
                  value: att1[len]
                });
              }
              return res;
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      age2.setOption(option2);

      var option3 = {
        animation: false,
        title: {
          left: 'center',
          text: '女性',
        },
        tooltip: {
          show: true
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          bottom: "6%",
          data: (function () {
            var res = [];
            var len = name.length;
            while (len--) {
              res.push({
                name: name[len]
              });
            }
            return res;
          })()
        },
        series: [{
          type: 'pie',
          radius: '60%',
          data: (function () {
            var res = [];
            var len = name.length;
            while (len--) {
              res.push({
                name: name[len],
                value: att2[len]
              });
            }
            return res;
          })(),
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      };
      age3.setOption(option3);

      var option4 = {
        animation: false,
        title: {
          left: 'center',
          text: '合计',
        },
        tooltip: {
          show: true
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          bottom: "6%",
          data: (function () {
            var res = [];
            var len = name.length;
            while (len--) {
              res.push({
                name: name[len]
              });
            }
            return res;
          })()
        },
        series: [{
          type: 'pie',
          radius: '60%',
          data: (function () {
            var res = [];
            var len = name.length;
            while (len--) {
              res.push({
                name: name[len],
                value: att3[len]
              });
            }
            return res;
          })(),
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      };
      age4.setOption(option4);
    }
  },
};
</script>
<style lang="scss">
.main-age {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .content-table {
    height: 32%;
  }

  .content-chart {
    height: calc(68% - 4px);
    display: flex;
    justify-content: space-between;

    .chart-left {
      width: calc(30% - 2px);

      .chart-box {
        width: 100%;
        height: 100%;
        background: #fff;
        padding: 12px;
        box-sizing: border-box;
      }
    }

    .chart-right {
      width: calc(70% - 2px);
      display: flex;

      .chart-box {
        width: 100%;
        height: 100%;
        background: #fff;
        padding: 12px;
        box-sizing: border-box;
      }
    }
  }
}
</style>