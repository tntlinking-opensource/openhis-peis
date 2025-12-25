<!-- 销售季度目标-制定季度目标 麦沃德科技 开发人:清风/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="440px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="130px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="年份" style="margin-right: 0" prop="year">
        <el-input style="width: 260px" v-model="form.year" readonly></el-input>
      </el-form-item>
      <el-form-item label="年度目标(元)" style="margin-right: 0" v-if="title != '查看'">
        <el-input style="width: 260px" v-model="goal" readonly></el-input>
      </el-form-item>
      <br v-else />
      <el-form-item label="第一季度目标(元)" style="margin-right: 0" prop="dyjdmb">
        <el-input placeholder="请输入目标额" style="width: 260px" v-model="form.dyjdmb" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="第二季度目标(元)" style="margin-right: 0" prop="dejdmb">
        <el-input placeholder="请输入目标额" style="width: 260px" v-model="form.dejdmb" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="第三季度目标(元)" style="margin-right: 0" prop="dsjdmb">
        <el-input placeholder="请输入目标额" style="width: 260px" v-model="form.dsjdmb" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="第四季度目标(元)" style="margin-right: 0" prop="dijdmb">
        <el-input placeholder="请输入目标额" style="width: 260px" v-model="form.dijdmb" :readonly="isRead"></el-input>
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
import { getTarget, addTarget, getYearTarget } from '@/api/sale/sales_target/quarterly_sales_target.js'
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
        dyjdmb: [
          { required: true, message: '第一季度目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        dejdmb: [
          { required: true, message: '第二季度目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        dsjdmb: [
          { required: true, message: '第三季度目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        dijdmb: [
          { required: true, message: '第四季度目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
      },
      // 总目标
      goal: 0,
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
      this.title = '制定季度目标'
      this.form = data
      this.isRead = false
      this.getGoal(listYear, row.userid)
    },
    // 编辑
    handleUpdate(row, listYear) {
      this.popData = undefined
      this.reset()
      this.open = true
      this.title = '编辑'
      this.popLoading = true
      this.isRead = false
      this.getGoal(listYear, row.userid)
      getTarget(row.id).then((response) => {
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
      getTarget(id).then((response) => {
        this.popLoading = false
        this.form = response.data
      })
    },
    // 获取当前销售年度总目标
    getGoal(year, userid) {
      this.popLoading = true
      getYearTarget({
        year,
        userid,
      })
        .then(({ data }) => {
          if (data) {
            this.goal = data
          } else {
            this.goal = 0
            this.open = false
            this.$alert('暂未制定年度销售目标，请联系销售总监制定！', '提示')
          }
          this.popLoading = false
        })
        .catch((err) => {
          console.error(err)
          this.popLoading = false
        })
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
      } else {
        this.form = {
          year: undefined,
          bz: undefined,
          dyjdmb: undefined,
          dejdmb: undefined,
          dsjdmb: undefined,
          dijdmb: undefined,
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
          if (this.goal != Number(this.form.dyjdmb) + Number(this.form.dejdmb) + Number(this.form.dsjdmb) + Number(this.form.dijdmb)) {
            this.$alert('季度目标和不等于年度目标', '提示')
            return
          }
          addTarget(this.form).then((response) => {
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
