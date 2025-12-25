<!-- 版本信息  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-refresh" size="mini" @click="resetQuery">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:versionInfo:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:versionInfo:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button class="zk-btn-style2" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['system:versionInfo:remove']">删除</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="dataList" border v-loading="loading" :data="dataList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick" :row-key="getRowKeys" :expand-row-keys="expands" @expand-change="expandChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="扩展内容" type="expand" align="center" width="90">
          <!-- 插槽格式报错的话--忽略 -->
          <template slot-scope="props">
            <div style="padding: 0 30px">
              <el-table :data="childList" height="400px" align="center" border stripe v-loading="expandLoading">
                <el-table-column label="序列" width="55" type="index" align="center" />
                <el-table-column label="版本ID" align="center" width="90" prop="id" show-overflow-tooltip />
                <el-table-column label="版本名称" align="center" prop="versionName" show-overflow-tooltip />
                <el-table-column label="版本号" align="center" prop="versionNumber" show-overflow-tooltip />
                <el-table-column label="状态" align="center" prop="status">
                  <template slot-scope="scope">
                    <el-tag type="danger" v-if="scope.row.status == -1">失效</el-tag>
                    <el-tag v-else-if="scope.row.status == 0">待更新</el-tag>
                    <el-tag v-else-if="scope.row.status == 1">部分已更新</el-tag>
                    <el-tag type="success" v-else>全部已更新</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
                <el-table-column label="版本标识" align="center" prop="versionFlag">
                  <template slot-scope="scope">
                    <el-tag type="info" v-if="scope.row.versionFlag == 0">历史</el-tag>
                    <el-tag type="success" v-else-if="scope.row.versionFlag == 1">最新</el-tag>
                    <el-tag v-else>草稿</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="350" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdateList(scope.row, 1)" v-hasPermi="['system:branch:addChild']">查看更新列表</el-button>
                    <el-button size="mini" type="text" icon="el-icon-plus" @click="handleUpdateList(scope.row, 2)" v-hasPermi="['system:branch:addChild']">添加更新内容</el-button>
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <!-- <pagination v-show="total2 > 0" :total="total2" :page.sync="queryParams2.current" :limit.sync="queryParams2.size" @pagination="getLqryData(scope.row.id)" /> -->
            </div>
          </template>
        </el-table-column>
        <el-table-column label="版本ID" align="center" width="90" prop="id" show-overflow-tooltip />
        <el-table-column label="版本名称" align="center" prop="versionName" show-overflow-tooltip />
        <el-table-column label="版本号" align="center" prop="versionNumber" show-overflow-tooltip />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.status == -1">失效</el-tag>
            <el-tag v-else-if="scope.row.status == 0">待更新</el-tag>
            <el-tag v-else-if="scope.row.status == 1">部分已更新</el-tag>
            <el-tag type="success" v-else>全部已更新</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdate" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)" v-hasPermi="['system:branch:addChild']">新增子版本</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改版本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="760px" class="sub-center report-setting" destroy-on-close append-to-body :close-on-click-modal="false" @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :inline="true" hide-required-asterisk v-loading="loading">
        <el-form-item label="父级" prop="parentId">
          <el-select v-model="form.parentId" placeholder="请选择父级" style="width: 220px">
            <el-option label="根目录" :value="0"></el-option>
            <el-option :label="item.versionNumber" :value="item.id" v-for="(item, index) in parentIdList" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="版本号" prop="versionNumber">
          <el-input v-model="form.versionNumber" placeholder="请输入版本号" clearable style="width: 220px" />
        </el-form-item>
        <el-form-item label="版本状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择版本状态" style="width: 220px">
            <el-option label="失效" :value="-1"></el-option>
            <el-option label="待更新" :value="0"></el-option>
            <el-option label="已更新部分" :value="1"></el-option>
            <el-option label="全部已更新" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本标识" prop="versionFlag">
          <el-select v-model="form.versionFlag" placeholder="请选择版本标识" style="width: 220px">
            <el-option label="历史" :value="0"></el-option>
            <el-option label="最新" :value="1"></el-option>
            <el-option label="草稿" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本描述" prop="notes">
          <el-input type="textarea" v-model="form.notes" placeholder="请输入版本描述" clearable style="width: 570px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 更新列表 -->
    <update-list ref="updateList"></update-list>
    <!-- 更新内容列表 -->
    <update-content ref="updateContent"></update-content>
  </div>
</template>

<script>
import { getListApi, addConfig, removeConfig, ConfigDetails, editConfig, getChildService } from '@/api/system/version_control/version_info'
import updateList from './updateList'
import updateContent from './updateContent'
export default {
  name: 'Version_info',
  components: {
    updateList,
    updateContent,
  },
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
        parentId: 0,
      },
      // 总条数
      total: 0,
      // 维护表格数据
      dataList: [],
      // 加载对应子版本
      expandLoading: false,
      // 展开页数据
      childList: [],
      // 要展开的行，数值的元素是row的key值
      expands: [],
      // 获取row的key值
      getRowKeys(row) {
        return row.id
      },

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
        versionName: [{ required: true, message: '版本名称不能为空', trigger: 'change' }],
        versionNumber: [{ required: true, message: '版本号不能为空', trigger: 'change' }],
      },
      // 父级列表
      parentIdList: [],
    }
  },
  created() {
    this.getList()
    getChildService({ parentId: 0 }).then((res) => {
      this.parentIdList = res.data
    })
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
          this.dataList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.parentId = row?.id || 0
      this.open = true
      this.title = this.parentId ? '新增子版本信息' : '新增版本信息'
      this.reset()
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        status: 0,
        versionName: '',
        versionNumber: '',
        versionFlag: 2,
        notes: '',
      }
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
      if (col.type != 'selection') this.$refs.dataList.clearSelection()
      this.$refs.dataList.toggleRowSelection(row)
    },
    // 展开查询
    expandChange(row, expandedRows) {
      if (expandedRows.length) {
        this.expands = []
        if (row) {
          this.expands.push(row.id)
          this.getChildData(row.id)
        }
      } else {
        this.expands = []
      }
    },
    // 获取子版本
    getChildData(parentId) {
      this.expandLoading = true
      this.childList = []
      getChildService({ parentId })
        .then((res) => {
          this.childList = res.data
          this.expandLoading = false
        })
        .catch((err) => {
          console.error(err)
          this.expandLoading = false
        })
    },
    // 查看对应更新列表
    handleUpdateList(row, type) {
      if (type == 1) {
        this.$refs.updateList.showDialog(row)
      } else {
        this.$refs.updateContent.showDialog(row)
      }
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return removeConfig(ids)
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
      this.title = '编辑版本信息'
      this.loading = true
      ConfigDetails(id)
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
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            editConfig(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addConfig(this.form).then((response) => {
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
