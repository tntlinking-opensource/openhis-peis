<!-- 科室拒检查询  开发人：麦沃德科技半夏 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="体检单位" prop="idOrg">
        <input-select :selectData="labTypeData" @change="labTypeChange"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="单位名称" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" align="center" show-overflow-tooltip />
        <el-table-column label="总检综述" prop="summarize" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/original_rejection'

export default {
  name: 'Original_rejection',
  components: {},
  props: [],
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
      labTypeData: {
        placeholder: '请输入输入码选择',
        value: '单位名称',
        url: '/sell/customer/getAllOrg',
        firstName: 'khdwmc',
        secondName: 'khdwmc',
        bindValue: '', //初始值(必传)
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        khdwmc: undefined,
        ddh: undefined,
        patientcode: undefined,
        patientname: undefined,
        idOrg: undefined,
      },
      // 排检表格数据
      tableList: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() {},
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      }
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.labTypeData.bindValue = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 选择体检单位
    labTypeChange(value) {
      this.queryParams.idOrg = value.id
    },
  },
}
</script>
<style lang="scss"></style>
