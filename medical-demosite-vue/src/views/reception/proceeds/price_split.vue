<!-- 拆分弹窗 -->
<template>
  <el-dialog title="拆分金额" :visible.sync="openSplit" width="440px" append-to-body>
    <el-form ref="queryForm" size="small" :inline="true" @submit.native.prevent style="padding: 0 20px" label-position="top">
      <el-form-item label="请输入拆分金额">
        <el-input-number ref="money" v-model="splitMoney" placeholder="请输入拆分金额" :precision="2" controls-position="right" style="width: 350px" @keyup.enter.native="submit"></el-input-number>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 拆分弹窗
      openSplit: false,
      // 拆分金额
      splitMoney: undefined,
    }
  },
  methods: {
    showDialog() {
      this.openSplit = true
      this.$nextTick(() => {
        this.$refs.money.focus()
      })
    },
    // 确认拆分金额
    submit() {
      if (!this.splitMoney) {
        this.$alert('请输入拆分金额', '提示')
        return
      }
      this.$emit('confirmSplit', this.splitMoney)
      this.cancel()
    },
    // 取消拆分
    cancel() {
      this.splitMoney = undefined
      this.openSplit = false
    },
  },
}
</script>

<style></style>
