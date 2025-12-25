<!-- 职业健康检查拒检补检人员名单	 开发人：麦沃德科技矢北 / 暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="orgIdData" label="体检单位">
        <input-select :selectData="orgIdData" selectWidth="200" @change="orgIdDataChange"></input-select>
      </el-form-item>
      <el-form-item prop="cid" label="分中心">
        <el-select v-model="queryParams.cid" placeholder="请选择分中心" style="width: 230px" clearable>
          <el-option v-for="options in branchList" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleLook" v-hasPermi="['statistical/professioncChecks:peopleList:export']">导出</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="分中心" prop="fzx" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sex == 0">男</span>
            <span v-if="scope.row.sex == 1">女</span>
            <span v-if="scope.row.sex == 2">通用</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" />
        <el-table-column label="体检单位" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" align="center" show-overflow-tooltip />
        <el-table-column label="工种" prop="trades" align="center" show-overflow-tooltip />
        <el-table-column label="接害工龄" prop="jhgl" align="center" />
        <el-table-column label="职业病危害因素" prop="jhys" align="center" show-overflow-tooltip />
        <el-table-column label="拒检补检项目" prop="items" align="center" show-overflow-tooltip />
        <el-table-column label="检查结论" prop="occupationSummary" align="center" show-overflow-tooltip />
        <el-table-column label="处理意见" prop="summaryText" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getListdata, getBranchData } from '@/api/statistical/professionchecks/people_list'

export default {
  data() {
    return {
      total: 0,
      // 分中心列表
      branchList: [],
      loading: false,
      queryParams: {
        current: 1,
        size: 20,
        idOrg: undefined,
        branchIds: undefined,
        startTime: undefined,
        endTime: undefined,
        cid: undefined,
      },
      tableList: [],
      // 性别
      sexOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      orgIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
    }
  },
  created() {
    this.getList()
    this.getBranch()
  },
  methods: {
    //搜素
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    //团检单位
    orgIdDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询分中心数据
    getBranch() {
      getBranchData().then((response) => {
        this.branchList = response.data
        if (!this.queryParams.branchIds) this.queryParams.branchIds = this.defaultBranch
      })
    },
    //请求列表
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
    //重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.orgIdData, 'bindValue')
      this.$set(this.orgIdData, 'bindValue', '')
      this.queryParams.idOrg = ''
      this.handleQuery()
    },

    //导出
    handleLook() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/statistics/summaryQuery/export', this.queryParams, `职业健康检查拒检补检人员名单.xlsx`)
    },
  },
}
</script>
