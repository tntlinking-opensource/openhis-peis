<!-- 对比报告 开发人：麦沃德科技半夏 -->
<template>
  <div>
    <div class="preview-contrast-report">
      <div class="contrast-report" v-if="reportData">
        <!-- 页眉 -->
        <div class="header">
          <span>{{ reportData.patientname }}</span>
        </div>
        <!-- 封面 -->
        <div class="report-cover">
          <div class="cover-header">
            <img src="@/assets/logo/zk_logo.png" />
            <h2>体检对比报告</h2>
          </div>
          <div class="cover-info">
            <div class="info-column">
              <div class="info-title"><span>姓名</span>：</div>
              <div class="info-cont">{{ reportData.patientname }}</div>
            </div>
            <div class="info-column">
              <div class="info-title"><span>性别</span>：</div>
              <div class="info-cont">{{ reportData.sex }}</div>
            </div>
            <div class="info-column">
              <div class="info-title"><span>报告日期</span>：</div>
              <div class="info-cont">{{ reportData.reportdate }}</div>
            </div>
          </div>
        </div>
        <!-- 内容 -->
        <table style="width: 100%">
          <thead>
            <tr>
              <td>
                <div class="page-header"></div>
              </td>
            </tr>
          </thead>
          <tfoot>
            <tr>
              <td>
                <div class="page-footer"></div>
              </td>
            </tr>
          </tfoot>
          <tbody>
            <tr>
              <td>
                <!-- 内容-总结 -->
                <div class="report-summary">
                  <div class="summary-box">
                    <div class="summary-title" data-content="个人历届体检对比报告">个人历届体检对比报告</div>
                    <div class="summary-info">
                      <table border="0" cellspacing="0">
                        <tr>
                          <td style="width: 14%" class="td-title"><span>健康档案</span></td>
                          <td style="width: 20%">{{ reportData.record }}</td>
                          <td style="width: 18%" class="td-title"><span>姓名</span></td>
                          <td style="width: 20%">{{ reportData.patientname }}</td>
                          <td style="width: 14%" class="td-title"><span>性别</span></td>
                          <td style="width: 14%">{{ reportData.sex }}</td>
                        </tr>
                        <tr>
                          <td class="td-title"><span>体检团体</span></td>
                          <td colspan="3" style="text-align: left">{{ reportData.workunit }}</td>
                          <td class="td-title"><span>婚姻情况</span></td>
                          <td>{{ reportData.marry }}</td>
                        </tr>
                        <tr>
                          <td class="td-title"><span>历届体检</span></td>
                          <td class="td-title"><span>年龄</span></td>
                          <td class="td-title"><span>体检号码</span></td>
                          <td class="td-title"><span>登记日期</span></td>
                          <td class="td-title" colspan="2"><span>套餐</span></td>
                        </tr>
                        <tr>
                          <td class="td-title">{{ reportData.registeryear }}</td>
                          <td>{{ reportData.age }}</td>
                          <td>{{ reportData.patientcode }}</td>
                          <td>{{ reportData.register }}</td>
                          <td colspan="2">{{ reportData.combo }}</td>
                        </tr>
                        <tr>
                          <td class="td-title">{{ reportData.registeryear1 }}</td>
                          <td>{{ reportData.age1 }}</td>
                          <td>{{ reportData.patientcode1 }}</td>
                          <td>{{ reportData.register1 }}</td>
                          <td colspan="2">{{ reportData.combo1 }}</td>
                        </tr>
                        <tr v-if="reportData.registeryear2">
                          <td class="td-title">{{ reportData.registeryear2 }}</td>
                          <td>{{ reportData.age2 }}</td>
                          <td>{{ reportData.patientcode2 }}</td>
                          <td>{{ reportData.register2 }}</td>
                          <td colspan="2">{{ reportData.combo2 }}</td>
                        </tr>
                      </table>
                    </div>
                    <div class="summary-conclusion">
                      <div class="conclusion-title">体检检查对比结论</div>
                      <div class="conclusion-cont" v-if="!isModify">{{ reportData.conclusion }}</div>
                      <el-input class="conclusion-cont" style="" v-else v-model="reportData.conclusion" type="textarea" resize="none" :rows="8"></el-input>
                    </div>
                  </div>
                  <div class="summary-total">
                    <div class="total-column" style="min-width: 220px">总检医生：{{ reportData.totaldoctor }}</div>
                    <div class="total-column">客服电话：{{ reportData.hotline }}</div>
                    <div class="total-column">报告日期：{{ reportData.reportdate }}</div>
                  </div>
                </div>
                <!-- 内容-科室 -->
                <div class="report-main">
                  <div class="main-column">
                    <div class="column-title">1.历届体检科室小结对比</div>
                    <div class="column-table">
                      <table class="table-title" border="0" cellspacing="0">
                        <tr>
                          <th style="width: 19%; color: #fff">体检年份</th>
                          <th style="width: 27%">{{ reportData.registeryear }}</th>
                          <th style="width: 27%">{{ reportData.registeryear1 }}</th>
                          <th style="width: 27%" v-if="reportData.registeryear2">{{ reportData.registeryear2 }}</th>
                        </tr>
                      </table>
                      <table class="table-item" border="0" cellspacing="0">
                        <tbody>
                          <tr v-for="(item, index) in reportData.conclusion_grid" :key="index">
                            <td style="width: 19%">{{ item.deptName }}</td>
                            <td style="width: 27%">{{ item.peiC }}</td>
                            <td style="width: 27%">{{ item.pei1C }}</td>
                            <td style="width: 27%" v-if="item.pei2C">{{ item.pei2C }}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <div class="main-column">
                    <div class="column-title">2.历届体检检验数据对比</div>
                    <div class="column-table">
                      <table class="table-title" border="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <th style="color: #fff">体检年份</th>
                            <th colspan="2">{{ reportData.registeryear }}</th>
                            <th colspan="2">{{ reportData.registeryear1 }}</th>
                            <th colspan="2" v-if="reportData.registeryear2">{{ reportData.registeryear2 }}</th>
                          </tr>
                          <tr>
                            <th style="width: 28%; color: #fff">检查项目</th>
                            <th style="width: 14%">检查结果</th>
                            <th style="width: 10%">标志</th>
                            <th style="width: 14%">检查结果</th>
                            <th style="width: 10%">标志</th>
                            <template v-if="reportData.registeryear2">
                              <th style="width: 14%">检查结果</th>
                              <th style="width: 10%">标志</th>
                            </template>
                          </tr>
                        </tbody>
                      </table>
                      <div>
                        <table class="table-item" border="0" cellspacing="0" v-for="(item, index) in reportData.inspection_doc" :key="index">
                          <tr>
                            <th colspan="7">{{ item.charges }}</th>
                          </tr>
                          <tr v-for="exam in item.inspect_grid" :key="exam.examitemNameR">
                            <td style="width: 28%">{{ exam.examitemNameR }}</td>
                            <td style="width: 14%">{{ exam.examitemvaluesreportP }}</td>
                            <td style="width: 10%">{{ exam.statusP }}</td>
                            <td style="width: 14%">{{ exam.examitemvaluesreportS }}</td>
                            <td style="width: 10%">{{ exam.statusS }}</td>
                            <template v-if="exam.examitemvaluesreportF">
                              <td style="width: 14%">{{ exam.examitemvaluesreportF }}</td>
                              <td style="width: 10%">{{ exam.statusF }}</td>
                            </template>
                          </tr>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div style="height: 40px; width: 100%" class="placeholder"></div>
      <div class="print-fixed" v-if="!isModify">
        <el-button type="primary" @click="handlePrint()">打 印</el-button>
      </div>
      <div class="print-fixed" v-else>
        <el-button type="primary" @click="modifyReport">确认修改</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import { getReportData, updateReportData } from '@/api/preview/contrast_report.js'
export default {
  name: 'ContrastReport',
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 查询参数
      queryParams: {
        dh: undefined,
        isJy: 0,
        patientcode: undefined,
        showAllImage: 0,
      },
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: {
        conclusion: '',
      },
      // 打印头部
      showF: true,
      // 打印内容
      showC: true,
      // 是否为修改报告
      isModify: false,
    }
  },
  created() {
    document.title = '对比报告'
    this.queryParams = this.$route.query
    this.queryParams.reportType = Number(this.queryParams.reportType)
    this.isModify = this.queryParams.isModify
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
  },
  methods: {
    // 获取报告数据
    getReport() {
      getReportData(this.queryParams).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, '提醒')
          this.loading.close()
          return
        }
        this.reportData = res.data
        this.loading.close()
      })
    },
    //打印按钮
    handlePrint() {
      window.print()
    },
    // 修改按钮
    modifyReport() {
      this.$confirm('是否确认修改当前报告?', '提示')
        .then(() => {
          this.loading = this.$loading({
            lock: true,
            text: '加载中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          updateReportData({ ...this.queryParams, content: this.reportData })
            .then(() => {
              this.$modal.msgSuccess('修改成功', '提示')
              this.loading.close()
            })
            .catch(() => {
              this.loading.close()
            })
        })
        .catch(() => { })
    },
  },
}
</script>

<style lang="scss" scoped>
.preview-contrast-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .contrast-report {
    width: 210mm;
    margin: 0 auto;

    * {
      font-family: 'song';
    }

    .report-cover {
      height: 297mm;
      background: #fff;
      padding: 40px 80px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .cover-header {
        img {
          width: auto;
          height: 66px;
        }

        h2 {
          margin: 150px 0 0;
          text-align: center;
          font-size: 62px;
          color: #22ade2;
          font-weight: 600;
        }
      }

      .cover-info {
        margin: 0 auto 180px;

        .info-column {
          display: flex;
          align-items: center;

          .info-title {
            font-size: 18px;
            font-weight: bold;

            span {
              display: inline-block;
              width: 80px;
              text-align: justify;
              text-align-last: justify;
            }
          }

          .info-cont {
            font-size: 18px;
            line-height: 46px;
          }
        }
      }
    }

    .report-summary {
      page-break-before: always;
      margin: 20px auto 0;
      background: #fff;
      padding: 40px 80px 60px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: flex-end;
      min-height: calc(297mm - 130px);

      .summary-box {
        width: 100%;
      }

      .summary-title {
        font-size: 34px;
        color: #780462;
        text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.6);
        font-weight: 900;
        text-stroke: 2px #fff;
        -webkit-text-stroke: 2px #fff;

        &[data-content]::before {
          content: attr(data-content);
          position: absolute;
          -webkit-text-stroke: 0;
          color: #780462;
        }
      }

      .summary-info {
        margin-top: 16px;

        table {
          border: 1px solid #22ade2;
          text-align: center;
          width: 100%;

          tr {
            td {
              border: 1px solid #22ade2;
              padding: 2px 4px;
              font-size: 16px;
              font-weight: bold;
              word-break: break-all;

              &.td-title {
                background: #22ade2;
                -webkit-print-color-adjust: exact;
                -moz-print-color-adjust: exact;
                -ms-print-color-adjust: exact;
                print-color-adjust: exact;

                span {
                  display: inline-block;
                  width: 66px;
                  text-align: justify;
                  text-align-last: justify;
                  color: #fff;
                }
              }
            }
          }
        }
      }

      .summary-conclusion {
        margin-top: 40px;

        .conclusion-title {
          font-size: 20px;
          font-weight: bold;
        }

        .conclusion-cont {
          margin-top: 10px;
          font-size: 16px;
        }
      }

      .summary-total {
        .total-column {
          font-size: 18px;
          line-height: 40px;
        }
      }
    }

    .report-main {
      page-break-before: always;
      margin: 20px auto 0;
      background: #fff;
      padding: 40px 80px;

      .main-column {
        margin-top: 16px;
        padding-bottom: 40px;

        .column-title {
          font-size: 22px;
          font-weight: bold;
        }

        .column-table {
          .table-title {
            width: 100%;
            margin-top: 12px;

            th {
              background: #22ade2;
              -webkit-print-color-adjust: exact;
              -moz-print-color-adjust: exact;
              -ms-print-color-adjust: exact;
              print-color-adjust: exact;
              padding: 2px 4px;
              font-size: 16px;
              font-weight: bold;
            }
          }

          .table-item {
            width: 100%;
            border: 1px solid #22ade2;
            margin-top: 32px;

            th {
              background: #22ade2;
              -webkit-print-color-adjust: exact;
              -moz-print-color-adjust: exact;
              -ms-print-color-adjust: exact;
              print-color-adjust: exact;
              padding: 2px 8px;
              font-size: 14px;
              line-height: 22px;
              word-break: break-all;
              text-align: left;
            }

            td {
              border: 1px solid #22ade2;
              padding: 2px 8px;
              font-size: 14px;
              line-height: 22px;
              word-break: break-all;
              white-space: pre-wrap;
              vertical-align: top;
            }
          }
        }
      }
    }
  }

  // 分页打印的问题
  table {
    width: 100%;
    page-break-inside: avoid;
    page-break-after: avoid;
    page-break-before: avoid;
  }
}

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

.header {
  display: none;
}

@media print {
  .preview-contrast-report {
    background: #fff;
    padding: 0;
  }

  .report-cover {
    margin: 0 !important;
    position: relative;
    z-index: 99;
  }

  .report-main,
  .report-summary {
    margin-top: 0 !important;
    padding-top: 0 !important;
    padding-bottom: 0 !important;
  }

  .report-summarize {
    margin-top: 0 !important;
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }

  .header,
  .page-header {
    height: 40px;
    margin-bottom: 32px;
  }

  .page-footer {
    height: 40px;
  }

  .header {
    position: fixed;
    top: 0;
    left: 60px;
    right: 60px;
    z-index: 9;
    border-bottom: 2px solid #333333;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    font-size: #333;
    font-weight: normal;
  }

  @page {
    margin: 0;
  }
}
</style>
