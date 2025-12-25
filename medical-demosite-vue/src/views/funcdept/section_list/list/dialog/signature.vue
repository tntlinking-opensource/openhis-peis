<template>
  <el-dialog ref="signature" :visible.sync="open" width="600px" append-to-body title="签名" @close="endSign" :close-on-click-modal="false">
    <div class="signature-box" v-if="!errorText">
      <img :src="signResult" v-if="signResult" />
    </div>
    <div class="signature-box signature-error" v-else>
      <span>{{ errorText }}</span>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="handleSave">确 定</el-button>
      <el-button class="section-btn-style2" @click="clearSign">清除笔迹</el-button>
      <el-button class="section-btn-style2" @click="beginSign">重新签名</el-button>
      <el-button class="section-btn-style2" @click="localUpload">本地上传</el-button>
      <el-button class="section-btn-style2" @click="cancel">取 消</el-button>
    </div>
    <!-- 上传图片对话框 -->
    <el-dialog title="上传图片" class="image-upload" :visible.sync="showUpload" :destroy-on-close="true" width="600px" append-to-body :close-on-click-modal="false">
      <el-form v-loading="uploadLoading" element-loading-text="上传中">
        <el-form-item>
          <image-upload ref="uploadImage" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></image-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">上 传</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
var ws
export default {
  data() {
    return {
      open: false,
      form: {},
      plugin: null,
      errorText: '',
      signResult: '',
      // 签名位置，可能同一页面不同参数调用
      signPosition: undefined,
      // 打开本地上传
      showUpload: false,
      // 上传加载
      uploadLoading: false,
      // 上传组件参数
      uploadData: {
        url: '/common/upload', //图片上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //图片上传个数限制
        data: {}, //上传时附带的额外参数
        picture: '',
      },
    }
  },
  methods: {
    // 打开弹窗
    signature(signPosition) {
      this.open = true
      this.initPlugin()
      this.signResult = ''
      this.signPosition = signPosition
    },
    // 初始化手写板
    initPlugin() {
      this.errorText = ''
      if ('WebSocket' in window) {
        ws = new WebSocket('ws://127.0.0.1:29999')
        ws.onopen = (res) => {
          this.beginSign()
        }
        ws.onmessage = this.OnMessage
        ws.onerror = (event) => {
          console.error('error' + event)
        }
      } else {
        this.errorText = '您的浏览器不支持 WebSocket!'
      }
    },
    OnMessage(msg) {
      try {
        var obj = JSON.parse(msg.data)
        var msgID = obj.msgID
        var HWPenSign = obj.HWPenSign
        if (msgID == '0') {
          if (HWPenSign == 'HWGetStatus') {
            var DeviceStatus = obj.DeviceStatus
            if (DeviceStatus == 1) {
              this.errorText = '设备未连接'
            }
          } else if (HWPenSign == 'HWGetSign') {
            this.signResult = obj.message
          }
        } else {
          this.errorText = '该插件不受支持'
        }
      } catch (e) {}
    },
    // 清除笔迹
    clearSign() {
      this.signResult = ''
    },
    // 开始签名
    beginSign() {
      ws.send(
        '{"HWPenSign":"HWInitialize","nLogo":"签名" ' +
          ',"width":"600","height":"300", "nOrgX":"100" ' +
          ',"backcolor":"ffffff"' +
          ',"fingerFap":"0","key":"7B68AA9F27255B17FCB7B14BED5514D4" ' +
          ',"setText":[{"text_x":20,"text_y":30,"text_t":"ESP500Test"},{"text_x":20,"text_y":50,"text_t":"TextTest"},{"text_x":20,"text_y":70,"text_t":"helloworld"}]}'
      )
    },
    // 结束签名
    endSign() {
      ws.close()
    },
    // 本地上传
    localUpload() {
      this.showUpload = true
    },
    // 取消上传图片
    cancelUpload() {
      this.showUpload = false
    },
    // 确认上传图片
    submitUpload() {
      var msg = this.$refs.uploadImage.isUpload()
      if (msg === true) {
        this.uploadLoading = true
        this.$refs.uploadImage.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 上传图片成功
    uploadFinish(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.uploadLoading = false
      this.showUpload = false
      this.open = false
      this.signResult = res.data
      this.$emit('saveSignPath2', this.signResult, this.signPosition)
    },
    // 提交
    handleSave() {
      if (this.signResult) {
        this.$emit('saveSignPath', this.signResult, this.signPosition)
        this.cancel()
      } else {
        this.$modal.msgWarning('请完成签名后再进行上传')
      }
    },
    // 关闭弹窗
    cancel() {
      this.endSign()
      this.open = false
    },
  },
}
</script>

<style lang="scss" scoped>
.signature-box {
  margin: 0 auto;
  width: 502px;
  height: 302px;
  border: 1px solid #555;

  img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
}

.signature-error {
  font-size: 26px;
  line-height: 302px;
  text-align: center;
}
</style>
