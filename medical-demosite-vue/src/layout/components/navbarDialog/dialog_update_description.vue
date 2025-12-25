<template>
  <!-- 已经是更新对话框 -->
  <el-dialog :visible.sync="dialogUpdate" width="520px" :show-close="false" center class="check-update-dialog2" @close="handleSave">
    <div slot="title" class="check-update-title">
      <div class="check-update-tips">新版本提示</div>
      <div class="check-update-version">
        当前版本：<span>正式版 {{ versionInfo.version ? versionInfo.version.versionNumber : '' }}</span>
      </div>
    </div>
    <div class="check-update-content-box">
      <p v-for="(item, index) in versionInfo.version.versionItemList" :key="index">{{ index + 1 + '.' + item.content }}</p>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogUpdate = false">我知道了</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { saveVersionNotify } from '@/api/system/version_control/version_info'
export default {
  props: ['versionInfo'],
  data() {
    return {
      // 是否打开检查更新弹窗 
      dialogUpdate: false,
    }
  },
  methods: {
    showDialog() {
      this.dialogUpdate = true
    },
    // 忽略此次更新
    ignoreUpdate() {
      // 跳过当前更新逻辑
      this.dialogUpdate = false
    },
    // 执行更新
    executeUpdate() {
      this.$alert('请联系信息科进行更新', '提示')
    },
    // 关闭窗口时记录用户新版已读记录
    handleSave() {
      saveVersionNotify()
    },
  },
}
</script>

<style scoped>
.check-update-dialog2 /deep/ .el-dialog {
  /* overflow: hidden; */
  border-radius: 8px;
}
.check-update-dialog2 /deep/ .el-dialog__header {
  padding: 16px;
  border-radius: 8px 8px 0 0;
  background: linear-gradient(180deg, #fe6939 0%, #ff9e68 100%);
}
.check-update-dialog2 /deep/ .el-dialog__body {
  padding: 16px;
}
.check-update-dialog2 .check-update-title {
  background-image: url('../../../assets/icon/topNav/update_bg.png');
  background-size: 100% 100%;
}
.check-update-dialog2 .check-update-tips {
  position: absolute;
  z-index: 2;
  width: calc(100% - 32px);
  font-size: 20px;
  font-weight: 600;
  color: #fff;
  text-align: center;
  transform: translateY(-60px);
}
.check-update-dialog2 .check-update-title {
  font-size: 16px;
  color: #fff;
}
.check-update-dialog2 .check-update-title span {
  color: #000;
}
.check-update-dialog2 .check-update-content-box {
  overflow: hidden;
  overflow-y: auto;
  height: 380px;
  padding: 0 16px;
  background-color: #f9fbff;
  border-radius: 8px;
  border-top: 10px solid #fe7040;
}
</style>
