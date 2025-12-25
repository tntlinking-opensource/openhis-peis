<!-- 正式客户 麦沃德科技 清风 /暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" ref="queryForm" v-model="queryParams" :inline="true" label-width="80px" class="no-margin-bottom">
      <el-form-item label="单位名称" style="margin-right: 16px">
        <el-input style="width: 200px" v-model="queryParams.khdwmc" clearable placeholder="请输入单位名称" @keyup.enter.native="searchButton"></el-input>
      </el-form-item>
      <el-form-item label="输入码" style="margin-right: 16px">
        <el-input style="width: 200px" v-model="queryParams.khdwsrm" clearable placeholder="请输入客户单位输入码" @keyup.enter.native="searchButton"></el-input>
      </el-form-item>
      <el-form-item label="销售经理" style="margin-right: 16px">
        <el-input style="width: 200px" v-model="queryParams.xsjl" clearable placeholder="请输入销售经理" @keyup.enter.native="searchButton"></el-input>
      </el-form-item>
      <el-form-item label="团体ID" style="margin-right: 16px">
        <el-input style="width: 200px" v-model="queryParams.orgId" clearable placeholder="团体ID" @keyup.enter.native="searchButton"></el-input>
      </el-form-item>
      <!--<el-form-item label="分中心" style="margin-right:16px;"></el-form-item>-->
      <el-form-item label="选择日期" style="margin-right: 24px">
        <el-date-picker v-model="queryParams.valueDate" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchButton">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="queryexport" v-hasPermi="['customer:formalCustomers:export']">导出</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" row-key="id" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
        <el-table-column prop="khdwsrm" label="客户单位输入码" align="center"></el-table-column>
        <el-table-column prop="intId" label="团体ID" align="center"></el-table-column>
        <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
        <el-table-column prop="bdrq" label="备单时间" align="center"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getList } from '@/api/customer/formal_customers.js'
export default {
  name:'Formal_customers',
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        khdwmc: undefined,
        khdwsrm: undefined,
        xsjl: undefined,
        orgId: undefined,
        idDepart: undefined,
        valueDate: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      tableData: [
        {
          nationName: '',
          khdwmc: '',
          khdwsrm: '',
          intId: '',
          xsjl: '',
          bdrq: '',
          fzx: '',
        },
      ],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    getList() {
      this.loading = true
      var queryParams = this.queryParams
      getList(queryParams).then((response) => {
        this.tableData = response.data.records
        this.loading = false
        this.total = response.data.total
      })
    },
    //导出
    queryexport() {
      this.download('/sell/formalCustomer/export', {}, `正式客户.xlsx`)
    },
    searchButton() {
      this.queryParams.current = 1
      if (this.queryParams.valueDate) {
        this.queryParams.startTime = this.queryParams.valueDate[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.valueDate[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },

    reset() {
      this.form = {
        current: 1,
        size: 10,
        khdwmc: undefined,
        khdwsrm: undefined,
        xsjl: undefined,
        orgId: undefined,
        idDepart: undefined,
        valueDate: undefined,
        startTime: undefined,
        endTime: undefined,
      }
      this.resetForm('queryForm')
      this.getList()
    },
  },
}
</script>
