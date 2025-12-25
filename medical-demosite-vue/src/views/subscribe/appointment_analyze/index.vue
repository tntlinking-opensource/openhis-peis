<!-- 预约来检情况分析  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column client-statistics">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchId">
        <el-select v-model="queryParams.branchId" placeholder="请选择" clearable ref="fzx" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="khdwmc">
        <el-input style="width: 180px" v-model="queryParams.khdwmc" placeholder="请输入单位名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="团体ID" prop="intId">
        <el-input style="width: 180px" v-model="queryParams.intId" placeholder="请输入团体ID" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="预约日期" prop="reservationDate">
        <el-date-picker v-model="queryParams.reservationDate" type="date" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择预约日期" style="width: 180px" @change="handleQuery"> </el-date-picker>
      </el-form-item>
      <el-form-item label="预约类型" prop="khdwmc">
        <el-select v-model="queryParamsR.type" placeholder="请选择预约类型" style="width: 180px" @change="getListR">
          <el-option label="已预约来检" value="1"></el-option>
          <el-option label="已预约未检" value="2"></el-option>
          <el-option label="未预约体检" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport(1)">导出</el-button>
      </el-col>
    </el-row>
    <el-row style="flex: 1">
      <el-col :span="13" style="height: 100%; display: flex; flex-direction: column; padding-right: 8px">
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="分中心" prop="fzx" align="center" width="120" show-overflow-tooltip />
            <el-table-column label="团体ID" prop="intId" align="center" show-overflow-tooltip />
            <el-table-column label="团体名称" prop="khdwmc" align="center" width="110" show-overflow-tooltip />
            <el-table-column label="VVIP" align="center">
              <el-table-column label="已约已检人数" prop="vvip1" align="center" show-overflow-tooltip />
              <el-table-column label="已约未检人数" prop="vvip2" align="center" show-overflow-tooltip />
              <el-table-column label="未约未检人数" prop="vvip3" align="center" show-overflow-tooltip />
            </el-table-column>
            <el-table-column label="VIP" align="center">
              <el-table-column label="已约已检人数" prop="vip1" align="center" show-overflow-tooltip />
              <el-table-column label="已约未检人数" prop="vip2" align="center" show-overflow-tooltip />
              <el-table-column label="未约未检人数" prop="vip3" align="center" show-overflow-tooltip />
            </el-table-column>
            <el-table-column label="普通会员" align="center">
              <el-table-column label="已约已检人数" prop="ordinary1" align="center" show-overflow-tooltip />
              <el-table-column label="已约未检人数" prop="ordinary2" align="center" show-overflow-tooltip />
              <el-table-column label="未约未检人数" prop="ordinary3" align="center" show-overflow-tooltip />
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        <div class="">
          今日预约人数：<span style="margin-right: 16px; color: red; font-size: 20px; font-weight: 600">{{ personInfo.num1 || 0 }}</span> 已预约未体检人数：<span style="margin-right: 16px; color: red; font-size: 20px; font-weight: 600">{{ personInfo.num2 || 0 }}</span> 未预约体检人数：<span
            style="color: red; font-size: 20px; font-weight: 600"
            >{{ personInfo.num3 || 0 }}</span
          >
        </div>
      </el-col>
      <el-col :span="11" style="height: 100%; display: flex; flex-direction: column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loadingR" :data="tableListR" height="100%" stripe>
            <el-table-column type="selection" width="45" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" width="110" />
            <el-table-column label="姓名" prop="patientname" align="center" width="110" />
            <el-table-column label="客户类型" prop="levelId" align="center" width="90">
              <template slot-scope="scope">
                {{ ['', '普通会员', 'VIP', 'VVIP'][scope.row.levelId] }}
              </template>
            </el-table-column>
            <el-table-column label="预约时间段" prop="reservationDate" align="center" width="140">
              <template slot-scope="scope">
                {{ scope.row.reservationDate || '未预约' }}
              </template>
            </el-table-column>
            <el-table-column label="问卷状态" prop="questionnaire" align="center" width="90">
              <template slot-scope="scope">
                <span :style="scope.row.questionnaire == 1 ? 'color:green' : ''">{{ ['未答', '已答'][scope.row.questionnaire] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="登记时间" prop="dateregister" align="center" width="140" />
            <el-table-column label="开单医生" prop="doctorapply" align="center" width="110" />
            <el-table-column label="备注" prop="note" align="center" width="180" show-overflow-tooltip />
          </el-table>
        </div>
        <pagination :total="totalR" :page.sync="queryParamsR.current" :limit.sync="queryParamsR.size" @pagination="getListR" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getListData, getToday, getRightPage } from '@/api/subscribe/appointment_analyze.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
export default {
  name: 'Appointment_analyze',
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 30,
        branchId: undefined,
        khdwmc: undefined,
        intId: undefined,
        reservationDate: undefined,
      },
      queryParamsR: {
        current: 1,
        size: 30,
        id: '',
        type: '1',
      },
      // 遮罩层
      loading: false,
      loadingR: false,
      // 总条数
      total: 0,
      totalR: 0,
      // 表格数据
      tableList: [],
      // 体检人数
      personInfo: {},
      // 右侧表格数据
      tableListR: [],
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
      this.queryParams.branchId = this.$getCookie('cid')
    })
    this.queryParams.reservationDate = this.$getDate()
    this.handleQuery()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParamsR.current = 1
      this.getList()
      this.getPersonNum()
      this.tableListR = []
    },
    // 重置
    resetQuery() {
      this.queryParams.branchId = this.$getCookie('cid')
      this.queryParamsR.type = '1'
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
    getPersonNum() {
      getToday(this.queryParams).then(({ data }) => {
        this.personInfo = data || {}
      })
    },
    // 查询列表
    getListR() {
      this.loadingR = true
      getRightPage({ ...this.queryParams, ...this.queryParamsR })
        .then(({ data }) => {
          this.tableListR = data.records
          this.totalR = data.total
          this.loadingR = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingR = false
        })
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
      } else if (selection.length == 1) {
        // this.selectId = selection[0].id
        this.queryParamsR.id = selection[0].id
        this.getListR()
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
      this.download(
        '/reservation/reservationAnalysis/export',
        {
          ...this.queryParams,
          ...this.queryParamsR,
        },
        `预约来检情况分析.xlsx`
      )
    },
  },
}
</script>
