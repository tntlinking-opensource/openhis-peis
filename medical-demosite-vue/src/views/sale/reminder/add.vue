<!-- 备忘提醒-添加 麦沃德科技 开发人:清风/半夏 -->
<template>
	<el-dialog :title="title" :visible.sync="open" width="468px" append-to-body
		:close-on-click-modal="false">
		<el-form ref="form" :model="form" :rules="rules" label-width="120px" @submit.native.prevent hide-required-asterisk v-loading="popLoading">
			<el-form-item label="客户单位名称" prop="khdwid">
				<el-select v-model="form.khdwid" filterable remote clearable placeholder="请输入关键词" :remote-method="remoteMethod"
					:loading="remoteLoading" style="width:260px;" @change="khdwmcChange">
					<el-option v-for="item in nameOptions" :key="item.id" :label="item.khdwmc" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="提醒时间" prop="remindTime">
				<el-date-picker style="width:260px;" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
					v-model="form.remindTime" placeholder="选择时间"></el-date-picker>
			</el-form-item>
			<el-form-item label="提醒内容" prop="content">
				<el-input type="textarea" style="width:260px;" v-model="form.content" placeholder="请输入"
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
import { getRemind, addRemind, updateRemind, getKhdwmcData } from "@/api/sale/reminder";
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
			// 客户单位名称列表
			nameOptions: [],
			// 编辑详情
			popData: {},
			// 是否显示弹出层
			open: false,
			// 表单校验
			rules: {
				khdwid: [{ required: true, message: "客户单位名称不能为空", trigger: "change" }],
				remindTime: [{ required: true, message: "提醒时间不能为空", trigger: "change" }],
			},
		}
	},
	methods: {
		// 添加
		handleAdd() {
			this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "新增提醒";
		},
		// 编辑
		handleUpdate(ids) {
			this.popData = undefined
			this.reset();
			const id = ids
			this.popLoading = true
			this.open = true;
			this.title = "编辑";
			getRemind(id).then(response => {
				this.popData = JSON.parse(JSON.stringify(response.data))
				this.form = response.data;
				this.popLoading = false
			});
		},
		// 表单重置
		reset() {
			if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				return
			}
			this.form = {
				id: undefined,
				khdwid: undefined,
				khdwmc: undefined,
				remindTime: undefined,
				content: undefined,
			}
			this.resetForm("form");
		},
		//远程选框的方法
		remoteMethod(keyword) {
			const query = {
				key: keyword
			}
			if (keyword !== '') {
				this.remoteLoading = true;
				getKhdwmcData(query).then(res => {
					this.remoteLoading = false;
					this.nameOptions = res.data;
				});
			} else {
				this.nameOptions = [];
			}
		},
		// 选择客户单位
		khdwmcChange(value){
			this.nameOptions.forEach((el) => {
				if(el.id == value) {
					this.form.khdwmc = el.khdwmc
				}
			})
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
						updateRemind(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.$emit("getList")
						});
					} else {
						addRemind(this.form).then(response => {
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