<!-- 团体预约 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchId">
        <el-select v-model="queryParams.branchId" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单名称" prop="ddmc">
        <el-input v-model="queryParams.ddmc" placeholder="请输入订单名称" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="选择日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['subscribe:groupAppointment:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['subscribe:groupAppointment:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['subscribe:groupAppointment:remove']">删除 </el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <!-- 表格 -->
      <el-table ref="tableList" v-loading="loading" :data="tableList" stripe border height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="日期" prop="reservationDate" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.reservationDate ? scope.row.reservationDate.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="订单号" prop="orderNum" align="center" show-overflow-tooltip />
        <el-table-column label="订单" prop="orderName" align="center" show-overflow-tooltip />
        <el-table-column label="销售经理" prop="xsjlName" align="center" show-overflow-tooltip />
        <el-table-column label="预约人" prop="creator" align="center" show-overflow-tooltip />
        <el-table-column label="预约类型" prop="levelName" align="center" show-overflow-tooltip />
        <el-table-column label="体检形式" prop="harmClassCode" align="center" show-overflow-tooltip />
        <el-table-column label="预约人数" prop="countAm" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="remark" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加 -->
    <add-item ref="addItem" @getList="getList"></add-item>
    <!-- 添加 -->
    <edit-item ref="editItem" @getList="getList"></edit-item>
  </div>
</template>
<script>
import { getListData, deleteListData } from '@/api/subscribe/group_appointment.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
import addItem from './add.vue'
import editItem from './edit.vue'
export default {
  components: { addItem, editItem },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selectData: [],
      // 总条数
      total: 50,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        branchId: undefined,
        ddmc: undefined,
        date: undefined,
        startDate: undefined,
        endDate: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchId = this.$getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
    let toData = new Date(new Date().toLocaleDateString()).getTime()
    let past7daysStart = toData + 30 * 3600 * 24 * 1000
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate(past7daysStart).split(' ')[0]]
    this.handleQuery()
  },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startDate = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endDate = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectData = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 添加
    handleAdd() {
      this.$refs.addItem.handleAdd()
    },
    // 编辑
    handleUpdate() {
      if (new Date(this.selectData[0].reservationDate.split(' ')[0]) < new Date(this.$getDate().split(' ')[0])) {
        this.$alert('当前预约时间已过期,请重新选择', '提示')
        return
      }
      this.$refs.editItem.handleUpdate(this.ids[0])
    },
    // 删除
    handleDelete() {
      let postIds = this.ids.join(',')
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(() => {
          this.loading = true
          deleteListData(postIds)
            .then(() => {
              this.loading = false
              this.$modal.msgSuccess('删除成功')
              this.getList()
            })
            .catch((error) => {
              console.error()
              this.loading = false
            })
        })
        .catch()
    },
  },
}
</script>
