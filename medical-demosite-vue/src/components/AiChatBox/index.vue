<template>
  <div class="ai-chat-box">
    <el-dialog
      :modal="false"
      :visible.sync="dialogVisible"
      :title="title"
      width="60%"
      :before-close="handleClose"
      class="ai-chat-dialog"
      v-draggable
      :close-on-click-modal="closeOnClickModal"
    >
      <div class="chat-container">
        <!-- 显示AI回复内容 -->
        <div v-if="loading" class="loading-container">
          <div class="loading-animation">
            <i class="el-icon-loading"></i>
            <div class="pulse-circle"></div>
          </div>
          <div class="loading-text">
            <p>AI 正在分析和{{ btntext }}</p>
            <div class="typing-dots">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </div>
        </div>
        
        <div v-else-if="completeResponse" class="result-content">
          <transition name="fade">
            <div class="think">
              <div class="think-header">
                <i class="el-icon-cpu"></i>
                <p v-if="isRecommending" class="thinking-text">AI <span class="bounce-text">正在{{ btntext }}中<span class="dots"><span>.</span><span>.</span><span>.</span></span></span></p>
                <p v-else>AI {{ btntext }}过程</p>
                <el-button 
                  type="text" 
                  class="toggle-think" 
                  @click="toggleThinkContent">
                  <i :class="thinkCollapsed ? 'el-icon-arrow-down' : 'el-icon-arrow-up'"></i>
                </el-button>
              </div>
              <div class="think-content" :class="{'collapsed': thinkCollapsed}">
                {{ recommendResult }}
              </div>
            </div>
          </transition>
          
          <transition name="slide-fade">
            <div v-if="answer" class="answer"> 
              <div class="answer-header">
                <i class="el-icon-document-checked"></i>
                <p class="answer-title">AI {{btntext}}结果</p>
              </div>
              <div class="answer-content" v-html="renderedAnswer">
              </div> 
              <!-- <div class="answer-content" v-html="renderedAnswer"></div>  -->
            </div>
          </transition>
        </div>
        
        <div v-else class="empty-result">
          <i class="el-icon-chat-line-square"></i>
          <p>请点击"{{btntext}}"按钮开始{{btntext}}</p>
        </div>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <div class="input-container">
          <el-input
            v-model="inputText"
            type="textarea"
            :rows="3"
            placeholder="请输入您要诊断的内容"
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
          >{{btntext}}</el-button>
          <el-button 
            v-if="answer" 
            type="success" 
            @click="useResult"
          >使用结果</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { aiApi } from '@/api/ai/index'
import draggable from './draggable'
import { marked } from 'marked'

export default {
  name: 'AiChatBox',
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
      default: 'AI 智能助手'
    },
    // 按钮文本
    btntext: {
      type: String,
      default: '诊断'
    },
    functionCode: {
      type: Number,
      default: 0
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
      isRecommending: true,
      recommendResult: '',
      answer: '',
      completeResponse: '',
      thinkCollapsed: true
    }
  },
  watch: {
    visible(val) {
      this.dialogVisible = val;
    },
    initialText(val) {
      this.inputText = val;
    }
  },
  computed: {
    renderedAnswer() {
      // 使用 marked 将 Markdown 转为 HTML
      const source = (this.answer || '').trim()
      return source
      // return marked(source, {
      //   breaks: true
      // })
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
      this.isRecommending = true;
      this.recommendResult = '';
      this.answer = '';
      this.completeResponse = '';
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
        functionCode: this.functionCode,
        model : "/root/.cache/modelscope/hub/models/deepseek-ai/DeepSeek-R1-Distill-Qwen-32B",
        messages: [],
        prompt: prompt,
        stream: true,
        temperature: 0.7,
        top_p: 0.95,
        frequency_penalty: 0,
        presence_penalty: 0,
        max_tokens: 800,
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
            this.loading = false
            console.log('请求结束：', this.completeResponse)
            return
          }
          if(this.completeResponse){
            this.loading = false
            this.thinkCollapsed = false
          }
          let chunk = decoder.decode(value)
          // console.log('请求完成chunk', chunk)
          chunk = chunk.replace(/data:\s?/g, '')
          let chunkData = chunk.trim()
          // 收集完整响应
          this.completeResponse += chunkData
          // console.log('请求完成completeResponse', this.completeResponse)

          if (this.isRecommending) {
            if (this.completeResponse.includes('</think>')) {
              this.recommendResult = this.completeResponse.split('</think>')[0]
              this.answer = this.completeResponse.split('</think>')[1]
              this.isRecommending = false
            } else {
              this.recommendResult = this.completeResponse
              this.isRecommending = true
            }
          } else {
            this.answer += chunkData
          }
          
          // 强制滚动到底部，确保用户看到最新内容
          this.$nextTick(() => {
            const resultElement = document.querySelector('.result-content')
            if (resultElement) {
              resultElement.scrollTop = resultElement.scrollHeight
            }
          })
          
          // 继续读取流
          return reader.read().then(processStream)
        };
        
        reader.read().then(processStream)
      } catch (error) {
        console.error('请求失败:', error)
        this.loading = false
        this.$message.error('获取AI回复失败：' + error.message)
      }
    },
    
    // 使用结果并发送给父组件
    useResult() {
      this.$emit('result', this.answer)
      this.$message.success('已应用AI回复结果')
      this.handleClose()
    }
  }
}
</script>

<style lang="scss" scoped>
.ai-chat-box {
  .ai-chat-dialog {
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
      min-height: 300px;
      max-height: 60vh;
      overflow-y: auto;
    }
  }
  
  .chat-container {
    min-height: 250px;
    
    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 200px;
      
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
      height: 200px;
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
          line-height: 1.6;
          color: #303133;
          background-color: #f9f9f9;
          white-space: normal;
        }
        /* 关键：对 v-html 里的 p 使用深度选择器 */
        // .answer-content ::v-deep p {
        //   text-indent: 2em;
        // }
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
