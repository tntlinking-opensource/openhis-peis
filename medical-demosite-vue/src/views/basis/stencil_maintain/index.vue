<!-- WORD模板维护  开发人：麦沃德科技半夏 -->
<template>
	<div class="app-container flex-direction-column">
		<!-- 筛选 -->
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch">
			<el-form-item label="科室" prop="depId">
				<input-select :selectData="selectData" selectWidth="220" @change="depChange"></input-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:stencil:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:stencil:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:stencil:remove']">删除
				</el-button>
			</el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" />
				<el-table-column label="模板名称" prop="modelName" align="center" show-overflow-tooltip />
				<el-table-column label="模板类型" prop="modelType" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						{{ modelType(scope.row.modelType) }}
					</template>
				</el-table-column>
				<el-table-column label="科室名称" prop="depName" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						{{ scope.row.depName || "非科室模板" }}
					</template>
				</el-table-column>
				<el-table-column label="适用类型" prop="suitableType" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						{{ suitableType(scope.row.suitableType) }}
					</template>
				</el-table-column>
				<el-table-column label="是否默认" prop="isDefault" align="center">
					<template slot-scope="scope">
						<div v-if="scope.row.isDefault == 1">非默认</div>
						<div v-else>默认</div>
					</template>
				</el-table-column>
				<el-table-column label="是否头模板" prop="isHead" align="center">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.isHead == 0">是</el-tag>
						<el-tag type="danger" v-else>否</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:stencil:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:stencil:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="908px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" label-width="102px" :inline="true" hide-required-asterisk v-loading="popLoading">
				<el-form-item label="模板名称" prop="modelName" style="margin-right: 48px">
					<el-input v-model="form.modelName" placeholder="请输入模板名称" clearable style="width: 308px" />
				</el-form-item>
				<el-form-item label="模板类型" prop="modelType" style="margin-right: 0">
					<el-select v-model="form.modelType" placeholder="请选择模板类型" style="width: 308px" @change="modelTypeChange">
						<el-option v-for="options in modelOptions" :key="options.id" :label="options.text" :value="options.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="是否头模板" prop="isHead" style="margin-right: 48px">
					<el-select v-model="form.isHead" placeholder="请选择是否头模板" style="width: 308px" @change="isHeadChange" :disabled="!rules.isHead[0].required">
						<el-option :key="0" label="是" :value="0" />
						<el-option :key="1" label="否" :value="1" />
					</el-select>
				</el-form-item>
				<el-form-item label="是否默认" prop="isDefault" style="margin-right: 0">
					<el-select v-model="form.isDefault" placeholder="请选择是否默认" style="width: 308px">
						<el-option :key="0" label="是" :value="0" />
						<el-option :key="1" label="否" :value="1" />
					</el-select>
				</el-form-item>
				<el-form-item label="适用类型" prop="suitableType" style="margin-right: 48px">
					<el-select v-model="form.suitableType" placeholder="请选择适用类型" style="width: 308px" :disabled="!rules.suitableType[0].required">
						<el-option v-for="options in suitableOptions" :key="options.id" :label="options.text" :value="options.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="适用科室" prop="depId" style="margin-right: 0">
					<input-select :selectData="addData" selectWidth="308" @change="addChange" :disabled="!rules.depId[0].required"></input-select>
				</el-form-item>
				<el-form-item prop="modelUrls" label-width="0" v-if="!popData">
					<span class="upload-title" style="font-size: 14px;color: #606266;font-weight: 700;">文件上传</span>
					<file-upload v-model="form.modelUrls" upload-width="868px" fileWidth="410px" />
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
import { listMaintain, getMaintain, addMaintain, updateMaintain, delMaintain } from "@/api/basis/stencil_maintain";
export default {
	name: 'Stencil',
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
			// 文件上传数量限制
			uploadLimit: 5,
			// 查询参数
			queryParams: {
				current: 1,
				size: 20,
				depId: undefined,
				depName: undefined,
			},
			// 排检表格数据
			tableList: [],
			// 科室选择
			selectData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题
				value: '名称',//第二列标题
				url: '/system/keshi/list',//请求连接
				bindValue: '',
				firstName: 'srm',//接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'name',//接口返回值对应第二列的参数名(不传默认为'name')
			},
			addData: {
				placeholder: '请输入输入码选择',
				key: '输入码',
				value: '分类名称',
				url: '/harm/findAllHarmclass',
				bindValue: '', //初始值(必传)
			},
			// 模板类型
			modelOptions: [
				{ id: 0, text: "科室（个检）" },
				{ id: 1, text: "团检模板" },
				{ id: 2, text: "对比模板" },
				{ id: 3, text: "单科室头模板" },
				{ id: 4, text: "复查通知单模板" },
				{ id: 5, text: "疑似职业病模板" },
				{ id: 6, text: "职业禁忌证模板" },
			],
			// 适用类型
			suitableOptions: [
				{ id: 0, text: "健康" },
				{ id: 1, text: "职业" },
			],
			// 表单参数
			form: {},
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
				modelName: [{ required: true, message: "模板名称不能为空", trigger: "change" }],
				modelType: [{ required: true, message: "模板类型不能为空", trigger: "change" }],
				depId: [{ required: false, message: "适用科室不能为空", trigger: "change" }],
				suitableType: [{ required: true, message: "适用类型不能为空", trigger: "change" }],
				isDefault: [{ required: true, message: "是否默认不能为空", trigger: "change" }],
				isHead: [{ required: true, message: "是否头模板不能为空", trigger: "change" }],
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
		// 模板类型改变
		modelTypeChange(value) {
			if (value == 3 || value == 4 || value == 5 || value == 6) {
				this.rules.depId[0].required = false
				this.form.depId = undefined
				this.addData.bindValue = undefined
				this.$refs.form.clearValidate(['depId']);
				this.rules.isHead[0].required = false
				this.form.isHead = undefined
				this.$refs.form.clearValidate(['isHead']);
				this.rules.suitableType[0].required = false
				this.form.suitableType = undefined
				this.$refs.form.clearValidate(['suitableType']);
			} else {
				if (this.popData && value != 0) {
					this.rules.depId[0].required = false
					this.form.depId = undefined
					this.addData.bindValue = undefined
					this.$refs.form.clearValidate(['depId']);
					this.rules.isHead[0].required = false
					this.form.isHead = undefined
					this.$refs.form.clearValidate(['isHead']);
					this.rules.suitableType[0].required = true
				} else {
					if (this.form.isHead != 0) {
						this.rules.depId[0].required = true
						this.rules.isHead[0].required = true
						this.rules.suitableType[0].required = true
					}
				}
			}
		},
		// 是否头模板改变
		isHeadChange(value) {
			if (this.rules.isHead[0].required == false) {
				return
			}
			if (value == 0) {
				this.rules.depId[0].required = false
				this.form.depId = undefined
				this.addData.bindValue = undefined
				this.$refs.form.clearValidate(['depId']);
			} else {
				this.rules.depId[0].required = true
			}
		},
		// 模板类型
		modelType(value) {
			if (value == 0) {
				return "科室(个检)模板";
			} else if (value == 1) {
				return "团检模板";
			} else if (value == 2) {
				return "对比模板";
			} else {
				return "单科室头模板";
			}
		},
		// 适用类型
		suitableType(value) {
			if (value == 0) {
				return "健康";
			} else if (value == 1) {
				return "职业";
			} else {
				return "单科室头结果";
			}
		},
		// 输入码选择
		depChange(value) {
			this.queryParams.depId = value.id
			this.queryParams.depName = value.name
			this.selectData.bindValue = value.name
		},
		addChange(value) {
			this.form.depId = value.id
			this.form.depName = value.name
			this.addData.bindValue = value.name
		},
		// 查询列表
		getList() {
			this.loading = true;
			listMaintain(this.queryParams).then(response => {
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
			this.selectData.bindValue = undefined
			this.resetForm("queryForm");
			this.handleQuery();
		},
		// 添加
		handleAdd() {
			this.open = true;
			this.title = "添加";
			this.popData = undefined
			this.reset();
		},
		// 删除
		handleDelete(row) {
			const ids = row.id || this.ids;
			this.$modal.confirm('您确定要删除该信息吗？').then(function () {
				return delMaintain(ids);
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
			getMaintain(id).then(response => {
				this.popLoading = false
				this.popData = JSON.parse(JSON.stringify(response.data))
				this.form = response.data;
				this.addData.bindValue = this.form.depName
				this.modelTypeChange(this.form.modelType)
				this.isHeadChange(this.form.isHead)
			});
		},
		// 表单重置
		reset() {
			if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				this.addData.bindValue = this.popData.depName
				this.modelTypeChange(this.popData.modelType)
				this.isHeadChange(this.popData.isHead)
				return
			}
			this.form = {
				modelName: undefined,
				modelType: undefined,
				isHead: undefined,
				isDefault: undefined,
				suitableType: undefined,
				depId: undefined,
			}
			this.addData.bindValue = undefined
			this.rules.depId[0].required = false
			this.rules.isHead[0].required = true
			this.rules.suitableType[0].required = true
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
					if (this.form.id != undefined) {
						// this.form.menuIds = this.getMenuAllCheckedKeys();
						// updateRole(this.form).then(response => {
						this.$modal.msgSuccess("修改成功");
						this.open = false;
						//   this.getList();
						// });
					} else {
						// this.form.menuIds = this.getMenuAllCheckedKeys();
						// addRole(this.form).then(response => {
						this.$modal.msgSuccess("添加成功");
						this.open = false;
						//   this.getList();
						// });
					}
				}
			});
		},
	},
};
</script>