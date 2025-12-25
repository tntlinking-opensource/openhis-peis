<!-- 体检项目检况统计  开发人：麦沃德科技 予安 -->
<template>
  <el-tabs type="card" class="app-container flex-direction-column statistics-items" @tab-click="tabClick">
    <el-tab-pane v-for="item in tabsData" :key="item.index" :label="item.title" class="flex-direction-column"
      style="height: 100%;">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
        <el-form-item label="选择日期" prop="dateRange">
          <el-date-picker v-model="queryParams.dateRange" type="daterange" range-separator="至" start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="科室" prop="divisionId" v-show="(item.index == 2)" style="margin-bottom: 0;">
          <input-select :selectData="selectData" @change="selectChange"></input-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport"
            v-hasPermi="['statistical:statisticsItems:export']">导出Excel</el-button>
        </el-col>
      </el-row>
      <!-- 表格 -->
      <div class="table-box" v-loading="loading">
        <el-table border v-if="!loading" :data="tableList" height="100%" stripe
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="接口代码" prop="interface_code" align="center" v-show="(item.index == 1)" />
          <el-table-column label="收费项目科室" prop="sfxmks" align="center" v-show="(item.index == 2)" />
          <el-table-column :label="item.index == 1 ? '检查项目' : '收费项目'" prop="examitem_name" align="center" />
          <el-table-column label="总人数" prop="totalPeople" align="center" />
          <el-table-column label="已检" prop="rs_yj" align="center" />
          <el-table-column label="补检" prop="rs_bj" align="center" />
          <el-table-column label="迟检" prop="rs_cj" align="center" />
          <el-table-column label="弃检" prop="rs_qj" align="center" />
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
        @pagination="getList" />
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import { listPrinttype, } from "@/api/basis/charge";
export default {
  components: {},
  props: [],
  data() {
    return {
      // tabs数据
      tabsData: [
        { title: '检验科', index: 1 },
        { title: '其他科室', index: 2 },
      ],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        dateRange: [],
        divisionId: undefined,
      },
      // 科室参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题 
        value: '科室',//第二列标题 
        url: '',//请求连接
        selectWidth: 160,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData: '',//向接口传递的参数名(不传默认为'inputCode')
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    // 切换标签页
    tabClick($event) {
     
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true;
      listPrinttype(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 科室选择返回值
    selectChange(value) {
      // this.参数 = value
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
    // 导出
    handleExport() {
      this.$modal.msg('导出')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
  },
};
</script>
<style lang="scss">
.statistics-items {
  .el-tabs__content {
    height: 100%;
  }
}
</style>