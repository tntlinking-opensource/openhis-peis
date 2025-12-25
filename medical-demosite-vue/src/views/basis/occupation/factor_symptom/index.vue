<!-- 危害因素重点询问症状 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="症状代码" prop="symptomcode">
        <el-input v-model="queryParams.symptomcode" placeholder="请输入症状代码" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="症状名称" prop="symptom">
        <el-input v-model="queryParams.symptom" placeholder="请输入症状名称" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="危害因素" prop="harmname">
        <input-select ref="harmname" :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:occupation:factorSymptom:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:factorSymptom:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:factorSymptom:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="handleSync" v-hasPermi="['basis:occupation:factorSymptom:sync']">同步
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="危害因素" prop="mdHarmName" align="center" show-overflow-tooltip />
        <el-table-column label="体检类别" prop="disiaseType" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['上岗前', '在岗期间', '离职时', '离岗后', '应急'][scope.row.disiaseType] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="症状名称" prop="symptom" align="center" show-overflow-tooltip />
        <el-table-column label="症状代码" prop="symptomcode" align="center" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #FFBA00;" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:factorSymptom:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #FF2727;" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:factorSymptom:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 添加弹窗 -->
    <add-dialog ref="addWindow" @closeDialog="closeDialog"></add-dialog>
    <!-- 编辑弹窗 -->
    <el-dialog :title="title" :visible.sync="openUpdate" width="434px" append-to-body :close-on-click-modal="false">
      <el-form ref="formUpdate" :model="formUpdate" :rules="rules" label-width="100px" hide-required-asterisk v-loading="updataLoading">
        <el-form-item label="症状代码" prop="symptomcode">
          <el-input v-model="formUpdate.symptomcode" controls-position="right" :min="0" placeholder="请输入症状代码" readonly />
        </el-form-item>
        <el-form-item label="症状名称" prop="symptom">
          <el-input v-model="formUpdate.symptom" controls-position="right" :min="0" placeholder="请输入症状名称" readonly clearable />
        </el-form-item>
        <el-form-item label="危害因素名称" prop="mdHarmName">
          <input-select ref="mdHarmName" :selectData="selectData1" @change="selectChange2" :selectWidth=294 :initialValue=initialValue v-if="!updataLoading">
          </input-select>
        </el-form-item>
        <el-form-item label="体检类别" prop="disiaseType">
          <el-select v-model="formUpdate.disiaseType" filterable placeholder="请选择体检类别" style="width:294px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import addDialog from "./add.vue";
import { listSymptom, getSymptom, asyncSymptom, updateSymptom, delSymptom } from '@/api/basis/occupation/factor_symptom.js'
export default {
  name: "Factor_symptom",
  components: {
    addDialog,
  },
  props: [],
  data() {
    return {
      showSearch: true,
      // 遮罩层
      loading: true,
      // 危害因素筛选数据
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '危害因素名称',
        url: '/whysqzfw/getAllHarmname',
        bindValue: '', //初始值(必传)
        secondName: 'harmName',//接口返回值对应第二列的参数名(不传默认为'name')
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
        symptomcode: '',
        symptom: '',
        harmname: '',
      },
      // 表格数据
      tableList: [],

      // ############# 弹窗
      // 是否显示编辑弹出层
      openUpdate: false,
      // 编辑加载中
      updataLoading: true,
      // 编辑表单参数
      formUpdate: {},
      // 重置保存参数
      popData: undefined,
      // 危害因素初始值
      initialValue: undefined,
      // 弹出层标题
      title: "",
      // 表单校验
      rules: {
        danagerId: [
          { required: true, message: "危害名称不能为空", trigger: "change" }
        ],
        disiaseType: [
          { required: true, message: "体检类别不能为空", trigger: "change" }
        ],
      },
      options: [{ value: 0, label: '上岗前' }, { value: 1, label: '在岗期间' }, { value: 2, label: '离职时' }, { value: 3, label: '离岗后' }, { value: 4, label: '应急' },],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
  },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true;
      listSymptom(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false;
      })
    },
    // 危害因素选择结果
    selectChange1(value) {
      this.queryParams.harmname = value.harmName
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.$refs.harmname.initData()
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.reset();
      this.$nextTick(() => {
        this.$refs.addWindow.show()
      })
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(() => {
        delSymptom(postIds).then(() => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        })
      })
    },
    // 编辑
    handleUpdate(row) {
      let postIds = row.id || this.ids
      this.popData = undefined
      this.reset();
      this.openUpdate = true;
      this.title = "编辑";
      this.updataLoading = true
      getSymptom(postIds).then(({ data }) => {
        this.initialValue = data.mdHarmName
        this.formUpdate = data
        // 确保symptomId存在
        if (!this.formUpdate.symptomId && this.formUpdate.id) {
          this.formUpdate.symptomId = this.formUpdate.id;
        }
        this.popData = JSON.parse(JSON.stringify(data))
        this.updataLoading = false
      }).catch(() => {
        this.updataLoading = false
      })
    },
    // 同步
    handleSync() {
      // 同步
      this.$confirm('确定要同步吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let id = this.ids.join('')
        asyncSymptom(id).then(() => {
          this.$message({
            type: 'success',
            message: '同步成功!'
          })
        })
      }).catch(() => { })
    },
    // 添加保存回调
    closeDialog() {
      this.getList()
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.formUpdate = JSON.parse(JSON.stringify(this.popData))
        this.initialValue = this.formUpdate.mdHarmName
        this.$refs.mdHarmName.initData(this.initialValue)
        return
      }
      this.formUpdate = {
        symptomcode: undefined,
        symptom: undefined,
        factorName: undefined,
        danagerId: undefined,
        disiaseType: undefined,
        symptomId: undefined,
      };
      this.resetForm("formUpdate");
    },
    // 编辑中危害因素选择结果
    selectChange2(value) {
      // this.formUpdate.danagerId = value.ids
      // this.formUpdate.mdHarmName = value.harmName
      this.formUpdate.harmname = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      // this.open = false;
      this.openUpdate = false;
      this.reset();
    },
    // 提交按钮
    submitForm: function () {
      this.$refs["formUpdate"].validate(valid => {
        if (valid) {
          updateSymptom(this.formUpdate).then(() => {
            this.$modal.msgSuccess("编辑成功");
            this.openUpdate = false;
            this.getList();
          })
        }
      });
    },
  },
};
</script>
<style lang="scss"></style>