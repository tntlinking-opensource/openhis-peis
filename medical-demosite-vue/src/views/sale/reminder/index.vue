<!-- 备忘提醒 麦沃德科技 开发人:清风/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleAdd()" v-hasPermi="['sale:reminder:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="handleUpdate()" v-hasPermi="['sale:reminder:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-edit-delete" :disabled="multiple" plain @click="handleDelete()" v-hasPermi="['sale:reminder:delete']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-switch-button" :disabled="multiple" plain @click="handleOver()" v-hasPermi="['sale:reminder:over']">结束</el-button>
      </el-col>
    </el-row>

    <!-- 表格数据 -->
    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" align="center" border height="100%" @selection-change="handleSelectionChange" :row-class-name="tableRowClassName">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" label="序列" width="55" align="center" />
        <el-table-column prop="khdwmc" label="单位名称" align="center" show-overflow-tooltip />
        <el-table-column prop="remindTime" label="提醒时间" align="center" show-overflow-tooltip />
        <el-table-column prop="content" label="提醒内容" align="center" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status == 1">已结束</el-tag>
            <el-tag type="danger" v-else>未结束</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <addItems ref="addItems" @getList="getList"></addItems>
  </div>
</template>

<script>
import { listRemind, delRemind, overRemind } from '@/api/sale/reminder'
import addItems from './add.vue'
export default {
  name:'Reminder',
  components: { addItems },
  data() {
    return {
      ids: [],
      statusList: [],
      single: true,
      multiple: true,
      loading: false,
      // 查询参数
      total: 0,
      queryParams: {
        current: 1,
        size: 10,
      },
      tableData: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listRemind(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.statusList = selection.map((item) => item.status)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    tableRowClassName({ row }) {
      if (row.due == 1 && row.status != 1) {
        return 'warning-row'
      }
      return ''
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 编辑
    handleUpdate() {
      if (this.statusList[0] == 1) {
        this.$alert('该记录已锁定，请解锁以后再进行编辑！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      this.$refs.addItems.handleUpdate(this.ids)
    },
    // 删除
    handleDelete() {
      const ids = this.ids
      const statusList = this.statusList
      for (var i in statusList) {
        if (statusList[i] == 1) {
          this.$alert(statusList.length > 1 ? '记录已锁定，请解锁以后再进行删除！' : '该记录已锁定，请解锁以后再进行删除！', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
          return
        }
      }
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delRemind(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 结束
    handleOver() {
      const ids = this.ids
      this.$modal
        .confirm('确定要结束所选记录吗？', '提醒')
        .then(function () {
          return overRemind(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('操作成功')
        })
        .catch(() => {})
    },
  },
}
</script>
<style>
.warning-row {
  background-color: #ffec8b !important;
}
</style>
