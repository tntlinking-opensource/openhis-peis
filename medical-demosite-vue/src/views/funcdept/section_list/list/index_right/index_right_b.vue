<template>
  <!-- 内科、神经内科、外科、耳鼻喉科、CR检查、DR检查、CT、动脉硬化、心电图、体脂肪检测、经颅多普勒室、中医咨询、中医科、调查问卷、肛门指针
  、皮肤科、电子阴道镜、红外乳透、虹膜检查、糖尿病检测、肝纤维化彩超、核磁共振、内镜室、胶囊肠胃镜、24H动态心电图、-->
  <div class="app-container flex-direction-column section-list-right-b">
    <!-- AI诊断弹窗 -->
    <AiChatBox
      :visible.sync="aiChatVisible"
      :initial-text="aiInitialText"
      :title="`AI ${$route.meta.title || '检查'}结果诊断`"
      :close-on-click-modal="false"
      @result="handleAiResult"
    />
    <div class="show-top">
      <h3 style="margin: 0; margin-bottom: 8px">【基本信息】</h3>
      <el-row>
        <el-col :span="19">
          <div class="base-info">
            <div style="display: inline-block">
              <span class="top-style">姓名</span>
              <input class="input-style" v-model="rightData.peispatient.patientname" style="width: 140px" readonly />
            </div>
            <div style="display: inline-block">
              <span class="top-style">性别</span>
              <select class="select-style" v-model="rightData.peispatient.idSex" disabled>
                <option :value="0">男</option>
                <option :value="1">女</option>
              </select>
            </div>
            <br />
            <div style="display: inline-block">
              <span class="top-style">年龄</span>
              <input class="input-style" v-model="rightData.peispatient.age" style="width: 140px" readonly />
            </div>
            <div style="display: inline-block">
              <span class="top-style">类型</span>
              <select class="select-style" v-model="rightData.peispatient.idExamtype" disabled>
                <option value="0">健康体检</option>
                <option value="1">职业体检</option>
                <option value="2">综合</option>
                <option value="3">复查</option>
              </select>
            </div>
            <br />
            <div style="display: inline-block">
              <span class="top-style">电话</span>
              <input class="input-style" v-model="rightData.peispatient.phone" style="width: 140px" readonly />
            </div>
            <div style="display: inline-block">
              <span class="top-style">体检号</span>
              <input class="input-style" v-model="rightData.peispatient.patientcode" style="width: 140px" readonly />
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <img :src="rightData.picture ? imgPath + rightData.picture : rightData.picture" alt="" style="width: 100px; height: 120px" v-if="rightData.picture" />
          <div style="width: 100px; height: 120px; border: 1px #ccc solid" v-else></div>
        </el-col>
      </el-row>
    </div>
    <div class="show-middle" style="padding-bottom: 0">
      <div style="display: flex; justify-content: space-between; margin-bottom: 4px">
        <h3 style="margin: 0">【档案】</h3>
        <el-button class="section-btn-style2" icon="el-icon-search" @click="handleMore">查看更多</el-button>
      </div>
      <textarea class="textarea-style" v-model="rightData.archive" :rows="2" placeholder=" " readonly> </textarea>
    </div>
    <div class="show-right-middle table-box flex-direction-column" v-if="$route.meta.dataType != '10'">
      <div style="display: flex; justify-content: space-between">
        <h3 style="margin: 0; margin-top: 10px; margin-left: 10px">【结论词】</h3>
        <!-- pacs科室隐藏按钮 -->
        <div style="padding-right: 20px">
          <el-button class="section-btn-style2" icon="el-icon-plus" @click="handleAdd" :disabled="mainDisabled || lockDisable || !rightData.peispatient.patientname || rightData.peispatient.isAudit == 1">新增</el-button>
          <el-button class="section-btn-style2" icon="el-icon-delete" @click="handleRemove" :disabled="mainDisabled || lockDisable || !rightData.peispatient.patientname || rightData.peispatient.isAudit == 1 || multiple">删除</el-button>
          <el-button class="section-btn-style2" icon="el-icon-folder-add" @click="handleSave" :disabled="mainDisabled || lockDisable || !rightData.peispatient.patientname || rightData.peispatient.isAudit == 1">保存</el-button>
        </div>
      </div>
      <div class="table-box">
        <el-table border v-loading="loading" :data="rightData.griddata" height="100%" stripe @selection-change="handleSelectionChange" style="margin: -1px" :cell-style="stateStyle">
          <el-table-column fixed type="selection" align="center" width="120px" />
          <el-table-column label="检查项目" prop="jcxm" align="center" min-width="100" />
          <el-table-column label="体征词" prop="tzc" align="center" min-width="100" />
          <el-table-column label="结论词" prop="jlcName" align="center" cell-style="background-color: red;" :style="mainDisabled || lockDisable || !rightData.peispatient.patientname || rightData.peispatient.isAudit == 1 ? 'pointer-events:none;' : ''">
            <template slot-scope="scope">
              <input-select :selectData="selectData2" @change="selectChange2($event, scope)" :selectSize="'mini'" :initialValue="scope.row.jlcName || scope.row.tzc" :disabled="mainDisabled || rightData.peispatient.isAudit == 1"> </input-select>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="show-bottom">
      <div style="display: flex; justify-content: space-between">
        <div class="summary-header">
          <h3 style="margin: 0; margin-bottom: 10px">【小结】</h3>
          <el-button 
            type="primary" 
            size="mini" 
            icon="el-icon-cpu" 
            class="ai-analysis-btn" 
            @click="openAiAnalysis"
            :disabled="!rightData.peispatient || !rightData.peispatient.patientname"
          >AI诊断</el-button>
        </div>
        <div style="padding: 0 20px" class="mb8">
          <!-- v-if="ksID != '143' && ksID != '24' && ksID != '171' && ksID != '165' && ksID != '173' && ksID != '402848e3625a920201625ff99a3404a5'" -->
          <el-button class="section-btn-style2" icon="el-icon-sort" @click="handleGetResult">获取结果</el-button>
          <el-button class="section-btn-style2" icon="el-icon-plus" @click="handleSummary" :disabled="mainDisabled || rightData.flag == 'audit'">小结</el-button>
          <el-button class="section-btn-style1" icon="el-icon-circle-check" @click="handleAudit" :disabled="mainDisabled || rightData.flag == 'audit'">审核</el-button>
          <el-button class="section-btn-style1" icon="el-icon-circle-close" @click="handleReAudit" :disabled="!mainDisabled || lockDisable || rightData.flag != 'audit' || !headParams.patientcode">反审核</el-button>
        </div>
      </div>
      <div style="padding: 0 20px">
        <textarea class="textarea-style" v-model="rightData.sectionResultMain.conclusions" :rows="3" placeholder="请输入内容" readonly @dblclick="checkDetails"> </textarea>
        <div style="display: inline-block">
          <span class="top-style" style="width: 70px">检查人</span>
          <input-select :disabled="mainDisabled || rightData.peispatient.isAudit == 1" id="rummagerName" :selectData="selectData1" @change="selectChange" :initialValue="rightData.sectionResultMain.rummagerName" :queryParams="{ ksID }"></input-select>
        </div>
        <div style="display: inline-block">
          <span class="top-style">检查时间</span>
          <el-date-picker v-model="rightData.sectionResultMain.rummagerTime" type="datetime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm:ss" style="width: 220px" :readonly="mainDisabled || rightData.peispatient.isAudit == 1" :clearable="false"> </el-date-picker>
        </div>
        <br />
        <div style="display: inline-block">
          <span class="top-style" style="width: 70px">录入人</span>
          <input-select id="rummagerName" :selectData="selectData11" :initialValue="rightData.sectionResultMain.writeName" :queryParams="{ ksID }" :disabled="mainDisabled || rightData.peispatient.isAudit == 1" @change="selectChange1"></input-select>
        </div>
        <div style="display: inline-block">
          <span class="top-style">录入时间</span>
          <el-date-picker v-model="rightData.sectionResultMain.writeTime" type="datetime" placeholder="选择日期时间" style="width: 220px" readonly></el-date-picker>
        </div>
      </div>
    </div>
    <!-- 查看档案 -->
    <checkRecord ref="checkRecord"></checkRecord>
  </div>
</template>

<script>
import checkRecord from '../dialog/check_record.vue'

import { saveOrUpdateJlc, getXDTResult } from '@/api/funcdept/section_list/index.js'
import Cookies from 'js-cookie'
import AiChatBox from '@/components/AiChatBox'
export default {
  components: {
    checkRecord,
    AiChatBox
  },
  props: ['mainDisabled', 'lockDisable', 'rightData', 'headParams'],
  data() {
    return {
      // AI诊断相关
      aiChatVisible: false,
      aiInitialText: '',
      aiAnalysisResult: '',
      ksID: undefined,
      // 检查人
      selectData1: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        selectWidth: 110, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 录入人
      selectData11: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        selectWidth: 110, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 表格加载中
      loading: false,
      // 表格数据
      tableListBelow: [],
      // 结论词参数
      selectData2: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '结论词', //第二列标题
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/basconclusion/getConclusion', //请求连接
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 非多个禁用
      multiple: true,

      selectList: [],
      tableIndex: 1,
      //图片地址
      imgPath: Cookies.get('imgPath'),
      // 分中心id
      centerId: undefined,
    }
  },
  created() {
    this.centerId = this.$getCookie('cid')
    this.bus.$off('conclusionsResultGmd')
    this.bus.$off('auditRightData')
    this.ksID = this.$route.meta.ksID
    if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
      this.selectData2.url = '/abteilung/division/getConclusion'
    }
    this.bus.$on('conclusionsResultGmd', (conclusions) => {
      this.$delete(this.rightData.sectionResultMain, 'conclusions')
      this.$set(this.rightData.sectionResultMain, 'conclusions', conclusions)
      console.log("conclusionsResultGmd",conclusions);

    })
    this.bus.$on('auditRightData', (auditRightData) => {
      this.$delete(this.rightData, 'sectionResultMain')
      this.$set(this.rightData, 'sectionResultMain', auditRightData)
      console.log("auditRightData的值",auditRightData);
      
    })
  },
  methods: {
    // 打开AI诊断弹窗
    openAiAnalysis() {
      // 检查是否有患者信息
      if (!this.rightData.peispatient || !this.rightData.peispatient.patientname) {
        this.$message.warning('没有患者信息，无法进行AI诊断')
        return
      }
      
      // 收集科室和体检者信息
      const departmentInfo = this.$route.meta.title || '检查科室'
      const patientInfo = this.rightData.peispatient
      const examResults = this.rightData.griddata || []
      const conclusions = this.rightData.sectionResultMain.conclusions || ''
      
      // 格式化检查结果数据
      let resultsTable = ''
      if (examResults && examResults.length > 0) {
        resultsTable = '检查项目 | 体征词 | 结论词\n'
        resultsTable += '--- | --- | ---\n'
        
        examResults.forEach(item => {
          resultsTable += `${item.jcxm || ''} | ${item.tzc || ''} | ${item.jlcName || ''}\n`
        })
      }

      if (resultsTable.trim() == '') {
        resultsTable = conclusions;
      }
      
      // 准备AI诊断的提示文本
      const promptText = `您好，请对以下${departmentInfo}结果进行诊断和分析：

顾客信息：
- 姓名：${patientInfo.patientname || '未知'}
- 性别：${patientInfo.idSex == 0 ? '男' : patientInfo.idSex == 1 ? '女' : '未知'}
- 年龄：${patientInfo.age || '未知'}

检查结果：
${resultsTable}

请对以上检查结果进行诊断，并提供以下内容：

## 小结
请列出所有异常值及其偏离正常范围的程度。

## 结论
这些异常值可能指向的健康问题和趋势，以及是否需要进一步检查或注意事项。

## 健康建议
有哪些具体的建议可以改善这些指标，包括生活方式、饮食和可能的药物或治疗方案。`;
      
      // 设置初始文本并打开对话框
      this.aiInitialText = promptText;
      this.aiChatVisible = true;
    },
    
    // 处理AI诊断结果
    handleAiResult(result) {
      this.aiAnalysisResult = result;
      
      // 如果小结为空，则直接将AI结果应用到小结中
      if (!this.rightData.sectionResultMain.conclusions) {
        this.rightData.sectionResultMain.conclusions = result;
        this.$message.success('AI诊断结果已应用到小结');
      } else {
        // 否则弹出确认框询问是否覆盖原有小结
        this.$confirm('是否将AI诊断结果追加到当前小结中？', '提示', {
          confirmButtonText: '追加',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.rightData.sectionResultMain.conclusions += '\n\n--- AI诊断结果 ---\n' + result;
          this.$message.success('AI诊断结果已追加到小结');
        }).catch(() => {
          this.$message.info('已取消应用AI诊断结果');
        });
      }
    },
    
    // 检查人返回选中的值
    selectChange(value) {
      this.$emit('selectChange', value)
      // this.rightData.peispatient.rummagerId = value.id
    },
    // 录入人返回选中的值
    selectChange1(value) {
      this.$emit('selectChange1', value)
      // this.rightData.peispatient.rummagerId = value.id
    },
    // 双击查看小结详情
    checkDetails() {
      if (this.rightData.peispatient.patientname && this.rightData.sectionResultMain.conclusions) {
        this.$emit('checkDetails', 1)
      }
    },

    // 查看更多
    handleMore() {
      if (this.rightData.peispatient.patientcode) {
        this.$refs.checkRecord.showDialog(this.rightData.peispatient.patientcode)
      } else {
        this.$modal.msgWarning('请输入体检号后再试')
      }
    },
    // 表格选中
    handleSelectionChange(selection) {
      this.selectList = selection.map((item) => item)
      this.multiple = !selection.length
    },
    // 新增
    handleAdd() {
      let obj = {
        jcxm: undefined,
        tzc: undefined,
        jlcName: '',
        tableIndex: this.tableIndex,
      }
      this.tableIndex += 1
      this.rightData.griddata.unshift(obj)
    },
    // 根据状态改变表格底色
    stateStyle(row, column, rowIndex, columnIndex) {
      // let cellStyle;
      // switch (row.testResult) {
      //   case '成功':
      //     cellStyle = 'background: green;color:white'
      //     break;
      //   case '失败':
      //     cellStyle = 'background: red;color:white'
      //     break;
      //   case '不支持':
      //     cellStyle = 'background: #aaa;color:white'
      //     break;
      //   default:
      //     cellStyle = ''
      // }
      // if (column.label == '测试结果')
      //   return cellStyle
    },
    // 结论词返回值
    selectChange2(value, scope) {
      this.rightData.griddata[scope.$index] = value
      if (value) {
        this.rightData.griddata[scope.$index].jlcId = this.rightData.griddata[scope.$index].id ? this.rightData.griddata[scope.$index].id : undefined
        this.rightData.griddata[scope.$index].jlcName = this.rightData.griddata[scope.$index].name ? this.rightData.griddata[scope.$index].name : undefined
      }
      this.rightData.griddata = this.rightData.griddata.slice(0)
    },
    // 删除
    handleRemove() {
      this.selectList.forEach((el) => {
        this.rightData.griddata.forEach((val, index) => {
          if ((val.tableIndex && el.tableIndex == val.tableIndex) || (val.jlcId && el.jlcId == val.jlcId)) {
            this.rightData.griddata.splice(index, 1)
          }
        })
      })
    },
    // 保存
    handleSave() {
      let griddata = JSON.parse(JSON.stringify(this.rightData.griddata))
      griddata.forEach((el) => {
        el.jcxmId = el.tjxmId
      })
      let query = {
        ksID: this.$route.meta.ksID,
        patientcode: this.rightData.peispatient.patientcode,
        griddata,
      }
      saveOrUpdateJlc(query).then(() => {
        this.$modal.msgSuccess('保存结论词成功')
      })
    },
    // 小结
    handleSummary() {
      if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.$emit('handleSummary')
        return
      }
      this.bus.$emit('conclusionsHandleGmd')
    },
    // 获取结果
    handleGetResult() {
      if(!this.rightData.peispatient.patientcode){
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      getXDTResult(this.rightData.peispatient.patientcode).then((response) => {
        console.log('获取心电图结果：', response)
        this.$modal.msgSuccess('获取结果成功')
      })
    },
    // 审核
    handleAudit() {
      if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.$emit('handleAudit')
        return
      } else if (this.$route.meta.dataType == '10') {
        this.bus.$emit('boneDensityHandleGmd', this.rightData)
      }
    },
    // 反审核
    handleReAudit() {
      if (this.$route.meta.dataType == '6' || this.$route.meta.dataType == '7') {
        this.$emit('handleReAudit')
        return
      }
      this.bus.$emit('caseReverseHandleGmd')
    },
  },
}
</script>

<style lang="scss" scoped>
.summary-header {
  display: flex;
  align-items: center;
  gap: 10px;
  
  h3 {
    margin: 0;
  }
  
  .ai-analysis-btn {
    margin-left: 10px;
  }
}
</style>

<style lang="scss">
.section-list-right-b {
  min-height: auto;
  padding: 0;
  border-left: #f6f7fb 4px solid;
  overflow-y: auto;

  h3 {
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .el-textarea__inner {
    background-color: #f6f7fb;
  }

  .show-top {
    height: auto;
    padding: 6px;
  }

  .show-middle {
    padding: 6px 20px;
    padding-bottom: 0;
    border-top: #f6f7fb 4px solid;
    border-bottom: #f6f7fb 4px solid;

    .textarea-style {
      width: 100%;
      padding: 4px;
      color: #000;
      font-size: 18px;
    }
  }

  .show-right-middle {
    min-height: 180px;

    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background: transparent;
    }
    .el-table--enable-row-transition .el-table__body td.el-table__cell {
      padding: 0;
    }
    .el-input__inner {
      padding: 0 8px;
      border-width: 0;
      background: transparent;
      text-align: center;

      &:focus {
        border-width: 1px;
      }
    }
  }

  .show-bottom {
    overflow-y: visible;
    padding: 6px;
    padding-bottom: 0;
    height: 300px;
    border-top: #f6f7fb 4px solid;
    .textarea-style {
      width: 100%;
      margin-bottom: 4px;
      font-size: 18px;
      font-weight: 400 !important;
    }
  }
  .mb8 {
    margin-bottom: 0px;
  }
  .base-info {
    .input-style {
      font-weight: 400 !important;
    }
    .select-style {
      width: 140px;
      height: 30px;
      color: #000;
      font-size: 16px;
      font-weight: 400 !important;
      cursor: pointer;
    }
    .top-style {
      width: 70px;
    }
  }
}
@-moz-document url-prefix() {
  .section-list-right-b h3 {
    font-weight: normal;
    font-family: '宋体', Arial, sans-serif;
  }
}
</style>
<style scoped>
.section-b-form /deep/ .el-form-item__content {
  width: 100% !important;
}
.show-right-middle /deep/ .el-table th.el-table__cell {
  height: auto !important;
  padding: 0 !important;
}
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
