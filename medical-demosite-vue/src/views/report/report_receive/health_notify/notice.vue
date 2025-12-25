<!-- 健康报告领取通知-电话通知 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :inline="true" hide-required-asterisk>
        <el-form-item label="备注:" prop="notifyMemo">
          <el-input v-model="form.notifyMemo" placeholder="请输入" clearable style="width: 640px" type="textarea"
            :rows="4" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleNotice(3)" v-if="type == 1">通 知</el-button>
        <el-button type="primary" @click="handleNotice(1)" v-if="type == 2">确 定</el-button>
        <el-button type="primary" @click="handleNotice(2)" v-if="type == 3">确 定</el-button>
        <el-button type="primary" plain @click="handleNotice(5)" v-if="type == 1">再通知</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getNotice } from "@/api/report/report_receive/health_notify.js";
export default {
  data() {
    return {
      // 通知类型
      type: undefined,
      // 选中的id
      id: undefined,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
    };
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() { },
  methods: {
    // 电话通知
    handleqNotice(type, id) {
      this.type = type
      this.id = id.join(',')
      this.reset();
      this.open = true;
      if (this.type == 1) {
        this.title = "电话通知";
      } else if (this.type == 2) {
        this.title = "号码错误";
      } else {
        this.title = "未接通";
      }
    },
    // 表单重置
    reset() {
      this.form = {
        ids: this.id,
        type: undefined,
        notifyMemo: '无',
      }
      this.resetForm("form");
    },
    // 执行通知
    handleNotice(type) {
      this.form.type = type
      if (!this.form.notifyMemo) {
        this.form.notifyMemo = '无'
      }
      let confirmText = ''
      if (type == 3) {
        confirmText = '确定执行电话通知吗？'
      }else if(type == 1){
        confirmText = '确定执行号码错误吗？'
      }else if(type == 2){
        confirmText = '确定执行未接通吗？'
      }else if(type == 5){
        confirmText = '确定执行电话再通知吗？'
      }
      this.$confirm(confirmText, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        getNotice(this.form).then(({ data }) => {
       
          this.$modal.msgSuccess('操作成功')
          this.cancel()
          this.$emit('handleQuery')
        });
      }).catch(() => {})

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            // updatePrinttype(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            // 	this.getList();
            // });
          } else {
            // addPrinttype(this.form).then(response => {
            this.$modal.msgSuccess("交接成功");
            this.open = false;
            // 	this.getList();
            // });
          }
        }
      });
    },
  },
};
</script>
<style lang="scss">
</style>
