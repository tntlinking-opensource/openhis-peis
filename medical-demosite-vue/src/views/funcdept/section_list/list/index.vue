<!-- 科室  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column section-list-main">
    <index-header
      ref="indexHeader"
      :ksID="ksID"
      :mainDisabled="mainDisabled"
      :lockDisable="lockDisable"
      :headParams="headParams"
      :patientData="patientData"
      :sectionOptions="sectionOptions"
      @changeHeadData="changeHeadData"
      @clearData="clearData"
      @handleAudit="handleAudit"
      @handleReAudit="handleReAudit"
      @handleSummary="handleSummary2"
      @handleSummaryPF="handleSummaryPF"
      @searchTableData="searchTableData"
      @handlePort="handlePort"
      @handleLisData="handleLisData"
      @startCheck="handleStart"
    ></index-header>
    <!-- 检验科 -->
    <clinical-laboratory ref="clinicalLaboratory" id="clinicalLaboratory" v-if="$route.meta.dataType == '1'" :clData="clData" :mainDisabled="mainDisabled"></clinical-laboratory>
    <!-- 左右布局 -->
    <div class="table-box" v-else id="sectionIndex">
      <el-row :gutter="10" class="mb8" style="height: 100%">
        <!-- 左侧 -->
        <el-col :span="setSize(1)" style="height: 100%; padding: 0">
          <!-- 一般检查 -->
          <general-inspection
            ref="generalInspection"
            v-if="$route.meta.dataType == '3'"
            :mainDisabled="mainDisabled"
            :leftData="leftData3"
            :giTableData="giTableData"
            @getSectionData="getSectionData"
            @changeJLC="changeJLC"
            @changeConclusion="changeConclusion"
            @getRankData="getRankData"
            @changeBtnState="changeBtnState"
          ></general-inspection>
          <!-- 碳14/碳13 -->
          <carbon v-if="$route.meta.dataType == '4' && showPicSection" :carbon="$route.meta.sectionNo == '179' ? '14' : '13'" :leftData="leftData4" :mainDisabled="mainDisabled" @inputResultChange="inputResultChange" @itemChange="itemChange2"></carbon>
          <!-- 肺功能 -->
          <pulmonary-function
            ref="pulmonaryFunction"
            v-if="$route.meta.dataType == '5'"
            :mainDisabled="mainDisabled"
            :leftData="leftData5"
            :tableData5="tableData5"
            @getRankData="getRankData"
            @getSectionData="getSectionData"
            @changeConclusion="changeConclusion"
            @changeJLC="changeJLC"
            @changeLeftData="changeLeftData5"
            @selectChangePF="selectChangePF"
            @handleTableData5Info="handleTableData5Info"
          ></pulmonary-function>
          <!-- 图像、无图像科室 -->
          <section-modal-a v-if="showPicSection && ($route.meta.dataType == '6' || $route.meta.dataType == '7')" :sectionID="$route.meta.sectionNo" :leftData="leftData6" :mainDisabled="mainDisabled" @itemChange="itemChange" @inputResultChange="inputResultChange"> </section-modal-a>
          <!-- 视力辩色力 181 -->
          <color-discrimination
            ref="colorDiscrimination"
            v-if="$route.meta.dataType == '11'"
            :mainDisabled="mainDisabled"
            @itemChangeSLJC="itemChangeSLJC"
            @getSectionData="getSectionData"
            @handleAudit="handleAudit"
            :leftData="leftData11"
            :patientcode="rightData11.patient.patientcode"
          ></color-discrimination>
          <!-- 骨密度 -->
          <bone-density ref="gmd" v-if="$route.meta.dataType == '10'" :mainDisabled="mainDisabled" @getSectionData="getSectionData" @handleAudit="handleAudit" @handleReAudit="handleReAudit" :leftData="leftData10" :rightData="rightData10"></bone-density>
          <!-- 职业性问诊 -->
          <greetings ref="greetings" v-if="$route.meta.dataType == '8'" :mainDisabled="mainDisabled" :leftData="leftData8" @greetingRefresh="greetingRefresh" @changeAudit="changeAudit"></greetings>
          <!-- 电测听 -->
          <electro ref="electro" v-if="$route.meta.dataType == '9'" :mainDisabled="mainDisabled" @getSectionData="getSectionData" :leftData="leftData9" @getRankData="getRankData" :tableData9="tableData9"> </electro>
        </el-col>
        <!-- 右侧 -->
        <el-col :span="setSize(2)" style="height: 100%; padding: 0">
          <!-- 一般检查/碳14/碳13/视力辩色力 -->
          <index-right-a
            v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '11'"
            :mainDisabled="mainDisabled"
            :rightData="$route.meta.dataType == '3' ? rightData3 : $route.meta.dataType == '4' ? rightData4 : $route.meta.dataType == '5' ? rightData5 : rightData11"
            @selectChange="selectChange"
            @selectChange1="selectChange1"
            @getSectionDataOnly="getSectionDataOnly"
          ></index-right-a>
          <!-- 图像、无图像科室 -->
          <index-right-b
            v-else-if="$route.meta.dataType == '6' || $route.meta.dataType == '7' || $route.meta.dataType == '10'"
            :mainDisabled="mainDisabled"
            :lockDisable="lockDisable"
            :headParams="headParams"
            :rightData="$route.meta.dataType == '6' || $route.meta.dataType == '7' ? rightData6 : rightData10"
            @handleAudit="handleAudit"
            @handleReAudit="handleReAudit"
            @handleSummary="handleSummary"
            @selectChange="selectChange"
            @selectChange1="selectChange1"
            @checkDetails="checkDetails"
          ></index-right-b>
          <!-- 职业性问诊 -->
          <index-right-c ref="indexRightC" v-else-if="$route.meta.dataType == '8'" :mainDisabled="mainDisabled" :rightData="rightData8" @getSectionData="getSectionData" @getRankData="getRankData" :tableData8="tableData8"></index-right-c>
          <!-- 电测听 -->
          <electro-audiometry ref="electroAudiometry" v-else-if="$route.meta.dataType == '9'" :mainDisabled="mainDisabled" :rightData="rightData9"></electro-audiometry>
        </el-col>
      </el-row>
    </div>
    <!-- 查看提醒对话框 -->
    <remind ref="remind"></remind>
    <!-- 查看小结详情 -->
    <look-detail ref="lookDetail" @updateConclusions="updateConclusions"></look-detail>
  </div>
</template>
<script>
import {
  getCarbonData,
  getGIData,
  getjlcData,
  saveOrUpdateGI,
  reverseGI,
  getAudiometryData,
  getCase1Data,
  getRemindStr,
  bmdGmd,
  getNkCheckedData,
  saveOrUpdateEA,
  reverseEA,
  caseReverse,
  case1AutoSave,
  visionSljc,
  caseshenhe,
  getInquiryData,
  reverseInquiry,
  caseReverseSljc,
  shenheSljc,
  getKsList,
} from '@/api/funcdept/section_list/index.js'
import { getUserInfo, getGridData, receiveApi } from '@/api/funcdept/section_list/clinical_laboratory.js'
import { getLungInfo, jlcDataPF, saveOrUpdatePF, reversePF, uploadLungIm, uploadLungImHN } from '@/api/funcdept/section_list/pulmonary_function.js'
import { getDeptDataConfig } from '@/api/system/config.js'


import indexHeader from './index_header.vue'
import clinicalLaboratory from './clinical_laboratory/index.vue'
import generalInspection from './index_left/general_inspection.vue'
import carbon from './index_left/carbon.vue'
import pulmonaryFunction from './index_left/pulmonary_function.vue'
import sectionModalA from './index_left/section_modal_a.vue'
import colorDiscrimination from './index_left/color_discrimination.vue'
import boneDensity from './index_left/bone_density'
import greetings from './index_left/professional_greetings.vue'
import electro from './index_left/electro_audiometry.vue'

import indexRightA from './index_right/index_right_a.vue'
import indexRightB from './index_right/index_right_b.vue'
import indexRightC from './index_right/index_right_c.vue'
import electroAudiometry from './index_right/electro_audiometry.vue'
import remind from './dialog/remind.vue'
import lookDetail from './dialog/look_detail.vue'
import Cookies from 'js-cookie'

export default {
  components: {
    indexHeader,
    clinicalLaboratory,
    generalInspection,
    carbon,
    pulmonaryFunction,
    greetings,
    sectionModalA,
    indexRightA,
    indexRightB,
    indexRightC,
    remind,
    electro,
    colorDiscrimination,
    boneDensity,
    electroAudiometry,
    lookDetail,
  },
  data() {
    return {
      // 科室id
      ksID: undefined,
      // 是否禁用
      mainDisabled: true,
      // 是否锁定
      lockDisable: false,
      // 传给头部组件的值
      headParams: {
        patientcode: '',
        patientname: '',
        autoFill: true,
        isVIP: '',
        isAudit: undefined,
        diagnosticReportD: false, //职业报告按钮是否显示
        diagnosticReportH: false, //健康报告按钮是否显示
        syxb: undefined, //科室加项需要
        tjlx: undefined, //科室加项需要
      },
      patientData: undefined,
      // 科室列表
      sectionOptions: [],

      // 检验科用户信息
      clData: {
        peispatient: {},
        sectionResultMain: {},
        jlcData: [],
        tableList: [],
      },
      clTableList: [],

      // 页面左侧相关数据
      leftData: {
        tjreg: {
          isUnchecked: undefined,
        },
        inputData: {},
        listData: [],
        peispatientConsultationPic: {},
      },

      // 一般检查页面左侧相关数据
      leftData3: {
        tjreg: {
          isUnchecked: undefined,
        },
        inputData: {},
        listData: [],
      },
      // 一般检查页面右侧相关数据
      rightData3: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      // 一般检查表格数据
      giTableData: {
        tableData: [],
        total: 0,
      },

      // 碳页面左侧相关数据
      leftData4: {
        tjreg: {
          isUnchecked: undefined,
        },
        inputData: {},
        listData: [],
      },
      // 碳页面右侧相关数据
      rightData4: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      // 碳13 14范围值
      C13Data: {
        high: 4
      },
      C14Data: {
        high: 40
      },

      // 肺功能页面左侧相关数据
      leftData5: {},
      leftData5Info: [],
      // 用户操作后肺功能数据
      tempdata5: {},
      formdata5: {},
      // 肺功能页面右侧相关数据
      rightData5: {
        patient: {},
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      // 肺功能表格数据
      tableData5: {
        tableData: [],
        total: 0,
      },
      // 肺功能科室端口
      port: null,
      reader: undefined,
      // writer: undefined,
      // writeInt: undefined,
      keepReading: false,

      // 图像、无图像页面左侧相关数据
      leftData6: {
        tjreg: {
          isUnchecked: undefined,
        },
        inputData: {},
        listData: [],
      },
      // 图像、无图像页面右侧相关数据
      rightData6: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },

      // 职业问诊读卡防抖
      readDebounce8: false,
      // 职业问诊页面左侧相关数据
      leftData8: {},
      // 职业问诊页面右侧相关数据
      rightData8: {},
      // 职业问诊表格数据
      tableData8: {
        tableData: [],
        total: 0,
      },

      //  电测听页面左侧相关数据
      leftData9: {},
      //  电测听页面右侧相关数据
      rightData9: {},
      // 电测听表格数据
      tableData9: {
        tableData: [],
        total: 0,
      },

      //  骨密度页面左侧相关数据
      leftData10: {
        tjreg: {
          isUnchecked: undefined,
        },
        inputData: {},
        listData: [],
      },
      //  骨密度页面右侧相关数据
      rightData10: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      // 骨密度表格数据
      tableData10: {
        tableData: [],
        total: 0,
      },
      // 视力检查页面左侧相关数据
      leftData11: {
        // tjreg: {
        //   isUnchecked: undefined,
        // },
        // inputData: {},
        // listData: [],
      },
      // 视力检查页面右侧相关数据
      rightData11: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      //视力检查表格数据
      tableData11: {
        tableData: [],
        total: 0,
      },

      // 页面右侧相关数据
      rightData: {
        patient: {}, //体检者信息
        peispatient: {
          conclusions: '',
        }, //体检者信息(不同接口)
        picture: '',
        sectionResultMain: {
          conclusions: '',
        }, //科室检查结果主表
        jlcData: [],
        griddata: [],
      },
      // 图像、无图像科室是否显示左侧
      showPicSection: false,
      // 审核时禁用按钮
      forbiddenAudit: false,
      // 保存时禁用按钮
      forbiddenSave: false,
      // 审核后重新获取后清空体检号
      clearCode: false,
    }
  },
  created() {
    this.ksID = this.$route.meta.ksID
    // 生成组件实例唯一标识，避免多路由或重复打开时事件冲突
    this.instanceId = this._uid || Date.now() + Math.random()
    this.rankDbEventName = 'rankDb' + this.ksID + '_' + this.instanceId

    // 先清理当前实例可能存在的旧监听（防止重复注册）
    this.bus.$off(this.rankDbEventName)

    //获取碳13 碳14范围
    getDeptDataConfig()
      .then((res) => {
        if (res.code != 200) {
          this.$alert('获取数据异常', '提醒').then(() => {
          })
        } else {
          this.C13Data = res.data.rangeData.find(item => item.deptName == '13C');
          this.C14Data = res.data.rangeData.find(item => item.deptName == '14C');
        }
      })
      .catch(() => {
      })

    if (this.$route.params.patientcode) {
      this.headParams.patientcode = this.$route.params.patientcode
      this.getSectionData(this.headParams.patientcode, this.ksID)
    }
    // 使用实例特定的处理函数，避免多个实例互相干扰
    this.handleRankDbEvent = (patientcode, ksID) => {
      // 只有当前实例的ksID匹配时才处理
      if (ksID === this.ksID) {
        this.getSectionData(patientcode, ksID)
      }
    }

    // 注册事件监听（使用通用事件名，但通过实例ID和ksID双重校验）
    this.bus.$on('rankDb' + this.ksID, this.handleRankDbEvent)
    if (this.$route.meta.dataType == '3' || this.$route.meta.dataType == '5' || this.$route.meta.dataType == '8' || this.$route.meta.dataType == '9') {
      let date = new Date()
      //获取当前时间的年份转为字符串
      let year = date.getFullYear().toString() //'2019'
      //获取月份，由于月份从0开始，此处要加1，判断是否小于10，如果是在字符串前面拼接'0'
      let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1).toString() : (date.getMonth() + 1).toString() //'04'
      //获取天，判断是否小于10，如果是在字符串前面拼接'0'
      let da = date.getDate() < 10 ? '0' + date.getDate().toString() : date.getDate().toString() //'1
      let day = year + '-' + month + '-' + da
      this.$nextTick(() => {
        if (this.$route.meta.dataType == '3') {
          this.$refs.generalInspection.searchTableData([day, day])
        } else if (this.$route.meta.dataType == '5') {
          getKsList(this.ksID).then((res) => {
            this.sectionOptions = res.data
          })
          this.$refs.pulmonaryFunction.searchTableData([day, day])
        } else if (this.$route.meta.dataType == '8') {
          this.$refs.indexRightC.searchTableData([day, day])
        } else {
          this.$refs.electro.searchTableData([day, day])
        }
      })
    }
    // 使用实例特定的处理函数，避免多个实例互相干扰
    this.handleGetConclusions = () => {
      let conclusions = ''
      if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        conclusions = this.rightData.peispatient.conclusions
      }
      this.bus.$emit('returnConclusions', conclusions)
    }
    this.bus.$on('getConclusions', this.handleGetConclusions)

    this.handleElectroUpload = (auto) => {
      // 校验是否为当前实例（通过ksID判断）
      let query = {
        autoFill: this.headParams.autoFill,
        ksId: this.ksID,
        patientCode: this.leftData9.patient.patientcode,
      }
      getAudiometryData(query).then(({ data }) => {
        if (data.audiometry && data.audiometry.airImgPath && data.audiometry.boneImgPath) {
          this.rightData9.audiometry = data.audiometry
          this.$modal.msgSuccess('图片生成成功', '提示')
          if (auto) {
            this.$refs.indexHeader.handleAudit()
          }
        } else {
          this.$modal.msgWarning('图片生成失败,请重新生成', '提示')
        }
      })
    }
    this.bus.$on('electroUpload', this.handleElectroUpload)
  },
  mounted() {
    this.$refs.indexHeader.$refs.patientcode.focus()
  },
  activated() {
    this.$refs.indexHeader.$refs.patientcode.focus()
    if (this.$route.meta.dataType == '5') {
      this.$nextTick(() => {
        this.$refs.pulmonaryFunction.tableData = this.leftData5Info
      })
    }
    if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
      this.leftData6 = JSON.parse(JSON.stringify(this.leftData6))
    }
  },
  beforeDestroy() {
    // 清理当前实例的事件监听
    if (this.handleRankDbEvent) {
      this.bus.$off('rankDb' + this.ksID, this.handleRankDbEvent)
    }
    // 也清理实例特定的事件名（如果使用了）
    if (this.rankDbEventName) {
      this.bus.$off(this.rankDbEventName)
    }
    // 清理其他事件监听
    if (this.handleGetConclusions) {
      this.bus.$off('getConclusions', this.handleGetConclusions)
    }
    if (this.handleElectroUpload) {
      this.bus.$off('electroUpload', this.handleElectroUpload)
    }
  },
  methods: {
    // 设置左右尺寸
    setSize(type) {
      if (this.$route.meta.dataType == '9') {
        //电测听检查
        if (type == 1) return 10
        else return 14
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        if (type == 1) return 14
        else return 10
      } else {
        return 12
      }
    },
    // 临时保存肺功能数据
    handleTableData5Info($event) {
      this.leftData5Info = $event
      this.tempdata5 = $event
    },
    // 头部数据改变
    changeHeadData(val) {
      this.headParams = val
    },
    // 重置
    clearData() {
      if (this.$route.meta.dataType == '3') {
        // 一般检查
        this.leftData3.tjreg.isUnchecked = 0
        this.leftData3.tjreg.isDanger = 0
        this.leftData3.tjreg.ssy = undefined
        this.leftData3.tjreg.szy = undefined
        this.leftData3.tjreg.xy = undefined
        this.leftData3.tjreg.xyms = null
        this.leftData3.tjreg.mb = undefined
        this.leftData3.tjreg.sg = undefined
        this.leftData3.tjreg.tz = undefined
        this.leftData3.tjreg.bmi = undefined
        this.leftData3.tjreg.bmims = null
        this.leftData3.tjreg.respiratoryRate = undefined
        this.leftData3.tjreg.temperature = undefined
        this.leftData3.tjreg.bust = undefined
        this.leftData3.tjreg.commonState = undefined
        this.leftData3.tjreg.xj = null
        this.rightData3.sectionResultMain.conclusions = null
        this.rightData3.jlcData = []
      } else if (this.$route.meta.dataType == '4') {
        //碳
        this.leftData4.forEach((el) => {
          el.qij = 0
          el.wjzjb = '0'
          el.jcms = ''
          if (el.fentryonly == 'shuru') {
            el.inputResult = ''
          } else {
            el.checkList = []
          }
        })
        this.rightData4.sectionResultMain.conclusions = ''
        this.rightData4.jlcData = []
      } else if (this.$route.meta.dataType == '5') {
        // 肺功能
        this.leftData5 = {
          depId: this.$route.meta.ksID,
          feffa: undefined,
          feffb: undefined,
          feffc: undefined,
          fev: undefined,
          fevFvc: undefined,
          fvc: undefined,
          id: this.leftData5.id || undefined,
          isDanger: '0',
          isUnchecked: '0',
          mmef: undefined,
          patientcode: this.headParams.patientcode,
          percentageFeffa: undefined,
          percentageFeffb: undefined,
          percentageFeffc: undefined,
          percentageFev: undefined,
          percentageFevFvc: undefined,
          percentageFvc: undefined,
          percentageMmef: undefined,
          predictFeffa: undefined,
          predictFeffb: undefined,
          predictFeffc: undefined,
          predictFev: undefined,
          predictFevFvc: undefined,
          predictFvc: undefined,
          predictMmef: undefined,
          xj: '',
        }
        this.rightData5.sectionResultMain.conclusions = ''
        this.formdata5 = JSON.parse(JSON.stringify(this.leftData5))
        this.tempdata5 = JSON.parse(JSON.stringify(this.leftData5))
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        // 图像、无图像
        this.leftData6.forEach((el) => {
          el.qij = 0
          el.wjzjb = '0'
          el.jcms = ''
          if (el.fentryonly == 'shuru') {
            el.inputResult = ''
          } else {
            el.checkList = []
          }
        })
        this.rightData6.sectionResultMain.conclusions = ''
        this.rightData6.griddata = []
      } else if (this.$route.meta.dataType == '8') {
        //问诊检查
        this.$refs.greetings.clearData()
      } else if (this.$route.meta.dataType == '10') {
        //骨密度
        this.$refs.gmd.clearLeftData10()
        this.rightData10.sectionResultMain.conclusions = ''
      } else if (this.$route.meta.dataType == '11') {
        //视力检查
        this.leftData11.sldata.forEach((el) => {
          if (el.fentryonly == 'shuru') {
            el.inputResult = ''
          } else {
            el.checkList = []
          }
        })
        this.rightData11.sectionResultMain.conclusions = ''
        this.rightData11.griddata = []
        this.rightData11.jlcData = []
      }
    },
    // 查询页面详情数据
    getSectionData(patientcode, ksID, showDia = true) {
      this.headParams.patientcode = patientcode
      this.headParams.isAudit = undefined
      this.mainDisabled = true
      this.lockDisable = false
      let query = {
        autoFill: this.headParams.autoFill,
      }
      getRemindStr({
        ksID: this.$route.meta.ksID,
        patientcode: patientcode,
        autoFill: this.headParams.autoFill,
      }).then(({ data }) => {
        if (data == 'true') {
          this.$refs.indexHeader.handleRemind()
        }
      })
      if (this.$route.meta.dataType == '1') {
        //检验科检查
        query.KsId = ksID
        query.patientCode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getUserInfo(query)
          .then(({ data, code, msg }) => {
            if (code == 500) {
              clLoading.close()
              this.$alert(msg.split('@')[1], '提示')
              this.$tab.refreshPage()
              return
            }
            this.headParams.isVIP = data.isVIP
            if (data && data.peispatient) {
              this.headParams.patientcode = data.peispatient.patientcode ? data.peispatient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.peispatient.patientname ? data.peispatient.patientname : ''
            }
            if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            this.clData.peispatient = data.peispatient
            this.clData.picture = data.picture
            this.clData.sectionResultMain = data.sectionResultMain || {}
            this.clData.sectionResultMain.conclusions = data.sectionResultMain && data.sectionResultMain.conclusions ? data.sectionResultMain.conclusions : ''
            this.patientData = this.clData
            clLoading.close()
            let alertMsg = ''
            if (data.peispatient.jktjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.peispatient.doctorfinalNameR == null ? '' : data.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
            } else if (data.peispatient.zytjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.peispatient.patientnameencoded == null ? '' : data.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
            } else if (data.peispatient.ffinallocked == 1) {
              alertMsg = '本体检者检查结果已被' + (data.peispatient.idDoctorapply == null ? '' : data.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
            } else if (data.peispatient.idGuidenurse == 1) {
              alertMsg = '本体检者检查结果已被' + (data.peispatient.parsedassignedsuiteandfi == null ? '' : data.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
            } else if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              alertMsg = '该体检号已审核，不能修改！'
            }
            if (alertMsg) {
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.mainDisabled = false
            }
          })
          .catch(() => {
            clLoading.close()
          })
        getGridData({ current: 1, size: 999, ksId: ksID, patientCode: patientcode, autoFill: this.headParams.autoFill }).then(({ data }) => {
          this.$refs.clinicalLaboratory.computeCell(data.records, () => {
            this.$nextTick(() => {
              this.clData.tableList = data.records
            })
          })
        })
        // 获取结论词
        getjlcData({
          ksId: ksID,
          patientCode: patientcode,
          autoFill: this.headParams.autoFill,
        }).then(({ data }) => {
          this.clData.jlcData = data
        })
      } else if (this.$route.meta.dataType == '2') {
        // 外送项目检查
      } else if (this.$route.meta.dataType == '3') {
        //一般检查
        query.ksId = ksID
        query.patientCode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getGIData(query)
          .then(({ data, code, msg }) => {
            if (code == 500) {
              clLoading.close()
              this.$alert(msg.split('@')[1], '提示', {
                confirmButtonText: '确定',
              })
              this.$tab.refreshPage()
              return
            }
            this.headParams.isVIP = data.isVIP
            if (data && data.patient) {
              this.headParams.patientcode = data.patient.patientcode ? data.patient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.patient.patientname ? data.patient.patientname : ''
            }
            if (this.clearCode) {
              this.$nextTick(() => {
                this.headParams.patientcode = ''
              })
            }
            this.clearCode = false
            let alertMsg = ''
            if (data.patient.jktjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.doctorfinalNameR == null ? '' : data.patient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
            } else if (data.patient.zytjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.patientnameencoded == null ? '' : data.patient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
            } else if (data.patient.ffinallocked == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.idDoctorapply == null ? '' : data.patient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
            } else if (data.patient.idGuidenurse == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.parsedassignedsuiteandfi == null ? '' : data.patient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
            } else if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              alertMsg = '该体检号已审核，不能修改！'
            }
            if (alertMsg) {
              this.mainDisabled = true
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.mainDisabled = false
            }
            if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            var idExamType = data.patient.idExamtype
            // 判断是否显示健康/职业报告按钮
            if (idExamType == '0') {
              this.headParams.diagnosticReportD = false
              this.headParams.diagnosticReportH = true
            } else if (idExamType == '1') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = false
            } else if (idExamType == '2') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = true
            }
            this.leftData3 = data
            if (!this.leftData3.tjreg) {
              this.leftData3.tjreg = {
                isUnchecked: 0,
                isDanger: 0,
                ssy: undefined,
                szy: undefined,
                xy: undefined,
                xyms: undefined,
                mb: undefined,
                sg: undefined,
                tz: undefined,
                bmi: undefined,
                bmims: undefined,
                respiratoryRate: undefined,
                temperature: undefined,
                bust: undefined,
                commonState: undefined,
              }
            } else {
              this.leftData3.tjreg.isUnchecked = this.leftData3.tjreg.isUnchecked == null ? 0 : this.leftData3.tjreg.isUnchecked
              this.leftData3.tjreg.isDanger = this.leftData3.tjreg.isDanger == null ? 0 : this.leftData3.tjreg.isDanger
              this.leftData3.tjreg.ssy = this.leftData3.tjreg.ssy == null ? undefined : this.leftData3.tjreg.ssy
              this.leftData3.tjreg.szy = this.leftData3.tjreg.szy == null ? undefined : this.leftData3.tjreg.szy
              this.leftData3.tjreg.xy = this.leftData3.tjreg.xy == null ? undefined : this.leftData3.tjreg.xy
              this.leftData3.tjreg.mb = this.leftData3.tjreg.mb == null ? undefined : this.leftData3.tjreg.mb
              this.leftData3.tjreg.sg = this.leftData3.tjreg.sg == null ? undefined : this.leftData3.tjreg.sg
              this.leftData3.tjreg.tz = this.leftData3.tjreg.tz == null ? undefined : this.leftData3.tjreg.tz
              this.leftData3.tjreg.bmi = this.leftData3.tjreg.bmi == null ? undefined : this.leftData3.tjreg.bmi
              this.leftData3.tjreg.respiratoryRate = this.leftData3.tjreg.respiratoryRate == null ? undefined : this.leftData3.tjreg.respiratoryRate
              this.leftData3.tjreg.temperature = this.leftData3.tjreg.temperature == null ? undefined : this.leftData3.tjreg.temperature
              this.leftData3.tjreg.bust = this.leftData3.tjreg.bust == null ? undefined : this.leftData3.tjreg.bust
              this.leftData3.tjreg.commonState = this.leftData3.tjreg.commonState == null ? undefined : this.leftData3.tjreg.commonState
            }
            this.rightData3.patient = data.patient
            this.rightData3.picture = data.picture
            this.rightData3.sectionResultMain = data.sectionResultMain ? data.sectionResultMain : { conclusions: '' }
            this.rightData3.sectionResultMain.conclusions = data.sectionResultMain && data.sectionResultMain.conclusions ? data.sectionResultMain.conclusions : ''
            let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
            this.rightData3.sectionResultMain.rummagerName = data.sectionResultMain && data.sectionResultMain.rummagerName ? data.sectionResultMain.rummagerName : auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.getCookie('username'))
            this.rightData3.sectionResultMain.rummagerId = data.sectionResultMain && data.sectionResultMain.rummagerId ? data.sectionResultMain.rummagerId : auditInfo ? auditInfo.rummagerId : decodeURIComponent(this.getCookie('userNo'))
            this.rightData3.sectionResultMain.writeName = data.sectionResultMain && data.sectionResultMain.writeName ? data.sectionResultMain.writeName : auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username'))
            this.rightData3.sectionResultMain.writeId = data.sectionResultMain && data.sectionResultMain.writeId ? data.sectionResultMain.writeId : auditInfo ? auditInfo.writeId : decodeURIComponent(this.getCookie('userNo'))
            this.rightData3.sectionResultMain.writeTime = data.sectionResultMain && data.sectionResultMain.writeTime ? data.sectionResultMain.writeTime : this.$getDate()
            this.rightData3.sectionResultMain.rummagerTime = data.sectionResultMain && data.sectionResultMain.rummagerTime ? data.sectionResultMain.rummagerTime : this.$getDate()
            clLoading.close()
          })
          .catch((error) => {
            console.error(error)
            clLoading.close()
          })
        // 获取结论词
        getjlcData(query).then(({ data }) => {
          this.rightData3.jlcData = data
        })
      } else if (this.$route.meta.dataType == '4') {
        //C13 C14检查
        query.ksID = ksID
        query.patientcode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getCarbonData(query)
          .then(({ data, code, msg }) => {
            if (code == 500) {
              clLoading.close()
              this.$alert(msg.split('@')[1], '提示', {
                confirmButtonText: '确定',
              })
              this.$tab.refreshPage()
              return
            }
            this.headParams.isVIP = data.isVIP
            if (data && data.peispatient) {
              this.headParams.patientcode = data.peispatient.patientcode ? data.peispatient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.peispatient.patientname ? data.peispatient.patientname : ''
            }
            if (this.clearCode) {
              this.headParams.patientcode = ''
            }
            this.clearCode = false
            if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            let alertMsg = ''
            if (data.flag == '该体检号已被禁检') {
              this.$alert('该体检号已被禁检', '提示', {
                confirmButtonText: '确定',
                type: 'error',
              })
              clLoading.close()
              return
            } else if (data.flag == 'audit') {
              if (data.peispatient.jktjzt == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.doctorfinalNameR == null ? '' : data.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.zytjzt == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.patientnameencoded == null ? '' : data.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.FFinallocked == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.idDoctorapply == null ? '' : data.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.idGuidenurse == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.parsedassignedsuiteandfi == null ? '' : data.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
              } else {
                alertMsg = '该体检号已审核，不能修改！'
              }
              this.mainDisabled = true
            } else if (data.flag == '该体检号已被禁检') {
              this.$alert('该体检号已被禁检', '提示', {
                confirmButtonText: '确定',
                type: 'error',
              })
              clLoading.close()
              return
            }
            if (alertMsg) {
              this.headParams.isAudit = true
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.mainDisabled = false
              this.headParams.isAudit = false
            }
            var idExamType = data.peispatient.idExamtype
            // 判断是否显示健康/职业报告按钮
            if (idExamType == '0') {
              this.headParams.diagnosticReportD = false
              this.headParams.diagnosticReportH = true
            } else if (idExamType == '1') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = false
            } else if (idExamType == '2') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = true
            }
            this.rightData4 = JSON.parse(JSON.stringify(data))
            this.rightData4.patient = data.peispatient
            let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
            this.rightData4.sectionResultMain = data.sectionResultMain || {
              rummagerId: auditInfo ? auditInfo.rummagerId : this.$getCookie('userNo'),
              rummagerName: auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.$getCookie('username')),
              rummagerTime: this.$getDate(),
              writeName: auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username')),
              writeId: auditInfo ? auditInfo.writeId : this.$getCookie('userNo'),
              writeTime: this.$getDate(),
              conclusions: '',
            }
            this.rightData4.sectionResultMain.isAudit = this.headParams.isAudit ? 1 : 0
            this.rightData4.jlcData = data.griddata
            this.rightData4.jlcData.forEach((el) => {
              el.jlcName = el.name
            })

            // 重组左侧多选格式
            let num = -1
            let title = ''
            let listData = []
            data.nkdata.forEach((el) => {
              if (title != el.jcxmmc) {
                if (el.fentryonly == 'shuru') {
                  var jcmsData = ""
                  if (el.sfxmmc.includes('14') || el.jcxmmc.includes('CPM') || el.jcxmmc.includes('cpm')) {
                    jcmsData = `CPM值(0-${this.C14Data.high}):0;`
                  } else {
                    jcmsData = `${el.jcxmmc}:0;`
                  }
                  listData.push(
                    Object.assign(el, {
                      jcms: jcmsData,
                      inputResult: '0',
                      qij: 0,
                      wjzjb: '0',
                    })
                  )
                } else {
                  listData.push({
                    sfxmmc: el.sfxmmc,
                    sfxmId: el.sfxmId,
                    tjxmId: el.tjxmId,
                    jcxmmc: el.jcxmmc,
                    checkList: [],
                    optionList: [el],
                    jcms: '',
                    qij: 0,
                    wjzjb: '0',
                  })
                }
                title = el.jcxmmc
                num++
              } else {
                listData[num].optionList.push(el)
              }
              if (el.fentryonly == 'gouxuan' && el.mrxz == 'morenxuanzhong') {
                listData[num].checkList.push(el.sname)
                listData[num].jcms = listData[num].jcxmmc + ': ' + listData[num].checkList.join('、') + ';'
              }
            })
            this.leftData4 = listData
            this.leftData4 = JSON.parse(JSON.stringify(this.leftData4))
            // 获取审核后数据
            // if (data.flag == 'audit') {
            getNkCheckedData(query).then(({ data }) => {
              if (data) {
                this.rightData4.sectionResultMain.conclusions = data.xj ? data.xj : ''
                this.rightData4.sectionResultMain.rummagerName = data.jcrName ? data.jcrName : this.rightData4.sectionResultMain.rummagerName
                if (!this.rightData4.sectionResultMain.rummagerName) {
                  this.rightData4.sectionResultMain.rummagerId = this.$getCookie('userNo')
                  this.rightData4.sectionResultMain.rummagerName = decodeURIComponent(this.$getCookie('username'))
                }
                this.rightData4.sectionResultMain.rummagerTime = (data.jcsj ? data.jcsj : this.rightData4.sectionResultMain.rummagerTime) || this.$getDate()
                this.rightData4.sectionResultMain.writeName = data.lrrName ? data.lrrName : this.rightData4.sectionResultMain.writeName
                this.rightData4.sectionResultMain.writeTime = (data.lrsj ? data.lrsj : this.rightData4.sectionResultMain.writeTime ? this.rightData4.sectionResultMain.writeTime : JSON.parse(JSON.stringify(this.rightData4.sectionResultMain.rummagerTime))) || this.$getDate()
                if (!this.mainDisabled) {
                  this.rightData4.sectionResultMain.writeName = decodeURIComponent(this.getCookie('username'))
                  this.rightData4.sectionResultMain.writeId = decodeURIComponent(this.getCookie('userNo'))
                }
                data.data.forEach((el) => {
                  if (el.inputResult) {
                    this.leftData4.forEach((val) => {
                      if (el.jxcmmc == val.jcxmmc) {
                        val.qij = el.qij
                        val.wjzjb = el.wjzjb
                        if (el.ms) {
                          val.jcms = el.ms
                        } else {
                          if (el.sfxmmc.includes('14') || el.jcxmmc.includes('CPM') || el.jcxmmc.includes('cpm')) {
                            val.jcms = `CPM值(0-${this.C14Data.high}):${el.inputResult};`
                          } else {
                            val.jcms = `${el.jcxmmc}:${el.inputResult};`
                          }
                        }
                        val.inputResult = el.inputResult
                      }
                    })
                  } else {
                    this.leftData4.forEach((val) => {
                      if (el.jxcmmc == val.jcxmmc) {
                        val.qij = el.qij
                        val.wjzjb = el.wjzjb
                        val.jcms = el.ms
                        if (val.checkList[0] == val.optionList[0].sname) {
                          val.checkList = [el.tzcmc]
                        } else {
                          val.checkList.push(el.tzcmc)
                        }
                      }
                    })
                  }
                })
              }
            })
            // }
            this.rightData4 = JSON.parse(JSON.stringify(this.rightData4))
            this.showPicSection = true
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
            this.$tab.refreshPage()
          })
      } else if (this.$route.meta.dataType == '5') {
        // 肺功能检查
        query.ksId = ksID
        query.patientCode = patientcode
        const pfLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getLungInfo(query).then(({ data, code, msg }) => {
          if (code == 500) {
            pfLoading.close()
            this.$alert(msg.split('@')[1], '提示')
            this.$tab.refreshPage()
            return
          }

          this.headParams.isVIP = data.isVIP
          this.headParams.age = data.patient.age
          this.headParams.idSex = data.patient.idSex

          if (data && data.patient) {
            this.headParams.patientcode = data.patient.patientcode ? data.patient.patientcode : this.headParams.patientcode
            this.headParams.patientname = data.patient.patientname ? data.patient.patientname : ''
          }
          if (this.clearCode) {
            this.headParams.patientcode = ''
          }
          this.clearCode = false
          if (data.main) {
            data.sectionResultMain = data.main
          }
          let alertMsg = ''
          if (data.patient.jktjzt == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.doctorfinalNameR == null ? '' : data.patient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.zytjzt == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.patientnameencoded == null ? '' : data.patient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.ffinallocked == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.idDoctorapply == null ? '' : data.patient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.idGuidenurse == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.parsedassignedsuiteandfi == null ? '' : data.patient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
          } else if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
            alertMsg = '该体检号已审核，不能修改！'
          }
          if (alertMsg) {
            this.mainDisabled = true
            if (showDia) {
              this.$alert(alertMsg, '提示')
            }
          } else {
            this.mainDisabled = false
          }
          if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
            this.headParams.isAudit = true
          } else {
            this.headParams.isAudit = false
          }
          // 数据串口
          // this.handlePort()
          var idExamType = data.patient.idExamtype
          // 判断是否显示健康/职业报告按钮
          if (idExamType == '0') {
            this.headParams.diagnosticReportD = false
            this.headParams.diagnosticReportH = true
          } else if (idExamType == '1') {
            this.headParams.diagnosticReportD = true
            this.headParams.diagnosticReportH = false
          } else if (idExamType == '2') {
            this.headParams.diagnosticReportD = true
            this.headParams.diagnosticReportH = true
          }
          if (data.lung) {
            data.lung.isDanger = data.feeitem.severedegree
            data.lung.isUnchecked = data.feeitem.fgiveup
          }
          this.leftData5 = data.lung || {
            depId: this.$route.meta.ksID,
            feffa: undefined,
            feffb: undefined,
            feffc: undefined,
            fev: undefined,
            fevFvc: undefined,
            fvc: undefined,
            id: undefined,
            isDanger: '0',
            isUnchecked: '0',
            mmef: undefined,
            patientcode: this.headParams.patientcode,
            percentageFeffa: undefined,
            percentageFeffb: undefined,
            percentageFeffc: undefined,
            percentageFev: undefined,
            percentageFevFvc: undefined,
            percentageFvc: undefined,
            percentageMmef: undefined,
            predictFeffa: undefined,
            predictFeffb: undefined,
            predictFeffc: undefined,
            predictFev: undefined,
            predictFevFvc: undefined,
            predictFvc: undefined,
            predictMmef: undefined,
            xj: '',
          }
          this.formdata5 = JSON.parse(JSON.stringify(this.leftData5))
          this.tempdata5 = JSON.parse(JSON.stringify(this.leftData5))
          this.rightData5.patient = data.patient
          this.rightData5.picture = data.picture
          this.rightData5.sectionResultMain = data.sectionResultMain ? data.sectionResultMain : { conclusions: '' }
          this.rightData5.sectionResultMain.conclusions = data.sectionResultMain && data.sectionResultMain.conclusions ? data.sectionResultMain.conclusions : ''
          let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
          this.rightData5.sectionResultMain.rummagerName = data.sectionResultMain && data.sectionResultMain.rummager ? data.sectionResultMain.rummager : auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.getCookie('username'))
          this.rightData5.sectionResultMain.rummagerId = data.sectionResultMain && data.sectionResultMain.rummagerId ? data.sectionResultMain.rummagerId : auditInfo ? auditInfo.rummagerId : decodeURIComponent(this.getCookie('userNo'))
          this.rightData5.sectionResultMain.writeName = data.sectionResultMain && data.sectionResultMain.writeName ? data.sectionResultMain.writeName : auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username'))
          this.rightData5.sectionResultMain.writeId = data.sectionResultMain && data.sectionResultMain.writeId ? data.sectionResultMain.writeId : auditInfo ? auditInfo.writeId : decodeURIComponent(this.getCookie('userNo'))
          this.rightData5.sectionResultMain.writeTime = data.sectionResultMain && data.sectionResultMain.writeTime ? data.sectionResultMain.writeTime : this.$getDate()
          this.rightData5.sectionResultMain.rummagerTime = data.sectionResultMain && data.sectionResultMain.rummagerTime ? data.sectionResultMain.rummagerTime : this.$getDate()
          pfLoading.close()
        })
        // 获取结论词
        jlcDataPF({
          ksId: ksID,
          patientCode: patientcode,
        }).then(({ data }) => {
          this.rightData5.jlcData = data
        })
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        //无图像检查、图像检查
        query.ksID = ksID
        query.patientcode = patientcode
        getCase1Data(query)
          .then(({ data, code, msg }) => {
            if (code == 500) {
              // clLoading.close()
              this.$alert(msg.split('@')[1], '提示', {
                confirmButtonText: '确定',
              })
              this.$tab.refreshPage()
              return
            }
            if (data.flag == '该体检号没有本科室收费项目!') {
              this.$alert('该体检号没有本科室收费项目!', '提示', {
                confirmButtonText: '确定',
                type: 'warning',
              })
              // clLoading.close()
              return
            } else if (data.flag == '该体检号尚未缴费!') {
              this.$alert('该体检号尚未缴费!', '提示', {
                confirmButtonText: '确定',
                type: 'warning',
              })
              // clLoading.close()
              return
            }
            let alertMsg = ''
            if (data.flag == '该体检号已被禁检') {
              this.$alert('该体检号已被禁检', '提示', {
                confirmButtonText: '确定',
                type: 'error',
              })
              // clLoading.close()
              return
            } else if (data.flag == 'audit') {
              this.lockDisable = true
              if (data.peispatient.jktjzt == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.doctorfinalNameR == null ? '' : data.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.zytjzt == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.patientnameencoded == null ? '' : data.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.FFinallocked == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.idDoctorapply == null ? '' : data.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
              } else if (data.peispatient.idGuidenurse == 1) {
                alertMsg = '本体检者检查结果已被' + (data.peispatient.parsedassignedsuiteandfi == null ? '' : data.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
              } else {
                alertMsg = '该体检号已审核，不能修改！'
                this.lockDisable = false
              }
              this.mainDisabled = true
            }
            if (alertMsg) {
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.mainDisabled = false
            }
            this.headParams.isVIP = data.isVIP
            if (data && data.peispatient) {
              this.headParams.patientcode = data.peispatient.patientcode ? data.peispatient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.peispatient.patientname ? data.peispatient.patientname : ''
            }
            if (this.clearCode) {
              this.headParams.patientcode = ''
            }
            this.clearCode = false
            if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            this.rightData6 = JSON.parse(JSON.stringify(data))
            this.rightData6.peispatient.isAudit = data.flag == 'audit' ? 1 : 0
            this.rightData6.sectionResultMain = data.sectionResultMain || {
              rummagerId: this.$getCookie('userNo'),
              rummagerName: decodeURIComponent(this.$getCookie('username')),
              rummagerTime: this.$getDate(),
              writeId: this.$getCookie('userNo'),
              writeName: decodeURIComponent(this.getCookie('username')),
              writeTime: this.$getDate(),
              conclusions: '',
            }
            let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
            this.rightData6.sectionResultMain = data.sectionResultMain || {
              rummagerId: auditInfo ? auditInfo.rummagerId : this.$getCookie('userNo'),
              rummagerName: auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.$getCookie('username')),
              rummagerTime: this.$getDate(),
              writeName: auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username')),
              writeId: auditInfo ? auditInfo.writeId : this.$getCookie('userNo'),
              writeTime: this.$getDate(),
              conclusions: '',
            }
            this.rightData6.sectionResultMain.writeName = this.rightData6.sectionResultMain.writeName ? this.rightData6.sectionResultMain.writeName : data.lrr
            this.rightData6.sectionResultMain.writeTime = this.rightData6.sectionResultMain.writeTime ? this.rightData6.sectionResultMain.writeTime : data.lrsj
            let griddata = []
            this.rightData6.griddata.forEach((el) => {
              if (el.jlcId) {
                griddata.push(el)
              }
            })
            this.rightData6.griddata = griddata
            this.headParams.syxb = data.peispatient.idSex
            this.headParams.tjlx = data.peispatient.idExamtype

            // 重组左侧多选格式
            let num = -1
            let title = ''
            let listData = []
            data.nkdata.forEach((el) => {
              if (title != el.jcxmmc) {
                if (el.fentryonly == 'shuru') {
                  listData.push(
                    Object.assign(el, {
                      jcms: '',
                      inputResult: '',
                      qij: 0,
                      wjzjb: '0',
                    })
                  )
                } else {
                  listData.push({
                    sfxmmc: el.sfxmmc,
                    sfxmId: el.sfxmId,
                    tjxmId: el.tjxmId,
                    jcxmmc: el.jcxmmc,
                    checkList: [],
                    // tempCheckList: [],
                    optionList: [el],
                    jcms: '',
                    qij: 0,
                    wjzjb: '0',
                  })
                }
                title = el.jcxmmc
                num++
              } else {
                if (el.fentryonly == 'shuru') {
                  // listData.push(
                  //   Object.assign(el, {
                  //     jcms: el.jcmc || '',
                  //     inputResult: el.jcmc || '',
                  //     qij: 0,
                  //     wjzjb: '0',
                  //   })
                  // )
                } else {
                  listData[num].optionList.push(el)
                }
              }
              if (el.fentryonly == 'gouxuan' && el.mrxz == 'morenxuanzhong') {
                listData[num].checkList.push(el.sname)
                // listData[num].tempCheckList.push(el.sname)
                if (el.jcmc) {
                  listData[num].jcms = el.jcmc
                } else {
                  listData[num].jcms = listData[num].checkList.join('、') + ';'
                  // listData[num].jcms = listData[num].tempCheckList.join('、') + ';'
                }
              }
            })
            this.leftData6 = listData
            //  获取审核后数据
            // if (data.flag == 'audit') {
            getNkCheckedData(query).then(({ data }) => {
              if (data) {
                this.rightData6.sectionResultMain.conclusions = data.xj ? data.xj : ''
                this.rightData6.sectionResultMain.rummagerName = data.jcrName ? data.jcrName : this.rightData6.sectionResultMain.rummagerName
                this.rightData6.sectionResultMain.rummagerTime = data.jcsj ? data.jcsj : this.rightData6.sectionResultMain.rummagerTime
                this.rightData6.sectionResultMain.writeName = data.lrrName ? data.lrrName : this.rightData6.sectionResultMain.writeName
                this.rightData6.sectionResultMain.writeTime = data.lrsj ? data.lrsj : this.rightData6.sectionResultMain.writeTime
                if (!this.mainDisabled) {
                  this.rightData6.sectionResultMain.writeName = decodeURIComponent(this.getCookie('username'))
                  this.rightData6.sectionResultMain.writeId = decodeURIComponent(this.getCookie('userNo'))
                }
                this.rightData6 = JSON.parse(JSON.stringify(this.rightData6))
                // this.$nextTick(() => {
                data.data.forEach((el) => {
                  this.leftData6.forEach((val) => {
                    if (el.inputResult || val.fentryonly == 'shuru') {
                      if (el.jxcmmc == val.jcxmmc) {
                        val.qij = el.qij
                        val.wjzjb = el.wjzjb
                        val.jcms = el.ms
                        val.inputResult = el.inputResult
                      }
                    } else {
                      if (el.jxcmmc == val.jcxmmc) {
                        val.qij = el.qij
                        val.wjzjb = el.wjzjb
                        val.jcms = el.ms
                        let multiple = 0
                        val.optionList.forEach((val2) => {
                          if (val2.fentryonly == 'gouxuan' && val2.mrxz == 'morenxuanzhong') {
                            multiple++
                          }
                        })
                        if (val.optionList.length && val.checkList[0] == val.optionList[0].sname && multiple == 1) {
                          val.checkList = [el.tzcmc]
                          // val.tempCheckList = [el.tzcmc]
                        } else {
                          val.checkList.push(el.tzcmc)
                          // val.tempCheckList.push(el.tzcmc)
                          // val.jcms = val.checkList.join('、') + ';'
                        }
                      }
                    }
                  })
                })
                // })
              }
            })
            // }
            // clLoading.close()
            this.showPicSection = true
          })
          .catch((error) => {
            console.error(error)
            // clLoading.close()
          })
      } else if (this.$route.meta.dataType == '8') {
        // 问诊检查
        if (this.readDebounce8) {
          return
        }
        this.readDebounce8 = true
        query.ksID = ksID
        query.patientcode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getInquiryData(query)
          .then(({ data }) => {
            if (data.flag) {
              clLoading.close()
              this.$tab.refreshPage()
              this.$alert(data.flag, '提示', {
                confirmButtonText: '确定',
              })
              this.readDebounce8 = false
              return
            }
            this.headParams.isVIP = data.isVIP
            if (data && data.peispatient) {
              this.headParams.patientcode = data.peispatient.patientcode ? data.peispatient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.peispatient.patientname ? data.peispatient.patientname : ''
            }
            if (this.clearCode) {
              this.headParams.patientcode = ''
            }
            this.clearCode = false
            this.headParams.id = data.peispatient.id
            if (data.peispatientConsultation && data.peispatientConsultation.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            this.leftData8 = data
            this.leftData8.ksID = ksID
            this.rightData8 = {
              jcr: data.jcr,
              jcrxm: data.jcrxm,
              jcsj: data.jcsj,
              lrrId: data.lrrId,
              lrr: data.lrr,
              lrsj: data.lrsj,
            }
            let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
            this.rightData8.lrr = data.lrr ? data.lrr : auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username'))
            this.rightData8.lrrId = data.lrrId ? data.lrrId : auditInfo ? auditInfo.writeId : decodeURIComponent(this.getCookie('userNo'))
            this.rightData8.lrsj = data.lrsj ? data.lrsj : this.$getDate()
            this.rightData8.jcsj = data.jcsj ? data.jcsj : this.$getDate()
            this.rightData8.jcrxm = data.jcrxm ? data.jcrxm : auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.getCookie('username'))
            this.rightData8.jcr = data.jcr ? data.jcr : auditInfo ? auditInfo.rummagerId : this.getCookie('userNo')
            var idExamType = data.peispatient.idExamtype
            // 判断是否显示健康/职业报告按钮
            if (idExamType == '0') {
              this.headParams.diagnosticReportD = false
              this.headParams.diagnosticReportH = true
            } else if (idExamType == '1') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = false
            } else if (idExamType == '2') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = true
            }
            if (data.peispatientConsultation) {
              if (data.peispatientConsultation.noKissTheCup == '1') {
                this.leftData8.peispatientConsultation.kiss = '0'
              } else if (data.peispatientConsultation.betweenKissTheCup == '1') {
                this.leftData8.peispatientConsultation.kiss = '1'
              } else if (data.peispatientConsultation.abstainLostKiss == '1') {
                this.leftData8.peispatientConsultation.kiss = '2'
              } else if (data.peispatientConsultation.evermoreKiss == '1') {
                this.leftData8.peispatientConsultation.kiss = '3'
              }
              this.leftData8.peispatientConsultation.ccnl = this.leftData8.peispatientConsultation.ccnl || undefined
              this.leftData8.peispatientConsultation.jq = this.leftData8.peispatientConsultation.jq || undefined
              this.leftData8.peispatientConsultation.zq = this.leftData8.peispatientConsultation.zq || undefined
              this.leftData8.peispatientConsultation.tjnl = this.leftData8.peispatientConsultation.tjnl || undefined
              this.leftData8.peispatientConsultation.familyNumber = this.leftData8.peispatientConsultation.familyNumber || undefined
              this.leftData8.peispatientConsultation.lc = this.leftData8.peispatientConsultation.lc || undefined
              this.leftData8.peispatientConsultation.zc = this.leftData8.peispatientConsultation.zc || undefined
              this.leftData8.peispatientConsultation.sc = this.leftData8.peispatientConsultation.sc || undefined
              this.leftData8.peispatientConsultation.ywrc = this.leftData8.peispatientConsultation.ywrc || undefined
              this.leftData8.peispatientConsultation.jt = this.leftData8.peispatientConsultation.jt || undefined
            } else {
              this.leftData8.peispatientConsultation = {
                everOfDisease: undefined,
                everOfDiseaseRemark: undefined,
                ccnl: undefined,
                jq: undefined,
                zq: undefined,
                tjnl: undefined,
                familyNumber: undefined,
                lc: undefined,
                zc: undefined,
                sc: undefined,
                ywrc: undefined,
                jt: undefined,
                abstainSmokeNote: undefined,
                everydaySmokeN: undefined,
                smokeYear: undefined,
                kiss: undefined,
                kissAmount: undefined,
                kissYearN: undefined,
                kissType: undefined,
                familyOfDisease: undefined,
                symptom: undefined,
              }
            }
            this.readDebounce8 = false
            clLoading.close()
            let alertMsg = ''
            if (data.peispatient.zytjzt == '1') {
              alertMsg = `本体检者检查结果已被${data.peispatient.patientnameencoded}完成总检，不能修改！如有未检项目也不再进行。`
              this.lockDisable = true
            } else if (data.peispatient.idGuidenurse == '1') {
              alertMsg = `本体检者检查结果已被${data.peispatient.parsedassignedsuiteandfi}锁定，不能修改！如有未检项目也不再进行。`
              this.lockDisable = true
            } else if (data.peispatientConsultation && data.peispatientConsultation.isAudit == '1') {
              alertMsg = '该体检号已审核，不能修改！'
              this.lockDisable = false
            }
            if (alertMsg) {
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.mainDisabled = false
            }
          })
          .catch((err) => {
            console.error(err)
            this.readDebounce8 = false
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '9') {
        //电测听检查
        query.ksId = ksID
        query.patientCode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        getAudiometryData(query)
          .then((res) => {
            if (res.code === 500) {
              clLoading.close()
              this.$tab.refreshPage()
              this.$alert(res.msg.split('error@')[1], '提示', {
                confirmButtonText: '确定',
              })
              return
            }
            var data = res.data
            this.headParams.isVIP = data.isVIP
            if (data && data.patient) {
              this.headParams.patientcode = data.patient.patientcode ? data.patient.patientcode : this.headParams.patientcode
              this.headParams.patientname = data.patient.patientname ? data.patient.patientname : ''
            }
            if (this.clearCode) {
              this.headParams.patientcode = ''
            }
            this.clearCode = false
            if (data.main && data.main.isAudit == 1) {
              this.headParams.isAudit = true
            } else {
              this.headParams.isAudit = false
            }
            this.leftData9 = {
              picture: data.picture,
              patient: data.patient,
            }
            this.rightData9 = {
              audiometry: data.audiometry,
              main: data.main,
              patient: data.patient,
            }
            if (!this.rightData9.audiometry) {
              var formdata = {
                airLeft125: null,
                airLeft250: null,
                airLeft500: null,
                airLeft1000: null,
                airLeft2000: null,
                airLeft3000: null,
                airLeft4000: null,
                airLeft6000: null,
                airLeft8000: null,
                airRight125: null,
                airRight250: null,
                airRight500: null,
                airRight1000: null,
                airRight2000: null,
                airRight3000: null,
                airRight4000: null,
                airRight6000: null,
                airRight8000: null,
                boneLeft250: null,
                boneLeft500: null,
                boneLeft1000: null,
                boneLeft2000: null,
                boneLeft3000: null,
                boneLeft4000: null,
                boneLeft6000: null,
                boneRight250: null,
                boneRight500: null,
                boneRight1000: null,
                boneRight2000: null,
                boneRight3000: null,
                boneRight4000: null,
                boneRight6000: null,
                describe: undefined,
                testResult: undefined,
                patientcode: data.patient.patientcode,
                idExamtype: data.patient.idExamtype,
                depId: this.ksID,
              }
              this.$set(this.rightData9, 'audiometry', formdata)
            } else {
              this.rightData9.audiometry.airLeft125 = this.rightData9.audiometry.airLeft125 ? this.rightData9.audiometry.airLeft125 : this.rightData9.audiometry.airLeft125 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft250 = this.rightData9.audiometry.airLeft250 ? this.rightData9.audiometry.airLeft250 : this.rightData9.audiometry.airLeft250 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft500 = this.rightData9.audiometry.airLeft500 ? this.rightData9.audiometry.airLeft500 : this.rightData9.audiometry.airLeft500 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft1000 = this.rightData9.audiometry.airLeft1000 ? this.rightData9.audiometry.airLeft1000 : this.rightData9.audiometry.airLeft1000 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft2000 = this.rightData9.audiometry.airLeft2000 ? this.rightData9.audiometry.airLeft2000 : this.rightData9.audiometry.airLeft2000 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft3000 = this.rightData9.audiometry.airLeft3000 ? this.rightData9.audiometry.airLeft3000 : this.rightData9.audiometry.airLeft3000 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft4000 = this.rightData9.audiometry.airLeft4000 ? this.rightData9.audiometry.airLeft4000 : this.rightData9.audiometry.airLeft4000 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft6000 = this.rightData9.audiometry.airLeft6000 ? this.rightData9.audiometry.airLeft6000 : this.rightData9.audiometry.airLeft6000 === 0 ? 0 : null
              this.rightData9.audiometry.airLeft8000 = this.rightData9.audiometry.airLeft8000 ? this.rightData9.audiometry.airLeft8000 : this.rightData9.audiometry.airLeft8000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight125 = this.rightData9.audiometry.airRight125 ? this.rightData9.audiometry.airRight125 : this.rightData9.audiometry.airRight125 === 0 ? 0 : null
              this.rightData9.audiometry.airRight250 = this.rightData9.audiometry.airRight250 ? this.rightData9.audiometry.airRight250 : this.rightData9.audiometry.airRight250 === 0 ? 0 : null
              this.rightData9.audiometry.airRight500 = this.rightData9.audiometry.airRight500 ? this.rightData9.audiometry.airRight500 : this.rightData9.audiometry.airRight500 === 0 ? 0 : null
              this.rightData9.audiometry.airRight1000 = this.rightData9.audiometry.airRight1000 ? this.rightData9.audiometry.airRight1000 : this.rightData9.audiometry.airRight1000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight2000 = this.rightData9.audiometry.airRight2000 ? this.rightData9.audiometry.airRight2000 : this.rightData9.audiometry.airRight2000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight3000 = this.rightData9.audiometry.airRight3000 ? this.rightData9.audiometry.airRight3000 : this.rightData9.audiometry.airRight3000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight4000 = this.rightData9.audiometry.airRight4000 ? this.rightData9.audiometry.airRight4000 : this.rightData9.audiometry.airRight4000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight6000 = this.rightData9.audiometry.airRight6000 ? this.rightData9.audiometry.airRight6000 : this.rightData9.audiometry.airRight6000 === 0 ? 0 : null
              this.rightData9.audiometry.airRight8000 = this.rightData9.audiometry.airRight8000 ? this.rightData9.audiometry.airRight8000 : this.rightData9.audiometry.airRight8000 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft250 = this.rightData9.audiometry.boneLeft250 ? this.rightData9.audiometry.boneLeft250 : this.rightData9.audiometry.boneLeft250 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft500 = this.rightData9.audiometry.boneLeft500 ? this.rightData9.audiometry.boneLeft500 : this.rightData9.audiometry.boneLeft500 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft1000 = this.rightData9.audiometry.boneLeft1000 ? this.rightData9.audiometry.boneLeft1000 : this.rightData9.audiometry.boneLeft1000 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft2000 = this.rightData9.audiometry.boneLeft2000 ? this.rightData9.audiometry.boneLeft2000 : this.rightData9.audiometry.boneLeft2000 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft3000 = this.rightData9.audiometry.boneLeft3000 ? this.rightData9.audiometry.boneLeft3000 : this.rightData9.audiometry.boneLeft3000 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft4000 = this.rightData9.audiometry.boneLeft4000 ? this.rightData9.audiometry.boneLeft4000 : this.rightData9.audiometry.boneLeft4000 === 0 ? 0 : null
              this.rightData9.audiometry.boneLeft6000 = this.rightData9.audiometry.boneLeft6000 ? this.rightData9.audiometry.boneLeft6000 : this.rightData9.audiometry.boneLeft6000 === 0 ? 0 : null
              // this.rightData9.audiometry.boneLeft8000 = this.rightData9.audiometry.boneLeft8000 ? this.rightData9.audiometry.boneLeft8000 : this.rightData9.audiometry.boneLeft8000 === 0 ? 0 : null
              delete this.rightData9.audiometry.boneLeft8000
              this.rightData9.audiometry.boneRight250 = this.rightData9.audiometry.boneRight250 ? this.rightData9.audiometry.boneRight250 : this.rightData9.audiometry.boneRight250 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight500 = this.rightData9.audiometry.boneRight500 ? this.rightData9.audiometry.boneRight500 : this.rightData9.audiometry.boneRight500 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight1000 = this.rightData9.audiometry.boneRight1000 ? this.rightData9.audiometry.boneRight1000 : this.rightData9.audiometry.boneRight1000 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight2000 = this.rightData9.audiometry.boneRight2000 ? this.rightData9.audiometry.boneRight2000 : this.rightData9.audiometry.boneRight2000 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight3000 = this.rightData9.audiometry.boneRight3000 ? this.rightData9.audiometry.boneRight3000 : this.rightData9.audiometry.boneRight3000 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight4000 = this.rightData9.audiometry.boneRight4000 ? this.rightData9.audiometry.boneRight4000 : this.rightData9.audiometry.boneRight4000 === 0 ? 0 : null
              this.rightData9.audiometry.boneRight6000 = this.rightData9.audiometry.boneRight6000 ? this.rightData9.audiometry.boneRight6000 : this.rightData9.audiometry.boneRight6000 === 0 ? 0 : null
              // this.rightData9.audiometry.boneRight8000 = this.rightData9.audiometry.boneRight8000 ? this.rightData9.audiometry.boneRight8000 : this.rightData9.audiometry.boneRight8000 === 0 ? 0 : null
              delete this.rightData9.audiometry.boneRight8000
            }
            if (!this.rightData9.main) {
              var main = {
                conclusions: undefined,
                rummager: undefined,
                rummagerId: undefined,
                rummagerTime: undefined,
                writeName: undefined,
                writeTime: undefined,
              }
              this.$set(this.rightData9, 'main', main)
            }
            let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
            this.rightData9.main.writeName = data.main && data.main.writeName ? data.main.writeName : auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username'))
            this.rightData9.main.writeId = data.main && data.main.writeId ? data.main.writeId : auditInfo ? auditInfo.writeId : decodeURIComponent(this.getCookie('userNo'))
            this.rightData9.main.writeTime = data.main && data.main.writeTime ? data.main.writeTime : this.$getDate()
            this.rightData9.main.rummagerTime = data.main && data.main.rummagerTime ? data.main.rummagerTime : this.$getDate()
            this.rightData9.main.rummager = data.main && data.main.rummager ? data.main.rummager : auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.getCookie('username'))
            this.rightData9.main.rummagerId = data.main && data.main.rummagerId ? data.main.rummagerId : auditInfo ? auditInfo.rummagerId : this.getCookie('userNo')
            var idExamType = data.patient.idExamtype
            // 判断是否显示健康/职业报告按钮
            if (idExamType == '0') {
              this.headParams.diagnosticReportD = false
              this.headParams.diagnosticReportH = true
            } else if (idExamType == '1') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = false
            } else if (idExamType == '2') {
              this.headParams.diagnosticReportD = true
              this.headParams.diagnosticReportH = true
            }
            clLoading.close()
            let alertMsg = ''
            if (data.patient.jktjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.doctorfinalNameR == null ? '' : data.patient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
              this.lockDisable = true
            } else if (data.patient.zytjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.patientnameencoded == null ? '' : data.patient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
              this.lockDisable = true
            } else if (data.patient.FFinallocked == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.idDoctorapply == null ? '' : data.patient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
              this.lockDisable = true
            } else if (data.patient.idGuidenurse == 1) {
              alertMsg = '本体检者检查结果已被' + (data.patient.parsedassignedsuiteandfi == null ? '' : data.patient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
              this.lockDisable = true
            } else if (data.main && data.main.isAudit == 1) {
              alertMsg = '该体检号已审核，不能修改！'
              this.lockDisable = false
            }
            this.mainDisabled = true
            if (alertMsg) {
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
              return
            } else {
              this.mainDisabled = false
            }
          })
          .catch((error) => {
            console.error(error)
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '10') {
        //骨密度
        this.mainDisabled = true
        query.ksId = ksID
        query.patientcode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        bmdGmd(query)
          .then((res) => {
            let alertMsg = ''
            if (res.data.flag == '该体检号已被禁检!' || res.data.flag == '该体检号没有本科室收费项目!') {
              this.$alert(res.data.flag, '提示', {
                confirmButtonText: '确定',
                type: 'warning',
              })
              clLoading.close()
              return
            }
            if (res.data.flag == 'audit') {
              if (res.data.peispatient.jktjzt == 1) {
                alertMsg = '本体检者检查结果已被' + (res.data.peispatient.doctorfinalNameR == null ? '' : res.data.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
              } else if (res.data.peispatient.zytjzt == '1') {
                alertMsg = '本体检者检查结果已被' + (res.data.peispatient.patientnameencoded == null ? '' : res.data.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
              } else if (res.data.peispatient.FFinallocked == '1') {
                alertMsg = '本体检者检查结果已被' + (res.data.peispatient.idDoctorapply == null ? '' : res.data.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
              } else if (res.data.peispatient.idGuidenurse == '1') {
                alertMsg = '本体检者检查结果已被' + (res.data.peispatient.parsedassignedsuiteandfi == null ? '' : res.data.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
              } else {
                alertMsg = '该体检号已审核，不能修改！'
              }
            }
            if (alertMsg) {
              this.headParams.isAudit = true
              if (showDia) {
                this.$alert(alertMsg, '提示')
              }
            } else {
              this.headParams.isAudit = false
              this.mainDisabled = false
            }
            this.headParams.patientcode = res.data.peispatient.patientcode
            this.headParams.patientname = res.data.peispatient.patientname
            if (this.clearCode) {
              this.headParams.patientcode = ''
            }
            this.clearCode = false
            this.rightData10 = res.data
            this.leftData10 = res.data
            this.rightData10.sectionResultMain = this.rightData10.peispatient
            this.rightData10.sectionResultMain.conclusions = this.rightData10.sectionResultMain && this.rightData10.sectionResultMain.conclusions ? this.rightData10.sectionResultMain.conclusions : ''
            // this.rightData10.sectionResultMain.writeName = res.data.lrr ? res.data.lrr : decodeURIComponent(this.getCookie('username'))
            // this.rightData10.sectionResultMain.writeTime = res.data.lrsj ? res.data.lrsj : this.$getDate()
            // // if (!this.mainDisabled) {
            // //   this.rightData10.sectionResultMain.writeName = decodeURIComponent(this.getCookie('username'))
            // //   this.rightData10.sectionResultMain.writeId = decodeURIComponent(this.getCookie('userNo'))
            // // }
            this.$refs.gmd.getDetailGmd(ksID, patientcode)
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '11') {
        //视力检查
        this.mainDisabled = true
        query.ksID = ksID
        query.patientcode = patientcode
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        visionSljc(query).then((res) => {
          if (res.data.flag == '该体检号已被禁检!') {
            this.$alert('该体检号已被禁检!', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
            clLoading.close()
            return
          } else if (res.data.flag == '该体检号没有本科室收费项目!') {
            this.$alert('该体检号没有本科室收费项目!', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
            clLoading.close()
            return
          }
          let alertMsg = ''
          if (res.data.flag == 'audit') {
            if (res.data.peispatient.jktjzt == 1) {
              alertMsg = '本体检者检查结果已被' + (res.data.peispatient.doctorfinalNameR == null ? '' : res.data.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
            } else if (res.data.peispatient.zytjzt == '1') {
              alertMsg = '本体检者检查结果已被' + (res.data.peispatient.patientnameencoded == null ? '' : res.data.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
            } else if (res.data.peispatient.FFinallocked == '1') {
              alertMsg = '本体检者检查结果已被' + (res.data.peispatient.idDoctorapply == null ? '' : res.data.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
            } else if (res.data.peispatient.idGuidenurse == '1') {
              alertMsg = '本体检者检查结果已被' + (res.data.peispatient.parsedassignedsuiteandfi == null ? '' : res.data.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
            } else {
              alertMsg = '该体检号已审核，不能修改！'
            }
          }
          if (alertMsg) {
            this.headParams.isAudit = true
            if (showDia) {
              this.$alert(alertMsg, '提示')
            }
          } else {
            this.headParams.isAudit = false
            this.mainDisabled = false
          }
          this.rightData11 = res.data
          this.headParams.patientcode = res.data.peispatient.patientcode
          this.headParams.patientname = res.data.peispatient.patientname
          if (this.clearCode) {
            this.headParams.patientcode = ''
          }
          this.clearCode = false
          this.rightData11.patient = res.data.peispatient
          this.rightData11.jlcData = []
          res.data.griddata.forEach((el) => {
            if (el.jlcId) {
              this.rightData11.jlcData.push(el)
            }
          })
          this.rightData11.sectionResultMain = this.rightData11.sectionResultMain
            ? this.rightData11.sectionResultMain
            : {
              rummagerName: decodeURIComponent(this.getCookie('username')),
              rummagerId: this.getCookie('userNo'),
              rummagerTime: this.$getDate(),
              writeName: decodeURIComponent(this.getCookie('username')),
              writeId: this.getCookie('userNo'),
              writeTime: this.$getDate(),
            }
          this.rightData11.sectionResultMain.isAudit = this.rightData11.sectionResultMain && this.rightData11.sectionResultMain.isAudit == '1' ? this.rightData11.sectionResultMain.isAudit : ''
          if (this.rightData11.sectionResultMain) {
            this.rightData11.sectionResultMain.rummagerId = this.rightData11.sectionResultMain.rummagerId ? this.rightData11.sectionResultMain.rummagerId : this.$getCookie('userNo')
            this.rightData11.sectionResultMain.rummagerName = this.rightData11.sectionResultMain.rummagerName ? this.rightData11.sectionResultMain.rummagerName : decodeURIComponent(this.$getCookie('username'))
            this.rightData11.sectionResultMain.rummagerTime = this.rightData11.sectionResultMain.rummagerTime ? this.rightData11.sectionResultMain.rummagerTime : this.$getDate()
            this.rightData11.sectionResultMain.writeId = this.rightData11.sectionResultMain.writeId ? this.rightData11.sectionResultMain.writeId : this.$getCookie('userNo')
            this.rightData11.sectionResultMain.writeName = this.rightData11.sectionResultMain && this.rightData11.sectionResultMain.writeName ? this.rightData11.sectionResultMain.writeName : decodeURIComponent(this.getCookie('username'))
            this.rightData11.sectionResultMain.writeTime = this.rightData11.sectionResultMain && this.rightData11.sectionResultMain.writeTime ? this.rightData11.sectionResultMain.writeTime : this.$getDate()
          }
          // var sldataCheck = []
          // for (var i in res.data.sldata) {
          //   // 252: 视力检查职业 256: 辨色力职业
          //   if (res.data.sldata[i].fentryonly == 'gouxuan') {
          //     if (res.data.sldata[i].sname) {
          //       sldataCheck.push(res.data.sldata[i])
          //     }
          //   } else if (res.data.sldata[i].fentryonly == 'shuru') {
          //     sldataCheck.push(res.data.sldata[i])
          //   }
          // }
          // 重组左侧多选格式
          let num = -1
          let title = ''
          let listData = []
          res.data.sldata.forEach((el) => {
            if (title != el.jcxmmc) {
              if (el.fentryonly == 'shuru') {
                listData.push(
                  Object.assign(el, {
                    jcms: '',
                    inputResult: '',
                  })
                )
              } else {
                listData.push({
                  sfxmmc: el.sfxmmc,
                  sfxmId: el.sfxmId,
                  tjxmId: el.tjxmId,
                  jcxmmc: el.jcxmmc,
                  checkList: [],
                  optionList: [el],
                  jcms: '',
                  qij: 0,
                  wjzjb: '0',
                })
              }
              title = el.jcxmmc
              num++
            } else {
              if (el.fentryonly == 'shuru') {
              } else {
                listData[num].optionList.push(el)
              }
            }
            if (el.fentryonly == 'gouxuan' && el.mrxz == 'morenxuanzhong') {
              listData[num].checkList.push(el.sname)
              if (el.jcmc) {
                listData[num].jcms = el.jcmc
              } else {
                listData[num].jcms = listData[num].checkList.join('、') + ';'
              }
            }
          })
          this.leftData11 = listData
          this.leftData11 = JSON.parse(JSON.stringify(this.leftData11))
          // if (res.data.flag == 'audit') {
          //获取审核后数据
          getNkCheckedData(query).then(({ data }) => {
            if (!data) return
            data.data.forEach((el) => {
              // this.rightData.sectionResultMain = {};
              let firstClear = false
              this.leftData11.forEach((val) => {
                if (el.jxcmmc == val.jcxmmc) {
                  if (val.optionList && !firstClear) {
                    val.checkList = [el.tzcmc]
                    val.jcms = el.ms
                    firstClear = true
                  } else if (val.optionList && val.optionList.length) {
                    val.checkList.push(el.tzcmc)
                  } else {
                    val.inputResult = el.inputResult
                  }
                }
              })
            })
            this.rightData11.sectionResultMain = {
              rummagerId: data.jcr,
              rummagerName: data.jcrName,
              rummagerTime: data.jcsj,
              lrr: data.lrr,
              writeName: data.lrrName,
              writeTime: data.lrsj,
              sfsh: data.sfsh,
              conclusions: data.xj,
            }
            if (this.rightData11.sectionResultMain) {
              this.rightData11.sectionResultMain.rummagerId = this.rightData11.sectionResultMain.rummagerId ? this.rightData11.sectionResultMain.rummagerId : this.$getCookie('userNo')
              this.rightData11.sectionResultMain.rummagerName = this.rightData11.sectionResultMain.rummagerName ? this.rightData11.sectionResultMain.rummagerName : decodeURIComponent(this.$getCookie('username'))
              this.rightData11.sectionResultMain.rummagerTime = this.rightData11.sectionResultMain.rummagerTime ? this.rightData11.sectionResultMain.rummagerTime : this.$getDate()
              this.rightData11.sectionResultMain.writeId = this.rightData11.sectionResultMain.writeId ? this.rightData11.sectionResultMain.writeId : this.$getCookie('userNo')
              this.rightData11.sectionResultMain.writeName = this.rightData11.sectionResultMain && this.rightData11.sectionResultMain.writeName ? this.rightData11.sectionResultMain.writeName : decodeURIComponent(this.getCookie('username'))
              this.rightData11.sectionResultMain.writeTime = this.rightData11.sectionResultMain && this.rightData11.sectionResultMain.writeTime ? this.rightData11.sectionResultMain.writeTime : this.$getDate()
            }
            if (!this.mainDisabled) {
              this.rightData11.sectionResultMain.writeName = decodeURIComponent(this.getCookie('username'))
              this.rightData11.sectionResultMain.writeId = decodeURIComponent(this.getCookie('userNo'))
            }
            this.$refs.colorDiscrimination.setQueryParams()
          })
          // }
          clLoading.close()
        })
        // .catch((error) => {
        // console.error(error);
        //   clLoading.close()
        // })
      } else if (this.$route.meta.dataType == '12') {
        //健康问卷
      }
    },
    // 保存结论词后软更新,只更新后端所需数据
    getSectionDataOnly(patientCode, ksId) {
      if (this.forbiddenSave) {
        return
      }
      this.forbiddenSave = true
      const clLoading = this.$loading({
        lock: true,
        text: '操作中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.1)',
      })
      getGIData({
        patientCode,
        ksId,
      })
        .then(({ data, code, msg }) => {
          if (code == 500) {
            clLoading.close()
            this.$alert(msg.split('@')[1], '提示', {
              confirmButtonText: '确定',
            })
            this.$tab.refreshPage()
            return
          }
          this.headParams.isVIP = data.isVIP
          if (data && data.patient) {
            this.headParams.patientcode = data.patient.patientcode ? data.patient.patientcode : this.headParams.patientcode
            this.headParams.patientname = data.patient.patientname ? data.patient.patientname : ''
          }
          let alertMsg = ''
          if (data.patient.jktjzt == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.doctorfinalNameR == null ? '' : data.patient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.zytjzt == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.patientnameencoded == null ? '' : data.patient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.ffinallocked == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.idDoctorapply == null ? '' : data.patient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
          } else if (data.patient.idGuidenurse == 1) {
            alertMsg = '本体检者检查结果已被' + (data.patient.parsedassignedsuiteandfi == null ? '' : data.patient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
          } else if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
            alertMsg = '该体检号已审核，不能修改！'
          }
          if (alertMsg) {
            this.mainDisabled = true
            this.$alert(alertMsg, '提示')
          } else {
            this.mainDisabled = false
          }
          if (data.sectionResultMain && data.sectionResultMain.isAudit == 1) {
            this.headParams.isAudit = true
          } else {
            this.headParams.isAudit = false
          }
          this.rightData3.sectionResultMain.id = data.sectionResultMain.id
          this.forbiddenSave = false
          clLoading.close()
        })
        .catch((error) => {
          console.error(error)
          this.forbiddenSave = false
          clLoading.close()
        })
    },
    // 检验科从Lis获取数据
    handleLisData() {
      if (!this.clData.peispatient.patientcode) {
        this.$alert('请查询体检号后再试', '提示')
      } else {
        this.$confirm('确定要获取体检号:' + this.clData.peispatient.patientcode + ' 的检查结果吗?', '提示')
          .then(() => {
            /* const clLoading = this.$loading({
              lock: true,
              text: '操作中',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.1)',
            })
            */
            receiveApi({ patientcode: this.headParams.patientcode })
              .then(() => {
                // clLoading.close()
                this.getSectionData(this.headParams.patientcode, this.ksID)
                this.$message({
                  type: 'success',
                  message: '获取成功!',
                })
              })
              .catch((err) => {
                console.error(err)
                // clLoading.close()
              })
          })
          .catch(() => { })
      }
    },
    // 肺功能串口数据 青岛市中心
    async handlePort() {
      if ('serial' in navigator) {
        // 提示用户选择一个串口。
        const ports = await navigator.serial.requestPort()
        if (this.port === null) {
          if (ports.length > 0) {
            // 已经连接过
            this.port = ports[0]
            this.openPort()
            return
          }
          // 提示用户选择一个串口
          this.port = await navigator.serial.requestPort()
          this.openPort()
        }
      } else {
        alert('你的浏览器不支持串口连接')
      }
    },
    // 青岛市中心打开串口
    async openPort() {
      // 打开串口
      // 等待串口打开

      // 如果是淮南
      if (Cookies.get('cid') == '402848e36b551aab016b5df90c8412e8') {
        // 淮南波特率9600
        await this.port.open({
          baudRate: 9600, // 波特率
          dataBits: 8, // 每帧的数据位数(7或8)
          stopBits: 1, // 停止位数(1或2)
          parity: 'none', // 校验模式，可以是none，偶数，奇数
          flowControl: 'none', // 流控模式(none或hardware)。
        })
        this.$modal.msgSuccess('串口连接成功')
      } else {
        await this.port.open({
          baudRate: 38400, // 波特率
          dataBits: 8, // 每帧的数据位数(7或8)
          stopBits: 1, // 停止位数(1或2)
          parity: 'none', // 校验模式，可以是none，偶数，奇数
          flowControl: 'none', // 流控模式(none或hardware)。
        })
        this.$modal.msgSuccess('串口连接成功')

        if (this.port) {
          await this.getWeight()
        }
      }
    },
    // 青岛市中心接收数据
    async getWeight() {
      const _this = this
      this.keepReading = true
      if (this.port) {
        this.reader = this.port.readable.getReader()
        // this.writer = this.port.writable.getWriter()
        // this.writeInt = setInterval(async () => {
        //   const data = new Uint8Array([2, 111, 111, 111, 111, 111, 13])
        //   await this.writer.write(data)
        // }, 200)
        let str = []
        var weight = ''
        let timeout = undefined

        while (_this.port.readable && this.keepReading) {
          try {
            while (true) {

              const { value, done } = await this.reader.read()
              if (done) {
                this.reader.releaseLock()
                break
              }
              str = [...str, ...value]

              weight += _this.Uint8ArrayToString(value.reverse())
              if (timeout) clearTimeout(timeout)
              timeout = setTimeout(() => {
                if (!this.leftData5.patientcode) {
                  weight = ''
                  str = []
                  this.$alert('请先读卡', '提示').then(() => {
                    // this.$tab.refreshPage()
                    this.port.close()
                    this.getWeight()
                  })
                  return
                }
                uploadLungIm({ data: weight })
                  .then(({ data }) => {
                    this.leftData5 = { ...this.leftData5, ...data }
                    this.$refs.pulmonaryFunction.excuteCalc()
                    this.$modal.msgSuccess('数据获取成功')
                    weight = ''
                    str = []
                  })
                  .catch(() => {
                    this.$modal.msgError('数据获取失败')
                    weight = ''
                    str = []
                  })
              }, 1000)
            }
          } catch (error) {
            // console.error(error)
          } finally {
            // 允许稍后关闭串口。
            this.reader.releaseLock()
            // this.writer.releaseLock()
          }
        }

        // clearInterval(this.writeInt)
        await this.port.close()
        this.keepReading = false
      }
    },
    // 淮南开始检查
    async handleStart(params) {
      const { patientcode, section, age, sex, sg, tz } = params;

      // 校验输入参数
      if (!patientcode) {
        this.$alert('请输入体检号', '提示', { confirmButtonText: '确定' });
        return;
      }
      if (!section) {
        this.$alert('请选择科室', '提示', { confirmButtonText: '确定' });
        return;
      }
      if (!sg) {
        this.$alert('请输入身高', '提示', { confirmButtonText: '确定' });
        return;
      }
      if (!tz) {
        this.$alert('请输入体重', '提示', { confirmButtonText: '确定' });
        return;
      }

      try {
        // 确保串口已连接
        if (!this.port || !this.port.readable) {
          await this.handlePort();
          if (!this.port) {
            this.$modal.msgError('串口未连接，请先连接串口');
            return;
          }
        }

        // 构造发送数据帧
        const formattedPatientCode = patientcode; // 直接使用原始体检号，无需额外格式化
        const formattedAge = String(age).padStart(4, '0'); // 年龄补足4位
        const formattedSex = sex == 0 ? '1' : '2'; // 性别：0 -> '1' (男), 1 -> '2' (女)
        const formattedHeight = String(sg).padStart(4, '0'); // 身高补足4位
        const formattedWeight = String(tz).padStart(4, '0'); // 体重补足4位

        // 按照新格式构造数据帧
        const dataToSend = `${formattedPatientCode}___${formattedAge}${formattedSex}${formattedHeight}${formattedWeight}`;
        // 发送数据
        const writer = this.port.writable.getWriter();
        const encoder = new TextEncoder();
        const dataArray = encoder.encode(dataToSend);
        await writer.write(dataArray);
        writer.releaseLock();
        this.$modal.msgSuccess('参数发送成功');
        // 接收设备响应
        await this.getWeightHN()

      } catch (error) {
        console.error('串口通信失败:', error);
        // this.$modal.msgError('串口通信失败');
      }
    },
    // 接收数据 淮南专用
    async getWeightHN() {
      const _this = this
      this.keepReading = true
      if (this.port) {
        this.reader = this.port.readable.getReader()
        // this.writer = this.port.writable.getWriter()
        // this.writeInt = setInterval(async () => {
        //   const data = new Uint8Array([2, 111, 111, 111, 111, 111, 13])
        //   await this.writer.write(data)
        // }, 200)
        let str = []
        var weight = ''
        let timeout = undefined

        while (_this.port.readable && this.keepReading) {
          try {
            while (true) {

              const { value, done } = await this.reader.read()
              if (done) {
                this.reader.releaseLock()
                break
              }
              str = [...str, ...value]

              weight += _this.Uint8ArrayToString(value.reverse())
              if (timeout) clearTimeout(timeout)
              timeout = setTimeout(() => {
                if (!this.leftData5.patientcode) {
                  weight = ''
                  str = []
                  this.$alert('请先读卡', '提示').then(() => {
                    // this.$tab.refreshPage()
                    this.port.close()
                    this.getWeightHN()
                  })
                  return
                }
                uploadLungImHN(weight)
                  .then(({ data }) => {
                    this.leftData5 = { ...this.leftData5, ...data }
                    this.$refs.pulmonaryFunction.excuteCalc()
                    this.$modal.msgSuccess('数据获取成功')
                    weight = ''
                    str = []
                  })
                  .catch(() => {
                    this.$modal.msgError('数据获取失败')
                    weight = ''
                    str = []
                  })
              }, 1000)
            }
          } catch (error) {
            // console.error(error)
          } finally {
            // 允许稍后关闭串口。
            this.reader.releaseLock()
            // this.writer.releaseLock()
          }
        }

        // clearInterval(this.writeInt)
        await this.port.close()
        this.keepReading = false
      }
    },
    // 肺功能转换
    Uint8ArrayToString(fileData) {
      fileData = fileData.reverse()
      var dataString = ''
      for (var i = 0; i < fileData.length; i++) {
        dataString += String.fromCharCode(fileData[i])
      }
      return dataString
    },
    // 检查人返回选中的值
    selectChange(value) {
      if (this.$route.meta.dataType == '3') {
        this.rightData3.sectionResultMain.rummagerId = value.id
        this.rightData3.sectionResultMain.rummagerName = value.username
        this.rightData3 = JSON.parse(JSON.stringify(this.rightData3))
      } else if (this.$route.meta.dataType == '4') {
        this.rightData4.sectionResultMain.rummagerId = value.id
        this.rightData4.sectionResultMain.rummagerName = value.username
      } else if (this.$route.meta.dataType == '5') {
        this.rightData5.sectionResultMain.rummagerId = value.id
        this.rightData5.sectionResultMain.rummagerName = value.username
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.rightData6.sectionResultMain.rummagerId = value.id
        this.rightData6.sectionResultMain.rummagerName = value.username
      } else if (this.$route.meta.dataType == '11') {
        this.rightData11.sectionResultMain.rummagerId = value.id
        this.rightData11.sectionResultMain.rummagerName = value.username
      } else if (this.$route.meta.dataType == '10') {
        this.rightData10.sectionResultMain.rummagerId = value.id
        this.rightData10.sectionResultMain.rummagerName = value.username
      }
    },
    // 录入人返回选中的值
    selectChange1(value) {
      if (this.$route.meta.dataType == '3') {
        this.rightData3.sectionResultMain.writeId = value.id
        this.rightData3.sectionResultMain.writeName = value.username
        this.rightData3 = JSON.parse(JSON.stringify(this.rightData3))
      } else if (this.$route.meta.dataType == '4') {
        this.rightData4.sectionResultMain.writeId = value.id
        this.rightData4.sectionResultMain.writeName = value.username
      } else if (this.$route.meta.dataType == '5') {
        this.rightData5.sectionResultMain.writeId = value.id
        this.rightData5.sectionResultMain.writeName = value.username
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.rightData6.sectionResultMain.writeId = value.id
        this.rightData6.sectionResultMain.writeName = value.username
      } else if (this.$route.meta.dataType == '11') {
        this.rightData11.sectionResultMain.writeId = value.id
        this.rightData11.sectionResultMain.writeName = value.username
      } else if (this.$route.meta.dataType == '10') {
        this.rightData10.sectionResultMain.writeId = value.id
        this.rightData10.sectionResultMain.writeName = value.username
      }
    },
    // 双击查看小结
    checkDetails(canChange) {
      if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.$refs.lookDetail.toDetail(this.rightData6.sectionResultMain.conclusions, canChange)
      }
    },
    // 更新小结
    updateConclusions(conclusions) {
      this.rightData6.sectionResultMain.conclusions = conclusions
    },
    // 根据体检数据修改结论词
    changeJLC(data) {
      if (this.$route.meta.dataType == 3) {
        this.rightData3.jlcData.forEach((el, index) => {
          if (el.jcxm == data.jcxm) {
            this.$delete(this.rightData3.jlcData, index)
          }
        })
        if (data.jlcId) {
          this.rightData3.jlcData.unshift(data)
        }
      } else if (this.$route.meta.dataType == 5) {
        this.rightData5.jlcData.forEach((el, index) => {
          if (el.jcxm == data.jcxm) {
            this.$delete(this.rightData5.jlcData, index)
          }
        })
        if (data.jlcId) {
          this.rightData5.jlcData.unshift(data)
        }
      }
    },
    // 生成结论词时不能审核，生成后才能审核
    changeBtnState(state) {
      if (state == 1) {
        this.mainDisabled = true
      } else {
        this.mainDisabled = false
      }
    },
    // 根据体检数据修改小结
    changeConclusion(text, num) {
      if (this.$route.meta.dataType == 3) {
        let tempText = text.substring(0, text.indexOf(':'))
        let tempArray = this.rightData3.sectionResultMain.conclusions ? this.rightData3.sectionResultMain.conclusions.split(';') : []
        tempArray.forEach((el, index) => {
          if (el.includes(tempText)) {
            this.$delete(tempArray, index)
          }
        })
        if (text && Number(num) != 0) {
          tempArray.push(text)
        }
        this.rightData3.sectionResultMain.conclusions = tempArray.filter((i) => i && i.trim()).join(';')
      } else if (this.$route.meta.dataType == 5) {
        let isChange = false
        if (this.rightData5.sectionResultMain.conclusions) {
          let conclusions = this.rightData5.sectionResultMain.conclusions.split(';')
          for (let i = 0; i < conclusions.length; i++) {
            if (conclusions[i] && conclusions[i].split(':')[0].includes(text.split(':')[0])) {
              conclusions[i] = text
              isChange = true
            }
          }
          conclusions = conclusions.join(';')
          this.rightData5.sectionResultMain.conclusions = conclusions
        } else {
          this.rightData5.sectionResultMain.conclusions = text + ';'
          isChange = true
        }
        if (!isChange) {
          this.rightData5.sectionResultMain.conclusions = text + ';'
        }
      }
    },
    // 图像、无图像科室左侧多选
    itemChange(item, index, value, isChecked, multiple) {
      if (isChecked) {
        this.leftData6[index].checkList.push(value)
      } else {
        // let checkList = this.leftData6[index].checkList
        for (let i = this.leftData6[index].checkList.length - 1; i >= 0; i--) {
          if (this.leftData6[index].checkList[i] == value) {
            this.$delete(this.leftData6[index].checkList, i)
          }
        }
      }
      if (multiple == 1) {
        let isMoren = false
        let morenItem = ''
        this.leftData6[index].optionList.forEach((el) => {
          if (el.mrxz == 'morenxuanzhong') {
            morenItem = el.sname
          }
          if (el.sname == this.leftData6[index].checkList.slice(-1)[0] && el.mrxz == 'morenxuanzhong') {
            this.leftData6[index].checkList = [el.sname]
            if (el.jcmc) {
              this.leftData6[index].jcms = el.jcmc
            } else {
              this.leftData6[index].jcms = el.sname + ';'
            }
            this.rightData6.griddata = this.rightData6.griddata.filter((item) => item.jcxmmc != el.jcxmmc && item.jcxm != el.jcxmmc)
            isMoren = true
          }
        })
        if (isMoren) {
          return
        } else if (this.leftData6[index].checkList.includes(morenItem)) {
          this.leftData6[index].checkList.splice(0, 1)
        }
      }
      // this.leftData6[index].jcms = this.leftData6[index].checkList.join('、') + ';'
      let jcmsList = JSON.parse(JSON.stringify(this.leftData6[index].checkList))
      jcmsList.forEach((el, index) => {
        item.optionList.forEach((val) => {
          if (el == val.sname) {
            if (val.jcmc) {
              jcmsList[index] = val.jcmc
            }
          }
        })
      })
      this.leftData6[index].jcms = jcmsList.join('、') + ';'
      if (this.leftData6[index].checkList.length == 0) {
        this.leftData6[index].jcms = ''
        this.rightData6.griddata.forEach((val, index2) => {
          if ((val.jcxmmc == item.jcxmmc || val.jcxm == item.jcxmmc) && !this.leftData6[index].checkList.includes(val.tzc)) {
            this.$delete(this.rightData6.griddata, index2)
          }
        })
        return
      }
      let _index = undefined
      item.optionList.forEach((el, index2) => {
        if (el.sname == this.leftData6[index].checkList.slice(-1)[0]) {
          _index = index2
        }
      })
      if (item.optionList[_index].jlcId) {
        if (this.rightData6.griddata.length == 0) {
          this.rightData6.griddata.push(
            Object.assign(item.optionList[_index], {
              jlcName: item.optionList[_index].jlcmc || '',
              tzc: item.optionList[_index].sname,
            })
          )
        } else {
          let array = []
          let jlcArray = this.rightData6.griddata.map((item) => item.jlcId)
          this.rightData6.griddata.forEach((val, index) => {
            array.push(val.tzc)
          })
          if (!array.includes(item.optionList[_index].sname) && !jlcArray.includes(item.optionList[_index].jlcId)) {
            this.rightData6.griddata.push(
              Object.assign(item.optionList[_index], {
                jlcName: item.optionList[_index].jlcmc,
                tzc: item.optionList[_index].sname,
              })
            )
          }
        }
      }
      this.rightData6.griddata.forEach((val, index2) => {
        if ((val.jcxmmc == item.jcxmmc || val.jcxm == item.jcxmmc) && !this.leftData6[index].checkList.includes(val.tzc)) {
          this.$delete(this.rightData6.griddata, index2)
        }
      })
      // }
    },
    // 视力辨色力左侧多选
    itemChangeSLJC(item, index) {
      let isMoren = false
      let morenItem = ''
      this.leftData11[index].optionList.forEach((el) => {
        if (el.mrxz == 'morenxuanzhong') {
          morenItem = el.sname
        }
        if (el.sname == this.leftData11[index].checkList.slice(-1)[0] && el.mrxz == 'morenxuanzhong') {
          this.leftData11[index].checkList = [el.sname]
          if (el.jcmc) {
            this.leftData11[index].jcms = el.jcmc
          } else {
            this.leftData11[index].jcms = el.sname + ';'
          }
          // this.rightData11.jlcData = this.rightData11.jlcData.filter((item) => item.jcxmmc != el.jcxmmc && item.jcxm != el.jcxmmc)
          isMoren = true
        }
      })
      if (item.checkList[item.checkList.length - 1] == '未检') {
        this.leftData11[index].checkList = ['未检']
        this.leftData11[index].jcms = '未检;'
        isMoren = true
      } else if (item.checkList[0] == '未检') {
        this.leftData11[index].checkList = this.leftData11[index].checkList.slice(1)
      }
      if (isMoren) {
        if (this.rightData11.jlcData.length) {
          for (let i = 0; i < 3; i++) {
            if (this.rightData11.length != 0) {
              this.rightData11.jlcData.forEach((val, index2) => {
                this.$delete(this.rightData11.jlcData, index2)
              })
            }
          }
        }
        return
      } else if (this.leftData11[index].checkList.includes(morenItem)) {
        this.leftData11[index].checkList.splice(0, 1)
      }
      let jcmsList = JSON.parse(JSON.stringify(this.leftData11[index].checkList))
      jcmsList.forEach((el, index) => {
        item.optionList.forEach((val) => {
          if (el == val.sname) {
            if (val.jcmc) {
              jcmsList[index] = val.jcmc
            }
          }
        })
      })
      this.leftData11[index].jcms = jcmsList.join('、') + ';'
      if (this.leftData11[index].checkList.length == 0) {
        this.leftData11[index].jcms = ''
        this.rightData11.jlcData.forEach((val, index2) => {
          if ((val.jcxmmc == item.jcxmmc || val.jcxm == item.jcxmmc) && !this.leftData11[index].checkList.includes(val.tzc)) {
            this.$delete(this.rightData11.jlcData, index2)
          }
        })
        return
      }
      let _index = undefined
      item.optionList.forEach((el, index2) => {
        if (el.sname == this.leftData11[index].checkList.slice(-1)[0]) {
          _index = index2
        }
      })
      if (item.optionList[_index].jlcId) {
        if (this.rightData11.jlcData.length == 0) {
          this.rightData11.jlcData.push(
            Object.assign(item.optionList[_index], {
              jlcName: item.optionList[_index].jlcmc || '',
              tzc: item.optionList[_index].sname,
            })
          )
        } else {
          let array = []
          let jlcArray = this.rightData11.jlcData.map((item) => item.jlcId)
          this.rightData11.jlcData.forEach((val, index) => {
            array.push(val.tzc)
          })
          if (!array.includes(item.optionList[_index].sname) && !jlcArray.includes(item.optionList[_index].jlcId)) {
            this.rightData11.jlcData.push(
              Object.assign(item.optionList[_index], {
                jlcName: item.optionList[_index].jlcmc,
                tzc: item.optionList[_index].sname,
              })
            )
          }
        }
        this.rightData11.jlcData.forEach((val, index2) => {
          if ((val.jcxmmc == item.jcxmmc || val.jcxm == item.jcxmmc) && !this.leftData11[index].checkList.includes(val.tzc)) {
            this.$delete(this.rightData11.jlcData, index2)
          }
        })
      }
    },
    // 碳13DPM值输入
    inputResultChange(index) {
      // 图像、无图像科室输入类型值改变
      if (this.$route.meta.dataType == 6 || this.$route.meta.dataType == 7) {
        if (this.leftData6[index].inputResult) {
          this.leftData6[index].jcms = this.leftData6[index].jcxmmc + ':' + this.leftData6[index].inputResult + ';'
        }
      } else {
        // 碳13 碳14 输入值
        // 如果是胶州 c13 正常值 小于等于4  c14 正常值 0-49
        // 获取范围值
        let otherIndex = index ? 0 : 1
        if ((this.leftData4[index].sfxmmc.includes('14') && this.leftData4[index].inputResult <= this.C14Data.high) || (this.leftData4[index].sfxmmc.includes('13') && this.leftData4[index].inputResult <= this.C13Data.high)) {
          this.leftData4[otherIndex].checkList = ['阴性']
          this.leftData4[otherIndex].jcms = this.leftData4[otherIndex].jcxmmc + ':阴性;'
        } else if ((this.leftData4[index].sfxmmc.includes('14') && this.leftData4[index].inputResult > this.C14Data.high) || (this.leftData4[index].sfxmmc.includes('13') && this.leftData4[index].inputResult > this.C13Data.high)) {
          this.leftData4[otherIndex].checkList = ['阳性']
          this.leftData4[otherIndex].jcms = this.leftData4[otherIndex].jcxmmc + ':阳性;'
        }
        this.itemChange2(this.leftData4[otherIndex], otherIndex)
        // 碳13DPM值输入
        this.leftData4[index].jcms = this.leftData4[index].jcxmmc + ':' + this.leftData4[index].inputResult + ';'
      }
    },
    // 碳13选择阴阳性
    itemChange2(item, index) {
      if (this.leftData4[index].checkList.length == 0) {
        this.leftData4[index].jcms = ''
        this.rightData4.jlcData.forEach((el, index) => {
          if (item.jcxmmc == el.jxcmmc || item.jcxmmc == el.jcxmmc) {
            this.$delete(this.rightData4.jlcData, index)
          }
        })
      } else {
        this.leftData4[index].checkList = [this.leftData4[index].checkList.slice(-1)[0]]
        this.leftData4[index].jcms = item.jcxmmc + ':' + this.leftData4[index].checkList[0] + ';'
        this.rightData4.jlcData = []
        this.rightData4.griddata = []
        item.optionList.forEach((el) => {
          if (el.jlcId) {
            if (el.sname == this.leftData4[index].checkList[0]) {
              el.jcxmId = el.tjxmId
              el.jcxm = el.jcxmmc
              el.tzc = el.sname
              el.jlcName = el.jlcmc
              this.rightData4.jlcData.push(el)
              this.rightData4.griddata.push(el)
            }
          }
        })
      }
    },
    // 碳13生成小结
    handleSummary2(fn = false) {
      if (this.$route.meta.dataType == '11') {
        this.$refs.colorDiscrimination.conclusionsHandle()
        return
      }
      let xj = ''
      this.leftData4.forEach((el) => {
        if (el.fentryonly == 'shuru') {
          xj = xj.concat(el.jcms)
        } else {
          el.checkList.forEach((val, index2) => {
            if (index2 == 0) {
              // xj = xj.concat(el.jcxmmc + ':' + val + el.jcms + ';')
              // 2025-3-31 检查结论加上检查描述手动输入
              xj = xj.concat(el.jcms)
            } else {
              xj = xj.substr(0, xj.length - 1).concat('、' + val + ';')
            }
          })
        }
      })
      if (xj == '') {
        xj = '未见异常;'
      }
      this.rightData4.sectionResultMain.conclusions = xj
      if (fn) {
        fn()
      }
    },
    // 肺功能生成小结
    handleSummaryPF() {
      this.$refs.pulmonaryFunction.excuteCalc(true)
    },
    // 肺功能修改是否弃检、危急值
    selectChangePF(data) {
      this.formdata5.isUnchecked = data.isUnchecked
      this.formdata5.isDanger = data.isDanger
    },
    // 肺功能左侧改变
    changeLeftData5(data) {
      this.tempdata5 = data
    },
    // 图像、无图像科室点击小结生成
    handleSummary(skip = false) {
      if (!this.rightData6.sectionResultMain.rummagerId) {
        this.$modal.msgError('请选择检查人')
        return
      }
      if (!this.rightData6.sectionResultMain.rummagerTime) {
        this.$modal.msgError('请选择检查时间')
        return
      }
      let xj = ''
      let morenWorld = ''
      this.leftData6.forEach((el, index) => {
        if (el.fentryonly != 'shuru') {
          el.optionList.forEach((val) => {
            if (val.mrxz == 'morenxuanzhong') {
              morenWorld = val.sname
            }
          })
          if (el.checkList.length) {
            if (el.checkList[0] != morenWorld || (el.checkList[0] == morenWorld && el.optionList[0].tzcId == '3502')) {
              xj += el.jcxmmc + ':' + el.jcms
            } else if (el.checkList[0].jcmc) {
              xj += el.jcxmmc + ':' + el.jcmc
            } else if (el.checkList[0] == morenWorld && el.jcms != morenWorld && el.jcms != morenWorld + ';') {
              xj += el.jcxmmc + ':' + el.jcms
            } else if (this.ksID == '8') {
              // 2025-04-07 淮南修改,科室id=8,经颅多普勒未见异常传全部名称
              xj += el.jcms
            }
          }
        } else {
          if (el.inputResult && el.inputResult != '无;') {
            xj += el.jcxmmc + ':' + el.inputResult
          }
        }
      })
      if (xj == '') {
        xj = this.ksID != '9' ? '未见异常;' : '正常心电图;'
      }
      this.rightData6.sectionResultMain.conclusions = xj
      this.rightData6 = JSON.parse(JSON.stringify(this.rightData6))
      let jsondata = []
      let data = []
      let zdwjz = 0
      this.leftData6.forEach((el) => {
        if (el.wjzjb + 0 > zdwjz) {
          zdwjz = el.wjzjb + 0
        }
        data.push({
          patientcode: this.headParams.patientcode,
          itemId: el.tjxmId,
          itemName: el.jcxmmc,
          depId: this.ksID,
          inspectDescribe: el.jcms || (el.fentryonly == 'shuru' ? '无;' : el.jcms),
          feeId: el.sfxmId,
          signList: (el.fentryonly != 'shuru' ? el.checkList.join(';') : el.inputResult) || '无',
        })
        if (el.fentryonly != 'shuru') {
          el.optionList.forEach((val, index2) => {
            if (el.checkList.includes(val.sname)) {
              jsondata.push({
                sfqj: el.qij,
                wjz: el.wjzjb,
                wjzjb: el.wjzjb == '3' ? '高' : el.wjzjb == '2' ? '中' : el.wjzjb == '1' ? '低' : '无',
                sfxmId: val.sfxmId,
                jcxmId: val.tjxmId,
                tzcId: val.tzcId,
                jlcId: val.jlcId,
                jcms: el.jcms,
              })
            }
          })
        } else {
          jsondata.push({
            sfqj: el.qij,
            wjz: el.wjzjb,
            wjzjb: el.wjzjb == '3' ? '高' : el.wjzjb == '2' ? '中' : el.wjzjb == '1' ? '低' : '无',
            sfxmId: el.sfxmId,
            jcxmId: el.tjxmId,
            tzcId: el.tzcId,
            jlcId: el.jlcId,
            jcms: el.jcms,
            inputResult: el.inputResult || '无;',
          })
        }
      })
      if (!skip) {
        // this.$modal.msgSuccess('保存成功')
        let query = {
          ksID: this.ksID,
          patientcode: this.headParams.patientcode,
          jcr: this.rightData6.sectionResultMain.rummagerId,
          jcsj: this.rightData6.sectionResultMain.rummagerTime,
          lrr: this.rightData6.sectionResultMain.writeId,
          lrsj: this.rightData6.sectionResultMain.writeTime,
          xjdata: this.rightData6.sectionResultMain.conclusions,
          zdwjz,
          zdwjzjb: zdwjz == '3' ? '高' : zdwjz == '2' ? '中' : zdwjz == '1' ? '低' : '无',
          data,
          griddata: this.rightData6.griddata,
          jsondata,
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        case1AutoSave(query)
          .then(() => {
            this.$modal.msgSuccess('保存成功')
            this.getSectionData(this.headParams.patientcode, this.ksID, false)
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else {
        let query = {
          ksID: this.ksID,
          patientcode: this.headParams.patientcode,
          jcr: this.rightData6.sectionResultMain.rummagerId,
          jcsj: this.rightData6.sectionResultMain.rummagerTime,
          lrr: this.rightData6.sectionResultMain.writeId,
          lrsj: this.rightData6.sectionResultMain.writeTime,
          xjdata: this.rightData6.sectionResultMain.conclusions,
          zdwjz,
          zdwjzjb: zdwjz == '3' ? '高' : zdwjz == '2' ? '中' : zdwjz == '1' ? '低' : '无',
          data,
          griddata: this.rightData6.griddata,
          jsondata,
        }
        if (!query.jsondata.length) {
          query.jsondata = [{ sfqj: 0, wjz: 0, wjzjb: '无', jcms: '' }]
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        caseshenhe(query)
          .then(() => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: this.rightData6.sectionResultMain.rummagerName,
                rummagerId: this.rightData6.sectionResultMain.rummagerId,
                writeName: this.rightData6.sectionResultMain.writeName,
                writeId: this.rightData6.sectionResultMain.writeId,
              })
            )
            this.$modal.msgSuccess('审核成功')
            // this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
            this.$nextTick(() => {
              this.clearCode = true
              this.$refs.indexHeader.$refs.patientcode.focus()
              this.$refs.indexHeader.$refs.patientcode.select(0, this.headParams.patientcode.length - 1)
            })
            this.forbiddenAudit = false
            clLoading.close()
            this.$tab.refreshPage()
          })
          .catch((error) => {
            console.error(error)
            this.forbiddenAudit = false
            clLoading.close()
          })
      }
    },
    // 审核
    handleAudit(context) {
      if (this.forbiddenAudit) {
        return
      }
      this.forbiddenAudit = true
      if (this.$route.meta.dataType == '1') {
        //检验科检查
      } else if (this.$route.meta.dataType == '2') {
        //外送项目检查
      } else if (this.$route.meta.dataType == '3') {
        //一般检查
        if (!this.rightData3.sectionResultMain.rummagerId) {
          this.$modal.msgError('请选择检查人')
          return
        } else if (!this.rightData3.sectionResultMain.rummagerTime) {
          this.$modal.msgError('请选择检查时间')
          return
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        let formdata = JSON.parse(JSON.stringify(this.leftData3.tjreg))
        if (formdata.xyms && !formdata.xyms.includes('正常')) {
          formdata.xyms = '<font color="red">' + formdata.xyms + '</font>'
        }
        if (formdata.bmims && !formdata.bmims.includes('正常')) {
          formdata.bmims = '<font color="red">' + formdata.bmims + '</font>'
        }
        formdata.ssy = formdata.ssy ? formdata.ssy : null
        formdata.szy = formdata.szy ? formdata.szy : null
        formdata.xy = formdata.xy ? formdata.xy : null
        formdata.mb = formdata.mb ? formdata.mb : null
        formdata.sg = formdata.sg ? formdata.sg : null
        formdata.tz = formdata.tz ? formdata.tz : null
        formdata.bmi = formdata.bmi ? formdata.bmi : null
        formdata.respiratoryRate = formdata.respiratoryRate ? formdata.respiratoryRate : null
        formdata.temperature = formdata.temperature ? formdata.temperature : null
        formdata.bust = formdata.bust ? formdata.bust : null
        formdata.commonState = formdata.commonState ? formdata.commonState : null
        saveOrUpdateGI({
          data: this.rightData3.sectionResultMain,
          griddata: this.rightData3.jlcData,
          formdata,
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(() => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: this.rightData3.sectionResultMain.rummagerName,
                rummagerId: this.rightData3.sectionResultMain.rummagerId,
                writeName: this.rightData3.sectionResultMain.writeName,
                writeId: this.rightData3.sectionResultMain.writeId,
              })
            )
            // this.$tab.refreshPage()
            this.$modal.msgSuccess('审核成功')
            this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
            this.$nextTick(() => {
              this.clearCode = true
              this.$refs.indexHeader.$refs.patientcode.focus()
              this.$refs.indexHeader.handleSearch()
            })
            this.forbiddenAudit = false
            clLoading.close()
          })
          .catch((error) => {
            console.error(error)
            clLoading.close()
            this.forbiddenAudit = false
          })
      } else if (this.$route.meta.dataType == '4') {
        //C13检查
        if (!this.rightData4.sectionResultMain.rummagerId) {
          this.$modal.msgError('请选择检查人')
          return
        }
        if (!this.rightData4.sectionResultMain.rummagerTime) {
          this.$modal.msgError('请选择检查时间')
          return
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        this.handleSummary2(() => {
          let jsondata = []
          let data = []
          let zdwjz = 0
          this.leftData4.forEach((el) => {
            if (el.wjzjb + 0 > zdwjz) {
              zdwjz = el.wjzjb + 0
            }
            data.push({
              patientcode: this.headParams.patientcode,
              itemId: el.tjxmId,
              itemName: el.jcxmmc,
              depId: this.ksID,
              inspectDescribe: el.jcms,
              feeId: el.sfxmId,
              signList: el.fentryonly == 'shuru' ? el.inputResult : el.checkList.join(';'),
            })
            if (el.fentryonly == 'shuru') {
              jsondata.push({
                sfqj: el.qij,
                wjz: el.wjzjb,
                wjzjb: el.wjzjb == '3' ? '高' : el.wjzjb == '2' ? '中' : el.wjzjb == '1' ? '低' : '无',
                sfxmId: el.sfxmId,
                jcxmId: el.tjxmId,
                tzcId: el.tzcId,
                jlcId: el.jlcId,
                jcms: el.jcms,
                inputResult: el.inputResult,
              })
            } else {
              el.optionList.forEach((val, index2) => {
                if (el.checkList.includes(val.sname)) {
                  jsondata.push({
                    sfqj: el.qij,
                    wjz: el.wjzjb,
                    wjzjb: el.wjzjb == '3' ? '高' : el.wjzjb == '2' ? '中' : el.wjzjb == '1' ? '低' : '无',
                    sfxmId: val.sfxmId,
                    jcxmId: val.tjxmId,
                    tzcId: val.tzcId,
                    jlcId: val.jlcId,
                    jcms: el.jcms,
                  })
                }
              })
            }
          })
          let query = {
            ksID: this.ksID,
            patientcode: this.headParams.patientcode,
            jcr: this.rightData4.sectionResultMain.rummagerId,
            jcsj: this.rightData4.sectionResultMain.rummagerTime,
            lrr: this.rightData4.sectionResultMain.writeId,
            lrsj: this.rightData4.sectionResultMain.writeTime,
            xjdata: this.rightData4.sectionResultMain.conclusions,
            zdwjz,
            zdwjzjb: zdwjz == '3' ? '高' : zdwjz == '2' ? '中' : zdwjz == '1' ? '低' : '无',
            data,
            griddata: this.rightData4.griddata,
            jsondata,
          }
          if (!query.jsondata.length) {
            query.jsondata = [{}]
          }
          caseshenhe(query)
            .then(() => {
              localStorage.setItem(
                'section' + this.$route.meta.ksID,
                JSON.stringify({
                  rummagerName: this.rightData4.sectionResultMain.rummagerName,
                  rummagerId: this.rightData4.sectionResultMain.rummagerId,
                  writeName: this.rightData4.sectionResultMain.writeName,
                  writeId: this.rightData4.sectionResultMain.writeId,
                })
              )
              // this.$tab.refreshPage()
              this.$modal.msgSuccess('审核成功')
              this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
              this.$nextTick(() => {
                this.clearCode = true
                this.$refs.indexHeader.$refs.patientcode.focus()
              })
              this.forbiddenAudit = false
              clLoading.close()
            })
            .catch((error) => {
              console.error(error)
              this.forbiddenAudit = false
              clLoading.close()
            })
        })
      } else if (this.$route.meta.dataType == '5') {
        //肺功能检查 审核
        if (!this.rightData5.sectionResultMain.rummagerName) {
          this.$modal.msgError('请选择检查人')
          return
        }
        if (!this.rightData5.sectionResultMain.rummagerTime) {
          this.$modal.msgError('请选择检查时间')
          return
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        // 解决反审核后再次审核,数据没值的问题
        if (!Array.isArray(this.tempdata5)) {
          this.tempdata5 = this.leftData5Info
        }
        if (Array.isArray(this.tempdata5)) {
          this.formdata5.fvc = this.tempdata5[0].actual ? this.tempdata5[0].actual : null
          this.formdata5.predictFvc = this.tempdata5[0].forecast ? this.tempdata5[0].forecast : null
          this.formdata5.percentageFvc = this.tempdata5[0].percentage ? this.tempdata5[0].percentage : null
          this.formdata5.fev = this.tempdata5[1].actual ? this.tempdata5[1].actual : null
          this.formdata5.predictFev = this.tempdata5[1].forecast ? this.tempdata5[1].forecast : null
          this.formdata5.percentageFev = this.tempdata5[1].percentage ? this.tempdata5[1].percentage : null
          this.formdata5.fevFvc = this.tempdata5[2].actual ? this.tempdata5[2].actual : null
          this.formdata5.predictFevFvc = this.tempdata5[2].forecast ? this.tempdata5[2].forecast : null
          this.formdata5.percentageFevFvc = this.tempdata5[2].percentage ? this.tempdata5[2].percentage : null
          this.formdata5.mmef = this.tempdata5[3].actual ? this.tempdata5[3].actual : null
          this.formdata5.predictMmef = this.tempdata5[3].forecast ? this.tempdata5[3].forecast : null
          this.formdata5.percentageMmef = this.tempdata5[3].percentage ? this.tempdata5[3].percentage : null
          this.formdata5.feffc = this.tempdata5[4].actual ? this.tempdata5[4].actual : null
          this.formdata5.predictFeffc = this.tempdata5[4].forecast ? this.tempdata5[4].forecast : null
          this.formdata5.percentageFeffc = this.tempdata5[4].percentage ? this.tempdata5[4].percentage : null
          this.formdata5.feffb = this.tempdata5[5].actual ? this.tempdata5[5].actual : null
          this.formdata5.predictFeffb = this.tempdata5[5].forecast ? this.tempdata5[5].forecast : null
          this.formdata5.percentageFeffb = this.tempdata5[5].percentage ? this.tempdata5[5].percentage : null
          this.formdata5.feffa = this.tempdata5[6].actual ? this.tempdata5[6].actual : null
          this.formdata5.predictFeffa = this.tempdata5[6].forecast ? this.tempdata5[6].forecast : null
          this.formdata5.percentageFeffa = this.tempdata5[6].percentage ? this.tempdata5[6].percentage : null
          if (!this.formdata5.id) {
            this.formdata5.id = this.leftData5.id
          }
          this.formdata5.patientcode = this.headParams.patientcode
          this.formdata5.xj = this.rightData5.sectionResultMain.conclusions
        } else {
          this.formdata5.fvc = null
          this.formdata5.predictFvc = null
          this.formdata5.percentageFvc = null
          this.formdata5.fev = null
          this.formdata5.predictFev = null
          this.formdata5.percentageFev = null
          this.formdata5.fevFvc = null
          this.formdata5.predictFevFvc = null
          this.formdata5.percentageFevFvc = null
          this.formdata5.mmef = null
          this.formdata5.predictMmef = null
          this.formdata5.percentageMmef = null
          this.formdata5.feffc = null
          this.formdata5.predictFeffc = null
          this.formdata5.percentageFeffc = null
          this.formdata5.feffb = null
          this.formdata5.predictFeffb = null
          this.formdata5.percentageFeffb = null
          this.formdata5.feffa = null
          this.formdata5.predictFeffa = null
          this.formdata5.percentageFeffa = null
          this.formdata5.patientcode = this.headParams.patientcode
          this.formdata5.xj = this.rightData5.sectionResultMain.conclusions
        }
        saveOrUpdatePF({
          data: this.rightData5.sectionResultMain,
          formdata: this.formdata5,
          griddata: this.rightData5.jlcData,
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(() => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: this.rightData5.sectionResultMain.rummagerName,
                rummagerId: this.rightData5.sectionResultMain.rummagerId,
                writeName: this.rightData5.sectionResultMain.writeName,
                writeId: this.rightData5.sectionResultMain.writeId,
              })
            )
            // this.$tab.refreshPage()
            this.$modal.msgSuccess('审核成功')
            this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
            this.$nextTick(() => {
              this.clearCode = true
              this.$refs.indexHeader.$refs.patientcode.focus()
              this.$refs.indexHeader.handleSearch()
            })
            this.forbiddenAudit = false
            clLoading.close()
          })
          .catch((error) => {
            console.error(error)
            this.forbiddenAudit = false
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        //无图像检查、图像检查
        this.handleSummary(true)
      } else if (this.$route.meta.dataType == '8') {
        //问诊检查
        if (!this.rightData8.jcr) {
          this.$modal.msgError('请选择检查人')
          return
        } else if (!this.rightData8.jcsj) {
          this.$modal.msgError('请选择检查时间')
          return
        }
        setTimeout(() => {
          this.forbiddenAudit = false
        }, 500)
        this.$refs.greetings.handleAudit(this.rightData8)
      } else if (this.$route.meta.dataType == '9') {
        //电测听检查
        if (!this.rightData9.main.rummagerId) {
          this.$modal.msgError('请选择检查人')
          this.forbiddenAudit = false
          return
        } else if (!this.rightData9.main.rummagerTime) {
          this.$modal.msgError('请选择检查时间')
          this.forbiddenAudit = false
          return
        }
        if (!this.rightData9.audiometry.testResult) {
          this.$alert('请输入（听力检测）结果评定', '提示')
          this.forbiddenAudit = false
          return
        }
        if (!this.rightData9.audiometry || !this.rightData9.audiometry.airImgPath || !this.rightData9.audiometry.boneImgPath || this.rightData9.audiometry.needRegenerateAirImgPath || this.rightData9.audiometry.needRegenerateBoneImgPath) {
          this.$refs.indexHeader.handleMakeImage(true)
          this.forbiddenAudit = false
          this.$modal.msgWarning('生成图像中')
          return
        }
        let formdata = JSON.parse(JSON.stringify(this.rightData9.audiometry))
        formdata.airLeft125 = formdata.airLeft125 ? formdata.airLeft125 : formdata.airLeft125 === 0 ? 0 : null
        formdata.airLeft250 = formdata.airLeft250 ? formdata.airLeft250 : formdata.airLeft250 === 0 ? 0 : null
        formdata.airLeft500 = formdata.airLeft500 ? formdata.airLeft500 : formdata.airLeft500 === 0 ? 0 : null
        formdata.airLeft1000 = formdata.airLeft1000 ? formdata.airLeft1000 : formdata.airLeft1000 === 0 ? 0 : null
        formdata.airLeft2000 = formdata.airLeft2000 ? formdata.airLeft2000 : formdata.airLeft2000 === 0 ? 0 : null
        formdata.airLeft3000 = formdata.airLeft3000 ? formdata.airLeft3000 : formdata.airLeft3000 === 0 ? 0 : null
        formdata.airLeft4000 = formdata.airLeft4000 ? formdata.airLeft4000 : formdata.airLeft4000 === 0 ? 0 : null
        formdata.airLeft6000 = formdata.airLeft6000 ? formdata.airLeft6000 : formdata.airLeft6000 === 0 ? 0 : null
        formdata.airLeft8000 = formdata.airLeft8000 ? formdata.airLeft8000 : formdata.airLeft8000 === 0 ? 0 : null
        formdata.airRight125 = formdata.airRight125 ? formdata.airRight125 : formdata.airRight125 === 0 ? 0 : null
        formdata.airRight250 = formdata.airRight250 ? formdata.airRight250 : formdata.airRight250 === 0 ? 0 : null
        formdata.airRight500 = formdata.airRight500 ? formdata.airRight500 : formdata.airRight500 === 0 ? 0 : null
        formdata.airRight1000 = formdata.airRight1000 ? formdata.airRight1000 : formdata.airRight1000 === 0 ? 0 : null
        formdata.airRight2000 = formdata.airRight2000 ? formdata.airRight2000 : formdata.airRight2000 === 0 ? 0 : null
        formdata.airRight3000 = formdata.airRight3000 ? formdata.airRight3000 : formdata.airRight3000 === 0 ? 0 : null
        formdata.airRight4000 = formdata.airRight4000 ? formdata.airRight4000 : formdata.airRight4000 === 0 ? 0 : null
        formdata.airRight6000 = formdata.airRight6000 ? formdata.airRight6000 : formdata.airRight6000 === 0 ? 0 : null
        formdata.airRight8000 = formdata.airRight8000 ? formdata.airRight8000 : formdata.airRight8000 === 0 ? 0 : null
        formdata.boneLeft250 = formdata.boneLeft250 ? formdata.boneLeft250 : formdata.boneLeft250 === 0 ? 0 : null
        formdata.boneLeft500 = formdata.boneLeft500 ? formdata.boneLeft500 : formdata.boneLeft500 === 0 ? 0 : null
        formdata.boneLeft1000 = formdata.boneLeft1000 ? formdata.boneLeft1000 : formdata.boneLeft1000 === 0 ? 0 : null
        formdata.boneLeft2000 = formdata.boneLeft2000 ? formdata.boneLeft2000 : formdata.boneLeft2000 === 0 ? 0 : null
        formdata.boneLeft3000 = formdata.boneLeft3000 ? formdata.boneLeft3000 : formdata.boneLeft3000 === 0 ? 0 : null
        formdata.boneLeft4000 = formdata.boneLeft4000 ? formdata.boneLeft4000 : formdata.boneLeft4000 === 0 ? 0 : null
        formdata.boneLeft6000 = formdata.boneLeft6000 ? formdata.boneLeft6000 : formdata.boneLeft6000 === 0 ? 0 : null
        // formdata.boneLeft8000 = formdata.boneLeft8000 ? formdata.boneLeft8000 : formdata.boneLeft8000 === 0 ? 0 : null
        delete formdata.boneLeft8000
        formdata.boneRight250 = formdata.boneRight250 ? formdata.boneRight250 : formdata.boneRight250 === 0 ? 0 : null
        formdata.boneRight500 = formdata.boneRight500 ? formdata.boneRight500 : formdata.boneRight500 === 0 ? 0 : null
        formdata.boneRight1000 = formdata.boneRight1000 ? formdata.boneRight1000 : formdata.boneRight1000 === 0 ? 0 : null
        formdata.boneRight2000 = formdata.boneRight2000 ? formdata.boneRight2000 : formdata.boneRight2000 === 0 ? 0 : null
        formdata.boneRight3000 = formdata.boneRight3000 ? formdata.boneRight3000 : formdata.boneRight3000 === 0 ? 0 : null
        formdata.boneRight4000 = formdata.boneRight4000 ? formdata.boneRight4000 : formdata.boneRight4000 === 0 ? 0 : null
        formdata.boneRight6000 = formdata.boneRight6000 ? formdata.boneRight6000 : formdata.boneRight6000 === 0 ? 0 : null
        // formdata.boneRight8000 = formdata.boneRight8000 ? formdata.boneRight8000 : formdata.boneRight8000 === 0 ? 0 : null
        delete formdata.boneRight8000
        let data = this.rightData9.main
        if (!data.conclusions) {
          data.conclusions = formdata.testResult + formdata.describe
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        saveOrUpdateEA({
          data,
          formdata,
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(() => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: this.rightData9.main.rummager,
                rummagerId: this.rightData9.main.rummagerId,
                writeName: this.rightData9.main.writeName,
                writeId: this.rightData9.main.writeId,
              })
            )
            this.$modal.msgSuccess('审核成功')
            this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
            this.$nextTick(() => {
              this.clearCode = true
              this.$refs.indexHeader.$refs.patientcode.focus()
              this.$refs.indexHeader.handleSearch()
            })
            this.forbiddenAudit = false
            clLoading.close()
          })
          .catch((error) => {
            console.error(error)
            this.forbiddenAudit = false
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '10') {
        //骨密度
        // this.$tab.refreshPage()
        this.clearCode = true
        this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
        this.$nextTick(() => {
          this.$refs.indexHeader.$refs.patientcode.focus()
        })
      } else if (this.$route.meta.dataType == '11') {
        //视力检查
        if (!this.rightData11.sectionResultMain.rummagerId) {
          this.$modal.msgError('请选择检查人!')
          return
        }
        if (!this.rightData11.sectionResultMain.rummagerTime) {
          this.$modal.msgError('请选择检查时间!')
          return
        }
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        let arrayData = []
        arrayData = this.rightData11.jlcData.map((item) => {
          return {
            jcxmId: item.verdictId || item.jcxmId || item.tjxmId,
            jlcId: item.jlcId,
            ms: item.jlcName,
            name: item.tzc,
            sfxmId: item.chargesId || item.sfxmId,
            tzcId: item.tzcId,
          }
        })
        if (!context.jsonData.length) {
          context.jsonData = [{}]
        }
        for (let i = context.jsonData.length - 1; i >= 0; i--) {
          if (!context.jsonData[i].inputResult && (context.jsonData[i].jcxmId == 254 || context.jsonData[i].jcxmId == 255)) {
            this.$delete(context.jsonData, i)
          }
        }
        shenheSljc({
          jsondata: context.jsonData,
          griddata: arrayData,
          jcr: this.rightData11.sectionResultMain.rummagerId,
          jcsj: this.rightData11.sectionResultMain.rummagerTime,
          ksID: this.ksID,
          lrr: this.rightData11.sectionResultMain.writeId,
          lrsj: this.rightData11.sectionResultMain.writeTime,
          patientcode: this.rightData11.patient.patientcode,
          xjdata: this.rightData11.sectionResultMain.conclusions,
          data: context.data,
          zdwjz: '0',
          zdwjzjb: '无',
        })
          .then((res) => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: this.rightData11.sectionResultMain.rummagerName,
                rummagerId: this.rightData11.sectionResultMain.rummagerId,
                writeName: this.rightData11.sectionResultMain.writeName,
                writeId: this.rightData11.sectionResultMain.writeId,
              })
            )
            this.$modal.msgSuccess('审核成功')
            this.clearCode = true
            this.$nextTick(() => {
              this.$refs.indexHeader.$refs.patientcode.focus()
            })
            this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
            // this.$tab.refreshPage()
            this.forbiddenAudit = false
            clLoading.close()
          })
          .catch((error) => {
            console.error(error)
            this.forbiddenAudit = false
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '12') {
        //健康问卷
      }
    },
    // 问诊检查审核完成后刷新页面
    greetingRefresh() {
      this.getSectionData(this.headParams.patientcode, this.$route.meta.ksID, false)
      this.$nextTick(() => {
        this.clearCode = true
        this.$refs.indexHeader.$refs.patientcode.focus()
        this.$refs.indexHeader.handleSearch()
      })
    },
    // 修改按钮状态
    changeAudit() {
      this.forbiddenAudit = false
    },
    // 反审核
    handleReAudit() {
      if (this.$route.meta.dataType == '1') {
        //检验科检查
      } else if (this.$route.meta.dataType == '2') {
        //外送项目检查
      } else if (this.$route.meta.dataType == '3') {
        //一般检查
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        reverseGI({
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(() => {
            this.$modal.msgSuccess('反审核成功')
            this.getSectionData(this.headParams.patientcode, this.ksID)
            this.$refs.indexHeader.handleSearch()
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '5') {
        //肺功能检查
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        reversePF({
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(() => {
            this.$modal.msgSuccess('反审核成功')
            this.getSectionData(this.headParams.patientcode, this.ksID)
            this.$refs.indexHeader.handleSearch()
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '4' || this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        //C13检查
        //无图像检查、图像检查
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        caseReverse({
          ksID: this.ksID,
          patientcode: this.headParams.patientcode,
        })
          .then(({ code }) => {
            if (code == 500) {
              clLoading.close()
              return
            }
            this.$modal.msgSuccess('反审核成功')
            this.getSectionData(this.headParams.patientcode, this.ksID)
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '8') {
        //问诊检查
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        reverseInquiry({
          ksID: this.ksID,
          id: this.headParams.id,
        })
          .then(({ code }) => {
            if (code == 500) {
              clLoading.close()
              return
            }
            this.$modal.msgSuccess('反审核成功')
            this.getSectionData(this.headParams.patientcode, this.ksID)
            this.$refs.indexHeader.handleSearch()
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '9') {
        //电测听检查
        const clLoading = this.$loading({
          lock: true,
          text: '操作中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.1)',
        })
        reverseEA({
          ksId: this.ksID,
          patientCode: this.headParams.patientcode,
        })
          .then(({ code }) => {
            if (code == 500) {
              clLoading.close()
              return
            }
            this.$modal.msgSuccess('反审核成功')
            this.getSectionData(this.headParams.patientcode, this.ksID)
            this.$refs.indexHeader.handleSearch()
            clLoading.close()
          })
          .catch(() => {
            clLoading.close()
          })
      } else if (this.$route.meta.dataType == '10') {
        //骨密度
        this.getSectionData(this.headParams.patientcode, this.ksID)
      } else if (this.$route.meta.dataType == '11') {
        //视力检查
        caseReverseSljc({
          ksID: this.ksID,
          patientcode: this.headParams.patientcode,
        }).then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess(res.msg)
            this.getSectionData(this.headParams.patientcode, this.ksID)
          }
        })
      } else if (this.$route.meta.dataType == '12') {
        //健康问卷
      }
    },
    // 搜索
    searchTableData(date, status) {
      if (this.$route.meta.dataType == 3) {
        this.$refs.generalInspection.searchTableData(date)
      } else if (this.$route.meta.dataType == 5) {
        this.$refs.pulmonaryFunction.searchTableData(date)
      } else if (this.$route.meta.dataType == 8) {
        this.$refs.indexRightC.searchTableData(date, status)
      } else if (this.$route.meta.dataType == 9) {
        this.$refs.electro.searchTableData(date, status)
      }
    },
    // 保存表格数值
    getRankData(tableData, total) {
      if (this.$route.meta.dataType == 3) {
        this.giTableData = {
          tableData,
          total,
        }
      } else if (this.$route.meta.dataType == 5) {
        this.tableData5 = {
          tableData,
          total,
        }
      } else if (this.$route.meta.dataType == 8) {
        this.tableData8 = {
          tableData,
          total,
        }
      } else if (this.$route.meta.dataType == 9) {
        this.tableData9 = {
          tableData,
          total,
        }
      }
    },
    // 获取cookie值
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
  },
}
</script>
<style lang="scss" scoped>
.app-container {
  padding: 0;
}
</style>
<style scoped>
.section-list-main /deep/ .el-button span {
  font-size: 16px;
}

.section-list-main /deep/ .el-input__inner,
.section-list-main /deep/ .el-textarea__inner {
  font-size: 16px;
}

.section-list-main /deep/ table .cell {
  font-size: 16px !important;
}

.section-list-main /deep/ .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #d41318 !important;
  border-color: #d41318 !important;
}

.section-list-main /deep/ .is-checked .el-checkbox__label {
  color: #d41318 !important;
}

.section-list-main /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}

.el-input__inner,
.el-textarea__inner {
  color: #000 !important;
  font-size: 16px !important;
}

/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {

  body,
  .el-input__inner,
  .el-textarea__inner,
  .el-button,
  .el-form .el-form-item__label,
  .checkbox,
  .textarea-style,
  .select-style,
  .input-style {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
<style lang="scss">
.section-list-main {
  .input-style {
    height: 30px;
    color: #000;
    font-size: 16px;
  }

  .top-style {
    display: inline-block;
    width: 100px;
    padding-right: 12px;
    margin-bottom: 14px;
    text-align: right;
    font-weight: 600;
  }

  .form-main-style {
    .input-style {
      width: 640px;
      font-weight: 600;
    }

    .textarea-style {
      width: 640px;
      padding: 4px;
      color: #000;
      font-size: 16px;
      font-weight: 600;
    }

    .select-style {
      width: 270px;
      height: 30px;
      color: #000;
      font-size: 16px;
      cursor: pointer;
    }

    .flex-style {
      display: flex;
      align-items: flex-start;
    }

    .gouxuan-main-style {
      display: flex;
      flex-wrap: wrap;
      min-width: 600px;
      margin-left: 20px;
      margin-bottom: 8px;
    }
  }
}
</style>
