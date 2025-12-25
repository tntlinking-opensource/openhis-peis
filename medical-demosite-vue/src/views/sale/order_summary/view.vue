<!-- 订单总结-查看 麦沃德科技 开发人:清风/半夏 -->
<template>
	<el-dialog title="查看" :visible.sync="open" width="428px" append-to-body>
		<el-form ref="form" :model="form" label-width="110px" hide-required-asterisk v-loading="popLoading">
			<el-form-item label="订单名称" prop="ddmc">
				<el-input readonly style="width:270px;" :value="form.ddmc"></el-input>
			</el-form-item>
			<el-form-item label="客户单位名称" prop="khdwmc">
				<el-input readonly style="width:270px;" :value="form.khdwmc"></el-input>
			</el-form-item>
			<el-form-item label="销售经理" prop="xsjl">
				<el-input readonly style="width:270px;" :value="form.xsjl"></el-input>
			</el-form-item>
			<el-form-item label="总结" prop="zj">
				<el-input type="textarea" readonly style="width:270px;" :value="form.zj" :autosize="autosize"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="cancel">关闭</el-button>
		</div>
	</el-dialog>
</template>

<script>
import { getSummary } from "@/api/sale/order_summary";
export default {
	data() {
		return {
			// 表单参数
			form: {},
			// 文本域自适应内容高度参数
			autosize: { minRows: 4, maxRows: 4 },
			// 弹出层加载动画
			popLoading: false,
			// 是否显示弹出层
			open: false,
		}
	},
	methods: {
		// 查看
		handleView(ids) {
			this.reset();
			const id = ids
			this.popLoading = true
			this.open = true;
			getSummary(id).then(response => {
				this.form = response.data;
				this.popLoading = false
			});
		},
		// 表单重置
		reset() {
			this.form = {
				id: undefined,
				ddmc: undefined,
				khdwmc: undefined,
				xsjl: undefined,
				zj: undefined,
			}
			this.resetForm("form");
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
	}
}

</script>