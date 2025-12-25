<!-- 服务过期提醒弹窗 麦沃德科技 开发人 予安 -->
<template>
  <el-dialog title="温馨提示" custom-class="expiration-dialog" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
    <!-- 提示 -->
    <div class="expiration-main">{{ expirationInfo.expiredTip }}</div>
    <el-button class="expiration-btn zk-btn-style1" @click="open = false" v-if="expirationInfo.canUse">我知道了</el-button>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      open: false,
      // 过期信息
      expirationInfo: {},
    }
  },
  methods: {
    showDialog(expirationInfo) {
      this.open = true
      this.expirationInfo = expirationInfo
    },
  },
}
</script>

<style lang="scss">
.expiration-dialog {
  .el-dialog__body {
    padding-top: 6px;
  }
  /* font-size: 30px; */
  .expiration-main {
    margin-bottom: 12px;
    color: #182233;
    font-size: 16px;
    line-height: 30px; /* 187.5% */
  }
  .expiration-btn {
    float: right;
    padding: 10px 20px !important;
    font-size: 16px;
    font-weight: 500;
    border-radius: 4px;
  }
}
</style>
