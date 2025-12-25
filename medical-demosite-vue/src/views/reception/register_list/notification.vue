<!-- 批量设置统收限额  开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="检前短信通知" :visible.sync="open" class="limit-receipt" width="580px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" hide-required-asterisk v-loading="loading">
      <el-form-item label="模板名称" prop="template">
        <el-select v-model="form.template" placeholder="请输入输入码或名称查询" style="width: 420px" @change="templateChange">
          <el-option label="劳模体检通知" :value="7"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="模板内容" prop="modelName">
        <el-input type="textarea" readonly v-model="form.modelName" style="width: 420px" :rows="5"></el-input>
      </el-form-item>
      <el-form-item label="立即发送" prop="modelName">
        <el-checkbox v-model="form.immediately"></el-checkbox>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendTime" v-if="!form.immediately">
        <el-date-picker v-model="form.sendTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getComboData, saveOrUpdateMsg } from '@/api/reception/register_list'
export default {
  data() {
    var checkSendtime = (rule, value, callback) => {
      if (!this.form.immediately && !value) {
        callback(new Error('请选择发送时间'))
      } else {
        callback()
      }
    }
    return {
      // 遮罩层
      loading: false,
      // 表单数据
      form: {
        template: undefined,
        idTemplate: undefined,
        modelName: undefined,
        immediately: undefined,
        sendTime: undefined,
      },
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        template: [{ required: true, message: '请选择模板', trigger: 'change' }],
        sendTime: [{ validator: checkSendtime, trigger: 'blur' }],
      },
      // 发送短信的体检号列表
      patientcodes: [],
    }
  },
  methods: {
    // 显示
    handleShow(array) {
      this.open = true
      this.patientcodes = array
      this.reset()
    },
    // 模板改变
    templateChange() {
      getComboData({
        messageType: this.form.template,
      }).then(({ data }) => {
        this.form.modelName = data[0].messageText
        this.form.idTemplate = data[0].id
      })
    },
    // 保存
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if(this.form.immediately){
            this.form.sendTime = ''
          }
          saveOrUpdateMsg({
            ...this.form,
            patientcodes: this.patientcodes,
          }).then(({ data }) => {
            if (data == 'success') {
              this.$modal.msgSuccess('保存成功！')
            } else {
              this.$alert(data, '提示', {
                dangerouslyUseHTMLString: true,
              })
            }
            this.open = false
          })
        }
      })
    },
    // 重置
    reset() {
      this.form = {
        template: undefined,
        idTemplate: undefined,
        modelName: undefined,
        immediately: true,
        sendTime: undefined,
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
<style lang="scss">
.limit-receipt {
  .age-box {
    display: flex;
    align-items: center;
  }
}
</style>
