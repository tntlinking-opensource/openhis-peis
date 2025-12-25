<!-- 上传word 开发人：麦沃德科技 予安 -->
<template>
  <el-dialog title="上传Word" :visible.sync="open" width="600px" append-to-body>
    <file-upload ref="uploadFile" :uploadData="uploadData" :uploadModel="3" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="uploadFile">上 传</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      // 打开对话框
      open: false,
      // 加载中
      loading: false,
      // 上传组件参数
      uploadData: {
        url: '/report/groupReport/uploadWord', //文件上传地址
        fileType: ['docx'], //文件类型
        data: {}, //上传时附带的额外参数
      },
      // 列表索引
      tableIndex: 0,
      // 删除时保存的临时数据
      tempList: [],
      selection: [],
    }
  },
  methods: {
    //打开按钮
    handleShow(id) {
      this.uploadData.data.id = id
      this.open = true
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        this.loading.close()
        this.$modal.msgSuccess('上传成功', '提醒')
        this.$emit('getList')
        this.open = false
      }
    },
    // 提交
    uploadFile() {
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.loading = this.$loading({
          lock: true,
          text: '上传中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
  },
}
</script>
