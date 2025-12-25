<!-- 危害因素标准范围维护 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="危害因素" prop="harmName">
        <input-select :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="项目名称" prop="jcId">
        <input-select :selectData="selectData2" @change="selectChange2"></input-select>
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
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:hazardFactor:remove']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div class="table-box">
      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="危害因素编码" prop="inputCode" align="center" />
        <el-table-column label="危害因素" prop="harmName" align="center" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="项目名称" prop="jcName" align="center" show-overflow-tooltip />
        <el-table-column label="接口编码" prop="lisId" align="center" show-overflow-tooltip />
        <el-table-column label="职业/综合" prop="zyorjk" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['职业', '综合'][scope.row.zyorjk] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" align="center" show-overflow-tooltip />
        <el-table-column label="男女标识" prop="sex" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['男', '女', '通用'][scope.row.sex] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="男取值范围下限" prop="scoperFloor" align="center" show-overflow-tooltip />
        <el-table-column label="男取值范围上限" prop="scopeUpper" align="center" show-overflow-tooltip />
        <el-table-column label="女取值范围下限" prop="gscoperfloor" align="center" show-overflow-tooltip />
        <el-table-column label="女取值范围上限" prop="gscopeupper" align="center" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #FFBA00;" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:hazardFactor:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #FF2727;" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:hazardFactor:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 添加或编辑弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="490px" append-to-body :close-on-click-modal="false" destroy-on-close>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" hide-required-asterisk v-loading="popLoading">
        <el-form-item label="危害因素名称" prop="harmName">
          <input-select ref="select3" :selectData="selectData3" :initialValue="form.harmName" @change="selectChange3"></input-select>
        </el-form-item>
        <el-form-item label="危害因素编码" prop="inputCode">
          <el-input v-model="form.inputCode" controls-position="right" placeholder="输入危害因素名称后自动生成" readonly />
        </el-form-item>
        <el-form-item label="项目名称" prop="jcId">
          <input-select ref="select4" :selectData="selectData4" :initialValue="form.jcName" @change="selectChange4"></input-select>
        </el-form-item>
        <el-form-item label="接口编码" prop="lisId">
          <el-input v-model="form.lisId" controls-position="right" placeholder="输入项目名称后自动生成" readonly />
        </el-form-item>
        <el-form-item label="职业/综合" prop="zyorjk">
          <el-select v-model="form.zyorjk" placeholder="请选择职业/综合" style="width:100%">
            <el-option label="职业" :value="0"> </el-option>
            <el-option label="综合" :value="1"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位名称" prop="unit">
          <el-input v-model="form.unit" controls-position="right" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="男女标识" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择男女标识" style="width:100%" @change="sexChange">
            <el-option label="男" :value="0"> </el-option>
            <el-option label="女" :value="1"> </el-option>
            <el-option label="通用" :value="2"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="男取值范围上限" prop="scopeUpper">
          <el-input-number v-model="form.scopeUpper" controls-position="right" :min="0" :disabled="form.sex == 1" style="width: 330px;" />
        </el-form-item>
        <el-form-item label="男取值范围下限" prop="scoperFloor">
          <el-input-number v-model="form.scoperFloor" controls-position="right" :min="0" :disabled="form.sex == 1" style="width: 330px;" />
        </el-form-item>
        <el-form-item label="女取值范围上限" prop="gscopeupper">
          <el-input-number v-model="form.gscopeupper" controls-position="right" :min="0" :disabled="form.sex == 0" style="width: 330px;" />
        </el-form-item>
        <el-form-item label="女取值范围下限" prop="gscoperfloor">
          <el-input-number v-model="form.gscoperfloor" controls-position="right" :min="0" :disabled="form.sex == 0" style="width: 330px;" />
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
import pinyin from "@/utils/pinyin.js";
import { listWhysqzfw, addWhysqzfw, delWhysqzfw, getWhysqzfw, getWhysData, getInterFaceCode } from "@/api/basis/occupation/factor_safeguard.js";
export default {
  name: "Factor_safeguard",
  data() {
    var boyValidatePass = (rule, value, callback) => {
      if (this.form.sex != 1 && this.form.scoperFloor > this.form.scopeUpper) {
        callback(new Error('取值范围上限不能小于下限'));
      } else {
        callback();
      }
    };
    var girlValidatePass = (rule, value, callback) => {
      if (this.form.sex != 0 && this.form.gscoperfloor > this.form.gscopeupper) {
        callback(new Error('取值范围上限不能小于下限'));
      } else {
        callback();
      }
    };
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
      // 项目名称筛选数据
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '项目名称名称',
        url: '/whysqzfw/getAllJcid',
        bindValue: '', //初始值(必传)
        secondName: 'examitemName',//接口返回值对应第二列的参数名(不传默认为'name')
      },
      //查询接口编码的信息
      queryInterFace(data) {
        const query = {
          data: data
        }
        getInterFaceCode(query).then(response => {

        })
      },
      // 选中数组
      ids: [],
      // 总条数
      total: 50,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        harmName: '',
        jcId: '',
      },
      //危害因素数组
      whys: [],
      // 排检表格数据
      tableList: [],

      // ############# 弹窗
      popLoading: false,
      popData: undefined,
      // 危害因素筛选数据
      selectData3: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '危害因素名称',
        url: '/whysqzfw/getAllHarmname',
        selectWidth: 330,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        secondName: 'harmName',//接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 项目名称筛选数据
      selectData4: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '项目名称名称',
        third: '检查项目类型',//第三列标题（没有传空）
        url: '/whysqzfw/getAllJcid',
        selectWidth: 330,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        secondName: 'examitemName',//接口返回值对应第二列的参数名(不传默认为'name')
        thirdName: 'idExamitemtype'
      },
      //危害因素
      whyscsz: undefined,
      // 表单参数
      form: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        harmName: [
          { required: true, message: "因素名称不能为空", trigger: "change" }
        ],
        jcId: [
          { required: true, message: "项目名称不能为空", trigger: "change" }
        ],
        zyorjk: [
          { required: true, message: "职业/综合不能为空", trigger: "change" }
        ],
        unit: [
          { required: true, message: "单位名称不能为空", trigger: "change" }
        ],
        sex: [
          { required: true, message: "男女标识不能为空", trigger: "change" }
        ],
        scopeUpper: [
          { validator: boyValidatePass, trigger: "change" }
        ],
        gscopeupper: [
          { validator: girlValidatePass, trigger: "change" }
        ],
      },
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList()
    getWhysData().then(response => {
      this.whys = response.data.records
    })
  },
  methods: {
    // 查询分类列表
    getList() {
      this.loading = true;
      listWhysqzfw(this.queryParams).then(({ data }) => {
        this.tableList = data.records
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
    // 危害因素选择结果
    selectChange1(value) {
      this.queryParams.harmName = value.id
    },
    // 项目名称选择结果
    selectChange2(value) {
      this.queryParams.jcId = value.id
    },

    // **********************弹窗
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 危害因素选择结果
    selectChange3(value) {
      this.form.harmName = value.id
      this.form.inputCode = value.inputCode
    },
    // 项目名称选择结果
    selectChange4(value) {
      this.form.jcId = value.id
      this.form.jcName = value.examitemName
      this.form.lisId = value.interfaceCode || ''
      this.form.lisId = value.inputCode
    },
    // 男女标识改变
    sexChange($event) {

      if ($event == 0) {
        this.form.gscoperfloor = 0
        this.form.gscopeupper = 0
      } else if ($event == 1) {
        this.form.scoperFloor = 0
        this.form.scopeUpper = 0
      }
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
        return delWhysqzfw(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    // 编辑
    handleUpdate(row) {
      this.popData = undefined
      this.reset();
      const id = row.id || this.ids
      this.open = true;
      this.title = "编辑";
      this.popLoading = true
      getWhysqzfw(id).then(({ data }) => {
        this.popData = JSON.parse(JSON.stringify(data))
        this.popLoading = false
        this.form = data;
        for (var i in this.whys) {
          if (this.whys[i].id == this.form.harmName) {


            this.form.harmNameID = this.whys[i].harmName

          }
        }
        // this.$refs.select3.initData(data.)

      })
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.form = {
        harmName: undefined,
        inputCode: undefined,
        jcId: undefined,
        lisId: undefined,
        zyorjk: undefined,
        unit: undefined,
        sex: undefined,
        scoperFloor: 0,
        scopeUpper: 0,
        gscoperfloor: 0,
        gscopeupper: 0,
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
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addWhysqzfw(this.form).then(({ data }) => {
            this.open = false;
            this.getList();
            if (this.form.id != undefined) {
              this.$modal.msgSuccess("编辑成功");
            } else {
              this.$modal.msgSuccess("添加成功");
            }
          })
        }
      });
    },
  },
};
</script>