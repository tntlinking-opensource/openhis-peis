<!-- 项目检况查询  开发人：麦沃德科技半夏 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 170px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 170px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记时间" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 170px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="公司" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入公司" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择" clearable style="width: 160px">
          <el-option v-for="options in typeOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="项目名称" prop="examfeeitemName">
        <el-input v-model="queryParams.examfeeitemName" placeholder="请输入项目名称" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检分类" prop="useCodeHidden">
        <el-select v-model="queryParams.useCodeHidden" placeholder="请选择" style="width: 160px" clearable>
          <el-option label="个检" value="0" />
          <el-option label="团检" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="examType">
        <el-select v-model="queryParams.examType" placeholder="请选择" style="width: 160px" clearable>
          <el-option label="健康体检" value="0" />
          <el-option label="职业体检" value="1" />
          <!-- <el-option label="综合" value="2" />
          <el-option label="复查" value="3" /> -->
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
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:itemsStatus:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检类型" prop="idExamtype" min-width="90" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.idExamtype == 0">健康体检</div>
            <div v-if="scope.row.idExamtype == 1">职业体检</div>
            <div v-if="scope.row.idExamtype == 2">综合</div>
            <div v-if="scope.row.idExamtype == 3">复查</div>
          </template>
        </el-table-column>
        <el-table-column label="登记日期" prop="dateregister" min-width="150" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.dateregister ? scope.row.dateregister.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ ['男', '女'][scope.row.idSex] }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="类型" prop="types" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="开单医生" prop="doctorapply" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="订单号" prop="ddh" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目" prop="examfeeitemName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="原价" prop="price" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="优惠价" prop="factprice" min-width="100" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/items_status'

export default {
  name: 'Items_status',
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
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientname: undefined,
        patientcode: undefined,
        date: undefined,
        phone: undefined,
        orgName: undefined,
        ddh: undefined,
        type: undefined,
        examfeeitemName: undefined,
        useCodeHidden: undefined,
        examType: undefined,
        regStartTime: undefined,
        regEndTime: undefined,
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
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.handleQuery()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.regStartTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.regEndTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.regStartTime = undefined
        this.queryParams.regEndTime = undefined
      }
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
    },

    // 导出
    handleExport() {
      this.download(
        '/query/itemsStatusQuery/export',
        {
          ...this.queryParams,
        },
        `项目检况查询.xlsx`
      )

    },
  },
}
</script>
<style lang="scss"></style>
