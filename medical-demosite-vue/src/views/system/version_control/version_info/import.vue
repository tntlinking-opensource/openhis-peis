<template>
  <el-dialog title="导入人员信息" width="440px" :visible.sync="importDialog" :destroy-on-close="true" :close-on-click-modal="false" append-to-body>
    <el-form label-position="top">
      <el-form-item label="请选择上传文件">
        <file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(1)">导 入</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from '@/utils/request'
export default {
  name: 'editRightImport',
  data() {
    return {
      importDialog: false,
      // 上传组件参数
      uploadData: {
        url: '/sysVersionItem/importItem', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: ['xls', 'xlsx'], //文件类型
        data: {}, //上传时附带的额外参数
      },
      // 加载
      loading: false,
    }
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.importDialog = true
    },
    // 上传文件成功
    uploadFinish(value, err, tempData, formData) {
      if (value == 1) {
        this.$emit('getList')
        this.$modal.msgSuccess('导入人员信息成功', '提醒')
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
