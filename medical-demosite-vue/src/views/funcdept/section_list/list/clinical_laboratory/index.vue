<!-- 检验科  开发人：麦沃德科技 予安 -->
<template>
  <div class="flex-direction-column clinical-laboratory">
    <!-- AI分析弹窗 -->
    <AiChatBox
      :visible.sync="aiChatVisible"
      :initial-text="aiInitialText"
      title="AI 检验结果诊断"
      :close-on-click-modal="false"
      @result="handleAiResult"
    />
    <!-- 表格 -->
    <div class="table-box">
      <el-table border :data="clData.tableList" height="100%" stripe :row-class-name="changRed" :span-method="objectSpanMethod">
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="收费项目" prop="examfeeitem" align="center" min-width="200" />
        <el-table-column label="检验项目" prop="examitemNameR" align="center" min-width="200" />
        <el-table-column label="LIS代码" prop="lisCode" align="center" min-width="120" />
        <el-table-column label="检验结果" align="center">
          <el-table-column label="数值" prop="examitemvaluesnumber" align="center" min-width="80" />
          <el-table-column label="结果" prop="examitemvaluesshort" align="center" min-width="80" />
          <el-table-column label="报告结果" prop="examitemvaluesreport" align="center" min-width="120" />
          <el-table-column label="正常" prop="status" align="center" min-width="80" />
          <el-table-column label="单位" prop="units" align="center" min-width="80" />
        </el-table-column>
        <el-table-column label="参考范围" align="center">
          <el-table-column label="LIS范围" prop="refrange" align="center" min-width="120" />
          <el-table-column label="报告范围" prop="reportRange" align="center" min-width="120" />
          <el-table-column label="最小值" prop="reflow" align="center" min-width="80" />
          <el-table-column label="最大值" prop="refhigh" align="center" min-width="80" />
        </el-table-column>
        <el-table-column label="检验医师" prop="inspectName" align="center" show-overflow-tooltip min-width="120" />
        <el-table-column label="审核医师" prop="auditName" align="center" show-overflow-tooltip min-width="120" />
        <el-table-column label="检验时间" prop="examDateTime" align="center" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            {{ scope.row.examDateTime ? scope.row.examDateTime.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="LIS样本编号" prop="lisybbh" align="center" show-overflow-tooltip min-width="100" />
      </el-table>
    </div>
    <el-row style="height: 200px; border-top: #f6f7fb 4px solid">
      <!-- 小结 -->
      <el-col :span="6" style="background-color: #fff">
        <div class="show-bottom">
          <div class="summary-header">
            <h3>【小结】</h3>
            <el-button 
              type="primary" 
              size="mini" 
              icon="el-icon-cpu" 
              class="ai-analysis-btn" 
              @click="openAiAnalysis"
            >AI诊断</el-button>
          </div>
          <el-input v-model="clData.sectionResultMain.conclusions" type="textarea" :rows="5" placeholder="请输入内容" :readonly="mainDisabled"></el-input>
        </div>
      </el-col>
      <!-- 中间列表 -->
      <el-col :span="9" style="border-left: #f6f7fb 4px solid; border-right: #f6f7fb 4px solid">
        <el-row style="padding: 10px; padding-bottom: 0" :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-plus" @click="handleAdd" :disabled="mainDisabled" v-hasPermi="['funcdept:sectionList:clinicalLaboratory:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-delete" @click="handleRemove" :disabled="mainDisabled || multiple" v-hasPermi="['funcdept:sectionList:clinicalLaboratory:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-folder-add" @click="handleSave" :disabled="mainDisabled" v-hasPermi="['funcdept:sectionList:clinicalLaboratory:save']">保存</el-button>
          </el-col>
        </el-row>
        <div class="add-table">
          <el-table border :data="clData.jlcData" height="150px" stripe @selection-change="handleSelectionChange" style="margin: -1px">
            <el-table-column fixed type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="检查项目" prop="jcxm" align="center" min-width="100" />
            <el-table-column label="体征词" prop="tzc" align="center" min-width="100" />
            <el-table-column label="结论词" prop="jlcName" align="center" width="205">
              <template slot-scope="scope">
                <input-select :selectData="selectData" @change="selectChange($event, scope)" :selectSize="'mini'" :initialValue="scope.row.jlcName" :style="{ 'pointer-events': mainDisabled ? 'none' : '' }"> </input-select>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <!-- 基本信息 -->
      <el-col :span="9" style="background-color: #fff">
        <div class="show-bottom">
          <h3>【基本信息】</h3>
          <el-row>
            <el-col :span="19">
              <el-form :model="clData" ref="clData" size="small" :inline="true" label-width="70px">
                <el-form-item label="姓名" prop="patientname">
                  <el-input v-model="clData.peispatient.patientname" style="width: 180px" readonly />
                </el-form-item>
                <el-form-item label="性别" prop="idSex">
                  <el-input :value="clData.peispatient.idSex == 0 ? '男' : clData.peispatient.idSex == 1 ? '女' : ''" style="width: 180px" readonly />
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                  <el-input v-model="clData.peispatient.age" style="width: 180px" readonly />
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                  <el-input v-model="clData.peispatient.phone" style="width: 180px" readonly />
                </el-form-item>
                <el-form-item label="体检号" prop="patientcode" style="margin-bottom: 0">
                  <el-input v-model="clData.peispatient.patientcode" style="width: 180px" readonly />
                </el-form-item>
                <el-form-item label="类型" prop="idExamtype" style="margin-bottom: 0">
                  <el-input :value="clData.peispatient.idExamtype == 0 ? '健康体检' : clData.peispatient.idExamtype == 1 ? '职业体检' : clData.peispatient.idExamtype == 2 ? '综合' : clData.peispatient.idExamtype == 3 ? '复查' : ''" style="width: 180px" readonly />
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="5">
              <img :src="clData.picture ? imgPath + clData.picture : clData.picture" alt="" style="width: 100px; height: 130px" v-if="clData.picture" />
              <div style="width: 100px; height: 130px; border: 1px #ccc solid" v-else></div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <!-- 下方 -->
  </div>
</template>
<script>
import { saveclJlc } from '@/api/funcdept/section_list/clinical_laboratory.js'
import Cookies from 'js-cookie'
import AiChatBox from '@/components/AiChatBox'
export default {
  components: {
    AiChatBox
  },
  props: ['clData', 'clTableList', 'mainDisabled'],
  data() {
    return {
      // 传给头部组件的值
      headParams: {
        patientcode: '',
        autoFill: true,
      },
      // 显示搜索条件
      showSearch: true,
      // 表单参数
      form: {},

      // 下方************************
      // 小结
      summary: '',
      // 结论词参数
      selectData: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '结论词', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      multiple: true,
      // 结论词选中列表
      selectList: [],
      // 结论词标识
      tableIndex: 1,
      //图片地址
      imgPath: Cookies.get('imgPath'),
      cellList: [], // 单元格数组
      // 审核时禁用按钮
      forbiddenAudit: false,
      // AI分析相关
      aiChatVisible: false,
      aiInitialText: '',
      aiAnalysisResult: '',
    }
  },
  methods: {
    // 异常行变红
    changRed({ row }) {
      console.log("异常行变红", row);

      // 检查 status 字段
      if (row.status) {
        console.log("Status value:", row.status);
        console.log(1111);
        
        if (row.status.indexOf('↑') != -1 || row.status.indexOf('↓') != -1) {
          console.log("Matched status condition, returning 'row-red'");
          return 'row-red';
        }
      }

      // 检查 examitemvaluesshort 字段
      if (row.examitemvaluesshort) {
        console.log(2222);

        const value = row.examitemvaluesshort.trim(); // 去除空格
        console.log('Trimmed value of examitemvaluesshort:', JSON.stringify(value));

        if (value.indexOf('阳性') !== -1 || value.replace(/[Ee]\+/g, "").indexOf("+") !== -1) {
          console.log('Matched examitemvaluesshort condition, returning "row-red"');
          return 'row-red';
        }
      } else {
        console.log(3333);

        console.log("examitemvaluesshort is undefined or null");
      }
    },
    // 计算要合并的单元格
    computeCell(tableBody, fn) {
      this.cellList = []
      let same = ''
      tableBody.forEach((el, index) => {
        if (el.examfeeitem != same) {
          this.cellList.push(index)
          same = el.examfeeitem
        }
      })
      if (fn) {
        fn()
      }
    },
    // 合并相同收费项目
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      // this.computeCell(this.clData.tableList)
      if (columnIndex == 1) {
        if (this.cellList.includes(rowIndex)) {
          let index = this.cellList.indexOf(rowIndex)
          let rowspan = undefined
          if (index == this.cellList.length - 1) {
            rowspan = this.clData.tableList.length - this.cellList[index]
          } else {
            rowspan = this.cellList[index + 1] - this.cellList[index]
          }
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },

    // 下方***********************
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
      this.clData.jlcData.unshift(obj)
    },
    // 结论词返回值
    selectChange(value, scope) {
      this.clData.jlcData[scope.$index] = value
      if (value) {
        this.clData.jlcData[scope.$index].jlcId = this.clData.jlcData[scope.$index].id ? this.clData.jlcData[scope.$index].id : undefined
        this.clData.jlcData[scope.$index].jlcName = this.clData.jlcData[scope.$index].name ? this.clData.jlcData[scope.$index].name : undefined
      }
      this.clData.jlcData = this.clData.jlcData.slice(0)
    },
    // 删除
    handleRemove() {
      this.selectList.forEach((el) => {
        this.clData.jlcData.forEach((val, index) => {
          if (el.tableIndex == val.tableIndex || (val.jlcId && el.jlcId == val.jlcId)) {
            this.clData.jlcData.splice(index, 1)
          }
        })
      })
    },
    // 保存
    handleSave() {
      if (this.forbiddenAudit) {
        return
      }
      this.forbiddenAudit = true
      let query = {
        id: this.clData.sectionResultMain.id,
        ksId: this.$route.meta.ksID,
        patientCode: this.clData.peispatient.patientcode,
        jlcGrids: this.clData.jlcData,
        conclusions: this.clData.sectionResultMain.conclusions,
      }
      saveclJlc(query)
        .then(() => {
          this.$modal.msgSuccess('保存结论词成功')
          this.forbiddenAudit = false
        })
        .catch((error) => {
          console.error(error)
          this.forbiddenAudit = false
        })
    },
    
    // 打开AI诊断弹窗
    openAiAnalysis() {
      // 检查是否有检验数据
      if (!this.clData.tableList || this.clData.tableList.length === 0) {
        this.$message.warning('检验数据为空，无法进行AI诊断')
        return
      }
      
      // 格式化检验结果数据
      const formattedResults = this.clData.tableList.map(item => {
        return {
          name: item.examitemNameR || '',
          value: item.examitemvaluesnumber || item.examitemvaluesshort || item.examitemvaluesreport || '',
          unit: item.units || '',
          status: item.status || '',
          refRange: item.refrange || `${item.reflow || ''}-${item.refhigh || ''}` || ''
        }
      }).filter(item => item.name && item.value); // 过滤掉没有名称或结果的项目
      
      // 将检验结果格式化为表格形式的文本
      let resultsTable = '检验项目 | 结果值 | 单位 | 状态 | 参考范围\n';
      resultsTable += '--- | --- | --- | --- | ---\n';
      
      formattedResults.forEach(item => {
        const status = item.status === 'H' ? '异常偏高' : 
                     item.status === 'L' ? '异常偏低' : 
                     item.status === 'N' ? '正常' : '未知';
        resultsTable += `${item.name} | ${item.value} | ${item.unit} | ${status} | ${item.refRange}\n`;
      });

      if (resultsTable.trim() == '') {
        resultsTable= this.clData.sectionResultMain.conclusions;
      }
      
      // 准备AI诊断的提示文本
      const patientInfo = `您好，请对以下检验结果进行诊断和分析：

顾客信息：
- 姓名：${this.clData.peispatient.patientname || '未知'}
- 性别：${this.clData.peispatient.idSex == 0 ? '男' : this.clData.peispatient.idSex == 1 ? '女' : '未知'}
- 年龄：${this.clData.peispatient.age || '未知'}

检验结果：
${resultsTable}

请对以上检验结果进行诊断，并提供以下内容：

## 小结
请列出所有异常值及其偏离正常范围的程度。

## 结论
这些异常值可能指向的健康问题和趋势，以及是否需要进一步检查或注意事项。

## 健康建议
有哪些具体的建议可以改善这些指标，包括生活方式、饮食和可能的药物或治疗方案。`;
      
      // 设置初始文本并打开对话框
      this.aiInitialText = patientInfo;
      this.aiChatVisible = true;
    },
    
    // 处理AI诊断结果
    handleAiResult(result) {
      this.aiAnalysisResult = result;
      // 可以将AI诊断结果应用到小结中或其他地方
      this.$message.success('AI诊断完成！');
      
      // 如果需要将结果应用到小结，可以打开下面的注释
      // this.clData.sectionResultMain.conclusions += '\n\n--- AI诊断结果 ---\n' + result;
    },
  },
}
</script>
<style lang="scss">
.clinical-laboratory {
  padding: 0;

  .show-bottom {
    overflow-y: auto;
    height: 200px;
    padding: 10px;

    .summary-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 10px;
      
      h3 {
        margin: 5px 0;
        font-weight: 600;
        font-size: 16px;
        line-height: 22px;
        color: #333333;
      }
      
      .ai-analysis-btn {
        padding: 5px 10px;
        font-size: 12px;
        transition: all 0.3s;
        
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 2px 8px rgba(64, 158, 255, 0.4);
        }
        
        &:disabled {
          cursor: not-allowed;
          opacity: 0.6;
        }
      }
    }

    .el-textarea__inner {
      background-color: #f6f7fb;
    }
  }
  .row-red .cell {
    color: red !important;
  }

  .add-table {
    border: 1px solid #d4d6d9;

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
    .el-table--enable-row-transition .el-table__body td.el-table__cell {
      padding: 0;
    }
  }
}
</style>
<style scoped>
.clinical-laboratory /deep/ .el-table .el-table__cell {
  padding: 5px 0 !important;
  height: auto;
}
.clinical-laboratory /deep/ .el-table--medium .el-table__cell {
  padding: 5px 0 !important;
  height: auto;
}
</style>
