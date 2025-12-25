<template>
  <el-dialog title="来检短信提醒" width="350px" :visible.sync="visible" append-to-body destroy-on-close>
    <el-tag style="width: 100%; margin-bottom: 16px"
      >当前已选中： <span>{{ form.ids.length }}</span
      >人</el-tag
    >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" hide-required-asterisk>
      <el-form-item label="姓名" prop="idContact">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="form.idContact"></input-select>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式" clearable type="tel"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { saveSmsToExam } from '@/api/reception/prepare_order.js'

export default {
  name: 'editRightImport',
  data() {
    return {
      visible: false,
      // 体检者id集合
      ids: '',
      selectData: {
        placeholder: '请输入',
        value: '联系人', //第二列标题
        url: '/reception/register/getLqrData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'occupationSummary', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'srm', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表单数据
      form: {
        idContact: undefined,
        phone: undefined,
        orderId: undefined,
        ids: [],
      },
      // 校验规则
      rules: {
        idContact: [{ required: true, message: '请选择联系人', trigger: 'blur' }],
        phone: [{ required: true, message: '联系方式不能为空', trigger: 'blur' }],
      },
    }
  },
  methods: {
    showDialog(ids, orderId) {
      this.form.orderId = orderId
      this.form.ids = ids
      this.visible = true
    },
    // 姓名返回值
    selectChange(val) {
      this.form.idContact = val.id
    },
    // 提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          saveSmsToExam(this.form).then(() => {
            this.$message({
              message: '添加成功',
              type: 'success',
            })
            this.visible = false
          })
        }
      })
    },
    // 取消
    cancel() {
      this.form.idContact = undefined
      this.form.phone = undefined
      this.visible = false
    },
  },
}
</script>

<style></style>
