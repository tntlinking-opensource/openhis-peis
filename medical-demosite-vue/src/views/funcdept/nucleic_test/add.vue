<!-- 核酸检测 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="30%" append-to-body>
      <el-form ref="form" :model="form"  :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item label="类别代号:" prop="drugClass">
          <el-input v-model="form.drugClass" placeholder="请输入" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="商品名:" prop="drugName">
          <el-input v-model="form.drugName" placeholder="请输入" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="规格:" prop="drugStandard">
          <el-input v-model="form.drugStandard" placeholder="请输入" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="产地:" prop="drugPlace">
          <el-input v-model="form.drugPlace" placeholder="请输入" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="剂型:" prop="drugType">
          <el-input v-model="form.drugType" placeholder="请输入"   clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="单位:" prop="drugUnit">
          <el-input v-model="form.drugUnit" placeholder="请输入"   clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="零售价:" prop="drugPrice">
          <el-input-number v-model="form.drugPrice" controls-position="right" style="width: 280px"  :min="1" :max="10000" ></el-input-number>
        </el-form-item>
        <el-form-item label="成本价:" prop="costPrice">
          <el-input-number v-model="form.costPrice" controls-position="right" style="width: 280px"  :min="1" :max="1000"></el-input-number>
        </el-form-item>
        <el-form-item label="库存:" prop="stock">
          <el-input-number v-model="form.stock" controls-position="right" style="width: 280px"  :min="1" :max="1000"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
import pinyin from "@/utils/pinyin.js";
export default {
	components: {},
	props: [],
	data() {
		return {
			// 查询参数
			queryExamination: {
				pageNum: 1,
				pageSize: 10,
				examName: undefined,
				examInputCode: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
			},
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,

		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		// 添加
		handleAdd() {
			this.reset();
			this.open = true;
			this.title = "添加";
		},
		// 加急
		handleUpdate(row) {
			this.reset();
			this.open = true;
			this.title = "加急";
		},
		// 表单重置
		reset() {
			this.examList = []
			this.selectList = []
			this.cidList = []
			this.examItems = []
			this.selectIds = []
			this.total = 0
			this.departData = []
			this.labTypeData = []
			this.resetForm("queryExam")
			this.form = {
				examfeeitemName: undefined,
				sfxmsrm: undefined,
				sysmanmark: 1,
				examfeeitemNameprn: undefined,
				bgdybs: undefined,
				dydfz: undefined,
				xh: 0,
				dyddybs: 1,
				idDepart: undefined,
				idCooporg: undefined,
				unitprice: 0,
				zk: 0,
				suiteprice: 0,
				xyBdm: undefined,
				wbjg: 0,
				ydjg: 0,
				nbj: 0,
				materialprice: 0,
				costprice: 0,
				coopprice: 0,
				idLabtype: undefined,
				dlbs: 1,
				fylx: undefined,
				cx: undefined,
				xsdyfl: undefined,
				tjlx: undefined,
				xb: 2,
				bs: undefined,
				examfeeitemCode: undefined,
				jccs: 0,
				fDiscountdisabled: 0,
				examfeeitemId: 0,
				isPublic: undefined,
				fzxIds: undefined,
				examfeeitemCodex: 1,
				preUpperLimit: 0,
				labtypeSub: undefined,
				reviewMatters: undefined,
				bz: undefined,
				guidesheetcode: undefined,
				barcodeName: undefined,
				barcodeCount: 1,
				examfeeitemClass: undefined,
				jcyy: undefined,
				groupOrder: 0,
			},
			this.resetForm("form");
		},
		// 重置
		resetQuery() {
			this.resetForm("queryExam");
			this.handleQuery();
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
						// updatePrinttype(this.form).then(response => {
						this.$modal.msgSuccess("修改成功");
						this.open = false;
						// 	this.getList();
						// });
					} else {
						// addPrinttype(this.form).then(response => {
						this.$modal.msgSuccess("加急成功");
						this.open = false;
						// 	this.getList();
						// });
					}
				}
			});
		},
	},
};
</script>
