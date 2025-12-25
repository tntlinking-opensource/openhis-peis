<!-- 审批流配置  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item prop="typeName" label="工作流名称">
        <el-input style="width: 230px" v-model="queryParams.flowName" placeholder="请输入工作流名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['approval:approval_management:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['approval:approval_management:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['approval:approval_management:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    </el-row>
    <div class="table-box">
      <el-table ref="workList" border v-loading="loading" :data="workList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="分中心名字" align="center" prop="fzx" show-overflow-tooltip />
        <el-table-column label="工作流名称" align="center" prop="flowName" show-overflow-tooltip />
        <el-table-column label="类型名称" align="center" prop="typeName" show-overflow-tooltip />
        <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-tag type="info" v-if="scope.row.status == 0">关闭</el-tag>
            <el-tag type="primary" v-else>启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['approval:approval_management:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['approval:approval_management:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改工作流对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1060px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="工作流名称" prop="flowName">
          <el-input v-model="form.flowName" placeholder="请输入工作流名称" clearable style="width: 340px" />
        </el-form-item>
        <el-form-item label="分中心" prop="fzxid">
          <el-select v-model="form.fzxid" placeholder="请选择分中心" clearable style="width: 340px" @change="handleChangeFzx">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="typeFlag">
          <el-select v-model="form.typeFlag" placeholder="请选择类型" clearable style="width: 340px" @change="handleChangeType">
            <el-option v-for="item in typeOptions" :key="item.typeFlag" :label="item.typeName" :value="item.typeFlag" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" clearable style="width: 340px" @change="handleChangeType">
            <el-option label="启用" :value="1" />
            <el-option label="关闭" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="2" v-model="form.remark" placeholder="请输入备注" clearable style="width: 810px" />
        </el-form-item>
        <el-form-item label="流程">
          <el-button type="primary" size="mini" @click="addHierarchy">添加层级</el-button>
        </el-form-item>
        <div style="height: 250px; overflow-y: auto">
          <div v-for="(item, index) in form.itemList" :key="index">
            <el-form-item label="角色名称">
              <el-input v-model="item.itemName" placeholder="请输入角色名称"></el-input>
            </el-form-item>
            <el-form-item label="审批人">
              <input-select :selectData="selectData" :initialValue="item.userName" @change="selectChange($event, index)"></input-select>
            </el-form-item>
            <el-form-item label="审批层级">
              <el-input-number v-model="item.level" placeholder="请输入审批层级" controls-position="right" :min="1" :precision="0"></el-input-number>
            </el-form-item>
            <el-button type="primary" size="mini" @click="handleDeleteLevel(index)">删除</el-button>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListApi, getTypeData, addWorkflow, workflowDetails, editWorkflow, removeWorkflow } from '@/api/approval/approval_management.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
export default {
  name: 'Approval_management',
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
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
        flowName:''
      },
      // 总条数
      total: 0,
      // 维护表格数据
      workList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 遮罩层
      loading: true,
      // 分中心列表
      fzxOptions: [],
      // 类型列表
      typeOptions: [],
      // 审批人参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '审批人', //第二列标题
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        url: '/member/consulation/getAllUserData', //请求连接
        bindValue: '',
        selectWidth: '180',
      },
      // 表单参数
      form: {
        // 审批层级列表
        itemList: [],
      },
      // 表单校验
      rules: {
        flowName: [{ required: true, message: '工作流名称不能为空', trigger: 'change' }],
        fzxid: [{ required: true, message: '分中心不能为空', trigger: 'change' }],
        typeFlag: [{ required: true, message: '请选择类型', trigger: 'change' }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }],
      },
    }
  },
  created() {
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
    getTypeData().then(({ data }) => {
      this.typeOptions = data
    })
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
          this.workList = data.records
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
      if (col.type != 'selection') this.$refs.workList.clearSelection()
      this.$refs.workList.toggleRowSelection(row)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.title = '新增'
      this.open = true
      this.$nextTick(() => {
        this.form.itemList = [
          {
            itemName: '',
            level: 1,
            userName: '',
            userNo: '',
          },
        ]
      })
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？','提示')
        .then(function () {
          return removeWorkflow(ids)
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
      workflowDetails(id)
        .then((response) => {
          this.form = response.data
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
    // 修改类型
    handleChangeType(val) {
      this.typeOptions.forEach((el) => {
        if (el.typeFlag == val) {
          this.form.typeName = el.typeName
        }
      })
    },
    // 添加层级
    addHierarchy() {
      let obj = {
        itemName: '',
        level: this.form.itemList.length + 1,
        userName: '',
        userNo: '',
      }
      this.form.itemList.push(obj)
    },
    // 删除层级
    handleDeleteLevel(index) {
      this.$confirm('是否删除当前层级', '提示')
        .then(() => {
          this.$delete(this.form.itemList, index)
        })
        .catch(() => {})
    },
    // 修改审批人方法返回选中的值
    selectChange(value, index) {
      this.form.itemList[index].userName = value.username
      this.form.itemList[index].userNo = value.id
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        flowName: '',
        fzxid: '',
        fzx: '',
        remark: '',
        status: 0,
        typeFlag: '',
        typeName: '',
        itemList: [],
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let error = ''
          this.form.itemList.forEach((el) => {
            if (!el.itemName) {
              error = '角色名称不能为空'
            } else if (!el.userNo) {
              error = '审批人不能为空'
            }
          })
          // if (!error) {
          //   const result = Array.from(new Set(this.form.itemList.map((item) => item.level)))
          //   if (result.length != this.form.itemList.length) {
          //     error = '审批层级不能相同'
          //   }
          // }
          // 新增跨级提交验证逻辑
          if (!error && this.form.itemList.length > 0) {
            // 获取所有层级并去重排序
            const levels = [...new Set(this.form.itemList.map(item => item.level))].sort((a, b) => a - b);
            
            // 检查第一级是否为1
            if (levels[0] !== 1) {
              error = '审批层级必须从1开始，不能跨级提交';
            }
            
            // 检查是否连续（不能跨级）
            if (!error) {
              for (let i = 0; i < levels.length - 1; i++) {
                if (levels[i] + 1 !== levels[i + 1]) {
                  error = '审批层级不能跨级提交，请完善审批流程';
                  break;
                }
              }
            }
          }
          if (error) {
            this.$alert(error, '提示')
            return
          }
          let form = JSON.parse(JSON.stringify(this.form))
          form.itemList.sort((a, b) => {
            return a.level - b.level
          })
          if (form.itemList.length == 1) {
            form.itemList[0].levelFlag = 3
          } else {
            form.itemList.forEach((el, index) => {
              if (index == 0) {
                el.levelFlag = 1
              } else if (index == form.itemList.length - 1) {
                el.levelFlag = 2
              } else {
                el.levelFlag = 0
              }
            })
          }
          if (form.id != null) {
            editWorkflow(form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addWorkflow(form).then((response) => {
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
