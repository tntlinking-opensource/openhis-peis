<!-- 登记列表  开发人：麦沃德科技半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号从" prop="ptcodeFrom">
        <el-input v-model="queryParams.ptcodeFrom" placeholder="请输入" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="到" prop="ptcodeTo">
        <el-input v-model="queryParams.ptcodeTo" placeholder="请输入" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="拼音码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入拼音码" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno">
        <el-input v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记人" prop="idDoctorreg">
        <el-select v-model="queryParams.idDoctorreg" filterable remote clearable :remote-method="remoteMethod" placeholder="请输入输入码搜索" :loading="remoteLoading" style="width: 180px">
          <el-option v-for="item in regOptions" :key="item.id" :label="item.occupationSummary" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="khdwmcid">
        <el-select v-model="queryParams.khdwmcid" filterable remote clearable :remote-method="groupMethod" placeholder="请输入输入码搜索" :loading="groupLoading" style="width: 200px">
          <el-option v-for="item in groupOptions" :key="item.id" :label="item.khdwmc" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="orgDepart">
        <el-input v-model="queryParams.orgDepart" placeholder="请输入部门" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="printType">
        <el-select v-model="queryParams.printType" placeholder="请选择" clearable style="width: 130px">
          <el-option label="全部" value="0" />
          <el-option label="条码" value="1" />
          <el-option label="导引单" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="打印" prop="pCount">
        <el-select v-model="queryParams.pCount" placeholder="请选择" clearable style="width: 130px">
          <el-option label="未打印" :value="0" />
          <el-option label="已打印" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="禁检" prop="paused">
        <el-select v-model="queryParams.paused" placeholder="请选择" clearable style="width: 130px">
          <el-option label="未禁检" :value="0" />
          <el-option label="已禁检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单结束状态" prop="orderFinished">
        <el-select v-model="queryParams.orderFinished" placeholder="请选择" clearable style="width: 130px">
          <el-option label="未结束" :value="0" />
          <el-option label="已结束" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="预约时间" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="请选择" clearable style="width: 130px">
          <el-option v-for="options in typeOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="开单医师" prop="idOpendoctor">
        <input-select :selectData="doctorData" selectWidth="130" @change="doctorChange"></input-select>
      </el-form-item>
      <el-form-item label="个检/团检" prop="useCodeHiden">
        <el-select v-model="queryParams.useCodeHiden" placeholder="请选择" clearable style="width: 130px">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否预收" prop="sfys">
        <el-select v-model="queryParams.sfys" placeholder="请选择" clearable style="width: 130px">
          <el-option label="否" :value="0" />
          <el-option label="是" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="selection.length != 0 && single" @click="handleRegister" v-hasPermi="['reception:registerList:register']">登记</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleLotReg" v-hasPermi="['reception:registerList:lotReg']">批量登记</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-view" :disabled="single" @click="handleView" v-hasPermi="['reception:registerList:view']">查看 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="handleDisable(1)" v-hasPermi="['reception:registerList:disable']">禁检 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleDisable(0)" v-hasPermi="['reception:registerList:disable']">反禁检 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-printer" :disabled="multiple" @click="handlePrint" v-hasPermi="['reception:registerList:print']">批量打印条码/导引单 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-plus" :disabled="multiple" @click="handleApply" v-hasPermi="['reception:registerList:apply']">批量申请 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleSetup" v-hasPermi="['reception:registerList:setup']">批量设置统收限额 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-plus" :disabled="single" @click="handleArrival">平安核销 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['reception:registerList:export']">导出Excel </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-message" :disabled="multiple" @click="handleNotification(1)" v-hasPermi="['reception:registerList:notification']">检前短信通知</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handleNotification(2)" v-hasPermi="['reception:registerList:notification']">取消短信通知 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleImportClient" v-hasPermi="['reception:registerList:importClient']">导入老系统未检客户</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" @row-click="rowClick" @row-dblclick="handleRegister" @selection-change="handleSelectionChange" :row-class-name="rowClassName">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" prop="rownum" align="center" />
        <el-table-column label="订单号" prop="orderNum" width="90" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.orderNum == -1 ? '-1' : scope.row.orderNum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="体检团体" prop="orgName" width="170" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="分组名称" prop="groupname" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" />
        <el-table-column label="预约时间" prop="medicaldate" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.medicaldate">{{ scope.row.medicaldate.split(' ')[0] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="禁检" prop="ispaused" min-width="80" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.ispaused == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="开单医师" prop="doctorapply" min-width="90" align="center" />
        <el-table-column label="是否预收" prop="sfys" min-width="100" align="center">
          <template slot-scope="scope">
            {{ ['', '是'][scope.row.sfys] }}
          </template>
        </el-table-column>
        <el-table-column label="预收金额" prop="ysje" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sfys == 1">{{ scope.row.ysje }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="预收方式" prop="payway" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sfys == 1">{{ scope.row.payway }}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="短信通知" prop="yjdx" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.yjdx == 1">已发送</el-tag>
            <el-tag type="danger" v-else>未发送</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="身份证号" prop="idcardno" min-width="180" align="center" />
        <el-table-column label="手机号" prop="phone" min-width="120" align="center" />
        <el-table-column label="性别" prop="idSex" min-width="60" align="center">
          <template slot-scope="scope">
            <div v-for="item in sexOptions" :key="item.id">
              <span v-if="item.id == scope.row.idSex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="60" align="center" />
        <el-table-column label="出生日期" prop="birthdate" min-width="110" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.birthdate">{{ scope.row.birthdate.split(' ')[0] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="体检者类型" prop="idPatientClass" min-width="120" align="center" />
        <el-table-column label="体检类型" prop="idExamtype" min-width="100" align="center">
          <template slot-scope="scope">
            <div v-for="item in examOptions" :key="item.id">
              <span v-if="item.id == scope.row.idExamtype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="体检套餐" prop="tjtcmc" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="登记人" prop="registerR" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column label="打印次数" align="center">
          <el-table-column label="条码" prop="tmyd" min-width="80" align="center" />
          <el-table-column label="导引单" prop="guideSignleCount" min-width="80" align="center" />
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="130" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #3cd495" @click="handleRegister(scope.row)" v-hasPermi="['reception:registerList:register']">登记</el-button>
            <el-button size="mini" type="text" style="color: #1890ff" @click="handleView(scope.row)" v-hasPermi="['reception:registerList:view']">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200, 500]" />
    <batch-items ref="batchItems" @getList="getList"></batch-items>
    <limit-receipt ref="limitReceipt"></limit-receipt>
    <barcode-guidance ref="barcodeGuidance"></barcode-guidance>
    <notification ref="notification"></notification>
    <!-- 导入老系统未检客户对话框 -->
    <el-dialog title="导入未检客户" :visible.sync="clientOpen" width="500px" append-to-body @close="cancelImport">
      <el-form v-loading="clientLoading" @submit.native.prevent>
        <el-form-item label="体检号">
          <el-input ref="clientInput" v-model="clientNum" type="text" autosize placeholder="请填写体检号,英文逗号分隔" @keyup.enter.native="confirmImport"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmImport">确 定</el-button>
        <el-button @click="cancelImport">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getListData, getGroupList, saveBatchRegister, getBdRegisterData, savePausedStatus, cancelSmsPredetection, importPeispatient, confirmOrder } from '@/api/reception/register_list'
import { getLqrData, middleDbInterface } from '@/api/reception/registration'

import batchItems from './batch'
import limitReceipt from './limit'
import notification from './notification'
import barcodeGuidance from './barcode_guidance' //批量打印条码或导引单

export default {
  name: 'Register_list',
  components: { batchItems, limitReceipt, barcodeGuidance, notification },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      selection: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        ptcodeFrom: undefined,
        ptcodeTo: undefined,
        inputCode: undefined,
        patientname: undefined,
        idcardno: undefined,
        phone: undefined,
        idDoctorreg: undefined,
        numorgresv: undefined,
        khdwmcid: undefined,
        orgDepart: undefined,
        printType: '0',
        pCount: undefined,
        paused: 0,
        orderFinished: 0,
        date: undefined,
        idPatientclass: undefined,
        idOpendoctor: undefined,
        useCodeHiden: undefined,
        sfys: undefined,
      },
      // 开单医师
      doctorData: {
        placeholder: '请选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        third: '部门', //第三列标题（没有传空）
        fourth: '体检中心', //第四列标题（没有传空）
        url: '/member/previewingTrack/getKdys', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
        thirdName: 'dname', //接口返回值对应第三列的参数名
        fourthName: 'cname', //接口返回值对应第四列的参数名
        queryData: 'key',
      },
      // 登记人加载
      remoteLoading: false,
      // 登记人列表
      regOptions: [],
      // 体检者类型
      typeOptions: [],
      // 性别
      sexOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      // 体检类型
      examOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
      // 输入码搜索
      nameOptions: [],
      groupOptions: [],
      groupLoading: false,
      // 排检表格数据
      tableList: [],
      // 导入未检用户对话框
      clientOpen: false,
      // 加载中
      clientLoading: false,
      // 体检号
      clientNum: '',
      // 请求版本号，用于防止旧请求覆盖新请求的数据
      requestVersion: 0,
    }
  },
  async created() {
    this.typeOptions = (await this.$getLevelList()).data
    this.getList()
  },
  mounted() {
    this.keyDown()
  },
  methods: {
    // 页面快捷键监听
    keyDown() {
      document.onkeydown = (e) => {
        // 兼容FF和IE和Opera
        var event = e || window.event
        var code = event.keyCode || event.which || event.charCode
        if (code == 13 && this.$refs.barcodeGuidance.open) {
          this.$refs.barcodeGuidance.submitForm(1)
        }
      }
    },
    // 批量打印
    handlePrint() {
      this.$refs.barcodeGuidance.openBarcodeGuidance(this.selection)
    },
    // 查询列表
    getList() {
      this.loading = true
      const currentVersion = ++this.requestVersion
      getListData(this.queryParams)
        .then((response) => {
          if (currentVersion === this.requestVersion) {
            this.tableList = response.data.records
            this.total = response.data.total
            this.loading = false
          }
        })
        .catch((err) => {
          if (currentVersion === this.requestVersion) {
            console.error(err)
            this.loading = false
          }
        })
    },
    //远程选框的方法
    remoteMethod(keyword) {
      const query = {
        current: 1,
        size: 99999,
        srm: keyword,
      }
      if (keyword !== '') {
        this.remoteLoading = true
        getLqrData(query).then((res) => {
          this.remoteLoading = false
          this.regOptions = res.data.records
        })
      } else {
        this.regOptions = []
      }
    },
    // 输入码搜索列表
    groupMethod(keyword) {
      const query = {
        key: keyword,
      }
      if (keyword !== '') {
        this.groupLoading = true
        getGroupList(query).then((res) => {
          this.groupLoading = false
          this.groupOptions = res.data
        })
      } else {
        this.groupOptions = []
      }
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.doctorData.bindValue = ''
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selection = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && (col.label == '操作' || col.type == 'selection')) {
        return
      }
      var isSelect = false
      this.selection.forEach((el) => {
        if (el.id == row.id) {
          isSelect = true
        }
      })
      if (isSelect) return
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
      this.selection = [row]
    },
    // 更换表格颜色
    rowClassName({ row }) {
      if (!row.birthdate) {
        return ''
      }
      var date = new Date()
      var myDate = new Date(row.birthdate)
      var month = date.getMonth()
      var day = date.getDate()
      var myMonth = myDate.getMonth()
      var myDay = myDate.getDate()
      if (month == myMonth && day == myDay) {
        return 'today_birth'
      }
      return ''
    },
    // 开单医师改变
    doctorChange(value) {
      this.doctorData.bindValue = value.id
      this.queryParams.idOpendoctor = value.id
    },
    // 登记
    handleRegister(selection) {
      var row = selection.id ? selection : this.selection[0]
      if (row) {
        // 禁检不可以登记
        if (row.ispaused == 1) {
          this.$modal.alertWarning('该体检者已经被禁检，不可以登记！', '提示')
          return
        }
        this.registerTab(row.patientcode, row.id)
      } else {
        // 个检
        this.registerTab()
      }
    },
    // 跳转登记
    registerTab(patientcode, id) {
      const obj = { path: '/reception/registration', name: 'Registration' }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name: obj.name, params: { patientcode: patientcode, id: id } })
      })
    },
    // 批量登记
    handleLotReg() {
      var rows = this.selection
      if (rows.length > 0) {
        this.$modal
          .confirm('确定批量登记选中客户?', '批量登记', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
          .then(() => {
            var ids
            var len = rows.length
            if (rows[0].id != undefined) {
              ids = rows[0].id
            }
            if (rows[0].patientcode == '' || rows[0].patientcode == null) {
              this.$modal.msgWarning('批量登记失败：第 1 条记录不存在体检号！')
              return
            }
            for (var i = 1; i < len; i++) {
              if (rows[i].patientcode == '' || rows[i].patientcode == null) {
                this.$modal.msgWarning('批量登记失败：第 ' + i + ' 条记录不存在体检号！')
                return
              }
              if (rows[i].id != undefined) {
                ids = ids + ',' + rows[i].id
              }
            }
            saveBatchRegister({ ids }).then(() => {
              this.getList()
              this.$modal.msgSuccess('批量登记成功')
            })
          })
          .catch(() => { })
      } else {
        this.$refs.batchItems.handleShow()
      }
    },
    // 查看
    handleView(selection) {
      var row = selection.id ? selection : this.selection[0]
      if (row) {
        // 禁检不可以登记
        if (row.ispaused == 1) {
          this.$modal.alertWarning('该体检者已经被禁检，不可以查看！', '提示')
          return
        }
        getBdRegisterData({ id: row.id }).then((res) => {
          var text = res.data
          if (text == 'success') {
            var patientcode = row.patientcode == null ? '-1' : row.patientcode
            const obj = { path: '/reception/register_view', name: 'RegisterView' }
            this.$tab.closePage(obj).then(() => {
              this.$router.push({ name: obj.name, params: { patientcode: patientcode, id: row.id } })
            })
          } else if (text == '-1') {
            this.$modal.alertWarning('该体检者已经登记！', '提示')
          } else if (text == '-2') {
            this.$modal.alertWarning('该体检者信息不存在！', '提示')
          } else if (text == '-3') {
            this.$modal.alertWarning('系统发生异常，请联系管理员！', '提示')
          }
        })
      } else {
        this.$modal.alertWarning('请选择一条记录!', '提示')
      }
    },
    // 禁检/反禁检 1禁检 0反禁检
    handleDisable(paused) {
      this.$modal
        .confirm(paused == 1 ? '确定要禁检吗?' : '确定要反禁检吗?', '提醒')
        .then(() => {
          var data = {
            ids: this.selection.map((item) => item.id).join(','),
            paused: paused,
          }
          return savePausedStatus(data)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('操作成功')
        })
        .catch(() => { })
    },

    // 批量申请
    handleApply() {
      var rows = this.selection
      if (rows.length == 0) {
        this.$modal.alertWarning('请选择体检者！')
        return
      }
      var arr = []
      for (var i = 0; i < rows.length; i++) {
        if (rows[i].patientcode) {
          arr.push(rows[i].patientcode)
        }
      }
      if (arr.length == 0) {
        this.$modal.alertWarning('请选择有体检号的体检者！')
        return
      }
      const msgId = this.$loading({
        lock: true,
        text: '正在提交中间库',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      middleDbInterface({ patientcode: arr.join(',') })
        .then((res) => {
          msgId.close()
          if (res.code != 200) {
            this.$modal.alertWarning('中间库获取数据异常', '提醒')
          } else {
            this.$modal.alertSuccess('数据重发成功', '提醒')
          }
        })
        .catch(() => {
          msgId.close()
        })
    },
    // 批量设置统收限额
    handleSetup(row) {
      var id = row.id || this.selection[0].id
      this.$refs.limitReceipt.handleShow(id)
    },
    // 到检确认
    handleArrival() {
      if (this.selection[0].countreportoccupation != 3 && this.selection[0].countreportoccupation != 1) {
        this.$alert('当前体检者不是平安好医生用户,请确认后重试', '提示')
        return
      }
      if (this.selection[0].foutpatient == 1) {
        this.$alert('所选记录已确认', '提示')
        return
      }
      this.$prompt('请输入平安好医生验证码', '提示')
        .then(({ value }) => {
          if (!value) {
            this.$modal.msgWarning('验证码不能为空')
            return
          }
          confirmOrder({
            id: this.selection[0].id,
            captcha: value
          }).then(() => {
            this.$modal.msgSuccess('到检确认成功')
          })
        })
        .catch(() => { })
    },
    // 导出Excel
    handleExport() {
      var searchData = this.queryParams
      this.download(
        '/reception/register/export',
        {
          ...searchData,
        },
        `登记列表_${new Date().getTime()}.xlsx`
      )
    },
    // 检前短信通知、取消放松短信
    handleNotification(type) {
      let error = ''
      this.selection.forEach((el) => {
        if (!error && !el.phone) {
          error = `体检号 ${el.patientcode} 未绑定手机号`
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      let array = this.selection.map((item) => item.patientcode)
      if (type == 1) {
        this.$refs.notification.handleShow(array)
      } else {
        this.$confirm('确定要取消发送短信吗?', '提示')
          .then(() => {
            cancelSmsPredetection({ patientcodes: array.join(',') }).then(() => {
              this.$modal.msgSuccess('取消发送成功')
            })
          })
          .catch(() => { })
      }
    },
    // 导入老系统未检客户
    handleImportClient() {
      this.clientOpen = true
      this.$nextTick(() => {
        this.$refs.clientInput.focus()
      })
    },
    // 确认导入
    confirmImport() {
      if (!this.clientNum) {
        this.$alert('请输入体检号', '提示')
        this.$refs.clientInput.focus()
        return
      }
      this.clientLoading = true
      importPeispatient({ patientcodes: this.clientNum.split(',') })
        .then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess('数据导入中,请稍后刷新查看')
            this.cancelImport()
            this.getList()
          } else {
            this.$alert(res.msg, '提示')
          }
          this.clientLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.clientLoading = false
        })
    },
    // 取消导入
    cancelImport() {
      this.clientNum = undefined
      this.clientOpen = false
    },
  },
}
</script>
<style lang="scss">
.today_birth {
  background: #fffacd !important;
}
</style>
