<!-- 样本录入 开发人：麦沃德科技 暴雨、予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建日期">
        <el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
          v-hasPermi="['funcdept:sample:sampleEntry:add']">录入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete"
          v-hasPermi="['funcdept:sample:sampleEntry:delete\n']" :disabled="multiple">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport"
          v-hasPermi="['funcdept:sample:sampleEntry:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="项目名称" prop="examfeeitemName" min-width="120px" align="center"
          show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="100px" align="center"
          show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="sex" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="创建人" prop="createId" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="创建时间" prop="createdate" min-width="120px" align="center"
          show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
  </div>
</template>
<script>
import { getListData, remove } from "@/api/funcdept/sample/sample_entry.js";
import addItems from './add'
export default {
  components: { addItems },
  props: [],
  data() {
    return {
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
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        patientname: undefined,
        date: [],
        startTime: undefined,
        endTime: undefined,
      },
      // 表格展示数据
      tableList: [],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0]
        this.queryParams.endTime = this.queryParams.date[1]
      }else{
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records;
        this.total = data.total;
        this.loading = false;
      });
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.queryParams.date = undefined
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete() {
      const ids = this.ids.join(',');
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return remove(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/handle/sampleDelivery/export', {
        ...this.queryParams
      }, `样本录入.xlsx`)
    },
  },
};
</script>
<style lang="scss">
</style>
