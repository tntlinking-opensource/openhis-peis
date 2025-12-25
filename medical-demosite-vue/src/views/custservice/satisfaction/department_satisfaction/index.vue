<!-- 科室满意度  开发人：麦沃德科技矢北 -->
<template>
	<div class="app-container flex-direction-column">
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" v-show="showSearch">
			<el-form-item label="姓名" prop="patientname">
				<el-input v-model="queryParams.patientname" placeholder="请输入输入码" clearable style="width: 230px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="体检号" prop="personcode">
				<el-input v-model="queryParams.personcode" placeholder="请输入收费项目名称" clearable style="width: 230px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="科室名称" prop="idDepart">
				<input-select :selectData="selectData1" v-model="queryParams.idDepart" @change="selectChange1"></input-select>
			</el-form-item>
			<el-form-item label="回访结果" prop="visitResult">
				<el-select v-model="queryParams.visitResult" placeholder="请选择满意度" clearable style="width: 230px;">
					<el-option v-for="options in hfjgOptions" :key="options.id" :label="options.text" :value="options.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="评价结果" prop="appraiseResult">
				<el-select v-model="queryParams.appraiseResult" placeholder="请选择满意度" clearable style="width: 230px;">
					<el-option v-for="options in pjjgOptions" :key="options.id" :label="options.text" :value="options.id" />
				</el-select>
				<!-- 日期选择器 -->
			</el-form-item>
			<el-form-item label="选择日期">
				<el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
					range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
			<!-- 操作按钮 -->
			<el-row :gutter="10" class="mb8">
				<el-col :span="1.5">
					<el-button type="success" plain size="mini" icon="el-icon-help" :disabled="single" @click="handleAdd"
						v-hasPermi="['custservice:satisfaction:departmentSatisfaction:departmentreturn']">科室满意度回访</el-button>
				</el-col>
				<el-col :span="1.5">
					<el-button type="success" plain size="mini" icon="el-icon-help" @click="cancel"
						v-hasPermi="['custservice:satisfaction:departmentSatisfaction:cancel']">取消不满意</el-button>
				</el-col>
				<el-col :span="1.5">
					<el-button type="success" plain size="mini" icon="el-icon-help" @click="exportData"
						v-hasPermi="['custservice:satisfaction:departmentSatisfaction:informationexport']">导出</el-button>
				</el-col>
			</el-row>
		</el-form>
		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" width="55" align="center" />
				<el-table-column label="序列" fixed type="index" width="60" align="center" />
				<el-table-column label="评价结果" fixed prop="appraiseResult" min-width="200" align="center"
					show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in pjjgOptions" :key="item.id">
							<span v-if="(item.id == scope.row.appraiseResult)">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="回访结果" fixed prop="visitResult" min-width="160" align="center"
					show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="item in hfjgOptions" :key="item.id">
							<span v-if="(item.id == scope.row.visitResult)">{{ item.text }}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="体验号" prop="experiencecode" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="姓名" prop="patientname" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="性别" prop="idSex" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="年龄" prop="age" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="公司" prop="orgName" min-width="100" align="center" />
				<el-table-column label="部门" prop="orgDepart" min-width="100" align="center" />
				<el-table-column label="电话" prop="phone" min-width="100" align="center" />
				<el-table-column label="评价医生" prop="ksDoctorName" min-width="100" align="center" />
				<el-table-column label="评价科室" prop="ksDoctorName" min-width="100" align="center" />
				<el-table-column label="评价时间" prop="appraiseTime" min-width="100" align="center" />
				<el-table-column label="评价备注" prop="reviewnotes" min-width="100" align="center" />
				<el-table-column label="回访人" prop="visitPerson" min-width="100" align="center" />
				<el-table-column label="回访时间" prop="visitTime" min-width="100" align="center" />
				<el-table-column label="回访备注" prop="returnnotes" min-width="100" align="center" />
			</el-table>
		</div>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
		<addView ref="addView" @getList="getList"> </addView>
	</div>
</template>
<script>
import { listDepartmentSatisfaction, cancelDissatisfactory } from '@/api/custservice/satisfaction/department_satisfaction.js'
import addView from "./view.vue"
export default {
	components: { addView },
	data() {
		return {
			///总数
			total: 0,
			// 遮罩层
			loading: false,
			///查询参数
			queryParams: {
				currrent: 1,
				size: 10,
				sfxmsrm: undefined,
				personcode: undefined,
				visitResult: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
				date: undefined,
				startDate: undefined,
				endDate: undefined,
				appraiseResult: undefined,
				idDepart: undefined,
				patientname: undefined
			},
			// 科室名称筛选数据
			selectData1: {
				placeholder: '请输入输入码选择',
				key: '科室码',
				value: '科室名称',
				url: '/basconclusion/findAllDepartment',
				queryData: 'key',
				bindValue: ' ', //初始值(必传)
			},
			single: true,
			// 显示搜索条件
			showSearch: true,
			// 评价结果
			pjjgOptions: [
				{
					id: 2,
					text: "满意"
				},
				{
					id: 1,
					text: "非常满意"
				},
				{
					id: 4,
					text: "取消不满意"
				},
				{
					id: 0,
					text: "未评价"
				},
				{
					id: 3,
					text: "不满意"
				}
			],
			//回访结果
			hfjgOptions: [
				{
					id: 2,
					text: "满意"
				},
				{
					id: 1,
					text: "非常满意"
				},
				{
					id: 4,
					text: "不满意"
				},
				{
					id: 0,
					text: "未评价"
				},
				{
					id: 3,
					text: "基本满意"
				}
			],

			manyidu: [],
			tableList: [
			]
		}
	},
	created() {
		this.getList();
	},
	methods: {
		// 重置
		resetQuery() {
			this.queryParams.patientname = undefined
			this.queryParams.personcode = undefined
			this.queryParams.appraiseResult = undefined
			this.queryParams.visitResult = undefined
			this.queryParams.date = undefined
			this.resetForm("queryForm");
			this.handleQuery();
		},
		//选择框
		selectChange1(value) {
			this.selectData1.bindValue = value.name;
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			if (this.queryParams.date) {
				this.queryParams.startDate = this.queryParams.date[0] + " 00:00:00"
				this.queryParams.endDate = this.queryParams.date[1] + " 23:59:59"

			} else {
				this.queryParams.startDate = undefined
				this.queryParams.endDate = undefined
			}

			this.getList();
		},
		// 添加
		handleAdd() {
			
			if (this.manyidu[0] != 4) {
				this.$refs.addView.getNewList(this.ids[0])
			} else {
				this.$message({
					message: '	请选择评价结果为不满意的记录进行回访！！！',
					type: 'warning'
				});
			}

		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.manyidu = selection.map((item) => item.appraiseResult);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 查询列表
		getList() {
			this.loading = true;
			listDepartmentSatisfaction(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
			});
			this.loading = false;
			// });
		},
		exportData() {
			this.download('/satisfaction/export', {
				...this.queryParams
			}, `科室不满意度回访.xlsx`)

		},
		cancel() {
			cancelDissatisfactory(this.ids).then(response => {
				
				this.getList()
			})
		},
	},


}
</script>
<style></style>