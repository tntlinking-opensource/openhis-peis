<!-- 设置现场加名单 开发人：麦沃德科技予安 -->
<template>
  <el-dialog title="备单提示" :visible.sync="open" width="800px" append-to-body>
    <div style="font-size: 18px; margin-bottom: 8px; color: #000">设置现场加名单后，该分组（套餐）下的客户可选择单位名称+预约码预约体检</div>
    <el-form ref="queryForm" size="small" @submit.native.prevent v-loading="loading" class="no-margin-bottom">
      <el-form-item label="单位预约码">
        <div>{{ codeInfo.extractedCode }}<span v-clipboard="codeInfo.extractedCode" style="color: skyblue; cursor: pointer; margin-left: 20px" @click="handleCopy">复制</span></div>
      </el-form-item>
      <el-form-item label="单位预约类型">
        <div>{{ ['', '普通会员', 'VIP', 'VVIP'][codeInfo.idPatientclass] }}</div>
      </el-form-item>
      <div style="padding: 20px; padding-bottom: 10px; background-color: #eee; border-radius: 10px">
        <el-form-item label="有效期">
          <el-radio-group v-model="codeInfo.expirationDate" @change="handleChangeUrl">
            <el-radio :label="7">7天</el-radio>
            <el-radio :label="14">14天</el-radio>
            <el-radio :label="30">30天</el-radio>
            <el-radio :label="60">60天</el-radio>
            <el-radio :label="120">120天</el-radio>
            <el-radio :label="365">365天</el-radio>
            <el-radio :label="999">永久</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否启用">
          <el-switch v-model="codeInfo.status" :active-value="0" :inactive-value="1" @change="handleChangeUrl"> </el-switch>
        </el-form-item>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="open = false">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { generateGroupCode, modifyGroupCode } from '@/api/reception/prepare_order.js'
import Vue from 'vue'
import VueClipboard from 'vue-clipboard2'
export default {
  data() {
    return {
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      // 体检者列表
      personList: [],
      // 预约码信息
      codeInfo: {},
    }
  },
  mounted() {
    // 开启剪贴板
    Vue.use(VueClipboard)
    this.$clipboardConfig.autoSetContainer = true
  },
  methods: {
    // 打开弹窗
    handleShow(query) {
      this.createUrl(query)
    },
    // 编辑打开弹窗
    handleSetting(data) {
      this.open = true
      this.codeInfo = data
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 生成链接
    createUrl(query) {
      this.loading = true
      generateGroupCode(query)
        .then(({ data }) => {
          this.open = true
          this.codeInfo = data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
          this.open = false
        })
    },
    // 修改链接信息
    handleChangeUrl() {
      this.loading = true
      modifyGroupCode({
        status: this.codeInfo.status,
        expirationDate: this.codeInfo.expirationDate,
        id: this.codeInfo.id,
      })
        .then(({ data }) => {
          this.$modal.msgSuccess('修改成功')
          this.codeInfo.path = data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    handleCopy() {
      this.$modal.msgSuccess('已复制到剪贴板')
    },
  },
}
</script>
