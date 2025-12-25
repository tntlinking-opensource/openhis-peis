<!-- 体检结账单  开发人：麦沃德科技半夏/清风 -->
<template>
  <div class="app-container flex-direction-column inspect-accounts">
    <div class="search-main">
      <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
        <el-form-item label="分中心" prop="branchIds">
          <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 230px" multiple collapse-tags>
            <el-option v-for="options in fzxOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单号" prop="order">
          <el-input v-model="queryParams.order" placeholder="请输入订单号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="体检团体" prop="idOrg">
          <!-- <el-input v-model="queryParams.idOrg" placeholder="请输入名称或拼音码进行查询" clearable style="width: 230px" @keyup.enter.native="handleQuery" /> -->
          <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.idOrg" :showTooltip="true"></input-select>
        </el-form-item>
        <el-form-item label="团体ID" prop="intId">
          <el-input v-model="queryParams.intId" placeholder="请输入团体ID" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button type="primary" plain icon="el-icon-refresh-left" size="mini" :disabled="!selections.length" @click="handleAsync">同步</el-button>
        </el-form-item>
      </el-form>
    </div>
    <drag-column style="flex: 1; min-height: 0" top-size="35%" bottom-size="65%">
      <template #topBox>
        <drag-row left-size="60%" right-size="40%">
          <template #leftBox>
            <div class="item-box flex-direction-column" style="padding-top: 8px">
              <div class="table-box">
                <el-table id="tableList" ref="tableList" border v-loading="loading" :data="tableList" height="calc(100% - 40px)" stripe @row-click="handleRowClick" @selection-change="handleSelectionChange">
                  <el-table-column type="selection" width="55" align="center" fixed="left" />
                  <el-table-column label="序列" width="55" type="index" align="center" />
                  <el-table-column label="订单号" min-width="120" prop="ddh" align="center" show-overflow-tooltip />
                  <el-table-column label="订单名称" min-width="120" prop="ddmc" align="center" show-overflow-tooltip />
                  <el-table-column label="团体ID" min-width="120" prop="intId" align="center" show-overflow-tooltip />
                  <el-table-column label="状态" align="center">
                    <el-table-column label="任务结束" min-width="100" prop="ffinished" align="center" show-overflow-tooltip>
                      <template slot-scope="scope">
                        <el-checkbox v-model="scope.row.ffinished" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
                      </template>
                    </el-table-column>
                    <el-table-column label="结算完成" min-width="100" prop="ffinished" align="center" show-overflow-tooltip>
                      <template slot-scope="scope">
                        <el-checkbox v-model="scope.row.ffinished" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
                      </template>
                    </el-table-column>
                  </el-table-column>
                  <el-table-column label="参检人数" align="center">
                    <el-table-column label="预计" min-width="80" prop="placeCount" align="center" show-overflow-tooltip />
                    <el-table-column label="备单" min-width="80" prop="bdcount" align="center" show-overflow-tooltip />
                    <el-table-column label="未登" min-width="80" prop="noreg" align="center" show-overflow-tooltip />
                    <el-table-column label="登记" min-width="80" prop="reg" align="center" show-overflow-tooltip />
                  </el-table-column>
                  <el-table-column label="日期时间" align="center">
                    <el-table-column label="任务预订" min-width="100" prop="ydDate" align="center" show-overflow-tooltip />
                    <el-table-column label="最后结算" min-width="100" prop="factprice" align="center" show-overflow-tooltip />
                  </el-table-column>
                </el-table>
                <!-- 分页 -->
                <el-pagination
                  small
                  background
                  :pager-count="5"
                  :current-page="queryParams.current"
                  style="margin: 5px"
                  :page-sizes="[20, 50, 100, 200 ,500]"
                  :page-size="queryParams.size"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                >
                </el-pagination>
              </div>
            </div>
          </template>
          <template #rightBox>
            <div class="item-box">
              <table-tr ref="tableTrItems"></table-tr>
            </div>
          </template>
        </drag-row>
      </template>
      <template #bottomBox>
        <drag-row left-size="60%" right-size="40%">
          <template #leftBox>
            <drag-column top-size="65%" bottom-size="35%">
              <template #topBox>
                <div class="item-box">
                  <table-middle-left ref="tableMiddleLeftItems"></table-middle-left>
                </div>
              </template>
              <template #bottomBox>
                <div class="item-box">
                  <table-button-left :orderId="orderId"></table-button-left>
                </div>
              </template>
            </drag-column>
          </template>
          <template #rightBox>
            <drag-column top-size="65%" bottom-size="35%">
              <template #topBox>
                <div class="item-box">
                  <table-middle-right></table-middle-right>
                </div>
              </template>
              <template #bottomBox>
                <div class="item-box">
                  <table-button-right></table-button-right>
                </div>
              </template>
            </drag-column>
          </template>
        </drag-row>
      </template>
    </drag-column>
  </div>
</template>
<script>
import dragRow from '@/components/DragRow'
import dragColumn from '@/components/DragColumn'
import tableTr from './table_tr.vue'
import tableMiddleLeft from './table_middle_left.vue'
import tableMiddleRight from './table_middle_right.vue'
import tableButtonLeft from './table_button_left.vue'
import tableButtonRight from './table_button_right.vue'

import { fzxApi, analyse, synchronizedChecked } from '@/api/finance/inspect_accounts.js'
export default {
  name: 'Inspect_accounts',
  components: { dragRow, dragColumn, tableTr, tableMiddleLeft, tableMiddleRight, tableButtonLeft, tableButtonRight },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selections: [],
      // 总条数
      total: 0,
      // 传入数据模板
      selectData: {
        placeholder: '请输入名称或拼音码进行查询',
        value: '客户单位名称', //第二列标题
        url: '/sell/customer/getListDatas', //请求连接
        bindValue: '', //初始值(必传)
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 50,
        branchIds: undefined,
        order: undefined,
        idOrg: undefined,
        intId: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 分中心列表
      fzxOptions: [],
      // 订单id
      orderId: '',
      //定义新变量-当前表格高度
      scrollTop: null,
    }
  },
  mounted() {
    this.getFzxList()
  },
  activated() {
    this.saveScroll()
  },
  beforeDestroy() {
    this.$refs.tableList.bodyWrapper.removeEventListener(
      'scroll',
      (res) => {
        let height = res.target
        this.scrollTop = height.scrollTop
      },
      false
    )
  },
  methods: {
    // 设置表格滚动条位置
    saveScroll() {
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollTop = this.$el.querySelector('.el-table__body-wrapper')
          scrollTop.scrollTop = this.scrollTop
        }, 50)
      })
    },
    // 获取分中心
    getFzxList() {
      fzxApi().then(({ data }) => {
        this.fzxOptions = data
        this.queryParams.branchIds = [this.$getCookie('cid')]
        this.getList()
      })
    },
    // 体检团体方法返回选中的值
    selectChange(value) {
      this.queryParams.idOrg = value.id
    },
    // 查询列表
    getList() {
      this.loading = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.branchIds = queryParams.branchIds.join(',')
      analyse(queryParams)
        .then((res) => {
          this.tableList = res.data.records
          this.total = res.data.total
          // this.queryParams
          this.loading = false
          this.$refs.tableList.bodyWrapper.addEventListener(
            'scroll',
            (res) => {
              let height = res.target
              this.scrollTop = height.scrollTop
            },
            false
          )
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
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
    // 同步
    handleAsync() {
      this.loading = true
      this.$modal.msgSuccess('同步接口正在维护中')
      this.loading = false
      // synchronizedChecked({
      //   ddh: this.selections[0].ddh,
      // })
      //   .then((res) => {
      //     if (res.data) {
      //       this.$modal.msgSuccess('同步成功')
      //     }
      //     this.loading = false
      //   })
      //   .catch((err) => {
      //     console.error(err)
      //     this.loading = false
      //   })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selections = selection
      if (selection.length > 1) {
        this.$refs.tableList.clearSelection()
        this.$refs.tableList.toggleRowSelection(selection.pop())
      } else if (selection.length == 1) {
        this.orderId = selection[0].id
        this.$refs.tableTrItems.getList(this.ids)
        this.$refs.tableMiddleLeftItems.handleGetList(this.ids, selection, this.queryParams.branchIds ? this.queryParams.branchIds.join(',') : '')
      }
    },
    //点击行选中
    handleRowClick(row) {
      this.$refs.tableList.toggleRowSelection(row, true)
    },
  },
}
</script>
<style lang="scss" scoped>
.app-container {
  height: 100%;
  // background: transparent;
  padding: 0;

  .search-main {
    // background: #fff;
    padding: 10px 10px 0;
    margin-bottom: 6px;

    .el-form-item {
      margin-bottom: 10px;
    }
  }

  .item-box {
    // background: #fff;
    height: 100%;
  }
}
</style>

<style scoped>
#tableList /deep/ .el-table__cell {
  padding: 5px 0;
}
#tableList /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.triggerFailure {
  pointer-events: none;
}
</style>
<style>
.inspect-accounts .el-table .el-table__cell {
  height: auto;
  padding: 2px 0 !important;
}
</style>
