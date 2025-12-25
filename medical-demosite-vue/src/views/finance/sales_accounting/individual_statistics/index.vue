<!-- 个检销售统计 开发人：麦沃德科技 清风\予安 -->
<template>
  <div class="app-container flex-direction-column sales-statistics">
    <el-form :inline="true" size="small" ref="queryForm" :model="queryParams" @submit.native.prevent>
      <el-form-item label="分中心" prop="branchIds">
        <el-select style="width: 230px" v-model="queryParams.branchIds" placeholder="请选择" clearable @change="handleSearch">
          <el-option v-for="(item, index) in fzxOptions" :key="index" :value="item.branchId" :label="item.fzx"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师" prop="xsjl">
        <el-input style="width: 230px" v-model="queryParams.xsjl" placeholder="汉字模糊" @keyup.enter.native="handleSearch"></el-input>
      </el-form-item>
      <el-form-item label="登记日期从" prop="startTime">
        <el-date-picker value-format="yyyy-MM-dd" type="date" v-model="queryParams.startTime" style="width: 200px" @change="handleSearch"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker value-format="yyyy-MM-dd" type="date" v-model="queryParams.endTime" style="width: 200px" @change="handleSearch"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportData(1)" v-hasPermi="['finance:salesAccounting:salesStatistics:export']">导出</el-button>
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportData(2)" v-hasPermi="['finance:salesAccounting:salesStatistics:exportDay']">按天导出</el-button>
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportListData()" v-hasPermi="['finance:salesAccounting:salesStatistics:exportDay']">关联数据导出</el-button>
      </el-form-item>
    </el-form>
    <drag-row left-size="60%" right-size="40%">
      <template #leftBox>
        <div class="table-box left-table-height">
          <el-table ref="leftTable" :data="tableDataL" v-loading="loading" border stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center" min-width="50px"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center" min-width="55"></el-table-column>
            <el-table-column prop="fzx" label="分中心" align="center" min-width="160" show-overflow-tooltip></el-table-column>
            <el-table-column prop="xsjl" label="开单医师" align="center" min-width="100"></el-table-column>
            <el-table-column prop="yjhj" label="原价合计" align="center" min-width="100"></el-table-column>
            <el-table-column prop="sshj" label="实收合计" align="center" min-width="100"></el-table-column>
            <el-table-column prop="addorgprice" label="加项原价合计" align="center" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column prop="addprice" label="科室加项费用" align="center" min-width="100"></el-table-column>
            <el-table-column prop="count" label="人数" align="center" min-width="100"></el-table-column>
            <el-table-column prop="zk" label="折扣" align="center" min-width="100"></el-table-column>
            <el-table-column prop="kdj" label="客单价" align="center" min-width="100"></el-table-column>
            <el-table-column prop="costprice" label="成本价" align="center" min-width="100"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="totalL" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </template>
      <template #rightBox>
        <div class="table-box left-table-height">
          <el-table :data="tableDataR" v-loading="loadingR" border stripe height="100%">
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="tjh" min-width="120px" label="体检号" align="center"></el-table-column>
            <el-table-column prop="gjrymc" min-width="100px" label="个检人员名称" align="center"></el-table-column>
            <el-table-column prop="yj" min-width="110px" label="原价" align="center"></el-table-column>
            <el-table-column prop="ss" min-width="110px" label="实收" align="center"></el-table-column>
            <el-table-column prop="zk" min-width="80px" label="折扣" align="center"></el-table-column>
            <el-table-column prop="regDate" min-width="160px" label="登记日期" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="totalR" :page.sync="tableParamsR.current" :limit.sync="tableParamsR.size" @pagination="handleRightTable" />
      </template>
    </drag-row>
  </div>
</template>

<script>
import { getListData, getDetailsData } from '@/api/finance/sales_accounting/individual_statistics.js'
import { getBranchData } from '@/api/finance/sales_accounting/sales_statistics.js'
import { getUserCid } from '@/api/system/branch.js'

import { getDate } from '@/utils/getDate.js'
import { getCookie } from '@/utils/getCookie.js'

import dragRow from '@/components/DragRow'
export default {
  components: { dragRow },

  data() {
    return {
      // 筛选
      queryParams: {
        current: 1,
        size: 20,
        branchIds: undefined,
        startTime: undefined,
        endTime: undefined,
        tjlx: undefined,
        idOrg: undefined,
        xsjlid: undefined,
        xsjl: '',
      },
      // 分中心选项
      fzxOptions: [],
      // 左侧表格数据
      loading: false,
      tableDataL: [],
      totalL: 0,
      // 选中的数据
      ids: [],
      // 右侧页数参数
      tableParamsR: {
        current: 1,
        size: 20,
      },
      // 右侧表格数据
      loadingR: false,
      tableDataR: [],
      totalR: 0,
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    this.queryParams.startTime = getDate().split(' ')[0]
    this.queryParams.endTime = getDate().split(' ')[0]
    if (this.isOnline) {
      getBranchData().then(({ data }) => {
        this.fzxOptions = data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
    this.getList()
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
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.startTime = queryParams.startTime ? queryParams.startTime + ' 00:00:00' : undefined
      queryParams.endTime = queryParams.endTime ? queryParams.endTime + ' 23:59:59' : undefined
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
      this.queryParams.branchIds = parseInt(getCookie('cid'))
      this.handleSearch()
    },
    // 获取右侧表格数据
    handleRightTable() {
      this.loadingR = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.startTime = queryParams.startTime ? queryParams.startTime + ' 00:00:00' : undefined
      queryParams.endTime = queryParams.endTime ? queryParams.endTime + ' 23:59:59' : undefined
      getDetailsData({
        ...queryParams,
        ...this.tableParamsR,
        id: this.ids.id,
        type: 2,
      })
        .then(({ data }) => {
          this.tableDataR = data.records
          this.totalR = data.total
          this.loadingR = false
        })
        .catch(() => {
          this.loadingR = false
        })
    },
    // 左侧表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = undefined
        this.tableDataR = []
      } else if (selection.length == 1) {
        this.ids = selection[0]
        this.handleRightTable()
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
    // 导出及按天导出
    exportData(type) {
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.startTime = queryParams.startTime ? queryParams.startTime + ' 00:00:00' : undefined
      queryParams.endTime = queryParams.endTime ? queryParams.endTime + ' 23:59:59' : undefined
      let url = type == 1 ? '/finance/individualStatistics/export' : '/finance/individualStatistics/exportByDay'
      this.download(
        url,
        {
          ...queryParams,
        },
        `个检销售统计${type == 2 ? '(按天)' : '' + new Date().getTime()}.xlsx`
      )
    },
    // 导出右侧关联数据
    exportListData() {
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.startTime = queryParams.startTime ? queryParams.startTime + ' 00:00:00' : undefined
      queryParams.endTime = queryParams.endTime ? queryParams.endTime + ' 23:59:59' : undefined
      let url = '/finance/individualStatistics/exportListData '
      this.download(
        url,
        {
          ...queryParams,
        },
        `个检销售统计关联数据${new Date().getTime()}.xlsx`
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
