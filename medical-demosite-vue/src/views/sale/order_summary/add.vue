<!-- 订单总结-新增 麦沃德科技 开发人:清风/半夏 -->
<template>
	<el-dialog :title="title" :visible.sync="open" width="428px" append-to-body :close-on-click-modal="false">
		<el-form ref="form" :model="form" :rules="rules" label-width="110px" hide-required-asterisk v-loading="popLoading">
			<el-form-item label="订单名称" prop="ddmc">
				<el-select filterable v-model="nameData" remote clearable placeholder="请输入输入码选择" :remote-method="remoteMethod"
					:loading="remoteLoading" style="width:270px;" @change="nameChange">
					<el-option v-for="(item, index) in nameOptions" :key="index" :label="item.ddmc" :value="index">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="客户单位名称" prop="khdwmc">
				<el-input placeholder="请输入客户单位名称" clearable style="width:270px;" v-model="form.khdwmc"></el-input>
			</el-form-item>
			<el-form-item label="销售经理" prop="xsjl">
				<el-input placeholder="请输入销售经理名称" clearable style="width:270px;" v-model="form.xsjl"></el-input>
			</el-form-item>
			<el-form-item label="总结" prop="zj">
				<el-input type="textarea" style="width:270px;" clearable v-model="form.zj" placeholder="请输入"
					:autosize="autosize"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button type="primary" @click="submitForm">保存</el-button>
			<el-button type="primary" plain @click="reset">重置</el-button>
			<el-button @click="cancel">关闭</el-button>
		</div>
	</el-dialog>
</template>

<script>
import { getSummary, updateSummary, addSummary, getListByKey } from "@/api/sale/order_summary";
import Cookies from 'js-cookie'
export default {
	data() {
		return {
			// 表单参数
			form: {},
			// 文本域自适应内容高度参数
			autosize: { minRows: 4, maxRows: 4 },
			// 弹出层标题
			title: "",
			// 弹出层加载动画
			popLoading: false,
			// 远程搜索加载动画
			remoteLoading: false,
			// 订单名称列表
			nameOptions: [],
			// 订单名称
			nameData: undefined,
			// 编辑详情
			popData: {},
			// 是否显示弹出层
			open: false,
			// 表单校验
			rules: {
				ddmc: [{ required: true, message: "订单名称不能为空", trigger: "change" }],
				khdwmc: [{ required: true, message: "客户单位名称不能为空", trigger: "change" }],
				xsjl: [{ required: true, message: "销售经理不能为空", trigger: "change" }],
				zj: [{ required: true, message: "总结不能为空", trigger: "change" }],
			},
		}
	},
	methods: {
		// 添加
		handleAdd() {
			this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "添加";
		},
		// 编辑
		handleUpdate(ids) {
			this.popData = undefined
			this.reset();
			const id = ids
			this.popLoading = true
			this.open = true;
			this.title = "编辑";
			getSummary(id).then(response => {
				this.popData = JSON.parse(JSON.stringify(response.data))
				this.form = response.data;
				this.nameData = this.form.ddmc
				this.popLoading = false
			});
		},
		// 订单名称
		nameChange(index) {
			if (index !== "") {
				this.form.ddid = this.nameOptions[index].id
				this.form.ddmc = this.nameOptions[index].ddmc
				this.form.khdwmc = this.nameOptions[index].khdwmc
			} else {
				this.form.ddid = undefined
				this.form.ddmc = undefined
				this.form.khdwmc = undefined
			}
		},
		// 表单重置
		reset() {
			if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				this.nameData = this.form.ddmc
				return
			}
			this.form = {
				id: undefined,
				ddid: undefined,
				ddmc: undefined,
				khdwmc: undefined,
				xsjl: decodeURIComponent(Cookies.get("username")),
				zj: undefined,
			}
			this.nameData = undefined
			this.resetForm("form");
		},
		//远程选框的方法
		remoteMethod(keyword) {
			const query = {
				key: keyword
			}
			if (keyword !== '') {
				this.remoteLoading = true;
				getListByKey(query).then(res => {
					this.remoteLoading = false;
					this.nameOptions = res.data;
				});
			} else {
				this.nameOptions = [];
			}
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
						updateSummary(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.$emit("getList")
						});
					} else {
						addSummary(this.form).then(response => {
							this.$modal.msgSuccess("添加成功");
							this.open = false;
							this.$emit("getList")
						});
					}
				}
			});
		},
	}
}

</script>