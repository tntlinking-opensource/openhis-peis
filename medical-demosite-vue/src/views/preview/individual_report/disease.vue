<!-- 职业个检报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-individual-disease-report">
    <!-- 页眉 -->
    <div class="header">
      <span v-if="reportList.length == 1">{{ reportList[0].end.patientcode }} - {{ reportList[0].end.patientname }}</span>
      <span v-else>{{ branchInfo.name }}</span>
    </div>
    <!-- 页脚 -->
    <div class="footer" v-if="reportList.length == 1">{{ reportList[0].end.patientcode }}</div>
    <div v-for="(reportData, index) in reportList" :key="index">
      <div class="individual-report">
        <template v-if="reportData">
          <!-- 封面 -->
          <div class="report-cover" v-if="reportData.head.title != '职业复查'">
            <div class="cover-top">
              <div class="top-header">
                <img class="header-avatar" :src="imgPath + reportData.head.one.head" />
                <div class="header-content">
                  <div class="content-info">
                    <div class="info-title"><span>姓名</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.name }}</div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>单位</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.company }}</div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>部门</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.orgDepart }}</div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>工号</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.jobno }}</div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>联系电话</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.groupphone }}</div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>编号</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.number }}</div>
                  </div>
                  <div class="content-info" style="margin-top: 2px">
                    <div class="info-title"><span>条形码</span>：</div>
                    <div class="info-img"><img class="barcode" style="transform: translateY(-1px)" :id="'barcode' + index" /></div>
                  </div>
                  <div class="content-info">
                    <div class="info-title"><span>填表日期</span>：</div>
                    <div class="info-cont">{{ reportData.head.one.date }}</div>
                  </div>
                  <div class="content-info" style="align-items: start">
                    <div class="info-title"><span>类别</span>：</div>
                    <div class="info-type">
                      <span>上岗前（{{ reportData.head.one.sgq || ' ' }}）</span>
                      <span>在岗期间（{{ reportData.head.one.zgqj || ' ' }}）</span>
                      <span>离岗时（{{ reportData.head.one.lgs || ' ' }}）</span>
                      <span>应急时（{{ reportData.head.one.yj || ' ' }}）</span>
                      <span>离岗后随访（{{ reportData.head.one.lgh || ' ' }}）</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="top-title">职业健康检查表</div>
            </div>
            <div class="cover-bottom">{{ reportData.head.one.producer || branchInfo.producer || '青岛锦都医院印制' }}</div>
          </div>
          <!-- 前言 -->
          <div class="report-preface" v-if="reportData.head.title != '职业复查'">
            <div class="title">报告说明：</div>
            <div class="content">
              <p>1、本报告涂改无效。</p>
              <p>2、本报告无主检医师签字无效。</p>
              <p>3、本报告无本单位盖章无效。</p>
              <p>4、本报告只对本次体检有效。</p>
              <p>5、若对本报告有异议，请于收到报告书之日起十五日内向本机构提出，逾期不予受理。</p>
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
                  <div class="report-main" v-if="reportData.head.title != '职业复查'">
                    <div class="main-info">
                      <div class="info-title">个人基本资料：</div>
                      <div class="info-box">
                        <div class="info-item" style="width: 34%">
                          <div class="item-title"><span>姓名</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.name }}</div>
                        </div>
                        <div class="info-item" style="width: 26%">
                          <div class="item-title"><span>性别</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.sex }}</div>
                        </div>
                        <div class="info-item" style="width: 40%">
                          <div class="item-title long-label"><span>年龄</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.age }}</div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item" style="width: 34%">
                          <div class="item-title"><span>出生地</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.bornAddress }}</div>
                        </div>
                        <div class="info-item" style="width: 26%">
                          <div class="item-title"><span>民族</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.nation }}</div>
                        </div>
                        <div class="info-item" style="width: 40%">
                          <div class="item-title long-label"><span>个人联系电话</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.phone }}</div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item" style="width: 60%">
                          <div class="item-title"><span>身份证号</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.identityCard }}</div>
                        </div>
                        <div class="info-item" style="width: 40%">
                          <div class="item-title long-label"><span>邮政编码</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.postCode }}</div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item">
                          <div class="item-title"><span>家庭地址</span>：</div>
                          <div class="item-cont" style="text-align: left">
                            <span>{{ reportData.head.two.address }}</span>
                          </div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item">
                          <div class="item-title" style="width: auto"><span>接触/拟接触的职业病危害因素</span>：</div>
                          <div class="item-cont" style="text-align: left">
                            <span>{{ reportData.head.two.hagard + reportData.head.two.hagard1 }}</span>
                          </div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item" style="width: 34%">
                          <div class="item-title"><span>总工龄</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.totalAge }}</div>
                        </div>
                        <div class="info-item" style="width: 26%"></div>
                        <div class="info-item" style="width: 40%">
                          <div class="item-title long-label"><span>接害工龄</span>：</div>
                          <div class="item-cont">{{ reportData.head.two.hagardAge }}</div>
                        </div>
                      </div>
                      <!-- 职业史 -->
                      <div class="info-table">
                        <div class="table-title" style="width: auto">职业史（由受检查本人填写）</div>
                        <table border="0" cellspacing="0">
                          <tr>
                            <th style="width: 16%">起止日期</th>
                            <th style="width: 24%">工作单位</th>
                            <th style="width: 12%">车间</th>
                            <th style="width: 12%">工种</th>
                            <th style="width: 24%">有害因素</th>
                            <th style="width: 12%">防护措施</th>
                          </tr>
                          <tr v-for="(item, index) in reportData.head.two.zys" :key="index">
                            <td style="word-break: normal">{{ item.startDate }}</td>
                            <td>{{ item.workUnit }}</td>
                            <td>{{ item.plant }}</td>
                            <td>{{ item.craft }}</td>
                            <td>{{ item.harm }}</td>
                            <td>{{ item.fence }}</td>
                          </tr>
                        </table>
                      </div>
                      <!-- 职业病史 -->
                      <div class="info-table">
                        <div class="table-title" style="width: auto">急慢性职业病史</div>
                        <table border="0" cellspacing="0">
                          <tr>
                            <th style="width: 35%">疾病名称</th>
                            <th style="width: 18%">诊断日期</th>
                            <th style="width: 35%">诊断单位</th>
                            <th style="width: 12%">是否痊愈</th>
                          </tr>
                          <tr v-for="(item, index) in reportData.head.two.zybs" :key="index">
                            <td>{{ item.diseaseName }}</td>
                            <td>{{ item.diagnosisDate }}</td>
                            <td>{{ item.diagnosisUnit }}</td>
                            <td>{{ item.isCure }}</td>
                          </tr>
                        </table>
                      </div>
                      <div class="info-box">
                        <div class="info-item" style="width: 55%; margin-top: 24px">
                          <div class="item-title" style="width: auto">受检人签名</div>
                          <img class="item-img" v-if="reportData.head.two.handwriting" :src="imgPath + reportData.head.two.handwriting" />
                        </div>
                        <div class="info-item" style="width: 45%; margin-top: 24px">
                          <div class="item-title" style="width: auto">用人单位盖章</div>
                        </div>
                      </div>
                      <div class="info-box">
                        <div class="info-item" style="margin-top: 0">
                          <div class="item-title" style="width: auto">{{ reportData.head.two.rummagerTime }}</div>
                        </div>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-title">一、既往病史：</div>
                      <div class="column-cont">
                        <span>{{ reportData.head.three.anamnesis + (reportData.head.three.anamnesis2 ? reportData.head.three.anamnesis2 : '') }}</span>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-box">
                        <div class="box-title">二、月经及生育史：</div>
                        <div class="box-cont">
                          初潮<span>{{ reportData.head.three.beginAge }}</span
                          >岁，
                        </div>
                        <div class="box-cont">
                          经期<span>{{ reportData.head.three.period }}</span
                          >天，
                        </div>
                        <div class="box-cont">
                          周期<span>{{ reportData.head.three.cycle }}</span
                          >天，
                        </div>
                        <div class="box-cont">
                          停经年龄<span>{{ reportData.head.three.menolipsisAge }}</span
                          >岁，
                        </div>
                        <div class="box-cont">
                          现有子女<span>{{ reportData.head.three.babyNum }}</span
                          >人，
                        </div>
                        <div class="box-cont">
                          流产<span>{{ reportData.head.three.misbirth }}</span
                          >次，
                        </div>
                        <div class="box-cont">
                          早产<span>{{ reportData.head.three.prematureBirth }}</span
                          >次，
                        </div>
                        <div class="box-cont">
                          死产<span>{{ reportData.head.three.deadBirth }}</span
                          >次，
                        </div>
                        <div class="box-cont">
                          异常胎<span>{{ reportData.head.three.fetalAnomalies }}</span
                          >次，
                        </div>
                        <div class="box-cont">
                          先天畸形<span>{{ reportData.head.three.malformation }}</span
                          >次，
                        </div>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-title">三、吸烟史：</div>
                      <div class="column-box">
                        <div class="box-cont">{{ reportData.head.three.neverSmoking }}从不吸烟，</div>
                        <div class="box-cont">{{ reportData.head.three.occasionally }}偶尔吸烟，</div>
                        <div class="box-cont">{{ reportData.head.three.former }}以往曾经吸烟，现已戒除，</div>
                        <div class="box-cont">
                          {{ reportData.head.three.often }}经常吸 <span>{{ reportData.head.three.bagNum }}</span
                          >支/天， 共<span>{{ reportData.head.three.smokeYear }}</span
                          >年<span>{{ reportData.head.three.smokeMonth }}</span
                          >月
                        </div>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-title">四、饮酒史：</div>
                      <div class="column-box">
                        <div class="box-cont">{{ reportData.head.three.neverDrank }}从不饮酒，</div>
                        <div class="box-cont">{{ reportData.head.three.occasionallyDrank }}偶尔饮酒，</div>
                        <div class="box-cont">{{ reportData.head.three.formerDrank }}以往曾经饮酒，现已戒除，</div>
                        <div class="box-cont">
                          {{ reportData.head.three.oftenDrank }}现在经常饮 <span>{{ reportData.head.three.ml }}</span
                          >ml/日， 饮酒种类<span>{{ reportData.head.three.types }}</span
                          >， 共<span>{{ reportData.head.three.drankYear }}</span
                          >年<span>{{ reportData.head.three.drankMonth }}</span
                          >月
                        </div>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-title">五、家族史：</div>
                      <div class="column-cont">
                        <span>{{ reportData.head.three.familyHistory }}</span>
                      </div>
                    </div>
                    <div class="main-column">
                      <div class="column-title">六、其他：</div>
                      <div class="column-cont">
                        <span>{{ reportData.head.three.other || '无' }}</span>
                      </div>
                    </div>
                    <div class="main-column" style="display: block">
                      <div class="column-title">七、症状：</div>
                      <table class="column-table" border="0" cellspacing="0">
                        <tr>
                          <th style="width: 30%">项目</th>
                          <th style="width: 20%">症状程度</th>
                          <th style="width: 30%">项目</th>
                          <th style="width: 20%">症状程度</th>
                        </tr>
                        <tbody v-if="reportData.head.four">
                          <tr v-for="(item, index) in reportData.head.four.list" :key="index">
                            <td>{{ item.name }}</td>
                            <td>{{ item.value }}</td>
                            <td>{{ item.name2 }}</td>
                            <td>{{ item.value2 }}</td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="column-info" v-if="reportData.head.four">
                        <div class="info-item">
                          <div class="item-title">医生签名：</div>
                          <span v-if="reportData.head.four.doctor.sign" style="padding-top: 0">
                            <img :src="imgPath + reportData.head.four.doctor.sign" />
                          </span>
                          <span v-else>{{ reportData.head.four.doctor.doctorName }}</span>
                        </div>
                        <div class="info-item">
                          <div class="item-title">审核时间：</div>
                          <span>{{ reportData.head.four.doctor.duty }}</span>
                        </div>
                      </div>
                      <div class="column-tips">*症状程度：偶有以"±"，较轻以"+"，中等以"++"，明显以"+++"，无以"-"表示</div>
                    </div>
                    <section-report :show-data="reportData.ksList" type-data="1" :imgPath="imgPath" :style="reportData.ksList && reportData.ksList.length ? 'page-break-before: always' : ''"></section-report>
                  </div>
                  <div class="report-main2" v-else-if="reportData.head.title == '职业复查'">
                    <div class="main-title print-show">{{ branchInfo.name }}职业复查报告</div>
                    <div class="main-title print-hide">
                      <el-input v-model="branchInfo.name" style="width: 300px" @blur="changeName"></el-input>
                      职业复查报告
                    </div>
                    <div class="main-info">
                      <table border="0" cellspacing="0">
                        <tr>
                          <td style="width: 36%">
                            <div class="flex">
                              <div class="td-title"><span>姓名</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientname }}</div>
                            </div>
                          </td>
                          <td style="width: 32%">
                            <div class="flex">
                              <div class="td-title"><span>性别</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientsex }}</div>
                            </div>
                          </td>
                          <td style="width: 32%">
                            <div class="flex">
                              <div class="td-title"><span>年龄</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientage }}</div>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div class="flex">
                              <div class="td-title"><span>电话</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientphone }}</div>
                            </div>
                          </td>
                          <td colspan="2">
                            <div class="flex">
                              <div class="td-title"><span>单位</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientorg }}</div>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div class="flex">
                              <div class="td-title"><span>体检类别</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientmedicaltype }}</div>
                            </div>
                          </td>
                          <td colspan="2">
                            <div class="flex">
                              <div class="td-title"><span>危害因素</span>：</div>
                              <div class="td-cont">{{ reportData.head.patientharm }}</div>
                            </div>
                          </td>
                        </tr>
                      </table>
                    </div>
                    <section-report :show-data="reportData.ksList" type-data="0" :imgPath="imgPath" :endPicture="reportData.endPicture"></section-report>
                  </div>
                  <!-- 总结 -->
                  <div class="report-summarize">
                    <div class="title">{{ branchInfo.name }}职业体检总结报告</div>
                    <div class="content">
                      <p>尊敬的{{ reportData.end.end_patientname }}：</p>
                      <p style="text-indent: 2em; margin-top: 0">您好！欢迎您来{{ branchInfo.name }}进行职业健康检查。对您的光临我们表示感谢，现将您的体检结果汇总报告如下，我们的专家针对您的检查结果提出了相关的建议和意见，供您参考。"</p>
                      <div class="content-result">
                        <h3>职业健康检查结论与处理意见</h3>
                        <p>{{ reportData.end.end_offer }}</p>
                      </div>
                      <div class="content-result">
                        <h3>本次检查结果汇总</h3>
                        <p>{{ reportData.end.positive }}</p>
                      </div>
                      <div class="content-info">
                        <div class="info-item" style="flex: 1">
                          <div class="item-cont">主检医师(签字)：</div>
                          <div class="item-img">
                            <img v-for="(item, index) in reportData.end.signPic" :key="index" :src="imgPath + item" />
                          </div>
                        </div>
                        <div class="info-item info-right">
                          <div class="item-cont">体检机构(盖章)：</div>
                          <div class="item-cont" style="margin-top: 4px">{{ reportData.end.end_date }}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </template>

        <!-- 封底 -->
        <div class="report-end" v-if="reportData && reportData.head.title != '职业复查'">
          <div style="min-height: 60%; display: flex; flex-direction: column; align-items: center; justify-content: center">
            <img v-if="branchInfo.logo" class="end-logo" :src="imgPath + branchInfo.logo" />
            <img v-if="branchInfo.miniProgramCode" class="end-code" :src="imgPath + branchInfo.miniProgramCode" />
            <img v-if="branchInfo.qrcode" class="end-code" :src="imgPath + branchInfo.qrcode" />
          </div>
          <div class="end-info">
            <div class="title">职业健康检查机构信息：</div>
            <div class="content">
              <p>名 称：{{ branchInfo.name }}</p>
              <p>地 址：{{ branchInfo.address }}</p>
              <p>电 话：{{ branchInfo.phone }}</p>
              <p>电子邮箱：{{ branchInfo.email }}</p>
              <p>邮 编：{{ branchInfo.postalCode }}</p>
              <p>传 真：{{ branchInfo.fax }}</p>
              <p>
                资质证书号：<b>{{ branchInfo.certificateNo }}</b>
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 隐私报告 -->
      <!-- <div class="report-private" v-if="reportData.ysbg">
                <private-report ref="privateReport" :report-data="reportData.ysbg"></private-report>
              </div> -->
    </div>
    <div :style="isMobile() ? 'height: 82px;' : ''" class="placeholder"></div>
    <div class="print-fixed">
      <el-button class="mobile" type="primary" @click="handlePrint()" v-if="isMobile()">下 载</el-button>
      <el-button type="primary" @click="handlePrint()" v-else>打 印</el-button>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import { getReportData, getData, getBranchConfig } from '@/api/preview/individual_report.js'
import { getCultural } from '@/utils/dataList.js'
import JsBarcode from 'jsbarcode'
import sectionReport from './module/section'

export default {
  name: 'IndividualDiseaseReport',
  components: { sectionReport },
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 查询参数
      queryParams: {
        dh: 1,
        isJy: 0,
        patientcode: null,
        showAllImage: 0,
      },
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
      //  文化程度
      culturalList: [],
      reportList: [],
      // 医院信息
      branchInfo: {},
    }
  },
  created() {
    document.title = '职业报告'
    this.culturalList = getCultural()
    this.queryParams.patientcode = this.$route.query.patientcode
    // this.queryParams.showAllImage = this.$route.query.showAllImage || 0
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
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
      window.print()
    },
    // 获取报告数据
    getReport() {
      let obj = {
        idExamtype: 1, //体检类型,0.健康体检 1.职业体检
        patientcode: '', //体检号
        reportType: 5, //报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 6.对比报告 7.Pacs报告
      }
      if (typeof this.queryParams.patientcode == 'string') {
        obj.patientcode = this.queryParams.patientcode
        this.goGetData(obj)
      } else {
        for (let i = 0; i < this.queryParams.patientcode.length; i++) {
          obj.patientcode = this.queryParams.patientcode[i]
          this.goGetData(obj)
        }
      }
    },
    goGetData(context) {
      getData(context).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, '提醒')
          this.loading.close()
          return
        }
        this.reportList.push(res.data)
        for (let i = 0; i < this.reportList.length; i++) {
          if (this.reportList[i].head.title != '职业复查')
            this.$nextTick(() => {
              JsBarcode('#barcode' + i, this.reportList[i].head.one.number, {
                format: 'CODE39', //条形码的格式
                width: 1, //线宽
                height: 40, //条码高度
                fontSize: 16,
                lineColor: '#000', //线条颜色
                displayValue: false, //是否显示文字
                margin: 2, //设置条形码周围的空白区域
              })
            })
          if (i == this.reportList.length - 1) {
            this.loading.close()
          }
        }
        if (this.reportList.length == 1) {
          document.title = this.reportList[0].end.patientcode + '-' + this.reportList[0].end.patientname + '-' + this.reportList[0].head.one.date + ' 职业报告'
        }
      })
      getBranchConfig({ branchId: this.$getCookie('cid') }).then(({ data }) => {
        this.branchInfo = data || { address: '' }
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
    // 中心名称改变
    changeName() {
      localStorage.setItem('centerName', this.centerName)
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

.preview-individual-disease-report {
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

  .report-cover {
    page-break-after: always;
    height: 297mm;
    width: 210mm;
    background: #fff;
    padding: 40px 60px 80px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .cover-top {
      .top-header {
        display: flex;
        justify-content: space-between;

        .header-avatar {
          width: 150px;
          height: 190px;
          object-fit: contain;
          margin-left: 36px;
        }

        .header-content {
          .content-info {
            width: 400px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            margin-top: 8px;

            .info-title {
              width: 80px;
              font-size: 16px;
              line-height: 32px;
              height: 32px;
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
              border-bottom: 1px solid #000;
              flex: 1;
              min-height: 33px;
              line-height: 32px;
            }

            .info-img {
              border-bottom: 1px solid #000;
              flex: 1;
              height: 39px;

              .barcode {
                width: auto;
                height: 38px;
                object-fit: contain;
              }
            }

            .info-type {
              padding-top: 5px;
              font-size: 16px;
              flex: 1;
              line-height: 22px;

              span {
                display: block;
                margin-bottom: 8px;
              }
            }
          }
        }
      }

      .top-title {
        font-size: 56px;
        text-align: center;
        font-weight: bold;
        margin-top: 48px;
        letter-spacing: 20px;
      }
    }

    .cover-bottom {
      font-size: 16px;
      text-align: center;
      font-weight: bold;
    }
  }

  .report-preface {
    page-break-after: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 15%;
    height: 297mm;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .title {
      font-size: 22px;
      line-height: 34px;
      color: #333333;
      font-weight: 600;
    }

    .content {
      p {
        margin: 0;
        font-size: 20px;
        line-height: 34px;
        color: #333333;
      }
    }
  }

  .report-main {
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 80px;

    .main-info {
      .info-title {
        font-size: 18px;
        font-weight: bold;
      }

      .info-box {
        display: flex;

        .info-item {
          width: 100%;
          margin: 0 auto;
          display: flex;
          margin-top: 8px;
          padding-right: 8px;

          .item-title {
            width: 80px;
            font-size: 16px;
            line-height: 32px;
            height: 32px;
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

            &.long-label {
              width: 115px;
            }
          }

          .item-cont {
            font-size: 16px;
            flex: 1;
            line-height: 32px;
            min-height: 32px;
            text-align: center;
            position: relative;
            word-break: break-all;

            &::after {
              content: '';
              width: 100%;
              display: block;
              border-bottom: 1px solid #000;
              position: absolute;
              bottom: 0;
            }

            span {
              border-bottom: 1px solid #000;
              line-height: 28px;
              padding: 4px 0;
              word-break: break-all;
            }
          }

          .item-img {
            width: auto;
            height: 32px;
            max-width: 260px;
            margin-left: 12px;
          }
        }
      }

      .info-table {
        margin-top: 24px;

        .info-title {
          font-size: 16px;
          line-height: 22px;
        }

        table {
          width: 100%;
          border-collapse: collapse;
          border: 1px solid #000;
          margin-top: 12px;

          tr {
            th,
            td {
              font-size: 16px;
              line-height: 22px;
              border: 1px solid #000;
              padding: 4px 2px;
              font-weight: 500;
              text-align: center;
              word-break: break-all;
              height: 31px;
            }

            td {
              font-size: 14px;
            }
          }
        }
      }
    }

    .main-column {
      display: flex;
      margin-top: 16px;

      .column-title {
        font-size: 16px;
        line-height: 32px;
        height: 32px;
      }

      .column-cont {
        font-size: 16px;
        flex: 1;
        line-height: 32px;
        min-height: 32px;
        position: relative;
        word-break: break-all;

        &::after {
          content: '';
          width: 100%;
          display: block;
          border-bottom: 1px solid #000;
          position: absolute;
          bottom: 0;
        }

        span {
          border-bottom: 1px solid #000;
          line-height: 28px;
          padding: 4px 0;
          word-break: break-all;
        }
      }

      .column-box {
        flex: 1;
        font-size: 16px;
        line-height: 32px;
        min-height: 32px;
        display: flex;
        flex-wrap: wrap;

        .box-title {
          font-size: 16px;
          line-height: 32px;
          height: 32px;
        }

        .box-cont {
          font-size: 16px;
          height: 32px;
          display: flex;
          align-items: center;
          margin-right: 4px;

          span {
            display: inline-block;
            min-width: 32px;
            height: 22px;
            line-height: 22px;
            text-align: center;
            border-bottom: 1px solid #000;
          }
        }
      }

      .column-table {
        width: 100%;
        border: 1px solid #06ff06;
        border-collapse: collapse;
        margin-top: 8px;

        th,
        td {
          border: 1px solid #06ff06;
          padding: 6px 12px;
          font-size: 14px;
          line-height: 18px;
          color: #000000;
          word-break: break-all;
          font-weight: 500;
          text-align: center;
        }
      }

      .column-info {
        display: flex;
        justify-content: end;
        margin-top: 22px;

        .info-item {
          display: flex;
          align-items: center;
          margin-left: 12px;

          .item-title {
            display: inline-block;
            font-size: 15px;
            line-height: 24px;
            padding-top: 10px;
            color: #333333;
          }

          span {
            display: inline-block;
            word-break: break-all;
            border-bottom: 1px solid #000;
            font-size: 15px;
            height: 34px;
            line-height: 24px;
            color: #333333;
            min-width: 100px;
            padding: 10px 2px 0;
          }

          img {
            width: auto;
            height: 32px;
            padding-bottom: 2px;
          }
        }
      }

      .column-tips {
        font-size: 16px;
        line-height: 24px;
        margin-top: 12px;
        text-align: right;
      }
    }
  }
  .report-main2 {
    margin: 0 auto;
    background: #fff;
    padding: 40px 80px;

    .main-title {
      font-size: 22px;
      font-weight: bold;
      text-align: center;
    }

    .main-info {
      margin-top: 32px;
      padding-bottom: 16px;
      border-bottom: 3px solid #000;

      table {
        width: 100%;

        td {
          padding: 6px 0;

          .flex {
            display: flex;

            .td-title {
              width: 88px;
              font-size: 17px;
              line-height: 22px;
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

            .td-cont {
              flex: 1;
              font-size: 17px;
              line-height: 22px;
            }
          }
        }
      }
    }
  }

  .report-summarize {
    page-break-before: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 20px 10% 10px;
    min-height: calc(297mm - 200px);

    .title {
      font-size: 22px;
      line-height: 34px;
      color: #333333;
      text-align: center;
    }

    .content {
      margin-top: 32px;

      p,
      h3 {
        margin: 0;
        font-size: 18px;
        line-height: 24px;
        white-space: pre-wrap;
        margin-top: 24px;
      }

      h3 {
        font-weight: 600;
      }

      .content-result {
        p {
          padding-left: 2em;
        }
      }

      .content-info {
        display: flex;
        justify-content: space-between;
        margin-top: 22px;
        align-items: flex-start;

        .info-item {
          display: flex;

          .item-cont {
            display: inline-block;
            font-size: 15px;
            line-height: 24px;
            color: #333333;
          }

          .item-img {
            flex: 1;
            display: flex;
            flex-wrap: wrap;

            img {
              max-width: 45%;
              width: auto;
              height: 36px;
              margin-left: 10px;
              margin-bottom: 12px;
            }
          }
        }

        .info-right {
          flex-direction: column;
          padding-right: 100px;
          margin-left: 12px;
        }
      }
    }
  }

  .report-end {
    page-break-before: always;
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

    .end-info {
      margin-top: 32px;

      .title {
        font-size: 20px;
        line-height: 32px;
        color: #333333;
        font-weight: 600;
      }

      .content {
        p {
          margin: 0;
          font-size: 18px;
          line-height: 32px;
          color: #333333;
        }
      }
    }
  }
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
.footer {
  display: none;
}

@media print {
  .print-hide {
    display: none;
  }
  .print-show {
    display: block;
  }
  .preview-individual-disease-report {
    padding: 0;
    background: #fff;
  }

  .report-cover,
  .report-preface,
  .report-end {
    margin: 0 !important;
    position: relative;
    z-index: 99;
  }

  .report-main {
    padding-top: 0 !important;
    padding-bottom: 0 !important;
    margin-top: 0 !important;
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
    height: 60px;
    margin-bottom: 10px;
  }

  .footer,
  .page-footer {
    height: 50px;
    margin-top: 10px;
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

  .footer {
    position: fixed;
    bottom: 0;
    left: 60px;
    right: 60px;
    z-index: 9;
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-top: 2px solid #333333;
    font-size: 14px;
    line-height: 22px;
    font-size: #333;
    background-color: #fff;
  }

  @page {
    margin: 0;
  }
}
</style>
