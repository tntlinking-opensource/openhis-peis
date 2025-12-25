<!-- 危急值提报-回访处理 开发人：麦沃德科技暴雨 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body :close-on-click-modal="false">
    <el-tag style="width: 100%; margin-bottom: 10px">提报信息</el-tag>
    <el-form ref="form" :model="form" :inline="true" label-width="110px" :rules="rules" hide-required-asterisk>
      <el-form-item label="体检号" prop="tjid">
        <el-input v-model="form.tjid" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="高危人员" prop="gwrymc">
        <el-input v-model="form.gwrymc" readonly clearable style="width: 240px; font-size: 20px" />
      </el-form-item>
      <el-form-item label="年龄" prop="nl">
        <el-input v-model="form.nl" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-input :value="form.xb == 0 ? '男' : '女'" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="联系电话" prop="lxdh">
        <el-input v-model="form.lxdh" readonly clearable style="width: 240px; font-size: 20px" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="form.tjlx" disabled clearable style="width: 240px">
          <el-option v-for="item in tjlx" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检日期" prop="tirq">
        <el-date-picker v-model="form.tirq" disabled style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="开单医生" prop="doctorapply">
        <el-input v-model="form.doctorapply" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="体检结果" prop="wjzxj">
        <el-input v-model="form.wjzxj" readonly clearable style="width: 960px; font-size: 20px" type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item label="业务处理人" prop="ywclr">
        <el-input v-model="form.ywclr" readonly clearable style="width: 240px" />
      </el-form-item>
      <el-form-item label="处理备注" prop="ywbz">
        <el-input v-model="form.ywbz" readonly clearable style="width: 600px; font-size: 20px" type="textarea" :rows="2" />
      </el-form-item>
      <el-tag style="width: 100%; margin-bottom: 10px">回访处理</el-tag>
      <el-form-item label="处理状态" prop="hfclzt">
        <el-select v-model="form.hfclzt" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="item in hfclzt" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理方式" prop="hfclfs">
        <el-select v-model="form.hfclfs" placeholder="请选择" clearable style="width: 240px">
          <el-option v-for="item in hfclfs" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理人" prop="hfclr">
        <!-- <el-input v-model="form.hfclr" placeholder="请输入内容" clearable style="width: 240px" /> -->
        <input-select :selectData="OpendoctorData" :isTrim="true" :initialValue="form.hfclr" selectWidth="240" @change="OpendoctorDataChange"></input-select>
      </el-form-item>
      <el-form-item label="备注" prop="hfbz">
        <el-input v-model="form.hfbz" placeholder="请输入内容" clearable style="width: 960px; font-size: 20px" type="textarea" :rows="3" />
      </el-form-item>
      <!-- <el-form-item label="发放方式" prop="hffffs">
          <el-select v-model="form.hffffs" placeholder="请选择" clearable style="width: 240px">
            <el-option v-for="item in hffffs" :key="item.id" :value="item.inputCode" :label="item.methodName"></el-option>
          </el-select>
        </el-form-item> -->
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="geyDetails">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { crisisValueDetail, saveHf, getIssueWayData } from '@/api/crisis/crisis_value.js'
export default {
  data() {
    return {
      // 查询参数
      queryExamination: {
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
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      rowId: '',
      tjlx: [
        { id: 0, text: '健康' },
        { id: 1, text: '职业' },
        { id: 2, text: '综合' },
        { id: 3, text: '复查' },
      ],
      hfclfs: [
        { id: 0, text: '当面告知' },
        { id: 1, text: '电话通知' },
        { id: 2, text: '短信通知' },
        { id: 3, text: '继续提报' },
      ],
      //回访处理状态
      hfclzt: [
        { id: 0, text: '已提报' },
        { id: 1, text: '继续提报' },
        { id: 2, text: '已完成' },
        { id: 3, text: '已取消' },
      ],
      //回访发放方式
      hffffs: [],
      // 校验
      rules: {
        hfclzt: [{ required: true, message: '请选择处理状态', trigger: 'change' }],
        hfclfs: [{ required: true, message: '请选择处理方式', trigger: 'change' }],
      },
      // 回访处理人列表
      OpendoctorData: {
        placeholder: '请输入输入码选择',
        value: '名称', //第二列标题
        url: '/report/healthAssociate/getAllUser', //请求连接--开单医师下拉路由
        secondName: 'username',
        bindValue: '',
      },
    }
  },
  methods: {
    //回访处理
    handlerv(row) {
      this.reset()
      this.open = true
      this.title = '回访处理'
      this.rowId = row
      this.geyDetails()
      this.getOption()
    },
    geyDetails() {
      crisisValueDetail(this.rowId).then((res) => {
        if (res.code == 200) {
          this.form = res.data.riskclient
          var obj = {
            doctorapply: res.data.doctorapply,
            orgDepart: res.data.orgDepart,
            orgName: res.data.orgName,
            wjzjb: res.data.wjzjb,
            wjzxj: res.data.wjzxj,
          }
          this.form = { ...this.form, ...obj }
          this.form.hfbz = this.form.hfbz ? this.form.hfbz : '已通知一个月复查'
          this.form.hfclr = this.form.hfclr ? this.form.hfclr : decodeURIComponent(this.$getCookie('username'))
        }
      })
    },
    //获取发放方式列表
    getOption() {
      getIssueWayData().then((res) => {
        if (res.code == 200) {
          res.data.forEach((item) => {
            this.hffffs.push(item)
          })
        } else {
          this.$modal.msgWarning(res.msg)
        }
      })
    },
    // 修改处理人
    OpendoctorDataChange(value) {
      this.form.hfclr = value.username
    },
    // 添加
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加'
    },
    // 加急
    handleUpdate(row) {
      this.reset()
      this.open = true
      this.title = '编辑危急值'
    },
    // 表单重置
    reset() {
      this.hffffs = []
      this.resetForm('queryExam')
      this.form = {
        gwrymc: '',
        hfbz: '',
        hfclfs: '',
        hfclr: '',
        hfclzt: '',
        hffffs: '',
        id: '',
        doctorapply: '',
        lxdh: '',
        nl: '',
        tirq: '',
        tjid: '',
        tjlx: '',
        wjzxj: '',
        xb: '',
        ywbz: '',
        ywclr: '',
      }
      this.OpendoctorData.bindValue = undefined
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
          saveHf(this.form).then((res) => {
            if (res.code == 200) {
              this.$modal.msgSuccess(res.msg)
              this.open = false
              this.$emit('getList')
            } else {
              this.$modal.msgWarning(res.msg)
            }
          })
        }
      })
    },
  },
}
</script>
