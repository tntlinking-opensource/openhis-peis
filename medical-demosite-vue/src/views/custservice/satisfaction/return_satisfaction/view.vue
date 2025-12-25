<template>
  <el-dialog title="不满意客户回访" :visible.sync="open" class="add-items" width="900px" :close-on-click-modal="false"
    append-to-body>
    <el-form v-loading="popLoading" ref="form" :model="form" :inline="true" label-width="102px" hide-required-asterisk>
      <el-form-item prop="patientcode" label="体检号">
        <el-input :readonly="readonly" v-model="form.patientcode" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="doctorapply" label="开单医生">
        <el-input :readonly="readonly" v-model="form.doctorapply" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="patientname" label="姓名">
        <el-input :readonly="readonly" v-model="form.patientname" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="idSex" label="性别">
        <el-select style="width:308px" :disabled="true" v-model="form.idSex" placeholder="请输入性别">
          <el-option label="男" :value="0"></el-option>
          <el-option label="女" :value="1"></el-option>
          <el-option label="通用" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="age" label="年龄">
        <el-input :readonly="readonly" v-model="form.age" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="orgName" label="公司">
        <el-input :readonly="readonly" v-model="form.orgName" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="orgDepart" label="部门">
        <el-input :readonly="readonly" v-model="form.orgDepart" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="电话">
        <el-input :readonly="readonly" v-model="form.phone" placeholder="" style="width:308px"></el-input>
      </el-form-item>
      <el-form-item prop="appraiseTime" label="评价时间">
        <el-date-picker :readonly="readonly" type="date" v-model="form.appraiseTime" style="width:308px"></el-date-picker>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="评价结果">
        <el-select :disabled="true" style="width:308px" placeholder="" v-model="form.appraiseResult">
          <el-option label="未评价" :value="0"></el-option>
          <el-option label="满意" :value="1"></el-option>
          <el-option label="未很满意评价" :value="2"></el-option>
          <el-option label="非常满意" :value="3"></el-option>
          <el-option label="满意" :value="4"></el-option>
          <el-option label="未评价" :value=null></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="note" label="评价备注">
        <el-input :readonly="readonly" type="textarea" v-model="form.note" rows="3" resize="none"
          style="width:728px"></el-input>
      </el-form-item>
      <el-form-item prop="visitTime" label="回访时间">
        <el-date-picker type="datetime" placeholder="请选择回访时间" value-format="yyyy-MM-dd hh:mm:ss" v-model="form.visitTime"
          style="width:308px"></el-date-picker>
      </el-form-item>
      <el-form-item prop="visitResult" label="回访结果">
        <el-select style="width:308px" placeholder="" v-model="form.visitResult">
          <el-option label="未评价" :value="0"></el-option>
          <el-option label="非常满意" :value="1"></el-option>
          <el-option label="满意" :value="2"></el-option>
          <el-option label="基本满意" :value="3"></el-option>
          <el-option label="不满意" :value="4"></el-option>
          <el-option label="未评价" :value=null></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="visitNote" label="回访备注">
        <el-input type="textarea" v-model="form.visitNote" placeholder="" rows="3" resize="none"
          style="width:728px"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保存</el-button>
      <el-button type="primary" @click="reset" plain>重置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { exportUnSatisfactionList, addList } from "@/api/custservice/satisfaction/return_satisfaction.js";
export default {
  data() {
    return {
      //只读
      readonly: true,
      // 弹窗开关
      open: false,
      //  遮罩层
      popLoading: false,
      form: {
        visitResult: '',
        id: '',
        visitTime: '',
        visitNote: '',
        patientcode :'',
        doctorapply :'',
        docpatientnametorapply :'',
        idSex :'',
        age :'',
        orgName :'',
        orgDepart:'',
        phone :'',
        note :'',
      },


    }
  },
  methods: {

    // 提交按钮
    submitForm() {
      this.form.personcode = this.form.patientcode

      this.$refs["form"].validate(valid => {

        if (valid) {
          if (this.form.patientcode != null) {
            addList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.$emit('getList')
              this.open = false;
            });
          }
        }
      });
    },
    reset() {

      this.resetForm("form");
      // this.selectData1.bindValue = undefined

    },
    openAdd() {
      this.open = true

    },
    cancel() {
      this.open = false
    },
    getNewList(query) {
      this.open = true;
      this.reset();
   
      exportUnSatisfactionList(query).then(response => {
        this.form.patientcode = response.data.peispatient.patientcode
        this.form.doctorapply = response.data.peispatient.doctorapply
        this.form.docpatientnametorapply = response.data.peispatient.docpatientnametorapply
        this.form.idSex = response.data.peispatient.idSex
        this.form.age = response.data.peispatient.age
        this.form.orgName = response.data.peispatient.orgName
        this.form.orgDepart = response.data.peispatient.orgDepart
        this.form.phone = response.data.peispatient.phone
        this.form.note = response.data.peispatient.note
    
        if (response.data.satisfaction) {
          this.form.id = response.data.satisfaction.id
          this.form.visitTime = response.data.satisfaction.visitTime
          this.form.visitResult = response.data.satisfaction.visitResult
          this.form.visitNote = response.data.satisfaction.visitNote
        } else {
          this.form.id = null
        }
        this.form.IdPatientarchive = response.data.peispatient.patientarchiveno
        this.popLoading = false
      });
    }
  },


}
</script>

<style></style>