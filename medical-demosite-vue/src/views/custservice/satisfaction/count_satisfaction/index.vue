<!-- 满意度统计  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="公司" prop="orgName">
        <el-input v-model="queryParams.orgName" placeholder="请输入公司名称" clearable style="width: 190px" @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="部门" prop="orgDepart">
        <el-input v-model="queryParams.orgDepart" placeholder="请输入部门名称" clearable style="width: 190px" @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="开单医生" prop="doctorapply">
        <el-input v-model="queryParams.doctorapply" placeholder="请输入开单医生" clearable style="width: 190px" @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="统计日期从">
        <el-date-picker style="width: 130px; margin-left: 10px" v-model="queryParams.startDate" size="mini" type="date" placeholder="选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item label="到">
        <el-date-picker style="width: 130px; margin-left: 10px" v-model="queryParams.endDate" type="date" size="mini" placeholder="选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['custservice:satisfaction:countSatisfaction:informationexport']">导出</el-button>
        </el-col>
      </el-row>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="统计时间(从)" prop="startdate" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="统计时间(到)" prop="endate" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="公司" prop="company" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="部门" prop="department" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="开单医生" prop="doctor" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="统计结果" align="center">
          <el-table-column label="统计类型" prop="type" min-width="160" align="center" show-overflow-tooltip />
          <el-table-column label="非常满意" prop="fcmy" min-width="100" align="center" />
          <el-table-column label="满意" prop="my" min-width="100" align="center" />
          <el-table-column label="基本满意" prop="jbmy" min-width="100" align="center" />
          <el-table-column label="不满意" prop="bmy" min-width="100" align="center" />
          <el-table-column label="未评价" prop="wpj" min-width="100" align="center" />
          <el-table-column label="满意度(%)" prop="myd" min-width="100" align="center" />
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { countList } from '@/api/custservice/satisfaction/count_satisfaction.js'
export default {
  data() {
    return {
      loading: false,
      total: 0,
      showSearch: true,
      queryParams: {
        current: 1,
        size: 10,
        orgName: undefined,
        doctorapply: '',
        orgDepart: undefined,
        idDepart: undefined,
        idLabtype: undefined,
        //起始时间
        startDate: undefined,
        //截止时间
        endDate: undefined,
      },
      tableList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      countList(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    ///重置
    resetQuery() {
      this.queryParams.date = undefined
      this.queryParams.startDate = undefined
      this.queryParams.endDate = undefined
      this.queryParams.orgName = undefined
      this.queryParams.orgDepart = undefined
      this.queryParams.doctorapply = undefined
      this.queryParams.idDepart = undefined
      this.queryParams.idLabtype = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 添加
    handleExport() {
      // this.download(
      //   '/satisfaction/export',
      //   {
      //     ...this.queryParams,
      //   },
      //   `科室满意度回访.xlsx`
      // )
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>

<style scoped></style>
