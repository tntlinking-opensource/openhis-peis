<template>
  <el-dialog title="更新内容" :visible.sync="open" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-download" size="mini" @click="handleDownload">导入模板下载</el-button>
      </el-col>
    </el-row>
    <el-table ref="dataList" :data="dataList" align="center" border height="500px" stripe v-loading="loading" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="模块名称" align="center" prop="moduleName" show-overflow-tooltip> </el-table-column>
      <el-table-column label="修改类型" align="center" prop="modifyType" width="90" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ ['', '新增', '优化', '修复'][scope.row.modifyType] }}
        </template>
      </el-table-column>
      <el-table-column label="更新内容" align="center" prop="content" show-overflow-tooltip> </el-table-column>
      <el-table-column label="完成时间" align="center" prop="finishTime" show-overflow-tooltip> </el-table-column>
    </el-table>

    <!-- 添加、修改对话框 -->
    <el-dialog :title="title" :visible.sync="open2" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :inline="true" hide-required-asterisk v-loading="loading2">
        <el-form-item label="模块名称" prop="moduleName">
          <el-input v-model="form.moduleName" placeholder="请输入" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="修改类型" prop="modifyType">
          <el-select v-model="form.modifyType" placeholder="请选择" style="width: 220px">
            <el-option label="新增" :value="1"></el-option>
            <el-option label="优化" :value="2"></el-option>
            <el-option label="修复" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="完成时间" prop="finishTime">
          <el-date-picker v-model="form.finishTime" placeholder="请选择" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" style="width: 220px"></el-date-picker>
        </el-form-item>
        <el-form-item label="更新内容" prop="content" style="width: 100%">
          <el-input type="textarea" v-model="form.content" placeholder="请输入" style="width: 550px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 导入对话框 -->
    <import-dialog ref="importDialog" @getList="getList"></import-dialog>
  </el-dialog>
</template>

<script>
import importDialog from './import.vue'
import { getVersionPage, addVersionItem, detailsVersionItem, editVersionItem, deleteVersionItem } from '@/api/system/version_control/version_info'
export default {
  components: { importDialog },
  data() {
    return {
      // 打开对话框
      open: false,
      // 加载中
      loading: false,
      // 所选服务信息
      versionInfo: {},
      // 数据列表
      dataList: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      // ***********************更新内容弹窗
      // 标题
      title: '',
      // 打开对话框
      open2: false,
      // 加载中
      loading2: false,
      // 表单数据
      form: {},
      // 表单校验
      rules: {},
      // 系统服务列表
      serviceList: [],
    }
  },
  methods: {
    // 打开对话框
    showDialog(row) {
      this.versionInfo = row
      this.dataList = []
      this.open = true
      this.getList()
    },
    // 刷新
    resetQuery() {
      this.getList()
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      getVersionPage({
        versionId: this.versionInfo.id,
        size: 100,
      })
        .then(({ data }) => {
          this.dataList = data.records
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open2 = true
      this.title = '新增更新内容'
      this.reset()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.dataList.clearSelection()
      this.$refs.dataList.toggleRowSelection(row)
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.logId || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return deleteVersionItem(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.logId || this.ids.join(',')
      this.open2 = true
      this.title = '编辑版本信息'
      this.loading2 = true
      detailsVersionItem(id)
        .then((response) => {
          this.form = response.data
          this.loading2 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading2 = false
        })
    },
    // 导入
    handleImport() {
      this.$refs.importDialog.showDialog()
    },
    // 导入模板下载
    handleDownload(){
      this.downloadStatic('/static/stencil/update_record.xlsx', '系统更新记录模板.xlsx')
    },

    // *************************
    // 表单重置
    reset() {
      this.form = {
        moduleName: '',
        modifyType: '',
        finishTime: this.$getDate(),
        content: '',
        versionId: this.versionInfo.id,
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading2 = true
          if (this.form.logId != null) {
            editVersionItem(this.form)
              .then((response) => {
                this.$modal.msgSuccess('修改成功')
                this.open2 = false
                this.getList()
                this.loading2 = false
              })
              .catch((err) => {
                console.error(err)
                this.loading2 = false
              })
          } else {
            addVersionItem([this.form])
              .then((response) => {
                this.$modal.msgSuccess('新增成功')
                this.open2 = false
                this.getList()
                this.loading2 = false
              })
              .catch((err) => {
                console.error(err)
                this.loading2 = false
              })
          }
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open2 = false
      this.reset()
    },
  },
}
</script>
