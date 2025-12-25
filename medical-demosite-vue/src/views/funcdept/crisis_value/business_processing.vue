<!-- 危急值提报-业务处理 开发人：麦沃德科技暴雨/清风 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body :close-on-click-modal="false">
    <el-tag style="width: 100%; margin-bottom: 10px">提报信息</el-tag>
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="110px" hide-required-asterisk>
      <el-form-item label="体检号" prop="tjid">
        <el-input v-model="form.tjid" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="高危人员" prop="gwrymc">
        <el-input v-model="form.gwrymc" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="年龄" prop="nl">
        <el-input v-model="form.nl" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-input :value="form.xb == 0 ? '男' : '女'" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="联系电话" prop="lxdh">
        <el-input v-model="form.lxdh" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="form.tjlx" disabled clearable style="width: 240px">
          <el-option v-for="item in tjlx" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检日期" prop="tirq">
        <el-date-picker v-model="form.tirq" disabled value-format="yyyy-MM-dd HH:mm:ss" type="date" placeholder="请选择日期" style="width: 240px"></el-date-picker>
      </el-form-item>
      <el-form-item label="开单医生" prop="doctorapply">
        <el-input v-model="form.doctorapply" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="体检结果" prop="wjzxj">
        <el-input v-model="form.wjzxj" readonly placeholder="请输入内容" clearable style="width: 960px; font-size: 20px" type="textarea" :rows="3" />
      </el-form-item>
      <el-tag style="width: 100%; margin-bottom: 10px">业务处理</el-tag>
      <el-form-item label="发放方式" prop="ywfffs">
        <el-select v-model="form.ywfffs" placeholder="请选择" clearable style="width: 240px">
          <el-option :label="item.methodName" :value="item.id" v-for="item in ywfffs" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="ywclzt">
        <el-select v-model="form.ywclzt" placeholder="请选择" style="width: 240px">
          <el-option v-for="item in ywclzt" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-row style="width: 600px; margin-bottom: 20px">
        <el-col :span="2" style="margin-left: 110px">
          <el-button type="warning" plain size="mini" icon="el-icon-tickets" @click="handleAdd" v-hasPermi="['funcdept:crisisValue:lsreport']">临时报告生成 </el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 310px">
          <el-button type="warning" plain size="mini" icon="el-icon-tickets" @click="handleUpdate" :disabled="isInterimReport" v-hasPermi="['funcdept:crisisValue:showlsreport']">临时报告预览 </el-button>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="ywbz">
        <el-input v-model="form.ywbz" placeholder="请输入内容" clearable style="width: 960px; font-size: 20px" type="textarea" :rows="3" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="geyDetails">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
import pinyin from '@/utils/pinyin.js'
import { crisisValueDetail, saveYw, getIssueWayData } from '@/api/crisis/crisis_value.js'
import { createTemp } from '@/api/search/check_query.js' // 获取临时报告接口

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
      // 遮罩层
      loading: true,
      // 选中数组
      examItems: [],
      selectIds: [],
      // 总条数
      total: 0,
      // 表单参数
      form: {
        interimReport: '',
      },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      ywfffs: [],
      rowId: '',

      //需求数组
      tjlx: [
        { id: 0, text: '健康' },
        { id: 1, text: '职业' },
        { id: 2, text: '综合' },
        { id: 3, text: '复查' },
      ],
      xb: [
        {
          id: 0,
          text: '男',
        },
        {
          id: 1,
          text: '女',
        },
        {
          id: 2,
          text: '通用',
        },
      ],
      ywclzt: [
        {
          id: 0,
          text: '未完成',
        },
        {
          id: 1,
          text: '已完成',
        },
      ],
      //补充体检者id
      patientcode: '',
      //补充是否查看报告
      isInterimReport: false,
      // 表单校验
      rules: {
        ywclzt: [{ required: true, message: '请选择处理状态', trigger: 'change' }],
      },
    }
  },
  computed: {
    interimReportWatch() {
      return this.form.interimReport
    },
  },
  methods: {
    //业务处理
    handlebp(row, tjid) {
      this.reset()
      this.open = true
      this.title = '业务处理'
      this.rowId = row
      this.patientcode = tjid
      let obj = {
        tjlx: row.tjlx,
        doctorapply: row.doctorapply,
        tirq: row.reportTime,
      }
      this.form = { ...this.form, ...obj }
      this.geyDetails()
      this.getOption()
    },
    //获取数据详情
    geyDetails() {
      crisisValueDetail(this.rowId).then((res) => {
        if (res.code == 200) {
          var riskclient = {
            id: res.data.riskclient.id,
            tjid: res.data.riskclient.tjid,
            gwrymc: res.data.riskclient.gwrymc,
            nl: res.data.riskclient.nl,
            xb: res.data.riskclient.xb,
            lxdh: res.data.riskclient.lxdh,
            tjlx: res.data.riskclient.tjlx,
            tirq: res.data.riskclient.tirq,
            wjzxj: res.data.riskclient.wjzxj,
            ywfffs: res.data.riskclient.ywfffs,
            ywclzt: res.data.riskclient.ywclzt,
            ywbz: res.data.riskclient.ywbz,
          }
          var obj = {
            doctorapply: res.data.doctorapply,
            orgDepart: res.data.orgDepart,
            orgName: res.data.orgName,
            wjzjb: res.data.wjzjb,
            wjzxj: res.data.wjzxj,
          }
          this.form = { ...riskclient, ...obj }
          if (this.form.interimReport == 1) {
            this.isInterimReport = true
          }
        } else {
          this.$modal.msgWarning('请求详情失败')
        }
      })
    },
    //获取发放方式列表
    getOption() {
      getIssueWayData().then((res) => {
        if (res.code == 200) {
          res.data.forEach((item) => {
            this.ywfffs.push(item)
          })
        } else {
          this.$modal.msgWarning(res.msg)
        }
      })
    },
    // 临时报告生成
    handleAdd() {
      this.$modal
        .confirm('确定生成临时报告?', '生成')
        .then(() => {
          let patientcode = this.patientcode
          var data = {
            dh: 0,
            patientcode: patientcode,
          }
          const loading = this.$loading({
            lock: true,
            text: '报告生成中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          createTemp(data)
            .then((response) => {
              loading.close()
              if (response.msg.indexOf('成功') != -1) {
                this.isInterimReport = true
                this.$modal.alertSuccess('报告生成成功！', '提醒')
              } else {
                this.$modal.alertWarning(response.msg, '提醒')
              }
              this.geyDetails()
            })
            .catch(() => {
              loading.close()
              this.geyDetails()
            })
        })
        .catch(() => {
          loading.close()
        })
    },
    // 临时报告预览
    handleUpdate() {
      var query = {
        patientcode: this.patientcode,
        idExamtype: 0,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualTemporaryReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
    // 表单重置
    reset() {
      this.form = {
        tjid: undefined,
        gwrymc: undefined,
        nl: undefined,
        xb: undefined,
        lxdh: undefined,
        tjlx: undefined,
        tjrq: undefined,
        doctorapply: undefined,
        wjzxj: undefined,
        ywfffs: undefined,
        ywclzt: 1,
        ywbz: undefined,
        id: undefined,
      }
      this.ywfffs = []
      this.resetForm('form')
    },
    // 重置
    resetQuery() {
      this.resetForm('queryExam')
      this.handleQuery()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          saveYw(this.form).then((res) => {
            if (res.code == 200) {
              this.$modal.msgSuccess(res.msg)
              this.open = false
              this.$emit('getList')
            }
          })
        }
      })
    },
  },
}
</script>
