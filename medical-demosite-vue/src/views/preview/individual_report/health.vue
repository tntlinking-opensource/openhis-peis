<!-- 健康个检报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-health-report" v-if="reportList.length">
    <div ref="capture" class="capture" id="capture" v-show="$route.query.from != 1 && !isMobile()">
      <img ref="capture" :src="capturePath" alt="" style="width: 100px" @mouseup="clickCapture" />
    </div>
    <!-- 页眉 -->
    <div class="header">
      <img src="@/assets/logo/zk_logo.png" />
      <span v-if="reportList.length == 1">{{ reportList[0].head.parameters.patientcode }} - {{ reportList[0].head.parameters.patientname }}</span>
    </div>
    <!-- 页脚 -->
    <div class="footer">
      <span v-if="reportList.length == 1">{{ reportList[0].head.parameters.patientcode }}</span>
      <span v-else>{{ branchInfo.name }}</span>
    </div>
    <div class="reportList only-scale" :style="isMobile() ? 'display:none' : ''" v-for="(reportData, index) in reportList" :key="'scale' + index">
      <div class="individual-report" v-if="reportData" id="pdfDom">
        <!-- 封面-VVIP -->
        <div class="report-cover-vvip" v-if="reportData.head && reportData.head.VVIP">
          <div class="cover-header">
            <img src="@/assets/logo/logo3.png" />
          </div>
          <div class="cover-bottom">
            <div class="bottom-info">
              <div class="info-title"><span>姓名</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.patientname }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>体检号码</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.patientcode }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>性别</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.sex }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>年龄</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.age }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>电话</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.phone }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>体检日期</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.dateregister }}</div>
            </div>
          </div>
          <div class="cover-bg">
            <div class="bg-row">
              <img src="@/assets/images/report/vvip_1.png" />
              <div class="row-center">
                <div class="title">健康检查报告书</div>
                <div class="subtitle">HEALTH EXAMINATION REPORT</div>
              </div>
              <img src="@/assets/images/report/vvip_1.png" />
            </div>
            <div class="bg-col">
              <img src="@/assets/images/report/vvip_2.png" />
            </div>
            <div class="vvip">
              <img src="@/assets/images/vvip.png" alt="" srcset="" />
            </div>
          </div>
        </div>
        <!-- 封面-普通会员 -->
        <div class="report-cover" v-else>
          <div class="cover-top">
            <div class="cover-header">
              <div class="header-left">
                <div style="padding: 150px"></div>
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
              <div class="content-info" v-for="(item, index2) in reportData.head.titleList" :key="index2">
                <div class="info-title">
                  <span>{{ item.title }}</span>
                </div>
                <div class="info-cont">
                  {{ item.value }}
                  <img style="width: 120px; height: 20px; margin-left: 4px" :id="'barcodeHead' + index" v-if="index2 == 2 && $route.query.from != 1 && $route.query.from != 2" />
                </div>
              </div>
            </div>
          </div>
          <div class="cover-bottom">
            <span>地址：{{ branchInfo.address }}</span>
            <span>电话：{{ branchInfo.phone }}</span>
            <span>邮编：{{ branchInfo.postalCode }}</span>
          </div>
        </div>
        <!-- 前言 -->
        <div class="report-preface">
          <div class="title">怎样看体检报告</div>
          <div class="content">
            <h3>☆体检与“看病”不同：</h3>
            <p>
              每个人都有“看病”的经历。“看病”是对疾病进行检查、诊断、采取治疗的过程。健 康体检是针对常见而且影响健康的异常指标进行筛查的过程。通过多项检查结果，对健 康状况作出初步评价，目的是提醒您关注您的健康并且积极行动起来，改变不良的生活
              方式，树立“主动防病，有病早治”的理念，所以体检的健康总评与看病的诊断是不同 的。健康体检发现指标异常，就会给您一个提醒，而不是达到疾病的诊断标准才提醒您 ，所以健康体检不是“看病”。健康体检内容主要包括：一是医生对你所检查的内容进
              行评价，让你了解自己的健康状况，以采取相应的保健措施；二是对明确的疾病作出诊 断，提醒你要接受必要的治疗；三是对一些可疑指标或结果，建议进一步检查或追踪观 察。需要声明的是：限于大部分单位体检只是针对最常见的问题而设计，不可能把每个
              人的异常都做出检查；一次体检只是对本次体检内容进行评价，不包括检测项目以外的 评价。人们祈望在健康没有出现大问题之前，把所有异常问题都提前解决，即使以目前 先进的医疗技术，仍然不能实现。
            </p>
            <h3>☆影响体检结果的因素：</h3>
            <p>
              有一些因素会影响到体检结果，如精神紧张、过度兴奋、睡眠不足都可以影响心率、血 压等；宵夜可以影响血糖、血脂等；患有糖尿病、高血压等病在服药前、后其检测结果 是不同的；又如尿液中基本上没有红细胞，但女性经期可因为污染，尿中会出现较多的
              红细胞。所以体检前一天晚上，饮食宜简单、清淡、尽量不要吃夜宵，也不宜长时间做 娱乐活动和剧烈的体育活动。
            </p>
            <h3>☆怎样对待体检的异常指标：</h3>
            <p>
              <b
                >体检后会发现一些异常指标，这些异常指标有的是有诊断意义的，而有的只是为进一步 检查提供了线索，所以对于异常指标就需要向医生或健康管理师咨询。要提醒您的是 ：各项检测结果是动态变化的，以后复查这些指标会有所不同，遇到这种情况也可以向
                我们咨询。更加关键的是：发现异常指标后，自己一定要行动起来！通过自己的行动来 改善和控制这些异常指标，这是您自己对您的健康最好的奖赏！</b
              >
            </p>
            <h3>☆温馨提示</h3>
            <p class="tips">生活在步调繁忙紧凑的现代社会里</p>
            <p class="tips">您是否曾停下脚步聆听身体的声音</p>
            <p class="tips">许多身体细微的声响</p>
            <p class="tips">都在提醒您应该更关心它的变化</p>
            <p class="tips">您或许忽略了或许听不到</p>
            <p class="tips">但却不代表它不存在</p>
          </div>
        </div>
        <!-- 内容 -->
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
                <!-- 内容 -->
                <div class="report-main">
                  <div class="empty-box"></div>
                  <div class="empty-box"></div>
                  <div class="main-info">
                    <table border="0" cellspacing="0">
                      <tr>
                        <td colspan="5">
                          <img class="barcode" :id="'barcode' + index" />
                        </td>
                        <td rowspan="4" style="width: 180px">
                          <img class="avatar" :src="imgPath + reportData.head.parameters.head" />
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="td-title">体检号码</div>
                          <span>{{ reportData.head.parameters.patientcode }}</span>
                        </td>
                        <td>
                          <div class="td-title">姓名</div>
                          <span>{{ reportData.head.parameters.patientname }}</span>
                        </td>
                        <td>
                          <div class="td-title">性别</div>
                          <span>{{ reportData.head.parameters.sex }}</span>
                        </td>
                        <td>
                          <div class="td-title">年龄</div>
                          <span>{{ reportData.head.parameters.age }}</span>
                        </td>
                        <td>
                          <div class="td-title">婚姻</div>
                          <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][reportData.head.parameters.marriage] }}</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="td-title">健康档案号码</div>
                          <span>{{ reportData.head.parameters.patientarchive }}</span>
                        </td>
                        <td colspan="4">
                          <div class="td-title">登记时间</div>
                          <span>{{ reportData.head.parameters.dateregister }}</span>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="5">
                          <div class="td-title">体检单位名称：</div>
                          <span>{{ reportData.head.parameters.workunit }}</span>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div class="main-offer">
                    <div class="offer-title">【体检建议】</div>
                    <div class="offer-content" style="white-space: pre-wrap">{{ reportData.head.parameters.totaloffer }}</div>
                    <div class="offer-info">
                      <div class="info-box">
                        <div class="info-item" style="page-break-inside: avoid">
                          <div class="item-title">总检医生：</div>
                          <div class="item-cont">
                            <template v-if="cid == 2">
                              <img v-if="reportData.head.parameters.signPic" :src="imgPath + reportData.head.parameters.signPic" alt="" style="width: 50px; max-height: 35px" />
                              <span v-else-if="reportData.head.parameters.totaldoctor">
                                {{ reportData.head.parameters.totaldoctor }}
                              </span>
                              <!-- 默认签名 -->
                              <template v-if="reportData.head.parameters.defaultSignPic">
                                <span style="display: inline-block; font-size: 22px; font-weight: 400" :style="{ 'margin-left': reportData.head.parameters.signPic ? '' : '5px' }">/</span>
                                <img :src="imgPath + reportData.head.parameters.defaultSignPic" alt="" style="width: 50px; max-height: 35px" />
                              </template>
                              <span v-else-if="reportData.head.parameters.defaultTotaldoctor">
                                {{ '/' + reportData.head.parameters.defaultTotaldoctor }}
                              </span>
                            </template>
                            <template v-else>
                              {{ reportData.head.parameters.totaldoctor }}
                            </template>
                          </div>
                        </div>
                        <div class="info-item">
                          <div class="item-title">咨询电话：</div>
                          <div class="item-cont">{{ branchInfo.phone }}</div>
                        </div>
                        <div class="info-item">
                          <div class="item-title">报告日期：</div>
                          <div class="item-cont" style="border: none">{{ reportData.head.parameters.reportdate }}</div>
                        </div>
                      </div>
                      <img style="width: 140px; object-fit: fill; position: absolute;top:0;right: 0; z-index: 1; opacity: 0.8; page-break-inside: avoid" :src="imgPath + branchInfo.stampPic" alt="" />
                    </div>
                  </div>
                  <div class="main-illustrate fox-page-after">
                    <div class="title">说明：</div>
                    <p>1、以上是对本次体检部分阳性结果的解释和建议，请您详细阅读本报告的全部内容，如 发现其他异常结果或有特殊疑问，请及时预约中心专家咨询，必要时进行复查以免延误 诊断。</p>
                    <p>2、本次体检报告反映的只是本次检查的结果及评估。</p>
                    <p>3、本次体检设定为健康体检，其结果与评估仅供参考使用。如要进行特殊疾病的筛查与 诊断，请咨询专家后选择特定检查项目，以免漏检。</p>
                    <p>4、本体检报告为个人健康体检保密资料，我们已提供了独立保密包装，请注意保管。</p>
                    <p>5、温馨提示：妇科检查项目中的月经史、婚育史信息由客户本人提供，只供大夫参考 ，若有不符可由客户本人提出更改。</p>
                  </div>
                  <section-report :show-data="reportData.ksList" :endPicture="reportData.endPicture" type-data="0" :imgPath="imgPath" :style="reportData.ksList && reportData.ksList.length ? 'page-break-before: always' : ''"></section-report>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <!-- 相关检测报告 -->
        <div class="detection-report" v-if="reportData.threeReport && reportData.threeReport.length">
          <div class="report-item" v-for="(value, index) in reportData.threeReport" :key="index" style="background-color: #fff; margin-bottom: 8px">
            <img :src="imgPath + value" alt="" style="width: 95%; height: 95%; margin: 0" />
          </div>
        </div>
        <!-- 封底-VVIP -->
        <div class="report-end-vvip" v-if="reportData.head && reportData.head.VVIP">
          <div class="end-info">
            <div class="info-top">
              <div class="top-cont">青岛沃德国际医疗健康产业股份有限公司</div>
              <div class="top-cont">股票代码：832113</div>
              <div class="top-cont">全国热线：400-0532-969</div>
            </div>
            <div class="info-bottom">
              <div class="bottom-cont">{{ reportData.head.VVIP.fzx }}</div>
              <div class="bottom-cont">地址：{{ reportData.head.VVIP.org_address }}</div>
              <div class="bottom-cont">电话：{{ reportData.head.VVIP.org_phone }}</div>
            </div>
          </div>
          <img class="end-code" src="@/assets/images/report/mini_code.png" />
        </div>
        <!-- 封底-普通会员 -->
        <div class="report-end" v-else id="report-end">
          <img v-if="branchInfo.logo" class="end-logo" :src="imgPath + branchInfo.logo" />
          <img v-if="branchInfo.miniProgramCode" class="end-code" :src="imgPath + branchInfo.miniProgramCode" />
          <img v-if="branchInfo.qrcode" class="end-code" :src="imgPath + branchInfo.qrcode" />
        </div>
        <!-- 隐私报告 -->
        <div class="report-end empty-show" v-if="reportData.ysbg"></div>
        <table v-if="reportData.ysbg">
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
                <div class="report-private">
                  <private-report :ref="'privateReport' + index" :report-data="reportData.ysbg" style="page-break-before: always"></private-report>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="reportList" :class="isMobile() ? '' : 'only-print'" v-for="(reportData, index) in reportList" :key="index">
      <div class="individual-report" v-if="reportData">
        <!-- 封面-VVIP -->
        <div class="report-cover-vvip" v-if="reportData.head && reportData.head.VVIP">
          <div class="cover-header">
            <img src="@/assets/logo/logo3.png" />
          </div>
          <div class="cover-bottom">
            <div class="bottom-info">
              <div class="info-title"><span>姓名</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.patientname }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>体检号码</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.patientcode }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>性别</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.sex }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>年龄</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.age }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>电话</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.phone }}</div>
            </div>
            <div class="bottom-info">
              <div class="info-title"><span>体检日期</span>：</div>
              <div class="info-cont">{{ reportData.head.parameters.dateregister }}</div>
            </div>
          </div>
          <div class="cover-bg">
            <div class="bg-row">
              <img src="@/assets/images/report/vvip_1.png" />
              <div class="row-center">
                <div class="title">健康检查报告书</div>
                <div class="subtitle">HEALTH EXAMINATION REPORT</div>
              </div>
              <img src="@/assets/images/report/vvip_1.png" />
            </div>
            <div class="bg-col">
              <img src="@/assets/images/report/vvip_2.png" />
            </div>
            <div class="vvip">
              <img src="@/assets/images/vvip.png" alt="" srcset="" />
            </div>
          </div>
        </div>
        <!-- 封面-普通会员 -->
        <div class="report-cover" v-else>
          <div class="cover-top">
            <div class="cover-header">
              <div class="header-left">
                <div style="padding: 150px"></div>
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
              <div class="content-info" v-for="(item, index2) in reportData.head.titleList" :key="index2">
                <div class="info-title">
                  <span>{{ item.title }}</span>
                </div>
                <div class="info-cont">
                  {{ item.value }}
                  <img style="width: 120px; height: 20px; margin-left: 4px" :id="'barcodeHead' + index" v-if="index2 == 2 && $route.query.from != 1 && $route.query.from != 2" />
                </div>
              </div>
            </div>
          </div>
          <div class="cover-bottom">
            <span>地址：{{ branchInfo.address }}</span>
            <span>电话：{{ branchInfo.phone }}</span>
            <span>邮编：{{ branchInfo.postalCode }}</span>
          </div>
        </div>
        <!-- 前言 -->
        <div class="report-preface">
          <div class="title">怎样看体检报告</div>
          <div class="content">
            <h3>☆体检与“看病”不同：</h3>
            <p>
              每个人都有“看病”的经历。“看病”是对疾病进行检查、诊断、采取治疗的过程。健 康体检是针对常见而且影响健康的异常指标进行筛查的过程。通过多项检查结果，对健 康状况作出初步评价，目的是提醒您关注您的健康并且积极行动起来，改变不良的生活
              方式，树立“主动防病，有病早治”的理念，所以体检的健康总评与看病的诊断是不同 的。健康体检发现指标异常，就会给您一个提醒，而不是达到疾病的诊断标准才提醒您 ，所以健康体检不是“看病”。健康体检内容主要包括：一是医生对你所检查的内容进
              行评价，让你了解自己的健康状况，以采取相应的保健措施；二是对明确的疾病作出诊 断，提醒你要接受必要的治疗；三是对一些可疑指标或结果，建议进一步检查或追踪观 察。需要声明的是：限于大部分单位体检只是针对最常见的问题而设计，不可能把每个
              人的异常都做出检查；一次体检只是对本次体检内容进行评价，不包括检测项目以外的 评价。人们祈望在健康没有出现大问题之前，把所有异常问题都提前解决，即使以目前 先进的医疗技术，仍然不能实现。
            </p>
            <h3>☆影响体检结果的因素：</h3>
            <p>
              有一些因素会影响到体检结果，如精神紧张、过度兴奋、睡眠不足都可以影响心率、血 压等；宵夜可以影响血糖、血脂等；患有糖尿病、高血压等病在服药前、后其检测结果 是不同的；又如尿液中基本上没有红细胞，但女性经期可因为污染，尿中会出现较多的
              红细胞。所以体检前一天晚上，饮食宜简单、清淡、尽量不要吃夜宵，也不宜长时间做 娱乐活动和剧烈的体育活动。
            </p>
            <h3>☆怎样对待体检的异常指标：</h3>
            <p>
              <b
                >体检后会发现一些异常指标，这些异常指标有的是有诊断意义的，而有的只是为进一步 检查提供了线索，所以对于异常指标就需要向医生或健康管理师咨询。要提醒您的是 ：各项检测结果是动态变化的，以后复查这些指标会有所不同，遇到这种情况也可以向
                我们咨询。更加关键的是：发现异常指标后，自己一定要行动起来！通过自己的行动来 改善和控制这些异常指标，这是您自己对您的健康最好的奖赏！</b
              >
            </p>
            <h3>☆温馨提示</h3>
            <p class="tips">生活在步调繁忙紧凑的现代社会里</p>
            <p class="tips">您是否曾停下脚步聆听身体的声音</p>
            <p class="tips">许多身体细微的声响</p>
            <p class="tips">都在提醒您应该更关心它的变化</p>
            <p class="tips">您或许忽略了或许听不到</p>
            <p class="tips">但却不代表它不存在</p>
          </div>
        </div>
        <!-- 内容 -->
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
                <!-- 内容 -->
                <div class="report-main">
                  <div class="empty-box"></div>
                  <div class="empty-box"></div>
                  <div class="main-info">
                    <table border="0" cellspacing="0">
                      <tr>
                        <td colspan="5">
                          <img class="barcode" :id="'barcode' + index" />
                        </td>
                        <td rowspan="4" style="width: 180px">
                          <img class="avatar" :src="imgPath + reportData.head.parameters.head" />
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="td-title">体检号码</div>
                          <span>{{ reportData.head.parameters.patientcode }}</span>
                        </td>
                        <td>
                          <div class="td-title">姓名</div>
                          <span>{{ reportData.head.parameters.patientname }}</span>
                        </td>
                        <td>
                          <div class="td-title">性别</div>
                          <span>{{ reportData.head.parameters.sex }}</span>
                        </td>
                        <td>
                          <div class="td-title">年龄</div>
                          <span>{{ reportData.head.parameters.age }}</span>
                        </td>
                        <td>
                          <div class="td-title">婚姻</div>
                          <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][reportData.head.parameters.marriage] }}</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="td-title">健康档案号码</div>
                          <span>{{ reportData.head.parameters.patientarchive }}</span>
                        </td>
                        <td colspan="4">
                          <div class="td-title">登记时间</div>
                          <span>{{ reportData.head.parameters.dateregister }}</span>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="5">
                          <div class="td-title">体检单位名称：</div>
                          <span>{{ reportData.head.parameters.workunit }}</span>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div class="main-offer">
                    <div class="offer-title">【体检建议】</div>
                    <div class="offer-content" style="white-space: pre-wrap">{{ reportData.head.parameters.totaloffer }}</div>
                    <div class="offer-info">
                      <div class="info-box">
                        <div class="info-item" style="page-break-inside: avoid">
                          <div class="item-title">总检医生：</div>
                          <div class="item-cont">
                            <template v-if="cid == 2">
                              <img v-if="reportData.head.parameters.signPic" :src="imgPath + reportData.head.parameters.signPic" alt="" style="width: 50px; max-height: 35px" />
                              <span v-else-if="reportData.head.parameters.totaldoctor">
                                {{ reportData.head.parameters.totaldoctor }}
                              </span>
                              <!-- 默认签名 -->
                              <template v-if="reportData.head.parameters.defaultSignPic">
                                <span style="display: inline-block; font-size: 22px; font-weight: 400" :style="{ 'margin-left': reportData.head.parameters.signPic ? '' : '5px' }">/</span>
                                <img :src="imgPath + reportData.head.parameters.defaultSignPic" alt="" style="width: 50px; max-height: 35px" />
                              </template>
                              <span v-else-if="reportData.head.parameters.defaultTotaldoctor">
                                {{ '/' + reportData.head.parameters.defaultTotaldoctor }}
                              </span>
                            </template>
                            <template v-else>
                              {{ reportData.head.parameters.totaldoctor }}
                            </template>
                          </div>
                        </div>
                        <div class="info-item">
                          <div class="item-title">咨询电话：</div>
                          <div class="item-cont">{{ branchInfo.phone }}</div>
                        </div>
                        <div class="info-item">
                          <div class="item-title">报告日期：</div>
                          <div class="item-cont" style="border: none">{{ reportData.head.parameters.reportdate }}</div>
                        </div>
                      </div>
                      <img style="width: 140px; object-fit: fill; position: absolute;top:0; right: 0; z-index: 1; opacity: 0.8; page-break-inside: avoid" :src="imgPath + branchInfo.stampPic" alt="" />
                    </div>
                  </div>
                  <div class="main-illustrate fox-page-after">
                    <div class="title">说明：</div>
                    <p>1、以上是对本次体检部分阳性结果的解释和建议，请您详细阅读本报告的全部内容，如 发现其他异常结果或有特殊疑问，请及时预约中心专家咨询，必要时进行复查以免延误 诊断。</p>
                    <p>2、本次体检报告反映的只是本次检查的结果及评估。</p>
                    <p>3、本次体检设定为健康体检，其结果与评估仅供参考使用。如要进行特殊疾病的筛查与 诊断，请咨询专家后选择特定检查项目，以免漏检。</p>
                    <p>4、本体检报告为个人健康体检保密资料，我们已提供了独立保密包装，请注意保管。</p>
                    <p>5、温馨提示：妇科检查项目中的月经史、婚育史信息由客户本人提供，只供大夫参考 ，若有不符可由客户本人提出更改。</p>
                  </div>
                  <section-report :show-data="reportData.ksList" :endPicture="reportData.endPicture" type-data="0" :imgPath="imgPath" :style="reportData.ksList && reportData.ksList.length ? 'page-break-before: always' : ''"></section-report>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <!-- 相关检测报告 -->
        <div class="detection-report" v-if="reportData.threeReport && reportData.threeReport.length">
          <div class="report-item" v-for="(value, index) in reportData.threeReport" :key="index" style="background-color: #fff; margin-bottom: 8px">
            <img :src="imgPath + value" alt="" style="width: 95%; height: 95%; margin: 0" />
          </div>
        </div>
        <!-- 封底-VVIP -->
        <div class="report-end-vvip" v-if="reportData.head && reportData.head.VVIP">
          <div class="end-info">
            <div class="info-top">
              <div class="top-cont">青岛沃德国际医疗健康产业股份有限公司</div>
              <div class="top-cont">股票代码：832113</div>
              <div class="top-cont">全国热线：400-0532-969</div>
            </div>
            <div class="info-bottom">
              <div class="bottom-cont">{{ reportData.head.VVIP.fzx }}</div>
              <div class="bottom-cont">地址：{{ reportData.head.VVIP.org_address }}</div>
              <div class="bottom-cont">电话：{{ reportData.head.VVIP.org_phone }}</div>
            </div>
          </div>
          <img class="end-code" src="@/assets/images/report/mini_code.png" />
        </div>
        <!-- 封底-普通会员 -->
        <div class="report-end" v-else id="report-end">
          <img v-if="branchInfo.logo" class="end-logo" :src="imgPath + branchInfo.logo" />
          <img v-if="branchInfo.miniProgramCode" class="end-code" :src="imgPath + branchInfo.miniProgramCode" />
          <img v-if="branchInfo.qrcode" class="end-code" :src="imgPath + branchInfo.qrcode" />
        </div>
        <!-- 隐私报告 -->
        <!-- 增加双面打印前换页 -->
        <div class="report-end empty-show" v-if="reportData.ysbg"></div>
        <table v-if="reportData.ysbg">
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
                <div class="report-private">
                  <private-report :ref="'privateReport' + index" :report-data="reportData.ysbg" style="page-break-before: always"></private-report>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div :style="isMobile() ? 'height: 82px;' : ''" class="placeholder"></div>
    <div class="print-fixed">
      <el-button class="mobile" type="primary" @click="handlePrint()" v-if="isMobile()" style="display: none">下 载</el-button>
      <el-button type="primary" @click="handlePrint()" v-else>打 印</el-button>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import { getData, getBranchConfig } from '@/api/preview/individual_report.js'
import { getCultural } from '@/utils/dataList.js'
import { getOnlineDomain, getOnlineData, getOnlineConfig } from '@/api/preview/search_report.js'
import { detailsApi, getShareConfig } from '@/api/preview/share_report.js'
import { printAlready } from '@/api/report/report_print/health_report.js'
import JsBarcode from 'jsbarcode'
import sectionReport from './module/section'
import privateReport from './module/private'

import html2Canvas from 'html2canvas'
export default {
  name: 'IndividualHealthReport',
  components: { sectionReport, privateReport },
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath') || 'https://newtj.obs.cn-north-4.myhuaweicloud.com',
      // imgPath: Cookies.get('imgPath') || 'https://zkmedical.oss-cn-qingdao.aliyuncs.com',
      // 分中心id
      cid: Cookies.get('cid'),
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
      reportList: [],
      // reportData: undefined,
      //  文化程度
      culturalList: [],
      // 医院信息
      branchInfo: {},
      // 略缩图地址
      capturePath: '',
    }
  },
  created() {
    document.title = '健康报告'
    this.culturalList = getCultural()
    this.queryParams.patientcode = this.$route.query.patientcode || ''
    // this.queryParams.showAllImage = this.$route.query.showAllImage || 0
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    if (this.$route.query.from == 1) {
      this.getOnlineReport()
    } else if (this.$route.query.from == 2) {
      this.getShareReport()
    } else {
      this.getReport()
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
    //打印按钮
    handlePrint() {
      printAlready(this.queryParams.patientcode)
      window.print() // 修改打印状态
    },
    // 获取报告数据-主系统
    getReport() {
      let obj = {
        idExamtype: 0, //体检类型,0.健康体检 1.职业体检
        patientcode: '', //体检号
        reportType: 5, //报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 6.对比报告 7.Pacs报告
      }
      this.reportList = []
      if (this.queryParams.patientcode) {
        if (typeof this.queryParams.patientcode == 'string') {
          obj.patientcode = this.queryParams.patientcode
          this.goGetData(obj, 1)
        } else {
          for (let i = 0; i < this.queryParams.patientcode.length; i++) {
            obj.patientcode = this.queryParams.patientcode[i]
            this.goGetData(obj, 0)
          }
        }
      } else {
        this.$modal.alertWarning('暂无报告，请稍后再试', '提醒')
        this.loading.close()
      }
    },
    // 调用接口-主系统
    goGetData(context, only) {
      getData(context).then((res) => {
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
        this.reportList.push(res.data)
        // this.reportList.forEach(el=>{
        //   el.ksList.forEach(val=>{
        //   })
        // })
        if (only) {
          document.title = res.data.head.parameters.patientcode + '-' + res.data.head.parameters.patientname + '-' + res.data.head.parameters.dateregister.split(' ')[0] + ' 健康报告'
        }
        if (!Array.isArray(this.queryParams.patientcode) || this.reportList.length == this.queryParams.patientcode.length) {
          this.$nextTick(() => {
            this.reportList.forEach((el, index) => {
              JsBarcode('#barcode' + index, el.head.parameters.patientcode, {
                format: 'CODE39', //条形码的格式
                width: 1, //线宽
                height: 30, //条码高度
                fontSize: 16,
                lineColor: '#000', //线条颜色
                displayValue: true, //是否显示文字
                margin: 2, //设置条形码周围的空白区域
              })
              if (this.$route.query.from != 1 && el.head && !el.head.VVIP) {
                JsBarcode('#barcodeHead' + index, el.head.parameters.patientcode, {
                  format: 'CODE128', //条形码的格式
                  width: 1, //线宽
                  height: 30, //条码高度
                  // fontSize: 16,
                  lineColor: '#000', //线条颜色
                  displayValue: false, //是否显示文字
                  margin: 0, //设置条形码周围的空白区域
                })
              }
              el.ksList.forEach((val) => {
                for (let i = val.summary.CONCLUSIONS.length - 1; i >= 1; i--) {
                  if (val.summary.CONCLUSIONS[i].conclu == '' && val.summary.CONCLUSIONS[i - 1].conclu == '') {
                    this.$delete(val.summary.CONCLUSIONS, i)
                  }
                }
              })
            })

            this.$nextTick(() => {
              setTimeout(() => {
                var opts = {
                  scale: 0.1,
                  useCORS: true, // 【重要】开启跨域配置
                }
                html2Canvas(document.querySelector('#pdfDom'), opts).then((canvas) => {
                  this.capturePath = canvas.toDataURL('image/png')
                })
              }, 0)
            })
          })
          this.loading.close()
        }
      })
      var query = {
        branchId: this.$getCookie('cid'),
      }
      getBranchConfig(query).then(({ data }) => {
        this.branchInfo = data || { address: '' }
      })
    },
    // 获取报告数据-线上
    getOnlineReport() {
      var storage = localStorage.getItem('searchReport')
      if (!storage || !JSON.parse(storage).token) {
        let routeUrl = this.$router.resolve({
          name: 'SearchReport',
        })
        window.open(routeUrl.href, '_self')
        return
      }
      var query = this.decode(this.$route.query.id)
      var token = JSON.parse(localStorage.getItem('searchReport')).token
      let obj = {
        token: token,
        id: query.split('&')[1],
      }
      this.reportList = []
      getOnlineDomain({ token: token }).then((res) => {
        this.imgPath = res.mdResources
        getOnlineData(obj).then((res) => {
          if (!res) {
            this.$modal.alertWarning(res.msg, '提醒')
            this.loading.close()
            return
          }
          if (res.data && res.data.ksList) {
            res.data.ksList.forEach((val) => {
              if (val && val.summary && val.summary.P_CONCLUSIONS) {
                val.summary.P_CONCLUSIONS = this.unhtml(val.summary.P_CONCLUSIONS)
              }
            })
          }
          this.reportList.push(res)
          if (!Array.isArray(this.queryParams.patientcode) || this.reportList.length == this.queryParams.patientcode.length) {
            this.$nextTick(() => {
              this.reportList.forEach((el, index) => {
                JsBarcode('#barcode' + index, el.head.parameters.patientcode, {
                  format: 'CODE39', //条形码的格式
                  width: 1, //线宽
                  height: 40, //条码高度
                  fontSize: 16,
                  lineColor: '#000', //线条颜色
                  displayValue: true, //是否显示文字
                  margin: 2, //设置条形码周围的空白区域
                })
              })
            })
            this.loading.close()
          }
        })
      })
      getOnlineConfig({ token: token, branchId: query.split('&')[0] }).then((res) => {
        this.branchInfo = res || { address: '' }
      })
    },
    // 获取报告数据-线上分享
    getShareReport() {
      this.reportList = []
      detailsApi({
        id: this.$route.query.id,
      }).then((res) => {
        if (!res) {
          this.$modal.alertWarning(res.msg, '提醒')
          this.loading.close()
          return
        }
        if (res.data && res.data.ksList) {
          res.data.ksList.forEach((val) => {
            if (val && val.summary && val.summary.P_CONCLUSIONS) {
              val.summary.P_CONCLUSIONS = this.unhtml(val.summary.P_CONCLUSIONS)
            }
          })
        }
        this.reportList.push(res.data)
        if (!Array.isArray(this.queryParams.patientcode) || this.reportList.length == this.queryParams.patientcode.length) {
          this.$nextTick(() => {
            this.reportList.forEach((el, index) => {
              JsBarcode('#barcode' + index, el.head.parameters.patientcode, {
                format: 'CODE39', //条形码的格式
                width: 1, //线宽
                height: 40, //条码高度
                fontSize: 16,
                lineColor: '#000', //线条颜色
                displayValue: true, //是否显示文字
                margin: 2, //设置条形码周围的空白区域
              })
            })
          })
          this.loading.close()
        }
      })
      var query = {
        branchId: this.$route.query.cid,
      }
      getShareConfig(query).then(({ data }) => {
        this.branchInfo = data || { address: '' }
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
    // 参数解密
    decode(str) {
      return decodeURIComponent(
        atob(str)
          .split('')
          .map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
          })
          .join('')
      )
    },
    // 获取文化程度
    getCultural(val) {
      for (var i in this.culturalList) {
        if (val == this.culturalList[i].value) {
          return this.culturalList[i].label
        }
      }
    },
    // 点击略缩图跳转指定位置
    clickCapture($event) {
      let offsetY = $event.offsetY
      let picHeight = $event.target.clientHeight
      const pageHeight = document.documentElement.scrollHeight
      const targetHeight = (pageHeight * offsetY) / picHeight
      window.scrollTo({
        top: targetHeight,
        behavior: 'smooth', // 平滑滚动
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

.preview-individual-health-report {
  min-height: 100%;
  padding: 20px 10px;

  .capture {
    position: fixed;
    top: 50%;
    left: 60px;
    transform: translateY(-50%);
    z-index: 99;
    max-height: 90vh;
    overflow-y: auto;
    ::-webkit-scrollbar {
      width: 200px !important; /* Set the width of the scrollbar */
    }
  }

  .placeholder {
    width: 100%;
    height: 50px;
  }
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
      margin-top: 58px;
      .content-info {
        width: 336px;
        margin: 0 auto;
        display: flex;
        align-items: center;
        margin-top: 8px;

        .info-title {
          display: flex;
          width: 80px;
          height: 22px;
          line-height: 22px;
          font-size: 16px;
          font-weight: 500;

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
          font-size: 16px;
          border-bottom: 1px solid #000;
          flex: 1;
          min-height: 24px;
          line-height: 22px;
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

  .report-cover-vvip {
    page-break-after: always;
    position: relative;
    height: 297mm;
    width: 100%;
    background: #fff;
    padding: 40px 50px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .cover-header {
      img {
        width: 220px;
        height: auto;
      }
    }

    .cover-bottom {
      .bottom-info {
        width: 270px;
        display: flex;
        align-items: center;
        margin-top: 10px;

        .info-title {
          width: 80px;
          font-size: 16px;
          line-height: 22px;
          color: #d0aa79;
          height: 22px;
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
          font-size: 16px;
          border-bottom: 1px solid #d0aa79;
          flex: 1;
          min-height: 24px;
          line-height: 22px;
          font-weight: bold;
        }
      }
    }

    .cover-bg {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      display: flex;
      align-items: center;

      .bg-row {
        flex: 1;
        padding-bottom: 240px;

        img {
          width: 100%;
          height: auto;
          display: block;
        }

        .row-center {
          background: #d0aa79;
          padding: 65px 52px;

          .title {
            font-size: 42px;
            line-height: 60px;
            color: #fff;
            font-weight: bold;
            letter-spacing: 6px;
          }

          .subtitle {
            font-size: 18px;
            line-height: 26px;
            color: #fff;
            letter-spacing: 1.4px;
          }
        }
      }

      .bg-col {
        width: 260px;
        height: 100%;

        img {
          width: 100%;
          height: 100%;
        }
      }
      .vvip {
        position: absolute;
        right: 260.5px;
        bottom: 70px;
        img {
          width: 180px;
          height: 180px;
        }
      }
    }
  }

  .report-preface {
    page-break-after: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 80px;
    height: 297mm;

    .title {
      text-align: center;
      font-size: 24px;
      line-height: 34px;
      color: #333333;
      font-weight: 600;
    }

    .content {
      h3 {
        margin: 16px 0 0;
        font-size: 18px;
        line-height: 24px;
        color: #333333;
        font-weight: bold;
      }

      p {
        margin: 16px 0 0;
        font-size: 16px;
        line-height: 24px;
        color: #333333;
      }

      .tips {
        margin-top: 8px;
        font-weight: 600;
      }
    }
  }

  .report-main {
    margin: 0 auto;
    background: #fff;
    padding: 0 80px 40px;

    .main-info {
      table {
        width: 100%;
        border: 1px solid #000;

        tr {
          td {
            border: 1px solid #000;
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

            .barcode {
              margin: 0 auto;
              display: block;
            }

            .avatar {
              width: 130px;
              height: 164px;
              object-fit: cover;
              display: block;
              margin: 0 auto;
            }
          }
        }
      }
    }

    .main-offer {
      .offer-title {
        font-weight: 600;
        font-size: 18px;
        margin-top: 24px;
      }

      .offer-content {
        margin-top: 16px;
        font-size: 14px;
        line-height: 18px;
      }

      .offer-info {
        position: relative;
        display: flex;
        justify-content: end;
        padding-top: 20px;
        padding-right: 40px;
        padding-bottom: 32px;

        .info-item {
          margin-top: 8px;
          display: flex;
          align-items: center;
          width: auto;

          .item-title {
            font-size: 14px;
            font-weight: 600;
            line-height: 22px;
          }

          .item-cont {
            flex: 1;
            display: flex;
            align-items: center;
            min-width: 140px;
            min-height: 22px;
            line-height: 22px;
            font-size: 14px;
            font-weight: 600;
            border-bottom: 1px solid #000;
          }
        }
      }
    }

    .main-illustrate {
      // 说明模块后打印要另起一页
      // page-break-after: auto;

      .title {
        font-size: 16px;
        line-height: 20px;
        margin-bottom: 12px;
      }

      p {
        margin: 0;
        font-size: 14px;
        line-height: 24px;
      }
    }
  }

  .report-end {
    page-break-before: always;
    page-break-inside: avoid;
    margin: 8px auto 0;
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
  .empty-show {
    display: none;
  }

  .report-end-vvip {
    page-break-before: always;
    background: #fff;
    height: 297mm;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .end-info {
      border-left: 66px solid #d0aa79;
      padding-left: 24px;

      .info-top {
        .top-cont {
          font-size: 16px;
          line-height: 16px;
          color: #d0aa79;
          margin-top: 8px;

          &:first-child {
            font-size: 20px;
            line-height: 20px;
            font-weight: bold;
            margin-top: 0;
          }

          &:last-child {
            font-size: 18px;
            line-height: 18px;
          }
        }
      }

      .info-bottom {
        margin-top: 24px;

        .bottom-cont {
          font-size: 14px;
          font-weight: bold;
          line-height: 24px;
        }
      }
    }

    .end-code {
      width: 150px;
      height: 150px;
      object-fit: contain;
      margin-right: 60px;
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
    box-sizing: border-box;
    width: 210mm;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 80px;
  }
}

// 分页打印的问题
// table {
//   width: 100%;
//   page-break-inside: auto;
//   page-break-after: avoid;
//   page-break-before: avoid;
// }

.header,
.footer {
  display: none;
}

.empty-box {
  padding: 20px;
  background-color: #fff;
}

@media print {
  .preview-individual-health-report {
    padding: 0;
    background: #fff;
    .capture {
      display: none;
    }
    .only-scale {
      display: none;
    }
    .only-print {
      display: block;
    }
  }

  .report-main {
    padding-top: 0 !important;
    padding-bottom: 0 !important;
    margin-top: 0 !important;
  }
  .report-private {
    padding-top: 0 !important;
    margin-top: 0 !important;
  }

  .empty-show {
    display: block !important;
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

  .empty-box,
  .placeholder,
  .print-fixed {
    display: none;
  }
  .page-header {
    padding: 45px 0;
  }
  .header {
    height: 80px;
    margin-bottom: 10px;
  }

  .page-footer {
    padding: 25px 0;
  }
  .footer {
    padding-bottom: 20px;
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
    text-align: center;
    height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #fff;

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
    // height: 50px;
    padding-bottom: 20px;
    border-top: 2px solid #333333;
    font-size: 14px;
    color: #333;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff;
  }

  // @page {
  //   margin: 0;
  // }
}
</style>
<style scoped>
@page {
  margin: 0;
}
</style>
