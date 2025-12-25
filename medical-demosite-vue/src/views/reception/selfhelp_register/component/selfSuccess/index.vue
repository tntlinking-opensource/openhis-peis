<template>
  <div class="body-main">
    <i class="success-icon el-icon-success" :style="{ color: theme }"> </i>
    <div class="body-title" :style="typeName == '调整' ? 'margin-bottom:8px' : ''">{{ typeName }}成功</div>
    <div class="body-title" v-if="typeName == '调整'">当前科室 <span style="color: #1890ff">心电图</span> ，前面有 <span style="color: #1890ff">6人</span> 在等待</div>
    <button class="back-btn" :style="{ backgroundColor: theme }" @click="backIndex">返回首页</button>
  </div>
</template>

<script>
export default {
  props: ['theme', 'typeName'],
  data() {
    return {}
  },
  methods: {
    // 返回首页
    backIndex() {
      this.$router.push({ name: 'selfHelp' })
    },
  },
}
</script>

<style lang="scss" scoped>
.body-main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 790px;
  padding-top: 20px;
  .success-icon {
    width: 280px;
    height: 280px;
    margin: 100px 0 17px;
    font-size: 280px;
  }
  .body-title {
    margin-bottom: 174px;
    text-align: center;
    font-size: 48px;
  }

  .back-btn {
    padding: 17px 406px;
    font-size: 45px;
    color: #fff;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    white-space: nowrap;
  }
}
@media screen and (max-width: 1950px) {
  .body-main {
    height: auto;
    .success-icon {
      width: 200px;
      height: 200px;
      margin: 100px 0 17px;
      font-size: 200px;
    }
    .body-title {
      margin-bottom: 114px;
      font-size: 40px;
    }

    .back-btn {
      font-size: 32px;
    }
  }
}
@media screen and (max-width: 1200px) {
  .body-main {
    .back-btn {
      padding: 12px 290px;
      font-size: 26px;
    }
  }
}
</style>
