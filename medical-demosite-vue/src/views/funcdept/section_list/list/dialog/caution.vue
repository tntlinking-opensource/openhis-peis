<template>
  <el-dialog title="我要提醒" :visible.sync="open" width="450px" append-to-body style="overflow: hidden" :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="80px" :inline="true" hide-required-asterisk :rules="rules" v-loading="loading">
      <el-form-item label="科室" prop="depIds">
        <el-select v-model="form.depIds" placeholder="请选择科室" style="width: 320px" multiple clearable>
          <el-option v-for="options in modelOptions" :key="options.id" :label="options.name" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="提醒内容" prop="content" style="margin-bottom: 0">
        <el-input v-model="form.content" placeholder="请输入" style="width: 320px" type="textarea" :rows="5" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1"  @click="handleSave">保 存</el-button>
      <el-button class="section-btn-style1"  plain @click="reset">重 置</el-button>
      <el-button class="section-btn-style2" @click="cancle">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getRemindKs, saveRemind, getRemindData } from '@/api/funcdept/section_list/dialog.js'
export default {
  props: [],
  data() {
    return {
      patientcode: undefined,
      // 弹窗
      open: false,
      loading: false,
      // 数据
      form: {},
      // 校验规则
      rules: {
        depIds: [{ required: true, message: '请选择科室', trigger: 'blur' }],
        content: [{ required: true, message: '请填写提醒内容', trigger: 'change' }],
      },
      // 科室列表
      modelOptions: [],
    }
  },
  methods: {
    showDialog(patientcode) {
      this.open = true
      this.patientcode = patientcode
      this.reset()
      getRemindKs(patientcode).then(({ data }) => {
        this.modelOptions = data
      })
    },
    // 获取当前科室提醒详情
    getMyRemind() {
      this.loading = true
      getRemindData({
        ksID: this.$route.meta.ksID,
        patientcode: this.patientcode,
      }).then(({ data }) => {
        if (data) {
          this.form.id = data.id
          this.form.depIds = data.depIds.split(',')
          this.form.content = data.remindContent
        }
        this.loading = false
      })
    },
    // 保存
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // this.cancle()
          let query = {
            id: this.form.id,
            patientcode: this.patientcode,
            content: this.form.content,
            depId: this.$route.meta.ksID,
            depIds: this.form.depIds.join(','),
          }
          saveRemind(query).then(({ data }) => {
            this.cancle()
            this.$modal.msgSuccess('保存成功')
          })
        }
      })
    },
    // 重置
    reset() {
      this.form = {
        id: undefined,
        depIds: undefined,
        content: undefined,
      }
      this.resetForm('form')
      this.getMyRemind()
    },
    // 取消
    cancle() {
      this.open = false
    },
  },
}
</script>

<style lang="scss"></style>
