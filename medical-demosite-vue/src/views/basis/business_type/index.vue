<!-- 业务类型  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item prop="inputCode" label="输入码">
        <el-input style="width: 230px" v-model="queryParams.inputCode" placeholder="请输入输入码"></el-input>
      </el-form-item>
      <el-form-item prop="typeName" label="名称">
        <el-input style="width: 230px" v-model="queryParams.typeName" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" v-hasPermi="['search:businessType:add']" plain size="mini" icon="el-icon-plus" @click="showDialog">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" v-hasPermi="['search:businessType:edit']" plain size="mini" icon="el-icon-edit" :disabled="single" @click="editList">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" v-hasPermi="['search:businessType:delete']" plain size="mini" :disabled="multiple" @click="handleDelete" icon="el-icon-delete">删除 </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:businessType:export']">导出Excel</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!--  表格部分 -->
    <div class="table-box">
      <el-table ref="tableList" style="width: 100%" border :data="tableList" v-loading="loading" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="typeName" label="名称" align="center" />
        <el-table-column prop="createdate" label="记录时间" align="center" />
        <el-table-column prop="inputCode" label="输入码" align="center" />
        <el-table-column prop="grade" label="层级" align="center">
          <!-- <template slot-scope="scope">
            <el-tag v-if="scope.row.isPublic == 1">公共</el-tag>
            <el-tag v-if="scope.row.isPublic == 0" type="danger">非公共</el-tag>
          </template> -->
        </el-table-column>
        <el-table-column prop="tip" label="介绍" align="center" />
        <el-table-column prop="status" label="审核状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == -1" type="danger">删除</el-tag>
            <el-tag v-else-if="scope.row.status == 0" type="warning">下线</el-tag>
            <el-tag v-else-if="scope.row.status == 1" type="success">正常</el-tag>
            <span v-else> </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="editList(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 拍照弹窗 -->
    <addItem @getList="getList" ref="addItem"></addItem>
  </div>
</template>

<script>
import { listBasConclusion, listDelete } from '@/api/basis/business_type'
import addItem from './add.vue'
export default {
  name: 'BusinessType',
  ///弹窗载入动画
  props: [],
  components: { addItem },
  data() {
    return {
      ids: [],
      loading: true,
      popData: undefined,
      typeId: [],
      // 显示搜索条件
      showSearch: true,
      multiple: true,
      single: true,
      // 是否显示弹出层
      open: false,
      tableList: [],
      typeOptions: [
        { id: -1, text: '删除' },
        { id: 0, text: '下线' },
        { id: 1, text: '正常' },
      ],
      form: {
        name: undefined,
      },
      //查询参数
      queryParams: {
        current: 1,
        size: 20,
        regimentationNote: null,
        occupationDiagnosis: null,
        modifydate: undefined,
      },
      total: 0,
      // 表单效验
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.typeId = selection.map((item) => item.typeId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 删除
    handleDelete(row) {
      const ids = this.typeId || row.typeId
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return listDelete(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    ////添加按钮
    showDialog() {
      this.$refs.addItem.addItem()
    },
    // 是否公共变化
    isPublicChange() {
      if (this.form.isPublic) {
        this.selectData1.bindValue = undefined
      }
    },
    // 查询列表
    getList() {
      this.loading = true
      listBasConclusion(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 编辑
    editList(row) {
      const id = row.typeId || this.typeId[0]
      this.$refs.addItem.editList(id)
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    ///搜素
    handleQuery() {
      this.queryParams.current = 1

      this.getList()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/data/businessCat/export',
        {
          ...this.queryParams,
        },
        `业务类别列表.xlsx`
      )
    },
    ///消息提醒
    message() {
      this.$alert('请选择审核状态为待审核的记录！', '错误提示', {
        confirmButtonText: '确定',
        callback: (action) => {
          this.$message({
            type: 'info',
            message: '已取消审核',
          })
        },
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.table-box {
  margin-top: 20px;
}

h3 {
  font-weight: 600;
}
</style>
