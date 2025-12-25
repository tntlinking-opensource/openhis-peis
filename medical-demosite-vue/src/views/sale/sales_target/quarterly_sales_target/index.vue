<!-- 销售季度目标 麦沃德科技 开发人:清风/半夏 -->
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
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="handleAdd()" v-hasPermi="['sale:quarterlySalesTarget:quarterly']">销售季度目标</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="handleUpdate()" v-hasPermi="['sale:quarterlySalesTarget:edit']">编辑</el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button size="mini" type="danger" icon="el-icon-folder-opened" :disabled="single" plain @click="handleView()" v-hasPermi="['sale:quarterlySalesTarget:viewgoals']">查看目标</el-button>
        </el-col> -->
        <el-col :span="1.5">
          <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport()" v-hasPermi="['sale:quarterlySalesTarget:export']">导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-position" plain @click="handleSkip(1)" v-hasPermi="['sale:quarterlySalesTarget:annual']">年度目标</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button size="mini" type="success" icon="el-icon-position" plain @click="handleSkip(3)" v-hasPermi="['sale:quarterlySalesTarget:monthly']">月度目标</el-button>
        </el-col>
      </el-row>

      <div class="table-box">
        <el-table id="setTable" ref="tableData" :data="tableList" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%" style="width: 100%" @row-click="rowClick">
          <el-table-column type="selection" width="45" align="center" fixed="left" />
          <el-table-column type="index" width="55" label="序列" align="center" fixed="left" />
          <el-table-column prop="username" min-width="120" label="销售经理" align="center" fixed="left" show-overflow-tooltip />
          <el-table-column prop="fzx" min-width="140" label="所属分中心" align="center" show-overflow-tooltip />
          <el-table-column prop="bz" min-width="140" label="备注" align="center" show-overflow-tooltip />
          <el-table-column prop="workingAge" min-width="120" label="工作年龄" align="center" />
          <el-table-column label="第一季度" align="center">
            <el-table-column prop="target1" min-width="120" label="目标额(元)" align="center" />
            <el-table-column prop="complete1" min-width="120" label="实际完成额(元)" align="center" />
            <el-table-column prop="percent1" min-width="120" label="完成进度" align="center" />
          </el-table-column>
          <el-table-column label="第二季度" align="center">
            <el-table-column prop="target2" min-width="120" label="目标额(元)" align="center" />
            <el-table-column prop="complete2" min-width="120" label="实际完成额(元)" align="center" />
            <el-table-column prop="percent2" min-width="120" label="完成进度" align="center" />
          </el-table-column>
          <el-table-column label="第三季度" align="center">
            <el-table-column prop="target3" min-width="120" label="目标额(元)" align="center" />
            <el-table-column prop="complete3" min-width="120" label="实际完成额(元)" align="center" />
            <el-table-column prop="percent3" min-width="120" label="完成进度" align="center" />
          </el-table-column>
          <el-table-column label="第四季度" align="center">
            <el-table-column prop="target4" min-width="120" label="目标额(元)" align="center" />
            <el-table-column prop="complete4" min-width="120" label="实际完成额(元)" align="center" />
            <el-table-column prop="percent4" min-width="120" label="完成进度" align="center" />
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

      <quarterlyItems ref="quarterlyItems" @getList="getList"></quarterlyItems>
    </div>
  </div>
</template>

<script>
import quarterlyItems from './quarterly.vue'

import { getAllYear, getBranchData, listTarget, getIsLeader } from '@/api/sale/sales_target/quarterly_sales_target.js'
import Cookies from 'js-cookie'
export default {
  name: 'Quarterly_sales_target',
  components: { quarterlyItems },
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
          })
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
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
    // 制定季度目标
    handleAdd() {
      let err = ''
      if (!this.queryParams.listYear) {
        err = '请先选择相关的年份！'
      } else if (this.selectRow.id) {
        err = '该销售目标已制定,不能重复制定！若要修改,请编辑！'
      } else if (this.selectRow['bz'] == '服务单') {
        err = '所选销售经理不属于当前分中心，不能制定目标'
      }
      if (err) {
        this.$alert(err, '提醒', {
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
          this.$refs.quarterlyItems.handleAdd(this.selectRow, this.queryParams.listYear)
        }
      })
    },
    // 编辑
    handleUpdate() {
      if (!this.queryParams.listYear) {
        this.$alert('请先选择相关的年份！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (!this.selectRow.id) {
        this.$alert('该销售目标还未制定还不能进行编辑,您可以先制定目标再编辑！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (this.selectRow['bz'] == '服务单') {
        this.$alert('所选销售经理不属于当前分中心，不能编辑', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      getIsLeader().then((response) => {
        if (!response.data) {
          this.$alert('您不是领导，没有该操作权限！', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        } else {
          this.$refs.quarterlyItems.handleUpdate(this.selectRow, this.queryParams.listYear)
        }
      })
    },
    // 查看
    handleView() {
      if (!this.queryParams.listYear) {
        this.$alert('请先选择相关的年份！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (!this.selectRow.id) {
        this.$alert('该销售目标还未制定还不能进行查看,您可以先制定目标再查看！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (this.selectRow['bz'] == '服务单') {
        this.$alert('所选销售经理不属于当前分中心，不能查看', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      getIsLeader().then((response) => {
        if (!response.data) {
          this.$alert('您不是领导，没有该操作权限！', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        } else {
          this.$refs.quarterlyItems.handleView(this.selectRow.id)
        }
      })
    },
    // 导出
    handleExport() {
      this.download(
        '/sell/target/export',
        {
          ...this.queryParams,
        },
        `销售季度目标.xlsx`
      )
    },
    // 跳转
    handleSkip(type) {
      let title = type == 1 ? '销售年度目标' : '销售月度目标'
      let url = type == 1 ? '/sale/sales_target/annual_sales_target' : '/sale/sales_target/monthly_sales_target'
      this.$tab.openPage(title, url)
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
