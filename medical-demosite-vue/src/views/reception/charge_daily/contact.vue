<!-- 每日通联明细  开发人：麦沃德科技半夏\予安 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="选择日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开单医生" prop="idOpendoctor">
        <input-select ref="idOpendoctor" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item label="状态" prop="fIsreturn">
        <el-select v-model="queryParams.fIsreturn" placeholder="收款/退款" clearable>
          <el-option label="收款" :value="0"></el-option>
          <el-option label="退款" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleExport" v-hasPermi="['reception:chargeDaily:contact:export']">导出Excel </el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column fixed label="团体ID" prop="intId" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="体检者" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="收费方式" prop="idPayway" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="支付平台订单号" prop="cardno" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="实收" prop="moneyamountpaid" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="收费人" prop="idFeecharger" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="收费日期" prop="feechargetime" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" min-width="120" prop="idExamtype" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in examOptions" :key="item.id">
              <span v-if="item.id == scope.row.idExamtype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="套餐名称" prop="examsuiteName" min-width="220" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" min-width="120" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getTonglianDataList } from '@/api/reception/charge_daily.js'

import { getDate } from '@/utils/getDate.js'
export default {
  components: {},
  props: [],
  data() {
    return {
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
        patientname: undefined,
        idOpendoctor: undefined,
        fIsreturn: undefined,
      },
      // 开单医师传入数据模板
      selectData: {
        placeholder: '请输入输入码选择',
        value: '名称', //第二列标题
        url: '/member/consulation/getAllUserData', //请求连接
        selectWidth: 200, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 排检表格数据
      tableList: [],
      // 体检类型
      examOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
    }
  },
  created() {
    this.queryParams.date = [getDate().split(' ')[0], getDate().split(' ')[0]]
    this.getList()
  },
  methods: {
    // 销售经理返回选中的值
    selectChange(value) {
      this.queryParams.idOpendoctor = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getTonglianDataList(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$refs.idOpendoctor.initData()
      this.handleQuery()
    },
    // 导出
    handleExport() {
      this.download('/reception/chargeQuery/exportTongLianData', this.queryParams, '每日通联明细.xlsx')
    },
  },
}
</script>
<style lang="scss" scoped>
.table-container {
  .el-row {
    .el-button {
      margin-bottom: 8px;
    }
  }
}
</style>
