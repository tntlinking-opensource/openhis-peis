<!-- 费用合计  开发人：麦沃德科技半夏/清风 -->
<template>
  <div class="app-container flex-direction-column finance_total">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item prop="startTime" label="缴费时间">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:inspect_cost:finance_total:export']">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <div class="main-content">
      <!-- 合计 -->
      <div class="total-box" :style="{ '--theme': theme }">
        <div class="total-item">
          <div class="item-icon">
            <div class="icon-bg"></div>
            <i class="el-icon-s-order"></i>
          </div>
          <div class="item-text">
            <span class="price">{{ totalInfo.yj }}</span>
            <span class="name">预交金额合计</span>
          </div>
        </div>
        <div class="total-item">
          <div class="item-icon">
            <div class="icon-bg"></div>
            <i class="el-icon-s-order"></i>
          </div>
          <div class="item-text">
            <span class="price">{{ totalInfo.sh }}</span>
            <span class="name">实收金额合计</span>
          </div>
        </div>
        <div class="total-item">
          <div class="item-icon">
            <div class="icon-bg"></div>
            <i class="el-icon-s-order"></i>
          </div>
          <div class="item-text">
            <span class="price">{{ totalInfo.totalMoney }}</span>
            <span class="name">合计费用</span>
          </div>
        </div>
      </div>
      <!-- 表格 -->
      <div class="data-box">
        <div class="table-data">
          <el-table border v-loading="loading" :data="totalInfo.data" height="100%" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="收费方式" min-width="80" prop="paywayName" align="center" show-overflow-tooltip />
            <el-table-column label="费用合计" align="center">
              <!-- yjMaoney yjTotal -->
              <el-table-column label="预交金额" min-width="100" prop="yjMaoney" align="center" show-overflow-tooltip />
              <!-- shMoney shTotal -->
              <el-table-column label="实收金额" min-width="100" prop="shMoney" align="center" show-overflow-tooltip />
              <el-table-column label="合计费用" min-width="100" prop="total" align="center" show-overflow-tooltip />
            </el-table-column>
          </el-table>
        </div>
        <div class="chart-data">
          <div class="bar-chart">
            <div class="echart" id="barChart"></div>
          </div>
          <div class="pie-chart">
            <div class="echart" id="pieChart"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import { getTotalList, totalExport } from '@/api/statistical/inspect_cost/finance_count.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 100,
        startTime: undefined,
        endTime: undefined,
      },
      // 体检类型
      typeOptions: [
        { id: '1', text: '弃检' },
        { id: '2', text: '补检' },
        { id: '3', text: '拒检' },
        { id: '4', text: '迟检' },
      ],
      // 数据
      totalInfo: {
        data: [],
        totalMoney: '76348.2',
        yj: '0.0',
        sh: '76348.2',
      },
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  mounted() {
    this.handleQuery()
  },
  methods: {
    // 查询列表
    async getList() {
      this.loading = false
      await getTotalList(this.queryParams).then((res) => {
        this.totalInfo.data = res.data.records
      })
      var data = this.totalInfo.data
      var xAxisData = []
      var piexAxisData = []
      var yjdata = []
      var ssdata = []
      var piedata = []
      for (var i in data) {
        var obj = data[i]
        xAxisData.push(obj.paywayName)
        yjdata.push(obj.yjMaoney)
        ssdata.push(obj.shMoney)
        if (obj.total > 0) {
          piedata.push({
            value: obj.total,
            name: obj.paywayName,
          })
          piexAxisData.push(obj.paywayName)
        }
      }
      this.initBarEcharts(xAxisData, yjdata, ssdata)
      this.initPieEcharts(
        piexAxisData,
        piedata.sort(function (a, b) {
          return a.value == b.value ? 0 : a.value > b.value ? -1 : 1
        })
      )
    },
    // 搜索
    handleQuery() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 导出
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/financeCount/totalExport', this.queryParams, `费用合计统计表.xlsx`)
    },
    // 柱状图
    initBarEcharts(xAxisData, yjdata, ssdata) {
      // 基本柱状图
      const option = {
        color: ['#5b9bd5', '#ed7d31'],
        tooltip: {
          trigger: 'axis',
        },
        title: {
          text: '折线图统计',
        },
        legend: {
          data: ['预交金额', '实收金额'],
          top: 'top',
          left: 'right',
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xAxisData,
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '预交金额',
            type: 'line',
            stack: '总量',
            data: yjdata,
          },
          {
            name: '实收金额',
            type: 'line',
            stack: '总量',
            data: ssdata,
          },
        ],
      }
      const barChart = echarts.init(document.getElementById('barChart'))
      barChart.setOption(option)
      //随着屏幕大小调节图表
      window.addEventListener('resize', () => {
        barChart.resize()
      })
    },
    // 柱状图
    initPieEcharts(legendData, data) {
      // 基本柱状图
      const option = {
        color: ['#ffc000', '#00b050', '#ed7d31', '#255e91', '#a0c885', '#4472c4'],
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)',
        },
        title: {
          text: '饼状图统计',
        },
        legend: {
          type: 'scroll',
          top: 'bottom',
          data: legendData,
        },
        series: [
          {
            type: 'pie',
            data: data,
            label: {
              formatter: '{b} {c}',
            },
            top: '40px',
          },
        ],
      }
      const pieChart = echarts.init(document.getElementById('pieChart'))
      pieChart.setOption(option)
      //随着屏幕大小调节图表
      window.addEventListener('resize', () => {
        pieChart.resize()
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.finance_total {
  background: transparent;
  padding: 0;

  .el-form {
    background: #fff;
    padding: 16px 16px 0;
  }

  .main-content {
    display: flex;
    margin-top: 4px;
    flex: 1;

    .total-box {
      padding: 16px;
      background: #fff;
      min-width: 248px;
      max-width: 332px;
      width: 20%;

      .total-item {
        display: flex;
        align-items: center;
        background: #ffffff;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.07);
        border-radius: 5px;
        padding: 20px 24px;
        margin-bottom: 24px;

        .item-icon {
          position: relative;
          width: 48px;
          height: 48px;
          border-radius: 50%;
          overflow: hidden;
          display: flex;
          justify-content: center;
          align-items: center;

          .icon-bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: #{'var(--theme)'};
            opacity: 0.1;
          }

          .el-icon-s-order {
            position: relative;
            font-size: 22px;
            color: #fff;
            background: #{'var(--theme)'};
            width: 36px;
            height: 36px;
            line-height: 36px;
            border-radius: 50%;
            text-align: center;
          }
        }

        .item-text {
          margin-left: 16px;

          span {
            display: block;
          }

          .price {
            font-weight: 600;
            font-size: 20px;
            line-height: 28px;
            color: #0059ff;
          }

          .name {
            font-size: 14px;
            line-height: 20px;
            color: #999999;
          }
        }
      }
    }

    .data-box {
      flex: 1;
      margin-left: 4px;
      overflow: hidden;
      display: flex;
      flex-direction: column;

      .table-data {
        height: 45%;
        background: #fff;
      }

      .chart-data {
        flex: 1;
        margin-top: 4px;
        display: flex;

        .bar-chart {
          background: #fff;
          width: 66%;
          padding: 20px;
        }

        .pie-chart {
          background: #fff;
          margin-left: 4px;
          flex: 1;
          padding: 20px;
        }

        .echart {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>
