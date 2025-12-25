<!-- 部位维护 开发人：麦沃德科技矢北/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item prop="name" label="名称/英文名">
        <el-input style="width: 230px" v-model="queryParams.name" placeholder="请输入名称或英文名" @keyup.enter.native="getList"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basicPACS:placeMaintain:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basicPACS:placeMaintain:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basicPACS:placeMaintain:delete']">删除 </el-button>
      </el-col>
      <right-toolbar :search="false" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <el-table ref="table" id="setTable" :data="tableList" v-loading="loading" stripe @selection-change="handleSelectionChange" height="100%" @row-click="rowClick" row-key="id" border default-expand-all :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column align="center" prop="partName" label="名称" />
        <el-table-column align="center" prop="enName" label="英文名" />
        <el-table-column align="center" prop="inputCode" label="输入码" />
        <el-table-column align="center" prop="partSort" label="序号" />
        <el-table-column align="center" prop="createdate" label="创建时间" />
        <el-table-column class-name="small-padding fixed-width" label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" v-hasPermi="['basicPACS:placeMaintain:edit']" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" v-hasPermi="['basicPACS:placeMaintain:delete']" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <addDialog ref="add" @getList="getList"></addDialog>
  </div>
</template>

<script>
import { getAutoCompleteData, delBasePart } from '@/api/PACS/basicPACS/place_maintain.js'
import addDialog from './add.vue'
export default {
  components: { addDialog },
  data() {
    return {
      queryParams: {
        name: '',
      },
      // 表格数据
      tableList: [],
      // 遮罩层
      loading: false,
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 选中数据
      selectRow: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getAutoCompleteData(this.queryParams)
        .then((res) => {
          this.tableList = this.handleTree(res.data, 'id', 'pid')
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 重置参数
    resetQuery() {
      this.queryParams.name = ''
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.table.clearSelection() //清空表格数据
        this.$refs.table.toggleRowSelection(selection.pop()) //最后一条数据
      } else {
        this.selectRow = {}
      }
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.table.clearSelection()
      this.$refs.table.toggleRowSelection(row)
      this.selectRow = row
    },
    // 添加
    handleAdd() {
      this.$refs.add.handleAdd()
    },
    // 编辑
    handleUpdate(row) {
      var id = row.id || this.selectRow.id
      this.$refs.add.handleUpdate(id)
    },
    // 删除按钮
    handleDelete(selection) {
      var row = selection.id ? selection : this.selectRow
      if (row.children && row.children.length > 0) {
        this.$modal
          .confirm('存在下级部位，您确定继续执行吗？', '提醒')
          .then(() => {
            var ids = this.getAllId(row)
            ids = ids.join()
            this.deleteOk(ids)
          })
          .catch(() => {})
      } else {
        //否则没有子节点
        this.deleteOk(row.id)
      }
    },
    // 删除
    deleteOk(ids) {
      this.$modal
        .confirm('您确定要删除该信息吗？', '提醒')
        .then(() => {
          return delBasePart(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 获取全部子类id
    getAllId(row) {
      var ids = []
      if (row.id !== null) {
        ids.push(row.id)
      }
      if (row.children && row.children.length > 0) {
        for (let i in row.children) {
          ids = [...ids, ...this.getAllId(row.children[i])]
        }
      }
      return ids
    },
  },
}
</script>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
