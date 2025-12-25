<template>
  <el-dialog title="导入人员信息" width="440px" :visible.sync="importDialog" :destroy-on-close="true" :close-on-click-modal="false" append-to-body>
    <el-form ref="form" :model="form" v-loading="loading" element-loading-text="导入中">
      <el-form-item label="模板类型" prop="modelType">
        <el-select v-model="form.modelType" placeholder="请选择" style="width: 330px">
          <el-option label="健康模板" :value="3"> </el-option>
          <el-option label="职业模板" :value="4"> </el-option>
        </el-select>
      </el-form-item>
      <el-form :model="form" label-position="top">
        <el-form-item label="请选择上传文件">
          <file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
        </el-form-item>
      </el-form>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" :loading="importing" @click="submitForm(1)">导 入</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from '@/utils/request2'
export default {
  name: 'editRightImport',
  data() {
    return {
      importDialog: false,
      form: {
        id: '',
        modelType: 3,
      },
      // 上传组件参数
      uploadData: {
        url: '/reception/order/importPatientBatch', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: ['xls', 'xlsx'], //文件类型
        data: {}, //上传时附带的额外参数
      },
      // 加载
      loading: false,
      // 导入状态
      importing: false
    }
  },
  methods: {
    // 打开弹窗
    showDialog(id) {
      this.form.id = id
      this.importDialog = true
    },
    // 上传文件成功
    uploadFinish(value, err, tempData, formData) {
      if (value == 1) {
        this.$emit('handleFreshen')
        this.$modal.msgSuccess('导入人员信息成功', '提醒')
        this.importDialog = false
      } else {
        // 备单导入名单特殊处理
        request({
          url: '/reception/order/checkList',
          method: 'post',
          responseType: 'blob',
          data: formData,
        }).then((res) => {
          let link = document.createElement('a')
          // type就是blob的type,是MIME类型的，可以自己查看MIME类型都有哪些
          let blogw = new Blob([res], { type: 'application/vnd.ms-excel;charset=utf-8' })
          let objectUrl = window.URL.createObjectURL(blogw) //创建一个新的url对象
          link.href = objectUrl
          let file_name = `名单校验结果.xlsx`
          link.download = file_name //  下载的时候自定义的文件名
          link.click()
          window.URL.revokeObjectURL(objectUrl) //为了更好地性能和内存使用状况，应该在适当的时候释放url.
        })
      }
      this.loading = false
      this.importing = false
    },
    // 提交
    submitForm() {
      // 防止重复点击
      if (this.importing) {
        return
      }
      
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.importing = true
        this.loading = true
        this.uploadData.data = this.form
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 取消
    cancel() {
      // 取消时重置导入状态
      this.importing = false
      this.importDialog = false
    },
  },
}
</script>