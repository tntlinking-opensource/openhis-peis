<!-- 套餐类型  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['applet:combo_type:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['applet:combo_type:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['applet:combo_type:remove']">删除</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="tableDate" border v-loading="loading" :data="tableDate" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="名称" align="center" prop="name" show-overflow-tooltip />
        <el-table-column label="描述" align="center" prop="note" show-overflow-tooltip />
        <el-table-column label="创建时间" align="center" prop="modifydate" show-overflow-tooltip />
        <el-table-column label="排序" align="center" prop="seq" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['applet:combo_type:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['applet:combo_type:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改工作流对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1060px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" clearable style="width: 340px" />
        </el-form-item>
        <el-form-item label="排序" prop="seq">
          <el-input type="number" v-model="form.seq" placeholder="请输入排序" clearable style="width: 340px" />
        </el-form-item>
        <el-form-item label="描述" prop="note">
          <el-input type="textarea" :rows="2" v-model="form.note" placeholder="请输入描述" clearable style="width: 810px" />
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
import { getListApi, saOrUp, getDetailsApi, deleteApi } from '@/api/applet/combo_type.js'
export default {
  name: 'Combo_type',
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
      tableDate: [],

      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: '名称不能为空', trigger: 'change' }],
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
    /** 查询工作流列表 */
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.tableDate = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableDate.clearSelection()
      this.$refs.tableDate.toggleRowSelection(row)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.title = '新增'
      this.open = true
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？', '提示')
        .then(function () {
          return deleteApi(ids)
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
      const id = row.id || this.ids.join(',')
      this.open = true
      this.title = '编辑'
      this.loading = true
      getDetailsApi(id)
        .then((response) => {
          this.form = response.data
          this.form.branchId = this.form.branchId.toString()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 修改分中心
    handleChangeFzx(val) {
      this.fzxOptions.forEach((el) => {
        if (el.branchId == val) {
          this.form.fzx = el.fzx
        }
      })
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        name: '',
        seq: '',
        note: '',
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.imgUrl == '') {
        this.$modal.msgError('请上传图片！')
        return
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          saOrUp(this.form).then((response) => {
            if (this.form.id != null) {
              this.$modal.msgSuccess('修改成功')
            } else {
              this.$modal.msgSuccess('新增成功')
            }
            this.open = false
            this.getList()
          })
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
