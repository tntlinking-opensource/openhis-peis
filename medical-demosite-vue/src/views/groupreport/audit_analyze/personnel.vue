<!-- 团检报告审批-综合样本分析-人员构成  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-personnel">
    <!-- 表格 -->
    <div class="content-table">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="类型" prop="type" align="center" />
        <el-table-column label="人数" align="center">
          <el-table-column label="男性" prop="boy1" align="center" />
          <el-table-column label="女性" prop="girl1" align="center" />
          <el-table-column label="合计" prop="total1" align="center" />
        </el-table-column>
        <el-table-column label="人员构成" align="center">
          <el-table-column label="男性" prop="boy2" align="center" />
          <el-table-column label="女性" prop="girl2" align="center" />
        </el-table-column>
        <el-table-column label="参检情况" align="center">
          <el-table-column label="男性" prop="boy3" align="center" />
          <el-table-column label="女性" prop="girl3" align="center" />
          <el-table-column label="合计" prop="total3" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-left">
        <div class="chart-box" id="people1"></div>
        <div class="chart-box" id="people2"></div>
      </div>
      <div class="chart-right">
        <div class="chart-box" id="people3"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { getPersonnelList } from "@/api/groupreport/audit_analyze";
import * as echarts from "echarts";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      getPersonnelList(this.$route.query.id).then(response => {
        this.tableList = response.data
        this.initCharts()
        this.loading = false;
      });
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts() {
      var data = this.tableList
      var people1 = echarts.init(document.getElementById('people1'))
      var people2 = echarts.init(document.getElementById('people2'))
      var people3 = echarts.init(document.getElementById('people3'))

      var option1 = {
        title: {
          text: '男性人数数据统计',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          right: 0,
          data: [
            {
              name: data[0]["boy1"] + '  A.计划',
            },
            {
              name: data[1]["boy1"] + '  B.参检',
            },
            {
              name: data[2]["boy1"] + '  C.未检',
            }
          ],
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1
        },
        series: [
          {
            name: data[0]["boy1"] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB'
              }
            },
            data: [
              {
                name: '男性人数',
                value: data[0]["boy1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                },
              }
            ]
          },
          {
            name: data[1]["boy1"] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF'
              }
            },
            data: [
              {
                value: data[1]["boy1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }
              }
            ]
          },
          {
            name: data[2]["boy1"] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00'
              }
            },
            data: [
              {
                value: data[2]["boy1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }

              }
            ]
          }
        ]
      };
      people1.setOption(option1);

      var option2 = {
        title: {
          text: '女性人数数据统计',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          right: 0,
          data: [
            {
              name: data[0]["girl1"] + '  A.计划',
            },
            {
              name: data[1]["girl1"] + '  B.参检',
            },
            {
              name: data[2]["girl1"] + '  C.未检',
            }
          ],
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1
        },
        series: [
          {
            name: data[0]["girl1"] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB'
              }
            },
            data: [
              {
                name: '女性人数',
                value: data[0]["girl1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }
              }
            ]
          },
          {
            name: data[1]["girl1"] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF'
              }
            },
            data: [
              {
                value: data[1]["girl1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }

              }
            ]
          },
          {
            name: data[2]["girl1"] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00'
              }
            },
            data: [
              {
                value: data[2]["girl1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }

              }
            ]
          }
        ]
      };
      people2.setOption(option2);

      var option3 = {
        title: {
          text: '合计人数数据统计',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          right: 0,
          data: [
            {
              name: data[0]["total1"] + '  A.计划',
            },
            {
              name: data[1]["total1"] + '  B.参检',
            },
            {
              name: data[2]["total1"] + '  C.未检',
            }
          ],
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1
        },
        series: [
          {
            name: data[0]["total1"] + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB'
              }
            },
            data: [
              {
                name: '合计人数',
                value: data[0]["total1"],
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 24
                    }
                  }
                }
              }
            ]
          },
          {
            name: data[1]["total1"] + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF'
              }
            },
            data: [{
              value: data[1]["total1"],
              label: {
                normal: {
                  show: true,
                  position: 'top',
                  textStyle: {
                    fontSize: 24
                  }
                }
              }
            }]
          },
          {
            name: data[2]["total1"] + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00'
              }
            },
            data: [{
              value: data[2]["total1"],
              label: {
                normal: {
                  show: true,
                  position: 'top',
                  textStyle: {
                    fontSize: 24
                  }
                }
              }
            }]
          }
        ]
      };
      people3.setOption(option3);
    }
  },
};
</script>
<style lang="scss">
.main-personnel {
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

    .chart-left,
    .chart-right {
      width: calc(50% - 2px);
    }

    .chart-left {
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .chart-box {
        width: 100%;
        height: calc(50% - 2px);
        background: #fff;
        padding: 12px;
        box-sizing: border-box;
      }
    }

    .chart-right .chart-box {
      width: 100%;
      height: 100%;
      background: #fff;
      padding: 12px;
      box-sizing: border-box;
    }
  }
}
</style>