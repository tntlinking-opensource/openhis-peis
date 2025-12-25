<template>
  <div class="ai-inspection-box">
    <el-dialog
      :modal="false"
      :visible.sync="dialogVisible"
      :title="title"
      width="50%"
      :before-close="handleClose"
      class="ai-inspection-dialog"
      v-draggable
      :close-on-click-modal="closeOnClickModal"
    >
      <div class="inspection-container">
        <!-- 显示AI分析中的加载状态 -->
        <div v-if="loading" class="loading-container">
          <div class="loading-animation">
            <i class="el-icon-loading"></i>
            <div class="pulse-circle"></div>
          </div>
          <div class="loading-text">
            <p>AI 正在进行智能总检分析</p>
            <div class="typing-dots">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </div>
        </div>
        
        <!-- 显示AI分析结果 -->
        <div v-else-if="completeResponse" class="result-content">
          <transition name="fade">
            <div class="think">
              <div class="think-header">
                <i class="el-icon-cpu"></i>
                <p v-if="isAnalyzing" class="thinking-text">AI <span class="bounce-text">正在分析中<span class="dots"><span>.</span><span>.</span><span>.</span></span></span></p>
                <p v-else>AI 分析过程</p>
                <el-button 
                  type="text" 
                  class="toggle-think" 
                  @click="toggleThinkContent">
                  <i :class="thinkCollapsed ? 'el-icon-arrow-down' : 'el-icon-arrow-up'"></i>
                </el-button>
              </div>
              <div class="think-content" :class="{'collapsed': thinkCollapsed}">
                {{ analysisProcess }}
              </div>
            </div>
          </transition>
          
          <transition name="slide-fade">
            <div v-if="analysisResult" class="answer"> 
              <div class="answer-header">
                <i class="el-icon-document-checked"></i>
                <p class="answer-title">AI 总检分析结果</p>
              </div>
              <div class="answer-content">
                <div v-if="formattedResult && !parsedJsonData" v-html="formattedResult"></div>
                <!-- <el-input
                  v-if="!parsedJsonData"
                  type="textarea"
                  v-model="formattedResult"
                  :rows="15"
                  resize="none"
                  class="result-textarea"
                ></el-input> -->
                <div v-if="parsedJsonData" class="formatted-json-display">
                  <div class="json-section" v-if="parsedJsonData.issuesAndConclusions">
                    <h3>{{ parsedJsonData.issuesAndConclusions.title }}</h3>
                    <el-input
                      type="textarea"
                      v-model="parsedJsonData.issuesAndConclusions.textarea"
                      :rows="5"
                      resize="none"
                      class="json-textarea"
                    ></el-input>
                  </div>
                  
                  <div class="json-section" v-if="parsedJsonData.interventionAndPrecautions">
                    <h3>{{ parsedJsonData.interventionAndPrecautions.title }}</h3>
                    
                    <div class="json-subsection" v-if="parsedJsonData.interventionAndPrecautions.diet">
                      <h4>{{ parsedJsonData.interventionAndPrecautions.diet.title }}</h4>
                      <el-input
                        type="textarea"
                        v-model="parsedJsonData.interventionAndPrecautions.diet.textarea"
                        :rows="3"
                        resize="none"
                        class="json-textarea"
                      ></el-input>
                    </div>
                    
                    <div class="json-subsection" v-if="parsedJsonData.interventionAndPrecautions.habits">
                      <h4>{{ parsedJsonData.interventionAndPrecautions.habits.title }}</h4>
                      <el-input
                        type="textarea"
                        v-model="parsedJsonData.interventionAndPrecautions.habits.textarea"
                        :rows="3"
                        resize="none"
                        class="json-textarea"
                      ></el-input>
                    </div>
                    
                    <div class="json-subsection" v-if="parsedJsonData.interventionAndPrecautions.followupAndMeds">
                      <h4>{{ parsedJsonData.interventionAndPrecautions.followupAndMeds.title }}</h4>
                      <el-input
                        type="textarea"
                        v-model="parsedJsonData.interventionAndPrecautions.followupAndMeds.textarea"
                        :rows="3"
                        resize="none"
                        class="json-textarea"
                      ></el-input>
                    </div>
                  </div>
                  
                  <div class="json-section" v-if="parsedJsonData.reviewAndReminders">
                    <h3>{{ parsedJsonData.reviewAndReminders.title }}</h3>
                    
                    <div class="json-subsection" v-if="parsedJsonData.reviewAndReminders.regularReviews">
                      <h4>{{ parsedJsonData.reviewAndReminders.regularReviews.title }}</h4>
                      <el-input
                        type="textarea"
                        v-model="parsedJsonData.reviewAndReminders.regularReviews.textarea"
                        :rows="3"
                        resize="none"
                        class="json-textarea"
                      ></el-input>
                    </div>
                    
                    <div class="json-subsection" v-if="parsedJsonData.reviewAndReminders.urgentSituations">
                      <h4>{{ parsedJsonData.reviewAndReminders.urgentSituations.title }}</h4>
                      <el-input
                        type="textarea"
                        v-model="parsedJsonData.reviewAndReminders.urgentSituations.textarea"
                        :rows="3"
                        resize="none"
                        class="json-textarea"
                      ></el-input>
                    </div>
                  </div>
                  
                  <div class="json-section" v-if="parsedJsonData.specialistFollowups">
                    <h3>{{ parsedJsonData.specialistFollowups.title }}</h3>
                    <el-input
                      type="textarea"
                      v-model="parsedJsonData.specialistFollowups.textarea"
                      :rows="3"
                      resize="none"
                      class="json-textarea"
                    ></el-input>
                  </div>
                </div>
              </div> 
            </div>
          </transition>
        </div>
        
        <!-- 初始状态 -->
        <div v-else class="empty-result">
          <i class="el-icon-data-analysis"></i>
          <p>请点击"开始分析"按钮进行AI智能总检</p>
        </div>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <div class="input-container">
          <el-input
            v-model="inputText"
            type="textarea"
            :rows="10"
            placeholder="请输入体检数据或修改提示内容"
            :disabled="loading"
          ></el-input>
        </div>
        <div class="button-container">
          <el-button @click="handleClose">取消</el-button>
          <el-button 
            type="primary" 
            @click="sendRequest" 
            :loading="loading"
            :disabled="!inputText.trim() || loading"
          >开始分析</el-button>
          <el-button 
            v-if="analysisResult" 
            type="success" 
            @click="useResult"
          >应用结果</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { aiApi } from '@/api/ai/index'
import draggable from '@/components/AiChatBox/draggable'

export default {
  name: 'AiInspection',
  directives: {
    draggable
  },
  props: {
    // 是否显示对话框
    visible: {
      type: Boolean,
      default: false
    },
    // 对话框标题
    title: {
      type: String,
      default: 'AI 智能总检'
    },
    // 初始输入文本
    initialText: {
      type: String,
      default: ''
    },
    // 是否允许点击模态对话框关闭
    closeOnClickModal: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      dialogVisible: this.visible,
      inputText: this.initialText,
      loading: false,
      isAnalyzing: true,
      analysisProcess: '',
      analysisResult: '',
      completeResponse: '',
      thinkCollapsed: true,
      parsedJsonData: null,
      formattedResult: ''
    }
  },
  watch: {
    visible(val) {
      this.dialogVisible = val;
    },
    initialText(val) {
      this.inputText = val;
    },
    analysisResult: {
      handler(val) {
        if (val) {
          try {
            // 尝试解析JSON
            const jsonStart = val.indexOf('{');
            const jsonEnd = val.lastIndexOf('}') + 1;
            
            if (jsonStart >= 0 && jsonEnd > jsonStart) {
              const jsonStr = val.substring(jsonStart, jsonEnd);
            //   let jsonDataStr = jsonStr;
              this.parsedJsonData = JSON.parse(jsonStr);
              this.formattedResult = JSON.stringify(this.parsedJsonData, null, 2);
              
              // 在控制台输出解析后的结果
              console.log('请求完成formattedResult', this.formattedResult);
              console.log('解析后的JSON数据', this.parsedJsonData);
            } else {
              this.formattedResult = val;
              this.parsedJsonData = null;
            }
          } catch (e) {
            console.error('解析JSON失败', e);
            this.formattedResult = val;
            this.parsedJsonData = null;
          }
        }
      },
      immediate: true
    }
  },
  methods: {
    // 关闭对话框
    handleClose() {
      this.resetState();
      this.$emit('update:visible', false);
      this.$emit('close');
    },
    
    // 重置状态
    resetState() {
      this.loading = false;
      this.isAnalyzing = true;
      this.analysisProcess = '';
      this.analysisResult = '';
      this.completeResponse = '';
      this.parsedJsonData = null;
      this.formattedResult = '';
    },
    
    // 切换思考内容的显示/隐藏
    toggleThinkContent() {
      this.thinkCollapsed = !this.thinkCollapsed;
    },
    
    // 发送请求到AI服务
    async sendRequest() {
      if (!this.inputText.trim()) return;
      
      this.resetState();
      this.loading = true;
      
      // 构建请求参数
      const prompt = this.inputText;
      
      // 构建请求对象
      const requestData = {
        functionCode: 3,
        model: "/root/.cache/modelscope/hub/models/deepseek-ai/DeepSeek-R1-Distill-Qwen-32B",
        messages: [
          {
            role: "user",
            content: prompt
          }
        ],
        stream: true,
        temperature: 0.15,
        top_p: 0.95,
        frequency_penalty: 0,
        presence_penalty: 0,
        max_tokens: 2000,
        stop: null,
        user: "user",
        functions: [],
        function_call: "auto",
        tools: [],
        tool_choice: "auto",
        logit_bias: {},
        top_logprobs: null,
        seed: null,
        response_format: {
          type: "text"
        },
        include_usage: true
      }
      
      try {
        // 发送请求
        const response = await aiApi(requestData);
        
        // 获取响应流
        const reader = response.body.getReader();
        const decoder = new TextDecoder('utf-8');
        
        const processStream = async ({ done, value }) => {
          if (done) {
            this.loading = false;
            return;
          }
          
          if(this.completeResponse){
            this.loading = false;
            this.thinkCollapsed = false;
          }
          
          const chunk = decoder.decode(value);
          
          // 处理每一行，移除所有"data:"前缀
          const lines = chunk.split('\n');
          const processedLines = lines.map(line => {
            // 移除行开头的"data:"或"data: "
            return line.replace(/^data:\s?/, '');
          });
          const chunkData = processedLines.join('\n').trim();
          
          // 收集完整响应
          this.completeResponse += chunkData;

          if (this.isAnalyzing) {
            if (this.completeResponse.includes('</think>')) {
              this.analysisProcess = this.completeResponse.split('</think>')[0];
              this.analysisResult = this.completeResponse.split('</think>')[1];
              this.isAnalyzing = false;
            } else {
              this.analysisProcess = this.completeResponse;
              this.isAnalyzing = true;
            }
          } else {
            this.analysisResult += chunkData;
          }
          
          // 强制滚动到底部，确保用户看到最新内容
          this.$nextTick(() => {
            const resultElement = document.querySelector('.result-content');
            if (resultElement) {
              resultElement.scrollTop = resultElement.scrollHeight;
            }
          });
          
          // 继续读取流
          return reader.read().then(processStream);
        };
        
        reader.read().then(processStream);
      } catch (error) {
        console.error('请求失败:', error);
        this.loading = false;
        this.$message.error('获取AI分析失败：' + error.message);
      }
    },
    
    // 使用结果并发送给父组件
    useResult() {
      if (this.parsedJsonData) {
        // 更新formattedResult以反映编辑后的内容
        this.formattedResult = JSON.stringify(this.parsedJsonData, null, 2);
        
        // 将编辑后的parsedJsonData发送给父组件
        this.$emit('result', this.parsedJsonData);
        console.log('发送给父组件的编辑后数据:', this.parsedJsonData);
      } else {
        // 如果没有JSON数据，则发送原始结果
        this.$emit('result', this.analysisResult);
      }
      
    //   this.$message.success('已应用AI总检结果');
      this.handleClose();
    }
  }
}
</script>

<style lang="scss" scoped>
.ai-inspection-box {
  .ai-inspection-dialog {
    &::v-deep .el-dialog__header {
      background: linear-gradient(135deg, #409EFF 0%, #67C23A 100%);
      padding: 15px 20px;
      
      .el-dialog__title {
        color: white;
        font-weight: bold;
      }
      
      .el-dialog__headerbtn .el-dialog__close {
        color: white;
      }
    }
    
    &::v-deep .el-dialog__body {
      padding: 20px;
      min-height: 400px;
      max-height: 90vh;
      overflow-y: auto;
    }
  }
  
  .inspection-container {
    min-height: 350px;
    
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
    
    .empty-result {
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
      }
    }
    
    .result-content {
      .think {
        margin-bottom: 20px;
        border: 1px solid #EBEEF5;
        border-radius: 8px;
        overflow: hidden;
        
        .think-header {
          display: flex;
          align-items: center;
          padding: 15px;
          background-color: #f5f7fa;
          border-bottom: 1px solid #EBEEF5;
          
          i {
            font-size: 18px;
            color: #409EFF;
            margin-right: 10px;
          }
          
          p {
            flex: 1;
            margin: 0;
            font-weight: bold;
            color: #303133;
          }
          
          .thinking-text {
            .bounce-text {
              display: inline-block;
              animation: bounce 1.5s infinite ease-in-out;
              
              .dots {
                display: inline-block;
                
                span {
                  display: inline-block;
                  
                  &:nth-child(1) {
                    animation: dotBounce 1.5s infinite ease-in-out;
                    animation-delay: 0s;
                  }
                  
                  &:nth-child(2) {
                    animation: dotBounce 1.5s infinite ease-in-out;
                    animation-delay: 0.2s;
                  }
                  
                  &:nth-child(3) {
                    animation: dotBounce 1.5s infinite ease-in-out;
                    animation-delay: 0.4s;
                  }
                }
              }
            }
          }
          
          .toggle-think {
            padding: 0;
            margin: 0;
            
            i {
              transition: transform 0.3s;
            }
          }
        }
        
        .think-content {
          padding: 15px;
          color: #606266;
          background-color: #fafafa;
          line-height: 1.6;
          max-height: 300px;
          overflow-y: auto;
          transition: max-height 0.3s ease, padding 0.3s ease;
          
          &.collapsed {
            max-height: 0;
            padding: 0 15px;
            overflow: hidden;
          }
        }
      }
      
      .answer {
        animation: slideUp 0.5s ease-out;
        
        .answer-header {
          display: flex;
          align-items: center;
          margin-bottom: 15px;
          
          i {
            font-size: 20px;
            color: #67C23A;
            margin-right: 10px;
          }
          
          .answer-title {
            margin: 0;
            font-size: 18px;
            font-weight: bold;
            color: #303133;
          }
        }
        
        .answer-content {
          border: 1px solid #EBEEF5;
          border-radius: 8px;
          padding: 15px;
          background-color: #f9f9f9;
          
          .result-textarea {
            width: 100%;
            font-family: monospace;
            margin-bottom: 20px;
            
            &::v-deep .el-textarea__inner {
              font-family: monospace;
              line-height: 1.6;
              padding: 10px;
              background-color: #f9f9f9;
            }
          }
          
          .formatted-json-display {
            border: 1px solid #EBEEF5;
            border-radius: 8px;
            padding: 15px;
            background-color: #fff;
            margin-top: 20px;
            
            .json-section {
              margin-bottom: 25px;
              
              h3 {
                font-size: 18px;
                color: #303133;
                margin-top: 0;
                margin-bottom: 15px;
                padding-bottom: 8px;
                border-bottom: 2px solid #409EFF;
              }
              
              .json-subsection {
                margin-bottom: 15px;
                padding: 12px;
                background-color: #f9f9f9;
                border-radius: 6px;
                
                h4 {
                  font-size: 16px;
                  color: #409EFF;
                  margin-top: 0;
                  margin-bottom: 10px;
                }
              }
              
              .json-textarea {
                width: 100%;
                margin-top: 5px;
                
                &::v-deep .el-textarea__inner {
                  font-family: inherit;
                  line-height: 1.6;
                  padding: 10px;
                  background-color: #f9f9f9;
                  border: 1px solid #dcdfe6;
                  border-radius: 4px;
                  transition: border-color 0.2s;
                  
                  &:hover, &:focus {
                    border-color: #409EFF;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  .dialog-footer {
    .input-container {
      margin-bottom: 15px;
    }
    
    .button-container {
      display: flex;
      justify-content: flex-end;
      gap: 10px;
    }
  }
}

/* 动画效果 */
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

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

@keyframes dotBounce {
  0%, 100% {
    opacity: 0.2;
    transform: translateY(0);
  }
  50% {
    opacity: 1;
    transform: translateY(-3px);
  }
}

@keyframes slideUp {
  from { 
    opacity: 0;
    transform: translateY(20px);
  }
  to { 
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateY(10px);
  opacity: 0;
}
</style>
