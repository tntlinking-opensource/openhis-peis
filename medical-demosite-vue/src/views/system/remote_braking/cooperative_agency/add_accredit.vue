<template>
  <!-- 添加授权信息对话框 -->
  <el-dialog title="添加授权信息" :visible.sync="open" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
      <el-form-item label="机构名称" prop="institutionName">
        <el-input v-model="agencyInfo.typeName" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="授权码" prop="authCode">
        <el-input v-model="form.authCode" placeholder="请输入16位授权码" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="授权类型" prop="authType">
        <el-select v-model="form.authType" style="width: 220px">
          <el-option label="永久授权" :value="0"></el-option>
          <el-option label="期限授权" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="到期是否可用" prop="canUse">
        <el-select v-model="form.canUse" style="width: 220px">
          <el-option label="可用" :value="1"></el-option>
          <el-option label="不可用" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker v-model="form.startTime" style="width: 220px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="到期时间" prop="expiredTime">
        <el-date-picker v-model="form.expiredTime" style="width: 220px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" style="width: 220px">
          <el-option label="正常" :value="1"></el-option>
          <el-option label="失效" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input v-model="form.version" placeholder="请输入版本号" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" clearable style="width: 570px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { generateApi } from '@/api/system/remote_braking/cooperative_agency'
export default {
  props: ['agencyInfo'],
  data() {
    var authCodeValidate = (rule, value, callback) => {
      if (value.length != 16) {
        callback(new Error('请输入16位授权码'))
      } else {
        callback()
      }
    }
    var expiredTimeValidate = (rule, value, callback) => {
      if (value < this.form.startTime) {
        callback(new Error('到期时间不能小于开始时间'))
      } else {
        callback()
      }
    }
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        authCode: [
          { required: true, message: '授权码不能为空', trigger: 'change' },
          { validator: authCodeValidate, trigger: 'change' },
        ],
        expiredTime: [
          { required: true, message: '请选择到期时间', trigger: 'change' },
          { validator: expiredTimeValidate, trigger: 'change' },
        ],
        startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
      },
    }
  },
  methods: {
    showDialog() {
      this.reset()
      this.open = true
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 表单重置
    reset() {
      this.form = {
        authCode: '',
        authType: 1,
        canUse: 1,
        expiredTime: '',
        startTime: this.$getDate().slice(0, 10),
        status: 1,
        remark: '',
        version: '',
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          generateApi({
            ...this.form,
            institutionId: this.agencyInfo.sourceId,
            institutionName: this.agencyInfo.typeName,
          }).then(() => {
            this.$modal.msgSuccess('添加成功')
            this.cancel()
            this.$emit('handleQuery')
          })
        }
      })
    },
  },
}
</script>

<style></style>
