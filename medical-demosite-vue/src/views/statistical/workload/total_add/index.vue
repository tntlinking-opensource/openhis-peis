<!-- 加项工作量  开发人：麦沃德科技 予安 / 暴雨-->
<template>
  <div class="app-container flex-direction-column total-add-main">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="选择日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="加项医生" prop="doctorId">
        <input-select :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:workload:totalAdd:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="统计日期（从）" prop="startTime" align="center" />
        <el-table-column label="统计日期（到）" prop="endTime" align="center" />
        <el-table-column label="加项医生" prop="jxys" align="center" />
        <el-table-column label="加项总价格" prop="price" align="center">
          <template slot-scope="scope">
            {{ scope.row.price ? Number(scope.row.price).toFixed(2) : '' }}
          </template>
        </el-table-column>
        <el-table-column label="加项总优惠" prop="factprice" align="center">
          <template slot-scope="scope">
            {{ scope.row.factprice ? Number(scope.row.factprice).toFixed(2) : '' }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListdata } from '@/api/statistical/total_add'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        divisionId: undefined,
        doctorId: undefined,
      },
      // 医师参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '医师姓名', //第二列标题
        url: '/report/healthGetReport/getAllUserData', //请求连接
        selectWidth: 200, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
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
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListdata(this.queryParams)
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
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 医师选择返回值
    selectChange(value) {
      this.queryParams.doctorId = value.id
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.selectData, 'bindValue')
      this.$set(this.selectData, 'bindValue', '')
      this.handleQuery()
    },
    // 导出
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/totalAddWork/export', this.queryParams, `工作量统计-加项工作量.xlsx`)
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
<style lang="scss">
.total-add-main {
  .el-table .cell {
    font-size: 14px;
    color: #000;
  }
}
</style>
