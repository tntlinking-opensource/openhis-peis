<!-- 隐私报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-private-report">
    <div class="individual-report" v-if="reportData">
      <div class="report-main">
        <private-report ref="privateReport" :report-data="reportData"></private-report>
      </div>
    </div>
    <div style="height: 40px; width: 100%;" class="placeholder"></div>
    <div class="print-fixed">
      <el-button type="primary" @click="handlePrint">打 印</el-button>
    </div>
  </div>
</template>
<script>
import { getData } from '@/api/preview/individual_report.js'
// import { getPrivateData } from '@/api/preview/individual_report.js'
import privateReport from './module/private';
export default {
  name: "IndividualPrivateReport",
  components: { privateReport },
  data() {
    return {
      // 体检号
      patientcode: {},
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
      idExamtype: "",
    };
  },
  created() {
    document.title = '隐私报告'
    this.patientcode = this.$route.query.patientcode;
    this.idExamtype = this.$route.query.idExamtype;
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    this.getReport()
  },
  methods: {
    //打印报告
    handlePrint() {
      window.print()
    },
    // 获取报告数据
    getReport() {
      let obj = {
        idExamtype: this.idExamtype, //体检类型,0.健康体检 1.职业体检 只有健康报告有隐私报告
        patientcode: this.patientcode, //体检号
        reportType: 2, //报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 6.对比报告 7.Pacs报告
      }
      getData(obj).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, "提醒");
          this.loading.close();
          return
        }
        this.reportData = res.data;
        this.$nextTick(() => {
          this.$refs.privateReport.setBarcode()
        })
        this.loading.close();
      })
    },
  }
};
</script>

<style lang="scss" scoped>
.print-fixed {
  position: fixed;
  left: 50%;
  bottom: 10px;
  transform: translateX(-50%);
  text-align: center;
  width: 260px;
  height: 35px;
  cursor: pointer;
  z-index: 999;
}

.preview-individual-private-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .individual-report {
    width: 210mm;
    margin: 0 auto;

    * {
      font-family: "song";
    }

    .report-main {
      margin: 20px auto 0;
      background: #fff;
      padding: 40px;
    }
  }

  // 分页打印的问题
  table {
    width: 100%;
    page-break-inside: auto;
    page-break-after: avoid;
    page-break-before: avoid;
  }
}

@media print {
  .preview-individual-private-report {
    padding: 0;
    background: #fff;
  }

  .report-main {
    margin-top: 0 !important;
    padding: 0 40px !important;
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }

  @page {
    margin: 32px 20px;
  }
}
</style>