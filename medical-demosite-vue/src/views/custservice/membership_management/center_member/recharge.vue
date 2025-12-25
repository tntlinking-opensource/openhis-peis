<!-- 会员卡充值 开发人：麦沃德矢北 -->
<template>
  <div class="add-container">
    <el-dialog title="积分充值" :visible="open" width="860px" append-to-body :close-on-click-modal="false">
      <el-form label-width="80px" :rules="rules" ref="form" :model="form" :inline="true" hide-required-asterisk>
        <el-form-item label="会员卡号" prop="patientcardno" style="margin-right:32px;">
          <el-input placeholder="请输入姓名" @keyup.enter.native="isMember" style="width:260px;"
            v-model="form.patientcardno"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone" style="margin-right:32px;">
          <el-input placeholder="请输入姓名" style="width:260px;" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="剩余积分" prop="balanceJf" style="margin-right:32px;">
          <el-input placeholder="" :readonly="true" style="width:260px;" v-model="form.balanceJf"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="patientname" style="margin-right:32px;">
          <el-input placeholder="请输入姓名" style="width:260px;" v-model="form.patientname"></el-input>
        </el-form-item>
        <el-form-item label="充值积分" prop="limit" style="margin-right:32px;">
          <el-input-number v-model="form.limit" style="width:260px ;" controls-position="right" :step=100 :min="1"
            :max="1000000000">
          </el-input-number>
        </el-form-item>
        <el-form-item label="卡说明" prop="cardState" style="margin-right:32px;">
          <el-input type="textarea" :readonly=true resize="none" rows="3" placeholder="" style="width:632px;"
            v-model="form.cardState"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="memo" style="margin-right:32px;">
          <el-input type="textarea" resize="none" rows="3" placeholder="请输入备注" style="width:632px;"
            v-model="form.memo"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button type="primary" plain @click="reset">重置</el-button>
        <el-button @click="onCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { rechargeDetail, getIsMember, addJf } from '../../../../api/custservice/membership_management/center_member';
export default {
  data() {
    return {
      // 表单效验
      rules: {
        cardNo: [{ required: true, message: "卡号不能为空不能为空", trigger: "change" }],

      },
      open: false,
      form: {
      },
      saveData: {
        formdata: {

        },


      },
      griddata: {

      },
      queryParams: {
        id: undefined
      }
    }
  },
  methods: {
    //保存
    save() {

      this.saveData.formdata.balanceJf = this.form.balanceJf
      this.saveData.formdata.branchCenter = '2'
      this.saveData.formdata.id = this.form.id
      this.saveData.formdata.limit = this.form.limit
      this.saveData.formdata.patientcardno = this.form.patientcardno
      this.saveData.formdata.patientcode = this.form.patientcode
      this.saveData.formdata.patientname = this.form.patientname
      this.saveData.formdata.phone = this.form.phone
      this.saveData.formdata.memotext = '充值'
      this.saveData.formdata.userName = '管理员'
     
      this.$refs["form"].validate(valid => {
        if (valid) {
          addJf(this.saveData).then(response => {
            
            this.$modal.msgSuccess("保存成功");
            this.open = false;

          })

        }
      });
    },
    //充值
    reset() {

    },
    isMember() {
      getIsMember().then(response => {
       
      })
    },
    //打开窗口
    addWindow(id) {
      this.reset()
      this.open = true;
      this.queryParams.id = id[0]
      rechargeDetail(this.queryParams).then(response => {
        this.form = response.data.peispatientarchive;
        this.form.balanceJf = response.data.card.balanceJf
        this.form.cardState = response.data.card.cardState
        this.form.memo = response.data.card.memo
      })
    },
    onCancel() {
      this.open = false
    }
  },
}
</script>

<style></style>