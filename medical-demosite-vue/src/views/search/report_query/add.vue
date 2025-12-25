<!-- 报告信息查询-修改发放方式 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <el-dialog title="修改发放方式" :visible.sync="open" width="390px" append-to-body>
    <el-form ref="form" :model="form" :inline="true" label-width="110px" hide-required-asterisk>
      <el-form-item label="处理结果" prop="sflj">
        <el-select v-model="form.sflj" placeholder="请选择" clearable style="width: 200px">
          <el-option v-for="(item, index) in this.typeData" :key="index" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getTypeData, saveOrUpData } from '@/api/search/report_query'

export default {
  components: {},
  props: [],
  data() {
    return {
      //勾选数据的id
      ids: [],
      typeData: [],
      form: {},
      // 是否显示弹出层
      open: false,
    }
  },
  created() {
    getTypeData().then((response) => {
      this.typeData = response.data
    })
  },
  methods: {
    // 表单重置
    reset() {
      this.examList = []
      this.selectList = []
      this.selectIds = []
      this.total = 0
      this.departData = []
      this.labTypeData = []
      this.resetForm('queryExam')
      this.form = {
        examfeeitemName: undefined,
        groupOrder: 0,
      }
      this.resetForm('form')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    //打开按钮
    handleModify(id) {
      this.ids = id
      this.open = true
    },
    // 提交按钮
    submitForm() {
      let id = this.ids.join(',')
      const query = {
        idInformway: this.form.sflj,
        ids: '',
      }
      if (this.form.id != null) {
        saveOrUpData(this.form).then((response) => {
          this.$modal.msgSuccess('保存成功')
          this.open = false
          this.$emit('getList')
        })
      }
    },
  },
}
</script>
