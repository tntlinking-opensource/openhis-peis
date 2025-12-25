<!-- 总检工作量  开发人：麦沃德科技 予安 / 暴雨-->
<template>
  <div class="app-container flex-direction-column general-checking-main">
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
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" style="width: 160px">
          <el-option label="健康体检" :value="0"></el-option>
          <el-option label="职业体检" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" style="width: 160px">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <drag-row left-size="60%" right-size="40%">
      <template #leftBox>
        <el-table border v-loading="loading" :data="tableList" height="92%" stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="医生" prop="doctorname" align="center" />
          <el-table-column label="总检日期" prop="examTime" align="center">
            <template slot-scope="scope">
              {{ scope.row.examTime ? scope.row.examTime.split(' ')[0] : '' }}
            </template>
          </el-table-column>
          <el-table-column label="星期" prop="dayForWeek" align="center" />
          <el-table-column label="工作量" prop="total" align="center" />
          <el-table-column label="工作量个人" prop="personnel" align="center" />
          <el-table-column label="工作量团体" prop="org" align="center" />
          <el-table-column label="普通会员工作量" prop="common" align="center" />
          <el-table-column label="VIP工作量" prop="vip" align="center" />
          <el-table-column label="VVIP工作量" prop="vvip" align="center" />
        </el-table>
        <div style="display: flex; align-items: center; justify-content: space-between; width: 100%">
          <span>
            合计工作量:
            <span>{{ totalWork }}</span>
          </span>
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>
      <template #rightBox>
        <el-table border v-loading="loadingR" :data="tableListR" height="92%" stripe @selection-change="handleSelectionChange">
          <el-table-column label="总检医生" prop="doctorName" align="center" />
          <el-table-column label="工作量" prop="total" align="center" />
          <el-table-column label="工作量个人" prop="personnel" align="center" />
          <el-table-column label="工作量团体" prop="org" align="center" />
          <el-table-column label="普通会员工作量" prop="common" align="center" />
          <el-table-column label="VIP工作量" prop="vip" align="center" />
          <el-table-column label="VVIP工作量" prop="vvip" align="center" />
        </el-table>
        <pagination v-show="total2 > 0" :total="total2" :page.sync="queryParamsRight.current" :limit.sync="queryParamsRight.size" @pagination="getListRight" />
      </template>
    </drag-row>
  </div>
</template>
<script>
import dragRow from '@/components/DragRow'
import { getListdata, getListdatas, getBranchData } from '@/api/statistical/general_checking'
export default {
  components: { dragRow },
  props: [],
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        name: undefined,
        tjlx: 0,
        fzx: undefined,
        branchIds: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 右表格参数
      queryParamsRight: {
        current: 1,
        size: 20,
        // dateRange: this.queryParams.dateRange,
        // name: this.queryParams.name,
        // tjlx: this.queryParams.tjlx,
        // fzx: this.queryParams.fzx
      },
      // 医师参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '医师姓名', //第二列标题
        url: '/drugstore/prescribe/getDoctor', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 遮罩层
      loading: false,
      loadingR: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      total2: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      tableListR: [],
      // 分中心
      fzxOptions: [],
      totalWork: 0,
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    getBranchData().then((res) => {
      this.fzxOptions = res.data
    })
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
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    getListRight() {
      this.loadingR = true
      getListdatas(this.queryParams)
        .then((response) => {
          this.tableListR = response.data.records
          this.total2 = response.data.total
          if (this.total2) {
            let totalWork = 0
            this.tableListR.forEach((el) => {
              if (el.doctorName) {
                totalWork += el.total
              }
            })
            this.totalWork = totalWork
          }
          this.loadingR = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingR = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
      this.getListRight()
    },
    // 医师选择返回值
    selectChange(value) {
      this.queryParams.name = value.id
    },

    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.selectData, 'bindValue')
      this.$set(this.selectData, 'bindValue', '')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/generalChecking/export', this.queryParams, `总检工作量.xlsx`)
    },
  },
}
</script>
<style lang="scss">
.general-checking-main {
  .el-table .cell {
    font-size: 14px;
    color: #000;
  }
}
</style>
