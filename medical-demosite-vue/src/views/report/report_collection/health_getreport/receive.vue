<!-- 健康报告领取-领取 开发人：麦沃德科技暴雨 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-charge" width="792px" append-to-body :close-on-click-modal="false">
    <el-form ref="queryExamination" :model="queryExamination" :rules="rules" :inline="true" label-width="110px" hide-required-asterisk>
      <el-form-item label="领取人:" prop="getterName">
        <el-autocomplete v-model="queryExamination.getterName" placeholder="请输入领取人" clearable style="width: 610px" :fetch-suggestions="querySearch"></el-autocomplete>
      </el-form-item>
      <el-form-item label="领取时间:" prop="getTime">
        <el-date-picker v-model="queryExamination.getTime" type="datetime" placeholder="选择领取时间" style="width: 610px" value-format="yyyy-MM-ddTHH:mm:ss"> </el-date-picker>
      </el-form-item>
      <el-form-item label="领取人电话:" prop="getterPhone">
        <el-input v-model="queryExamination.getterPhone" placeholder="请输入领取人电话" clearable style="width: 610px" />
      </el-form-item>
      <el-form-item label="发放方式" prop="grantId">
        <el-select v-model="queryExamination.grantId" placeholder="请选择发放方式" clearable style="width: 610px">
          <el-option v-for="item in options" :key="item.id" :label="item.methodName" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="快递号:" prop="expressNum">
        <el-input v-model="queryExamination.expressNum" placeholder="请输入快递号" clearable style="width: 610px" />
      </el-form-item>
      <el-form-item label="快递公司" prop="expressId">
        <el-select v-model="queryExamination.expressId" placeholder="请选择快递公司" style="width: 610px">
          <el-option label="顺丰" :value="1" />
          <el-option label="EMS" :value="2" />
          <el-option label="圆通" :value="3" />
          <el-option label="申通" :value="4" />
          <el-option label="韵达" :value="5" />
          <el-option label="中通" :value="6" />
          <el-option label="其他" :value="7" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检号" prop="patientCode">
        <el-input ref="patientCodeInput" v-model="queryExamination.patientCode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleadd" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientName" label-width="60px">
        <el-input ref="patientNameInput" v-model="queryExamination.patientName" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleadd" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone" label-width="60px">
        <el-input ref="phoneInput" v-model="queryExamination.phone" placeholder="请输入手机号" clearable style="width: 160px" @keyup.enter.native="handleadd" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleadd" v-hasPermi="['report:reportCollection:healthGetreport:add']">添加 </el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-delete" @click="handleremove" :disabled="multiple" v-hasPermi="['report:reportCollection:healthGetreport:remove']">删除 </el-button>
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleSign" v-hasPermi="['report:reportCollection:healthGetreport:signature']">签名 </el-button>
      </el-form-item>
    </el-form>
    <el-table ref="examList" border :data="examList" stripe height="500" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" align="center" />
      <el-table-column label="序列" type="index" width="55" align="center" />
      <el-table-column label="体检号" prop="patientcode" align="center" width="110px" show-overflow-tooltip />
      <el-table-column label="姓名" prop="patientname" align="center" width="90px" show-overflow-tooltip />
      <el-table-column label="手机" prop="phone" min-width="100px" align="center" show-overflow-tooltip />
      <el-table-column label="开单医生" prop="doctorapply" align="center" show-overflow-tooltip />
      <el-table-column label="登记日期" prop="dateregister" align="center" show-overflow-tooltip />
      <el-table-column label="公司名称" prop="orgName" align="center" show-overflow-tooltip />
      <el-table-column label="订单号" prop="numorgresv" align="center" show-overflow-tooltip />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">领 取</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
    <signature ref="signature" @saveSignPath="saveSignPath"></signature>
  </el-dialog>
</template>
<script>
import { getBoxData, getReport, getSousData } from '@/api/report/report_collection/health_getreport'
import { uploadFile } from '@/api/funcdept/preregistration'
import signature from '@/views/funcdept/section_list/list/dialog/signature'
export default {
  components: { signature },
  props: [],
  data() {
    return {
      // 查询参数
      queryExamination: {
        ids: undefined,
        getterName: undefined,
        getTime: undefined,
        getterPhone: undefined,
        grantId: undefined,
        expressNum: undefined,
        expressId: undefined,
        patientCode: undefined,
        patientName: undefined,
        phone: undefined,
      },
      // 表单校验
      rules: {
        getterName: [{ required: true, message: '领取人不能为空', trigger: 'change' }],
        grantId: [{ required: true, message: '发放方式不能为空', trigger: 'change' }],
      },
      // 遮罩层
      loading: true,
      // 选中数组
      examItems: [],
      selectIds: [],
      examList: [],
      // 发送方式
      options: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表格多选
      multiple: true,
      // 签名返回的地址
      signPath: undefined,
      // 领取人缓存列表
      receiverName: [],
    }
  },
  methods: {
    // 获取领取人缓存
    querySearch(queryString, cb) {
      // 调用 callback 返回建议列表的数据
      let array = localStorage.getItem('receiverName')
      this.receiverName = array ? JSON.parse(array) : []
      cb(this.receiverName)
    },
    // 领取
    handleReceive(zhi = []) {
      this.reset()
      this.open = true
      this.title = '领取'
      this.getBoxData()
      this.examList = zhi
      if (zhi && zhi.length) {
        this.queryExamination.grantId = zhi[0].methodName
      }
    },
    // 领取按钮
    submitForm() {
      this.$refs['queryExamination'].validate((valid) => {
        if (valid) {
          let ids = this.examList.map((item) => item.id)
          this.queryExamination.ids = ids.join(',')
          getReport({
            ...this.queryExamination,
            signUrl: this.signPath,
          }).then(() => {
            let _index = this.receiverName.map((item) => item.value).indexOf(this.queryExamination.getterName)
            if (_index != -1) {
              this.$delete(this.receiverName, _index)
            }
            this.receiverName.unshift({ value: this.queryExamination.getterName })
            localStorage.setItem('receiverName', JSON.stringify(this.receiverName.slice(0, 10)))
            this.open = false
            this.$modal.msgSuccess('领取成功!')
            this.$emit('getList')
          })
        }
      })
    },
    //新增
    handleadd() {
      if (!this.queryExamination.patientCode && !this.queryExamination.patientName && !this.queryExamination.phone) {
        this.$alert('查询条件不能为空!', '提醒', {
          type: 'warning',
        })
      } else {
        this.loading = true
        getSousData({ ...this.queryExamination, tjlx: 0 })
          .then((response) => {
            let error = ''
            this.examList.forEach((el) => {
              response.data.forEach((val) => {
                if (el.patientcode == val.patientcode) {
                  error = '体检号 ' + val.patientcode + ' 已存在'
                }
              })
            })
            if (error) {
              this.$alert(error, '提醒', {
                type: 'warning',
              })
            } else {
              this.examList = [...response.data, ...this.examList]
              if (response.data && response.data.length && !this.queryExamination.grantId) {
                this.queryExamination.grantId = response.data[0].methodName
              }
            }
            this.queryExamination.patientCode = ''
            this.queryExamination.patientName = ''
            this.queryExamination.phone = ''
            this.$refs.patientCodeInput.focus()
            this.loading = false
          })
          .catch((error) => {
            console.error(error)
            this.loading = false
            this.queryExamination.patientCode = ''
            this.$refs.patientCodeInput.focus()
          })
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.examList.clearSelection()
      this.$refs.examList.toggleRowSelection(row)
    },
    //删除
    handleremove() {
      this.ids.forEach((val) => {
        this.examList.forEach((el, index) => {
          if (val == el.id) {
            this.$delete(this.examList, index)
          }
        })
      })
    },
    // 签名
    handleSign() {
      this.$refs.signature.signature()
    },
    // 返回签名图片地址
    saveSignPath(url) {
      var bytes = window.atob(url.split(',')[1])
      var array = []
      for (var i = 0; i < bytes.length; i++) {
        array.push(bytes.charCodeAt(i))
      }
      var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
      var formData = new FormData()
      formData.append('file', blob)
      this.loading = true
      uploadFile(formData)
        .then((res) => {
          this.signPath = res.data
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 表单重置
    reset() {
      this.examList = []
      this.examItems = []
      this.selectIds = []
      this.signPath = undefined
      this.queryExamination = {
        ids: undefined,
        getterName: undefined,
        getTime: undefined,
        getterPhone: undefined,
        grantId: undefined,
        expressNum: undefined,
        expressId: undefined,
        patientCode: undefined,
        patientName: undefined,
        phone: undefined,
      }
      this.resetForm('queryExamination')
      this.queryExamination.getTime = this.$getDate()
    },
    // 重置
    resetQuery() {
      this.reset()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    //获取发放方式
    getBoxData() {
      getBoxData().then((response) => {
        this.options = response.data
      })
    },
  },
}
</script>
