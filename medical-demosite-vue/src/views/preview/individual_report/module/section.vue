<!-- 健康团体报告 开发人：麦沃德科技半夏、予安 -->
<template>
  <div class="preview-individual-module-section">
    <div class="main-report" v-for="(item, index) in showData" :key="index">
      <!-- 多模板科室 -->
      <div v-if="picType.indexOf(String(item.head.sjbggs)) > -1">
        <div class="report-table">
          <!-- <div class="table-tag" v-if="item.head.sjbggs == 1"><b>说明：</b>此检验结果仅对本次标本负责！</div> -->
          <!-- 检验科 -->
          <table border="0" cellspacing="0" class="clinical-laboratory" v-if="item.head.sjbggs == 1">
            <tr>
              <th class="table-title" colspan="5">
                <span>{{ item.head.yblx }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <th style="width: 28%; border-bottom: 1px solid #06ff06">检验项目</th>
              <th style="width: 20%; border-bottom: 1px solid #06ff06">结果</th>
              <th style="width: 16%; border-bottom: 1px solid #06ff06">标志</th>
              <th style="width: 20%; border-bottom: 1px solid #06ff06">参考值</th>
              <th style="width: 16%; border-bottom: 1px solid #06ff06">单位</th>
            </tr>
            <tbody v-for="(exam, examIndex) in item.item" :key="examIndex">
              <tr>
                <td colspan="5" class="td-all">
                  <div class="flex">
                    <span>{{ exam.ITEM_NAME }}</span>
                    <span>收样时间：{{ exam.RECEIVE_DATE }}</span>
                    <span>审核日期：{{ exam.DATE }}</span>
                    <div class="flex-box">
                      <span>检验者：</span>
                      <div class="all-cont" style="margin-right: 12px">
                        <img v-if="exam.jy_audit_path" :src="imgPath + exam.jy_audit_path" />
                        <span v-else>{{ exam.P_NAME }}</span>
                      </div>
                      <span>审核人：</span>
                      <div class="all-cont">
                        <img v-if="exam.jy_rummager_path" :src="imgPath + exam.jy_rummager_path" />
                        <span v-else>{{ exam.P_NAME_LAB }}</span>
                      </div>
                    </div>
                  </div>
                </td>
              </tr>
              <tr :style="setAbnormal(list.CONSULT, list.RESULT, list.red) ? 'background: #FFDAB9;-webkit-print-color-adjust: exact;-moz-print-color-adjust: exact;-ms-print-color-adjust: exact;print-color-adjust: exact;' : ''" v-for="(list, listIndex) in exam.EXAMLIST" :key="listIndex">
                <td>{{ list.ITEM }}</td>
                <td>{{ list.RESULT }}</td>
                <td>{{ list.CONSULT }}</td>
                <td>{{ list.UNIT }}</td>
                <td>{{ list.SIGN }}</td>
              </tr>
              <tr>
                <td colspan="5" class="td-tag">
                  <div class="table-tag" v-if="item.head.sjbggs == 1"><b>说明：</b>此检验结果仅对本次标本负责！</div>
                </td>
              </tr>
            </tbody>
          </table>
          <!-- 电测听-健康 -->
          <table border="0" cellspacing="0" class="electro-audiometer" v-else-if="item.head.sjbggs == 9 && typeData === '0' && item.item[0].tjlx == 0">
            <tr>
              <th class="table-title" colspan="8">
                <span>{{ item.head.DEP_NAME }}</span>
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
              <td>4KHZ</td>
              <td>8KHZ</td>
            </tr>
            <tbody v-for="(exam, examIndex) in item.item" :key="examIndex">
              <tr v-for="(list, index) in exam.EXAMLIST" :key="list.series">
                <template v-if="index != 0">
                  <template v-if="list['125HZ'] !== '' || list['250HZ'] !== '' || list['500HZ'] !== '' || list['1KHZ'] !== '' || list['2KHZ'] !== '' || list['4KHZ'] !== '' || list['8KHZ'] !== ''">
                    <td style="width: 19.5%">{{ list.series }}</td>
                    <td style="width: 11.5%">{{ list['125HZ'] ? parseInt(list['125HZ']) : list['125HZ'] }}</td>
                    <td style="width: 11.5%">{{ list['250HZ'] ? parseInt(list['250HZ']) : list['250HZ'] }}</td>
                    <td style="width: 11.5%">{{ list['500HZ'] ? parseInt(list['500HZ']) : list['500HZ'] }}</td>
                    <td style="width: 11.5%">{{ list['1KHZ'] ? parseInt(list['1KHZ']) : list['1KHZ'] }}</td>
                    <td style="width: 11.5%">{{ list['2KHZ'] ? parseInt(list['2KHZ']) : list['2KHZ'] }}</td>
                    <td style="width: 11.5%">{{ list['4KHZ'] ? parseInt(list['4KHZ']) : list['4KHZ'] }}</td>
                    <td style="width: 11.5%">{{ list['8KHZ'] ? parseInt(list['8KHZ']) : list['8KHZ'] }}</td>
                  </template>
                </template>
              </tr>
            </tbody>
          </table>
          <!-- 电测听-职业 -->
          <table border="0" cellspacing="0" class="electro-audiometer" v-else-if="item.head.sjbggs == 9 && (typeData != 0 || item.item[0].tjlx != 0)">
            <tr>
              <th class="table-title" colspan="15">
                <span>{{ item.head.DEP_NAME }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td></td>
              <td colspan="2">500HZ</td>
              <td colspan="2">1KHZ</td>
              <td colspan="2">2KHZ</td>
              <td colspan="2">3KHZ</td>
              <td colspan="2">4KHZ</td>
              <td colspan="2">6KHZ</td>
              <td colspan="2">8KHZ</td>
            </tr>
            <tr>
              <td></td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
              <td>原始</td>
              <td>校正</td>
            </tr>
            <tbody v-for="(exam, examIndex) in item.item" :key="examIndex">
              <tr v-for="list in exam.EXAMLIST" :key="list.series">
                <template v-if="list['500HZ'] != '0.0' || list['1KHZ'] != '0.0' || list['2KHZ'] != '0.0' || list['3KHZ'] != '0.0' || list['4KHZ'] != '0.0'">
                  <td>{{ list.series }}</td>
                  <td>{{ list['500HZ'] ? parseInt(list['500HZ']) : list['500HZ'] }}</td>
                  <td>{{ list['500HZC'] ? parseInt(list['500HZC']) : list['500HZC'] }}</td>
                  <td>{{ list['1KHZ'] ? parseInt(list['1KHZ']) : list['1KHZ'] }}</td>
                  <td>{{ list['1KHZC'] ? parseInt(list['1KHZC']) : list['1KHZC'] }}</td>
                  <td>{{ list['2KHZ'] ? parseInt(list['2KHZ']) : list['2KHZ'] }}</td>
                  <td>{{ list['2KHZC'] ? parseInt(list['2KHZC']) : list['2KHZC'] }}</td>
                  <td>{{ list['3KHZ'] ? parseInt(list['3KHZ']) : list['3KHZ'] }}</td>
                  <td>{{ list['3KHZC'] ? parseInt(list['3KHZC']) : list['3KHZC'] }}</td>
                  <td>{{ list['4KHZ'] ? parseInt(list['4KHZ']) : list['4KHZ'] }}</td>
                  <td>{{ list['4KHZC'] ? parseInt(list['4KHZC']) : list['4KHZC'] }}</td>
                  <td>{{ list['6KHZ'] ? parseInt(list['6KHZ']) : list['6KHZ'] }}</td>
                  <td>{{ list['6KHZC'] ? parseInt(list['6KHZC']) : list['6KHZC'] }}</td>
                  <td>{{ list['8KHZ'] ? parseInt(list['8KHZ']) : list['8KHZ'] }}</td>
                  <td>{{ list['8KHZC'] ? parseInt(list['8KHZC']) : list['8KHZC'] }}</td>
                </template>
              </tr>
            </tbody>
          </table>
          <!-- 其他 -->
          <table border="0" cellspacing="0" style="border-color: #ffde36" v-else>
            <tr>
              <th class="table-title">
                <span>{{ item.head.DEP_NAME }}</span>
                <span>检查结果</span>
              </th>
            </tr>
          </table>
        </div>
        <div class="report-picture" v-if="item.head.sjbggs != 9">
          <div v-for="(img, imgIndex) in item.picture" :key="imgIndex" style="page-break-inside: auto; width: 100%; margin-left: 0">
            <div style="display: flex; justify-content: space-between; width: 100%">
              <img :style="{ width: img.image3 ? '31%' : img.image2 ? '48%' : '' }" :src="imgPath + img.image" v-if="img.image" />
              <img :style="{ width: img.image3 ? '31%' : img.image2 ? '48%' : '' }" :src="imgPath + img.image2" v-if="img.image2" />
              <img :style="{ width: img.image3 ? '31%' : img.image2 ? '48%' : '' }" :src="imgPath + img.image3" v-if="img.image3" />
            </div>
          </div>
        </div>
        <div class="report-result">
          <div class="result-title">小结：</div>
          <div class="result-content">
            <div v-html="item.summary.P_CONCLUSIONS" style="white-space: pre-wrap"></div>
            <!-- <div v-else>
              <p v-for="(conclusions, conIndex) in item.summary.CONCLUSIONS" :key="conIndex">{{ conclusions.conclu || '\n' }}</p>
            </div> -->
          </div>
        </div>
        <div class="report-footer" v-if="item.head.sjbggs != 1">
          <div class="footer-column">
            <div class="column-title">体检时间：</div>
            <div class="column-cont">
              {{ item.summary.audit_time }}
            </div>
          </div>
          <div class="footer-column">
            <div class="column-title">检查者：</div>
            <div class="column-cont" style="padding-top: 0">
              <img v-if="item.summary.rummager_path" :src="imgPath + item.summary.rummager_path" />
              <span v-else>{{ item.summary.rummager_name }}</span>
            </div>
          </div>
          <div class="footer-column">
            <div class="column-title">审核者：</div>
            <div class="column-cont" style="padding-top: 0">
              <img v-if="item.summary.audit_path" :src="imgPath + item.summary.audit_path" />
              <span v-else>{{ item.summary.audit_name }}</span>
            </div>
          </div>
        </div>
        <!-- 电测听 -->
        <div class="report-picture dct-pic" v-if="item.head.sjbggs == 9 && centerId != 'ff8080817ee18637017ee77dc0322d8c'">
          <div v-for="(img, imgIndex) in item.picture" :key="imgIndex" style="display: flex;justify-content: center;width:100%;">
            <img style="width: 45%;" :src="imgPath + img.image" v-if="img.image" />
            <img style="width: 45%;" :src="imgPath + img.image2" v-if="img.image2" />
          </div>
        </div>
      </div>
      <!-- 单模板科室 -->
      <div v-else>
        <div class="report-table">
          <!-- 一般检查 -->
          <!-- <table border="0" cellspacing="0" :style="item.item.length == 0 ? 'border-color: #FFDE36;' : ''" v-if="item.head.sjbggs == 3">
            <tr>
              <th class="table-title" colspan="2">
                <span>{{ item.head.DEP_NAME }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tbody v-for="(exam, examIndex) in item.item" :key="examIndex">
              <tr v-for="(list, listIndex) in exam.EXAMLIST" :key="listIndex">
                <td>{{ list.ITEM }}</td>
                <td><span v-html="list.SIGN"></span></td>
              </tr>
            </tbody>
          </table> -->
          <!-- 肺功能 -->
          <table border="0" class="exams-function" cellspacing="0" v-if="item.head.sjbggs == 5">
            <tr>
              <th class="table-title" colspan="5">
                <span>{{ item.head.DEP_NAME }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tr>
              <td style="width: 40%">项目</td>
              <td style="width: 15%">实测值</td>
              <td style="width: 15%">预计值</td>
              <td style="width: 15%">比值</td>
              <td style="width: 15%">单位</td>
            </tr>
            <tr>
              <td>用力肺活量（FVC）</td>
              <td>{{ item.item[0].FVC }}</td>
              <td>{{ item.item[0].PREFVC }}</td>
              <td>{{ item.item[0].PERFVC }}</td>
              <td>L</td>
            </tr>
            <tr>
              <td>1秒用力呼气容积(FEV1)</td>
              <td>{{ item.item[0].FEV }}</td>
              <td>{{ item.item[0].PREFEV }}</td>
              <td>{{ item.item[0].PERFEV }}</td>
              <td>L</td>
            </tr>
            <tr>
              <td>1秒用力呼气容积/用力肺活量</td>
              <td>{{ item.item[0].FEV_FVC }}</td>
              <td>{{ item.item[0].PREFEV_FVC }}</td>
              <td>{{ item.item[0].PERFEV_FVC }}</td>
              <td>%</td>
            </tr>
            <template v-if="typeData === '0'">
              <tr>
                <td>最大呼气中期流速</td>
                <td>{{ item.item[0].MMEF }}</td>
                <td>{{ item.item[0].PREMMEF }}</td>
                <td>{{ item.item[0].PERMMEF }}</td>
                <td>L/s</td>
              </tr>
              <tr>
                <td>25%呼气中段流量（FEF25%）</td>
                <td>{{ item.item[0].FEFFC }}</td>
                <td>{{ item.item[0].PREFEFFC }}</td>
                <td>{{ item.item[0].PERFEFFC }}</td>
                <td>L/s</td>
              </tr>
              <tr>
                <td>50%呼气中段流量（FEF50%）</td>
                <td>{{ item.item[0].FEFFB }}</td>
                <td>{{ item.item[0].PREFEFFB }}</td>
                <td>{{ item.item[0].PERFEFFB }}</td>
                <td>L/s</td>
              </tr>
              <tr>
                <td>75%呼气中段流量（FEF75%）</td>
                <td>{{ item.item[0].FEFFA }}</td>
                <td>{{ item.item[0].PREFEFFA }}</td>
                <td>{{ item.item[0].PERFEFFA }}</td>
                <td>L/s</td>
              </tr>
            </template>
          </table>
          <!-- 其他 -->
          <table border="0" cellspacing="0" :style="item.item.length == 0 ? 'border-color: #FFDE36;' : ''" v-else>
            <tr>
              <th class="table-title" colspan="2">
                <span>{{ item.head.DEP_NAME }}</span>
                <span>检查结果</span>
              </th>
            </tr>
            <tbody v-for="(exam, examIndex) in item.item" :key="examIndex">
              <tr v-for="(list, listIndex) in exam.EXAMLIST" :key="listIndex">
                <template v-if="list.SIGN !== '' || exam.ITEM_NAME == '妇科'">
                  <td>{{ list.ITEM }}</td>
                  <td><span v-html="list.SIGN"></span></td>
                </template>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="report-result">
          <div class="result-title">小结：</div>
          <div class="result-content">
            <div v-html="item.summary.P_CONCLUSIONS" style="white-space: pre-wrap"></div>
            <!-- <div v-else>
              <p v-for="(conclusions, conIndex) in item.summary.CONCLUSIONS" :key="conIndex">{{ conclusions.conclu || '\n' }}</p>
            </div> -->
          </div>
        </div>
        <div class="report-footer">
          <div class="footer-column">
            <div class="column-title">体检时间：</div>
            <div class="column-cont">
              {{ item.summary.audit_time }}
            </div>
          </div>
          <div class="footer-column">
            <div class="column-title">检查者：</div>
            <div class="column-cont" style="padding-top: 0">
              <img v-if="item.summary.rummager_path" :src="imgPath + item.summary.rummager_path" />
              <span v-else>{{ item.summary.rummager_name }}</span>
            </div>
          </div>
          <div class="footer-column">
            <div class="column-title">审核者：</div>
            <div class="column-cont" style="padding-top: 0">
              <img v-if="item.summary.audit_path" :src="imgPath + item.summary.audit_path" />
              <span v-else>{{ item.summary.audit_name }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 电测听 -->
    <div class="report-picture dct-pic page-break" v-if="dctPicList.length && centerId == 'ff8080817ee18637017ee77dc0322d8c'">
      <div v-for="(img, imgIndex) in dctPicList" :key="imgIndex" style="display: flex;justify-content: center;width:100%;">
        <img style="width: 45%;" :src="imgPath + img.image" v-if="img.image" />
        <img style="width: 45%;" :src="imgPath + img.image2" v-if="img.image2" />
      </div>
    </div>
    <!-- 相关检测报告 -->
    <div class="detection-report" v-if="endPicture && endPicture.length">
      <div v-for="(value, index) in endPicture" :key="index">
        <div class="report-item" v-if="value && value.image">
          <img :src="imgPath + value.image" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
export default {
  props: ['showData', 'endPicture', 'typeData', 'imgPath'],
  data() {
    return {
      // 图片科室
      picType: ['1', '7', '9', '10'],
      // 分中心id
      centerId: Cookies.get('cid'),
      // 电测听图片列表
      dctPicList: [],
    }
  },
  watch: {
    showData: {
      immediate: true,
      handler(val) {
        if (val && val.length && this.centerId == 'ff8080817ee18637017ee77dc0322d8c') {
          val.forEach((el) => {
            if (el.head && el.head.sjbggs == 9) {
              this.dctPicList = el.picture
            }
          })
        }
      },
    },
  },
  methods: {
    // 返回异常指标
    setAbnormal(consult, result, red) {
      if (consult == '↑' || consult == '↓' || result.indexOf('+') != -1 || result.indexOf('阳性') != -1 || red == 1) {
        return true
      } else {
        return false
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.preview-individual-module-section {
  // 说明模块后打印要另起一页
  // page-break-before: always;

  .main-report {
    margin: 4px auto 0;
    background: #fff;
    .report-table {
      table {
        width: 100%;
        border-bottom: 1px solid #06ff06;

        tr {
          .table-title {
            width: 100%;
            text-align: center;
            background: #ffff99;
            -webkit-print-color-adjust: exact;
            -moz-print-color-adjust: exact;
            -ms-print-color-adjust: exact;
            print-color-adjust: exact;
            border: 1px solid #ffde36;
            border-bottom: none;
            padding: 0;

            &:first-child {
              border-left: 1px solid #ffde36;
            }

            span {
              width: 50%;
              display: inline-block;
              word-break: break-all;
              padding: 4px 12px;
            }
          }

          td,
          th {
            width: 50%;
            border-top: 0.5px solid #06ff06;
            border-bottom: 0.5px solid #06ff06;
            border-left: 0.5px solid #06ff06;
            border-right: 0.5px solid #06ff06;
            padding: 2px 12px;
            font-size: 14px;
            line-height: 18px;
            color: #000000;
            word-break: break-all;
            font-weight: 600;

            &:first-child {
              border-left: 1px solid #06ff06;
            }
          }
          td {
            font-size: 13px;
            font-weight: 400;
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
              align-items: center;
              flex-wrap: wrap;

              .flex-box {
                display: flex;
                justify-content: space-around;
                align-items: center;
              }
            }
          }

          .td-all {
            font-size: 10px;
            border-top: none;
            padding: 9px 0;

            span {
              padding: 3px 0;
            }

            .all-cont {
              img {
                height: 30px;
                width: auto;
                max-width: 100px;
                display: block;
                padding: 3px 0;
                object-fit: contain;
              }
            }
          }
        }

        &.electro-audiometer {
          tr td {
            width: auto;
            padding: 6px 0;
            text-align: center;
          }
        }

        &.exams-function {
          tr td {
            width: auto;
            text-align: center;

            &:first-child {
              text-align: left;
            }
          }
        }

        &.job-inquiry-history {
          tr th {
            width: auto;
            background: #ffff99;
            -webkit-print-color-adjust: exact;
            -moz-print-color-adjust: exact;
            -ms-print-color-adjust: exact;
            print-color-adjust: exact;
            border: 1px solid #ffde36;
            border-left: none;
            font-size: 14px;
            line-height: 18px;
            color: #000000;
            padding: 8px 12px;

            &:first-child {
              border-left: 1px solid #ffde36;
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
        margin-bottom: 8px;
      }
    }

    .report-result {
      margin-top: 12px;
      display: flex;

      .result-title {
        font-weight: 600;
        font-size: 14px;
        line-height: 22px;
        color: #333333;
      }

      .result-content {
        flex: 1;
        font-size: 14px;
        font-weight: 600;
        line-height: 22px;
        color: #333333;
        word-break: break-all;
        white-space: pre-wrap;

        p {
          margin: 0;
        }
        div {
          word-break: break-all;
          white-space: pre-wrap;
        }
      }
    }

    .report-footer {
      display: flex;
      justify-content: space-around;
      margin: 8px 0;
      page-break-before: none;

      .footer-column {
        display: flex;
        align-items: center;

        .column-title {
          display: inline-block;
          font-size: 15px;
          line-height: 28px;
          height: 34px;
          padding-top: 6px;
          color: #333333;
        }

        .column-cont {
          display: inline-block;
          word-break: break-all;
          border-bottom: 1px solid #000;
          display: inline-block;
          font-size: 15px;
          height: 34px;
          line-height: 28px;
          color: #333333;
          min-width: 100px;
          padding: 6px 2px 0;

          img {
            width: auto;
            height: 32px;
            max-width: 120px;
            object-fit: contain;
          }
        }
      }
    }
  }

  .report-picture {
    // display: flex;
    // flex-wrap: wrap;
    // // justify-content: space-between;
    // justify-content: center;
    margin-top: 4px;
    text-align: center;
    page-break-inside: avoid;

    div {
      display: inline-block;
      margin-top: 8px;
      width: 42%;

      &:nth-child(2n) {
        margin-left: 4%;
      }

      img {
        width: 100%;
        max-height: 300px;
        // height: 100%;
        // page-break-before: auto;
        page-break-inside: avoid;
        object-fit: contain;
      }
    }
  }

  .detection-report {
    .report-item {
      text-align: center;
      page-break-before: always;

      img {
        width: 100%;
        margin: 20px 0;
      }
    }
  }

  .dct-pic {
    div {
      height: auto !important;
    }
    img {
      height: auto !important;
      max-height: 600px !important;
      object-fit: cover !important;
    }
  }
  .page-break {
    page-break-after: always;
    page-break-before: always;
  }
}

@-moz-document url-prefix() {
  .preview-individual-module-section .main-report .report-result {
    margin-bottom: 8px;
  }
}
</style>
