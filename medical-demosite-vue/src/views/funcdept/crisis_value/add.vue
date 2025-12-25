<!-- 危急值提报 开发人：麦沃德科技暴雨 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="110px" hide-required-asterisk>
      <el-form-item label="体检号" prop="tjid">
        <el-input v-model="form.tjid" placeholder="请输入" @keydown.enter.native="searchData" clearable style="width: 308px" />
      </el-form-item>
      <el-form-item label="开单医生" prop="doctorapply">
        <el-input v-model="form.doctorapply" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="姓名" prop="gwrymc">
        <el-input v-model="form.gwrymc" readonly placeholder="体检号查询获取" clearable style="width: 308px" />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-input :value="['男', '女'][form.xb]" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="年龄" prop="nl">
        <el-input v-model="form.nl" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="公司" prop="orgName">
        <el-input v-model="form.orgName" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="部门" prop="orgDepart">
        <el-input v-model="form.orgDepart" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" readonly placeholder="体检号查询获取" style="width: 308px" />
      </el-form-item>
      <el-form-item label="危急程度" prop="wjzjb">
        <el-select v-model="form.wjzjb" placeholder="请选择" clearable style="width: 308px">
          <el-option v-for="item in wjzjb" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="登记时间" prop="dateregister">
        <el-date-picker v-model="form.dateregister" style="width: 308px" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="提报者" prop="reportMan">
        <el-input v-model="form.reportMan" placeholder="请输入提报者" clearable style="width: 308px" />
      </el-form-item>
      <el-form-item label="提报科室" prop="reportDivision">
        <!-- <el-input v-model="form.reportDivision" placeholder="请输入内容"   clearable style="width: 308px"/> -->
        <input-select ref="reportDivision" :selectData="reportDivision" :initialValue="form.deptName" @change="handleReportDivision" selectWidth="308" :disabled="!form.gwrymc" :queryParams="{ patientcode: form.tjid }"></input-select>
      </el-form-item>
      <el-form-item label="体检结果" prop="wjzxj">
        <el-input v-model="form.wjzxj" placeholder="请输入内容" clearable style="width: 736px" type="textarea" :rows="5" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset(1)">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { crisisValueAdd, crisisValueDetail, getTjzData } from '@/api/crisis/crisis_value.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      rowId: '',
      //input-select
      reportDivision: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/abteilung/CrisisValue/getKs',
        bindValue: '', //初始值(必传)
        queryData: 'key',
      },
      wjzjb: [
        {
          id: 1,
          text: '高',
        },
        {
          id: 2,
          text: '中',
        },
        {
          id: 3,
          text: '低',
        },
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
          id: '',
          text: '',
        },
      ],
      // 表单校验
      rules: {
        tjid: [{ required: true, message: '体检号不能为空', trigger: 'change' }],
        wjzjb: [{ required: true, message: '请选择危急程度', trigger: 'change' }],
        // reportMan: [{ required: true, message: '提报者不能为空', trigger: 'change' }],
        reportDivision: [{ required: true, message: '请选择提报科室', trigger: 'change' }],
      },
    }
  },
  methods: {
    //获取科室下拉
    handleReportDivision(value) {
      this.form.reportDivision = value.id
      this.reportDivision.bindValue = value.id
      this.form.wjzxj = value.conclusions
    },
    //按下键盘查询
    searchData() {
      var obj = {
        personcode: this.form.tjid,
        key: true,
      }
      getTjzData(obj).then((res) => {
        if (res.code == 200) {
          var obj = {
            gwrymc: res.data.patientname || '',
            xb: res.data.idSex,
            doctorapply: res.data.doctorapply || '',
            idOpendoctor: res.data.idOpendoctor || '',
            nl: res.data.age || '',
            phone: res.data.phone || '',
            orgDepart: res.data.orgDepart || '',
            orgName: res.data.orgName || '',
            idOrg: res.data.idOrg || '',
            dateregister: res.data.dateregister || '',
            tjlx: res.data.idExamtype || '',
          }
          this.form = { ...this.form, ...obj }
          this.form.tjid = res.data.patientcode
        } else {
          this.$modal.msgWarning(res.msg)
        }
      })
    },
    // 添加
    handleAdd() {
      this.open = true
      this.title = '添加'
      this.rowId = undefined
      this.reset()
    },
    // 加急
    handleUpdate(row) {
      this.open = true
      this.title = '编辑'
      this.rowId = row
      this.reset()
      this.details()
    },
    // 获取详情
    details() {
      crisisValueDetail(this.rowId).then((res) => {
        if (res.code == 200) {
          this.form = res.data.riskclient || {}
          var obj = {
            doctorapply: res.data.doctorapply,
            idOpendoctor: res.data.idOpendoctor,
            orgDepart: res.data.orgDepart,
            orgName: res.data.orgName,
            idOrg: res.data.idOrg,
            wjzjb: res.data.wjzjb,
            wjzxj: res.data.wjzxj,
            deptName: res.data.deptName,
            phone: res.data.riskclient.lxdh,
            dateregister: res.data.riskclient.tirq,
          }
          this.form = { ...this.form, ...obj }
          // this.searchData()
        }
      })
    },
    // 表单重置
    reset(type) {
      if (this.rowId && type == `1`) {
        this.handleUpdate(this.rowId)
        return
      } else {
        this.form = {
          doctorapply: '', //开单医生
          idOpendoctor: '', //销售经理(开单医生id)
          id: '',
          gwrymc: '', //高危人员名称
          idOrg: '', //团体ID
          phone: '', //联系电话
          nl: '', //年龄
          orgDepart: '', //部门
          orgName: '', //团体名称(公司)
          reportDivision: '', //提报科室
          reportMan: decodeURIComponent(this.$getCookie('username')), //提报者
          reportstatus: 0, //提报状态
          tirq: '', //体检日期(登录日期)
          tjid: '', ///体检id(体检号)
          wjzjb: '', //危急值级别(危急程度)
          wjzxj: '', //危急值小结
          xb: '', //性别
          dateregister: '',
          deptName: '',
        }
        this.reportDivision.bindValue = ''
        this.resetForm('form')
      }
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
          if (this.form.id) {
            crisisValueAdd(this.form).then((res) => {
              if (res.code == 200) {
                this.$modal.msgSuccess('修改成功')
              }
              this.open = false
              this.$emit('getList')
            })
          } else {
            this.form.lxdh = this.form.phone
            this.form.tirq = this.form.dateregister
            // return
            crisisValueAdd(this.form).then((res) => {
              if ((res.code = 200)) {
                this.$modal.msgSuccess('添加成功')
              }
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>
