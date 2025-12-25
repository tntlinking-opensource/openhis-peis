<!-- 每日客服报表统计  开发人：麦沃德科技半夏 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" label-width="70px" :inline="true" class="no-margin-bottom">
      <el-form-item label="费用类型" prop="sheet">
        <el-select v-model="queryParams.sheet" placeholder="请选择费用类型" style="width: 230px" @change="sheetChange">
          <el-option v-for="options in sheetOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="团体名称" prop="orgName" v-if="queryParams.sheet == 0 || queryParams.sheet == 1">
        <el-input v-model="queryParams.orgName" placeholder="请输入输入码搜索" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname" v-if="queryParams.sheet == 0">
        <el-input v-model="queryParams.patientname" placeholder="请输入拼音码搜索" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype" v-if="queryParams.sheet == 0">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择体检类型" clearable style="width: 160px">
          <el-option v-for="options in examOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleExport(0)" v-hasPermi="['reception:chargeDaily:service:export1']">导出Excel(客服)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-pie-chart" @click="handleView" v-hasPermi="['reception:chargeDaily:service:todayCost']">今日费用结算情况</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleExport(1)" v-hasPermi="['reception:chargeDaily:service:export2']">导出Excel(记账报表)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload(0)" v-hasPermi="['reception:chargeDaily:service:upload1']">上传个检费用</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload(1)" v-hasPermi="['reception:chargeDaily:service:upload2']">上传团检费用</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload(2)" v-hasPermi="['reception:chargeDaily:service:upload3']">上传个检结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload(3)" v-hasPermi="['reception:chargeDaily:service:upload4']">上传团体结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-edit-outline" @click="handleSettle(0)" v-hasPermi="['reception:chargeDaily:service:settlement1']">积分和体检卡月度个检结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-edit-outline" @click="handleSettle(1)" v-hasPermi="['reception:chargeDaily:service:settlement2']">积分和体检卡月度团体结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-notebook-2" @click="handleInspect" v-hasPermi="['reception:chargeDaily:service:inspect']">检验统收团体金蝶名</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload(4)" v-hasPermi="['reception:chargeDaily:service:upload5']">上传统收</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh" @click="handleRefresh" v-hasPermi="['reception:chargeDaily:service:refresh']">金蝶客户数据更新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleExport(2)" v-hasPermi="['reception:chargeDaily:service:export3']">导出(疫苗费用)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleExport(3)" v-hasPermi="['reception:chargeDaily:service:export4']">导出(疫苗名单)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" @click="handleExport(4)" v-hasPermi="['reception:chargeDaily:service:export5']">导出(金蝶名单)</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" v-if="queryParams.sheet == 0">
      <el-table border v-loading="loading" :data="tableList" key="grid0" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column fixed label="团体ID" min-width="120" prop="intId" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" min-width="200" prop="orgName" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" min-width="120" prop="patientcode" align="center" show-overflow-tooltip />
        <el-table-column label="体检者" min-width="100" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="收费方式" min-width="100" prop="idPayway" align="center" show-overflow-tooltip />
        <el-table-column label="卡号" min-width="140" prop="cardNo" align="center" show-overflow-tooltip />
        <el-table-column label="实收" min-width="120" prop="moneyamountpaid" align="center" show-overflow-tooltip />
        <el-table-column label="收费人" min-width="120" prop="idFeecharger" align="center" show-overflow-tooltip />
        <el-table-column label="收费日期" min-width="160" prop="feechargetime" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" min-width="120" prop="idExamtype" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in examOptions" :key="item.id">
              <span v-if="item.id == scope.row.idExamtype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="套餐名称" min-width="200" prop="examsuiteName" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" min-width="100" prop="doctorapply" align="center" show-overflow-tooltip />
        <el-table-column label="体检号生成时间" min-width="120" prop="timingstartedat" align="center" show-overflow-tooltip />
      </el-table>
      <!-- <el-table border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" />
				<el-table-column label="结算金额" prop="moneyamountpaid" align="center" />
				<el-table-column label="结算方式" prop="idPayway" align="center" />
				<el-table-column label="结算日期" prop="moneyamountpaiddate" align="center" />
			</el-table> -->
    </div>
    <div class="table-box" v-if="queryParams.sheet == 1">
      <el-table border v-loading="loading" :data="tableList" key="grid1" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="团体ID" width="110" prop="intId" align="center" />
        <el-table-column label="团体名称" prop="orgName" align="center" />
        <el-table-column label="金额" prop="paid" align="center" />
        <el-table-column label="销售经理" prop="name" align="center" />
      </el-table>
    </div>
    <div class="table-box" v-if="queryParams.sheet == 2">
      <el-table border v-loading="loading" :data="tableList" key="grid2" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="收费方式" prop="paywayName" align="center" />
        <el-table-column label="实收费用" align="center">
          <el-table-column label="个检费用" prop="per" align="center" />
          <el-table-column label="团检费用" prop="org" align="center" />
          <el-table-column label="个检记账结算" prop="perjs" align="center">
            <template slot-scope="scope">
              {{ scope.row.perjs || 0 }}
            </template>
          </el-table-column>
          <el-table-column label="团检记账结算" prop="orgjs" align="center">
            <template slot-scope="scope">
              {{ scope.row.orgjs || 0 }}
            </template>
          </el-table-column>
          <el-table-column label="疫苗费" prop="yimiao" align="center" />
          <el-table-column label="合计费用" prop="total" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <div class="table-box" v-if="queryParams.sheet == 3">
      <el-table border v-loading="loading" :data="tableList" key="grid3" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="团体ID" prop="dwid" align="center" />
        <el-table-column label="单位名称" prop="dwmc" align="center" />
        <el-table-column label="金额" prop="je" align="center" />
        <el-table-column label="销售经理" prop="xsjl" align="center" />
        <el-table-column label="收费日期" prop="sfrq" align="center" />
      </el-table>
    </div>
    <div style="margin-top: 8px; font-size: 20px" v-if="queryParams.sheet != 0">
      合计费用:
      <span style="color: #1890ff; font-weight: 600">{{ totalPrice }}</span>
      元
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0 && (this.queryParams.sheet == 0 || this.queryParams.sheet == 3)" :total="total" :page.sync="queryParams.current" :page-sizes="[20, 50, 100, 200 ,500]" :limit.sync="queryParams.size" @pagination="getList" />
    <settlement-items ref="settlementItems"></settlement-items>
  </div>
</template>
<script>
import { getChargeList, upgradeCustomer, updateMonth, updateMonthGroup, checkOrgKingdeeName, taskAboutUpdateG, taskAboutUpdateT, updateGroupSettlement, updateSettlementOfOrg, updateSettlement } from '@/api/reception/charge_daily.js'
import { getCookie } from '@/utils/getCookie.js'
import { getDate } from '@/utils/getDate.js'

import settlementItems from './settlement'
export default {
  components: { settlementItems },
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
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        sheet: 2,
        orgName: undefined,
        startTime: undefined,
        endTime: undefined,
        patientname: undefined,
        idExamtype: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 合计费用
      totalPrice: 0,
      // 费用类型
      sheetOptions: [
        { id: 0, text: '当日所有检查的费用' },
        { id: 1, text: '当日所有检查统收的统计' },
        { id: 2, text: '当日所有检查实收的费用统计' },
        { id: 3, text: '当日团体非统收汇总' },
      ],
      // 不同费用类型对应的url
      sheetUrl: '/reception/chargeQuery/getEveryDayToPayWayData',
      // 体检类型
      examOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
    }
  },
  created() {
    this.queryParams.startTime = getDate().split(' ')[0] + ' 00:00:00'
    this.queryParams.endTime = getDate().split(' ')[0] + ' 23:59:59'
    this.getList()
  },
  methods: {
    // 费用类型改变
    sheetChange() {
      let sheet = this.queryParams.sheet
      if (sheet == 0) {
        this.sheetUrl = '/reception/chargeQuery/getListData'
      } else if (sheet == 1) {
        this.sheetUrl = '/reception/chargeQuery/getEveryDayToTongDataSql'
      } else if (sheet == 2) {
        this.sheetUrl = '/reception/chargeQuery/getEveryDayToPayWayData'
      } else {
        this.sheetUrl = '/reception/chargeQuery/getFeiTongPayData'
      }
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.sheet == 0) {
        this.queryParams.containTongShou = 1
      } else {
        this.queryParams.containTongShou = undefined
      }
      getChargeList(this.sheetUrl, this.queryParams)
        .then(({ data }) => {
          this.tableList = data.data || data.records
          this.totalPrice = data.rtotal || 0
          if (data.records && data.records.length) {
            data.records.forEach((el) => {
              this.totalPrice += parseFloat(el.je)
            })
          }
          this.totalPrice = this.totalPrice.toFixed(1)
          this.total = data.total || 0
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      let sheet = this.queryParams.sheet
      this.resetForm('queryForm')
      this.queryParams.sheet = sheet
      this.handleQuery()
    },
    // 今日费用结算情况
    handleView() {
      this.$refs.settlementItems.handleShow()
    },
    // 导出
    handleExport(context) {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      // 0 客服, 1 记账报表, 2 疫苗费用, 3 疫苗名单, 4 金蝶名单
      let url = '/reception/chargeQuery/'
      let name = ''
      switch (context) {
        case 0:
          url += 'everyDayKeFuExport'
          name = '导出Excel(客服).xlsx'
          break
        case 1:
          url += 'exportEveryDay'
          name = '导出Excel(记账报表).xlsx'
          break
        case 2:
          url += 'exportVaccine'
          name = '导出Excel(疫苗费用).xlsx'
          break
        case 3:
          url += 'exportVaccineName'
          name = '导出Excel(疫苗名单).xlsx'
          break
        case 4:
          url += 'exportKingdeeName'
          name = '导出Excel(金蝶名单).xlsx'
          break
      }
      this.download(
        url,
        {
          branchIds: getCookie('cid'),
          ...this.queryParams,
        },
        name
      )
    },
    // 上传
    handleUpload(context) {
      let error = ''
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.substring(0, 10)
      } else {
        error = '请先选择开始时间'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.substring(0, 10)
      } else {
        error = '请先选择结束时间'
      }
      if (error) {
        this.$modal.msgWarning(error)
        return
      }
      // return;
      let query = {
        startTime: this.queryParams.startTime,
        endTime: this.queryParams.endTime,
      }
      if (context == 0) {
        //上传个检费用
        taskAboutUpdateT(query).then((res) => {
          this.$modal.msgSuccess('上传成功')
        })
      } else if (context == 1) {
        //上传团检费用
        taskAboutUpdateG(query).then((res) => {
          this.$modal.msgSuccess('上传成功')
        })
      } else if (context == 2) {
        //上传个检结算
        updateSettlement(query).then((res) => {
          this.$modal.msgSuccess('上传成功')
        })
      } else if (context == 3) {
        //上传团检结算
        updateGroupSettlement(query).then((res) => {
          this.$modal.msgSuccess('上传成功')
        })
      } else if (context == 4) {
        // 上传统收
        updateSettlementOfOrg(query).then((res) => {
          this.$modal.msgSuccess('上传成功')
        })
      }
    },
    // 结算
    handleSettle(context) {
      let obj = {
        startTime: this.queryParams.startTime,
        endTime: this.queryParams.endTime,
      }
      if (context == 0) {
        //个检
        updateMonth(obj).then((res) => {
          if (res.data) {
            let msg = JSON.parse(res.data).Reason
            this.$modal.msgSuccess(msg)
          }
        })
      } else if (context == 1) {
        updateMonthGroup(obj).then((res) => {
          if (res.data) {
            let msg = JSON.parse(res.data).Reason
            this.$modal.msgSuccess(msg)
          }
        })
      }
    },
    // 检验
    handleInspect() {
      let error = ''
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.substring(0, 10)
      } else {
        error = '请先选择开始时间'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.substring(0, 10)
      } else {
        error = '请先选择结束时间'
      }
      if (error) {
        this.$modal.msgWarning(error)
        return
      }
      checkOrgKingdeeName({
        startTime: this.queryParams.startTime,
        endTime: this.queryParams.endTime,
      }).then((res) => {
        if (res.data) this.$modal.msgSuccess(res.data)
      })
    },
    // 金蝶客户更新
    handleRefresh() {
      upgradeCustomer().then((res) => {
        if (res.data) {
          this.$modal.msgSuccess(res.data)
        }
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
