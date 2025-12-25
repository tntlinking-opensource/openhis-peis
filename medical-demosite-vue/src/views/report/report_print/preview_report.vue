<!-- 预览报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview_report">
    <iframe ref="reportFrame" class="report-iframe"></iframe>
  </div>
</template>
<script>
export default {
  name: 'PreviewReport',
  data() {
    return {
      // 遮罩层
      loading: null,
      // 报告链接
      reportUrl: '',
    }
  },
  created() {
    this.reportUrl = decodeURIComponent(this.$route.query.url)
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
    async getReport() {
      try {
        if (!this.reportUrl) {
          throw new Error('没有成功加载任何PDF文件')
        }
        const pdfBytes = await this.fetchPdfBytes(this.reportUrl)
        const blob = new Blob([pdfBytes], { type: 'application/pdf' })
        const url = URL.createObjectURL(blob)
        this.$refs.reportFrame.src = url
        this.loading.close()
      } catch (error) {
        console.error('PDF预览失败:', error)
        this.$message.error('PDF预览失败，请稍后再试')
        this.loading.close()
      }
    },
    // 获取PDF文件字节流
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
.preview_report {
  .report-iframe {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
}
</style>
