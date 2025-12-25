<!-- PACS科室工作量统计  开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column finance_total" style="background: white">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检时间" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检科室" prop="ks">
        <input-select ref="tjks" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="医师" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入医师" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="录入人" prop="writename">
        <el-input v-model="queryParams.writename" placeholder="请输入录入人" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="审核人" prop="auditName">
        <el-input v-model="queryParams.auditName" placeholder="请输入审核人" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-checkbox clearable v-model="queryParams.isPacs">Pacs登记人员</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['PACS:department_workload:export']">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <div class="main-content">
      <!-- 表格 -->
      <div class="data-box">
        <div class="table-data">
          <el-table border v-loading="loading" :data="totalInfo.data" height="calc(100% - 10px)" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="科室" min-width="50" prop="deptName" align="center" show-overflow-tooltip />
            <el-table-column label="体检号" min-width="80" prop="patientcode" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" min-width="50" prop="patientname" align="center" show-overflow-tooltip />
            <el-table-column label="性别" min-width="30" prop="idSex" align="center" show-overflow-tooltip />
            <el-table-column label="年龄" min-width="30" prop="age" align="center" show-overflow-tooltip />
            <el-table-column label="登记日期" min-width="60" prop="dateregister" align="center" show-overflow-tooltip />
            <el-table-column label="体检日期" min-width="60" prop="medicaldate" align="center" show-overflow-tooltip />
            <el-table-column label="收费项目" min-width="130" prop="examfeeitemName" align="center" show-overflow-tooltip />
            <el-table-column label="录入人" min-width="60" prop="username" align="center" show-overflow-tooltip />
            <el-table-column label="医师" min-width="60" prop="examdoctor" align="center" show-overflow-tooltip />
            <el-table-column label="检查时间" min-width="60" prop="examdatetime" align="center" show-overflow-tooltip />
            <el-table-column label="描述" min-width="180" prop="examresultdesc" align="center" show-overflow-tooltip />
            <el-table-column label="小结" min-width="180" prop="examresultsummary" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
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
import { getTotalList, totalExport, getecharts } from '@/api/PACS/department_workload.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      legend: [],
      seriesPie: [],
      seriesLine: [],
      // 遮罩层
      loading: true,
      // 选中数组我
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        isPacs: undefined,
        auditName:'',
        writename: '',
        name: '',
        ks: '',
        date: '',
        current: 1,
        size: 20,
      },
      // 科室参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getKsData', //请求连接
        selectWidth: 200, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'inputCode', //向接口传递的参数名(不传默认为'inputCode')
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

  watch: {},
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  mounted() {
    this.getList()
    this.getecharts()
  },
  methods: {
    // 科室选择返回值
    selectChange(value) {
      this.queryParams.ks = value.id
    },
    // 查询列表
    getList() {
      this.loading = false
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      getTotalList(this.queryParams).then((res) => {
        this.totalInfo.data = res.data.records
        this.total = res.data.total
        this.queryParams.isPacs = undefined
      })
    },
    // 查询图表
    getecharts() {
      this.loading = false
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }

      getecharts(this.queryParams).then((res) => {
        this.legend = res.data.legend
        this.seriesPie = res.data.seriesPie
        this.seriesLine = res.data.seriesLine

        var xAxisData = []
        var piexAxisData = []
        var yjdata = []
        var piedata = []

        for (var i in this.seriesPie) {
          var obj = this.seriesPie[i]
          xAxisData.push(obj.name)
          yjdata.push(obj.value)
          piedata.push({
            value: obj.value,
            name: obj.name,
          })
          piexAxisData.push(obj.name)
        }
        this.initBarEcharts(xAxisData, yjdata)
        this.initPieEcharts(
          piexAxisData,
          piedata.sort(function (a, b) {
            return a.value == b.value ? 0 : a.value > b.value ? -1 : 1
          })
        )
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.isPacs == true) {
        this.queryParams.isPacs = 1
      } else {
        this.queryParams.isPacs = undefined
      }
      this.getList()
      this.getecharts()
    },
    // 重置
    resetQuery() {
      this.$refs.tjks.initData()
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
      this.download('/statistics/pacsQuery/export', this.queryParams, `PACS科室工作量统计.xlsx`)
    },
    // 柱状图
    initBarEcharts(xAxisData, yjdata) {
      // 基本柱状图
      const option = {
        color: ['#5b9bd5'],
        tooltip: {
          trigger: 'axis',
        },
        title: {
          text: '工作量折线可视化',
        },
        legend: {
          data: ['工作量'],
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
            name: '工作量',
            type: 'line',
            stack: '总量',
            data: yjdata,
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
        color: ['#ffc000', '#00b050', '#ed7d31', '#255e91', '#a0c885', '#4472c4', 'red', 'pink'],
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)',
        },
        title: {
          text: '工作量饼图可视化',
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
        flex: 1;
        background: #fff;
      }

      .chart-data {
        flex: 1;
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
