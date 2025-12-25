<!-- 销售年度目标 麦沃德科技 开发人:清风/予安/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <div class="rightBorder">
      <div style="text-align: left">
        <el-tag class="tagBorder">年份</el-tag>
      </div>
      <el-radio-group v-model="queryParams.listYear" @change="handlerChange" style="width: 160px; display: flex; flex-direction: column">
        <el-radio-button id="radioBox" v-for="item in yearOptions" :label="item.year" :value="item.year" :key="item.id"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="content flex-direction-column" style="flex: 1">
      <el-form size="small" :inline="true" class="no-margin-bottom">
        <el-form-item label="分中心" prop="branchIds">
          <el-input :value="currentBranch" readonly clearable style="width: 230px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-download" plain @click="handleDownload" v-hasPermi="['sale:annualSalesTarget:download']">导入模板下载</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-download" plain @click="handleImport" v-hasPermi="['sale:annualSalesTarget:import']">导入年度目标</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport()" v-hasPermi="['sale:annualSalesTarget:export']">导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="handleAdd()" v-hasPermi="['sale:annualSalesTarget:setgoals']">制定年度目标</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="handleUpdate()" v-hasPermi="['sale:annualSalesTarget:edit']">编辑</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-position" plain @click="handleSkip(2)" v-hasPermi="['sale:annualSalesTarget:quarterly']">季度目标</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-position" plain @click="handleSkip(3)" v-hasPermi="['sale:annualSalesTarget:monthly']">月度目标</el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button size="mini" type="danger" icon="el-icon-folder-opened" :disabled="single" plain @click="handleView()" v-hasPermi="['sale:annualSalesTarget:query']">查看目标</el-button>
        </el-col> -->
        <el-col :span="1.5">
          <el-button size="mini" type="primary" plain>目标额合计：{{ totalData.target }} 元</el-button>
          <el-button size="mini" type="primary" plain>实际完成额合计：{{ totalData.complete }} 元</el-button>
        </el-col>
      </el-row>

      <div class="table-box">
        <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%" @row-click="rowClick">
          <el-table-column type="selection" align="center" width="45" />
          <el-table-column type="index" label="序列" width="55" align="center" />
          <el-table-column prop="username" label="销售经理" align="center" show-overflow-tooltip />
          <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip />
          <el-table-column prop="ndmb" label="目标额(元)" align="center" />
          <el-table-column prop="complete" label="实际完成额(元)" align="center" />
          <el-table-column prop="percent" label="完成进度" align="center" />
          <el-table-column prop="bz" label="备注" align="center" show-overflow-tooltip />
          <el-table-column prop="workingAge" label="工作年龄" align="center" />
        </el-table>
      </div>

      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      <!-- 制定年度目标 -->
      <annual-goals-items ref="annualGoalsItems" @getList="handleQuery(2)" @getSummary="getSummary"></annual-goals-items>
      <!-- 导入年度目标 -->
      <import-dialog ref="importDialog" @getList="handleQuery(2)"></import-dialog>
    </div>
  </div>
</template>

<script>
import annualGoalsItems from './annual_goals.vue'
import importDialog from './import.vue'
import { listYears, listYearTarget, getBranchData, getIsLeader, getSummaryData } from '@/api/sale/sales_target/annual_sales_target.js'
import Cookies from 'js-cookie'
export default {
  name: 'Annual_sales_target',
  components: { annualGoalsItems, importDialog },
  data() {
    return {
      // 年份列表
      yearOptions: [],
      // 筛选参数
      queryParams: {
        current: 1,
        size: 50,
        listYear: undefined,
        branchIds: Cookies.get('cid'),
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
      // 合计数据
      totalData: {},
    }
  },
  created() {
    this.getYears()
    this.getBranch()
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
    // 获取年份
    getYears() {
      this.loading = true
      listYears().then(({ data }) => {
        this.yearOptions = data
        this.queryParams.listYear = data[data.length - 1].year
        this.getSummary()
        this.getList()
      })
    },
    // 搜索
    handleQuery(type) {
      if (type != 2) {
        this.queryParams.current = 1
      }
      this.getList()
      this.getSummary()
    },
    // 获取数据
    getList() {
      this.loading = true
      listYearTarget(this.queryParams)
        .then(({ data }) => {
          data.records.forEach((el) => {
            if (el.ndmb) {
              el.ndmb = Number(el.ndmb)
              el.complete = Number(el.complete)
              var percent = (el.complete * 100) / el.ndmb
              el.percent = percent.toFixed(2) + '%'
            } else {
              el.percent = ''
            }
          })
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.tableList = []
          this.loading = false
        })
    },
    // 获取合计数据
    getSummary() {
      getSummaryData(this.queryParams).then((response) => {
        this.totalData = response.data[0]
      })
    },
    // 选择年份
    handlerChange(value) {
      this.queryParams.current = 1
      this.queryParams.listYear = value
      this.getList()
      this.getSummary()
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
    // 制定年度目标
    handleAdd() {
      if (!this.queryParams.listYear) {
        this.$alert('请先选择相关的年份！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (this.selectRow.id) {
        this.$alert('该销售目标已制定,不能重复制定！若要修改,请编辑！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (this.selectRow['bz'] == '服务单') {
        this.$alert('所选销售经理不属于当前分中心，不能制定目标', '提醒', {
          type: 'warning',
        })
        return
      }
      getIsLeader().then((response) => {
        if (!response.data) {
          this.$alert('您不是领导，没有该操作权限！', '提醒', {
            type: 'warning',
          })
          return
        } else {
          this.$refs.annualGoalsItems.handleAdd(this.selectRow, this.queryParams.listYear)
        }
      })
    },
    // 编辑
    handleUpdate() {
      if (!this.queryParams.listYear) {
        this.$alert('请先选择相关的年份！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (!this.selectRow.id) {
        this.$alert('该销售目标还未制定还不能进行编辑,您可以先制定目标再编辑！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (this.selectRow['bz'] == '服务单') {
        this.$alert('所选销售经理不属于当前分中心，不能编辑', '提醒', {
          type: 'warning',
        })
        return
      }
      getIsLeader().then((response) => {
        if (!response.data) {
          this.$alert('您不是领导，没有该操作权限！', '提醒', {
            type: 'warning',
          })
          return
        } else {
          this.$refs.annualGoalsItems.handleUpdate(this.selectRow.id)
        }
      })
    },
    // 查看
    handleView() {
      if (!this.queryParams.listYear) {
        this.$alert('请先选择相关的年份！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (!this.selectRow.id) {
        this.$alert('该销售目标还未制定还不能进行查看,您可以先制定目标再查看！', '提醒', {
          type: 'warning',
        })
        return
      }
      if (this.selectRow['bz'] == '服务单') {
        this.$alert('所选销售经理不属于当前分中心，不能查看', '提醒', {
          type: 'warning',
        })
        return
      }
      getIsLeader().then((response) => {
        if (!response.data) {
          this.$alert('您不是领导，没有该操作权限！', '提醒', {
            type: 'warning',
          })
          return
        } else {
          this.$refs.annualGoalsItems.handleView(this.selectRow.id)
        }
      })
    },
    // 跳转
    handleSkip(type) {
      let title = type == 2 ? '销售季度目标' : '销售月度目标'
      let url = type == 2 ? '/sale/sales_target/quarterly_sales_target' : '/sale/sales_target/monthly_sales_target'
      this.$tab.openPage(title, url)
    },
    // 导入模板下载
    handleDownload() {
      this.downloadStatic('/static/stencil/annual_sales_target.xlsx', '销售年度目标模板.xlsx')
    },
    // 导入
    handleImport() {
      this.$refs.importDialog.showDialog(this.queryParams.listYear)
    },
    // 导出
    handleExport() {
      this.download(
        '/sell/leadertarget/export',
        {
          fzxIds: this.queryParams.branchIds,
          ...this.queryParams,
        },
        `销售年度目标.xlsx`
      )
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
