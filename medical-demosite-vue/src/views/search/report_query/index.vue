<!-- 报告信息查询 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="收费日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 180px" multiple collapse-tags>
          <el-option v-for="options in fzxOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" style="width: 160px">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="报告状态" prop="examstate">
        <el-select v-model="queryParams.examstate" placeholder="请选择体检类型" clearable style="width: 160px">
          <el-option v-for="item in reportTypeList" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="拼音码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入姓名拼音码" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="团体名称" prop="idOrg">
        <input-select :selectData="departData" selectWidth="160" style="width: 160px" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="预警类型" prop="yjlx">
        <el-select v-model="queryParams.yjlx" placeholder="请选择预警类型" clearable style="width: 160px">
          <el-option label="报告交接预警" :value="0" />
          <el-option label="报告领取预警" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="handleModify" v-hasPermi="['search:reportQuery:modify']">修改发放方式 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:reportQuery:export']">导出Excel </el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="140px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" min-width="150px" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="numorgresv" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="80px" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" min-width="80px" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="tjlx" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ physicalType[scope.row.tjlx] ? physicalType[scope.row.tjlx].label : '' }}
          </template>
        </el-table-column>
        <el-table-column label="检查开始" prop="fexamStart" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.fexamStart == 1" style="color: green">是</span>
            <span v-if="scope.row.fexamStart == 0" style="color: orange">否</span>
          </template>
        </el-table-column>
        <el-table-column label="分检完成" prop="freadytofinal" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.freadytofinal == 1" style="color: green">是</span>
            <span v-if="scope.row.freadytofinal == 0" style="color: orange">否</span>
          </template>
        </el-table-column>
        <el-table-column label="总检锁定" prop="ffinallocked" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.ffinallocked == 1" style="color: green">是</span>
            <span v-if="scope.row.ffinallocked == 0" style="color: orange">否</span>
          </template>
        </el-table-column>
        <el-table-column label="是否打印" prop="isTotal" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.isTotal == 1" style="color: green">是</span>
            <span v-if="scope.row.isTotal == 0" style="color: orange">否</span>
          </template>
        </el-table-column>
        <el-table-column label="报告状态" prop="tjzt" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ reportTypeList[scope.row.tjzt] ? reportTypeList[scope.row.tjzt].text : '' }}
          </template>
        </el-table-column>
        <el-table-column label="总检医师" prop="doctorFinalName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="总检完成时间" prop="datefinalexamed" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="交接人" prop="joinPersonMan" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="承接人" prop="revPersonMan" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="交接时间" prop="revTime" min-width="160px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.revTime ? scope.row.revTime.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="通知时间" prop="notifyDate" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="领取人" prop="getterName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="领取时间" prop="getTime" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="领取人电话" prop="getterPhone" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="发放方式" prop="sendMethod" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="快递号" prop="expressNum" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="快递公司" prop="expressId" min-width="160px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ ['', ...expressTypeList][scope.row.expressId] ? ['', ...expressTypeList][scope.row.expressId].text : '' }}
          </template>
        </el-table-column>
        <el-table-column label="柜子号" prop="chestNum" min-width="100px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 弹窗 -->
    <add-items ref="addItems" @getList="getList"></add-items>
  </div>
</template>
<script>
import { getListData } from '@/api/search/report_query'
import addItems from './add'
import { physicalType, reportType, expressType } from '@/utils/dataList.js'
import { fzxApi } from '@/api/finance/inspect_accounts.js'
export default {
  name: 'Report_query',
  components: { addItems },
  props: [],
  data() {
    return {
      departData: {
        placeholder: '请输入输入码选择',
        value: '团体名称',
        url: '/query/inspecting/getOrgs',
        firstName: 'khdwmc',
        secondName: 'khdwmc',
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表格展示数据 
      tableList: [],
      // 遮罩层 
      loading: true,
      // 选中数组 
      selectData: [],
      // 总条数
      total: 0,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        date: undefined,
        date: undefined,
        tjlx: 0,
        examstate: undefined,
        patientname: undefined,
        inputCode: undefined,
        patientcode: undefined,
        idOrg: undefined,
        yjlx: undefined,
        numorgresv: undefined,
        branchIds: undefined,

      },
      // 体检类型
      physicalType: physicalType(),
      // 报告状态列表
      reportTypeList: reportType(),
      // 快递方式列表
      expressTypeList: expressType(),
      // 分中心列表
      fzxOptions: [],
    }
  },
  created() {
    this.getList()
    this.getFzxList()

  },
  methods: {
     // 获取分中心
     getFzxList() {
      fzxApi().then(({ data }) => {
        this.fzxOptions = data
        this.getList()
      })
    },
    // 团体名称查询
    selectChange1(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      }
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 修改发放方式
    handleModify() {
      let error = ''
      this.selectData.forEach((el) => {
        if (!error && el.status && Number(el.status >= 9)) {
          error = '体检号【' + el.patientcode + '】报告已交接，不能修改。'
        } else if (!error && !el.id) {
          error = '体检号【' + el.patientcode + '】未生成报告，不能修改。'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs.addItems.handleModify(this.selectData)
    },
    //  多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/query/reportQuery/export', this.queryParams, `报告信息查询.xlsx`)
    },
  },
}
</script>
