<!-- 柜子管理-弹窗 开发人：麦沃德科技暴雨/矢北 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="30%" append-to-body>
			<el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="100px" hide-required-asterisk>
				<el-form-item label="体检类型" prop="tjlx">
					<el-select v-model="form.tjlx" placeholder="请选择体检类型" style="width: 280px">
						<el-option label="健康" :value="1" />
						<el-option label="职业" :value="2" />
						<el-option label="综合" :value="3" />
					</el-select>
				</el-form-item>
				<el-form-item label="订单号:" clearable  prop="ddh">
					<input-select ref="select"  :selectData="selectData" style="width: 280px"
						@change="labTypeChange"></input-select>
				</el-form-item>
				<el-form-item label="单位名称:" prop="dwmc">
					<el-input :readonly="disable" v-model="form.dwmc" placeholder="按照订单号查询" clearable style="width: 280px" />
				</el-form-item>
				<el-form-item label="柜子号:" prop="gzh">
					<el-input v-model="form.gzh" placeholder="请输入柜子号" clearable style="width: 280px" />
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
import { upDataList, getListData,getDetail} from "@/api/report/chest_manage";
import pinyin from "@/utils/pinyin.js";
export default {
	components: {},
	props: [],
	data() {
		return {
			// 添加参数
			form: {
				tjlx: undefined,
				ddh: undefined,
				dwmc: undefined,
				gzh: undefined,
				id:undefined
			},
			// 遮罩层
			disable: true,
			loading: true,
			// 科室名称
			selectData: {
				placeholder: '请输入输入码选择',
				key: '订单号',
				value: '单位',
				url: '/report/chest/getDdhData',
				bindValue: '',//初始值(必传)
				firstName: 'ddh',
				secondName: 'khdwmc',
				queryData: 'key',
			},
			// 选中数组
			examItems: [],
			selectIds: [],
			examList: [],
			// 总条数
			total: 0,
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			popData: undefined,
			// 表单效验
			rules: {
				tjlx: [{ required: true, message: "体检类型不能为空", trigger: "change" }],
				gzh: [{ required: true, message: "柜子号不能为空", trigger: "change" }],
			},
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		handleAdd() {
			this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "添加";
		},
		// 编辑
		handleUpdate(ids) {

			this.reset();
			getDetail(ids).then(response => {
				this.form = response.data;
				this.selectData.bindValue=this.form.ddh
			
		
			});
			this.form = {};
			this.open = true;
			this.title = "编辑柜子";

		},

		// 表单重置
		reset() {
			this.total = 0
			this.resetForm("form")
			this.form = {
				tjlx: undefined,
				ddh: undefined,
				dwmc: undefined,
				gzh: undefined,
			},
			this.$nextTick(() => {
          this.$refs.select.initData(this.form.ddh);
          this.selectData.bindValue = this.form.ddh
        })
				this.resetForm("form");
		},
	
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
		labTypeChange(value) {
			this.form.ddh = value.ddh

			this.selectData.bindValue = this.form.ddh;
			this.disable = false
			this.form.dwmc = value.name
			this.disable = true

		},
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
						upDataList(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.$emit('getList')
						});
					} else {
						upDataList(this.form).then(response => {
							this.$modal.msgSuccess("添加成功");
							this.open = false;
							this.$emit('getList')
						});
					}
				}
			});
	
		},
	},
};
</script>