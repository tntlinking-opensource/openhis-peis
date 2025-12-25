<template>
  <el-dialog title="科室咨询" :visible.sync="open" width="450px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" v-loading="loading" label-width="90px" :inline="true" hide-required-asterisk>
      <el-form-item label="咨询内容" prop="consultContent">
        <el-input v-model="form.consultContent" placeholder="请输入" style="width: 300px" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item label="电子签名">
        <el-button class="section-btn-style2" @click="signName" icon="el-icon-edit">签名</el-button>
      </el-form-item>
      <el-form-item style="width: 100%">
        <img :src="signResult" v-if="signResult" style="width: 100%; height: auto; max-width: 300px" />
        <img :src="imgPath + form.signPath" v-else-if="form.signPath" style="width: 100%; height: auto; max-width: 300px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="handleSave">保 存</el-button>
      <el-button class="section-btn-style3" @click="showDialog(form.patientcode)">重 置</el-button>
      <el-button class="section-btn-style2" @click="cancel">取 消</el-button>
    </div>
    <signature ref="signature" @saveSignPath="saveSignPath"></signature>
  </el-dialog>
</template>

<script>
import { saveOrUpdateDep, divisionDep, uploadFile } from '@/api/funcdept/section_list/dialog.js'
import signature from '@/views/funcdept/section_list/list/dialog/signature'
import Cookies from 'js-cookie'
export default {
  components: { signature },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 弹窗
      open: false,
      // 数据
      form: {},
      // 校验规则
      rules: [],
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 签名图片
      signResult: '',
    }
  },
  methods: {
    showDialog(patientcode) {
      this.open = true
      this.reset()
      this.form.patientcode = patientcode
      this.getDetails()
    },
    // 获取详情
    getDetails() {
      this.loading = true
      divisionDep({ depId: this.form.depId, patientcode: this.form.patientcode }).then((res) => {
        if (res.data) this.form = res.data
        this.loading = false
      })
    },
    // 电子签名
    signName() {
      this.$refs.signature.signature()
    },
    // 签名返回值
    saveSignPath(url) {
      this.signResult = url
    },
    // 保存
    handleSave() {
      this.loading = true
      var form = this.form
      if (this.signResult) {
        this.uploadSign(form)
      } else {
        this.saveOrUpdateDep(form)
      }
    },
    // 上传签名图片
    uploadSign(form) {
      var bytes = window.atob(this.signResult.split(',')[1])
      var array = []
      for (var i = 0; i < bytes.length; i++) {
        array.push(bytes.charCodeAt(i))
      }
      var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
      var formData = new FormData()
      formData.append('file', blob)
      uploadFile(formData)
        .then((res) => {
          form.signPath = res.data
          this.saveOrUpdateDep(form)
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 保存接口
    saveOrUpdateDep(form) {
      saveOrUpdateDep(form).then(() => {
        this.loading = false
        this.$modal.msgSuccess('保存成功')
        this.cancel()
      })
    },
    // 重置
    reset() {
      this.signResult = ''
      this.form = {
        depId: this.$route.meta.ksID,
        patientcode: undefined,
        consultType: 1,
        signPath: undefined,
        consultContent: undefined,
        id: undefined,
      }
      this.resetForm('form')
    },
    // 取消
    cancel() {
      this.open = false
    },
  },
}
</script>

<style lang="scss"></style>
