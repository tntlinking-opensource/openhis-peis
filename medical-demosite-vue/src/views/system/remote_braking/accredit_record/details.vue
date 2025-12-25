<!-- 添加或修改合作机构对话框 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="860px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
    <el-form ref="form" :model="form" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
      <el-form-item label="记录ID" prop="id">
        <el-input v-model="form.id" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="业务标识" prop="bsFlag">
        <el-input v-model="form.bsFlag" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="授权码类型" prop="codeType">
        <el-input :value="form.codeType == 1 ? '外部合作商提供' : '系统提供'" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="有效期" prop="expiryDate">
        <el-input v-model="form.expiryDate" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="记录说明" prop="remark">
        <el-input v-model="form.remark" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input :value="form.status == 1 ? '正常' : '失效'" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createdate">
        <el-input v-model="form.createdate" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="授权码" prop="authCode">
        <el-input v-model="form.authCode" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="业务方的授权码" prop="bizAuthCode">
        <el-input v-model="form.bizAuthCode" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="加密公钥" prop="keyText">
        <el-input v-model="form.keyText" readonly style="width: 220px" />
      </el-form-item>
      <el-form-item label="解密私钥" prop="valueText">
        <el-input v-model="form.valueText" readonly style="width: 220px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { ConfigDetails } from '@/api/system/remote_braking/accredit_record'
export default {
  data() {
    return {
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
    }
  },
  methods: {
    // 打开对话框
    showDialog(id) {
      this.open = true
      this.title = '授权记录详情'
      this.loading = true
      this.reset()
      ConfigDetails(id)
        .then((response) => {
          this.form = response.data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 表单重置
    reset() {
      this.form = {
        id: '',
        bsFlag: '',
        codeType: '',
        expiryDate: '',
        remark: '',
        status: '',
        createdate: '',
        authCode: '',
        bizAuthCode: '',
        keyText: '',
        valueText: '',
      }
      this.resetForm('form')
    },
  },
}
</script>
