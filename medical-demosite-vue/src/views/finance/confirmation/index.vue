<!-- 应收预收函证 开发人：麦沃德科技 予安-->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item label="销售经理" prop="xsjlid">
        <input-select :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="khdwmc">
        <el-input placeholder="请输入单位名称" v-model="queryParams.khdwmc" style="width: 230px" clearable></el-input>
      </el-form-item>
      <el-form-item label="登记日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['finance:confirmation:export']">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableData" height="100%" stripe>
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户全称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="ddh" label="订单号" align="center" width="110" show-overflow-tooltip></el-table-column>
        <el-table-column prop="totalMoney" label="新增备单总金额" align="center" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="tjtcmc" label="备单套餐名称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="isTong" label="是否为统收" align="center" width="110" show-overflow-tooltip></el-table-column>
        <el-table-column prop="zhjg" label="新增备单客单价(元)" align="center" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="numberOfPeople" label="计划来检人数" align="center" width="110" show-overflow-tooltip></el-table-column>
        <el-table-column prop="numberOfPeopleRegistered" label="实际到检人数" align="center" width="110" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getListData } from '@/api/finance/confirmation'
export default {
  name:'Confirmation',
  data() {
    return {
      // 传入数据模板
      selectData: {
        placeholder: '请输入输入码或姓名',
        value: '销售经理', //第二列标题
        url: '/reception/order/getAllUserSql2', //请求连接
        selectWidth: null, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询条件
      queryParams: {
        current: 1,
        size: 20,
        xsjlid: '',
        khdwmc: '',
        startTime: '',
        endTime: '',
      },
      // 总数
      total: 0,
      loading: false,
      //数据表格
      tableData: [],
    }
  },
  created() {
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 30 * 3600 * 24 * 1000
    this.queryParams.startTime = this.$getDate(past7daysStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 搜索
    handleSearch() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      this.getList()
    },
    // 销售经理返回值
    selectChange(value) {
      this.queryParams.xsjlid = value.id
      this.selectData.bindValue = value.username
    },
    // 重置
    resetQuery() {
      this.selectData.bindValue = ''
      this.resetForm('queryForm')
      this.getList()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      this.download('/finance/advanceReceiptLetter/export', this.queryParams, `应收预收函证 .xlsx`)
    },
  },
}
</script>
