<!-- 危害因素匹配 开发人：麦沃德科技暴雨 -->
<template>
	<div class="app-container flex-direction-column">
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
			<el-form-item label="选择日期">
				<el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
					range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="2">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleUpdate"
					v-hasPermi="['reception:peisHarm:matching']">匹配
				</el-button>
			</el-col>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" align="center" />
				<el-table-column label="序列" type="index" width="65" align="center" />
				<el-table-column label="套餐名称" prop="tcmc" align="center" show-overflow-tooltip />
				<el-table-column label="需匹配因素" prop="harms" align="center" show-overflow-tooltip />
				<el-table-column label="已匹配因素" prop="matches" align="center" show-overflow-tooltip />
				<el-table-column label="操作" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #00bfff" @click="handleMate(scope.row)"
							v-hasPermi="['basis:scheduling:edit']">匹配</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems"></add-items>
	</div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
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
				pageNum: 1,
				pageSize: 10,
				sfxmsrm: undefined,
				examfeeitemName: undefined,
				tjlx: undefined,
				xb: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
			},
			// 表格展示数据
			tableList: [
				{
					"tcmc": "体检套餐",
					"harms": "123",
					"matches": "123",
				}
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
	mounted() { },
	methods: {
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 查询列表
		getList() {
			this.loading = true;
			// listPrinttype(this.queryParams).then(response => {
			// 	this.printtypeList = response.rows;
			// 	this.total = response.total;
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
		// 添加
		handleAdd() {
			this.$refs.addItems.handleAdd()
		},
		// 删除
		handleDelete(row) {
			const ids = row.id || this.ids;
			this.$modal
				.confirm('是否确认删除该数据项？')
				.then(function () {
					// return delPrinttype(ids);
					return;
				})
				.then(() => {
					this.getList();
					this.$modal.msgSuccess("删除成功");
				})
				.catch(() => { });
		},
		// 编辑
		handleUpdate(row) {
			this.$refs.addItems.handleUpdate(row)
		},
		// 匹配
		handleMate(row) {

		},
	},
};
</script>
