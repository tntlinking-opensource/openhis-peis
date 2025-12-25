<!-- 总结 开发人：麦沃德科技矢北/予安 -->
<template>
  <div class="add-container">
    <!-- 总结对话框 -->
    <el-dialog title="总结" :visible.sync="open" width="580px" append-to-body destroy-on-close>
      <el-form
        ref="form"
        :model="form"
        :inline="true"
        label-width="120px"
        hide-required-asterisk
        v-loading="loading"
        :rules="rules"
      >
        <el-form-item label="订单名称" prop="ddmc">
          <el-input v-model="form.ddmc" placeholder="请输入" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="客户单位名称" prop="khdwmc">
          <el-input v-model="form.khdwmc" placeholder="请输入" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="销售经理" prop="xsjl">
          <el-input v-model="form.xsjl" placeholder="请输入" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="总结" prop="zj">
          <el-input
            v-model="form.zj"
            placeholder="请输入"
            clearable
            style="width: 360px"
            type="textarea"
            :rows="5"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addWithOrder, saOrUpSummary } from "@/api/sale/order_customization.js"
export default {
  data() {
    return {
      loading: false,
      open: false,
      form: {},
      rules: {
        ddmc: [{ required: true, message: "订单名称不能为空", trigger: "blur" }],
        khdwmc: [{ required: true, message: "客户单位名称不能为空", trigger: "blur" }],
        xsjl: [{ required: true, message: "销售经理不能为空", trigger: "blur" }],
        zj: [{ required: true, message: "总结不能为空", trigger: "blur" }]
      }
    }
  },
  methods: {
    //显示
    handleShow(id) {
      this.open = true
      this.loading = true
      addWithOrder({ id })
        .then(({ data }) => {
          this.form = data
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 取消按钮
    cancel() {
      this.form = {
        ddmc: undefined,
        khdwmc: undefined,
        xsjl: undefined,
        zj: undefined
      }
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          this.form.id = this.form.id == null ? undefined : this.form.id
          saOrUpSummary(this.form)
            .then(() => {
              this.$modal.msgSuccess("保存总结成功")
              this.cancel()
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        }
      })
    }
  }
}
</script>

<style>
</style>
