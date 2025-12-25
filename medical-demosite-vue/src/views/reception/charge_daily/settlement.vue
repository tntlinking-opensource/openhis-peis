<!-- 今日费用结算情况  开发人：麦沃德科技半夏、予安 -->
<template>
  <div class="settle-container">
    <el-dialog title="今日费用结算情况" :visible.sync="open" class="settle-daily" width="1348px" append-to-body>
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="选择日期">
            <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="姓名" prop="patientname">
            <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
            <el-table-column label="优惠总价" prop="moneyamountpaid" align="center" show-overflow-tooltip />
            <el-table-column label="实收" prop="totalss" align="center" show-overflow-tooltip />
            <el-table-column label="开单医生" prop="kdys" align="center" show-overflow-tooltip />
            <el-table-column label="登记员" prop="djy" align="center" show-overflow-tooltip />
            <el-table-column label="登记时间" prop="dateregister" align="center" show-overflow-tooltip min-width="110" />
            <el-table-column label="备注" prop="memo" align="center" show-overflow-tooltip min-width="110" />
          </el-table>
        </div>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getBackInfoData } from '@/api/reception/charge_daily.js'

import { getDate } from '@/utils/getDate.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientname: undefined,
        patientcode: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 表格数据
      tableList: [],
      // 是否显示弹出层
      open: false,
    }
  },
  methods: {
    // 显示
    handleShow() {
      this.resetQuery()
      this.tableList = []
      this.queryParams.date = [getDate(), getDate()]
      this.open = true
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0]
        this.queryParams.endTime = this.queryParams.date[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getBackInfoData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
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
      this.resetForm('queryForm')
      this.queryParams.date = [getDate(), getDate()]
      this.handleQuery()
    },
  },
}
</script>
<style lang="scss">
.settle-daily {
  .el-dialog {
    height: 100%;
    max-height: 700px;
  }
}
</style>
