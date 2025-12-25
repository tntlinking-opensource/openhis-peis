<!-- 科室-小结 麦沃德科技 开发人 暴雨 -->
<template>
  <el-dialog title="小结" :visible.sync="open" width="1600px" append-to-body class="openDialog" :close-on-click-modal="false">
    <!-- 小结 -->
    <textarea v-model="conclusions" style="width: 100%; height: 300px; font-size: 24px" :readonly="!canChange"></textarea>
    <div style="text-align: center">
      <el-button class="section-btn-style1" style="width: 20%; margin-top: 1%" @click="handleUpdate">确认</el-button>
      <el-button class="section-btn-style2" style="width: 20%; margin-top: 1%" @click="open = false">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      open: false,
      // 小结
      conclusions: '',
      // 是否可以修改
      canChange: true,
    }
  },
  methods: {
    toDetail(conclusions, canChange) {
      this.conclusions = ''
      this.conclusions = conclusions
      this.canChange = true
      if (canChange == 1) {
        this.canChange = false
      }
      this.open = true
    },
    handleUpdate() {
      this.open = false
      // 当数据修改完成后，通过自定义事件将修改后的值传递回原页面
      this.$emit('updateConclusions', this.conclusions)
    },
  },
}
</script>

<style scoped>
.openDialog /deep/ .el-dialog__body {
  display: flex;
  flex-direction: column;
}
</style>
