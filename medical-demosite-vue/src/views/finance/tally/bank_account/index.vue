<!-- 银行结款结算 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="form" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="汇款单位" prop="remitter">
        <input-select ref="dwData" :selectData="dwData" :isTrim="true" @change="dwChange" :notShowEmpty="true" @keyup.enter.native="handleQuery"></input-select>
      </el-form-item>
      <el-form-item label="还款日期" prop="startTime">
        <!-- <el-date-picker style="width: 240px" value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.startTime" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleChangeTime(0)"></el-date-picker> -->
        <el-date-picker style="width: 140px" value-format="yyyy-MM-dd" type="date" v-model="queryParams.startTime" :picker-options="pickerOptions" @change="handleChangeTime(0)"></el-date-picker>
      </el-form-item>
      <el-form-item label="至" prop="endTime">
        <el-date-picker style="width: 140px" value-format="yyyy-MM-dd" type="date" v-model="queryParams.endTime" :picker-options="pickerOptions" @change="handleChangeTime(0)"></el-date-picker>
      </el-form-item>
      <el-form-item label="年度" prop="year">
        <el-date-picker style="width: 100px" type="year" value-format="yyyy" v-model="queryParams.year" clearable @change="handleChangeTime(1)"></el-date-picker>
      </el-form-item>
      <el-form-item label="季度" prop="quarter">
        <el-select v-model="queryParams.quarter" placeholder="" style="width: 120px" clearable @change="handleChangeTime(2)">
          <el-option label="第一季度" :value="1"></el-option>
          <el-option label="第二季度" :value="2"></el-option>
          <el-option label="第三季度" :value="3"></el-option>
          <el-option label="第四季度" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="月度" prop="month">
        <el-select v-model="queryParams.month" placeholder="" style="width: 120px" clearable @change="handleChangeTime(3)">
          <el-option v-for="item in monthList" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <!-- <el-date-picker style="width: 120px" type="month" value-format="yyyy-MM" v-model="queryParams.month" clearable @change="handleChangeTime(3)"></el-date-picker> -->
      </el-form-item>
      <el-form-item label="结算金额" prop="money">
        <el-input-number v-model="queryParams.money" controls-position="right" clearable style="width: 120px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="结算状态" prop="isJs">
        <el-select v-model="queryParams.isJs" placeholder="请选择" style="width: 120px" clearable>
          <el-option label="未结算" value="0"></el-option>
          <el-option label="已结算" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="isSh">
        <el-select v-model="queryParams.isSh" placeholder="请选择" style="width: 120px" clearable>
          <el-option label="未审核" value="0"></el-option>
          <el-option label="已审核" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable multiple collapse-tags>
          <el-option v-for="item in branchIdOptions" :key="item.branchId" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-plus" :disabled="single" @click="account" v-hasPermi="['finance:inspectAccounts:export']">结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh" @click="bank" v-hasPermi="['finance:inspectAccounts:export']">银行交易流水更新 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh" @click="otherCenter" v-hasPermi="['finance:inspectAccounts:export']">其他中心编码更新 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh" @click="depart" v-hasPermi="['finance:inspectAccounts:export']">部门编码更新 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport(1)" v-hasPermi="['finance:inspectAccounts:export']">导出统收 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport(2)" v-hasPermi="['finance:inspectAccounts:export']">导出充卡 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport(3)" v-hasPermi="['finance:inspectAccounts:export']">导出代收 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleExport(4)" v-hasPermi="['finance:inspectAccounts:export']">导出个检 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border ref="tableList" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="交易流水号" prop="transactionnumber" align="center" show-overflow-tooltip />
        <el-table-column label="汇款单位" prop="remitter" align="center" show-overflow-tooltip />
        <el-table-column label="结算金额" prop="income" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.income ? Number(scope.row.income).toFixed(2) : '' }}
          </template>
        </el-table-column>
        <el-table-column label="银行汇款时间" prop="transDate" align="center" show-overflow-tooltip />
        <el-table-column label="已结算金额" prop="jsje" align="center" show-overflow-tooltip />
        <el-table-column label="结算状态" prop="isJs" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span style="color: red" v-if="scope.row.isJs == 0">未结算</span>
            <span style="color: green" v-else>已结算</span>
          </template>
        </el-table-column>
        <el-table-column label="结算时间" prop="jssj" align="center" show-overflow-tooltip />
        <el-table-column label="审核状态" prop="isSh" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span style="color: red" v-if="scope.row.isSh == 0">未审核</span>
            <span style="color: green" v-else>已审核</span>
          </template>
        </el-table-column>
        <el-table-column label="审核时间" prop="shsj" align="center" show-overflow-tooltip />
        <el-table-column label="销售员" prop="xsjl" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <div style="display: flex; align-items: center">
      <div>汇总金额: {{ sumMoney }} 元</div>
      <!-- 分页 -->
      <pagination style="flex: 1" v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </div>
    <account @getList="handleQuery" ref="account"></account>
  </div>
</template>
<script>
import { getList, kingDeeBank, otherCenter, departUpdate, summaryAmount } from '@/api/finance/tally/bank_account.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import account from './account'

export default {
  name: 'Bank_account',
  components: { account },
  data() {
    return {
      //总金额
      count: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中的数据
      selectInfo: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
       // 日期选择参数
       pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      // 体检团体
      dwData: {
        placeholder: '请输入中文搜索',
        value: '团体名称', //第二列标题
        url: '/finance/tallyQuery/getRemitter', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'remitter',
        secondName: 'remitter', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        name: undefined,
        khdwmcid: undefined,
        year: undefined,
        quarter: undefined,
        month: undefined,
        startTime: undefined,
        endTime: undefined,
        branchIds: undefined,
      },
      // 月份列表
      monthList: [
        { value: '01', label: '1月' },
        { value: '02', label: '2月' },
        { value: '03', label: '3月' },
        { value: '04', label: '4月' },
        { value: '05', label: '5月' },
        { value: '06', label: '6月' },
        { value: '07', label: '7月' },
        { value: '08', label: '8月' },
        { value: '09', label: '9月' },
        { value: '10', label: '10月' },
        { value: '11', label: '11月' },
        { value: '12', label: '12月' },
      ],
      // 表格展示数据
      tableList: [],
      //分中心
      branchIdOptions: [],
      // 汇总金额
      sumMoney: '0.00',
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.queryParams.year = this.$getDate().split(' ')[0].slice(0, 4)
    this.queryParams.branchIds = [this.$getCookie('cid')]
    getBranchData().then((response) => {
      this.branchIdOptions = response.data
    })
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.count = 0
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.loading = true
      getList(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        for (var i in this.tableList) {
          this.count += this.tableList[i].moneyamountpaid
        }
        this.loading = false
      })
      summaryAmount(this.queryParams).then(({ data }) => {
        if (data) {
          this.sumMoney = data.amount || '0.00'
        } else {
          this.sumMoney = '0.00'
        }
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 改变时间
    handleChangeTime(type) {
      if (type == 0) {
        this.queryParams.year = ''
        this.queryParams.quarter = ''
      } else if (type == 1) {
        if (this.queryParams.year) {
          this.queryParams.startTime = this.queryParams.year + '-1-1'
          this.queryParams.endTime = this.queryParams.year + '-12-31'
          this.queryParams.quarter = ''
          this.queryParams.month = ''
        }
      } else if (type == 2) {
        if (!this.queryParams.year) {
          this.$alert('请先选择年份', '提示')
          this.queryParams.quarter = ''
          return
        }
        let year = this.queryParams.year.slice(0, 4)
        let start = ''
        let end = ''
        if (this.queryParams.quarter == 1) {
          start = year + '-1-1'
          end = year + '-3-31'
        } else if (this.queryParams.quarter == 2) {
          start = year + '-4-1'
          end = year + '-6-30'
        } else if (this.queryParams.quarter == 3) {
          start = year + '-7-1'
          end = year + '-9-30'
        } else if (this.queryParams.quarter == 4) {
          start = year + '-10-1'
          end = year + '-12-31'
        }
        this.queryParams.startTime = start
        this.queryParams.endTime = end
        this.queryParams.month = ''
      } else if (type == 3) {
        let year = this.queryParams.year
        if (!year) {
          this.$alert('请先选择年份', '提示')
          this.queryParams.month = ''
          return
        }
        let month = this.queryParams.month
        if (month) {
          let lastDay = ''
          if (['01', '03', '05', '07', '08', '10', '12'].includes(month)) {
            lastDay = '31'
          } else if (['04', '06', '09', '11'].includes(month)) {
            lastDay = '30'
          } else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
              lastDay = '29'
            } else {
              lastDay = '28'
            }
          }
          this.queryParams.startTime = this.queryParams.year + '-' + month + '-1'
          this.queryParams.endTime = this.queryParams.year + '-' + month + '-' + lastDay
          this.queryParams.quarter = ''
        }
      }
      this.handleQuery()
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.transactionnumber)
      this.selectInfo = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    account() {
      const id = this.ids[0]
      this.$refs.account.showDialog(id, this.selectInfo[0].isSh)
    },
    //银行
    bank() {
      const query = {
        startTime: undefined,
        endTime: undefined,
        branchList: this.queryParams.branchIds,
      }
      if (this.queryParams.startTime && this.queryParams.endTime) {
        query.startTime = this.queryParams.startTime
        query.endTime = this.queryParams.endTime
        kingDeeBank(query).then((response) => {
          if (response.code == 200) {
            this.$modal.msgSuccess('更新完成')
            this.getList()
          }
        })
      } else {
        this.$modal.msgWarning('请选择时间区间')
      }
    },
    //其他中心
    otherCenter() {
      otherCenter(this.queryParams).then((response) => {
        if (response.code == 200) {
          this.$modal.msgSuccess('更新完成')
        }
      })
    },
    //部门
    depart() {
      departUpdate(this.queryParams).then((response) => {
        if (response.code == 200) {
          this.$modal.msgSuccess('更新完成')
        }
      })
    },
    //重置方法
    handleReset() {
      this.$refs.dwData.initData()
      this.resetForm('form')
      this.handleQuery()
    },
    dwChange(value) {
      this.queryParams.remitter = value.remitter
    },
    // 重置
    resetQuery() {
      this.$refs['form'].resetFields()
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport(data) {
      if (data == 1) {
        this.queryParams.task = 'TongShou'
        this.download(
          '/finance/bankSettlement/export',
          {
            ...this.queryParams,
          },
          `统收结算明细.xlsx`
        )
      }
      if (data == 2) {
        this.queryParams.task = 'ChongKa'
        this.download(
          '/finance/bankSettlement/export',
          {
            ...this.queryParams,
          },
          `充卡结算明细.xlsx`
        )
      }
      if (data == 3) {
        this.queryParams.task = 'DaiShou'
        this.download(
          '/finance/bankSettlement/export',
          {
            ...this.queryParams,
          },
          `代收结算明细.xlsx`
        )
      }
      if (data == 4) {
        this.queryParams.task = 'GeJian'
        this.download(
          '/finance/bankSettlement/export',
          {
            ...this.queryParams,
          },
          `个检结算明细.xlsx`
        )
      }
    },
  },
}
</script>
