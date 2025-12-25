<!-- 家庭卡 麦沃德科技 予安 -->
<template>
  <el-dialog title="家庭卡消费" :visible.sync="open" width="750px" append-to-body :close-on-click-modal="false">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="90px">
      <el-form-item label="家庭卡号" prop="cardId">
        <el-input v-model="queryParams.cardId" readonly style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item :label="'消费' + (queryParams.idPayway == 19 ? '金额' : '积分')" prop="jsf">
        <el-input v-model="queryParams.jsf" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="剩余金额" prop="balanceMoney">
        <el-input v-model="queryParams.balanceMoney" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="剩余积分" prop="balanceJf">
        <el-input v-model="queryParams.balanceJf" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="发卡人" prop="getter">
        <el-input v-model="queryParams.getter" readonly style="width: 250px"> </el-input>
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
import { handlePay, handleAdvancePay } from '@/api/reception/proceeds.js'

export default {
  data() {
    return {
      // 弹窗是否显示
      open: false,
      // 查询参数
      queryParams: {
        inputCode: null,
        phone: null,
        balanceJf: null,
        patientname: null,
        limit: null,
        branchCenter: null,
        branchCenter: null,
      },
      // 加载中
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
    }
  },
  methods: {
    dialogShow(info, ids, version, cardInfo,idcardno) {
      this.open = true
      this.queryParams = {
        patientcode: info.patientcode,
        patientName: info.patientname,
        idcardno,
        version,
        idPayway: ids.idPayway,
        jsf:  ids.moneyamountpaid.toFixed(2) ,
        limit: (0 - ids.moneyamountpaid).toFixed(2),
        chargeParam: {
          moneyamount: ids.moneyamount,
          moneyamountpaid: ids.moneyamountpaid,
          isTotalcharge: 0,
          ffeecharged: 0,
          idPayway: ids.idPayway,
          isChange: 1,
          _state: 'added',
          cardno: cardInfo.cardNo,
          kashou: 1,
        },
        consumeType: '0',
        kkfs: ids.idPayway == 19 ? '6' : '7',
        memotext: (ids.idPayway == 19 ? '余额' : '积分') + '消费',
        cardId: cardInfo.cardNo,
        balanceMoney: cardInfo.balanceMoney ? Number(cardInfo.balanceMoney).toFixed(2) : '0.00',
        balanceJf: cardInfo.balanceJf ? Number(cardInfo.balanceJf).toFixed(2) : '0.00',
        cardState: cardInfo.cardState,
        getter: cardInfo.getter,
        getterId: cardInfo.getterId,
      }
    },
    // 重置
    reset() {
      this.$refs['queryForm'].resetFields()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 确定
    handleConfirm() {
      let error = ''
      if (this.queryParams.idPayway == 19 && Number(this.queryParams.balanceMoney) < Number(this.queryParams.jsf)) {
        error = '消费金额大于剩余金额,请修改后重试'
      } else if (this.queryParams.idPayway == 18 && Number(this.queryParams.balanceJf) < Number(this.queryParams.jsf)) {
        error = '消费积分大于剩余积分,请修改后重试'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.loading = true
      this.$confirm('确定要操作?', '提示')
        .then(() => {
          let queryParams = JSON.parse(JSON.stringify(this.queryParams))
          // queryParams.limit = 0 - queryParams.limit
          if (this.isPrePay) {
            handleAdvancePay(queryParams)
              .then(({ data }) => {
                this.$modal.msgSuccess('保存成功')
                this.loading = false
                this.$emit('paySuccess', data.id, this.queryParams.cardId, data.version, this.queryParams.idPayway == 19 ? '家庭卡余额' : '家庭卡积分', 1, this.queryParams.jsf)
                this.cancel()
              })
              .catch(() => {
                this.loading = false
              })
          } else {
            handlePay(queryParams)
              .then(({ data }) => {
                this.$modal.msgSuccess('保存成功')
                this.loading = false
                this.$emit('paySuccess', data.id, this.queryParams.cardId, data.version, this.queryParams.idPayway == 19 ? '家庭卡余额' : '家庭卡积分')
                this.cancel()
              })
              // .catch(() => {
              //   this.loading = false
              // })
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style></style>
