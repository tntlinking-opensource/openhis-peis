<!-- 发票管理 开发人：麦沃德科技清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :inline="true" size="mini" class="no-margin-bottom">
      <el-form-item label="单位名称" prop="orderId">
        <!-- <el-input style="width: 180px" v-model="queryParams.orderId" clearable placeholder="请输入单位名称"></el-input> -->
        <el-tooltip popper-class="tooltip-item" effect="dark" :content="selectedOrderName" :disabled="!selectedOrderName" placement="left">
          <input-select ref="orderId" :selectData="selectData" @change="selectChange"></input-select>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="分中心" prop="fzxid" style="margin-bottom: 8px">
        <el-select v-model="queryParams.fzxid" placeholder="请选择分中心" clearable style="width: 180px">
          <el-option v-for="options in fzxOptions" :key="options.id" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="发票抬头" prop="fptt">
        <el-input style="width: 180px" v-model="queryParams.fptt" clearable placeholder="请输入发票抬头" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="ssxsjl">
        <el-input style="width: 180px" v-model="queryParams.ssxsjl" clearable placeholder="请输入销售经理" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="订单号" prop="ssddh">
        <el-input style="width: 180px" v-model="queryParams.ssddh" clearable placeholder="请输入订单号" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select style="width: 180px" placeholder="请选择" v-model="queryParams.status" @change="handleQuery">
          <el-option v-for="item in searchStatusOptions" :key="item.id" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额" prop="receiptcorenumber">
        <el-input style="width: 180px" v-model="queryParams.receiptcorenumber" clearable placeholder="请输入金额" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['finance:controlOverInvoices:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handleEdit" v-hasPermi="['finance:controlOverInvoices:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['finance:controlOverInvoices:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-document-copy" :disabled="single" @click="handleCopy" v-hasPermi="['finance:controlOverInvoices:copy']">复制 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-check" :disabled="single" @click="handleExamine" v-hasPermi="['finance:controlOverInvoices:examine']">审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-close" :disabled="single" @click="handleUnExamine" v-hasPermi="['finance:controlOverInvoices:examineToFalse']">审核不通过 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-check" :disabled="single" @click="unhandleExamine" v-hasPermi="['finance:controlOverInvoices:unexamine']">反审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-receiving" :disabled="single" @click="handleIssueTickets" v-hasPermi="['finance:controlOverInvoices:ticketing']">出票 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-document" :disabled="single" @click="handleTicketsExchange" v-hasPermi="['finance:controlOverInvoices:changeTicketsApplication']">换票申请 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-refresh-left" :disabled="single" @click="handleTicketsReturn" v-hasPermi="['finance:controlOverInvoices:changeTicketsBack']">换票撤回 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-delete" :disabled="single" @click="handleTicketsAudit" v-hasPermi="['finance:controlOverInvoices:changeTicketsExamine']">换票审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-check" :disabled="single" @click="handleTicketsUnaudit" v-hasPermi="['finance:controlOverInvoices:changeTicketsUnexamine']">换票反审核 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-document-copy" :disabled="single" @click="handleTicketsConfirm" v-hasPermi="['finance:controlOverInvoices:changeTickets']">换票 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['finance:controlOverInvoices:export']">导出 </el-button>
      </el-col>
    </el-row>

    <div style="flex: 1">
      <el-table :data="tableList" ref="tableList" v-loading="loading" :border="true" :stripe="true" @row-click="rowClickHandle" @selection-change="handleSelectionChange" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" show-overflow-tooltip label="单位名称" min-width="200px" align="center"></el-table-column>
        <el-table-column prop="orderId" show-overflow-tooltip label="订单号" min-width="110" align="center"></el-table-column>
        <el-table-column prop="idReceipttypeName" show-overflow-tooltip label="发票类型" min-width="110" align="center"></el-table-column>
        <el-table-column prop="receiptsheetno" show-overflow-tooltip label="发票号" min-width="120px" align="center"></el-table-column>
        <el-table-column prop="fptt" show-overflow-tooltip label="发票抬头" min-width="120px" align="center"></el-table-column>
        <el-table-column prop="receiptcorenumber" show-overflow-tooltip label="发票面额" min-width="110px" align="center"></el-table-column>
        <el-table-column prop="applicationTime" show-overflow-tooltip label="发票申请时间" min-width="120px" align="center"></el-table-column>
        <el-table-column prop="proposer" show-overflow-tooltip label="申请人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="xsjl" show-overflow-tooltip label="销售经理" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="status" show-overflow-tooltip label="发票状态" min-width="100px" align="center">
          <template slot-scope="scope">
            <!-- <div v-for="(item, index) in statusOptions" :key="index">
              <span v-if="item.id == scope.row.status">{{ item.text }}</span>
            </div> -->
            <span v-if="scope.row.status == 0"><el-tag>未审核</el-tag></span>
            <span v-if="scope.row.status == 1"><el-tag type="success">已审核</el-tag></span>
            <span v-if="scope.row.status == 2"><el-tag type="success">已出票</el-tag></span>
            <span v-if="scope.row.status == 3"><el-tag>换票申请</el-tag></span>
            <span v-if="scope.row.status == 4"><el-tag type="success">换票已审核</el-tag></span>
            <span v-if="scope.row.status == 5"><el-tag type="success">已换票</el-tag></span>
            <span v-if="scope.row.status == 6"><el-tag type="danger">审核不通过</el-tag></span>
          </template>
        </el-table-column>
        <el-table-column prop="idRemitter" show-overflow-tooltip label="出票人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="remittime" show-overflow-tooltip label="出票时间" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="idReturner" show-overflow-tooltip label="换票人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="ttyy" show-overflow-tooltip label="换票原因" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="returntime" show-overflow-tooltip label="换票时间" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="note" show-overflow-tooltip label="备注" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="shName" show-overflow-tooltip label="审核人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="shTime" show-overflow-tooltip label="审核时间" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="unauditNote" show-overflow-tooltip label="反审核备注" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="unauditName" show-overflow-tooltip label="反审人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="unauditDate" show-overflow-tooltip label="反审时间" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="firstReceiptsheetno" show-overflow-tooltip label="原发票号" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="idReturnApplyer" show-overflow-tooltip label="换票申请人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="returnApplyTime" show-overflow-tooltip label="换票申请时间" min-width="120px" align="center"></el-table-column>
        <el-table-column prop="returnAuditer" show-overflow-tooltip label="换票审核人" min-width="100px" align="center"></el-table-column>
        <el-table-column prop="returnAuditTime" show-overflow-tooltip label="换票审核时间" min-width="120px" align="center"></el-table-column>
      </el-table>
    </div>
    <div style="display: flex; justify-content: space-between; align-items: center">
      <el-form :inline="true" label-width="100px" class="no-margin-bottom">
        <el-form-item label="选择日期">
          <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.valueDate" style="width: 500px" @change="handleBarData" key="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <!-- 分页 -->
      <pagination style="flex: 1" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200, 500]" />
    </div>
    <!-- 折线图 -->
    <div style="height: 200px" id="setCharts">
      <div class="charts-box">
        <div id="mychart" :style="myChartStyle"></div>
      </div>
    </div>
    <dialogItems ref="dialogItems" @getList="getList"></dialogItems>
    <dialogOtherItems ref="dialogOtherItems" @getList="getList"></dialogOtherItems>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import Cookies from 'js-cookie'
import dialogItems from './dialog.vue'
import dialogOtherItems from './dialog_other.vue'

//接口
import { page, getBarData, deleteTable, saveReturnCancle } from '@/api/finance/control_over_invoices'
import { getfzxApi } from '@/api/finance/safety_bill.js'

export default {
  components: { dialogItems, dialogOtherItems },
  data() {
    return {
      // 分中心
      fzxOptions: [],
      ids: [],
      single: true,
      multiple: true,
      loading: false,
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        orderId: '',
        fptt: '',
        ssxsjl: '',
        ssddh: '',
        status: '',
        fzxid: '',
        receiptcorenumber: '',
      },
      // 单位名称数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/abteilung/CrisisValue/getListData', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 已选单位名称
      selectedOrderName: '',
      //数据表格
      tableList: [],
      selection: {},

      //折线图
      myChartStyle: { width: '100%', height: '100%' },
      barOptions: ['申请未审', '已审未出', '换票未审'],
      showdata: [],
      //状态选项
      searchStatusOptions: [
        { id: '', text: '' },
        { id: '0', text: '未审核' },
        { id: '1', text: '已审核' },
        { id: '2', text: '已出票' },
        { id: '3', text: '换票申请' },
        { id: '4', text: '换票已审核' },
        { id: '5', text: '已换票' },
      ],
      statusOptions: [
        { id: '0', text: '未审核' },
        { id: '1', text: '已审核' },
        { id: '2', text: '已出票' },
        { id: '3', text: '换票申请' },
        { id: '4', text: '换票已审核' },
        { id: '5', text: '已换票' },
        { id: '6', text: '审核不通过' },
      ],
    }
  },
  mounted() {
    // this.initEcharts()
    this.getList()
  },
  created() {
    getfzxApi().then(({ data }) => {
      this.fzxOptions = data
    })
    // 默认选中当前分中心
    this.queryParams.fzxid = Cookies.get('cid')
  },
  methods: {
    // 单位名称返回选中的值
    selectChange(value) {
      this.queryParams.orderId = value.id
      this.selectedOrderName = value.name
      this.handleQuery()
    },
    //导出
    handleExport() {
      this.download('/finance/invoiceRequest/export', { ...this.queryParams }, `发票导出.xlsx`)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.$refs.orderId.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    //获取table数据 -- 根据搜索查询
    getList() {
      this.loading = true
      page(this.queryParams).then((res) => {
        this.tableList = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    //获取折线图数据
    handleBarData() {
      let startTime = ''
      let endTime = ''
      if (this.queryParams.valueDate) {
        startTime = this.queryParams.valueDate[0]
        endTime = this.queryParams.valueDate[1]
      }
      let obj = {
        startTime,
        endTime,
      }
      getBarData(obj).then((res) => {
        this.showdata = res.data
        // this.showdata = [1,2,0]//测试值
        this.initEcharts()
      })
    },
    //选中行
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      }
    },
    //单击行
    rowClickHandle(row, column) {
      if (column.className == 'el-table-column--selection') {
        this.$refs.tableList.toggleRowSelection(row, true)
      } else {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(row, true)
      }
    },
    //新增
    handleAdd() {
      this.$refs.dialogItems.handleAdd(0)
    },
    //编辑
    handleEdit() {
      if (this.selection.status == '1') {
        this.$alert('	该记录不是未审核状态！', '提示', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogItems.handleAdd(1, this.selection)
    },
    //复制
    handleCopy() {
      this.$refs.dialogItems.handleAdd(4, this.selection)
    },
    //审核
    handleExamine() {
      //是否审核 0--未审核 1--审核
      if (this.selection.status == '1') {
        this.$alert('	该记录不是未审核状态！', '提示', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogItems.handleAdd(2, this.selection)
    },
    //审核不通过
    handleUnExamine() {
      this.$refs.dialogItems.handleAdd(3, this.selection)
    },
    //反审核
    unhandleExamine() {
      //是否审核 0--未审核 1--审核
      if (this.selection.status != '1' && this.selection.status != '6') {
        this.$alert('非审核通过/不通过状态不可反审核！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogItems.handleAdd(5, this.selection)
    },
    //出票
    handleIssueTickets() {
      if (this.selection.status != '1') {
        this.$alert('	该记录不是已审核状态，无法进行出票操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogOtherItems.setBasicForm(0, this.selection)
    },
    //换票申请
    handleTicketsExchange() {
      if (this.selection.status != '2' && this.selection.status != '3') {
        this.$alert(' 该记录不是已出票或换票申请状态，无法进行换票申请操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogOtherItems.setBasicForm(1, this.selection)
    },
    //换票审核
    handleTicketsAudit() {
      if (this.selection.status != '3') {
        this.$alert(' 该记录不是换票申请状态，无法进行换票审核操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogOtherItems.setBasicForm(2, this.selection)
    },
    //换票反审核
    handleTicketsUnaudit() {
      if (this.selection.status != '4') {
        this.$alert(' 该记录不是换票已审核状态，无法进行换票反审核操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogOtherItems.setBasicForm(3, this.selection)
    },
    //换票
    handleTicketsConfirm() {
      if (this.selection.status != '4') {
        this.$alert(' 该记录不是换票已审核状态，无法进行换票反审核操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      this.$refs.dialogOtherItems.setBasicForm(4, this.selection)
    },
    //换票撤回
    handleTicketsReturn() {
      if (this.selection.status != '3') {
        this.$alert('	该记录不是换票申请状态，无法进行换票撤回操作！', '提醒', {
          type: 'warning',
        })
        return
      }
      let obj = { id: '' }
      this.ids.forEach((el) => {
        obj.id += el + ','
      })
      obj.id = obj.id.substring(0, obj.id.length - 1)
      saveReturnCancle(obj)
        .then((res) => {
          this.$modal.msgSuccess(res.msg)
          this.getList()
        })
        .catch(() => { })
        .finally(() => {
          this.getList()
        })
    },
    //删除
    handleDelete() {
      let obj = ''
      this.ids.forEach((el) => {
        obj += el + ','
      })
      obj = obj.substring(0, obj.length - 1)
      deleteTable(obj).then((res) => {
        this.$modal.msgSuccess(res.msg)
      })
    },
    //折线图js
    initEcharts() {
      // 基于准备好的dom，初始化echarts实例
      var barOptions = this.barOptions //x轴数据
      var showdata = this.showdata //y轴数据
      var myChart = echarts.init(document.getElementById('mychart'))
      // 指定图表的配置项和数据
      var option = {
        color: ['#41E4BB', '#1890FF'],
        tooltip: {
          trigger: 'axis',
        },
        //显示多个展示图图标
        toolbox: {
          show: true,
          feature: {
            magicType: { type: ['bar', 'line'] }, //设置点击图像切换展示类型数组
          },
        },
        xAxis: {
          type: 'category', //展示数据指向data
          boundaryGap: true, //是否留白
          data: barOptions,
        },
        yAxis: [
          {
            type: 'value', //展示数据指向series
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed', //设置为虚线
              },
            },
            axisLabel: {
              formatter: '{value}',
            },
            axisLine: { show: false },
            axisTick: { show: false },
          },
        ],
        series: [
          {
            type: 'bar',
            itemStyle: {
              barBorderRadius: [20, 20, 0, 0], // 圆角（左上、右上、右下、左下）
              color: new echarts.graphic.LinearGradient(
                0,
                1,
                0,
                0,
                ['#2FAEF2', '#1CD8A8'].map((color, offset) => ({ color, offset }))
              ), //0-1 渐变
            },
            data: showdata,
          },
        ],
      }
      myChart.setOption(option)
    },
  },
}
</script>

<style scoped>
.charts-box {
  width: 100%;
  height: 100%;
  border: 1px solid #c4c4c4;
  padding: 8px;
  display: inline-block;
}

/* #setCharts /deep/ canvas {
height: calc(100% - 30px) !important;
} */
</style>
