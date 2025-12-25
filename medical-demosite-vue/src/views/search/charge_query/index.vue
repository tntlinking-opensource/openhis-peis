<!-- 收费信息查询 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="单位" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入拼音码" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="patientname">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开单医师" prop="doctorapply">
        <el-input v-model="queryParams.doctorapply" placeholder="请输入开单医师" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择体检类型" clearable style="width: 180px">
          <el-option label="健康" :value="0" />
          <el-option label="职业" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="收费员" prop="idFeecharger">
        <el-input v-model="queryParams.idFeecharger" placeholder="请输入拼音码" clearable style="width: 150px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="收费方式" prop="idPayway">
        <input-select :selectData="selectData" @change="selectChange" selectSize="small"></input-select>
      </el-form-item>
      <el-form-item label="收费日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 300px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item prop="containTongShou">
        <el-checkbox :true-label="1" :false-label="0" v-model="queryParams.containTongShou">含统收</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:chargeQuery:export']">导出Excel </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip width="130" />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" align="center" show-overflow-tooltip width="130" />
        <el-table-column label="收费日期" prop="feechargetime" align="center" show-overflow-tooltip width="200" />
        <el-table-column label="收费方式" prop="idPayway" align="center" show-overflow-tooltip width="90" />
        <el-table-column label="收费员" prop="idFeecharger" align="center" show-overflow-tooltip />
        <el-table-column label="原价" prop="personpricelimit" align="center" show-overflow-tooltip />
        <el-table-column label="实收" prop="moneyamountpaid" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="idExamtype" align="center" show-overflow-tooltip width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.idExamtype == 0">健康体检</span>
            <span v-else-if="scope.row.idExamtype == 1">职业体检</span>
            <span v-else-if="scope.row.idExamtype == 2">综合</span>
            <span v-else-if="scope.row.idExamtype == 3">复查</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="开单医师" prop="doctorapply" align="center" show-overflow-tooltip width="90" />
        <el-table-column label="分组类型" prop="idOrgreservationgroup" align="center" show-overflow-tooltip width="130" />
        <el-table-column label="工作单位" prop="orgName" align="center" show-overflow-tooltip width="150" />
        <el-table-column label="体检卡号" prop="tjk" align="center" show-overflow-tooltip width="100" />
        <el-table-column label="套餐" prop="examsuiteName" align="center" show-overflow-tooltip width="200" />
        <el-table-column label="备注" prop="note" align="center" show-overflow-tooltip width="120" />
      </el-table>
    </div>
    <div style="display: flex; align-items: center">
      <div>
        <div>个检合计: {{ sum.gjhj }} 元</div>
        <div>团检合计: {{ sum.tthj }} 元</div>
        <div>总合计: {{ sum.total }} 元</div>
      </div>
      <!-- 分页 -->
      <pagination style="flex: 1" v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200 ,500]"/>
    </div>
  </div>
</template>
<script>
import { getListData, getPayWay, financeCountAmount } from '@/api/search/charge_query'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'

export default {
  name: 'Charge_query',
  data() {
    return {
      // 表格展示数据
      tableList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
       // 分中心列表
       fzxOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        orgName: '',
        patientname: '',
        patientcode: '',
        idExamtype: '',
        date: undefined,
        idFeecharger: '',
        containTongShou: '',
        idPayway: '',
        ddh:'',
        doctorapply:''
      },
      // 付款方式参数
      selectData: {
        placeholder: '请选择收费方式',
        key: '输入码', //第一列标题
        value: '收费方式', //第二列标题
        url: '/reception/register/getPayWayData', //请求连接
        selectWidth: '180', //选择器宽度（选填，默认230）不加px
        bindValue: '',
        secondName: 'paywayName', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      sum: {
        gjhj: '0.00',
        tthj: '0.00',
        total:'0.00'
      },
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
  },
  methods: {
    // 获取合计数据
    getSumData() {
      financeCountAmount(this.queryParams).then(({ data }) => {
        this.sum = data || { gjhj: '0.00', tthj: '0.00', total: '0.00' }
      })
    },
    selectChange(value) {
      this.queryParams.idPayway = value.id
      this.selectData.bindValue = value.paywayName
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
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
      this.getSumData()
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.selectData.bindValue = ''
      this.resetForm('queryForm')
      this.handleQuery()
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('/query/chargeInfo/export', this.queryParams, `收费信息查询.xlsx`)
    },
  },
}
</script>
