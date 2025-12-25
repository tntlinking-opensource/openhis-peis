<template>
  <el-dialog id="bills_model_list" :visible.sync="open" width="calc(210mm + 80px)" :modal-append-to-body="false" :close-on-click-modal="false">
    <div ref="bills">
      <div class="header">
        <div v-if="reportData && reportData.length == 1">
          <span style="margin-right: 20px">{{ reportData[0].name }}</span>
          <span>{{ reportData[0].patientCode }}</span>
        </div>
      </div>
      <div class="footer" id="footer">
        <div id="warm-prompt">
          {{ footerText }}
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
        <tbody>
          <tr>
            <td>
              <div class="main_model_list">
                <div class="list-info" id="list-info" v-for="(item, index) in reportData" :key="index">
                  <div style="padding-bottom: 10px; font-size: 16px">{{ item.dydremark }}</div>
                  <div class="list-title">
                    <span>{{ item.cid }}--导引单</span>
                  </div>
                  <div class="info-title">
                    <img style="width: 200px; height: 26px" class="info-barcode" id="barcode" />
                    <div class="info-code-text">
                      {{ item.numorgresv }}
                      <div style="display: inline-block; vertical-align: bottom; line-height: 14px">*</div>
                      {{ item.patientCode }}
                    </div>
                    <div class="info-text">{{ item.informWay }}</div>
                  </div>
                  <div class="info-table">
                    <img :src="`${item.picture}`" class="table-picture" />
                    <div class="table-box" style="flex: 1; padding: 1px">
                      <table border="0" cellspacing="0">
                        <tr>
                          <td>
                            <div style="white-space: nowrap">姓名</div>
                          </td>
                          <td>
                            <div>{{ item.name }}</div>
                          </td>
                          <td>
                            <div style="white-space: nowrap">性别</div>
                          </td>
                          <td>
                            <div>{{ item.sex }}</div>
                          </td>
                          <td>
                            <div style="white-space: nowrap">年龄</div>
                          </td>
                          <td>
                            <div>{{ item.age }}</div>
                          </td>
                          <td>
                            <div style="white-space: nowrap">婚姻</div>
                          </td>
                          <td>
                            <div v-for="marriage in marriageType" :key="marriage.value">
                              <div v-if="marriage.value == item.marriagec">{{ marriage.label }}</div>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div style="white-space: nowrap">证件</div>
                          </td>
                          <td colspan="3">
                            <div>{{ item.idcardno }}</div>
                          </td>
                          <td>
                            <div style="white-space: nowrap">体检日期</div>
                          </td>
                          <td colspan="3">
                            <div>{{ item.medicaldate }}</div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div style="white-space: nowrap">部门</div>
                          </td>
                          <td colspan="4">
                            <div>{{ item.depart }}</div>
                          </td>
                          <td colspan="3">
                            <div>{{ item.zytjlb }}</div>
                          </td>
                        </tr>
                        <tr>
                          <template v-if="hideConfig.userLevel != '1'">
                            <td>
                              <div style="white-space: nowrap">会员类型</div>
                            </td>
                            <td colspan="3">
                              <div>{{ item.patientClass }}</div>
                            </td>
                          </template>
                          <td>
                            <div style="white-space: nowrap">体检类别</div>
                          </td>
                          <td :colspan="hideConfig.userLevel != '1' ? 3 : 7">
                            <div>
                              {{ item.type }}
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div style="white-space: nowrap">体检套餐</div>
                          </td>
                          <td colspan="7">
                            <div>{{ item.meal }}</div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div style="white-space: nowrap">团体</div>
                          </td>
                          <td colspan="7">
                            <div>{{ item.org }}</div>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </div>
                  <div class="list-result">
                    <div class="result-content" v-if="hideConfig.eyeView != '1'">
                      <div>视力检查：</div>
                      <div>（裸视/矫正）L:</div>
                      <div class="content-line"></div>
                      <div>R：</div>
                      <div class="content-line"></div>
                      <div style="margin-left: 5px">辨色力：</div>
                      <div class="content-line"></div>
                      <div style="margin-left: 5px">眼压：L：</div>
                      <div class="content-line"></div>
                      <div>R：</div>
                      <div class="content-line"></div>
                    </div>
                    <div class="result-content">
                      <div>一般检查：</div>
                      <div>血压：</div>
                      <div class="content-line2"><span style="margin-right: 30px;">/</span>mmHg</div>
                      <div style="margin-left: 5px">脉搏：</div>
                      <div class="content-line2"><span></span>次/分</div>
                      <div style="margin-left: 5px">身高：</div>
                      <div class="content-line2"><span></span>cm</div>
                      <div style="margin-left: 5px">体重：</div>
                      <div class="content-line2"><span></span>kg</div>
                    </div>
                  </div>
                  <div class="info-tables" style="margin-top: 10px">
                    <div class="table-box">
                      <table border="0" cellspacing="0" id="tab" class="itemTab">
                        <!-- 固定行 -->
                        <thead>
                          <tr style="width: 100%">
                            <td class="text-align-td text-align-first" style="width: 8%">
                              <div>温馨提示</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 10%">
                              <div>检查科室</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 20%">
                              <div>检前提示</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 8%">
                              <div>检查者</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 26%">
                              <div>检查项目</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 4%">
                              <div>补检</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 4%">
                              <div>弃检</div>
                            </td>
                            <td class="text-align-td text-align-first" style="width: 8%">
                              <div>顾客签名</div>
                            </td>
                          </tr>
                        </thead>
                        <!-- 餐序 -->
                        <template v-for="(ksItem, ksIndex) in item.items">
                          <tr v-for="(jcxmItem, jcxmIndex) in ksItem.value" :key="String(ksIndex) + jcxmIndex" style="width: 100%">
                            <td class="text-align-td" style="width: 8%">
                              <div>{{ jcxmItem.cx }}</div>
                            </td>
                            <td class="text-align-td" style="width: 10%">
                              <div v-html="jcxmItem.ks"></div>
                            </td>
                            <td style="padding: 4px 2px; width: 20%">
                              <div>{{ jcxmItem.bz }}</div>
                            </td>
                            <td style="width: 8%">
                              <div></div>
                            </td>
                            <td style="padding: 4px 2px; width: 26%">
                              <div>
                                {{ jcxmItem.items }}
                              </div>
                            </td>
                            <td style="width: 4%">
                              <div></div>
                            </td>
                            <td style="width: 4%">
                              <div></div>
                            </td>
                            <td style="width: 8%">
                              <div></div>
                            </td>
                          </tr>
                        </template>
                        <!-- 营养餐厅 -->
                        <!-- <tr v-if="hideConfig.breakfast != '1' && item.items.slice(-1)[0].value && item.items.slice(-1)[0].value.length && item.items.slice(-1)[0].value[0].ks != '营养餐厅'">
                          <td>
                            <div></div>
                          </td>
                          <td class="text-align-td">
                            <div>营养餐厅</div>
                          </td>
                          <td>
                            <div>餐前项目还未全部完成的顾客,请勿饮水用餐</div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td style="padding: 4px 2px">
                            <div>营养早餐</div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                        </tr> -->
                        <!-- 备注 -->
                        <tr>
                          <td class="text-align-td">
                            <div>备注</div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                          <td>
                            <div></div>
                          </td>
                        </tr>
                      </table>
                      <div class="after-table" v-if="reportData[0].lineUpOpen == '1'">
                        <div>
                          <!-- 二维码 -->
                          <div ref="qrimg" class="qrimg" id="qrimg"></div>
                          <div style="text-align: center; white-space: nowrap; font-size: 14px">扫码看排队</div>
                        </div>
                        <div class="tipsText">
                          <span class="tips_title">体检排队查询入口: </span>
                          <div class="tips_content">
                            <div class="contentF">1.扫码查询科室排队信息（排队科室仅彩超）。</div>
                            <div class="contentS">2.关注{{ item.cid }}公众号查询电子版报告，个检5-7个工作日，团检7-14工作日。</div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td>
              <div class="page-footer"></div>
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
    <div class="footer-text" v-if="showFooterText">
      编辑页脚内容
      <el-input v-model="footerText" placeholder=""></el-input>
    </div>
    <el-tag class="body_position" @click="printHtml">打印</el-tag>
  </el-dialog>
</template>

<script>
import { getBillModelData } from '@/api/preview/bills_model_list.js'
import { getConfigKey } from '@/api/system/config.js'
import { getCookie } from '@/utils/getCookie.js'
import { marriageType } from '@/utils/dataList.js'
//打印条形码接口及插件
import { printBarCode } from '@/utils/printBarCode.js'
import QRCode from 'qrcodejs2'
import JsBarcode from 'jsbarcode'

export default {
  data() {
    return {
      queryParams: {},
      loading: null,
      reportData: undefined,
      ksArray: [], //科室检查项目
      //体检类别
      Medicaltype: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      //婚姻
      marriageType: [],
      open: false,
      // 条形码接口参数
      query: {},
      //页脚
      footerText: '❤温馨提示❤: 您的留言将于检程结束后第一时间送达总经办，由中心总经理亲自阅读，再次感谢您的配合，祝您健检愉快!',
      showFooterText: true,

      hideConfig: {},
    }
  },
  methods: {
    //重置
    reset() {
      this.reportData = []
      this.queryParams = {}
      this.marriageType = []
    },
    // 获取报告数据
    getReport(data, query, fn = false, barNum) {
      this.reset()
      this.open = true
      getConfigKey('LEADING_PAGE_CONFIG').then(({ msg, code }) => {
        if (code == 200 && msg) {
          this.hideConfig = JSON.parse(msg)
        }
      })
      let _body = document.querySelector('body')
      _body.classList.add('_background')
      this.marriageType = marriageType()
      this.queryParams = data
      this.query = query
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      var query = {
        cid: getCookie('cid'),
        dydStyle: this.queryParams.dydStyle,
        ids: this.queryParams.ids, //体检号
        model: this.queryParams.model,
        usePrintName: '1',
      }
      if (typeof query.ids == 'string') {
        query.ids = [this.queryParams.ids]
      }
      getBillModelData(query)
        .then((res) => {
          let isEmpty = false
          res.data.forEach((item) => {
            if (!isEmpty) {
              if (!item.items || !item.items.length) {
                isEmpty = true
                return
              }
              item.items.forEach((el) => {
                el.forEach((val) => {
                  if (val && val.bz) {
                    let array = val.bz.split(',')
                    if (array.length) {
                      val.bz = Array.from(new Set(array)).join(',')
                    }
                  }
                })
              })
            }
          })
          if (isEmpty) {
            this.open = false
            this.loading.close()
            this.$modal.msgError('检查项目为空')
            return
          }
          this.$nextTick(() => {
            this.setReport(res.data, fn, barNum)
          })
        })
        .catch(() => {
          this.loading.close()
        })
      if (!localStorage.getItem('footerText')) {
        localStorage.setItem('footerText', this.footerText)
      } else {
        this.footerText = localStorage.getItem('footerText')
      }
    },
    // 设置报告数据
    setReport(data, fn, barNum) {
      this.reportData = data
      this.$nextTick(async () => {
        await this.getTableData()
        this.$nextTick(() => {
          for (let i = 0; i < data.length; i++) {
            JsBarcode('#barcode', data[i].patientCode, {
              format: 'CODE128', //条形码的格式
              width: 1, //线宽
              height: 18, //条码高度
              lineColor: '#000', //线条颜色
              displayValue: false, //是否显示文字
              margin: 2, //设置条形码周围的空白区域
            })
            if (this.queryParams.dydStyle != '0' && data[0].lineUpOpen == '1') {
              let qrcode = new QRCode(this.$refs.qrimg[i], {
                text: data[i].QUEUEING_SYSTEM_URL,
                width: 80,
                height: 80,
                colorDark: '#000000',
                colorLight: '#FFFFFF',
                correctLevel: QRCode.CorrectLevel.H,
              })
            }
          }
          this.loading.close()
          // this.open = false;
          this.$nextTick(() => {
            this.printHtml(barNum)
            if (fn) {
              setTimeout(() => {
                fn()
              }, 1500)
            }
          })
        })
      })
    },
    printHtml(barNum) {
      if (this.query) {
        printBarCode(
          this.query,
          () => {
            // this.open = false
          },
          barNum
        )
      }
      localStorage.setItem('footerText', this.footerText)
      this.$nextTick(() => {
        try {
          this.$nextTick(() => {
            requestAnimationFrame(() => {
              this.$print(this.$refs.bills)
              setTimeout(() => {
                this.open = false
              }, 800);
            })
          })
        } catch (error) {
          this.$print(this.$refs.bills)
          setTimeout(() => {
            this.open = false
          }, 800);
        }
      })
    },
    getTableData() {
      return new Promise((resolve) => {
        let url = getCookie('imgPath') //获取cookie图片前缀
        this.reportData.forEach((el) => {
          el.picture = url + el.picture
          let list = []
          for (var key in el.items) {
            list.push({
              key: key,
              value: el.items[key],
            })
          }
          el.items = [...list]
        })
        this.$nextTick(() => {
          this.uniteTdCells()
          this.$nextTick(() => {
            resolve()
          })
        })
      })
    },
    uniteTdCells() {
      var table = document.getElementsByClassName('itemTab')
      for (let k in table) {
        if (k == 'length') return
        for (let i = 0; i < table[k].rows.length; i++) {
          for (let c = 0; c < table[k].rows[i].cells.length; c++) {
            if (c == 0) {
              //选择要合并的列序数，去掉默认全部合并
              for (let j = i + 1; j < table[k].rows.length; j++) {
                let cell1 = table[k].rows[i].cells[c].innerHTML
                let cell2 = table[k].rows[j].cells[c].innerHTML
                if (cell1 == cell2) {
                  table[k].rows[j].cells[c].style.display = 'none' //隐藏相同的第二条
                  table[k].rows[i].cells[c].rowSpan++
                }
              }
            }
            if (c == 1) {
              //选择要合并的列序数，去掉默认全部合并
              for (let j = i + 1; j < table[k].rows.length; j++) {
                let cell1 = table[k].rows[i].cells[c].innerHTML
                let cell2 = table[k].rows[j].cells[c].innerHTML
                if (cell1 == cell2) {
                  table[k].rows[j].cells[c].style.display = 'none' //隐藏相同的第二条
                  table[k].rows[i].cells[c].rowSpan++
                } else {
                  break
                }
              }
            }
          }
        }
      }
    },
  },
}
</script>
<style scoped>
#bills_model_list /deep/ .el-dialog__body {
  /* min-height: 297mm; */
  position: relative;
}

#bills_model_list /deep/ .el-dialog__body .body_position {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  cursor: pointer;
  width: 120px;
  height: 25px;
  line-height: 25px;
  font-size: 16px;
  text-align: center;
}
</style>

<style lang="scss" scoped>
.after-table {
  display: flex;
  justify-content: flex-start;
  // align-items: center;
  margin: 20px 0 0;
  // display: none;
  page-break-inside: avoid;

  .qrimg {
    display: inline-block;
  }

  .tipsText {
    display: inline-block;
    margin: 0 20px;
  }
}

@media print {
  .body_position {
    display: none;
  }
}

#bills_model_list {
  display: block;
}

table {
  margin: 0 auto;
}

.main_model_list {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 210mm;
  padding: 20px 40px;
  margin: 0 auto;
  background: #fff;
  font-family: '宋体', Arial, sans-serif !important;
  background-color: #fff;

  .list-title {
    display: flex;
    text-align: center;

    span {
      width: 100%;
      word-break: break-all;
      font-size: 24px;
      line-height: 18px;
      color: #000;
      padding: 8px 12px;
    }
  }

  .list-info {
    position: relative;

    .info-title {
      height: 40px;
      text-align: left;
      display: flex;
      justify-content: space-between;

      .info-barcode {
        width: 200px;
        margin: 8px 38px 6px 138px;
        display: inline-block;
      }

      .info-code-text {
        align-self: end;
        flex: 1;
        display: inline-block;
        color: #000;
        font-size: 16px;
        margin-bottom: 6px;
      }

      .info-text {
        align-self: end;
        margin-bottom: 6px;
        color: rgb(179, 125, 30);
        font-size: 16px;
        display: inline-block;
      }
    }

    .info-table {
      display: flex;
      align-items: center;
    }

    .info-table,
    .info-tables {
      .table-box {
        // flex: 1;
        // padding: 1px;

        table {
          width: 100%;
          border: 1px solid #333333;
          border-collapse: collapse;

          tr {
            td {
              border: 1px solid #333333;
              border-collapse: collapse;

              padding: 1px 6px;
              min-width: 40px;

              .td-title {
                font-size: 15px;
                line-height: 15px;
                color: #333333;
              }

              div {
                font-size: 14px !important;
              }

              span {
                font-weight: 500;
                font-size: 12px;
                line-height: 12px;
                color: #333333;
                display: inline-block;
                margin-top: 8px;
                width: 100%;
                height: 18px;
              }
            }

            .text-align-td {
              text-align: center;
              padding: 4px 2px;
            }

            .text-align-first {
              font-weight: 500;
            }
          }
        }
      }

      .table-picture {
        width: 100px;
        height: 130px;
        object-fit: cover;
        // border: 1px solid #c1c1c1;
        margin-right: 8px;
      }
    }
  }

  .list-result {
    margin-top: 16px;

    .result-title {
      font-weight: 600;
      font-size: 14px;
      line-height: 16px;
      color: #333333;
    }

    .result-content {
      margin-top: 6px;
      font-size: 14px;
      line-height: 16px;
      color: #333333;

      .content-line {
        border-bottom: 0.5px solid #333;
        width: 70px;
        margin-bottom: -3px;
      }

      .content-line2 {
        text-align: right;
        border-bottom: 0.5px solid #333;
        width: 100px;
        margin-bottom: -3px;

        span {
          margin-right: 4px;
        }
      }

      div {
        display: inline-block;
      }
    }
  }
}
</style>
<style lang="scss" scoped>
.footer-text {
  width: 200mm;
  margin: 10px auto 0;
}

// table {
//   margin: 0 auto;
// }

.header,
.footer {
  display: none;
}

@media print {
  table {
    page-break-inside: auto;
  }

  .main_model_list {
    padding-top: 0;
  }

  .footer-text {
    display: none;
  }

  .header,
  .page-header {
    height: 80px;
    padding-top: 40px;
    padding-bottom: 10px;
  }

  .page-footer {
    height: 50px;
    padding-top: 90px;
    // margin-top: 90px;
  }

  .header {
    position: fixed;
    top: 10px;
    left: 20px;
    right: 20px;
    padding-top: 10px;
    padding-bottom: 10px;
    z-index: 9;
    display: flex;
    justify-content: end;
    align-items: center;
    background-color: #fff;
  }

  .footer {
    position: fixed;
    bottom: 10px;
    left: 40px;
    right: 40px;
    z-index: 9;
    padding-bottom: 20px;
    font-size: 14px;
    color: #333;
    display: flex;
    align-items: center;
    background-color: #fff;
    border-top: 2px solid #333333;
  }

  @page {
    margin: 0; // 可以控制打印布局（四周边距）
  }
}
</style>
