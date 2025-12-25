<!-- 自助登记机：身份验证 开发人：麦沃德矢北、予安 -->
<template>
  <div class="home self-verify">
    <!-- 头部 -->
    <selfHelp ref="selfHelp"></selfHelp>
    <div class="container-body">
      <div class="show-main">
        <template v-if="!showInfo">
          <self-progress :step="step" themeColor="#ab0004" :progressItem="progressItem"></self-progress>
          <!-- 放置身份证图片 -->
          <cardVerify v-if="step == 1"></cardVerify>
          <!-- 个人信息 -->
          <div class="person-info flex-colum" v-else-if="step == 2">
            <div class="page-title">个人信息</div>
            <div id="personInfo">
              <div class="person-item">
                <div class="title">基本信息</div>
                <div class="content">
                  <span v-if="personInfo.patientname">
                    {{ personInfo.patientname + '    ' + (personInfo.idSex == 0 ? '男' : '女') + '    ' + personInfo.age + '岁' }}
                  </span>
                </div>
              </div>
              <div class="person-item">
                <div class="title">身份证号</div>
                <div class="content">{{ personInfo.idcardno }}</div>
              </div>
              <div class="person-item">
                <div class="title">手机号码</div>
                <div class="content">
                  <!-- <span v-if="!isEmptyNum"> -->
                  <span v-if="false">
                    {{ personInfo.phone }}
                  </span>
                  <el-input v-else ref="phoneNum" v-model="personInfo.phone" placeholder="请输入手机号" @focus="showKeyboard = true"></el-input>
                  <div class="keyboard" v-if="showKeyboard">
                    <div class="keyboard-item" v-for="item in 9" :key="item" @click="tapNum(item)">{{ item }}</div>
                    <div class="keyboard-item" @click="tapNum(0)">0</div>
                    <div class="keyboard-item" style="flex: 1" @click="tapNum('')">退格</div>
                    <div class="keyboard-item" @click="handleNext">下一步</div>
                  </div>
                </div>
              </div>
              <div class="person-item">
                <div class="title">单位名称</div>
                <div class="content">{{ personInfo.orgName }}</div>
              </div>
              <div class="person-item">
                <div class="title">体检套餐</div>
                <div class="content">{{ personInfo.examsuiteName }}</div>
              </div>
              <div class="person-item">
                <div class="title">工种类型</div>
                <div class="content">{{ personInfo.trades }}</div>
              </div>
            </div>
            <button class="page-btn" @click="handleNext">下一步</button>
          </div>
          <!-- 打印导引单及条码 -->
          <div class="print-main" v-else-if="step == 5">
            <img src="@/assets/images/selfHelp/prints.png" alt="" />
            <div class="print-title">导引单及条码打印中...</div>
          </div>
          <!-- 登记完成 -->
          <self-success v-else-if="step == 6" theme="#ab0004" typeName="登记"></self-success>
        </template>
        <!-- 选择体检信息 -->
        <div class="table-main-box" v-else>
          <div class="page-title">请选择你要登记的体检信息</div>
          <el-table ref="examInfoTable" :data="examInfoTable" height="360" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center"> </el-table-column>
            <el-table-column label="创建日期" prop="createDate" align="center"></el-table-column>
            <el-table-column label="体检号" prop="patientCode" align="center"></el-table-column>
            <el-table-column label="姓名" prop="patientName" align="center"></el-table-column>
            <el-table-column label="团体名称" prop="teamName" align="center"></el-table-column>
            <el-table-column label="套餐名称" prop="mealName" align="center"></el-table-column>
            <el-table-column label="统收限额" prop="tsLimit" align="center"></el-table-column>
            <el-table-column label="待缴费金额" prop="payAmount" align="center"></el-table-column>
          </el-table>
          <div class="table-tips">温馨提示:如果存在待缴费金额时，请到导检台人工窗口办理业务</div>
          <button class="page-btn" @click="handleSubmit">确认提交</button>
        </div>
      </div>
    </div>
    <!-- 输入手机号蒙版 -->
    <div class="phone-modal" v-if="showKeyboard" @click="showKeyboard = false"></div>
    <!-- 打印 -->
    <print-html ref="printHtml" :selfRigister="true"></print-html>
    <!-- 语音：进入读身份证页 -->
    <audio controls="controls" hidden src="@/assets/voice/first.mp3" ref="audio"></audio>
    <!-- 语音：读取到信息后 -->
    <audio controls="controls" hidden src="@/assets/voice/second.mp3" ref="audio2"></audio>
    <!-- 语音：进入个人信息页 -->
    <audio controls="controls" hidden src="@/assets/voice/third.mp3" ref="audio3"></audio>
    <!-- 语音：下一步打印 -->
    <audio controls="controls" hidden src="@/assets/voice/fourth.mp3" ref="audio4"></audio>
    <!-- 语音：打印完成出单子 -->
    <audio controls="controls" hidden src="@/assets/voice/fifth.mp3" ref="audio5"></audio>
  </div>
</template>

<script>
import { registerApi, registerSelect, checkAmount, registerInfo, confirmRegister, printApi, modelApi } from '@/api/reception/selfHelp/self_Border'
import { middleDbInterface, uploadImage } from '@/api/reception/registration'
import selfHelp from '@/views/reception/selfhelp_register/component/selfHelp'
import selfProgress from '../component/selfProgress'
import selfSuccess from '../component/selfSuccess'
import printHtml from '@/views/reception/registration/print.vue'
import cardVerify from '@/views/reception/selfhelp_register/component/cardVerify'
// 获取默认打印条数
import { barcodePrinter } from '@/api/reception/register_list'
import axios from 'axios'
export default {
  name: 'selfBoard',
  components: {
    selfHelp,
    selfProgress,
    selfSuccess,
    printHtml,
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
      progressItem: ['身份验证', '个人信息', '选择项目', '完成支付', '导引单条码打印', '登记完成'],
      // 选择体检信息
      showInfo: false,
      // 体检信息表格
      examInfoTable: [],
      // 选中的表格信息
      selectInfo: undefined,
      // 表格加载中
      tableLoading: false,
      // 体检者信息
      personInfo: {},
      // 是否为空需输入手机号
      // isEmptyNum: false,
      // 显示虚拟数字按键
      showKeyboard: false,
      // 个人信息上传图片使用
      personData: {},
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
    base64ToFile(base64, fileName) {
      const arr = base64.split(',')
      const mime = arr[0].match(/:(.*?);/)[1]
      const bstr = atob(arr[1])
      let n = bstr.length
      const u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], fileName, { type: mime })
    },
    // 读身份证
    handleReadCard(fn) {
      axios({
        method: 'get',
        url: 'http://localhost:9001/machine/readCard/onMessage',
      }).then(({ data }) => {
        if (data.code === 200) {
          if (fn) {
            fn()
          }
          this.personData = {}
          // 创建 FormData 对象，用于上传文件
          const file = this.base64ToFile(data.data.picture, 'picture.png')
          const formData = new FormData()
          formData.append('file', file)
          uploadImage(formData)
            .then(({ data: data1 }) => {
              this.personData = {
                idNumber: data.data.idNumber,
                name: data.data.name,
                sex: data.data.sex,
                picture: data1,
              }
              // 获取体检号
              registerApi(this.personData).then((response) => {
                if (response.data.code === '0' && response.data.data) {
                  this.patientCodes = response.data.data.patientcode
                  this.showInfo = true
                  this.step = 2
                  this.tableLoading = true
                  // 获取体检套餐信息
                  registerSelect({
                    patientCodes: this.patientCodes,
                  })
                    .then(({ data }) => {
                      this.examInfoTable = data
                      this.tableLoading = false
                      // 播放语音
                      this.$refs.audio2.currentTime = 0 //从头开始播放
                      this.$refs.audio2.play() //播放
                    })
                    .catch((error) => {
                      console.error(error)
                      this.tableLoading = false
                    })
                } else {
                  this.$alert('此卡当前没有登记或者预约记录，请到导检台人工窗口办理业务', '提示', { showClose: false })
                    .then(() => {
                      this.$router.push({ name: 'selfHelp' })
                    })
                    .catch(() => {
                      this.$router.push({ name: 'selfHelp' })
                    })
                }
              })
            })
            .catch((error) => {
              console.error(error)
            })
        }
      })
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.selectInfo = undefined
      } else if (selection.length == 1) {
        this.selectInfo = selection[0]
      } else if (selection.length > 1) {
        this.$refs.examInfoTable.clearSelection() //清空表格数据
        this.$refs.examInfoTable.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.examInfoTable.clearSelection()
      this.$refs.examInfoTable.toggleRowSelection(row)
    },
    // 按下数字按键
    tapNum(number) {
      if (number || number === 0) {
        this.personInfo.phone += number.toString()
      } else {
        this.personInfo.phone = this.personInfo.phone.substr(0, this.personInfo.phone.length - 1)
      }
    },
    // 提交套餐信息
    handleSubmit() {
      let error = ''
      if (!this.selectInfo) {
        error = '请先选择体检套餐'
      } else if (this.selectInfo.payAmount != 0) {
        error = '该登记信息存在待缴费金额，请到导检台人工窗口办理业务'
      } else if (this.selectInfo.canReg && this.selectInfo.isPay) {
        error = '该登记信息存在待缴费金额，请到导检台人工窗口办理业务'
      } else if (this.selectInfo.fPaused == 1) {
        error = '当前选择的体检号处于禁检状态，请联系销售经理处理'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.patientCode = undefined
      this.examInfoTable.forEach((el, index) => {
        if (el == this.selectInfo) {
          // this.patientCode = this.patientCodes.split(',')[index]
          this.patientCode = el.patientCode
        }
      })
      checkAmount({ patientCode: this.patientCode }).then((res) => {
        if (res.data.code === '0') {
          this.showInfo = false
          const loading = this.$loading({ target: '#personInfo' })
          registerInfo({
            patientCode: this.patientCode,
            ...this.personData,
            idSex: this.personData.sex == '男' ? 0 : 1,
          }).then(({ data }) => {
            this.personInfo = data
            // 播放语音
            this.$refs.audio3.currentTime = 0 //从头开始播放
            this.$refs.audio3.play() //播放
            loading.close()
            // if (!data.phone) {
            //   this.isEmptyNum = true
            // }
          })
        } else {
          this.$modal.msg(data.message)
        }
      })
    },
    // 下一步
    handleNext() {
      // 个人信息位置下一页
      if (this.step == 2) {
        var telStr = /^[1]([3-9])[0-9]{9}$/
        if (!telStr.test(this.personInfo.phone)) {
          this.$alert('手机号码输入不规范', '提示')
          this.$refs.phoneNum.focus()
          return
        }
        // getItemData({ patientCode: this.patientCode }).then(({ data }) => {
        // this.
        confirmRegister({
          ...this.personInfo,
          patientCode: this.personInfo.patientcode,
        }).then(({ data }) => {
          if (data.code === '0') {
            if (data.data.canReg === 1) {
              if (data.data.isPay === 1) {
                //进入支付
                // window.location.href = '${pageContext.request.contextPath}/big/register!charge.action?patientcode=' + data.data.patientCode
                this.$alert('存在待缴费金额，请到导检台人工窗口办理业务', '提示', { showClose: false }).then(() => {
                  this.$router.push({ name: 'selfHelp' })
                })
              } else {
                // 进入打印
                // 数据重发
                // this.chongfa(data.data.patientCode)
                this.step = 5
                // 播放语音
                this.$refs.audio4.currentTime = 0 //从头开始播放
                this.$refs.audio4.play() //播放
                // 数据重发数据
                var chongfaData = data.data.patientCode
                // 登记完成
                printApi({ patientCode: data.data.patientCode }).then(({ data, code }) => {
                  if (code == 200) {
                    // 数据重发
                    this.chongfa(chongfaData)   
                    let dataInfo = {
                      id: this.$getCookie('cid'), //分中心id
                      ids: [this.personInfo.patientcode], //体检者ids
                      model: 1, //模板id
                      dydStyle: 1, //导引单类型 0 简单 1 复杂 2 普通
                      radio: 2, //内容
                    }
                    const query = {
                      id: this.$getCookie('cid'), //分中心id
                      ids: [this.personInfo.id], //体检者ids
                      model: 1, //模板id
                    }
                    // 获取默认打印个数
                    let defaultNumber = 0
                    barcodePrinter().then(({ data }) => {
                      if(data.machineNum){
                        defaultNumber = data.machineNum
                      }else{
                        defaultNumber = 12
                      }
                    }) 
                    // this.$nextTick(() => {
                    this.$refs.printHtml.getReport(
                      dataInfo,
                      query,
                      () => {
                        this.step = 6
                        this.$refs.selfHelp.startCountTime()
                        // 播放语音
                        this.$refs.audio5.currentTime = 0 //从头开始播放
                        this.$refs.audio5.play() //播放
                      },
                      defaultNumber
                    )
                    // })
                     
                  }
                })
              }
            } else {
              this.$alert('存在待缴费金额，请到导检台人工窗口办理业务', '提示', { showClose: false }).then(() => {
                this.$router.push({ name: 'selfHelp' })
              })
            }
          }
        })
        // })
      }
    },
    // 数据重发
    chongfa(patientcode) {
      const msgId = this.$loading({
        lock: true,
        text: '正在提交中间库',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      middleDbInterface({ patientcode })
        .then((res) => {
          msgId.close()
          if (res.code != 200) {
            this.$alert('中间库获取数据异常', '提醒')
          } else {
            this.$modal.msgSuccess('数据重发成功', '提醒')
          }
        })
        .catch(() => {
          msgId.close()
        })
    },
  },
}
</script>

<style lang="scss">
.home.self-verify {
  min-height: 100%;
  display: flex;
  flex-direction: column;

  .container-body {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    border: 32px solid #ffe5e6;
    // background-color: #ffe5e6;

    .show-main {
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      margin-bottom: 0;
      background-color: #fff;

      .table-main-box {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 885px;
        padding: 0 84px;
        .table-tips {
          font-size: 34px;
          color: #ab0004;
        }
      }
      .flex-colum {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      .person-info {
        height: 790px;
        margin-bottom: 24px;
        text-align: center;
        .person-item {
          display: flex;
          align-items: center;
          justify-content: center;
          .el-input--medium .el-input__inner {
            height: 45px;
            line-height: 45px;
            font-size: 20px;
          }

          .title {
            height: 57px;
            line-height: 57px;
            padding: 0 80px;
            margin-right: 13px;
            margin-bottom: 12.5px;
            color: #333;
            font-size: 28px;
            font-weight: 600;
            background: #f5f5f5;
          }
          .content {
            width: 700px;
            height: 57px;
            line-height: 57px;
            padding: 0 48px;
            color: #333;
            font-size: 28px;
            white-space: pre-wrap;
            background: #f5f5f5;
            text-align: left;
            .keyboard {
              position: relative;
              z-index: 3;
              display: flex;
              align-items: center;
              justify-content: space-between;
              flex-wrap: wrap;
              width: 200px;
              background-color: #fff;
              .keyboard-item {
                width: 62px;
                height: 62px;
                line-height: 62px;
                text-align: center;
                cursor: pointer;
                &:hover {
                  background-color: #ffe5e6;
                }
              }
            }
          }
        }
      }
      .print-main {
        display: flex;
        flex-direction: column;
        align-items: center;

        height: 790px;
        img {
          width: 390px;
          height: 390px;
          margin-top: 162px;
          margin-bottom: 45px;
        }
        .print-title {
          font-size: 56px;
        }
      }

      .page-title {
        padding: 56px 0 40px;
        font-size: 56px;
      }
      .page-btn {
        padding: 17px 406px;
        margin: 31px 0 8px;
        font-size: 45px;
        color: #fff;
        background: #ab0004;
        border: none;
        border-radius: 10px;
        cursor: pointer;
        white-space: nowrap;
      }
    }
  }
  .phone-modal {
    position: absolute;
    z-index: 2;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 100%;
  }
}
@media screen and (max-width: 1950px) {
  .home.self-verify {
    display: flex;
    flex-direction: column;
    .container-body {
      flex: 1;
      display: flex;
      flex-direction: column;
      .show-main {
        justify-content: flex-start;

        .table-main-box {
          display: block;
          height: auto;
          padding: 0 84px;
          text-align: center;
          .page-title {
            padding: 40px 0 30px;
            font-size: 40px;
          }
          .table-tips {
            font-size: 24px;
            margin: 20px 0;
          }
        }
        .person-info {
          height: auto;
          .person-item {
            .title {
              margin-right: 9px;
              margin-bottom: 8px;
              font-size: 20px;
            }
            .content {
              width: 700px;
              margin-bottom: 8px;
              font-size: 20px;
            }
          }
        }
        .print-main {
          height: auto;
          img {
            width: 280px;
            height: 280px;
            margin-top: 116px;
            margin-bottom: 45px;
          }
          .print-title {
            font-size: 40px;
          }
        }

        .page-title {
          padding: 20px 8px;
          font-size: 40px;
        }
        .page-btn {
          padding: 17px 406px;
          font-size: 32px;
          margin-top: 10px;
        }
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .home.self-verify {
    .container-body {
      .show-main {
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
  .home.self-verify {
    .container-body {
      .show-main {
        margin-bottom: 50%;
      }
    }
  }
}
@media screen and (min-height: 2000px) {
  .home.self-verify {
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
  .self-verify /deep/ .el-table__header .cell {
    height: auto !important;
    padding: 10px 0;
    font-size: 34px !important;
    font-weight: 600;
    color: #333;
  }
  .self-verify /deep/ .el-table__body .cell {
    height: auto !important;
    padding: 10px 0;
    line-height: 30px;
    font-size: 28px !important;
  }
  .self-verify /deep/ .el-checkbox__input .el-checkbox__inner {
    width: 26px;
    height: 26px;
  }
  .self-verify /deep/ .el-checkbox__input .el-checkbox__inner::after {
    width: 12px;
    height: 16px;
  }
}
.self-verify /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
