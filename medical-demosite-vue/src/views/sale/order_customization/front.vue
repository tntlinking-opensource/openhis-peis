<!-- 前台变更须知  开发人:麦沃德科技 矢北/予安  -->
<template>
  <el-dialog
    title="变更前台须知"
    :visible.sync="open"
    width="388px"
    append-to-body
    class="add-container alter-note"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :inline="true" hide-required-asterisk v-loading="loading">
      <el-form-item label="前台变更须知" prop="qtxz">
        <el-input
          type="textarea"
          style="width: 340px"
          rows="6"
          placeholder="请输入前台须知"
          v-model="form.qtxz"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="frontDiaSubmitForm">保存</el-button>
      <el-button type="primary" plain @click="frontCancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { testQtxz, saveQtxz } from "@/api/sale/order_customization.js"
export default {
  data() {
    return {
      open: false,
      // 订单id
      id: undefined,
      loading: false,
      // 修改前端须知窗口表单
      form: { qtxz: "" }
    }
  },
  methods: {
    showDialog(id) {
      this.id = id
      this.open = true
      this.loading = true
      this.form.qtxz = ""
      testQtxz({ id })
        .then(({ data }) => {
          if (data) {
            this.form.qtxz = data
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
          this.open = false
        })
    },

    // 前台修改须知表单提交方法
    frontDiaSubmitForm() {
      if (!this.form.qtxz) {
        this.$modal.msgWarning("请输入前台须知")
        return
      }
      this.loading = true
      saveQtxz({
        orderId: this.id,
        qtxz: this.form.qtxz
      })
        .then(() => {
          this.loading = false
          this.$modal.msgSuccess("变更前台须知成功")
          this.frontCancel()
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 前段修改须知表单取消提交
    frontCancel() {
      this.open = false
    }
  }
}
</script>