<!-- 待领取报告-查看 麦沃德科技 开发人:清风/半夏 -->
<template>
	<el-dialog title="查看" class="report-view" :visible.sync="open" width="466px" append-to-body
		:close-on-click-modal="false">
		<el-form ref="form" :model="form" :inline="true" label-width="110px" hide-required-asterisk v-loading="popLoading">
			<el-form-item label="体检号" style="margin-right:0;" prop="patientcode">
				<el-input style="width:308px;" v-model="form.patientcode" readonly></el-input>
			</el-form-item>
			<el-form-item label="客户姓名" style="margin-right:0;" prop="patientname">
				<el-input style="width:308px;" v-model="form.patientname" readonly></el-input>
			</el-form-item>
			<el-form-item label="联系方式" style="margin-right:0;" prop="phone">
				<el-input style="width:308px;" v-model="form.phone" readonly></el-input>
			</el-form-item>
			<el-form-item label="客户单位名称" style="margin-right:0;" prop="orgName">
				<el-input style="width:308px;" v-model="form.orgName" readonly></el-input>
			</el-form-item>
			<el-form-item label="柜子号" style="margin-right:0;" prop="chestNum">
				<el-input style="width:308px;" v-model="form.chestNum" readonly></el-input>
			</el-form-item>
			<el-form-item label="交接人" style="margin-right:0;" prop="joinPersonMan">
				<el-input style="width:308px;" v-model="form.joinPersonMan" readonly></el-input>
			</el-form-item>
			<el-form-item label="交接时间" style="margin-right:0;" prop="joinTime">
				<el-input style="width:308px;" v-model="form.joinTime" readonly></el-input>
			</el-form-item>
			<el-form-item label="报告状态" style="margin-right:0;" prop="status">
				<el-input style="width:308px;" value="报告已交接" readonly v-if="form.status == 9"></el-input>
				<el-input style="width:308px;" value="报告未交接" readonly v-else></el-input>
			</el-form-item>
			<el-form-item label="登记时间" style="margin-right:0;" prop="dateregister">
				<el-input style="width:308px;" v-model="form.dateregister" readonly></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="cancel">关闭</el-button>
		</div>
	</el-dialog>
</template>

<script>
import { getReport } from "@/api/sale/receive_report";
export default {
	data() {
		return {
			// 表单参数
			form: {},
			// 是否显示弹出层
			open: false,
			// 弹出层加载动画
			popLoading: false,
		}
	},
	methods: {
		// 查看
		handleView(id) {
			this.open = true
			this.popLoading = true
			getReport(id).then(response => {
				this.form = response.data;
				this.popLoading = false
			});
		},
		// 取消按钮
		cancel() {
			this.open = false;
		},
	}
}
</script>
<style>
.report-view .el-form-item {
	margin-bottom: 16px !important;
}
</style>
