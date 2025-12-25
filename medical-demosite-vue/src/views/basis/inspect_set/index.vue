<!-- 检查项目设置  开发人：麦沃德科技半夏/清风 -->
<template>
	<div class="app-container flex-direction-column">
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" v-show="showSearch">
			<el-form-item label="名称" prop="examitemName">
				<el-input v-model="queryParams.examitemName" placeholder="请输入名称" clearable style="width: 230px;" @keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="输入码" prop="inputCode">
				<el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px;" @keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="性别" prop="forMale">
				<el-select v-model="queryParams.forMale" placeholder="请选择性别" clearable style="width: 230px;" @change="handleQuery">
					<el-option label="男" :value="0" />
					<el-option label="女" :value="1" />
					<el-option label="通用" :value="2" />
				</el-select>
			</el-form-item>
			<el-form-item label="类型" prop="type">
				<el-select v-model="queryParams.type" placeholder="请选择类型" clearable style="width: 230px;" @change="handleQuery">
					<el-option v-for="options in typeOptions" :key="options.id" :label="options.text" :value="options.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="科室" prop="divisionId">
				<input-select :selectData="selectData" selectWidth="230" @change="divisionIdChange"></input-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:inspectSet:add']">添加</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:inspectSet:edit']">编辑
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:inspectSet:remove']">删除
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-view" :disabled="single" @click="handleDetails" v-hasPermi="['basis:inspectSet:details']">查看
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="warning" plain size="mini" icon="el-icon-connection" :disabled="single" @click="handleRelation" v-hasPermi="['basis:inspectSet:relation']">查看第三方接口关联
				</el-button>
			</el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
				<el-table-column fixed type="selection" width="55" align="center" />
				<el-table-column label="序列" fixed type="index" width="60" align="center" />
				<el-table-column label="名称" fixed prop="examitemName" min-width="200" align="center" show-overflow-tooltip />
				<el-table-column label="简称" prop="examitemNameabr" min-width="200" align="center" show-overflow-tooltip />
				<el-table-column label="输入码" prop="inputCode" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="类型" prop="type" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in typeOptions" :key="item.id">
							<span v-if="item.id == scope.row.type">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<!-- name -- divisionId -->
				<el-table-column label="科室" prop="divisionId" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in divisionTable" :key="item.id">
							<span v-if="item.id == scope.row.divisionId">{{ item.departName }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="接口代码" prop="interfaceCode" min-width="120" align="center" show-overflow-tooltip />
				<el-table-column label="检查项目类型" prop="idExamitemtype" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in idExamitemtype" :key="item.id">
							<span v-if="item.id == scope.row.idExamitemtype">{{ item.examitemtypeName }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="男性体征词上限" prop="valuemalemax" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="男性体征词下限" prop="valuemalemin" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="女性体征词上限" prop="valuefemalemax" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="女性体征词下限" prop="valuefemalemin" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="手工录入标示" prop="forLabByhand" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="分中心" prop="fzxIds" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in fzxIds" :key="item.id">
							<span v-if="item.id == scope.row.fzxIds">{{ item.fzx }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="结论词（高值）" prop="idConclusionhi" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="结论词（低值）" prop="idConclusionlo" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="描述进小结" prop="isDesc" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isDesc == 1"><i class="el-icon-check"></i>
						</el-tag>
						<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
					</template>
				</el-table-column>
				<el-table-column label="名称进小结" prop="isName" min-width="140" align="center" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isName == 1"><i class="el-icon-check"></i>
						</el-tag>
						<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
					</template>
				</el-table-column>
				<el-table-column label="男/女标示" prop="forMale" min-width="120" align="center" show-overflow-tooltip>
					<!-- forMale -- fmale -->
					<template slot-scope="scope">
						<div v-for="item in forMaleOptions" :key="item.id">
							<span v-if="item.id == scope.row.fmale">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="危急值上限" prop="valuedangerousmax" min-width="120" align="center" show-overflow-tooltip />
				<el-table-column label="危急值下限" prop="valuedangerousmin" min-width="120" align="center" show-overflow-tooltip />
				<el-table-column label="排序" prop="disporder" min-width="100" align="center" show-overflow-tooltip />
				<el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
					<template slot-scope="scope">
						<el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:inspectSet:edit']">编辑</el-button>
						<el-button size="mini" type="text" style="color: #0059FF" @click="handleView(scope.row)" v-hasPermi="['basis:inspectSet:query']">查看项目</el-button>
						<el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:inspectSet:remove']">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
		<!-- 添加或修改对话框 -->
		<add-items ref="addItems" @getList="getList"></add-items>
		<!-- 详情对话框 -->
		<details-items ref="detailsItems"></details-items>
		<!-- 第三方接口关联对话框 -->
		<relation-items ref="relationItems"></relation-items>
		<!-- 检查项目对话框 -->
		<el-dialog title="检查项目" :visible.sync="showExam" width="1000px" append-to-body :close-on-click-modal="false">
			<div style="height: 400px;min-height: 400px;">
				<el-table border v-loading="loading" :data="detailList" height="100%" stripe>
					<el-table-column label="序列" type="index" width="65" align="center" />
					<el-table-column label="顺序" prop="orderindex" width="120" align="center" />
					<el-table-column label="体征词名称" prop="tzcname" width="120" align="center" show-overflow-tooltip />
					<el-table-column label="是否阳性结果" prop="checkboxcolumn" width="120" align="center">
						<template slot-scope="scope">
							<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.checkboxcolumn == 1">
								<i class="el-icon-check"></i>
							</el-tag>
							<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
						</template>
					</el-table-column>
					<el-table-column label="体征详细描述" prop="bodyDetail" width="153" align="center" show-overflow-tooltip />
					<el-table-column label="体征详细描述（职业病）" prop="bodyDetailZy" width="180" align="center" show-overflow-tooltip />
					<el-table-column label="结论词" prop="resultId" width="120" align="center" show-overflow-tooltip />
					<el-table-column label="可疑疾病重症级0到9级" prop="intensiveLevel" width="160" align="center" show-overflow-tooltip />
					<el-table-column label="不进小结" prop="isInSummary" width="120" align="center">
						<template slot-scope="scope">
							<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isInSummary == 1">
								<i class="el-icon-check"></i>
							</el-tag>
							<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
						</template>
					</el-table-column>
					<el-table-column label="互斥分组（异组互斥）" prop="otherMutex" width="160" align="center" show-overflow-tooltip />
					<el-table-column label="互斥分组（同组同正整数编号互斥）" prop="numMutex" width="240" align="center" show-overflow-tooltip />
				</el-table>
			</div>
		</el-dialog>
	</div>
</template>
<script>
import { listBasexamltem, delBasexamltem, getFeatureListData, typelist, getBranchData } from "@/api/basis/inspect";
import { ksBasic } from "@/api/statistical/every_project.js"
import addItems from './add'
import detailsItems from './details'
import relationItems from './relation'
export default {
	name: "Inspect_set",
	components: { addItems, detailsItems, relationItems },
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
				size: 20,
				examitemName: undefined,
				inputCode: undefined,
				forMale: undefined,
				type: undefined,
				divisionId: undefined,
			},
			// 排检表格数据
			tableList: [],
			// 类型
			typeOptions: [
				{ id: 0, text: '健康体检' },
				{ id: 1, text: '职业体检' },
				{ id: 2, text: '综合' }
			],
			// 科室
			divisionOptions: [
				{ id: 1, text: '健康问卷' },
				{ id: 2, text: '检验科' }
			],
			// 检查项目类型
			itemTypeOptions: [
				{ id: 1, text: '全科' },
				{ id: 2, text: '免疫' }
			],
			// 性别
			forMaleOptions: [
				{ id: 0, text: "男" },
				{ id: 1, text: "女" },
				{ id: 2, text: "通用" }
			],
			// 检查项目列表
			detailList: [],
			// 科室选择
			selectData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题
				value: '科室',//第二列标题
				url: '/system/keshi/list',//请求连接
				bindValue: '',
				firstName: 'srm',//接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'name',//接口返回值对应第二列的参数名(不传默认为'name')
			},
			// 显示模态框
			showExam: false,
			ids: [],
			selection: [],
			divisionTable: [],//table 科室
			idExamitemtype: [],
			fzxIds: [],
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getKsOptions();
		this.getList();
	},
	mounted() { },
	methods: {
		//查询所有科室/类型ID/分中心
		getKsOptions() {
			ksBasic().then((res) => {
				this.divisionTable = res.data;
			});
			typelist().then((res) => {
				this.idExamitemtype = res.data;
			})
			getBranchData().then(res => {
				this.fzxIds = res.data;
			})
		},
		// 查询列表
		getList() {
			this.loading = true;
			listBasexamltem(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
			});
		},
		// 选择科室
		divisionIdChange(value) {
			this.queryParams.divisionId = value.id
			this.divisionOptions.bindValue = value.name
			this.handleQuery()
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			this.getList();
		},
		// 重置
		resetQuery() {
			this.divisionOptions.bindValue = undefined
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
					return delBasexamltem(ids);
				})
				.then(() => {
					this.getList();
					this.$modal.msgSuccess("删除成功");
				})
				.catch(() => { });
		},
		// 编辑
		handleUpdate(row) {
			let data = row.id ? row : this.selection[0];
			this.$refs.addItems.handleUpdate(data);
		},
		// 查看项目
		handleView(row) {
			this.showExam = true
			let obj = {
				id: row.id
			}
			getFeatureListData(obj).then((res) => {
				if (res.code == 200) {
					this.detailList = res.data.records;
				}
			})
		},
		// 查看
		handleDetails() {
			let data = this.selection[0];
			this.$refs.detailsItems.handleShow(data)
		},
		// 查看第三方接口关联
		handleRelation() {
			this.$refs.relationItems.getList(this.ids)
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
			if (selection.length == 1) {
				this.selection = selection;
			} else {
				this.selection = [];
			}
		},
		// 表格单击事件
		rowClick(row, col) {
			if (col.type != 'selection') this.$refs.tableList.clearSelection()
			this.$refs.tableList.toggleRowSelection(row)
		},
	},
};
</script>
<style lang="scss"></style>