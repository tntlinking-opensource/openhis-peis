# AiChatBox 组件

AiChatBox 是一个可复用的 AI 对话弹窗组件，可以在任何页面中引用，用于向 AI 发送咨询内容并展示 AI 的回复。该组件支持流式响应，实时展示 AI 思考过程和最终回复，并可以将结果回传给父组件。

## 功能特点

- 弹窗式设计，可在任何页面中调用
- 支持初始文本传入，方便预设咨询内容
- 实时展示 AI 思考过程，带有动画效果
- 支持流式响应，逐步展示 AI 回复
- 可将 AI 回复结果回传给父组件使用
- 美观的 UI 设计和动画效果

## 安装与引入

1. 确保组件已放置在 `src/components/AiChatBox` 目录下
2. 在需要使用的页面中引入组件：

```javascript
import AiChatBox from '@/components/AiChatBox';

export default {
  components: {
    AiChatBox
  }
  // ...
}
```

## 属性 (Props)

| 属性名 | 类型 | 默认值 | 说明 |
|-------|------|-------|------|
| visible | Boolean | false | 控制对话框是否显示 |
| title | String | 'AI 智能助手' | 对话框标题 |
| initialText | String | '' | 初始输入文本 |
| closeOnClickModal | Boolean | true | 是否允许点击背景关闭对话框 |

## 事件 (Events)

| 事件名 | 参数 | 说明 |
|-------|------|------|
| update:visible | Boolean | 更新 visible 属性，用于支持 .sync 修饰符 |
| close | - | 对话框关闭时触发 |
| result | String | 用户点击"使用结果"按钮时触发，参数为 AI 回复内容 |

## 使用示例

### 基本用法

```vue
<template>
  <div>
    <el-button @click="openAiChat">打开 AI 助手</el-button>
    
    <AiChatBox
      :visible.sync="aiChatVisible"
      @result="handleAiResult"
    />
  </div>
</template>

<script>
import AiChatBox from '@/components/AiChatBox';

export default {
  components: {
    AiChatBox
  },
  data() {
    return {
      aiChatVisible: false
    }
  },
  methods: {
    openAiChat() {
      this.aiChatVisible = true;
    },
    handleAiResult(result) {
      console.log('AI 回复结果:', result);
      // 处理 AI 回复结果
    }
  }
}
</script>
```

### 传入初始文本

```vue
<template>
  <div>
    <el-button @click="askAboutDiagnosis">询问诊断建议</el-button>
    
    <AiChatBox
      :visible.sync="aiChatVisible"
      :initial-text="initialQuestion"
      title="AI 诊断助手"
      @result="handleDiagnosisResult"
    />
  </div>
</template>

<script>
import AiChatBox from '@/components/AiChatBox';

export default {
  components: {
    AiChatBox
  },
  data() {
    return {
      aiChatVisible: false,
      initialQuestion: '',
      patientInfo: {
        name: '张三',
        age: 45,
        symptoms: '头痛、发热'
      }
    }
  },
  methods: {
    askAboutDiagnosis() {
      this.initialQuestion = `请根据以下患者信息给出可能的诊断建议：\n姓名：${this.patientInfo.name}\n年龄：${this.patientInfo.age}\n症状：${this.patientInfo.symptoms}`;
      this.aiChatVisible = true;
    },
    handleDiagnosisResult(result) {
      // 处理诊断建议结果
      this.diagnosisResult = result;
    }
  }
}
</script>
```

## 注意事项

1. 组件内部使用了 `aiApi` API 进行请求，确保 `src/api/ai/index.js` 文件中已正确配置该 API
2. 组件默认处理带有 `<think>` 标签的流式响应格式，如果后端返回格式不同，可能需要调整处理逻辑
3. 使用 `.sync` 修饰符可以实现双向绑定 `visible` 属性
4. 组件内部会在关闭时重置状态，确保每次打开都是全新的对话

## 自定义样式

组件使用了 Element UI 的 Dialog 组件作为基础，并添加了自定义样式。如需进一步自定义样式，可以通过以下方式：

1. 在父组件中使用深度选择器修改样式：

```scss
.your-parent-component {
  /deep/ .ai-chat-dialog {
    .el-dialog {
      // 自定义样式
    }
  }
}
```

2. 直接修改 AiChatBox 组件中的样式定义
