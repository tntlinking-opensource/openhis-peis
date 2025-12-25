<!-- 团检报告审批-综合样本分析 开发人：麦沃德科技半夏 -->
<template>
  <div class="app-container flex-direction-column audit-analyze">
    <ul class="main-nav" :style="{ '--theme': theme }" v-if="$route.query.type == 0">
      <li v-for="item in healthList" :key="item.id" :class="{ active: item.id == selectNav }" @click="toSwitch(item.id)">
        <div class="bg"></div>
        <span>{{ item.name }}</span>
      </li>
    </ul>
    <ul class="main-nav" :style="{ '--theme': theme }" v-if="$route.query.type == 1">
      <li v-for="item in occupationList" :key="item.id" :class="{ active: item.id == selectNav }" @click="toSwitch(item.id, item.flag)">
        <div class="bg"></div>
        <span>{{ item.name }}</span>
      </li>
    </ul>
    <div class="main-content flex-direction-column">
      <!-- 操作按钮 -->
      <el-row :gutter="10">
        <el-col :span="1.5" v-if="($route.query.type == 0 && selectNav == '17') || $route.query.type == 1">
          <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" @click="handleGenerate">生成报告</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-if="$route.query.type == 0">导出阴性和阳性结果 </el-button>
        </el-col>
      </el-row>
      <!-- 人员构成 -->
      <personnel-item ref="personnelItem" v-if="selectNav == 1"></personnel-item>
      <!-- 年龄分布 -->
      <age-item ref="ageItem" v-if="selectNav == 2"></age-item>
      <!-- 日期分布 -->
      <date-item ref="ageItem" v-if="selectNav == 3"></date-item>
      <!-- 健康 -->
      <!-- 项目参检 -->
      <project-item ref="projectItem" v-if="selectNav == 4"></project-item>
      <!-- 检出统计 -->
      <detection-item ref="detectionItem" v-if="selectNav == 5"></detection-item>
      <!-- 完成情况 -->
      <finish-item ref="finishItem" v-if="selectNav == 6 || selectNav == 15"></finish-item>
      <!-- 团体小结 -->
      <group-item ref="groupItem" v-if="selectNav == 7"></group-item>
      <!-- 阳性结果 -->
      <positive-item ref="positiveItem" v-if="selectNav == 8"></positive-item>
      <!-- 生成报告 -->
      <report-item ref="reportItem" v-if="selectNav == 17"></report-item>
      <!-- 职业 -->
      <!-- 危害因素人员分布 -->
      <hazard-item ref="hazardItem" v-if="selectNav == 9"></hazard-item>
      <!-- 疑似职业病人员一览表/职业禁忌证人员一览表/需复查人员一览表/其他疾病异常结果人员一览表/未见异常人员一览表 -->
      <browse-item ref="browseItem" :flag="selectFlag" v-if="selectNav == 10 || selectNav == 11 || selectNav == 12 || selectNav == 13 || selectNav == 14"></browse-item>
      <!-- 复查情况 -->
      <review-item ref="reviewItem" v-if="selectNav == 16"></review-item>
    </div>
  </div>
</template>
<script>
import personnelItem from './personnel'
import ageItem from './age'
import dateItem from './date'
import projectItem from './project'
import detectionItem from './detection'
import finishItem from './finish'
import groupItem from './group'
import positiveItem from './positive'
import hazardItem from './hazard'
import browseItem from './browse'
import reviewItem from './review'
import reportItem from './report'
import { createReportData } from '@/api/preview/group_report.js'

export default {
  name: 'AuditAnalyze',
  components: {
    personnelItem,
    ageItem,
    dateItem,
    projectItem,
    detectionItem,
    finishItem,
    groupItem,
    positiveItem,
    hazardItem,
    browseItem,
    reviewItem,
    reportItem,
  },
  props: [],
  data() {
    return {
      healthList: [
        {
          id: 1,
          name: '人员构成',
        },
        {
          id: 2,
          name: '年龄分布',
        },
        {
          id: 3,
          name: '日期分布',
        },
        {
          id: 4,
          name: '项目参检',
        },
        {
          id: 5,
          name: '检出统计',
        },
        {
          id: 6,
          name: '完成情况',
        },
        {
          id: 7,
          name: '团体小结',
        },
        {
          id: 8,
          name: '阳性结果',
        },
        {
          id: 17,
          name: '生成报告',
        },
      ],
      occupationList: [
        {
          id: 1,
          name: '人员构成',
        },
        {
          id: 2,
          name: '年龄分布',
        },
        {
          id: 3,
          name: '日期分布',
        },
        {
          id: 9,
          name: '危害因素人员分布',
        },
        {
          id: 10,
          name: '疑似职业病人员一览表',
          flag: 1,
        },
        {
          id: 11,
          name: '职业禁忌证人员一览表',
          flag: 2,
        },
        {
          id: 12,
          name: '需复查人员一览表',
          flag: 3,
        },
        {
          id: 13,
          name: '其他疾病异常结果人员一览表',
          flag: 4,
        },
        {
          id: 14,
          name: '未见异常人员一览表',
          flag: 5,
        },
        {
          id: 15,
          name: '完成情况',
        },
        {
          id: 16,
          name: '复查情况',
        },
      ],
      selectNav: 1,
      selectFlag: 0,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  methods: {
    // 切换
    toSwitch(id, flag) {
      this.selectNav = 0
      this.$nextTick(function () {
        this.selectNav = id
        if (flag) this.selectFlag = flag
      })
    },
    // 查看报告 --> 生成报告
    handleGenerate() {
      this.$modal
        .confirm('确定生成报告?', '生成')
        .then(() => {
          if (this.$route.query.type == '0') {
            this.$refs.reportItem.handleCreatePic()
          } else {
            let data = {
              dh: this.$route.query.dh, //体检状态 0 健康 1 职业
              analyzeId: this.$route.query.id, //样本Id
              orderId: this.$route.query.ddh, //订单id
            }
            createReportData(data).then((response) => {
              if (response.msg.indexOf('成功') != -1) {
                this.$modal.alertSuccess('报告生成成功！', '提醒')
              } else {
                this.$modal.alertWarning(response.msg, '提醒')
              }
            })
          }
        })
        .catch((err) => {
          console.log(err)
        })
      return
      let routeUrl = this.$router.resolve({
        name: this.$route.query.type == 1 ? 'GroupDiseaseReport' : 'GroupHealthReport',
        query: { analyzeId: this.$route.query.id, orderId: this.$route.query.orderId },
      })
      window.open(routeUrl.href)
    },
    // 导出阴性和阳性结果
    handleExport() {
      var queryParams = {
        reportId: this.$route.query.id,
      }
      this.download(
        'group/report_audit/export',
        {
          ...queryParams,
        },
        `user_${new Date().getTime()}.xlsx`
      )
    },
  },
}
</script>
<style lang="scss">
.audit-analyze {
  padding: 0;
  background: transparent;

  .main-nav {
    display: flex;
    list-style: none;
    padding: 0;
    margin: 0;

    li {
      position: relative;
      padding: 8px 18px;
      margin-right: 8px;
      border-radius: 5px 5px 0px 0px;
      overflow: hidden;
      background: #fff;
      cursor: pointer;

      .bg {
        background: #{'var(--theme)'};
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        opacity: 0;
      }

      span {
        position: relative;
        color: #666666;
        font-size: 14px;
        line-height: 20px;
      }

      &.active {
        .bg {
          opacity: 0.07;
        }

        span {
          color: #{'var(--theme)'};
        }
      }
    }
  }

  .main-content {
    flex: 1;

    .el-row {
      padding: 12px 16px;
      background: #fff;
      margin: 0 !important;

      .el-col {
        padding: 0 8px !important;
      }
    }
  }
}
</style>
