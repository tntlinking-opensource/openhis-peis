<!-- 操作说明  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:branch:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:branch:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['system:branch:remove']">删除</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="reportList" border v-loading="loading" :data="reportList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="文档名称" align="center" prop="documentname" show-overflow-tooltip />
        <el-table-column label="类型" align="center" prop="documentname" show-overflow-tooltip width="140">
          <template slot-scope="scope">
            {{ ['', '使用说明', '流程说明', '视频讲解'][scope.row.type] }}
          </template>
        </el-table-column>
        <el-table-column label="文档地址" align="center" prop="urlPdf" show-overflow-tooltip />
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip width="140" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改操作说明对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="文档名称" prop="documentname">
          <el-input v-model="form.documentname" placeholder="请输入文档名称" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 220px">
            <el-option label="使用说明" value="1"></el-option>
            <el-option label="流程说明" value="2"></el-option>
            <el-option label="视频讲解" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="对应文件" prop="urlPdf" style="display: block">
          <el-input v-model="form.urlPdf" placeholder="请上传文件" readonly style="width: 550px; margin-bottom: 8px" />
          <div class="setimg">
            <file-upload class="upload-report-setting" ref="uploadT" :uploadData="uploadData" :uploadModel="2" @handleUploading="handleUploading" @uploadFinish="uploadFinish" />
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListApi, addHelpDocument, removeHelpDocument, HelpDocumentDetails, editHelpDocument } from '@/api/system/operation_instruction'
export default {
  name: 'ReportSetting',
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
      },
      // 总条数
      total: 0,
      // 维护表格数据
      reportList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 上传图片配置参数
      uploadData: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1,
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        autoUpload: true,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        documentname: [{ required: true, message: '文档名称不能为空', trigger: 'change' }],
        type: [{ required: true, message: '清选择类型', trigger: 'change' }],
        urlPdf: [{ required: true, message: '请上传文件', trigger: 'change' }],
      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 刷新
    resetQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.reportList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true
      this.title = '新增操作说明'
    },
    // 上传中
    handleUploading() {
      this.$modal.msgWarning('上传中')
      this.loading = true
    },
    // 上传文件
    uploadFinish(value, res) {
      this.loading = false
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
        this.form.urlPdf = res.data
        this.form = JSON.parse(JSON.stringify(this.form))
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {}
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.reportList.clearSelection()
      this.$refs.reportList.toggleRowSelection(row)
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return removeHelpDocument(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids.join(',')
      this.open = true
      this.title = '编辑操作说明'
      this.loading = true
      HelpDocumentDetails(id)
        .then((response) => {
          this.form = response.data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 提交按钮 */
    submitForm() {
      if (this.loading) {
        this.$modal.msgWarning('文件上传中')
        return
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            editHelpDocument(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addHelpDocument(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss">
.sub-center {
  .el-input-number {
    .el-input__inner {
      text-align: left;
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
}
.report-setting {
  .setimg {
    display: flex;
    flex-direction: inherit;
    width: auto;
    img {
      background-color: transparent;
    }
    .btn {
      margin: 8px;
      width: 70px;
      height: 36px;
    }
  }
}
</style>

<style scoped>
.upload-report-setting /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

.upload-report-setting /deep/ .el-upload-list__item-name {
  display: none;
  /* max-width: 80px;
  padding: 0; */
}
</style>
