<!-- 交单记录查询  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="交单日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="提交人" prop="presenter">
        <el-input v-model="queryParams.presenter" placeholder="请输入提交人" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleExport">导出Excel</el-button>
      </el-col>
    </el-row> -->
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" />
        <el-table-column label="姓名" prop="patientname" align="center" />
        <el-table-column label="提交人" prop="presenter" align="center" />
        <el-table-column label="交单日期" prop="submitdate" align="center" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/document_query.js'
export default {
  name: 'Document_query',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        presenter: undefined,
        patientname: undefined,
        patientcode: undefined,
      },
      // 总条数
      total: 0,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 7 * 3600 * 24 * 1000
    this.queryParams.startTime = this.$getDate(past7daysStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      if (queryParams.startTime) {
        queryParams.startTime += ' 00:00:00'
      }
      if (queryParams.endTime) {
        queryParams.endTime += ' 23:59:59'
      }
      getListData(queryParams)
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
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 导出
    handleExport() {
      this.download('/statistics/healthResult/export', this.queryParams, `健康检查结果 .xlsx`)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
  },
}
</script>
