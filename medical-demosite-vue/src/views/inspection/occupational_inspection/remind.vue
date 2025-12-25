<!-- 职业总检-查看详情-提醒 麦沃德科技 开发人:清风 -->
<template>
	<div class="add-container">
		<el-dialog title="查看提醒" class="add-items" :visible.sync="open" width="1600px" @close="handleClose" append-to-body :close-on-click-modal="false">
			<div style="display: flex; flex-direction: column;">
				<div style="display: flex; flex-direction: row;">
					<div style="flex-shrink: 0;">
						<el-form id="setTextarea" :inline="true" label-width="100px" @submit.native.prevent style="width:450px;border: 1px solid rgb(225, 225, 225);">
							<el-form-item>
								<template slot="label">
									<div style="font-size: 18px; ">提醒内容</div>
								</template>
								<template>
									<el-input type="textarea" v-model="rowSelection.content" readonly></el-input>
								</template>
							</el-form-item>
						</el-form>
						<el-form label-width="80px" :model="model" style="width:450px; margin-top:20px; border: 1px solid rgb(225, 225, 225);">
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
						<el-form :inline="true" label-width="100px">
							<el-form-item label="体检号" label-width="56px">
								<el-input style="width:160px;" placeholder="请输入体检号" @keyup.enter.native="goGetRemindPatient" v-model="model.searchcode"></el-input>
							</el-form-item>
							<el-form-item label="选择日期">
								<el-date-picker style="width:366px;" v-model="model.valueDate" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="goGetRemindPatient"></el-date-picker>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="goGetRemindPatient">搜索</el-button>
								<el-button @click="resetRemindPatient">重置</el-button>
							</el-form-item>
						</el-form>
						<div class="table-box" style="flex:1;">
							<el-table :data="tableData" v-loading="loading" :row-style="{ height: '42px' }" @row-click="rowClick" :cell-style="{ height: '42px', padding: '0px' }" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
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
import { } from "@/api/inspection/occupational_inspection.js"

export default {
	data() {
		return {
			// 遮罩层
			loading: false,
			// 显示搜索条件
			showSearch: true,
			open: false,
			total: 0,
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
			rowSelection: {
				content: "",
				depName: "",
				patientcode: "",
				patientname: ""
			}
		}
	},
	methods: {
		remindWindow(data) {
			this.model.patientcode = data.patientno || data.patientcode;
			this.model.patientname = data.patientname;
			this.open = true;
			this.goGetRemindPatient();
		},
		goGetRemindPatient() {
			this.loading = true;
			var valueDate_0 = "", valueDate_1 = "";
			if (!this.model.valueDate) {
				valueDate_0 = "";
				valueDate_1 = "";
			} else {
				valueDate_0 = this.model.valueDate[0];
				valueDate_1 = this.model.valueDate[1];
			}
			var obj = {
				current: 1,
				size: 10,
				startTime: valueDate_0 || "",//开始时间
				endTime: valueDate_1 || "",//结束时间
				ksID: "",//科室ID
				patientcode: this.model.searchcode || this.model.patientcode,//体检号
			}
			getRemindPatient(obj).then((res) => {
				if (res.code == 200) {
					this.tableData = res.data.records;
					res.data.records.forEach(element => {
						if (this.model.patientcode == element.patientcode) {
							this.rowSelection = element;
							this.rowSelection.content.split("/n/t").join("<br>");
							return
						}
					});
					this.loading = false;
				}
				this.loading = false;
			}).catch(() => {
				this.loading = false;
			})
		},
		resetRemindPatient() {
			this.$set(this.model, "searchcode", "");
			this.$set(this.model, "valueDate", []);
			this.getList();
		},
		//关闭弹窗
		handleClose() {
			this.tableData = [];
			this.model = {};
		},
		getList() {

		},
		handleSelectionChange(selection) {
			if (selection.length == 1) {
				this.selection = selection[0]
			}
		},
		rowClick(row) {
			this.rowSelection = row;
			this.rowSelection.content.split("/n/t").join("<br>");
			this.model.patientcode = row.patientcode;
			this.model.patientname = row.patientname;
		}
	}
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
	width: 420px !important;
	min-height: 500px !important;
	border: 0 !important;
	background-color: rgb(246, 247, 251) !important;
	margin-left: 10px !important;
}
</style>