<!-- 新产品数据查询  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="登记日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="收费项目名称" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="请输入收费项目名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="单位名称" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入单位名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开单医生名称" prop="doctorapply">
        <el-input v-model="queryParams.doctorapply" placeholder="请输入开单医生名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 230px" multiple collapse-tags>
          <el-option v-for="options in fzxOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleExport">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="团体id" prop="intId" align="center" width="100" />
        <el-table-column label="单位名称" prop="orgName" align="center" width="240" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" />
        <el-table-column label="原价" prop="unitprice" align="center" />
        <el-table-column label="优惠价" prop="factprice" align="center" />
        <el-table-column label="项目名称" prop="itemName" align="center" width="240" show-overflow-tooltip />
        <el-table-column label="团检/个检" prop="type" align="center" />
        <el-table-column label="开单医生	" prop="doctorapply" align="center" />
        <el-table-column label="是否加项" prop="isAdd" align="center">
          <template slot-scope="scope">
            {{ ['否', '是'][scope.row.isAdd] }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/product_query.js'
import { fzxApi } from '@/api/finance/inspect_accounts.js'
export default {
  name: 'Product_query',
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
        itemName: undefined,
        orgName: undefined,
        doctorapply: undefined,
        branchIds: undefined,
      },
      // 总条数
      total: 0,
      // 排检表格数据
      tableList: [],
      // 分中心列表
      fzxOptions: [],
    }
  },
  created() {
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    //  上个月的天数
    const days = this.lastMonthDats()
    //  上月开始时间戳
    const lastMonthStart = new Date(timeStamp).getTime() - days * 24 * 3600 * 1000
    this.queryParams.startTime = this.$getDate(lastMonthStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getFzxList()
    this.getList()
  },
  methods: {
    // 获取上个月共几天
    lastMonthDats() {
      const date = new Date()
      const year = date.getFullYear()
      //  上个月月份
      let month = date.getMonth() + 1 - 1 //  0-11 表示 1月-12月
      //  0 表示12月
      month = month || 12
      //  30天的月份
      const arr30 = [4, 6, 9, 11]
      //  31天的月份
      const arr31 = [1, 3, 5, 7, 8, 10, 12]
      if (arr30.indexOf(month) !== -1) {
        //  上个月是 30 天
        return 30
      } else if (arr31.indexOf(month) !== -1) {
        //  上个月是 31 天
        return 31
      } else {
        //  2月
        if (this.isRunYear(year)) {
          return 29
        } else {
          return 28
        }
      }
    },
    isRunYear(year) {
      //  条件:能被4整除并且不能被100整除，或者被400整除的
      let flag = false
      if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
        flag = true
      }
      return flag
    },
    // 获取分中心
    getFzxList() {
      fzxApi().then(({ data }) => {
        this.fzxOptions = data
        this.getList()
      })
    },
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
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      if (queryParams.startTime) {
        queryParams.startTime += ' 00:00:00'
      }
      if (queryParams.endTime) {
        queryParams.endTime += ' 23:59:59'
      }
      this.download('/query/geneQuery/export', queryParams, `新产品数据查询 .xlsx`)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
  },
}
</script>
