<!-- 我的审批情况  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column client-statistics">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="任务名称" prop="caseName">
        <el-input style="width: 180px" v-model="queryParams.caseName" placeholder="请输入任务名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="提交日期" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" type="date" value-format="yyyy-MM-dd" style="width: 180px" placeholder="请选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item label="—" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd" style="width: 180px" placeholder="请选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="dblClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="创建时间" prop="createdate" align="center" width="140" show-overflow-tooltip />
        <el-table-column label="任务名称" prop="caseName" align="center" width="280" show-overflow-tooltip />
        <el-table-column label="审批进度" prop="schedule" align="center" width="100" show-overflow-tooltip />
        <el-table-column label="当前审批人" prop="userName" align="center" width="100" show-overflow-tooltip />
        <el-table-column label="状态" prop="status" align="center" width="90" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :style="{ color: scope.row.status == 2 ? 'green' : scope.row.status == 3 ? 'red' : '' }">{{ ['等待开始', '进行中', '已通过', '被驳回'][scope.row.status] || '失效' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="审批备注" prop="remark" align="center" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="90">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleDetails(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 订单审批 -->
    <order-approval ref="orderApproval" :isApproval="true"></order-approval>
    <!-- 预约审批 -->
    <appointment-approval ref="appointmentApproval" :isApproval="true"></appointment-approval>
    <!-- 超量审批 -->
    <over-approval ref="overApproval" :isApproval="true"></over-approval>
  </div>
</template>
<script>
import { getListData } from '@/api/approval/my_approval.js'
import { questDetails } from '@/api/approval/approval_quest.js'
import orderApproval from '@/views/sale/order_customization/order_approval.vue'
import appointmentApproval from '@/views/reception/prepare_order/edit/appointment_approval.vue'
import overApproval from '../approval_quest/over_approval.vue'
export default {
  name: 'My_approval',
  components: {
    orderApproval,
    appointmentApproval,
    overApproval,
  },
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 30,
        caseName: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 表格数据
      tableList: [],
      // 选中项的id
      selectId: undefined,
    }
  },
  created() {
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData - 7 * 3600 * 24 * 1000
    this.queryParams.startTime = this.$getDate(past7daysStart)
    this.queryParams.endTime = this.$getDate()
    this.handleQuery()
  },
  methods: {
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
    // 查询列表
    getList() {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
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
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
      } else if (selection.length == 1) {
        this.selectId = selection[0].id
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表格双击事件
    dblClick(row, col) {
      this.handleDetails(row)
    },
    handleDetails(item) {
      if (item.status == -1) {
        this.$alert('该任务已失效,请重新选择', '提示')
        return
      }
      this.loading = true
      questDetails(item.id)
        .then((res) => {
          if (item.typeFlag == 'ORDER_FLOW' || item.typeFlag == 'ORDER_FLOW_OCCUPATION' || item.typeFlag == 'ORDER_FLOW_CHANGE' || item.typeFlag == 'ORDER_FLOW_CHANGE_OCCUPATIONAL') {
            this.$refs.orderApproval.showDialog(res.data.bizId, item.typeFlag != 'ORDER_FLOW_CHANGE' ? 0 : 1, res.data.itemList, item.id)
          } else if (item.typeFlag == 'OVER_RESERVATION') {
            this.$refs.appointmentApproval.showDialog('', '', [], res.data)
          } else if (item.typeFlag == 'EXCESS_RESERVATION') {
            this.$refs.overApproval.showDialog('', '', [], res.data)
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
  },
}
</script>
