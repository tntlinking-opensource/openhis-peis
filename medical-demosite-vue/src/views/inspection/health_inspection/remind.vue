<!-- 健康总检-查看详情-提醒 麦沃德科技 开发人:清风 -->
<template>
	<div class="add-container">
		<el-dialog title="查看提醒" class="add-items" :visible.sync="open" width="1600px" append-to-body @close="closeDialog" :close-on-click-modal="false">
			<div style="display: flex; flex-direction: column;">
				<div style="display: flex; flex-direction: row;">
					<div style="flex-shrink: 0;">
						<el-form :inline="true" label-width="100px" style="width:450px;border: 1px solid rgb(225, 225, 225);">
							<el-form-item>
								<template slot="label">
									<div style="font-size: 18px; ">提醒内容</div>
								</template>
								<div style="width:420px; height: 500px; background-color: rgb(246, 247, 251); margin-left: 10px;"></div>
							</el-form-item>
						</el-form>
						<el-form label-width="80px" style="width:450px; margin-top:20px; border: 1px solid rgb(225, 225, 225);">
							<el-form-item style="margin-bottom:10px;">
								<template slot="label">
									<div style="font-size: 18px;">体检人</div>
								</template>
							</el-form-item>
							<el-form-item label="体检号" style="margin-bottom:10px;">
								<el-input placeholder="请输入体检号" v-model="model.patientcode" readonly style="width:350px;"></el-input>
							</el-form-item>
							<el-form-item label="姓名" style="margin-bottom:10px;">
								<el-input placeholder="请输入姓名" v-model="model.patientname" readonly style="width:350px;"></el-input>
							</el-form-item>
						</el-form>
					</div>

					<div style="flex-shrink:1;flex-grow: 1;margin-left: 16px; display: flex;flex-direction: column;">
						<el-form :inline="true" label-width="100px" @submit.native.prevent>
							<el-form-item label="体检号" label-width="56px">
								<el-input style="width:160px;" placeholder="请输入体检号" v-model="model.searchcode" @keyup.enter.native="remindWindow(rowData)"></el-input>
							</el-form-item>
							<el-form-item label="选择日期">
								<el-date-picker style="width:366px;" v-model="model.valueDate" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="remindWindow(rowData)"></el-date-picker>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="remindWindow(rowData)">搜索</el-button>
								<el-button @click="resetRemindWindow">重置</el-button>
							</el-form-item>
						</el-form>
						<div class="table-box" style="flex-grow:1;">
							<el-table :data="tableData" v-loading="loading" :row-style="{ height: '42px' }" :cell-style="{ height: '42px', padding: '0px' }" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
								<el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
								<el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
								<el-table-column prop="depName" label="科室" align="center"></el-table-column>
								<el-table-column prop="content" label="提醒内容" align="center"></el-table-column>
							</el-table>
						</div>
					</div>
				</div>
				<!-- 分页 -->
				<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { getRemindPatient } from "@/api/inspection/health_inspection.js"

export default {
	data() {
		return {
			// 遮罩层
			loading: false,
			// 选中数组
			ids: [],
			// 非单个禁用
			single: true,
			// 非多个禁用
			multiple: true,
			// 显示搜索条件
			showSearch: true,
			open: false,
			total: 30,
			queryParams: {
				pageNum: 1,
				pageSize: 30,
			},
			model: {
				patientcode: "",
				patientname: "",
				searchcode: "",
				valueDate: []
			},
			tableData: [
				{
					patientcode: "",
					patientname: "",
					depName: "",
					content: "",
				}
			],
			rowData: {}
		}
	},
	methods: {
		remindWindow(data) {
			this.loading = true;
			this.rowData = data;
			this.model.patientcode = this.rowData.patientcode;
			this.model.patientname = this.rowData.patientname;
			this.open = true;
			if (!this.model.valueDate) {
				this.model.valueDate[0] = "";
				this.model.valueDate[1] = "";
			}
			var obj = {
				current: 1,
				size: 10,
				startTime: this.model.valueDate[0] || "",//开始时间
				endTime: this.model.valueDate[1] || "",//结束时间
				ksID: "",//科室ID
				patientcode: this.model.searchcode || this.model.patientcode,//体检号
			}
			getRemindPatient(obj).then((res) => {
				this.tableData = res.data.records;
				this.loading = false;
			})
		},
		resetRemindWindow() {
			this.$set(this.model, "searchcode", "");
			this.$set(this.model, "valueDate", []);
			this.remindWindow(this.rowData)
		},
		getList() {
			var obj = {
				current: this.queryParams.pageNum,
				size: this.queryParams.pageSize,
				startTime: this.model.valueDate[0] || "",//开始时间
				endTime: this.model.valueDate[1] || "",//结束时间
				ksID: "",//科室ID
				patientcode: this.model.searchcode || this.model.patientcode,//体检号
			}
			getRemindPatient(obj).then((res) => {
				this.tableData = res.data.records;
				this.loading = false;
			})
		},
		closeDialog() {
			this.model.searchcode = "";
		},
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => { return item.id });
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
	}
}
</script>
