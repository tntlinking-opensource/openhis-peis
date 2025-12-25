<!-- 平安好医生排检  开发人：麦沃德科技半夏 -->
<template>
	<div class="app-container flex-direction-column">
		<!-- 筛选 -->
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
			<el-form-item label="分中心" prop="branchIds">
				<el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 230px;">
					<el-option v-for="options in branchOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
				</el-select>
			</el-form-item>
			<el-form-item label="选择日期">
				<el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:scheduling:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:scheduling:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:scheduling:remove']">删除
				</el-button>
			</el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" />
				<el-table-column label="日期" prop="sortDate" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						{{ scope.row.sortDate.split(" ")[0] }}
					</template>
				</el-table-column>
				<el-table-column label="分中心" prop="fzx" align="center" show-overflow-tooltip />
				<el-table-column label="是否开检" prop="isOpen" align="center">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.isOpen == 1">是</el-tag>
						<el-tag type="danger" v-else>否</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="普检区可预约人数" prop="areaId" align="center" show-overflow-tooltip />
				<el-table-column label="VIP区可预约人数" prop="sortNum" align="center" show-overflow-tooltip />
				<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:scheduling:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:scheduling:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" label-width="130px" hide-required-asterisk v-loading="popLoading">
				<el-form-item label="分中心" prop="branchId">
					<el-select v-model="form.branchId" placeholder="请选择分中心" style="width: 308px">
						<el-option v-for="options in branchOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
					</el-select>
				</el-form-item>
				<el-form-item label="是否开检" prop="isOpen">
					<el-select v-model="form.isOpen" placeholder="请选择是否开检" style="width: 308px">
						<el-option :key="1" label="是" :value="1" />
						<el-option :key="0" label="否" :value="0" />
					</el-select>
				</el-form-item>
				<el-form-item label="选择日期" prop="sortDate" v-if="form.id">
					<el-date-picker v-model="form.sortDate" style="width: 308px" value-format="yyyy-MM-dd hh:mm:ss" type="datetime" key="date" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="选择日期" prop="date" v-else>
					<el-date-picker v-model="form.date" style="width: 308px" value-format="yyyy-MM-dd" type="daterange" key="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
				</el-form-item>
				<el-form-item label="普检区可预约人数" prop="areaId">
					<el-input-number v-model="form.areaId" controls-position="right" :min="0" style="width: 308px; text-align: left" />
				</el-form-item>
				<el-form-item label="VIP区可预约人数" prop="sortNum">
					<el-input-number v-model="form.sortNum" controls-position="right" :min="0" style="width: 308px" />
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
import { getPeissortexam, listPeissortexam, addPeissortexam, updatePeissortexam, delPeissortexam } from "@/api/basis/scheduling";
import { listBranch } from "@/api/system/branch";
export default {
	name: 'Scheduling',
	components: {},
	props: [],
	data() {
		return {
			// 遮罩层
			loading: true,
			// 显示搜索条件
			showSearch: true,
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
				branchIds: undefined,
				date: undefined,
				startTime: undefined,
				endTime: undefined,
			},
			queryBranch: {
				current: 1,
				size: 99999,
			},
			// 排检表格数据
			tableList: [],
			// 分中心列表
			branchOptions: [],
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 弹出层加载动画
			popLoading: false,
			// 弹出层临时数据
			popData: undefined,
			// 是否显示弹出层
			open: false,
			// 表单校验
			rules: {
				branchId: [{ required: true, message: "分中心不能为空", trigger: "change" }],
				isOpen: [{ required: true, message: "是否开检不能为空", trigger: "change" }],
				date: [{ required: true, message: "日期不能为空", trigger: "change" }],
				sortDate: [{ required: true, message: "日期不能为空", trigger: "change" }],
				areaId: [{ required: true, message: "普检区可预约人数不能为空", trigger: "change" }],
				sortNum: [{ required: true, message: "VIP区可预约人数不能为空", trigger: "change" }],
			},
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
		this.getBranch()
	},
	mounted() { },
	methods: {
		// 查询列表
		getList() {
			this.loading = true;
			listPeissortexam(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
			});
		},
		/** 查询分中心维护列表 */
		getBranch() {
			listBranch(this.queryBranch).then((response) => {
				this.branchOptions = response.data.records;
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
			this.queryParams.date = undefined
			this.queryParams.startTime = undefined
			this.queryParams.endTime = undefined
			this.resetForm("queryForm");
			this.handleQuery();
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
			const ids = row.id || this.ids;
			this.$modal
				.confirm('是否确认删除该数据项？')
				.then(function () {
					return delPeissortexam(ids);
				})
				.then((res) => {
					this.getList();
					this.$modal.msgSuccess(res.msg);
				})
				.catch(() => { });
		},
		// 编辑
		handleUpdate(row) {
			this.popData = undefined
			this.reset();
			const id = row.id || this.ids
			this.open = true;
			this.title = "编辑";
			this.popLoading = true
			getPeissortexam(id).then(response => {
				this.popData = JSON.parse(JSON.stringify(response.data))
				this.popLoading = false
				this.form = response.data;
			});
		},
		// 表单重置
		reset() {
			if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				return
			}
			this.form = {
				id: undefined,
				branch: undefined,
				isOpen: undefined,
				date: undefined,
				areaId: 0,
				sortNum: 0,
			};
			this.resetForm("form");
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
		// 提交按钮
		submitForm: function () {
			this.$refs["form"].validate((valid) => {
				if (valid) {
					if (this.form.id != null) {
						updatePeissortexam(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.getList();
						});
					} else {
						this.form.startDate = this.form.date[0]
						this.form.endDate = this.form.date[1]
						addPeissortexam(this.form).then(response => {
							this.$modal.msgSuccess("添加成功");
							this.open = false;
							this.getList();
						});
					}
				}
			});
		},
	},
};
</script>
