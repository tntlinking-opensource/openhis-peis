<!-- 健康总检-查看详情 麦沃德科技 开发人:清风/予安 -->
<template>
  <div class="app-container flex-direction-column health-inspection-details">
    <!-- AI智能总检弹窗 -->
    <AiInspection
      :visible.sync="aiChatVisible"
      :initial-text="aiInitialText"
      :title="`AI智能总检`"
      :close-on-click-modal="false"
      @result="handleAiResult"
    />
    <div style="display: flex; justify-content: space-between">
      <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom" @submit.native.prevent>
        <el-form-item label="读卡" prop="patientno">
          <el-input ref="patientno" placeholder="请输入体检号" style="width: 160px" @keyup.enter.native="handleQuery" clearable v-model="queryParams.patientno" @change="handleChange" @clear="setFocus"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
        <el-form-item label="姓名" style="margin-left: 32px">
          <div style="font-size: 20px">{{ form.patientname }}</div>
        </el-form-item>
        <el-form-item label="性别" style="margin-left: 32px">
          <div style="font-size: 20px" v-if="form.idSex == 0">男</div>
          <div style="font-size: 20px" v-else>女</div>
        </el-form-item>
        <el-form-item label="年龄" style="margin-left: 32px">
          <div style="font-size: 20px">{{ form.age }}</div>
        </el-form-item>
        <el-form-item label="体检号" style="margin-left: 40px" label-width="80px">
          <div style="font-size: 20px">{{ form.patientcode }}</div>
        </el-form-item>
        <el-form-item label="会员类型" style="margin-left: 32px" label-width="100px">
          <template>
            <el-tag type="info" v-if="form.idPatientclass == '1'" class="el-icon-user" style="font-size: 20px">普通会员</el-tag>
            <el-tag v-else-if="form.idPatientclass == '2'" class="el-icon-user" style="font-size: 20px">VIP会员</el-tag>
            <el-tag v-else-if="form.idPatientclass == '3'" class="el-icon-user" style="font-size: 20px">VVIP会员</el-tag>
          </template>
        </el-form-item>
      </el-form>
      <!-- <el-descriptions :column="6" style="width: 1000px">
          <el-descriptions-item label=" "></el-descriptions-item>
          <el-descriptions-item class="align-height" label="姓名">{{ form.patientname || '' }}</el-descriptions-item>
          <el-descriptions-item class="align-height" label="性别">{{ form.idSex == 0 ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item class="align-height" label="年龄">{{ form.age || '' }}</el-descriptions-item>
          <el-descriptions-item class="align-height" label="体检号">{{ form.patientcode || '' }}</el-descriptions-item>
          <el-descriptions-item class="align-height" label="会员类型">
            <el-tag type="danger" v-if="form.idPatientclass != '1'" class="el-icon-user">vip会员</el-tag>
            <el-tag type="info" v-else class="el-icon-user">普通会员</el-tag>
          </el-descriptions-item>
        </el-descriptions> -->
    </div>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8 left-btns">
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-coin" plain @click="departmentResultsWindow" :disabled="verdicts" v-hasPermi="['inspection:healthInspection:details:departmentResults']">科室结果</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="remindBtnWindow" :disabled="remindBtn" v-hasPermi="['inspection:healthInspection:details:remindBtn']">提醒</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" @click="historyBtnWindow" v-if="hasHistory" :disabled="historyBtn" v-hasPermi="['inspection:healthInspection:details:historyBtn']">历史</el-button>
        <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="historyBtnWindow" v-else :disabled="historyBtn" v-hasPermi="['inspection:healthInspection:details:historyBtn']">历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-refresh-right" plain @click="freshWindow" v-hasPermi="['inspection:healthInspection:details:fresh']">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-document-add" plain @click="createReport" v-hasPermi="['inspection:healthInspection:details:checkReport']">生成报告</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-search" plain @click="viewReport" :disabled="view" v-hasPermi="['inspection:healthInspection:details:viewReport']">查看报告</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="synchronizationWindow" :disabled="synchronization" v-hasPermi="['inspection:healthInspection:details:synchronization']">同步</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-video-play" plain @click="lockWindow(1)" :disabled="lockBtn" v-hasPermi="['inspection:healthInspection:details:lock']">锁定</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-video-pause" plain @click="lockWindow(0)" :disabled="unlockBtn" v-hasPermi="['inspection:healthInspection:details:unlock']">解锁</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-refresh-right" @click="auditWindow(1)" :disabled="audit" v-hasPermi="['inspection:healthInspection:details:audit']">审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-refresh-left" plain @click="auditWindow(0)" :disabled="unaudit" v-hasPermi="['inspection:healthInspection:details:unaudit']">反审</el-button>
      </el-col>
      <el-col :span="1.5">
        <span class="audit-state green" v-if="audit">已审核</span>
        <span class="audit-state red" v-else>未审核</span>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-cpu" plain @click="openAiAnalysis" :disabled="!form.patientcode">AI智能总检</el-button>
      </el-col>
    </el-row>
    <div class="tabs" id="mainBox">
      <div class="tabs-left">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="综述" name="first"> </el-tab-pane>
          <el-tab-pane label="健康建议" name="second"> </el-tab-pane>
        </el-tabs>
        <div class="tabs-item">
          <el-input type="textarea" style="height: 100%" resize="none" readonly :value="activeName == 'first' ? detailsSummarize : detailsOffer"></el-input>
        </div>
      </div>
      <div class="tabs-right" id="rightBox">
        <div class="add-table">
          <div class="table-box" style="min-height: 240px">
            <el-table ref="tableData" :data="tableData" v-loading="loading" border size="small" height="100%" @row-click="rowClickHandle" @selection-change="handleSelectionChange">
              <el-table-column type="selection" align="center" width="40"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="flag" label="不出现" align="center" width="70">
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.flag" :disabled="see" true-label="0" false-label="1" @change="handleChangeFlag"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="结论词" align="center" min-width="120px">
                <template slot-scope="scope">
                  <input-select
                      :ref="'departData' + scope.$index"
                      :selectData="departData"
                      :disabled="concludingWords || scope.row.mergeName ? true : false"
                      :hiddenClear="true"
                      :initialValue="scope.row.basconclusionName"
                      :current-index="scope.row.sort"
                      :notShowEmpty="true"
                      :showTooltip="true"
                      selectWidth="100%"
                      @focus="handlerClickInput(scope.row)"
                      @change="departChange"
                    ></input-select>
                    <!-- 2024-10-16 去掉气泡提示框 -->
                  <!-- <el-tooltip popper-class="tooltip-item" effect="dark" :content="scope.row.basconclusionName" placement="left">
                    <input-select
                      :ref="'departData' + scope.$index"
                      :selectData="departData"
                      :disabled="concludingWords || scope.row.mergeName ? true : false"
                      :hiddenClear="true"
                      :initialValue="scope.row.basconclusionName"
                      :current-index="scope.row.sort"
                      :notShowEmpty="true"
                      :showTooltip="true"
                      selectWidth="100%"
                      @focus="handlerClickInput(scope.row)"
                      @change="departChange"
                    ></input-select>
                  </el-tooltip> -->
                </template>
              </el-table-column>
              <el-table-column prop="division" label="分检科室" align="center" width="90" show-overflow-tooltip=""></el-table-column>
              <el-table-column prop="creater" label="维护人" align="center" width="90"></el-table-column>
              <el-table-column prop="mergeName" label="合并结论词" align="center" width="110" show-overflow-tooltip></el-table-column>
            </el-table>
          </div>
          <el-row :gutter="10" class="mb8 tabs-btn" style="margin: 10px 5px 0">
            <el-col :span="1.5">
              <el-button size="mini" type="primary" icon="el-icon-plus" @click="addTable()" :disabled="add" v-hasPermi="['inspection:healthInspection:add']">增加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="danger" icon="el-icon-delete" plain @click="removeTable()" :disabled="remove" v-hasPermi="['inspection:healthInspection:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="isMove" plain @click="moveWindow('up')" v-hasPermi="['inspection:healthInspection:unlock']">上移</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="isMove" plain @click="moveWindow('down')" v-hasPermi="['inspection:healthInspection:unlock']">下移</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="getMergeWindow()" :disabled="merge" v-hasPermi="['inspection:healthInspection:unlock']">合并</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="danger" icon="el-icon-video-play" plain @click="unlockWindow()" :disabled="split" v-hasPermi="['inspection:healthInspection:unlock']">拆分</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="primary" icon="el-icon-video-play" @click="getCommitSign()" :disabled="isSave" v-hasPermi="['inspection:healthInspection:unlock']">保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="warning" icon="el-icon-video-play" :disabled="isDisabledThesaurus" plain @click="getMaintain()" v-hasPermi="['inspection:healthInspection:unlock']">存入词库</el-button>
            </el-col>
          </el-row>
        </div>
        <div class="inspection-details-RD" style="width: 100%; margin: 0; border: 1px solid rgb(225, 225, 225)">
          <el-form style="padding: 8px 16px" :inline="true" :model="inspection" class="no-margin-bottom">
            <div style="display: flex; align-items: center">
              <span style="margin-right: 16px; font-size: 22px">总检建议录入</span>
              <el-radio-group v-model="inspection.checkbox">
                <el-radio label="新增" :true-label="1" @click.native.prevent="onClickAdd('新增')"></el-radio>
                <el-radio label="微调结论" :true-label="0" @click.native.prevent="onClickAdd('微调结论')"></el-radio>
              </el-radio-group>
            </div>
            <el-form-item label="结论词名称">
              <el-input style="width: 360px" :readonly="!isZjjy" v-model="inspection.name" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="科室">
              <input-select ref="ksData" :selectData="ksData" :initialValue="inspection.ks" :disabled="!isKs" @change="ksChange"></input-select>
            </el-form-item>
            <el-form-item label="总检建议" style="width: 100%">
              <el-input type="textarea" :readonly="!isZjjy" style="width: 100%" v-model="inspection.zjjy" placeholder="请输入" :rows="4"></el-input>
            </el-form-item>
            <el-form-item label="团检建议" style="width: 100%">
              <el-input type="textarea" :readonly="!isTjjy" style="width: 100%" v-model="inspection.tjjy" placeholder="请输入" :rows="2"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <remindItems ref="remindItems"></remindItems>
    <historyItems ref="historyItems"></historyItems>
    <departmentResultsItems ref="departmentResultsItems"></departmentResultsItems>
  </div>
</template>

<script>
import remindItems from './remind.vue'
import historyItems from './history.vue'
import departmentResultsItems from './department_results.vue' //科室结果
import AiInspection from '@/views/ai/ai-inspection/index.vue'

import { listHealth, canTotal, isLock, getVerdictListData, commitSign, synchronize, saveOrUpdate, getHistoryData, maintain, gototal, getMerge, getBreakUp, getMergeConbination, gototalAdd, unfinish } from '@/api/inspection/health_inspection.js'
import { createReportData } from '@/api/preview/individual_report.js'
export default {
  name: 'Health_inspection_details',
  components: { remindItems, departmentResultsItems, historyItems, AiInspection },
  data() {
    return {
      // AI智能总检相关
      aiChatVisible: false,
      aiInitialText: '',
      aiAnalysisResult: '',
      // 对话框加载中
      dialogLoading: false,
      // 遮罩层
      loading: false,
      // 详情参数
      queryParams: {
        patientno: '',
        patientcode: '',
        flag: '',
        dh: 0,
      },
      patientcode: '',
      // 详情数据
      detailsSummarize: '',
      conclusion: '',
      detailsOffer: '',
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
        idPatientclass: '1',
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
        queryData: 'srm',
      },
      //结论词 input-select
      departData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '结论词', //第二列标题
        url: '/total/health/getConclusionListData', //请求连接
        bindValue: '',
        queryData: 'srm',
        fontSize: '16',
      },
      tableData: [],
      concludingName: [],
      selection: [],
      isDisabledThesaurus: true,
      examstate: '',

      //控制按钮
      historyBtn: false, //历史按钮
      remindBtn: false, //提醒按钮
      verdicts: false, //科室结果按钮
      lockBtn: false,
      unlockBtn: false,
      audit: false,
      unaudit: false,
      add: false,
      remove: false,
      synchronization: false,
      concludingWords: false,
      see: false,
      isMove: false,
      isSave: false, //保存按钮是否 可用
      merge: false,
      split: false,
      view: false,
      //获取历史记录id
      historyId: '',
      ksId: '',
      islockman: undefined,
      bgGriddata: [],
      tableIndex: [],
      mergeRow: undefined,
      conIds: undefined,
      isMerge: undefined,
      creater: undefined,
      beforeCheckbox: '',

      hasHistory: false, //是否有历史数据

      // 接口3秒调用
      countSecond: true,
      // 标签名称
      tabName: 1,
      // 体检号改变则重新赋值综述
      changeCode: '',
      // 初次打开页面
      firstShow: false,
      // 审核定时器
      auditTimer: false,
      // 清空输入框
      emptyInput: false,
      // 结论词执行保存
      jlcSave: false,
      jlcRowIndex: -1,
      // 查看报告pdf
      pdfUrl: '',
    }
  },
  created() {
    let patientcode = this.$route.query.patientcode
    this.initialBtn()
    this.queryParams.flag = this.$route.query.flag
    this.queryParams.patientno = patientcode
    this.queryParams.patientcode = patientcode
    this.patientcode = patientcode
    this.examstate = this.$route.query.examstate
    this.activeName = 'first'
    this.firstShow = true
    if (patientcode) {
      this.getCanTotal(patientcode, this.$route.query.flag) //是否可以总检 接口调用优先顺序 1
    } else {
      this.$nextTick(() => {
        this.$refs.patientno.focus()
      })
    }
  },
  activated() {
    if (!this.firstShow && !this.audit && this.$getCookie('cid') == '2' && this.form.patientcode) {
      this.$confirm('当前体检号您未审核，是否确认审核?', '提示', {
        confirmButtonText: '审核',
      })
        .then(() => {
          this.auditWindow(1)
        })
        .catch(() => {})
    } else {
      this.firstShow = false
    }
  },
  methods: {
    // 打开AI智能总检弹窗
    openAiAnalysis() {
      // 检查是否有顾客信息
      if (!this.form.patientcode) {
        this.$message.warning('没有顾客信息，无法进行AI智能总检')
        return
      }

      // 收集总检数据和体检者信息
      const patientInfo = this.form
      const summarize = this.detailsSummarize || ''
      const offer = this.detailsOffer || ''

      // 收集结论词数据
      let resultsTable = ''

      // 如果没有表格数据，但有总检数据，也使用这些数据
      if (resultsTable.trim() === '') {
        if (summarize) {
          resultsTable += `总检综述：\n${summarize}\n\n`
        }
        if (offer) {
          resultsTable += `总检结论词汇总：\n${offer}`
        }
      }

      if (resultsTable.trim() === '') {
        this.$message.warning('没有体检数据，无法进行AI智能总检')
        return
      }

      // 准备AI总检的提示文本，使用新的JSON格式
      const promptText = `顾客信息：
- 姓名：${patientInfo.patientname || '未知'}
- 性别：${patientInfo.idSex == 0 ? '男' : patientInfo.idSex == 1 ? '女' : '未知'}
- 年龄：${patientInfo.age || '未知'}
体检数据和结果：
${resultsTable}`;
      
      // 设置初始文本并打开对话框
      this.aiInitialText = promptText;
      this.aiChatVisible = true;
    },

    // 处理AI总检结果
    handleAiResult(result) {
      this.aiAnalysisResult = result;
      console.log('从 AI 组件接收到的结果:', result);

      let summarize = '';
      let offer = '';

      try {
        // 如果结果是JSON对象
        let jsonData = null;
        if (typeof result === 'object') {
          jsonData = result;
          console.log('接收到已编辑的JSON对象:', jsonData);
        } else {
          // 尝试从文本中提取JSON
          const jsonStart = result.indexOf('{');
          const jsonEnd = result.lastIndexOf('}') + 1;

          if (jsonStart >= 0 && jsonEnd > jsonStart) {
            const jsonStr = result.substring(jsonStart, jsonEnd);
            jsonData = JSON.parse(jsonStr);
            console.log('从文本中提取并解析的JSON:', jsonData);
          }
        }

        if (jsonData) {
          // 从新格式中提取总检描述和建议
          const issues = jsonData.issuesAndConclusions?.textarea || '';
          const interventional = jsonData.interventionAndPrecautions?.title || '';
          const diet = jsonData.interventionAndPrecautions?.diet?.textarea || '';
          const habits = jsonData.interventionAndPrecautions?.habits?.textarea || '';
          const followupAndMeds = jsonData.interventionAndPrecautions?.followupAndMeds?.textarea || '';
          const reviewAndReminders = jsonData.reviewAndReminders?.title || '';
          const regularReviews = jsonData.reviewAndReminders?.regularReviews?.textarea || '';
          const urgentSituations = jsonData.reviewAndReminders?.urgentSituations?.textarea || '';
          const specialistFollowups = jsonData.specialistFollowups?.textarea || '';

          // 组合总检描述
          // summarize = issues;

          // 组合健康建议
          offer = '';
          //主要健康问题总结
          if (issues) offer += '【'+jsonData.issuesAndConclusions.title+'】\n    ' + issues.replace(/\n/g, '\n    ') + '\n\n';

          // //日常干预与注意事项
          if (interventional) offer += '【'+interventional+'】\n';
          if (diet) offer += '    · 饮食管理\n        ' + diet.replace(/\n/g, '\n        ') + '\n\n';
          if (habits) offer += '    · 生活习惯\n        ' + habits.replace(/\n/g, '\n        ') + '\n\n';
          if (followupAndMeds) offer += '    · 专科随访与用药\n        ' + followupAndMeds.replace(/\n/g, '\n        ') + '\n\n';

          // //复查与就医提醒
          if (reviewAndReminders) offer += '【'+reviewAndReminders+'】\n';
          if (regularReviews) offer += '    · 必须定期复查的项目\n        ' + regularReviews.replace(/\n/g, '\n        ') + '\n\n';
          if (urgentSituations) offer += '    · 需要及时就医的情况\n        ' + urgentSituations.replace(/\n/g, '\n        ') + '\n\n';

          // //专科随访
          if (specialistFollowups) offer += '【建议定期做的专科随访】\n    ' + specialistFollowups.replace(/\n/g, '\n    ') + '\n\n';

          // console.log('处理后的总检描述:', summarize);
          console.log('处理后的健康建议:', offer);
        } else {
          // 如果不是JSON格式，尝试使用旧的提取方式
          const summarizeMatch = result.match(/##\s*\u603b\u68c0\u63cf\u8ff0[\s\S]*?(?=##\s*\u603b\u68c0\u5efa\u8bae|$)/i);
          const offerMatch = result.match(/##\s*\u5065\u5eb7\u5efa\u8bae[\s\S]*?$/i);

          if (summarizeMatch) {
            summarize = summarizeMatch[0].replace(/##\s*\u603b\u68c0\u63cf\u8ff0/i, '').trim();
          }

          if (offerMatch) {
            offer = offerMatch[0].replace(/##\s*\u5065\u5eb7\u5efa\u8bae/i, '').trim();
          }

          // 如果提取失败，则使用全部结果
          if (!summarize && !offer) {
            summarize = result;
          }
        }
      } catch (error) {
        console.error('解析AI总检结果失败:', error);
        summarize = result;
      }

      // 弹出确认框询问是否应用AI总检结果
      this.$confirm('是否将AI智能总检结果应用到当前总检中？', '提示', {
        confirmButtonText: '应用',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 应用总检描述和健康建议
        // if (summarize) {
        //   this.detailsSummarize = summarize;
        // }

        if (offer) {
          this.detailsOffer = offer;
        }

        this.$message.success('AI智能总检结果已应用');
      }).catch(() => {
        this.$message.info('已取消应用AI智能总检结果');
      });
    },

    // 同步体检号参数
    handleChange() {
      this.queryParams.patientcode = this.queryParams.patientno
    },
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
    detailsWindow(patientcode, flag, examstate) {
      this.lockBtn = true
      this.unlockBtn = false
      this.audit = false
      this.unaudit = false
      this.initialBtn()
      this.queryParams.flag = flag
      this.queryParams.patientno = patientcode
      this.queryParams.patientcode = patientcode
      this.patientcode = patientcode
      this.examstate = examstate
      // this.activeName = 'first'
      this.getCanTotal(patientcode, flag) //是否可以总检 接口调用优先顺序 1
    },
    getgototalAdd(patientcode) {
      var obj = {
        flag: true,
        patientCode: patientcode,
      }
      this.getTest(patientcode) // 判断接口状态 优先顺序 2
    },
    //获取详情页面按钮状态
    getTest(data) {
      var obj = { patientno: data, flag: true }
      //test 接口停用: 里面的数据gototal里都有
      gototal(obj).then((res) => {
        if (res.code == 200) {
          if (this.changeCode != this.queryParams.patientno) {
            this.changeCode = this.queryParams.patientno
          }
          this.detailsSummarize = res.data.sectionTotal.summarize
          this.islockman = res.data.islockman
          //判断是否有报告
          if (res.data.fpdf == 1) {
            // 1 存在 空 不存在
            this.view = false
            this.pdfUrl = res.data.pdfUrl
          } else {
            this.view = true
          }
          if (!this.patientcode) {
            this.unaudit = true
            this.verdicts = true
            this.remindBtn = true
            this.historyBtn = true
            this.btncontrol()
            return
          }
          // 不是锁定人不能编辑
          if (Number(this.islockman) === 0) {
            this.$alert(res.data.msg, '提醒', {
              confirmButtonText: '确定',
              type: 'primary',
            })
            this.unaudit = true
            this.btncontrol()
          } else {
            if (res.data.peispatient.jktjzt == 0) {
              this.unaudit = true
              if (res.data.peispatient.ffinallocked == 0) {
                this.lockBtn = false //锁定true 解锁false
                this.unlockBtn = true
              } else if (res.data.peispatient.ffinallocked == 1) {
                this.lockBtn = true
                this.unlockBtn = false
              }
            } else {
              this.btncontrol()
            }
          }
          if (!this.jlcSave) {
            this.activeName = 'first'
          }
          this.historyId = res.data.sectionTotal.id
          this.hasHistoryData()
          this.verdictListData(this.patientcode) // 获取表格数据 优先顺序 低
          this.form = res.data.peispatient
          if (!this.emptyInput) {
            this.queryParams.patientno = this.form.patientcode
            this.patientcode = this.form.patientcode
          } else {
            this.queryParams.patientno = ''
          }
          this.emptyInput = false
        }
      })
    },
    //按钮控制
    btncontrol() {
      //控制按钮
      this.lockBtn = true
      this.unlockBtn = true
      this.audit = true
      this.add = true
      this.remove = true
      this.synchronization = true
      this.concludingWords = true
      this.see = true
      this.isMove = true
      this.isSave = true
      this.merge = true
      this.split = true
      this.isDisabledThesaurus = true //存入词库
    },
    //初始化按钮
    initialBtn() {
      this.inspection = {
        checkbox: '',
        name: '',
        ks: '',
        zjjy: '',
        tjjy: '',
      }
      this.view = false
      this.add = false
      this.remove = false
      this.isMove = false
      this.isSave = false
      this.merge = false
      this.split = false
      this.isDisabledThesaurus = true
      this.synchronization = false //同步
      this.see = false
      this.concludingWords = false

      // 初始化综述、健康建议
      if (this.changeCode != this.queryParams.patientno) {
        this.detailsSummarize = ''
      }
      this.detailsOffer = ''
    },
    //保存
    getCommitSign() {
      for (var i = 0; i < this.bgGriddata.length; i++) {
        let row = this.bgGriddata[i]
        if (typeof row.id == 'undefined' && !row.basconclusionName) {
          this.$alert('请选择结论词', '提示')
            .then(() => {
              this.$nextTick(() => {
                this.$refs['departData' + i].setFocus()
              })
            })
            .catch(() => {
              this.$nextTick(() => {
                this.$refs['departData' + i].setFocus()
              })
            })
          return
        }
      }
      this.$confirm('确定要保存吗？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.handleExecuteSave()
        })
        .catch(() => {})
    },
    // 执行保存
    handleExecuteSave() {
      // if(this.inspection.checkbox){
      this.bgGriddata.forEach((el, index) => {
        if (el.id == this.inspection.id) {
          // this.bgGriddata[index].basconclusionName = this.inspection.name
          // this.bgGriddata[index].division = this.inspection.division
          // this.bgGriddata[index].divisionId = this.inspection.ksId
          // this.bgGriddata[index].totalAdvice = this.inspection.zjjy
          this.bgGriddata[index].tjjy = this.inspection.tjjy
        }
      })
      // }
      // this.tableData.forEach((el, i) => {
      //   this.bgGriddata.forEach((els, index) => {
      //     if (el.id) {
      //       if (el.basconclusionId == els.basconclusionId && el.id == els.id) {
      //         this.bgGriddata[index].see = el.flag
      //         this.bgGriddata[index].tjjy = el.tjjy
      //         this.bgGriddata[index].basconclusionName = el.basconclusionName
      //         this.bgGriddata[index].basconclusion = el.basconclusion
      //         this.bgGriddata[index].division = el.division
      //         this.bgGriddata[index].divisionId = el.divisionId
      //         this.bgGriddata[index].totalAdvice = el.totalAdvice
      //       }
      //     } else {
      //       if (el.basconclusionId == els.basconclusionId) {
      //         this.bgGriddata[index].see = el.flag
      //         this.bgGriddata[index].tjjy = el.tjjy
      //         this.bgGriddata[index].basconclusionName = el.basconclusionName
      //         this.bgGriddata[index].basconclusion = el.basconclusion
      //         this.bgGriddata[index].division = el.division
      //         this.bgGriddata[index].divisionId = el.divisionId
      //         this.bgGriddata[index].totalAdvice = el.totalAdvice
      //       }
      //     }
      //   })
      // })
      let data = this.bgGriddata
      for (var i = 0; i < data.length; i++) {
        let row = data[i]
        row.sort = i
        if (row.state == 'added' && typeof row.id == 'undefined') {
          row.state = 'added'
          this.bgGriddata[i] = row
        } else if (row.state == 'removed') {
          row.state = 'removed'
          this.bgGriddata[i] = row
        } else {
          row.state = 'modified'
          this.bgGriddata[i] = row
        }
        this.bgGriddata[i].see = row.flag
      }
      let griddata = this.bgGriddata
      let conclusion = ''
      this.bgGriddata.forEach((el, index) => {
        conclusion += index + 1 + '、' + el.division + ':' + el.basconclusionName + '\n\n'
      })
      var advice = this.detailsOffer //建议
      var formdata = {
        verdict: conclusion,
        offer: advice,
        patientCode: this.patientcode,
      }
      saveOrUpdate({
        bgFormData: formdata,
        bgGriddata: griddata,
        type: 1, //1保存2审核
        dh: 0,
      })
        .then((res) => {
          if (res.code == 200) {
            this.inspection = {
              checkbox: '',
            }
          }
        })
        .then(() => {
          this.audit = false
          this.unaudit = true
          this.$modal.msgSuccess('保存成功')
          this.detailsWindow(this.patientcode)
          // this.verdictListData(this.patientcode, this.queryParams.flag);
        })
    },
    //是否可以总检
    getCanTotal(patientcode, flag) {
      this.queryParams = {
        patientno: patientcode,
        flag: flag || true,
        dh: 0, //健康总检0/职业总检1
      }
      canTotal(this.queryParams)
        .then((res) => {
          if (res.code == 500) {
            let str = new RegExp('error@')
            let _msg = res.msg.replace(str, '')
            this.$alert(`${_msg}`, '提醒', {
              confirmButtonText: '确认',
              type: 'warning',
            }).then(() => {
              // 关闭details
            })
          } else {
            this.getgototalAdd(patientcode) // 辅助判断 接口状态 优先顺序1.5
          }
        })
        .catch(() => {
          this.getgototalAdd(this.patientcode) // 辅助判断 接口状态 优先顺序1.5
        })
    },
    verdictListData(patientcode) {
      var obj = {
        dh: 0,
        patientno: patientcode,
      }
      getVerdictListData(obj)
        .then((res) => {
          let gb = []
          let bgGriddata = res.data
          this.bgGriddata = []
          this.tableData = res.data
          let offer = ''
          let index = 1
          this.tableData.forEach((el) => {
            if (el.basconclusionName && el.totalAdvice && el.flag == '1') {
              offer += index++ + '、' + el.basconclusionName + '\n  ' + el.totalAdvice + '\n\n'
            } else if (el.basconclusionName && el.flag == '1') {
              offer += index++ + '、' + el.basconclusionName + '\n\n'
            }
          })
          this.detailsOffer = offer

          bgGriddata.forEach((el) => {
            gb = Object.assign(el, {
              state: 'modified',
            })
            this.bgGriddata.push(gb)
          })
          for (var i = 0; i < this.tableData.length; i++) {
            this.tableData[i].state = ''
            this.tableData[i].sort = i
            this.bgGriddata[i].state = ''
            this.bgGriddata[i].sort = i
          }
          for (var i = 0; i < this.tableData.length; i++) {
            this.concludingName[i] = {
              placeholder: '请输入输入码选择',
              key: '输入码', //第一列标题
              value: '科室', //第二列标题
              url: '/total/health/getConclusionListData', //请求连接
              bindValue: '',
            }
          }

          if (this.jlcSave) {
            this.$nextTick(() => {
              this.$refs.tableData.toggleRowSelection(this.tableData[this.jlcRowIndex], true)
              this.jlcRowIndex = -1
              this.jlcSave = false
            })
          }
        })
        .catch(() => {})
    },
    // 清除信息时重新聚焦
    setFocus() {
      this.queryParams.patientno = ''
      this.$nextTick(() => {
        this.$refs.patientno.focus()
      })
    },
    // 获取详情
    getList() {
      this.dialogLoading = false
    },
    // 搜索
    handleQuery() {
      if (!this.queryParams.patientno) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      if (!this.audit && this.$getCookie('cid') == '2' && this.form.patientcode) {
        this.$confirm('当前体检号您未审核，是否确认审核?', '提示', {
          confirmButtonText: '审核',
          cancelButtonText: '搜索',
        })
          .then(() => {
            this.auditWindow(1)
          })
          .catch(() => {
            if (this.countSecond) {
              this.countSecond = false
              this.queryParams.patientcode = this.queryParams.patientno
              this.detailsWindow(this.queryParams.patientcode)
              setTimeout(() => {
                this.countSecond = true
              }, 3000)
            }
          })
      } else {
        if (this.countSecond) {
          this.countSecond = false
          this.queryParams.patientcode = this.queryParams.patientno
          this.detailsWindow(this.queryParams.patientcode)
          setTimeout(() => {
            this.countSecond = true
          }, 3000)
        }
      }
    },
    // 重置
    resetQuery() {
      this.queryParams.patientno = this.patientcode
      this.handleQuery()
    },

    handleClick(tab) {
      if (tab.label == '综述') {
        this.tabName = 1
      } else {
        this.tabName = 2
      }
    },
    // 切换是否出现
    handleChangeFlag() {
      this.$nextTick(() => {
        this.handleExecuteSave()
      })
    },
    // 选择结论词
    departChange(value, sort) {
      let error = ''
      let hasId = false
      this.tableData.forEach((el, index) => {
        if (value.name === el.basconclusionName && index != sort) {
          error = '存在重复结论词名称'
          if (el.id) {
            hasId = true
          }
        }
      })
      if (error) {
        this.$alert(error, '提示', {
          type: 'warning',
        })
        if (hasId) {
          this.$refs['departData' + sort].initData(this.tableData[sort].basconclusionName)
        } else {
          this.$refs['departData' + sort].initData()
        }
        return
      }
      for (let i in this.tableData) {
        if (sort == this.tableData[i].sort) {
          this.$delete(this.tableData[i], 'basconclusionId')
          this.$delete(this.tableData[i], 'basconclusionName')
          this.$delete(this.tableData[i], 'basconclusion')
          this.$delete(this.tableData[i], 'division')
          this.$delete(this.tableData[i], 'divisionId')
          this.$delete(this.tableData[i], 'totalAdvice')

          this.$set(this.tableData[i], 'basconclusionId', value.id)
          this.$set(this.tableData[i], 'basconclusionName', value.name)
          this.$set(this.tableData[i], 'basconclusion', value.name)
          this.$set(this.tableData[i], 'division', value.deptName)
          this.$set(this.tableData[i], 'divisionId', value.divisionId)
          this.$set(this.tableData[i], 'totalAdvice', value.suggestion)
        }
      }
      for (let i in this.bgGriddata) {
        if (sort == this.bgGriddata[i].sort) {
          //存储的girdData信息
          this.$delete(this.bgGriddata[i], 'basconclusionId')
          this.$delete(this.bgGriddata[i], 'basconclusionName')
          this.$delete(this.bgGriddata[i], 'basconclusion')
          this.$delete(this.bgGriddata[i], 'division')
          this.$delete(this.bgGriddata[i], 'divisionId')
          this.$delete(this.bgGriddata[i], 'totalAdvice')

          this.$set(this.bgGriddata[i], 'basconclusionId', value.id)
          this.$set(this.bgGriddata[i], 'basconclusionName', value.name)
          this.$set(this.bgGriddata[i], 'basconclusion', value.name)
          this.$set(this.bgGriddata[i], 'division', value.deptName)
          this.$set(this.bgGriddata[i], 'divisionId', value.divisionId)
          this.$set(this.bgGriddata[i], 'totalAdvice', value.suggestion)
          this.inspection = {
            checkbox: '',
            name: this.bgGriddata[i].basconclusionName || '',
            ks: this.bgGriddata[i].division || '',
            division: this.bgGriddata[i].division || '',
            zjjy: this.bgGriddata[i].totalAdvice || '',
            tjjy: this.bgGriddata[i].tjjy || '',
            ksId: this.bgGriddata[i].divisionId || '',
            id: this.bgGriddata[i].id || '',
            basconclusionId: this.bgGriddata[i].basconclusionId || '',
          }
        }
      }
      let offer = ''
      this.tableData.forEach((el, index) => {
        if (el.basconclusionName && el.totalAdvice && el.flag == '1') {
          offer += index + 1 + '、' + el.basconclusionName + '\n  ' + el.totalAdvice + '\n\n'
        } else if (el.basconclusionName && el.flag == '1') {
          offer += index + 1 + '、' + el.basconclusionName + '\n\n'
        }
      })
      this.detailsOffer = offer
      this.$nextTick(() => {
        this.jlcRowIndex = sort
        this.jlcSave = true
        this.handleExecuteSave()
      })
    },
    // 选择标本类型 input-select
    ksChange(value) {
      this.ksData.bindValue = value.name
      this.ksId = value.id
      this.inspection.ksId = value.id
      this.inspection.division = value.name
    },

    getMaintain() {
      let error = ''
      if (!this.inspection.name) {
        error = '请输入结论词名称'
      } else if (!this.inspection.ksId) {
        error = '请选择科室'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      var obj = {
        conIds: this.conIds, //结论词id
        isMerge: this.isMerge, //是否合并
        ks: this.ksId || this.inspection.ksId,
        name: this.inspection.name,
        division: this.inspection.division,
        tjjy: this.inspection.tjjy,
        zjjy: this.inspection.zjjy,
      }
      maintain(obj)
        .then((res) => {
          let data = res.data
          if (res.code == 200) {
            if (this.isMerge == '1') {
              var mergeId = data.id
              var mergeName = obj.name
              var totalAdvice = obj.zjjy
              var tjjy = obj.tjjy
              var see = this.mergeRow[0]['see']
              for (var i = 0; i < this.mergeRow.length; i++) {
                var bean = this.mergeRow[i]
                bean.mergeId = mergeId
                bean.mergeName = mergeName
                bean.totalAdvice = totalAdvice
                bean.see = see
                bean.tjjy = tjjy
                this.tableData.forEach((el, index) => {
                  if (bean.basconclusionId == el.basconclusionId) {
                    this.$set(this.tableData, index, bean)
                  }
                })
                this.bgGriddata.forEach((el, index) => {
                  if (bean.basconclusionId == el.basconclusionId) {
                    this.$set(this.bgGriddata, index, bean)
                  }
                })
              }
              // form.clear();
              this.changeLeftText()
              // mini.get('insert').setValue('false');
              this.inspection.checkbox = 'false'
              this.isTjjy = false //团检建议编辑
              this.isDisabledThesaurus = false //存入词库按钮
              this.isKs = false //科室状态编辑
              this.isName = false //名称状态编辑
            } else {
              // this.creater = data.creater;
              // this.basconclusionId = data.id;//结论词id
              let pushData = {
                division: obj.division,
                // division_id:obj.ks,
                totalAdvice: obj.zjjy,
                basconclusion: obj.name,
                basconclusionId: data.id,
                creater: data.creater,
                tjjy: obj.tjjy,
                see: 1,

                basconclusionName: obj.name,
                divisionId: obj.ks,
                flag: '1',
                id: res.data.id,
                mergeId: '',
                mergeName: '',
                sort: this.bgGriddata.length,
              }
              this.tableData.push(pushData)
              this.bgGriddata.push(pushData)
              this.changeLeftText()
              this.inspection.checkbox = 'false'
              this.isTjjy = false //团检建议编辑
              this.isDisabledThesaurus = false //存入词库按钮
              this.isKs = false //科室状态编辑
              this.isName = false //名称状态编辑
            }
          }
        })
        .then(() => {
          // this.$modal.msgSuccess('存入成功')
          this.$nextTick(() => {
            this.handleExecuteSave()
          })
        })
        .catch(() => {})
    },

    handleSelectionChange(selection) {
      //选择输入
      this.isName = false
      this.isKs = false
      this.isZjjy = true
      this.isTjjy = false
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.tableIndex = selection.map((item) => item.sort)
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection
        this.inspection = {
          checkbox: '',
          name: this.selection[0].basconclusionName,
          ks: this.selection[0].division,
          division: this.selection[0].division,
          zjjy: this.selection[0].totalAdvice,
          tjjy: this.selection[0].tjjy,
          ksId: this.selection[0].divisionId,
          id: this.selection[0].id,
          basconclusionId: this.selection[0].basconclusionId,
        }
      } else if (selection.length == 0) {
        this.inspection.checkbox = undefined
        this.inspection.name = undefined
        this.inspection.ksId = undefined
        this.inspection.division = undefined
        this.inspection.zjjy = undefined
        this.inspection.tjjy = undefined
        this.inspection.id = undefined
        this.ksData.bindValue = undefined
        this.ksId = undefined
        this.$refs.ksData.initData()
      } else {
        this.selection = selection
        this.inspection.checkbox = false
        this.isName = false
        this.isKs = false
        this.isZjjy = true
        this.isTjjy = false
      }
    },
    remindBtnWindow() {
      this.$refs.remindItems.remindWindow(this.form)
    },
    //科室结果
    departmentResultsWindow() {
      this.$refs.departmentResultsItems.departmentResultsWindow(this.patientcode)
    },
    onClickAdd(e) {
      if (this.inspection.checkbox == '新增') {
        this.beforeCheckbox = '新增'
      }
      e === this.inspection.checkbox ? (this.inspection.checkbox = '') : (this.inspection.checkbox = e)
      if (this.inspection.checkbox == '' || this.inspection.checkbox == false || this.inspection.checkbox == '微调结论') {
        this.isDisabledThesaurus = true
        this.isKs = false
      } else {
        this.isKs = true
        this.isDisabledThesaurus = false
      }
      if (this.inspection.checkbox == '新增') {
        this.state = 'added'
        this.isName = true
        this.isKs = true
        this.isZjjy = false
        this.isTjjy = true
        if (this.selection.length > 0) {
          if (this.selection[0].flag == 1 && this.see == false) {
            this.isZjjy = true
          }
        }
        if (this.see == false) {
          this.isZjjy = true
        }
      } else if (this.inspection.checkbox == '微调结论') {
        this.state = 'modified'
        this.isName = true
        this.isKs = false
        this.isZjjy = true
        this.isTjjy = false
        if (this.selection.length == 1) {
          if (this.selection[0].flag == '1') {
            this.isZjjy = true
          }
        }
        this.zjjyBlur()
      } else {
        if (this.beforeCheckbox == '新增') {
          this.inspection = {}
        }
        this.isName = false
        this.isKs = false
        this.isZjjy = false
        this.isTjjy = false
      }
    },
    // 修改对应总检建议
    zjjyBlur() {
      if (this.inspection.basconclusionId) {
        this.bgGriddata.forEach((el) => {
          if (el.basconclusionId == this.inspection.basconclusionId) {
            el.totalAdvice = this.inspection.zjjy
            el.tjjy = this.inspection.tjjy
            el.basconclusionName = this.inspection.name
            el.basconclusion = this.inspection.name
          }
        })
        this.tableData.forEach((el) => {
          if (el.basconclusionId == this.inspection.basconclusionId) {
            el.totalAdvice = this.inspection.zjjy
            el.tjjy = this.inspection.tjjy
            el.basconclusionName = this.inspection.name
            el.basconclusion = this.inspection.name
          }
        })
        this.$nextTick(() => {
          this.handleExecuteSave()
        })
      }
    },
    addTable() {
      //展示
      let sort = 0
      if (this.tableData.length > 0) sort = this.bgGriddata.length
      let obj = { sort, state: 'added', flag: '1' }
      this.tableData.push(obj)
      //存储
      let sortSecond = 0
      if (this.bgGriddata.length > 0) sortSecond = this.bgGriddata.length
      let objSecond = { sort: sortSecond, state: 'added', flag: '1' }
      this.bgGriddata.push(objSecond)
      this.$nextTick(() => {
        this.$refs.tableData.bodyWrapper.scrollTop = this.$refs.tableData.bodyWrapper.scrollHeight
        this.$refs[`departData${this.tableData.length - 1}`].setFocus()
      })
    },
    removeTable() {
      var isDelete = true
      for (var i in this.tableData) {
        var el = this.tableData[i]
        if (this.tableIndex.indexOf(el.sort) > -1) {
          if (el.mergeId) {
            isDelete = false
            break
          }
        }
      }
      if (!isDelete) {
        this.$alert('已合并的结论词不能删除，请先解除合并！', '提示', {
          confirmButtonText: '确认',
          type: 'warning',
        })
        return
      }
      for (let i in this.tableIndex) {
        this.tableData.forEach((el, index) => {
          if (this.tableIndex[i] == el.sort) {
            this.$delete(this.tableData, index)
          }
        })
        this.bgGriddata.forEach((item, index) => {
          if (this.tableIndex[i] == item.sort) {
            if (this.bgGriddata[index].id) {
              this.$delete(this.bgGriddata[index], 'state')
              this.$set(this.bgGriddata[index], 'state', 'removed')
            } else {
              this.$delete(this.bgGriddata, index)
            }
          }
        })
      }
      this.$nextTick(() => {
        this.handleExecuteSave()
      })
    },
    //查看历史
    historyBtnWindow() {
      this.$refs.historyItems.historyWindow(this.historyId)
    },
    //刷新
    freshWindow() {
      if (!this.audit && this.$getCookie('cid') == '2' && this.form.patientcode) {
        this.$confirm('当前体检号您未审核，是否确认审核?', '提示', {
          confirmButtonText: '审核',
          cancelButtonText: '刷新',
        })
          .then(() => {
            this.auditWindow(1)
          })
          .catch(() => {
            this.detailsWindow(this.patientcode)
          })
      } else {
        if (this.countSecond) {
          this.countSecond = false
          this.detailsWindow(this.patientcode)
          setTimeout(() => {
            this.countSecond = true
          }, 3000)
        }
      }
    },
    // 生成报告
    createReport() {
      const clLoading = this.$loading({ target: '#mainBox' })
      createReportData({
        dh: 0,
        isJy: 0,
        patientcode: this.patientcode,
        showAllImage: 0,
      })
        .then(() => {
          this.$modal.msgSuccess('生成报告成功', '提示')
          clLoading.close()
        })
        .catch(() => {
          clLoading.close()
        })
    },
    //查看报告
    viewReport() {
      let url = this.$getCookie('imgPath') + this.pdfUrl
      window.open(url, '_blank')
      return
      var query = {
        patientcode: this.patientcode,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualHealthReport',
        query,
      })
      window.open(routeUrl.href)
    },
    // 同步
    synchronizationWindow() {
      var obj = {
        patientno: this.patientcode,
        dh: 0,
      }
      synchronize(obj).then((res) => {
        if (res.code == 200) {
          this.queryParams.patientno = this.form.patientcode
          this.handleQuery()
          this.$message({
            message: res.msg,
            type: 'success',
          })
        }
      })
    },
    lockWindow(state) {
      //state 1锁定 0解锁
      var obj = {
        ids: this.patientcode,
        state,
      }
      if (state == 1) {
        isLock(obj).then((res) => {
          if (res.code) {
            this.$alert('锁定成功', '提醒', {
              confirmButtonText: '确定',
              type: 'warning',
            })
              .then(() => {
                this.lockBtn = true
                this.unlockBtn = false
                this.$modal.msgSuccess('锁定成功')
              })
              .catch(() => {})
          }
        })
      } else {
        isLock(obj).then((res) => {
          this.$alert('解锁成功', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
            .then(() => {
              this.unlockBtn = true
              this.lockBtn = false
              this.$modal.msgSuccess('解锁成功')
            })
            .catch(() => {})
        })
      }
    },
    auditWindow(data) {
      //data 1审核保存 0反审保存
      for (var i = 0; i < this.tableData.length; i++) {
        if (!(this.tableData[i].state == 'added' || this.tableData[i].state == 'removed')) {
          this.tableData[i].state = 'modified'
        }
      }
      for (var i = 0; i < this.tableData.length; i++) {
        this.bgGriddata[i] = {
          basconclusion: this.tableData[i].basconclusionName, //结论词
          basconclusionId: this.tableData[i].basconclusionId, //结论词ID
          divisionId: this.tableData[i].divisionId, //科室ID
          division: this.tableData[i].division, //
          id: this.tableData[i].id, //id
          mergeId: this.tableData[i].mergeId, //合并结论词ID
          mergeName: this.tableData[i].mergeName, //结论名称
          see: this.tableData[i].flag, //标志: 1出现,0不出现
          sort: i, //排序
          state: this.tableData[i].state, //	状态,added添加，modified修改，removed删除
          tjjy: this.tableData[i].tjjy, //	团检建议
          totalAdvice: this.tableData[i].totalAdvice, //	总检建议
        }
      }
      let conclusion = ''
      this.bgGriddata.forEach((el, index) => {
        conclusion += index + 1 + '、' + el.division + ':' + el.basconclusion + '\n\n'
      })
      var obj = {
        bgFormData: {
          doctors: '',
          jkoffer: '',
          posistive: '',
          reportConclusions: '',
          summaryId: '',
          offer: this.detailsOffer,
          patientCode: this.patientcode,
          verdict: conclusion,
        },
        bgGriddata: [...this.bgGriddata],
        patientno: this.patientcode,
        type: 2, //审核
        dh: 0, // 健康0/职业1
      }
      if (data == 1) {
        if (this.auditTimer) {
          this.$modal.msgWarning('提交中,请勿重复操作')
          return
        }
        //审核
        this.$confirm('确定审核完成,请确定已保存修改内容？', '提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.auditTimer = true
            let timer = setTimeout(() => {
              this.auditTimer = false
            }, 20000)
            obj.bgGriddata = obj.bgGriddata.filter(el => el.basconclusionId)
            saveOrUpdate(obj)
              .then((res) => {
                if (res.code == 200) {
                  this.auditTimer = false
                  this.unaudit = false
                  this.audit = true
                  this.changeCode = ''
                  this.setFocus()
                  this.$modal.msgSuccess('审核成功，已开始生成报告！')
                  // this.$alert('审核成功，已开始生成报告！', '提醒', {
                  //   type: 'primary',
                  // })
                  //   .then(() => {
                  createReportData({
                    dh: 0,
                    isJy: 0,
                    patientcode: this.patientcode,
                    showAllImage: 0,
                  })
                    .then(() => {
                      this.$modal.msgSuccess('生成报告成功')
                      this.detailsWindow(this.patientcode)
                      this.emptyInput = true
                    })
                    .catch((error) => {
                      console.error(error)
                    })
                  // })
                  // .catch(() => {})
                } else {
                  this.auditTimer = false
                  clearTimeout(timer)
                }
              })
              .catch((error) => {
                console.error(error)
                this.auditTimer = false
                clearTimeout(timer)
              })
          })
          .catch(() => {})
      } else {
        //反审--未完成
        this.$confirm('确定反审？', '提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            var objPatientno = { patientno: this.patientcode }
            unfinish(objPatientno).then((res) => {
              this.audit = false
              this.unaudit = true
              this.$modal.msgSuccess('反审成功')
              this.detailsWindow(this.patientcode)
            })
          })
          .catch(() => {})
      }
    },
    moveWindow(data) {
      if (data == 'up' && this.tableData.length > 1) {
        for (var i = 0; i < this.tableData.length; i++) {
          if (this.selection[0].id == this.tableData[i].id) {
            if (i != 0) {
              this.tableData[i] = this.tableData.splice(i - 1, 1, this.tableData[i])[0]
              this.bgGriddata[i] = this.bgGriddata.splice(i - 1, 1, this.bgGriddata[i])[0]
              let offer = ''
              let index = 1
              this.tableData.forEach((el) => {
                if (el.basconclusionName && el.totalAdvice && el.flag == '1') {
                  offer += index++ + '、' + el.basconclusionName + '\n  ' + el.totalAdvice + '\n\n'
                } else if (el.basconclusionName && el.flag == '1') {
                  offer += index++ + '、' + el.basconclusionName + '\n\n'
                }
              })
              this.detailsOffer = offer
              return
            } else {
              this.$message({
                message: '此数据已位于队列顶部!',
                type: 'warning',
              })
              return
            }
          }
        }
      } else if (data == 'down' && this.tableData.length > 1) {
        for (var i = 0; i < this.tableData.length; i++) {
          if (this.selection[0].id == this.tableData[i].id) {
            if (i != this.tableData.length - 1) {
              this.tableData[i] = this.tableData.splice(i + 1, 1, this.tableData[i])[0]
              this.bgGriddata[i] = this.bgGriddata.splice(i + 1, 1, this.bgGriddata[i])[0]
              let offer = ''
              let index = 1
              this.tableData.forEach((el) => {
                if (el.basconclusionName && el.totalAdvice && el.flag == '1') {
                  offer += index++ + '、' + el.basconclusionName + '\n  ' + el.totalAdvice + '\n\n'
                } else if (el.basconclusionName && el.flag == '1') {
                  offer += index++ + '、' + el.basconclusionName + '\n\n'
                }
              })
              this.detailsOffer = offer
              return
            } else {
              this.$message({
                message: '此数据已位于队列末端!',
                type: 'warning',
              })
              return
            }
          }
        }
      }
    },
    closeDialog() {
      this.lockBtn = true
      this.unlockBtn = false
      this.audit = false
      this.unaudit = false
      this.tableData = []
      this.inspection.checkbox = false
      this.$emit('getList')
    },
    //合并结论词
    getMergeWindow() {
      if (this.selection.length == 0) {
        this.$modal.msgWarning('请选择结论词！')
        return
      }
      var rows = []
      var conIds = []
      var see = this.selection[0].see
      for (var i = 0; i < this.selection.length; i++) {
        var bean = this.selection[i]
        if (bean.basconclusionId) {
          if (bean.mergeId) {
            var merges = []
            this.bgGriddata.forEach((el) => {
              if (el.mergeId == bean.mergeId) {
                merges.push(el)
              }
            })
            for (var j = 0; j < merges.length; j++) {
              if (!JSON.stringify(conIds).includes(merges[j]['basconclusionId'])) {
                rows.push(merges)
                conIds.push(merges[j]['basconclusionId'])
              }
            }
          } else {
            if (!JSON.stringify(conIds).includes(bean.basconclusionId)) {
              conIds.push(bean.basconclusionId)
              rows.push(bean)
            }
          }
        }
      }
      getMerge(conIds)
        .then((result) => {
          if (result.code == 200) {
            var mergeId = result.data.mergeId
            var mergeName = result.data.mergeName
            var totalAdvice = result.data.suggestion
            var tjjy = result.data.tjjy
            for (var i = 0, l = rows.length; i < l; i++) {
              var bean = rows[i]
              bean.mergeId = mergeId
              bean.mergeName = mergeName
              bean.totalAdvice = totalAdvice
              bean.see = see
              bean.tjjy = tjjy
              this.tableData.forEach((el, index) => {
                if (el.basconclusionId == rows[i].basconclusionId) {
                  this.tableData[index] = Object.assign(el, bean)
                }
              })
            }
            this.changeLeftText()
          } else {
            let str = new RegExp('error@')
            let _msg = result.msg.replace(str, '')
            if (_msg.indexOf('新增') == -1) _msg += '，是否要新增？'
            this.$confirm(_msg, '提醒', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            })
              .then(() => {
                this.mergeRow = rows
                this.$delete(this.inspection, 'checkbox')
                this.$set(this.inspection, 'checkbox', '新增')
                this.isMerge = '1'
                this.conIds = conIds.toString()
              })
              .catch(() => {})
          }
        })
        .catch(() => {})
    },
    //拆分
    unlockWindow() {
      if (this.selection.length == 0) {
        this.$modal.msgWarning('请选择结论词！')
        return
      }
      var ids = []
      var rows = []
      for (var i = 0; i < this.selection.length; i++) {
        var bean = this.selection[i]
        if (bean.mergeId) {
          var merges = []
          this.bgGriddata.forEach((el) => {
            if (el.mergeId == bean.mergeId) {
              merges.push(el)
            }
          })
          for (var j = 0; j < merges.length; j++) {
            var merge = merges[j]
            if (ids[j] != merge.basconclusionId) {
              ids.push(merge.basconclusionId)
              rows.push(merge)
              continue
            }
          }
        }
      }
      if (ids.length == 0) {
        this.$modal.msgWarning('合并结论词为空,请检查选中数据')
        return
      }
      getBreakUp(ids).then((result) => {
        for (var i = 0; i < rows.length; i++) {
          var bean = rows[i]
          bean.mergeId = ''
          bean.mergeName = ''
          bean.totalAdvice = result[bean.basconclusionId] ? result[bean.basconclusionId] : ''
          this.bgGriddata.forEach((el, index) => {
            if (el.basconclusionId == rows[i].basconclusionId) {
              this.bgGriddata[index] = Object.assign(el, bean)
            }
          })
        }
        this.changeLeftText()
      })
    },
    //修改结论和健康建议
    changeLeftText() {
      var data = this.tableData
      var offer = ''
      var n = 1
      var merges = []
      for (var i in data) {
        var row = data[i]
        if (row.flag != 1 || !row.basconclusionId) continue
        var mergeId = row.mergeId
        var conclusion_name = ''
        if (mergeId) {
          if (!JSON.stringify(mergeId).includes(merges)) continue
          conclusion_name = row.mergeName
        } else {
          conclusion_name = row.basconclusionName
        }
        var totalAdvice = row.totalAdvice
        if (totalAdvice) {
          offer = offer + n + '、' + conclusion_name + '\n' + totalAdvice + '\n\r'
          n++
        }
        merges.push(mergeId)
      }
      this.detailsOffer = offer || '本次体检未见异常'
    },
    //单击选中行
    rowClickHandle(row, column) {
      if (column.className == 'el-table-column--selection') {
        this.$refs.tableData.toggleRowSelection(row, true)
      } else {
        this.$refs.tableData.clearSelection()
        this.$refs.tableData.toggleRowSelection(row, true)
      }
    },
    handlerClickInput(row) {
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row, true)
    },
  },
}
</script>

<style lang="scss">
.health-inspection-details {
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
    display: flex;
    overflow: hidden;
    background-color: #fff;
    .tabs-left {
      overflow: hidden;
      display: flex;
      flex-direction: column;
      width: 50%;
      height: 100%;
      min-height: 0;
      margin-right: 1%;

      .tabs-item {
        flex: auto;
        margin-right: 2%;
        padding: 0;
        border: none;

        .el-textarea__inner {
          height: 100%;
          white-space: pre-wrap;
          font-size: 24px !important;
          color: black;
        }
      }
    }
    .tabs-right {
      display: flex;
      flex-direction: column;
      overflow-y: auto;
      width: 49%;
      height: 100%;
      min-height: 0;
      .add-table {
        // border: 1px solid #d4d6d9;
        flex: 1;
        display: flex;
        flex-direction: column;
        width: 100%;
        margin-bottom: 8px;
        .table-btn {
          padding: 16px 20px;
        }

        .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
          background: transparent;
        }

        .el-textarea__inner,
        .el-input__inner {
          padding: 0 8px;
          border-width: 0;
          text-align: center;
          background: transparent;
          font-size: 16px !important;

          &:focus {
            border-width: 1px;
            text-align: left;
          }
        }

        .tabs-btn {
          .el-button {
            padding: 3px 8px;
          }
        }
      }
    }
  }

  .el-descriptions--medium:not(.is-bordered) .el-descriptions-item__cell {
    line-height: 29px;
  }
}
</style>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.health-inspection-details /deep/ .el-button span {
  font-size: 16px;
}

.health-inspection-details /deep/ .el-table__row .cell {
  font-size: 14px;
  color: #000;
}

.health-inspection-details .inspection-details-RD /deep/ .el-form-item__content {
  width: 100%;
}
</style>

<style scoped>
.health-inspection-details .left-btns .el-button.ai-analysis-btn {
  background-color: #67c23a;
  border-color: #67c23a;
  color: #fff;
}

.health-inspection-details .left-btns .el-button.ai-analysis-btn:hover {
  background-color: #85ce61;
  border-color: #85ce61;
  color: #fff;
}

.health-inspection-details /deep/ .el-button span {
  font-size: 16px;
}
.health-inspection-details /deep/ table .cell {
  font-size: 16px !important;
}

.health-inspection-details /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}

.health-inspection-details /deep/ .el-input__inner,
.health-inspection-details /deep/ .el-textarea__inner,
.health-inspection-details .inspection-details-RD /deep/ .el-input__inner,
.health-inspection-details .inspection-details-RD /deep/ .el-textarea__inner {
  color: #000 !important;
  font-size: 16px !important;
}
.health-inspection-details .add-table .table-box /deep/ .el-input__inner,
.health-inspection-details .inspection-details-RD /deep/ .el-radio__label,
.health-inspection-details .inspection-details-RD /deep/ .el-form-item__label {
  font-size: 16px !important;
  color: #000 !important;
  font-weight: normal;
}
.health-inspection-details .add-table .table-box /deep/ .el-input__inner {
  font-size: 22px !important;
}

/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {
  body,
  .health-inspection-details .el-input__inner,
  .health-inspection-details .el-form .el-form-item__label,
  .health-inspection-details .checkbox {
    font-family: '宋体', Arial, sans-serif;
    background: transparent;
  }
  .health-inspection-details .el-button {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
