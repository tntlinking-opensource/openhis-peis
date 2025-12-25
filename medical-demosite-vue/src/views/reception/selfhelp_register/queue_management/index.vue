<!-- 自助登记机：排队管理 开发人：麦沃德矢北、予安 -->
<template>
  <div class="home queue-management">
    <!-- 头部 -->
    <selfHelp ref="selfHelp"></selfHelp>
    <div class="container-body">
      <div class="show-main">
        <self-progress :step="step" themeColor="#1890FF" :progressItem="progressItem"></self-progress>
        <!-- 放置身份证图片 -->
        <card-verify v-if="step == 1"></card-verify>
        <!-- 选择排队 -->
        <div class="page-info flex-colum" v-else-if="step == 2">
          <div ref="qrimg" class="qrimg" id="qrimg"></div>
          <div class="queue-tips">请微信扫码后查看</div>
          <button class="back-btn" @click="backIndex">返回首页</button>

          <!-- <div class="person-item">
              <div class="title">基本信息</div>
              <div class="content">{{ personInfo.patientname + '    ' + (personInfo.idSex == 0 ? '男' : '女') + '    ' + personInfo.age + '岁' }}</div>
            </div>
            <div class="person-item">
              <div class="title">身份证号</div>
              <div class="content">{{ personInfo.idcardno }}</div>
            </div>
            <div class="person-item">
              <div class="title">手机号码</div>
              <div class="content">{{ personInfo.phone }}</div>
            </div>
            <div class="queue-info">
              <div class="section">当前科室 <span style="color: #1890ff">心电图</span> ，前面有 <span style="color: #1890ff">6人</span> 在等待</div>
              <div class="section">请选择目标科室，点击<span style="color: #1890ff">调整</span></div>
            </div>
            <div style="width: 100%">
              <el-table ref="examTable" :data="examTable" style="width: 975px; margin: 0 auto" @selection-change="handleSelectionChange" @row-click="rowClick">
                <el-table-column type="selection" width="80" align="center"> </el-table-column>
                <el-table-column label="序列" width="80" type="index" align="center" />
                <el-table-column label="体检科室" prop="a" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column label="排队状态" prop="b" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column label="等待总人数" prop="c" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column label="前面人数" prop="d" align="center" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
            <button class="page-btn" @click="handleNext">确认调整</button> -->
        </div>
        <!-- 调整成功 -->
        <self-success v-else-if="step == 3" theme="#1890ff" typeName="调整"></self-success>
      </div>
    </div>
    <!-- 语音：进入读身份证页 -->
    <audio controls="controls" hidden src="@/assets/voice/first.mp3" ref="audio"></audio>
  </div>
</template>

<script>
import { onMessage } from '@/api/reception/selfHelp/self_Border'
import selfHelp from '@/views/reception/selfhelp_register/component/selfHelp'
import selfProgress from '../component/selfProgress'
import selfSuccess from '../component/selfSuccess'
import cardVerify from '@/views/reception/selfhelp_register/component/cardVerify'
import QRCode from 'qrcodejs2'
import axios from 'axios'
export default {
  name: 'selfQueueManagement',
  components: {
    selfHelp,
    selfProgress,
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
      progressItem: [
        '身份验证',
        //  '调整排队', '完成调整'
        '查看排队',
      ],
      // 身份信息
      personInfo: {},
      // 表格加载中
      tableLoading: false,
      // 项目表格数据
      examTable: [
        {
          a: '内科',
          b: '等待中',
          c: '5',
          d: '10',
        },
        {
          a: '外科',
          b: '等待中',
          c: '5',
          d: '10',
        },
        {
          a: '磁共振',
          b: '等待中',
          c: '5',
          d: '10',
        },
        {
          a: '磁共振',
          b: '等待中',
          c: '5',
          d: '10',
        },
        {
          a: '磁共振',
          b: '等待中',
          c: '5',
          d: '10',
        },
      ],
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
              message: 'queue',
            }).then(({ data }) => {
              if (!data.patientcodes || !data.patientcodes.length) {
                this.$alert('此卡当前没有排队信息，请登记后再试', '提示', { showClose: false }).then(() => {
                  this.$router.push({ name: 'selfHelp' })
                })
              } else {
                this.step++
                this.$nextTick(() => {
                  new QRCode(this.$refs.qrimg, {
                    text: data.QUEUEING_SYSTEM_URL + data.patientcodes[0],
                    width: 200,
                    height: 200,
                    colorDark: '#000000',
                    colorLight: '#FFFFFF',
                    correctLevel: QRCode.CorrectLevel.L,
                  })
                })
              }
            })
            if (fn) {
              fn()
            }
          }
        })
        .catch(() => {})
    },
    // 返回首页
    backIndex() {
      this.$router.push({ name: 'selfHelp' })
    },

    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.selectInfo = undefined
      } else if (selection.length == 1) {
        this.selectInfo = selection[0]
      } else if (selection.length > 1) {
        this.$refs.examTable.clearSelection() //清空表格数据
        this.$refs.examTable.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.examTable.clearSelection()
      this.$refs.examTable.toggleRowSelection(row)
    },
    // 下一步
    handleNext() {
      // 个人信息位置下一页
      if (this.step == 2) {
        this.step++
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.home.queue-management {
  min-height: 100%;
  display: flex;
  flex-direction: column;

  .container-body {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    border: 32px solid #d0e8ff;
    // background: #d0e8ff;

    .show-main {
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      margin-bottom: 0;
      background-color: #fff;

      .page-info {
        min-height: 790px;
        margin-top: 16px;
        .qrimg {
          width: 200px;
          height: 200px;
        }
        .queue-tips {
          margin-top: 50px;
          margin-bottom: 50px;
          font-size: 42px;
        }
        .back-btn {
          padding: 17px 406px;
          font-size: 45px;
          color: #fff;
          background-color: #1890ff;
          border: none;
          border-radius: 10px;
          cursor: pointer;
          white-space: nowrap;
        }
        .person-item {
          display: flex;
          align-items: center;
          width: 975px;
          margin-bottom: 12.5px;
          .title {
            width: 275px;
            height: 57px;
            padding: 10px;
            margin-right: 13px;
            color: #333;
            font-size: 28px;
            font-weight: 600;
            text-align: center;
            background: #f5f5f5;
          }
          .content {
            width: 700px;
            height: 57px;
            padding: 10px 48px;
            color: #333;
            font-size: 28px;
            white-space: pre-wrap;
            background: #f5f5f5;
          }
        }
        .queue-info {
          width: 975px;
          padding: 16px;
          margin-bottom: 16px;
          background: #edf6ff;
          .section {
            font-size: 28px;
            text-align: center;
            &:first-child {
              padding-bottom: 16px;
            }
          }
        }
      }
      .flex-colum {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      .page-btn {
        padding: 17px 406px;
        margin-top: 41px;
        font-size: 45px;
        color: #fff;
        background: #1890ff;
        border: none;
        border-radius: 10px;
      }
    }
  }
}
@media screen and (max-width: 1950px) {
  .home.queue-management {
    display: flex;
    flex-direction: column;
    .container-body {
      flex: 1;
      display: flex;
      flex-direction: column;
      min-height: calc(100% - 120px);

      .show-main {
        justify-content: flex-start;

        .page-info {
          height: 100%;
          min-height: auto;

          .qrimg {
            margin-top: 80px;
          }
          .back-btn {
            font-size: 36px;
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
  .home.queue-management {
    .container-body {
      .show-main {
        .page-info {
          .back-btn {
            padding: 12px 290px;
            font-size: 26px;
            margin-top: 10px;
          }
        }
      }
    }
  }
}

@media screen and (min-height: 1200px) {
  .home.queue-management {
    .container-body {
      .show-main {
        margin-bottom: 50%;
      }
    }
  }
}
@media screen and (min-height: 2000px) {
  .home.queue-management {
    .container-body {
      .show-main {
        margin-bottom: 80%;
      }
    }
  }
}
</style>
<style scoped>
.queue-management /deep/ .el-table__header .cell {
  height: auto !important;
  padding: 10px 0;
  font-size: 34px !important;
  font-weight: 600;
  color: #333;
}
.queue-management /deep/ .el-table__body .cell {
  height: auto !important;
  padding: 10px 0;
  line-height: 30px;
  font-size: 28px !important;
}
.queue-management /deep/ .el-checkbox__input .el-checkbox__inner {
  width: 26px;
  height: 26px;
}
.queue-management /deep/ .el-checkbox__input .el-checkbox__inner::after {
  width: 12px;
  height: 16px;
}

.queue-management /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
