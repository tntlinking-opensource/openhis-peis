<template>
  <el-dialog title="查看问卷" :visible.sync="open" width="400px" append-to-body style="overflow: hidden" destroy-on-close>
    <div class="history">
      <template v-if="historyList.length != 0">
        <div v-html="item" v-for="(item, index) in historyList" :key="index" style="margin-bottom: 10px"></div>
      </template>
      <span v-else>未填写过问卷</span>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style2" @click="open = false">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { peisQuestionnaireSecond } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 上传弹窗
      open: false,
      historyList: [],
    }
  },
  methods: {
    showDialog(patientCode) {
      this.open = true
      peisQuestionnaireSecond({
        patientCode,
      }).then(({ data }) => {
        this.historyList = data || []
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.history {
  overflow-y: auto;
  min-height: 500px;
}
</style>
