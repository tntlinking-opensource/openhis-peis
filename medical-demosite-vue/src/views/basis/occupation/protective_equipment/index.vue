<!-- 个人防护用品维护  开发人：麦沃德科技半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch">
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:occupation:protectiveEquipment:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:protectiveEquipment:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:protectiveEquipment:remove']">删除 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="个人防护用品代码" prop="defendIndividualCode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="个人防护用品名称" prop="defendIndividual" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="个人防护用品分类" prop="defendIndividualClass" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="输入码" fixed prop="inputCode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:protectiveEquipment:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:protectiveEquipment:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-dialog ref="addDialog" @getList="getList"></add-dialog>
  </div>
</template>
<script>
import { listProtectiveEquipment, delProtectiveEquipment } from '@/api/basis/occupation/protective_equipment'
import addDialog from './add'
export default {
  name: "Protective_equipment",
  components: { addDialog },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否显示筛选
      showSearch: true,
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
        inputCode: undefined,
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
      listProtectiveEquipment(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      }).catch((err) => {
        console.error(err)
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
    // 添加
    handleAdd() {
      this.$refs.addDialog.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delProtectiveEquipment(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addDialog.handleUpdate(row)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>