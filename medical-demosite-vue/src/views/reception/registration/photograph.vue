<template>
  <el-dialog title="拍照" :visible.sync="open" width="648px" append-to-body style="overflow: hidden" :close-on-click-modal="false" @close="cancel">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-camera" :disabled="imgSrc != ''" @click="takePhotos">拍照</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-refresh-right" :disabled="!imgSrc" @click="handleShow">重拍</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="imgSrc != ''" @click="setCurrentDevice" v-if="videoArr.length > 1">切换</el-button>
      </el-col>
    </el-row>
    <div class="camera_outer" :style="`width: ${videoWidth}px; height: ${videoHeight}px`">
      <vue-cropper ref="cropper" :img="imgSrc" :info="true" :autoCrop="options.autoCrop" :autoCropWidth="options.autoCropWidth" :autoCropHeight="options.autoCropHeight" :fixedBox="options.fixedBox" v-show="imgSrc" />
      <canvas ref="canvas" :width="videoWidth" :height="videoHeight" v-show="false"></canvas>
      <video ref="video" :width="videoWidth" :height="videoHeight" autoplay v-show="!imgSrc"></video>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="takePhotos">拍 照</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { VueCropper } from 'vue-cropper'
export default {
  components: { VueCropper },
  data() {
    return {
      // 打开弹窗
      open: false,
      // 拍照参数
      videoWidth: 480,
      videoHeight: 480,
      canvasCamera: null,
      contextCamera: null,
      videoCamera: null,
      // 所有的摄像头
      videoArr: [],
      // 当前使用的摄像头
      modelSel: '',
      // 图片链接
      imgSrc: '',
      // 裁剪参数
      options: {
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 520, // 默认生成截图框宽度
        autoCropHeight: 520, // 默认生成截图框高度
        fixedBox: false, // 是否固定截图框大小
      },
    }
  },
  created() {
    this.getDevice()
  },
  methods: {
    // 打开弹窗
    handleShow() {
      this.open = true
      this.imgSrc = ''
      this.$nextTick(() => {
        this.callCamera()
      })
    },
    // 取消按钮
    cancel() {
      try {
        this.closeCamera()
        this.open = false
      } catch (err) {
        this.open = false
      }
    },
    // 打开摄像头
    callCamera() {
      this.canvasCamera = this.$refs['canvas']
      this.contextCamera = this.canvasCamera.getContext('2d')
      this.videoCamera = this.$refs['video']
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {}
      }
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = (constraints) => {
          // 首先获取现存的getUserMedia
          var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
          // 有些浏览器不支持，会返回错误信息
          // 保持接口一致
          if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
          }
          // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
          return new Promise((resolve, reject) => {
            getUserMedia.call(navigator, constraints, resolve, reject)
          })
        }
      }
      this.getUserMedia()
    },
    // 设置摄像头
    getUserMedia() {
      var constraints = {
        audio: false,
        video: {
          width: this.videoWidth,
          height: this.videoHeight,
          transform: 'scaleX(-1)',
        },
      }
      if (this.modelSel) constraints.video.deviceId = this.modelSel
      navigator.mediaDevices
        .getUserMedia(constraints)
        .then((stream) => {
          // 新的浏览器使用srcObject。旧的浏览器可能没有srcObject
          if ('srcObject' in this.videoCamera) {
            this.videoCamera.srcObject = stream
          } else {
            // 避免在新的浏览器中使用它，因为它正在被弃用。
            this.videoCamera.src = window.URL.createObjectURL(stream)
          }
          this.videoCamera.onloadedmetadata = () => {
            this.videoCamera.play()
          }
        })
        .catch((err) => {
          this.$modal.alertWarning('没有开启摄像头权限或浏览器版本不兼容', '提示')
        })
    },
    // 获取全部摄像头
    getDevice() {
      if (navigator && navigator.mediaDevices) {
        navigator.mediaDevices
          .enumerateDevices()
          .then((devices) => {
            this.modelSel = ''
            this.videoArr = []
            devices.forEach((device) => {
              //音频是audioautput  摄像头videoinput
              if (device.kind == 'videoinput') {
                this.videoArr.push(device.deviceId)
              }
            })
          })
          .catch(function (err) {
            layer.msg(err.name + ': ' + err.message)
          })
      }
    },
    // 拍照
    takePhotos() {
      // 点击，canvas画图
      this.contextCamera.drawImage(this.videoCamera, 0, 0, this.videoWidth, this.videoHeight)
      // 获取图片base64链接
      this.imgSrc = this.canvasCamera.toDataURL('image/png', 0.7)
      this.closeCamera()
      this.$nextTick(() => {
        setTimeout(() => {
          this.submitForm()
        }, 700)
      })
    },
    // 重拍
    resetCamera() {
      this.imgSrc = ''
      this.callCamera()
    },
    // 关闭摄像头
    closeCamera() {
      this.videoCamera.srcObject.getTracks()[0].stop()
    },
    // 切换摄像头
    setCurrentDevice() {
      if (this.modelSel) {
        let index = this.videoArr.findIndex((item) => {
          if (this.modelSel == item) {
            return true
          }
        })
        if (index == this.videoArr.length - 1) {
          this.modelSel = this.videoArr[0]
        } else {
          this.modelSel = this.videoArr[index + 1]
        }
      } else {
        this.modelSel = this.videoArr[1]
      }
      this.getUserMedia()
    },
    // 提交按钮
    submitForm() {
      if (!this.imgSrc) {
        this.$modal.alertWarning('照片不能为空！')
      } else {
        this.$refs.cropper.getCropBlob((res) => {
          this.$emit('setPicture', res)
          this.cancel()
        })
      }
    },
  },
}
</script>

<style scoped>
.camera_outer {
  margin: 0 auto;
}
</style>
