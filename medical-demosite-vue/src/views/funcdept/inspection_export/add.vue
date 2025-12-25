<!-- 检验结果导出 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="50%" append-to-body>
      <el-form ref="form" :model="form"  :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item label="体检号:" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入" clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="开单医生:" prop="doctorapply">
          <el-input v-model="form.doctorapply" placeholder="请输入" clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="姓名:" prop="patientname">
          <el-input v-model="form.patientname" placeholder="请输入" clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="性别:" prop="idSex">
          <el-input v-model="form.idSex" placeholder="请输入" clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="年龄:" prop="age">
          <el-input v-model="form.age" placeholder="请输入"   clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="公司:" prop="orgName">
          <el-input v-model="form.orgName" placeholder="请输入"   clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="部门:" prop="orgDepart">
          <el-input v-model="form.orgDepart" placeholder="请输入"  clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="电话:" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入"   clearable style="width: 200px"/>
        </el-form-item>
        <el-form-item label="处理结果" prop="sflj">
          <el-select v-model="form.sflj" placeholder="请选择" clearable style="width: 200px;">
            <el-option label="看备注" :value="0" />
            <el-option label="弃检" :value="1" />
            <el-option label="补检" :value="2" />
            <el-option label="已电话通知" :value="3" />
            <el-option label="迟检来检" :value="4" />
            <el-option label="补检来检" :value="5" />
            <el-option label="再通知" :value="6" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约来检时间:" prop="ljsj">
          <el-date-picker v-model="form.ljsj" style="width: 200px;" value-format="yyyy-MM-dd" type="date" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理时间:" prop="visitTime">
          <el-date-picker v-model="form.visitTime" style="width: 200px;" value-format="yyyy-MM-dd" type="date" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预处理结果" prop="preResult">
          <el-select v-model="form.preResult" placeholder="请选择" clearable style="width: 200px;">
            <el-option label="看备注" :value="0" />
            <el-option label="弃检" :value="1" />
            <el-option label="补检" :value="2" />
            <el-option label="已电话通知" :value="3" />
            <el-option label="迟检来检" :value="4" />
            <el-option label="补检来检" :value="5" />
            <el-option label="再通知" :value="6" />
          </el-select>
        </el-form-item>
        <el-form-item label="预处理时间:" prop="preTime">
          <el-date-picker v-model="form.preTime" style="width: 200px;" value-format="yyyy-MM-dd" type="date" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目名称:" prop="idExamfeeitem">
          <el-input v-model="form.idExamfeeitem" placeholder="请输入内容"   clearable style="width: 520px"   type="textarea"  :rows="3"/>
        </el-form-item>
        <el-form-item label="处理备注:" prop="memo">
          <el-input v-model="form.memo" placeholder="请输入内容"   clearable style="width: 520px"   type="textarea"  :rows="3"/>
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
			this.title = "迟补检回访";
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
