<!-- 编辑开单助理 开发人：麦沃德科技矢北 -->
<template>
  <el-dialog
    title="编辑开单助理"
    :visible.sync="open"
    style="height: 640px"
    width="640px"
    append-to-body
  >
    <el-form size="small" :inline="true" ref="form" :model="form" v-loading="loading">
      <el-form-item prop="ddmc" label-width="120px" label="订单名称">
        <el-input style="width: 470px" v-model="form.ddmc" readonly></el-input>
      </el-form-item>
      <el-form-item prop="xsjl" label-width="120px" label="销售经理">
        <el-input style="width: 470px" v-model="form.xsjl" readonly></el-input>
      </el-form-item>
      <el-form-item prop="cjddrq" label-width="120px" label="创建订单日期">
        <el-input style="width: 470px" v-model="form.cjddrq" readonly></el-input>
      </el-form-item>
      <el-form-item label="开单助理" label-width="120px" prop="kdzlName">
        <search-select
          :selectData="selectDatakdzl"
          @change="selectChangekdzl"
          selectWidth="470"
          :initialValue="form.kdzlName"
          :multiple="true"
        ></search-select>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="diasubmitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addKdzl, saveKdzl } from "@/api/sale/order_customization.js"

export default {
  data() {
    return {
      open: false,
      loading: false,
      // 订单id
      id: undefined,
      // 表单数据
      form: {},
      // 开单助理数据
      selectDatakdzl: {
        placeholder: "请选择...",
        inputTitle: "拼音搜索", // 搜索标题
        inputPlaceholder: " ", // 搜索placeholder
        key: "输入码", //第一列标题
        value: "助理姓名", //第二列标题
        url: "/sell/createorder/getKdzl", //请求连接
        bindValue: "", //初始值(必传)
        firstName: "inputCode", //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: "userName", //接口返回值对应第二列的参数名(不传默认为'name')
        thirdName: "所属分中心",
        fourthName: "所属部门",
        thirdData: "ssFzx",
        fourthData: "ssDep"
        // optionFlag:'id',
      }
    }
  },
  methods: {
    //打开按钮
    showDialog(id) {
      this.id = id
      this.reset()
      this.open = true
      this.loading = true
      addKdzl({ id })
        .then(({ data }) => {
          this.form = data
          this.form.kdzlName = this.form.kdzlName.split(',');
          this.form.cjddrq = data.cjddrq ? data.cjddrq.split(" ")[0] : ""
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 选择开单助理
    selectChangekdzl(value) {
      this.form.kdzlName = []
      value.forEach((el) => {
        this.form.kdzlName.push(el.userName)
      })
      // this.form.kdzlName = value.userName
    },
    diasubmitForm() {
      if (!this.form.kdzlName) {
        this.$modal.msgWarning("请选择开单助理")
        return
      }
      this.loading = true
      this.form.kdzlName = this.form.kdzlName.join(',');

      saveKdzl({
        id: this.id,
        kdzlName: this.form.kdzlName
      })
        .then(() => {
          this.loading = false
          this.$modal.msgSuccess("修改成功")
          this.cancel()
          this.$emit("getList")
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 表单重置
    reset() {
      this.form = {
        ddmc: undefined,
        xsjl: undefined,
        cjddrq: undefined,
        kdzlName: undefined
      }
      this.resetForm("form")
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