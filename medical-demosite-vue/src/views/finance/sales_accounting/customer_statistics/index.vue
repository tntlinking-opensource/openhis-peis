<!-- 客服销售统计 开发人：麦沃德科技清风 / 暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select style="width: 230px" v-model="queryParams.branchIds" placeholder="请选择" clearable @change="handleSearch">
          <el-option v-for="(item, index) in fzxOptions" :key="index" :value="item.branchId" :label="item.fzx"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师">
        <el-input style="width: 230px" v-model="queryParams.xsjl" placeholder="请输入开单医师" clearable></el-input>
      </el-form-item>
      <el-form-item label="登记日期">
        <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.date" style="width: 366px" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item style="margin-right: 26px">
        <el-radio v-model="queryParams.depart" label="2">所有</el-radio>
        <el-radio v-model="queryParams.depart" label="1">客服</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportData" v-hasPermi="['finance:salesAccounting:customerStatistics:export']">导出</el-button>
      </el-form-item>
    </el-form>

    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="jlmc" label="开单医生" align="center"></el-table-column>
        <el-table-column prop="lastJlmc" label="上次团体开单医生" align="center"></el-table-column>
        <el-table-column prop="tjh" label="体检号" align="center"></el-table-column>
        <el-table-column prop="tjrymc" label="体检人员名称" align="center"></el-table-column>
        <el-table-column prop="kstchj" label="可算提成合计" align="center"></el-table-column>
        <el-table-column prop="sf" label="实付合计" align="center"></el-table-column>
        <el-table-column prop="bkstc" label="不可算提成" align="center"></el-table-column>
        <el-table-column prop="jxyhjhj" label="加项优惠价合计" align="center"></el-table-column>
        <el-table-column prop="sjtcs" label="实际提成数" align="center"></el-table-column>
        <el-table-column prop="djrq" label="登记日期" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="tcmc" label="套餐名称" align="center" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getListData } from '@/api/finance/sales_accounting/customer_statistics'
import { getBranchData } from '@/api/finance/sales_accounting/sales_statistics'
import { getUserCid } from '@/api/system/branch.js'
import { getCookie } from '@/utils/getCookie'

export default {
  name: 'Customer_statistics',
  data() {
    return {
      ids: [],
      single: true,
      multiple: true,
      loading: false,
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        depart: '2',
        fzx: '',
        xsjl: '',
        date: '',
        startTime: '',
        endTime: '',
        branchIds: '',
      },
      showSearch: true,
      // 分中心选项
      fzxOptions: [],
      tableData: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    this.getList()
    if (this.isOnline) {
      // 查询分中心列表
      getBranchData().then(({ data }) => {
        this.fzxOptions = data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleSearch() {
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
    resetQuery() {
      this.queryParams.xsjl = ''
      this.queryParams.date = ''
      this.queryParams.depart = '2'
      this.handleSearch()
    },
    // 导出
    exportData() {
      this.download('/finance/individualCustStatistics/export', this.queryParams, `客户销售统计.xlsx`)
    },
  },
}
</script>
