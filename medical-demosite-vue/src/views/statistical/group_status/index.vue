<!-- 体检状态明细  开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="体检团体" prop="orgname">
        <input-select :selectData="workData" @change="workChange" />
      </el-form-item>
      <el-form-item label="开单医师" prop="idOpendoctor">
        <input-select :selectData="departData" @change="departChange"></input-select>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable style="width: 130px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item prop="isOrg">
        <el-checkbox v-model="queryParams.isOrg" :true-label="1" :false-label="0">团检</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:groupStatus:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="登记日期" prop="dateregister" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="80px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.sex == 0">男</span>
            <span v-if="scope.row.sex == 1">女</span>
            <span v-if="scope.row.sex == 2">通用</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="80px" align="center" show-overflow-tooltip />
        <el-table-column label="婚姻" prop="idMarriage" min-width="80px" align="center" show-overflow-tooltip />
        <el-table-column label="体检团体" prop="orgName" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="团体部门" prop="orgDepart" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="ddh" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="idOpendoctor" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="体检套餐" prop="examsuiteName" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检状态" prop="tjzt" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="健康总检状态" prop="jktjzt" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.jktjzt == -1">总检未开始</span>
            <span v-if="scope.row.jktjzt == 0">总检开始</span>
            <span v-if="scope.row.jktjzt == 1">总检完成</span>
            <span v-if="scope.row.jktjzt == 2">报告已打印</span>
            <span v-if="scope.row.jktjzt == 3">报告一审通过</span>
            <span v-if="scope.row.jktjzt == 4">报告一审不通过</span>
            <span v-if="scope.row.jktjzt == 5">报告二审通过</span>
            <span v-if="scope.row.jktjzt == 6">报告二审不通过</span>
            <span v-if="scope.row.jktjzt == 7">报告终审通过</span>
            <span v-if="scope.row.jktjzt == 8">报告终审不通过</span>
            <span v-if="scope.row.jktjzt == 9">报告已交接</span>
            <span v-if="scope.row.jktjzt == 10">报告已通知</span>
            <span v-if="scope.row.jktjzt == 11">报告已领取</span>
          </template>
        </el-table-column>
        <el-table-column label="职业总检状态" prop="zytjzt" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.zytjzt == 0">总检开始</span>
            <span v-if="scope.row.zytjzt == 1">总检完成</span>
            <span v-if="scope.row.zytjzt == 2">报告已打印</span>
            <span v-if="scope.row.zytjzt == 3">报告一审通过</span>
            <span v-if="scope.row.zytjzt == 4">报告一审不通过</span>
            <span v-if="scope.row.zytjzt == 5">报告二审通过</span>
            <span v-if="scope.row.zytjzt == 6">报告二审不通过span</span>
            <span v-if="scope.row.zytjzt == 7">报告终审通过</span>
            <span v-if="scope.row.zytjzt == 8">报告终审不通过</span>
            <span v-if="scope.row.zytjzt == 9">报告已交接</span>
            <span v-if="scope.row.zytjzt == 10">报告已通知</span>
            <span v-if="scope.row.zytjzt == 11">报告已领取</span>
          </template>
        </el-table-column>
        <el-table-column label="未检项目" prop="unchecked" min-width="150px" align="center" show-overflow-tooltip />
        <!--<el-table-column label="职业未检项目" prop="uncheckedZy" min-width="150px" align="center" show-overflow-tooltip />-->
        <el-table-column label="科室小结" prop="conclusions" min-width="150px" align="center" show-overflow-tooltip />
        <el-table-column label="创建日期" prop="createDate" min-width="160px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListdata } from '@/api/statistical/group_status'

export default {
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
      //
      departData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/report/healthGetReport/getAllUserData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        isOrg: undefined,
        startTime: undefined,
        endTime: undefined,
        startRegTime: undefined,
        endRegTime: undefined,
        idOpendoctor: undefined,
        orgName: undefined,
      },
      // 工作单位
      workData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
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
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListdata(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
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
      this.$delete(this.workData, 'bindValue')
      this.$delete(this.departData, 'bindValue')
      this.$set(this.workData, 'bindValue', '')
      this.$set(this.departData, 'bindValue', '')
      this.handleQuery()
    },
    // 选择工作单位
    workChange(value) {
      this.queryParams.orgName = value.khdwmc
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 导出
    handleExport() {
      this.download('/statistics/groupStatus/exportJjz', this.queryParams, `体检状态明细.xlsx`)
    },
    //
    departChange(value) {
      this.queryParams.idOpendoctor = value.id
    },
  },
}
</script>
<style lang="scss"></style>
