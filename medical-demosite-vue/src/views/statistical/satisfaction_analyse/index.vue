<!-- 科室满意度排名  开发人：麦沃德科技 予安/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="选择日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="医师" prop="name">
        <input-select :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="科室" prop="divisionId">
        <input-select :selectData="selectData1" @change="selectChange1"></input-select>
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
        <el-table-column label="科室" prop="depName" align="center" />
        <!-- doctorName -- doctorname -->
        <el-table-column label="医生" prop="doctorname" align="center" />
        <el-table-column label="总分" prop="points" align="center" />
        <el-table-column label="排名" prop="rowid" align="center" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { page } from '@/api/statistical/satisfaction_analyse.js'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        name: undefined,
        divisionId: undefined,
      },
      // 医师参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/drugstore/prescribe/getDoctor', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: '', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 科室参数
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getKsData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: '', //向接口传递的参数名(不传默认为'inputCode')
      },
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
      // 表格数据
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
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 医师选择返回值
    selectChange(value) {
      this.queryParams.name = value.username
    },
    // 科室选择返回值
    selectChange1(value) {
      this.queryParams.divisionId = value.id
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
      this.$delete(this.selectData, 'bindValue')
      this.$delete(this.selectData1, 'bindValue')
      this.$set(this.selectData, 'bindValue', '')
      this.$set(this.selectData1, 'bindValue', '')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
