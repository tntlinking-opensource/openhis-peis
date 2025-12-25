<!-- 报告审核工作量  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="终审时间" prop="date">
        <el-date-picker v-model="queryParams.date" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" style="width: 240px"> </el-date-picker>
      </el-form-item>
      <el-form-item label="终审人" prop="lastId">
        <input-select ref="lastId" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="diseaseHealth">
        <el-select v-model="queryParams.diseaseHealth" placeholder="请选择" clearable style="width: 160px">
          <el-option label="健康" :value="0"></el-option>
          <el-option label="职业" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="请选择" clearable style="width: 160px">
          <el-option v-for="item in memberType" :key="item.levelId" :label="item.levelName" :value="item.levelId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择" style="width: 160px">
          <el-option label="一审工作量" :value="1"></el-option>
          <el-option label="二审工作量" :value="2"></el-option>
          <el-option label="终审工作量" :value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:statisticsItems:export']">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <el-row style="flex: 1">
      <el-col :span="12" style="height: 100%; display: flex; flex-direction: column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="终审人" prop="lastName" align="center" />
            <el-table-column label="终审时间" prop="lastTime" align="center" />
            <el-table-column label="合计" prop="total" align="center" />
            <el-table-column label="合计个人" prop="totalPersonal" align="center" />
            <el-table-column label="合计团体" prop="totalGroup" align="center" />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList(1)" />
      </el-col>
      <el-col :span="12" style="height: 100%; display: flex; flex-direction: column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loadingAll" :data="tableListAll" height="100%" stripe>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="终审人" prop="lastName" align="center" />
            <el-table-column label="合计" prop="total" align="center" />
            <el-table-column label="合计个人" prop="totalPersonal" align="center" />
            <el-table-column label="合计团体" prop="totalGroup" align="center" />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="totalAll" :page.sync="queryParamsAll.current" :limit.sync="queryParamsAll.size" @pagination="getList(2)" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getListData, getTotalData } from '@/api/statistical/workload/report_audit.js'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        lastId: undefined,
        diseaseHealth: undefined,
        idPatientclass: undefined,
        state: 3,
        url: undefined,
        urlAll: undefined,
      },
      queryParamsAll: {
        current: 1,
        size: 20,
      },
      // 审核人参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '姓名', //第二列标题
        url: '/statistics/ReportReview/getAllUserData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 遮罩层
      loading: true,
      loadingAll: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      totalAll: 0,
      // 表格数据
      tableList: [],
      // 表格数据所有
      tableListAll: [],
      // 体检者类型列表
      memberType: [],
    }
  },
  async created() {
    this.memberType = (await this.$getLevelList()).data
    let nowTime = this.$getDate().split(' ')[0]
    this.queryParams.date = [nowTime, nowTime]
    this.getList()
  },
  methods: {
    // 查询列表
    getList(type) {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      let url = ''
      let urlAll = ''
      if (this.queryParams.state == 1) {
        url = '/statistics/ReportReview/getFistListData'
        urlAll = '/statistics/ReportReview/getFistTotalData'
      } else if (this.queryParams.state == 2) {
        url = '/statistics/ReportReview/getSecondListData'
        urlAll = '/statistics/ReportReview/getSecondTotalData'
      } else {
        url = '/statistics/ReportReview/getListData'
        urlAll = '/statistics/ReportReview/getTotalData'
      }
      if (type != 2) {
        this.loading = true
        getListData(url, this.queryParams)
          .then((response) => {
            this.tableList = response.data.records
            this.total = response.data.total
            this.loading = false
          })
          .catch((error) => {
            console.error(error)
            this.loading = false
          })
      }
      if (type != 1) {
        this.loadingAll = true
        getTotalData(urlAll, { ...this.queryParams, ...this.queryParamsAll })
          .then((response) => {
            this.tableListAll = response.data.records
            this.totalAll = response.data.total
            this.loadingAll = false
          })
          .catch((error) => {
            console.error(error)
            this.loadingAll = false
          })
      }
    },
    // 审核人选择返回值
    selectChange(value) {
      this.queryParams.lastId = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      let nowTime = this.$getDate().split(' ')[0]
      this.queryParams.date = [nowTime, nowTime]
      this.queryParamsAll = {
        current: 1,
        size: 20,
      }
      this.$refs.lastId.initData()
      this.handleQuery()
    },
    // 导出
    handleExport() {
      let url = ''
      let name = ''
      if (this.queryParams.state == 1) {
        url = '/statistics/ReportReview/exportFist'
        name = '一审'
      } else if (this.queryParams.state == 2) {
        url = '/statistics/ReportReview/exportSecond'
        name = '二审'
      } else {
        url = '/statistics/ReportReview/export'
        name = '终审'
      }
      this.download(url, this.queryParams, name + `报告审核工作量.xlsx`)
    },
  },
}
</script>
