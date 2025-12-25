<!-- 健康终审-审核不通过 开发人：麦沃德科技暴雨/半夏 -->
<template>
	<div class="add-container">
		<el-dialog :title="title" :visible.sync="open" width="792px" append-to-body>
			<el-form ref="form" :model="form" label-width="100px" :inline="true" hide-required-asterisk>
				<el-form-item label="体检号:" prop="patientcode" style="width: 100%;margin-right: 0;">
					<el-input v-model="form.patientcode" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="姓名:" prop="patientname" style="margin-right: 24px;">
					<el-input v-model="form.patientname" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="性别:" prop="sex" style="margin-right: 0;">
					<el-input v-model="form.sex" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="年龄:" prop="age" style="margin-right: 24px;">
					<el-input v-model="form.age" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="公司:" prop="orgName" style="margin-right: 0;">
					<el-input v-model="form.orgName" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="电话:" prop="phone" style="margin-right: 24px;">
					<el-input v-model="form.phone" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="登记时间:" prop="dateregister" style="margin-right: 0;">
					<el-input v-model="form.dateregister" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="开单医生:" prop="doctorapply" style="margin-right: 24px;">
					<el-input v-model="form.doctorapply" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="任务编号:" prop="numorgresv" style="margin-right: 0;">
					<el-input v-model="form.numorgresv" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="总检时间:" prop="datefinalexamed" style="margin-right: 24px;">
					<el-input v-model="form.datefinalexamed" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="总检大夫:" prop="doctorfinalNameR" style="margin-right: 0;">
					<el-input v-model="form.doctorfinalNameR" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="打印时间:" prop="printTime" style="margin-right: 24px;">
					<el-input v-model="form.printTime" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="打印人:" prop="printMan" style="margin-right: 0;">
					<el-input v-model="form.printMan" readonly style="width: 260px" />
				</el-form-item>
				<el-form-item label="未通过原因:" prop="lastReason" style="margin-right: 0;">
					<el-input v-model="form.lastReason" placeholder="请输入未通过原因" type="textarea" :rows="4" clearable
						style="width: 644px" />
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
import { healthLastUncheck } from "@/api/report/report_review/health_last";
export default {
	components: {},
	props: [],
	data() {
		return {
			// 遮罩层
			loading: true,
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			// 表单参数-初始
			formReport: {},
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		// 审核不通过
		handleUnpass(row) {
			this.reset();
			this.open = true;
			this.title = "报告审核";
			this.form = row
			this.formReport = JSON.parse(JSON.stringify(row))
		},
		// 表单重置
		reset() {
			this.form.lastReason = this.formReport.lastReason
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
		// 提交按钮
		submitForm() {
			healthLastUncheck(this.form.id, this.form.lastReason || "无").then(response => {
				this.$modal.msgSuccess("保存成功");
				this.open = false;
				this.$emit("getList")
			});
		},
	},
};
</script>
