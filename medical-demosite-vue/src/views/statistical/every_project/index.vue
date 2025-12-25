<!-- 每日体检项目统计  开发人：麦沃德科技 予安/清风/予安 -->
<template>
  <div class="app-container flex-direction-column statistics-items">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="startTime" label="登记日期">
        <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item prop="endTime" label="至">
        <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
      </el-form-item>
      <el-form-item label="体检科室" prop="ks">
        <el-select v-model="queryParams.ks" clearable filterable :filter-method="filterMethod" style="width: 160px">
          <el-option v-for="item in ksOptions" :key="item.id" :value="item.id" :label="item.departName"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收费项目" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入收费项目" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="已检/未检" prop="FExaminated">
        <el-select v-model="queryParams.FExaminated" clearable style="width: 160px">
          <el-option v-for="item in FExaminatedOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
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
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExportTotal" v-hasPermi="['statistical:everyProject:export']">导出统计</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExportInfo" :disabled="single" v-hasPermi="['statistical:everyProject:exportPeople']">导出人员清单</el-button>
      </el-col>
    </el-row>
    <drag-row left-size="60%" right-size="40%">
      <template #leftBox>
        <!-- 表格 -->
        <div class="table-box no-padding-table" v-loading="loading" style="height: 92%">
          <el-table border ref="tableList" v-if="!loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="handleRowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="登记日期" prop="every0" align="center" min-width="120" />
            <el-table-column label="星期" prop="every7" align="center" min-width="80" />
            <el-table-column label="科室" prop="every1" align="center" min-width="100" show-overflow-tooltip />
            <el-table-column label="检查项目" prop="every2" align="center" min-width="160" show-overflow-tooltip />
            <el-table-column label="医生" prop="every3" align="center" min-width="120" />
            <el-table-column label="体检者数量" prop="every4" align="center" min-width="120" />
            <el-table-column label="体检者数量（个人）" prop="every5" align="center" min-width="170" />
            <el-table-column label="体检者数量（团体）" prop="every6" align="center" min-width="170" />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </template>
      <template #rightBox>
        <!-- 表格 -->
        <div class="table-box no-padding-table" v-loading="rightLoading" style="height: 92%">
          <el-table border v-if="!rightLoading" :data="tableRightList" height="100%" stripe>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="开单医生" prop="doctorreg" align="center" min-width="120" />
            <el-table-column label="体检号" prop="patientcode" align="center" min-width="140" />
            <el-table-column label="体检者" prop="patientname" align="center" min-width="120" />
            <el-table-column label="性别" prop="idSex" align="center" min-width="60">
              <template slot-scope="scope">
                {{ ['男', '女'][scope.row.idSex] }}
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" align="center" min-width="60" />
            <el-table-column label="体检团体" prop="orgName" align="center" min-width="200" />
            <el-table-column label="体检日期" prop="dateregister" align="center" min-width="120" />
            <el-table-column label="科室" prop="dept" align="center" min-width="160" />
            <el-table-column label="外送机构" prop="wsjg" align="center" min-width="160" />
            <el-table-column label="收费项目" prop="feeitem" align="center" min-width="160" />
            <el-table-column label="登记" prop="fregistered" align="center" min-width="80">
              <template slot-scope="scope">
                {{ ['', '√'][scope.row.fregistered] }}
              </template>
            </el-table-column>
            <el-table-column label="收费" prop="ffeecharged" align="center" min-width="80">
              <template slot-scope="scope">
                {{ ['', '√'][scope.row.ffeecharged] }}
              </template>
            </el-table-column>
            <el-table-column label="已检" prop="fexaminated" align="center" min-width="80">
              <template slot-scope="scope">
                {{ ['', '√'][scope.row.fexaminated] }}
              </template>
            </el-table-column>
            <el-table-column label="审核时间" prop="auditTime" align="center" min-width="170" show />
            <el-table-column label="电话" prop="phone" align="center" min-width="120" />
            <el-table-column label="套餐" prop="examsuiteName" align="center" min-width="140" show-overflow-tooltip />
            <el-table-column label="体检类型" prop="idExamtype" align="center" min-width="140">
              <template slot-scope="scope">
                {{ ['健康', '职业', '综合', '复查'][scope.row.idExamtype] }}
              </template>
            </el-table-column>
            <el-table-column label="体检者类型" prop="idPatientclass" align="center" min-width="140" />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="ctotal > 0" :total="ctotal" :page.sync="queryRightParams.current" :limit.sync="queryRightParams.size" @pagination="getRightBasic" />
      </template>
    </drag-row>
  </div>
</template>
<script>
import dragRow from '@/components/DragRow'
import { page, analyseInfo, ksBasic } from '@/api/statistical/every_project.js'
import { filterMethod } from '@/utils/filterMethod.js'
export default {
  name: 'Every_project',
  components: { dragRow },
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        ks: undefined,
        name: undefined,
        FExaminated: undefined,
      },
      queryRightParams: {
        current: 1,
        size: 20,
      },
      //获取科室
      ksOptions: [],
      // 查询的科室列表
      filterList: [],
      //获取已检--未检
      FExaminatedOptions: [
        { id: '0', text: '未检' },
        { id: '1', text: '已检' },
      ],
      loading: true,
      //右表数据loading
      rightLoading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      //右表总条数
      ctotal: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      //右表查询
      tableRightList: [],
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    ksBasic().then(({ data }) => {
      this.ksOptions = data
      this.filterList = JSON.parse(JSON.stringify(data))
    })
    this.getList()
  },
  methods: {
    // 体检科室条件查询选择
    filterMethod(value) {
      this.ksOptions = this.filterList
      this.ksOptions = filterMethod(value, this.ksOptions, 'departName')
    },
    // 查询列表
    getList() {
      this.loading = true
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      page(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
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
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 导出统计
    handleExportTotal() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.download('/query/everyProject/export', this.queryParams, `每日体检项目统计表导出.xlsx`)
    },
    // 导出人员清单
    handleExportInfo() {
      let obj = {
        dateregister: this.rightBasic.dateregister,
        depId: this.rightBasic.depId,
        doctor: this.rightBasic.doctor,
        itemId: this.rightBasic.itemId,
      }
      this.download('/query/everyProject/exportInfo', obj, `每日体检项目统计表导出(人员清单).xlsx`)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length > 1) {
        this.$refs.tableList.clearSelection()
        this.$refs.tableList.toggleRowSelection(selection.pop())
      }
    },
    //点击事件
    handleRowClick(row) {
      this.$refs.tableList.toggleRowSelection(row, true)
      this.rightLoading = true
      let obj = {
        current: this.queryRightParams.current,
        dateregister: row.every0,
        depId: row.depId,
        doctor: row.every3,
        itemId: row.itemId,
        size: this.queryRightParams.size,
        FExaminated: this.queryParams.FExaminated,
      }
      this.rightBasic = obj
      analyseInfo(obj).then((res) => {
        this.ctotal = res.data.total
        this.tableRightList = res.data.records
        this.rightLoading = false
      })
    },
    getRightBasic() {
      this.rightLoading = true
      analyseInfo(this.rightBasic)
        .then((res) => {
          this.ctotal = res.data.total
          this.tableRightList = res.data.records
          this.rightLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.rightLoading = false
        })
    },
  },
}
</script>
<style lang="scss">
.statistics-items {
  .el-tabs__content {
    height: 100%;
  }
}
</style>
