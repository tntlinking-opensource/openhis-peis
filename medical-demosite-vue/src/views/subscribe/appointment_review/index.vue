<!-- 预约审核  开发人：麦沃德科技半夏 -->
<template>
	<div class="app-container flex-direction-column">
		<!-- 筛选 -->
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
			<el-form-item label="会员类型" prop="idPatientclass">
				<el-select v-model="queryParams.idPatientclass" placeholder="请选择会员类型" clearable style="width: 230px;">
					<el-option v-for="options in memberOptions" :key="options.id" :label="options.text" :value="options.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="顾客姓名" prop="patientname">
				<el-input v-model="queryParams.patientname" placeholder="请输入顾客姓名" clearable style="width: 230px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="审核人" prop="idCheck">
				<el-input v-model="queryParams.idCheck" placeholder="请输入审核人" clearable style="width: 230px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="审核状态" prop="approvedStatus">
				<el-select v-model="queryParams.approvedStatus" placeholder="请选择审核状态" clearable style="width: 230px;">
					<el-option v-for="options in statusOptions" :key="options.id" :label="options.text" :value="options.id" />
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
				<el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="multiple" @click="handleApproved"
					v-hasPermi="['subscribe:appointmentReview:approved']">审核通过</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-edit" :disabled="multiple" @click="handleUnApproved"
					v-hasPermi="['subscribe:appointmentReview:approved']">审核不通过
				</el-button>
			</el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" />
				<el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
				<el-table-column label="体检号" prop="successCode" align="center" show-overflow-tooltip />
				<el-table-column label="电话" prop="phone" align="center" show-overflow-tooltip />
				<el-table-column label="申请人" prop="idReservation" align="center" show-overflow-tooltip />
				<el-table-column label="会员类型" prop="idPatientclass" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in memberOptions" :key="item.id">
							<span v-if="item.id == scope.row.idPatientclass">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="预约类型" prop="type" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in typeOptions" :key="item.id">
							<span v-if="item.id == scope.row.type">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="审核状态" prop="idCheck" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in statusOptions" :key="item.id">
							<el-tag :type="item.type" v-if="item.id == scope.row.idCheck">{{ item.text }}</el-tag>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="体检时间" prop="comboboxcolumn" align="center" show-overflow-tooltip />
				<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleApproved(scope.row)"
							v-hasPermi="['subscribe:appointmentReview:approved']">通过</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleUnApproved(scope.row)"
							v-hasPermi="['subscribe:appointmentReview:approved']">不通过</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
			@pagination="getList" />
		<div class="charts-box">
			<div class="echart" id="mychart" :style="myChartStyle"></div>
		</div>
	</div>
</template>
<script>
import * as echarts from "echarts";
export default {
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
				pageNum: 1,
				pageSize: 10,
				idPatientclass: undefined,
				patientname: undefined,
				idCheck: undefined,
				approvedStatus: undefined,
			},
			// 排检表格数据
			tableList: [
				{
					patientname: "张三",
					successCode: "165",
					phone: "15888888888",
					idReservation: "张三",
					idPatientclass: 1,
					type: 2,
					idCheck: 1,
					comboboxcolumn: "2022-10-26",
				},
				{
					patientname: "张三",
					successCode: "162",
					phone: "15888888888",
					idReservation: "张三",
					idPatientclass: 2,
					type: 2,
					idCheck: 2,
					comboboxcolumn: "2022-10-26",
				},
				{
					patientname: "张三",
					successCode: "161",
					phone: "15888888888",
					idReservation: "张三",
					idPatientclass: 3,
					type: 2,
					idCheck: 0,
					comboboxcolumn: "2022-10-26",
				},
			],
			// 会员类型
			memberOptions: [
				{ id: 1, text: '普通会员' },
				{ id: 2, text: 'VIP' },
				{ id: 3, text: 'VVIP' }
			],
			// 预约类型
			typeOptions: [
				{ id: 2, text: '超量申请' }
			],
			// 审核状态
			statusOptions: [
				{ id: 0, text: '等待处理', type: 'warning' },
				{ id: 1, text: '申请通过', type: '' },
				{ id: 2, text: '申请不通过', type: 'danger' }
			],
			//图表样式
			myChartStyle: { width: "100%", height: "100%" }
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() {
		this.initEcharts();
	},
	methods: {
		// 查询列表
		getList() {
			this.loading = true;
			// listPrinttype(this.queryParams).then(response => {
			// 	this.printtypeList = response.rows;
			// 	this.total = response.total;
			this.total = 150;
			this.loading = false;
			// });
		},
		// 搜索
		handleQuery() {
			this.queryParams.pageNum = 1;
			this.getList();
		},
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
			this.handleQuery();
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 审核通过
		handleApproved() {
			this.$confirm('确定通过所选项目吗？', '提醒', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.$modal.msgSuccess("操作成功");
			}).catch(() => { });
		},
		// 审核不通过
		handleUnApproved() {
			this.$confirm('确定不通过所选项目吗？', '提醒', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.$modal.msgSuccess("操作成功");
			}).catch(() => { });
		},
		initEcharts() {
			// 基本柱状图
			const option = {
				color: ["#41E4BB", "#1890FF", "#FF7A00", "#FF2727", "#45A6FF", "#4618FF", "#FFCC18", "#FF18B0"],
				tooltip: {
					trigger: 'item',
					axisPointer: { // 坐标轴指示器，坐标轴触发有效
						type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				title: {
					text: '本周预约情况',
					subtext: ''
				},
				legend: {
					data: ['普上(预计)', '普上(实际)', '普下(预计)', '普下(实际)', 'VIP(预计)', 'VIP(实际)', 'VVIP(预计)', 'VVIP(实际)']
				},
				grid: {
					left: '3%',
					right: '4%',
					bottom: '3%',
					containLabel: true
				},
				xAxis: [{
					type: 'category',
					data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
				}],
				yAxis: [{
					type: 'value'
				}],
				toolbox: {
					show: true,
					feature: {
						mark: {
							show: true
						},
						magicType: {
							show: true,
							type: ['line', 'bar']
						},
						restore: {
							show: true
						},
						saveAsImage: {
							show: true
						}
					}
				},
				series: [
					{
						name: '普上(预计)',
						type: 'bar',
						data: [0.6, 0.8, 0.3, 0.5, 0.8, 0.85, 0.9]
					},
					{
						name: '普上(实际)',
						type: 'bar',
						data: [0.4, 0.6, 0.7, 0.8, 0.4, 0.5, 0.9]
					},
					{
						name: '普下(预计)',
						type: 'bar',
						data: [0.6, 0.4, 0.7, 0.5, 0.7, 0.45, 0.4]
					},
					{
						name: '普下(实际)',
						type: 'bar',
						data: [0.2, 0.4, 0.54, 0.45, 0.8, 0.6, 0.5]
					},
					{
						name: 'VIP(预计)',
						type: 'bar',
						data: [0.6, 0.8, 0.3, 0.5, 0.8, 0.85, 0.9]
					},
					{
						name: 'VIP(实际)',
						type: 'bar',
						data: [0.4, 0.6, 0.7, 0.8, 0.4, 0.5, 0.9]
					},
					{
						name: 'VVIP(预计)',
						type: 'bar',
						data: [0.6, 0.4, 0.7, 0.5, 0.7, 0.45, 0.4]
					},
					{
						name: 'VVIP(实际)',
						type: 'bar',
						data: [0.2, 0.4, 0.54, 0.45, 0.8, 0.6, 0.5]
					},
				]
			};
			const myChart = echarts.init(document.getElementById("mychart"));
			myChart.setOption(option);
			//随着屏幕大小调节图表
			window.addEventListener("resize", () => {
				myChart.resize();
			});
		}
	},
};
</script>
<style lang="scss" scoped>
.charts-box {
	width: 100%;
	height: 43%;
	border: 1px solid #C4C4C4;
	padding: 16px;
}
</style>