<!-- 图像存储配置 开发人：麦沃德科技矢北/半夏 -->
<template>
	<div class="app-container flex-direction-column">
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
					v-hasPermi="['basicPACS:storageConfig:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate"
					v-hasPermi="['basicPACS:storageConfig:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete"
					v-hasPermi="['basicPACS:storageConfig:remove']">删除
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="warning" plain size="mini" icon="el-icon-help" :disabled="single" @click="handleApply"
					v-hasPermi="['basicPACS:storageConfig:apply']">应用
				</el-button>
			</el-col>
			<right-toolbar :search="false" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table ref="table" border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange" @row-click="rowClick">
				<el-table-column type="selection" width="55" align="center" />
				<el-table-column label="序列" width="55" type="index" align="center" show-overflow-tooltip />
				<el-table-column label="标识" width="80" prop="flag" align="center" show-overflow-tooltip />
				<el-table-column label="AETITLE" min-width="120" prop="aetitle" align="center" show-overflow-tooltip />
				<el-table-column label="IP" min-width="120" prop="ip" align="center" show-overflow-tooltip />
				<el-table-column label="端口" min-width="120" prop="port" align="center" show-overflow-tooltip />
				<el-table-column label="实际路径" min-width="140" prop="realPath" align="center" show-overflow-tooltip />
				<el-table-column label="映射路径" min-width="140" prop="mappingPath" align="center" show-overflow-tooltip />
				<el-table-column label="访问路径" min-width="160" prop="visitPath" align="center" show-overflow-tooltip />
				<el-table-column label="备注" min-width="160" prop="memo" align="center" show-overflow-tooltip />
				<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)"
							v-hasPermi="['basicPACS:storageConfig:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)"
							v-hasPermi="['basicPACS:storageConfig:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="466px" style="min-height:300px;" append-to-body
			:close-on-click-modal="false">
			<el-form ref="form" id="addForm" :model="form" :inline="true" label-width="80px" hide-required-asterisk
				v-loading="popLoading">
				<el-form-item label="标识" prop="flag">
					<el-input-number v-model="form.flag" controls-position="right" style="width:340px" :min="0"
						:max="999"></el-input-number>
				</el-form-item>
				<el-form-item label="AETITLE" prop="aetitle">
					<el-input v-model="form.aetitle" placeholder="请输入AETITLE" clearable style="width:340px" />
				</el-form-item>
				<el-form-item label="IP" prop="ip">
					<el-input v-model="form.ip" placeholder="请输入IP" clearable style="width:340px" />
				</el-form-item>
				<el-form-item label="端口" prop="port">
					<el-input-number v-model="form.port" controls-position="right" style="width:340px" :min="0"
						:max="9999999999"></el-input-number>
				</el-form-item>
				<el-form-item label="实际路径" prop="realPath">
					<el-input v-model="form.realPath" placeholder="请输入实际路径" clearable style="width:340px" />
				</el-form-item>
				<el-form-item label="映射路径" prop="mappingPath">
					<el-input v-model="form.mappingPath" placeholder="请输入映射路径" clearable style="width:340px" />
				</el-form-item>
				<el-form-item label="访问路径" prop="visitPath">
					<el-input v-model="form.visitPath" placeholder="请输入访问路径" clearable style="width:340px" />
				</el-form-item>
				<el-form-item label="备注" prop="memo">
					<el-input type="textarea" rows="3" v-model="form.memo" placeholder="请输入备注" clearable style="width:340px" />
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">保 存</el-button>
				<el-button type="primary" plain @click="reset">重 置</el-button>
				<el-button @click="cancel">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { getConfigList, getConfig, saveOrUpdate, delConfig } from '@/api/PACS/basicPACS/storage_config.js'
export default {
	data() {
		return {
			// 表格数据
			tableList: [],
			// 遮罩层
			loading: false,
			// 非多个禁用
			multiple: true,
			// 非单个禁用
			single: true,
			// 选中数组
			selectRows: [],
			// 总条数
			total: 0,
			// 搜索参数
			queryParams: {
				current: 1,
				size: 10,
			},
			// 表单参数
			form: {},
			// 最新标识
			lastFlag: 0,
			// 文本域自适应内容高度参数
			autosize: { minRows: 4, maxRows: 4 },
			// 弹出层标题
			title: "",
			// 弹出层加载动画
			popLoading: false,
			// 编辑详情
			popData: {},
			// 是否显示弹出层
			open: false,
			// 表单校验
			rules: {
				seq: [{ required: true, message: "名称序号不能为空", trigger: "change" }],
				shunxu: [{ required: true, message: "顺序不能为空", trigger: "change" }],
				printName: [{ required: true, message: "打印项目分类名称不能为空", trigger: "change" }],
			},
		}
	},
	created() {
		this.getList()
	},
	methods: {
		// 查询列表
		getList() {
			this.loading = true;
			getConfigList(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
				this.getLastFlag()
			});
		},
		// 获取最后一项标示
		getLastFlag() {
			var query = {
				current: this.total,
				size: 1
			}
			getConfigList(query).then(res => {
				this.lastFlag = res.data.records[0].flag
			});
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.selectRows = selection
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		//单击事件选中当前行
		rowClick(row, col) {
			if (col && (col.label == "操作" || col.type == "selection")) {
				return
			}
			var isSelect = false
			this.selectRows.forEach(el => {
				if (el.id == row.id) {
					isSelect = true
				}
			});
			if (isSelect) return
			this.$refs.table.clearSelection();
			this.$refs.table.toggleRowSelection(row);
			this.selectRows = [row];
		},
		// 添加
		handleAdd() {
			this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "新增配置";
		},
		// 编辑
		handleUpdate(row) {
			this.popData = undefined
			this.reset();
			const id = row.id || this.selectRows[0].id
			this.popLoading = true
			this.open = true;
			this.title = "编辑配置";
			getConfig(id).then(res => {
				this.popData = JSON.parse(JSON.stringify(res.data))
				this.form = res.data;
				this.popLoading = false
			});
		},
		// 删除
		handleDelete(row) {
			var ids = row.id
			if (!row.id) {
				ids = this.selectRows.map((item) => item.id);
			}
			this.$modal.confirm('确定要删除所选记录吗？').then(() => {
				return delConfig(ids);
			}).then(() => {
				this.getList();
				this.$modal.msgSuccess("删除成功");
			}).catch(() => { });
		},
		// 应用
		handleApply() {
			try {
				var row = this.selectRows[0];
				if (row) {
					window.external.configup("js", row.ip, row.aetitle, Number(row.port), row.mappingPath, null);
				} else {
					this.$modal.alertWarning("请选择标识最大的一条数据", "提醒");
				}
			} catch (e) {
				this.$modal.alertWarning("设置失败,请客户端设置!", "提醒");
			}
		},
		// 表单重置
		reset() {
			if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				return
			}
			this.form = {
				flag: Number(this.lastFlag) + 1,
				aetitle: undefined,
				ip: undefined,
				port: 0,
				realPath: undefined,
				mappingPath: undefined,
				visitPath: undefined,
				memo: undefined,
			}
			this.resetForm("form");
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
					saveOrUpdate(this.form).then(() => {
						this.$modal.msgSuccess("保存成功");
						this.open = false;
						this.getList();
					});
				}
			});
		},
	}
}
</script>

<style scoped>
#addForm /deep/ .el-form-item {
	margin-right: 0;
	margin-bottom: 12px;
}
</style>
