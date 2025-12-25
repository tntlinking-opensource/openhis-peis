<!-- 销售团检统计 开发人：麦沃德科技 清风\予安 -->
<template>
  <div class="app-container flex-direction-column sales-statistics">
    <el-form :inline="true" size="small" ref="queryForm" :model="queryParams" @submit.native.prevent>
      <el-form-item label="分中心" prop="fzxid">
        <el-select style="width: 230px" v-model="queryParams.fzxid" placeholder="请选择">
          <el-option v-for="(item, index) in fzxOptions" :key="index" :value="item.branchId" :label="item.fzx"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间从" prop="valueDate">
        <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.valueDate" style="width: 366px" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select style="width: 130px" v-model="queryParams.tjlx" placeholder="请选择" clearable>
          <el-option v-for="(item, index) in tjlxOptions" :key="index" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="idOrg">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.idOrg"></input-select>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjlid">
        <input-select :selectData="selectData2" @change="selectChange2" :initialValue="queryParams.xsjlid"></input-select>
      </el-form-item>
      <el-form-item label="团体ID" prop="intId">
        <el-input style="width: 230px" v-model="queryParams.intId" placeholder="请输入" type="number"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportData" v-hasPermi="['finance:salesAccounting:salesStatistics:export']">导出</el-button>
      </el-form-item>
    </el-form>
    <drag-row left-size="60%" right-size="40%">
      <template #leftBox>
        <div class="table-box left-table-height">
          <el-table ref="leftTable" :data="tableDataL" v-loading="loading" border stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center" min-width="50px"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center" min-width="55"></el-table-column>
            <el-table-column prop="xsjl" label="销售经理" align="center" min-width="100"></el-table-column>
            <el-table-column prop="kdzl" label="开单助理" align="center" min-width="100"></el-table-column>
            <el-table-column prop="intId" label="团体ID" align="center" min-width="80"></el-table-column>
            <el-table-column prop="khdwmc" label="客户单位名称" align="center" min-width="200" show-overflow-tooltip></el-table-column>
            <el-table-column prop="yj" label="原价" align="center" min-width="100"></el-table-column>
            <el-table-column label="平均变动成本率" align="center" min-width="90px">
              <template slot-scope="scope">
                <span>{{ scope.row.variableCostRate ? Math.round(scope.row.variableCostRate * 100) + '%' : '-' }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="ss" label="实收" align="center" min-width="100"></el-table-column>
            <el-table-column prop="rs" label="人数" align="center" min-width="80"></el-table-column>
            <el-table-column prop="zk" label="折扣" align="center" min-width="80"></el-table-column>
            <el-table-column prop="kdj" label="客单价" align="center" min-width="100"></el-table-column>
            <el-table-column prop="tsfy" label="统收费用" align="center" min-width="100"></el-table-column>
            <el-table-column prop="jxfy" label="加项费用" align="center" min-width="100"></el-table-column>
            <el-table-column prop="jxrs" label="加项人数" align="center" min-width="80"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="totalL" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200 ,500]"/>
      </template>
      <template #rightBox>
        <div class="table-box" style="height: 100%">
          <el-table :data="tableDataR" v-loading="loadingR" border stripe height="100%">
            <el-table-column type="index" label="序列" width="55" align="center" min-width="55"></el-table-column>
            <el-table-column prop="order" label="订单号" align="center" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column prop="khdwmc" label="客户单位名称" align="center" min-width="200" show-overflow-tooltip></el-table-column>
            <el-table-column prop="ts" label="统收" align="center" min-width="100"></el-table-column>
            <el-table-column prop="qt" label="其他" align="center" min-width="100"></el-table-column>
            <el-table-column prop="hj" label="合计" align="center" min-width="100"></el-table-column>
            <el-table-column prop="jz" label="记账" align="center" min-width="100"></el-table-column>
            <el-table-column prop="regDate" label="备单日期" align="center" min-width="160"></el-table-column>
            <el-table-column prop="firstDate" label="首位顾客来检日期" align="center" min-width="160"></el-table-column>
          </el-table>
        </div>
      </template>
    </drag-row>
  </div>
</template>

<script>
import { getListData, getTotalList, getBranchData } from '@/api/finance/sales_accounting/sales_statistics.js'

import { getDate } from '@/utils/getDate.js'
import Cookies from 'js-cookie'

import dragRow from '@/components/DragRow'
export default {
  components: { dragRow },

  data() {
    return {
      // 筛选 
      queryParams: {
        current: 1,
        size: 20,
        fzxid: undefined,
        valueDate: undefined,
        startTime: undefined,
        endTime: undefined,
        jhjqc: undefined,
        jhjqd: undefined,
        tjlx: undefined,
        idOrg: undefined,
        xsjlid: undefined,
        intId: '',
      },
      // 单位名称筛选参数 
      selectData: {
        placeholder: '请输入输入码选择',
        value: '单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 销售经理筛选参数
      selectData2: {
        placeholder: '请输入输入码选择',
        value: '销售经理', //第二列标题
        url: '/reception/order/getAllUserSql2', //请求连接
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 分中心选项
      fzxOptions: [],
      // 体检类型
      tjlxOptions: [
        { id: 2, text: '所有' },
        { id: 0, text: '健康' },
        { id: 1, text: '职业' },
      ],
      // 左侧表格数据
      loading: false,
      tableDataL: [],
      totalL: 0,
      // 选中的数据
      ids: [],
      // 右侧表格数据
      loadingR: false,
      tableDataR: [],
    }
  },
  created() {
    // 默认选中当前分中心
    this.queryParams.fzxid = Cookies.get('cid')

    this.queryParams.valueDate = [getDate().split(' ')[0], getDate().split(' ')[0]]
    this.getList()
    // 查询分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
  },
  methods: {
    // 单位名称返回值
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 销售经理返回值
    selectChange2(value) {
      this.queryParams.xsjlid = value.id
    },
    // 分页查询  
    getList() {
      this.loading = true
      let queryParams = this.queryParams
      if (this.queryParams.valueDate) {
        this.queryParams.startTime = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.valueDate[1] + ' 23:59:59'
        this.queryParams.jhjqc = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.jhjqd = this.queryParams.valueDate[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
        this.queryParams.jhjqc = undefined
        this.queryParams.jhjqd = undefined
      }
      getListData(queryParams)
        .then(({ data }) => {
          this.tableDataL = data.records
          this.totalL = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 搜索
    handleSearch() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleSearch()
    },
    // 左侧表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = undefined
        this.tableDataR = []
      } else if (selection.length == 1) {
        this.ids = selection[0]
        this.loadingR = true
        this.queryParams.jhjqc = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.jhjqd = this.queryParams.valueDate[1] + ' 23:59:59'
        getTotalList({
          ...this.queryParams,
          id: this.ids.intId,
        })
          .then(({ data }) => {
            this.tableDataR = data
            this.loadingR = false
          })
          .catch(() => {
            this.loadingR = false
          })
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件 
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    // 导出 
    exportData() {
      let queryParams = this.queryParams
      if (this.queryParams.valueDate && this.queryParams.valueDate.length) {
        this.queryParams.startTime = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.valueDate[1] + ' 23:59:59'
        this.queryParams.jhjqc = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.jhjqd = this.queryParams.valueDate[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = this.queryParams.endTime = undefined
        this.queryParams.jhjqc = this.queryParams.jhjqd = undefined
      }
      this.download(
        '/finance/healthyStatistics/export',
        {
          ...queryParams,
        },
        `销售团检统计_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
<style scoped>
.sales-statistics .el-form-item {
  margin-bottom: 10px;
}
.sales-statistics /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.sales-statistics .left-table-height {
  height: calc(100% - 50px);
}
</style>
