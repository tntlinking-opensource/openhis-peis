<!-- 每日记账报表统计  开发人：麦沃德科技半夏/予安 -->
<template>
  <div class="table-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="团体名称" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="姓名" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入拼音码" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" key="date" placeholder="选择日期"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleExport" v-hasPermi="['reception:chargeDaily:contact:export']" style="margin-bottom: 8px">导出Excel </el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column fixed label="记账人" prop="jzdwr" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column label="记账单位" prop="jzdw" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="体检者" prop="patientname" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column label="记账金额" prop="jzje" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="审批人" prop="spr" min-width="110" align="center" show-overflow-tooltip />
        <el-table-column label="收费方式" prop="idPayWay" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="记账已结" prop="moneyamountpaid" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="销售经理" prop="xs" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="日期" prop="createDate" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="note" min-width="220" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { getEveryDayJZDataList } from '@/api/reception/charge_daily.js'

import { getDate } from '@/utils/getDate.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        khdwmcid: undefined,
        inputCode: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 团体名称筛选参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '团体名称', //第二列标题
        url: '/abteilung/CrisisValue/getListData', //请求连接
        selectWidth: 200, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.queryParams.startTime = getDate().split(' ')[0]
    this.queryParams.endTime = getDate().split(' ')[0]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getEveryDayJZDataList(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 获取团体名称列表
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 导出
    handleExport() {
      // 创建一个新的参数对象，避免污染原始 queryParams
      const exportParams = { ...this.queryParams };

      // 如果 startTime 和 endTime 存在，则添加时分秒
      if (exportParams.startTime) {
        exportParams.startTime = exportParams.startTime + ' 00:00:00';
      }
      if (exportParams.endTime) {
        exportParams.endTime = exportParams.endTime + ' 23:59:59';
      }

      // 调用下载方法
      this.download('/reception/chargeQuery/exportEveryDay', exportParams, '每日记账报表统计.xlsx');
    },
  },
}
</script>
