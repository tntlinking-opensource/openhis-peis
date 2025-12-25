<template>
  
    <el-dialog title="查看" class="add-items" :visible.sync="open" width="860px" append-to-body
      :close-on-click-modal="false">
      <el-form ref="addForm" :inline="true" label-width="130px" :model="form">
        <el-form-item label="客户单位名称" style="margin-right:32px;" prop="khdwmc">
          <el-input placeholder="" style="width:260px;" v-model="form.khdwmc" :readonly="isRead"></el-input>
        </el-form-item>
        <el-form-item label="销售经理" style="margin-right:0;" prop="xsjl">
          <el-input placeholder="" style="width:260px;" v-model="form.xsjl" :readonly="isRead"></el-input>
        </el-form-item>
        <el-form-item label="客户负责人" style="margin-right:32px;" prop="khfzr">
          <el-input placeholder="" style="width:260px;" v-model="form.khfzr" :readonly="isRead"></el-input>
        </el-form-item>
        <el-form-item label="跟进阶段" style="margin-right:0;" prop="sshy">
          <el-select  :disabled="true" style="width:260px;" v-model="form.gjjd" placeholder="">
            <el-option label="需求交流" :value="0"></el-option>
            <el-option label="方案谈判" :value="1"></el-option>
            <el-option label="价格谈判" :value="2"></el-option>
            <el-option label="确认体检" :value="3"></el-option>
            <el-option label="合同签订" :value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期" style="margin-right:32px;" prop="ksrq">
          <el-date-picker readonly v-model="form.createdate" style="width:260px;" type="date" placeholder="">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" style="margin-right:0;" prop="jsrq">
          <el-date-picker readonly v-model="form.jsrq" style="width:260px;" type="date" placeholder="">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="主题" prop="zt">
          <el-input placeholder="" style="width:674px;" v-model="form.zt" :readonly="isRead"></el-input>
        </el-form-item>
        <el-form-item label="跟进内容" prop="gjnr">
          <el-input placeholder="" type="textarea" resize="none" style="width:674px;" v-model="form.gjnr" rows="3"
            :readonly="isRead"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCancel()">关闭</el-button>
      </div>
    </el-dialog>

</template>

<script>

import { getData } from '@/api/customer/phase_tracking'
export default {
  data() {
    return {
      form: {
        khdwmc: undefined,
        xsjl: undefined,
        khfzr: undefined,
        gjjd: undefined,
        ksrq: undefined,
        jsrq: undefined,
        zt: undefined,
        gjnr: undefined,
      },
      open: false,
      isRead: true
      
    }
  },
  methods: {
    onCancel() {
      this.open = false
    },
    openDia(id) {
  
      this.open = true
      this.getData(id)
    },
    getData(id) {
      getData(id).then(response=>{ 
         this.form=response.data
       })
    }
  },

}



</script>
<style></style>