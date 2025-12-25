<!--会员卡 麦沃德科技 予安 -->
<template>
  <el-dialog title="会员卡积分调整" :visible.sync="open" width="750px" append-to-body :close-on-click-modal="false" destroy-on-close @close="cancel">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="90px" v-loading="loadingCard" :rules="rules" hide-required-asterisk>
      <el-form-item label="会员卡号" prop="patientcardno">
        <el-input ref="cardno" v-model="queryParams.patientcardno" :readonly="readOnly" placeholder="请输入会员卡号" clearable style="width: 250px" @keyup.enter.native="handleCardNum(1)" @change="cardnoChange"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input type="tel" v-model="queryParams.phone" :readonly="readOnly" placeholder="请输入电话消费" clearable style="width: 250px" @keyup.enter.native="handleCardNum(2)"> </el-input>
      </el-form-item>
      <el-form-item label="剩余积分" prop="balanceJf">
        <el-input :value="queryParams.balanceJf ? Number(queryParams.balanceJf).toFixed(2) : '0.00'" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="充值积分" prop="limit">
        <el-input type="number" v-model="queryParams.limit" readonly style="width: 250px" @blur="changeLimit"> </el-input>
      </el-form-item>
      <el-form-item label="卡说明" prop="cardState">
        <el-input v-model="queryParams.cardState" type="textarea" :rows="3" resize="none" readonly style="width: 600px"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="memotext">
        <el-input type="textarea" :rows="3" resize="none" v-model="queryParams.memotext" placeholder="请输入备注" clearable style="width: 600px"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getMemberData } from '@/api/reception/proceeds.js'
import { saveOrUpdateFee } from '@/api/finance/tally/tally_refund.js'

export default {
  data() {
    return {
      // 弹窗是否显示
      open: false,
      // 会员卡信息加载中
      loadingCard: false,
      // 查询参数
      queryParams: {
        patientcardno: undefined,
        phone: undefined,
        balanceJf: undefined,
        patientname: undefined,
        limit: undefined,
        cardState: undefined,
        memotext: undefined,
      },
      // 校验
      rules: {
        patientcardno: [{ required: true, message: '体检卡号不能为空', trigger: 'change' }],
        limit: [{ required: true, message: '充值积分不能为空', trigger: 'change' }],
      },
      // 退款时只读
      readOnly: false,

      // 表格数据
      tableData: [],
    }
  },
  created() {},
  methods: {
    dialogShow(info, ids) {
      this.readOnly = false
      this.queryParams = {
        patientcode: info.patientcode,
        patientName: info.patientname,
        idPayway: '6',
        jsf: ids.moneyamountpaid.toFixed(2),
        limit: (0 - ids.moneyamountpaid).toFixed(2),
        chargeParam: {
          moneyamount: ids.moneyamount,
          moneyamountpaid: ids.moneyamountpaid,
          isTotalcharge: 0,
          ffeecharged: 0,
          idPayway: '6',
          isChange: 1,
          _state: 'added',
          kashou: 1,
        },
        consumeType: '0',
        kkfs: 2,
        memotext: '消费',
      }
      if (ids.id) {
        this.readOnly = true
        this.queryParams.patientcardno = ids.cardno
        this.queryParams.memotext = '退积分'
        this.queryParams.jsf = 0 - this.queryParams.jsf
        this.queryParams.limit = 0 - this.queryParams.limit
        if (this.queryParams.chargeParam.moneyamount) {
          this.queryParams.chargeParam.moneyamount = 0 - this.queryParams.chargeParam.moneyamount
        }
        this.queryParams.chargeParam.moneyamountpaid = 0 - this.queryParams.chargeParam.moneyamountpaid
        this.queryParams.chargeParam.cardno = this.queryParams.patientcardno
        this.handleCardNum(1)
      }
      this.open = true
      this.$nextTick(() => {
        this.$refs.cardno.focus()
      })
    },
    // 充值积分保留1位小数
    changeLimit() {
      if (this.queryParams.limit) {
        this.$nextTick(() => {
          this.queryParams.limit = Number(this.queryParams.limit).toFixed(2)
          this.queryParams.jsf = 0 - Number(this.queryParams.limit).toFixed(2)
          this.queryParams.chargeParam.moneyamountpaid = 0 - Number(this.queryParams.limit).toFixed(2)
        })
      }
    },
    // 查询会员卡
    handleCardNum(type) {
      if (type == 1 && !this.queryParams.patientcardno) {
        this.$alert('请输入会员卡号后再试', '提示')
        return
      }
      if (type == 2 && !this.queryParams.phone) {
        this.$alert('请输入会员电话后再试', '提示')
        return
      }
      this.loadingCard = true
      getMemberData({
        patientcardno: this.queryParams.patientcardno,
        cardNo: this.queryParams.patientcardno,
        phone: this.queryParams.phone,
      })
        .then(({ data }) => {
          if (data.success == 'false') {
            this.$alert('会员卡号不存在,请重新输入')
          } else {
            this.cardId = data.Patientcardno
            this.queryParams = { ...this.queryParams, ...data }
          }
          this.loadingCard = false
        })
        .catch(() => {
          this.loadingCard = false
        })
    },
    // 体检号改变则提交中的体检号也要改变
    cardnoChange() {
      this.queryParams.chargeParam.cardno = this.queryParams.patientcardno
    },
    // 确定
    handleConfirm() {
      this.$refs['queryForm'].validate((valid) => {
        if (valid) {
          this.loadingCard = true
          this.$confirm('确定要操作?', '提示')
            .then(() => {
              let queryParams = JSON.parse(JSON.stringify(this.queryParams))
              // 新接口需要
              // queryParams.patientname = queryParams.patientName
              saveOrUpdateFee({
                ...queryParams,
                cardId: this.queryParams.patientcardno,
              })
                .then(({ data }) => {
                  this.$modal.msgSuccess('保存成功')
                  this.loadingCard = false
                  this.$emit('paySuccess', this.queryParams.patientcardno, '会员积分', this.queryParams.memotext)
                  this.cancel()
                })
                .catch(() => {
                  this.loadingCard = false
                })
            })
            .catch((error) => {
              console.error(error)
              this.loadingCard = false
            })
        }
      })
    },
    // 重置
    reset() {
      this.$refs['queryForm'].resetFields()
    },
    // 取消按钮
    cancel() {
      this.reset()
      this.open = false
    },
  },
}
</script>

<style></style>
