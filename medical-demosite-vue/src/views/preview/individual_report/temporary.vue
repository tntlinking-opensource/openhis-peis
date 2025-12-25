<!-- 临时报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-temporary-report">
    <div class="individual-report only-scale" v-if="reportData">
      <!-- 封面 -->
      <div class="report-cover">
        <div class="cover-top">
          <div class="cover-header">
            <div class="header-left">
              <div class="title">健康体检报告</div>
              <div class="subtitle">Physical Examination Report</div>
            </div>
            <div class="header-right">
              <div class="logo">
                <img src="@/assets/logo/cover_logo.png" />
              </div>
              <div class="tag">
                <div class="tag-icon">
                  <div class="garden"></div>
                  <div class="garden"></div>
                </div>
                <div class="tag-text">广<br />博<br />慈<br />爱<br />·<br />厚<br />德<br />尚<br />道</div>
              </div>
            </div>
          </div>
          <div class="cover-content">
            <!-- <div class="content-info">
              <div class="info-title"><span>体检单位</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.workunit }}</div>
            </div> -->
            <div class="content-info" v-for="(item, index) in reportData.head.titleList" :key="index">
              <div class="info-title">
                <span>{{ item.title }}</span>
              </div>
              <div class="info-cont">
                <span>{{ item.value }}</span>
                <img class="barcode" id="barcode" v-if="item.title == '体检号码：'" />
              </div>
            </div>
          </div>
        </div>
        <div class="cover-bottom">
          <span>地址：{{ reportData.head.parameters.org_address }}</span>
          <span>电话：{{ reportData.head.parameters.org_phone }} </span>
          <span>邮编：{{ reportData.head.parameters.org_zipcode }}</span>
        </div>
      </div>
      <!-- 内容 -->
      <div class="report-main" v-if="reportData.ksList.length || reportData.endPicture.length">
        <!-- <section-report  :imgPath="imgPath" :show-data="reportData.ksList" type-data="0"></section-report> -->
        <section-report :show-data="reportData.ksList" :endPicture="reportData.endPicture" type-data="0" :imgPath="imgPath" :style="reportData.ksList && reportData.ksList.length ? 'page-break-before: always' : ''"></section-report>
      </div>
      <!-- 相关检测报告 -->
      <div class="detection-report" v-if="reportData.threeReport && reportData.threeReport.length">
        <div class="report-item" v-for="(value, index) in reportData.threeReport" :key="index" style="background-color: #fff; margin-bottom: 8px">
          <img :src="imgPath + value" alt="" style="width: 95%; height: 95%; margin: 0" />
        </div>
      </div>
      <!-- 封底 -->
      <div class="report-end">
        <img class="end-logo" src="@/assets/logo/logo.png" />
        <img class="end-code" src="@/assets/images/report/mini_code.png" />
        <img class="end-code" src="@/assets/images/report/QR_code.png" />
      </div>
      <!-- 隐私报告 -->
      <div class="report-private" v-if="reportData.ysbg">
        <private-report ref="privateReport" :report-data="reportData.ysbg"></private-report>
      </div>
    </div>
    <div class="individual-report only-print" v-if="reportData">
      <!-- 封面 -->
      <div class="report-cover">
        <div class="cover-top">
          <div class="cover-header">
            <div class="header-left">
              <div class="title">健康体检报告</div>
              <div class="subtitle">Physical Examination Report</div>
            </div>
            <div class="header-right">
              <div class="logo">
                <img src="@/assets/logo/cover_logo.png" />
              </div>
              <div class="tag">
                <div class="tag-icon">
                  <div class="garden"></div>
                  <div class="garden"></div>
                </div>
                <div class="tag-text">广<br />博<br />慈<br />爱<br />·<br />厚<br />德<br />尚<br />道</div>
              </div>
            </div>
          </div>
          <div class="cover-content">
            <!-- <div class="content-info">
              <div class="info-title"><span>体检单位</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.workunit }}</div>
            </div> -->
            <div class="content-info" v-for="(item, index) in reportData.head.titleList" :key="index">
              <div class="info-title">
                <span>{{ item.title }}</span>
              </div>
              <div class="info-cont">
                <span>{{ item.value }}</span>
                <img class="barcode" id="barcode" v-if="item.title == '体检号码：'" />
              </div>
            </div>
          </div>
        </div>
        <div class="cover-bottom">
          <span>地址：青岛经济技术开发区井冈山路596号</span>
          <span>电话：0532-86899999 </span>
          <span>邮编：266555</span>
        </div>
      </div>
      <!-- 内容 -->
      <div class="report-main" v-if="reportData.ksList.length || reportData.endPicture.length">
        <!-- <section-report  :imgPath="imgPath" :show-data="reportData.ksList" type-data="0"></section-report> -->
        <section-report :show-data="reportData.ksList" :endPicture="reportData.endPicture" type-data="0" :imgPath="imgPath" :style="reportData.ksList && reportData.ksList.length ? 'page-break-before: always' : ''"></section-report>
      </div>
      <!-- 相关检测报告 -->
      <div class="detection-report" v-if="reportData.threeReport && reportData.threeReport.length">
        <div class="report-item" v-for="(value, index) in reportData.threeReport" :key="index" style="background-color: #fff; margin-bottom: 8px">
          <img :src="imgPath + value" alt="" style="width: 95%; height: 95%; margin: 0" />
        </div>
      </div>
      <!-- 封底 -->
      <div class="report-end">
        <img class="end-logo" src="@/assets/logo/logo.png" />
        <img class="end-code" src="@/assets/images/report/mini_code.png" />
        <img class="end-code" src="@/assets/images/report/QR_code.png" />
      </div>
      <!-- 隐私报告 -->
      <div class="report-private" v-if="reportData.ysbg">
        <private-report ref="privateReport" :report-data="reportData.ysbg"></private-report>
      </div>
    </div>
    <div style="height: 40px; width: 100%" class="placeholder"></div>
    <div class="print-fixed">
      <el-button type="primary" @click="handlePrint">打 印</el-button>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import { getData } from '@/api/preview/individual_report.js'
import JsBarcode from 'jsbarcode'
import sectionReport from './module/section'
import privateReport from './module/private'
export default {
  name: 'IndividualTemporaryReport',
  components: { sectionReport, privateReport },
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 查询参数
      queryParams: {
        dh: 0,
        isJy: 0,
        patientcode: null,
        showAllImage: 0,
      },
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
    }
  },
  created() {
    document.title = '临时报告'
    this.queryParams.patientcode = this.$route.query.patientcode
    this.queryParams.idExamtype = this.$route.query.idExamtype
    this.queryParams.showAllImage = this.$route.query.showAllImage || 0
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
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
        // analyzeId:"", //样本id(团检报告查询使用)
        // compareReportId:"", //对比报告表ID(对比报告使用)
        idExamtype: 0, //体检类型,0.健康体检 1.职业体检
        // ksId:"", //科室id(Pacs报告查询使用)
        patientcode: this.queryParams.patientcode, //体检号
        reportType: 3, //报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 6.对比报告 7.Pacs报告
      }
      getData(obj).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, '提醒')
          this.loading.close()
          return
        }
        if (res.data) {
          if (!res.data.head.titleList) {
            res.data.head.parameters.titleList.unshift({
              title: '体检单位：',
              value: res.data.head.parameters.org,
            })
            res.data.head.parameters.titleList.push({
              title: '体检日期：',
              value: res.data.head.parameters.dateregister,
            })
            res.data.head.titleList = res.data.head.parameters.titleList
          }
          if (res.data.ksList) {
            res.data.ksList.forEach((val) => {
              if (val && val.summary && val.summary.P_CONCLUSIONS) {
                val.summary.P_CONCLUSIONS = this.unhtml(val.summary.P_CONCLUSIONS)
              }
            })
          }
        }
        this.reportData = res.data

        this.$nextTick(() => {
          JsBarcode('#barcode', this.reportData.head.parameters.patientcode, {
            format: 'CODE39', //条形码的格式
            width: 1, //线宽
            height: 30, //条码高度
            fontSize: 16,
            lineColor: '#000', //线条颜色
            displayValue: false, //是否显示文字
            margin: 2, //设置条形码周围的空白区域
          })
          if (this.reportData.ysbg) this.$refs.privateReport.setBarcode()
          this.loading.close()
        })
      })
    },
    // 正则特殊符号
    unhtml(str) {
      return str
        ? str.replace(/[<">']/g, (a) => {
            return {
              '<': '&lt;',
              '"': '&quot;',
              '>': '&gt;',
              "'": '&#39;',
            }[a]
          })
        : ''
    },
  },
}
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

.preview-individual-temporary-report {
  // background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;
  .only-scale {
    transform: scale(1.5);
    transform-origin: top;
  }

  .only-print {
    display: none;
  }
}

.individual-report {
  width: 210mm;
  margin: 0 auto;

  * {
    font-family: 'song';
  }

  .report-cover {
    page-break-after: always;
    height: 297mm;
    background: #fff;
    padding: 40px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .cover-header {
      display: flex;
      align-items: end;

      .header-left {
        padding-left: 70px;
        margin-bottom: 80px;
        flex: 1;

        .title {
          font-size: 56px;
          line-height: 80px;
          text-align: center;
          letter-spacing: 0.3em;
          color: #d0aa79;
          text-align: center;
        }

        .subtitle {
          font-size: 20px;
          line-height: 29px;
          text-align: center;
          letter-spacing: 0.1em;
          color: #d0aa79;
          margin-top: 10px;
          text-align: center;
        }
      }

      .header-right {
        .logo {
          img {
            width: 70px;
            height: auto;
          }

          &::before,
          &::after {
            content: '';
            display: block;
            width: 45px;
            margin: 0 auto;
            height: 6px;
          }

          &::before {
            border-top: 1px solid #000000;
          }

          &::after {
            border-bottom: 1px solid #000000;
          }
        }

        .tag {
          margin-top: 12px;

          .tag-icon {
            .garden {
              margin: 0 auto;
              border-radius: 50%;
              width: 22px;
              border-top: 22px solid #c13a1d;
              position: relative;
              z-index: 2;

              &:last-child {
                border-color: #ebe4d1;
                margin-top: -10px;
                z-index: 1;
              }
            }
          }

          .tag-text {
            text-align: center;
            margin-top: 12px;
            font-weight: 200;
            font-size: 16px;
            line-height: 200%;
            text-align: center;
            color: #333333;
          }
        }
      }
    }

    .cover-content {
      .content-info {
        width: 400px;
        margin: 0 auto;
        display: flex;
        align-items: center;

        .info-title {
          width: 80px;
          font-size: 16px;
          line-height: 40px;
          height: 40px;
          display: flex;

          span {
            display: inline-block;
            text-align: justify;
            flex: 1;
          }

          span::after {
            content: '';
            display: inline-block;
            width: 100%;
          }
        }

        .info-cont {
          border-bottom: 1px solid #000;
          flex: 1;
          min-height: 40px;
          display: flex;
          align-items: center;

          span {
            font-size: 16px;
            line-height: 40px;
          }

          .barcode {
            width: 140px;
            height: 30px;
            display: inline-block;
            margin-left: 12px;
          }
        }
      }
    }

    .cover-bottom {
      display: flex;
      justify-content: space-between;
      width: 580px;
      margin: 0 auto;

      span {
        font-size: 14px;
        line-height: 20px;
        color: #333333;
      }
    }
  }

  .report-main {
    page-break-after: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px;
  }

  .report-end {
    margin: 20px auto 0;
    background: #fff;
    padding: 40px;
    height: 297mm;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .end-logo {
      width: auto;
      height: 260px;
      object-fit: contain;
    }

    .end-code {
      width: 150px;
      height: 150px;
      object-fit: contain;
      margin-top: 12px;
    }
  }

  .detection-report {
    page-break-after: always;
    background: #fff;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .report-item {
      height: 293mm;
      text-align: center;
      page-break-before: always;

      img {
        width: 100%;
        padding-top: 20px;
        margin: 20px 0 0;
        page-break-inside: avoid;
      }
    }
  }
  .report-private {
    page-break-before: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px;
  }
}

// 分页打印的问题
// table {
//   width: 100%;
//   page-break-inside: avoid;
//   page-break-after: avoid;
//   page-break-before: avoid;
// }

@media print {
  .preview-individual-temporary-report {
    padding: 0;
    background: #fff;

    .only-scale {
      display: none;
    }
    .only-print {
      display: block;
    }
  }

  .report-main,
  .report-private {
    margin-top: 0 !important;
    padding: 0 40px !important;
  }

  .report-cover {
    padding: 0 !important;
  }
  .report-cover,
  .report-cover-vvip,
  .detection-report,
  .report-preface,
  .report-end,
  .report-end-vvip {
    margin: 0 !important;
    position: relative;
    z-index: 99;
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }

  @page {
    margin: 28px 20px;
  }
}
</style>
