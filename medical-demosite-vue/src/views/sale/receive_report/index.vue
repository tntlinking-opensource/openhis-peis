<!-- 待领取报告 麦沃德科技 开发人:清风/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="客户姓名" prop="patientname">
        <el-input placeholder="请输入请输入客户姓名" style="width: 230px" clearable v-model="queryParams.patientname" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input placeholder="请输入体检号" style="width: 230px" clearable v-model="queryParams.patientcode" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input placeholder="请输入联系电话" style="width: 230px" clearable v-model="queryParams.phone" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="单位名称" prop="orgName">
        <el-input placeholder="请输入单位名称" style="width: 230px" clearable v-model="queryParams.orgName" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="交接时间" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 320px" clearable value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="handleView()" v-hasPermi="['sale:receiveReport:view']">查看</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" align="center" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="rowClick" height="100%">
        <el-table-column type="selection" align="center" />
        <el-table-column type="index" label="序列" width="55" align="center" />
        <el-table-column prop="patientcode" label="体检号" align="center" />
        <el-table-column prop="patientname" label="客户姓名" align="center" />
        <el-table-column prop="phone" label="联系方式" align="center" />
        <el-table-column prop="orgName" label="客户单位名称" align="center" show-overflow-tooltip />
        <el-table-column prop="chestNum" label="柜子号" align="center" />
        <el-table-column prop="joinPersonMan" label="交接人" align="center" />
        <el-table-column prop="joinTime" label="交接时间" align="center" />
        <el-table-column label="报告状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 9">报告已交接</el-tag>
            <el-tag type="danger" v-else>报告未交接</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dateregister" label="登记时间" align="center" />
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <view-items ref="viewItems"></view-items>
  </div>
</template>

<script>
import { listReport } from '@/api/sale/receive_report'
import viewItems from './view.vue'
export default {
  name:'Receive_report',
  components: { viewItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      selectRow: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientname: undefined,
        patientcode: undefined,
        phone: undefined,
        orgName: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listReport(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
      this.single = selection.length != 1
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 查看
    handleView() {
      this.$refs.viewItems.handleView(this.selectRow.id)
    },
  },
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
