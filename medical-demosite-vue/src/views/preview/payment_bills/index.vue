<!-- 缴费单 麦沃德科技 予安-->
<template>
  <el-dialog :visible.sync="dialogPayment" class="paymentBills">
    <div ref="paymentBills">
      <div class="page-footer-scope">
        <span>美好人生 健康相伴</span>
        <div class="row-border"></div>
        <div class="title-flex">
          <div class="title-text">青岛沃德体检中心</div>
          <span>{{ nowData }}</span>
        </div>
      </div>
      <table style="width: 100%">
        <thead>
          <tr>
            <td></td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <div class="payment-bills">
                <div class="header">
                  <h2 class="title">收费明细单</h2>
                  <img class="info-barcode" alt="" id="barcode" />
                </div>
                <div class="user-info">
                  <div class="item-key">姓名:</div>
                  <div class="item-value" style="flex: 3">{{ userData.name }}</div>
                  <div class="item-key">性别:</div>
                  <div class="item-value" style="flex: 1">{{ userData.sex }}</div>
                  <div class="item-key">年龄:</div>
                  <div class="item-value" style="flex: 2">{{ userData.age }}岁</div>
                  <div class="item-key">登记日期:</div>
                  <div class="item-value" style="flex: 4">{{ userData.dateregister }}</div>
                </div>
                <div class="first-table">
                  <div class="table-title">
                    <div class="title-item-1">项目名称</div>
                    <div class="title-item-1">收费</div>
                    <div class="title-item-1">价格(元)</div>
                  </div>
                  <div class="table-data" v-for="(item, index) in userData.items" :key="index">
                    <div class="title-item-1">{{ index + 1 + ' ' + item.itemname }}</div>
                    <div class="title-item-1">{{ item.FFeecharged }}</div>
                    <div class="title-item-1">{{ item.price }}</div>
                  </div>
                </div>
                <div style="display: flex">
                  <div style="flex: 1"></div>
                  <div class="first-table" style="flex: 4">
                    <div class="table-title" style="padding-left: 0">
                      <div class="title-item-2">收费时间</div>
                      <div class="title-item-2">计算方式</div>
                      <div class="title-item-2">已结否</div>
                      <div class="title-item-2">实收款(元)</div>
                    </div>
                    <div class="table-data" style="padding-left: 0" v-for="(item, index) in userData.charges" :key="index">
                      <div class="title-item-2">{{ item.feechargetime ? item.feechargetime.split(' ')[0] : '' }}</div>
                      <div class="title-item-2">{{ item.payway }}</div>
                      <div class="title-item-2">{{ item.FFeecharged }}</div>
                      <div class="title-item-2">{{ item.moneyamountpaid }}</div>
                    </div>
                  </div>
                </div>
                <div class="sum">合计：{{ userData.summary.toFixed(2) }}元</div>
              </div>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td>
              <div class="td-page-footer"></div>
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
  </el-dialog>
</template>

<script>
import { chargeDataPrint } from '@/api/reception/proceeds.js'

import JsBarcode from 'jsbarcode'

export default {
  data() {
    return {
      // 对话框显示
      dialogPayment: false,
      // 用户信息
      userData: {
        items: [],
        charges: [],
        summary: 0,
      },
      nowData: undefined,
    }
  },
  computed: {},
  watch: {},
  mounted() {
    let nowTime = new Date()
    let _year = nowTime.getFullYear()
    let _mounth = nowTime.getMonth() + 1
    let _data = nowTime.getDate()
    let _hour = nowTime.getHours()
    let _minute = nowTime.getMinutes()
    let _second = nowTime.getSeconds()
    this.nowData = _year + '-' + _mounth + '-' + _data + '  ' + _hour + ':' + _minute + ':' + _second
  },
  methods: {
    showDialog(id) {
      this.dialogPayment = true
      chargeDataPrint({
        id,
      }).then(({ data }) => {
        if (data[0].success) {
          this.userData = data[0]
          this.userData.summary = 0
          data[0].charges.forEach((el) => {
            this.userData.summary += el.moneyamountpaid
          })
          this.$nextTick(() => {
            JsBarcode('#barcode', this.userData.patientCode, {
              format: 'CODE39', //条形码的格式
              width: 1, //线宽
              height: 25, //条码高度
              fontSize: 15,
              lineColor: '#000', //线条颜色
              displayValue: true, //是否显示文字
              margin: 0, //设置条形码周围的空白区域
            })
          })
          this.$nextTick(() => {
            this.$print(this.$refs.paymentBills)
            this.dialogPayment = false
          })
        } else {
          this.dialogPayment = false
          this.$alert(data[0].msg, '提示')
        }
      })
    },
  },
}
</script>

<style lang="scss">
.paymentBills {
  display: none;
}
.payment-bills {
  font-family: Simsun;
  color: #000;
  .header {
    position: relative;
    .title {
      text-align: center;
      margin-bottom: 22px;
      font-size: 25px;
      font-weight: 600;
    }
    .info-barcode {
      position: absolute;
      top: 0;
      right: 20px;
      display: inline-block;
    }
  }
  .user-info {
    display: flex;
    justify-content: space-between;
    // align-items: center;
    padding: 10px 0 6px;
    font-size: 16px;
    border-top: 2px #000 solid;
    border-bottom: 2px #000 solid;
    .item-key {
      padding: 0 20px 0 15px;
    }
    .item-value {
      height: 18px;
      padding-bottom: 4px;
      text-align: center;
      border-bottom: 2px #000 dotted;
    }
  }
  .first-table {
    margin-top: 12px;
    width: 100%;
    .table-title,
    .table-data {
      display: flex;
      padding-left: 15px;
      padding-right: 25px;
      font-size: 20px;
      border-bottom: 1px #000 solid;
      .title-item-1 {
        flex: 1;
        &:first-child {
          flex: 5;
        }
      }
      .title-item-2 {
        flex: 3;
        &:last-child {
          flex: 2;
        }
      }
    }
    .table-data {
      font-size: 18px;
      border-bottom: 2px #000 dotted;
    }
  }
  .sum {
    text-align: right;
    font-size: 20px;
    font-weight: 600;
  }
}
@media print {
  .paymentBills {
    display: block;
  }
  .td-page-footer,
  .page-footer-scope {
    margin-top: 40px;
    height: 100px;
  }
  .page-footer-scope {
    position: fixed;
    bottom: 0;
    width: 100%;
    .title-flex {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
    }
    .row-border {
      border-top: 2px solid black;
      padding: 2px;
    }
    .title-text {
      font-size: 14px;
    }
  }
}
</style>
