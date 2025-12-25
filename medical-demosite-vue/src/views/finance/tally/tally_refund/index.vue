<!-- 记账结算 开发人：麦沃德科技暴雨/予安 -->
<template>
  <div class="app-container flex-direction-column tally-refund">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入拼音码" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="记账单位" prop="jzdw">
        <el-input v-model="queryParams.jzdw" placeholder="请输入记账单位" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="记账日期">
        <el-date-picker style="width: 360px" value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.dateValue" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single" @click="handleRefund" v-hasPermi="['finance:tally:tallyRefund:refund']">结算 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport" v-hasPermi="['finance:tally:tallyRefund:export']">导出 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="团体" prop="orgName" align="center" min-width="120px" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" align="center" min-width="120px" show-overflow-tooltip />
        <el-table-column label="姓名" prop="name" align="center" min-width="120px" show-overflow-tooltip />
        <el-table-column label="记账金额" prop="jzje" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="记账人" prop="jzr" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="记账单位" prop="jzdw" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="审批人" prop="spr" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="记账日期" prop="jzDate" align="center" min-width="160px" show-overflow-tooltip />
        <el-table-column label="结算金额" prop="ypid" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="结算状态" prop="jszt" align="center" min-width="100px" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.paid <= 0">已结算</el-tag>
            <el-tag type="danger" v-else>未结算</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="note" align="center" min-width="100px" show-overflow-tooltip />
      </el-table>
    </div>
    <div style="padding: 5px 30px 0">
      记账总合计: <span style="color: #1890ff">{{ chargeCount }} 元</span>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 结算对话框 -->
    <settlement ref="settlement" @settlementSuccess="settlementSuccess"></settlement>
  </div>
</template>
<script>
import { getListApi } from '@/api/finance/tally/tally_refund.js'
import settlement from './settlement.vue'

export default {
  components: { settlement },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 体检团体
      selectData: {
        placeholder: '请输入输入码',
        value: '团体名称', //第二列标题
        url: '/sell/customer/getListDatas', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        jzdw: undefined,
        dateValue: undefined,
        startTime: undefined,
        endTime: undefined,
        js: 0,
      },
      // 表格展示数据
      tableList: [],
      // 记账合计
      chargeCount: 0,
    }
  },
  created() {
    this.queryParams.dateValue = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
  },
  methods: {
    // 查询列表
    getList(fn) {
      this.loading = true
      this.chargeCount = 0
      if (this.queryParams.dateValue) {
        this.queryParams.startTime = this.queryParams.dateValue[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.dateValue[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getListApi(this.queryParams).then(({ data }) => {
        data.records.forEach((el) => {
          if (el.jzje) {
            el.jzje = Number(el.jzje).toFixed(2)
            this.chargeCount += Number(el.jzje)
          }
        })
        this.chargeCount = this.chargeCount.toFixed(2)
        this.tableList = data.records
        this.total = data.total
        this.loading = false
        if (fn) {
          fn()
        }
      })
    },
    // 体检团体返回值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.single = true
        this.ids = undefined
      } else if (selection.length == 1) {
        this.ids = selection[0]
        this.single = selection.length != 1
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 结算完成刷新
    settlementSuccess() {
      let selectIndex = undefined
      this.tableList.forEach((el, index) => {
        if (el.id == this.ids.id) {
          selectIndex = index
        }
      })
      this.getList(() => {
        this.$nextTick(() => {
          this.$refs.tableList.toggleRowSelection(this.tableList[selectIndex], true)
        })
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    //重置方法
    handleReset() {
      this.resetForm('form')
      this.handleQuery()
    },
    //结算
    handleRefund() {
      this.$refs.settlement.showDialog(this.ids)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/finance/tallyQuery/export',
        {
          ...this.queryParams,
        },
        `记账结算.xlsx`
      )
    },
  },
}
</script>
<style scoped>
.tally-refund /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
