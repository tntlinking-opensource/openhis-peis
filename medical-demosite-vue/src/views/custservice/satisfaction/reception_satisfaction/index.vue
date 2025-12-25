<!-- 前台满意度  开发人：麦沃德科技矢北 -->
<template>
	<div class="app-container flex-direction-column">
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" v-show="showSearch">
			<el-form-item label="姓名" prop="patientname">
				<el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
			<el-form-item label="体检号" prop="personcode">
				<el-input v-model="queryParams.personcode" placeholder="请输入收费项目名称" clearable style="width: 180px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>


			<el-form-item label="回访结果" prop="visitResult">
				<el-select v-model="queryParams.visitResult" placeholder="请选择满意度" clearable style="width: 180px;">
					<el-option v-for="options in hfjgOptions" :key="options.id" :label="options.text" :value="options.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="评价结果" prop="appraiseResult">
				<el-select v-model="queryParams.appraiseResult" placeholder="请选择满意度" clearable style="width: 180px;">
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
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-help" @click="handleAdd" :disabled="single"
					v-hasPermi="['custservice:satisfaction:receptionSatisfaction:departmentreturn']">前台满意度回访</el-button>
			</el-col>
		</el-row>

		<!-- 表格 -->
		<div class="table-box">
			<el-table border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column fixed type="selection" width="55" align="center" />
				<el-table-column label="序列" fixed type="index" width="60" align="center" />
				<el-table-column label="评价结果" fixed prop="appraiseResult" min-width="200" align="center"
					show-overflow-tooltip >
					<template slot-scope="scope">
						<span v-if="scope.row.appraiseResult == 1" >非常满意</span>
            <span v-else-if="scope.row.appraiseResult == 2" >满意</span>
						<span v-else-if="scope.row.appraiseResult == 3" >基本满意</span>
            <span v-else-if="scope.row.appraiseResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template> 
					</el-table-column>
				<el-table-column label="回访结果" fixed prop="visitResult" min-width="160" align="center"
					show-overflow-tooltip >
					<template slot-scope="scope">
            <span v-if="scope.row.visitResult == 1" >非常满意</span>
            <span v-else-if="scope.row.visitResult == 2" >满意</span>
						<span v-else-if="scope.row.visitResult == 3" >基本满意</span>
            <span v-else-if="scope.row.visitResult == 4" >不满意</span>
						<span v-else >未评价</span>
					</template> 
					</el-table-column>
				<el-table-column label="体验号" prop="personcode" min-width="160" align="center"
					show-overflow-tooltip />
				<el-table-column label="姓名" prop="patientname" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="性别" prop="idSex" min-width="140" align="center" show-overflow-tooltip />
				<el-table-column label="年龄" prop="age" min-width="160" align="center" show-overflow-tooltip />
				<el-table-column label="公司" prop="orgName" min-width="100" align="center" />
				<el-table-column label="部门" prop="orgDepart	" min-width="100" align="center" />
				<el-table-column label="电话" prop="phone" min-width="100" align="center" />
				<el-table-column label="登记员" prop="ksDoctorName" min-width="100" align="center" />
				<el-table-column label="评价时间" prop="appraiseTime" min-width="100" align="center" />
				<el-table-column label="评价备注" prop="note" min-width="100" align="center" />
				<el-table-column label="回访人" prop="visitPerson" min-width="100" align="center" />
				<el-table-column label="回访时间" prop="visitTime" min-width="100" align="center" />
				<el-table-column label="回访备注" prop="visitNote" min-width="100" align="center" />
			</el-table>
		</div>
		<returnVisit ref="returnVisit" @getList="getList"></returnVisit>
		<!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />

	</div>

</template>

<script>
import { listTestSatisfaction } from '@/api/custservice/satisfaction/reception_satisfaction.js'
import returnVisit from './return.vue'
export default {
	components:{returnVisit},
	data() {
		return {
			total: 0,
			// 遮罩层
			loading: false,
			///查询参数
			queryParams: {
				current: 1,
				size: 10,
				visitResult:'',
				appraiseResult:'',
				xb: undefined,
				patientname:'',
				personcode:'',
				date:[],
        startDate:undefined,
        endDate:undefined,
			},

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
			single:true,
			// 科室列表
			departData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题 
				value: '科室',//第二列标题 
				url: '',//请求连接
				bindValue:''//必传值
			},
			tableList: [
			
			]
		}
	},
	created() {
		this.getList();
	},
	methods: {
		// 查询列表
		getList() {
			this.loading = true;
      if(this.queryParams.date&&this.queryParams.date.length){
        this.queryParams.startDate = this.queryParams.date[0]+' 00:00:00'
        this.queryParams.endDate = this.queryParams.date[1]+' 23:59:59'
      }else{
        this.queryParams.startDate = undefined
        this.queryParams.endDate = undefined
      }
			listTestSatisfaction(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
			}).catch(()=>{
				this.loading = false;
			});
		},
		// 搜索
		handleQuery() {
			this.queryParams.current = 1;
			if (this.queryParams.date) {
				this.queryParams.startDate= this.queryParams.date[0] + " 00:00:00"
				this.queryParams.endDate= this.queryParams.date[1] + " 23:59:59"
			} else {
				this.queryParams.startDate= undefined
				this.queryParams.endDate= undefined
			}
			this.getList();
		},
			///重置
			resetQuery() {
			this.queryParams.date = undefined
			this.queryParams.startData= undefined
			this.queryParams.endData= undefined
		
			this.resetForm("queryForm");
			this.handleQuery();
    },
		// 满意度回访
		handleAdd() {
			this.$refs.returnVisit.getNewList(this.ids[0])
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;

		},
	
	},

}
</script>

<style>

</style>