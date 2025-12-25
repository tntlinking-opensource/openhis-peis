<!-- 销售年度目标-制定年度目标 麦沃德科技 开发人:清风/予安/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="90px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="年份" style="margin-right: 0" prop="year">
        <el-input style="width: 260px" v-model="form.year" readonly></el-input>
      </el-form-item>
      <el-form-item label="目标额(元)" style="margin-right: 0" prop="ndmb">
        <el-input style="width: 260px" placeholder="请输入目标额" v-model="form.ndmb" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="备注" style="margin-right: 0" prop="bz">
        <el-input type="textarea" placeholder="请输入" style="width: 260px" :autosize="autosize" v-model="form.bz" :readonly="isRead"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" v-if="!isRead">保 存</el-button>
      <el-button type="primary" plain @click="reset" v-if="!isRead">重 置</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getYearTarget, addYearTarget } from '@/api/sale/sales_target/annual_sales_target.js'
export default {
  data() {
    var checkNumber = (rule, value, callback) => {
      if (isNaN(value)) {
        callback(new Error('请输入数字'))
      } else {
        if (value < 0) {
          callback(new Error('目标额不能小于0'))
        } else {
          callback()
        }
      }
    }
    return {
      // 表单参数
      form: {},
      // 选中数组
      ids: [],
      // 文本域自适应内容高度参数
      autosize: { minRows: 3, maxRows: 3 },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 类型：查看/处理
      type: 0,
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否只读
      isRead: false,
      // 表单校验
      rules: {
        year: [{ required: true, message: '年份不能为空', trigger: 'change' }],
        ndmb: [
          { required: true, message: '目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
      },
    }
  },
  methods: {
    // 添加
    handleAdd(row, listYear) {
      var data = JSON.parse(JSON.stringify(row))
      data.year = listYear
      data.ndmb = undefined
      data.bz = undefined
      this.popData = JSON.parse(JSON.stringify(data))
      this.reset()
      this.open = true
      this.title = '制定年度目标'
      this.form = data
      this.isRead = false
    },
    // 编辑
    handleUpdate(id) {
      this.popData = undefined
      this.reset()
      this.open = true
      this.title = '编辑'
      this.popLoading = true
      this.isRead = false
      getYearTarget(id).then((response) => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data
      })
    },
    // 查看
    handleView(id) {
      this.popData = undefined
      this.reset()
      this.open = true
      this.title = '查看'
      this.popLoading = true
      this.isRead = true
      getYearTarget(id).then((response) => {
        this.popLoading = false
        this.form = response.data
      })
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
      } else {
        this.form = {
          year: undefined,
          ndmb: undefined,
          bz: undefined,
        }
      }
      this.resetForm('form')
    },
    //取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.leaUserId = this.form.userid
          addYearTarget(this.form).then((response) => {
            this.$modal.msgSuccess('保存成功')
            this.open = false
            this.$emit('getList')
          })
        }
      })
    },
  },
}
</script>
