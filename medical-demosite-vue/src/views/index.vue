<template>
  <div class="app-container">
    <div v-if="isOnline == 0" class="home">
      <h1><img src="@/assets/images/title.svg" /></h1>
      <ul class="index-nav">
        <li v-for="item in navList" :key="item.index" @click="goTarget(item.name, item.label)">
          <img :src="item.image" alt="item.id" />
          <span>{{ item.label }}</span>
        </li>
      </ul>
      <div class="index-flow">
        <div class="flow-box">
          <flow-chart @goTarget="goTarget" :process="flowLeftList"></flow-chart>
        </div>
        <div class="flow-box">
          <flow-chart @goTarget="goTarget" :process="flowRightList"></flow-chart>
        </div>
      </div>
      <div class="index-state">{{ backgroundConfig.copyright }}</div>
    </div>
    <sale-index v-else></sale-index>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import flowChart from '../components/FlowChart/index.vue'
import saleIndex from '@/views/sale/index.vue'
import { getBackgroundConfig } from '@/api/login'

export default {
  name: 'Index',
  components: { flowChart, saleIndex },
  data() {
    return {
      // 版本号
      version: '1.0.0',
      navList: [
        { id: 'prepareOrder', image: require('../assets/images/index/prepare_order.png'), label: '备单', name: 'Prepare_order' },
        { id: 'registrationList', image: require('../assets/images/index/registration_list.png'), label: '登记列表', name: 'Register_list' },
        { id: 'registration', image: require('../assets/images/index/registration.png'), label: '登记', name: 'Registration' },
        { id: 'refund', image: require('../assets/images/index/refund.png'), label: '退费', name: 'ReturnItem' },
        { id: 'department', image: require('../assets/images/index/department.png'), label: '科室列表', name: 'Section_list' },
        { id: 'inspectAdditions', image: require('../assets/images/index/additions.png'), label: '检验加项', name: 'Inspection_addition' },
        { id: 'departmentAdditions', image: require('../assets/images/index/additions.png'), label: '科室加项', name: 'Clinicallab' },
        { id: 'criticalValue', image: require('../assets/images/index/critical_value.png'), label: '危机值提报', name: 'Manage_crisis_value' },
        { id: 'signIn', image: require('../assets/images/index/sign_in.png'), label: '护理登记', name: 'Preregistration' },
      ],
      flowLeftList: [
        {
          num: 1,
          list: [
            {
              direction: 'row',
              label: '健康总检',
              image: require('../assets/images/index/healthy.png'),
              width: '240px',
              height: '100px',
              iconSize: '80px',
              id: 'healthy',
              clear: 'before',
              name: 'Health_inspection_details',
            },
          ],
        },
        {
          num: 2,
          unset_border: 'unset_bottom',
          list: [
            {
              label: '报告打印',
              image: require('../assets/images/index/report_print.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportPrint',
              clear: 'after',
              name: 'HealthReport',
            },
            {
              label: '报告终审',
              image: require('../assets/images/index/report_final.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportFinal',
              clear: 'after',
              name: 'Health_last',
            },
            {
              label: '报告交接',
              image: require('../assets/images/index/report_handover.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportHandover',
              clear: 'after',
              name: 'Health_handover',
            },
            {
              label: '报告领取',
              image: require('../assets/images/index/report_collection.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportCollection',
              clear: 'after',
              name: 'Health_getreport',
            },
          ],
        },
      ],
      flowRightList: [
        {
          num: 1,
          list: [
            {
              direction: 'row',
              label: '职业总检',
              image: require('../assets/images/index/occupation.png'),
              width: '240px',
              height: '100px',
              iconSize: '80px',
              id: 'occupation',
              clear: 'before',
              name: 'Occupational_inspection_details',
            },
          ],
        },
        {
          num: 2,
          unset_border: 'unset_bottom',
          list: [
            {
              label: '报告打印',
              image: require('../assets/images/index/report_print.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportPrint',
              clear: 'after',
              name: 'DiseaseReport',
            },
            {
              label: '报告终审',
              image: require('../assets/images/index/report_final.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportFinal',
              clear: 'after',
              name: 'Profession_last',
            },
            {
              label: '报告交接',
              image: require('../assets/images/index/report_handover.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportHandover',
              clear: 'after',
              name: 'Career_handover',
            },
            {
              label: '报告领取',
              image: require('../assets/images/index/report_collection.png'),
              width: '148px',
              height: '148px',
              iconSize: '80px',
              id: 'reportCollection',
              clear: 'after',
              name: 'Get_reprot',
            },
          ],
        },
      ],
      // 判断为线上还是线下
      isOnline: Cookies.get('isOnline'),
      // 当前年份
      currentYear: '',
      // 背景配置
      backgroundConfig: {},
    }
  },
  created() {
    if (this.isOnline == 1) {
      this.$route.meta.title = '销售主页'
    }
    this.currentYear = new Date().getFullYear()
  },
  mounted() {
    this.go()
    // 获取背景配置
    this.getBackgroundConfig();
  },
  methods: {
     // 获取配置
    getBackgroundConfig() {
      getBackgroundConfig().then((res) => {
        this.backgroundConfig = res.data;
      }).catch((error) => {
        console.error('获取配置:', error);
      });
    },
    goTarget(href, label) {
      if (Cookies.get('cid') == 1) {
      } else {
        let permi = false
        switch (href) {
          case 'Prepare_order':
            permi = this.$auth.hasPermiAnd(['reception:prepareOrder:list'])
            break
          case 'Register_list':
            permi = this.$auth.hasPermiAnd(['reception:registerList:list'])
            break
          case 'Registration':
            permi = this.$auth.hasPermiAnd(['reception:registration:list'])
            break
          case 'ReturnItem':
            permi = this.$auth.hasPermiAnd(['reception:returnItem:list'])
            break
          case 'Section_list':
            permi = this.$auth.hasPermiAnd(['funcdept:sectionList:list'])
            break
          case 'Clinicallab':
            permi = this.$auth.hasPermiAnd(['funcdept:clinicallab:list'])
            break
          case 'Inspection_addition':
            permi = this.$auth.hasPermiAnd(['funcdept:sample:inspectionAddition:list'])
            break
          case 'Manage_crisis_value':
            permi = this.$auth.hasPermiAnd(['funcdept:crisisValue:list'])
            break
          case 'Preregistration':
            permi = this.$auth.hasPermiAnd(['funcdept:preregistration:list'])
            break
          case 'Health_inspection_details':
            permi = this.$auth.hasPermiAnd(['inspection:healthInspection:list'])
            break
          case 'HealthReport':
            permi = this.$auth.hasPermiAnd(['report:reportPrint:healthReport:list'])
            break
          case 'Health_last':
            permi = this.$auth.hasPermiAnd(['report:reportReview:healthLast:list'])
            break
          case 'Health_handover':
            permi = this.$auth.hasPermiAnd(['report:reportHandover:healthHandover:list'])
            break
          case 'Health_getreport':
            permi = this.$auth.hasPermiAnd(['report:reportCollection:healthGetreport:list'])
            break
          case 'Occupational_inspection_details':
            permi = this.$auth.hasPermiAnd(['inspection:occupationalInspection:list'])
            break
          case 'DiseaseReport':
            permi = this.$auth.hasPermiAnd(['report:reportPrint:diseaseReport:list'])
            break
          case 'Profession_last':
            permi = this.$auth.hasPermiAnd(['report:jobreportReview:professionLast:list'])
            break
          case 'Career_handover':
            permi = this.$auth.hasPermiAnd(['report:reportHandover:careerHandover:list'])
            break
          case 'Get_reprot':
            permi = this.$auth.hasPermiAnd(['report:reportCollection:getReprot:list'])
            break
        }
        if (permi == true) {
          this.$router.push({ name: href, query: { fromIndex: true } })
        } else {
          this.$modal.notifyError('您暂时未获得进入' + label + '的权限，如有需要！请联系管理员！')
        }
      }
    },

    go() {
      this.timer = setTimeout(this.saleIndex, 500)
    },
    saleIndex() {
      if (Cookies.get('cid') == 1) {
        this.$router.push({ name: 'SaleIndex' })
      }
    },
  },
  beforeDestroy() {
    clearTimeout(this.timer)
  },
}
</script>

<style scoped lang="scss">
.app-container {
  height: 100%;
  padding: 0;
  .home {
    // background: linear-gradient(180deg, #fff4f4 0%, #ffe8e9 100%);
    background: #fff8f4;
    position: relative;
    min-height: 100%;
    padding-bottom: 72px;

    h1 {
      text-align: center;
      margin: 48px 0 32px;

      img {
        height: 63px;
      }
    }

    .index-nav {
      display: flex;
      align-items: center;
      justify-content: center;
      text-align: center;
      flex-wrap: wrap;

      li {
        cursor: pointer;
        background: rgba(255, 255, 255, 0.5);
        border-radius: 24px;
        width: 148px;
        height: 148px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin: 16px 16px 0;

        img {
          width: 80px;
          height: 80px;
        }

        span {
          font-weight: 600;
          font-size: 16px;
          line-height: 22px;
          text-align: center;
          color: #333333;
          margin-top: 6px;
        }
      }
    }

    .index-flow {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-around;

      .flow-box {
        width: 700px;
        margin-top: 32px;
      }
    }

    .index-state {
      font-weight: 400;
      font-size: 14px;
      line-height: 20px;
      color: #333333;
      text-align: center;
      position: absolute;
      bottom: 24px;
      width: 100%;
    }

    blockquote {
      padding: 10px 20px;
      margin: 0 0 20px;
      font-size: 17.5px;
      border-left: 5px solid #eee;
    }

    hr {
      margin-top: 20px;
      margin-bottom: 20px;
      border: 0;
      border-top: 1px solid #eee;
    }

    .col-item {
      margin-bottom: 20px;
    }

    ul {
      padding: 0;
      margin: 0;
      list-style-type: none;
    }

    font-family: 'open sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    font-size: 13px;
    color: #676a6c;
    overflow-x: hidden;

    h4 {
      margin-top: 0px;
    }

    h2 {
      margin-top: 10px;
      font-size: 26px;
      font-weight: 100;
    }

    p {
      margin-top: 10px;

      b {
        font-weight: 700;
      }
    }

    .update-log {
      ol {
        display: block;
        list-style-type: decimal;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0;
        margin-inline-end: 0;
        padding-inline-start: 40px;
      }
    }
  }
}
</style>
