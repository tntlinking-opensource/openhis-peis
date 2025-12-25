<!-- 会员卡补办 开发人：麦沃德矢北 -->
<template>
  <div class="add-container">
    <el-dialog title="会员卡挂失" :visible.sync="open" width="860px" append-to-body :close-on-click-modal="false">
      <el-form label-width="80px" :rules="rule" ref="form" :model="form" :inline="true" hide-required-asterisk>
        <el-form-item label="姓名" prop="patientname" style="margin-right:32px;">
          <el-input placeholder="请输入姓名" style="width:260px;" v-model="form.patientname"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="idSex" style="margin-right:32px;">
          <el-select v-model="form.idSex" style="width:260px;" placeholder="">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="挂失卡号" prop="beginCard" style="margin-right:32px;">
          <el-input placeholder="" style="width:260px;" v-model="form.beginCard" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idcardno" style="margin-right:32px;">
          <el-input placeholder="" style="width:260px;" v-model="form.idcardno"></el-input>
        </el-form-item>
        <el-form-item label="会员级别" prop="memberlevel" style="margin-right:32px;">
          <el-select style="width:260px;" v-model="form.memberlevel" placeholder="">
            <el-option label="普通会员" value="1"></el-option>
            <el-option label="VIP会员" value="2"></el-option>
            <el-option label="VVIP会员" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="剩余积分" prop="balanceJf" style="margin-right:32px;">
          <el-input placeholder="" style="width:260px;" v-model="form.balanceJf"></el-input>
        </el-form-item>
        <el-form-item label="补办卡号" prop="patientcardno" style="margin-right:32px;">
          <el-input placeholder="" style="width:260px;" v-model="form.patientcardno"></el-input>
        </el-form-item>
        <el-form-item label="充值积分" prop="limit" style="margin-right:32px;">
          <el-input-number @change="consoleform" v-model="form.limit" style="width:260px ;" controls-position="right"
            :step=100 :min="1" :max="1000000000"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="note" style="margin-right:32px;">
          <el-input type="textarea" resize="none" rows="3" placeholder="请输入备注" style="width:632px;"
            v-model="form.note"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { saveMend ,loseData} from '../../../../api/custservice/membership_management/center_member';
export default {
  data() {
    return {
      open: false,
      form: {
      },
      queryParams: {
      },
      memberlevel: [
        { id: 1, text: "普通会员" },
        { id: 2, text: "VIP会员" },
        { id: 3, text: "VVIP会员" }
      ],
      rule: {
        patientcardno: [{ required: true, message: "补办卡号不能为空不能为空", trigger: "change" }],
      }
    }
  },
  methods: {
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          saveMend(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    //打开页面
    openMend(card) {
      
        const cards={
          id:card.id
        }
      loseData(cards).then(response=>{
        
        this.form = response.data.peisPatientArchive
        this.form.beginCard=card.patientcardno
      
      })
      
      this.open = true
    },
    // 重置数据
    reset() {
      this.form = {
      }
      this.resetForm("form");

    },
    cancel() {
      this.open = false;
      this.reset();
    },
    consoleform() {
     
    }

  }
}
</script>

<style></style>