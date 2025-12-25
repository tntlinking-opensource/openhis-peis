<!-- 隐私报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-module-private">
    <div class="private-info">
      <div class="info-title">【基本信息】</div>
      <img class="info-barcode" id="barcode" />
      <div class="info-table">
        <div class="table-box">
          <table border="0" cellspacing="0">
            <tr>
              <td>
                <div class="td-title">体检号码</div>
                <span>{{ reportData.head.patientcode }}</span>
              </td>
              <td>
                <div class="td-title">姓名</div>
                <span>{{ reportData.head.patientname }}</span>
              </td>
              <td>
                <div class="td-title">性别</div>
                <span>{{ reportData.head.sex }}</span>
              </td>
              <td>
                <div class="td-title">年龄</div>
                <span>{{ reportData.head.age }}</span>
              </td>
              <td>
                <div class="td-title">婚姻</div>
                <span>{{ reportData.head.marry }}</span>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="td-title">健康档案号码</div>
                <span>{{ reportData.head.archiveno }}</span>
              </td>
              <td colspan="3">
                <div class="td-title">登记时间</div>
                <span>{{ reportData.head.dateregister }}</span>
              </td>
            </tr>
            <tr>
              <td colspan="5">
                <div class="td-title">体检单位名称：</div>
                <span>{{ reportData.head.orgName }}</span>
              </td>
            </tr>
          </table>
        </div>
        <img class="table-picture" :src="imgPath + reportData.head.picture" />
      </div>
    </div>
    <div class="private-table">
      <div class="table-head">
        <div class="head-tag"><b>说明：</b>此检验结果仅对本次标本负责！</div>
        <table border="0" cellspacing="0">
          <tr>
            <th class="table-title" colspan="5">
              <span>【检验科】</span>
              <span>检查结果</span>
            </th>
          </tr>
          <tr>
            <th style="width: 28%">检验项目</th>
            <th style="width: 20%">结果</th>
            <th style="width: 16%">标志</th>
            <th style="width: 20%">参考值</th>
            <th style="width: 16%">单位</th>
          </tr>
        </table>
      </div>
      <div class="table-exams" v-for="(item, index) in reportData.examdata" :key="index">
        <div class="exams-info">
          <span>{{ item.itemName }}</span>
          <span>审核日期：{{ item.auditDate }}</span>
          <span>审核人：{{ item.auditName }}</span>
        </div>
        <table border="0" cellspacing="0">
          <tr :style="setAbnormal(exam.CONSULT, exam.RESULT, exam.red) ? 'background: #FFDAB9;-webkit-print-color-adjust: exact;-moz-print-color-adjust: exact;-ms-print-color-adjust: exact;print-color-adjust: exact;' : ''" v-for="(exam, examIndex) in item.exams" :key="examIndex">
            <td style="width: 28%">{{ exam.ITEM }}</td>
            <td style="width: 20%">{{ exam.RESULT }}</td>
            <td style="width: 16%">{{ exam.CONSULT }}</td>
            <td style="width: 20%">{{ exam.UNIT }}</td>
            <td style="width: 16%">{{ exam.SIGN }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div class="private-result">
      <span><b>小结：</b>{{ reportData.head.conclusions }}</span>
    </div>
    <div class="private-tips" v-if="reportData.modelName == 'private_report.docx'">
      <h3>结果提示：</h3>
      <p>①、乙肝表面抗体阳性：是一种保护性抗体。阳性表示既往感染过乙肝病毒且已产生免疫力或接种乙肝疫苗后产生免疫效果。</p>
      <p>②、乙肝五项全阴性：说明您对乙肝病毒没有免疫力，容易被乙肝病毒感染，建议注射乙肝疫苗。</p>
      <p>③、乙肝五项(第1,5项阳性)：急性或慢性乙肝、乙肝病毒复制减弱。</p>
      <p>④、乙肝五项(第1,3,5项阳性)：俗称“大三阳”，提示急性或慢性乙肝病毒复制活跃，传染性强。</p>
      <p>⑤、乙肝五项(第1,4,5项阳性)：急性或慢性乙肝、乙肝病毒复制减弱，俗称“小三阳”。建议进一步做ＤＮＡ检测病毒复制情况。</p>
      <p>⑥、乙肝五项(第2,4,5项阳性)：乙肝恢复期或既往有轻度乙肝病毒感染史，不具有传染性。</p>
      <p>⑦、乙肝五项(第2,5项阳性)：表示曾受乙肝病毒感染或接种乙肝疫苗，已有免疫力。</p>
      <p>⑧、乙肝五项(第4,5项阳性)：表示乙肝病毒感染恢复期或既往感染史。建议定期检查。</p>
      <p>⑨、乙肝五项(第5项阳性)：表示乙肝病毒既往感染史，少数为正处于急性感染的潜伏期或乙肝隐性携带者。可结合临床及病史综合判断，定期随访。</p>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import JsBarcode from 'jsbarcode'
export default {
  props: ['reportData'],
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
    }
  },
  methods: {
    // 设置二维码
    setBarcode() {
      JsBarcode('#barcode', this.reportData.head.patientcode, {
        format: 'CODE39', //条形码的格式
        width: 1, //线宽
        height: 40, //条码高度
        fontSize: 16,
        lineColor: '#000', //线条颜色
        displayValue: true, //是否显示文字
        margin: 2, //设置条形码周围的空白区域
      })
    },
    // 返回异常指标
    setAbnormal(consult, result, red) {
      if (consult == '↑' || consult == '↑' || result.indexOf('+') != -1 || result.indexOf('阳性') != -1 || red == 1) return true
      else return false
    },
  },
}
</script>

<style lang="scss" scoped>
.preview-individual-module-private {
  width: 100%;
  .private-info {
    .info-title {
      background: #ffff99;
      -webkit-print-color-adjust: exact;
      -moz-print-color-adjust: exact;
      -ms-print-color-adjust: exact;
      print-color-adjust: exact;
      border: 1px solid #ffde36;
      word-break: break-all;
      padding: 8px 12px;
    }

    .info-barcode {
      margin: 12px auto;
      display: block;
    }

    .info-table {
      display: flex;
      align-items: center;

      .table-box {
        flex: 1;
        border: 1px solid #05b153;
        padding: 1px;

        table {
          width: 100%;
          border: 1px solid #05b153;
          border-collapse: collapse;

          tr {
            td {
              word-break: break-all;
              border: 1px solid #05b153;
              padding: 8px 12px;

              .td-title {
                font-size: 12px;
                line-height: 17px;
                color: #8bb449;
              }

              span {
                font-weight: 600;
                font-size: 14px;
                line-height: 18px;
                color: #333333;
                display: inline-block;
                margin-top: 8px;
                width: 100%;
                height: 18px;
              }
            }
          }
        }
      }

      .table-picture {
        width: 130px;
        height: 164px;
        object-fit: cover;
        border: 1px solid #c1c1c1;
        margin-left: 8px;
      }
    }
  }

  .private-table {
    margin-top: 20px;

    .table-head {
      .head-tag {
        font-size: 15px;
        line-height: 18px;
        margin-bottom: 8px;
      }

      table {
        width: 100%;
        text-align: center;
        border-collapse: collapse;

        .table-title {
          width: 100%;
          background: #ffff99;
          -webkit-print-color-adjust: exact;
          -moz-print-color-adjust: exact;
          -ms-print-color-adjust: exact;
          print-color-adjust: exact;
          border: 2px solid #ffde36;
          padding: 0;

          span {
            width: 50%;
            display: inline-block;
            word-break: break-all;
            padding: 8px 12px;
          }
        }

        th {
          padding: 4px 8px;
          font-size: 14px;
          font-weight: 500;
          line-height: 18px;
          border: 2px solid #06ff06;
          border-top: none;
          word-break: break-all;
        }
      }
    }

    .table-exams {
      margin-top: 24px;

      .exams-info {
        font-size: 14px;
        line-height: 18px;
        margin-bottom: 6px;
      }

      table {
        width: 100%;
        text-align: center;
        border: 1px solid #000;

        td {
          padding: 4px 8px;
          font-size: 14px;
          line-height: 18px;
          border: 1px solid #000;
          word-break: break-all;
        }
      }
    }
  }

  .private-result {
    margin-top: 8px;
    font-size: 15px;
    line-height: 22px;
  }

  .private-tips {
    margin-top: 40px;

    h3 {
      font-size: 15px;
      line-height: 22px;
      margin: 0;
      font-weight: bold;
      margin-bottom: 6px;
    }

    p {
      font-size: 13px;
      line-height: 22px;
      margin: 0;
    }
  }
}
</style>
