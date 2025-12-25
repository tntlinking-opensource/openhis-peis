<!-- 订单定制-修改发放方式 开发人：麦沃德科技 矢北/予安 -->
<template>
  <el-dialog
    title="修改发放方式"
    :visible.sync="reviseOpen"
    width="388px"
    append-to-body
    class="order-customization"
    :close-on-click-modal="false"
  >
    <el-form
      ref="reviseDiaForm"
      :model="reviseDiaForm"
      label-width="70px"
      hide-required-asterisk
      v-loading="loading"
    >
      <el-form-item label="发放方式">
        <el-select v-model="reviseDiaForm.type" placeholder="请选择">
          <el-option
            style="width: 230px"
            v-for="options in reviseState"
            :key="options.id"
            :label="options.text"
            :value="options.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="reviseDiaSubmitForm">保存</el-button>
      <el-button type="primary" plain @click="reviseCancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getOrderNotifycation, saveInfo } from "@/api/sale/order_customization.js"
export default {
  data() {
    return {
      // 修改发放方式
      reviseOpen: false,
      loading: false,
      // 订单号
      ids: "",
      reviseDiaForm: {
        type: undefined
      },
      // 发放方式列表
      reviseState: []
    }
  },
  methods: {
    //打开按钮
    showDialog(ids) {
      this.ids = ids
      this.diaReset()
      this.reviseOpen = true
      this.loading = true
      getOrderNotifycation().then(({ data }) => {
        this.reviseState = data
        this.loading = false
      })
    },
    // 取消
    reviseCancel() {
      this.reviseOpen = false
      this.diaReset()
    },
    //修改方式弹窗提交
    reviseDiaSubmitForm() {
      if (!this.reviseDiaForm.type) {
        this.$modal.msgWarning("请选择发放方式")
        return
      }
      this.loading = true
      saveInfo({
        idInformway: this.reviseDiaForm.type,
        ids: this.ids
      })
        .then(() => {
          this.loading = false
          this.$modal.msgSuccess("修改成功")
          this.reviseCancel()
          this.$emit("getList")
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 重置
    diaReset() {
      this.reviseDiaForm = {
        type: undefined
      }
      this.resetForm("reviseDiaForm")
    }
  }
}
</script>

<style scope>
.order-customization .el-dialog .el-dialog__body {
  overflow: hidden;
}
</style>