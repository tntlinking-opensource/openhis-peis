<!-- 预约来检回访 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="预约时间">
        <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="本次是否来检" prop="isInspects">
        <el-select v-model="queryParams.isInspects" placeholder="请选择" clearable style="width: 180px;">
          <el-option label="是" :value="0" />
          <el-option label="否" :value="1" />
          <el-option label="再通知" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd" :disabled="single"
                   v-hasPermi="['custservice:customerservice:appointReturn:add']">预约来捡回访
        </el-button>
      </el-col>
      <el-col :span="1">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport"
                   v-hasPermi="['custservice:customerservice:appointReturn:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe  @selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" align="center" />
				<el-table-column label="序列"  type="index"   align="center" />
        <el-table-column label="本次是否来检"  prop="isinspects"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isinspects == 0" type="success">是</el-tag>
            <el-tag v-if="scope.row.isinspects == 1" type="danger">否</el-tag>
            <el-tag v-if="scope.row.isinspects == 2">再通知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="本次预约时间"  prop="inspectTimes"   align="center" show-overflow-tooltip />
        <el-table-column label="姓名"  prop="patientname"   align="center" show-overflow-tooltip />
        <el-table-column label="性别"  prop="idSex"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.idSex == 0">男</div>
            <div v-if="scope.row.idSex == 1">女</div>
          </template>
        </el-table-column>
				<el-table-column label="年龄"  prop="age"   align="center" show-overflow-tooltip />
				<el-table-column label="公司"  prop="dw"  align="center" show-overflow-tooltip />
				<el-table-column label="电话" prop="phone"   align="center" show-overflow-tooltip />
        <el-table-column label="首次预约时间"  prop="inspectTime"   align="center" show-overflow-tooltip />
        <el-table-column label="首次回访人"  prop="visitId"  align="center" show-overflow-tooltip />
        <el-table-column label="首次回访时间" prop="visitTime"   align="center" show-overflow-tooltip />
        <el-table-column label="首次回访备注" prop="memo"   align="center" show-overflow-tooltip />
        <el-table-column label="本次回访人"  prop="visitids"   align="center" show-overflow-tooltip />
        <el-table-column label="本次回访时间" prop="visitTimes"   align="center" show-overflow-tooltip />
        <el-table-column label="本次回访备注" prop="memos"   align="center" show-overflow-tooltip />
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
import {listPrinttype} from "@/api/custservice/customerservice/appoint_return/appoint_return";
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
        size: 10,
				sfxmsrm: undefined,
				examfeeitemName: undefined,
				tjlx: undefined,
				xb: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
        dateguidancereturned:undefined,
        notifyResult:undefined,
        useCodeHiden:undefined,
        isInspects:undefined,
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
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() {},

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
        this.queryParams.startDate = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endDate = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
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
			this.$refs.addItems.handleAdd(this.ids)
		},
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/member/advanceVisitWrite/export', {
        ...this.queryParams
      }, `预约来检回访.xlsx`)
    },
	},
};
</script>
