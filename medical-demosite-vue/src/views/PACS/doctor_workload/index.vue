<!-- PACS医生工作量统计  开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column statistics-items">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="检查时间" prop="dateRange">
        <el-date-picker v-model="queryParams.dateRange" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"> </el-date-picker>
      </el-form-item>
      <el-form-item label="收费项目" prop="itemId">
        <input-select ref="itemId" :selectData="selectData2" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item label="医师" prop="name">
        <input-select ref="docName" :selectData="selectData3" @change="selectChange3"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <drag-row left-size="60%" right-size="40%">
      <template #leftBox>
        <div style="height: 100%" class="flex-direction-column">
          <!-- 操作按钮 -->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport">导出Excel</el-button>
            </el-col>
          </el-row>
          <!-- 表格 -->
          <div class="table-box no-padding-table" v-loading="loading" style="height: 92%">
            <el-table border ref="tableList" :data="tableList" height="100%" stripe>
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="序列" width="55" type="index" align="center" />
              <el-table-column label="检查日期" prop="rummagerTime" align="center" min-width="60px">
                <template slot-scope="scope">
                  {{ scope.row.rummagerTime ? scope.row.rummagerTime.split(' ')[0] : '' }}
                </template>
              </el-table-column>
              <el-table-column label="星期" prop="dayForWeek" align="center" min-width="30px" />
              <el-table-column label="科室" prop="depName" align="center" min-width="50px" show-overflow-tooltip />
              <el-table-column label="收费项目" prop="examName" align="center" min-width="150px" show-overflow-tooltip />
              <el-table-column label="医生" prop="username" align="center" min-width="50px" />
              <el-table-column label="工作量" prop="gzlTotal" align="center" min-width="30px" />
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>
      <template #rightBox>
        <div style="height: 100%" class="flex-direction-column">
          <!-- 操作按钮 -->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport2">导出Excel</el-button>
            </el-col>
          </el-row>
          <!-- 表格 -->
          <div class="table-box no-padding-table" v-loading="rightLoading" style="height: 100%">
            <el-table border v-if="!rightLoading" :data="tableRightList" height="100%" stripe>
              <el-table-column label="科室" prop="depName" align="center" min-width="50px" />
              <el-table-column label="收费项目" prop="examName" align="center" min-width="100px" />
              <el-table-column label="医生" prop="username" align="center" min-width="50px" />
              <el-table-column label="工作量" prop="gzlTotal" align="center" min-width="30px" />
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination :total="ctotal" :page.sync="queryRightParams.current" :limit.sync="queryRightParams.size" @pagination="getRightBasic" />
          <div class="bar-charts">
            <div class="echart" id="barChart"></div>
          </div>
        </div>
      </template>
    </drag-row>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import dragRow from '@/components/DragRow'
import { page, analyseInfo, ksBasic, getecharts } from '@/api/PACS/doctor_workload.js'
export default {
  components: { dragRow },
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        itemId: undefined,
        dateRange: undefined,
        startTime: undefined,
        endTime: undefined,
        ks: undefined,
        name: undefined,
        FExaminated: undefined,
      },
      queryRightParams: {
        current: 1,
        size: 20,
      },
      //获取科室
      ksOptions: [],
      // 查询的科室列表
      filterList: [],
      //获取已检--未检
      FExaminatedOptions: [
        { id: '0', text: '未检' },
        { id: '1', text: '已检' },
      ],
      loading: true,
      //右表数据loading
      rightLoading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      //右表总条数
      ctotal: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      //右表查询
      tableRightList: [],
      // 收费项目参数
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        url: '/items/page', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'sfxmsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'examfeeitemName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'sfxmsrm', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 医师参数
      selectData3: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/drugstore/prescribe/getDoctor', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
    }
  },
  created() {
    this.queryParams.dateRange = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    ksBasic().then(({ data }) => {
      this.ksOptions = data
      this.filterList = JSON.parse(JSON.stringify(data))
    })
    this.handleQuery()
  },
  methods: {
    // 柱状图
    initBarEcharts(xAxisData, yjdata) {
      // 基本柱状图
      const option = {
        color: ['#dd4246'],
        tooltip: {
          trigger: 'axis',
        },
        title: {
          text: '工作量柱状可视化',
        },
        legend: {
          data: ['工作量'],
          top: 'top',
          left: 'right',
        },
        grid: {
          left: '0%',
          right: '0%',
          bottom: '20%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: xAxisData,
          axisLabel: {
            interval: 0,
            formatter: (value, index) => {
              if (xAxisData.length <= 10) {
                return value
              } else {
                if (index % 2 == 0) {
                  return value
                } else {
                  return ''
                }
              }
            },
          },
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '工作量',
            type: 'bar',
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
    // 收费项目选择返回值
    selectChange2(value) {
      this.queryParams.itemId = value.id
      this.queryRightParams.itemId = value.id
    },
    // 医师选择返回值
    selectChange3(value) {
      this.queryParams.name = value.id
      this.queryRightParams.name = value.id
    },

    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
      this.getRightBasic()
      this.getecharts()
    },
    // 重置
    resetQuery() {
      this.$refs.itemId.initData()
      this.$refs.docName.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.dateRange) {
        this.queryParams.startTime = this.queryParams.dateRange[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.dateRange[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      page(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 查询图表
    getecharts() {
      if (this.queryParams.dateRange) {
        this.queryParams.startTime = this.queryParams.dateRange[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.dateRange[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      getecharts(this.queryParams).then((res) => {
        this.initBarEcharts(res.data.xAxis, res.data.series)
      })
    },
    getRightBasic() {
      this.rightLoading = true
      if (this.queryParams.dateRange) {
        this.queryParams.startTime = this.queryParams.dateRange[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.dateRange[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      analyseInfo({ ...this.queryParams, ...this.queryRightParams })
        .then((res) => {
          this.ctotal = res.data.total
          this.tableRightList = res.data.records
          this.rightLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.rightLoading = false
        })
    },
    // 导出
    handleExport() {
      this.download('/statistics/pacsDoctorQuery/exportWorkData', this.queryParams, `PACS科室医生工作量统计.xlsx`)
    },
    // 导出总计
    handleExport2() {
      this.download('/statistics/pacsDoctorQuery/exportTotal', this.queryParams, `PACS科室医生工作量总计统计.xlsx`)
    },
  },
}
</script>
<style lang="scss">
.statistics-items {
  .el-tabs__content {
    height: 100%;
  }
}
.bar-charts {
  background: #fff;
  width: 100%;
  height: 40%;
  padding: 20px;

  .echart {
    width: 100%;
    height: 100%;
  }
}
</style>
