<!-- 职业病名称维护 开发人：麦沃德科技 予安-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch">
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
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['basis:occupation:nameSafeguard:add']">添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:nameSafeguard:edit']">编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:nameSafeguard:remove']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="职业病分类" align="center" prop="occupationDiseastClass" show-overflow-tooltip />
        <el-table-column label="职业病名称代码" align="center" prop="occupationDiseastCode" show-overflow-tooltip />
        <el-table-column label="职业病名称" align="center" prop="occupationDiseast" show-overflow-tooltip />
        <el-table-column label="输入码" align="center" prop="inputCode" show-overflow-tooltip />
        <el-table-column label="操作者" align="center" prop="dbUser" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #FFBA00;" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:nameSafeguard:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #FF2727;" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:nameSafeguard:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="职业病分类" prop="occupationDiseastClass ">
          <el-input v-model="form.occupationDiseastClass" placeholder="请输入职业病分类" />
          <!--<input-select :selectData="selectData" @change="selectChange"></input-select>-->
        </el-form-item>
        <el-form-item label="职业病名称代码" prop="occupationDiseastCode">
          <el-input v-model="form.occupationDiseastCode" placeholder="请输入检查结论名称" />
        </el-form-item>
        <el-form-item label="职业病名称" prop="occupationDiseast">
          <el-input v-model="form.occupationDiseast" placeholder="请输入打印中文简称" @input="nameChange" />
        </el-form-item>
        <el-form-item label="输入码" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="输入检查结论名称后自动生成" readonly />
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
import { listDiseast, getDiseast, addDiseast, updateDiseast, delDiseast } from "@/api/basis/namesafeguard";
import pinyin from "@/utils/pinyin.js";
export default {
  name: "Name_safeguard",
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
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 总条数
      total: 50,
      // 表格数据
      tableData: [],
      // ***************弹出层
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '职业病分类名称',//第二列标题
        url: '/occupationDiseast/getAutoCompleteData',//请求连接
        secondName: 'occupationDiseastClass',
        selectWidth: 350,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true;
      listDiseast(this.queryParams).then(({ data }) => {
        this.tableData = data.records
        this.total = data.total
        this.loading = false;
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.popData = undefined
      this.reset();
      this.open = true;
      this.title = "添加";
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delDiseast(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    // 编辑
    handleUpdate(row) {
      this.open = true;
      this.popData = undefined
      this.reset();
      const id = row.id || this.ids
      this.title = "编辑";
      this.popLoading = true
      getDiseast(id).then(response => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data;
      });
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 职业病分类筛选返回值
    selectChange(value) {
      this.form.occupationDiseastClassName = value.occupationDiseastClassName
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.form = {
        occupationDiseastClass: undefined,
        occupationDiseastCode: undefined,
        occupationDiseast: undefined,
        inputCode: undefined,
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDiseast(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList()
            });
          } else {
            addDiseast(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.open = false;
              this.getList()
            });
          }
        }
      });
    },
  }
};
</script>
<style lang="scss" scoped></style>
