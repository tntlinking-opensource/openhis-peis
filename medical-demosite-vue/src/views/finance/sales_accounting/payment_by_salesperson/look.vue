<!-- 销售员回款 - 查看 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open"  width="860px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="110px"  style="margin-top: 20px" hide-required-asterisk v-loading="loading">
				<el-form-item label="1月(元):" prop="yy">
					<el-input v-model="form.yy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
				</el-form-item>
        <el-form-item label="2月(元):" prop="ey">
          <el-input v-model="form.ey" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="3月(元):" prop="sy">
          <el-input v-model="form.sy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="4月(元):" prop="iy">
          <el-input v-model="form.iy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="5月(元):" prop="wy">
          <el-input v-model="form.wy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="6月(元):" prop="ly">
          <el-input v-model="form.ly" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="7月(元):" prop="qy">
          <el-input v-model="form.qy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="8月(元):" prop="ay">
          <el-input v-model="form.ay" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="9月(元):" prop="jy">
          <el-input v-model="form.jy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="10月(元):" prop="oy">
          <el-input v-model="form.oy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="11月(元):" prop="ny">
          <el-input v-model="form.ny" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="12月(元):" prop="dy">
          <el-input v-model="form.dy" placeholder="暂无数据" clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="年份:" prop="year">
          <el-input v-model="form.year"  clearable style="width: 260px" :readonly="true"/>
        </el-form-item>
        <el-form-item label="创建时间:" prop="createdate">
          <el-date-picker v-model="form.createdate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="暂无数据" style="width: 260px" :readonly="true"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注:" prop="bz">
          <el-input v-model="form.bz" placeholder="暂无数据" clearable style="width: 640px" :readonly="true"/>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancel">关 闭</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>
// import { getPrinttype, listPrinttype, addPrinttype, updatePrinttype, delPrinttype } from "@/api/basis/charge";
import pinyin from "@/utils/pinyin.js";
import { listNotificationmethod, getNotificationmethod, addNotificationmethod, updateNotificationmethod, delNotificationmethod  } from '@/api/basis/issueway'
import { getPeissortexam } from '@/api/basis/scheduling'
import { fiInput, FinanceInput, saveOrUpdate } from '@/api/finance/sales_accounting/payment_by_salesperson'
export default {
	components: {  },
	props: [],
	data() {
		return {
			// 查询参数
			queryExamination: {
        current: 1,
        size: 10,
				examName: undefined,
				examInputCode: undefined,
			},
      // 表单校验
      rules: {
        methodName: [{ required: true, message: "方式名不能为空", trigger: "change" }],

      },
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
			// 表单参数
			form: {
        yy:undefined,
        ey:undefined,
        sy:undefined,
        iy:undefined,
        wy:undefined,
        ly:undefined,
        qy:undefined,
        ay:undefined,
        jy:undefined,
        oy:undefined,
        ny:undefined,
        dy:undefined,
        year:undefined,
        bz:undefined,
        xsjlid:undefined,
        createdate:undefined,
        id:undefined
      },
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      //表格数据
      examList:{
        conclusionId:"",
        depName:""
      },
      ids:''
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
    // 提交按钮
    submitForm() {
      saveOrUpdate(this.form).then(response => {
        this.open = false;
        this.reset();
        this.$emit("getList")
      });
    },
		// 添加
		handleAdd(ids) {
      this.reset();
      this.ids=ids.toString()
      this.loading=true
      fiInput(this.ids).then(response => {
        this.form=response.data
        this.loading=false
      });
 
			this.open = true;
			this.title = "查看";
		},
		// 表单重置
		reset() {
      this.form= {
          yy:undefined,
          ey:undefined,
          sy:undefined,
          iy:undefined,
          wy:undefined,
          ly:undefined,
          qy:undefined,
          ay:undefined,
          jy:undefined,
          oy:undefined,
          ny:undefined,
          dy:undefined,
          year:undefined,
          bz:undefined,
          xsjlid:undefined,
          createdate:undefined
      }
		},
		// 重置
		resetQuery() {
			this.resetForm("queryExam");
			this.handleQuery();
		},

    // 删除
    handleDelete() {
      this.resetForm("queryExam");
      this.handleQuery();
    },
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},

	},
};
</script>
