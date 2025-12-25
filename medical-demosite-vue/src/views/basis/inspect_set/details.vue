<!-- 检查项目设置查看  开发人：麦沃德科技半夏 -->
<template>
	<div class="det-container">
		<!-- 查看对话框 -->
		<el-dialog :title="title" :visible.sync="open" class="det-inspect" width="95%" append-to-body>
			<el-form ref="form" :model="form" v-loading="popLoading" :inline="true" label-width="120px" hide-required-asterisk>
				<el-form-item label="名称" prop="examitemName">
					<el-input v-model="form.examitemName" placeholder="请输入名称" style="width: 220px" />
				</el-form-item>
				<el-form-item label="输入码" prop="inputCode">
					<el-input v-model="form.inputCode" placeholder="输入名称后自动生成" style="width: 220px" />
				</el-form-item>
				<el-form-item label="简称" prop="examitemNameabr">
					<el-input v-model="form.examitemNameabr" placeholder="请输入简称" style="width: 220px" />
				</el-form-item>
				<el-form-item label="类型" prop="type">
					<el-select v-model="form.type" placeholder="请选择类型" style="width: 220px">
						<el-option v-for="item in typeOptions" :key="item.id" :label="item.text" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="科室" prop="divisionId">
					<el-input v-model="form.name" placeholder="请选择科室" style="width: 220px" />
				</el-form-item>
				<el-form-item label="检查项目类型" prop="idExamitemtype">
					<el-input v-model="form.examitemtypeName" placeholder="请选择检查项目类型" style="width: 220px" />
				</el-form-item>
				<el-form-item label="男性体征上限" prop="valuemalemax">
					<el-input-number v-model="form.valuemalemax" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="男性体征下限" prop="valuemalemin">
					<el-input-number v-model="form.valuemalemin" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="女性体征上限" prop="valuefemalemax">
					<el-input-number v-model="form.valuefemalemax" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="女性体征下限" prop="valuefemalemin">
					<el-input-number v-model="form.valuefemalemin" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="自动完成" prop="forLabByhand">
					<el-checkbox v-model="form.forLabByhand" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="结论词（高值）" prop="idConclusionhi">
					<el-input v-model="form.idConclusionhiName" placeholder="请选择结论词（高值）" style="width: 220px" />
				</el-form-item>
				<el-form-item label="结论词（低值）" prop="idConclusionlo">
					<el-input v-model="form.idConclusionloName" placeholder="请选择结论词（低值）" style="width: 220px" />
				</el-form-item>
				<el-form-item label="排序" prop="disporder">
					<el-input-number v-model="form.disporder" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="名称进小结" prop="isName">
					<el-checkbox v-model="form.isName" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="男/女标示" prop="forMale">
					<el-select v-model="form.forMale" placeholder="请选择标示" style="width: 220px">
						<el-option v-for="item in forMaleOptions" :key="item.id" :label="item.text" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="危急值上限" prop="valuedangerousmax">
					<el-input-number v-model="form.valuedangerousmax" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="危急值下限" prop="valuedangerousmin">
					<el-input-number v-model="form.valuedangerousmin" controls-position="right" :min="0" style="width: 220px;" />
				</el-form-item>
				<el-form-item label="描述进小结" prop="isDesc">
					<el-checkbox v-model="form.isDesc" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="自由输入" prop="fEntryonly">
					<el-checkbox v-model="form.fEntryonly" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="结论词（阳性）" prop="idConclusionpo">
					<el-input v-model="form.idConclusionpoName" placeholder="请选择结论词（阳性）" style="width: 220px" />
				</el-form-item>
				<el-form-item label="结论词（阴性）" prop="idConclusionne">
					<el-input v-model="form.idConclusionneName" placeholder="请选择结论词（阴性）" style="width: 220px" />
				</el-form-item>
				<el-form-item label="是否公共" prop="isPublic">
					<el-checkbox v-model="form.isPublic" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="分中心" prop="fzxIds">
					<el-select v-model="form.fzxIds" placeholder="请选择标示" style="width: 220px">
						<el-option v-for="item in fzxOptions" :key="item.id" :label="item.text" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="接口代码" prop="interfaceCode">
					<el-input v-model="form.interfaceCode" placeholder="请输入接口代码" style="width: 220px" />
				</el-form-item>
				<el-form-item label="打印名称" prop="examitemNameprn">
					<el-input v-model="form.examitemNameprn" placeholder="请输入打印名称" style="width: 220px" />
				</el-form-item>
				<el-form-item label="手动获取" prop="FLabNowait">
					<el-checkbox v-model="form.FLabNowait" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="收费项目可重复" prop="fCanDup">
					<el-checkbox v-model="form.fCanDup" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="描述进结果" prop="patientbaseinfodisporder">
					<el-checkbox v-model="form.patientbaseinfodisporder" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="检验标示重置" prop="examitemCodehm">
					<el-select v-model="form.examitemCodehm" placeholder="请选择标示" style="width: 220px">
						<el-option v-for="item in examitemOptions" :key="item.id" :label="item.text" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="艾迪康代码" prop="examitemCode3">
					<el-input v-model="form.examitemCode3" placeholder="请选择艾迪康代码" style="width: 220px" />
				</el-form-item>
				<el-form-item label="是否外送" prop="expressionoccudisease">
					<el-checkbox v-model="form.expressionoccudisease" style="width: 220px" border />
				</el-form-item>
				<el-form-item label="检查结果类型" prop="valuetype">
					<el-select v-model="form.valuetype" placeholder="请选择标示" style="width: 220px">
						<el-option v-for="item in valueTypeOptions" :key="item.id" :label="item.text" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="计量单位" prop="valueunit">
					<el-input v-model="form.valueunit" placeholder="请输入计量单位" style="width: 220px" />
				</el-form-item>
				<el-form-item label="东软Pacs关联" prop="devicetypePositionCheckitem">
					<el-input v-model="form.devicetypePositionCheckitem" placeholder="请选择东软Pacs关联" style="width: 220px" />
				</el-form-item>
				<el-form-item label="是否显示单位" prop="addUnit">
					<el-checkbox v-model="form.addUnit" style="width: 220px" border />
				</el-form-item>
			</el-form>
			<div class="det-table">
				<!-- 筛选 -->
				<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" style="padding: 14px 18px 0;">
					<el-form-item label="体征词名称" prop="tzcname">
						<el-input v-model="queryParams.tzcname" placeholder="请输入体征词名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
					</el-form-item>
					<el-form-item label="结论词" prop="resultId">
						<el-input v-model="queryParams.resultId" placeholder="请输入结论词" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
						<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
					</el-form-item>
				</el-form>
				<el-table border v-loading="loading" :data="tableList" stripe>
					<el-table-column type="selection" width="55" align="center" />
					<el-table-column label="序列" type="index" width="65" align="center" />
					<el-table-column label="顺序" prop="orderindex" min-width="120" align="center" />
					<el-table-column label="体征词名称" prop="tzcname" min-width="120" align="center" show-overflow-tooltip />
					<el-table-column label="是否阳性结果" prop="isPositive" min-width="120" align="center">
						<template slot-scope="scope">
							<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isPositive == 1"><i class="el-icon-check"></i>
							</el-tag>
							<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
						</template>
					</el-table-column>
					<el-table-column label="体征详细描述" prop="bodyDetail" min-width="153" align="center" show-overflow-tooltip />
					<el-table-column label="体征详细描述（职业病）" prop="bodyDetailZy" min-width="180" align="center" show-overflow-tooltip />
					<el-table-column label="结论词" prop="resultId" min-width="120" align="center" show-overflow-tooltip />
					<el-table-column label="可疑疾病重症级0到9级" prop="intensiveLevel" min-width="160" align="center" show-overflow-tooltip />
					<el-table-column label="默认" prop="isDefault" min-width="120" align="center">
						<template slot-scope="scope">
							<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isDefault == 1"><i class="el-icon-check"></i>
							</el-tag>
							<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
						</template>
					</el-table-column>
					<el-table-column label="不进小结" prop="isInSummary" min-width="120" align="center">
						<template slot-scope="scope">
							<el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isInSummary == 1"><i class="el-icon-check"></i>
							</el-tag>
							<el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
						</template>
					</el-table-column>
					<el-table-column label="互斥分组（异组互斥）" prop="otherMutex" min-width="160" align="center" show-overflow-tooltip />
					<el-table-column label="互斥分组（同组同正整数编号互斥）" prop="numMutex" min-width="240" align="center" show-overflow-tooltip />
				</el-table>
			</div>
		</el-dialog>
	</div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";

import { getBasexamltem, getFeatureListData, getBranchData } from "@/api/basis/inspect";

export default {
	components: {},
	props: [],
	data() {
		return {
			// 体检类型列表
			typeOptions: [
				{
					id: 0,
					text: "健康体检"
				},
				{
					id: 1,
					text: "职业体检"
				},
				{
					id: 2,
					text: "综合"
				}
			],
			// 分中心
			fzxOptions: [],
			// 性别列表
			forMaleOptions: [
				{
					id: 0,
					text: "男"
				},
				{
					id: 1,
					text: "女"
				},
				{
					id: 2,
					text: "通用"
				}
			],
			// 检验标示列表
			examitemOptions: [
				{ id: 1, text: "<" },
				{ id: 2, text: ">" },
				{ id: 3, text: "<>" }
			],
			// 检查结果类型
			valueTypeOptions: [
				{ id: 1, text: '数值型' },
				{ id: 2, text: '字符型' },
				{ id: 3, text: '枚举型' },
			],
			// 表单参数
			form: {
			},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			// 查询参数
			queryParams: {
				current: 1,
				size: 10,
				tzcname: undefined,
				resultId: undefined,
			},
			// 表格列表
			tableList: [
			],
			// 遮罩层
			loading: true,
			// 选中数组
			ids: [],
			popLoading: false,
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() { },
	methods: {
		// 显示
		async handleShow(data) {
			let obj = { id: data.id }
			this.open = true;
			this.title = "查看检查项目";
			this.popLoading = true;
			let pass = await this.getInitalData();
			if (!pass) return;
			getBasexamltem(data.id).then(response => {
				this.popData = JSON.parse(JSON.stringify(response.data))
				this.popLoading = false;
				this.form = response.data;
				this.form.fzxIds = Number(this.form.fzxIds);
				getFeatureListData(obj).then(res => {
					if (res.code == 200) {
						this.tableList = res.data.records;
					}
				})
			}).catch(() => {
				this.popLoading = false;
			})
		},
		getInitalData() {
			getBranchData().then(res => {
				this.fzxOptions = res.data;
			})
			return true;
		},
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
			this.queryParams.current = 1;
			this.getList();
		},
		// 重置
		resetQuery() {
			this.resetForm("queryForm");
			this.handleQuery();
		},
	},
};
</script>
<style lang="scss">
.det-inspect {
	.el-dialog {
		min-width: 800px;
	}

	.el-form-item {
		margin-bottom: 20px;
	}

	.el-form-item__content,
	.el-input__suffix-inner {
		pointer-events: none;
	}

	.det-table {
		border: 1px solid #D4D6D9;

		.el-table {
			margin: -1px;
		}
	}
}
</style>