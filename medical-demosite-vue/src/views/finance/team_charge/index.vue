<!-- 团体结算 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column team-charge">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="订单号" prop="orderNum" style="margin-bottom: 8px">
        <el-input v-model="queryParams.orderNum" placeholder="请输入订单号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid" style="margin-bottom: 8px">
        <!-- <el-input v-model="queryParams.khdwmcid" placeholder="请输入体检团体" clearable style="width: 230px" @keyup.enter.native="handleQuery" /> -->
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="结算日期从" prop="startTime" style="margin-bottom: 8px">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime" style="margin-bottom: 8px">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="销售经理:" prop="xsjl" style="margin-bottom: 8px">
        <el-input v-model="queryParams.xsjl" placeholder="请输入销售经理" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single" @click="handleRefund" v-hasPermi="['finance:teamCharge:refund']">结算 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" :disabled="single" v-hasPermi="['finance:teamCharge:export']">导出 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--布局-->
    <el-row :gutter="1" style="height: 100%">
      <el-col :span="10" style="height: 100%">
        <!-- 左侧表格 -->
        <div class="table-box flex-direction-column">
          <el-table border ref="tableList" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="订单号" prop="orderNum" align="center" show-overflow-tooltip min-width="80px" />
            <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip min-width="130px" />
            <el-table-column label="应收金额" prop="ys" align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                {{ scope.row.ys ? Number(scope.row.ys).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="回款金额" prop="sf" align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                {{ scope.row.sf ? Number(scope.row.sf).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="销售经理" prop="czxsjl" align="center" show-overflow-tooltip />
            <el-table-column label="待结" prop="balance" align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                {{ scope.row.balance ? Number(scope.row.balance).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <pagination :total="total" :page.sync="queryParams.current" :page-sizes="[20, 100, 200 ,500]" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </el-col>
      <el-col :span="14" style="height: 100%">
        <!-- 右侧表格 -->
        <div class="table-box flex-direction-column" ref="tableBox" style="padding-bottom: 57px; padding-left: 4px">
          <el-table border v-loading="loadingR" :data="tableListR" height="100%" stripe>
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="结算金额" prop="moneyamountpaid" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.moneyamountpaid ? Number(scope.row.moneyamountpaid).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="付款方式" prop="paywayName" align="center" show-overflow-tooltip />
            <el-table-column label="已收已退" prop="isCharged" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.isCharged == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="操作员" prop="userName" align="center" show-overflow-tooltip />
            <el-table-column label="收费时间" prop="moneyamountpaiddate" align="center" show-overflow-tooltip min-width="110px" />
            <el-table-column label="卡号" prop="cardno" align="center" show-overflow-tooltip />
            <el-table-column label="备注" prop="note" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </el-col>
    </el-row>

    <!-- 结算对话框 -->
    <settlement ref="settlement" @settlementSuccess="settlementSuccess"></settlement>
  </div>
</template>
<script>
import { getListData, getBillingData } from '@/api/finance/team_charge.js'
import settlement from './settlement.vue'
export default {
  components: { settlement },
  props: [],
  data() {
    return {
      // 体检团体参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '团体名称', //第二列标题
        url: '/sell/customer/getListDatas', //请求连接
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        orderNum: undefined,
        khdwmcid: undefined,
        startTime: undefined,
        endTime: undefined,
        xsjl: undefined,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: undefined,
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 显示搜索条件
      showSearch: true,
      // 表格展示数据
      tableList: [],
      // 右侧表格数据
      tableListR: [],
      loadingR: false,
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() {},
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 体检团体返回值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = []
        this.single = true
      } else if (selection.length == 1) {
        this.ids = selection[0]
        this.single = selection.length != 1
        this.handleSettlement()
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 结算完成刷新
    settlementSuccess() {
      let selectIndex = undefined
      this.tableList.forEach((el, index) => {
        if (el.id == this.ids.id) {
          selectIndex = index
        }
      })
      this.getList(() => {
       
        this.$nextTick(() => {
          this.$refs.tableList.toggleRowSelection(this.tableList[selectIndex], true)
          this.handleSettlement()
        })
      })
    },
    // 查询结算数据
    handleSettlement() {
      this.loadingR = true
      getBillingData({
        id: this.ids.id,
      })
        .then(({ data }) => {
          this.tableListR = data
          this.loadingR = false
        })
        .catch(() => {
          this.loadingR = false
        })
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 结算
    handleRefund() {
      this.$refs.settlement.showDialog(JSON.parse(JSON.stringify(this.ids)))
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'finance/teamCharge/export',
        {
          ...this.queryParams,
        },
        `团体结算_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
<style scoped>
.team-charge /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
