<!-- pacs--上传 麦沃德科技 开发人 清风 -->
<template>
  <el-dialog title="上传文件" :visible.sync="open" width="476px" append-to-body class="openDialog" :close-on-click-modal="false" destroy-on-close @close="cancel">
    <span style="margin-right: 12px;font-size: 16px;">是否为当前体检项目</span>
    <el-radio v-model="isCurrent" :label="true">是</el-radio>
    <el-radio v-model="isCurrent" :label="false">否</el-radio>
    <div style="margin-bottom: 8px"></div>
    <file-upload ref="fileUpload" uploadModel="2" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">上 传</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import fileUpload from '@/components/FileUpload2/index.vue'
export default {
  components: { fileUpload },
  props: ['ksID'],
  data() {
    return {
      open: false,
      // 加载中
      loading: false,
      // 上传组件参数
      uploadData: {
        url: '/pacs/sysytem/pacsAbteilungs/uploadDicom', //文件上传地址
        multiple: true, //是否可以上传多个
        fileType: ['dcm','DCM'], //文件类型
        data: {
          //上传时附带的额外参数
          feeitemId: undefined,
        },
      },
      // 是否为当前体检项目
      isCurrent: true,
      // 项目id
      feeitemId: '',
    }
  },
  methods: {
    // 打开对话框
    uploadWindow(feeitemId) {
      this.open = true
      this.feeitemId = feeitemId
    },
    // 上传完成
    uploadFinish(value) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
        this.open = false
        this.$emit('getItemPic')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      // this.getList();
    },
    // 点击提交
    submitForm() {
      if (this.isCurrent) {
        this.uploadData.data.feeitemId = this.feeitemId
      } else {
        this.uploadData.data.feeitemId = ''
      }
      var msg = this.$refs.fileUpload.isUpload()
      this.$nextTick(() => {
        this.loading = this.$loading({
          lock: true,
          text: '加载中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        if (msg === true) {
          this.$refs.fileUpload.handelUpload()
        } else {
          this.$alert(msg, '提示')
          this.loading.close()
        }
      })
    },
    cancel() {
      this.$refs.fileUpload.resetUpload()
      this.open = false
    },
  },
}
</script>

<style></style>
