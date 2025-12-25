<!-- 公共客户状态日志 麦沃德科技 开发人:清风/予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="选择日期" prop="daterange">
        <el-date-picker style="width: 310px" value-format="yyyy-MM-dd" type="daterange" v-model="daterange" key="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input placeholder="请输入客户单位" style="width: 180px" v-model="queryParams.khdwmc" clearable></el-input>
      </el-form-item>
      <el-form-item label="操作人" prop="operateName">
        <el-input placeholder="请输入操作人" style="width: 180px" v-model="queryParams.operateName" clearable></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjlmc">
        <el-input placeholder="请输入销售经理" style="width: 180px" v-model="queryParams.xsjlmc" clearable></el-input>
      </el-form-item>
      <el-form-item label="操作类型" prop="operateType">
        <el-select placeholder="请选择" style="width: 180px" v-model="queryParams.operateType" clearable>
          <el-option v-for="(item, index) in operateType" :key="index" :value="item" :label="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分配人" prop="fprmc">
        <el-input placeholder="请输入分配人" style="width: 180px" v-model="queryParams.fprmc" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" row-key="id" height="100%">
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户" align="center"></el-table-column>
        <el-table-column prop="xsjlmc" label="销售经理" align="center"></el-table-column>
        <el-table-column prop="operateName" label="操作人" align="center"></el-table-column>
        <el-table-column prop="operateType" label="操作类型" align="center"></el-table-column>
        <el-table-column prop="fprmc" label="分配人" align="center"></el-table-column>
        <el-table-column prop="createdate" label="操作时间" align="center"></el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { listCustomer } from '@/api/customer/public_customer_log.js'
export default {
  name:'public_customer_log',
  data() {
    return {
      loading: false,
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        khdwmc: undefined,
        operateName: undefined,
        xsjlmc: undefined,
        operateType: undefined,
        fprmc: undefined,
      },
      // 起始日期
      daterange: [],
      operateType: ['客户领取', '领导分配', '客户释放', '客户流失', '领导释放', '系统释放', '客户转移'],
      tableData: [],
    }
  },
  watch: {
    daterange: {
      handler(newValue) {
        if (newValue) {
          this.queryParams.startTime = newValue[0] + ' 00:00:00'
          this.queryParams.endTime = newValue[1] + ' 23:59:59'
        }
      },
    },
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCustomer(this.queryParams).then(({ data }) => {
        this.tableData = data.records
        this.total = data.total
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
      this.daterange = undefined
      this.queryParams.startTime = undefined
      this.queryParams.endTime = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>
