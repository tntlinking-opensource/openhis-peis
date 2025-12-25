<!-- 合并结论词维护 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="名称:" prop="mergeName">
        <el-input v-model="queryParams.mergeName" placeholder="请输入名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="输入码:" prop="inputCode">
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
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:mergeKeyword:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleUpdate" :disabled="single" v-hasPermi="['basis:mergeKeyword:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" v-hasPermi="['basis:mergeKeyword:remove']">删除 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" :row-key="getRowKeys" :expand-row-keys="expands" @expand-change="expandChange">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="#" type="expand" align="center" width="55">
          <template slot-scope="scope">
            <div style="padding: 0 30px">
              <el-table :data="dataList" align="center" border stripe v-loading="expandLoading" @selection-change="handleSelectionChange2">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column align="center" type="index" label="序列" width="55"> </el-table-column>
                <el-table-column align="center" prop="name" label="结论词名称"> </el-table-column>
                <el-table-column align="center" prop="depName" label="科室"> </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination v-show="total2 > 0" :total="total2" :page.sync="queryParams2.current" :limit.sync="queryParams2.size" @pagination="getLqryData(scope.row.id)" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="名称" prop="mergeName" align="center" show-overflow-tooltip />
        <el-table-column label="输入码" prop="inputCode" align="center" show-overflow-tooltip />
        <el-table-column label="总检建议" prop="suggestion" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 检查项目对话框 -->
    <el-dialog title="查看结论词" :visible.sync="showExam" width="1000px" append-to-body>
      <div style="min-height: 400px">
        <el-table border v-loading="loading" :data="examList" stripe>
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="结论词" prop="name" align="center" show-overflow-tooltip />
          <el-table-column label="科室" prop="jcxmlx" align="center" show-overflow-tooltip />
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="resetQuery">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items @getList="getList" ref="addItems"></add-items>
  </div>
</template>
<script>
import { listbasMerge, delbasMerge, getExpendData } from '@/api/basis/mergekeyword'
import { getLqryData } from '@/api/customer/public_pool.js'
import addItems from './add'
export default {
  name: 'MergeKeyword',
  components: { addItems },
  props: [],
  data() {
    return {
      selection: {},
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
        size: 20,
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
      examList: {},
      //表格扩展的数据
      queryParams2: {},
      // 表格展开时加载
      expandLoading: false,
      //扩展表格
      dataList: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
      // 要展开的行，数值的元素是row的key值
      expands: [],
      total2: 0,
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  mounted() { },
  methods: {
    // 有查询条件列表
    getList() {
      this.loading = true
      listbasMerge(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      }
    },
    // 展开行时发起请求
    expandChange(row, expandedRows) {
      if (expandedRows.length) {
        this.expands = []
        if (row) {
          this.expands.push(row.id)
          this.getLqryData(row.id)
        }
      } else {
        this.expands = []
      }
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
    // 取消按钮
    cancel() {
      this.showExam = false
      this.reset()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delbasMerge(ids)
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
    // 表格展开的多选
    handleSelectionChange2(selection) {
      this.xsId = selection.map((item) => item.id)
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // updatePrinttype(this.form).then(response => {
            this.$modal.msgSuccess('修改成功')
            this.showExam = false
            // 	this.getList();
            // });
          } else {
            // addPrinttype(this.form).then(response => {
            this.$modal.msgSuccess('添加成功')
            this.showExam = false
            // 	this.getList();
            // });
          }
        }
      })
    },
    // 获取领取人员的信息
    getLqryData(id) {
      this.queryParams2.id = id
      this.expandLoading = true
      getExpendData(this.queryParams2)
        .then((response) => {
          this.dataList = response.data.records
          this.expandLoading = false
        })
        .catch(() => {
          this.expandLoading = false
        })
    },
    // 获取row的key值
    getRowKeys(row) {
      return row.id
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep .el-table__expand-icon .el-icon-arrow-right:before {
  content: '+';
  color: #1890ff;
}
</style>
