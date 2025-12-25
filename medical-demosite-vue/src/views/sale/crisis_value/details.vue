<!-- 危机值管理-处理 麦沃德科技 开发人:清风/半夏 -->
<template>
	<el-dialog :title="title" :visible.sync="open" width="886px" append-to-body :close-on-click-modal="false">
		<el-form ref="form" :model="form" :inline="true"  label-width="110px" hide-required-asterisk v-loading="popLoading">
			<el-form-item label="体检号" prop="tjid">
				<el-input style="width:308px;" v-model="form.tjid" readonly></el-input>
			</el-form-item>
			<el-form-item label="高危人员名称" prop="gwrymc" style="margin-right: 0;">
				<el-input style="width:308px;" v-model="form.gwrymc" readonly></el-input>
			</el-form-item>
			<el-form-item label="年龄" prop="nl">
				<el-input style="width:308px;" v-model="form.nl" readonly></el-input>
			</el-form-item>
			<el-form-item label="性别" prop="xb" style="margin-right: 0;">
				<el-input style="width:308px;" value="男" readonly v-if="form.xb == 0"></el-input>
				<el-input style="width:308px;" value="女" readonly v-else-if="form.xb == 1"></el-input>
				<el-input style="width:308px;" readonly v-else></el-input>
			</el-form-item>
			<el-form-item label="联系电话" prop="lxdh">
				<el-input style="width:308px;" v-model="form.lxdh" readonly></el-input>
			</el-form-item>
			<el-form-item label="体检类型" prop="tjlx" style="margin-right: 0;">
				<el-input style="width:308px;" value="健康" readonly v-if="form.tjlx == 0"></el-input>
				<el-input style="width:308px;" value="职业" readonly v-else-if="form.tjlx == 1"></el-input>
				<el-input style="width:308px;" value="综合" readonly v-else-if="form.tjlx == 2"></el-input>
				<el-input style="width:308px;" value="复查" readonly v-else-if="form.tjlx == 3"></el-input>
				<el-input style="width:308px;" readonly v-else></el-input>
			</el-form-item>
			<el-form-item label="体检日期" prop="tirq">
				<el-input style="width:308px;" v-model="form.tirq" readonly></el-input>
			</el-form-item>
			<el-form-item label="处理状态" prop="tjzt" style="margin-right: 0;">
				<el-input style="width:308px;" value="未处理" readonly v-if="form.tjzt == 0"></el-input>
				<el-input style="width:308px;" value="已处理" readonly v-else-if="form.tjzt == 1"></el-input>
				<el-input style="width:308px;" readonly v-else></el-input>
			</el-form-item>
			<el-form-item label="体检结果" prop="appraiseResult" style="margin-right: 0;">
				<el-input type="textarea" style="width: 736px;" :autosize="autosize" v-model="form.appraiseResult"
					readonly></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="bz" style="margin-right: 0;">
				<el-input type="textarea" style="width: 736px;" :autosize="autosize" v-model="form.bz"
					:readonly="type == 1"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button type="primary" @click="submitForm" v-if="type == 2">保存</el-button>
			<el-button @click="cancel">取消</el-button>
		</div>
	</el-dialog>
</template>

<script>
import { getRisk, handleRisk } from "@/api/sale/crisis_value";
export default {
	data() {
		return {
			// 表单参数
			form: {},
			// id
			id: "",
			// 文本域自适应内容高度参数
			autosize: { minRows: 3, maxRows: 3 },
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			// 类型：查看/处理
			type: 0,
			// 弹出层加载动画
			popLoading: false,
		}
	},
	created() {

	},
	methods: {
		// 查询详情
		getDetails() {
			this.popLoading = true
			const id = this.id;
			getRisk(id).then(response => {
				this.form = response.data;
				this.popLoading = false
			});
		},
		// 查看
		handleView(id) {
			this.title = "查看"
			this.type = 1
			this.id = id
			this.open = true
			this.getDetails()
		},
		// 处理
		handleRisk(id) {
			this.title = "处理"
			this.type = 2
			this.id = id
			this.open = true
			this.getDetails()
		},
		// 取消按钮
		cancel() {
			this.open = false;
		},
		// 提交按钮
		submitForm() {
			if(this.form.bz){
				const id = this.id;
			this.$modal.confirm('确定处理选择的记录？').then(()=> {
				return handleRisk({riskId: id, bz: this.form.bz});
			}).then(() => {
				this.$emit("getList");
				this.open = false;
				this.$modal.msgSuccess("处理成功");
			}).catch(() => { });
			}else{
				this.$modal.msgWarning("请先填写备注");
			}
		
		},
	}
}
</script>