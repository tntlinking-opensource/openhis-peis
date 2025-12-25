<!-- 报告存储配置 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:reportStorage:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleUpdate" :disabled="single" v-hasPermi="['basis:reportStorage:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['basis:reportStorage:remove']">删除 </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-house" v-hasPermi="['basis:reportStorage:apply']">应用
				</el-button>
			</el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="标识" fixed prop="flag" align="center" show-overflow-tooltip />
        <el-table-column label="AETITLE" fixed prop="aetitle" align="center" show-overflow-tooltip />
        <el-table-column label="IP" prop="ip" align="center" show-overflow-tooltip />
        <el-table-column label="端口" prop="port" align="center" show-overflow-tooltip />
        <el-table-column label="实际路径" prop="realPath" align="center" show-overflow-tooltip />
        <el-table-column label="映射路径" prop="mappingPath" align="center" show-overflow-tooltip />
        <el-table-column label="访问路径" prop="visitPath" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="memo" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:reportStorage:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:reportStorage:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
  </div>
</template>
<script>
import { listBaseAttachmentConfig, delBaseAttachmentConfig } from '@/api/basis/reportstorage'
import addItems from './add'
export default {
  name: 'Report_storage',
  components: { addItems },
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
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
      },
      // 表格展示数据
      tableList: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() { },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 查询列表
    getList() {
      this.loading = true
      listBaseAttachmentConfig(this.queryParams).then((response) => {
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
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delBaseAttachmentConfig(ids)
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row, this.ids)
    },
  },
}
</script>
