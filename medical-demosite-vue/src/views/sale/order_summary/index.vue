<!-- 订单总结 麦沃德科技 开发人:清风/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="订单名称" prop="dmc">
        <el-input v-model="queryParams.dmc" placeholder="请输入订单名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="单位名称" prop="dwmc">
        <el-input v-model="queryParams.dwmc" placeholder="请输入单位名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="销售经理" prop="xjl">
        <el-input v-model="queryParams.xjl" placeholder="请输入销售经理" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleAdd" v-hasPermi="['sale:orderSummary:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="handleUpdate" v-hasPermi="['sale:orderSummary:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-delete" :disabled="multiple" plain @click="handleDelete" v-hasPermi="['sale:orderSummary:delete']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="handleView" v-hasPermi="['sale:orderSummary:query']">查看</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table :data="tableList" v-loading="loading" align="center" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
        <el-table-column type="selection" width="60px" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="ddmc" label="订单名称" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
        <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
        <el-table-column prop="zj" label="总结" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:chargeCategory:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:chargeCategory:remove']">删除</el-button>
            <el-button size="mini" type="text" style="color: rgb(0, 89, 255)" @click="handleView(scope.row)" v-hasPermi="['basis:chargeCategory:query']">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <add-items ref="addItems" @getList="getList"></add-items>
    <view-items ref="viewItems"></view-items>
  </div>
</template>

<script>
import { listSummary, delSummary } from '@/api/sale/order_summary'
import addItems from './add.vue'
import viewItems from './view.vue'
export default {
  name:'Order_summary',
  components: { addItems, viewItems },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        dmc: undefined,
        dwmc: undefined,
        xjl: undefined,
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
      listSummary(this.queryParams).then((response) => {
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
      this.ids = selection.map((item) => item.id)
      this.statusList = selection.map((item) => item.status)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 编辑
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$refs.addItems.handleUpdate(id)
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('确定要删除选择的记录？', '提醒')
        .then(function () {
          return delSummary(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功！')
        })
        .catch(() => {})
    },
    // 查看
    handleView(row) {
      const id = row.id || this.ids
      this.$refs.viewItems.handleView(id)
    },
  },
}
</script>
