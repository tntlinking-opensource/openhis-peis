<!-- 科室报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-section-report">
    <div class="main-report" v-if="reportData">
      <div class="report-info">
        <div class="info-title">【基本信息】</div>
        <img class="info-barcode" id="barcode">
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
                  <span>{{ reportData.head.idSex == 0 ? "男" : "女" }}</span>
                </td>
                <td>
                  <div class="td-title">年龄</div>
                  <span>{{ reportData.head.age }}</span>
                </td>
                <td>
                  <div class="td-title">婚姻</div>
                  <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][reportData.head.idMarriage] }}</span>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div class="td-title">健康档案号码</div>
                  <span>{{ reportData.head.patientarchiveno }}</span>
                </td>
                <td colspan="3">
                  <div class="td-title">登记时间</div>
                  <span>{{ reportData.head.dateregister ? reportData.head.dateregister.split(" ")[0] : '' }}</span>
                </td>
              </tr>
              <tr>
                <td colspan="5">
                  <div class="td-title">体检单位名称：</div>
                  <span>{{ reportData.head.workunit }}</span>
                </td>
              </tr>
            </table>
          </div>
          <img :src="imgPath + reportData.head.picture" class="table-picture">
        </div>
      </div>
      <!-- 职业问诊 -->
      <div v-if="queryParams.dataType == 8">
        <div class="report-table">
          <table border="0" cellspacing="0" class="job-inquiry">
            <tr>
              <th class="table-title" colspan="4">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td>文化程度</td>
              <td>{{ getCultural(reportData.peispatientConsultation.cultural) }}</td>
              <td>是否不饮酒</td>
              <td>{{ reportData.peispatientConsultation.noKissTheCup == 1 ? '是' : '否' }}</td>
            </tr>
            <tr>
              <td>既往病</td>
              <td>{{ reportData.peispatientConsultation.everOfDisease }}</td>
              <td>是否偶饮酒</td>
              <td>{{ reportData.peispatientConsultation.betweenKissTheCup == 1 ? '是' : '否' }}</td>
            </tr>
            <tr>
              <td>初潮年龄</td>
              <td>{{ reportData.peispatientConsultation.ccnl }}</td>
              <td>是否经常饮酒</td>
              <td>{{ reportData.peispatientConsultation.evermoreKiss == 1 ? '是' : '否' }}</td>
            </tr>
            <tr>
              <td>经期</td>
              <td>{{ reportData.peispatientConsultation.jq }}</td>
              <td>是否戒酒</td>
              <td>{{ reportData.peispatientConsultation.abstainLostKiss == 1 ? '是' : '否' }}</td>
            </tr>
            <tr>
              <td>周期</td>
              <td>{{ reportData.peispatientConsultation.zq }}</td>
              <td>经常饮酒年数</td>
              <td>{{ reportData.peispatientConsultation.kissYearN }}</td>
            </tr>
            <tr>
              <td>停经年龄</td>
              <td>{{ reportData.peispatientConsultation.tjnl }}</td>
              <td>饮酒量</td>
              <td>{{ reportData.peispatientConsultation.kissAmount }}</td>
            </tr>
            <tr>
              <td>现有子女人数</td>
              <td>{{ reportData.peispatientConsultation.familyNumber }}</td>
              <td>饮酒种类</td>
              <td>{{ reportData.peispatientConsultation.kissType }}</td>
            </tr>
            <tr>
              <td>早产</td>
              <td>{{ reportData.peispatientConsultation.zc }}</td>
              <td>家族病史</td>
              <td>{{ reportData.peispatientConsultation.familyOfDisease }}</td>
            </tr>
            <tr>
              <td>死产</td>
              <td>{{ reportData.peispatientConsultation.sc }}</td>
              <td>症状</td>
              <td>{{ reportData.peispatientConsultation.symptom }}</td>
            </tr>
            <tr>
              <td>流产</td>
              <td>{{ reportData.peispatientConsultation.lc }}</td>
              <td>参加工作时间</td>
              <td>
                {{ reportData.peispatientConsultation.workDate ? reportData.peispatientConsultation.workDate.split(" ")[0] : '' }}
              </td>
            </tr>
            <tr>
              <td>畸胎</td>
              <td>{{ reportData.peispatientConsultation.jt }}</td>
              <td>从事该工种时间</td>
              <td>
                {{ reportData.peispatientConsultation.harmDate ? reportData.peispatientConsultation.harmDate.split(" ")[0] : '' }}
              </td>
            </tr>
            <tr>
              <td>异位妊娠</td>
              <td>{{ reportData.peispatientConsultation.ywrc }}</td>
              <td>吸烟年数</td>
              <td>{{ reportData.peispatientConsultation.smokeYear }}</td>
            </tr>
            <tr>
              <td>吸烟情况</td>
              <td>{{ ['从不吸烟', '偶尔吸烟', '以往曾经吸烟，现已戒除', '经常吸烟'][reportData.peispatientConsultation.abstainSmokeNote] }}</td>
              <td rowspan="2">既往病备注</td>
              <td rowspan="2">{{ reportData.peispatientConsultation.everOfDiseaseRemark }}</td>
            </tr>
            <tr>
              <td>每天吸烟支数</td>
              <td>{{ reportData.peispatientConsultation.everydaySmokeN }}</td>
            </tr>
          </table>
        </div>
        <div class="report-table">
          <div class="report-title">☆职业史</div>
          <table border="0" cellspacing="0" class="job-inquiry-history">
            <tr>
              <th style="width: 15%">开始年月</th>
              <th style="width: 15%">截至年月</th>
              <th style="width: 20%">工作单位</th>
              <th style="width: 17%">部门</th>
              <th style="width: 16%">工种</th>
              <th style="width: 17%">有无危害因素</th>
            </tr>
            <tr v-for="(item, index) in reportData.wzZys" :key="index">
              <td>{{ item.startDate ? item.startDate.split(" ")[0] : '' }}</td>
              <td>{{ item.stopDate ? item.stopDate.split(" ")[0] : '' }}</td>
              <td>{{ item.dept }}</td>
              <td>{{ item.branch }}</td>
              <td>{{ item.typeOfWork }}</td>
              <td>{{ item.harmName || '无' }}</td>
            </tr>
          </table>
        </div>
        <div class="report-table">
          <div class="report-title">☆职业病史</div>
          <table border="0" cellspacing="0" class="job-inquiry-history">
            <tr>
              <th style="width: 38%">疾病名称</th>
              <th>诊断日期</th>
              <th style="width: 28%">诊断单位</th>
              <th>是否痊愈</th>
            </tr>
            <tr v-for="(item, index) in reportData.wzZybs" :key="index">
              <td>{{ item.occupationDiseastName }}</td>
              <td>{{ item.diagnosisDate ? item.diagnosisDate.split(" ")[0] : '' }}</td>
              <td>{{ item.diagnosisDept }}</td>
              <td>{{ item.status == 1 ? '是' : '否' }}</td>
            </tr>
          </table>
        </div>
      </div>
      <!-- 多模板科室 -->
      <div v-else-if="picType.indexOf(queryParams.dataType) > -1">
        <div class="report-table">
          <div class="table-tag" v-if="queryParams.dataType == 1"><b>说明：</b>此检验结果仅对本次标本负责！</div>
          <!-- 检验科 -->
          <table border="0" cellspacing="0" class="clinical-laboratory" v-if="queryParams.dataType == 1">
            <tr>
              <th class="table-title" colspan="5">
                <span>{{ queryParams.title }}</span>
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
            <tbody v-for="(item, index) in reportData.peispatientexamitems" :key="index">
              <tr v-if="index == 0 || item.itemName != reportData.peispatientexamitems[index - 1].itemName">
                <td colspan="5" class="td-all" :style="index == 0 ? 'border-top: 1px solid #06FF06;' : ''">
                  <div class="flex">
                    <span>{{ item.itemName }}</span>
                    <span>审核日期：{{ item.date }}</span>
                    <span>检查人：{{ item.pname }}</span>
                    <span>审核人：{{ item.auditName }}</span>
                  </div>
                </td>
              </tr>
              <tr :style="setAbnormal(item.consult, item.result, item.red) ? 'background: #FFDAB9;' : ''">
                <td>{{ item.item }}</td>
                <td>{{ item.result }}</td>
                <td>{{ item.consult }}</td>
                <td>{{ item.unit }}</td>
                <td>{{ item.sign }}</td>
              </tr>
              <tr v-if="index == reportData.peispatientexamitems.length - 1 || item.itemName != reportData.peispatientexamitems[index + 1].itemName">
                <td colspan="5" class="td-tag">
                  <div class="table-tag" v-if="queryParams.dataType == 1"><b>说明：</b>此检验结果仅对本次标本负责！</div>
                </td>
              </tr>
            </tbody>
          </table>
          <!-- 电测听 -->
          <table border="0" cellspacing="0" class="electro-audiometer" v-else-if="queryParams.dataType == 9">
            <tr>
              <th class="table-title" colspan="10">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td></td>
              <td>125HZ</td>
              <td>250HZ</td>
              <td>500HZ</td>
              <td>1KHZ</td>
              <td>2KHZ</td>
              <td>3KHZ</td>
              <td>4KHZ</td>
              <td>6KHZ</td>
              <td>8KHZ</td>
            </tr>
            <tr>
              <td>气导左耳</td>
              <td>{{ reportData.electroAudiometer.airLeft125 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft250 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft500 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft1000 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft2000 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft3000 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft4000 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft6000 }}</td>
              <td>{{ reportData.electroAudiometer.airLeft8000 }}</td>
            </tr>
            <tr>
              <td>气导右耳</td>
              <td>{{ reportData.electroAudiometer.airRight125 }}</td>
              <td>{{ reportData.electroAudiometer.airRight250 }}</td>
              <td>{{ reportData.electroAudiometer.airRight500 }}</td>
              <td>{{ reportData.electroAudiometer.airRight1000 }}</td>
              <td>{{ reportData.electroAudiometer.airRight2000 }}</td>
              <td>{{ reportData.electroAudiometer.airRight3000 }}</td>
              <td>{{ reportData.electroAudiometer.airRight4000 }}</td>
              <td>{{ reportData.electroAudiometer.airRight6000 }}</td>
              <td>{{ reportData.electroAudiometer.airRight8000 }}</td>
            </tr>
            <tr>
              <td>骨导左耳</td>
              <td>{{ reportData.electroAudiometer.boneLeft125 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft250 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft500 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft1000 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft2000 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft3000 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft4000 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft6000 }}</td>
              <td>{{ reportData.electroAudiometer.boneLeft8000 }}</td>
            </tr>
            <tr>
              <td>骨导右耳</td>
              <td>{{ reportData.electroAudiometer.boneRight125 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight250 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight500 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight1000 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight2000 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight3000 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight4000 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight6000 }}</td>
              <td>{{ reportData.electroAudiometer.boneRight8000 }}</td>
            </tr>
          </table>
          <!-- 一般检查 -->
          <table border="0" cellspacing="0" v-else-if="queryParams.dataType == 3">
            <tr>
              <th class="table-title" colspan="2">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td>收缩压(mmHg)</td>
              <td>{{ reportData.tjreg.ssy }}</td>
            </tr>
            <tr>
              <td>舒张压(mmHg)</td>
              <td>{{ reportData.tjreg.szy }}</td>
            </tr>
            <tr>
              <td>血压结论</td>
              <td v-html="reportData.tjreg.xyms"></td>
            </tr>
            <tr>
              <td>脉搏(次/分钟)</td>
              <td>{{ reportData.tjreg.mb }}</td>
            </tr>
            <tr>
              <td>身高(cm)</td>
              <td>{{ reportData.tjreg.sg }}</td>
            </tr>
            <tr>
              <td>体重(kg)</td>
              <td>{{ reportData.tjreg.tz }}</td>
            </tr>
            <tr>
              <td>体重指数</td>
              <td>{{ reportData.tjreg.bmi }};<span v-html="reportData.tjreg.bmims"></span></td>
            </tr>
            <tr v-if="reportData.tjreg.respiratoryRate != null">
              <td>呼吸频率</td>
              <td>{{ reportData.tjreg.respiratoryRate }}</td>
            </tr>
            <tr v-if="reportData.tjreg.temperature != null">
              <td>体温测量</td>
              <td>{{ reportData.tjreg.temperature }}</td>
            </tr>
            <tr v-if="reportData.tjreg.bust != null">
              <td>胸围(cm)</td>
              <td>{{ reportData.tjreg.bust }}</td>
            </tr>
          </table>
          <!-- 其他 -->
          <table border="0" cellspacing="0" v-else>
            <tr>
              <th class="table-title">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
          </table>
        </div>
        <div class="report-picture" v-if="queryParams.dataType != 9 && queryParams.dataType != 3">
          <img :src="imgPath + item.filePath" v-for="(item, index) in reportData.attachment" :key="index">
        </div>
        <div class="report-result">
          <div class="result-title">小结：</div>
          <div class="result-content" v-if="queryParams.dataType == 1">{{ reportData.conclusions }}</div>
          <div class="result-content" v-else-if="reportData.sectionResultMain">{{ reportData.sectionResultMain.conclusions }}</div>
        </div>
        <div class="report-footer" v-if="reportData.sectionResultMain && queryParams.dataType != 1">
          <div class="footer-column">
            <div class="column-title">体检时间：</div>
            <span>
              {{ reportData.sectionResultMain.auditTime ? reportData.sectionResultMain.auditTime.split(" ")[0] : '' }}
            </span>
          </div>
          <div class="footer-column">
            <div class="column-title">检查者：</div>
            <span>{{ reportData.sectionResultMain.rummagerName }}</span>
          </div>
          <div class="footer-column">
            <div class="column-title">审核者：</div>
            <span>{{ reportData.sectionResultMain.auditName }}</span>
          </div>
        </div>
        <!-- 电测听 -->
        <div class="report-picture" v-if="queryParams.dataType == 9">
          <img :src="imgPath + reportData.electroAudiometer.airImgPath">
          <img :src="imgPath + reportData.electroAudiometer.boneImgPath">
        </div>
      </div>
      <!-- 单模板科室 -->
      <div v-else>
        <div class="report-table">
          <!-- 肺功能 -->
          <table border="0" cellspacing="0" v-if="queryParams.dataType == 5">
            <tr>
              <th class="table-title" colspan="2">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td>用力肺活量（FVC）</td>
              <td>{{ reportData.lung.fvc }}</td>
            </tr>
            <tr>
              <td>1秒用力呼气容积(FEV1)</td>
              <td>{{ reportData.lung.fev }}</td>
            </tr>
            <tr>
              <td>1秒用力呼气容积/用力肺活量(FEV1%G）</td>
              <td>{{ reportData.lung.fevFvc }}</td>
            </tr>
            <tr>
              <td>最大呼气中期流速(MMEF)</td>
              <td>{{ reportData.lung.mmef }}</td>
            </tr>
            <tr>
              <td>25%呼气中段流量（FEF25%）</td>
              <td>{{ reportData.lung.feffc }}</td>
            </tr>
            <tr>
              <td>50%呼气中段流量（FEF50%）</td>
              <td>{{ reportData.lung.feffb }}</td>
            </tr>
            <tr>
              <td>75%呼气中段流量（FEF75%）</td>
              <td>{{ reportData.lung.feffa }}</td>
            </tr>
          </table>
          <!-- 其他 -->
          <table border="0" cellspacing="0" v-else>
            <tr>
              <th class="table-title" colspan="2">
                <span>{{ queryParams.title }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr v-if="reportData.describes[0]">
              <td style="font-weight: 600;" colspan="2">{{ reportData.describes[0].feeName }}</td>
            </tr>
            <tr v-for="(item, index) in reportData.describes" :key="index">
              <td>{{ item.itemName }}</td>
              <td>{{ item.inspectDescribe }}</td>
            </tr>
          </table>
        </div>
        <div class="report-result" v-if="reportData.sectionResultMain">
          <div class="result-title">小结：</div>
          <div class="result-content">{{ reportData.sectionResultMain.conclusions }}</div>
        </div>
        <div class="report-footer" v-if="reportData.sectionResultMain">
          <div class="footer-column">
            <div class="column-title">体检时间：</div>
            <span>{{ reportData.sectionResultMain.auditTime ? reportData.sectionResultMain.auditTime.split(" ")[0] : ''
            }}</span>
          </div>
          <div class="footer-column">
            <div class="column-title">检查者：</div>
            <span>{{ reportData.sectionResultMain.rummagerName }}</span>
          </div>
          <div class="footer-column">
            <div class="column-title">审核者：</div>
            <span>{{ reportData.sectionResultMain.auditName }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import { diagnosticReport, picReport, inspectReport } from '@/api/preview/section_report.js'
import { getCultural } from '@/utils/dataList.js'
import JsBarcode from 'jsbarcode'
export default {
  name: "SectionReport",
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get("imgPath"),
      // 查询参数
      queryParams: {},
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
      // 图片科室
      picType: ['1', '3', '7', '9', '10'],
      //  文化程度
      culturalList: [],
    };
  },
  created() {
    document.title = this.$route.query.title + "报告"
    this.culturalList = getCultural()
    this.queryParams = this.$route.query
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    this.getReport()
  },
  methods: {
    // 获取报告数据
    getReport() {
      var query = {
        dh: this.queryParams.dh,
        ksID: this.queryParams.ksID,
        patientcode: this.queryParams.patientcode,
      }
      if (this.queryParams.dataType == 1) {
        inspectReport(query).then((res) => {
          this.setReport(res.data)
        })
      } else if (this.picType.indexOf(this.queryParams.dataType) > -1) {
        picReport(query).then((res) => {
          this.setReport(res.data)
        })
      } else {
        diagnosticReport(query).then((res) => {
          this.setReport(res.data)
        })
      }
    },
    // 设置报告数据
    setReport(data) {
      this.reportData = data
      this.$nextTick(() => {
        JsBarcode("#barcode", data.head.patientcode, {
          format: "CODE39",//条形码的格式
          width: 1,//线宽
          height: 40,//条码高度
          fontSize: 16,
          lineColor: "#000",//线条颜色
          displayValue: true,//是否显示文字
          margin: 2//设置条形码周围的空白区域
        })
        this.loading.close();
      })
    },
    // 获取文化程度
    getCultural(val) {
      for (var i in this.culturalList) {
        if (val == this.culturalList[i].value) {
          return this.culturalList[i].label
        }
      }
    },
    // 返回异常指标
    setAbnormal(consult, result, red) {
      if (consult == "↑" || consult == "↑" || result.indexOf("+") != -1 || result.indexOf("阳性") != -1 || red == 1) return true
      else return false
    },
  }
};
</script>

<style lang="scss" scoped>
.preview-section-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .main-report {
    width: 800px;
    margin: 0 auto;
    padding: 40px;
    background: #fff;
    min-height: calc(100vh - 40px);

    .report-info {
      .info-title {
        background: #FFFF99;
        border: 1px solid #FFDE36;
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
                  color: #8BB449;
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

    .report-table {
      margin-top: 20px;

      .report-title {
        font-weight: 600;
        font-size: 14px;
        line-height: 18px;
        color: #333333;
        margin-bottom: 12px;
      }

      table {
        width: 100%;
        border-bottom: 1px solid #06FF06;

        tr {
          .table-title {
            width: 100%;
            text-align: center;
            background: #FFFF99;
            border: 1px solid #FFDE36;
            border-bottom: none;
            padding: 0;

            &:first-child {
              border-left: 1px solid #FFDE36;
            }

            span {
              width: 50%;
              display: inline-block;
              word-break: break-all;
              padding: 8px 12px;
            }
          }

          td,
          th {
            width: 50%;
            border-top: 1px solid #06FF06;
            border-right: 1px solid #06FF06;
            padding: 6px 12px;
            font-size: 14px;
            line-height: 18px;
            color: #000000;
            word-break: break-all;

            &:first-child {
              border-left: 1px solid #06FF06;
            }
          }
        }

        &.job-inquiry {
          tr td {
            width: 20%;

            &:nth-child(2n) {
              width: 30%;
            }
          }
        }

        &.clinical-laboratory {
          border-bottom: none;

          tr th,
          tr td {
            width: auto;
            text-align: center;
          }

          .td-all,
          .td-tag {
            border-left: none;
            border-right: none;
            padding: 12px;

            .flex {
              display: flex;
              justify-content: space-around;
            }
          }

          .td-all {
            border-top: none;
          }
        }

        &.electro-audiometer {
          tr td {
            width: auto;
          }
        }

        &.job-inquiry-history {
          tr th {
            width: auto;
            background: #FFFF99;
            border: 1px solid #FFDE36;
            border-left: none;
            font-size: 14px;
            line-height: 18px;
            color: #000000;
            padding: 8px 12px;

            &:first-child {
              border-left: 1px solid #FFDE36;
            }
          }

          tr td {
            text-align: center;
            width: auto;
          }
        }
      }

      .table-tag {
        font-size: 14px;
        line-height: 18px;
        color: #000000;
        margin-bottom: 4px;
      }
    }

    .report-picture {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;

      img {
        width: 48%;
        margin-top: 16px;

        &:nth-child(2n) {
          margin-left: 2%;
        }
      }
    }

    .report-result {
      margin-top: 16px;

      .result-title {
        font-weight: 600;
        font-size: 14px;
        line-height: 18px;
        color: #333333;
      }

      .result-content {
        margin-top: 12px;
        font-size: 14px;
        line-height: 18px;
        color: #333333;
      }
    }

    .report-footer {
      display: flex;
      justify-content: space-around;
      margin-top: 22px;

      .footer-column {
        .column-title {
          display: inline-block;
          font-size: 14px;
          line-height: 18px;
          color: #333333;
        }

        span {
          display: inline-block;
          border-bottom: 1px solid #000;
          display: inline-block;
          font-size: 12px;
          line-height: 18px;
          color: #333333;
          min-width: 100px;
          padding: 0 2px;
        }
      }
    }
  }
}
</style>