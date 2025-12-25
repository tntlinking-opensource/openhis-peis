<!-- 家庭会员-充值 麦沃德科技 开发人:清风 -->
<template>
    <div class="add-container">
        <el-dialog title="充值" :visible.sync="open" width="856px" :close-on-click-modal="false">
            <el-form size="small" :inline="true" label-width="80px" :model="form">
              <el-form-item label="家庭卡号" style="margin-right:32px;">
                <el-input style="width:308px; height: 40px;" placeholder="请输入卡号,按回车加载卡信息"
                          @keyup.enter.native="handleQuery" v-model="form.cardno"
                ></el-input>
              </el-form-item>
              <el-form-item label="主持卡人" style="margin-right:0;">
                <el-input style="width:308px; height: 40px;" placeholder="此卡暂无数据" v-model="form.patientname"
                          :readonly="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="充值积分" style="margin-right:32px;">
                <el-input-number style="width:308px;" placeholder="0" controls-position="right" :min="0" :max="999999"
                                 v-model="form.jf"
                ></el-input-number>
              </el-form-item>
                <el-form-item label="剩余积分" style="margin-right:0;">
                  <el-input style="width:308px;" placeholder="0" v-model="form.startJf" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="充值金额" style="margin-right:32px;">
                    <el-input-number style="width:308px;" placeholder="0" controls-position="right" :min="0" :max="999999" v-model="form.money"></el-input-number>
                </el-form-item>
                <el-form-item label="剩余金额" style="margin-right:0;">
                  <el-input style="width:308px; height: 40px;" placeholder="0" v-model="form.startMoney"
                            :readonly="true"
                  ></el-input>
                </el-form-item>
              <el-form-item label="付款方式" style="margin-right:32px;">
                <el-select style="width:308px; height: 40px;" placeholder="请选择" v-model="form.idPayway">
                  <el-option v-for="item in Payment" :key="item.id" :label="item.paywayName" :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
                <el-form-item label="卡说明" style="margin-right:auto;">
                  <el-input type="textarea" id="cardState" placeholder="请输入内容" style="width:728px;"
                            v-model="form.cardState" :readonly="true" :rows="3"
                  ></el-input>
                </el-form-item>
                <el-form-item label="备注" style="margin-right:auto;">
                  <el-input type="textarea" id="note" placeholder="请输入内容" style="width:728px;" v-model="form.note"
                            :rows="3"
                  ></el-input>
                </el-form-item>
            </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveCharge">保存</el-button>
            <el-button @click="onCancel">取消</el-button>
          </div>
        </el-dialog>
    </div>
</template>

<script>
  import {
    chargeLoadCard, chargeLoadCards,
    getPayWayData,
    saveCharge
  } from '@/api/custservice/family_members/family_members/family_members'

  export default {
    data() {
      return {
        open: false,
        form: {
          idcardno: undefined,
          patientname: undefined,
          patientcardno: undefined,
          idSex: undefined,
          dw: undefined,
          phone: undefined,
          birthdate: undefined,
          fzx: undefined,
          memberlevel: undefined,
          khjl: undefined,
          note: undefined,
          startJf: undefined,
          idPayway: undefined,
          money: undefined,
          jf: undefined
        },
        //全部支付方式
        Payment: []
      }
    },

    methods: {
      // 搜索卡号
      handleQuery() {
        chargeLoadCards(this.form).then(response => {
          this.form.patientname = response.data.msg.patientname
          this.form.startJf = response.data.msg.startJf
          this.form.startMoney = response.data.msg.startMoney
          this.form.cardState = response.data.msg.cardState
          this.form.money = response.data.msg.money
          this.form.jf = response.data.msg.jf
        })
      },
      //获取全部支付方式
      getPayWay() {
        getPayWayData().then(response => {
          this.Payment = response.data.records
        })
      },
      //窗口开启
      addWindow() {
        this.open = true
        this.getPayWay()
      },
      //充值
      saveCharge() {
        this.form.patientcardno = this.form.cardno
        saveCharge(this.form).then(response => {
          this.$modal.msgSuccess('充值成功')
          this.onCancel()
          this.$emit("getList")
        })
      },
      //取消
      onCancel() {
        this.open = false
        this.form.patientname = undefined
        this.form.startJf = undefined
        this.form.startMoney = undefined
        this.form.cardState = undefined
      }
        }
    }
</script>

<style scoped>
#cardState /deep/ .el-textarea__inner {
    min-height: 100px !important;
}
#note /deep/ .el-textarea__inner {
    min-height: 100px !important;
}
</style>
