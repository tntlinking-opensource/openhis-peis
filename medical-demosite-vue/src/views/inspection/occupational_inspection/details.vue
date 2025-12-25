<!-- 职业总检-职业总检报告 麦沃德科技 开发人:清风 -->
<template>
  <div class="app-container flex-direction-column health-inspection-details">
    <!-- <el-dialog title="职业总检" class="add-container add-items health-inspection-details" :visible.sync="open" width="1600px" append-to-body :close-on-click-modal="false" @close="handleCloseDialog" v-loading="dialogLoading"> -->
    <div style="display: flex; justify-content: space-between">
      <el-form :model="queryParams" style="overflow: hidden;" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
        <el-form-item label="读卡" prop="patientno">
          <el-input ref="patientno" placeholder="请输入体检号" style="width: 160px" clearable @keyup.enter.native="handleQuery" v-model="queryParams.patientno"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
        <el-form-item label="体检号" style="margin-left: 40px">
          <div v-text="queryParams.patientcode"></div>
        </el-form-item>
        <el-form-item label="姓名" style="margin-left: 32px">
          <div v-text="queryParams.patientname"></div>
        </el-form-item>
        <el-form-item label="性别" style="margin-left: 32px">
          <div v-if="queryParams.idSex == 0">男</div>
          <div v-else-if="queryParams.idSex == 1">女</div>
        </el-form-item>
        <el-form-item label="年龄" style="margin-left: 32px">
          <div v-text="queryParams.age"></div>
        </el-form-item>
        <el-form-item label="会员类型" style="margin-left: 32px">
          <template>
            <el-tag type="info" v-if="queryParams.isVIP == '普通会员'" class="el-icon-user">普通会员</el-tag>
            <el-tag v-else-if="queryParams.isVIP == 'VIP会员'" class="el-icon-user">VIP会员</el-tag>
            <el-tag v-else-if="queryParams.isVIP == 'VVIP会员'" class="el-icon-user">VVIP会员</el-tag>
          </template>
        </el-form-item>
        <template v-if="queryParams.harms">
          <div class="header-harms">{{ queryParams.harms[0] }}</div>
          <div class="header-harms">{{ queryParams.harms[1] }}</div>
          <el-tooltip popper-class="tooltip-item" effect="dark" :content="queryParams.harms[2]" placement="bottom" :disabled="queryParams.harms[2].length <= 20">
            <div class="header-harms text-ellipsis">{{ queryParams.harms[2] }}</div>
          </el-tooltip>
          <div class="header-harms">复查次数: {{ reviewTimes }}</div>
        </template>
      </el-form>
    </div>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-coin" plain @click="goDepartmentResultsWindow()" :disabled="!verdicts" v-hasPermi="['inspection:occupationalInspection:details:departmentResults']">科室小结</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="electricalAudiometryWindow()" :disabled="!audio" v-hasPermi="['inspection:occupationalInspection:details:electricalAudiometry']">电测听</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-document-add" plain @click="createReport" :disabled="view" v-hasPermi="['inspection:occupationalInspection:details:checkReport']">生成报告</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-search" plain @click="viewReportWindow()" :disabled="view" v-hasPermi="['inspection:occupationalInspection:details:viewReport']">查看报告</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="remindWindow()" :disabled="!remindBtn" v-hasPermi="['inspection:occupationalInspection:details:remind']">提醒</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" @click="historyWindow()" v-if="hasHistory" :disabled="!historyBtn" v-hasPermi="['inspection:occupationalInspection:details:history']">历史</el-button>
        <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="historyWindow()" v-else :disabled="!historyBtn" v-hasPermi="['inspection:occupationalInspection:details:history']">历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-refresh" plain @click="synchronizationWindow()" :disabled="!audit" v-hasPermi="['inspection:occupationalInspection:details:synchronization']">同步</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-refresh-right" plain @click="saveWindow()" :disabled="!save" v-hasPermi="['inspection:occupationalInspection:details:save']">保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-lock" plain @click="lockWindow(1)" :disabled="!lock" v-hasPermi="['inspection:occupationalInspection:details:audit']">锁定</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-unlock" plain @click="lockWindow(0)" :disabled="!unlock" v-hasPermi="['inspection:occupationalInspection:details:audit']">解锁</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-refresh-right" @click="auditWindow(1)" :disabled="!audit" v-hasPermi="['inspection:occupationalInspection:details:audit']">审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-refresh-left" plain @click="auditWindow(0)" :disabled="!unaudit" v-hasPermi="['inspection:occupationalInspection:details:unaudit']">反审</el-button>
      </el-col>
      <el-col :span="1.5">
        <span class="audit-state green" v-if="!audit">已审核</span>
        <span class="audit-state red" v-else>未审核</span>
      </el-col>
    </el-row>

    <div class="tabs" id="mainBox">
      <div style="width: 50%; height: 100%; margin-right: 1%">
        <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 100%">
          <el-tab-pane label="综述" name="first" style="height: 100%">
            <el-input type="textarea" class="tabs-item" v-model="sectionTotal.summarize" :readonly="!summary" resize="none"></el-input>
          </el-tab-pane>
          <el-tab-pane label="职业结论及建议" name="second" style="height: 100%">
            <el-input type="textarea" class="tabs-item" v-model="sectionTotal.jkoffer" :readonly="!advice" resize="none"></el-input>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div style="width: 49%; height: 100%; display: flex; flex-direction: column">
        <div id="plate" style="width: 100%; height: 310px; display: flex; flex-direction: row">
          <div style="margin-right: 4px" id="finall">
            <div style="padding: 0 8px; height: 44px; line-height: 44px">
              <div style="font-size: 20px; font-weight: 500; color: #000">阳性结果</div>
            </div>
            <!-- <div style="height: 200px; overflow: hidden"> -->
            <!-- <div style="margin:16px 12px 0;width: 438px;height: 200px; overflow: auto;">{{ sectionTotal.posistive }}
                </div> -->
            <el-input type="textarea" v-model="sectionTotal.posistive" style="padding: 0 12px; height: 250px; font-size: 18px" resize="none"></el-input>
            <!-- </div> -->
          </div>
          <div id="finall">
            <div style="padding: 0 8px; height: 44px; line-height: 44px">
              <div style="font-size: 20px; font-weight: 500; color: #000">最终结论建议</div>
            </div>
            <!-- <div style="height: 200px; overflow: hidden"> -->
            <!-- <div style="padding:0 12px;width: 438px;height: 200px; overflow: auto;">
                  {{ sectionTotal.reportConclusions }}
                </div> -->
            <el-input type="textarea" v-model="sectionTotal.reportConclusions" :readonly="!audit" style="padding: 0 12px; height: 250px; font-size: 18px" resize="none"></el-input>
            <!-- </div> -->
          </div>
        </div>
        <div style="border: 1px solid #c4c4c4; width: 100%; margin-top: 4px; height: calc(100% - 310px); display: flex; flex-direction: column">
          <div style="padding: 8px">
            <div style="font-size: 20px; font-weight: 500; color: #000">职业处理意见</div>
          </div>
          <el-row :gutter="10" class="mb8" id="setMrgin">
            <el-col :span="1.5">
              <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="!add" @click="optionsWindow">增加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="danger" icon="el-icon-delete" plain @click="deleteTableRows" :disabled="!remove || !ids.length">删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="success" icon="el-icon-edit-outline" plain :disabled="!review" @click="reviewWindow">复查</el-button>
            </el-col>
            <el-col :span="1.5" class="align" v-for="item in occupationList" :key="item.id">
              <el-radio @change="optionsWindows(item.inputCode, item.id, true)" :disabled="!summaryId" :label="item.id" :value="checked">{{ item.occupationSummary }}</el-radio>
            </el-col>
          </el-row>
          <div class="table-box">
            <el-table ref="tableData" :data="tableData" v-loading="loading" :border="true" :stripe="true" row-key="id" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="occupationDiagnosis" label="危害因素" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="occupationSummary" label="职业结论" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="zyjjzdm" label="禁忌疾病" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="kyzyb" label="可疑职业病" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="summaryText" label="处理意见" show-overflow-tooltip align="center"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>
    <department-items ref="departmentItems" />
    <remind-items ref="remindItems" />
    <history-items ref="historyItems" />
    <electrical-items ref="electricalItems" />
    <options-items ref="optionsItems" @refreshParents="treatmentData" />
    <review-items ref="reviewItems" @optionsWindows="optionsWindows" @noticeFunc="noticeFunc" />
    <confirm-items ref="confirmItems" :audit="audit" :unaudit="unaudit" @savehealthWindow="savehealthWindow" @detailsWindow="detailsWindow" />
    <!-- </el-dialog> -->
  </div>
</template>

<script>
import departmentItems from './department.vue'
import remindItems from './remind.vue'
import historyItems from './history.vue'
import electricalItems from './electrical.vue'
import optionsItems from './opinions.vue'
import reviewItems from './review.vue'
import confirmItems from './confirm.vue'
import Cookies from 'js-cookie'

import { gototal, getTreatmentData, getRemindStr, canTotal, showZySummaryService, page, saveOrUpdate, removeRows, unlock, lock, unfinish, healthSaveOrUpdate, getReviewProjects } from '@/api/inspection/occupational_inspection.js'
import { synchronize, gototalAdd, getHistoryData } from '@/api/inspection/health_inspection.js'
import { createReportData } from '@/api/preview/individual_report.js'

export default {
  name: 'OccupationalInspectionDetails',
  components: {
    departmentItems,
    remindItems,
    historyItems,
    electricalItems,
    optionsItems,
    reviewItems,
    confirmItems,
  },
  data() {
    return {
      closeDialog: false,
      open: false,
      // 对话框加载中
      dialogLoading: false,
      // 遮罩层
      loading: false,
      // 详情参数
      queryParams: {
        patientno: '', //体检号
        patientcode: '',
        patientname: undefined,
        flag: false, //是否补0
        dh: 0,
      },
      patientcode: '',
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      activeName: 'first',
      form: {
        patientname: '',
        idSex: '',
        age: '',
        patientcode: '',
        patientclass: '',
      },
      inspection: {
        checkbox: undefined,
        name: '',
        ks: '',
        zjjy: '',
        tjjy: '',
      },
      //选择输入
      isName: false,
      isKs: false,
      isZjjy: false,
      isTjjy: false,

      // 科室列表 input-select
      ksData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/total/health/getKs', //请求连接
        bindValue: '',
      },
      //结论词 input-select
      departData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/total/health/getConclusionListData', //请求连接
        bindValue: '',
      },
      tableData: [],
      concludingName: [],
      selection: [],
      isDisabledThesaurus: true,
      examstate: '',

      //控制按钮
      verdicts: true, //科室结果
      audio: true, //电测听
      lock: true, //锁定
      unlock: true, //未锁
      audit: true, //审核
      unaudit: true, //反审
      save: true, //保存
      add: true, //新增
      remove: true, //删除
      review: true, //复查
      synchronization: true, //同步
      summary: false, //综述
      advice: false, //建议
      summaryId: true, //???

      historyBtn: true, //历史按钮
      remindBtn: true, //提醒按钮
      verdicts: true, //科室结果按钮
      concludingWords: true,
      islockman: '', //锁定人
      bgGriddata: [],
      regimentationNote: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      //历史id
      historyId: '',
      reportConclusions: '',
      sectionTotal: {
        summarize: undefined,
        jkoffer: undefined,
        posistive: undefined,
        reportConclusions: undefined,
      },

      //表格多选按钮-(失效停用)
      checkedzyjjz: '', //职业禁忌症
      checkedyszyb: '', //疑似职业病
      checkedbc: '', //补查
      checkedmqwjyc: '', //目前未见异常
      checkedqtjbhyc: '', //其他疾病或异常
      checkedfc: '', //复查
      hasRemind: false,
      hasPatientno: false,
      errMsg: '',

      //列表行数据
      rowData: {},
      occupationList: [], //职业病检查结论列表
      checked: false,
      doctors: '',
      reportConclusions: '', //旧值
      hasHistory: false, //是否存在历史数据

      //查看报告
      view: true,

      // 接害因素id
      jhysId: undefined,
      // 初次打开体检号
      firstCode: undefined,
      // 保存选择的检查结论码
      occupationSummary: '',
      // 复查次数
      reviewTimes: 0,
      // 体检号改变则重新赋值综述
      changeCode: '',
      // 是否执行的同步
      isSync: false,
      // 查看报告pdf
      pdfUrl: '',
    }
  },
  created() {
    let first = this.$route.query.first
    this.tableData = []
    this.activeName = 'first'
    if (this.$route.query.obj) {
      this.rowData = JSON.parse(this.$route.query.obj)
      if (first) {
        this.queryParams.patientno = this.rowData.patientno
        this.firstCode = JSON.parse(JSON.stringify(this.rowData.patientno))
      }
      this.queryParams.patientcode = this.rowData.patientcode
      this.queryParams.flag = this.rowData.flag
      if (this.queryParams.patientcode) {
        this.goCanTotal(this.rowData) //判断有无体检号 优先顺序 1
      }
    }
    if (this.$route.query.fromIndex) {
      this.disableButton()
      this.$nextTick(() => {
        this.$refs.patientno.focus()
      })
    }
    // this.getGototalAdd(obj);//获取体检完成状态||返回按钮判断依据
  },
  methods: {
    //是否存在历史数据
    hasHistoryData() {
      let obj = {
        current: 1,
        dh: 0,
        id: this.historyId,
        size: 10,
      }
      getHistoryData(obj).then((res) => {
        if (res.data.records.length >= 1) {
          this.hasHistory = true
        } else {
          this.hasHistory = false
        }
      })
    },
    //关闭dialog
    handleCloseDialog() {
      //控制按钮重置
      this.verdicts = true //科室结果
      this.audio = true //电测听
      this.lock = true //锁定
      this.unlock = true //未锁
      this.audit = true //审核
      this.unaudit = true //反审
      this.save = true //保存
      this.add = true //新增
      this.remove = true //删除
      this.review = true //复查
      this.synchronization = true //同步
      this.summary = false //综述--只读
      this.advice = false //建议--只读
      this.summaryId = true //???
      this.historyBtn = true //历史按钮
      this.remindBtn = true //提醒按钮
      this.concludingWords = true
      this.closeDialog = false
      this.occupationList = []
      this.$emit('getList')
    },
    //按钮状态--禁止编辑
    disableButton() {
      this.audit = false
      this.unaudit = true
      this.unlock = false
      this.lock = false
      this.save = false
      this.add = false
      this.remove = false
      this.review = false
      this.synchronization = false
      this.summary = false
      this.advice = false
      this.summaryId = false
      //表格设置为只读grid.setReadOnly(true);
    },
    //电测听
    electricalAudiometryWindow() {
      this.$refs.electricalItems.electricalWindow(this.queryParams.patientcode)
    },
    //getRemindStr判断有无提醒
    getGetRemindStr() {
      getRemindStr({ patientcode: this.queryParams.patientcode || this.queryParams.patientno })
        .then((res) => {
          if (res.code == 200) {
            this.hasRemind = res.data
          }
        })
        .then(() => {
          this.getGoTotal() // 获取职业总检 展示内容 优先顺序 低
        })
        .catch(() => { })
    },
    // 生成报告
    createReport() {
      const clLoading = this.$loading({ target: '#mainBox' })
      createReportData({
        dh: 1,
        isJy: 0,
        patientcode: this.queryParams.patientcode,
        showAllImage: 0,
      })
        .then(() => {
          // 生成成功后调用详情,更新pdfUrl
          this.getGoTotal()
          this.$modal.msgSuccess('生成报告成功', '提示')
          clLoading.close()
        })
        .catch(() => {
          clLoading.close()
        })
    },
    //查看报告
    viewReportWindow() {
      if (!this.pdfUrl) {
        this.$alert('请先生成报告', '提示')
        return
      }
      let url = this.$getCookie('imgPath') + this.pdfUrl
      window.open(url, '_blank')
      return
      var query = {
        patientcode: this.queryParams.patientcode,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualDiseaseReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
    //提醒
    remindWindow() {
      this.$refs.remindItems.remindWindow(this.queryParams)
    },
    //科室小结跳转
    goDepartmentResultsWindow() {
      this.$refs.departmentItems.departmentResultsWindow(this.queryParams.patientcode)
    },
    //历史记录跳转
    historyWindow() {
      this.$refs.historyItems.historyWindow(this.historyId)
    },
    //详情界面初始化调用
    detailsWindow(obj, first) {
      this.open = true
      if (this.queryParams.patientno != this.changeCode) {

        // 如果不是淮南,则清空, 是淮南则保留,解决淮南总检审核后再次查询无值情况
        // 25-11-04 去掉情况逻辑 所有分中心都保留
        // if(Cookies.get('cid') != '402848e36b551aab016b5df90c8412e8'){
        //   this.sectionTotal.summarize = undefined
        //   this.sectionTotal.posistive = undefined
        // }

        this.sectionTotal.jkoffer = undefined
        this.sectionTotal.reportConclusions = undefined
        this.activeName = 'first'
      }
      this.tableData = []
      if (obj) {
        this.rowData = obj
        this.queryParams.patientcode = this.rowData.patientcode
        this.queryParams.patientname = this.rowData.patientname
        this.queryParams.age = this.rowData.age
        this.queryParams.idSex = this.rowData.idSex
        this.queryParams.flag = this.rowData.flag
      }
      if (first) {
        this.queryParams.patientno = obj.patientno
        this.firstCode = JSON.parse(JSON.stringify(obj.patientno))
      }
      this.goCanTotal(this.rowData) //判断有无体检号 优先顺序 1
      // this.getGototalAdd(obj);//获取体检完成状态||返回按钮判断依据
    },
    //条件判断筛选按钮
    waitInterface() {
      if (!this.queryParams.patientcode) {
        this.disableButton()
        this.unaudit = false
        this.verdicts = false
        this.remindBtn = false
        this.historyBtn = false
        this.audio = false
        return
      }
      if (this.islockman == '0') {
        //不是锁定人不能编辑
        this.$message({
          message: '不是锁定人不能编辑',
          type: 'warning',
        })
        this.unaudit = false
        this.disableButton()
      } else {
        if (this.totalfinish == 0) {
          this.unaudit = false
          if (this.FFinallocked == 0) {
            this.lock = true
            this.unlock = false
          } else if (this.FFinallocked == 1) {
            this.lock = false
            this.unlock = true
          }
        } else {
          //已审核不能编辑
          this.disableButton()
        }
      }
    },
    //有无体检号
    goCanTotal(obj) {
      var obj = {
        dh: 1, //0健康1职业
        flag: obj.flag,
        patientno: obj.patientno,
      }
      canTotal(obj)
        .then((res) => {
          if (res.code == 200) {
            this.getGetRemindStr() // 判断有无提醒 优先顺序 2
          } else {
            let str = new RegExp('error@')
            let _msg = res.msg.replace(str, '')
            this.$alert(`${_msg}`, '提示', {
              confirmButtonText: '确定',
              type: 'warning',
              callback: () => {
                this.open = false
                this.$emit('getList')
              },
            })
          }
        })
        .catch(() => { })
    },
    //获取下拉职业病检查结论
    goshowZySummaryService() {
      this.occupationList = []
      showZySummaryService().then((res) => {
        if (res.code == 200) {
          let occupationList = []
          let harm = this.queryParams.harms[0].split(':')[1]
          res.data.forEach((el) => {
            // SUMMARY-1 目前未见异常
            if (el.occupationSummaryCode == 'SUMMARY-1') {
              if (harm.includes('上岗前')) {
                el.occupationSummaryExplain = '体检类别（上岗前）本次检查未发现目标疾病，可以上岗。'
              } else if (harm.includes('在岗期间')) {
                el.occupationSummaryExplain = '体检类别（在岗期间）本次检查未发现目标疾病，可以从事当前作业。'
              } else if (harm.includes('离岗时')) {
                el.occupationSummaryExplain = '体检类别（离岗时）本次检查未发现目标疾病，可以离岗。'
              }
            }
            // SUMMARY-5 其他疾病或异常
            if (el.occupationSummaryCode == 'SUMMARY-5') {
              if (harm.includes('上岗前')) {
                el.occupationSummaryExplain = '体检类别（上岗前）可以上岗，其他疾病建议到专科医院诊治。'
              } else if (harm.includes('在岗期间')) {
                el.occupationSummaryExplain = '体检类别（在岗期间）可以从事当前作业，其他疾病建议到专科医院诊治。'
              } else if (harm.includes('离岗时')) {
                el.occupationSummaryExplain = '体检类别（离岗时）可以离岗，其他疾病建议到专科医院诊治。'
              }
            }
            occupationList.push(el)
          })
          this.occupationList = occupationList
        } else {
          this.$message({
            message: res.msg,
            type: 'warning',
          })
        }
      })
    },
    //处理意见表
    treatmentData(data) {
      this.loading = true
      var obj = {
        patientno: this.queryParams.patientcode,
      }
      getTreatmentData(obj).then((res) => {
        if (res.code == 200) {
          if (res.msg == '职业处理意见表没有值!') {
            this.tableData = []
            this.loading = false
            return
          } else {
            if (res.data) {
              this.tableData = res.data
              let offer = ''
              let harm = []
              if (this.tableData.length > 0) {
                this.tableData.forEach((el, index) => {
                  let zyjjzdm = el.zyjjzdm == '无' ? '' : el.zyjjzdm
                  if (el.occupationDiagnosis && el.occupationSummary && zyjjzdm && el.summaryText) {
                    offer += index + 1 + '、' + el.occupationDiagnosis + '：' + el.occupationSummary + '\n  ' + zyjjzdm + ',' + el.summaryText + '\n\n'
                  } else if (el.occupationDiagnosis && el.occupationSummary && zyjjzdm) {
                    offer += index + 1 + '、' + el.occupationDiagnosis + '：' + el.occupationSummary + '\n  ' + zyjjzdm + '\n\n'
                  } else if (el.occupationDiagnosis && el.occupationSummary && el.summaryText) {
                    offer += index + 1 + '、' + el.occupationDiagnosis + '：' + el.occupationSummary + '\n  ' + el.summaryText + '\n\n'
                  } else if (el.occupationDiagnosis && zyjjzdm && el.summaryText) {
                    offer += index + 1 + '、' + el.occupationDiagnosis + '\n  ' + zyjjzdm + ',' + el.summaryText + '\n\n'
                  } else if (el.occupationDiagnosis && el.summaryText) {
                    offer += index + 1 + '、' + el.occupationDiagnosis + '\n  ' + el.summaryText + '\n\n'
                  }
                  // 获取职业禁忌症数据
                  if (this.checked == '4' && el.occupationSummaryId == '4') {
                    harm.push(el.occupationDiagnosis)
                  }
                })
              }
              if (this.checked == '4') {
                if (harm.length) {
                  this.sectionTotal.reportConclusions = '职业禁忌证\n体检类别（' + this.queryParams.harms[0].split(':')[1] + '）建议调离接害因素' + harm.join('、') + '作业。'
                } else {
                  this.sectionTotal.reportConclusions = '职业禁忌证\n您有不适合从事目前作业的疾患。'
                }
              }
              getReviewProjects({ patientno: this.queryParams.patientcode }).then(({ data }) => {
                if (data && data.items) {
                  let index = this.sectionTotal.reportConclusions.indexOf('复查项目：')
                  if (index == -1) {
                    this.sectionTotal.reportConclusions += '\n复查项目：' + data.items + '\n注意事项：' + data.notes + '\n' + data.reviewTips
                  } else {
                    if (this.sectionTotal.reportConclusions[index - 1] == '\n') {
                      index = index - 1
                    } else {
                      index = index - 2
                    }
                    this.sectionTotal.reportConclusions = this.sectionTotal.reportConclusions.slice(0, index) + '\n复查项目：' + data.items + '\n注意事项：' + data.notes + '\n' + data.reviewTips
                  }
                }
              })
              this.$delete(this.sectionTotal, 'jkoffer')
              this.$set(this.sectionTotal, 'jkoffer', offer)
            }
          }
          this.loading = false
        }
      })
      // .catch(() => {})
    },
    getGoTotal() {
      var obj = {
        patientno: this.queryParams.patientcode || this.queryParams.patientno,
        flag: this.queryParams.flag,
      }
      this.reviewTimes = 0
      gototal(obj)
        .then((res) => {
          if (res.code == 200) {
            var obj = {
              patientcode: res.data.sectionTotal.patientcode,
              isVIP: res.data.isVIP,
              harms: res.data.harms || '',
              regimentationNote: res.data.regimentationNote || '',
              jkoffer: res.data.sectionTotal.jkoffer,
              offer: res.data.sectionTotal.offer,
              summaryId: res.data.sectionTotal.summaryId,
              verdict: res.data.sectionTotal.verdict,
              reportConclusions: res.data.sectionTotal.verdict || res.data.sectionTotal.reportConclusions,
              posistive: res.data.sectionTotal.posistive,
            }
            this.queryParams.patientno = res.data.peispatient.patientcode
            this.queryParams.patientname = res.data.peispatient.patientname
            this.queryParams.age = res.data.peispatient.age
            this.queryParams.idSex = res.data.peispatient.idSex
            this.rowData.patientno = res.data.peispatient.patientcode
            this.jhysId = res.data.peispatient.jhys
            this.checked = res.data.sectionTotal.summaryId
            obj.harms = obj.harms.split(';')
            if (res.data.fpdf == 1) {
              this.view = false
              this.pdfUrl = res.data.pdfUrl
            } else {
              this.view = true
            }

            this.view = false
            if (res.data.islockman === 0 || res.data.islockman == 0) {
              this.islockman = res.data.islockman
            } else {
              this.islockman = res.data.islockman
            }
            this.totalfinish = res.data.peispatient.zytjzt
            this.FFinallocked = res.data.peispatient.idGuidenurse //职业锁定判断
            this.reviewTimes = res.data.peispatient.counterreportprinted || '0' // 复查次数
            if (this.changeCode != res.data.peispatient.patientcode || this.isSync) {
              this.sectionTotal = res.data.sectionTotal
              this.sectionTotal.summarize = res.data.sectionTotal.summarize
              this.changeCode = res.data.peispatient.patientcode
              this.isSync = false
            }
            this.reportConclusions = res.data.sectionTotal.reportConclusions
            this.sectionTotal.reportConclusions = JSON.parse(JSON.stringify(res.data.sectionTotal.reportConclusions))
            this.sectionTotal.jkoffer = res.data.sectionTotal.offer //职业--职业结论及建议
            this.historyId = res.data.sectionTotal.id
            this.queryParams = { ...this.queryParams, ...obj }
            this.hasHistoryData()
            this.treatmentData() // 获取处理意见表数据 优先顺序 低
            this.$nextTick(() => {
              this.goshowZySummaryService() // 获取 处理意见表 上方 单选按钮组 优先顺序 低
              if (this.queryParams.patientcode && this.hasRemind == 'true') {
                this.remindWindow()
              }
            })
          }
        })
        .then(() => {
          this.waitInterface()
        })
    },
    //增加按钮打开职业处理意见
    optionsWindow() {
      var obj = {
        patientclass: this.queryParams.patientclass, //体检类别
        harms: this.queryParams.harms,
        patientno: this.queryParams.patientcode, //体检码
        occupationSummary: this.occupationSummary,
      }
      this.$refs.optionsItems.openOptionsWindow(obj, this.jhysId, this.tableData)
    },
    //其他单选打开职业处理意见
    optionsWindows(data, id, isCheck, autoSave) {
      this.checked = id

      if (isCheck) {
        this.$nextTick(() => {
          this.occupationList.forEach((el) => {
            if (el.inputCode == data) {
              this.sectionTotal.reportConclusions = el.occupationSummary + '\n' + el.occupationSummaryExplain
            }
          })
          if (this.sectionTotal.reportConclusions) {
            this.sectionTotal.reportConclusions = this.sectionTotal.reportConclusions.split('\n').splice(0, 2).join('\n')
          } else {
            this.sectionTotal.reportConclusions = ''
          }
          if (this.checked == '4') {
            let harm = []
            if (this.tableData.length > 0) {
              this.tableData.forEach((el, index) => {
                // 获取职业禁忌症数据
                if (el.occupationSummaryId == '4') {
                  harm.push(el.occupationDiagnosis)
                }
              })
            }
            if (harm.length) {
              this.sectionTotal.reportConclusions = '职业禁忌证\n体检类别（' + this.queryParams.harms[0].split(':')[1] + '）建议调离接害因素' + harm.join('、') + '作业。'
            } else {
              this.sectionTotal.reportConclusions = '职业禁忌证\n您有不适合从事目前作业的疾患。'
            }
          }
        })
      }
      this.occupationSummary = data.inputCode || data || ''
      getReviewProjects({ patientno: this.queryParams.patientcode }).then(({ data }) => {
        if (data && data.items) {
          let index = this.sectionTotal.reportConclusions.indexOf('复查项目：')
          if (index == -1) {
            this.sectionTotal.reportConclusions += '\n复查项目：' + data.items + '\n注意事项：' + data.notes + '\n' + data.reviewTips
          } else {
            if (this.sectionTotal.reportConclusions[index - 1] == '\n') {
              index = index - 1
            } else {
              index = index - 2
            }
            this.sectionTotal.reportConclusions = this.sectionTotal.reportConclusions.slice(0, index) + '\n复查项目：' + data.items + '\n注意事项：' + data.notes + '\n' + data.reviewTips
          }
          // 复查保存后
          if (autoSave) {
            this.$nextTick(() => {
              this.saveWindow(true)
            })
          }
        }
      })
      var obj = {
        patientclass: this.queryParams.patientclass, //体检类别
        harms: this.queryParams.harms,
        occupationSummary: data.inputCode || data || '',
        patientno: this.queryParams.patientcode, //体检码
      }
      this.$refs.optionsItems.openOptionsWindow(obj, this.jhysId)
    },
    //复查组件传值
    noticeFunc(value) {
      this.$delete(this.sectionTotal, 'reportConclusions')
      this.$set(this.sectionTotal, 'reportConclusions', this.reportConclusions + '\n' + value)
    },
    //删除选中表格数据
    deleteTableRows() {
      let obj = {
        ids: '',
        patientno: this.queryParams.patientcode,
      }
      let strIds = ''
      this.ids.forEach((el) => {
        strIds += el + ','
      })
      obj.ids = strIds.substring(0, strIds.length - 1)
      this.$confirm('是否确认删除', '提示')
        .then(() => {
          removeRows(obj).then((res) => {
            if (res.code == 200) {
              this.sectionTotal.jkoffer = res.data.offer
              this.$modal.msgSuccess('删除成功')
              this.treatmentData('1')
            } else {
              this.$message({
                message: res.msg,
                type: 'danger',
              })
            }
          })
        })
        .catch(() => { })
    },
    //复查按钮打开复查通知
    reviewWindow() {
      let obj = {}
      this.occupationList.forEach((el) => {
        if (el.id == this.checked) {
          obj.inputCode = el.inputCode
          obj.inputId = el.id
        }
      })
      obj = Object.assign({ ...obj, ...this.rowData })

      this.$refs.reviewItems.openReviewWindow(obj)
    },
    //同步
    synchronizationWindow() {
      var obj = {
        dh: 1, //职业1,健康0;
        patientno: this.queryParams.patientcode,
      }
      synchronize(obj)
        .then((res) => {
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: 'success',
            })
            this.queryParams.patientno = this.queryParams.patientcode
            this.isSync = true
            this.handleQuery()
            // this.detailsWindow(this.rowData)
          }
        })
        .catch(() => { })
    },
    // doctorsFunc($event) {
    //   this.doctors = $event[0]
    // },
    //总检
    savehealthWindow(doctors) {
      // 参数为数组
      // this.doctors = doctors
      // 参数为,连接字符串
      this.doctors = doctors.join(',')
      var obj = {
        bgFormData: {
          doctors: this.doctors,
          // 修改成总检医生id的字段名
          // 字段名:this.doctors,
          jkoffer: this.sectionTotal.jkoffer,
          posistive: this.sectionTotal.posistive,
          reportConclusions: this.sectionTotal.reportConclusions,
          summaryId: this.checked,
          summary: this.sectionTotal.summarize,
          offer: this.sectionTotal.jkoffer,
          patientCode: this.queryParams.patientcode,
          verdict: this.sectionTotal.summarize,
          // verdict: '',
        },
        bgGriddata: this.tableData,
        patientno: this.queryParams.patientcode,
        type: 2, //审核
        dh: 1, // 健康0/职业1
      }
      healthSaveOrUpdate(obj).then((res) => {
        if (res.code == 200) {
          this.queryParams.patientno = ''
          this.$nextTick(() => {
            this.$refs.patientno.focus()
          })
          this.$refs.confirmItems.closeOpenDialog(res.data.msg)
        }
      })
    },
    //保存职业意见增加界面数据
    saveWindow(skip) {
      if (skip) {
        this.executeSave()
      } else {
        this.$confirm('确定要保存吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'primary',
        })
          .then(() => {
            this.executeSave()
          })
          .catch()
      }
    },
    // 执行保存
    executeSave() {
      var obj = {
        // conclusion: this.sectionTotal.reportConclusions || '', //职业--最终建议
        // jkoffer: this.sectionTotal.jkoffer, //职业总检：健康建议
        // // offer: this.sectionTotal.jkoffer,//职业总检：健康建议
        // patientno: this.queryParams.patientcode,
        // posistive: this.sectionTotal.posistive,
        // summary: this.sectionTotal.summarize,
        // doctors: this.doctors,
        // type: this.typeCHild,
        bgFormData: {
          doctors: this.doctors,
          // 修改成总检医生id的字段名
          // 字段名:this.doctors,
          jkoffer: this.sectionTotal.jkoffer,
          posistive: this.sectionTotal.posistive,
          reportConclusions: this.sectionTotal.reportConclusions,
          summaryId: this.checked,
          summary: this.sectionTotal.summarize,
          offer: this.sectionTotal.jkoffer,
          patientCode: this.queryParams.patientcode,
          verdict: '',
        },
        // bgGriddata: this.tableData,
        patientno: this.queryParams.patientcode,
        type: 1, //保存
        dh: 1, // 健康0/职业1
      }
      healthSaveOrUpdate(obj).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: 'success',
          })
          this.detailsWindow(this.rowData)
        } else {
          this.$message({
            message: res.msg,
            type: 'danger',
          })
        }
      })
    },
    //解锁or锁定
    lockWindow(data) {
      //data==0解锁 data==1锁定
      var obj = {
        patientcode: this.queryParams.patientcode,
      }
      if (data == 0) {
        unlock(obj)
          .then((res) => {
            if (res.code == 200) {
              this.$message({
                message: res.msg,
                type: 'success',
              })
              this.FFinallocked = 0
              this.waitInterface()
            }
          })
          .catch((error) => {
            this.$alert(error, '提示', {
              confirmButtonText: '确定',
              type: 'danger',
            })
          })
          .finally(() => {
            // this.occupationList = []
            // this.tableData = []
          })
      } else if (data == 1) {
        lock(obj)
          .then((res) => {
            if (res.code == 200) {
              this.$message({
                message: res.msg,
                type: 'success',
              })
              this.lock = true
              this.unlock = false
              // this.occupationList = []
              // this.tableData = []
              this.detailsWindow(this.rowData)
            }
          })
          .catch(() => { })
      }
    },
    //审核or反审
    auditWindow(data) {
      //data==1审核 data==0反审
      if (data == 1) {
        if (!this.checked) {
          this.$alert('请选择职业结论', '提示')
          return
        }
        // let jhysList = this.queryParams.harms[2].split(',')
        // if (this.tableData.length < jhysList.length) {
        //   this.$alert('职业处理意见少于接害因素数量', '提示')
        //   return
        // }
        let sort = 999
        this.tableData.forEach((el) => {
          this.occupationList.forEach((val) => {
            if (el.occupationSummary == val.occupationSummary && sort > val.sort) {
              sort = val.sort
            }
          })
        })
        let checkSort = null
        this.occupationList.forEach((el) => {
          if (this.checked == el.id) {
            checkSort = el.sort
          }
        })
        if (checkSort != sort) {
          this.$alert('最终结论与子结论不匹配', '提示')
          return
        }
        this.$refs.confirmItems.openConfirmWindow(this.queryParams.patientcode)
      } else if (data == 0) {
        var obj = {
          patientno: this.queryParams.patientcode,
        }
        unfinish(obj).then((res) => {
          if (res.code == 200) {
            this.$alert('反审成功', '提示', {
              confirmButtonText: '确认',
              callback: () => {
                this.audit = true
                this.unaudit = false
                this.FFinallocked = 0
                this.totalfinish = 0
                this.summaryId = true
                this.add = true
                this.remove = true
                this.review = true
                this.waitInterface()
                this.$message({
                  message: res.msg,
                  type: 'success',
                })
              },
            })
          }
        })
      }
    },
    //阻止事件冒泡
    stopPropagation(event) {
      if (e && e.stopPropagation) {
        e.stopPropagation()
      }
      this.handleQuery()
    },
    //搜索栏查询
    handleQuery() {
      if (!this.queryParams.patientno) {
        this.$alert('请输入体检号', '提示', {
          confirmButtonText: '确定',
        })
        return
      }
      var tableList = {}
      this.occupationList = []
      var flag = false
      if (this.queryParams.patientno.length < 12) {
        flag = true
      }
      var pageObj = {
        patientcode: this.queryParams.patientno,
        autoFill: true
      }
      page(pageObj)
        .then((res) => {
          if (res.code == 200) {
            tableList = res.data.records[0]
          }
        })
        .then(() => {
          this.handleCloseDialog()
          var canTotalObj = {
            ...tableList,
            dh: 1, //0健康1职业
            flag, //true补零false不加
            patientno: this.queryParams.patientno,
          }
          this.detailsWindow(canTotalObj)
        })
    },
    //表单选中
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //重置
    resetQuery() {
      this.queryParams.patientno = this.firstCode
      this.handleQuery()
    },
    //切换标签页
    handleClick() { },
  },
}
</script>

<style lang="scss">
.health-inspection-details {
  .header-harms {
    display: inline-block;
    vertical-align: top;
    padding: 0 8px;
    height: 28px;
    line-height: 28px;
    font-size: 15px;
    font-weight: 600;
    color: #000;

    &.text-ellipsis {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      max-width: 100%;
    }
  }

  .audit-state {
    padding: 2px 8px;
    font-size: 20px;
    color: #fff;
    font-weight: 600;
    border-radius: 4px;

    &.green {
      background-color: green;
    }

    &.red {
      background-color: #d41318;
    }
  }

  .tabs {
    flex: 1;

    .tabs-item {
      display: inline-block;
      overflow-y: auto;
      width: 100%;
      height: 100%;
      padding: 10px;
      margin-right: 2%;
      border: 1px solid rgb(225, 225, 225);
      white-space: pre-wrap;
      font-size: 24px;
    }
  }

  .el-descriptions--medium:not(.is-bordered) .el-descriptions-item__cell {
    line-height: 29px;
  }

  .add-table {
    border: 1px solid #d4d6d9;

    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px;
        text-align: left;
      }
    }
  }
}
</style>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.tabs-item /deep/ .el-textarea__inner {
  border: 0;
  height: 100%;
}

/* .tabs-item /deep/ .el-tabs.el-tabs--top {
  height: 100% !important;
} */
.tabs /deep/ .el-tabs__content {
  height: calc(100% - 55px) !important;
}

.align {
  /* height: 36px; */
  line-height: 28px;
}

#setMrgin {
  padding: 0 8px;
}

#plate /deep/ .el-textarea__inner {
  border: 0;
  width: calc(100% - 30px);
  height: 100%;
  overflow: hidden;
  padding: 0;
  margin: 5px 15px;
}

#finall {
  flex: 1;
  border: 1px solid #c4c4c4;
}

#finall /deep/ .el-textarea__inner {
  overflow: auto;
}

.health-inspection-details /deep/ .el-button span {
  font-size: 16px;
}

.health-inspection-details /deep/ .el-table__row .cell {
  font-size: 14px;
  color: #000;
}
</style>
