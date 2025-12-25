<!-- 通联支付弹窗 -->
<template>
  <el-dialog title="支付" :visible.sync="open" width="440px" append-to-body @close="cancel" :show-close = "false" destroy-on-close :close-on-click-modal="false" :close-on-press-escape="false">
    <div style="font-size: 30px; line-height: 30px">{{ tips }}</div>
    <div style="font-size: 16px; line-height: 30px">请不要进行操作,请确认是否支付成功</div>
    <div style="font-size: 16px; line-height: 30px; color: red" v-if="showPassword">请提醒客户输入支付密码</div>
    <el-input style="opacity: 0" ref="card" v-model="cardId" @blur="changeBlur" @change="complete" :readonly="once"></el-input>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" v-if="showPassword" @click="getTonglianResult">立即查询</el-button>
      <el-button @click="failCancel">取消支付</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { handlePay, handleAdvancePay, queryTongLian, querySuiXing } from '@/api/reception/proceeds.js'
export default {
  data() {
    return {
      // 弹窗 
      open: false,
      // 提示文字
      tips: '',
      // 扫码返回的付款码
      cardId: undefined,
      // 参数列表
      queryParams: {},
      // 执行一次
      once: false,
      // 是否为预收费
      payType: '',
      // 密码支付中
      showPassword: false,
      // 循环调用查询是否支付成功
      payTimer: null,
      // 循环查询时的订单id
      trxid: undefined,
      chargeId: undefined,
      // 支付方式  idPayway= 22 通联支付 idPayway= 99 随行付 idPayway= 100 通联支付2
      idPayway: '',
    }
  },
  methods: {
    dialogShow(info, ids, version, payType, idPayway) {
      this.tips = '请提醒用户扫码'
      this.showPassword = false
      this.trxid = undefined
      this.chargeId = undefined
      this.idPayway = idPayway
      this.queryParams = {
        patientcode: info.patientcode,
        patientname: info.patientname,
        version: version,
        idPayway: idPayway || '22',
        jsf: ids.moneyamountpaid,
        limit: ids.moneyamountpaid,
        chargeParam: {
          moneyamount: ids.moneyamount,
          moneyamountpaid: ids.moneyamountpaid,
          ffeecharged: 0,
          isTotalcharge: 0,
          idPayway: idPayway || '22',
          isChange: 1,
          _state: 'added',
          kashou: 0,
          isAdd: ids.isAdd,
        },
        consumeType: '0',
        deviceInfo: '1',
        // 原来kkfs判断
        // kkfs: idPayway == '99' ? 10 : 9,
        // 加上通联支付2
        kkfs: idPayway == '99' ? 10 : idPayway == '100' ? 11 : 9,
      }
      console.log("支付弹窗queryParams的值",this.queryParams);
      
      this.payType = payType
      if (payType == 'prepayment') {
        this.queryParams.chargeParam.note = ids.note
        this.queryParams.chargeParam.ffeecharged = 1
      }
      this.open = true
      this.$nextTick(() => {
        this.$refs.card.focus()
      })
    },
    // 防止失去焦点扫码失败
    changeBlur(type) {
      if (type) return
      this.$refs.card.focus()
    },
    // 完成扫码
    complete() {
      if (!this.once) {
        this.once = true
        this.tips = '支付中...'
        this.handleconfirm()
      }
    },
    // 确认支付
    handleconfirm() {
      if (this.payType == 'prepayment' || this.payType == 'settlement') {
        handleAdvancePay(this.payType == 'prepayment' ? 1 : 2, {
          ...this.queryParams,
          cardId: this.cardId,
          chargeId: this.chargeId,
          consumeId: this.trxid,
        })
          .then(({ data }) => {
            // this.$modal.msgSuccess('支付成功')
            // this.$emit('paySuccess', data.id, data.payNo, data.version, '通联支付（扫码支付）', 1)
            // this.cancel()
            if (data.version == 2000 && data.status == 'fail') {
              this.showPassword = true
              this.trxid = data.payNo
              this.chargeId = data.id
              this.payTimer = setInterval(() => {
                if (this.idPayway == '22') {
                  this.getTonglianResult()
                } else if (this.idPayway == '99') {
                  this.getSuiXingResult()
                }
              }, 1000)
            } else {
              this.once = false
              this.$modal.msgSuccess('支付成功')
              // this.$emit('paySuccess', data.id, data.payNo, data.version, this.idPayway == '22' ? '通联支付（扫码支付）' : '随行付', 1)
              this.$emit('paySuccess', data.id, data.payNo, data.version, this.idPayway == '22' ? '通联支付（扫码支付）' : this.idPayway == '100' ? '通联支付2' : '随行付', 1)
              this.cancel()
            }
          })
          .catch(() => {
            this.once = false
            // this.$emit('clearSelect')
            this.failCancel()
          })
      } else if (this.payType == 'settlement') {
      } else {
        handlePay({
          ...this.queryParams,
          cardId: this.cardId,
          chargeId: this.chargeId,
          consumeId: this.trxid,
        })
          .then(({ data }) => {
            if (data.version == 2000 && data.status == 'fail') {
              this.showPassword = true
              this.trxid = data.payNo
              this.chargeId = data.id
              this.payTimer = setInterval(() => {
                if (this.idPayway == '22') {
                  this.getTonglianResult()
                } else if (this.idPayway == '99') {
                  this.getSuiXingResult()
                }
              }, 1000)
            } else {
              this.once = false
              this.$modal.msgSuccess('支付成功')
              // this.$emit('paySuccess', data.id, data.payNo, data.version, this.idPayway == '22' ? '通联支付（扫码支付）' : '随行付')
              this.$emit('paySuccess', data.id, data.payNo, data.version, this.idPayway == '22' ? '通联支付（扫码支付）' : this.idPayway == '100' ? '通联支付2' : '随行付')
              this.cancel()
            }
          })
          .catch(() => {
            this.once = false
            // this.$emit('clearSelect')
            this.failCancel()
          })
      }
    },
    // 查询通联支付结果
    getTonglianResult() {
      queryTongLian({
        trxid: this.trxid,
        kkfs: this.queryParams.kkfs
      }).then(({ data }) => {
        if (data.retcode == 'SUCCESS') {
          if (data.trxstatus == '2000') {
          } else if (data.trxstatus == '0000') {
            this.handleconfirm()
            clearInterval(this.payTimer)
            this.payTimer = null
          } else {
            this.$modal.msgWarning('支付失败,请重试')
            this.failCancel()
            clearInterval(this.payTimer)
            this.payTimer = null
          }
        }
      })
    },
    // 查询随行付支付结果
    getSuiXingResult() {
      querySuiXing({
        ordNo: this.chargeId,
      }).then(({ data }) => {
        // if (data.retcode == 'SUCCESS') {
        if (data.respData.tranSts == 'PAYING') {
        } else if (data.respData.tranSts == 'SUCCESS') {
          this.handleconfirm()
          clearInterval(this.payTimer)
          this.payTimer = null
        } else {
          this.$modal.msgWarning('支付失败,请重试')
          this.failCancel()
          clearInterval(this.payTimer)
          this.payTimer = null
        }
        // }
      })
    },
    // 支付失败取消
    failCancel() {
      clearInterval(this.payTimer)
      this.payTimer = null
      this.cardId = undefined
      this.once = false
      this.open = false
      this.$emit('clearSelect')
    },
    // 支付成功关闭弹窗
    cancel() {
      clearInterval(this.payTimer)
      this.payTimer = null
      this.cardId = undefined
      this.once = false
      this.open = false
    },
  },
}
</script>
