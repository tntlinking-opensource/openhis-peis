<!-- 团检费用统计  开发人：麦沃德科技暴雨/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-tooltip popper-class="tooltip-item" effect="dark" :content="branchNames" :disabled="!branchNames" placement="left">
          <el-select class="multiple-select" v-model="queryParams.branchIds" placeholder="请选择分中心" multiple clearable style="width: 160px" @change="handleQuery">
            <el-option v-for="item in centerOptions" :key="item.id" :value="item.branchId" :label="item.fzx" />
          </el-select>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="登记日期" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="至" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="销售经理" style="margin-right: 16px" prop="name">
        <el-input style="width: 160px" placeholder="请输入销售经理" v-model="queryParams.name" clearable @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择" clearable style="width: 180px" @change="handleQuery">
          <el-option v-for="item in tjlxOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检形式" prop="tjxs">
        <el-select v-model="queryParams.tjxs" placeholder="请选择" clearable style="width: 180px" @change="handleQuery">
          <el-option v-for="item in tjxsOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
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
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleexport" v-hasPermi="['statistical:inspectCost:sellGroup:export']">导出Excel</el-button>
      </el-col>
    </el-row>

    <dragRow left-size="50%" right-size="50%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table :data="tableData" ref="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="handleRowClick" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="sellName" min-width="120" show-overflow-tooltip label="销售经理" align="center"></el-table-column>
              <el-table-column prop="kdzl" min-width="120" show-overflow-tooltip label="开单助理" align="center"></el-table-column>
              <el-table-column prop="intId" width="120" show-overflow-tooltip label="团体ID" align="center"></el-table-column>
              <el-table-column prop="orgName" min-width="180" show-overflow-tooltip label="体检单位" align="center"></el-table-column>
              <el-table-column prop="price" width="120" show-overflow-tooltip label="原价" align="center"></el-table-column>
              <el-table-column prop="fastprice" width="120" show-overflow-tooltip label="实收" align="center"></el-table-column>
              <el-table-column prop="addorgprice" width="120" show-overflow-tooltip label="加项原价合计" align="center"></el-table-column>
              <el-table-column prop="addprice" width="120" show-overflow-tooltip label="科室加项金额" align="center"></el-table-column>
              <el-table-column prop="counts" width="120" show-overflow-tooltip label="人数" align="center"></el-table-column>
              <el-table-column prop="zk" width="120" show-overflow-tooltip label="折扣" align="center"></el-table-column>
              <el-table-column prop="kdj" width="120" show-overflow-tooltip label="客单价" align="center"></el-table-column>
            </el-table>
          </div>

          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>

      <template #rightBox>
        <!-- 表2 -->
        <div class="item-box">
          <div class="flex-direction-column">
            <div class="table-box">
              <el-table :data="tableRightList" v-loading="loadingr" style="max-width: 100%" :border="true" :stripe="true" height="100%">
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="order" label="订单号" align="center" min-width="120px"></el-table-column>
                <el-table-column prop="khdwmc" show-overflow-tooltip label="客户单位名称" min-width="180px" align="center"></el-table-column>
                <el-table-column prop="ts" label="统收" align="center"></el-table-column>
                <el-table-column prop="qt" label="其它" align="center"></el-table-column>
                <el-table-column prop="hj" label="合计" align="center"></el-table-column>
                <el-table-column prop="jz" label="记账" align="center"></el-table-column>
                <el-table-column prop="regDate" show-overflow-tooltip label="备单日期" min-width="180px" align="center"></el-table-column>
                <el-table-column prop="FirstDate" show-overflow-tooltip label="首次顾客来检日期" min-width="180px" align="center"></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </template>
    </dragRow>
  </div>
</template>

<script>
import dragRow from '@/components/DragRow'
import { delItems } from '@/api/basis/charge'
import { page, exportTable, getTotalList, getBranchData } from '@/api/statistical/inspect_cost/sell_group.js'
export default {
  name: 'Sell_group',
  components: { dragRow },
  data() {
    return {
      // 日期选择参数
      pickerOptions: {
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        branchIds: [],
        startTime: undefined,
        endTime: undefined,
        name: '',
        tjlx: '2',
        tjxs: undefined,
      },
      //分中心名称
      branchNames: '',
      rightTotalPages: {},
      //体检类型
      tjlxOptions: [
        { id: '2', text: '所有' },
        { id: '0', text: '健康' },
        { id: '1', text: '职业' },
      ],
      //体检形式
      tjxsOptions: [
        { id: '0', text: '内检' },
        { id: '1', text: '外检' },
        { id: '2', text: '内检加外检' },
      ],
      //分中心
      centerOptions: [],
      ids: [],
      single: '',
      multiple: '',
      loading: false,
      loadingr: false,
      search: {
        patientname: '',
        idcardno: '',
        phone: '',
      },
      tableData: [],
      tableRightList: [],
    }
  },
  created() {
    this.$set(this.queryParams, 'branchIds', this.$getCookie('cid').split(','))
    this.$set(this.queryParams, 'startTime', this.$getDate().split(' ')[0])
    this.$set(this.queryParams, 'endTime', this.$getDate().split(' ')[0])
    this.getBasic()
  },
  methods: {
    //getBasic获取查询queryParams基础属性
    getBasic() {
      getBranchData().then((res) => {
        res.data.forEach((el) => {
          this.centerOptions.push(el)
        })
        this.getList()
      })
    },
    //分中心改变
    changeBranchIds(value) {
      this.branchNames = value.map((item) => {
        return this.fzxOptions.find((fzx) => fzx.branchId == item).fzx
      }).join('、')
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length > 1) {
        this.$refs.tableData.clearSelection()
        this.$refs.tableData.toggleRowSelection(selection.pop())
      } else if (selection.length == 1) {
        if (!selection[0].intId) {
          this.tableRightList = []
          return
        }
        let startTime, endTime
        this.loadingr = true
        if (this.queryParams.startTime) {
          startTime = this.queryParams.startTime + ' 00:00:00'
        }
        if (this.queryParams.endTime) {
          endTime = this.queryParams.endTime + ' 23:59:59'
        }
        let obj = {
          branchIds: this.queryParams.center,
          id: selection[0].intId,
          type: this.queryParams.tjlx || '2',
          startTime,
          endTime,
        }
        this.rightTotalPages = obj
        getTotalList(obj)
          .then((res) => {
            this.tableRightList = res.data
            this.loadingr = false
          })
          .catch(() => {
            this.loadingr = false
          })
      }
    },
    //单击左行
    handleRowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    //右表分页查询
    totalPages() {
      this.loadingr = true
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime + ' 23:59:59'
      }
      let obj = {
        branchIds: this.queryParams.center,
        id: this.rightTotalPages.id,
        type: this.queryParams.tjlx || '2',
        startTime,
        endTime,
      }
      getTotalList(obj)
        .then((res) => {
          this.tableRightList = res.data.records
          this.loadingr = false
        })
        .catch(() => {
          this.loadingr = false
        })
    },
    //导出
    handleexport() {
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime + ' 23:59:59'
      }
      this.download(
        '/statistics/personalTotal/export',
        {
          ...this.queryParams,
          startTime,
          endTime,
        },
        `团检费用统计表.xlsx`
      )
    },
    // 添加
    handleAdd() {
      this.$refs.addDialog.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delItems(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addDialog.handleUpdate(row)
    },
    // 分页条数发生改变
    handleSizeChange(val) {
      if (this.queryParams.current * val > this.total) {
        this.queryParams.current = 1
      }
      this.queryParams.size = val
      this.getList()
    },
    // 分页页码发生改变
    handleCurrentChange(val) {
      this.queryParams.current = val
      this.getList()
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
    getList() {
      this.loading = true
      let startTime, endTime
      if (this.queryParams.startTime) {
        startTime = this.queryParams.startTime + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        endTime = this.queryParams.endTime + ' 23:59:59'
      }
      page({ ...this.queryParams, startTime, endTime })
        .then((res) => {
          this.total = res.data.total
          this.tableData = res.data.records
          this.loading = false
        })
        .catch(() => {
          this.loadingr = false
        })
    },
    getListr() {
      this.loadingr = true
      setTimeout(() => {
        this.loadingr = false
      }, 500)
    },
  },
}
</script>

<style lang="scss" scoped>
.app-container {
  .item-box {
    background: #fff;
    height: 100%;
  }

  .multiple-select ::v-deep .el-select__tags {
    flex-wrap: nowrap;
    overflow: hidden;
  }
}
</style>
