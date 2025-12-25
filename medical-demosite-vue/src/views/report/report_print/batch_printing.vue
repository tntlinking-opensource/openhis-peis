<!-- 批量打印报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="batch_printing">
    <iframe ref="printFrame" class="print-iframe"></iframe>
  </div>
</template>
<script>
import { getReportAddress } from '@/api/report/report_print/batch_printing.js'

export default {
  name: 'BatchPrinting',
  data() {
    return {
      // 遮罩层
      loading: null,
      // 查询参数
      queryParams: {
        diseaseHealth: null,
        patientcodes: 0,
      },
      // 报告列表
      reportList: [],
    }
  },
  created() {
    this.queryParams.diseaseHealth = this.$route.query.type
    this.queryParams.patientcodes = this.$route.query.ids
    this.loading = this.$loading({
      lock: true,
      text: '加载PDF中...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
  },
  methods: {
    // 获取报告路径
    getReport() {
      this.reportList = []
      if (this.queryParams.patientcodes) {
        getReportAddress(this.queryParams).then(({ data }) => {
          this.reportList = data || []
          this.mergeReport()
        }).catch(() => {
          this.loading.close()
          this.$modal.alertWarning('获取报告失败，请稍后再试', '提醒')
        })
      } else {
        this.$modal.alertWarning('暂无报告，请稍后再试', '提醒')
        this.loading.close()
      }
    },
    // 合并报告
    async mergeReport() {
      if (this.reportList.length === 0) {
        this.$message.warning('没有可打印的报告')
        this.loading.close()
        return
      }
      try {
        // 动态导入pdf-lib
        const { PDFDocument } = await import('pdf-lib')

        const mergedPdf = await PDFDocument.create()
        let successCount = 0
        for (const report of this.reportList) {
          if (report) {
            const pdfBytes = await this.fetchPdfBytes(report)
            if (pdfBytes) {
              const pdfDoc = await PDFDocument.load(pdfBytes)
              const pages = await mergedPdf.copyPages(pdfDoc, pdfDoc.getPageIndices())
              pages.forEach(page => mergedPdf.addPage(page))
              successCount++
            }
          }
        }
        if (successCount === 0) {
          throw new Error('没有成功加载任何PDF文件')
        }
        const mergedPdfBytes = await mergedPdf.save()
        const blob = new Blob([mergedPdfBytes], { type: 'application/pdf' })
        const url = URL.createObjectURL(blob)
        const iframe = this.$refs.printFrame
        iframe.src = url
        this.loading.close()
      } catch (error) {
        console.error('PDF合并失败:', error)
        this.$message.error('PDF合并失败，请稍后再试')
        this.loading.close()
      }
    },
    // 获取PDF文件字节
    async fetchPdfBytes(url) {
      try {
        const response = await fetch(url, {
          method: 'GET',
          headers: {
            'Accept': 'application/pdf'
          }
        })
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }
        const buffer = await response.arrayBuffer()
        return buffer
      } catch (error) {
        console.error('获取PDF文件失败:', error)
        this.$message.warning(`PDF文件加载失败: ${error.message}`)
        return null
      }
    },
  },
  beforeDestroy() {
    if (this.loading && typeof this.loading.close === 'function') {
      this.loading.close()
    }
  }
}
</script>

<style lang="scss" scope>
.batch_printing {
  .print-iframe {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
}
</style>
