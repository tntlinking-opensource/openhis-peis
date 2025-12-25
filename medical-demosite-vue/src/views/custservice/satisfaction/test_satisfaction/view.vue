<template>
  <el-dialog title="满意度编辑" :visible.sync="open" class="add-items" width="1000px" :close-on-click-modal="false"
    append-to-body>
    <el-form v-loading="popLoading" ref="form" :model="form" :inline="true" label-width="220px" hide-required-asterisk>
      <el-form-item prop="appraiseResult" label="预约流程便捷性">
        <el-select  style="width:208px" placeholder="" v-model="form.question1">
          <el-option label="1分" value="1"></el-option>
          <el-option label="2分" value="2"></el-option>
          <el-option label="3分" value="3"></el-option>
          <el-option label="4分" value="4"></el-option>
          <el-option label="5分" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="体检环境整洁舒适度">
        <el-select  style="width:208px" placeholder="" v-model="form.question2">
          <el-option label="1分" value="1"></el-option>
          <el-option label="2分" value="2"></el-option>
          <el-option label="3分" value="3"></el-option>
          <el-option label="4分" value="4"></el-option>
          <el-option label="5分" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="医护人员服务态度">
        <el-select  style="width:208px" placeholder="" v-model="form.question3">
          <el-option label="1分" value="1"></el-option>
          <el-option label="2分" value="2"></el-option>
          <el-option label="3分" value="3"></el-option>
          <el-option label="4分" value="4"></el-option>
          <el-option label="5分" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="检查项目指引清晰度">
        <el-select  style="width:208px" placeholder="" v-model="form.question4">
          <el-option label="1分" value="1"></el-option>
          <el-option label="2分" value="2"></el-option>
          <el-option label="3分" value="3"></el-option>
          <el-option label="4分" value="4"></el-option>
          <el-option label="5分" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="等待时间合理性">
        <el-select  style="width:208px" placeholder="" v-model="form.question5">
          <el-option label="1分" value="1"></el-option>
          <el-option label="2分" value="2"></el-option>
          <el-option label="3分" value="3"></el-option>
          <el-option label="4分" value="4"></el-option>
          <el-option label="5分" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="appraiseResult" label="您对本次体检服务的整体满意度">
        <el-select  style="width:208px" placeholder="" v-model="form.question10">
          <el-option label="非常满意" value="1"></el-option>
          <el-option label="满意" value="2"></el-option>
          <el-option label="一般" value="3"></el-option>
          <el-option label="不满意" value="4"></el-option>
          <el-option label="非常不满意" value="5"></el-option>
        </el-select>
      </el-form-item>
       <el-form-item prop="appraiseResult" label="您是否会推荐他人选择本中心">
        <el-select  style="width:208px" placeholder="" v-model="form.question11">
          <el-option label="一定会" value="1"></el-option>
          <el-option label="可能会" value="2"></el-option>
          <el-option label="不确定" value="3"></el-option>
          <el-option label="不会" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="note" label="您对本次体检服务最满意的方面">
        <el-input  type="textarea" v-model="form.question7" rows="3" resize="none"
          style="width:728px"></el-input>
      </el-form-item>
         <el-form-item prop="note" label="您认为需要改进的地方">
        <el-input  type="textarea" v-model="form.question8" rows="3" resize="none"
          style="width:728px"></el-input>
      </el-form-item>
          <el-form-item prop="note" label="其它建议或意见">
        <el-input  type="textarea" v-model="form.question9" rows="3" resize="none"
          style="width:728px"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getDetailData, saveOrUpdate } from "@/api/custservice/satisfaction/test_satisfaction.js";
export default {
    data()
    {
      return{
        id:undefined,
        form:{

        },
        open:false,
        popLoading:false,
        readonly:true,
      }
    },
    methods:{
      submitForm(){
   
      this.form.id = this.id

   
     saveOrUpdate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('getList');

            });
            
          
        
      },
      reset(){
        this.form = {
        depName: undefined,
        name: undefined,
        isLong: undefined,
        isPublic: undefined,
        isfzx: undefined,
        suggestion: undefined,
        depiction: undefined,
        advice: undefined,
        dietguide: undefined,
        sportguide: undefined,
        knowledge: undefined,
        note: undefined,
        divisionId: undefined,
        center: undefined,
        data1: undefined,
        data2: undefined,
        visitTime: undefined,
        patientcode:undefined
      }
      this.resetForm("form");
      // this.selectData1.bindValue = undefined
      },
      cancel()
      {
        this.open = false
      },
      getNewList(query){
        this.id = query
      this.open = true;
      this.reset();


      // listTestSatisfaction(query).then(response => {
      //   //判断是否存在此对象主要赋值对象
      //   if(response.data.Satisfaction)
      //   {
      //     this.form = response.data.Satisfaction
      //     this.form.doctorapply=response.data.Satisfaction.doctorapply
      //   this.form.patientname=response.data.Satisfaction.patientname
      //   this.form.doctorapply=response.data.Satisfaction.doctorapply
      //   this.form.doctorapply=response.data.Satisfaction.doctorapply
      //   this.form.id =response.data.Satisfaction.id
      //   }
      //   this.form=response.data
      
        
      //   this.popLoading = false
      // });
      }
    }
}
</script>

<style>

</style>