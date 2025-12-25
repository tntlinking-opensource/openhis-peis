<template>
  <div class="upgrade-dialog">
    <el-dialog :title="title" :visible.sync="dialogVisible" width="400px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="分中心" prop="centerorgname">
          <el-select v-model="form.centerorgname" placeholder="请选择金蝶客户的分中心" style="width: 100%" filterable clearable>
            <el-option v-for="item in centerOrgList" :key="item.id" :label="item.fzx" :value="item.centerorgname">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCenterOrgNameList } from '@/api/customer/customer_list'

export default {
  name: 'CustomerUpgrade',
  data() {
    return {
      // 弹窗标题
      title: '客户升级',
      // 弹窗显示状态
      dialogVisible: false,
      // 表单数据
      form: {
        centerorgname: '',
      },
      // 分中心列表
      centerOrgList: [],
      // 表单校验
      rules: {
        centerorgname: [
          {
            required: true,
            message: '请选择分中心',
            trigger: 'change'
          }
        ]
      },
      // 回调函数
      successCallback: null,
      errorCallback: null
    }
  },
  mounted() {
    this.loadCenterOrgList()
  },
  methods: {
    // 显示弹窗
    show(successCallback, errorCallback) {
      this.resetForm()
      this.successCallback = successCallback
      this.errorCallback = errorCallback
      this.dialogVisible = true
    },
    // 隐藏弹窗
    hide() {
      this.dialogVisible = false
    },
    // 重置表单
    resetForm() {
      const matchItem = this.centerOrgList.find(item => item.branchId == this.$getCookie('cid'))
      if (matchItem) {
        this.form.centerorgname = matchItem.centerorgname
      }
      if (this.$refs.form) {
        this.$refs.form.resetFields()
      }
    },
    // 取消操作
    cancel() {
      this.hide()
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 提交成功，调用回调函数
          if (this.successCallback) {
            this.successCallback(this.form)
          }
          this.hide()
        }
      })
    },
    // 加载分中心列表
    loadCenterOrgList() {
      getCenterOrgNameList().then(({ data }) => {
        this.centerOrgList = data || []
        const matchItem = data.find(item => item.branchId == this.$getCookie('cid'))
        if (matchItem) {
          this.form.centerorgname = matchItem.centerorgname
        }
      }).catch((error) => {
        console.error('加载分中心列表失败:', error)
        this.$message.error('加载分中心列表失败')
        if (this.errorCallback) {
          this.errorCallback(error)
        }
      })
    }
  }
}
</script>

<style scoped>
.upgrade-dialog {
  .dialog-footer {
    text-align: center;
  }
}
</style>