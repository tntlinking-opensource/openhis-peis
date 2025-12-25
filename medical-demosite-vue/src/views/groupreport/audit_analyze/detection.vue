<!-- 团检报告审批-综合样本分析-检出统计  开发人：麦沃德科技半夏 -->
<template>
  <div class="main-detection">
    <!-- 表格 -->
    <div class="content-table">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="行号" width="60" type="index" align="center" />
        <el-table-column label="分类" prop="depName" align="center" show-overflow-tooltip />
        <el-table-column label="结论" prop="conclusion" align="center" show-overflow-tooltip />
        <el-table-column label="检出人数" align="center">
          <el-table-column label="男性" prop="detectionMale" align="center" />
          <el-table-column label="女性" prop="detectionFemale" align="center" />
          <el-table-column label="合计" prop="detectionTotal" align="center" />
        </el-table-column>
        <el-table-column label="总体人数" align="center">
          <el-table-column label="男性" prop="allMale" align="center" />
          <el-table-column label="女性" prop="allFemale" align="center" />
          <el-table-column label="合计" prop="allTotal" align="center" />
        </el-table-column>
        <el-table-column label="总体百分比" align="center">
          <el-table-column label="男性" prop="checkMale" align="center" />
          <el-table-column label="女性" prop="checkFemale" align="center" />
          <el-table-column label="合计" prop="checkTotal" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200]" />
    <!-- 图表 -->
    <div class="content-chart">
      <div class="chart-box" id="check1"></div>
      <div class="chart-box" id="check2"></div>
      <div class="chart-box" id="check3"></div>
    </div>
  </div>
</template>
<script>
import { getDetectionList, getAnalyzeList } from '@/api/groupreport/audit_analyze'
import * as echarts from 'echarts'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 排检表格数据
      tableList: [],
      // 统计图数据
      chartList: [],
      // 分页
      queryParams: {
        current: 1,
        size: 50,
      },
      total: 0,
    }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.getChartList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getDetectionList(this.$route.query.id, this.$route.query.groupId, this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          this.loading = false
          console.error(err)
        })
    },
    // 查询统计图数据
    getChartList() {
      getAnalyzeList(this.$route.query.id, this.$route.query.groupId).then((response) => {
        this.chartList = response.data
        this.initCharts()
      })
    },
    // 基于准备好的dom，初始化echarts图表
    initCharts() {
      var data = this.chartList
      var att1 = new Array(data.boy.length)
      for (var i = 0; i < data.boy.length; i++) {
        att1[i] = data.boy[i]['name']
      }
      var att2 = new Array(data.girl.length)
      for (var i = 0; i < data.girl.length; i++) {
        att2[i] = data.girl[i]['name']
      }
      var att3 = new Array(data.total.length)
      for (var i = 0; i < data.total.length; i++) {
        att3[i] = data.total[i]['name']
      }
      var check1 = echarts.init(document.getElementById('check1'))
      var check2 = echarts.init(document.getElementById('check2'))
      var check3 = echarts.init(document.getElementById('check3'))
      var option1 = {
        title: {
          text: '男性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option2 = {
        title: {
          text: '女性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option3 = {
        title: {
          text: '男女综合前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      option1.series[0].data = data.boy
      option2.series[0].data = data.girl
      option3.series[0].data = data.total
      option1.xAxis[0].data = att1
      option2.xAxis[0].data = att2
      option3.xAxis[0].data = att3
      check1.setOption(option1)
      check2.setOption(option2)
      check3.setOption(option3)
    },
    labelFormat(val) {
      if (val && val.length > 10) {
        return val.substring(0, 10) + '\n' + val.substring(10)
      } else {
        return val
      }
    },
  },
}
</script>
<style lang="scss">
.main-detection {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #fff;

  .content-table {
    height: 60%;
  }

  .content-chart {
    height: calc(40% - 4px);
    display: flex;

    .chart-box {
      width: 100%;
      height: 100%;
      background: #fff;
      padding: 12px;
      box-sizing: border-box;
      margin-left: 4px;

      &:first-child {
        margin-left: 0;
      }
    }
  }
}
</style>
