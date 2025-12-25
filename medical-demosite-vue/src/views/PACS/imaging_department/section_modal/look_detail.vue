<!-- pacs科室-描述 麦沃德科技 开发人 暴雨 -->
<template>
  <el-dialog title="描述内容编辑" :visible.sync="open" width="1600px" append-to-body class="openDialog" :close-on-click-modal="false">
    <!-- 描述 -->
    <div style="font-size: 26px">描述</div>
    <textarea v-model="description" style="width: 100%; height: 300px; font-size: 24px"></textarea>
    <!-- 小结 -->
    <div style="font-size: 26px">小结</div>
    <textarea v-model="conclusions" style="width: 100%; height: 300px; font-size: 24px"></textarea>
    <div style="text-align: center">
      <el-button type="primary" style="width: 20%; margin-top: 1%" @click="handleUpdate">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      open: false,
      // 描述
      description: '',
      // 小结
      conclusions: '',
    }
  },
  methods: {
    toDetail(description, conclusions) {
      this.description = description
      this.conclusions = conclusions
      this.open = true
    },
    handleUpdate() {
      this.open = false
      // 当数据修改完成后，通过自定义事件将修改后的值传递回原页面
      this.$emit('update-data', this.description, this.conclusions)
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
