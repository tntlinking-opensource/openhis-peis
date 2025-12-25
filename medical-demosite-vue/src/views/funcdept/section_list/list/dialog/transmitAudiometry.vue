<!-- 电测听-上传 开发人：麦沃德科技暴雨 -->
<template>
  <el-dialog title="上传" :visible.sync="open" width="464px" append-to-body :close-on-click-modal="false" :destroy-on-close="true">
    <el-form :model="form" ref="form" size="small" :inline="true" :rules="rules" hide-required-asterisk label-width="70px" v-loading="loading">
      <el-form-item label="科室名称" style="margin-right: 0" prop="id">
        <el-select v-model="form.id" placeholder="请选择科室" style="width: 350px">
          <el-option v-for="item in ksOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="上传结果" style="margin-right: 0">
          <el-input
            :value="formattedUploadResult"
            readonly
            type="textarea"
            :rows="5"
            style="width: 420px"
          ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="submitInfo">上传数据</el-button>
      <el-button class="section-btn-style1" @click="submitImg">上传图片</el-button>
      <el-button class="section-btn-style2" @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getKsList, upLoadFilePic, uploadData } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 打开弹窗
      open: false,
      // 数据
      form: {},
      // 上传结果 
      uploadResult: '体检号:290230488064不存在，上传失败 /r/n体检号:290230488064不存在， /r/n上传失败体检号:290230488064不存在，上传失败',
      // 科室列表
      ksOptions: [],
      // 表单校验
      rules: {
        id: [{ required: true, message: '科室不能为空', trigger: 'change' }],
      },
      // 加载中
      loading: false,
    }
  },
  computed: {
    formattedUploadResult() {
      // 将 /r/n 替换为换行符 \n
      return this.uploadResult.replace(/\/r\/n/g, '\n');
    }
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.reset()
      this.getList()
      this.open = true
    },
    // 获取科室列表 
    getList() {
      getKsList(this.$route.meta.ksID).then((res) => {
        this.ksOptions = res.data
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        ksId: this.$route.meta.ksID,
        type: this.$route.meta.dataType == 10 ? 2 : 1,
      }
      this.uploadResult = ''
      this.resetForm('form')
    },
    // 上传数据
    submitInfo() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.uploadResult = ''
          this.loading = true
          uploadData(this.form)
            .then((res) => {
              this.uploadResult += res.data
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.uploadResult += '上传异常!\n'
              this.loading = false
            })
        }
      })
    },
    // 上传图片
    submitImg() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.uploadResult = ''
          this.loading = true
          upLoadFilePic(this.form)
            .then((res) => {
              this.uploadResult += res.data
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.uploadResult += '上传异常!\n'
              this.loading = false
            })
        }
      })
    },
    // 取消
    cancel() {
      this.open = false
    },
  },
}
</script>
