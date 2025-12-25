<!-- 审批任务  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="任务名称" prop="caseName">
        <el-input style="width: 180px" v-model="queryParams.caseName" placeholder="请输入任务名称" @keyup.enter.native="getList" clearable></el-input>
      </el-form-item>
      <el-form-item label="提交日期" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" type="date" value-format="yyyy-MM-dd" style="width: 180px" placeholder="请选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item label="—" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd" style="width: 180px" placeholder="请选择时间"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate(false)">处理</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>  
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
      <el-col :span="1.5">
        <el-button @click="handleVoice">处理1</el-button>
      </el-col>
    </el-row> -->
    <div class="table-box" style="padding-top: 16px;">
      <el-table ref="workList" border v-loading="loading" :data="workList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="任务名称" align="center" prop="caseName" show-overflow-tooltip />
        <el-table-column label="分中心名字" align="center" prop="fzx" show-overflow-tooltip />
        <el-table-column label="审批进度" align="center" prop="flowName" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ !scope.row.curLevel ? '审批未开始' : scope.row.curLevel + '/' + scope.row.allLevel }}
          </template>
        </el-table-column>
        <el-table-column label="类型名称" align="center" prop="typeName" show-overflow-tooltip />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <span v-if="scope.row.status == -1" style="color: gray">已失效</span>
            <span :style="scope.row.status == 2 ? 'color:green' : ''" v-else>
              {{ ['等待开始', '进行中', '已通过', '被驳回'][scope.row.status] }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.status == -1 || scope.row.status == 3">{{ scope.row.failText || '无' }}</span>
            <span v-else>{{ scope.row.remark || '无' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[20, 50, 100, 200 ,500]" />
    <!-- 订单审批 -->
    <order-approval ref="orderApproval" :isApproval="true" @getList="getList"></order-approval>
    <!-- 预约审批 -->
    <appointment-approval ref="appointmentApproval" :isApproval="true" @getList="getList"></appointment-approval>
    <!-- 超量审批 -->
    <over-approval ref="overApproval" :isApproval="true" @getList="getList"></over-approval>
    <audio controls="controls" hidden src="@/assets/voice/fifth.mp3" ref="audio"></audio>
  </div>
</template>

<script>
import { getListApi, questDetails } from '@/api/approval/approval_quest.js'
import orderApproval from '@/views/sale/order_customization/order_approval.vue'
import appointmentApproval from '@/views/reception/prepare_order/edit/appointment_approval.vue'
import overApproval from './over_approval.vue'

export default {
  name: 'Approval_quest',
  components: {
    orderApproval,
    appointmentApproval,
    overApproval,
  },
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 30,
        caseName: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 总条数
      total: 0,
      // 维护表格数据
      workList: [],
      // 遮罩层
      loading: true,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleVoice() {
      this.$refs.audio.currentTime = 0 //从头开始播放
      this.$refs.audio.play() //播放
    },
    // 刷新
    resetQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 查询审批列表 */
    getList() {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.workList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.workList.clearSelection()
      this.$refs.workList.toggleRowSelection(row)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const item = row || this.ids[0]
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
<style lang="scss">
.sub-center {
  .el-input-number {
    .el-input__inner {
      text-align: left;
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
}
.report-setting {
  .setimg {
    display: flex;
    flex-direction: inherit;
    width: auto;
    img {
      background-color: transparent;
    }
    .btn {
      margin: 8px;
      width: 70px;
      height: 36px;
    }
  }
}
</style>

<style scoped>
.upload-report-setting /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

.upload-report-setting /deep/ .el-upload-list__item-name {
  display: none;
  /* max-width: 80px;
  padding: 0; */
}
</style>
