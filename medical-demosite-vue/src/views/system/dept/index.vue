<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:dept:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-refresh" size="mini" @click="handleKingDeeDept" v-hasPermi="['system:dept:kingDeeDept']">同步部门编码</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-if="refreshTable" v-loading="loading" :data="deptList" height="100%" row-key="deptId" stripe :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column prop="deptName" label="部门名称"></el-table-column>
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="pym" label="输入码" align="center"></el-table-column>
        <el-table-column prop="descript" label="部门描述" align="center"></el-table-column>
        <el-table-column prop="isgnks" label="是否功能科室" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isFunction == 0">否</span>
            <span v-else-if="scope.row.isFunction == 1">是</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column prop="dydxh" label="导引单序号" align="center"></el-table-column>
        <el-table-column prop="sjbggs" label="数据报告格式" align="center"></el-table-column>
        <el-table-column prop="jklx" label="接口类型" align="center"></el-table-column>
        <el-table-column prop="ksh" label="科室号" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:dept:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)" v-hasPermi="['system:dept:add']">新增</el-button>
            <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:dept:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" v-loading="editLoading">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0"> </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="form.deptName" @input="inputChange" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入码" prop="inputCode">
              <el-input :readonly="true" v-model="form.inputCode" placeholder="" />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col> -->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门描述" prop="description">
              <el-input v-model="form.description" placeholder="请输入部门描述" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.parentId !== 0">
            <el-form-item label="上级部门" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer" placeholder="选择上级部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否功能科室" prop="isFunction">
              <el-select v-model="form.isFunction" placeholder="请选择是否功能科室" style="width: 277px" maxlength="20">
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" style="width: 280px" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报告数据格式" prop="sjbggs">
              <el-select v-model="form.sjbggs" placeholder="请输入报告格式" style="width: 277px" maxlength="20">
                <el-option v-for="(item, index) in type" :key="index" :label="item.text" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查地点" prop="jcdd">
              <el-input v-model="form.jcdd" placeholder="请输入检查地点" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="接口类型" prop="jklx">
              <el-select v-model="form.jklx" placeholder="请选择接口类型" style="width: 277px" maxlength="20">
                <el-option v-for="(item, id) in interFaceType" :key="id" :label="item.text" :value="item.text"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室号" prop="ksh">
              <el-input v-model="form.ksh" placeholder="请输入科室号" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="导引单序号" prop="dydXh">
              <el-input v-model="form.dydXh" placeholder="请输入科室号" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报告序号" prop="reportSort">
              <el-input v-model="form.reportSort" placeholder="请输入报告序号" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="金蝶编号" prop="kingdeeAccountNo">
              <el-input v-model="form.kingdeeAccountNo" style="width: 277px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="金蝶列表" prop="divisions">
              <depSearchSelect :selectData="ksData" style="width: 277px" @change="ksChange"></depSearchSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value"> {{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="导引单备注" prop="dydMemo">
              <el-input type="textarea" resize="none" :rows="3" v-model="form.dydMemo" placeholder="请输入负责人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="上传图片">
              <div class="setimg">
                <image-upload id="upload" @submitFile="submitFile" ref="upload" :uploadData="uploadData" :typeChoose="1" @uploadFinish="uploadFinish" uploadWidth="100%" />
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--   -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild, upgradeDepartmentByKingdee } from '@/api/system/dept'
import depSearchSelect from '@/components/depSearchSelect'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import pinyin from '@/utils/pinyin.js'
export default {
  name: 'Dept',
  dicts: ['sys_normal_disable'],
  components: { Treeselect, depSearchSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      editLoading: false,
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined,
      },
      type: [
        { id: 1, text: '检验科检查' },
        { id: 2, text: '外送项目检查' },
        { id: 3, text: '一般检查' },
        { id: 4, text: 'C13检查' },
        { id: 5, text: '肺功能检查' },
        { id: 6, text: '无图像检查' },
        { id: 7, text: '图像检查' },
        { id: 8, text: '图像检查' },
        { id: 9, text: '电测听检查' },
        { id: 10, text: '骨密度检查' },
        { id: 11, text: '视力检查' },
        { id: 12, text: '健康检查' },
      ],
      interFaceType: [
        { id: 1, text: 'HIS' },
        { id: 2, text: 'LIS' },
        { id: 3, text: 'DR' },
        { id: 4, text: 'US' },
        { id: 5, text: 'CR' },
        { id: 6, text: 'CT' },
        { id: 7, text: 'EYE' },
        { id: 8, text: 'MR' },
      ],
      //科室数据
      ksData: {
        placeholder: '请输入输入码选择',
        key: '科室号', //第一列标题
        value: '科室名称', //第二列标题
        url: '/system/dept/getKingdeeData', //请求连接
        bindValue: '',
        firstName: 'ksh', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [{ required: true, message: '上级部门不能为空', trigger: 'blur' }],
        deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
        orderNum: [{ required: true, message: '显示排序不能为空', trigger: 'blur' }],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change'],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur',
          },
        ],
      },
      //上传图片弹窗
      upload: {
        title: '上传图片',
        open: false,
      },
      //上传对象
      uploadData: {
        url: '/common/upload',
        multiple: false, //是否可以上传多个
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {}, //上传时附带的额外参数
      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //确认上传图片
    submitFile() {
      var msg = this.$refs.upload.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload.handelUpload()
      } else {
        this.$alert(msg, '提示')
        this.loading.close()
      }
    },
    //接收图片返回地址
    uploadFinish(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.upload.open = false
      this.loading.close()
      this.loading = false
      this.form.imgpath = res.data
      return
    },
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listDept(this.queryParams).then((response) => {
        this.deptList = this.handleTree(response.data, 'deptId')
        this.loading = false
      })
    },
    //输入码
    inputChange(value) {
      this.form.inputCode = pinyin(value)
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      }
    },
    ksChange(value) {
      let id = []
      for (var i in value) {
        id.push(value[i].id)
      }
      this.form.divisions = id
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: '0',
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      if (row != undefined) {
        this.form.parentId = row.deptId
      }
      this.open = true
      this.title = '添加部门'
      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, 'deptId')
      })
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    // 同步部门编码
    handleKingDeeDept() {
      upgradeDepartmentByKingdee().then((res) => {
        this.$modal.msgSuccess(res.msg)
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.open = true
      this.title = '修改部门'
      this.editLoading = true
      getDept(row.deptId).then((response) => {
        this.editLoading = false
        this.form = response.data
        listDeptExcludeChild(row.deptId).then((response) => {
          this.deptOptions = this.handleTree(response.data, 'deptId')
          if (this.deptOptions.length == 0) {
            const noResultsOptions = { deptId: this.form.parentId, deptName: this.form.parentName, children: [] }
            this.deptOptions.push(noResultsOptions)
          }
        })
      })
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.deptId != undefined) {
            updateDept(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addDept(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal
        .confirm('是否确认删除名称为"' + row.deptName + '"的数据项？')
        .then(function () {
          return delDept(row.deptId)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
  },
}
</script>

<style lang="scss" scoped>
.setimg {
  display: flex;
  flex-direction: column;
  align-items: center;

  img {
    width: 100px;
    height: 100px;
    background: rgba($color: #000000, $alpha: 0.3);
  }

  .btn {
    margin: 8px;
    width: 70px;
    height: 36px;
  }
}
</style>

<style scoped>
#upload /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
  max-width: 80px;
}
</style>
