<!-- 电测听-右  开发人：麦沃德科技 予安/半夏 -->
<template>
  <div class="app-container flex-direction-column electro-audiometry-r" style="height: 100%; min-height: auto; padding: 0; border-left: #f6f7fb 4px solid; overflow-y: auto">
    <!-- AI诊断弹窗 -->
    <AiChatBox :visible.sync="aiChatVisible" :initial-text="aiInitialText" :title="`AI ${$route.meta.title || '电测听'}结果诊断`" :close-on-click-modal="false" @result="handleAiResult" />
    <div class="show-top">
      <el-form :model="defaultData" ref="queryDefault" size="small" :inline="true" label-width="100px" v-if="!mainDisabled" style="margin-bottom: -14px">
        <el-form-item>
          <el-button class="section-btn-style2" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
        <el-form-item label="气导默认值" prop="airDefaultValue">
          <el-input-number v-model="defaultData.airDefaultValue" controls-position="right" :min="-10" :max="120"></el-input-number>
        </el-form-item>
        <el-form-item label="骨导默认值" prop="boneDefaultValue">
          <el-input-number v-model="defaultData.boneDefaultValue" controls-position="right" :min="-10" :max="120"></el-input-number>
        </el-form-item>
      </el-form>
      <div style="position: relative">
        <div class="table-head">
          <div class="s1"></div>
          <div class="s2"></div>
          <div class="word1">
            <div>频率</div>
            <div style="margin-left: 8px">(HZ)</div>
          </div>
          <div class="word2">
            <div>结果</div>
            <div style="margin-left: 8px">(DB)</div>
          </div>
          <div class="word3">类型</div>
        </div>
        <table border="0" cellspacing="0" class="show-table" :class="{ 'read-only': mainDisabled }">
          <tr style="height: 70px">
            <th width="100px" style="min-width: 100px"></th>
            <th>双耳</th>
            <th v-if="jk_td">125</th>
            <th v-if="jk_td">250</th>
            <th>500</th>
            <th>1000</th>
            <th>2000</th>
            <th v-if="zy_td">3000</th>
            <th>4000</th>
            <th v-if="zy_td">6000</th>
            <th v-if="jk_td">8000</th>
          </tr>
          <tr>
            <td rowspan="2">气导</td>
            <td>左耳</td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airLeft125" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airLeft250" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft500" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft500c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft1000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft1000c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft2000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft2000c }}</span>
              </div>
            </td>
            <td v-if="zy_td">
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft3000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft3000c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft4000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft4000c }}</span>
              </div>
            </td>
            <td v-if="zy_td">
              <div class="flex">
                <el-input v-model="formData.formdata.airLeft6000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airLeft6000c }}</span>
              </div>
            </td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airLeft8000" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
          </tr>
          <tr>
            <td>右耳</td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airRight125" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airRight250" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airRight500" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight500c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airRight1000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight1000c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airRight2000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight2000c }}</span>
              </div>
            </td>
            <td v-if="zy_td">
              <div class="flex">
                <el-input v-model="formData.formdata.airRight3000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight3000c }}</span>
              </div>
            </td>
            <td>
              <div class="flex">
                <el-input v-model="formData.formdata.airRight4000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight4000c }}</span>
              </div>
            </td>
            <td v-if="zy_td">
              <div class="flex">
                <el-input v-model="formData.formdata.airRight6000" size="small" @change="onAudiometryValueChanged"></el-input>
                <span class="correct" v-if="correct">{{ correctData.airRight6000c }}</span>
              </div>
            </td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.airRight8000" size="small" @change="onAudiometryValueChanged"></el-input>
            </td>
          </tr>
          <tr>
            <td rowspan="2">骨导</td>
            <td>左耳</td>
            <td v-if="jk_td" rowspan="2"></td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.boneLeft250" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneLeft500" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneLeft1000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneLeft2000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td v-if="zy_td">
              <el-input v-model="formData.formdata.boneLeft3000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneLeft4000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td v-if="zy_td">
              <el-input v-model="formData.formdata.boneLeft6000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td v-if="jk_td" rowspan="2"></td>
          </tr>
          <tr>
            <td>右耳</td>
            <td v-if="jk_td">
              <el-input v-model="formData.formdata.boneRight250" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneRight500" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneRight1000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneRight2000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td v-if="zy_td">
              <el-input v-model="formData.formdata.boneRight3000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td>
              <el-input v-model="formData.formdata.boneRight4000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
            <td v-if="zy_td">
              <el-input v-model="formData.formdata.boneRight6000" size="small" @change="setNeedRegenerateBoneImgPath"></el-input>
            </td>
          </tr>
        </table>
      </div>
      <el-form class="hear" :model="formData" ref="queryForm" size="small" :inline="true" label-width="160px" label-position="left">
        <el-form-item label="（听力检测）结果评定" prop="testResult">
          <el-input type="textarea" v-model="formData.formdata.testResult" :readonly="mainDisabled" @input="onResultChange"></el-input>
        </el-form-item>
        <el-form-item label="（听力检测）备注说明" prop="describe">
          <el-input type="textarea" v-model="formData.formdata.describe" readonly></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="show-right">
      <div class="summary-header">
        <h3>【小结】</h3>
        <el-button type="primary" size="mini" icon="el-icon-cpu" class="ai-analysis-btn" @click="openAiAnalysis" :disabled="!rightData.patient || !rightData.patient.patientname">AI诊断</el-button>
      </div>
      <el-form :model="formData" ref="queryMain" size="small" :inline="true" style="padding: 10px 0">
        <el-form-item prop="conclusions" class="summary-form">
          <el-input v-model="formData.data.conclusions" type="textarea" :rows="3" placeholder="请输入内容" readonly></el-input>
        </el-form-item>
        <el-form-item label="检查人" prop="rummager" style="margin-right: 32px">
          <el-input :value="formData.data.rummager" readonly style="width: 310px" v-if="mainDisabled" />
          <input-select :selectData="selectData" :initialValue="formData.data.rummager" :queryParams="queryParams" selectWidth="310" @change="selectChange" v-else></input-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="rummagerTime" style="margin-right: 0">
          <el-input prefix-icon="el-icon-time" :value="formData.data.rummagerTime" readonly style="width: 310px" v-if="mainDisabled" />
          <el-date-picker v-model="formData.data.rummagerTime" type="datetime" placeholder="选择日期时间" style="width: 310px" value-format="yyyy-MM-dd HH:mm:ss" v-else></el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="writeName" style="margin-right: 32px">
          <el-input :value="formData.data.writeName" readonly style="width: 310px" v-if="mainDisabled" />
          <input-select :selectData="selectData1" :initialValue="formData.data.writeName" :queryParams="queryParams" selectWidth="310" @change="selectChange1" v-else></input-select>
        </el-form-item>
        <el-form-item label="录入时间" prop="writeTime" style="margin-right: 0">
          <el-input prefix-icon="el-icon-time" v-model="formData.data.writeTime" readonly style="width: 310px" value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
      </el-form>
    </div>
    <!-- 生成/查看图像 -->
    <generateImage ref="generateImage"></generateImage>
    <div class="chart_main">
      <div class="image-box">
        <div class="chart" id="air_chart"></div>
        <div class="chart" id="bone_chart"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDeviation } from '@/api/funcdept/section_list/electro_audiometry.js'
import generateImage from '../dialog/generate_image.vue'
import * as echarts from 'echarts'
import airLeft from '@/assets/audiometry/lanx.png'
import boneRight from '@/assets/audiometry/gy.png'
import boneLeft from '@/assets/audiometry/gz.png'
import AiChatBox from '@/components/AiChatBox'
export default {
  components: {
    generateImage,
    AiChatBox
  },
  props: ['rightData', 'mainDisabled'],
  data() {
    return {
      // AI诊断相关
      aiChatVisible: false,
      aiInitialText: '',
      aiAnalysisResult: '',
      // 数据
      formData: {
        formdata: {},
        data: {},
      },
      // 默认参数
      defaultData: {
        airDefaultValue: undefined,
        boneDefaultValue: undefined,
      },
      // 检查人
      selectData: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 录入人
      selectData1: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 检查人参数
      queryParams: {
        ksID: '',
      },
      // 表格加载中
      loading: false,
      // 健康体检
      jk_td: true,
      // 职业体检
      zy_td: true,
      // 职业体检标签
      correct: false,
      // 校正值数据
      correctData: {},
      // 图表
      air_chart: null,
      bone_chart: null,
      // 听阈偏差值
      deviation: undefined,
    }
  },
  watch: {
    rightData: {
      handler(value) {
        if (value.patient && value.patient.patientcode) {
          this.initData()
        } else {
          this.resetData()
        }
      },
      immediate: true,
      deep: true,
    },
    'formData.formdata.testResult': {
      handler(newVal) {
        var conclusions = this.formData.formdata.testResult + this.formData.formdata.describe
        this.$set(this.formData.data, 'conclusions', conclusions || '')
      },
    },
  },
  created() {
    this.queryParams.ksID = this.$route.meta.ksID
    this.bus.$off('handleMakeImage')
    this.bus.$on('handleMakeImage', (type, auto) => {
      this.handleMakeImage(type, auto)
    })
    this.getDeviation(() => { })
  },
  beforeDestroy() {
    this.bus.$off('handleMakeImage')
  },
  methods: {
    // 打开AI诊断弹窗
    openAiAnalysis() {
      // 检查是否有患者信息
      if (!this.rightData.patient || !this.rightData.patient.patientname) {
        this.$message.warning('没有顾客信息，无法进行AI诊断')
        return
      }

      // 收集科室和体检者信息
      const departmentInfo = this.$route.meta.title || '电测听'
      const patientInfo = this.rightData.patient
      const testResult = this.formData.formdata.testResult || ''
      const conclusions = this.formData.data.conclusions || ''

      // 格式化听力测试结果数据
      let resultsData = ''

      // 气导数据
      if (this.formData.formdata.airLeft125 || this.formData.formdata.airRight125 ||
        this.formData.formdata.airLeft250 || this.formData.formdata.airRight250 ||
        this.formData.formdata.airLeft500 || this.formData.formdata.airRight500 ||
        this.formData.formdata.airLeft1000 || this.formData.formdata.airRight1000 ||
        this.formData.formdata.airLeft2000 || this.formData.formdata.airRight2000 ||
        this.formData.formdata.airLeft3000 || this.formData.formdata.airRight3000 ||
        this.formData.formdata.airLeft4000 || this.formData.formdata.airRight4000 ||
        this.formData.formdata.airLeft6000 || this.formData.formdata.airRight6000 ||
        this.formData.formdata.airLeft8000 || this.formData.formdata.airRight8000) {

        resultsData += '气导听力测试结果：\n'
        resultsData += '频率(Hz) | 左耳(dB) | 右耳(dB)\n'
        resultsData += '--- | --- | ---\n'

        if (this.formData.formdata.airLeft125 || this.formData.formdata.airRight125) {
          resultsData += `125 | ${this.formData.formdata.airLeft125 || '-'} | ${this.formData.formdata.airRight125 || '-'}\n`
        }
        if (this.formData.formdata.airLeft250 || this.formData.formdata.airRight250) {
          resultsData += `250 | ${this.formData.formdata.airLeft250 || '-'} | ${this.formData.formdata.airRight250 || '-'}\n`
        }
        if (this.formData.formdata.airLeft500 || this.formData.formdata.airRight500) {
          resultsData += `500 | ${this.formData.formdata.airLeft500 || '-'} | ${this.formData.formdata.airRight500 || '-'}\n`
        }
        if (this.formData.formdata.airLeft1000 || this.formData.formdata.airRight1000) {
          resultsData += `1000 | ${this.formData.formdata.airLeft1000 || '-'} | ${this.formData.formdata.airRight1000 || '-'}\n`
        }
        if (this.formData.formdata.airLeft2000 || this.formData.formdata.airRight2000) {
          resultsData += `2000 | ${this.formData.formdata.airLeft2000 || '-'} | ${this.formData.formdata.airRight2000 || '-'}\n`
        }
        if (this.formData.formdata.airLeft3000 || this.formData.formdata.airRight3000) {
          resultsData += `3000 | ${this.formData.formdata.airLeft3000 || '-'} | ${this.formData.formdata.airRight3000 || '-'}\n`
        }
        if (this.formData.formdata.airLeft4000 || this.formData.formdata.airRight4000) {
          resultsData += `4000 | ${this.formData.formdata.airLeft4000 || '-'} | ${this.formData.formdata.airRight4000 || '-'}\n`
        }
        if (this.formData.formdata.airLeft6000 || this.formData.formdata.airRight6000) {
          resultsData += `6000 | ${this.formData.formdata.airLeft6000 || '-'} | ${this.formData.formdata.airRight6000 || '-'}\n`
        }
        if (this.formData.formdata.airLeft8000 || this.formData.formdata.airRight8000) {
          resultsData += `8000 | ${this.formData.formdata.airLeft8000 || '-'} | ${this.formData.formdata.airRight8000 || '-'}\n`
        }

        resultsData += '\n'
      }

      // 骨导数据
      if (this.formData.formdata.boneLeft250 || this.formData.formdata.boneRight250 ||
        this.formData.formdata.boneLeft500 || this.formData.formdata.boneRight500 ||
        this.formData.formdata.boneLeft1000 || this.formData.formdata.boneRight1000 ||
        this.formData.formdata.boneLeft2000 || this.formData.formdata.boneRight2000 ||
        this.formData.formdata.boneLeft3000 || this.formData.formdata.boneRight3000 ||
        this.formData.formdata.boneLeft4000 || this.formData.formdata.boneRight4000) {

        resultsData += '骨导听力测试结果：\n'
        resultsData += '频率(Hz) | 左耳(dB) | 右耳(dB)\n'
        resultsData += '--- | --- | ---\n'

        if (this.formData.formdata.boneLeft250 || this.formData.formdata.boneRight250) {
          resultsData += `250 | ${this.formData.formdata.boneLeft250 || '-'} | ${this.formData.formdata.boneRight250 || '-'}\n`
        }
        if (this.formData.formdata.boneLeft500 || this.formData.formdata.boneRight500) {
          resultsData += `500 | ${this.formData.formdata.boneLeft500 || '-'} | ${this.formData.formdata.boneRight500 || '-'}\n`
        }
        if (this.formData.formdata.boneLeft1000 || this.formData.formdata.boneRight1000) {
          resultsData += `1000 | ${this.formData.formdata.boneLeft1000 || '-'} | ${this.formData.formdata.boneRight1000 || '-'}\n`
        }
        if (this.formData.formdata.boneLeft2000 || this.formData.formdata.boneRight2000) {
          resultsData += `2000 | ${this.formData.formdata.boneLeft2000 || '-'} | ${this.formData.formdata.boneRight2000 || '-'}\n`
        }
        if (this.formData.formdata.boneLeft3000 || this.formData.formdata.boneRight3000) {
          resultsData += `3000 | ${this.formData.formdata.boneLeft3000 || '-'} | ${this.formData.formdata.boneRight3000 || '-'}\n`
        }
        if (this.formData.formdata.boneLeft4000 || this.formData.formdata.boneRight4000) {
          resultsData += `4000 | ${this.formData.formdata.boneLeft4000 || '-'} | ${this.formData.formdata.boneRight4000 || '-'}\n`
        }

        resultsData += '\n'
      }

      // 如果没有表格数据，但有测试结果或小结，也使用这些数据
      if (resultsData.trim() === '' && (testResult || conclusions)) {
        resultsData = testResult || conclusions;
      }

      // 准备AI诊断的提示文本
      const promptText = `您好，请对以下${departmentInfo}结果进行诊断和分析：

      顾客信息：
      - 姓名：${patientInfo.patientname || '未知'}
      - 性别：${patientInfo.idSex == 0 ? '男' : patientInfo.idSex == 1 ? '女' : '未知'}
      - 年龄：${patientInfo.age || '未知'}

      听力测试结果：
      ${resultsData}

      请对以上听力测试结果进行诊断，并提供以下内容：

      ## 小结
      请列出所有异常值及其偏离正常范围的程度。

      ## 结论
      这些异常值可能指向的听力问题和趋势，以及是否需要进一步检查或注意事项。

      ## 健康建议
      有哪些具体的建议可以改善听力状况，包括生活方式、保护措施和可能的治疗方案。`;

      // 设置初始文本并打开对话框
      this.aiInitialText = promptText;
      this.aiChatVisible = true;
    },
    // 处理AI诊断结果
    handleAiResult(result) {
      this.aiAnalysisResult = result;

      // 如果小结为空，则直接将AI结果应用到小结中
      if (!this.formData.data.conclusions) {
        this.formData.data.conclusions = result;
        this.$message.success('AI诊断结果已应用到小结');
      } else {
        // 否则弹出确认框询问是否覆盖原有小结
        this.$confirm('是否将AI诊断结果追加到当前小结中？', '提示', {
          confirmButtonText: '追加',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.data.conclusions += '\n\n--- AI诊断结果 ---\n' + result;
          this.$message.success('AI诊断结果已追加到小结');
        }).catch(() => {
          this.$message.info('已取消应用AI诊断结果');
        });
      }
    },

    // 初始化数据
    initData() {
      var idExamtype = this.rightData.patient.idExamtype
      if (idExamtype == 0) {
        //健康
        this.jk_td = true
        this.zy_td = false
        this.correct = false
      } else if (idExamtype == 1) {
        //职业
        this.jk_td = false
        this.zy_td = true
        this.correct = true
      } else {
        //综合(综合按职业显示) 复查也按职业显示
        this.jk_td = false
        this.zy_td = true
        this.correct = true
      }
      this.formData.formdata = this.rightData.audiometry
      this.formData.formdata.describe = this.rightData.audiometry.describe || ''
      this.getCorrectAll()
      this.formData.data = this.rightData.main
      this.selectData.bindValue = this.rightData.main.rummager
      this.generateCharts()
    },
    // 获取听阈偏差值
    getDeviation(fn) {
      getDeviation().then((res) => {
        this.deviation = res.data
        fn()
      })
    },
    // 值改变事件
    onAudiometryValueChanged() {
      this.setNeedRegenerateAirImgPath()
      if (!this.rightData.patient || this.rightData.patient.idExamtype == 0) {
        //健康体检不需要计算
        return
      }
      this.getCorrectAll()
      var result = '' //结果评定
      var jhys = this.rightData.patient.jhys
      var exclude = false //没有噪声，但有压力容器或机动车
      var harms = ['155', '158']
      if (jhys != '') {
        var jh_arr = jhys.split(',')
        if (jh_arr.indexOf('127') == -1) {
          for (var i = 0; i < jh_arr.length; i++) {
            if (harms.indexOf(jh_arr[i]) != -1) {
              exclude = true
              break
            }
          }
        }
      }
      var age = this.rightData.patient.age //体检者年龄
      var sex = this.rightData.patient.idSex == 0 ? 'M' : 'F' //体检者性别
      //压力容器作业和职业机动车驾驶作业  只显示双耳语频听阈均值XX dB（保留整数） 语频是前三列  高频是后三列
      //但如果有噪声，仍然显示高频
      //如果都没有显示高频
      //做电测听一定有压力容器作业和职业机动车驾驶作业和噪声其中至少一种，不存在都没有的情况
      //如果没有噪声，使用原始值计算双耳语频听阈均值,如果有噪声，就用校正值计算
      if (exclude) {
        if (!isNaN(this.formData.formdata.airLeft500) && !isNaN(this.formData.formdata.airLeft1000) && !isNaN(this.formData.formdata.airLeft2000) && !isNaN(this.formData.formdata.airRight500) && !isNaN(this.formData.formdata.airRight1000) && !isNaN(this.formData.formdata.airRight2000)) {
          var L3k = Number(this.formData.formdata.airLeft500 || 0) //-dev_500;//不为空
          var L4k = Number(this.formData.formdata.airLeft1000 || 0) //-dev_1000;
          var L6k = Number(this.formData.formdata.airLeft2000 || 0) //-dev_2000;
          var R3k = Number(this.formData.formdata.airRight500 || 0) //-dev_500;
          var R4k = Number(this.formData.formdata.airRight1000 || 0) //-dev_1000;
          var R6k = Number(this.formData.formdata.airRight2000 || 0) //-dev_2000;
          var BHFTA = ((L3k + L4k + L6k + R3k + R4k + R6k) / 6).toFixed(0) //双耳语频听阈均值
          // 可能存在-0的情况，转化为0
          if (BHFTA == -0) {
            BHFTA = 0;
          }
          result = '双耳语频听阈均值' + BHFTA + 'dB。'
        }
        this.$set(this.formData.formdata, 'testResult', result)
        var desc = this.formData.formdata.describe //备注说明
        var conclusions = result + desc
        // this.$set(this.formData.data, 'conclusions', conclusions)
      } else {
        if (!isNaN(this.formData.formdata.airLeft3000) && !isNaN(this.formData.formdata.airLeft4000) && !isNaN(this.formData.formdata.airLeft6000) && !isNaN(this.formData.formdata.airRight3000) && !isNaN(this.formData.formdata.airRight4000) && !isNaN(this.formData.formdata.airRight6000)) {
          var dev_3000 = this.getDev(sex + '_List_3k', age)
          var dev_4000 = this.getDev(sex + '_List_4k', age)
          var dev_6000 = this.getDev(sex + '_List_6k', age)
          var L3k = Number(this.formData.formdata.airLeft3000 || 0) - dev_3000 //不为空
          var L4k = Number(this.formData.formdata.airLeft4000 || 0) - dev_4000
          var L6k = Number(this.formData.formdata.airLeft6000 || 0) - dev_6000
          var R3k = Number(this.formData.formdata.airRight3000 || 0) - dev_3000
          var R4k = Number(this.formData.formdata.airRight4000 || 0) - dev_4000
          var R6k = Number(this.formData.formdata.airRight6000 || 0) - dev_6000
          var BHFTA = ((L3k + L4k + L6k + R3k + R4k + R6k) / 6).toFixed(0) //双耳高频听阈均值
          // 可能存在-0的情况，转化为0
          if (BHFTA == -0) {
            BHFTA = 0;
          }
          result = '双耳高频平均听阈' + BHFTA + 'dB。'

          /*针对噪声作业*/
          var dev_500 = this.getDev(sex + '_List_500', age)
          var dev_1000 = this.getDev(sex + '_List_1k', age)
          var dev_2000 = this.getDev(sex + '_List_2k', age)
          var L500 = Number(this.formData.formdata.airLeft500 || 0) - dev_500
          var L1k = Number(this.formData.formdata.airLeft1000 || 0) - dev_1000
          var L2k = Number(this.formData.formdata.airLeft2000 || 0) - dev_2000
          var R500 = Number(this.formData.formdata.airRight500 || 0) - dev_500
          var R1k = Number(this.formData.formdata.airRight1000 || 0) - dev_1000
          var R2k = Number(this.formData.formdata.airRight2000 || 0) - dev_2000
          var HLL = (((L1k + L2k + L500) * 0.9) / 3 + L4k * 0.1).toFixed(0)
          var HLR = (((R1k + R2k + R500) * 0.9) / 3 + R4k * 0.1).toFixed(0)
          var YPL = ((L500 + L1k + L2k) / 3).toFixed(0)
          var YPR = ((R500 + R1k + R2k) / 3).toFixed(0)
          result += '\r左耳语频平均听阈' + YPL + 'dB，左耳听阈加权值' + HLL + 'dB。'
          result += '\r右耳语频平均听阈' + YPR + 'dB，右耳听阈加权值' + HLR + 'dB。'
        }
        this.$set(this.formData.formdata, 'testResult', result)
        // this.$set(this.formData.data, 'conclusions', result)
      }
    },
    // 设置气导图片需要重新生成
    setNeedRegenerateAirImgPath() {
      if (this.rightData && this.rightData.audiometry) {
        this.rightData.audiometry.needRegenerateAirImgPath = true;
      }
    },
    // 设置骨导图片需要重新生成
    setNeedRegenerateBoneImgPath() {
      if (this.rightData && this.rightData.audiometry) {
        this.rightData.audiometry.needRegenerateBoneImgPath = true;
      }
    },
    // 计算所有校正值
    getCorrectAll() {
      if (!this.rightData.patient || this.rightData.patient.idExamtype == 0) {
        //健康体检不需要计算
        return
      }
      if (!this.deviation) {
        this.getDeviation(() => {
          this.getCorrectAll()
        })
        return
      }
      this.getCorrect({
        key: '500',
        value: this.formData.formdata.airLeft500,
        name: 'airLeft500c',
      })
      this.getCorrect({
        key: '1k',
        value: this.formData.formdata.airLeft1000,
        name: 'airLeft1000c',
      })
      this.getCorrect({
        key: '2k',
        value: this.formData.formdata.airLeft2000,
        name: 'airLeft2000c',
      })
      this.getCorrect({
        key: '3k',
        value: this.formData.formdata.airLeft3000,
        name: 'airLeft3000c',
      })
      this.getCorrect({
        key: '4k',
        value: this.formData.formdata.airLeft4000,
        name: 'airLeft4000c',
      })
      this.getCorrect({
        key: '6k',
        value: this.formData.formdata.airLeft6000,
        name: 'airLeft6000c',
      })
      this.getCorrect({
        key: '500',
        value: this.formData.formdata.airRight500,
        name: 'airRight500c',
      })
      this.getCorrect({
        key: '1k',
        value: this.formData.formdata.airRight1000,
        name: 'airRight1000c',
      })
      this.getCorrect({
        key: '2k',
        value: this.formData.formdata.airRight2000,
        name: 'airRight2000c',
      })
      this.getCorrect({
        key: '3k',
        value: this.formData.formdata.airRight3000,
        name: 'airRight3000c',
      })
      this.getCorrect({
        key: '4k',
        value: this.formData.formdata.airRight4000,
        name: 'airRight4000c',
      })
      this.getCorrect({
        key: '6k',
        value: this.formData.formdata.airRight6000,
        name: 'airRight6000c',
      })
    },
    // 获取校正值
    getCorrect(e) {
      if (e) {
        if (e.value || e.value === 0) {
          var age = Number(this.rightData.patient.age) //体检者年龄
          var sex = this.rightData.patient.idSex == 0 ? 'M' : 'F' //体检者性别
          var dev = this.getDev(sex + '_List_' + e.key, age)
          var correctValue = Number(e.value) - dev
          this.correctData[e.name] = correctValue
        } else {
          this.correctData[e.name] = ''
        }
      }
    },
    // 获取校正值
    getDev(key, age) {
      var dev_array = this.deviation[key].split(',')
      var result = 0
      for (var i = 0; i < dev_array.length; i++) {
        var start = Number(dev_array[i].split('-')[0])
        var end = Number(dev_array[i].split('-')[1].split(':')[0])
        if (age >= start && age <= end) {
          result = Number(dev_array[i].split(':')[1])
          break
        }
      }
      return result
    },
    // 输入结果评定事件
    onResultChange() {
      // var conclusions = this.formData.formdata.testResult + this.formData.formdata.describe
      // this.$set(this.formData.data, 'conclusions', conclusions)
    },
    // 数据重置
    resetData() {
      var formdata = {
        airLeft125: 25,
        airLeft250: 25,
        airLeft500: 25,
        airLeft1000: 25,
        airLeft2000: 25,
        airLeft3000: 25,
        airLeft4000: 25,
        airLeft6000: 25,
        airLeft8000: 25,
        airRight125: 25,
        airRight250: 25,
        airRight500: 25,
        airRight1000: 25,
        airRight2000: 25,
        airRight3000: 25,
        airRight4000: 25,
        airRight6000: 25,
        airRight8000: 25,
        boneLeft250: 25,
        boneLeft500: 25,
        boneLeft1000: 25,
        boneLeft2000: 25,
        boneLeft3000: 25,
        boneLeft4000: 25,
        boneLeft6000: 25,
        boneLeft8000: 25,
        boneRight250: 25,
        boneRight500: 25,
        boneRight1000: 25,
        boneRight2000: 25,
        boneRight3000: 25,
        boneRight4000: 25,
        boneRight6000: 25,
        boneRight8000: 25,
        describe: '',
        testResult: undefined,
      }
      var data = {
        conclusions: undefined,
        rummager: decodeURIComponent(this.getCookie('username')),
        rummagerId: undefined,
        rummagerTime: this.$getDate(),
        writeName: decodeURIComponent(this.getCookie('username')),
        writeTime: this.$getDate(),
      }
      this.$set(this.formData, 'formdata', formdata)
      this.$set(this.formData, 'data', data)
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
    // 重置
    resetQuery() {
      this.$set(this.formData.formdata, 'airLeft125', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft250', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft500', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft1000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft2000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft3000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft4000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft6000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airLeft8000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight125', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight250', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight500', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight1000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight2000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight3000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight4000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight6000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'airRight8000', this.defaultData.airDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft250', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft500', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft1000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft2000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft3000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft4000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneLeft6000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight250', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight500', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight1000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight2000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight3000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight4000', this.defaultData.boneDefaultValue)
      this.$set(this.formData.formdata, 'boneRight6000', this.defaultData.boneDefaultValue)
      this.setNeedRegenerateAirImgPath()
      this.setNeedRegenerateBoneImgPath()
      if (this.rightData.patient) this.onAudiometryValueChanged()
    },
    // 检查人返回选中的值
    selectChange(value) {
      this.formData.data.rummagerId = value.id
      this.formData.data.rummager = value.username
      this.selectData.bindValue = value.username
    },
    // 录入人返回选中的值
    selectChange1(value) {
      this.formData.data.writeId = value.id
      this.formData.data.writeName = value.username
      this.selectData1.bindValue = value.username
    },
    // 生成/查看图像按钮
    handleMakeImage(type, auto) {
      if (!this.rightData.patient || !this.rightData.patient.patientcode) {
        this.$modal.msgWarning('请先读卡,再执行后续操作!')
        return
      }
      if (type == 1) {
        if (this.rightData.audiometry && (this.rightData.audiometry.airImgPath || this.rightData.audiometry.boneImgPath)) {
          if (this.rightData.audiometry.needRegenerateAirImgPath && this.rightData.audiometry.needRegenerateBoneImgPath) {
            this.$modal.msgWarning(`气导和骨导数据已发生变化，请重新生成图片！`)
          } else if (this.rightData.audiometry.needRegenerateAirImgPath) {
            this.$modal.msgWarning(`气导数据已发生变化，请重新生成图片！`)
          } else if (this.rightData.audiometry.needRegenerateBoneImgPath) {
            this.$modal.msgWarning(`骨导数据已发生变化，请重新生成图片！`)
          } else {
            this.$refs.generateImage.handleViewImage(this.rightData.audiometry.airImgPath, this.rightData.audiometry.boneImgPath)
          }
        } else {
          this.$modal.msgWarning(`体检号${this.rightData.patient.patientcode}还没有生成图片！`)
        }
      } else {
        var chartData = JSON.stringify(this.getChartData())
        this.$refs.generateImage.handleMakeImage(chartData, this.rightData.patient.idExamtype, this.rightData.audiometry, auto)
      }
    },
    // 图表数据
    getChartData() {
      var idExamType = this.rightData.patient.idExamtype
      var audiometry = this.formData.formdata
      var result = {}
      var air_left_list = []
      var air_right_list = []
      var bone_left_list = []
      var bone_right_list = []
      var air_xAxis
      var bone_xAxis
      if (idExamType == '0') {
        air_xAxis = ['125', '250', '500', '1000', '2000', '4000', '8000']
        bone_xAxis = air_xAxis
        air_left_list.push(audiometry.airLeft125)
        air_left_list.push(audiometry.airLeft250)
        air_left_list.push(audiometry.airLeft500)
        air_left_list.push(audiometry.airLeft1000)
        air_left_list.push(audiometry.airLeft2000)
        air_left_list.push(audiometry.airLeft4000)
        air_left_list.push(audiometry.airLeft8000)

        air_right_list.push(audiometry.airRight125)
        air_right_list.push(audiometry.airRight250)
        air_right_list.push(audiometry.airRight500)
        air_right_list.push(audiometry.airRight1000)
        air_right_list.push(audiometry.airRight2000)
        air_right_list.push(audiometry.airRight4000)
        air_right_list.push(audiometry.airRight8000)

        bone_left_list.push(null)
        bone_left_list.push(audiometry.boneLeft250)
        bone_left_list.push(audiometry.boneLeft500)
        bone_left_list.push(audiometry.boneLeft1000)
        bone_left_list.push(audiometry.boneLeft2000)
        bone_left_list.push(audiometry.boneLeft4000)
        bone_left_list.push(null)

        bone_right_list.push(null)
        bone_right_list.push(audiometry.boneRight250)
        bone_right_list.push(audiometry.boneRight500)
        bone_right_list.push(audiometry.boneRight1000)
        bone_right_list.push(audiometry.boneRight2000)
        bone_right_list.push(audiometry.boneRight4000)
        bone_right_list.push(null)
      } else if (idExamType == '1' || idExamType == '2' || idExamType == '3') {
        bone_xAxis = ['500', '1000', '2000', '3000', '4000', '6000']
        air_xAxis = ['500', '1000', '2000', '3000', '4000', '6000']

        air_left_list.push(audiometry.airLeft500)
        air_left_list.push(audiometry.airLeft1000)
        air_left_list.push(audiometry.airLeft2000)
        air_left_list.push(audiometry.airLeft3000)
        air_left_list.push(audiometry.airLeft4000)
        air_left_list.push(audiometry.airLeft6000)

        air_right_list.push(audiometry.airRight500)
        air_right_list.push(audiometry.airRight1000)
        air_right_list.push(audiometry.airRight2000)
        air_right_list.push(audiometry.airRight3000)
        air_right_list.push(audiometry.airRight4000)
        air_right_list.push(audiometry.airRight6000)

        bone_left_list.push(audiometry.boneLeft500)
        bone_left_list.push(audiometry.boneLeft1000)
        bone_left_list.push(audiometry.boneLeft2000)
        bone_left_list.push(audiometry.boneLeft3000)
        bone_left_list.push(audiometry.boneLeft4000)
        bone_left_list.push(audiometry.boneLeft6000)

        bone_right_list.push(audiometry.boneRight500)
        bone_right_list.push(audiometry.boneRight1000)
        bone_right_list.push(audiometry.boneRight2000)
        bone_right_list.push(audiometry.boneRight3000)
        bone_right_list.push(audiometry.boneRight4000)
        bone_right_list.push(audiometry.boneRight6000)
      } else {
        bone_xAxis = ['250', '500', '1000', '2000', '3000', '4000', '6000']
        air_xAxis = ['125', '250', '500', '1000', '2000', '3000', '4000', '6000', '8000']

        air_left_list.push(audiometry.airLeft125)
        air_left_list.push(audiometry.airLeft250)
        air_left_list.push(audiometry.airLeft500)
        air_left_list.push(audiometry.airLeft1000)
        air_left_list.push(audiometry.airLeft2000)
        air_left_list.push(audiometry.airLeft3000)
        air_left_list.push(audiometry.airLeft4000)
        air_left_list.push(audiometry.airLeft6000)
        air_left_list.push(audiometry.airLeft8000)

        air_right_list.push(audiometry.airRight125)
        air_right_list.push(audiometry.airRight250)
        air_right_list.push(audiometry.airRight500)
        air_right_list.push(audiometry.airRight1000)
        air_right_list.push(audiometry.airRight2000)
        air_right_list.push(audiometry.airRight3000)
        air_right_list.push(audiometry.airRight4000)
        air_right_list.push(audiometry.airRight6000)
        air_right_list.push(audiometry.airRight8000)

        bone_left_list.push(audiometry.boneLeft250)
        bone_left_list.push(audiometry.boneLeft500)
        bone_left_list.push(audiometry.boneLeft1000)
        bone_left_list.push(audiometry.boneLeft2000)
        bone_left_list.push(audiometry.boneLeft3000)
        bone_left_list.push(audiometry.boneLeft4000)
        bone_left_list.push(audiometry.boneLeft6000)

        bone_right_list.push(audiometry.boneRight250)
        bone_right_list.push(audiometry.boneRight500)
        bone_right_list.push(audiometry.boneRight1000)
        bone_right_list.push(audiometry.boneRight2000)
        bone_right_list.push(audiometry.boneRight3000)
        bone_right_list.push(audiometry.boneRight4000)
        bone_right_list.push(audiometry.boneRight6000)
      }
      result.air_right = air_right_list
      result.air_left = air_left_list
      result.bone_left = bone_left_list
      result.bone_right = bone_right_list
      result.air_xAxis = air_xAxis
      result.bone_xAxis = bone_xAxis
      return result
    },
    // 审核获取图像
    getPicture() {
      this.generateCharts()
      return this.getImg()
    },
    // 生成图像
    generateCharts() {
      var xAxistop = ['125', '', '250', '', '500', '', '1k', '', '2k', '', '4k', '', '8k']
      var xAxisbottom = ['', '', '', '', '', '750', '', '1.5k', '', '3k', '', '6k', '']
      var idExamtype = this.rightData.patient.idExamtype
      if (idExamtype === '0') {
        this.generateHealth()
        return
      }
      var yAxis = [-10, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 120]
      var chart_data = this.getChartData()
      //气导
      if (this.isEmpty(chart_data.air_left) && this.isEmpty(chart_data.bone_left)) {
        this.air_chart = null
      } else {
        var air_option = {
          grid: {
            borderColor: '#3e3eff',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（左耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气左', '骨左'],
            right: '0%',
            top: '0%',
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: false,
              },
              axisLabel: {
                interval: 0,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: true,
              },
              axisLabel: {
                interval: 0,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
          },
          series: [
            {
              name: '气左',
              type: 'line',
              symbolSize: 20,
              connectNulls: true,
              symbol: 'image://' + airLeft,
              data: this.getProAir(chart_data.air_left),
              xAxisIndex: 1,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
            {
              name: '骨左',
              type: 'line',
              xAxisIndex: 0,
              connectNulls: true,
              symbolSize: 20,
              data: this.getProBone(chart_data.bone_left),
              symbol: 'image://' + boneRight,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
          animation: false,
        }
        if (document.getElementById('air_chart')) {
          this.air_chart = echarts.init(document.getElementById('air_chart'))
          this.air_chart.setOption(air_option)
        } else {
          setTimeout(() => {
            this.air_chart = echarts.init(document.getElementById('air_chart'))
            this.air_chart.setOption(air_option)
          }, 200);
        }
      }

      if (this.isEmpty(chart_data.air_right) && this.isEmpty(chart_data.bone_right)) {
        this.bone_chart = null
        return
      } //如果没有骨导数据
      //右耳
      var bone_option = {
        grid: {
          borderColor: 'red',
          borderWidth: 3,
          show: true,
          width: 400,
          height: 500,
          containLabel: true,
        },
        title: {
          text: '听力检测图（右耳）',
          right: '50%',
          top: '0%',
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data: ['气右', '骨右'],
          right: '0%',
          top: '0%',
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: xAxisbottom,
            name: '',
            nameLocation: 'middle',
            nameGap: '30',
            splitLine: {
              show: false,
            },
            axisLabel: {
              interval: 0,
            },
          },
          {
            type: 'category',
            boundaryGap: false,
            data: xAxistop,
            name: '',
            nameLocation: 'middle',
            nameGap: '30',
            splitLine: {
              show: true,
            },
            axisLabel: {
              interval: 0,
            },
          },
        ],
        yAxis: {
          type: 'value',
          data: yAxis,
          min: '-10',
          max: '120',
          name: '听力阈值（dB）',
          nameLocation: 'middle',
          nameGap: '30',
          splitNumber: '10',
          inverse: true,
          axisLabel: {
            show: true,
          },
        },
        series: [
          {
            name: '气右',
            type: 'line',
            data: this.getProAir(chart_data.air_right),
            connectNulls: true,
            symbolSize: 20,
            xAxisIndex: 1,
            itemStyle: {
              normal: {
                borderType: 'solid',
                lineStyle: {
                  color: 'red',
                },
                color: 'red',
              },
            },
          },
          {
            name: '骨右',
            type: 'line',
            connectNulls: true,
            xAxisIndex: 0,
            data: this.getProBone(chart_data.bone_right),
            symbolSize: 20,
            symbol: 'image://' + boneLeft,
            itemStyle: {
              normal: {
                borderType: 'solid',
                lineStyle: {
                  color: 'red',
                },
              },
            },
          },
        ],
        textStyle: {
          fontSize: 20,
        },
        animation: false,
      }
      if (document.getElementById('bone_chart')) {
        this.bone_chart = echarts.init(document.getElementById('bone_chart'))
        this.bone_chart.setOption(bone_option)
      } else {
        setTimeout(() => {
          this.bone_chart = echarts.init(document.getElementById('bone_chart'))
          this.bone_chart.setOption(bone_option)
        }, 200);
      }
    },
    // 判断骨导是否有值
    isEmpty(array) {
      for (var i = 0; i < array.length; i++) {
        if (array[i] !== '') {
          return false
        }
      }
      return true
    },
    // 获得图片
    getImg() {
      var air_img =
        this.air_chart == null
          ? null
          : this.air_chart.getDataURL({
            type: 'png',
            pixelRatio: 1, // 导出的图片分辨率比例
            backgroundColor: '#fff',
          })
      var bone_img =
        this.bone_chart == null
          ? null
          : this.bone_chart.getDataURL({
            type: 'png',
            pixelRatio: 1,
            backgroundColor: '#fff',
          })
      return [air_img, bone_img]
    },
    // 生成纯健康图像
    generateHealth() {
      var xAxistop = ['125', '', '250', '', '500', '', '1k', '', '2k', '', '4k', '', '8k']
      var xAxisbottom = ['', '', '', '', '', '750', '', '1.5k', '', '3k', '', '6k', '']
      var yAxis = [-10, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 120]
      var chart_data = this.getChartData()
      //气导
      if (this.isEmpty(chart_data.air_left) && this.isEmpty(chart_data.bone_left)) {
        this.air_chart = null
      } else {
        var air_option = {
          grid: {
            borderColor: '#3e3eff',
            borderWidth: 3,
            show: true,
            width: 400,
            height: 500,
            containLabel: true,
          },
          title: {
            text: '听力检测图（左耳）',
            right: '50%',
            top: '0%',
          },
          tooltip: {
            trigger: 'axis',
          },
          legend: {
            data: ['气左', '骨左'],
            right: '0%',
            top: '0%',
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: xAxisbottom,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: false,
              },
              axisLabel: {
                interval: 0,
              },
            },
            {
              type: 'category',
              boundaryGap: false,
              data: xAxistop,
              name: '',
              nameLocation: 'middle',
              nameGap: '30',
              splitLine: {
                show: true,
              },
              axisLabel: {
                interval: 0,
              },
            },
          ],
          yAxis: {
            type: 'value',
            data: yAxis,
            min: '-10',
            max: '120',
            name: '听力阈值（dB）',
            nameLocation: 'middle',
            nameGap: '30',
            splitNumber: '10',
            inverse: true,
          },
          series: [
            {
              name: '气左',
              type: 'line',
              symbolSize: 20,
              connectNulls: true,
              xAxisIndex: 1,
              symbol: 'image://' + airLeft,
              data: this.getHealthAir(chart_data.air_left),
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
            {
              name: '骨左',
              type: 'line',
              symbolSize: 20,
              xAxisIndex: 0,
              connectNulls: true,
              data: this.getHealthBone(chart_data.bone_left),
              symbol: 'image://' + boneRight,
              itemStyle: {
                normal: {
                  borderType: 'solid',
                  lineStyle: {
                    color: 'blue',
                  },
                },
              },
            },
          ],
          textStyle: {
            fontSize: 20,
          },
          animation: false,
        }
        if (document.getElementById('air_chart')) {
          this.air_chart = echarts.init(document.getElementById('air_chart'))
          this.air_chart.setOption(air_option)
        } else {
          setTimeout(() => {
            this.air_chart = echarts.init(document.getElementById('air_chart'))
            this.air_chart.setOption(air_option)
          }, 200);
        }
      }
      if (this.isEmpty(chart_data.air_right) && this.isEmpty(chart_data.bone_right)) {
        this.bone_chart = null
        return
      } //如果没有骨导数据
      //骨导
      var bone_option = {
        grid: {
          borderColor: 'red',
          borderWidth: 3,
          show: true,
          width: 400,
          height: 500,
          containLabel: true,
        },
        title: {
          text: '听力检测图（右耳）',
          right: '50%',
          top: '0%',
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data: ['气右', '骨右'],
          right: '0%',
          top: '0%',
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: xAxisbottom,
            name: '',
            nameLocation: 'middle',
            nameGap: '30',
            axisLabel: {
              show: true,
              interval: 0,
            },
            splitLine: {
              show: false,
            },
          },
          {
            type: 'category',
            boundaryGap: false,
            data: xAxistop,
            name: '',
            nameLocation: 'middle',
            nameGap: '30',
            axisLabel: {
              show: true,
              interval: 0,
            },
            splitLine: {
              show: true,
            },
          },
        ],
        yAxis: {
          type: 'value',
          data: yAxis,
          min: '-10',
          max: '120',
          name: '听力阈值（dB）',
          nameLocation: 'middle',
          nameGap: '30',
          splitNumber: '10',
          inverse: true,
          axisLabel: {
            show: true,
          },
        },
        series: [
          {
            name: '气右',
            type: 'line',
            data: this.getHealthAir(chart_data.air_right),
            symbolSize: 20,
            connectNulls: true,
            xAxisIndex: 1,
            itemStyle: {
              normal: {
                borderType: 'solid',
                lineStyle: {
                  color: 'red',
                },
                color: 'red',
              },
            },
          },
          {
            name: '骨右',
            type: 'line',
            data: this.getHealthBone(chart_data.bone_right),
            symbolSize: 20,
            connectNulls: true,
            xAxisIndex: 0,
            symbol: 'image://' + boneLeft,
            itemStyle: {
              normal: {
                borderType: 'solid',
                lineStyle: {
                  color: 'red',
                },
              },
            },
          },
        ],
        textStyle: {
          fontSize: 20,
        },
        animation: false,
      }
      if (document.getElementById('bone_chart')) {
        this.bone_chart = echarts.init(document.getElementById('bone_chart'))
        this.bone_chart.setOption(bone_option)
      } else {
        setTimeout(() => {
          this.bone_chart = echarts.init(document.getElementById('bone_chart'))
          this.bone_chart.setOption(bone_option)
        }, 200);
      }
    },
    getHealthAir(data) {
      return data ? [data[0], '', data[1], '', data[2], '', data[3], '', data[4], '', data[5], '', data[6]] : data
    },
    getHealthBone(data) {
      return data ? ['', '', data[1], '', data[2], '', data[3], '', data[4], '', data[5], '', ''] : data
    },
    getProAir(data) {
      return data ? ['', '', '', '', data[0], '', data[1], '', data[2], data[3], data[4], data[5], ''] : data
    },
    getProBone(data) {
      return data ? ['', '', '', '', data[0], '', data[1], '', data[2], data[3], data[4], data[5], ''] : data
    },
  },
}
</script>

<style lang="scss" scoped>
.summary-header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  h3 {
    margin: 0;
  }

  .ai-analysis-btn {
    margin-left: 10px;
  }
}
</style>
<style lang="scss">
.electro-audiometry-r {
  border-left: 4px #f6f7fb solid;

  .show-top {
    padding: 12px 20px;

    .table-head {
      overflow: hidden;
      position: absolute;
      top: 0;
      left: 0;
      z-index: 2;
      width: 100px;
      height: 70px;
      font-size: 12px;

      .s1 {
        transform: rotate(45deg);
        border-bottom: 1px solid rgba(0, 0, 0, 0.2);
        position: relative;
        top: 60px;
        left: 0;
        width: 180%;
      }

      .s2 {
        transform: rotate(20deg);
        border-top: 1px solid rgba(0, 0, 0, 0.2);
        position: relative;
        top: 49px;
        left: -28px;
        width: 150%;
      }

      .word1 {
        position: absolute;
        right: 5px;
        top: 8px;
      }

      .word2 {
        position: absolute;
        left: 10px;
        top: 8px;
      }

      .word3 {
        position: absolute;
        left: 10px;
        bottom: 6px;
      }
    }

    .show-table {
      white-space: nowrap;
      font-size: 14px;
      border-bottom: 1px solid rgba(0, 0, 0, 0.2);
      border-right: 1px solid rgba(0, 0, 0, 0.2);
      width: 100%;

      &.read-only .el-input {
        pointer-events: none;
      }

      th {
        padding: 20px 20px;
        border-top: 1px solid rgba(0, 0, 0, 0.2);
        border-left: 1px solid rgba(0, 0, 0, 0.2);
      }

      td {
        text-align: center;
        border-top: 1px solid rgba(0, 0, 0, 0.2);
        border-left: 1px solid rgba(0, 0, 0, 0.2);

        .el-input {
          padding: 1px;
          width: 100%;
        }

        .el-input__inner {
          border-radius: 0;
          text-align: center;
          padding: 0 8px;
        }

        .flex {
          display: flex;
          align-items: center;

          .el-input {
            flex: 1;
          }

          .correct {
            min-width: 32px;
            font-size: 12px;
          }
        }
      }
    }

    .hear {
      margin-top: 24px;

      .el-form-item {
        width: 100%;

        .el-form-item__content {
          width: calc(100% - 160px);
        }
      }
    }
  }

  .show-right {
    padding: 0 20px;

    h3 {
      margin: 0;
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
    }

    .summary-form {
      width: 100%;
      padding-bottom: 16px;

      .el-form-item__content {
        width: 100%;
      }
    }

    .el-textarea__inner {
      background-color: #f6f7fb;
    }

    .el-form-item--small.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 8px;
    }
  }

  .chart_main {
    width: 1000px;
    height: 1000px;
    overflow: hidden;
    visibility: hidden;

    .image-box {
      display: flex;
      justify-content: space-between;

      .chart {
        width: 460px;
        height: 700px;
      }
    }
  }
}
</style>
<style scoped>
/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {

  body,
  .el-input__inner,
  .el-textarea__inner,
  .el-button,
  .el-form .el-form-item__label,
  .checkbox {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
