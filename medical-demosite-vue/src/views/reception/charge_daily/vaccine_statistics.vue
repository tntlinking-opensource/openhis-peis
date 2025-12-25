<!-- 每日疫苗收费统计  开发人：麦沃德科技 予安 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择" clearable @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <el-input v-model="queryParams.xsjl" placeholder="请输入销售经理" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleExport" v-hasPermi="['reception:chargeDaily:contact:export']" style="margin-bottom: 8px">导出Excel </el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column fixed label="分中心" prop="fzx" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column fixed label="体检者" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column fixed label="收费方式" prop="paywayName" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column fixed label="支付平台订单号" prop="cardno" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column fixed label="实收" prop="moneyamountpaid" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column fixed label="收费人" prop="userName" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column fixed label="收费项目名称" prop="examfeeitemName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column fixed label="开单医师" prop="doctorreg" min-width="110" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getVaccinum } from '@/api/reception/charge_daily.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import { getDate } from '@/utils/getDate.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 分中心列表
      fzxOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        branchIds: undefined,
        xsjl: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.startTime = getDate().split(' ')[0] + ' 00:00:00'
    this.queryParams.endTime = getDate().split(' ')[0] + ' 23:59:59'
    // this.queryParams.branchIds = this.$getCookie('cid')
    // 获取分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getVaccinum(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 导出
    handleExport() {
      this.download('/reception/chargeQuery/exportVaccinum', this.queryParams, '每日疫苗收费统计.xlsx')
    },
  },
}
</script>
