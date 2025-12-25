<!-- 同期销售对比 麦沃德科技 开发人:予安 -->
<template>
  <div class="app-container flex-direction-column">
    <div class="content flex-direction-column">
      <el-form size="small" :inline="true" class="no-margin-bottom">
        <el-form-item label="分中心">
          <el-input :value="currentBranch" readonly clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="对比类型">
          <el-select v-model="state" style="width: 80px" @change="handleQuery">
            <el-option label="年" :value="1"></el-option>
            <el-option label="季" :value="2"></el-option>
            <el-option label="月" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="统计日期">
          <el-date-picker v-model="queryParams.startYear" type="year" value-format="yyyy" placeholder="选择年" style="width: 100px" @change="handleQuery"> </el-date-picker>
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker v-model="queryParams.endYear" type="year" value-format="yyyy" placeholder="选择年" style="width: 100px" @change="handleQuery"> </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport()" v-hasPermi="['sale:monthlySalesTarget:export']">导出</el-button>
        </el-col>
      </el-row>

      <div class="table-box">
        <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%" @row-click="rowClick">
          <el-table-column type="selection" width="45" align="center" />
          <el-table-column type="index" width="55" label="序列" align="center" />
          <el-table-column prop="username" min-width="120" label="销售经理" align="center" show-overflow-tooltip />
          <el-table-column prop="fzx" min-width="140" label="所属分中心" align="center" show-overflow-tooltip />
          <template v-if="state == 1">
            <el-table-column prop="growth" label="年同比" align="center" />
            <el-table-column :label="queryParams.startYear" align="center">
              <el-table-column prop="ndmb" label="目标额(元)" align="center" />
              <el-table-column prop="complete" label="实际完成额(元)" align="center" />
              <el-table-column prop="completion1" label="完成进度" align="center" />
            </el-table-column>
            <el-table-column :label="queryParams.endYear" align="center">
              <el-table-column prop="ndmb2" label="目标额(元)" align="center" />
              <el-table-column prop="complete2" label="实际完成额(元)" align="center" />
              <el-table-column prop="completion2" label="完成进度" align="center" />
            </el-table-column>
          </template>
          <template v-else-if="state == 2">
            <el-table-column label="第一季度" align="center">
              <el-table-column prop="growth1" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete1" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="第二季度" align="center">
              <el-table-column prop="growth2" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete2" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="第三季度" align="center">
              <el-table-column prop="growth3" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete3" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="第四季度" align="center">
              <el-table-column prop="growth4" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete4" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
          </template>
          <template v-else-if="state == 3">
            <el-table-column label="1月" align="center">
              <el-table-column prop="growth1" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete1" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="2月" align="center">
              <el-table-column prop="growth2" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete2" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="3月" align="center">
              <el-table-column prop="growth3" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete3" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="4月" align="center">
              <el-table-column prop="growth4" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete4" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="5月" align="center">
              <el-table-column prop="growth5" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete5" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="6月" align="center">
              <el-table-column prop="growth6" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete6" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="7月" align="center">
              <el-table-column prop="growth7" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete7" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="8月" align="center">
              <el-table-column prop="growth8" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete8" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="9月" align="center">
              <el-table-column prop="growth9" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete9" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="10月" align="center">
              <el-table-column prop="growth19" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete10" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="11月" align="center">
              <el-table-column prop="growth11" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete11" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
            <el-table-column label="12月" align="center">
              <el-table-column prop="growth12" min-width="120" label="同比" align="center" />
              <el-table-column prop="complete12" min-width="120" label="实际完成额(元)" align="center" />
            </el-table-column>
          </template>
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </div>
  </div>
</template>

<script>
import { getBranchData } from '@/api/sale/sales_target/monthly_sales_target.js'
import { getDataApi } from '@/api/sale/sales_target/sales_comparison'
import Cookies from 'js-cookie'
export default {
  name: 'Sales_comparison',
  data() {
    return {
      // 对比类型
      state: 1,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 50,
        branchIds: Cookies.get('cid'),
        startYear: '',
        endYear: '',
      },
      // 当前分中心名称
      currentBranch: '',
      // 选中数组
      selectRow: [],
      // 非单个禁用
      single: true,
      // 总条数
      total: 0,
      // 遮罩层
      loading: false,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    var myDate = new Date()
    var tYear = myDate.getFullYear()
    this.queryParams.startYear = (tYear - 1).toString()
    this.queryParams.endYear = tYear.toString()
    this.getBranch()
    this.handleQuery()
  },
  methods: {
    // 查询分中心数据
    getBranch() {
      getBranchData().then((response) => {
        for (var i in response.data) {
          if (response.data[i].branchId == Cookies.get('cid')) {
            this.currentBranch = response.data[i].fzx
          }
        }
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 获取数据
    getList() {
      this.loading = true
      this.tableList = []
      let url = '/sell/sellDate/' + (this.state == 1 ? 'getSellDateYear' : this.state == 2 ? 'getSellDateQuarter' : 'getSellDateMonth')
      getDataApi(url, this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 表格项被选中
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
    // 导出
    handleExport() {
      let url = '/sell/sellDate/' + (this.state == 1 ? 'exportSellDateYear' : this.state == 2 ? 'exportSellDateQuarter' : 'exportSellDateMonth')
      this.download(url, this.queryParams, `销售同期对比(${this.state == 1 ? '年' : this.state == 2 ? '季' : '月'}).xlsx`)
    },
  },
}
</script>

<style scoped>
.app-container {
  flex-direction: row;
}

.app-container .content {
  display: flex;
  flex: 1;
  min-width: 0;
}

#radioBox /deep/ .el-radio-button__inner {
  text-align: left;
  width: 100%;
  border: 0px solid transparent;
  border-radius: 0;
}

#radioBox /deep/ .el-radio-button {
  box-shadow: inset !important;
}

.rightBorder {
  width: 160px;
  height: 104%;
  margin: -12px 16px 0 0;
  border-right: 16px solid rgb(246, 247, 251);
  display: inline-block;
  border: 0px solid transparent;
  box-shadow: none !important;
}

.tagBorder {
  margin-top: 12px;
  /* margin-left: 12px;  */
  background-color: transparent;
  border: 0 solid transparent;
  color: black;
  font-size: 16px;
  font-weight: 600;
}

#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
