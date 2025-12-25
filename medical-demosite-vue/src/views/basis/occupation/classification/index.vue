<!-- 职业病名称分类 开发人：麦沃德科技 予安-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent
      v-show="showSearch">
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['basis:occupation:classification:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['basis:occupation:classification:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['basis:occupation:classification:remove']">删除 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <div class="table-box">
      <el-table class="table-main" v-loading="loading" :data="tableData" @selection-change="handleSelectionChange"
        stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="职业病分类代码" align="center" prop="occupationDiseastClassCode"
          show-overflow-tooltip />
        <el-table-column label="职业病分类名称" align="center" prop="occupationDiseastClassName"
          show-overflow-tooltip />
        <el-table-column label="输入码" align="center" prop="inputCode" show-overflow-tooltip />
        <el-table-column label="操作者" align="center" prop="dbUser" show-overflow-tooltip />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)"
              v-hasPermi="['basis:occupation:classification:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)"
              v-hasPermi="['basis:occupation:classification:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" v-loading="editLoading" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="职业病分类代码" prop="occupationDiseastClassCode">
          <el-input v-model="form.occupationDiseastClassCode" placeholder="请输入检查结论名称" />
        </el-form-item>
        <el-form-item label="职业病分类名称" prop="occupationDiseastClassName">
          <el-input v-model="form.occupationDiseastClassName" placeholder="请输入打印中文简称" @input="nameChange" />
        </el-form-item>
        <el-form-item label="输入码" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="输入检查结论名称后自动生成" style="pointer-events: none;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="resetEdit">重置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import pinyin from '@/utils/pinyin.js'
import {listData,editQueryData,deleteData,saveOrUpdateData,queryData} from '@/api/basis/occupation/classification.js'
export default {
  name: "Classification",
  data() {
    return {
      showSearch: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        inputCode: null,
      },
      // 总条数
      total: 50,
      // 表格数据
      tableData: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,

      // 表单参数
      form: {},
      // 表单校验
      rules: {occupationDiseastClassCode:[{required:true,message:"请输入职业病分类代码",trigger:"blur"}],
      occupationDiseastClassName:[{required:true,message:"请输入职业病分类名称",trigger:"blur"}],
      inputCode:[{required:true,message:"请输入输入码",trigger:"blur"}]
    },
      selection:{},
      editLoading:false,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询分类列表
    getList() {
      listData(this.queryParams).then(response=>{
        this.tableData = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      })

      this.$nextTick(() => { })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.queryParams.size = 10;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加'
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids
      let that = this;
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(() => {
          deleteData(postIds).then(()=>{
            this.getList();
            this.$modal.msgSuccess('删除成功');
          })
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.reset()
      this.open = true;
      this.title = '编辑';
      this.editLoading = true;
      let id = row.id || this.selection.id;
      this.editQueryData(id);
    },
    //编辑接口
    editQueryData(obj){
      editQueryData(obj).then(response=>{
        this.form = response.data;
        this.editLoading = false;
      })
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 表单重置
    reset() {
      this.form = {
        occupationDiseastClassName: null,
        occupationDiseastClassCode: null,
        inputCode: null,
      }
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if(selection.length == 1){
        this.selection = selection[0];
      }
    },
    //重置编辑
    resetEdit(){
      this.handleUpdate();
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            // updateRole(this.form).then(response => {
            saveOrUpdateData(this.form).then(()=>{
              this.$modal.msgSuccess("编辑成功");
              this.getList();
            })
            this.open = false
            //   this.getList();
            // });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            // addRole(this.form).then(response => {
            saveOrUpdateData(this.form).then(()=>{
              this.$modal.msgSuccess("添加成功");
							this.getList();
            })
            this.open = false
            //   this.getList();
            // });
          }
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>

</style>
