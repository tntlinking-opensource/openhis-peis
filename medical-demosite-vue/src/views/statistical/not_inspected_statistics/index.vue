<!-- 已登记未检客户统计  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column client-statistics">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择" clearable ref="fzx">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="khdwmc">
        <el-input style="width: 180px" v-model="queryParams.khdwmc" placeholder="请输入单位名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="体检人" prop="patientname">
        <el-input style="width: 180px" v-model="queryParams.patientname" placeholder="请输入体检人" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport">导出</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="分中心" prop="fzx" align="center" width="120" show-overflow-tooltip />
        <el-table-column label="单位名称" prop="khdwmc" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientCode" align="center" show-overflow-tooltip />
        <el-table-column label="体检人" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" align="center" show-overflow-tooltip />
        <el-table-column label="收费方式" prop="payway" align="center" show-overflow-tooltip />
        <el-table-column label="未检项目" prop="examfeeitemName" align="center" show-overflow-tooltip />
        <el-table-column label="原价" prop="moneyamount" align="center" show-overflow-tooltip />
        <el-table-column label="优惠价" prop="moneyamountpaid" align="center" show-overflow-tooltip />
        <!-- <el-table-column label="备单分类" prop="tjlx" align="center" width="90">
          <template slot-scope="scope">
            {{ ['健康', '职业', '综合'][scope.row.tjlx] }}
          </template>
        </el-table-column> -->
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/statistical/not_inspected_statistics.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
export default {
  name: 'Not_inspected_statistics',
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 30,
        branchIds: undefined,
        startTime: undefined,
        endTime: undefined,
        khdwmc: undefined,
        patientname: undefined,
      },
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 表格数据
      tableList: [],
      // 选中项的id
      selectId: undefined,
      // 分中心列表
      fzxOptions: [],
    }
  },
  created() {
    // 获取分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
      this.queryParams.branchIds = this.$getCookie('cid')
    })
    let nowTime = this.$getDate().split(' ')[0]
    this.queryParams.startTime = nowTime
    this.queryParams.endTime = nowTime
    this.handleQuery()
  },
  methods: {
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
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
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
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
      } else if (selection.length == 1) {
        this.selectId = selection[0].id
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/registrationNotCheck/export', this.queryParams, `人员信息.xlsx`)
    },
  },
}
</script>
