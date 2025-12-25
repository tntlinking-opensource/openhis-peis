<!-- 查看第三方接口关联  开发人：麦沃德科技半夏/清风 -->
<template>
	<div class="rel-container">
		<!-- 查看第三方接口关联 -->
		<el-dialog title="查看第三方接口关联" :visible.sync="open" class="rel-inspect" width="1200px" append-to-body>
			<div class="flex-direction-column">
				<!-- 筛选 -->
				<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
					<el-form-item label="输入码" prop="inputCode">
						<el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
						<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
					</el-form-item>
				</el-form>
				<!-- 操作按钮 -->
				<el-row :gutter="10" class="mb8">
					<el-col :span="1.5">
						<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:thirdRelation:add']">添加</el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:thirdRelation:edit']">编辑
						</el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:thirdRelation:remove']">删除
						</el-button>
					</el-col>
				</el-row>
				<!-- 表格 -->
				<div class="table-box">
					<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
						<el-table-column type="selection" width="55" align="center" />
						<el-table-column label="序列" width="55" type="index" align="center" />
						<el-table-column label="检查项目编号" prop="baseItemCode" align="center" show-overflow-tooltip />
						<el-table-column label="简称" prop="baseItemName" align="center" show-overflow-tooltip />
						<el-table-column label="第三方接口名称" prop="interfaceName" align="center" show-overflow-tooltip />
						<el-table-column label="第三方检查项目编号" prop="interfaceItemCode" align="center" show-overflow-tooltip />
					</el-table>
				</div>
				<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList(baseItemId)" />
			</div>
			<el-dialog width="476px" :title="title" :visible.sync="innerVisible" destroy-on-close append-to-body :close-on-click-modal="false">
				<el-form ref="form" :model="form" :rules="rules" label-width="150px" hide-required-asterisk>
					<el-form-item label="检查项目名称" prop="baseItemName">
						<el-input v-model="form.baseItemName" placeholder="请输入检查项目名称" clearable style="width: 278px" />
					</el-form-item>
					<el-form-item label="检查项目编号" prop="baseItemCode">
						<el-input v-model="form.baseItemCode" placeholder="请输入检查项目编号" clearable style="width: 278px" />
					</el-form-item>
					<el-form-item label="第三方接口名称" prop="interfaceName">
						<el-input v-model="form.interfaceName" placeholder="请输入第三方接口名称" clearable style="width: 278px" />
					</el-form-item>
					<el-form-item label="第三方检查项目编号" prop="interfaceItemCode">
						<el-input v-model="form.interfaceItemCode" placeholder="请输入第三方检查项目编号" clearable style="width: 278px" />
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="submitForm">确 定</el-button>
					<el-button type="primary" plain @click="reset">重 置</el-button>
					<el-button @click="cancel">取 消</el-button>
				</div>
			</el-dialog>
		</el-dialog>
	</div>
</template>
<script>
import { getItemRelatedInformation, saveOrUpdate, remove, edit } from "@/api/basis/inspect.js"
export default {
	components: {},
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
			// 查询参数
			queryParams: {
				current: 1,
				size: 10,
				inputCode: undefined,
			},
			// 排检表格数据
			tableList: [
			],
			// 表单参数
			form: {},
			// 文本域自适应内容高度参数
			autosize: { minRows: 4, maxRows: 4 },
			// 弹出层标题
			title: "",
			// 表单校验
			rules: {
				seq: [{ required: true, message: "名称序号不能为空", trigger: "change" }],
				shunxu: [{ required: true, message: "顺序不能为空", trigger: "change" }],
				printName: [{ required: true, message: "打印项目分类名称不能为空", trigger: "change" }],
			},
			// 是否显示弹出层
			open: false,
			innerVisible: false,
			baseItemId: undefined
		};
	},
	computed: {},
	watch: {},
	created() {
	},
	mounted() { },
	methods: {
		// 查询列表
		getList(baseItemId) {
			this.open = true;
			if (baseItemId) {
				this.baseItemId = baseItemId;
			}
			this.loading = true;
			let obj = {
				baseItemId: this.baseItemId[0] || this.baseItemId,
				current: 1,
				size: 10
			}
			getItemRelatedInformation(obj).then((res) => {
				this.tableList = res.data.records;
				this.queryParams = {
					current: res.data.current,
					size: res.data.size
				}
				this.loading = false;
			})
			// });
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			this.getList(this.baseItemId);
		},
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
			this.handleQuery();
		},
		// 添加
		handleAdd() {
			this.reset();
			this.innerVisible = true;
			this.title = "添加"
		},
		// 删除
		handleDelete() {
			let idStr = "";
			let obj = { ids: "" }
			this.ids.forEach((el) => {
				idStr += el + ","
			})
			idStr = idStr.substring(0, idStr.length - 1);
			obj.ids = idStr;
			remove(obj).then((res) => {
				this.$modal.msgSuccess(res.msg)
			}).then(() => {
				this.getList(this.baseItemId);
			}).catch(() => {
				this.getList(this.baseItemId);
			})
		},
		// 编辑
		handleUpdate() {
			this.reset();
			let obj = {
				baseItemId: this.baseItemId[0] || this.baseItemId,
				id: this.ids[0]
			}
			edit(obj).then((res) => {
				this.form = res.data;
			})
			this.innerVisible = true;
			this.title = "编辑";
		},
		// 表单重置
		reset() {
			this.form = {
				baseItemName: undefined,
				baseItemCode: undefined,
				interfaceName: undefined,
				interfaceItemCode: undefined,
			},
				this.resetForm("form");
		},
		// 取消按钮
		cancel() {
			this.innerVisible = false;
			this.reset();
		},
		// 提交按钮(添加/修改)
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
						let obj = Object.assign(this.form, {
							baseItemId: this.baseItemId[0],
							id: this.ids[0]
						})
						saveOrUpdate(obj).then((res) => {
							this.$modal.msgSuccess(res.msg);
							this.innerVisible = false;
						}).then(() => {
							this.getList(this.baseItemId);
						}).catch(() => {
							this.getList(this.baseItemId);
						})
					} else {
						let obj = Object.assign(this.form, {
							baseItemId: this.baseItemId[0]
						})
						saveOrUpdate(obj).then((res) => {
							this.$modal.msgSuccess(res.msg);
							this.innerVisible = false;
						}).then(() => {
							this.getList(this.baseItemId);
						}).catch(() => {
							this.getList(this.baseItemId);
						})
					}
				}
			});
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
.rel-inspect {
	.el-dialog {
		height: 100%;
	}

	.el-dialog__footer {
		padding-top: 0;

		.pagination-container {
			margin-top: 0;
		}
	}
}
</style>