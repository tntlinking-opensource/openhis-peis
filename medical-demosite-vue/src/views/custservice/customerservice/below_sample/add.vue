<!-- 不合格样本回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog title="不合格样本回访" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" hide-required-asterisk v-loading="loading">
        <el-form-item label="体检号:" prop="patientcode">
          <el-input v-model="form.patientcode" :readonly="true" clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="姓名:" prop="patientname">
          <el-input v-model="form.patientname" :readonly="true" clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="性别:" prop="idSex">
          <el-select v-model="form.idSex" disabled clearable style="width: 230px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话:" prop="phone">
          <el-input v-model="form.phone" :readonly="true" clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="体检时间:" prop="dateregister">
          <el-date-picker v-model="form.dateregister" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期" clearable :readonly="true" style="width: 230px"></el-date-picker>
        </el-form-item>
        <el-form-item label="不合格样本数量:" prop="count">
          <el-input v-model="form.count" :readonly="true" clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="不合格原因:" prop="belowquestion">
          <el-input v-model="form.belowquestion" placeholder="暂无数据" :readonly="true" clearable style="width: 590px" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="回访时间:" prop="visitTime">
          <el-date-picker v-model="form.visitTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期" clearable style="width: 230px"></el-date-picker>
        </el-form-item>
        <el-form-item label="再通知:" prop="noticeAgain">
          <el-checkbox v-model="form.noticeAgain"></el-checkbox>
        </el-form-item>
        <el-form-item label="回访备注:" prop="memo">
          <el-input v-model="form.memo" placeholder="请输入内容" clearable style="width: 590px" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { saveOrUpdate, belowSample } from '@/api/custservice/customerservice/below_sample/below_sample.js'
import pinyin from '@/utils/pinyin.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 查询参数
      queryExamination: {
        pageNum: 1,
        pageSize: 10,
        examName: undefined,
        examInputCode: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
      },
      // 表单校验
      rules: {},
      sid: 0,
      //男女
      options: [
        {
          value: 0,
          label: '男',
        },
        {
          value: 1,
          label: '女',
        },
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      examItems: [],
      selectIds: [],
      // 总条数
      total: 0,

      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {
        patientcode: undefined,
        patientname: undefined,
        idSex: undefined,
        phone: undefined,
        dateregister: undefined,
        count: undefined,
        belowquestion: undefined,
        visitTime: undefined,
        noticeAgain: true,
        memo: undefined,
        id: undefined,
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    // 提交按钮
    submitForm() {
      let noticeAgains = 0
      if (this.form.noticeAgain == true) {
        noticeAgains = 1
      } else {
        noticeAgains = 0
      }
      let param = {
        belowquestion: this.form.belowquestion,
        count: this.form.count,
        dateregister: this.form.dateregister,
        id: this.form.id,
        idSex: this.form.idSex,
        memo: this.form.memo,
        noticeAgain: noticeAgains,
        patientcode: this.form.patientcode,
        patientname: this.form.patientname,
        phone: this.form.phone,
        visitTime: this.form.visitTime,
      }
      saveOrUpdate(param).then(() => {
        this.$modal.msgSuccess('修改成功!')
        this.open = false
        this.$emit('getList')
      })
    },
    // 添加
    handleAdd(id) {
      this.sid = id
      this.open = true
      this.loading = true
      belowSample(this.sid)
        .then((response) => {
          this.form = response.data
          this.form.noticeAgain = this.form.noticeAgain == '1' ? true : false
          this.form.visitTime = this.form.visitTime  ? this.form.visitTime : this.$getDate()
          this.form = JSON.parse(JSON.stringify(this.form))
          this.loading = false
        })
        .catch((error) => {
          this.loading = false
          console.error(error)
        })
    },
    // 表单重置
    reset() {
      belowSample(this.sid).then((response) => {
        this.form = response.data
        this.form.noticeAgain = this.form.noticeAgain == '1' ? true : false
        this.form = JSON.parse(JSON.stringify(this.form))
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
  },
}
</script>
