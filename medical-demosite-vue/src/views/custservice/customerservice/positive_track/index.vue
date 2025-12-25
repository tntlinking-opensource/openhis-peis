<!-- 个检危机值回访 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"  v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 120px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 150px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 150px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择" clearable style="width: 100px;">
          <el-option label="健康" :value="0" />
          <el-option label="职业" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="客户类型" prop="khlx">
        <el-select v-model="queryParams.khlx" placeholder="请选择" clearable style="width: 100px;">
          <el-option label="个检" :value="0" />
          <el-option label="团检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检时间">
        <el-date-picker v-model="queryParams.date" style="width: 300px;" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="阳性结果" prop="yxjg">
        <el-input v-model="queryParams.yxjg" placeholder="" clearable style="width: 100px;"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="危急程度" prop="wjzjb">
        <el-select v-model="queryParams.wjzjb" placeholder="请选择" clearable style="width: 100px;">
          <el-option label="高" :value="1" />
          <el-option label="中" :value="2" />
          <el-option label="低" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="2.5" >
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleAdd" :disabled="single"
                   v-hasPermi="['custservice:customerservice:positiveTrack:add']">阳性结果回访
        </el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport"
                   v-hasPermi="['custservice:customerservice:positiveTrack:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe  @selection-change="handleSelectionChange"  >
				<el-table-column fixed type="selection" min-width="120px" align="center" />
				<el-table-column label="序列"  type="index"  min-width="120px"  align="center" />
        <el-table-column label="姓名"  prop="patientname" min-width="120px"   align="center" show-overflow-tooltip />
        <el-table-column label="性别"  prop="idSex" min-width="120px"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.idSex == 0">男</div>
            <div v-if="scope.row.idSex == 1">女</div>
            <div v-if="scope.row.idSex == 2">通用</div>
          </template>
        </el-table-column>
        <el-table-column label="体检号"  prop="id" min-width="120px"   align="center" show-overflow-tooltip />
				<el-table-column label="年龄"  prop="age" min-width="120px"   align="center" show-overflow-tooltip />
				<el-table-column label="电话"  prop="phone" min-width="120px"  align="center" show-overflow-tooltip />
				<el-table-column label="体检类型" prop="idExamtype" min-width="120px"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <div v-if="scope.row.idExamtype == 0">健康</div>
            <div v-if="scope.row.idExamtype == 1">职业</div>
            <div v-if="scope.row.idExamtype == 2">综合</div>
          </template>
        </el-table-column>
        <el-table-column label="开单医生"  prop="doctorapply"  min-width="120px"  align="center" show-overflow-tooltip />
        <el-table-column label="体检时间"  prop="tjrq" min-width="120px"  align="center" show-overflow-tooltip />
        <el-table-column label="阳性检查项目" prop="gwxm"  min-width="120px"  align="center" show-overflow-tooltip />
        <el-table-column label="阳性结果" prop="wjzxj" min-width="120px"   align="center" show-overflow-tooltip />
        <el-table-column label="危急程度"  prop="wjzjb" min-width="120px"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.wjzjb == 1" type="danger">高</el-tag>
            <el-tag v-if="scope.row.wjzjb == 2">中</el-tag>
            <el-tag v-if="scope.row.wjzjb == 3" type="success">低</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="回访人" prop="visitId" min-width="120px"   align="center" show-overflow-tooltip />
        <el-table-column label="回访时间" prop="visitTime" min-width="120px"   align="center" show-overflow-tooltip />
        <el-table-column label="回访备注" prop="memo" min-width="120px"   align="center" show-overflow-tooltip />
        <el-table-column label="处理状态"  prop="statuss" min-width="120px"   align="center" show-overflow-tooltip >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.statuss == 1">已回访</el-tag>
            <el-tag type="danger" v-else>未回访</el-tag>
          </template>
        </el-table-column>
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
import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/custservice/customerservice/positive_track/positive_track.js";
import addItems from './add'
export default {
	components: { addItems },
	props: [],
	data() {
		return {
			// 遮罩层
			loading: true,
			// 选中数组
			ids: 0,
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
        patientname:'',
        patientcode:'',
        phone:'',
        idExamtype:'',
        khlx:'',
        yxjg:'',
        wjzjb:'',
        date:[],
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
		// 重置
		resetQuery() {
      this.queryParams.date=[]
			this.resetForm("queryForm");
			this.handleQuery();
		},
		// 添加
		handleAdd() {
      if(this.rows == undefined){
        this.$alert("请选择一条记录修改!", "提醒", {
          confirmButtonText: "确定",
          type: "warning",
        })
      }else{
        this.$refs.addItems.handleAdd(this.ids)
      }

		},

    /** 导出按钮操作 */
    handleExport() {
      this.download('/member/peispatient/export', {
      }, `个检危机值回访.xlsx`)
    },
	},
};
</script>
