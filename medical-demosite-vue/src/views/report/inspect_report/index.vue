<!-- 检验报告 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <div>
        <el-form-item label="登记日期:" prop="date">
          <el-date-picker v-model="queryParams.date" style="width: 320px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="单位:" prop="idOrg">
          <input-select :selectData="currencyData" selectWidth="140" @change="currencyDataChange"></input-select>
        </el-form-item>
        <el-form-item label="打印状态:" prop="printStatus">
          <el-select v-model="queryParams.printStatus" placeholder="请选择" clearable style="width: 140px">
            <el-option label="未打印" :value="0" />
            <el-option label="已打印" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="体检号:" prop="patientcode">
          <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="姓名:" prop="patientname">
          <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
          <el-button type="primary" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="纸类型:" prop="paper">
          <el-select v-model="queryParams.paper" style="width: 180px">
            <el-option label="半张A4" :value="1" />
            <el-option label="A4" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" :disabled="single" @click="printBotton">打印</el-button>
        </el-form-item>
      </div>
    </el-form>

    <el-row :gutter="20" style="height: 80%">
      <!-- 左侧页面 -->
      <el-col :span="13" style="height: 100%">
        <div class="table-box" style="height: 100%">
          <el-table border v-loading="loading" id="setTable" ref="leftTable" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="体检号" prop="patientcode" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="idSex" min-width="100px" align="center">
              <template slot-scope="scope">
                {{ ['男', '女'][scope.row.idSex] }}
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="登记时间" prop="dateregister" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="打印次数" prop="printCount" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="打印时间" prop="printTime" min-width="140px" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>
      <!-- 右侧页面 -->
      <el-col :span="11" style="height: 100%">
        <div class="table-box" style="height: 100%">
          <el-table border v-loading="rightLoading" id="setTable" :data="tableListtwo" height="100%" stripe>
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="项目" prop="examitemNameprn" align="center" show-overflow-tooltip />
            <el-table-column label="结果" prop="examitemvaluesreport" align="center" show-overflow-tooltip />
            <el-table-column label="参考值" prop="refrange" align="center" show-overflow-tooltip />
            <el-table-column label="单位" prop="units" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <pagination :total="totalRight" :page.sync="rightParams.current" :limit.sync="rightParams.size" @pagination="getRightData" />
        <!-- 分页 -->
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";

import { getListData, getRightListData, createReport } from '@/api/report/inspect_report'

export default {
  name: "Inspect_report",
  props: [],
  data() {
    return {
      rightLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      totalRight: 0,
      // 非单个禁用
      single: true,
      // 显示搜索条件
      selection: undefined,
      showSearch: true,
      rightParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        printStatus: undefined,
        idOrg: undefined,
        patientcode: '',
        patientname: '',
        date: undefined,
        paper: 2,
      },
      // 列表
      currencyData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/query/inspecting/getOrgs', //请求连接
        firstName: 'khdwsrm',
        secondName: 'khdwmc',
        bindValue: '',
      },
      // 表格展示数据
      tableList: [],
      tableListtwo: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 选择标本类型
    currencyDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    handleSelectionChange(selection) {
      this.selection = selection[0]
      this.single = selection.length == 0
      if (selection.length == 1) {
        this.rightParams.patientcode = selection[0].patientcode
        this.getRightData()
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else {
        this.selectId = ''
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    getRightData() {
      this.rightLoading = true
      getRightListData(this.rightParams).then((response) => {
        this.tableListtwo = response.data.records
        this.totalRight = response.data.total
        this.rightLoading = false
      })
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.current = 1
      this.getList()
    },
    printBotton() {
      this.loading = true
      createReport({ patientCodes: [this.selection.patientcode] })
        .then(() => {
          this.loading = false
          var query = {
            patientcode: this.selection.patientcode,
            idExamtype: this.selection.idExamtype,
            isHalf: this.queryParams.paper,
          }
          let routeUrl = this.$router.resolve({
            name: 'IndividualInspectReport',
            query: query,
          })
          window.open(routeUrl.href)
        })
        .catch((error) => {
          this.loading = false
          console.error(error)
        })
    },
    // 重置
    resetQuery() {
      this.queryParams.date = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    reset() {
      this.resetForm('queryForm')
    },
  },
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
