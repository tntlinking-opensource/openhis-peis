<!-- 记账结算明细 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="form" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入拼音码" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="记账日期" prop="dateValue">
        <el-date-picker style="width: 360px" value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.dateValue" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="开单医师" prop="doctorapply">
        <el-input v-model="queryParams.doctorapply" placeholder="请输入开单医师" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport" v-hasPermi="['finance:inspectAccounts:export']">导出 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain size="mini" icon="el-icon-edit" v-hasPermi="['finance:inspectAccounts:export']">总金额:{{ this.count }} </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="团体" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="name" align="center" show-overflow-tooltip />
        <el-table-column label="结算金额" prop="moneyamountpaid" align="center" show-overflow-tooltip />
        <el-table-column label="付款方式" prop="paywayName" align="center" show-overflow-tooltip />
        <el-table-column label="收费员" prop="idFeecharger" align="center" show-overflow-tooltip />
        <el-table-column label="结算收费时间" prop="moneyamountpaiddate" align="center" show-overflow-tooltip />
        <el-table-column label="记账时间" prop="feechargetime" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" align="center" show-overflow-tooltip />
        <el-table-column label="上次团体开单医师" prop="lastJlmc" align="center" />
        <el-table-column label="卡号" prop="cardno" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="note" align="center" show-overflow-tooltip />
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getList } from '@/api/finance/tally/tally_query.js'
export default {
  props: [],
  data() {
    return {
      //总金额
      count: 0,
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
      // 体检团体
      selectData: {
        placeholder: '请输入输入码',
        value: '团体名称', //第二列标题
        url: '/sell/customer/getListDatas', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        dateValue: undefined,
        startTime: undefined,
        endTime: undefined,
        doctorapply: undefined,
      },
      // 表格展示数据
      tableList: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.queryParams.dateValue = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
  },
  mounted() {},
  methods: {
    // 查询列表
    getList() {
      this.count = 0
      this.loading = true
      if (this.queryParams.dateValue) {
        this.queryParams.startTime = this.queryParams.dateValue[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.dateValue[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getList(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        for (var i in this.tableList) {
          this.count += this.tableList[i].moneyamountpaid
        }
        this.loading = false
      })
    },
    // 体检团体返回值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //重置方法
    handleReset() {
      this.resetForm('form')
      this.queryParams = {
        current: 1,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        dateValue: undefined,
        startTime: undefined,
        endTime: undefined,
        doctorapply: undefined,
      }
      this.handleQuery()
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs['form'].resetFields()
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/finance/tallyDetail/export',
        {
          ...this.queryParams,
        },
        `记账结算明细.xlsx`
      )
    },
  },
}
</script>
