<!-- 报告审核统计 开发人：麦沃德科技暴雨/矢北 -->
<template>
	<div class="app-container flex-direction-column">
		<!--页面-->
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
			<el-form-item label="审核人:" prop="inputCode">
				<input-select ref="select" v-model="queryParams.inputCode" :selectData="selectData" style="width: 280px"
					@change="labTypeChange"></input-select>
			</el-form-item>
			<el-form-item label="审核日期:" prop="date">
				<el-date-picker v-model="queryParams.date" style="width: 340px;" value-format="yyyy-MM-dd" type="daterange"
					range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
			</el-form-item>
		</el-form>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe>
				<el-table-column type="selection" align="center" />
				<el-table-column label="序列" type="index" width="65" align="center" />
				<el-table-column label="审核人姓名" prop="xm" min-width="160px" align="center" show-overflow-tooltip />
				<el-table-column label="审核阶段" prop="jd" min-width="100px" align="center" show-overflow-tooltip />
				<el-table-column label="审核数量" prop="sl" min-width="160px" align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->

	</div>
</template>
<script>
import { getListData, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/report/reportreview_count";
import receiveItems from './receive'
export default {
	name: "Reportreview_count",
	components: { receiveItems },
	props: [],
	data() {
		return {
			// 科室名称
			selectData: {
				placeholder: '请输入输入码选择',
				key: '部门',
				value: '姓名',
				url: '/report/totalAudit/getNameData',
				bindValue: '',//初始值(必传)
				firstName: 'depId',
				secondName: 'userName',
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
			// 显示搜索条件
			showSearch: true,
			// 查询参数
			queryParams: {
				pageNum: 1,
				pageSize: 10,
				sfxmsrm: undefined,
				userName: undefined,
				date: undefined,
				startTime: undefined,
				endTime: undefined,
				name: undefined,
				inputCode: undefined
			},
			// 表格展示数据
			tableList: [

			],
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
			getListData(this.queryParams).then(response => {
				;
				this.tableList = response.data;
				this.total = response.data.length;
				this.loading = false;
			});
			this.loading = false
		},
		labTypeChange(value) {
			 this.queryParams.name = value.id
			this.queryParams.inputCode = value.userName
	
			 this.selectData.bindValue = this.queryParams.inputCode;

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
	},
};
</script>
