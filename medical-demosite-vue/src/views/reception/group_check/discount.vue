<!-- 团检加/弃项-折扣  开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 对话框 -->
    <el-dialog title="折扣" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :inline="true" label-width="130px" hide-required-asterisk>
        <el-form-item label="当前项目:" prop="sfxmt">
          <div style="width: 400px">{{ form.sfxmt }}</div>
        </el-form-item>
        <el-form-item label="当前项目原始价格:" prop="curitemprice">
          <div style="width: 400px">{{ form.curitemprice }}</div>
        </el-form-item>
        <el-form-item label="当前或批量扣率%:" prop="curitemkl">
          <el-input-number v-model="form.curitemkl" :precision="1" controls-position="right" :min="0" style="width: 400px" @blur="rateChange(form.curitemkl)" />
        </el-form-item>
        <el-form-item label="当前项目折后价格:" prop="curitemafterprice">
          <el-input-number v-model="form.curitemafterprice" :precision="1" controls-position="right" :min="0" style="width: 400px" @blur="priceChange(form.curitemafterprice)" />
        </el-form-item>
        <div style="width: 530px; height: 50px; line-height: 50px; background-color: #fff0ef; margin-top: 10px">
          <span style="color: #ff6161; margin-left: 10px">价格圆整至: 1角 说明: 无折扣则扣率为0%,九折为10%,八折为20%,....免费为100%</span>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
    }
  },
  methods: {
    //折扣
    handleDiscount(data) {
      this.reset()
      this.open = true
      this.title = '折扣'
      this.form.sfxmt = data.examfeeitemName
      this.form.curitemprice = data.price.toFixed(1)
      this.form.curitemafterprice = data.price.toFixed(1)
    },
    // 比率改变
    rateChange(value) {
      if (value) {
        this.form.curitemafterprice = this.form.curitemprice - this.form.curitemprice * value * 0.01
      }
    },
    // 折后价格改变
    priceChange(value) {
      this.form.curitemkl = 100 - (value / this.form.curitemprice) * 100
    },
    // 初始化
    reset() {
      this.form = {
        sfxmt: '',
        curitemprice: '',
        curitemkl: '',
        curitemafterprice: '',
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.cancel()
      this.$emit('discountConfirm', this.form.curitemafterprice)
    },
  },
}
</script>
