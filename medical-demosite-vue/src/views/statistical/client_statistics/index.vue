<!-- 来检客户统计  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column client-statistics">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择" clearable ref="fzx">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单类型" prop="fUsecodehiden">
        <el-select v-model="queryParams.fUsecodehiden" placeholder="请选择" clearable style="width: 160px">
          <el-option label="个检" :value="0"></el-option>
          <el-option label="团检" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <el-input style="width: 180px" v-model="queryParams.xsjl" placeholder="请输入销售经理名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item prop="startTime" label="来检日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport(1)">导出订单信息</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport(2)">导出人员信息</el-button>
      </el-col>
    </el-row>
    <el-row style="flex: 1">
      <el-col :span="13" style="height: 100%; display: flex; flex-direction: column; padding-right: 8px">
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="分中心" prop="fzx" align="center" width="120" show-overflow-tooltip />
            <el-table-column label="单位名称" prop="khdwmc" align="center" show-overflow-tooltip />
            <el-table-column label="订单名称" prop="ddmc" align="center" show-overflow-tooltip />
            <el-table-column label="备单分类" prop="tjlx" align="center" width="90">
              <template slot-scope="scope">
                {{ ['健康', '职业', '综合'][scope.row.tjlx] }}
              </template>
            </el-table-column>
            <el-table-column label="来检人数" prop="ljrs" align="center" width="80" />
            <el-table-column label="来检业绩" prop="total" align="center" width="80" />
            <el-table-column label="销售经理" prop="xsjl" align="center" width="80" />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>
      <el-col :span="11" style="height: 100%; display: flex; flex-direction: column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loadingR" :data="tableListR" height="100%" stripe>
            <el-table-column type="selection" width="45" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" width="110" />
            <el-table-column label="套餐名称" prop="examsuiteName" align="center" width="130" show-overflow-tooltip />
            <el-table-column label="登记时间" prop="dateregister" align="center" width="140" />
            <el-table-column label="收费时间" prop="chargdate" align="center" width="140" />
            <el-table-column label="收费金额" prop="moneyamount" align="center" width="80" />
            <el-table-column label="状态" prop="status" align="center" width="80">
              <template slot-scope="scope">
                <span style="color: red" v-if="scope.row.status == 0">未结账</span>
                <span style="color: green" v-else>已结账</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <pagination :total="totalR" :page.sync="queryParamsR.current" :limit.sync="queryParamsR.size" @pagination="getListR" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getListData, getPhysicalExaminer } from '@/api/statistical/client_statistics.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
export default {
  name: 'Client_statistics',
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 30,
        branchIds: undefined,
        startTime: undefined,
        endTime: undefined,
        xsjl: undefined,
        state: undefined,
      },
      queryParamsR: {
        current: 1,
        size: 30,
        ddh: undefined,
      },
      // 遮罩层
      loading: false,
      loadingR: false,
      // 总条数
      total: 0,
      totalR: 0,
      // 表格数据
      tableList: [],
      tableListR: [],
      // 选中项的id
      selectId: undefined,
      // 分中心列表
      fzxOptions: [],
    }
  },
  created() {
    // 获取分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
      this.queryParams.branchIds = this.$getCookie('cid')
    })
    let nowTime = this.$getDate().split(' ')[0]
    this.queryParams.startTime = nowTime
    this.queryParams.endTime = nowTime
    this.handleQuery()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParamsR.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
      this.getListR()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
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
    // 查询列表
    getListR() {
      this.loadingR = true
      getPhysicalExaminer({ ...this.queryParams, ...this.queryParamsR })
        .then(({ data }) => {
          this.tableListR = data.records
          this.totalR = data.total
          this.loadingR = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingR = false
        })
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
      } else if (selection.length == 1) {
        this.selectId = selection[0].id
        this.queryParamsR.ddh = selection[0].ddh
        this.getListR()
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    /** 导出按钮操作 */
    handleExport(type) {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download(type == 1 ? '/statistics/customerStatistics/exportOrg' : '/statistics/customerStatistics/exportPer', this.queryParams, type == 1 ? `订单信息.xlsx` : `人员信息.xlsx`)
    },
  },
}
</script>
