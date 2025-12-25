<!-- 未检项目查询  开发人：麦沃德科技半夏 / 暴雨-->
<template>
  <div class="app-container flex-direction-column not-status" :style="total > 0 || itemTotal > 0 ? 'padding-bottom: 0;' : 'padding-bottom: 12px;'">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登记时间" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="leftTable" border v-loading="loading" :data="tableList" height="100%" stripe @row-click="rowClick" @selection-change="handleSelectionChange" style="width: calc(100% - 460px); display: inline-block">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
        <el-table-column label="登记日期" prop="dateregister" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="tjlx" min-width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.tjlx == 0">健康体检</div>
            <div v-if="scope.row.tjlx == 1">职业体检</div>
            <div v-if="scope.row.tjlx == 2">综合</div>
            <div v-if="scope.row.tjlx == 3">复查</div>
          </template>
        </el-table-column>
        <el-table-column label="体检状态" prop="tjzt" min-width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.tjzt == 0">总检开始</div>
            <div v-if="scope.row.tjzt == 1">总检完成</div>
            <div v-if="scope.row.tjzt == 2">报告已打印</div>
            <div v-if="scope.row.tjzt == 3">一审通过</div>
            <div v-if="scope.row.tjzt == 4">一审不通过</div>
            <div v-if="scope.row.tjzt == 5">二审通过</div>
            <div v-if="scope.row.tjzt == 6">二审不通过</div>
            <div v-if="scope.row.tjzt == 7">终审通过</div>
            <div v-if="scope.row.tjzt == 8">终审不通过</div>
            <div v-if="scope.row.tjzt == 9">报告已交接</div>
            <div v-if="scope.row.tjzt == 10">报告已通知</div>
            <div v-if="scope.row.tjzt == 11">报告已领取</div>
            <div v-if="scope.row.tjzt == 12">分检完成</div>
            <div v-if="scope.row.tjzt == 13">分检未完成</div>
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="sex" min-width="50" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.sex == 0">男</div>
            <div v-if="scope.row.sex == 1">女</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="50" align="center" show-overflow-tooltip />
        <el-table-column label="工作单位" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="总检完成" align="center">
          <el-table-column label="健康完成" prop="jkwc" min-width="100" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.jkwc == 0">未完成</div>
              <div v-if="scope.row.jkwc == 1">已完成</div>
            </template>
          </el-table-column>
          <el-table-column label="职业完成" prop="zywc" min-width="100" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.zywc == 0">未完成</div>
              <div v-if="scope.row.zywc == 1">已完成</div>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="报告打印" align="center">
          <el-table-column label="健康已打" prop="jkyd" min-width="100" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.jkyd == 0">未打印</div>
              <div v-if="scope.row.jkyd == 1">已打印</div>
            </template>
          </el-table-column>
          <el-table-column label="职业已打" prop="zyyd" min-width="100" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.zyyd == 0">未打印</div>
              <div v-if="scope.row.zyyd == 1">已打印</div>
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
      <el-table border v-loading="loadingR" :data="itemList" height="100%" stripe style="width: 460px; display: inline-block">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="未完成项目" prop="idExamfeeitem" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="flex-pagination" v-if="total > 0 || itemTotal > 0">
      <div class="pagination-left">
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </div>
      <div class="pagination-right">
        <pagination v-show="itemTotal > 0" :total="itemTotal" :page.sync="queryItem.current" :limit.sync="queryItem.size" @pagination="getList" layout="sizes, prev, pager, next" :pagerCount="5" />
      </div>
    </div>
  </div>
</template>
<script>
import { getListData, getRListData } from '@/api/search/not_status'

export default {
  name: 'Not_status',
  data() {
    return {
      loading1: true,
      // 遮罩层
      loading: true,
      itemLoading: false,
      loadingR: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      itemTotal: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientname: undefined,
        patientcode: undefined,
        date: undefined,
      },
      queryItem: {
        current: 1,
        size: 20,
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
      itemList: [],
    }
  },
  created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.handleQuery()
  },
  methods: {
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    // 左侧表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.ids = undefined
      } else if (selection.length == 1) {
        this.patientcode = selection[0].patientcode

        this.handleRightTable()
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 获取右侧表格数据
    handleRightTable() {
      this.loadingR = true
      let queryParamss = {
        patientcode: this.patientcode,
      }
      getRListData(queryParamss).then(({ data }) => {
        this.itemList = data.records
        this.itemTotal = data.total
        this.loadingR = false
      })
    },
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.itemList = []
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      ;(this.queryParams.data = undefined), (this.queryParams.startTime = undefined)
      this.queryParams.endTime = undefined
      this.handleQuery()
    },
    // 多选框选中数据
    handleItemChange(selection) {},
  },
}
</script>
<style lang="scss">
.not-status {
  .flex-pagination {
    display: flex;

    .pagination-left {
      width: calc(100% - 460px);
      overflow: auto;
      border-right: 1px solid #ebeef5;

      .pagination-container {
        min-width: 630px;

        .el-pagination {
          right: 0;
          left: auto;
        }
      }
    }

    .pagination-right {
      width: 460px;
      border-left: 1px solid #ebeef5;

      .el-pagination {
        left: 0;
        right: auto;
      }
    }
  }
}
</style>
