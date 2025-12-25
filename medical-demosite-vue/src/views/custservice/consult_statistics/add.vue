<!-- 咨询与随访统计 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="450px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="100px" hide-required-asterisk>
        <el-form-item label="加载信息:" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入加载信息" clearable style="width: 300px"/>
        </el-form-item>
        <el-form-item label="姓名:" prop="consultName">
          <el-input v-model="form.consultName" placeholder="请输入姓名" clearable style="width: 300px"/>
        </el-form-item>
        <el-form-item label="电话:" prop="consultPhone">
          <el-input v-model="form.consultPhone" placeholder="请输入电话" clearable style="width: 300px"/>
        </el-form-item>
        <el-form-item label="类型:" prop="consultType">
          <el-input v-model="form.consultType" placeholder="请输入类型" clearable style="width: 300px"/>
        </el-form-item>
        <el-form-item label="内容:" prop="consultContent">
          <el-input v-model="form.consultContent" placeholder="请输入内容"  type="textarea" clearable style="width: 300px"/>
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
			},
      // 表单校验
      rules: {
        patientcode: [{ required: true, message: "加载信息不能为空", trigger: "change" }],
        consultName: [{ required: true, message: "姓名不能为空", trigger: "change" }],
        consultPhone: [{ required: true, message: "电话不能为空", trigger: "change" }],
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
			this.title = "咨询与随访";
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
