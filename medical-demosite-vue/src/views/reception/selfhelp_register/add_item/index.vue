<!-- 自助登记机：加项缴费 开发人：麦沃德矢北、予安 -->
<template>
  <div class="home add-item">
    <!-- 头部 -->
    <selfHelp ref="selfHelp"></selfHelp>
    <div class="container-body">
      <div class="show-main">
        <self-progress :step="step" themeColor="#00B473" :progressItem="progressItem"></self-progress>
        <!-- 放置身份证图片 -->
        <card-verify v-if="step == 1"></card-verify>
        <!-- 选择体检项目 -->
        <div class="page-info flex-colum" v-else-if="step == 2">
          <div class="page-title">选择体检项目</div>
          <div class="table-main">
            <el-table ref="examTable" :data="examTable" style="width: 100%; margin: 0 auto" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" width="200" align="center"> </el-table-column>
              <el-table-column label="项目名称" prop="itemName" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column label="金额" width="200" prop="factprice" align="center">
                <template slot-scope="scope">
                  <span style="color: #00b473">{{ scope.row.factprice ? Number(scope.row.factprice).toFixed(2) : '0.00' }}元</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <button class="page-btn" @click="handleNext">确认缴费</button>
        </div>
        <!-- 支付页面 -->
        <self-pay v-else-if="step == 3" theme="#00b473"></self-pay>
        <!-- 支付成功 -->
        <self-success v-else-if="step == 4" theme="#00b473" typeName="加项"></self-success>
      </div>
    </div>
    <!-- 语音：进入读身份证页 -->
    <audio controls="controls" hidden src="@/assets/voice/first.mp3" ref="audio"></audio>
  </div>
</template>

<script>
import { onMessage, addItemsApi } from '@/api/reception/selfHelp/self_Border'
import selfHelp from '@/views/reception/selfhelp_register/component/selfHelp'
import selfProgress from '../component/selfProgress'
import selfPay from '../component/selfPay'
import selfSuccess from '../component/selfSuccess'
import cardVerify from '@/views/reception/selfhelp_register/component/cardVerify'
import axios from 'axios'
export default {
  name: 'selfAddItem',
  components: {
    selfHelp,
    selfProgress,
    selfPay,
    selfSuccess,
    cardVerify,
  },
  data() {
    return {
      // 备单多个则在s中存储
      patientCodes: undefined,
      patientCode: undefined,
      // 循环调用读卡器
      timer: undefined,
      // 步骤
      step: 1,
      // 步骤内容
      progressItem: ['身份验证', '选择项目', '完成支付', '加项完成'],
      // 表格加载中
      tableLoading: false,
      // 项目表格数据
      examTable: [],
      // 选中的表格信息
      selectInfo: [],
    }
  },
  mounted() {
    this.handleReadCard(() => {
      clearInterval(this.timer)
      clearTimeout(timer2)
    })
    this.timer = setInterval(() => {
      // 读身份证
      this.handleReadCard(() => {
        clearInterval(this.timer)
        clearTimeout(timer2)
      })
    }, 2000)
    let timer2 = setTimeout(() => {
      clearInterval(this.timer)
      this.$refs.selfHelp.backIndex()
    }, 60000)
    // 播放语音
    this.$refs.audio.currentTime = 0 //从头开始播放
    this.$refs.audio.play() //播放
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    // 读身份证
    handleReadCard(fn) {
      axios({
        method: 'get',
        url: 'http://localhost:9001/machine/readCard/onMessage',
      })
        .then(({ data }) => {
          if (data.code === 200) {
            // 获取体检号
            onMessage({
              idcardno: data.data.idNumber,
              name: data.data.name,
              sex: data.data.sex,
              message: 'addItem',
            }).then((res) => {
              if (!res.data || !res.data.patientcode) {
                this.$alert('暂无加项缴费内容', '提示', { showClose: false }).then(() => {
                  this.$router.push({ name: 'selfHelp' })
                })
              } else {
                this.$alert('功能升级中,请到导检台人工窗口办理业务', '提示', { showClose: false }).then(() => {
                  this.$router.push({ name: 'selfHelp' })
                })
                // addItemsApi({
                //   patientcode: res.data.patientcode,
                // }).then(({ data }) => {
                //   this.step++
                //   this.examTable = data.items
                // })
              }
            })
            if (fn) {
              fn()
            }
          }
        })
        .catch(() => {})
    },
    // 表格选中
    handleSelectionChange(selection) {
      this.selectInfo = selection
    },
    // 表格单击事件
    rowClick(row) {
      this.$refs.examTable.toggleRowSelection(row)
    },
    // 下一步
    handleNext() {
      // 个人信息位置下一页
      if (this.step == 2) {
        this.step++
        setTimeout(() => {
          this.step++
        }, 1000)
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.home.add-item {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  .container-body {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    border: 32px solid rgba(0, 180, 115, 0.2);
    // background: rgba(0, 180, 115, 0.2);
    .show-main {
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      margin-bottom: 0;
      background-color: #fff;
      .page-info {
        height: 790px;
        .table-main {
          width: 100%;
          height: 510px;
        }
      }
      .flex-colum {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }

      .page-title {
        padding: 56px 0 40px;
        font-size: 56px;
      }
      .page-btn {
        padding: 17px 406px;
        margin-top: 41px;
        font-size: 45px;
        color: #fff;
        background: #00b473;
        border: none;
        border-radius: 10px;
        cursor: pointer;
        white-space: nowrap;
      }
    }
  }
}
@media screen and (max-width: 1950px) {
  .home.add-item {
    display: flex;
    flex-direction: column;
    .container-body {
      flex: 1;
      min-height: calc(100% - 120px);
      .show-main {
        justify-content: flex-start;

        .page-info {
          height: auto;
          .table-main {
            height: 350px;
          }
        }
        .page-title {
          padding: 40px 0 30px;
          font-size: 40px;
        }
        .page-btn {
          margin-top: 41px;
          font-size: 32px;
        }
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .home.add-item {
    .container-body {
      .show-main {
        margin-bottom: 50%;
        .person-info {
          .person-item {
            .title {
              padding: 0 60px;
            }
            .content {
              width: 500px;
            }
          }
        }
        .page-btn {
          padding: 12px 290px;
          font-size: 26px;
          margin-top: 10px;
        }
      }
    }
  }
}
@media screen and (min-height: 1200px) {
  .home.add-item {
    .container-body {
      .show-main {
        margin-bottom: 50%;
      }
    }
  }
}
@media screen and (min-height: 2000px) {
  .home.add-item {
    .container-body {
      .show-main {
        margin-bottom: 80%;
      }
    }
  }
}
</style>
<style scoped>
@media screen and (min-width: 1950px) {
  .add-item /deep/ .el-table__header .cell {
    height: auto !important;
    padding: 10px 0;
    font-size: 34px !important;
    font-weight: 600;
    color: #333;
  }
  .add-item /deep/ .el-table__body .cell {
    height: auto !important;
    padding: 10px 0;
    line-height: 30px;
    font-size: 28px !important;
  }
  .add-item /deep/ .el-checkbox__input .el-checkbox__inner {
    width: 26px;
    height: 26px;
  }
  .add-item /deep/ .el-checkbox__input .el-checkbox__inner::after {
    width: 12px;
    height: 16px;
  }
}
</style>
