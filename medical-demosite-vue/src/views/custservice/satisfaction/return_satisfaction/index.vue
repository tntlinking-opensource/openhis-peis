<!-- 满意度回访  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入输入码" clearable style="width: 220px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="personcode">
        <el-input v-model="queryParams.personcode" placeholder="请输入收费项目名称" clearable style="width: 220px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="评价结果" prop="appraiseResult">
        <el-select v-model="queryParams.appraiseResult" placeholder="请选择评价结果" clearable style="width: 220px">
          <el-option v-for="options in pjjgOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="回访结果" prop="visitResult">
        <el-select v-model="queryParams.visitResult" placeholder="请选择回访结果" clearable style="width: 230px">
          <el-option v-for="options in hfjgOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
        <!-- 日期选择器 -->
      </el-form-item>
      <el-form-item label="登记时间">
        <el-date-picker v-model="queryParams.date" style="width: 320px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary" @click="reset" plain>重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="single" icon="el-icon-help" @click="handleReturn" v-hasPermi="['custservice:satisfaction:returnSatisfaction:informationedit']">不满意客户回访</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="type" plain size="mini" icon="el-icon-help" @click="handleView" v-hasPermi="['custservice:satisfaction:returnSatisfaction:informationlookup']">查看非常满意</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-help" @click="handleExport" v-hasPermi="['custservice:satisfaction:returnSatisfaction:informationexport']">导出</el-button>
      </el-col>
    </el-row>
    <!-- 表格部分 -->
    <div class="table-box">
      <el-table border v-loading="loading" ref="tableList" @row-click="rowClick" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="评价结果" fixed prop="appraiseResult" min-width="200" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.appraiseResult == 0">未评价</span>
            <span v-else-if="scope.row.appraiseResult == 1">非常满意</span>
            <span v-else-if="scope.row.appraiseResult == 2">满意</span>
            <span v-else-if="scope.row.appraiseResult == 3">基本满意</span>
            <span v-else-if="scope.row.appraiseResult == 4">不满意</span>
            <span v-else>未评价</span>
          </template>
        </el-table-column>
        <el-table-column label="回访结果" fixed prop="visitResult" min-width="160" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.visitResult == 0">未评价</span>
            <span v-else-if="scope.row.visitResult == 1">非常满意</span>
            <span v-else-if="scope.row.visitResult == 2">满意</span>
            <span v-else-if="scope.row.visitResult == 3">基本满意</span>
            <span v-else-if="scope.row.visitResult == 4">不满意</span>
            <span v-else>未评价</span>
          </template>
        </el-table-column>
        <el-table-column label="体验号" prop="patientcode" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="90" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="60" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in xbOptions" :key="item.id">
              <span v-if="item.id == scope.row.idSex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="60" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="orgName" min-width="100" align="center" />
        <el-table-column label="部门" prop="orgDepart" min-width="100" align="center" />
        <el-table-column label="电话" prop="tel" min-width="100" align="center" />
        <el-table-column label="开单医生" prop="doctorapply" min-width="100" align="center" />
        <el-table-column label="评价时间" prop="commentsdate" min-width="100" align="center" />
        <el-table-column label="评价备注" prop="reviewnotes" min-width="100" align="center" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <add-view @getList="getList" ref="view"></add-view>
    <very-satisfaction @getList="getList" ref="verySatisfaction"></very-satisfaction>
  </div>
</template>

<script>
import { listReturnSatisfaction } from '@/api/custservice/satisfaction/return_satisfaction.js'
import addView from './view.vue'
import verySatisfaction from './very_satisfaction.vue'
export default {
  components: { addView, verySatisfaction },
  data() {
    return {
      total: 0,
      showSearch: true,
      queryParams: {
        startDate: undefined,
        endDate: undefined,
        current: 1,
        size: 10,
        personcode: '',
        appraiseResult: undefined,
        visitResult: undefined,
        patientname: '',
      },
      unSatisfactionQuery: {
        patientcode: undefined,
        id: undefined,
      },
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      patientCode: [],
      tableList: [],
      // 性别
      xbOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      hfjgOptions: [
        {
          id: 0,
          text: '未评价',
        },
        {
          id: 2,
          text: '满意',
        },
        {
          id: 3,
          text: '基本满意',
        },
        {
          id: 4,
          text: '不满意',
        },
      ],
      pjjgOptions: [
        {
          id: 0,
          text: '未评价',
        },
        {
          id: 2,
          text: '满意',
        },
        {
          id: 3,
          text: '基本满意',
        },
        {
          id: 4,
          text: '不满意',
        },
      ],
      loading: true,
    }
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.patientCode = selection.map((item) => item.patientcode)
      this.unSatisfactionQuery.patientcode = this.patientCode[0]
      this.ids = selection.map((item) => item.sid)

      this.unSatisfactionQuery.id = this.ids[0]
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    getList() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startDate = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endDate = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
      }
      listReturnSatisfaction(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    handleReturn() {
      this.$refs.view.getNewList(this.unSatisfactionQuery)
    },
    reset() {
      this.resetForm('queryForm')
      this.queryParams.patientname = ''
      this.queryParams.personcode = ''
      this.queryParams.appraiseResult = undefined
      this.queryParams.visitResult = undefined
      this.queryParams.date = undefined
      this.handleQuery()
    },
    handleView() {
      this.$refs.verySatisfaction.openAdd()
    },
    handleExport() {
      this.download(
        '/member/firstInterview/export',
        {
          ...this.queryParams,
        },
        `不满意客户回访.xlsx`
      )
    },
  },

  created() {
    this.getList()
  },
}
</script>

<style></style>
