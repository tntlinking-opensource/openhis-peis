<template>
  <div class="home">
    <selfHelp position="index"></selfHelp>
    <div class="container-body">
      <div class="body-center">
        <div class="center-items" @click="target(index)" v-for="(items, index) in navList" :key="index">
          <div class="img-box" :id="`img${index}`">
            <img :src="items.image_src" />
            <div class="text-box">
              <span class="text-label">{{ items.label }}</span>
              <span class="text-eLabel">{{ items.eLabel }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import selfHelp from '@/views/reception/selfhelp_register/component/selfHelp'
export default {
  components:{
    selfHelp
  },
  data() {
    return {
      navList: [
        { id: 'prepareOrder', eLabel: 'Self-boarding', image_src: require('@/assets/images/selfHelp/print.png'), label: '自助登记', name: 'Prepare_order' },
        { id: 'registrationList', eLabel: 'Add a payment', image_src: require('@/assets/images/selfHelp/addItem.png'), label: '加项缴费', name: 'Register_list' },
        { id: 'registration', eLabel: 'Queue management', image_src: require('@/assets/images/selfHelp/order.png'), label: '排队管理', name: 'Registration' },
        { id: 'refund', eLabel: 'Report to print', image_src: require('@/assets/images/selfHelp/prints.png'), label: '报告打印', name: 'ReturnItem' },
      ],
    }
  },
  methods: {
    target(index) {
      if (index == 0) {
        this.$router.push({ name: 'selfBoard' })
      } else if (index == 1) {
        this.$router.push({ name: 'selfAddItem' })
      } else if (index == 2) {
        this.$router.push({ name: 'selfQueueManagement' })
      } else if (index == 3) {
        this.$router.push({ name: 'selfReportPrint' })
      } else {
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.home {
  height: 100%;
  background-image: url('../../../assets/images/shbg.png');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;

  .container-body {
    height: calc(60% - 168px);
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 30px;

    .body-center {
      width: 80%;
      padding: 0 0 0px 0;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      align-content: space-between;

      :nth-child(n + 3) {
        margin-top: 2vh;
      }

      .center-items {
        width: 770px;
        height: 490px;
        background-color: white;
        border-radius: 10px;
        cursor: pointer;

        .img-box {
          width: 100%;
          height: 100%;
          display: flex;
          flex-flow: column;
          justify-content: center;
          align-items: center;

          img {
            width: 196px;
            height: 196px;
            text-align: center;
            vertical-align: middle;
          }

          .text-box {
            display: flex;
            flex-flow: column;
            text-align: center;

            span {
              margin-top: 10px;
            }
            .text-label {
              font-size: 56px;
              font-weight: 600;
            }
            .text-eLabel {
              font-size: 34px;
              color: #999999;
            }
          }
        }
      }
    }
  }
}
@media screen and (max-width: 1950px) {
  .home {
    height: auto;
    min-height: 100%;
    .container-body {
      height: auto;
      .body-center {
        .center-items {
          width: 48%;
          height: auto;
          padding: 52px;
          .img-box {
            img {
              width: 140px;
              height: 140px;
            }
            .text-box {
              display: flex;
              flex-flow: column;
              text-align: center;
              .text-label {
                font-size: 40px;
              }
              .text-eLabel {
                font-size: 24px;
              }
            }
          }
        }
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .home {
    .container-body {
      height: auto;
      margin-top: 280px;
      .body-center {
        .center-items {
          width: 48%;
          height: auto;
          padding: 52px;
          .img-box {
            img {
              width: 140px;
              height: 140px;
            }
            .text-box {
              display: flex;
              flex-flow: column;
              text-align: center;
              .text-label {
                font-size: 40px;
              }
              .text-eLabel {
                font-size: 24px;
              }
            }
          }
        }
      }
    }
  }
}

</style>
