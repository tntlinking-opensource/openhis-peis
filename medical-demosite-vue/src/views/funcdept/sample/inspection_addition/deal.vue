<!-- 检验加项-处理对话框 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="450px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :inline="true" label-width="90px" :rules="rules" hide-required-asterisk v-loading="loading">
        <el-form-item label="项目名称" prop="examfeeitemName">
          <el-input v-model="form.examfeeitemName" readonly style="width: 280px" />
        </el-form-item>
        <el-form-item label="处理状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择" clearable style="width: 280px">
            <el-option label="未处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="检验结束" :value="2" />
            <el-option label="需要重检" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理人" prop="handleNameId">
          <input-select :selectData="selectData" @change="selectChange" :initialValue="form.handleName"></input-select>
        </el-form-item>
        <el-form-item label="处理时间" prop="handleTime">
          <el-date-picker v-model="form.handleTime" style="width: 280px" value-format="yyyy-MM-dd hh:mm:ss" type="datetime"> </el-date-picker>
        </el-form-item>
        <el-form-item label="处理意见" prop="idea">
          <el-input v-model="form.idea" placeholder="请输入内容" clearable style="width: 280px" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getDetails, handleDeal } from '@/api/funcdept/sample/inspection_addition.js'
export default {
  data() {
    return {
      // 详情id
      id: undefined,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 处理人数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '', //第一列标题
        value: '处理人', //第二列标题
        url: '/outside/sendRegister/getLqrData', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'occupationSummary', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'srm', //向接口传递的参数名(不传默认为'inputCode')
      },
      rules: {
        handleNameId: [{ required: true, message: '请选择处理人', trigger: 'change' }],
        handleTime: [{ required: true, message: '请选择处理时间', trigger: 'change' }],
      },
    }
  },
  methods: {
    // 选择处理人返回值
    selectChange(value) {
      this.form.handleNameId = value.id
    },
    // 处理
    handleDeal(id) {
      this.id = id
      this.reset()
      this.open = true
      this.getItemDetails()
      this.title = '处理'
    },
    // 反处理
    handleRedeal(id) {
      this.id = id
      this.reset()
      this.open = true
      this.title = '反处理'
    },
    // 获取详情
    getItemDetails() {
      this.loading = true
      getDetails(this.id).then(({ data }) => {
        data.handleName = data.handleName ? data.handleName : decodeURIComponent(this.$getCookie('username'))
        data.handleNameId = data.handleNameId ? data.handleNameId : this.$getCookie('userNo')
        data.handleTime = data.handleTime ? data.handleTime : this.$getDate()
        this.form = data
        this.loading = false
      })
    },
    // 表单重置
    reset() {
      this.form = {
        examfeeitemName: undefined,
        status: undefined,
        handleNameId: undefined,
        handleName: undefined,
        handleTime: undefined,
        idea: undefined,
      }
      this.getItemDetails()
      this.resetForm('form')
    },
    // 重置
    resetQuery() {
      this.resetForm('queryExam')
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.idea) {
            this.form.idea = '无'
          }
          this.form.item = this.form.examfeeitemName
          // this.form.handleTime = this.form.handleTime.slice(0, 10) + ' 00:00:00'
          if (this.title == '处理') {
            handleDeal(this.form).then((response) => {
              this.$modal.msgSuccess('处理成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            handleDeal(this.form).then((response) => {
              this.$modal.msgSuccess('反处理成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>
