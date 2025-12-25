<template>
  <el-dialog :title="title" :visible.sync="open" class="add-items"   style="min-width:860px"
    :close-on-click-modal="false" append-to-body>
    <el-form v-loading="popLoading" ref="form" :model="form" :inline="true" label-width="102px" hide-required-asterisk>
      <el-form-item prop="typeName" label="类型名称">
        <el-input style="width: 330px" @input="pinyin"  v-model="form.typeName" placeholder="请类型名称"></el-input>
      </el-form-item>
      <el-form-item prop="inputCode" label="输入码">
        <el-input style="width: 330px" :disabled="true" v-model="form.inputCode" placeholder="请输入输入码"></el-input>
      </el-form-item>
      <el-form-item prop="grade" label="层级">
        <el-input style="width: 330px" v-model="form.grade" oninput="value=value.replace(/[^0-9]/g,'')"  placeholder="请类型名称"></el-input>
      </el-form-item>
      <el-form-item prop="status" label="状态">
        <el-select style="width: 330px" v-model="form.status" placeholder="请选择">
          <el-option  v-for="options in typeOptions" :key="options.id" :label="options.text"
            :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item prop="tip" label="介绍">
        <el-input style="width: 762px" resize="none" v-model="form.tip" rows="3" type="textarea" placeholder=""></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" @click="reset" plain>重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import pinyin from "@/utils/pinyin.js";
import {  detailData,saveData,updateData } from "@/api/basis/business_type";
export default {
  data() {
    return {
      form: {
        name: undefined
      },
      key:undefined,
      open: false,
      title: '编辑',
      rules: {
        // name: [{ required: true, message: "结论词不能为空", trigger: "change" }],
        // depName: [{ required: true, message: "科室名不能为空", trigger: "change" }],
      },
      typeOptions: [
        { id: -1, text: '删除' },
        { id: 0, text: '下线' },
        { id: 1, text: '正常' },
      ],
      popLoading: undefined,
    }
  },
  methods: {
    addItem(){
      this.popLoading = true
      this.open = true;
      this.title = "新增";
      this.reset()
      this.popLoading = false
    },
    // 编辑
    editList(row) {
      this.key=row
      this.popLoading = true
      this.open = true;
      this.popLoading = false
      this.title = "编辑";
      detailData(row).then(response => {
        this.form = response.data; 
   
      });
    },
    // 重置数据
    reset() {
      this.form = {
        depName: undefined,
      }
      this.resetForm("form");
      if(this.title=='编辑'){
        detailData(this.key).then(response => {
        this.form = response.data; 
   
      });
      }

    },
    pinyin(value){
      this.form.inputCode = pinyin(value)
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.typeId != null) {
            saveData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('getList')
            });
          } else {
            updateData(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.open = false;
              this.$emit('getList')
            });
          }
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
  }
}
</script>

<style></style>