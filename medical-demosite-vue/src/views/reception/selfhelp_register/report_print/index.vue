<template>
  <div class="home report-print">
    <!-- 头部 -->
    <self-help ref="selfHelp"></self-help>
    <div class="container-body">
      <div class="show-main">
        <div style="height: 60%; display: flex; flex-direction: column; justify-content: center">
          <self-progress :step="step" themeColor="#0059FF" :progressItem="progressItem"></self-progress>
          
          <!-- 身份验证步骤增加Tab切换 -->
          <div v-if="step == 1" class="auth-container">
            <div class="auth-tabs">
              <div 
                class="tab-item" 
                :class="{ active: activeTab === 'idcard' }"
                @click="activeTab = 'idcard'"
              >
                身份证验证
              </div>
              <div 
                class="tab-item" 
                :class="{ active: activeTab === 'phone' }"
                @click="activeTab = 'phone'"
              >
                手机号验证
              </div>
            </div>
            
            <!-- 身份证验证 -->
            <card-verify v-if="activeTab === 'idcard'" @verified="handleIdCardVerified"></card-verify>
            
            <!-- 手机号验证 -->
            <div v-else class="phone-auth">
              <div class="input-group">
                <label>身份证号码</label>
                <input 
                  v-model="idcardForm.idcardno" 
                  type="text" 
                  placeholder="请输入身份证号码"
                  maxlength="18"
                  class="auth-input"
                />
              </div>
              
              <div class="input-group">
                <label>手机号码</label>
                <input 
                  v-model="idcardForm.phone" 
                  type="text" 
                  placeholder="请输入手机号码"
                  maxlength="11"
                  class="auth-input"
                />
              </div>
              
              <div class="input-group verification-group">
                <label>验证码</label>
                <div class="verification-input">
                  <input 
                    v-model="idcardForm.verifyCode" 
                    type="text" 
                    placeholder="请输入验证码"
                    maxlength="6"
                    class="auth-input short-input"
                  />
                  <button 
                    class="send-code-btn" 
                    :disabled="isSendingCode || countdown > 0"
                    @click="sendVerificationCode"
                  >
                    {{ countdown > 0 ? `${countdown}s后重发` : '发送验证码' }}
                  </button>
                </div>
              </div>
              
              <button class="confirm-btn" @click="verifyByPhone">确认</button>
            </div>
          </div>
          
          <!-- 打印列表 -->
          <div v-else-if="step == 2" class="print-list-container">
            <div class="print-list-header">
              <h2>可打印报告列表</h2>
            </div>
            <div class="print-list-content">
              <el-table 
                :data="reportList" 
                style="width: 100%" 
                ref="reportTable"
              >
                <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
                <el-table-column prop="idcardno" label="身份证号"  align="center" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="patientcode" label="体检号"  align="center"></el-table-column>
                <el-table-column prop="phone" label="手机号"  align="center" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="printNum" label="打印次数"  align="center"></el-table-column>
                <el-table-column label="操作" width="120" align="center">
                  <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="printReport(scope.row)">打印</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="print-list-actions">
              <button class="action-btn cancel-btn" @click="backToAuth">返回</button>
            </div>
          </div>
          
          <!-- 选择支付方式 -->
          <div v-else-if="step == 3" class="payment-method-container">
            <div class="payment-method-header">
              <h2>请选择支付方式</h2>
            </div>
            <div class="payment-method-content">
              <div class="payment-method-item" @click="selectPaymentMethod('W01')">
                <img src="@/assets/images/selfHelp/wechat-pay.png" alt="微信支付" class="payment-icon" />
                <span class="payment-name">微信支付</span>
              </div>
              <div class="payment-method-item" @click="selectPaymentMethod('A01')">
                <img src="@/assets/images/selfHelp/alipay.png" alt="支付宝" class="payment-icon" />
                <span class="payment-name">支付宝</span>
              </div>
            </div>
            <div class="payment-method-actions">
              <button class="action-btn cancel-btn" @click="cancelPaymentMethod">返回</button>
            </div>
          </div>
          
          <!-- 支付二维码 -->
          <div v-else-if="step == 4" class="payment-container">
            <div class="payment-header">
              <h2>请使用{{ paymentMethodName }}扫描二维码完成支付</h2>
            </div>
            <div class="qrcode-container">
              <div ref="qrcode" class="qrcode-wrapper" id="qrcode-container"></div>
              <div v-if="!qrCodeGenerated && generatingQRCode" class="loading-qrcode">
                二维码生成中...
              </div>
              <div v-if="!qrCodeGenerated && qrCodeError" class="error-qrcode">
                {{ qrCodeError }}
              </div>
            </div>
            <div class="payment-info">
              <p>支付状态: {{ paymentStatus }}</p>
            </div>
            <div class="payment-actions">
              <button class="action-btn cancel-btn" @click="cancelPayment">取消支付</button>
            </div>
          </div>
          
          <!-- 打印成功 -->
          <self-success v-else-if="step == 5" theme="#0059FF" typeName="打印"></self-success>
        </div>
      </div>
    </div>
    <!-- 打印 -->
    <print-html ref="printHtml" :selfRigister="true"></print-html>
    <!-- 语音：进入读身份证页 -->
    <audio controls="controls" hidden src="@/assets/voice/first.mp3" ref="audio"></audio>
    <!-- 语音：读取到信息后 -->
    <audio controls="controls" hidden src="@/assets/voice/second.mp3" ref="audio2"></audio>
  </div>
</template>

<script>
import { 
  encryptionIdCardNo, 
  sendSmsCode, 
  verificationCodeLogin, 
  reportPrintList,
  reportPrintPayment,
  queryTongLian,
  callback 
} from '@/api/reception/selfHelp/self_Border'
import selfHelp from '@/views/reception/selfhelp_register/component/selfHelp'
import selfProgress from '../component/selfProgress'
import selfSuccess from '../component/selfSuccess'
import printHtml from '@/views/reception/registration/print.vue'
import cardVerify from '@/views/reception/selfhelp_register/component/cardVerify'
// 引入二维码生成库
import QRCode from 'qrcodejs2'
import axios from 'axios'

export default {
  name: 'selfQueueManagement',
  components: {
    selfHelp,
    selfProgress,
    selfSuccess,
    printHtml,
    cardVerify,
  },
  data() {
    return {
      // 添加Tab切换控制 
      activeTab: 'idcard', 

      // 手机号验证表单
      idcardForm: {
        idcardno: '',
        phone: '',
        verifyCode: ''
      },

      // 验证码倒计时
      countdown: 0,
      isSendingCode: false,

      // 用户token
      userToken: null,
      
      // 步骤
      step: 1,
      
      // 步骤内容
      progressItem: ['身份验证', '选择打印报告', '选择支付方式', '支付', '完成打印'],
      
      // 报告列表
      reportList: [],
      
      // 选中的报告
      selectedReports: [],
      
      // 支付相关
      qrCodeInstance: null, // 二维码实例
      qrCodeGenerated: false, // 二维码是否已生成
      generatingQRCode: false, // 是否正在生成二维码
      qrCodeError: '', // 二维码错误信息
      paymentAmount: 0,
      paymentStatus: '等待支付',
      paymentTimer: null,
      trxid: '',
      patientcode: '',
      paymentMethod: '', // 支付方式 W01微信 A01支付宝
      paymentMethodName: '', // 支付方式名称
      payParams: null, // 支付参数
    }
  },
  
  mounted() {
    // 初始化读卡器
    this.initCardReader()
    
    // 播放语音
    this.$refs.audio.currentTime = 0
    this.$refs.audio.play()
  },
  
  beforeDestroy() {
    this.clearTimers()
    this.clearQRCode()
  },
  
  methods: {
    // 清除所有定时器
    clearTimers() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
      if (this.countdownTimer) {
        clearInterval(this.countdownTimer)
        this.countdownTimer = null
      }
      if (this.paymentTimer) {
        clearInterval(this.paymentTimer)
        this.paymentTimer = null
      }
    },
    
    // 清除二维码 
    clearQRCode() {
      if (this.qrCodeInstance) {
        this.qrCodeInstance.clear()
        this.qrCodeInstance = null
      }
      this.qrCodeGenerated = false
      this.generatingQRCode = false
      this.qrCodeError = ''
    },
    
    // 初始化读卡器
    initCardReader() {
      // 只有在身份验证步骤且使用身份证验证时才启动读卡器
      if (this.step === 1 && this.activeTab === 'idcard') {
        this.timer = setInterval(() => {
          this.handleReadCard()
        }, 2000)
      }
    },
    
    // 处理身份证读取
    handleReadCard() {
      // 防止重复调用
      if (this.userToken || this.step !== 1 || this.activeTab !== 'idcard') {
        return
      }
      
      // 实际项目中应通过硬件接口读取真实身份证信息 
      // const idcardno = '530381200104210972'
      // const idcardno = '530381200104210972'

          
      //     // 加密身份证号并获取用户token
      //     encryptionIdCardNo({ idcardno }).then((res) => {
      //       if (res.code === 200) {
      //         this.userToken = res.data
      //         this.fetchReportList()
      //         this.clearTimers() 
      //       }
      //     })

      // 通过硬件接口读取真实身份证信息
      axios({
        method: 'get',
        url: 'http://localhost:9001/machine/readCard/onMessage',
      }).then(({ data }) => {
        if (data.code === 200) {
          const idcardno = data.data.idNumber
          
          // 加密身份证号并获取用户token
          encryptionIdCardNo({ idcardno }).then((res) => {
            if (res.code === 200) {
              this.userToken = res.data
              this.fetchReportList()
              this.clearTimers() 
            }
          })
        }
      }).catch(() => {})
    },
    
    // 处理身份证验证完成事件
    handleIdCardVerified(data) {
      // 防止重复调用
      if (this.userToken || this.step !== 1) {
        return
      }
      
      // 使用读取到的身份证信息获取用户token
      encryptionIdCardNo({ idcardno: data.idcardno }).then((res) => {
        if (res.code === 200) {
          this.userToken = res.data
          this.fetchReportList()
        }
      })
    },
    
    // 获取报告列表
    fetchReportList() {
      // 使用userToken获取报告列表
      reportPrintList({ userToken: this.userToken }).then((res) => {
        if (res.code === 200) {
          this.reportList = res.data || []
          this.step = 2
          
          // 播放语音
          this.$refs.audio2.currentTime = 0
          this.$refs.audio2.play()
        }
      })
    },
    
    // 发送验证码
    sendVerificationCode() {
      // 验证手机号
      if (!this.idcardForm.phone) {
        this.$message.warning('请输入手机号')
        return
      }
      
      if (!/^1[3-9]\d{9}$/.test(this.idcardForm.phone)) {
        this.$message.warning('请输入正确的手机号')
        return
      }
      
      // 验证身份证号
      if (!this.idcardForm.idcardno) {
        this.$message.warning('请输入身份证号码')
        return
      }
      
      if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(this.idcardForm.idcardno)) {
        this.$message.warning('请输入正确的身份证号码')
        return
      }
      
      // 发送验证码
      sendSmsCode({ 
        phone: this.idcardForm.phone,
        idcardno: this.idcardForm.idcardno 
      }).then(() => {
        this.isSendingCode = true
        this.$message.success('验证码已发送')
        this.startCountdown()
      }).finally(() => {
        this.isSendingCode = false
      })
    },

    // 开始倒计时
    startCountdown() {
      this.countdown = 60
      this.countdownTimer = setInterval(() => {
        this.countdown--
        if (this.countdown <= 0) {
          clearInterval(this.countdownTimer)
          this.countdownTimer = null
        }
      }, 1000)
    },

    // 手机号验证 
    verifyByPhone() {
      if (!this.idcardForm.idcardno) {
        this.$message.warning('请输入身份证号码')
        return
      }
      
      if (!this.idcardForm.phone) {
        this.$message.warning('请输入手机号')
        return
      }
      
      if (!this.idcardForm.verifyCode) {
        this.$message.warning('请输入验证码')
        return
      }
      
      // 验证码登录
      verificationCodeLogin({
        phone: this.idcardForm.phone,
        idcardno: this.idcardForm.idcardno,
        code: this.idcardForm.verifyCode
      }).then((res) => {
        if (res.code === 200) {
          this.userToken = res.data
          this.fetchReportList()
        } else {
          this.$message.error(res.msg || '验证失败')
        }
      })
    },

  
    
    // 打印单个报告
    printReport(row) {
      // 保存选中的报告
      this.selectedReports = [row]
      // 判断打印次数，如果为0则直接打印，否则需要支付
      if (row.printNum == 0) {
        // 直接打印流程 - 使用现有的processPayment方法，它已经能处理免费情况
        // 免费时,paymentMethod随便传一个W01即可
        this.paymentMethod = 'W01'
        this.processPayment();
      } else {
        // 进入选择支付方式步骤
        this.step = 3
      }
    },
    

    
    // 选择支付方式
    selectPaymentMethod(method) {
      this.paymentMethod = method
      this.paymentMethodName = method === 'W01' ? '微信' : '支付宝'
      // 显示加载提示
      const loading = this.$loading({
        lock: true,
        text: '正在处理支付请求...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      // 调用支付接口
      this.processPayment().finally(() => {
        // 无论成功或失败都关闭loading
        this.$nextTick(() => {
          loading.close()
        })
      })
    },
    
    // 取消选择支付方式
    cancelPaymentMethod() {
      this.step = 2
    },
    
    // 处理支付流程
    processPayment() {
      // 获取第一个报告的体检号（假设所有报告属于同一体检者）
      const patientcode = this.selectedReports[0].patientcode
      
      // 调用支付接口，传入正确的参数
      return reportPrintPayment({ 
        patientcode: patientcode,
        paytype: this.paymentMethod
      }).then((res) => {
        if (res.code === 200) {
          // 保存体检号用于后续操作 
          this.patientcode = patientcode
          
          // 如果返回pdfUrl，说明是免费打印，直接打印
          if (res.data.pdfUrl) {
            this.directPrint(res.data.pdfUrl)
          } else {
            // 否则需要支付，生成二维码
            this.payParams = res.data
            this.trxid = res.data.payNo
            this.paymentStatus = '等待支付'
            
            // 生成二维码
            this.$nextTick(() => {
              this.generateQRCode()
            })
            
            this.step = 4
            
            // 只有在有trxid时才开始轮询支付状态
            if (this.trxid) {
              this.startPaymentPolling()
            }
          }
        } else {
          this.$message.error(res.msg || '支付请求失败')
        }
      })
    },
    
    // 生成支付二维码
    generateQRCode() {
      if (!this.payParams) {
        this.qrCodeError = '支付参数不存在'
        return
      }
      
      // 清除之前的二维码
      this.clearQRCode()
      
      // 设置正在生成二维码状态
      this.generatingQRCode = true
      this.qrCodeError = ''
      
      // 构造支付参数字符串（根据实际需求调整）
      let payString = ''
      
      // 打印调试信息
      console.log('支付参数:', this.payParams)
      
      // 根据支付参数构造支付URL
      if (this.payParams.remarks && this.payParams.remarks.startsWith('http')) {
        // 如果remarks是URL，使用remarks作为支付链接
        payString = this.payParams.remarks
        console.log('使用remarks作为支付链接:', payString)
      }
      
      // 确保支付字符串不为空
      if (!payString) {
        this.qrCodeError = '无法生成支付二维码：支付参数为空'
        console.error('无法生成支付二维码：支付参数为空')
        this.generatingQRCode = false
        return
      }
      
      // 确保支付字符串是有效的URL格式
      if (!payString.startsWith('http') && !payString.startsWith('https')) {
        this.qrCodeError = '支付链接格式不正确'
        console.error('支付链接格式不正确:', payString)
        this.generatingQRCode = false
        return
      }
      
      // 生成二维码
      try {
        // 使用 nextTick 确保 DOM 更新完成
        this.$nextTick(() => {
          // 尝试通过 ref 获取元素
          let qrContainer = this.$refs.qrcode
          
          // 如果 ref 不存在，尝试通过 ID 获取元素
          if (!qrContainer) {
            qrContainer = document.getElementById('qrcode-container')
            console.log('通过ID获取二维码容器:', qrContainer)
          }
          
          // 如果仍然不存在，记录错误
          if (!qrContainer) {
            this.qrCodeError = '二维码容器不存在'
            console.error('二维码容器不存在')
            this.generatingQRCode = false
            return
          }
          
          // 清空容器
          qrContainer.innerHTML = ''
          
          console.log('开始生成二维码，文本内容:', payString)
          
          this.qrCodeInstance = new QRCode(qrContainer, {
            text: payString,
            width: 300,
            height: 300,
            colorDark: '#000000',
            colorLight: '#ffffff',
            correctLevel: QRCode.CorrectLevel.H
          })
          
          console.log('二维码生成完成')
          this.qrCodeGenerated = true
          this.generatingQRCode = false
        })
      } catch (err) {
        console.error('二维码生成失败:', err)
        this.qrCodeError = '二维码生成失败: ' + (err.message || '未知错误')
        this.generatingQRCode = false
      }
    },
    
    // 开始轮询支付状态
    startPaymentPolling() {
      // 只有在有trxid时才开始轮询
      if (!this.trxid) {
        console.warn('无法开始支付轮询：trxid为空')
        return
      }
      
      this.paymentTimer = setInterval(() => {
        // 使用正确的参数调用查询接口
        queryTongLian({ trxid: this.trxid }).then((res) => {
          if (res.code === 200 && res.data.trxstatus === '0000') {
            // 支付成功，调用回调接口
            this.handlePaymentSuccess()
          } else if (res.code === 200 && res.data.status === 'FAILED') {
            // 支付失败
            this.paymentStatus = '支付失败'
            clearInterval(this.paymentTimer)
            this.paymentTimer = null
          } else if (res.code === 200) {
            // 其他状态，继续轮询
            this.paymentStatus = '等待支付'
          }
        }).catch(err => {
          console.error('支付状态查询失败:', err)
        })
      }, 3000) // 每3秒查询一次
    },
    
    // 处理支付成功 
    handlePaymentSuccess() {
      clearInterval(this.paymentTimer)
      this.paymentTimer = null
      this.paymentStatus = '支付成功'
      
      // 使用正确的参数调用回调接口
      callback({ 
        id: this.selectedReports[0].id,
        trxid: this.trxid 
      }).then((res) => {
        if (res.code == 200) {
          this.directPrint(res.data)
        } else {
          this.$message.error('支付回调处理失败')
        }
      })
    },
    
    // 直接打印
    directPrint(pdfUrl) {
      this.step = 5
      this.$refs.selfHelp.startCountTime()
      
      try {
        // 直接在新窗口中打开PDF文件，让用户手动打印
        const newWindow = window.open(pdfUrl, '_blank')
        
        if (!newWindow) {
          // 如果弹窗被阻止，提示用户手动操作
          this.$alert(`打印被浏览器阻止，请点击下方按钮下载或在新窗口中打开PDF文件进行打印。`, '打印提示', {
            confirmButtonText: '打开PDF',
            callback: () => {
              // 创建一个临时的下载链接
              const link = document.createElement('a')
              link.href = pdfUrl
              link.target = '_blank'
              link.click()
            }
          })
        } else {
          // 给用户一些提示信息
          this.$message.info('PDF文件已在新窗口中打开，请使用浏览器的打印功能进行打印')
        }
      } catch (error) {
        console.error('打开PDF时出错:', error)
        // 备用方案：创建下载链接
        this.$alert(`请点击下方按钮下载PDF文件并手动打印。`, '打印提示', {
          confirmButtonText: '下载PDF',
          callback: () => {
            const link = document.createElement('a')
            link.href = pdfUrl
            link.download = '体检报告.pdf'
            link.target = '_blank'
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
          }
        })
      }
    },

    
    // 取消支付
    cancelPayment() {
      this.clearTimers()
      this.clearQRCode()
      this.step = 3 // 返回到选择支付方式
      this.paymentAmount = 0
      this.paymentStatus = '等待支付'
      this.trxid = ''
      this.payParams = null
    },
    
    // 返回到身份验证步骤
    backToAuth() {
      // 重置相关数据
      this.userToken = null
      this.reportList = []
      this.selectedReports = []
      this.paymentMethod = ''
      this.paymentMethodName = ''
      this.clearQRCode()
      this.payParams = null
      
      // 设置步骤为1
      this.step = 1
      
      // 重新初始化读卡器
      this.$nextTick(() => {
        this.initCardReader()
      })
      
      // 重新播放语音
      this.$refs.audio.currentTime = 0
      this.$refs.audio.play()
    },
  },
  
  watch: {
    // 监听步骤变化
    step(newVal) {
      // 如果切换到身份验证步骤，重新初始化读卡器
      if (newVal === 1) {
        this.$nextTick(() => {
          this.initCardReader()
        })
      }
    },
    
    // 监听验证方式切换
    activeTab(newVal) {
      // 切换验证方式时清除之前的定时器
      this.clearTimers()
      
      // 如果切换到身份证验证，重新初始化读卡器
      if (newVal === 'idcard' && this.step === 1) {
        this.$nextTick(() => {
          this.initCardReader()
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.home.report-print {
  min-height: 100%;
  display: flex;
  flex-direction: column;

  .container-body {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    border: 32px solid #d1e1ff;
    background-color: #fff;
    
    .show-main {
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      margin-bottom: 0;

      .auth-container {
        width: 100%;
        max-width: 800px;
        margin: 0 auto;

        .auth-tabs {
          display: flex;
          justify-content: center;
          margin-bottom: 40px;
          
          .tab-item {
            padding: 15px 40px;
            font-size: 28px;
            cursor: pointer;
            border-bottom: 4px solid transparent;
            
            &.active {
              color: #0059ff;
              border-bottom: 4px solid #0059ff;
              font-weight: bold;
            }
          }
        }

        .phone-auth {
          .input-group {
            margin-bottom: 30px;
            
            label {
              display: block;
              font-size: 24px;
              margin-bottom: 10px;
              color: #333;
            }
            
            .auth-input {
              width: 100%;
              height: 60px;
              padding: 0 20px;
              font-size: 24px;
              border: 2px solid #ddd;
              border-radius: 8px;
              
              &:focus {
                outline: none;
                border-color: #0059ff;
              }
            }
          }
          
          .verification-group {
            .verification-input {
              display: flex;
              gap: 20px;
              
              .short-input {
                flex: 1;
              }
              
              .send-code-btn {
                width: 200px;
                height: 60px;
                font-size: 24px;
                background: #0059ff;
                color: white;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                
                &:disabled {
                  background: #ccc;
                  cursor: not-allowed;
                }
              }
            }
          }
          
          .confirm-btn {
            width: 100%;
            height: 70px;
            font-size: 28px;
            background: #0059ff;
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            margin-top: 20px;
          }
        }
      }

      .print-list-container {
        width: 100%;
        max-width: 1400px;
        margin: 0 auto;
        padding: 20px;
        
        .print-list-header {
          text-align: center;
          margin-bottom: 30px;
          
          h2 {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
          }
          
          p {
            font-size: 20px;
            color: #666;
          }
        }
        
        .print-list-content {
          margin-bottom: 30px;
        }
        
        .print-list-actions {
          display: flex;
          justify-content: center;
          gap: 30px;
          
          .action-btn {
            padding: 15px 40px;
            font-size: 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
            
            &.cancel-btn {
              background-color: #f5f5f5;
              color: #666;
              
              &:hover {
                background-color: #e0e0e0;
              }
            }
            
            &.print-btn {
              background-color: #0059ff;
              color: white;
              
              &:disabled {
                background-color: #ccc;
                cursor: not-allowed;
              }
              
              &:hover:not(:disabled) {
                background-color: #003db8;
              }
            }
          }
        }
      }
      
      // 选择支付方式样式
      .payment-method-container {
        width: 100%;
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        text-align: center;
        
        .payment-method-header {
          margin-bottom: 30px;
          
          h2 {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
          }
          
          p {
            font-size: 20px;
            color: #666;
          }
        }
        
        .payment-method-content {
          display: flex;
          justify-content: center;
          gap: 50px;
          margin-bottom: 30px;
          
          .payment-method-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            border: 2px solid #ddd;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s;
            
            &:hover {
              border-color: #0059ff;
              box-shadow: 0 0 10px rgba(0, 89, 255, 0.2);
            }
            
            .payment-icon {
              width: 100px;
              height: 100px;
              margin-bottom: 15px;
            }
            
            .payment-name {
              font-size: 24px;
              color: #333;
            }
          }
        }
        
        .payment-method-actions {
          .action-btn {
            padding: 15px 40px;
            font-size: 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
            
            &.cancel-btn {
              background-color: #f5f5f5;
              color: #666;
              
              &:hover {
                background-color: #e0e0e0;
              }
            }
          }
        }
      }
      
      // 支付二维码样式
      .payment-container {
        width: 100%;
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        text-align: center;
        
        .payment-header {
          margin-bottom: 30px;
          
          h2 {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
          }
          
          p {
            font-size: 20px;
            color: #666;
          }
        }
        
        .qrcode-container {
          margin-bottom: 30px;
          display: flex;
          justify-content: center;
          
          .qrcode-wrapper {
            padding: 20px;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            min-height: 340px;
            min-width: 340px;
            display: flex;
            align-items: center;
            justify-content: center;
          }
          
          .loading-qrcode, .error-qrcode {
            font-size: 24px;
            color: #666;
            padding: 50px;
            text-align: center;
          }
          
          .error-qrcode {
            color: #ff0000;
          }
        }
        
        .payment-info {
          margin-bottom: 30px;
          
          p {
            font-size: 20px;
            margin: 10px 0;
          }
        }
        
        .payment-actions {
          .action-btn {
            padding: 15px 40px;
            font-size: 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
            
            &.cancel-btn {
              background-color: #f5f5f5;
              color: #666;
              
              &:hover {
                background-color: #e0e0e0;
              }
            }
          }
        }
      }
    }
  }
}

@media screen and (max-width: 1950px) {
  .home.report-print {
    .container-body {
      .show-main {
        justify-content: flex-start;
        
        .print-list-container {
          padding: 10px;
        }
      }
    }
  }
}

@media screen and (max-width: 1200px) {
  .home.report-print {
    .container-body {
      .show-main {
        .print-list-container {
          padding: 10px;
          
          .print-list-actions {
            .action-btn {
              padding: 12px 30px;
              font-size: 20px;
            }
          }
        }
        
        .payment-method-container {
          .payment-method-content {
            flex-direction: column;
            align-items: center;
            gap: 20px;
          }
        }
        
        .payment-container {
          .qrcode-wrapper {
            min-height: 240px;
            min-width: 240px;
            
            :deep(canvas) {
              width: 200px !important;
              height: 200px !important;
            }
          }
        }
      }
    }
  }
}
</style>

<style scoped>
.report-print /deep/ .el-table th {
  background-color: #f5f7fa;
  font-size: 18px;
  font-weight: bold;
}

.report-print /deep/ .el-table td {
  font-size: 16px;
}

.report-print /deep/ .el-table .el-button--mini {
  padding: 7px 10px;
}
</style>