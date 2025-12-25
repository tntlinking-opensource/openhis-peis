<template>
  <el-dialog title="导入销售目标" width="440px" :visible.sync="importDialog" :destroy-on-close="true" :close-on-click-modal="false" append-to-body>
    <el-form v-loading="loading" element-loading-text="导入中">
      <el-form label-position="top">
        <el-form-item label="请选择上传文件">
          <file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
        </el-form-item>
      </el-form>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(1)">导 入</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      importDialog: false,
      // 上传组件参数
      uploadData: {
        url: 'sell/leadertarget/importYearTarget', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: ['xls', 'xlsx'], //文件类型
        data: {
          year: '',
        }, //上传时附带的额外参数
      },
      // 加载
      loading: false,
    }
  },
  methods: {
    // 打开弹窗
    showDialog(year) {
      this.uploadData.data.year = year
      this.importDialog = true
    },
    // 上传文件成功
    uploadFinish(value) {
      if (value == 1) {
        this.$emit('getList')
        this.$modal.msgSuccess('导入销售目标成功', '提醒')
        this.importDialog = false
      }
      this.loading = false
    },
    // 提交
    submitForm() {
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.loading = true
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 取消
    cancel() {
      this.importDialog = false
    },
  },
}
</script>
