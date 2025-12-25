<template>
  <div class="body-main">
    <div class="body-title">扫码支付<span :style="{ color: theme }">888元</span></div>
    <div class="body-tips">支付成功后进入打印导引单和条码页面</div>
    <div class="pay-box">
      <div class="pay-list">
        <div class="pay-item" v-for="(item, index) in payList" :key="index" @click="selectPayWay(index)">
          <img :src="item.imgSrc" alt="" />
          <div class="pay-name" :style="{ color: index == payWay ? theme : '' }">{{ item.name }}</div>
          <i :class="index == payWay ? 'el-icon-success' : ''" style="width: 40px; font-size: 40px" :style="{ color: theme }"></i>
        </div>
      </div>
      <div class="qr-code">
        <img src="@/assets/images/selfHelp/QRcode.png" alt="" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['theme'],
  data() {
    return {
      // 支付方式列表
      payList: [
        {
          imgSrc: require('@/assets/images/selfHelp/paypal.png'),
          name: '支付宝',
        },
        {
          imgSrc: require('@/assets/images/selfHelp/wechat.png'),
          name: '微信',
        },
      ],
      // 选中的支付方式
      payWay: 2,
    }
  },
  methods: {
    // 选择支付方式
    selectPayWay(index) {
      return
      if (this.payWay != index) this.payWay = index
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

  .body-title {
    text-align: center;
    padding: 100px 0 17px;
    font-size: 48px;
  }
  .body-tips {
    color: #999;
    font-size: 34px;
  }
  .pay-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 30px;
    margin: 45px 0 150px;
    border-radius: 10px;
    background: #eaf3f8;
    .pay-list {
      .pay-item {
        display: flex;
        align-items: center;
        padding: 24px;
        margin-right: 20px;
        border-radius: 10px;
        img {
          width: 90px;
          height: 90px;
        }
        .pay-name {
          width: 200px;
          margin-left: 43px;
          font-size: 34px;
        }
      }
    }
    .qr-code {
      height: 100%;
      img {
        height: 100%;
      }
    }
  }
}
@media screen and (max-width: 1950px) {
  .body-main {
    height: auto;
    .body-title {
      padding: 50px 0 17px;
      font-size: 40px;
    }
    .body-tips {
      font-size: 24px;
    }
    .pay-box {
      padding: 20px;
      margin: 42px 0;
      .pay-list {
        .pay-item {
          padding: 24px;
          margin-right: 20px;
          border-radius: 10px;
          img {
            width: 64px;
            height: 64px;
          }
          .pay-name {
          width:160px;
            font-size: 28px;
          }
        }
      }
    }
  }
}
</style>
