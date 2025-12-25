<!-- 检查项目类型  开发人：麦沃德科技半夏 -->
<template>
	<div class="app-container flex-direction-column">
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:inspectType:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:inspectType:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:inspectType:remove']">删除
				</el-button>
			</el-col>
			<right-toolbar :search="false" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box" ref="tableBox">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" />
				<el-table-column label="健值" prop="examitemtypekey" align="center" show-overflow-tooltip />
				<el-table-column label="项目类型名称" prop="examitemtypeName" align="center" show-overflow-tooltip />
				<el-table-column label="项目类型名称(外文)" prop="examitemtypeNameeng" align="center" show-overflow-tooltip />
				<el-table-column label="输入码" prop="inputCode" align="center" show-overflow-tooltip />
				<el-table-column label="备注" prop="note" align="center" show-overflow-tooltip />
				<el-table-column label="导出代码" prop="examitemtypecodex" align="center" show-overflow-tooltip />
				<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:inspectType:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:inspectType:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="498px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" label-width="150px" hide-required-asterisk v-loading="popLoading">
				<el-form-item label="健值" prop="examitemtypekey">
					<el-input v-model="form.examitemtypekey" placeholder="请输入健值" clearable style="width: 308px" />
				</el-form-item>
				<el-form-item label="项目类型名称" prop="examitemtypeName">
					<el-input v-model="form.examitemtypeName" placeholder="请输入项目类型名称" clearable style="width: 308px" @input="nameChange" />
				</el-form-item>
				<el-form-item label="项目类型名称(外文)" prop="examitemtypeNameeng">
					<el-input v-model="form.examitemtypeNameeng" placeholder="请输入项目类型名称(外文)" clearable style="width: 308px" />
				</el-form-item>
				<el-form-item label="导出代码" prop="examitemtypecodex">
					<el-input v-model="form.examitemtypecodex" placeholder="请输入导出代码" clearable style="width: 308px" />
				</el-form-item>
				<el-form-item label="备注" prop="note">
					<el-input v-model="form.note" placeholder="请输入内容" type="textarea" :autosize="autosize" clearable style="width: 308px" />
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
import { listBasexamltemtype, getBasexamltemtype, addBasexamltemtype, updateBasexamltemtype, delBasexamltemtype } from "@/api/basis/inspect";
import pinyin from "@/utils/pinyin.js";
export default {
	name: "Inspect_type",
	data() {
		return {
			// 遮罩层
			loading: true,
			// 选中数组
			ids: [],
			sequences: [],
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
				inputCode: undefined,
			},
			// 排检表格数据
			tableList: [],
			// 表单参数
			form: {},
			// 文本域自适应内容高度参数
			autosize: { minRows: 4, maxRows: 4 },
			// 弹出层标题
			title: "",
			// 弹出层加载动画
			popLoading: false,
			// 编辑详情
			popData: undefined,
			// 是否显示弹出层
			open: false,
			// 表单校验
			rules: {
				seq: [{ required: true, message: "名称序号不能为空", trigger: "change" }],
				shunxu: [{ required: true, message: "顺序不能为空", trigger: "change" }],
				printName: [{ required: true, message: "打印项目分类名称不能为空", trigger: "change" }],
			},
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() { },
	methods: {
		// 查询列表
		getList() {
			this.loading = true;
			listBasexamltemtype(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
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
			this.$modal.confirm('您确定要删除该信息吗？').then(function () {
				return delBasexamltemtype(ids);
			}).then(() => {
				this.getList();
				this.$modal.msgSuccess("删除成功");
			}).catch(() => { });
		},
		// 编辑
		handleUpdate(row) {
			this.popData = undefined
			this.reset();
			const id = row.id || this.ids
			this.open = true;
			this.title = "编辑";
			this.popLoading = true
			getBasexamltemtype(id).then(response => {
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
				inputCode: undefined,
				note: undefined,
				printName: undefined,
				seq: 0,
				shunxu: 0
			},
				this.resetForm("form");
		},
		// 项目类型名称改变
		nameChange(value) {
			this.form.inputCode = pinyin(value)
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
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
						updateBasexamltemtype(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.getList();
						});
					} else {
						addBasexamltemtype(this.form).then(response => {
							this.$modal.msgSuccess("新增成功");
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