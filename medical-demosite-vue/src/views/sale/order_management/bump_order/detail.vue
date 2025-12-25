<!-- 撞单排查详情  开发人：麦沃德科技矢北 -->
<template>
  <el-dialog title="撞单处理" width="860px" :visible.sync="open">
    <el-form :model="form" inline :rules="rules" label-width="100px">
   
      <el-form-item  label="是否撞单" prop="dealResult">
        <el-checkbox style="width: 230px;" true-label="1" false-label="0" v-model="form.dealResult"></el-checkbox>
      </el-form-item>
      <el-form-item  label="处理意见" prop="remark">
        <el-input type="textarea" rows="3" resize="none" style="width: 630px;" v-model="form.remark"  placeholder="">
        </el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
import {  } from "@/api/sale/create_offer";
import {  addItem } from "@/api/sale/order_management/order_management.js"
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 表单
      form: {dealResult:0},
    
      editData:{

      },
      rules:{
      
      }
    };
  },
  computed: {},
  watch: {},
  created() { },
  mounted() { },
  methods: {
    // 显示
    handleShow(row) {
     
      this.reset();
      this.open = true;
      this.form.ddId=row.ddId
      this.form.id=row.id
     
    },
    // 表单重置
    reset() {
      this.form = {
        pinganId: undefined,
      }
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      if(this.form.dealResult==undefined){
          this.form.dealResult=0
       
    }
    
      addItem(this.form).then(() => {
       
        this.$modal.msgSuccess("操作成功！");
        this.open = false;
        this.$emit("getList")
    
      
      });
    },
  },
};
</script>
<style scoped>
.el-form-item {
  display: flex;
}
</style>