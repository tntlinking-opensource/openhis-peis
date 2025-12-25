<template>
  <div class="app-container">
    <div class="ai-report-interpret">
      <div class="ai-header">
        <div class="ai-header-title">
          <i class="el-icon-document"></i>
          <h1>AI 智能体检报告解读</h1>
        </div>
        <div class="ai-header-subtitle">基于人工智能分析，为您提供专业的体检报告解读</div>
      </div>

      <div class="report-interpret-container">
        <!-- 左侧体检号输入和科室列表 -->
        <div class="left-panel">
          <div class="panel-header">
            <i class="el-icon-search"></i>
            <span>体检信息查询</span>
          </div>
          
          <el-form :model="formData" ref="formRef" label-width="60px" size="small" class="animated-form">
            <el-form-item label="体检号" prop="examNo">
              <div class="input-icon-wrapper">
                <i class="el-icon-document-checked input-icon"></i>
                <el-input
                  v-model="formData.examNo"
                  placeholder="请输入体检号"
                  clearable
                >
                  <el-button slot="append" icon="el-icon-search" @click="searchExamination"></el-button>
                </el-input>
              </div>
            </el-form-item>
            
            <!-- <el-form-item class="form-actions">
              <el-button 
                type="primary" 
                @click="searchExamination" 
                :loading="loading" 
                class="submit-button"
                icon="el-icon-search">
                <span>查询</span>
              </el-button>
              <el-button 
                @click="resetForm" 
                icon="el-icon-refresh-right"
                class="reset-button">
                重置
              </el-button>
            </el-form-item> -->
          </el-form>

          <!-- 科室列表 -->
          <div v-if="departments.length > 0" class="department-list">
            
            <el-card shadow="hover" class="department-card overall-card" @click.native="selectDepartment(null)">
              <div class="department-info">
                <div class="department-name">整体报告解读</div>
                <div class="department-actions">
                  <el-button 
                    type="success" 
                    size="mini" 
                    icon="el-icon-chat-line-square"
                    @click="interpretOverall">
                    整体解读
                  </el-button>
                </div>
              </div>
            </el-card>
            <div class="list-header">
              <i class="el-icon-office-building"></i>
              <span>单科室报告解读-检查科室列表</span>
            </div>
            <el-card shadow="hover" class="department-card" v-for="(dept, index) in departments" :key="index" @click.native="selectDepartment(dept)">
              <div class="department-info">
                <div class="department-name">{{ dept.yblx }}</div>
                <div class="department-actions">
                  <el-button 
                    type="primary" 
                    size="mini" 
                    icon="el-icon-chat-line-square"
                    @click.stop="interpretDepartment(dept)">
                    解读
                  </el-button>
                </div>
              </div>
            </el-card>
          </div>
        </div>
        
        <!-- 右侧体检者信息和科室数据 -->
        <div class="right-panel">
          <div class="result-header">
            <div class="result-title">
              <i class="el-icon-user"></i>
              <span>体检者信息</span>
            </div>
          </div>
          
          <div v-if="loading" class="loading-container">
            <div class="loading-animation">
              <i class="el-icon-loading"></i>
              <div class="pulse-circle"></div>
            </div>
            <div class="loading-text">
              <p>正在查询体检信息</p>
              <div class="typing-dots">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
          
          <!-- 单科室报告解读时仅显示4项基础信息（每行一项） -->
          <div v-else-if="patientInfo && selectedDepartment" class="patient-info">
            <div class="patient-info-header" @click="isPatientInfoCollapsed = !isPatientInfoCollapsed">
              <span>体检者信息</span>
              <i :class="['el-icon-arrow-down', { 'rotate-180': !isPatientInfoCollapsed }]"></i>
            </div>
            <el-collapse-transition>
              <div v-show="!isPatientInfoCollapsed" class="patient-brief">
                <div class="brief-row" v-if="patientInfo.patientname">姓名：{{ patientInfo.patientname }}</div>
                <div class="brief-row" v-if="patientInfo.patientcode">体检号：{{ patientInfo.patientcode }}</div>
                <div class="brief-row" v-if="patientInfo.age">年龄：{{ patientInfo.age }}</div>
                <div class="brief-row" v-if="patientInfo.sex">性别：{{ patientInfo.sex }}</div>
                <div class="brief-row" v-if="patientInfo.idPatientclass">体检类型：{{ patientInfo.idPatientclass==1?'健康体检':patientInfo.idPatientclass==2?'职业体检':'综合体检' }}</div>
              </div>
            </el-collapse-transition>
          </div>
          
          <!-- 未选择科室时展示完整信息集 -->
          <div v-else-if="patientInfo && !selectedDepartment" class="patient-info">
            <div class="patient-info-header" @click="isPatientInfoCollapsed = !isPatientInfoCollapsed">
              <span>体检者信息</span>
              <i :class="['el-icon-arrow-down', { 'rotate-180': !isPatientInfoCollapsed }]"></i>
            </div>
            <el-collapse-transition>
              <div v-show="!isPatientInfoCollapsed" class="patient-brief">
                <div class="brief-row" v-if="patientInfo.patientname">姓名：{{ patientInfo.patientname }}</div>
                <div class="brief-row" v-if="patientInfo.age">年龄：{{ patientInfo.age }}</div>
                <div class="brief-row" v-if="patientInfo.sex">性别：{{ patientInfo.sex }}</div>
                <div class="brief-row" v-if="patientInfo.patientcode">体检号：{{ patientInfo.patientcode }}</div>
                <div class="brief-row" v-if="patientInfo.phone">电话：{{ patientInfo.phone }}</div>
                <div class="brief-row" v-if="patientInfo.dateregister">登记时间：{{ patientInfo.dateregister }}</div>
                <div class="brief-row" v-if="patientInfo.idPatientclass">体检类型：{{ patientInfo.idPatientclass==1?'健康体检':patientInfo.idPatientclass==2?'职业体检':'综合体检' }}</div>
                <div class="brief-row" v-if="patientInfo.org">单位：{{ patientInfo.org }}</div>
                <div class="brief-row" v-if="patientInfo.department">部门：{{ patientInfo.department }}</div>
                <div class="brief-row" v-if="patientInfo.reportdate">报告日期：{{ patientInfo.reportdate }}</div>
                
              </div>
            </el-collapse-transition>
          </div>
          
          <template v-if="selectedDepartment">
            <div class="department-data">
              <div class="department-header" @click="toggleDepartment(selectedDepartment)">
                <i class="el-icon-data-analysis"></i>
                <span>{{ selectedDepartment.yblx }} 检查数据</span>
                <i :class="['el-icon-arrow-down', { 'rotate-180': !selectedDepartment.isCollapsed }]"></i>
              </div>
              <el-collapse-transition>
                <div v-show="!selectedDepartment.isCollapsed" class="department-content">
                  <el-table
                    :data="selectedDepartment.items || []"
                    style="width: 100%"
                    border
                    stripe
                    size="small">
                    <el-table-column prop="name" label="检查项目" min-width="150"></el-table-column>
                    <el-table-column prop="value" label="检查结果" min-width="100"></el-table-column>
                    <el-table-column prop="unit" label="单位" width="80"></el-table-column>
                    <el-table-column prop="reference" label="参考范围" min-width="150"></el-table-column>
                  </el-table>
                  
                  <div v-if="selectedDepartment.pConclusion" class="conclusion">
                    <div class="conclusion-title">检查结论</div>
                    <div class="conclusion-content">{{ selectedDepartment.pConclusion }}</div>
                  </div>
                </div>
              </el-collapse-transition>
            </div>
          </template>

          <template v-else-if="patientInfo && !selectedDepartment">
            <div class="department-data">
              <div class="department-header" @click="isReportCollapsed = !isReportCollapsed">
                <i class="el-icon-data-analysis"></i>
                <span>体检报告</span>
                <i :class="['el-icon-arrow-down', { 'rotate-180': !isReportCollapsed }]"></i>
              </div>
              <el-collapse-transition>
                <div v-show="!isReportCollapsed" class="department-content">
                  <div class="block">
                    <div class="block-label">体检数据：</div>
                    <div class="block-content">{{ '......' }}</div>
                  </div>

                  <div v-if="patientInfo.totaloffer" class="conclusion">
                    <div class="conclusion-title">总检医师：{{ patientInfo.totaldoctor }}</div>
                    <div class="conclusion-title">总检建议：</div>
                    <div class="conclusion-content">{{ patientInfo.totaloffer }}</div>
                  </div>
                </div>
              </el-collapse-transition>
            </div>
          </template>
          
          <div v-else-if="!loading" class="empty-result">
            <i class="el-icon-search"></i>
            <p>请输入体检号查询体检信息</p>
            <div class="empty-result-tips">
              <i class="el-icon-info"></i>
              <span>输入体检号后点击"查询"按钮获取体检信息</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- AI解读对话框组件 -->
    <ai-chat-box
      btntext="解读"
      :visible.sync="chatVisible"
      :title="chatTitle"
      :initial-text="chatInitialText"
      :function-code="functionCode"
      @result="handleChatResult"
      @close="handleChatClose"
    ></ai-chat-box>
    </div>
</template>

<script>
import AiChatBox from '@/components/AiChatBox/index'
import { getData as getIndividualReport } from '@/api/preview/individual_report'

export default {
  name: 'AiReportInterpret',
  components: {
    AiChatBox
  },
  data() {
    return {
      isPatientInfoCollapsed: false,
      isReportCollapsed: false,
      formData: {
        examNo: '010232254584'
      },
      loading: false,
      functionCode: 4,
      patientInfo: null,
      departments: [],
      selectedDepartment: null,
      
      // AI对话框相关
      chatVisible: false,
      chatTitle: 'AI 报告解读',
      chatInitialText: '',
      chatType: '', // 'department' 或 'overall'
      currentDepartment: null
    }
  },
  methods: {
    // 查询体检信息
    async searchExamination() {
      if (!this.formData.examNo.trim()) {
        this.$message.warning('请输入体检号')
        return
      }

      this.loading = true
      this.patientInfo = null
      this.departments = []
      this.selectedDepartment = null
      this.$modal.loading('正在查询...，请稍等...')

      try {
        const params = {
          reportType: 5,
          patientcode: this.formData.examNo
        }
        const res = await getIndividualReport(params)
        if (!res || res.code !== 200) {
          throw new Error(res && res.msg ? res.msg : '查询失败')
        }

        const data = res.data || {}
        const head = data.head || {}
        const p = head.parameters || {}

        // 仅保留 parameters 下指定字段
        this.patientInfo = {
          patientname: p.patientname || head.patientname || '',
          patientcode: p.patientcode || head.patientcode || this.formData.examNo,
          phone: p.phone || '',
          reportdate: p.reportdate || '',
          idPatientclass: p.idPatientclass || head.idPatientClass || '',
          org_address: p.org_address || '',
          totaloffer: p.totaloffer || '',
          sex: p.sex || head.sex || '',
          org: p.org || '',
          org_phone: p.org_phone || '',
          department: p.department || '',
          dateregister: p.dateregister || head.dateregister || '',
          totaldoctor: p.totaldoctor || head.totaldoctor || '',
          age: p.age || head.age || ''
        }

        const ksList = Array.isArray(data.ksList) ? data.ksList : []
        this.departments = ksList.map((ks, idx) => {
          const kHead = ks.head || {}
          const kSummary = ks.summary || {}
          const items = []

          const detailItems = Array.isArray(ks.item) ? ks.item : []
          const records = []
          detailItems.forEach(di => {
            const examList = Array.isArray(di.EXAMLIST) ? di.EXAMLIST : []
            if (examList.length > 0) {
              records.push({ DATE: di.DATE || '', RECEIVE_DATE: di.RECEIVE_DATE || '' })
              examList.forEach(e => {
                items.push({
                  name: e.ITEM || di.ITEM_NAME || '',
                  value: e.RESULT || e.SIGN || '',
                  unit: e.SIGN || e.UNIT || '',
                  reference: e.UNIT || e.CONSULT || '',
                  status: String(e.red) === '1' ? 'abnormal' : 'normal'
                })
              })
            } else if (di.ITEM_NAME) {
              items.push({
                name: di.ITEM_NAME,
                value: (di.TXT_VALUES || '').trim() || (kSummary.P_CONCLUSIONS || ''),
                unit: '',
                reference: '',
                status: 'normal'
              })
            }
          })

        const conclusions = Array.isArray(kSummary.CONCLUSIONS)
            ? kSummary.CONCLUSIONS.map(c => c.conclu).join('\n')
            : ''

          return {
            id: idx + 1,
            yblx: kHead.yblx || kHead.DEP_NAME || '科室',
            pConclusion: kSummary.P_CONCLUSIONS || conclusions || '',
            records,
            items
          }
        })

        if (this.departments.length === 0) {
          this.$message.info('未查询到该体检号的科室明细')
        }
      } catch (err) {
        this.$message.error(err.message || '查询体检者信息失败，请检查与体检系统数据库连接！')
      } finally {
        this.$modal.closeLoading()
        this.loading = false
      }
    },
    
    // 重置表单
    resetForm() {
      this.$refs.formRef.resetFields()
      this.patientInfo = null
      this.departments = []
      this.selectedDepartment = null
    },
    
    // 仅查看科室数据
    selectDepartment(dept) {
      // this.selectedDepartment = {
      //   ...dept,
      //   isCollapsed: false
      // };
      this.selectedDepartment = dept
    },
    toggleDepartment(dept) {
      if (dept) {
        dept.isCollapsed = !dept.isCollapsed;
        this.$set(this, 'selectedDepartment', {...dept});
      }
    },
    
    // 解读单个科室报告
    interpretDepartment(department) {
      this.functionCode = 4
      this.selectedDepartment = department
      this.currentDepartment = department
      this.chatType = 'department'

      //构建提示词组合参数：
      const params = {
        patientName: this.patientInfo.patientname,
        gender: this.patientInfo.sex,
        age: this.patientInfo.age,
        department: department.yblx,
        items: '',
        conclusion: department.pConclusion
      }

      let prompt = ``
      prompt += `【体检者信息】\n`
      prompt += `姓名：${this.patientInfo.patientname}\n`
      prompt += `性别：${this.patientInfo.sex}\n`
      prompt += `年龄：${this.patientInfo.age}\n`
      prompt += `【科室】${department.yblx}\n`
      prompt += `【检查项目】\n`
      let itemsStr = ''
      department.items.forEach(item => {
        itemsStr += `- ${item.name}：${item.value} ${item.unit}（参考范围：${item.reference}）${item.status === 'normal' ? '正常' : '异常'}\n`
      })
      params.items = itemsStr
      console.log('单科室报告AI解读请求参数：', params)
      prompt += itemsStr
      
      prompt += `\n【科室结论】\n${department.pConclusion}\n`
      
      this.chatTitle = `${department.yblx} - AI报告解读`
      this.chatInitialText = prompt
      this.chatVisible = true
    },
    
    // 整体报告解读
    interpretOverall() {
      this.functionCode = 5
      this.chatType = 'overall'
      this.selectedDepartment = null
      
      // 仅使用指定字段构建整体提示词
      let prompt = ``
      prompt += `【体检者信息】\n`
      prompt += `姓名：${this.patientInfo.patientname}\n`
      prompt += `性别：${this.patientInfo.sex}\n`
      prompt += `年龄：${this.patientInfo.age}\n`
      prompt += `总检医师：${this.patientInfo.totaldoctor}\n`
      prompt += `总检建议：${this.patientInfo.totaloffer}\n\n`
      
      prompt += `【各科室结果】\n`
      this.departments.forEach(dept => {
        prompt += `\n科室：${dept.yblx}\n`
        // if (dept.records && dept.records.length) {
        //   dept.records.forEach(rec => {
        //     prompt += `采样时间：${rec.DATE}，接收时间：${rec.RECEIVE_DATE}\n`
        //   })
        // }
        prompt += `检查项目：\n`
        dept.items.forEach(item => {
          prompt += `- ${item.name}：${item.value} ${item.unit}（参考范围：${item.reference}）${item.status === 'normal' ? '正常' : '异常'}\n`
        })
        prompt += `科室结论：${dept.pConclusion}\n`
      })
      
      this.chatTitle = `整体体检报告 - AI解读`
      this.chatInitialText = prompt
      this.chatVisible = true
    },
    
    // 处理AI对话框结果
    handleChatResult(result) {
      this.$message.success('AI解读完成')
      // 可以在这里处理AI返回的结果，例如保存到历史记录等
    },
    
    // 处理AI对话框关闭
    handleChatClose() {
      this.chatVisible = false
      this.chatInitialText = ''
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
  background-color: #f5f7fa;
  height: calc(100vh - 118px);
}

.ai-report-interpret {
  height: 100%;
  max-width: 1400px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
  animation: fadeIn 0.5s ease-in-out;
  
  .ai-header {
    background: linear-gradient(135deg, #409EFF 0%, #67C23A 100%);
    color: white;
    padding: 20px;
    text-align: center;
    height: 100px;
    
    .ai-header-title {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 10px;
      
      i {
        font-size: 28px;
        margin-right: 10px;
      }
      
      h1 {
        font-size: 24px;
        margin: 0;
      }
    }
    
    .ai-header-subtitle {
      font-size: 14px;
      opacity: 0.8;
    }
  }
}

.report-interpret-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  height: calc(100% - 100px);
  overflow: hidden;
  
  .left-panel {
    flex: 1;
    min-width:200px;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    
    .panel-header {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 2px solid #409EFF;
      
      i {
        font-size: 20px;
        color: #409EFF;
        margin-right: 10px;
      }
      
      span {
        font-size: 18px;
        font-weight: bold;
        color: #303133;
      }
    }
    
    .animated-form {
      .input-icon-wrapper {
        position: relative;
        
        .input-icon {
          position: absolute;
          top: 7px;
          left: 2px;
          color: #909399;
          z-index: 1;
        }
      }
      
      .form-actions {
        display: flex;
        justify-content: center;
        margin-top: 30px;
        
        .submit-button {
          padding: 12px 20px;
          font-size: 16px;
          transition: all 0.3s;
          
          &:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
          }
        }
        
        .reset-button {
          margin-left: 15px;
        }
      }
    }
    
    .department-list {
      margin-top: 30px;
      
      .list-header {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
        
        i {
          font-size: 18px;
          color: #409EFF;
          margin-right: 10px;
        }
        
        span {
          font-size: 16px;
          font-weight: bold;
          color: #303133;
        }
      }
      
      .department-card {
        margin-bottom: 10px;
        transition: all 0.3s;
        
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        
        .department-info {
          display: flex;
          justify-content: space-between;
          align-items: center;
          
          .department-name {
            font-weight: bold;
            color: #303133;
          }
        }
        
        &.overall-card {
          margin-top: 20px;
          border: 1px solid #67C23A;
          
          .department-name {
            color: #67C23A;
            font-size: 16px;
          }
        }
      }
    }
  }
  
  .right-panel {
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    flex: 2;
    min-width: 300px;
    border: 1px solid #EBEEF5;
    border-radius: 8px;
    padding: 20px;
    min-height: 500px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    
    &:hover {
      box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.1);
    }
    
    .result-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 1px solid #EBEEF5;
      
      .result-title {
        display: flex;
        align-items: center;
        
        i {
          font-size: 20px;
          color: #409EFF;
          margin-right: 10px;
        }
        
        span {
          font-size: 18px;
          font-weight: bold;
          color: #303133;
        }
      }
    }
    
    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 300px;
      
      .loading-animation {
        position: relative;
        margin-bottom: 30px;
        
        i {
          font-size: 40px;
          color: #409EFF;
        }
        
        .pulse-circle {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          width: 70px;
          height: 70px;
          border-radius: 50%;
          background-color: rgba(64, 158, 255, 0.2);
          animation: pulse 1.5s infinite;
        }
      }
      
      .loading-text {
        display: flex;
        align-items: center;
        color: #606266;
        font-size: 16px;
        
        .typing-dots {
          display: flex;
          margin-left: 5px;
          
          span {
            width: 5px;
            height: 5px;
            margin: 0 2px;
            background-color: #606266;
            border-radius: 50%;
            display: inline-block;
            animation: typing 1.5s infinite ease-in-out;
            
            &:nth-child(1) {
              animation-delay: 0s;
            }
            
            &:nth-child(2) {
              animation-delay: 0.2s;
            }
            
            &:nth-child(3) {
              animation-delay: 0.4s;
            }
          }
        }
      }
    }
    
    .patient-info {
      margin-bottom: 30px;
      background-color: #f8f9fa;
      border-radius: 4px;
      padding: 0;
      margin-bottom: 20px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
      overflow: hidden;
      
      .patient-info-header {
        padding: 15px;
        cursor: pointer;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: 500;
        background-color: #f0f2f5;
        transition: background-color 0.3s;
        
        &:hover {
          background-color: #e6e9ed;
        }
        
        .el-icon-arrow-down {
          transition: transform 0.3s;
          
          &.rotate-180 {
            transform: rotate(180deg);
          }
        }
      }

      .patient-brief {
        padding: 16px 8px;
        .brief-row {
          display: inline-flex;
          line-height: 28px;
          padding: 4px 8px;
          border: 1px solid #9d9d9d;
          border-radius: 4px;
          margin-bottom: 8px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          margin-right: 4px;
          background: #F5F5F9;
        }
      }
      
      
    }
    
    
    
    .department-data {
      margin-top: 30px;
      
      .department-header {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
        padding: 10px;
        border-radius: 4px;
        background-color: #f5f7fa;
        cursor: pointer;
        transition: background-color 0.3s;
        
        &:hover {
          background-color: #ecf5ff;
        }
        
        i {
          font-size: 18px;
          color: #409EFF;
          margin-right: 10px;
        }
        
        span {
          font-size: 16px;
          font-weight: bold;
          color: #303133;
        }
      }

      .block {
        background: #F5F5F9;
        padding: 16px 8px;
        .block-label {
          float: left;
          color: #606266;
          font-weight: 600;
        }
        .block-content {
          color: #303133;
        }
      }
      
      .conclusion {
        margin-top: 20px;
        padding: 15px;
        background-color: #f0f9eb;
        border-radius: 4px;
        
        .conclusion-title {
          font-weight: bold;
          margin-bottom: 10px;
          color: #67C23A;
        }
        
        .conclusion-content {
          color: #606266;
          line-height: 1.6;
        }
      }
    }
    
    .empty-department, .empty-result {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 300px;
      color: #909399;
      
      i {
        font-size: 64px;
        margin-bottom: 20px;
        color: #DCDFE6;
      }
      
      p {
        font-size: 16px;
        margin-bottom: 20px;
      }
      
      .empty-result-tips {
        display: flex;
        align-items: center;
        background-color: #f0f9eb;
        padding: 10px 15px;
        border-radius: 4px;
        max-width: 80%;
        
        i {
          font-size: 16px;
          color: #67C23A;
          margin-right: 10px;
          margin-bottom: 0;
        }
        
        span {
          font-size: 14px;
          color: #606266;
        }
      }
    }
  }
}

/* 动画效果 */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes pulse {
  0% {
    transform: translate(-50%, -50%) scale(0.8);
    opacity: 0.8;
  }
  50% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0.5;
  }
  100% {
    transform: translate(-50%, -50%) scale(0.8);
    opacity: 0.8;
  }
}

@keyframes typing {
  0% {
    transform: translateY(0);
    opacity: 0.3;
  }
  50% {
    transform: translateY(-5px);
    opacity: 1;
  }
  100% {
    transform: translateY(0);
    opacity: 0.3;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .report-interpret-container {
    flex-direction: column;
  }
  
  .right-panel, .left-panel {
    min-width: 100% !important;
  }
}
</style>
