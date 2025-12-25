<!-- 健康团体报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-group-health-report">
    <div class="group-report" id="print-content" v-if="reportData">
      <!-- 封面 -->
      <div class="report-cover">
        <div class="cover-header">
          <img class="logo" src="@/assets/logo/zk_logo.png" />
          <div class="title">团体体检健康检查报告</div>
        </div>
        <div class="cover-content">
          <div class="title">{{ branchConfig.name }}</div>
          <div class="subtitle">
            <span>团</span>
            <span>检</span>
            <span>报</span>
            <span>告</span>
          </div>
          <img class="image" src="@/assets/images/report/cover.png" />
          <div class="info">
            <div class="info-column">
              <div class="info-title">团体名称：</div>
              <div class="info-cont">{{ reportData.data1_1.TEAM_NAME }}</div>
            </div>
            <div class="info-column">
              <div class="info-title">团体地址：</div>
              <div class="info-cont">{{ reportData.data1_1.TEAM_ADDRESS }}</div>
            </div>
            <div class="info-column">
              <div class="info-title">订单编号：</div>
              <div class="info-cont">{{ reportData.data1_1.ORDER_ID }}</div>
            </div>
            <div class="info-column">
              <div class="info-title">登记起止日期：</div>
              <div class="info-cont">{{ reportData.data1_1.RANGE_DATE }}</div>
            </div>
          </div>
        </div>
        <div class="cover-footer">
          <div class="address">地址：{{ branchConfig.address }}</div>
          <div class="link">
            <p>联系电话：{{ branchConfig.phone }}</p>
            <p>网址：<a href="https://www.chihm.com" target="_blank">www.chihm.com</a></p>
          </div>
        </div>
      </div>
      <!-- 前言 -->
      <div class="report-preface">
        <div class="title">前言</div>
        <div class="content">
          <p>这是一本针对贵单位员工体检结果的综合分析资料，希望透过本资料，让贵单位主管能够了解员工的健康状况及可以改善的方向。</p>
          <p>
            这份报告包括四大部份，我们在每个部份都会提供重要的健康与疾病相关知识，以及后续应该如何处理等信息。让非常关心员工健康的您和单位知道如何再进一步改善员工健康。(注:敬请保护个人隐私,本档案仅供贵单位负责职工健康体检的人员查阅!未经员工本人同意,不得外泄资料,否则,造成不良影响由单位承担,与本中心无关!)
          </p>
          <p>第一部份：基本资料。</p>
          <p>第二部份：体检异常情况统计。</p>
          <p>第三部份：员工健康问题分析及保健建议。</p>
          <p>第四部份：全体员工体检结果汇总。</p>
          <p>附录部份：本次体检相关其它结果及其它信息。</p>
          <p>沃德国际体检中心根据单位提出的需要提供完善的后续服务，包括心理咨询服务、营养咨询、运动咨询、中医保健养生咨询、远程会诊、绿色就医转诊、私人医生、专业护理等特殊服务。如有任何需要，请电洽客服中心，电话：0532-86899999。</p>
          <p>定期健康检查已成为社会潮流，更重要的是完成体检后,改善员工的健康状况才是健康检查最终的目标，医学上称之为“一级预防”（适当的运动、均衡的饮食、身心的休闲）。完全依赖药物治疗并不是人类健康的唯一方法，定期健康检查并积极落实一级预防医学，才是人类健康的必然选择。</p>
        </div>
      </div>
      <!-- 内容 -->
      <div class="report-main" style="position: relative">
        <!-- 一、基本资料 -->
        <div class="module">
          <div class="main-title" style="margin-top: 0">一、基本资料</div>
          <div class="main-describe">简要描述了贵单位受检人员的情况。</div>
          <div class="main-subtitle">1.1 体检时间：</div>
          <table class="other-table" border="0" cellspacing="0">
            <tr>
              <td class="text-center bg-color" style="width: 210px">登记起始日期</td>
              <td>{{ reportData.data1_1.REGISTER_BEGIN }}</td>
            </tr>
            <tr>
              <td class="text-center bg-color">登记截止日期</td>
              <td>{{ reportData.data1_1.REGISTER_END }}</td>
            </tr>
          </table>
          <div class="main-subtitle">1.2 参检人员构成情况：</div>
          <table class="other-table text-center" border="0" cellspacing="0">
            <tr>
              <th rowspan="2">类型</th>
              <th colspan="3">人数</th>
              <th colspan="2">构成情况</th>
              <th colspan="3">参检情况</th>
            </tr>
            <tr>
              <th>男性</th>
              <th>女性</th>
              <th>合计</th>
              <th>男性</th>
              <th>女性</th>
              <th>男性</th>
              <th>女性</th>
              <th>平均</th>
            </tr>
            <tr>
              <td class="bg-color">A、计划</td>
              <td>{{ reportData.data1_1.PLAN_M }}</td>
              <td>{{ reportData.data1_1.PLAN_W }}</td>
              <td>{{ reportData.data1_1.PLAN_T }}</td>
              <td>{{ reportData.data1_1.PLAN_M_M }}</td>
              <td>{{ reportData.data1_1.PLAN_M_W }}</td>
              <td>100%</td>
              <td>100%</td>
              <td>100%</td>
            </tr>
            <tr>
              <td class="bg-color">B、参检</td>
              <td>{{ reportData.data1_1.INSPECT_M }}</td>
              <td>{{ reportData.data1_1.INSPECT_W }}</td>
              <td>{{ reportData.data1_1.INSPECT_T }}</td>
              <td>{{ reportData.data1_1.INSPECT_M_M }}</td>
              <td>{{ reportData.data1_1.INSPECT_M_W }}</td>
              <td>{{ reportData.data1_1.INSPECT_T_M }}</td>
              <td>{{ reportData.data1_1.INSPECT_T_W }}</td>
              <td>{{ reportData.data1_1.INSPECT_T_A }}</td>
            </tr>
            <tr>
              <td class="bg-color">C、未检</td>
              <td>{{ reportData.data1_1.NO_M }}</td>
              <td>{{ reportData.data1_1.NO_W }}</td>
              <td>{{ reportData.data1_1.NO_T }}</td>
              <td>{{ reportData.data1_1.NO_M_M }}</td>
              <td>{{ reportData.data1_1.NO_M_W }}</td>
              <td>{{ reportData.data1_1.NO_T_M }}</td>
              <td>{{ reportData.data1_1.NO_T_W }}</td>
              <td>{{ reportData.data1_1.NO_T_A }}</td>
            </tr>
          </table>
          <!-- 图表 -->
          <div class="main-chart main-flex" style="height: 350px">
            <div class="chart-column chart-double">
              <div class="chart-box" id="bar1_1_M"></div>
              <div class="chart-box" id="bar1_1_W"></div>
            </div>
            <div class="chart-column">
              <div class="chart-box" id="bar1_1_T"></div>
            </div>
          </div>
          <div class="main-subtitle">1.3 参检人员年龄分布：</div>
          <table class="other-table text-center" border="0" cellspacing="0">
            <tr>
              <th style="width: 22%" rowspan="2">年龄组(岁)</th>
              <th colspan="3">人数</th>
            </tr>
            <tr>
              <th>男性</th>
              <th>女性</th>
              <th>合计</th>
            </tr>
            <tr>
              <td class="bg-color">&lt;18岁</td>
              <td>{{ reportData.data1_1.LITTLE18_M }}</td>
              <td>{{ reportData.data1_1.LITTLE18_W }}</td>
              <td>{{ reportData.data1_1.LITTLE18_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">18～29岁</td>
              <td>{{ reportData.data1_1.LITTLE30_M }}</td>
              <td>{{ reportData.data1_1.LITTLE30_W }}</td>
              <td>{{ reportData.data1_1.LITTLE30_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">30～39岁</td>
              <td>{{ reportData.data1_1.LITTLE40_M }}</td>
              <td>{{ reportData.data1_1.LITTLE40_W }}</td>
              <td>{{ reportData.data1_1.LITTLE40_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">40～49岁</td>
              <td>{{ reportData.data1_1.LITTLE50_M }}</td>
              <td>{{ reportData.data1_1.LITTLE50_W }}</td>
              <td>{{ reportData.data1_1.LITTLE50_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">50～59岁</td>
              <td>{{ reportData.data1_1.LITTLE60_M }}</td>
              <td>{{ reportData.data1_1.LITTLE60_W }}</td>
              <td>{{ reportData.data1_1.LITTLE60_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">60～69岁</td>
              <td>{{ reportData.data1_1.LITTLE70_M }}</td>
              <td>{{ reportData.data1_1.LITTLE70_W }}</td>
              <td>{{ reportData.data1_1.LITTLE70_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">&gt;=70岁</td>
              <td>{{ reportData.data1_1.MORE69_M }}</td>
              <td>{{ reportData.data1_1.MORE69_W }}</td>
              <td>{{ reportData.data1_1.MORE69_T }}</td>
            </tr>
            <tr>
              <td class="bg-color">合计</td>
              <td>{{ reportData.data1_1.M_T }}</td>
              <td>{{ reportData.data1_1.W_T }}</td>
              <td>{{ reportData.data1_1.T_T }}</td>
            </tr>
          </table>
          <div class="main-chart" style="height: 280px; page-break-before: always">
            <div class="chart-box" id="bar1_1_A"></div>
          </div>
          <div class="main-chart">
            <div class="chart-column main-flex" style="height: 280px">
              <div class="chart-box" id="pie1_1_M"></div>
              <div class="chart-box" id="pie1_1_W"></div>
            </div>
            <div class="chart-column" style="width: 480px; height: 280px; margin: 0 auto">
              <div class="chart-box" id="pie1_1_T"></div>
            </div>
          </div>
          <div class="main-subtitle">1.4 体检项目与体检项目的参检情况：</div>
          <table class="other-table" border="0" cellspacing="0">
            <tr>
              <th colspan="2">体检项目</th>
              <th colspan="3">该项检出人数</th>
              <th colspan="3">总体参检人数</th>
              <th colspan="3">该项参检百分比</th>
            </tr>
            <tr>
              <th style="width: 16%">检查科室</th>
              <th style="width: 16%">项目名称</th>
              <th>男</th>
              <th>女</th>
              <th>合计</th>
              <th>男</th>
              <th>女</th>
              <th>合计</th>
              <th>男</th>
              <th>女</th>
              <th>平均</th>
            </tr>
            <tr v-for="(item, index) in reportData.data1_2.CJQK" :key="index">
              <td>{{ item['CJQK.DEP_NAME'] }}</td>
              <td>{{ item['CJQK.ITEM_NAME'] }}</td>
              <td>{{ item['CJQK.O_M_N'] }}</td>
              <td>{{ item['CJQK.O_W_N'] }}</td>
              <td>{{ item['CJQK.O_T_N'] }}</td>
              <td>{{ item['CJQK.T_M_N'] }}</td>
              <td>{{ item['CJQK.T_W_N'] }}</td>
              <td>{{ item['CJQK.T_T_N'] }}</td>
              <td>{{ item['CJQK.O_M_B'] }}</td>
              <td>{{ item['CJQK.O_W_B'] }}</td>
              <td>{{ item['CJQK.O_A_B'] }}</td>
            </tr>
          </table>
        </div>
        <!-- 二、体检异常结果统计 -->
        <div class="module">
          <div class="main-title">二、体检异常结果统计</div>
          <table border="0" cellspacing="0">
            <tr>
              <th rowspan="2" style="width: 15%">分类</th>
              <th rowspan="2" style="width: 35%">结论</th>
              <th colspan="3">检出人数</th>
            </tr>
            <tr>
              <th>男性</th>
              <th>女性</th>
              <th>合计</th>
            </tr>
            <tr v-for="(item, index) in reportData.data2_1['']" :key="index">
              <td>{{ item.TYPE }}</td>
              <td>{{ item.CONCLUSION }}</td>
              <td>{{ item.DETECTION_M }}</td>
              <td>{{ item.DETECTION_W }}</td>
              <td>{{ item.DETECTION_T }}</td>
            </tr>
            <tr v-if="reportData.data2_1[''] && !reportData.data2_1[''].length">
              <td>无</td>
              <td>暂无异常</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
            </tr>
          </table>
          <template v-if="reportData.data2_1[''] && reportData.data2_1[''].length">
            <div class="main-chart" style="height: 220px">
              <div class="chart-box" id="bar2_2_M"></div>
            </div>
            <div class="main-chart" style="height: 220px">
              <div class="chart-box" id="bar2_2_W"></div>
            </div>
            <div class="main-chart" style="height: 220px">
              <div class="chart-box" id="bar2_2_T"></div>
            </div>
          </template>
        </div>
        <!-- 三、员工健康问题分析 -->
        <div class="module">
          <div class="main-title">三、员工健康问题分析</div>
          <div class="main-column" v-for="(data, key) in reportData.data3_0" :key="key">
            <div class="column-title">{{ data.name }}</div>
            <div class="column-list" v-for="(item, index) in data.list" :key="index">
              <div class="list-title">{{ item.BASCONCLUSION }}</div>
              <div class="list-subtitle"><i class="icon-diamond"></i>检出人员名单</div>
              <table border="0" cellspacing="0">
                <tr>
                  <th style="width: 20%">体检号</th>
                  <th style="width: 18%">姓名</th>
                  <th style="width: 18%">性别</th>
                  <th style="width: 18%">年龄</th>
                  <th style="width: 26%">部门</th>
                </tr>
                <tr v-for="(list, num) in item.LIST" :key="num">
                  <td>{{ list.PATIENTCODE }}</td>
                  <td>{{ list.NAME }}</td>
                  <td>{{ list.SIX }}</td>
                  <td>{{ list.AGE }}</td>
                  <td>{{ list.DEP }}</td>
                </tr>
              </table>
              <div class="list-subtitle"><i class="icon-diamond"></i>健康建议</div>
              <div class="list-cont">
                <span v-if="!isModify">{{ item.HEALTH_OFFER }}</span>
                <el-input v-else v-model="item.HEALTH_OFFER" type="textarea" :rows="3"></el-input>
              </div>
            </div>
          </div>
        </div>
        <!-- 四、参检人员体检结果汇总 -->
        <div class="module">
          <div class="main-title">四、参检人员体检结果汇总</div>
          <div class="main-describe">个人隐私请保密,仅供贵单位领导参阅.</div>
          <table class="other-table" style="margin-bottom: 32px" border="0" cellspacing="0" v-for="(item, index) in reportData.data4_2" :key="index">
            <tr>
              <th style="width: 10%">体检号</th>
              <td style="width: 14%">{{ item.tjh }}</td>
              <th style="width: 8%">姓名</th>
              <td style="width: 12%">{{ item.xm }}</td>
              <th style="width: 8%">性别</th>
              <td style="width: 8%">{{ item.xb }}</td>
              <th style="width: 8%">年龄</th>
              <td style="width: 8%">{{ item.nl }}</td>
              <th style="width: 8%">部门</th>
              <td style="width: 16%">{{ item.bm }}</td>
            </tr>
            <tr>
              <th colspan="10" style="text-align: left">体检结论</th>
            </tr>
            <tr>
              <td colspan="10" style="white-space: pre-wrap">
                <span v-if="!isModify">{{ item.tjjl }}</span>
                <el-input v-else v-model="item.tjjl" type="textarea" :rows="5"></el-input>
              </td>
            </tr>
          </table>
          <div class="main-tips">(注:敬请保护个人隐私,本档案仅供贵单位负责职工健康体检的人员查阅!未经员工本人同意,不得外泄资料,否则,造成不良影响由单位承担,与本中心无关!)</div>
        </div>
      </div>
      <!-- 结束语 -->
      <div class="report-conclusion">
        <div class="conclusion-title">结束语</div>
        <div class="conclusion-cont">
          <p>影响健康的因素很多，包括遗传基因、日常生活习惯、社会环境、医疗资源等等，其中，与一般民众息息相关的医疗资源，仅占影响健康因素的10%，最大的影响因素还是日常生活习惯及社会环境。根据世界卫生组织(WHO)的估计，75%的癌症是已知的环境因素所引起，而这些因素都是可以预防的，例如抽烟、酗酒。</p>
          <p>世界卫生组织(WHO)指出:“二十一世纪的医学不应该继续以疾病为主要研究对象，而应该以人类健康作为医学研究的主要方向。”这个精神旨在宣告预防医学和健康保健将成为医学的主导。</p>
          <p>我中心愿以卓越的服务、优质的技术继续为贵单位提供医疗保健服务，从体检中发现健康问题，从保健中解决健康问题。</p>
          <p>最后，感谢您对沃德民泽体检中心的支持，别忘了定期为员工安排健康检查。您的任何意见，都是我们成长的来源！敬请不吝指教。</p>
        </div>
      </div>
      <!-- 封底 -->
      <div class="report-end">
        <img class="end-logo" src="@/assets/logo/logo.png" />
        <div class="end-info">
          <div class="info-name">{{ branchConfig.name }}</div>
          <div class="info-title">企业员工健康检查统计报告</div>
          <div class="info-link">
            <p>咨询电话：{{ branchConfig.phone }}</p>
            <p>网址：<a href="http://www.chihm.com" target="_blank">www.chihm.com</a></p>
            <p>地址：{{ branchConfig.address }}</p>
          </div>
        </div>
      </div>
      <div :style="isMobile() ? 'height: 82px;' : ''" class="placeholder"></div>
      <div class="print-fixed" v-if="!isModify">
        <el-button class="mobile" type="primary" @click="handlePrint()" v-if="isMobile()">下 载</el-button>
        <el-button type="primary" @click="handlePrint()" v-else>打 印</el-button>
      </div>
      <div class="print-fixed" v-else>
        <el-button type="primary" @click="modifyReport">修 改</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { getData } from '@/api/preview/individual_report.js'
import { updateReportData } from '@/api/preview/contrast_report.js'

import * as echarts from 'echarts'
export default {
  name: 'GroupHealthReport',
  data() {
    return {
      // 查询参数
      queryParams: {},
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
      printObj: {
        id: '#a4Div',
        popTitle: '打印', // 页眉标题
      },
      // 是否为修改报告
      isModify: false,
      // 分中心配置参数
      branchConfig: {},
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
  created() {
    document.title = '健康团体报告'
    this.queryParams = this.$route.query
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
    // 判断是否为手机
    isMobile() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag
    },
    // 打印
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
        .catch(() => {})
    },
    // 查看健康报告数据
    getReport() {
      getData(this.queryParams).then((res) => {
        this.reportData = res.data
        this.branchConfig = res.data.branchConfig
        let array3 = []
        for (let key in res.data.data3_0) {
          array3.push({
            name: key,
            list: res.data.data3_0[key],
          })
        }
        this.reportData.data3_0 = array3
        this.$nextTick(() => {
          if (res.data.data1_1) {
            this.initCharts_P()
            this.initCharts_A()
            this.initCharts_R()
          }
        })
        this.loading.close()
      })
    },
    // 人员构成
    initCharts_P() {
      var data = this.reportData.data1_1
      var people1 = echarts.init(document.getElementById('bar1_1_M'))
      var people2 = echarts.init(document.getElementById('bar1_1_W'))
      var people3 = echarts.init(document.getElementById('bar1_1_T'))
      var optionP1 = {
        title: {
          text: '男性人数',
          subtext: '',
          left: 'right',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'bottom',
          data: [
            {
              name: data.PLAN_M + '  A.计划',
            },
            {
              name: data.INSPECT_M + '  B.参检',
            },
            {
              name: data.NO_M + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: '20%',
          left: 0,
          right: '30%',
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data.PLAN_M + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '男性人数',
                value: data.PLAN_M,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.INSPECT_M + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data.INSPECT_M,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.NO_M + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data.NO_M,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people1.setOption(optionP1)
      var optionP2 = {
        title: {
          text: '女性人数',
          subtext: '',
          left: 'right',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'bottom',
          data: [
            {
              name: data.PLAN_W + '  A.计划',
            },
            {
              name: data.INSPECT_W + '  B.参检',
            },
            {
              name: data.NO_W + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: '20%',
          left: 0,
          right: '30%',
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data.PLAN_W + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '女性人数',
                value: data.PLAN_W,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.INSPECT_W + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data.INSPECT_W,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.NO_W + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data.NO_W,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people2.setOption(optionP2)
      var optionP3 = {
        title: {
          text: '合计人数',
          subtext: '',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none',
          },
        },
        legend: {
          top: 28,
          left: 0,
          data: [
            {
              name: data.PLAN_T + '  A.计划',
            },
            {
              name: data.INSPECT_T + '  B.参检',
            },
            {
              name: data.NO_T + '  C.未检',
            },
          ],
          textStyle: {
            color: '#fft',
          },
        },
        grid: {
          top: 100,
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [''],
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: [
          {
            name: data.PLAN_T + '  A.计划',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#41E4BB',
              },
            },
            data: [
              {
                name: '合计人数',
                value: data.PLAN_T,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.INSPECT_T + '  B.参检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#1890FF',
              },
            },
            data: [
              {
                value: data.INSPECT_T,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
          {
            name: data.NO_T + '  C.未检',
            type: 'bar',
            itemStyle: {
              normal: {
                color: '#FF7A00',
              },
            },
            data: [
              {
                value: data.NO_T,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                      fontSize: 18,
                    },
                  },
                },
              },
            ],
          },
        ],
      }
      people3.setOption(optionP3)
    },
    // 年龄分布
    initCharts_A() {
      var data = this.reportData.data1_1.nlfb
      var name = new Array(data.length)
      var att1 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        name[i] = data[i]['agegroup']
        att1[i] = data[i]['boy1']
      }
      let ageArray = []
      name.forEach((el) => {
        let age = el.split('~')
        if (age[0] >= 20 && age[0] <= 60) {
          ageArray.push(el)
        }
      })
      var att2 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        att2[i] = data[i]['girl1']
      }
      var att3 = new Array(data.length)
      for (var i = 0; i < data.length; i++) {
        att3[i] = data[i]['total1']
      }
      var age1 = echarts.init(document.getElementById('bar1_1_A'))
      var age2 = echarts.init(document.getElementById('pie1_1_M'))
      var age3 = echarts.init(document.getElementById('pie1_1_W'))
      var age4 = echarts.init(document.getElementById('pie1_1_T'))
      var optionA1 = {
        title: {
          text: '男女合计各年龄段构成',
          subtext: '',
          top: 'bottom',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
        },
        grid: {
          top: '10%',
          left: 0,
          right: 0,
          bottom: 40,
          containLabel: true,
        },
        legend: {
          data: ['人数统计'],
        },
        xAxis: [
          {
            type: 'category',
            data: name,
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: att3,
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 18,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      age1.setOption(optionA1)
      var optionA2 = {
        animation: false,
        title: {
          left: 'center',
          text: '男性',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att1[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '45%',
            center: ['40%', '56%'],
            data: (function () {
              var res = []
              var len = 0
              while (len < name.length) {
                res.push({
                  name: name[len],
                  value: att1[len],
                })
                len++
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age2.setOption(optionA2)
      var optionA3 = {
        animation: false,
        title: {
          left: 'center',
          text: '女性',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att2[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '45%',
            center: ['40%', '56%'],
            data: (function () {
              var res = []
              var len = 0
              while (len < name.length) {
                res.push({
                  name: name[len],
                  value: att2[len],
                })
                len++
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age3.setOption(optionA3)
      var optionA4 = {
        animation: false,
        title: {
          left: 'center',
          text: '合计',
        },
        tooltip: {
          show: true,
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          top: 'center',
          textStyle: {
            color: '#fft',
          },
          data: ageArray,
          formatter: function (name1) {
            let num = undefined
            name.forEach((el, index) => {
              if (el == name1) {
                num = att3[index]
              }
            })
            return name1 + ' ' + num
          },
        },
        series: [
          {
            type: 'pie',
            radius: '56%',
            center: ['50%', '56%'],
            data: (function () {
              var res = []
              var len = 0
              while (len < name.length) {
                res.push({
                  name: name[len],
                  value: att3[len],
                })
                len++
              }
              return res
            })(),
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      age4.setOption(optionA4)
    },
    // 前十大异常结果
    initCharts_R() {
      var data = this.reportData.data2_2
      var att1 = new Array(data.boy.length)
      for (var i = 0; i < data.boy.length; i++) {
        att1[i] = data.boy[i]['name']
      }
      var att2 = new Array(data.girl.length)
      for (var i = 0; i < data.girl.length; i++) {
        att2[i] = data.girl[i]['name']
      }
      var att3 = new Array(data.total.length)
      for (var i = 0; i < data.total.length; i++) {
        att3[i] = data.total[i]['name']
      }
      var check1 = echarts.init(document.getElementById('bar2_2_M'))
      var check2 = echarts.init(document.getElementById('bar2_2_W'))
      var check3 = echarts.init(document.getElementById('bar2_2_T'))
      var option1 = {
        title: {
          text: '男性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option2 = {
        title: {
          text: '女性前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      var option3 = {
        title: {
          text: '男女综合前十大异常结果(单位：人)',
        },
        tooltip: {
          show: true,
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 1,
          containLabel: true,
        },
        legend: {
          data: [],
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisLabel: {
              interval: 0,
              formatter: this.labelFormat(),
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            minInterval: 1,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 24,
                },
              },
            },
            itemStyle: {
              normal: {
                color: function (value) {
                  return '#' + ('00000' + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6)
                },
              },
            },
          },
        ],
      }
      option1.series[0].data = data.boy
      option2.series[0].data = data.girl
      option3.series[0].data = data.total
      option1.xAxis[0].data = att1
      option2.xAxis[0].data = att2
      option3.xAxis[0].data = att3
      check1.setOption(option1)
      check2.setOption(option2)
      check3.setOption(option3)
    },
    labelFormat(val) {
      if (val && val.length > 10) {
        return val.substring(0, 10) + '\n' + val.substring(10)
      } else {
        return val
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.preview-group-health-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .placeholder {
    width: 100%;
    height: 50px;
  }

  .group-report {
    width: 210mm;
    margin: 0 auto;

    * {
      font-family: 'Song';
    }

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

    .report-cover {
      page-break-after: always;
      min-height: 297mm;
      background: #fff;
      padding: 40px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .cover-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        border-bottom: 1px solid #000000;
        padding: 0 12px 16px;

        .logo {
          width: auto;
          height: 40px;
        }

        .title {
          font-size: 26px;
          color: #008100;
          line-height: 28px;
        }
      }

      .cover-content {
        .title {
          font-size: 48px;
          text-align: center;
          margin-top: 26px;
        }

        .subtitle {
          font-size: 36px;
          display: flex;
          justify-content: space-around;
          margin-top: 18px;
        }

        .image {
          width: 80%;
          height: auto;
          display: block;
          margin: 24px auto 0;
        }

        .info {
          width: 80%;
          margin: 52px auto 0;

          .info-column {
            display: flex;
            align-items: center;

            .info-title {
              font-size: 18px;
              width: 130px;
              text-align: right;
            }

            .info-cont {
              font-size: 18px;
              border-bottom: 1px solid #000;
              flex: 1;
              // height: 46px;
              // line-height: 20px;
              padding: 10px 0;
            }
          }
        }
      }

      .cover-footer {
        width: 580px;
        margin: 24px auto 0;

        .address {
          font-size: 16px;
          text-align: right;
        }

        .link {
          border-top: 1px solid #000;
          margin-top: 10px;
          padding: 18px 40px 0;
          display: flex;
          justify-content: space-between;

          a {
            color: #0000ff;
            text-decoration: underline;
          }
        }
      }
    }

    .report-preface {
      page-break-after: always;
      margin: 20px auto 0;
      background: #fff;
      padding: 40px 10%;
      min-height: 297mm;

      .title {
        font-size: 32px;
        text-align: center;
        border-bottom: 1px solid #000;
        padding-bottom: 12px;
      }

      .content {
        font-size: 20px;
        margin-top: 24px;
        font-weight: bold;
        line-height: 42px;

        p {
          margin: 0;
          text-indent: 2em;
        }
      }
    }

    .report-main {
      page-break-after: always;
      margin: 20px auto 0;
      background: #fff;
      padding: 40px 80px;

      .main-title {
        font-size: 24px;
        line-height: 26px;
        margin-top: 36px;
      }

      .main-describe {
        margin-top: 20px;
        font-size: 16px;
        line-height: 24px;
      }

      .main-subtitle {
        font-size: 16px;
        line-height: 24px;
        margin-top: 8px;
      }

      table {
        font-size: 16px;
        width: 100%;
        margin-top: 12px;
        border: 1px solid #000;
        border-collapse: collapse;

        th,
        td {
          word-break: break-all;
          border: 1px solid #000;
          padding: 3px 8px;
          line-height: 23px;
        }

        .text-center {
          text-align: center;
        }

        .bg-color,
        th {
          background: #92d000;
          -webkit-print-color-adjust: exact;
          -moz-print-color-adjust: exact;
          -ms-print-color-adjust: exact;
          print-color-adjust: exact;
        }
      }

      .other-table {
        border-color: #00b050 !important;
        -webkit-print-color-adjust: exact;
        -moz-print-color-adjust: exact;
        -ms-print-color-adjust: exact;
        print-color-adjust: exact;

        th,
        td {
          border-color: #00b050 !important;
          -webkit-print-color-adjust: exact;
          -moz-print-color-adjust: exact;
          -ms-print-color-adjust: exact;
          print-color-adjust: exact;
        }
      }

      .main-chart {
        margin-top: 12px;
        page-break-inside: avoid;

        .chart-column {
          width: 100%;
          height: 100%;
        }

        .chart-double {
          display: flex;
          flex-direction: column;
          justify-content: space-between;

          .chart-box {
            height: calc(50% - 2px);
          }
        }

        .chart-box {
          width: 100%;
          height: 100%;
          padding: 12px;
          box-sizing: border-box;
        }
      }

      .main-flex {
        display: flex;
      }

      .main-column {
        .column-title {
          font-size: 16px;
          font-weight: bold;
          padding: 12px 8px;
          margin-top: 24px;
          background: #c7edcc;
          -webkit-print-color-adjust: exact;
          -moz-print-color-adjust: exact;
          -ms-print-color-adjust: exact;
          print-color-adjust: exact;
        }

        .column-list {
          .list-title {
            font-size: 16px;
            font-weight: bold;
            margin-top: 18px;
          }

          .list-subtitle {
            font-size: 16px;
            font-weight: bold;
            margin-top: 16px;
            height: 20px;
            line-height: 20px;

            .icon-diamond {
              display: inline-block;
              border: 2px solid #000;
              width: 12px;
              height: 12px;
              transform: rotate(45deg);
              margin-right: 6px;
            }
          }

          .list-cont {
            margin-top: 8px;
            font-size: 16px;
            line-height: 22px;
          }
        }
      }

      .main-tips {
        width: 540px;
        margin: 0 auto;
        padding-top: 32px;
        font-size: 16px;
        line-height: 22px;
      }
    }

    .report-conclusion {
      page-break-after: always;
      margin: 0 auto;
      background: #fff;
      padding: 40px 10%;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .conclusion-title {
        font-size: 22px;
        text-align: center;
      }

      .conclusion-cont {
        font-size: 16px;
        line-height: 26px;
        margin-top: 28px;

        p {
          margin: 0;
          text-indent: 2em;
        }
      }
    }

    .report-end {
      margin: 20px auto 0;
      background: #fff;
      padding: 40px;
      min-height: 297mm;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .end-logo {
        width: 200px;
        height: 200px;
        object-fit: contain;
      }

      .end-info {
        text-align: center;
        margin-top: 200px;

        .info-name {
          font-size: 18px;
          font-weight: bold;
        }

        .info-title {
          font-size: 22px;
          margin-top: 16px;
          font-weight: bold;
        }

        .info-link {
          margin-top: 26px;

          p {
            margin: 0;
            display: block;
            font-size: 16px;
            line-height: 22px;
          }
        }
      }
    }
  }
}

// 分页打印的问题
table {
  width: 100%;
  page-break-inside: auto;
}

@media print {
  .preview-group-health-report {
    padding: 0;
    background: #fff;
  }

  .report-preface,
  .report-end {
    margin-top: 0 !important;
  }

  .report-main {
    padding-top: 0 !important;
    margin-top: 0 !important;
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }

  .page-header {
    height: 40px;
  }

  .page-footer {
    height: 40px;
  }

  @page {
    margin: 28px 20px;
  }
}
</style>
