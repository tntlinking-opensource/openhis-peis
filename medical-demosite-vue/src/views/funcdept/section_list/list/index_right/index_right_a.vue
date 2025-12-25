<template>
  <!-- 一般检查、C13、C14、肺功能、骨密度、体脂肪检测-->
  <div class="app-container flex-direction-column section-list-right-a" style="height: 100%; min-height: auto; padding: 0; border-left: #f6f7fb 4px solid; overflow-y: auto">
    <!-- AI诊断弹窗 -->
    <AiChatBox
      :visible.sync="aiChatVisible"
      :initial-text="aiInitialText"
      :title="`AI ${$route.meta.title || '检查'}结果诊断`"
      :close-on-click-modal="false"
      @result="handleAiResult"
    />
    <div class="show-right">
      <div class="summary-header">
        <h3>【小结】</h3>
        <el-button 
          type="primary" 
          size="mini" 
          icon="el-icon-cpu" 
          class="ai-analysis-btn" 
          @click="openAiAnalysis"
          :disabled="!rightData.patient || !rightData.patient.patientname"
        >AI诊断</el-button>
      </div>
      <el-form :model="rightData.sectionResultMain" ref="queryForm" size="small" :inline="true" label-width="70px" label-position="left" style="padding: 10px 20px">
        <el-form-item prop="summary" :inline="false" style="width: 100%" class="section-a-form">
          <el-input v-model="rightData.sectionResultMain.conclusions" type="textarea" :rows="3" placeholder="请输入内容" style="width: 100%" :readonly="mainDisabled || rightData.sectionResultMain.isAudit == 1 || $route.meta.dataType == '4'"> </el-input>
        </el-form-item>
        <el-form-item label="检查人" prop="rummagerName">
          <input-select id="rummagerName" :selectData="selectData1" @change="selectChange" :initialValue="rightData.sectionResultMain.rummagerName" :queryParams="{ ksID }" :disabled="mainDisabled || rightData.sectionResultMain.isAudit == 1"></input-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="rummagerTime" label-width="80px">
          <el-date-picker v-model="rightData.sectionResultMain.rummagerTime" type="datetime" placeholder="选择日期时间" :readonly="mainDisabled || rightData.sectionResultMain.isAudit == 1" style="width: 220px" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
        <br />
        <el-form-item label="录入人" prop="writeName">
          <!-- <el-input v-model="rightData.sectionResultMain.writeName" style="width: 230px" readonly /> -->
          <input-select id="rummagerName" :selectData="selectData11" @change="selectChange1" :initialValue="rightData.sectionResultMain.writeName" :queryParams="{ ksID }" :disabled="mainDisabled || rightData.sectionResultMain.isAudit == 1"></input-select>
        </el-form-item>
        <el-form-item label="录入时间" prop="writeTime" label-width="80px">
          <el-date-picker v-model="rightData.sectionResultMain.writeTime" type="datetime" :readonly="mainDisabled || rightData.sectionResultMain.isAudit == 1" style="width: 220px" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div class="show-right-middle table-box flex-direction-column add-table">
      <h3 style="padding-top: 10px; padding-left: 10px">【结论词】</h3>
      <el-row style="padding: 10px; padding-bottom: 0" :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button class="section-btn-style2" icon="el-icon-plus" @click="handleAdd" :disabled="mainDisabled || !rightData.patient.patientname || rightData.sectionResultMain.isAudit == 1">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button class="section-btn-style2" icon="el-icon-delete" @click="handleRemove" :disabled="mainDisabled || !rightData.patient.patientname || rightData.sectionResultMain.isAudit == 1 || multiple">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button class="section-btn-style1" icon="el-icon-folder-add" @click="handleSave" :disabled="mainDisabled || !rightData.patient.patientname || rightData.sectionResultMain.isAudit == 1">保存</el-button>
        </el-col>
      </el-row>
      <div class="table-box">
        <el-table border ref="tableData" v-loading="loading" :data="rightData.jlcData" height="100%" stripe style="margin: -1px" @selection-change="handleSelectionChange" @row-click="rowClick">
          <el-table-column fixed type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="检查项目" prop="jcxm" align="center" min-width="100" />
          <el-table-column label="体征词" prop="tzc" align="center" min-width="100" />
          <el-table-column label="结论词" prop="jlcName" align="center" width="255">
            <template slot-scope="scope">
              <!-- <el-input size="mini" v-model="scope.row.jlc" style="width: 100%" /> -->
              <input-select :selectData="selectData2" @change="selectChange2($event, scope)" :selectSize="'mini'" :initialValue="scope.row.jlcName" :style="{ 'pointer-events': mainDisabled || !rightData.patient.patientname || rightData.sectionResultMain.isAudit == 1 ? 'none' : '' }"> </input-select>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="show-bottom">
      <h3>【基本信息】</h3>
      <el-row style="padding-top: 10px">
        <el-col :span="16">
          <el-form :model="rightData.patient" ref="queryForm" size="small" :inline="true" label-width="70px">
            <el-form-item label="姓名" prop="patientname">
              <el-input v-model="rightData.patient.patientname" clearable style="width: 140px" readonly />
            </el-form-item>
            <el-form-item label="性别" prop="idSex">
              <el-select v-model="rightData.patient.idSex" style="width: 140px" placeholder=" " disabled>
                <el-option label="男" :value="0"> </el-option>
                <el-option label="女" :value="1"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input v-model="rightData.patient.age" clearable style="width: 140px" readonly />
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="rightData.patient.phone" clearable style="width: 140px" readonly />
            </el-form-item>
            <el-form-item label="体检号" prop="patientcode">
              <el-input v-model="rightData.patient.patientcode" clearable style="width: 180px" readonly />
            </el-form-item>
            <el-form-item label="类型" prop="idExamtype" style="margin-bottom: 0;">
              <el-select v-model="rightData.patient.idExamtype" style="width: 180px" placeholder=" " disabled>
                <el-option label="健康体检" value="0"> </el-option>
                <el-option label="职业体检" value="1"> </el-option>
                <el-option label="综合" value="2"> </el-option>
                <el-option label="复查" value="3"> </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="5">
          <img :src="rightData.picture ? imgPath + rightData.picture : rightData.picture" alt="" style="width: 100px; height: 130px" v-if="rightData.picture" />
          <div style="width: 100px; height: 130px; border: 1px #ccc solid" v-else></div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import { saveGIJlc, saveOrUpdateJlc } from '@/api/funcdept/section_list/index.js'
import AiChatBox from '@/components/AiChatBox'
export default {
  components: {
    AiChatBox
  },
  props: ['rightData', 'mainDisabled'],
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
      // 结论词参数
      selectData2: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '结论词', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 非多个禁用
      multiple: true,
      // 结论词选中的数组
      selectList: [],
      tableIndex: 1,
      indexI: 0, //视力检查
      jlcDataLength: 0, //视力检查
      imgPath: Cookies.get('imgPath'), //图片地址
    }
  },
  created() {
    this.ksID = this.$route.meta.ksID
    if (this.$route.meta.dataType == 5) {
      this.selectData2.url = '/abteilung/divisionLung/getConclusion'
    }
    // 视力检查 正反选绑定
    if (this.$route.meta.dataType == '11') {
      // this.bus.$off('checkboxSljc')
      // this.bus.$on('checkboxSljc', (jlcData) => {
      //   this.getCheckBoxState(jlcData)
      // })
      this.bus.$off('conclusionsSljc')
      this.bus.$on('conclusionsSljc', (conclusions, isAudit) => {
        this.getConclusion(conclusions, isAudit)
      })
    }
  },
  methods: {
    // 打开AI诊断弹窗
    openAiAnalysis() {
      // 检查是否有患者信息
      if (!this.rightData.patient || !this.rightData.patient.patientname) {
        this.$message.warning('没有患者信息，无法进行AI诊断')
        return
      }
      
      // 收集科室和体检者信息
      const departmentInfo = this.$route.meta.title || '检查科室'
      const patientInfo = this.rightData.patient
      const examResults = this.rightData.jlcData || []
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
    
    //视力检查获取小结
    getConclusion(conclusions, isAudit) {
      this.$delete(this.rightData.sectionResultMain, 'conclusions')
      this.$set(this.rightData.sectionResultMain, 'conclusions', conclusions)
      if (isAudit) {
        this.bus.$emit('handleAuditSljc')
      }
    },
    //视力检查多选框选中状态
    getCheckBoxState(jlcData) {
      this.indexI += 1
      if (this.indexI == 1) {
        this.jlcDataLength = this.rightData.jlcData.length
      }
      if (jlcData.length == 0) {
        //修改上传状态
        this.setGridData(0)
        //修改展示数组
        for (var i in this.rightData.jlcData) {
          if (this.rightData.jlcData[i].jcxm == '辨色力' && this.rightData.jlcData[i].jlcName == '色盲') {
            this.$delete(this.rightData.jlcData, i)
          } else if (this.rightData.jlcData[i].jcxm == '辨色力' && this.rightData.jlcData[i].jlcName == '色弱') {
            this.$delete(this.rightData.jlcData, i)
          }
        }
        return
      }
      if (jlcData.length == 1) {
        //判断是否有重复
        if (JSON.stringify(this.rightData.jlcData).includes(jlcData[0].jlcId) && JSON.stringify(this.rightData.jlcData).includes(jlcData[0].jcxm)) {
          if (jlcData[0].tzc == '未检' || jlcData[0].tzc == '正常') {
            return
          }
          for (var index = 0; index < this.rightData.jlcData.length; index++) {
            //单选筛选
            if (jlcData[0].tzc == this.rightData.jlcData[index].tzc && this.rightData.jlcData[index].tzc == '色盲' && this.rightData.jlcData[index].jcxm == '辨色力') {
              for (var k = 0; k < this.rightData.jlcData.length; k++) {
                if (this.rightData.jlcData[k].tzc == '色弱' && this.rightData.jlcData[k].jcxm == '辨色力') {
                  this.$delete(this.rightData.jlcData, k)
                }
              }
            } else if (jlcData[0].tzc == this.rightData.jlcData[index].tzc && this.rightData.jlcData[index].tzc == '色弱' && this.rightData.jlcData[index].jcxm == '辨色力') {
              // this.$set(this.rightData.griddata[index],"state","modified")
              for (var k = 0; k < this.rightData.jlcData.length; k++) {
                if (this.rightData.jlcData[k].tzc == '色盲' && this.rightData.jlcData[k].jcxm == '辨色力') {
                  this.$delete(this.rightData.jlcData, k)
                }
              }
            }
          }
          return
        } else {
          //无重复直接添加
          if (jlcData[0].tzc == '正常' || jlcData[0].tzc == '未检') {
            //正常,未检
            this.rightData.jlcData.forEach((el, index) => {
              if (el.tzc == '色盲' && el.jcxm == '辨色力') {
                this.$delete(this.rightData.jlcData, index)
                this.rightData.jlcData.forEach((els, i) => {
                  if (els.tzc == '色弱' && els.jcxm == '辨色力') {
                    this.$delete(this.rightData.jlcData, i)
                  }
                })
              }
              if (el.tzc == '色弱' && el.jcxm == '辨色力') {
                this.$delete(this.rightData.jlcData, index)
                this.rightData.jlcData.forEach((els, i) => {
                  if (els.tzc == '色盲' && els.jcxm == '辨色力') {
                    this.$delete(this.rightData.jlcData, i)
                  }
                })
              }
            })
          } else if (jlcData[0].tzc == '色盲' || jlcData[0].tzc == '色弱') {
            if (this.rightData.jlcData.length > 0) {
              for (var k = 0; k < this.rightData.jlcData.length; k++) {
                if (this.rightData.jlcData[k].tzc == '色盲' && this.rightData.jlcData[k].jcxm == '辨色力') {
                  this.$delete(this.rightData.jlcData, k)
                } else if (this.rightData.jlcData[k].tzc == '色弱' && this.rightData.jlcData[k].jcxm == '辨色力') {
                  this.$delete(this.rightData.jlcData, k)
                } else {
                  this.rightData.jlcData.push(...jlcData)
                  this.rightData.griddata.push(...jlcData)
                  this.$set(this.rightData.griddata[this.rightData.griddata.length - 1], 'state', 'added')
                }
              }
            } else {
              this.rightData.jlcData.push(...jlcData)
              this.rightData.griddata.push(...jlcData)
              this.$set(this.rightData.griddata[this.rightData.griddata.length - 1], 'state', 'added')
            }
          }
        }
      } else if (jlcData.length == 2) {
        if (this.rightData.jlcData.length > 0) {
          for (var index = 0; index < this.rightData.jlcData.length; index++) {
            if (this.rightData.jlcData[index].jcxm == '辨色力' && this.rightData.jlcData[index].jlcId == jlcData[1].jlcId) {
              this.$delete(this.rightData.jlcData, index)
              return
            } else {
              this.rightData.jlcData.splice(this.rightData.jlcData.length, 1, { ...jlcData[1] })
              this.rightData.griddata.splice(this.rightData.griddata.length, 1, { ...jlcData[1] })
              return
            }
          }
        } else {
          this.rightData.jlcData.splice(this.rightData.jlcData.length, 1, { ...jlcData[1] })
          this.rightData.griddata.splice(this.rightData.griddata.length, 1, { ...jlcData[1] })
          // this.$set(this.rightData.griddata[this.rightData.griddata.length-1],"state","added")
          return
        }
      }
    },
    //修改上传状态
    setGridData(context) {
      if (context == '0') {
        this.rightData.griddata.forEach((el) => {
          if (el.jlcName == '色盲' && el.jcxm == '辨色力') {
            this.$set(el, 'state', 'remove')
          }
          if (el.jlcName == '色弱' && el.jcxm == '辨色力') {
            this.$set(el, 'state', 'remove')
          }
        })
      }
    },
    // 获取数据
    getList() {
      this.loading = false
    },
    // 检查人返回选中的值
    selectChange(value) {
      this.$emit('selectChange', value)
      // this.rightData.sectionResultMain.rummagerId = value.id
    },
    // 录入人返回选中的值
    selectChange1(value) {
      this.$emit('selectChange1', value)
      // this.rightData.sectionResultMain.rummagerId = value.id
    },
    // 表格选中
    handleSelectionChange(selection) {
      this.selectList = selection.map((item) => item)
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
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
      this.rightData.jlcData.unshift(obj)
    },
    // 结论词返回值
    selectChange2(value, scope) {
      this.rightData.jlcData[scope.$index] = value
      if (value) {
        this.rightData.jlcData[scope.$index].jlcId = this.rightData.jlcData[scope.$index].id ? this.rightData.jlcData[scope.$index].id : undefined
        this.rightData.jlcData[scope.$index].jlcName = this.rightData.jlcData[scope.$index].name ? this.rightData.jlcData[scope.$index].name : undefined
      }
      this.rightData.jlcData = this.rightData.jlcData.slice(0)
    },
    // 删除
    handleRemove() {
      this.selectList.forEach((el) => {
        this.rightData.jlcData.forEach((val, index) => {
          if ((val.tableIndex && el.tableIndex == val.tableIndex) || (val.id && el.id == val.id) || (val.jlcId && el.jlcId == val.jlcId)) {
            this.rightData.jlcData.splice(index, 1)
          }
        })
      })
    },
    // 保存
    handleSave() {
      if (this.$route.meta.dataType == '4') {
        let query2 = {
          ksID: this.$route.meta.ksID,
          patientcode: this.rightData.patient.patientcode,
          griddata: this.rightData.jlcData,
        }
        saveOrUpdateJlc(query2).then(({ data }) => {
          this.$modal.msgSuccess('保存结论词成功')
        })
        return
      } else if (this.$route.meta.dataType == '11') {
        // return;
        let arrayData = []
        arrayData = this.rightData.jlcData.map((item) => {
          return {
            jcxmId: item.jcxmId,
            jlcId: item.jlcId,
            ms: item.jlcName,
            name: item.tzc,
            sfxmId: item.chargesId || item.sfxmId,
            tzcId: item.tzcId,
          }
        })
        let sljcObj = {
          ksID: this.$route.meta.ksID,
          patientcode: this.rightData.patient.patientcode,
          griddata: arrayData,
        }
        saveOrUpdateJlc(sljcObj).then(({ data }) => {
          this.$modal.msgSuccess('保存结论词成功')
          // this.$emit('getSectionDataOnly', this.rightData.patient.patientcode, this.ksID)
        })
        return
      }
      let query = {
        ksId: this.$route.meta.ksID,
        patientCode: this.rightData.patient.patientcode,
        jlcGridFairchecks: this.rightData.jlcData,
      }
      saveGIJlc(query).then(({ data }) => {
        this.$modal.msgSuccess('保存结论词成功')
        if (this.$route.meta.dataType == '3') {
          this.$emit('getSectionDataOnly', this.rightData.patient.patientcode, this.ksID)
        }
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.summary-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
  margin-bottom: 5px;
  
  h3 {
    margin: 0;
  }
  
  .ai-analysis-btn {
    margin-right: 10px;
  }
}
</style>

<style lang="scss">
.section-list-right-a {
  h3 {
    margin: 0;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .add-table {
    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0;
      text-align: left;
      background: transparent;

      &:focus {
        border-width: 1px;
      }
    }
  }

  .show-right {
    padding: 10px 10px 0;

    .el-textarea__inner {
      background-color: #f6f7fb;
    }

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 8px;
    }
  }

  .show-right-middle {
    min-height: 200px;
    border: none;
    border-top: #f6f7fb 4px solid;
    border-bottom: #f6f7fb 4px solid;

    .el-table--enable-row-transition .el-table__body td.el-table__cell {
      padding: 0;
    }
  }

  .show-bottom {
    height: 200px;
    padding: 10px;
    padding-bottom: 0;

    .el-textarea__inner {
      background-color: #f6f7fb;
    }
  }
}
</style>
<style scoped>
.section-list-right-a /deep/ .add-table .el-input__inner {
  text-align: center;
}
.section-a-form /deep/ .el-form-item__content {
  width: 100% !important;
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
