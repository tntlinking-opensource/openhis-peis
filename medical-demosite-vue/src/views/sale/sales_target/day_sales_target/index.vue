<!-- 销售完成额统计 麦沃德科技 开发人:予安 -->
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
    <div class="content flex-direction-column">
      <el-form size="small" :inline="true" class="no-margin-bottom">
        <el-form-item label="分中心">
          <el-input :value="currentBranch" readonly clearable style="width: 230px" />
        </el-form-item>
        <el-form-item label="统计日期">
          <el-date-picker v-model="queryParams.startTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 230px"> </el-date-picker>
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 230px"> </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport()">导出</el-button>
        </el-col>
      </el-row>

      <div class="table-box">
        <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%" @row-click="rowClick">
          <el-table-column type="selection" width="45" align="center" />
          <el-table-column type="index" width="55" label="序列" align="center" />
          <el-table-column prop="username" min-width="120" label="销售经理" align="center" show-overflow-tooltip />
          <el-table-column prop="fzx" min-width="140" label="所属分中心" align="center" show-overflow-tooltip />
          <el-table-column prop="complete" min-width="140" label="实际完成额(元)" align="center" show-overflow-tooltip />
          <el-table-column prop="bz" min-width="140" label="备注" align="center" show-overflow-tooltip />
          <el-table-column prop="workingAge" min-width="120" label="工作年龄" align="center" />
        </el-table>
      </div>

      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </div>
  </div>
</template>

<script>
import { getAllYear, getBranchData } from '@/api/sale/sales_target/monthly_sales_target.js'
import { listTarget } from '@/api/sale/sales_target/day_sales_target.js'
import Cookies from 'js-cookie'
export default {
  name: 'Day_sales_target',
  data() {
    return {
      // 年份列表
      yearOptions: [],
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        listYear: undefined,
        startTime: undefined,
        endTime: undefined,
        branchIds: Cookies.get('cid'),
      },
      // 当前分中心名称
      currentBranch: '',
      // 选中数组
      selectRow: [],
      // 非单个禁用
      single: true,
      // 总条数
      total: 30,
      // 遮罩层
      loading: false,
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.getYears()
    this.getBranch()
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 7 * 3600 * 24 * 1000
    this.queryParams.startTime = this.$getDate(past7daysStart).split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
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
      getAllYear().then(({ data }) => {
        this.yearOptions = data
        this.queryParams.listYear = data[data.length - 1].year
        this.getList()
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 获取数据
    getList() {
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.loading = true
      listTarget(this.queryParams)
        .then(({ data }) => {
          data.records.forEach((el) => {
            if (el.target1) {
              el.target1 = Number(el.target1)
              el.complete1 = Number(el.complete1)
              var percent1 = (el.complete1 * 100) / el.target1
              el.percent1 = percent1.toFixed(2) + '%'
            } else {
              el.percent1 = ''
            }
            if (el.target2) {
              el.target2 = Number(el.target2)
              el.complete2 = Number(el.complete2)
              var percent2 = (el.complete2 * 100) / el.target2
              el.percent2 = percent2.toFixed(2) + '%'
            } else {
              el.percent2 = ''
            }
            if (el.target3) {
              el.target3 = Number(el.target3)
              el.complete3 = Number(el.complete3)
              var percent3 = (el.complete3 * 100) / el.target3
              el.percent3 = percent3.toFixed(2) + '%'
            } else {
              el.percent3 = ''
            }
            if (el.target4) {
              el.target4 = Number(el.target4)
              el.complete4 = Number(el.complete4)
              var percent4 = (el.complete4 * 100) / el.target4
              el.percent4 = percent4.toFixed(2) + '%'
            } else {
              el.percent4 = ''
            }
            if (el.target5) {
              el.target5 = Number(el.target5)
              el.complete5 = Number(el.complete5)
              var percent5 = (el.complete5 * 100) / el.target5
              el.percent5 = percent5.toFixed(2) + '%'
            } else {
              el.percent5 = ''
            }
            if (el.target6) {
              el.target6 = Number(el.target6)
              el.complete6 = Number(el.complete6)
              var percent6 = (el.complete6 * 100) / el.target6
              el.percent6 = percent6.toFixed(2) + '%'
            } else {
              el.percent6 = ''
            }
            if (el.target7) {
              el.target7 = Number(el.target7)
              el.complete7 = Number(el.complete7)
              var percent7 = (el.complete7 * 100) / el.target7
              el.percent7 = percent7.toFixed(2) + '%'
            } else {
              el.percent7 = ''
            }
            if (el.target8) {
              el.target8 = Number(el.target8)
              el.complete8 = Number(el.complete8)
              var percent8 = (el.complete8 * 100) / el.target8
              el.percent8 = percent8.toFixed(2) + '%'
            } else {
              el.percent8 = ''
            }
            if (el.target9) {
              el.target9 = Number(el.target9)
              el.complete9 = Number(el.complete9)
              var percent9 = (el.complete9 * 100) / el.target9
              el.percent9 = percent9.toFixed(2) + '%'
            } else {
              el.percent9 = ''
            }
            if (el.target10) {
              el.target10 = Number(el.target10)
              el.complete10 = Number(el.complete10)
              var percent10 = (el.complete10 * 100) / el.target10
              el.percent10 = percent10.toFixed(2) + '%'
            } else {
              el.percent10 = ''
            }
            if (el.target11) {
              el.target11 = Number(el.target11)
              el.complete11 = Number(el.complete11)
              var percent11 = (el.complete11 * 100) / el.target11
              el.percent11 = percent11.toFixed(2) + '%'
            } else {
              el.percent11 = ''
            }
            if (el.target12) {
              el.target12 = Number(el.target12)
              el.complete12 = Number(el.complete12)
              var percent12 = (el.complete12 * 100) / el.target12
              el.percent12 = percent12.toFixed(2) + '%'
            } else {
              el.percent12 = ''
            }
          })
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.tableList = [
            {
              workingAge: '2',
              bz: '美时美刻',
              fzx: 'QDITHC健康管理中心',
              username: '测试条例1',
              percent1: '41500',
              complete1: '22200',
              target1: '33300',
              percent2: '41500',
              complete2: '41500',
              target2: '33300',
              percent3: '33300',
              complete3: '22200',
              target3: '41500',
              percent4: '33300',
              complete4: '22200',
              target4: '18800',
              percent5: '33300',
              complete5: '22200',
              target5: '18800',
            },
            {
              workingAge: '2',
              bz: '好吃家员工体检套餐',
              fzx: 'QDITHC健康管理中心',
              username: '测试条例1',
              percent1: '41500',
              complete1: '22200',
              target1: '33300',
              percent2: '41500',
              complete2: '41500',
              target2: '33300',
              percent3: '33300',
              complete3: '22200',
              target3: '41500',
              percent4: '33300',
              complete4: '22200',
              target4: '18800',
              percent5: '33300',
              complete5: '22200',
              target5: '18800',
            },
          ]
          this.loading = false
        })
    },
    // 选择年份
    handlerChange(value) {
      this.queryParams.current = 1
      this.queryParams.listYear = value
      this.getList()
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
      this.download('/sell/dayTarget/export', this.queryParams, `销售完成额统计.xlsx`)
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
