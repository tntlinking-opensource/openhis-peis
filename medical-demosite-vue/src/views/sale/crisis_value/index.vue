<!-- 危机值管理 麦沃德科技 开发人:清风/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号" prop="tjid">
        <el-input v-model="queryParams.tjid" placeholder="请输入体检号关键字" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="高危人员名称" prop="gwrymc">
        <el-input v-model="queryParams.gwrymc" placeholder="请输入高危人员名称关键字" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-select v-model="queryParams.xb" placeholder="请选择性别" clearable style="width: 230px">
          <el-option label="男" :value="0" />
          <el-option label="女" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="tjzt">
        <el-select v-model="queryParams.tjzt" placeholder="请选择处理状态" clearable style="width: 230px">
          <el-option label="未处理" :value="0" />
          <el-option label="处理" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="handleView()" v-hasPermi="['sale:crisisValue:query']">查看</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="handleRisk()" v-hasPermi="['sale:crisisValue:handle']">处理</el-button>
      </el-col>
    </el-row>

    <!-- 表格数据 -->
    <div class="table-box">
      <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" align="center" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column type="index" label="序列" width="55" align="center" />
        <el-table-column prop="tjid" label="体检号" align="center" />
        <el-table-column prop="gwrymc" label="高危人员名称" align="center" show-overflow-tooltip />
        <el-table-column prop="nl" label="年龄" align="center" />
        <el-table-column prop="xb" label="性别" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.xb == 0">男</span>
            <span v-if="scope.row.xb == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="lxdh" label="联系电话" align="center" />
        <el-table-column prop="reportDivision" label="提报科室" align="center" show-overflow-tooltip />
        <el-table-column prop="tjlx" label="体检类型" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.tjlx == 0">健康</span>
            <span v-if="scope.row.tjlx == 1">职业</span>
            <span v-if="scope.row.tjlx == 2">综合</span>
            <span v-if="scope.row.tjlx == 3">复查</span>
          </template>
        </el-table-column>
        <el-table-column prop="tirq" label="体检日期" align="center" show-overflow-tooltip />
        <el-table-column prop="bz" label="备注" align="center" />
        <el-table-column prop="tjzt" label="处理状态" align="center">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.tjzt == 0">未处理</el-tag>
            <el-tag v-if="scope.row.tjzt == 1">已处理</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <details-items ref="detailsItems" @getList="getList"></details-items>
  </div>
</template>

<script>
import { listRisk, handleIsCl } from '@/api/sale/crisis_value'
import detailsItems from './details.vue'
export default {
  name:'Crisis_value',
  components: { detailsItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      selectRow: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        tjid: undefined,
        gwrymc: undefined,
        xb: undefined,
        lzt: undefined,
      },
      // 排检表格数据
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
      listRisk(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
      this.single = selection.length != 1
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 查看
    handleView() {
      this.$refs.detailsItems.handleView(this.selectRow.id)
    },
    // 处理
    handleRisk() {
      handleIsCl({ isClId: this.selectRow.id }).then((res) => {
        if (res.data != '') {
          //该记录已被处理过
          this.$alert('该记录已处理！请选择未处理的记录！', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
        } else {
          //进行处理
          this.$refs.detailsItems.handleRisk(this.selectRow.id)
        }
      })
    },
  },
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
