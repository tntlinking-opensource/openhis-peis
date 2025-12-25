<template>
  <div class="add-container">
    <!-- 增加提醒 -->
    <el-dialog title="增加提醒" :visible.sync="open" width="450px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" hide-required-asterisk>
        <el-form-item label="客户单位名称" prop="khdwmcid">
          <input-select :selectData="selectData1" @change="selectChange1" :initialValue="form.khdwmc"></input-select>
        </el-form-item>
        <el-form-item label="提醒时间" prop="remindTime">
          <el-date-picker v-model="form.remindTime" style="width: 280px;" placeholder="请选择时间" value-format="yyyy-MM-dd HH:mm:ss" type="datetime">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提醒内容" prop="content">
          <el-input v-model="form.content" placeholder="请输入内容" clearable style="width: 280px" type="textarea" :rows="8" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="diaSubmitForm">保 存</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addRemind } from '@/api/sale/reminder.js'
export default {
  data() {
    return {
      open: false,
      khdwmcid: undefined,
      khdwmc: undefined,
      form: {},
      // 客户单位名称筛选数据
      selectData1: {
        placeholder: '请输入输入码选择',
        value: '客户单位名称', //第二列标题
        url: '/sell/createorder/getKhdwmcData', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key' //向接口传递的参数名
      },
      // 表单效验
      rules: {
        khdwmcid: [{ required: true, message: '客户单位名称不能为空', trigger: 'change' }],
        remindTime: [{ required: true, message: '提醒时间不能为空', trigger: 'change' }],
        content: [{ required: true, message: '提醒内容不能为空', trigger: 'change' }]
      }
    }
  },
  methods: {
    showDialog(khdwmcid, khdwmc) {
      this.open = true
      this.reset()
      this.khdwmcid = JSON.parse(JSON.stringify(khdwmcid))
      this.khdwmc = JSON.parse(JSON.stringify(khdwmc))
      this.form.khdwmcid = khdwmcid
      this.form.khdwmc = khdwmc
     
    },
    // 客户单位名称返回值
    selectChange1(value) {
      this.form.khdwmcid = value.id
      this.form.khdwmc = value.khdwmc
    },
    // 表单重置
    reset() {
      if (this.khdwmcid) {
        this.form = {
          khdwmcid: this.khdwmcid,
          khdwmc: this.khdwmc,
          remindTime: undefined,
          content: undefined
        }
      } else {
        this.form = {
          khdwmcid: undefined,
          khdwmc: undefined,
          remindTime: undefined,
          content: undefined
        }
      }
      this.resetForm('form')
    },
    // 保存提醒
    diaSubmitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          addRemind(this.form).then(() => {
            this.cancel()
            this.$modal.msgSuccess('保存成功')
          })
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    }
  }
}
</script>

<style>
</style>