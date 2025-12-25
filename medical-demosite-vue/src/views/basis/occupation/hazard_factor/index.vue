<!--  职业健康危害因素 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="因素代码" prop="harmCode">
        <el-input v-model="queryParams.harmCode" placeholder="请输入因素代码" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="类别" prop="harmClass">
        <input-select ref="harmClass1" :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="危害因素输入码" prop="inputCode">
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
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:occupation:hazardFactor:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:hazardFactor:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:hazardFactor:remove']">删除 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <!-- 表格 -->
      <el-table ref="tableList" v-loading="loading" :data="tableList" stripe border height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="因素代码" prop="harmCode" align="center" />
        <el-table-column label="因素名称" prop="harmName" align="center" show-overflow-tooltip width="300px">
          <template slot-scope="scope">
            <span>{{ scope.row.harmName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="类别" prop="mzhcHarmClass" align="center" show-overflow-tooltip />
        <el-table-column label="输入码" prop="inputCode" align="center" show-overflow-tooltip />
        <el-table-column label="分中心" prop="fzx" align="center" show-overflow-tooltip />
        <el-table-column label="对健康的影响" prop="affect" align="center" show-overflow-tooltip width="508">
          <template slot-scope="scope">
            <span>{{ scope.row.affect }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人" prop="creater" align="center" show-overflow-tooltip />
        <el-table-column label="初创中心" prop="createFzx" align="center" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:hazardFactor:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:hazardFactor:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 添加或编辑弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="460px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" hide-required-asterisk v-loading="popLoading">
        <el-form-item label="因素代码" prop="harmCode">
          <el-input v-model="form.harmCode" clearable placeholder="请输入因素代码" />
        </el-form-item>
        <el-form-item label="因素名称" prop="harmName">
          <el-input v-model="form.harmName" clearable placeholder="请输入因素名称" @input="nameChange" />
        </el-form-item>
        <el-form-item label="类别" prop="harmClass">
          <input-select ref="harmClass" :selectData="selectData1" @change="selectChange2" :selectWidth="320" v-if="!popLoading" :initialValue="initialValue"></input-select>
        </el-form-item>
        <el-form-item label="输入码" prop="inputCode">
          <el-input v-model="form.inputCode" clearable placeholder="输入因素名称后自动生成" readonly />
        </el-form-item>
        <el-form-item label="是否公共" prop="isPublic">
          <el-checkbox v-model="form.isPublic" border size="medium" @change="isPublicChange"></el-checkbox>
        </el-form-item>
        <el-form-item label="分中心" prop="fzxIds">
          <el-select v-model="form.fzxIds" multiple placeholder="请选择分中心" :disabled="form.isPublic == 1" style="width: 320px">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="对健康的影响" prop="affect">
          <el-input type="textarea" :rows="6" v-model="form.affect" clearable placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import pinyin from '@/utils/pinyin.js'
import { listHarm, addHarm, delHarm, getHarm } from '@/api/basis/occupation/hazard_factor.js'
import { listBranch } from '@/api/system/branch.js'
export default {
  name: "Hazard_factor",
  data() {
    var validatePass = (rule, value, callback) => {
      if (this.form.isPublic == false && !value) {
        callback(new Error('请选择分中心'))
      } else {
        callback()
      }
    }
    return {
      showSearch: true,
      // 遮罩层
      loading: false,
      // 分类筛选数据
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '分类名称',
        url: '/zyHarmClass/getHarmclassData',
        bindValue: '', //初始值(必传)
      },
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        harmCode: '',
        harmClass: '',
        inputCode: '',
      },
      // 表格数据
      tableList: [],

      // ############# 弹窗
      popLoading: false,
      popData: undefined,
      // 表单参数
      form: {
        isPublic: undefined,
      },
      // 类别初始值
      initialValue: undefined,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        harmCode: [{ required: true, message: '因素代码不能为空', trigger: 'change' }],
        harmName: [{ required: true, message: '因素名称不能为空', trigger: 'change' }],
        harmClass: [{ required: true, message: '危害因素分类不能为空', trigger: 'submit' }],
        affect: [{ required: true, message: '对健康的影响不能为空', trigger: 'change' }],
        fzxIds: [{ validator: validatePass, trigger: 'blur' }],
      },
      // 分中心选项
      fzxOptions: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true
      listHarm(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 类别选择结果
    selectChange1(value) {
      this.queryParams.harmClass = value.id
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
      this.popData = undefined
      this.initialValue = undefined
      this.reset()
      this.$nextTick(() => {
        this.open = true
        this.title = '添加'
      })
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除该数据项？').then(() => {
        delHarm(postIds).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
      })
    },
    // 编辑
    handleUpdate(row) {
      const id = row.id || this.ids
      this.popData = undefined
      this.reset()
      this.open = true
      this.title = '编辑'
      this.popLoading = true
      getHarm(id).then(({ data }) => {
        if (data.isPublic == 1) {
          data.isPublic = true
        } else {
          data.isPublic = false
        }
        data.fzxIds = data.fzxIds ? data.fzxIds.split(',') : []
        this.form = data
        this.initialValue = data.mzhcHarmClass
        this.popData = JSON.parse(JSON.stringify(data))
        this.popLoading = false
      })
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 对话框类别选择结果
    selectChange2(value) {
      this.form.harmClass = value.id
      if (this.$refs.form && value.id) {
        this.$refs.form.clearValidate(['harmClass'])
      }
    },
    // 是否公共变化
    isPublicChange() {
      if (this.form.isPublic) {
        this.form.fzxIds = undefined
      }
    },
    // 表单重置
    reset() {
      listBranch({
        current: 1,
        size: 99999,
      }).then(({ data }) => {
        this.fzxOptions = data.records
        this.fzxOptions.forEach((el) => {
          el.fzxIds = el.id.toString()
        })
      })
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.form = {
        harmCode: null,
        harmName: null,
        harmClass: null,
        inputCode: null,
        isPublic: true,
        fzxIds: null,
        affect: null,
      }
      if (this.$refs.form) {
        this.$refs.form.resetFields()
      }
      this.$nextTick(() => {
        if (this.$refs.harmClass) {
          this.$refs.harmClass.initData(undefined)
        }
      })
    },
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
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.isPublic) {
            this.form.isPublic = 1
          } else {
            this.form.isPublic = 0
          }
          let form = JSON.parse(JSON.stringify(this.form))
          if (form.fzxIds) {
            form.fzxIds = form.fzxIds.join(',')
          }
          addHarm(form).then(() => {
            if (this.form.id != undefined) {
              this.$modal.msgSuccess('编辑成功')
            } else {
              this.$modal.msgSuccess('添加成功')
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
