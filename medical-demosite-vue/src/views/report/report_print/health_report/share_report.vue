<!-- 分享报告 开发人：麦沃德科技予安 -->
<template>
  <el-dialog title="分享报告" :visible.sync="open" width="800px" append-to-body>
    <el-form ref="queryForm" size="small" @submit.native.prevent v-loading="loading">
      <el-form-item label="已选顾客">
        <div>{{ urlInfo.num }}名</div>
      </el-form-item>
      <el-form-item label="报告链接">
        <div>{{ urlInfo.path }}<span v-clipboard="urlInfo.path" style="color: skyblue; cursor: pointer; margin-left: 20px" @click="handleCopy">复制</span></div>
      </el-form-item>
      <el-form-item label="提取码">
        <div>{{ urlInfo.extractedCode }}</div>
      </el-form-item>
      <div style="padding: 20px; padding-bottom: 10px; background-color: #eee; border-radius: 10px">
        <el-form-item label="有效期">
          <el-radio-group v-model="urlInfo.expirationDate" @change="handleChangeUrl">
            <el-radio label="1">1天</el-radio>
            <el-radio label="3">3天</el-radio>
            <el-radio label="7">7天</el-radio>
            <el-radio label="14">14天</el-radio>
            <el-radio label="30">30天</el-radio>
            <el-radio label="999">永久</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分享链接自动填充提取码">
          <el-switch v-model="urlInfo.autofill" :active-value="1" :inactive-value="0" @change="handleChangeUrl"> </el-switch>
        </el-form-item>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="open = false">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { reportShare, updateReportShare } from '@/api/report/report_print/health_report'
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
      // 链接信息
      urlInfo: {},
    }
  },
  mounted() {
    // 开启剪贴板
    Vue.use(VueClipboard)
    this.$clipboardConfig.autoSetContainer = true
  },
  methods: {
    // 打开弹窗
    handleShow(personList) {
      this.personList = personList
      this.createUrl()
    },
    // 编辑打开弹窗
    handleSetting(data) {
      this.open = true
      this.urlInfo = data
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 生成链接
    createUrl() {
      this.loading = true
      reportShare({
        patientcodes: this.personList.map((item) => item.patientcode),
      })
        .then(({ data }) => {
          this.open = true
          this.urlInfo = data
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
      updateReportShare({
        autofill: this.urlInfo.autofill,
        expirationDate: this.urlInfo.expirationDate,
        id: this.urlInfo.id,
      })
        .then(({ data }) => {
          this.urlInfo.path = data
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
