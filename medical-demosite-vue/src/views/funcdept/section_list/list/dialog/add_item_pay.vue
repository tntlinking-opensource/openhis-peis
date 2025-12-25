<template>
  <!-- 科室加项对话框 -->
  <el-dialog title="科室加项订单" :visible.sync="openDialog" width="800px" class="add-item-pay" append-to-body :close-on-click-modal="false" :close-on-press-escape="false" @close="handleClose">
    <div class="patient-info zk-word-color">
      <span>{{ patientInfo.patientname }}</span>
      <span>{{ patientInfo.age }}岁</span>
      <span>{{ ['男', '女'][patientInfo.idSex] }}</span>
      <span>{{ ['健康体检', '职业体检', '综合体检', '复查'][patientInfo.idExamtype] }}</span>
      <span>{{ patientInfo.phone }}</span>
      <span>{{ patientInfo.patientcode }}</span>
    </div>
    <el-table ref="selectList" border v-loading="loading" :data="addItemList" height="200px">
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="收费项目名称" prop="examfeeitemName" align="center" show-overflow-tooltip />
      <el-table-column label="价格" prop="price" align="center" show-overflow-tooltip width="120px" />
      <el-table-column label="加项医生" prop="doctorUsername" align="center" show-overflow-tooltip width="100px" />
      <el-table-column label="所属科室" prop="ssks" align="center" show-overflow-tooltip width="100px" />
    </el-table>
    <div class="price-total zk-word-color">
      <div>合计</div>
      <div>{{ patientInfo.totalPrice }}元</div>
    </div>
    <div class="pay-box">
      <div class="pay-options">
        <div v-for="(item, index) in payOptions" :key="index" :class="selecttPayIndex == index ? 'select-item' : ''" @click="changePayWay(index)">{{ item }}</div>
      </div>
      <div class="pay-container">
        <div class="pay-container-box" v-if="selecttPayIndex == 0">
          <img src="@/assets/images/section/barcode_scanner.png" alt="" />
          <div class="tips">
            <div class="watch-hand zk-bg-color">
              <img src="@/assets/images/section/watch_hand.png" alt="" />
            </div>
            <div class="tips-word">{{ payTips }}</div>
            <el-input class="add-item-cardId" ref="cardId" v-model="cardId" @blur="handleCardIdBlur" @keyup.enter.native="handleCreateOrder"></el-input>
          </div>
        </div>
        <div class="pay-container-box" v-else>
          <div ref="payQrcode" id="payQrcode" class="pay-qrcode-box"></div>
          <div class="tips">
            <div class="watch-hand zk-bg-color">
              <img src="@/assets/images/section/watch_hand.png" alt="" />
            </div>
            <div class="tips-word">{{ payTips }}</div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1"  @click="getTonglianResult">查询支付结果</el-button>
    </div> -->
  </el-dialog>
</template>

<script>
import { payment, callBackApi } from '@/api/funcdept/section_list/dialog.js'
import { queryTongLian } from '@/api/reception/proceeds.js'
import QRCode from 'qrcodejs2'
export default {
  data() {
    return {
      // 更新日志对话框
      openDialog: false,
      // 体检者信息
      patientInfo: {},
      // 加载中
      loading: false,
      // 加项数据
      addItemList: [],
      // 支付方式
      payOptions: ['扫码枪扫码', '微信支付', '支付宝支付'],
      // 选择的支付方式索引
      selecttPayIndex: 0,
      // 支付提示
      payTips: '',
      // 扫码枪扫描的付款码号
      cardId: '',
      // 循环查询时的订单id
      trxid: undefined,
      // 循环调用查询是否支付成功
      payTimer: null,
    }
  },
  methods: {
    showDialog(data) {
      this.patientInfo = data
      this.addItemList = data.list
      this.selecttPayIndex = 0
      this.payTips = '请使用扫码枪扫描付款码'
      this.openDialog = true
      this.$nextTick(() => {
        this.$refs.cardId.focus()
      })
    },
    // 切换支付方式
    changePayWay(index) {
      if (this.selecttPayIndex == index) {
        return
      }
      if (this.payTips == '请提醒用户输入支付密码') {
        return
      }
      this.selecttPayIndex = index
      if (index == 0) {
        this.payTips = '请使用扫码枪扫描付款码'
        this.$nextTick(() => {
          this.$refs.cardId.focus()
        })
      } else {
        if (index == 1) {
          this.payTips = '请使用微信扫码扫描上方二维码进行支付'
        } else if (index == 2) {
          this.payTips = '请使用支付宝扫码扫描上方二维码进行支付'
        }
        this.$nextTick(() => {
          const videoFlvContainer = document.getElementById('payQrcode')
          while (videoFlvContainer.firstChild) {
            videoFlvContainer.removeChild(videoFlvContainer.firstChild)
          }
          this.handleCreateOrder()
        })
      }
    },
    // 扫码枪时强制聚焦
    handleCardIdBlur() {
      if (this.selecttPayIndex == '0') {
        this.$nextTick(() => {
          this.$refs.cardId.focus()
        })
      }
    },
    // 生成订单及流水号等
    handleCreateOrder() {
      let query = {}
      if (this.selecttPayIndex == 0) {
        query = {
          idPayway: '22',
          cardId: this.cardId,
        }
      } else if (this.selecttPayIndex == 1) {
        query = {
          idPayway: '101',
          paytype: 'W01',
        }
      } else {
        query = {
          idPayway: '101',
          paytype: 'A01',
        }
      }
      payment({
        list: this.addItemList,
        patientcode: this.patientInfo.patientcode,
        totalPrice: this.patientInfo.totalPrice,
        ...query,
      })
        .then((res) => {
          if (this.selecttPayIndex == 0) {
            if (res.data.version == 2000 && res.data.status == 'fail') {
              this.payTips = '请提醒用户输入支付密码'
              this.trxid = res.data.payNo
              this.payTimer = setInterval(() => {
                this.getTonglianResult()
              }, 1000)
            } else {
              this.$modal.msgSuccess('支付成功')
              this.openDialog = false
              this.$emit('getListRight')
            }
          } else {
            this.trxid = res.data.payNo
            // 生成付款码
            let qrcode = new QRCode(this.$refs.payQrcode, {
              text: res.data.remarks,
              width: 216,
              height: 216,
              colorDark: '#000000',
              colorLight: '#FFFFFF',
              correctLevel: QRCode.CorrectLevel.H,
            })
            clearInterval(this.payTimer)
            this.payTimer = null
            this.payTimer = setInterval(() => {
              this.getTonglianResult()
            }, 1000)
          }
        })
        .catch((err) => {
          console.error(err)
          if (this.selecttPayIndex == '0') {
            this.cardId = ''
            this.$nextTick(() => {
              this.$refs.cardId.focus()
            })
          }
        })
    },
    // 查询通联支付结果
    getTonglianResult() {
      queryTongLian({
        trxid: this.trxid,
      }).then(({ data }) => {
        if (data.retcode == 'SUCCESS') {
          if (data.trxstatus == '2000') {
          } else if (data.trxstatus == '0000') {
            this.handleconfirm()
            clearInterval(this.payTimer)
            this.payTimer = null
          } else {
            this.$modal.msgWarning('支付失败,请重试')
            this.openDialog = false
            clearInterval(this.payTimer)
            this.payTimer = null
          }
        }
      })
    },
    // 支付成功后的回调
    handleconfirm() {
      let query = {}
      if (this.selecttPayIndex == 0) {
        query = {
          idPayway: '22',
          cardId: this.cardId,
        }
      } else if (this.selecttPayIndex == 1) {
        query = {
          idPayway: '101',
          paytype: 'W01',
        }
      } else {
        query = {
          idPayway: '101',
          paytype: 'A01',
        }
      }
      callBackApi({
        list: this.addItemList,
        patientcode: this.patientInfo.patientcode,
        totalPrice: this.patientInfo.totalPrice,
        consumeId: this.trxid,
        ...query,
      }).then((res) => {
        this.$modal.msgSuccess('支付成功')
        this.openDialog = false
        this.$emit('getListRight')
      })
    },
    // 关闭弹窗
    handleClose() {
      clearInterval(this.payTimer)
      this.payTimer = null
    },
  },
}
</script>

<style lang="scss" scoped>
.add-item-pay {
  .patient-info {
    font-size: 16px;
    margin-bottom: 8px;
    span {
      margin-right: 12px;
    }
  }

  .price-total {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 8px 0;
    font-size: 16px;
  }

  .pay-box {
    overflow: hidden;
    border-radius: 8px;
    .pay-options {
      display: flex;
      align-items: center;
      & > div {
        flex: 1;
        padding: 8px 0;
        font-size: 16px;
        text-align: center;
        cursor: pointer;
        &:first-child {
          color: #fe6939;
          background-color: #fff3ef;
          &:hover,
          &.select-item {
            color: #fff;
            background-color: #fe6939;
          }
        }
        &:nth-child(2) {
          color: #00b473;
          background-color: #f1fffa;
          &:hover,
          &.select-item {
            color: #fff;
            background-color: #00b473;
          }
        }
        &:nth-child(3) {
          color: #188df0;
          background-color: #ecf6ff;
          &:hover,
          &.select-item {
            color: #fff;
            background-color: #188df0;
          }
        }
      }
    }

    .pay-container {
      height: 320px;
      background-color: #f0f2f4;
      .pay-container-box {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
        img {
          width: 216px;
          height: 216px;
        }
        .tips {
          display: flex;
          align-items: center;
          padding: 8px;
          margin-top: 12px;
          border-radius: 24px;
          background-color: #2c2c2c;
          .watch-hand {
            width: 28px;
            height: 28px;
            border-radius: 50%;
            img {
              width: 10px;
              height: 10px;
              transform: translate(12px, 2px);
            }
          }
          .tips-word {
            margin: 0 8px;
            color: #fff;
            font-size: 16px;
          }
          .add-item-cardId {
            position: absolute;
            opacity: 0;
          }
        }

        .pay-qrcode-box {
          overflow: hidden;
          width: 216px;
          height: 216px;
        }
      }
    }
  }
}
</style>
