<!-- 销售月度目标-制定季度目标 麦沃德科技 开发人:清风/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="810px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="110px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="年份" prop="year">
        <el-input style="width: 260px" v-model="form.year" readonly></el-input>
      </el-form-item>
      <el-form-item label="年度目标(元)" v-if="title != '查看'">
        <el-input style="width: 260px" v-model="goal" readonly></el-input>
      </el-form-item>
      <br v-else />
      <el-form-item label="1月目标(元)" prop="target1">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target1" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="2月目标(元)" prop="target2">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target2" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="3月目标(元)" prop="target3">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target3" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="4月目标(元)" prop="target4">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target4" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="5月目标(元)" prop="target5">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target5" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="6月目标(元)" prop="target6">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target6" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="7月目标(元)" prop="target7">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target7" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="8月目标(元)" prop="target8">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target8" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="9月目标(元)" prop="target9">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target9" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="10月目标(元)" prop="target10">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target10" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="11月目标(元)" prop="target11">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target11" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="12月目标(元)" prop="target12">
        <el-input placeholder="请输入目标额" style="width: 260px" type="number" v-model="form.target12" :readonly="isRead"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="bz">
        <el-input type="textarea" placeholder="请输入" style="width: 640px" :autosize="autosize" v-model="form.bz" :readonly="isRead"></el-input>
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
import { getTarget, addTarget } from '@/api/sale/sales_target/monthly_sales_target.js'
import { getYearTarget } from '@/api/sale/sales_target/quarterly_sales_target.js'
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
        target1: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target2: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target3: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target4: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target5: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target6: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target7: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target8: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target9: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target10: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target11: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
          { validator: checkNumber, trigger: 'change' },
        ],
        target12: [
          { required: true, message: '月目标额不能为空', trigger: 'change' },
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
      this.title = '制定月度目标'
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
          target1: undefined,
          target2: undefined,
          target3: undefined,
          target4: undefined,
          target5: undefined,
          target6: undefined,
          target7: undefined,
          target8: undefined,
          target9: undefined,
          target10: undefined,
          target11: undefined,
          target12: undefined,
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
          if (
            this.goal !=
            Number(this.form.target1) +
              Number(this.form.target2) +
              Number(this.form.target3) +
              Number(this.form.target4) +
              Number(this.form.target5) +
              Number(this.form.target6) +
              Number(this.form.target7) +
              Number(this.form.target8) +
              Number(this.form.target9) +
              Number(this.form.target10) +
              Number(this.form.target11) +
              Number(this.form.target12)
          ) {
            this.$alert('月度目标和不等于年度目标', '提示')
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
