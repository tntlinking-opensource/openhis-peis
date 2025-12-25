<!-- 收费明细  开发人：麦沃德科技半夏/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom" @submit.native.prevent>
      <el-form-item prop="startTime" label="缴费时间">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <!--  patientcode -- patientCode-->
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:inspect_cost:finance_count:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="rowDblClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="收费方式" prop="paywayName" align="center" show-overflow-tooltip />
        <el-table-column label="收费时间" prop="feechargetime" align="center" show-overflow-tooltip />
        <el-table-column label="是否预收" prop="sfys" align="center">
          <template slot-scope="scope">
            <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.sfys == 1">
              <i class="el-icon-check"></i>
            </el-tag>
            <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag> </template
        ></el-table-column>
        <el-table-column label="预交金额" prop="ysje" align="center" show-overflow-tooltip />
        <el-table-column label="实收金额" prop="ssmoney" align="center" show-overflow-tooltip />
        <el-table-column label="实际费用产生时间" prop="sstime" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page-sizes="[20, 50, 100, 200 ,500]"  :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { page, exportTable } from '@/api/statistical/inspect_cost/finance_count.js'
export default {
  name: 'Finance_count',
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
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        patientCode: undefined,
      },
      // 体检类型
      typeOptions: [
        { id: '1', text: '弃检' },
        { id: '2', text: '补检' },
        { id: '3', text: '拒检' },
        { id: '4', text: '迟检' },
      ],
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      page(this.queryParams)
        .then((res) => {
          this.tableList = res.data.records
          this.total = res.data.total
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
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
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
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表格双击事件
    rowDblClick(row) {
      const obj = { path: '/reception/registration', name: 'Registration' }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name: obj.name, params: { patientcode: row.patientcode, id: row.id } })
      })
    },
    // 导出
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/financeCount/export', this.queryParams, `收费明细统计表.xlsx`)
    },
  },
}
</script>
