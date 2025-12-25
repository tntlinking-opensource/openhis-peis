<!-- 上传图片 开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="上传图片" :visible.sync="open" width="400px" append-to-body :close-on-click-modal="false" :destroy-on-close="true">
    <el-form :inline="true" v-loading="loading" element-loading-text="上传中" label-position="top">
      <el-form-item label="选择文件" style="width: 100%">
        <image-upload ref="uploadImage" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></image-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="submitForm()">上 传</el-button>
      <el-button class="section-btn-style3" @click="reset()">重 置</el-button>
      <el-button class="section-btn-style2" @click="cancel()">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import imageUpload from '@/components/ImageUpload2/index.vue'
export default {
  components: {
    imageUpload,
  },
  data() {
    return {
      // 打开弹窗
      open: false,
      // 上传组件参数
      uploadData: {
        url: '/abteilung/image/uploads', //文件上传地址
        multiple: true, //是否可以上传多个
        data: {}, //上传时附带的额外参数
      },
      // 加载
      loading: false,
    }
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.uploadData.data.ksID = this.$route.meta.ksID
      this.open = true
      this.loading = false
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        if (res.data.urlList.length == 0) {
          this.$alert(res.data.resultMsg, '提醒', {
            dangerouslyUseHTMLString: true,
          })
        } else {
          this.$modal.alertSuccess('上传成功', '提醒')
        }
        this.reset()
      }
      this.loading = false
    },
    // 提交
    submitForm() {
      var msg = this.$refs.uploadImage.isUpload()
      if (msg === true) {
        this.loading = true
        this.$refs.uploadImage.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 取消
    cancel() {
      this.open = false
    },
    // 重置
    reset() {
      this.$refs.uploadImage.resetUpload()
    },
  },
}
</script>
