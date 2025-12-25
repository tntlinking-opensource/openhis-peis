<!-- 职业结果告知书 麦沃德科技 予安 -->
<template>
  <div class="results-notice-box">
    <div class="results-notice">
      <module :printData="printData" :type="type" :isModify="isModify" :centerName="centerName" :isJinDu="isJinDu" :currentDate="currentDate" @handleUpdata="handleUpdata"></module>
    </div>
  </div>
</template>

<script>
import { getData, updateApi, getDetailByNO } from '@/api/report/print_notice.js'

import module from './module.vue'
export default {
  name: 'ResultsNotice',
  components: {
    module,
  },
  data() {
    return {
      // 打印类型
      type: undefined,
      // 打印内容
      printData: {},
      // 分中心名称
      centerName: {},
      // 是否为锦都原医院
      isJinDu: false,
      // 当前年月日
      currentDate: '',
    }
  },
  created() {
    this.isJinDu = this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c' || window.location.href.includes('http://XXX.XXX.XXX') || window.location.href.includes('http://XXX.XXX.XXX') ? true : false
    this.currentDate = this.$getDate().slice(0, 4) + '年' + this.$getDate().slice(5, 7) + '月' + this.$getDate().slice(8, 10) + '日'
    this.type = this.$route.query.type

    this.isModify = this.$route.query.isModify
    this.getPrintData(this.$route.query.patientcode)
  },
  methods: {
    // 获取打印内容
    getPrintData(patientcode) {
      getData({ patientcode, reportType: 8, idExamtype: this.type }).then(({ data }) => {
        this.printData = this.type == 1 ? data.zyb : this.type == 2 ? data.jjz : data.fc
        this.printData.patientcode = patientcode
      })
      getDetailByNO({ branchId: this.$getCookie('cid') }).then(({ data }) => {
        this.centerName = data
      })
    },
    // 修改打印内容
    handleUpdata() {
      const loading = this.$loading({
        lock: true,
        text: '正在提交',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      updateApi({
        idExamtype: this.type,
        reportType: 8,
        patientcode: this.printData.patientcode,
        content: this.type == 3 ? { fc: this.printData } : this.type == 2 ? { jjz: this.printData } : { zyb: this.printData },
      }).then(() => {
        loading.close()
        this.$modal.msgSuccess('修改成功')
      })
    },
  },
}
</script>

<style lang="scss">
.results-notice-box {
  padding-top: 20px;
  width: 100%;
  min-height: 100%;
  // background-color: rgba(0, 0, 0, 0.06);
  font-family: Simsun;
}
.results-notice {
  padding: 40px 30px;
  margin: auto;
  width: 794px;
  background-color: #fff;
}
</style>
