<!-- 条码和导引单 开发人：麦沃德科技 清风\予安 导引单用这个 -->
<template>
  <div id="bills_model_list" ref="bills_model_list">
    <div style="display: flex; justify-content: center; align-items: center; text-align-last: left; height: 40px; line-height: 40px">
      <template v-if="queryParams.printBar">
        <div style="margin-right: 8px">条码打印个数</div>
        <el-radio v-model="barNum" label="3">3个</el-radio>
        <el-radio v-model="barNum" label="6">6个</el-radio>
        <el-radio v-model="barNum" label="9">9个</el-radio>
        <el-radio v-model="barNum" label="12">12个</el-radio>
        <el-radio v-model="barNum" label="15">15个</el-radio>
        <el-radio v-model="barNum" label="18">18个</el-radio>
      </template>
      <el-tag style="cursor: pointer; padding: 0 20px" @click="printMethods">打 印</el-tag>
    </div>
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
      <table style="background-color: #fff">
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
                <div class="list-info list-info-top" id="list-info" v-for="(item, index) in reportData" :key="index">
                  <div style="padding-bottom: 10px; font-size: 16px" v-if="item.dydremark">{{ item.dydremark }}</div>
                  <div class="list-title">
                    <span>{{ item.cid }}--导引单</span>
                  </div>
                  <div class="info-title">
                    <img style="width: 200px; height: 36px" class="info-barcode" :id="'barcode' + index" />
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
                              <!-- 如果是彩超,则项目每个占一行, -->
                              <div v-if="jcxmItem.ks != '彩超'"> 
                                {{ jcxmItem.items }}
                              </div>
                              <div v-else>
                                <div v-html="formatItems(jcxmItem.items)"></div>  
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
                        <!-- <tr key="breakfast2" v-if="hideConfig.breakfast != '1' && item.items.slice(-1)[0].value && item.items.slice(-1)[0].value.length && item.items.slice(-1)[0].value[0].ks != '营养餐厅'">
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
                      <div class="after-table" v-if="!reportData[0].imagePath && queryParams.dydStyle != '0' && reportData[0].lineUpOpen == '1'" :style="{ 'page-break-after': index != reportData.length - 1 ? 'always' : 'avoid' }">
                        <div>
                          <div ref="qrimg" class="qrimg" id="qrimg"></div>
                          <div style="text-align: center; white-space: nowrap; font-size: 14px">扫码看排队</div>
                        </div>
                        <div class="tipsText" v-if="queryParams.dydStyle == '1'" v-html="reportData[0].lineUpRemarks"></div>
                        <!-- <span class="tips_title">体检排队查询入口: </span>
                        <div class="tips_content">
                          <div class="contentF">1.扫码查询科室排队信息（排队科室仅彩超）。</div>
                          <div class="contentS">2.关注{{ item.cid }}公众号查询电子版报告，个检5-7个工作日，团检7-14工作日。</div>
                        </div> -->
                      </div>
                      <div class="after-table" v-if="reportData[0].imagePath"> 
                        <div>
                          <img :src="reportData[0].imagePath" style="width: 100px;height: 100px;" class="qrimg">
                          <div style="text-align: center; white-space: nowrap; font-size: 14px">扫码填写满意度</div>
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
  </div>
</template>
<script>
import { getBillModelData } from '@/api/preview/bills_model_list.js'
import { marriageType } from '@/utils/dataList.js'
import { getConfigKey } from '@/api/system/config.js'
import QRCode from 'qrcodejs2'
import JsBarcode from 'jsbarcode'
import { getCookie } from '@/utils/getCookie.js'
//打印条形码接口及插件
import { printBarCode } from '@/utils/printBarCode.js'
import Cookies from 'js-cookie'


export default {
  name: 'BillsModelList',
  // components:{footerItem},
  data() {
    return {
      queryParams: {},
      loading: null,
      reportData: [{}],
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
      marriageType: marriageType(),
      //页脚
      footerText: '❤温馨提示❤: 您的留言将于检程结束后第一时间送达总经办，由中心总经理亲自阅读，再次感谢您的配合，祝您健检愉快!',
      showFooterText: true,
      // 个性配置
      hideConfig: {},
      // 条码打印个数
      barNum: '9',
    }
  },
  created() {
    document.title = '导引单'
    this.queryParams = this.$route.query
    // if (this.$getCookie('cid') == 2) {
    //   this.barNum = '12'
    // }
    this.barNum = localStorage.getItem('barNum') || this.barNum
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
    getConfigKey('LEADING_PAGE_CONFIG').then(({ msg, code }) => {
      if (code == 200 && msg) {
        this.hideConfig = JSON.parse(msg)
      }
    })
  },
  methods: {
    formatItems(itemsString) {  
      // 假设项之间用逗号分隔，你可以根据需要修改分隔符  
      return itemsString.split(',').map(item => item.trim()).join('<br>');  
    },  
    printMethods() {
      let query = this.queryParams.printBar ? JSON.parse(this.queryParams.printBar) : ''
      if (query) {
        localStorage.setItem('barNum', this.barNum)
        printBarCode(query, () => {}, this.barNum)
      }
      localStorage.setItem('footerText', this.footerText)
      this.showFooterText = false
      this.$nextTick(() => {
        this.showFooterText = true
        // window.print()
        this.$print(this.$refs.bills)
      })
      // this.simulateKeyPress(80)
    },
    // 获取报告数据
    getReport() {
      var query = {
        // id: this.queryParams.id,
        // ids: this.queryParams.ids,
        // model: this.queryParams.model,
        //05-25 修改传参
        cid: getCookie('cid'), //分中心
        ids: this.queryParams.ids, //体检号集合
        dydStyle: this.queryParams.dydStyle, //导引单样式
        model: this.queryParams.model, //模型
        usePrintName: '1', //用户打印名称
      }
      if (typeof query.ids == 'string') {
        query.ids = [this.$route.query.ids]
      }
      getBillModelData(query).then((res) => {
        let isEmpty = false
        res.data.forEach((item) => {
          if (!isEmpty) {
            if (!item.items.length) {
              isEmpty = true
              return
            }
            item.items.forEach((el) => {
              el.forEach((val) => {
                let array = val.bz.split(',')
                if (array.length) {
                  val.bz = Array.from(new Set(array)).join(',')
                }
              })
            })
          }
        })
        if (isEmpty) {
          this.loading.close()
          this.$modal.msgError('检查项目为空')
          return
        }
        this.setReport(res.data)
      })
      if (!localStorage.getItem('footerText')) {
        localStorage.setItem('footerText', this.footerText)
      } else {
        this.footerText = localStorage.getItem('footerText')
      }
    },
    // 设置报告数据 
    setReport(data) {
      this.reportData = data
      this.getTableData()
      this.$nextTick(() => {
        for (let i = 0; i < data.length; i++) {
          let inttPatientCode = ''
          
          // 如果是淮南,则不截取,取12位
          if(Cookies.get('cid') == '402848e36b551aab016b5df90c8412e8'){
            inttPatientCode = data[i].patientCode; 
          }else{
            // 如果不是淮南,截取后8位生成
            inttPatientCode = data[i].patientCode.slice(-8); 
          }
          JsBarcode('#barcode' + i, inttPatientCode, {
            format: 'CODE128', //条形码的格式
            width: 1, //线宽
            height: 14, //条码高度
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
      })
    },
    //获取cookie
    getCookie(cookieName) {
      const strCookie = document.cookie
      const cookieList = strCookie.split(';')
      for (let i = 0; i < cookieList.length; i++) {
        const arr = cookieList[i].split('=')
        if (cookieName === arr[0].trim()) {
          return arr[1]
        }
      }
      return ''
    },
    getTableData() {
      if (this.reportData.length < 1) {
        this.$alert('暂无体检报告')
        return
      }
      let url = this.getCookie('imgPath')
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
    white-space: pre-wrap;
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
  width: 210mm;
  height: 100%;
  margin: 0 auto;
  padding: 20px 40px;
  background: #fff;
  display: flex;
  flex-direction: column;
  position: relative;
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
    page-break-before: always;
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
  // table {
  //   page-break-inside: auto;
  // }
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
    margin: 13px 13mm; // 可以控制打印布局（四周边距）
  }
}
</style>
