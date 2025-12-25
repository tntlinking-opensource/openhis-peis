<!-- 检验报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-inspect-report">
    <div class="individual-report" v-if="reportData">
      <!-- 页脚 -->
      <div :class="isHalf == 1 ? 'harf-footer' : 'footer'">
        <div style="width: 100%">
          <div class="audit-info">
            <div class="audit-item" v-for="item in 4" :key="item">
              <div class="audit-title">{{ item == 1 ? '核收时间' : item == 2 ? '检验者' : item == 3 ? '审核者' : '审核时间' }}:</div>
              <div class="audit-content">{{ item == 1 ? reportData.rummagerTime : item == 2 ? reportData.rummager : item == 3 ? reportData.auditer : reportData.auditTime }}</div>
            </div>
          </div>
          <div class="audit-tips">
            <div>注：此报告只对本次样本负责，如有异议，请在七工作日内提出复查！</div>
            <div>质评合格 省内参考</div>
          </div>
        </div>
      </div>
      <table style="width: 100%">
        <tfoot>
          <tr>
            <td>
              <div :class="isHalf == 1 ? 'harf-page-footer' : 'page-footer'"></div>
            </td>
          </tr>
        </tfoot>
        <thead>
          <tr>
            <td>
              <div class="page-header"></div>
            </td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <!-- 检验报告 -->
              <div class="report-main">
                <div class="main-title print-show">{{ reportData.title.centerId }}</div>
                <div class="main-title print-hide">
                  <el-input v-model="reportData.title.centerId" style="width: 300px" @blur="changeName"></el-input>
                </div>
                <div class="main-info">
                  <table border="0" cellspacing="0">
                    <tr v-for="item in 3" :key="item">
                      <td style="width: 20%">
                        <div class="flex">
                          <div class="td-title first-title">
                            <span>{{ item == 1 ? '姓名' : item == 2 ? '性别' : '年龄' }}：</span>
                          </div>
                          <div class="td-cont">{{ item == 1 ? reportData.patientname : item == 2 ? reportData.sex : reportData.age }}</div>
                        </div>
                      </td>
                      <td style="width: 29%">
                        <div class="flex">
                          <div class="td-title second-title">
                            <span>{{ item == 1 ? '科别' : item == 2 ? '床号' : '门诊号' }}：</span>
                          </div>
                          <div class="td-cont">{{ item == 3 ? reportData.patientcode : '/' }}</div>
                        </div>
                      </td>
                      <td style="width: 22%">
                        <div class="flex">
                          <div class="td-title">
                            <span>{{ item == 1 ? '样本类型' : item == 2 ? '送检医生' : '临床诊断' }}：</span>
                          </div>
                          <div class="td-cont">{{ item == 1 ? '血液' : '/' }}</div>
                        </div>
                      </td>
                      <td style="width: 29%">
                        <div class="flex">
                          <div class="td-title">
                            <span>{{ item == 1 ? '样本编号' : item == 2 ? '检验备注' : '送检单位' }}：</span>
                          </div>
                          <div class="td-cont">{{ item == 1 ? reportData.lisybbh : item == 2 ? '/' : reportData.title.inspectionUnit }}</div>
                        </div>
                      </td>
                    </tr>
                  </table>
                </div>
                <div class="item-list">
                  <table class="">
                    <thead>
                      <tr>
                        <th style="width: 40%; text-align: left">项目名称</th>
                        <th style="width: 12%; text-align: left">结果</th>
                        <th style="width: 12%; text-align: left">正常</th>
                        <th style="width: 24%; text-align: left">参考区间</th>
                        <th style="width: 12%; text-align: left">单位</th>
                      </tr>
                    </thead>
                  </table>
                  <div style="border-bottom: 3px solid #000"></div>
                  <table>
                    <tbody>
                      <tr v-for="(item, index) in reportData.exams" :key="index">
                        <td style="width: 40%">{{ item.examTotalName }}</td>
                        <td style="width: 12%">{{ item.examitemvaluesreport }}</td>
                        <td style="width: 12%">{{ item.arrow }}</td>
                        <td style="width: 24%">{{ item.refrange }}</td>
                        <td style="width: 12%">{{ item.units }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <!-- <section-report :show-data="reportData.ksList" type-data="0"></section-report> -->
              </div>
              <!-- 隐私报告 -->
              <div class="report-private" v-if="reportData.ysbg">
                <private-report ref="privateReport" :report-data="reportData.ysbg"></private-report>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div :style="isMobile() ? 'height: 82px;' : ''" class="placeholder"></div>
    <div class="print-fixed">
      <el-button class="mobile" type="primary" @click="handlePrint()" v-if="isMobile()">下 载</el-button>
      <el-button type="primary" @click="handlePrint()" v-else>打 印</el-button>
    </div>
  </div>
</template>
<script>
import { getData } from '@/api/preview/individual_report.js'
import sectionReport from './module/section'
import privateReport from './module/private'
export default {
  name: 'IndividualInspectReport',
  components: { sectionReport, privateReport },
  data() {
    return {
      // 查询参数
      queryParams: {
        dh: 0,
        isJy: 1,
        patientcode: null,
        showAllImage: 0,
      },
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: {
        title: {},
      },
      // 中心名称
      centerName: '青岛沃德国际体检中心',
      // 是否半张A4
      isHalf: false,
    }
  },
  created() {
    document.title = '检验报告'
    this.queryParams.patientcode = this.$route.query.patientcode
    this.queryParams.idExamtype = this.$route.query.idExamtype
    this.isHalf = this.$route.query.isHalf == 1 ? true : false
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
    let centerName = localStorage.getItem('centerName')
    if (centerName) {
      this.centerName = centerName
    }
  },
  mounted() {
    const metaTags = document.getElementsByTagName('meta')
    for (let i = 0; i < metaTags.length; i++) {
      if (metaTags[i].getAttribute('name') === 'viewport') {
        metaTags[i].setAttribute('content', 'width=device-width, initial-scale=0.42, maximum-scale=1.0')
        break
      }
    }
  },
  methods: {
    // 判断是否为手机
    isMobile() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag
    },
    // 中心名称改变
    changeName() {
      localStorage.setItem('centerName', this.centerName)
    },
    //打印按钮
    handlePrint() {
      window.print()
    },
    // 获取报告数据
    getReport() {
      let obj = {
        idExamtype: this.queryParams.idExamtype, //体检类型,0.健康体检 1.职业体检 只有健康报告有隐私报告
        patientcode: this.queryParams.patientcode, //体检号
        reportType: 9, //报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 6.对比报告 7.Pacs报告
      }
      getData(obj).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, '提醒')
          this.loading.close()
          return
        }
        this.reportData = res.data
        this.$nextTick(() => {
          if (this.reportData.ysbg) this.$refs.privateReport.setBarcode()
          this.loading.close()
        })
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.print-fixed {
  position: fixed;
  left: 50%;
  bottom: 20px;
  transform: translateX(-50%);
  text-align: center;
  width: 260px;
  height: 35px;
  cursor: pointer;
  z-index: 999;

  .mobile {
    transform: scale(1.8);
    transform-origin: bottom center;
  }
}

.preview-individual-inspect-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .placeholder {
    width: 100%;
    height: 50px;
  }
}

.individual-report {
  width: 210mm;
  margin: 0 auto;

  * {
    font-family: 'song';
  }

  .report-main {
    margin: 0 auto;
    background: #fff;
    padding: 40px;

    .main-title {
      font-size: 22px;
      font-weight: bold;
      text-align: center;
    }

    .main-info {
      margin-top: 16px;
      border-top: 3px solid #000;
      border-bottom: 3px solid #000;

      table {
        width: 100%;

        td {
          padding: 6px 0;

          .flex {
            display: flex;
            align-items: center;

            .td-title {
              width: 110px;
              font-size: 17px;
              line-height: 22px;
              height: 22px;
              display: flex;

              &.first-title {
                width: 70px;
              }
              &.second-title {
                width: 85px;
              }

              span {
                display: inline-block;
                text-align: left;
              }

              span::after {
                content: '';
                display: inline-block;
                width: 100%;
              }
            }

            .td-cont {
              flex: 1;
              padding-right: 5px;
              font-size: 17px;
              line-height: 22px;
            }
          }
        }
      }
    }
  }

  .report-private {
    page-break-before: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px;
    min-height: 297mm;
  }
}

.audit-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  .audit-item {
    display: flex;
    font-size: 10px;
  }
}
.audit-tips {
  display: flex;
  justify-content: space-between;
  font-size: 10px;
}

// 分页打印的问题
table {
  width: 100%;
  page-break-inside: auto;
  page-break-after: avoid;
  page-break-before: avoid;
}
.print-show,
.header,
.footer,
.harf-footer {
  display: none;
}

@media print {
  .report-main {
    padding-top: 10px;
  }
  .print-hide {
    display: none;
  }
  .print-show {
    display: block;
  }

  .preview-individual-inspect-report {
    padding: 0;
    background: #fff;
  }

  .report-main {
    padding-top: 0 !important;
    padding-bottom: 0 !important;
    margin-top: 0 !important;
  }

  .report-private {
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

  .header,
  .page-header {
    height: 30px;
    margin-bottom: 10px;
  }

  .footer,
  .page-footer {
    height: 50px;
    margin-top: 20px;
  }

  .header {
    position: fixed;
    top: 0;
    left: 60px;
    right: 60px;
    border-bottom: 2px solid #333333;
    z-index: 9;
    font-size: 14px;
    line-height: 22px;
    font-size: #333;
    font-weight: normal;
    text-align: center;
    height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      position: absolute;
      top: 15px;
      left: 0;
      width: auto;
      height: 50px;
    }
  }

  .footer {
    position: fixed;
    bottom: 0;
    left: 60px;
    right: 60px;
    z-index: 9;
    border-top: 1px solid #333333;
    font-size: 14px;
    color: #333;
    font-weight: normal;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .harf-footer {
    position: fixed;
    bottom: 0;
    left: 60px;
    right: 60px;
    z-index: 9;
    display: flex;
    justify-content: center;
    height: 160mm;
    border-top: 2px solid #333333;
    font-size: 14px;
    color: #333;
    font-weight: normal;
  }

  @page {
    margin: 0;
  }
}
</style>
