<!-- 外送项目查询 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="收费日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="外送机构" prop="center">
        <el-input v-model="queryParams.center" placeholder="请输入外送机构" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:totalSend:export']">导出Excel </el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" align="center" />
        <el-table-column label="外送时间" prop="send0" align="center" show-overflow-tooltip />
        <el-table-column label="返回时间" prop="send1" align="center" show-overflow-tooltip />
        <el-table-column label="外送机构" prop="send2" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目" prop="send3" align="center" show-overflow-tooltip />
        <el-table-column label="成本价" prop="costPrice" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="send4" align="center" show-overflow-tooltip />
        <el-table-column label="体检团体" prop="send5" align="center" show-overflow-tooltip />
        <el-table-column label="体检者" prop="send6" align="center" show-overflow-tooltip />
        <el-table-column label="位置" prop="send7" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="send8" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.send8 == 0">男</span>
            <span v-else-if="scope.row.send8 == 1">女</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="send9" align="center" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="send10" align="center" show-overflow-tooltip />
        <el-table-column label="预约" prop="send11" align="center" show-overflow-tooltip />
        <el-table-column label="已审" prop="send12" align="center" show-overflow-tooltip />
        <el-table-column label="审核时间" prop="send13" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/total_send'
export default {
  name: 'TotalSend',
  props: [],
  data() {
    return {
      // 表格展示数据
      tableList: [],
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        center: undefined,
      },
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.loading = true
      getListData(this.queryParams)
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('/query/chargeInfo/export', this.queryParams, `外送项目列表.xlsx`)
    },
  },
}
</script>
