<!-- 登记信息查询 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column check-query-main">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <input ref="patientcode" v-model="queryParams.patientcode" placeholder="请输入体检号" clearable class="input-style form-item-width" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="名字" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入名字" clearable class="form-item-width" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable class="form-item-width" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno">
        <el-input v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable class="form-item-width2" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检团体" prop="idOrg">
        <input-select :selectData="idOrgData" @change="orgDataChange" :initialValue="queryParams.idOrg" :showTooltip="true"></input-select>
      </el-form-item>
      <el-form-item label="部门" prop="orgDepart">
        <el-input v-model="queryParams.orgDepart" placeholder="请输入部门" clearable class="form-item-width" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择体检类型" clearable class="form-item-width" @change="handleQuery">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
          <el-option label="复查" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检类别" prop="medicaltype">
        <el-select v-model="queryParams.medicaltype" placeholder="请选择体检类别" clearable class="form-item-width" @change="handleQuery">
          <el-option label="上岗前" :value="0" />
          <el-option label="在岗期间" :value="1" />
          <el-option label="离岗时" :value="2" />
          <el-option label="离岗后" :value="3" />
          <el-option label="应急" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="体检者类型" clearable class="form-item-width" @change="handleQuery">
          <el-option v-for="item in levelList" :key="item.levelId" :label="item.levelName" :value="item.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="登记日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" class="form-item-width" value-format="yyyy-MM-dd" type="date" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" class="form-item-width" value-format="yyyy-MM-dd" type="date" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="健康报告状态" prop="jkreportStatus">
        <el-select v-model="queryParams.jkreportStatus" placeholder="健康报告状态" clearable style="width: 170px" @change="handleQuery">
          <el-option :label="item.text" :value="item.id" v-for="item in reportStateList" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="职业报告状态" prop="zyreportStatus">
        <el-select v-model="queryParams.zyreportStatus" placeholder="职业报告状态" clearable style="width: 170px" @change="handleQuery">
          <el-option :label="item.text" :value="item.id" v-for="item in reportStateList" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否结案" prop="close">
        <el-select v-model="queryParams.close" placeholder="请选择是否结案" clearable class="form-item-width" @change="handleQuery">
          <el-option label="未结案" :value="0" />
          <el-option label="已结案" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否归档" prop="dbtype">
        <el-select v-model="queryParams.dbtype" placeholder="请选择是否归档" clearable class="form-item-width" @change="handleQuery">
          <el-option label="未归档" :value="0" />
          <el-option label="已归档" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否交单" prop="countreportcompare">
        <el-select v-model="queryParams.countreportcompare" placeholder="请选择是否交单" clearable class="form-item-width" @change="handleQuery">
          <el-option label="否" :value="0" />
          <el-option label="是" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="登记员" prop="idDoctorreg">
        <input-select :selectData="idDoctorregData" @change="doctorregDataChange" :initialValue="queryParams.idDoctorreg"></input-select>
      </el-form-item>
      <el-form-item label="登记员部门" prop="idDepart">
        <input-select :selectData="idDepartData" @change="departDataChange" :initialValue="queryParams.idDepart"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable class="form-item-width" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="销售经理" prop="idOpendoctor">
        <input-select :selectData="xsjlData" @change="xsjlDataChange" :notShowEmpty="true" :initialValue="queryParams.idOpendoctor"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single" @click="handleLook" v-hasPermi="['search:checkQuery:look']">查看 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleCreatePrivate" v-hasPermi="['search:checkQuery:createReport']">隐私报告生成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-search" :disabled="single || selection[0].orgDepartsubb != 1" @click="handleViewPrivate" v-hasPermi="['search:checkQuery:previewReport']">隐私报告预览</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleCreateReport" v-hasPermi="['search:checkQuery:createReport']">临时报告生成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-search" :disabled="single || selection[0].fpdfcreated != 1" @click="handlePreviewReport" v-hasPermi="['search:checkQuery:previewReport']">临时报告预览</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleCreateReportJy" v-hasPermi="['search:checkQuery:createReportJy']">检验报告生成 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-search" :disabled="single || selection[0].jyPdfcreated != 1" @click="handlePreviewReportJy" v-hasPermi="['search:checkQuery:previewReportJy']">检验报告预览 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-search" :disabled="multiple || forbidHandover" @click="handleHandover" v-hasPermi="['search:checkQuery:handover']">终审交接 </el-button>
      </el-col>
      <!-- 到检确认改为平安核销 -->
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handlePaConfirm">平安核销</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="multiple" @click="handleClose" v-hasPermi="['search:checkQuery:close']">结案 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleReMove" v-hasPermi="['search:checkQuery:reMove']">旧案召回 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:checkQuery:export']">导出Excel </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="multiple" @click="handleSend" v-hasPermi="['search:checkQuery:send']">批量申请 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-s-promotion" :disabled="multiple" @click="handleUrgent" v-hasPermi="['search:checkQuery:urgent']">加急 </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox" id="tableBox" style="height: 0">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="handleRegister">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" prop="rownum" width="55" align="center" />
        <el-table-column label="登记日期" prop="dateregister" min-width="140px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="单击跳转护理登记" placement="right">
              <span @click="toNurse(scope.row)">
                {{ scope.row.dateregister }}
              </span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="销售经理" prop="doctorapply" min-width="90px" align="center" show-overflow-tooltip />
        <el-table-column label="登记员" prop="doctorreg" min-width="90px" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="ddh" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" min-width="200px" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="110px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="90px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="60px" align="center">
          <template slot-scope="scope">
            <span>{{ ['男', '女'][scope.row.idSex] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="60px" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="出生日期" prop="birthdate" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.birthdate ? scope.row.birthdate.split(' ')[0] : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="身份证号" prop="idcardno" min-width="150px" align="center" show-overflow-tooltip />
        <el-table-column label="健康报告状态" prop="jk" min-width="120px" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.jk != -1 ? (reportStateList.length != 0 ? (reportStateList[scope.row.jk] ? reportStateList[scope.row.jk].text : '') : '') : '总检未开始' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="职业报告状态" prop="zy" min-width="120px" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.zy != -1 ? (reportStateList.length != 0 ? (reportStateList[scope.row.zy] ? reportStateList[scope.row.zy].text : '') : '') : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="隐私报告生成状态" prop="orgDepartsubb" min-width="140px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.orgDepartsubb == 1" style="color: green">已生成</span>
            <span v-else style="color: red">未生成</span>
          </template>
        </el-table-column>
        <el-table-column label="临时报告生成状态" prop="fpdfcreated" min-width="140px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.fpdfcreated == 1" style="color: green">已生成</span>
            <span v-else style="color: red">未生成</span>
          </template>
        </el-table-column>
        <el-table-column label="检验报告生成状态" prop="jyPdfcreated" min-width="140px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.jyPdfcreated == 1" style="color: green">已生成</span>
            <span v-else style="color: red">未生成</span>
          </template>
        </el-table-column>
        <el-table-column label="团体部门" prop="orgDepart" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="团体分组" prop="groupname" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="付款方式" prop="paywayName" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检者类型" prop="idPatientclass" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['', '普通会员', 'VIP', 'VVIP'][scope.row.idPatientclass] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="体检类型" prop="idExamtype" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['健康体检 ', '职业体检', '综合', '复查'][scope.row.idExamtype] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="体检类别" prop="medicaltype" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['上岗前 ', '在岗期间', '离岗时', '离岗后', '应急'][scope.row.medicaltype] || '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="体检套餐" prop="tcmc" min-width="200px" align="center" show-overflow-tooltip />
        <el-table-column label="是否替检" prop="countreportxml" min-width="100px" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.countreportxml == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="原体检者姓名" prop="tjr" min-width="140px" align="center" show-overflow-tooltip />
        <el-table-column label="结案" prop="close" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['未结案', '已结案'][scope.row.close] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="归档" prop="dbtype" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['未归档', '已归档'][scope.row.dbtype] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否交单" prop="countreportcompare" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['否', '是'][scope.row.countreportcompare] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="加急状态" prop="isjj" min-width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ ['', '加急'][scope.row.isjj] }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200, 500]" />
    <!-- 查看体检者页面 -->
    <check-patient ref="checkPatient"></check-patient>
  </div>
</template>
<script>
import { getListData, handoverApi, updateclose, reSaveHistory, urgentApi, createReport, createTemp } from '@/api/search/check_query.js'
import { confirmOrder } from '@/api/reception/register_list'
import { createPrivate } from '@/api/report/report_print/health_report'
import { middleDbInterface } from '@/api/reception/registration'
import { reportType } from '@/utils/dataList.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
import checkPatient from './checkPatient/index.vue'
export default {
  name: 'Check_query',
  components: { checkPatient },
  data() {
    return {
      // 日期选择参数
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        fRegistered: 1, //fRegistered 是否已登记 ，直接传1就行
        patientcode: undefined,
        inputCode: undefined,
        phone: undefined,
        idcardno: undefined,
        idOrg: undefined,
        orgDepart: undefined,
        idExamtype: undefined,
        medicaltype: undefined,
        idPatientclass: undefined,
        startTime: undefined,
        endTime: undefined,
        jkreportStatus: undefined,
        zyreportStatus: undefined,
        close: undefined,
        dbtype: undefined,
        countreportcompare: undefined,
        idDoctorreg: undefined,
        idDepart: undefined,
        ddh: undefined,
        doctorapply: undefined,
        branchIds: undefined,
      },
      // 总条数
      total: 0,
      // 体检团体参数
      idOrgData: {
        placeholder: '请输入输入码选择',
        value: '团体名称', //第二列标题
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: 'sell/customer/getAllOrg', //请求连接
        secondName: 'khdwmc',
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 登记员参数
      idDoctorregData: {
        placeholder: '请输入输入码选择',
        value: '名称', //第二列标题
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/report/healthGetReport/getAllUserData', //请求连接
        secondName: 'username',
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 登记部门参数
      idDepartData: {
        placeholder: '请输入输入码选择',
        value: '部门名称', //第二列标题
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/report/health/getAllDepartData', //请求连接
        secondName: 'departName',
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 销售经理参数
      xsjlData: {
        placeholder: '请输入输入码选择',
        value: '销售经理', //第二列标题
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/sell/sellDate/getXsryByCode', //请求连接
        firstName: 'userNo', //接口返回值对应第二列的参数名
        secondName: 'userName',
        queryData: 'code', //向接口传递的参数名(不传默认为'inputCode')
      },

      // 报告状态列表
      reportStateList: reportType(),
      // 表格展示数据
      tableList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      selection: [],
      idExamtype: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 是否禁止终审交接
      forbidHandover: false,
      // 体检者类型
      levelList: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  async created() {
    this.queryParams.branchIds = this.$getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
    //  当月开始时间戳
    const timeStamp = new Date().toLocaleDateString()
    //  上个月的天数
    const days = this.lastMonthDats()
    //  上月开始时间戳
    const lastMonthStart = new Date(timeStamp).getTime() - days * 24 * 3600 * 1000
    this.queryParams.startTime = this.$getDate(lastMonthStart).slice(0, 10) + ' 00:00:00'
    this.queryParams.endTime = this.$getDate().slice(0, 10) + ' 23:59:59'
    this.levelList = (await this.$getLevelList()).data
    this.getList()
  },
  methods: {
    // 获取上个月共几天
    lastMonthDats() {
      const date = new Date()
      const year = date.getFullYear()
      //  上个月月份
      let month = date.getMonth() + 1 - 1 //  0-11 表示 1月-12月
      //  0 表示12月
      month = month || 12
      //  30天的月份
      const arr30 = [4, 6, 9, 11]
      //  31天的月份
      const arr31 = [1, 3, 5, 7, 8, 10, 12]
      if (arr30.indexOf(month) !== -1) {
        //  上个月是 30 天
        return 30
      } else if (arr31.indexOf(month) !== -1) {
        //  上个月是 31 天
        return 31
      } else {
        //  2月
        if (this.isRunYear(year)) {
          return 29
        } else {
          return 28
        }
      }
    },
    isRunYear(year) {
      //  条件:能被4整除并且不能被100整除，或者被400整除的
      let flag = false
      if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
        flag = true
      }
      return flag
    },
    // 选择体检团体返回值
    orgDataChange(value) {
      this.queryParams.idOrg = value.id
      this.handleQuery()
    },
    // 选择登记员返回值
    doctorregDataChange(value) {
      this.queryParams.idDoctorreg = value.id
      this.handleQuery()
    },
    // 选择登记员部门返回值
    departDataChange(value) {
      this.queryParams.idDepart = value.id
      this.handleQuery()
    },
    // 销售经理返回值
    xsjlDataChange(value) {
      this.queryParams.idOpendoctor = value.userNo
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.tableList = JSON.parse(JSON.stringify(this.tableList))
        this.total = data.total
        this.loading = false
        if (this.queryParams.patientcode && data.total == 1) {
          this.queryParams.patientcode = data.records[0].patientcode
        }
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection[0]) {
        this.idExamtype = selection[0].idExamtype
      }
      this.selection = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length) {
        this.forbidHandover = false
        selection.forEach((el) => {
          if (el.fpdfcreated != 1) {
            this.forbidHandover = true
          }
        })
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    // 双击跳转登记登记
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
    // 跳转护理登记
    toNurse(row) {
      const obj = { path: '/funcdept/preregistration', name: 'Preregistration' }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name: obj.name, params: { patientcode: row.patientcode } })
      })
    },
    //查看
    handleLook() {
      this.$refs.checkPatient.showDialog(this.selection[0].patientcode)
    },
    //隐私报告生成
    handleCreatePrivate() {
      for (var i in this.selection) {
        var row = this.selection[i]
        if (row.containsPrivate != '是') {
          this.$modal.alertWarning('体检号' + row.patientcode + '没有隐私项目，不能生成隐私报告。', '提示')
          return
        }
      }
      const loading = this.$loading({
        lock: true,
        text: '报告生成中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      let patientcode = this.selection.map((item) => item.patientcode)
      createPrivate(patientcode)
        .then((response) => {
          loading.close()
          if (response.msg.indexOf('成功') != -1) {
            this.$modal.alertSuccess('隐私报告生成成功！', '提示')
            this.getList()
          } else {
            this.$modal.alertWarning(response.msg, '提示')
          }
        })
        .catch(() => {
          loading.close()
        })
    },
    //隐私报告预览
    handleViewPrivate() {
      var data = this.selection[0]
      //现在都用pdf了,没有pdf就去重新生成
      if (!data.ysPdf) {
        this.$alert('请先生成隐私报告', '提示')
        return
      }
      let url = this.$getCookie('imgPath') + data.ysPdf
      window.open(url, '_blank')
      return
    },
    //临时报告生成
    handleCreateReport() {
      this.$modal
        .confirm('确定生成临时报告?', '生成')
        .then(() => {
          let patientcode = this.selection.map((item) => item.patientcode)
          var data = {
            dh: 0,
            patientcode: patientcode.join(),
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
                this.$modal.alertSuccess('报告生成成功！', '提醒')
                this.getList()
              } else {
                this.$modal.alertWarning(response.msg, '提醒')
              }
            })
            .catch(() => {
              loading.close()
            })
        })
        .catch(() => {
          loading.close()
        })
    },
    //临时报告预览
    handlePreviewReport() {
      if (this.selection[0].lsPdf) {
        let url = this.$getCookie('imgPath') + this.selection[0].lsPdf
        window.open(url, '_blank')
        return
      }
      var query = {
        patientcode: this.selection[0].patientcode,
        idExamtype: this.selection[0].idExamtype,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualTemporaryReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
    //检验报告生成
    handleCreateReportJy() {
      this.$modal
        .confirm('确定生成报告?', '生成')
        .then(() => {
          let patientcode = this.selection.map((item) => item.patientcode)
          var data = {
            dh: this.idExamtype,
            isJy: 1,
            patientcode: patientcode,
          }
          const loading = this.$loading({
            lock: true,
            text: '报告生成中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          createReport(data)
            .then((response) => {
              loading.close()
              if (response.msg.indexOf('成功') != -1) {
                this.$modal.alertSuccess('报告生成成功！', '提醒')
                this.getList()
              } else {
                this.$modal.alertWarning(response.msg, '提醒')
              }
            })
            .catch(() => {
              loading.close()
            })
        })
        .catch(() => {
          loading.close()
        })
    },
    //检验报告预览
    handlePreviewReportJy() {
      if (this.selection[0].jyPdf) {
        let url = this.$getCookie('imgPath') + this.selection[0].jyPdf
        window.open(url, '_blank')
        return
      }
      var query = {
        patientcode: this.selection[0].patientcode,
        idExamtype: this.selection[0].idExamtype,
      }
      let routeUrl = this.$router.resolve({
        name: 'IndividualTemporaryReport',
        query: query,
      })
      window.open(routeUrl.href)
    },
    // 终审交接
    handleHandover() {
      this.$confirm('确定要终审交接吗?', '提示')
        .then(() => {
          let ids = this.selection.map((item) => item.id).join(',')
          this.loading = true
          handoverApi({ ids })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.getList()
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => { })
    },
    //到检确认
    handlePaConfirm() {
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
    //结案
    handleClose() {
      let error = ''
      if (this.selection.length == 1 && this.selection[0].close == 1) {
        error = '该记录已经归档！'
      }
      if (!error) {
        this.selection.forEach((el) => {
          if (!error && el.close == 1) {
            error = el.patientname + ' 该体检者已经结案！'
            return
          }
        })
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      let ids = this.selection.map((item) => item.id).join(',')
      this.loading = true
      updateclose({ ids })
        .then(() => {
          this.$modal.msgSuccess('结案成功')
          this.getList()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //旧案召回
    handleReMove() {
      let error = ''
      if (this.selection.length == 1 && this.selection[0].dbtype != 1) {
        error = '该记录没有归档！'
      }
      if (!error) {
        this.selection.forEach((el) => {
          if (!error && el.dbtype != 1) {
            error = el.patientname + ' 没有归档！'
            return
          }
        })
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$confirm('确定要旧案召回吗?', '提示')
        .then(() => {
          let ids = this.selection.map((item) => item.id).join(',')
          this.loading = true
          reSaveHistory({ ids })
            .then(() => {
              this.$modal.msgSuccess('旧案召回成功')
              this.getList()
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      this.download(
        'query/checkQuery/export',
        {
          ...this.queryParams,
        },
        `登记信息查询_${new Date().getTime()}.xlsx`
      )
    },
    //批量申请
    handleSend() {
      const msgId = this.$loading({
        lock: true,
        text: '正在提交中间库',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      let patientCode = this.selection.map((item) => item.patientcode).join(',')
      middleDbInterface({ patientcode: patientCode })
        .then((res) => {
          msgId.close()
          if (res.code != 200) {
            // this.$confirm('中间库获取数据异常', '提醒', {
            //   confirmButtonText: '调用评价器',
            //   cancelButtonText: '取消',
            //   type: 'warning',
            // })
            //   .then(() => {
            //     this.mPageCtr()
            //   })
            //   .catch(() => {
            //     this.mPageCtr()
            //   })
            this.$alert('中间库获取数据异常', '提醒').then(() => {
              this.getList()
            })
          } else {
            this.$modal.alertSuccess('数据重发成功', '提醒')
          }
        })
        .catch(() => {
          msgId.close()
        })
    },
    //加急
    handleUrgent() {
      this.$confirm('确定要加急吗?', '提示')
        .then(() => {
          let ids = this.selection.map((item) => item.id).join(',')
          this.loading = true
          urgentApi({ ids })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.getList()
              this.loading = false
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => { })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : ''
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : ''
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>
<style scoped>
.check-query-main /deep/ .el-form-item--small.el-form-item {
  margin-bottom: 8px;
}

.check-query-main .form-item-width {
  width: 160px;
}

.check-query-main .input-style {
  height: 32px;
}

.check-query-main .form-item-width2 {
  width: 180px;
}

.check-query-main /deep/ .el-table {
  /* min-height: 450px;
  max-height: 495px; */
  align-self: stretch;
}

.check-query-main /deep/ .el-form-item__label,
.check-query-main /deep/ .el-form-item__content,
.check-query-main /deep/ .el-input__inner,
.check-query-main /deep/ .el-select .el-input .el-select__caret,
.check-query-main /deep/ .el-input--small .el-input__icon {
  height: 32px;
  line-height: 32px;
  font-size: 13px;
}

.check-query-main /deep/ .el-button--mini {
  font-size: 12px;
  padding: 7px 15px;
}

.check-query-main /deep/ .el-checkbox__inner {
  width: 14px;
  height: 14px;
}

.check-query-main/deep/ .el-table .cell {
  line-height: 23px;
}
</style>
