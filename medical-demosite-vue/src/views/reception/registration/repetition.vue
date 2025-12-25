<template>
  <el-dialog title="最近体检人员列表" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" :close-on-click-modal="false">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体检号" prop="patientcode">
        <el-input-number v-model="queryParams.patientcode" controls-position="right" style="width: 180px" @keyup.enter.native="handleQuery"></el-input-number>
      </el-form-item>
      <el-form-item label="姓名" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入姓名拼音码" clearable style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="来检时间">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table border ref="table" id="setTable" v-loading="loading" :data="tableList" height="485px" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="submitForm">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="体检号" prop="patientcode" align="center" width="120" />
      <el-table-column label="姓名" prop="patientname" align="center" width="170" />
      <el-table-column label="性别" prop="idSex" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.idSex == 0">男</span>
          <span v-if="scope.row.idSex == 1">女</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" prop="age" align="center" />
      <el-table-column label="体检套餐" prop="tcmc" align="center" show-overflow-tooltip />
      <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip />
      <el-table-column label="团体分组" prop="groupname" align="center" show-overflow-tooltip />
    </el-table>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getPatientForRegister } from '@/api/reception/registration'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        inputCode: undefined,
        date: undefined,
      },
      // 表格数据
      tableList: [],
      // 选中数据
      selectRow: undefined,
      // 表格加载中
      loading: false,
      // 总数
      total: 0,
    }
  },
  created() {
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 7 * 3600 * 24 * 1000
    this.queryParams.date = [this.$getDate(past7daysStart).split(' ')[0], this.$getDate().split(' ')[0]]
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.open = true
      this.handleQuery()
    },
    // 获取表格数据
    getList() {
      this.loading = true
      getPatientForRegister(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 查询
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
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.selectRow = undefined
      } else if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.table.clearSelection() //清空表格数据
        this.$refs.table.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col && col.type == 'selection') {
        return
      }
      this.$refs.table.clearSelection()
      this.$refs.table.toggleRowSelection(row)
      this.selectRow = row
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      if (!this.selectRow) {
        this.$modal.alertWarning('请选中一条记录！')
        return
      }
      this.open = false
      this.$emit('setLookSimilarPeople', this.selectRow)
    },
  },
}
</script>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
