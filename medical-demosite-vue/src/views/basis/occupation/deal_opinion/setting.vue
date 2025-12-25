<template>
  <el-dialog title="检查结论设置" :visible.sync="open" width="1050px" append-to-body :close-on-click-modal="false">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['basis:occupation:dealOpinion:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:dealOpinion:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:dealOpinion:remove']">删除 </el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <el-table ref="tableData" v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" @row-click="rowClick" stripe border height="450px">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" align="center" width="100px" type="index" />
      <el-table-column label="序列号" align="center" prop="serialNo" show-overflow-tooltip />
      <el-table-column label="检查结论名称" align="center" prop="occupationSummary" show-overflow-tooltip />
      <el-table-column label="打印中文简称" align="center" prop="printForShort" show-overflow-tooltip />
      <el-table-column label="输入码" align="center" prop="inputCode" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:dealOpinion:edit']">编辑</el-button>
          <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:dealOpinion:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="dialogOpen" width="470px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px" v-loading="loadingDia">
        <el-form-item label="序列号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="请输入症状代码" />
        </el-form-item>
        <el-form-item label="检查结论名称" prop="occupationSummary">
          <el-input v-model="form.occupationSummary" placeholder="请输入检查结论名称" @input="nameChange" />
        </el-form-item>
        <el-form-item label="打印中文简称" prop="printForShort">
          <el-input v-model="form.printForShort" placeholder="请输入打印中文简称" />
        </el-form-item>
        <el-form-item label="输入码" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="输入检查结论名称后自动生成" readonly />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import pinyin from '@/utils/pinyin.js'
import { listSummary, addSummary, getSummary, delSummary } from '@/api/basis/occupation/conclusion.js'
export default {
  data() {
    return {
      // 弹窗是否显示
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        inputCode: undefined,
      },
      // 总条数
      total: 0,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 加载中
      loading: false,
      // 表格数据
      tableData: [],
      // 数据索引
      tableIndex: 0,

      // ******************内部添加修改对话框
      dialogOpen: false,
      // 对话框加载中
      loadingDia: false,
      // 标题
      title: '',
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    }
  },
  methods: {
    dialogShow() {
      this.open = true
      this.resetQuery()
    },
    // 查询分类列表
    getList() {
      this.loading = true
      listSummary(this.queryParams)
        .then(({ data }) => {
          data.records.forEach((el) => {
            el.tableIndex = this.tableIndex++
          })
          this.tableData = data.records
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
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.reset()
      this.dialogOpen = true
      this.title = '添加'
    },
    // 编辑
    handleUpdate() {
      this.reset()
      this.title = '编辑'
      this.dialogOpen = true
      const id = this.ids[0]
      this.loadingDia = true
      getSummary(id).then(({ data }) => {
        this.form = data
        this.loadingDia = false
      })
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？', '提示')
        .then(() => {
          this.loading = true
          delSummary(postIds).then(() => {
            this.$modal.msgSuccess('删除成功')
            this.loading = false
            this.getList()
          })
          // .catch((error) => {
          //   console.error(error)
          //   this.loading = false
          // })
        })
        .catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        serialNo: null,
        occupationSummary: null,
        printForShort: null,
        inputCode: null,
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loadingDia = true
          if (this.form.id != undefined) {
            addSummary(this.form)
              .then((response) => {
                this.$modal.msgSuccess('编辑成功')
                this.loadingDia = false
                this.dialogOpen = false
                this.getList()
              })
              .catch((error) => {
                console.error(error)
                this.loadingDia = false
              })
          } else {
            addSummary(this.form)
              .then((response) => {
                this.$modal.msgSuccess('添加成功')
                this.loadingDia = false
                this.dialogOpen = false
                this.getList()
              })
              .catch((error) => {
                console.error(error)
                this.loadingDia = false
              })
          }
        }
      })
    },
    // 内部对话框取消按钮
    cancel2() {
      this.dialogOpen = false
      this.reset()
    },
  },
}
</script>

<style></style>
