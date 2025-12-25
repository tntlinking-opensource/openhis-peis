<!-- 体检状态统计  开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="体检单位" prop="idOrg">
        <input-select :selectData="orgIdData" selectWidth="200" @change="orgIdDataChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['statistical:examinerStatus:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="登记日期" prop="date0" align="center" show-overflow-tooltip />
        <el-table-column label="星期" prop="date1" align="center" show-overflow-tooltip />
        <el-table-column label="几天前" prop="date2" align="center" show-overflow-tooltip />
        <el-table-column label="已登记" prop="date3" align="center" show-overflow-tooltip />
        <el-table-column label="已缴费" prop="date4" align="center" show-overflow-tooltip />
        <el-table-column label="已分检" prop="date5" align="center" show-overflow-tooltip />
        <el-table-column label="已总检" prop="date6" align="center" show-overflow-tooltip />
        <el-table-column label="已打印" prop="date7" align="center" show-overflow-tooltip />
        <el-table-column label="已领取" prop="date8" align="center" show-overflow-tooltip />
        <el-table-column label="已结案" prop="date9" align="center" show-overflow-tooltip />
        <el-table-column label="已归档" prop="orgDepart" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListdata } from '@/api/statistical/examiner_statuslist'

export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
        idOrg: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      orgIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
      // 体检类型
      typeOptions: [
        { id: '1', text: '弃检' },
        { id: '2', text: '补检' },
        { id: '3', text: '拒检' },
        { id: '4', text: '迟检' },
      ],
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListdata(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.orgIdData, 'bindValue')
      this.$set(this.orgIdData, 'bindValue', '')
      this.handleQuery()
    },
    //团检单位
    orgIdDataChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 导出
    handleExport() {
      this.download('/statistics/examinerStatus/export', this.queryParams, `体检状态统计.xlsx`)
    },
  },
}
</script>
