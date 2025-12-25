<!-- pacs科室--生成报告 麦沃德科技 开发人 清风 -->
<template>
  <el-dialog title="选择技术/方法" :visible.sync="open" width="438px" append-to-body class="openDialog" :close-on-click-modal="false">
    <el-form size="mini" label-width="110px" v-loading="loading">
      <el-form-item label="技术/方法">
        <el-select v-model="arrContent" multiple clearable>
          <el-option v-for="item in arr" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否打印图像">
        <el-checkbox v-model="addpic"></el-checkbox>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保存</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { techApi, createRad } from '@/api/PACS/section_modal.js'

export default {
  props: ['patientcode'],
  data() {
    return {
      // 显示对话框
      open: false,
      // 技术数组
      arr: [],
      // 选择的技术
      arrContent: [],
      // 是否打印图像
      addpic: true,
      // 加载中
      loading: undefined,
    }
  },
  methods: {
    // 打开对话框
    generateReportWindow() {
      this.arrContent = []
      this.addpic = true
      this.open = true
      this.loading = true
      techApi()
        .then(({ data }) => {
          this.arr = data
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 提交生成报告
    submitForm() {
      // if (!this.arrContent.length) {
      //   this.$alert('请选择技术/方法', '提示')
      //   returns
      // }
      this.loading = true
      createRad({
        ksId: this.$route.meta.deptNo,
        patientcode: this.patientcode,
        tech: this.arrContent.join(','),
        addpic: this.addpic.toString(),
        type: this.arrContent.length ? 1 : 0,
        source: this.$route.meta.deptNo == 143 ? 1 : 0,
      })
        .then(() => {
          this.$modal.msgSuccess('生成报告成功')
          this.loading = false
          this.open = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 取消
    cancel() {
      this.open = false
    },
  },
}
</script>

<style scoped></style>
