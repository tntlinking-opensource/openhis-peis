<!-- 加急报告-加急 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog title="新增加急" :visible.sync="open" width="450px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="体检号:" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入体检号" clearable style="width: 320px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { saveOrUpdate } from '@/api/reception/urgent_report.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientcode: [{ required: true, message: '体检号号不能为空', trigger: 'change' }],
      },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
    }
  },
  methods: {
    // 加急
    handleUpdate() {
      this.reset()
      this.open = true
    },
    // 表单重置
    reset() {
      this.form = {
        patientcode: undefined,
      }
      this.resetForm('form')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          saveOrUpdate(this.form)
            .then(() => {
              this.loading = false
              this.cancel()
              this.$modal.msgSuccess('加急成功')
              this.$emit('getList')
            })
            .catch(() => {
              this.loading = false
            })
        }
      })
    },
  },
}
</script>
