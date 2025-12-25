<!-- 报告审核统计-领取 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="30%" append-to-body>
      <el-form ref="form" :model="form"  :inline="true" label-width="100px" hide-required-asterisk>
        <el-form-item label="体检类型" prop="tjlx">
          <el-select v-model="form.tjlx" placeholder="请选择体检类型" style="width: 280px">
            <el-option  label="健康" :value="1" />
            <el-option  label="职业" :value="2" />
            <el-option  label="综合" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单号:" prop="ddh">
         
        </el-form-item>
        <el-form-item label="单位名称:" prop="dwmc">
          <el-input v-model="form.dwmc" placeholder="请输入单位名称" clearable style="width: 280px" />
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
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
import pinyin from "@/utils/pinyin.js";
export default {
	components: {},
	props: [],
	data() {
		return {
			labTypeData:{},
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
      examList: [],
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
    //新增
    handleadd(){

    },
    //删除
    handleremove(){

    },
    // 编辑
    handleUpdate(row) {
      this.reset();
      // const id = row.id || this.ids
      // getPrinttype(id).then(response => {
      // 	this.form = response.data;
      // 	this.open = true;
      // 	this.title = "编辑";
      // });
      // this.form = {};
      this.open = true;
      this.title = "编辑柜子";
    },
		// 短信通知
    handleAdd() {
			this.reset();
			this.open = true;
			this.title = "添加柜子";
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
						this.$modal.msgSuccess("交接成功");
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
