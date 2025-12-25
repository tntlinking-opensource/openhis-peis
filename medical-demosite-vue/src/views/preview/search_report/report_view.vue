<!-- 分享报告-查看老系统pdf 开发人：麦沃德科技予安 -->
<template>
  <div class="report-view-pdf">
    <img :src="item" alt="" srcset="" v-for="(item, index) in pdfList" :key="index" style="width: 100%; margin-bottom: 8px" />
    <div v-if="pdfTotal >= pdfQuery.curPage" style="text-align: center"><i class="el-icon-loading"></i>加载中...</div>
  </div>
</template>
<script>
import { pdfToBase64 } from '@/api/preview/search_report.js'

export default {
  name: 'ShareReportView',
  data() {
    return {
      // pdf请求参数
      pdfQuery: {},
      // pdf总数
      pdfTotal: 1, // 为2初始显示加载中
      // pdf列表
      pdfList: [],
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  created() {
    document.title = '健康体检报告'
    var query = JSON.parse(localStorage.getItem('searchReport'))
    query.curPage = 1
    let timer = setInterval(() => {
      if(this.$route.query.pdfUrl){
        query.remotePath = this.$route.query.pdfUrl
        this.pdfQuery = JSON.parse(JSON.stringify(query))
        this.handleGetPdf()
        clearInterval(timer)
      }
    }, 200);
  },
  methods: {
    handleGetPdf() {
      pdfToBase64(this.pdfQuery).then((res) => {
        if (this.pdfQuery.curPage == 1) {
          this.pdfTotal = res.total
        }
        this.pdfList.push('data:image/jpeg;base64,' + res.imgBase64)
        this.pdfQuery.curPage++
        if (this.pdfQuery.curPage <= this.pdfTotal) {
          setTimeout(() => {
            this.handleGetPdf()
          }, 800)
        }
      })
    },
  },
}
</script>

<style lang="scss">
body {
  width: 100%;
  height: 100%;
}
.report-view-pdf {
  width: 100%;
  height: 100%;
}
</style>
