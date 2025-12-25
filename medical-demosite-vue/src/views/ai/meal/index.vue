<template>
  <div class="app-container">
    <div class="ai-meal-recommend">
      <div class="ai-header">
        <div class="ai-header-title">
          <i class="el-icon-s-platform"></i>
          <h1>AI 智能体检套餐推荐</h1>
        </div>
        <div class="ai-header-subtitle">基于人工智能分析，为您量身定制最适合的体检方案</div>
      </div>

      <div class="meal-recommend-container">
        <!-- 左侧用户信息和输入区域 -->
        <div class="left-panel">
          <div class="panel-header">
            <i class="el-icon-user"></i>
            <span>用户信息采集</span>
          </div>
          
          <el-form :model="formData" ref="formRef" label-width="100px" size="small" class="animated-form">
            <el-form-item label="用户信息" prop="userInfo">
              <div class="input-icon-wrapper">
                <i class="el-icon-user-solid input-icon"></i>
                <el-input
                  type="textarea"
                  v-model="formData.userInfo"
                  :rows="6"
                  placeholder="请输入用户基本信息，如：年龄、性别、职业等"
                ></el-input>
              </div>
            </el-form-item>
            
            <el-form-item label="健康状况" prop="healthStatus">
              <div class="input-icon-wrapper">
                <i class="el-icon-first-aid-kit input-icon"></i>
                <el-input
                  type="textarea"
                  v-model="formData.healthStatus"
                  :rows="8"
                  placeholder="请输入用户健康状况，如：有无慢性病、家族病史等"
                ></el-input>
              </div>
            </el-form-item>
            
            <el-form-item label="体检需求" prop="requirements">
              <div class="input-icon-wrapper">
                <i class="el-icon-aim input-icon"></i>
                <el-input
                  type="textarea"
                  v-model="formData.requirements"
                  :rows="6"
                  placeholder="请输入用户体检需求，如：关注哪些方面的健康问题等"
                ></el-input>
              </div>
            </el-form-item>
            
            <el-form-item class="form-actions">
              <el-button 
                type="primary" 
                @click="submitForm" 
                :loading="loading" 
                class="submit-button"
                icon="el-icon-s-promotion">
                <span>AI 智能推荐</span>
              </el-button>
              <el-button 
                @click="resetForm" 
                icon="el-icon-refresh-right"
                class="reset-button">
                重置
              </el-button>
            </el-form-item>
          </el-form>
        </div>
        
        <!-- 右侧AI推荐结果展示区域 -->
        <div class="right-panel">
          <div class="result-header">
            <div class="result-title">
              <i class="el-icon-data-analysis"></i>
              <span>AI 推荐结果</span>
            </div>
            <div class="opt-btn">
                <el-button 
                v-if="recommendResult" 
                type="success" 
                size="small" 
                icon="el-icon-plus" 
                class="create-button"
                @click="createPatient">
                生成个检
                </el-button>
                <el-button 
                v-if="recommendResult" 
                type="success" 
                size="small" 
                icon="el-icon-plus" 
                class="create-button"
                @click="createMeal">
                创建套餐
                </el-button>
            </div>
          </div>
          
          <div v-if="loading" class="loading-container">
            <div class="loading-animation">
              <i class="el-icon-loading"></i>
              <div class="pulse-circle"></div>
            </div>
            <div class="loading-text">
              <p>AI 正在分析您的需求</p>
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
                  <p v-if="isRecommending" class="thinking-text">AI <span class="bounce-text">正在思考中<span class="dots"><span>.</span><span>.</span><span>.</span></span></span></p>
                  <p v-else>AI 思考推理过程</p>
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
                  <p class="answer-title">推荐套餐</p>
                </div>
                <div v-if="jsonData" class="answer-content" v-html="formattedJsonData"></div>
                <div v-else class="answer-content">{{ answer }}</div> 
              </div>
            </transition>
          </div>
          
          <div v-else class="empty-result">
            <i class="el-icon-data-board"></i>
            <p>请在左侧填写信息后点击"AI 智能推荐"按钮</p>
            <div class="empty-result-tips">
              <i class="el-icon-info"></i>
              <span>AI 将根据您提供的信息，智能推荐最适合的体检套餐</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { aiApi } from '@/api/ai/index'

export default {
  name: 'AiMealRecommend',
  data() {
    return {
      formData: {
        userInfo: '年龄：45；性别：男；身高：175cm；体重：82kg',
        healthStatus:
          '1. 主要健康关切：近期常感疲劳，担心心血管健康；2. 家族病史：父亲有高血压和糖尿病史；3. 现有症状：偶尔头晕，睡眠质量不佳；4. 生活习惯：办公室工作，每周饮酒2-3次，吸烟10支/天，运动较少；5. 职业：IT行业；6. 过往疾病史：无重大疾病；7. 最近一次体检时间：2年前；8. 过敏史：无',
        requirements: '预算范围：1000-2000元；特别关注项目：心脑血管相关检查'
      },
      loading: false,
      percentage: 0,
      progressStatus: '',
      isLoading: true,
      isRecommending: true,
      recommendResult: '',
      answer: '',
      completeResponse: '',
      jsonData: null,
      thinkCollapsed: false // 控制思考内容的折叠状态
    }
  },
  computed: {
    formattedJsonData() {
      if (!this.jsonData) return ''

      try {
        // 解析JSON字符串为对象（如果还不是对象）
        const data =
          typeof this.jsonData === 'string' ? JSON.parse(this.jsonData) : this.jsonData

        // 提取套餐信息 data.recommendations如果是数组就取第一个，如果是对象就直接赋值
        let recommendations = null
        if (Array.isArray(data.recommendations) && data.recommendations.length > 0) {
          recommendations = data.recommendations[0]
        } else if (data.recommendations && typeof data.recommendations === 'object') {
          recommendations = data.recommendations
        }

        if (!recommendations) return `<pre>${JSON.stringify(data, null, 2)}</pre>`

        const items = Array.isArray(recommendations.items) ? recommendations.items : []
        const itemsRowsHtml = items
          .map(
            item => `
                  <tr style="border-bottom: 1px outset #ccc;">
                    <td class="item-name" style="padding: 4px 12px; text-align: left; font-weight: 600; color: #303133; min-width: 150px;">${item.name}</td>
                    <td class="item-reason" style="padding: 4px 12px; text-align: left; color: #606266; line-height: 1.6;">${item.reason}</td>
                  </tr>
          `
          )
          .join('')

        let html = `
          <div class="package-info" style="position: relative; display: flex; flex-direction: column; gap: 16px;">
            <h2 class="package-name" style="font-size: 24px; color: #409EFF; margin-top: 0; margin-bottom: 2px; padding-bottom: 10px; border-bottom: 2px solid rgba(64, 158, 255, 0.3); letter-spacing: 1px;">
              ${recommendations.package_name}</h2>
            <div class="package-meta" style="display: flex; flex-wrap: wrap; gap: 12px 20px; margin-bottom: 8px; padding: 14px 16px; background: linear-gradient(135deg, #f5f7fa 0%, #f9fbff 100%); border-radius: 8px;
              border: 1px dashed rgba(64, 158, 255, 0.35);">
              <div class="meta-item" style="flex: 1; min-width: 200px; display: flex; align-items: baseline;">
                <span class="meta-label" style="font-weight: 600; color: #303133; margin-right: 6px; white-space: nowrap;">适合人群：</span>
                <span class="meta-value" style="color: #606266; word-break: break-all;">${recommendations.suitable_for}</span>
              </div>
              <div class="meta-item">
                <span class="meta-label" style="font-weight: 600; color: #303133; margin-right: 6px; white-space: nowrap;">价格范围：</span>
                <span class="meta-value" style="color: #606266; word-break: break-all;">${recommendations.price_range}</span>
              </div>
            </div>
            
            <h3 class="section-title" style="font-size: 18px; margin-top: 4px; margin-bottom: 2px; padding-bottom: 8px; border-bottom: 1px solid #EBEEF5; color: #303133; display: flex; align-items: center;">
              检查项目
            </h3>
            <div class="table-container" style="margin-top: 4px; overflow-x: auto; border-radius: 8px; box-shadow: 0 3px 14px rgba(0, 0, 0, 0.04); background-color: #ffffff;">
              <table class="items-table" style="width: 100%; border-collapse: collapse; border: 1px solid #EBEEF5;">
                <thead>
                  <tr>
                    <th style="padding: 8px 12px; text-align: left; border-bottom: 1px solid #EBEEF5; background: linear-gradient(180deg, #f5f7fa 0%, #eef3fb 100%); color: #303133; font-weight: 600;">项目名称</th>
                    <th style="padding: 8px 12px; text-align: left; border-bottom: 1px solid #EBEEF5; background: linear-gradient(180deg, #f5f7fa 0%, #eef3fb 100%); color: #303133; font-weight: 600;">检查意义</th>
                  </tr>
                </thead>
                <tbody>
        `

        html += itemsRowsHtml

        html += `
                </tbody>
              </table>
            </div>
            
            <h3 class="section-title" style="font-size: 18px; margin-top: 4px; margin-bottom: 2px; padding-bottom: 8px; border-bottom: 1px solid #EBEEF5; color: #303133; display: flex; align-items: center;">
              推荐理由
            </h3>
            <div class="rationale" style="margin-top: 2px; padding: 14px 16px; background-color: #f0f9eb; border-left: 4px solid #67C23A; border-radius: 4px; color: #606266; line-height: 1.6;">${data.rationale}</div>
          </div>
        `

        return html
      } catch (e) {
        console.error('解析JSON失败', e)
        return `<pre>${this.jsonData}</pre>`
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.getRecommendation()
        }
      })
    },
    resetForm() {
      this.$refs.formRef.resetFields()
      this.recommendResult = ''
    },
    async getRecommendation() {
      this.loading = true
      this.percentage = 0
      this.progressStatus = ''
      this.isLoading = true
      this.recommendResult = ''
      this.completeResponse = ''
      this.answer = ''
      this.jsonData = null
      
      // 构建请求参数
      const prompt = `用户信息：${this.formData.userInfo}\n健康状况：${this.formData.healthStatus}\n体检需求：${this.formData.requirements}\n请根据以上信息，推荐1个适合的体检套餐。`
      
      // 构建请求对象
      const requestData = {
        functionCode: 1,
        messages: [
          {
            role: 'user',
            content: prompt
          }
        ],
        model: '/root/.cache/modelscope/hub/models/deepseek-ai/DeepSeek-R1-Distill-Qwen-32B',
        stream: true,
        prompt: prompt,
        temperature: 0.8,
        stream_options: {
          include_usage: true
        }
      }
      console.log('---------请求开始---------')
      
      try {
        const response = await aiApi(requestData)
        
        // 检查响应是否成功
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }
        this.loading = false
        // 获取响应流
        const reader = response.body.getReader()
        const decoder = new TextDecoder('utf-8')
        console.log('---------获取到响应流---------')
        
        const processStream = async ({ done, value }) => {
          if (done) {
            console.log('---------请求完成：', this.recommendResult)
            this.loading = false
            try {
              // 提取JSON字符串
              const jsonStart = this.answer.indexOf('{')
              const jsonEnd = this.answer.lastIndexOf('}') + 1
              if (jsonStart >= 0 && jsonEnd > jsonStart) {
                this.jsonData = this.answer.substring(jsonStart, jsonEnd)
                // console.log('---------提取的JSON字符串：', this.jsonData);
                // 尝试解析JSON
                const parsedJson = JSON.parse(this.jsonData)
                // console.log('---------解析后的JSON对象：', parsedJson);
              }
            } catch (e) {
              console.error('解析JSON失败', e)
            }
            return
          }
          
          const chunk = decoder.decode(value)
          
          // 处理每一行，移除所有"data:"前缀
          const lines = chunk.split('\n')
          const processedLines = lines.map(line => {
            // 移除行开头的"data:"或"data: "
            return line.replace(/^data:\s?/, '')
          })
          const chunkData = processedLines.join('\n')
          // 收集完整响应
          this.completeResponse += chunkData.trim()

          if (this.isRecommending) {
            if (this.completeResponse.includes('</think>')) {
              this.recommendResult = this.completeResponse.split('</think>')[0]
              this.answer = this.completeResponse.split('</think>')[1]
              this.isRecommending = false
            } else {
              this.recommendResult = this.completeResponse
              this.isRecommending = true
              // 强制滚动到底部，确保用户看到最新内容
              this.$nextTick(() => {
                const resultElement = document.querySelector('.think-content')
                if (resultElement) {
                  resultElement.scrollTop = resultElement.scrollHeight
                }
              })
            }
          } else {
            this.answer += chunkData.trim()
          }
          
          // 强制滚动到底部，确保用户看到最新内容
          this.$nextTick(() => {
            const resultElement = document.querySelector('.right-panel')
            if (resultElement) {
              resultElement.scrollTop = resultElement.scrollHeight
            }
          })

          // 继续读取流
          return reader.read().then(processStream)
        }
        
        reader.read().then(processStream)
      } catch (error) {
        console.error('请求失败:', error)
        this.loading = false
        this.$message.error('获取推荐失败：' + error.message)
      } finally {
        if (this.progressInterval) {
          clearInterval(this.progressInterval)
        }
      }
    },
    toggleThinkContent() {
      this.thinkCollapsed = !this.thinkCollapsed
    },

    createPatient() {
      this.$modal.loading('正在提交，请稍候...')
      // setTimeout(() => {
      //   this.$message.error('个检备单失败，体检者备单信息写入数据库失败，请检查与体检系统数据库连接！')
      //   this.$modal.closeLoading()
      // }, 3000)
      // this.$message.success('生成个检备单成功！')
    },
    
    createMeal() {
      this.$modal.loading('正在提交，请稍候...')
      // setTimeout(() => {
      //   this.$message.error('创建套餐失败，套餐信息写入数据库失败，请检查与体检系统数据库连接！')
      //   this.$modal.closeLoading()
      // }, 3000)
      // this.$message.success('创建套餐成功！')
      // 这里可以实现创建套餐的逻辑
    }
  },
  beforeDestroy() {
    if (this.progressInterval) {
      clearInterval(this.progressInterval)
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

.ai-meal-recommend {
  max-width: 1200px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
  animation: fadeIn 0.5s ease-in-out;
  height: 100%;
  
  .ai-header {
    background: linear-gradient(135deg, #409EFF 0%, #67C23A 100%);
    color: white;
    padding: 20px;
    text-align: center;
    
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

.meal-recommend-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  height: calc(100% - 100px);
  
  .left-panel {
    flex: 2;
    min-width: 300px;
    
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
  }
  
  .right-panel {
    flex: 3;
    min-width: 300px;
    border: 1px solid #EBEEF5;
    border-radius: 8px;
    padding: 20px;
    min-height: 500px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    
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
      
      .create-button {
        transition: all 0.3s;
        
        &:hover {
          transform: scale(1.05);
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
    
    .result-content {
      // height: 100%;
      // overflow-y: auto;

      .think {
        margin-bottom: 30px;
        border: 1px solid #EBEEF5;
        border-radius: 8px;
        overflow: hidden;
        transition: all 0.3s ease;
        
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
          border-radius: 10px;
          padding: 22px 24px;
          box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);
          background: radial-gradient(circle at top left, #f8fbff 0, #ffffff 45%, #f9f9fb 100%);

          // v-html 渲染出来的内容不会带 scoped 的 data-v 属性，这里用 :deep 让样式生效
          :deep(.package-info) {
            position: relative;
            display: flex;
            flex-direction: column;
            gap: 16px;

            .package-name {
              font-size: 24px;
              color: #409EFF;
              margin-top: 0;
              margin-bottom: 16px;
              padding-bottom: 10px;
              border-bottom: 2px solid rgba(64, 158, 255, 0.3);
              letter-spacing: 1px;
            }
            
            .package-meta {
              display: flex;
              flex-wrap: wrap;
              gap: 12px 20px;
              margin-bottom: 8px;
              padding: 14px 16px;
              background: linear-gradient(135deg, #f5f7fa 0%, #f9fbff 100%);
              border-radius: 8px;
              border: 1px dashed rgba(64, 158, 255, 0.35);
              
              .meta-item {
                flex: 1;
                min-width: 200px;
                display: flex;
                align-items: baseline;
                
                .meta-label {
                  font-weight: 600;
                  color: #303133;
                  margin-right: 6px;
                  white-space: nowrap;
                }
                
                .meta-value {
                  color: #606266;
                  word-break: break-all;
                }
              }
            }
            
            .section-title {
              font-size: 18px;
              margin-top: 22px;
              margin-bottom: 12px;
              padding-bottom: 8px;
              border-bottom: 1px solid #EBEEF5;
              color: #303133;
              display: flex;
              align-items: center;
              
              &::before {
                content: '';
                display: inline-block;
                width: 4px;
                height: 16px;
                background: linear-gradient(180deg, #409EFF, #67C23A);
                margin-right: 10px;
                border-radius: 2px;
              }
            }
            
            .table-container {
              margin-top: 12px;
              overflow-x: auto;
              border-radius: 8px;
              box-shadow: 0 3px 14px rgba(0, 0, 0, 0.04);
              background-color: #ffffff;
              
              .items-table {
                width: 100%;
                border-collapse: collapse;
                border: 1px solid #EBEEF5;
                
                th,
                td {
                  padding: 11px 14px;
                  text-align: left;
                  border-bottom: 1px solid #EBEEF5;
                }
                
                th {
                  background: linear-gradient(180deg, #f5f7fa 0%, #eef3fb 100%);
                  color: #303133;
                  font-weight: 600;
                  position: sticky;
                  top: 0;
                  z-index: 10;
                }
                
                tr:nth-child(even) {
                  background-color: #fafafa;
                }
                
                tr {
                  transition: background-color 0.25s ease;
                  
                  &:hover {
                    background-color: #f0f9eb;
                  }
                }
                
                .item-name {
                  font-weight: 600;
                  color: #303133;
                  min-width: 150px;
                }
                
                .item-reason {
                  color: #606266;
                  line-height: 1.6;
                }
              }
            }
            
            .rationale {
              margin-top: 18px;
              padding: 14px 16px;
              background-color: #f0f9eb;
              border-left: 4px solid #67C23A;
              border-radius: 4px;
              color: #606266;
              line-height: 1.6;
            }
          }
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

/* 响应式设计 */
@media (max-width: 768px) {
  .meal-recommend-container {
    flex-direction: column;
  }
  
  .right-panel, .left-panel {
    min-width: 100% !important;
  }
}

/* 过渡动画 */
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
