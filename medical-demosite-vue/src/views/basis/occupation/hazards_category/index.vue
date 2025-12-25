<!-- 个检预检回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px;" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检时间">
        <el-date-picker v-model="queryParams.date" style="width: 310px;" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="是否来检" prop="isInspect">
        <el-select v-model="queryParams.isInspect" placeholder="请选择" clearable style="width: 160px;">
          <el-option label="是" :value="0" />
          <el-option label="否" :value="1" />
          <el-option label="再通知" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检金额" prop="smoneyamountpaid,emoneyamountpaid" style="width: 360px;">
        <template>
          <el-input-number v-model="queryParams.minmoneyamountpaid" controls-position="right" :min="0" :max="100000"></el-input-number>
        </template> -
        <template>
          <el-input-number v-model="queryParams.maxmoneyamountpaid" controls-position="right" :min="0" :max="100000"></el-input-number>
        </template>
      </el-form-item>
      <el-form-item label="套餐名称" prop="examsuiteName">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 160px;" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开单医师" prop="idOpendoctor">
        <input-select :selectData="idoctorData" selectWidth="160" @change="idoctorDataChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd" :disabled="single" v-hasPermi="['custservice:customerservice:previewingTrack:add']">个检客户预检跟踪回访
        </el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['custservice:customerservice:previewingTrack:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" min-width="120px" align="center" />
        <el-table-column label="序列" type="index" min-width="120px" align="center" />
        <el-table-column label="是否来检" prop="isInspect" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isInspect == 0" type="success">是</el-tag>
            <el-tag v-if="scope.row.isInspect == 1" type="danger">否</el-tag>
            <el-tag v-if="scope.row.isInspect == 2">再通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="回访备注" prop="memo" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.idSex == 0">男</div>
            <div v-if="scope.row.idSex == 1">女</div>
            <div v-if="scope.row.idSex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="套餐名称" prop="examsuiteName" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="开单医师" prop="doctorapply" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检时间" prop="dateregister" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="来检次数" prop="count" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="体检项目" prop="examfeeitemName" min-width="200px" align="center" show-overflow-tooltip />
        <el-table-column label="体检结果" prop="verdict" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="加项明细" prop="additems" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="报告结论" prop="conclusions" min-width="200px" align="center" show-overflow-tooltip />
        <el-table-column label="体检金额" prop="moneyamountpaid" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="支付方式" prop="payways" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访人" prop="visitId" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="回访时间" prop="visitTime" min-width="120px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { listPrinttype } from "@/api/custservice/customerservice/previewing_track/previewing_track.js";
export default {
  name: 'Hazards_category',
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
      idoctorData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '医师姓名',//第二列标题
        third: '部门',//第三列标题（没有传空）
        fourth: '体检中心',//第四列标题（没有传空）
        url: '/member/previewingTrack/getKdys',//请求连接
        bindValue: '',
        firstName: 'inputCode',//接口返回值对应第一列的参数名
        secondName: 'name',//接口返回值对应第二列的参数名
        thirdName: 'dname',//接口返回值对应第三列的参数名
        fourthName: 'cname',//接口返回值对应第四列的参数名
        queryData: "key"
      },
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
        djdate: undefined,
        isInspect: undefined,
        maxmoneyamountpaid: undefined,
        minmoneyamountpaid: undefined,
        idOpendoctor: undefined
      },
      // 检查项目列表
      examList: [],
      // 表格展示数据
      tableList: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
      listPrinttype(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd(this.ids, this.rows)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row)
    },
    //开单医师
    idoctorDataChange(value) {
      this.queryParams.idOpendoctor = value.id;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/member/previewingTrack/export', {
        ...this.queryParams
      }, `个检预检回访.xlsx`)
    },
  },
};
</script>
