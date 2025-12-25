<!-- 省市级平台数据对照 开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column comparisonMain">
    <div class="comparison-top">
      <div class="category">分类</div>
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="名称" prop="mz">
          <el-input v-model="queryParams.mz" placeholder="请输入名称" clearable style="width: 230px"
            @keyup.enter.native="handleQuery"></el-input>
        </el-form-item>
        <el-form-item label="输入码" prop="srm">
          <el-input v-model="queryParams.srm" placeholder="请输入输入码" clearable style="width: 230px"
            @keyup.enter.native="handleQuery"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-box">
      <el-tabs tab-position="left" @tab-click="tabClick" v-loading="loading" style="height: 100%;">
        <el-tab-pane :label="item.className" v-for="item in dataClass" :key="item.id" style="height: 100%;">
          <!-- 表格 -->
          <el-table :data="tableList" @selection-change="handleSelectionChange" height="100%" stripe v-loading="loadEnd" border>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" align="center" width="100px" type="index" />
            <el-table-column label="名称" prop="mc" align="center" />
            <el-table-column label="科室" prop="ks" align="center" show-overflow-tooltip
              v-if="item.className == '体检项目' || item.className == '收费项目'" />
            <el-table-column label="字典代码" prop="zddm" align="center" show-overflow-tooltip />
            <el-table-column label="字典名称" prop="zdmc" align="center" show-overflow-tooltip />
            <template v-if="item.className == '职业禁忌证' || item.className == '疑似职业病'">
              <el-table-column label="体检类别" prop="tjlb" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span>{{ ['上岗前', '在岗期间', '离岗时', '离岗后', '应急'][scope.row.tjlb] }}</span>
                </template>
              </el-table-column>
              <el-table-column label="职业危害因素" prop="whys" align="center" show-overflow-tooltip />
              <el-table-column label="检查结论" prop="jcjl" align="center" show-overflow-tooltip />
            </template>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
              <template slot-scope="scope">
                <el-button size="mini" type="text" style="color: #FFBA00;" @click="handleUpdate(scope.row)"
                  v-hasPermi="['basis:occupation:dataComparison:edit']">编辑字典关联</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body destroy-on-close
      :close-on-click-modal="false">
      <el-form ref="form" :rules="rule" :model="form" label-width="150px" hide-required-asterisk style="height:480px"
        v-loading="dialogLoading">
        <el-form-item label="现在匹配的字典ID" prop="dictionaryCode">
          <el-input v-model="form.dictionaryCode" :maxlength="0" readonly style="width:330px" />
        </el-form-item>
        <el-form-item label="现在匹配的字典名称" prop="dictionaryName">
          <el-input v-model="form.dictionaryName" :maxlength="0" readonly style="width:330px" />
        </el-form-item>
        <el-form-item label="数据字典" prop="dictionaryId">
          <search-select ref="select1" :selectData="selectData" @change="change1" :selectWidth="330">
          </search-select>
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
import { getClassApi, getListApi, getDetailsApi, addApi } from '@/api/basis/occupation/data_comparison.js'
export default {
  name: "Data_comparison",
  data() {
    var checkDictionaryId = (rule, value, callback) => {
      if (!this.form.dictionaryCode && !this.form.dictionaryId) {
        callback(new Error('数据字典不能为空'));
      } else {
        callback();
      }
    };
    return {
      // 数据分类
      dataClass: undefined,
      // 遮罩层
      loading: true,
      // 加载完成
      loadEnd: true,
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
        mz: '',
        srm: '',
      },
      // 表格数据
      tableList: [],

      // ############# 弹窗
      // 加载中
      dialogLoading: false,
      popData: undefined,
      // id
      bdid: undefined,
      // 表单参数
      form: {},
      // 弹出层标题
      title: "编辑",
      // 是否显示弹出层
      open: false,
      // 搜索选择器数据
      selectData: {
        placeholder: '请选择...',
        inputTitle: '名称',// 搜索标题
        inputPlaceholder: '请输入名称查询',// 搜索placeholder
        key: '编码',//第一列标题
        value: '名称',//第二列标题
        url: '/baseDictionary/searchDataDictionary',//请求连接
        firstName: 'dictionaryCode',//接口返回值对应第一列的参数名
        secondName: 'dictionaryName',//接口返回值对应第二列的参数名
        params: {}
      },
      rule: {
        dictionaryId: [
          { validator: checkDictionaryId, trigger: 'blur' }
        ]
      },
    }
  },
  computed: {},
  watch: {},
  created() {
    getClassApi().then(({ data }) => {
      this.loading = false
      this.dataClass = data
      this.queryParams.classCode = data[0].classCode
      this.getList()
    })
  },
  mounted() {},
  methods: {
    // tab 被选中时触发
    tabClick($event) {
      this.loading = true;
      this.queryParams.mz = ''
      this.queryParams.srm = ''
      this.queryParams.current = 1;
      this.dataClass.forEach(el => {
        if (el.className == $event.label) {
          this.queryParams.classCode = el.classCode
          this.loading = false;
          this.getList()
        }
      })
    },
    // 查询分类列表
    getList() {
      this.loadEnd = true;
      getListApi(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loadEnd = false
      });
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.queryParams.mz = ''
      this.queryParams.srm = ''
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 编辑
    handleUpdate(row) {
      this.popData = undefined
      this.reset();
      this.open = true;
      this.bdid = row.bdid
      this.dialogLoading = true
      this.selectData.params = {
        classCode: this.queryParams.classCode
      }
      getDetailsApi(row.bdid, this.queryParams.classCode).then(({ data }) => {
        if (data) {
          this.form = data
          this.form.nowId = this.form.dictionaryCode
          this.popData = JSON.parse(JSON.stringify(data))
        }
        this.dialogLoading = false
      })
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.$refs.select1.initData()
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.form = {
        dictionaryCode: undefined,
        dictionaryName: undefined,
        dictionaryId: undefined,
        nowId: undefined
      };
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 搜索选择器返回值
    change1(value) {
      this.form.dictionaryId = value.id || undefined
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let data = {
            dictionaryId: this.form.dictionaryId || '',
            medicalId: this.bdid,
            classCode: this.queryParams.classCode,
            nowId: this.form.nowId
          }
          addApi(data).then(() => {
            this.$modal.msgSuccess("保存匹配关系成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
  },
};
</script>
<style lang="scss">
.comparisonMain {

  .comparison-top {
    display: flex;
    align-items: center;

    .category {
      width: 110px;
      padding: 0 20px;
      margin-right: 20px;
      margin-bottom: 18px;
    }
  }

  .el-tabs--left .el-tabs__item.is-left {
    text-align: left;
  }

  .el-tabs__content {
    height: 100%;
  }
}
</style>