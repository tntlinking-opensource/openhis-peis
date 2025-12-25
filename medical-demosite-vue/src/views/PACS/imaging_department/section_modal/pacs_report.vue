<!-- pacs报告 开发人：麦沃德科技半夏 -->
<template>
  <div>
    <div class="preview-pacs-report">
      <div>
        <div class="contrast-report">
          <div class="header">
            <div class="set-flex"></div>
          </div>
          <div class="footer">
            <div style="display: flex; justify-content: space-between; width: 100%">
              <div class="">此报告仅供临床参考，不作证明使用</div>
              <div class="">报告时间: {{ reportData.auditdate }}</div>
            </div>
          </div>
          <table>
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
                  <!-- 内容-科室 -->
                  <div class="report-main">
                    <div class="title">
                      <img :src="require('@/assets/logo/logo2.png')" alt="" style="width: 80px" />
                      <h2>沃德国际健康医疗体检中心影像科{{ reportData.dep }}报告单沃德国际健康医疗体检单</h2>
                    </div>
                    <div class="user-info">
                      <div class="row" style="margin-top: 10px; font-weight: 600">
                        姓&emsp;&emsp;名 <span class="value" style="min-width: 100px">: {{ reportData.patientname }} </span> 性&emsp;&emsp;别<span class="value" style="min-width: 50px">: {{ reportData.sex }} </span>年&emsp;&emsp;龄<span class="value" style="min-width: 50px"
                          >: {{ reportData.age }} 岁
                        </span>
                        检查科别
                        <span class="value" style="min-width: 50px">: {{ reportData.kb }}</span>
                      </div>
                      <div class="row" style="font-weight: 600">
                        体检号 <span class="value" style="min-width: 100px">: {{ reportData.patientcode }} </span> 登记号<span class="value" style="min-width: 50px">: {{ reportData.djh }} </span>检查时间<span class="value" style="min-width: 50px">: {{ reportData.examdate }} </span>
                      </div>
                      <div class="row" style="font-weight: 600">{{ reportData.parts }}</div>
                      <!-- 体检图片 -->
                      <div style="display: flex; flex-wrap: wrap; justify-content: space-between; width: 100%">
                        <img style="width: 48%; margin-bottom: 10px" :src="item" alt="" v-for="(item, index) in reportData.pic" :key="index" />
                        <div v-if="reportData.pic&&reportData.pic.length % 2 != 0"></div>
                      </div>
                      <div class="row" :class="index % 2 == 0 ? 'row-title' : 'row-content'" v-for="(item, index) in reportData.content" :key="index">{{ item }}</div>
                      <div class="row" style="display: flex; justify-content: space-between">
                        <div style="display: flex">
                          <div style="font-weight: 600">报告医生:</div>
                          <div style="min-width: 100px; margin-left: 10px">{{ reportData.rn || '' }}</div>
                        </div>
                        <div style="display: flex">
                          <div style="font-weight: 600">审核医生:</div>
                          <div style="min-width: 100px; margin-left: 10px">{{ reportData.an || '' }}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div style="height: 40px; width: 100%" class="placeholder"></div>
      <div class="print-fixed">
        <el-button type="primary" @click="handlePrint">打印</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ContrastReport',
  data() {
    return {
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: {},
    }
  },
  created() {
    document.title = '报告预览'
    this.reportData = this.$route.query
  },
  methods: {
   
    //打印按钮
    handlePrint() {
      this.$nextTick(() => {
        window.print()
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.preview-pacs-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .contrast-report {
    width: 210mm;
    min-height: calc(291mm);
    margin: 0 auto;
    background: #fff;

    * {
      font-family: 'song';
    }

    .report-main {
      margin: 20px auto 0;
      background: #fff;
      padding: 0 40px;

      .title {
        display: flex;
        align-items: center;
        width: 100%;
        padding-bottom: 10px;
        border-bottom: 2px #000 solid;

        h2 {
          font-size: 30px;
          font-weight: 700;
          text-align: center;
        }
      }

      .user-info {
        font-size: 20px;
        .row {
          display: flex;
          flex-wrap: wrap;
          align-items: baseline;
          // margin-top: 10px;
          line-height: 40px;
          .value {
            margin: 0 10px;
            // text-decoration: underline;
            border-bottom: 1.5px #000 solid;
          }
        }
        .row-title {
          margin-top: 30px;
          font-weight: 600;
        }
        .row-content {
          min-height: 200px;
        }
      }
    }
  }

  // 分页打印的问题
  table {
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
}

.header,
.footer {
  display: none;
}

@media print {
  .preview-pacs-report {
    background: #fff;

    .contrast-report {
      min-height: auto;
      .report-main {
        min-height: calc(297mm - 40px);
      }
    }
  }

  .placeholder {
    display: none;
  }
  .print-fixed {
    display: none;
  }
  .page-header {
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .page-footer {
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .header {
    text-align: center;
    display: block;
    position: fixed;
    top: 0;
    width: 100%;
    margin: 0 30px;
    margin-top: 10px;
    z-index: 2000;
    border-bottom: 1px solid #333333;
  }
  .footer {
    display: block;
    position: fixed;
    bottom: 0;
    z-index: 2000;
    width: 100%;
    padding: 0 10px;
    margin-bottom: 10px;
    font-size: 14px;
    border-bottom: 1px solid #333333;
  }
}
</style>
