<template>
  <div class="container-header">
    <!-- 左部分 -->
    <div id="img-left">
      <img :src="data" alt="" />
    </div>
    <!-- 右部 -->
    <div class="code" v-if="position">
      <div class="time">{{ nowTime }}</div>
      <img :src="data_left" alt="image description" />
    </div>
    <div v-else>
      <button class="back-btn" @click="backIndex">
        <i class="el-icon-arrow-left"></i>
        <span>返回首页</span>
      </button>
    </div>
    <div class="count-time" v-if="countTime">{{ countTime }}</div>
  </div>
</template>

<script>
export default {
  props: ['position'],
  data() {
    return {
      data: require('@/assets/images/selfHelp/logo2.png'),
      data_left: require('@/assets/images/selfHelp/QRcode.png'),
      nowTime: this.$getDate(),
      // 倒计时 60s后返回首页
      countTime: 0,
      timer: undefined,
    }
  },
  created() {
    setInterval(() => {
      this.nowTime = this.$getDate()
    }, 1000)
  },
  methods: {
    // 返回首页
    backIndex() {
      clearInterval(this.timer)
      this.$router.push({ name: 'selfHelp' })
    },
    // 开始计时
    startCountTime() {
      this.countTime = 10
      this.timer = setInterval(() => {
        this.countTime--
        if (this.countTime <= 0) {
          this.backIndex()
        }
      }, 1000)
    },
  },
}
</script>

<style lang="scss" scoped>
.container-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 20px;
  background-color: white;

  .code {
    padding: 0 10px;
    height: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .time {
      padding-right: 30px;
      font-size: 34px;
      white-space: nowrap;
    }

    img {
      width: 124px;
      height: 124px;
    }
  }

  .back-btn {
    padding: 13px;
    font-size: 23px;
    border: none;
    background-color: #fe6939 ;
    border-radius: 7px;
    color: #fff;
    cursor: pointer;

    i {
      width: 34px;
      height: 34px;
      margin-right: 7px;
      line-height: 34px;
      border-radius: 50%;
      background-color: #fff;
      color: #fe6939 ;
    }
  }
}
@media screen and (max-width: 1950px) {
  .container-header {
    position: relative;
    height: auto;
    padding: 16px 30px;
    .code {
      .time {
        font-size: 24px;
      }
      img {
        width: 88px;
        height: 88px;
      }
    }
    .back-btn {
      padding: 9px;
      font-size: 16px;

      i {
        width: 24px;
        height: 24px;
        line-height: 24px;
      }
    }
    .count-time {
      position: absolute;
      right: 20px;
      bottom: -30px;
      color: red;
    }
  }
}
</style>
