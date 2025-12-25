<!-- 危害因素诊断标准依据维护 开发人：麦沃德科技 予安/矢北-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch">
      <el-form-item label="危害因素" prop="harmId">
        <input-select ref="selectData" :selectData="selectData" @change="selectChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['basis:occupation:contrain:add']">添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:contrain:edit']">编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:contrain:remove']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="危害因素" align="center" prop="harmName" show-overflow-tooltip />
        <el-table-column label="在岗阶段" align="center" prop="poststage" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.poststage == 0">上岗前</span>
            <span v-if="scope.row.poststage == 1">在岗期间</span>
            <span v-if="scope.row.poststage == 2">离岗时</span>
            <span v-if="scope.row.poststage == 3">离岗后</span>
            <span v-if="scope.row.poststage == 4">应急</span>
          </template>
        </el-table-column>
        <el-table-column label="关键字" align="center" prop="keyword" show-overflow-tooltip />
        <el-table-column label="诊断依据" align="center" prop="diagnosis" show-overflow-tooltip width="300" />
        <el-table-column label="目标疾病(职业病)" align="center" prop="industrialDisease" show-overflow-tooltip />
        <el-table-column label="目标疾病(禁忌症)" align="center" prop="contraindication" show-overflow-tooltip />
        <el-table-column label="分中心" align="center" prop="fzx" show-overflow-tooltip width="150" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #FFBA00;" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:contrain:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #FF2727;" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:contrain:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <div style="max-height:630px;overflow-y:auto;padding-right: 10px;overflow-x: hidden;">
        <el-form ref="form" :model="form" :rules="rules" label-width="140px">
          <el-form-item label="危害因素名称" prop="harmId">
            <input-select :selectData="selectData1" :initialValue="this.form.harmName" @change="selectChange1" :selectWidth="330"></input-select>
          </el-form-item>
          <el-form-item label="在岗阶段" prop="poststage">
            <el-select v-model="form.poststage" placeholder="请选择" style="width:100%">
              <el-option label="上岗前" :value="0"> </el-option>
              <el-option label="在岗期间" :value="1"> </el-option>
              <el-option label="离岗时" :value="2"> </el-option>
              <el-option label="离岗后" :value="3"> </el-option>
              <el-option label="应急" :value="4"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="关键字" prop="keyword">
            <el-input v-model="form.keyword" style="width:330px" placeholder="请输入关键字" />
          </el-form-item>
          <el-form-item label="诊断依据" prop="diagnosis">
            <el-input v-model="form.diagnosis" style="width:330px" placeholder="请输入诊断依据" />
          </el-form-item>
          <el-form-item label="目标疾病(职业病)" prop="industrialDisease">
            <el-input v-model="form.industrialDisease" style="width:330px" placeholder="请输入目标疾病(职业病)" />
          </el-form-item>
          <el-form-item label="目标疾病(禁忌症)" prop="contraindication">
            <el-input v-model="form.contraindication" style="width:330px" placeholder="请输入目标疾病(禁忌症)" type="textarea" :rows="4" />
          </el-form-item>
          <el-form-item label="是否公共" prop="isPublic">
            <el-checkbox v-model="form.isPublic" :true-label="1" :false-label="0" style="width:330px" @change="checkboxChange" border size="medium"></el-checkbox>
          </el-form-item>
          <el-form-item label="分中心" prop="fzxIds">
            <el-select v-model="form.fzxIds" placeholder="请选择" style="width:330px" :disabled="this.form.isPublic != 1">
              <el-option label="QDITHC健康管理中心" value="1"> </el-option>
              <el-option label="QDITHC健康管理中心2" value="2"> </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import pinyin from "@/utils/pinyin.js";
import { listData, deleteData, addData, queryData } from "@/api/basis/occupation/contrain.js"
export default {
  name: "Contrain",
  data() {
    return {
      //是否能够被编辑
      isEdit: true,
      showSearch: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 危害因素筛选参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '危害因素',//第二列标题
        url: '/occupationDrug/getHarmData',//请求连接
        secondName: 'harmName',//接口返回值对应第二列的参数名(不传默认为'name')
        bindValue: '',
      },
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '危害因素',//第二列标题
        url: '/occupationDrug/getHarmData',//请求连接
        secondName: 'harmName',//接口返回值对应第二列的参数名(不传默认为'name')
        bindValue: '',
        selectWidth: 330,//选择器宽度（选填，默认230）不加px
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        harmName: undefined,
        inputCode: undefined,
      },
      // 总条数
      total: 50,
      // 表格数据
      tableData: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 表单参数
      form: {},
      // 表单校验
      rules: {
        harmId: [{ required: true, message: "请输入危害因素名称", trigger: "blur" },],
        poststage: [{ required: true, message: "请选择在岗阶段", trigger: "change" }],
        keyword: [{ required: true, message: "请输入关键字", trigger: "blur" }],
        diagnosis: [{ required: true, message: "请输入诊断依据", trigger: "blur" }],
        industrialDisease: [{ required: true, message: "请输入目标疾病(职业病)名称", trigger: "blur" }],
        contraindication: [{ required: true, message: "请输入目标疾病(禁忌病)名称", trigger: "blur" }],
      },
      inputCode: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.loading = false;
        this.tableData = response.data.records;
        this.total = response.data.total;
      });
    },
    // 危害因素返回选中的值
    selectChange(value) {
      this.queryParams.harmName = value.name;
      this.queryParams.inputCode = value.harmCode;
      this.harmName = value;
    },
    selectChange1(value) {
      this.form.harmName = value.harmName
      this.form.harmId = value.id;
      this.selectData1.bindValue = value.name
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.loading = true;
      let obj = {
        harmId: this.queryParams.inputCode,
        size: this.queryParams.size,
        current: this.queryParams.current,
      }
      listData(obj).then((response) => {
        this.loading = false;
        this.queryParams.current = response.data.current || 1;
        this.queryParams.size = response.data.size || 10;
        this.tableData = response.data.records || [];
        this.total = response.data.total || 0;
      }).catch(() => {
        this.loading = false;
      })
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.reset(0);
      this.open = true;
      this.title = "添加";
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids;
      let that = this;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        deleteData(postIds).then(response => {
          that.getList();
          that.$modal.msgSuccess("删除成功");
        }).catch(() => { });
      })
    },
    // 编辑
    handleUpdate(row) {
      let postIds = row.id || this.ids;
      this.form = this.selection;
      this.reset(1);
      this.open = true;
      this.title = "编辑";
      queryData(postIds).then(({ data }) => {
        this.form = data;
      })
    },
    // 是否勾选分中心
    checkboxChange() {
      if (this.form.isPublic == 1) {
        this.isEdit = true
      } else {
        this.isEdit = false
      }
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 表单重置
    reset(context) {
      this.form = {
        harmId: null,
        poststage: null,
        keyword: null,
        diagnosis: null,
        industrialDisease: null,
        contraindication: null,
        isPublic: true,
        fzxIds: null,
      };
      if (context == 0) {
        this.selectData1 = {
          placeholder: '请输入输入码选择',
          key: '输入码',//第一列标题
          value: '危害因素',//第二列标题
          url: '/whysqzfw/getAllHarmname',//请求连接
          secondName: 'harmName',//接口返回值对应第二列的参数名(不传默认为'name')
          bindValue: '',
          selectWidth: 330,//选择器宽度（选填，默认230）不加px
        }
      }
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      if (selection.length == 1) {
        this.selection = selection[0];
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            let obj = this.form;
            addData(obj).then(response => {
              this.$modal.msgSuccess("编辑成功");
              this.open = false;
              this.getList()
            })

          } else {
            let obj = {
              "contraindication": this.form.contraindication,
              "createdate": this.form.createdate,
              "diagnosis": this.form.diagnosis,
              "fzxIds": this.form.fzxIds,
              "harmId": this.form.harmId,
              "id": "",
              "industrialDisease": this.form.industrialDisease,
              "isPublic": 0,
              "keyword": this.form.keyword,
              "modifydate": this.form.modifydate,
              "poststage": 0
            };
            addData(obj).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.getList();
            }).catch(err => {
              this.$message.error(err.message)
            })
            this.open = false;
          }
        }
      });

    },
  }
};
</script>
<style lang="scss" scoped></style>