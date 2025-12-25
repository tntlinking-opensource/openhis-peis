<template>
  <el-col :span="12">
    <div class="app-container flex-direction-column proceeds-left">
      <!-- 上方 -->
      <!-- <div class="table-box flex-direction-column"> -->
      <el-row style="width: 100%; margin-bottom: 3px; flex: 1; min-height: 200px; overflow: auto">
        <el-col :span="24" style="height: 100%" class="flex-direction-column">
          <!-- 筛选条件 -->
          <el-form :model="queryParams" ref="queryParams" size="small" :inline="true" @submit.native.prevent>
            <el-form-item label="" prop="autoFill" style="margin-bottom: 0">
              <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
            </el-form-item>
            <el-form-item label="体检号" prop="patientCode" style="margin-bottom: 0">
              <el-input v-model="queryParams.patientCode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
            </el-form-item>
            <el-form-item style="margin-bottom: 0">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-hasPermi="['reception:proceeds:search']">查询</el-button>
            </el-form-item>
          </el-form>
          <!-- 按钮 -->
          <el-form size="small" :inline="true" style="padding: 0 0">
            <el-form-item style="margin-bottom: 5px">
              <el-button type="success" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['reception:proceeds:add']">添加</el-button>
              <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleRemove" v-hasPermi="['reception:proceeds:remove']" :disabled="multiple">删除</el-button>
              <!-- <el-button type="success" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh" v-hasPermi="['reception:proceeds:refresh']">刷新</el-button> -->
              <el-button type="primary" plain size="mini" :disabled="single" @click="handleMembers" v-hasPermi="['reception:proceeds:member']">会员卡</el-button>
              <el-button type="primary" plain size="mini" :disabled="single" @click="handleExperience" v-hasPermi="['reception:proceeds:experience']">体检卡</el-button>
              <el-button type="primary" plain size="mini" :disabled="single" @click="handleFamily" v-hasPermi="['reception:proceeds:family']">家庭卡</el-button>
              <el-button type="danger" plain size="mini" :disabled="single" @click="handleRefund" v-hasPermi="['reception:proceeds:refund']">误操作退款</el-button>
              <!-- <el-button type="warning" plain icon="el-icon-date" size="mini" @click="handleReview" v-hasPermi="['reception:proceeds:review']">复查额度</el-button> -->
              <!-- <el-button type="warning" plain icon="el-icon-date" size="mini" @click="handleCheck" v-hasPermi="['reception:proceeds:review']">工费查询</el-button> -->
              <el-button type="success" plain size="mini" :disabled="single" @click="handleSplit" v-hasPermi="['reception:proceeds:split']">拆分</el-button>
              <el-button type="success" plain size="mini" :disabled="single" @click="handleCharging" v-hasPermi="['reception:proceeds:split']">修改收费方式</el-button>
            </el-form-item>
          </el-form>

          <!-- 上表格 -->
          <div class="table-box" style="min-height: 200px">
            <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" width="40" align="center" />
              <el-table-column label="序列" width="50" type="index" align="center" />
              <el-table-column label="应收应退" prop="moneyamount" align="center" min-width="100px">
                <template slot-scope="scope">
                  <span>{{ scope.row.moneyamount ? scope.row.moneyamount : '0.00' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="实收实退" prop="moneyamountpaid" align="center" min-width="125px">
                <template slot-scope="scope">
                  <div v-if="scope.row.ffeecharged == 1 || scope.row.id" style="padding: 0 8px">{{ scope.row.moneyamountpaid ? scope.row.moneyamountpaid.toFixed(2) : '0.00' }}</div>
                  <el-input-number v-else v-model="scope.row.moneyamountpaid" controls-position="right" :precision="2" :disabled="scope.row.isAdd == 1" @change="moneyChange(scope.$index)" size="small" class="text-left" style="width: 100%; text-align: left" />
                </template>
              </el-table-column>
              <el-table-column label="付款方式" prop="payway" align="center" show-overflow-tooltip min-width="120px">
                <template slot-scope="scope">
                  <span v-if="scope.row.ffeecharged == 1 || scope.row.id">{{ scope.row.payway }}</span>
                  <input-select :ref="'payway' + scope.$index" v-else :selectData="selectData" @change="selectChange($event, scope.$index)" selectSize="small" :initialValue="scope.row.payway"></input-select>
                </template>
              </el-table-column>
              <el-table-column label="统收" prop="isTotalcharge" align="center" min-width="50px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.isTotalcharge == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已收" prop="ffeecharged" align="center" min-width="50px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.ffeecharged == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="加项" prop="isAdd" align="center" min-width="50px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.isAdd == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="收费员" prop="feechargerName" align="center" show-overflow-tooltip min-width="80px" />
              <el-table-column label="收退费时间" prop="feechargetime" align="center" show-overflow-tooltip min-width="130px" />
              <el-table-column label="卡号" prop="cardno" align="center" show-overflow-tooltip min-width="150px">
                <template slot-scope="scope">
                  <span v-if="scope.row.ffeecharged == 1 || scope.row.id || scope.row.idPayway == 6 || scope.row.idPayway == 7 || scope.row.idPayway == 22 || scope.row.idPayway == 44 || scope.row.idPayway == 18 || scope.row.idPayway == 19">{{ scope.row.cardno || scope.row.txTradeNo }}</span>
                  <el-input :ref="'cardno' + scope.$index" v-else v-model="scope.row.cardno" size="small" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column label="备注" prop="note" align="center" min-width="280px">
                <template slot-scope="scope">
                  <span v-if="scope.row.ffeecharged == 1">{{ scope.row.note }}</span>
                  <el-input v-else v-model="scope.row.note" size="small" type="textarea" :rows="1"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
      <!-- </div> -->

      <!-- 中间 -->
      <div class="statistics">
        <div class="item-list">
          <div class="item" v-for="(item, index) in statisticsList" :key="index">
            <div class="title">{{ item.title }}</div>
            <el-input-number v-if="index == 3" v-model="item.count" :precision="2" :min="0.0" style="width: 100%; padding: 0" controls-position="right"></el-input-number>
            <div class="number" v-else :style="{ '--theme': theme }">{{ item.count.toFixed(2) }}</div>
          </div>
        </div>
        <div class="btn-list">
          <el-button type="warning" @click="handelPrint" size="small">缴费单打印</el-button>
          <el-button type="primary" size="small" style="width: 100%; margin-left: 0; margin-top: 5px" @click="handleCharge">收款</el-button>
        </div>
      </div>

      <!-- 下表格 -->
      <el-row style="width: 100%">
        <el-col :span="24">
          <!-- 表格 -->
          <el-table border v-loading="loading" :data="tableListBelow" height="200px" stripe>
            <el-table-column label="体检号" prop="patientcode" align="center" min-width="120px" />
            <el-table-column label="姓名" prop="patientname" align="center" min-width="100px" />
            <el-table-column label="性别" prop="idSex" align="center" min-width="80px">
              <template slot-scope="scope">
                <span>{{ ['男', '女'][scope.row.idSex] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" align="center" min-width="80px" />
            <el-table-column label="手机号码" prop="phone" align="center" min-width="120px" />
            <el-table-column label="身份证号" prop="idcardno" align="center" min-width="180px" />
            <el-table-column label="预约" prop="fisforreserve" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.fisforreserve == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="登记" prop="fregistered" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.fregistered == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="登记员" prop="doctorreg" align="center" show-overflow-tooltip min-width="80px" />
          </el-table>
        </el-col>
      </el-row>
    </div>

    <!-- 打印弹窗 -->
    <el-dialog title="提醒" :visible.sync="openPrintTitle" width="340px" append-to-body :close-on-click-modal="false">
      <div style="padding: 10px 20px">需要打印条码和导引单吗？</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="quickPprint" @keyup.enter="quickPprint">速 打</el-button>
        <el-button plain type="primary" @click="printWindow">打 印</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改收费方式 -->
    <el-dialog title="修改收费方式" :visible.sync="editCharging" width="340px" append-to-body :close-on-click-modal="false">
      <div style="padding-bottom: 16px;"> 当前收费方式: </div>
      <div style="padding-bottom: 16px;color: red;">{{ chargingType }}</div>
      <div style="padding-bottom: 16px;"> 修改收费方式:</div>
      <div> <input-select :selectData="selectData" @change="selectChangeEdit($event)" selectSize="small" :initialValue="chargingType"></input-select></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="changePaymentMethod">确认修改</el-button>
        <el-button @click="editCharging = false">取消修改</el-button>
      </div>
    </el-dialog>

    <!-- 打印对话框 -->
    <el-dialog title="打印" :visible.sync="openPrint" width="500px" destroy-on-close append-to-body style="overflow: hidden" :close-on-click-modal="false">
      <el-form :model="printParams" size="small" label-width="100px">
        <el-form-item label="模式" prop="mode">
          <el-radio-group v-model="printParams.mode" :disabled="setModel">
            <el-radio v-for="item in modelType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="导引单样式" prop="style">
          <el-radio-group v-model="printParams.style" :disabled="setDydStyle">
            <el-radio v-for="item in dydStyleType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-radio-group v-model="printParams.content">
            <el-radio v-for="item in radioType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="printConfirm">确 定</el-button>
        <el-button type="" @click="openPrint = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 会员卡弹窗 -->
    <member-card ref="memberCard" @paySuccess="paySuccess"></member-card>
    <!-- 体检卡弹窗 -->
    <exam-card ref="examCard" @paySuccess="paySuccess"></exam-card>
    <!-- 家庭卡弹窗 -->
    <family-card ref="familyCard" @paySuccess="paySuccess"></family-card>
    <!-- 通联支付弹窗 -->
    <WXPay ref="WXPay" @clearSelect="clearSelect" @paySuccess="paySuccess"></WXPay>
    <!-- 拆分弹窗 -->
    <price-split ref="priceSplit" @confirmSplit="confirmSplit"></price-split>
    <!-- 打印缴费单 -->
    <payment-bills ref="paymentBills"></payment-bills>
    <!-- 复查额度 -->
    <review-quota ref="reviewQuota"></review-quota>
    <!-- 速打 -->
    <print-html ref="printHtml"></print-html>
  </el-col>
</template>

<script>
import { getCustomerData, getChargeData, receiveRefund, searchPatientcode, refundApi, getOldFamilyCard, changePaymentMethod } from '@/api/reception/proceeds.js'
import { middleDbInterface } from '@/api/reception/registration.js'

import priceSplit from './price_split.vue'
import memberCard from './card/member_card.vue'
import examCard from './card/exam_card.vue'
import familyCard from './card/family_card.vue'
import WXPay from './WX_pay.vue'
import reviewQuota from './review_quota.vue'
import paymentBills from '@/views/preview/payment_bills/index.vue'

import printHtml from '../registration/print.vue'
import { getCookie } from '@/utils/getCookie.js' //获取cookie数据

import { printBarCode } from '@/utils/printBarCode.js' // 打印条形码

// 获取默认打印条数
import { barcodePrinter } from '@/api/reception/register_list'
export default {
  components: {
    priceSplit,
    memberCard,
    examCard,
    familyCard,
    WXPay,
    reviewQuota,
    paymentBills,
    printHtml,
  },
  data() {
    return {
      // 上方表格****************
      // 筛选参数
      queryParams: {
        patientCode: undefined,
        autoFill: true,
        type: '1',
      },
      // 当前收费方式
      chargingType: '',
      chargingTypeID: '',
      chargdPayway: '',
      // 表格加载
      loading: false,
      // 上方选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      // 表格索引
      tableIndex: 0,
      // 付款方式参数
      selectData: {
        placeholder: '请选择',
        key: '输入码', //第一列标题
        value: '支付方式', //第二列标题
        url: '/reception/register/getPayWayData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        secondName: 'paywayName', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 统计参数列表
      statisticsList: [
        { title: '应收应退合计', count: 0 },
        { title: '实收实退合计', count: 0 },
        { title: '不足金额', count: 0 },
        { title: '现金收款', count: 0 },
        { title: '现金请付', count: 0 },
        { title: '现金找零', count: 0 },
      ],
      // 版本号
      version: undefined,
      // 打印参数
      printParams: {
        mode: '1',
        style: '1',
        content: '2',
      },
      // 复查额度弹窗
      openLimit: false,
      // 复查额度参数
      limitParams: {
        num: undefined,
        cost: undefined,
        residue: undefined,
        explain: undefined,
        note: undefined,
      },
      // 下方表格********************
      tableListBelow: [],

      //打印弹窗
      openPrintTitle: false,
      //修改收费方式弹窗
      editCharging: false,
      //打印对话框
      openPrint: false,
      //打印 -- 条码/导引单选项
      modelType: [
        //模式
        { id: '0', text: '设计' },
        { id: '1', text: '打印' },
      ],
      dydStyleType: [
        //导引单样式
        { id: '0', text: '简单' },
        { id: '1', text: '复杂' },
        { id: '2', text: '普通' },
      ],
      radioType: [
        //内容
        { id: '0', text: '条码' },
        { id: '1', text: '导引单' },
        { id: '2', text: '条码和导引单' },
      ],
      setModel: false,
      setDydStyle: false,
      // 收款按钮防抖
      chargeDebounce: false,
    }
  },
  watch: {
    tableList: {
      handler(newValue) {
        this.computePrice()
      },
      immediate: true,
      deep: true,
    },
    watchInputData: {
      handler(newValue) {
        let num = newValue - this.statisticsList[4].count
        this.statisticsList[5].count = num >= 0 ? num : 0
      },
    },
    watchContent: {
      handler(newVal, oldVal) {
        if (newVal == '0') {
          this.setModel = false
          this.setDydStyle = true
        } else if (newVal == '1') {
          this.setModel = false
          this.setDydStyle = false
        } else if (newVal == '2') {
          this.setModel = true
          this.setDydStyle = false
        }
      },
      immediate: true,
    },
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    watchInputData() {
      return this.statisticsList[3].count
    },
    watchContent() {
      return this.printParams.content
    },
  },
  created() {
    this.queryParams.patientCode = this.$route.params.patientCode ? JSON.parse(JSON.stringify(this.$route.params.patientCode)) : ''
    if (this.queryParams.patientCode) {
      this.getList()
    }
    this.bus.$on('HandleReverseRight', () => {
      this.getList()
    })
    document.addEventListener('keydown', (e) => {
      var key = window.event ? e.keyCode : e.which
      if (key === 13 && this.openPrintTitle) {
        // 这里执行相应的行为动作
        this.quickPprint()
      }
    })
  },
  beforeDestroy() {
    this.bus.$off('HandleReverseRight')
  },
  methods: {
    // 修改收费方式返回值
    selectChangeEdit($event) {
      this.chargdPayway = $event.id
    },
    // 执行修改接口
    changePaymentMethod() {
      changePaymentMethod({
        idPayway: this.chargdPayway,
        id: this.chargingTypeID,
      })
        .then(() => {
          this.getList()
          this.editCharging = false
          this.$modal.msgSuccess('修改收费方式成功')
        })
        .catch((error) => {
          console.error(error)
        })
    },
    // 修改收费方式
    handleCharging() {
      // "44","99","7","6","18","101"，这些收费方式不能用这个接口修改 reception/charge/changePaymentMethod
      if (this.ids[0].idPayway == 44 || this.ids[0].idPayway == 99 || this.ids[0].idPayway == 7 ||
        this.ids[0].idPayway == 6 || this.ids[0].idPayway == 18 || this.ids[0].idPayway == 101) {

        this.$alert('当前收费方式不可修改', '提示')
        return

      }
      this.chargingTypeID = this.ids[0].id
      this.chargingType = this.ids[0].payway
      this.editCharging = true
    },
    // 计算价格
    computePrice() {
      if (this.tableListBelow.length) {
        this.statisticsList[0].count = this.tableListBelow[0].moneyamount
        this.statisticsList[1].count = 0
        this.statisticsList[4].count = 0
        this.tableList.forEach((el) => {
          // el.moneyamount ? (this.statisticsList[0].count += parseFloat(el.moneyamount)) : '0.00'
          el.moneyamountpaid ? (this.statisticsList[1].count += parseFloat(el.moneyamountpaid)) : '0.00'
          if (el.idPayway == 1 && el.ffeecharged != 1) {
            el.moneyamountpaid ? (this.statisticsList[4].count += parseFloat(el.moneyamountpaid)) : '0.00'
          }
        })
        this.statisticsList[1].count = Number(this.statisticsList[1].count.toFixed(2))
        this.statisticsList[2].count = this.tableListBelow[0].moneyamount - this.statisticsList[1].count
        let num = this.statisticsList[3].count - this.statisticsList[4].count
        this.statisticsList[5].count = num >= 0 ? num : 0
      }
    },
    //速打
    quickPprint() {
      let data = {
        cid: getCookie('cid'), //分中心id
        ids: this.tableListBelow[0].patientcode, //体检号
        model: 1, //模板id
        dydStyle: 1, //导引单类型 0 简单 1 复杂 2 普通
        // radio: 2, //内容
      }
      const query = {
        id: getCookie('cid'), //分中心id
        ids: [this.tableListBelow[0].id], //体检者ids
        model: 1, //模板id
      }

      // 获取默认打印个数
      let defaultNumber = 0
      barcodePrinter().then(({ data }) => {
        if (data.receptionNum) {
          defaultNumber = data.receptionNum
        } else {
          defaultNumber = 9
        }
      })

      this.$refs.printHtml.getReport(data, query, () => {
        this.openPrintTitle = false
      }, defaultNumber)
    },
    //打印
    printWindow() {
      if (this.tableListBelow.length < 1) return
      let id = this.tableListBelow[0].id
      let tjCode = this.tableListBelow[0].patientcode
      if (id == '') {
        this.$modal.msgWarning('体检者不能为空！')
        return
      }
      if (!tjCode) {
        this.$modal.msgWarning('体检号不能为空！')
        return
      }
      this.openPrint = true
      this.openPrintTitle = false
    },
    //打印弹窗 -- 取消
    cancel() {
      this.openPrintTitle = false
    },
    //打印对话框-确认
    printConfirm() {
      const query = {
        id: getCookie('cid'), //分中心id
        ids: [this.tableListBelow[0].id], //体检者ids
        model: this.printParams.mode, //模板id
      }
      let routeUrl = undefined
      if (this.printParams.content == '0') {
        printBarCode(query, () => {
          this.openPrint = false
        })
      } else {
        let queryParams = {
          cid: getCookie('cid'), //分中心id
          ids: this.tableListBelow[0].patientcode, //体检号
          model: this.printParams.mode, //模板id
          type: '0',
          dydStyle: this.printParams.style, //导引单类型 0 简单 1 复杂 2 普通
          printBar: undefined,
        }
        if (this.printParams.content == '2') {
          queryParams.printBar = JSON.stringify(query)
        }
        routeUrl = this.$router.resolve({
          name: 'BillsModelList',
          query: queryParams,
        })
        window.open(routeUrl.href, '_blank')
        this.openPrint = false
      }
    },
    // 实收实退修改，应收应退同步修改
    moneyChange(index) {
      if (this.statisticsList[2].count === '0.00' || this.statisticsList[2].count == 0) {
        this.$alert('当前不足金额为0,不可修改', '提示')
        this.$nextTick(() => {
          this.tableList[index].moneyamountpaid = 0
          this.tableList = JSON.parse(JSON.stringify(this.tableList))
        })
        return
      }
      this.tableList[index].moneyamount = Number(this.tableList[index].moneyamountpaid).toFixed(2)
    },
    // 获取体检者与收费项目信息
    getList() {
      this.loading = true
      this.tableList = []
      this.tableListBelow = []
      this.statisticsList.forEach((el) => {
        el.count = 0
      })
      this.bus.$emit('handleProceedsRightLoading', true)
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      // queryParams.autoFill = queryParams.autoFill ? 1 : 0
      // 获取体检者相关数据
      getCustomerData(queryParams)
        .then(({ data }) => {
          if (!data.patientData) {
            this.$alert('未查询到体检者信息')
            this.loading = false
            this.bus.$emit('handleProceedsRightLoading', false)
            return
          }
          this.queryParams.patientCode = data.patientData.patientcode
          this.version = data.version
          if (!data.patientData.moneyamount) {
            data.patientData.moneyamount = 0
          }
          this.tableListBelow.push(data.patientData)
          this.loading = false
          this.bus.$emit('handleProceedsRight', data)
          this.bus.$emit('handleProceedsRightLoading', false)
          this.computePrice()
          // 获取体检者收费信息
          getChargeData({ patientcode: this.queryParams.patientCode }).then(({ data: data1 }) => {
            if (data1.length) {
              data1.forEach((el) => {
                el.tableIndex = 'tableIndex' + this.tableIndex++
                el.moneyamount = el.moneyamount ? el.moneyamount.toFixed(2) : '0.00'
                el._state = 'modified'
              })
              this.tableList = data1
            }
            let price = this.tableListBelow[0].moneyamount
            let yushou = 0
            data1.forEach((el) => {
              price = price - el.moneyamount
              if (el.ffeecharged == 1 && el.note && el.note.includes('预收')) {
                yushou += Number(el.moneyamountpaid)
              }
            })
            price = Math.round(price * 100) / 100
            let hasMore = false
            let addHasMore = false
            let money = 0
            let _money = 0
            data.examfeeitemData.forEach((el) => {
              if (el.fFeecharged != 1 && el.sfjx == 1) {
                money += Number(el.factprice)
                addHasMore = true
              }
              if (el.fFeecharged != 1) {
                hasMore = true
              }
            })
            // 加项全部已收
            let addAllCharge = true
            this.tableList.forEach((el) => {
              if (el.ffeecharged != 1 && el.id && el.isAdd == 1) {
                _money += Number(el.moneyamountpaid)
              }
              if (el.ffeecharged != 1) {
                addAllCharge = false
              }
            })
            if (!addAllCharge) {
              money = money - _money
            }
            money = Math.round(money * 100) / 100
            if (money) {
              if (yushou != 0) {
                if (yushou < money) {
                  this.tableList.push({
                    tableIndex: 'tableIndex' + this.tableIndex++,
                    idPayway: undefined,
                    isTotalcharge: 0,
                    ffeecharged: 0,
                    moneyamount: (money - yushou).toFixed(2),
                    moneyamountpaid: (money - yushou).toFixed(2),
                    isAdd: 1,
                  })
                }
                if (yushou != money && price - money != 0) {
                  this.handleAdd()
                }
              } else {
                this.tableList.push({
                  tableIndex: 'tableIndex' + this.tableIndex++,
                  idPayway: undefined,
                  isTotalcharge: 0,
                  ffeecharged: 0,
                  moneyamount: money.toFixed(2),
                  moneyamountpaid: money.toFixed(2),
                  isAdd: 1,
                })
                if (price - money != 0) {
                  this.$nextTick(() => {
                    this.handleAdd()
                  })
                }
              }
            } else {
              if (price && hasMore) {
                this.$nextTick(() => {
                  this.handleAdd()
                })
              }
            }
          })
        })
        .catch(() => {
          this.loading = false
          this.bus.$emit('handleProceedsRightLoading', false)
        })
      //
    },
    // 查询
    handleQuery() {
      if (!this.queryParams.patientCode) {
        this.$modal.msgWarning('请输入体检号')
        return
      }
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 单击某行
    rowClick(row, col) {
      if (col?.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 付款方式返回参数
    selectChange($event, index) {
      this.tableList[index].idPayway = $event.id
      this.tableList[index].payway = $event.paywayName
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(this.tableList[index], true)
      if ($event.id == 6) {
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          this.handleMembers()
        })
      } else if ($event.id == 7) {
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          this.handleExperience()
        })
      } else if ($event.id == 22 || $event.id == 99 || $event.id == 100) {

        // $event.id == 22 通联支付 $event.id == 99 随行付 $event.id == 100 通联支付2
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          if (!this.ids[0].moneyamountpaid) {
            this.$alert('金额不能为0')
            this.tableList[index].idPayway = undefined
            this.tableList[index].payway = undefined
            this.$refs['payway' + index].initData()
            this.$nextTick(() => {
              this.tableList = JSON.parse(JSON.stringify(this.tableList))
            })
            return
          }
          this.$refs['payway' + index].setBlur()
          this.handleWXPay($event.id)
          // this.$confirm('是否进行通联扫码支付?', '提示')
          //   .then(() => {
          //   })
          //   .catch(() => {
          //     this.tableList[index].idPayway = undefined
          //     this.tableList[index].payway = undefined
          //     this.$refs['payway' + index].initData()
          //     this.$nextTick(() => {
          //       this.tableList = JSON.parse(JSON.stringify(this.tableList))
          //     })
          //   })
        })
      } else if ($event.id == 18 || $event.id == 19) {
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          this.handleFamily()
        })
      }
    },
    // 通联支付失败
    clearSelect() {
      let index = undefined
      this.tableList.forEach((el, index1) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          index = index1
        }
      })
      this.$refs['payway' + index].initData()
      this.tableList[index].payway = ''
      this.tableList[index].idPayway = ''
      this.$forceUpdate()
    },
    // 新增
    handleAdd() {
      if (this.tableListBelow.length == 0) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      var obj = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        idPayway: undefined,
        payway: undefined,
        isTotalcharge: 0,
        ffeecharged: 0,
      }
      if (!this.tableList.length) {
        obj = {
          ...obj,
          moneyamount: this.tableListBelow[0].moneyamount ? this.tableListBelow[0].moneyamount.toFixed(2) : '',
          moneyamountpaid: this.tableListBelow[0].moneyamount ? this.tableListBelow[0].moneyamount.toFixed(2) : '',
        }
      } else {
        obj = {
          ...obj,
          moneyamount: 0,
          moneyamountpaid: 0,
        }
        let moneyamount = 0
        let moneyamountpaid = 0
        this.tableList.forEach((el) => {
          if (el.moneyamount) {
            moneyamount += Number(el.moneyamount)
          }
          moneyamountpaid += Number(el.moneyamountpaid)
        })
        obj.moneyamountpaid = (this.tableListBelow[0].moneyamount - moneyamountpaid).toFixed(2)
        obj.moneyamount = (this.tableListBelow[0].moneyamount - moneyamount).toFixed(2)
      }
      this.tableList.push(obj)
    },
    // 删除
    handleRemove() {
      this.ids.forEach((el) => {
        for (let i = this.tableList.length - 1; i >= 0; i--) {
          if (this.tableList[i].tableIndex == el.tableIndex) {
            let alert = ''
            if (!alert && this.tableList[i].ffeecharged == 1) {
              alert = '选择的收费信息中第 ' + (i + 1) + ' 条收费信息已经收费，不能删除'
            }
            if (!alert && this.tableList[i].isAdd == 1) {
              alert = '选择的收费信息中第 ' + (i + 1) + ' 条收费信息是加项收费，不能删除'
            }
            if (!alert && this.tableList[i].id) {
              alert = '选择的收费信息中第 ' + (i + 1) + ' 条收费信息已经卡收费或者通联收费，不能删除'
            }
            if (alert) {
              this.$alert(alert, '提示')
              return
            }
            this.$delete(this.tableList, i)
          }
        }
      })
    },
    // 刷新
    handleRefresh() {
      if (this.tableListBelow.length == 0) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      this.getList()
    },
    // 会员卡
    handleMembers() {
      if (this.ids[0].idPayway != 6) {
        this.$alert('请选择会员积分付款方式', '提示')
        return
      }
      if (!this.ids[0].moneyamountpaid) {
        this.$alert('金额不能为0', '提示')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作', '提示')
      } else {
        this.$refs.memberCard.dialogShow(this.tableListBelow[0], this.ids[0], this.version)
      }
    },
    // 体验卡
    handleExperience() {
      if (this.ids[0].idPayway != 7) {
        this.$alert('请选择体检卡付款方式', '提示')
        return
      }
      if (!this.ids[0].moneyamountpaid) {
        this.$alert('金额不能为0', '提示')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作', '提示')
      } else {
        this.$refs.examCard.dialogShow(this.tableListBelow[0], this.ids[0], this.version)
      }
    },
    // 家庭卡
    handleFamily() {
      if (this.ids[0].idPayway != 18 && this.ids[0].idPayway != 19) {
        this.$alert('请选择家庭卡付款方式', '提示')
        return
      }
      if (!this.ids[0].moneyamountpaid) {
        this.$alert('金额不能为0', '提示')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作', '提示')
      } else {
        // 暂时注释
        // searchPatientcode({
        //   patientcode: this.tableListBelow[0].patientcode,
        // }).then(({ data }) => {
        //   if (data.status == 'failed') {
        //     this.$alert(data.msg, '提示')
        //   } else {
        // this.$refs.familyCard.dialogShow(this.tableListBelow[0], this.ids[0], this.version, data.msg)
        //   }
        // })
        getOldFamilyCard({ idcardno: this.tableListBelow[0].idcardno })
          .then(({ data }) => {
            this.$refs.familyCard.dialogShow(this.tableListBelow[0], this.ids[0], this.version, data, this.tableListBelow[0].idcardno)
          })
          .catch((err) => {
            let { message } = err
            console.error(err)
            // if (message == '身份证未绑定家庭卡') {
            //   this.$alert(message, '提示')
            // }
          })
      }
    },
    // 通联支付
    handleWXPay(idPayway) {
      this.$refs.WXPay.dialogShow(this.tableListBelow[0], this.ids[0], this.version, false, idPayway)
    },
    // 卡及通联支付成功
    paySuccess(id, cardno, version, name) {
      this.tableList.forEach((el) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          el.id = id
          el.payway = name
          el.cardno = cardno
          el._state = 'modified'
        }
      })
      this.version = version
      this.$nextTick(() => {
        this.tableList = JSON.parse(JSON.stringify(this.tableList))
        this.handleCharge({}, 'skip')
      })
    },
    // 卡及通联支付退费
    handleRefund() {
      let type = this.ids[0].idPayway
      if (this.ids[0].ffeecharged == 1) {
        this.$alert('已经收费，不可更改', '提示')
        return
      }
      if (type == 6 || type == 7 || type == 22 || type == 18 || type == 19 || type == 44) {
        let data = {
          id: this.ids[0].id,
          version: this.version,
          patientname: this.tableListBelow[0].patientname,
          cardId: this.ids[0].cardno,
          jsf: this.ids[0].moneyamountpaid,
          limit: this.ids[0].moneyamountpaid,
          branchId: this.$getCookie('cid'),
          consumeType: '0',
          kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 22 ? 9 : type == 18 ? 7 : type == 19 ? 6 : type == 99 ? 10 : type == 100 ? 11 : '',
          memotext: '退款',
        }
        if (type == 18 || type == 19) {
          data.idcardno = this.tableListBelow[0].idcardno
          data.patientcode = this.tableListBelow[0].patientcode
          data.idPayway = type
        }
        this.$confirm('是否执行误操作退款?', '提示')
          .then(() => {
            this.loading = true
            refundApi(data)
              .then(() => {
                this.getList()
                this.$modal.msgSuccess('退款成功')
                this.loading = false
              })
              .catch(() => {
                this.loading = false
              })
          })
          .catch(() => { })
      } else {
        this.$alert('该付款方式不支持误操作退款', '提示')
      }
    },
    // 拆分
    handleSplit() {
      let alert = ''
      if (this.ids[0].ffeecharged == 1) {
        alert = '已经收费，不可更改'
      } else if (this.ids[0].id) {
        alert = '已经使用会员积分抵扣或者使用体检卡、家庭卡、复查余额消费，不可更改'
      }
      if (alert) {
        this.$alert(alert, '提示')
        return
      }
      this.$refs.priceSplit.showDialog()
    },
    // 执行拆分
    confirmSplit(splitMoney) {
      let isAdd = ''
      this.tableList.forEach((el) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          // el.moneyamount = (Number(el.moneyamount) - splitMoney).toFixed(2)
          el.moneyamountpaid -= splitMoney
          isAdd = el.isAdd
        }
      })
      var obj = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        idPayway: undefined,
        payway: undefined,
        // moneyamount: splitMoney.toFixed(2),
        moneyamount: 0,
        moneyamountpaid: splitMoney,
        isTotalcharge: 0,
        ffeecharged: 0,
        isAdd,
      }
      this.tableList.push(obj)
      this.$message({
        message: '拆分成功',
        type: 'success',
      })
    },
    // 收费
    handleCharge(evt, skip) {
      if (!this.tableListBelow[0] || !this.tableListBelow[0].id) {
        this.$alert('体检者信息不存在!', '提示')
        return
      }
      let error = ''
      if (this.chargeDebounce) {
        error = '提交中,请勿重复操作'
      }
      if (this.statisticsList[2].count < 0) {
        error = '费用已经多收'
      }
      if (this.statisticsList[2].count > 0) {
        error = '费用没有收齐'
      }
      let _index = ''
      this.tableList.forEach((el, index) => {
        if (!error) {
          if (!el.idPayway) {
            error = '付款方式:不能为空,请修改后重新操作'
            _index = index
          } else if (['6', '7', '18', '19', 'ff80808172a5f6210172a809a9cb2222', '17', '9', '101'].includes(el.idPayway) && !el.cardno && !el.txTradeNo) {
            if (this.$refs['cardno' + index]) {
              this.$refs['cardno' + index].focus()
            }
            error = '第' + (index + 1) + '条数据卡号不能为空,请修改或重新操作'
          }
          if (el._state != 'modified') {
            el._state = 'added'
          }
        }
      })
      if (error) {
        this.$alert(error, '提示')
          .then(() => {
            if (error == '付款方式:不能为空,请修改后重新操作') this.$refs['payway' + _index].setFocus()
          })
          .catch(() => {
            if (error == '付款方式:不能为空,请修改后重新操作') this.$refs['payway' + _index].setFocus()
          })
        return
      }
      this.chargeDebounce = true
      if (skip != 'skip') {
        let target = evt.target
        if (target.nodeName == 'SPAN') {
          target = evt.target.parentNode
        }
        target.blur()
      }
      this.$confirm('确定要收款吗？', '提示')
        .then(() => {
          let patientcode = this.tableListBelow[0].patientcode
          receiveRefund({
            chargeItems: this.tableList,
            moneyamountpaid: this.statisticsList[1].count,
            patientcode,
            patientname: this.tableListBelow[0].patientname,
            version: this.version,
          })
            .then(() => {
              this.chargeDebounce = false
              this.getList()
              this.$alert('收款成功', '提示', {
                confirmButtonText: '确定',
                type: 'success',
              })
                .then(() => {
                  this.openPrintTitle = true
                })
                .catch(() => {
                  this.openPrintTitle = true
                })
              const loading = this.$loading({
                lock: true,
                text: '正在插入中间库...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)',
              })
              middleDbInterface({ patientcode })
                .then(() => {
                  loading.close()
                })
                .catch(() => {
                  loading.close()
                })
            })
            .catch((error) => {
              console.error(error)
              this.chargeDebounce = false
            })
        })
        .catch(() => {
          this.chargeDebounce = false
        })
    },

    // 复查额度
    handleReview() {
      this.openLimit = true
      this.$refs.reviewQuota.showDialog()
    },
    // 复查额度内查询
    limitSearch() {
      this.$modal.msg('复查额度内查询')
    },
    // 复查额度确定
    discountConfirm() {
      this.$modal.msg('复查额度确定')
      this.openLimit = false
    },
    // 工费查询
    handleCheck() { },
    //缴费单打印
    handelPrint() {
      if (this.tableListBelow.length < 1) {
        this.$alert('体检者信息不存在!', '提示')
        return
      }
      let id = this.tableListBelow[0].id
      if (!id) {
        this.$alert('体检者信息不存在!', '提示')
        return
      }
      this.$confirm('是否打印缴费单', '打印', {
        type: 'warning',
      })
        .then(() => {
          this.$refs.paymentBills.showDialog(id)
        })
        .catch(() => { })
    },
  },
}
</script>

<style lang="scss" scoped>
.app-container {
  min-height: auto;
  padding: 0;
}

.proceeds-left {
  .statistics {
    display: flex;
    justify-content: space-between;

    .item-list {
      flex: 1;
      display: flex;
      overflow: auto;

      // min-width: 400px;
      .item {
        width: 100px;
        padding: 12px 12px;
        margin-right: 4px;
        background: #f7f8fa;
        border-radius: 10px;

        .title {
          font-size: 12px;
          line-height: 17px;
          color: #858586;
          margin-bottom: 5px;
          white-space: nowrap;
        }

        .number {
          font-weight: 600;
          font-size: 16px;
          margin-top: 8px;
          color: #{'var(--theme)'} !important;
        }
      }
    }

    .btn-list {
      display: flex;
      flex-direction: column;
      margin-left: 20px;
    }
  }
}
</style>
<style lang="scss">
.proceeds-left {
  overflow-y: auto;
}

.proceeds-left .el-table--medium .el-table__cell {
  padding: 0;
  height: 32px;

  .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
    background: transparent;
  }

  .text-left .el-input__inner {
    text-align: left !important;
  }

  .el-input__inner {
    padding: 0 8px;
    border-width: 0;
    text-align: center;
    background: transparent;

    &:focus {
      border-width: 1px;
      text-align: left;
    }
  }

  .el-input-number.is-controls-right .el-input-number__increase,
  .el-input-number.is-controls-right .el-input-number__decrease {
    border: none;
    background-color: transparent;
  }
}

.proceeds-left .item {
  .el-input__inner {
    padding: 0;
  }

  .el-input-number.is-controls-right .el-input__inner {
    padding: 0;
    padding-left: 2px;
  }

  .el-input-number__decrease,
  .el-input-number__increase {
    width: 15px;
  }
}
</style>
