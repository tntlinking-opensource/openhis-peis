<template>
  <el-col :span="12">
    <div class="flex-direction-column refund-right" style="overflow-y: auto">
      <el-row style="width: 100%; margin-bottom: 3px; flex: 1">
        <div class="flex-direction-column" style="min-height: 300px">
          <!-- 按钮 -->
          <el-form size="small" :inline="true" class="no-margin-bottom">
            <el-form-item>
              <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['basis:registration:returnItem:add']">新增</el-button>
              <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleRemove" v-hasPermi="['basis:registration:returnItem:remove']" :disabled="multiple">删除</el-button>
              <el-button type="primary" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh" v-hasPermi="['basis:registration:returnItem:refresh']">刷新</el-button>
              <el-button type="success" plain icon="el-icon-document-copy" size="mini" @click="handleSplit" v-hasPermi="['basis:registration:returnItem:split']" :disabled="single">拆分</el-button>
            </el-form-item>
          </el-form>
          <!-- 上表格 -->
          <div class="table-box" style="min-height: 200px">
            <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="序列" width="55" type="index" align="center" />
              <el-table-column label="应收应退" prop="moneyamount" align="center" min-width="100px">
                <template slot-scope="scope">
                  <span>{{ scope.row.moneyamount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="实收实退" prop="moneyamountpaid" align="center" min-width="120px">
                <template slot-scope="scope">
                  <!-- 修改保留小数 -->
                  <span v-if="scope.row.ffeecharged == 1 || scope.row._state == 'modified'" style="padding: 0 8px">{{ scope.row.moneyamountpaid.toFixed(2) }}</span>
                  <!-- :disabled="scope.row.idPayway != null" -->
                  <el-input-number v-else :ref="'moneyamountpaid' + scope.$index" v-model="scope.row.moneyamountpaid" @change="moneyChange(scope.$index)" controls-position="right" :precision="2" :max="0" size="small" class="text-left" style="width: 100%; text-align: left" />
                </template>
              </el-table-column>
              <el-table-column label="付款方式" prop="payway" align="center" min-width="130px" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.ffeecharged == 1 || scope.row._state == 'modified'">{{ scope.row.payway }}</span>
                  <input-select :ref="'payway' + scope.$index" v-else :selectData="selectData" @change="selectChange($event, scope.$index)" selectSize="small" :initialValue="scope.row.payway"></input-select>
                </template>
              </el-table-column>
              <el-table-column label="统收" prop="isTotalcharge" align="center" min-width="60px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.isTotalcharge == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已收" prop="yishou" align="center" min-width="60px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.yishou == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已退" prop="yitui" align="center" min-width="60px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.yitui == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="卡号" prop="cardno" align="center" show-overflow-tooltip min-width="150px">
                <template slot-scope="scope">
                  <template v-if="scope.row.idPayway == 22 || scope.row.idPayway == 24">
                    <el-input :ref="'txTradeNo' + scope.$index" v-if="scope.row._state != 'modified'" v-model="scope.row.txTradeNo" clearable size="small"></el-input>
                    <span v-else>{{ scope.row.txTradeNo }}</span>
                  </template>
                  <template v-else>
                    <el-input :ref="'cardno' + scope.$index" v-if="scope.row._state != 'modified'" v-model="scope.row.cardno" clearable size="small"></el-input>
                    <span v-else>{{ scope.row.cardno }}</span>
                  </template>
                </template>
              </el-table-column>
              <el-table-column label="收费员" prop="feechargerName" align="center" show-overflow-tooltip min-width="100px" />
              <el-table-column label="收退费时间" prop="feechargetime" align="center" show-overflow-tooltip min-width="160px" />
              <el-table-column label="备注" prop="note" align="center" min-width="280px">
                <template slot-scope="scope">
                  <el-input v-if="scope.row.yishou != 1 && scope.row.yitui != 1" v-model="scope.row.note" clearable type="textarea" size="small" :rows="1"></el-input>
                  <span v-else>{{ scope.row.note }}</span>
                </template>
              </el-table-column>
              <el-table-column label="加项" prop="isAdd" align="center" min-width="80px">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.isAdd == 1"></el-checkbox>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-row>

      <!-- 中间 -->
      <el-row style="width: 100%; height: 99px">
        <el-col :span="24" style="margin-bottom: 3px; height: 96px; overflow-x: auto">
          <div class="statistics">
            <div class="item" v-for="(item, index) in statisticsList" :key="index">
              <div class="title">{{ item.title }}</div>
              <el-input-number v-if="index == 3" v-model="item.count" :precision="2" :min="0.0" style="width: 100%; padding: 0" controls-position="right"></el-input-number>
              <div class="number" v-else :style="{ '--theme': theme }">{{ item.count.toFixed(2) }}</div>
            </div>
            <div class="btn-list">
              <el-button type="warning" @click="handelPrint">缴费单打印</el-button>
              <el-button type="primary" style="width: 100%; margin-left: 0; margin-top: 5px" @click="handleRefund">退款</el-button>
            </div>
          </div>
        </el-col>
      </el-row>

      <!-- 下表格 -->
      <el-row style="width: 100%">
        <el-col :span="24">
          <!-- 表格 -->
          <el-table border v-loading="loadingBelow" :data="tableListBelow" height="200px" stripe>
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="收费项目" prop="examfeeitemName" align="center" show-overflow-tooltip min-width="120px" />
            <el-table-column label="原始单价" prop="price" align="center" show-overflow-tooltip min-width="80px">
              <template slot-scope="scope">
                {{ scope.row.price ? Number(scope.row.price).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="优惠单价" prop="factprice" align="center" show-overflow-tooltip min-width="80px">
              <template slot-scope="scope">
                {{ scope.row.factprice ? Number(scope.row.factprice).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="退费金额" prop="endtuiprice" align="center" show-overflow-tooltip min-width="80px">
              <template slot-scope="scope">
                {{ scope.row.endtuiprice ? Number(scope.row.endtuiprice).toFixed(2) : '0.00' }}
              </template>
            </el-table-column>
            <el-table-column label="已退" prop="FMarkFeereturn" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FMarkFeereturn == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="操作" prop="endtuiprice" align="center" min-width="80px">
              <template slot-scope="scope">
                <a @click="handleDelete(scope.row)" class="delete-now" v-if="scope.row.FMarkFeereturn != 1">删除</a>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>

      <!-- 拆分弹窗 -->
      <price-split ref="priceSplit" @confirmSplit="confirmSplit"></price-split>
      <!-- 打印缴费单 -->
      <payment-bills ref="paymentBills"></payment-bills>
    </div>
  </el-col>
</template>

<script>
import { getChargeData, receiveRefund, getCardData, getMemberData } from '@/api/reception/proceeds.js'
import { getExamfeeByPatientCode, checkInspection, noticeApi, returnItem, receiveTjkCard, refundThirdPayment } from '@/api/reception/return_item.js'
import { middleDbInterface } from '@/api/reception/registration.js'

import priceSplit from '../proceeds/price_split.vue'
import paymentBills from '@/views/preview/payment_bills/index.vue'
export default {
  components: {
    priceSplit,
    paymentBills,
  },
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 上方表格****************
      // 表格加载
      loading: false,
      // 左侧选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      // 表格索引
      tableIndex: 0,
      // 付款方式选择
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
      // 体检者信息
      patientInfo: {},
      // 体检者id
      userId: undefined,
      // 总额
      moneyamountpaid: undefined,
      // 应付总额
      moneyamount: undefined,
      // 体检号
      patientcode: undefined,
      // 体检姓名
      patientname: undefined,
      // 版本号
      version: undefined,
      // 打印弹窗
      openPrint: false,

      // 下方表格********************
      loadingBelow: false,
      // 表格数据
      tableListBelow: [],
      // 退款防抖
      refundDebounce: false,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    watchInputData() {
      return this.statisticsList[3].count
    },
  },
  watch: {
    tableList: {
      handler(newValue) {
        if (this.patientcode) {
          this.statisticsList[0].count = 0
          this.statisticsList[1].count = 0
          this.statisticsList[4].count = 0
          newValue.forEach((el) => {
            if (el.ffeecharged == 1 && Number(el.moneyamount) >= 0) {
              el.yishou = 1
            } else {
              el.yishou = 0
            }
            if (el.ffeecharged == 1 && Number(el.moneyamount) < 0) {
              el.yitui = 1
            } else {
              el.yitui = 0
            }
            el.moneyamount ? (this.statisticsList[0].count = Number((this.statisticsList[0].count + Number(parseFloat(el.moneyamount).toFixed(2))).toFixed(2))) : ''
            el.moneyamountpaid ? (this.statisticsList[1].count = Number((this.statisticsList[1].count + Number(parseFloat(el.moneyamountpaid).toFixed(2))).toFixed(2))) : ''
          })
          this.statisticsList[2].count = this.moneyamount - this.statisticsList[1].count
          let num = this.statisticsList[3].count - this.statisticsList[4].count
          this.statisticsList[5].count = num >= 0 ? num : 0
        }
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
  },
  created() {
    this.bus.$on('handleRefundRight', (patientInfo, version) => {
      this.patientInfo = patientInfo
      this.userId = patientInfo.id
      this.moneyamountpaid = patientInfo.moneyamountpaid
      this.moneyamount = patientInfo.moneyamount
      this.patientcode = patientInfo.patientcode
      this.patientname = patientInfo.patientname
      this.version = version
      this.handleChargeData()
    })
  },
  beforeDestroy() {
    this.bus.$off('handleRefundRight')
  },
  methods: {
    // 获取体检者信息
    handleChargeData() {
      this.loading = true
      getChargeData({ patientcode: this.patientcode })
        .then(({ data }) => {
          if (data) {
            data.forEach((el) => {
              el.tableIndex = 'tableIndex' + this.tableIndex++
              if (el.moneyamount) el.moneyamount = el.moneyamount.toFixed(2)
              el._state = 'modified'
            })
            this.tableList = data
            this.handleExamfee()
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 实收实退修改，应收应退同步修改
    moneyChange(index) {
      this.tableList[index].moneyamount = Number(this.tableList[index].moneyamountpaid).toFixed(2)
    },
    // 获取右下角退费项目信息
    handleExamfee() {
      this.loadingBelow = true
      getExamfeeByPatientCode({
        patientCode: this.patientcode,
        type: 2,
      })
        .then(({ data }) => {
          this.tableListBelow = data
          this.loadingBelow = false
          this.$nextTick(() => {
            // this.tableListBelow.forEach((el) => {
            //   if (el.FMarkFeereturn != 1 && el.sfjx == 1) {
            //     this.tableList.push({
            //       tableIndex: 'tableIndex' + this.tableIndex++,
            //       idPayway: undefined,
            //       payway: undefined,
            //       isTotalcharge: 0,
            //       ffeecharged: 0,
            //       _state: 'added',
            //       isChange: 1,
            //       isAdd: 1,
            //       moneyamount: el.endtuiprice.toFixed(2),
            //       moneyamountpaid: el.endtuiprice.toFixed(2),
            //     })
            //   }
            // })
            if (this.statisticsList[2].count != 0) {
              this.handleAdd()
            }
          })
        })
        .catch(() => {
          this.loadingBelow = false
        })
    },
    // 新增
    handleAdd() {
      if (!this.patientcode) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      var obj = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        idPayway: undefined,
        payway: undefined,
        isTotalcharge: 0,
        ffeecharged: 0,
        _state: 'added',
        isChange: 1,
      }
      if (!this.tableList.length) {
        obj = {
          ...obj,
          moneyamount: this.moneyamount ? this.moneyamount.toFixed(2) : '0.00',
          moneyamountpaid: this.moneyamount ? this.moneyamount.toFixed(2) : '0.00',
        }
      } else {
        obj = {
          ...obj,
          moneyamount: '',
          moneyamountpaid: 0,
        }
        let moneyamount = 0
        let moneyamountpaid = 0
        this.tableList.forEach((el) => {
          if (el.moneyamount) moneyamount += parseFloat(el.moneyamount)
          moneyamountpaid += parseFloat(el.moneyamountpaid)
        })
        obj.moneyamountpaid = (this.moneyamount - moneyamountpaid).toFixed(2)
        obj.moneyamount = (this.moneyamount - moneyamount).toFixed(2)
      }
      this.tableList.push(obj)
    },
    // 删除
    handleRemove() {
      this.ids.forEach((el) => {
        for (let i = this.tableList.length - 1; i >= 0; i--) {
          if (this.tableList[i].tableIndex == el.tableIndex) {
            let alert = ''
            if (this.tableList[i].ffeecharged == 1) {
              alert = '选择的收费信息中第' + (i + 1) + '条收费信息已经收费，不能删除'
            }
            if (this.tableList[i].isAdd == 1) {
              alert = '选择的收费信息中第' + (i + 1) + '条收费信息是加项收费，不能删除'
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
      if (!this.patientcode) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      this.handleChargeData()
    },
    // 拆分
    handleSplit() {
      let alert = ''
      if (this.ids[0].ffeecharged == 1) {
        alert = '已经收费，不可更改'
      } else if (this.ids[0].kashou == '1') {
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
      this.tableList.forEach((el) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          el.moneyamount = (Number(el.moneyamount) - splitMoney).toFixed(2)
          el.moneyamountpaid = (Number(el.moneyamountpaid) - splitMoney).toFixed(2)
        }
      })
      var obj = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        idPayway: undefined,
        payway: undefined,
        moneyamount: splitMoney.toFixed(2),
        moneyamountpaid: splitMoney.toFixed(2),
        isTotalcharge: 0,
        ffeecharged: 0,
        _state: 'added',
      }
      this.tableList.push(obj)
      this.$message({
        message: '拆分成功',
        type: 'success',
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 付款方式返回参数
    selectChange($event, index) {
      let allReturn = true
      this.tableListBelow.forEach((el) => {
        if (!el.FMarkFeereturn) {
          allReturn = false
        }
      })
      if (allReturn) {
        this.$alert('没有需要退款的项目', '提示')
        return
      }
      if ($event.moneyamountpaid > 0) {
        this.$alert('退款金额应为负数', '提示')
        return
      }
      let type = $event.id
      this.$set(this.tableList[index], 'idPayway', type)
      this.$set(this.tableList[index], 'payway', $event.paywayName)

      let cardNo = ''
      let payId = ''
      // 24-11-22 随行付id44换成99 25-10-31 加上id100的通联支付2
      // if (type == 6 || type == 7 || type == 18 || type == 19 || type == 22 || type == 24 || type == 99) {
      if (type == 6 || type == 7 || type == 18 || type == 19 || type == 22 || type == 24 || type == 99 || type == 100) {
        if (this.tableList[index].moneyamountpaid == 0) {
          this.$alert('退款金额不能为0,请重新输入', '提示')
          this.$set(this.tableList[index], 'idPayway', undefined)
          this.$set(this.tableList[index], 'payway', undefined)
          this.$refs['payway' + index].initData()
          this.$nextTick(() => {
            this.$refs['moneyamountpaid' + index].focus()
          })
          return
        }
        this.loading = true
        let error = ''
        let yishouList = []
        this.tableList.forEach((el) => {
          if (el.idPayway == type && el.yishou == 1) {
            yishouList.push(el)
          }
        })
        if (yishouList.length > 1) {
          if (!this.tableList[index].cardno) {
            error = '请输入已收中要退款到的卡号'
          } else {
            let yishou = {}
            // let yituiList = []
            let yishouSum = 0
            let yituiSum = 0
            this.tableList.forEach((el) => {
              if (el.idPayway == type && (el.yitui == 1 || (el.yishou != 1 && el.yitui != 1 && el._state == 'modified')) && (el.cardno == this.tableList[index].cardno || el.txTradeNo == this.tableList[index].cardno)) {
                // yituiList.push(el)
                yituiSum += Number(el.moneyamountpaid)
              }
            })
            yishouList.forEach((el) => {
              if (el.cardno == this.tableList[index].cardno || el.txTradeNo == this.tableList[index].cardno) {
                yishou = el
                yishouSum += Number(el.moneyamountpaid)
              }
            })
            if (Math.round(Number(yishouSum) + yituiSum + this.tableList[index].moneyamountpaid) >= 0) {
              cardNo = yishou.cardno || yishou.txTradeNo
              payId = yishou.id
            } else {
              error = '退款金额超出所填卡号剩余退款上限,请修改金额或卡号后重试'
            }
          }
        } else {
          let sum = 0
          this.tableList.forEach((el) => {
            if (el.idPayway == type && el.yishou == 1) {
              sum += Number(el.moneyamountpaid)
              cardNo = el.cardno || el.txTradeNo
              payId = el.id
            }
          })
          if (0 - Number(this.tableList[index].moneyamountpaid) > sum) {
            error = '该付款方式已超出退款金额上限,请切换退款方式后重试'
          }
          this.$set(this.tableList[index], 'txTradeNo', cardNo)
          this.$set(this.tableList[index], 'cardno', cardNo)
        }
        if (error) {
          this.$alert(error, '提示')
          this.$refs['payway' + index].initData()
          this.$set(this.tableList[index], 'idPayway', undefined)
          this.$set(this.tableList[index], 'payway', undefined)
          this.$set(this.tableList[index], 'txTradeNo', undefined)
          this.$set(this.tableList[index], 'cardno', undefined)
          this.$refs['payway' + index].setFocus()
          this.loading = false
          return
        }
        this.$set(this.tableList[index], 'txTradeNo', this.tableList[index].cardno)
        // this.tableList.forEach((el) => {
        //   if (el.idPayway == type && el.yishou == 1) {
        //     cardNo = el.cardno
        //     payId = el.id
        //   }
        // })
        // if (type != 22 && type != 24 && type != 44) {
        console.log("type的值是", type);

        if (type != 22 && type != 24 && type != 99 && type != 100) {

          this.$confirm(`是否将费用退回至${(type == 6 ? '会员卡' : type == 7 ? '体检卡' : type == 18 ? '家庭卡积分' : '家庭卡余额') + cardNo}中?`, '提示')
            .then(() => {
              let formdata = {
                branchCenter: this.$getCookie('cid'),
                cardId: cardNo,
                consumeType: '0',
                jsf: type == 6 ? 0 - this.tableList[index].moneyamountpaid : 0 - this.tableList[index].moneyamountpaid,
                limit: type == 6 ? 0 - this.tableList[index].moneyamountpaid : 0 - this.tableList[index].moneyamountpaid,
                kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 18 ? 7 : type == 19 ? 6 : '',
                // kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 22 ? 9 : '',
                memotext: '退款',
              }
              let data = {
                _state: 'added',
                cardno: cardNo,
                ffeecharged: 0,
                idPayway: type,
                payway: $event.paywayName,
                isChange: 1,
                isTotalcharge: 0,
                kashou: 1,
                kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 18 ? 7 : type == 19 ? 6 : '',
                moneyamount: type == 6 ? this.tableList[index].moneyamountpaid : this.tableList[index].moneyamountpaid,
                moneyamountpaid: type == 6 ? this.tableList[index].moneyamountpaid : this.tableList[index].moneyamountpaid,
                yishou: 0,
                yitui: 0,
              }
              if (type == 7) {
                getCardData({
                  cardId: cardNo,
                })
                  .then(({ data: data1 }) => {
                    formdata = {
                      ...data1,
                      ...formdata,
                    }
                    // return
                    this.handleCardRefund('receiveTjkCard', formdata, data)
                  })
                  .catch((error) => {
                    console.error(error)
                    this.loading = false
                  })
              } else if (type == 6) {
                getMemberData({
                  cardNo,
                })
                  .then(({ data: data1 }) => {
                    formdata = {
                      ...data1,
                      ...formdata,
                    }
                    // return
                    this.handleCardRefund('receiveMemberCard', formdata, data)
                  })
                  .catch((error) => {
                    console.error(error)
                    this.loading = false
                  })
              } else {
                data.idPayway = type
                formdata.idPayway = type
                formdata.cardNo = cardNo
                formdata.idcardno = this.patientInfo.idcardno
                formdata.patientcode = this.patientcode
                formdata.money = data.moneyamountpaid
                this.handleCardRefund('receivesFamilyCard', formdata, data)
              }
            })
            .catch(() => {
              this.$set(this.tableList[index], 'idPayway', undefined)
              this.$set(this.tableList[index], 'payway', undefined)
              this.$set(this.tableList[index], 'txTradeNo', undefined)
              this.$set(this.tableList[index], 'cardno', undefined)
              this.$refs['payway' + index].initData()
              this.$refs['payway' + index].setFocus()
              this.loading = false
            })
        } else {
          if (this.refundDebounce) {
            this.$alert('退款中,请勿重复操作!', '提示')
            return
          }
          this.$confirm(`是否执行${type == 22 ? '通联' : type == 99 ? '随行付' : type == 100 ? '通联支付2' : '二维码'}支付退款功能?`, '提示', {
            showClose: false,
            closeOnClickModal: false,
            closeOnPressEscape: false,
          })
            .then(() => {
              this.refundDebounce = true
              let data = {
                _state: 'added',
                cardno: cardNo,
                ffeecharged: 0,
                idPayway: type,
                payway: $event.paywayName,
                isChange: 1,
                isTotalcharge: 0,
                kashou: 1,
                // kkfs: type == 99 ? 10 : 9,
                // 加上了通联支付2 type=100  kkfs为11
                kkfs: type == 99 ? 10 : type == 100 ? 11 : 9,
                moneyamount: type == 6 ? this.tableList[index].moneyamountpaid : this.tableList[index].moneyamountpaid,
                moneyamountpaid: type == 6 ? this.tableList[index].moneyamountpaid : this.tableList[index].moneyamountpaid,
                txTradeNo: cardNo,
                yishou: 0,
                yitui: 0,
              }
              refundThirdPayment({
                data,
                id: payId,
                patientname: this.patientname,
                patientcode: this.patientcode,
                version: this.version,
                // kkfs: type == 99 ? 10 : 9,
                // 加上了通联支付2 type=100  kkfs为11
                kkfs: type == 99 ? 10 : type == 100 ? 11 : 9,
              })
                .then(() => {
                  this.handleRefresh()
                  this.$modal.msgSuccess(`${type == 22 ? '通联' : type == 99 ? '随行付' : '二维码'}退款成功`)
                  this.loading = false
                  this.refundDebounce = false
                })
                .catch((error) => {
                  console.error(error)
                  this.loading = false
                  this.refundDebounce = false
                })
            })
            .catch(() => {
              this.$nextTick(() => {
                this.$set(this.tableList[index], 'idPayway', '')
                this.$set(this.tableList[index], 'payway', '')
              })
              this.loading = false
            })
        }
      } else {
        let sum = 0
        this.tableList.forEach((el) => {
          if (el.idPayway == type && (el.yishou == 1 || el.yitui == 1)) {
            sum += Number(el.moneyamountpaid)
          }
        })
        if (0 - Number(this.tableList[index].moneyamountpaid) > sum) {
          this.$alert('该付款方式已超出退款金额上限,请切换退款方式后重试', '提示')
          this.$refs['payway' + index].initData()
          this.$set(this.tableList[index], 'idPayway', undefined)
          this.$set(this.tableList[index], 'payway', undefined)
          this.$refs['payway' + index].setFocus()
          return
        }
      }
      if (['18', '19', 'ff80808172a5f6210172a809a9cb2222', '17', '9'].includes(this.tableList[index].idPayway)) {
        let cardno = ''
        this.tableList.forEach((el) => {
          if (el.idPayway == $event.id && el.yishou == 1) {
            cardno = el.cardno
          }
        })
        this.$set(this.tableList[index], 'cardno', cardno)
      }
    },
    // 执行卡退款
    handleCardRefund(url, formdata, data) {
      receiveTjkCard(url, {
        formdata,
        patientname: this.patientname,
        patientcode: this.patientcode,
        version: this.version,
        data,
        type: 'saveCard',
      })
        .then(() => {
          this.handleRefresh()
          this.$modal.msgSuccess('卡退款成功')
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 退款
    handleRefund() {
      if (!this.patientcode) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      let allReturn = true
      this.tableListBelow.forEach((el) => {
        if (!el.FMarkFeereturn) {
          allReturn = false
        }
      })
      if (allReturn) {
        this.$alert('没有需要退款的项目', '提示')
        return
      }
      let error = ''
      this.tableList.forEach((el, index) => {
        // if (!error && el.moneyamountpaid == 0 && el.yishou != 1) {
        //   error = '     '
        //   this.$refs['moneyamountpaid' + index].focus()
        // }
        if (!error && !el.idPayway && el.yishou != 1) {
          error = '付款方式:不能为空,请修改后重新操作'
          this.$refs['payway' + index].setFocus()
        } else if (['18', '19', 'ff80808172a5f6210172a809a9cb2222', '17', '9'].includes(el.idPayway) && el.yishou != 1) {
          if (el.idPayway != 22 && el.idPayway != 24 && el.idPayway != 44 && !el.cardno) {
            if (this.$refs['cardno' + index]) {
              this.$refs['cardno' + index].focus()
              error = '第' + (index + 1) + '条数据卡号不能为空,请修改后重新操作'
            }
          }
        }
      })
      if (!error && this.statisticsList[2].count < 0) {
        error = '费用还没有退完'
      }
      if (!error && this.statisticsList[2].count > 0) {
        error = '费用没有收齐'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      checkInspection({ key: this.patientcode }).then(({ data }) => {
        if (data) {
          this.$confirm('退项项目中存在检验科项目，是否通知检验科？', '提示')
            .then(() => {
              // 缺少通知检验科接口
              noticeApi({
                patientcode: this.patientcode,
              }).then(() => {
                this.doRefund()
              })
            })
            .catch(() => {
              this.doRefund()
            })
        } else {
          this.$confirm('确定要退款吗？', '提示')
            .then(() => {
              this.doRefund()
            })
            .catch(() => { })
        }
      })
    },
    // 执行退款
    doRefund() {
      if (this.refundDebounce) {
        this.$alert('退款中,请勿重复操作!', '提示')
        return
      }
      this.refundDebounce = true
      
      receiveRefund({
        chargeItems: this.tableList,
        moneyamountpaid: this.statisticsList[1].count,
        patientcode: this.patientcode,
        patientname: this.patientname,
        version: this.version,
      })
        .then(() => {
          this.handleChargeData()
          this.$message({
            type: 'success',
            message: '退款成功',
          })
          this.refundDebounce = false
          const loading = this.$loading({
            lock: true,
            text: '正在插入中间库...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          middleDbInterface({ patientcode: this.patientcode })
            .then(() => {
              loading.close()
            })
            .catch(() => {
              loading.close()
            })
        })
        .catch((err) => {
          console.error(err)
          this.refundDebounce = false
        })
    },
    // 删除取消项目退款
    handleDelete(data) {
      this.$confirm('确定要删除?', '提示', {})
        .then(() => {
          this.loadingBelow = true
          returnItem({
            id: data.id,
          })
            .then(() => {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.bus.$emit('handleGetList')
              this.handleChargeData()
              this.loadingBelow = false
            })
            .catch(() => {
              this.loadingBelow = false
            })
        })
        .catch(() => { })
    },
    //缴费单打印
    handelPrint() {
      if (!this.patientcode) {
        this.$alert('请添加体检人员后再试', '提示')
        return
      }
      this.$confirm('是否打印缴费单', '打印', {
        type: 'warning',
      })
        .then(() => {
          this.$refs.paymentBills.showDialog(this.userId)
        })
        .catch(() => { })
    },
  },
}
</script>

<style lang="scss" scoped>
.statistics {
  display: flex;
  justify-content: space-between;
  width: 830px;
  height: 96px;
  padding: 10px;

  .item {
    width: 100px;
    height: 100%;
    padding: 12px 12px;
    background: #f7f8fa;
    border-radius: 10px;

    .title {
      font-size: 12px;
      line-height: 17px;
      color: #858586;
      margin-bottom: 5px;
    }

    .number {
      font-weight: 600;
      font-size: 16px;
      line-height: 34px;
      color: #{'var(--theme)'} !important;
    }
  }

  .btn-list {
    display: flex;
    flex-direction: column;
    margin-left: 20px;

    el-button {
      width: 100%;
    }
  }
}

.delete-now {
  color: #ff9292;
}

.delete-now:hover {
  color: #1890ff;
}
</style>

<style lang="scss">
.refund-right .el-table--medium .el-table__cell {
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

.refund-right .item {
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
