<!-- 自费收费汇总 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="登记日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="收费方式" prop="idPayway">
        <el-select v-model="queryParams.idPayway" placeholder="请选择收费方式" clearable filterable :filter-method="filterMethod" style="width: 160px">
          <el-option v-for="item in paywayData" :key="item.id" :label="item.fzx" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="收费人" prop="idFeecharger">
        <input-select ref="typeName1" :selectData="selectData" @change="currencyDataChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:chargeCollection:export']">导出Excel </el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" align="center" />
        <el-table-column label="收费方式" prop="payway" min-width="140px" align="center" show-overflow-tooltip />
        <el-table-column label="收费员" prop="feecharger" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="金额" prop="moneyamountpaid" min-width="140px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/charge_collection'
import { getPaywayData } from '@/api/reception/prepare_order.js'
import { filterMethod } from '@/utils/filterMethod.js'
export default {
  name: 'Charge_collection',
  data() {
    return {
      // 表格展示数据
      tableList: [],
      // 收费人
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医生名称', //第二列标题
        url: '/member/consulation/getAllUserData', //请求连接
        firstName: 'inputCode',
        secondName: 'username',
        bindValue: '',
      },
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        idPayway: undefined,
        idFeecharger: undefined,
      },
      paywayData: [],
      filterList: [],
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    getPaywayData().then(({ data }) => {
      this.filterList = JSON.parse(JSON.stringify(data))
      this.paywayData = data
    })
    this.handleQuery()
  },
  methods: {
    // 体检科室条件查询选择
    filterMethod(value) {
      this.paywayData = this.filterList
      this.paywayData = filterMethod(value, this.paywayData, 'fzx')
    },
    // 选择类型
    currencyDataChange(value) {
      this.queryParams.idFeecharger = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs.typeName1.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/query/chargeCollection/export', this.queryParams, `自费收费汇总.xlsx`)
    },
  },
}
</script>
