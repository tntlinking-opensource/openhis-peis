<!-- 职业健康检查结果汇总表(按单位)	开发人：麦沃德科技矢北/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="branchIds" label="分中心">
        <el-select v-model="queryParams.branchIds" clearable placeholder="请选择">
          <el-option v-for="item in fzxOptions" :key="item.id" :value="item.id" :label="item.fzx"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleLook" v-hasPermi="['statistical:professioncChecks:summaryTable:export']">导出Excel</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" type="index" width="65" align="center" />
        <el-table-column fixed label="用人单位名称" prop="khdwmc" width="200" align="center" show-overflow-tooltip />
        <el-table-column label="上岗前体检" align="center">
          <el-table-column label="体检人数" prop="sgq" align="center" width="120"> </el-table-column>
          <el-table-column prop="sgqJjz" align="center" label="禁忌症人数" width="120"> </el-table-column>
        </el-table-column>
        <el-table-column label="在岗体检" align="center">
          <el-table-column label="体检人数" prop="zg" align="center" width="120"> </el-table-column>
          <el-table-column prop="zgJjz" align="center" label="禁忌症人数" width="120"> </el-table-column>
          <el-table-column prop="zgZyb" align="center" label="可疑职业病人数" width="120"> </el-table-column>
          <el-table-column prop="zgFc" align="center" label="需要观察复查人数" width="120"> </el-table-column>
        </el-table-column>
        <el-table-column label="离岗体检" align="center">
          <el-table-column label="体检人数" prop="lg" align="center" width="120"> </el-table-column>
          <el-table-column prop="lgZyb" align="center" label="可疑职业病人数" width="120"> </el-table-column>
          <el-table-column prop="lgFc" align="center" label="需要观察复查人数" width="120"> </el-table-column>
        </el-table-column>
        <el-table-column label="应急体检" align="center">
          <el-table-column label="体检人数" prop="yj" align="center" width="120"> </el-table-column>
          <el-table-column prop="yjZyb" align="center" label="可疑职业病人数" width="120"> </el-table-column>
          <el-table-column prop="yjFc" align="center" label="需要观察复查人数" width="120"> </el-table-column>
        </el-table-column>
        <el-table-column label="离岗随访" align="center">
          <el-table-column label="体检人数" prop="lgsf" align="center" width="120"> </el-table-column>
          <el-table-column prop="lgsfZyb" align="center" label="可疑职业病人数" width="120"> </el-table-column>
          <el-table-column prop="lgsfFc" align="center" label="需要观察复查人数" width="120"> </el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { page, exportComponyQuery, getBranchData } from '@/api/statistical/professionchecks/summary_table.js'

export default {
  data() {
    return {
      total: 50,
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map((item) => item.id)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      loading: false,
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        branchIds: undefined,
      },
      // 科室列表
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '', //请求连接
        bindValue: '',
      },
      // 科室列表
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '', //请求连接
        bindValue: '',
      },
      tableList: [],
      // 性别
      sexOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      fzxOptions: [],
    }
  },
  mounted() {
    this.getBasic()
    this.getList()
  },
  methods: {
    //获取查询基本数据
    getBasic() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    },
    ///搜素
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    ///请求列表
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
    ///重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleLook() {
      let obj = {
        branchIds: this.queryParams.branchIds,
        startTime: this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined,
        endTime: this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined,
      }
      this.download('/statistics/componyQuery/exportComponyQuery', obj, ` 职业健康检查结果汇总表(按单位).xlsx`)
    },
    // 科室名称选择结果
    selectChange1(value) {
      this.queryParams.jcId = value.id
    },
    // 科室名称选择结果
    selectChange2(value) {
      this.queryParams.jcId = value.id
    },
  },
}
</script>

<style></style>
