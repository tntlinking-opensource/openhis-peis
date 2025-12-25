<!-- 个检预检回访  麦沃德科技 开发人：暴雨-->
<template>
  <div class="add-container">
    <el-dialog :title="title" :visible.sync="open" width="760px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" label-width="100px" :inline="true" hide-required-asterisk>
        <el-form-item label="姓名" >
          <el-input  style="width:240px;" v-model="form.patientname" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
          <el-select v-model="form.idSex"  clearable style="width: 240px;" disabled>
            <el-option label="男" :value="0" />
            <el-option label="女" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" >
          <el-input  style="width:240px;" v-model="form.age" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="公司" >
          <el-input  style="width:240px;" v-model="form.dw" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="电话" >
          <el-input  style="width:240px;" v-model="form.phone" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="费用" >
          <el-input  style="width:240px;" v-model="form.moneyamount" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="收费方式" >
          <el-input  style="width:240px;" v-model="form.payways" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="备注" >
          <el-input  style="width:240px;" v-model="form.note" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="开单医生">
          <el-input  style="width:240px;" v-model="form.doctorapply" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="套餐名称" >
          <el-input  style="width:240px;" v-model="form.examsuiteName" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="上次体检时间" >
          <el-date-picker v-model="form.dateregister" value-format="yyyy-MM-dd HH:mm:ss"  :readonly="true"
                          type="datetime" placeholder="选择日期" clearable style="width: 240px"></el-date-picker>
        </el-form-item>
        <el-form-item label="来检次数" >
          <el-input  style="width:240px;" v-model="form.count" placeholder="暂无数据" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="上次体检项目" >
          <el-input type="textarea" style="width:240px;" placeholder="暂无数据" v-model="form.examfeeitemName"  :readonly="true"  :rows="4"></el-input>
        </el-form-item>
        <el-form-item label="上次体检结果" >
          <el-input type="textarea" style="width:240px;" placeholder="暂无数据" v-model="form.signList" :readonly="true" :rows="4"></el-input>
        </el-form-item>
        <el-form-item label="回访时间" >
          <el-date-picker v-model="form.visitTime" value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime" placeholder="选择日期" clearable style="width: 240px"></el-date-picker>
        </el-form-item>
        <el-form-item label="来检时间" >
          <el-date-picker v-model="form.inspectTime" value-format="yyyy-MM-dd HH:mm:ss"  :readonly="true"
                          type="datetime" placeholder="暂无数据" clearable style="width: 240px"></el-date-picker>
        </el-form-item>
        <el-form-item label="是否来检" >
          <el-select v-model="form.isInspect" placeholder="请选择" clearable style="width: 240px;" readonly="true">
            <el-option label="是" :value="0" />
            <el-option label="否" :value="1" />
            <el-option label="再通知" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="回访备注" >
          <el-input type="textarea" style="width:600px;" placeholder="请输入回访备注" v-model="form.memo" :rows="4"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button type="primary" plain @click="onResets">重置</el-button>
        <el-button @click="onCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { previewing , saveOrUpdate } from '@/api/custservice/customerservice/previewing_track/previewing_track'
  export default{
    data(){
      return{
        title:"",
        open:false,
        form:{
          age:"",
          count:"",
          dateregister:"",
          doctorapply:"",
          dw:"",
          examfeeitemName:"",
          examsuiteName:"",
          id:"",
          idSex:"",
          inspectTime:"",
          isInspect:"",
          memo:"",
          moneyamount:"",
          note:"",
          patientname:"",
          payways:"",
          phone:"",
          signList:"",
          visitTime:"",
        },
      }
    },
    methods:{
      //个检客户预检跟踪回访
      handleAdd(id,rows){
        previewing(id).then(response => {
          this.form.patientname = response.data.peispatient.patientname;
          this.form.idSex=response.data.peispatient.idSex
          this.form.age=response.data.peispatient.age
          this.form.dw=response.data.peispatient.orgName
          this.form.phone=response.data.peispatient.phone
          this.form.doctorapply=response.data.peispatient.doctorapply
          this.form.examsuiteName=response.data.peispatient.examsuiteName
          this.form.dateregister=response.data.peispatient.dateregister
          response.data.advanceVisitWrite=response.data.advanceVisitWrite?response.data.advanceVisitWrite:{
            visitTime:"",
            inspectTime:"",
            isInspect:"",
            memo:"",
            note:"",
          }
          this.form.visitTime=response.data.advanceVisitWrite.visitTime //待确认
          this.form.inspectTime=response.data.advanceVisitWrite.inspectTime //待确认
          this.form.isInspect=response.data.advanceVisitWrite.isInspect //待确认
          this.form.memo=response.data.advanceVisitWrite.memo //待确认
          this.form.note=response.data.peispatientChargeMain.note
          this.form.moneyamount=response.data.peispatientChargeMain.moneyamount
          this.form.count=rows[0].count
          this.form.id=rows[0].id
          this.form.signList=response.data.signList
          this.form.examfeeitemName=response.data.examfeeitemName
          this.form.payways=response.data.payways
          this.form.age=response.data.age
        });
        this.title="个检客户预检跟踪回访"
        this.open=true;
      },
      // 提交按钮
      submitForm() {
        saveOrUpdate(this.form).then(() => {
          this.$modal.msgSuccess("回访成功!");
          this.open = false;
          this.$emit('getList')
        });
      },
      onReset(){
          this.form.age=undefined,
          this.form.count=undefined,
          this.form.dateregister=undefined,
          this.form.doctorapply=undefined,
          this.form.dw=undefined,
          this.form.examfeeitemName=undefined,
          this.form.examsuiteName=undefined,
          this.form.id=undefined,
          this.form.idSex=undefined,
          this.form.inspectTime=undefined,
          this.form.isInspect=undefined,
          this.form.memo=undefined,
          this.form.moneyamount=undefined,
          this.form.note=undefined,
          this.form.patientname=undefined,
          this.form.payways=undefined,
          this.form.phone=undefined,
          this.form.visitTime=undefined,
          this.form.signList=undefined
      },
      onResets(){
          this.form.visitTime=undefined,
          this.form.isInspect=undefined,
          this.form.memo=undefined
      },
      onCancel(){
        this.onReset()
        this.open=false;
      },
    }
  }
</script>

<style scoped>
  #setTextarea /deep/ .el-textarea__inner{
    height: 100px;
  }
</style>
