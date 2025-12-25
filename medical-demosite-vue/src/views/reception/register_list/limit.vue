<!-- 批量设置统收限额  开发人：麦沃德科技半夏 -->
<template>
  <div class="batch-container">
    <el-dialog title="批量设置统收限额" :visible.sync="open" class="limit-receipt" width="500px" append-to-body
      :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" hide-required-asterisk v-loading="loading">
        <el-form-item label="订单号" prop="ddh">
          <input-select :selectData="ddhData" selectWidth="380" @change="ddhChange" :queryParams="{customerId: order}"></input-select>
        </el-form-item>
        <el-form-item label="套餐" prop="jctcId">
          <el-select v-model="form.jctcId" filterable remote clearable :remote-method="remoteMethod"
            placeholder="请输入输入码或名称查询" :loading="remoteLoading" style="width: 380px;">
            <el-option v-for="item in remoteOptions" :key="item.id" :label="item.tjtcmc" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <div class="age-box">
            <el-input-number v-model="form.ageStart" controls-position="right" style="width:175px" :min="0"
              :max="form.ageEnd"></el-input-number>
            <span style="flex: 1;text-align: center;">—</span>
            <el-input-number v-model="form.ageEnd" controls-position="right" style="width:175px" :min="0" :max="300"
              @change="ageEndChange"></el-input-number>
          </div>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.idSex" placeholder="请选择性别" clearable style="width:380px">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="充值金额" prop="money">
          <el-input-number v-model="form.money" controls-position="right" style="width:380px"
            :max="999999"></el-input-number>
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
import { getAllComboAndMealData, saveTsLimitEdit } from "@/api/reception/register_list";
export default {
  components: {},
  props: [],
  data() {
    return {
      order: "",
      // 遮罩层
      loading: false,
      // 表单数据
      form: {},
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        ddh: [{ required: true, message: "订单号不能为空", trigger: "change" }],
        jctcId: [{ required: true, message: "套餐不能为空", trigger: "change" }],
        money: [{ required: true, message: "充值金额不能为空", trigger: "change" }],
      },
      // 订单号
      ddhData: {
        placeholder: '请输入订单号',
        key: '订单号',//第一列标题 
        value: '订单名称',//第二列标题 
        third: '单位名称',//第三列标题（没有传空）
        url: '/reception/register/getDdhData',//请求连接
        bindValue: '',
        firstName: 'ddh',//接口返回值对应第一列的参数名
        secondName: 'ddmc',//接口返回值对应第二列的参数名
        thirdName: 'customername',//接口返回值对应第三列的参数名
        // queryData: "key",
      },
      remoteOptions: [],
      remoteLoading: false,
    }
  },
  computed: {},
  watch: {},
  created() { },
  mounted() { },
  methods: {
    // 显示
    handleShow(id) {
      this.order = id
      this.open = true
      this.reset()
    },
    // 保存
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          saveTsLimitEdit(this.form).then(()=> {
            this.$modal.alertSuccess("保存成功！")
          })
        }
      });
    },
    // 重置
    reset() {
      this.form = {
        order: this.order,
        ddh: undefined,
        jctcId: undefined,
        ageStart: undefined,
        ageEnd: undefined,
        gender: undefined,
        money: undefined,
      }
      this.ddhData.bindValue = ""
      this.resetForm("form");
    },
    // 取消
    cancel() {
      this.open = false;
    },
    // 年龄改变
    ageEndChange(val) {
      if (val < this.form.ageStart) {
        this.form.ageStart = val
      }
    },
    // 订单号改变
    ddhChange(value) {
      this.ddhData.bindValue = value.id
      this.form.ddh = value.id;
    },
    //远程选框的方法
    remoteMethod(keyword) {
      const query = {
        key: keyword
      }
      this.remoteLoading = true;
      getAllComboAndMealData(query).then(res => {
        this.remoteLoading = false;
        this.remoteOptions = res.data;
      });
    },
  },
}
</script>
<style lang="scss">
.limit-receipt {
  .age-box {
    display: flex;
    align-items: center;
  }
}
</style>
