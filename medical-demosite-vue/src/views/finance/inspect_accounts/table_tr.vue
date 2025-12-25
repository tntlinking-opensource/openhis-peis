<!-- 体检结账单-右上表格  开发人：麦沃德科技半夏 -->
<template>
	<div class="table-container flex-direction-column">
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8" style="padding: 8px 8px 0">
			<el-col :span="1.5">
				<el-button type="primary" plain size="mini" icon="el-icon-refresh-right" @click="handleRefresh">刷新</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="multiple" @click="handleDisable(1)"
					v-hasPermi="['finance:inspectAccounts:header:disable']">禁检
				</el-button>
			</el-col>
			<el-col :span="1.5">
				<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDisable(0)"
					v-hasPermi="['finance:inspectAccounts:header:export']">反禁检
				</el-button>
			</el-col>
		</el-row>
		<!-- 表格 -->
		<div class="table-box">
			<el-table id="tableList" border v-loading="loading" :data="tableList" height="100%" stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center" fixed="left" />
				<el-table-column label="序列" width="55" type="index" align="center" fixed="left" />
				<el-table-column label="禁检" prop="groupLimit" align="center" fixed="left" show-overflow-tooltip >
					<template slot-scope="scope">
						<el-checkbox v-model="scope.row.groupLimit" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
					</template>
				</el-table-column>
				<el-table-column label="分组名称" prop="orgreservationgroupname" align="center" show-overflow-tooltip />
				<el-table-column label="计划日期" prop="dateexamplanned" align="center" show-overflow-tooltip />
				<el-table-column label="分组名称" prop="branch" align="center" show-overflow-tooltip />
				<el-table-column label="分组条件" align="center">
					<el-table-column label="组类" prop="grouptype" align="center" show-overflow-tooltip />
					<el-table-column label="男" prop="forMale" align="center" show-overflow-tooltip >
						<template slot-scope="scope">
							<el-checkbox v-model="scope.row.fMale" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column label="女" prop="forFemale" align="center" show-overflow-tooltip >
						<template slot-scope="scope">
							<el-checkbox v-model="scope.row.fFemale" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column label="已婚" prop="forHasmarried" align="center" show-overflow-tooltip >
						<template slot-scope="scope">
							<el-checkbox v-model="scope.row.fHasmarried" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column label="未婚" prop="forNevermarried" align="center" show-overflow-tooltip >
						<template slot-scope="scope">
							<el-checkbox v-model="scope.row.fNevermarried" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
						</template>
					</el-table-column>
					<el-table-column label="优先" prop="dispOrder" align="center" show-overflow-tooltip />
					<el-table-column label="年龄自" prop="agemin" align="center" show-overflow-tooltip />
					<el-table-column label="年龄至" prop="agemax" align="center" show-overflow-tooltip />
				</el-table-column>
				<el-table-column label="付款方式" prop="payWayName" align="center" show-overflow-tooltip />
			</el-table>
		</div>
		<!-- 分页 -->
		<el-pagination background :pager-count="5" :current-page="queryParams.pageNum" hide-on-single-page
			style="margin: 10px 5px;" :page-sizes="[20, 50, 100, 200 ,500]" :page-size="10" layout="sizes, prev, pager, next, jumper"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
		</el-pagination>
	</div>
</template>
<script>

import {getGroupData,updateGroupLimit} from '@/api/finance/inspect_accounts.js'
import { async } from 'q';

export default {
	components: {},
	props: [],
	data() {
		return {
			// 遮罩层
			loading: false,
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
				pageNum: 1,
				pageSize: 10,
			},
			// 排检表格数据
			tableList: [
			],
			idOrgRw:undefined,
			isUpdate: false,
		};
	},
	created() {
		// this.getList();
	},
	mounted() { },
	methods: {
		// 查询列表
		getList(idOrgRw) {
			this.loading = true;
			if(idOrgRw){
				this.idOrgRw = idOrgRw;
			}
			let obj = { idOrgRw:this.idOrgRw[0] }
			getGroupData(obj).then((res)=>{
				this.tableList = res.data.data;
				this.loading = false;
			})
			// });
		},
		// 分页条数发生改变
		handleSizeChange(val) {
			if (this.queryParams.pageNum * val > this.total) {
				this.queryParams.pageNum = 1
			}
			this.queryParams.pageSize = val
			this.getList()
		},
		// 分页页码发生改变
		handleCurrentChange(val) {
			this.queryParams.pageNum = val
			this.getList()
		},
		// 刷新
		handleRefresh() {
			this.queryParams.pageNum = 1;
			this.getList();
		},
		// 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		// 禁检-反禁检
		async handleDisable(type){
			let obj = {
				ids: "",
				type
			}
			let str = ""
			this.ids.forEach(element => {
				str += element + ",";
			});
			str = str.substring(0,str.length-1);
			obj.ids = str;
			await this.getUpdateGroupLimit(obj);
			if(!this.isUpdate) return;
			this.getList(this.idOrgRw);
		},
		getUpdateGroupLimit(obj){
			console.log("禁检或反禁检的值",obj);

			//禁检1
			if(obj.ids && obj.type == 1){
				updateGroupLimit(obj).then((res)=>{
					this.$modal.msgSuccess("操作成功")
					this.bus.$emit("updateGetList",this.idOrgRw)
				})
			//反禁检0
			} else if(obj.ids && obj.type == 0) {
				updateGroupLimit(obj).then((res)=>{
					this.$modal.msgSuccess("操作成功")
					this.bus.$emit("updateGetList",this.idOrgRw)
				})
			}
			this.isUpdate = true;
		}
	},
};
</script>

<style scoped>
 #tableList /deep/ .el-table__cell{
	padding: 5px 0;
}
.triggerFailure {
	pointer-events: none;
}
</style>
