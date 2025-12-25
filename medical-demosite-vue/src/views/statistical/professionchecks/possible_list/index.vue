<!-- 职业健康检查可疑职业病人名单	 开发人：麦沃德科技矢北 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="cid" label="分中心">
        <el-select v-model="queryParams.cid" placeholder="请选择分中心" style="width: 230px" clearable>
          <el-option v-for="options in branchList" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item prop="orgId" label="体检单位">
        <input-select :selectData="orgIdData" selectWidth="200" @change="orgIdDataChange"></input-select>
      </el-form-item>
      <el-form-item prop="medicaltype" label="体检类别">
        <el-select v-model="queryParams.medicaltype" placeholder="请选择体检类别" clearable>
          <el-option label="上岗前" :value="0"></el-option>
          <el-option label="在岗期间" :value="1"></el-option>
          <el-option label="离岗时" :value="2"></el-option>
          <el-option label="离岗后" :value="3"></el-option>
          <el-option label="应急" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item prop="serialNo" label="总结分类">-->
      <!--        <el-select v-model="queryParams.serialNo" placeholder="请选择总结分类" clearable>-->
      <!--          <el-option label="可疑职业病" :value="1"></el-option>-->
      <!--          <el-option label="职业禁忌症" :value="2"></el-option>-->
      <!--          <el-option label="复查" :value="3"></el-option>-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleLook" v-hasPermi="['statistical:professioncChecks:possibleList:export']">导出</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="分中心" prop="fzx" align="center" />
        <el-table-column label="单位名称" prop="orgName" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" />
        <el-table-column label="姓名" prop="patientname" align="center" />
        <el-table-column label="性别" prop="sex" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sex == 0">男</span>
            <span v-if="scope.row.sex == 1">女</span>
            <span v-if="scope.row.sex == 2">通用</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" />
        <el-table-column label="体检类别" prop="medicaltype" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.medicaltype == 0">上岗前</span>
            <span v-if="scope.row.medicaltype == 1">在岗期间</span>
            <span v-if="scope.row.medicaltype == 2">离岗时</span>
            <span v-if="scope.row.medicaltype == 3">离岗后</span>
            <span v-if="scope.row.medicaltype == 4">应急</span>
          </template>
        </el-table-column>
        <el-table-column label="接害工龄(年)" prop="jhgl" align="center" />
        <el-table-column label="接害因素" prop="jhys" align="center" />
        <el-table-column label="可疑职业病名称" prop="disease" align="center" show-overflow-tooltip />
        <el-table-column label="职业阳性结果" prop="posistive" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.posistive == 0">阴性</span>
            <span v-if="scope.row.posistive == 1">阳性</span>
            <span v-if="scope.row.posistive == null">阴性</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getBranchData, getListdata } from '@/api/statistical/professionchecks/forbidden_list'

export default {
  data() {
    return {
      total: 0,
      // 分中心列表
      branchList: [],
      loading: false,
      showSearch: true,
      queryParams: {
        cid: undefined,
        startTime: undefined,
        endTime: undefined,
        idOrg: undefined,
        medicaltype: undefined,
        serialNo: 1,
      },
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
      tableList: [],
      // 性别
      sexOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
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
    // 查询分中心数据
    getBranch() {
      getBranchData().then((response) => {
        this.branchList = response.data
        if (!this.queryParams.branchIds) this.queryParams.branchIds = this.defaultBranch
      })
    },
    //团检单位
    orgIdDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    //请求列表
    getList() {
      this.loading = true
      getListdata(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    ///重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.orgIdData, 'bindValue')
      this.$set(this.orgIdData, 'bindValue', '')
      this.queryParams.idOrg = ''
      this.handleQuery()
    },
    handleLook() {
      this.download(
        '/statistics/contraindication/exportJjz',
        {
          ...this.queryParams,
        },
        `职业健康检查可疑职业病人名单.xlsx`
      )
    },
  },
}
</script>
