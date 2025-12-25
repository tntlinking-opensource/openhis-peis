<!-- 每月每日套餐统计  开发人：麦沃德科技 予安 / 暴雨 /予安-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="选择日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="体检套餐输入码" prop="tjtcsrm">
        <el-input v-model="queryParams.tjtcsrm" placeholder="请输入输入码" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检套餐名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:totalCombo:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="登记日期" prop="combo0" align="center" />
        <el-table-column label="套餐名称" prop="combo1" align="center" min-width="200" show-overflow-tooltip />
        <el-table-column label="数量" prop="combo2" align="center" />
        <el-table-column label="套餐优惠价" prop="tcyhj" align="center">
          <template slot-scope="scope">
            {{ scope.row.tcyhj ? Number(scope.row.tcyhj).toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="优惠统收合计" prop="yhtshj" align="center">
          <template slot-scope="scope">
            {{ scope.row.yhtshj ? Number(scope.row.yhtshj).toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column label="数量（个人）" prop="combo5" align="center" />
        <el-table-column label="数量（团体）" prop="combo6" align="center" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListdata } from '@/api/statistical/total_combo'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        tjtcsrm: undefined,
        name: undefined,
        branchIds: undefined,
      },
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.handleQuery()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getListdata(this.queryParams)
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
    // 导出
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/totalCombo/export', this.queryParams, `每月每日套餐统计.xlsx`)
    },
  },
}
</script>
