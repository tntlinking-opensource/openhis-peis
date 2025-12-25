<!-- 报告发放方式 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open"  width="450px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="110px"  style="margin-top: 20px" hide-required-asterisk v-loading="popLoading">
				<el-form-item label="方式名:" prop="methodName">
					<el-input v-model="form.methodName" placeholder="请输入方式名" clearable style="width: 260px" @input="nameChange"/>
				</el-form-item>
        <el-form-item label="拼音码:" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="请输入方式名后自动生成拼音码" readonly clearable style="width: 260px"/>
        </el-form-item>
        <el-form-item label="是否发送通知:" prop="isSendNotice">
          <el-select v-model="form.isSendNotice" placeholder="请选择是否启用" style="width: 260px">
            <el-option :key="1" label="是" value="1" />
            <el-option :key="0" label="否" value="0" />
          </el-select>
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
import { listNotificationmethod, getNotificationmethod, addNotificationmethod, updateNotificationmethod, delNotificationmethod  } from '@/api/basis/issueway'
import { getPeissortexam } from '@/api/basis/scheduling'
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
			form: {},
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
      }
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
      this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "添加";
		},
		// 编辑
		handleUpdate(row,ids) {
      this.open = true;
      this.popData = undefined
      this.reset();
      const id = row.id || ids
      this.title = "编辑";
      this.popLoading = true
      getNotificationmethod(id).then(response => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data;
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
        methodName: undefined,
        inputCode: undefined,
        isSendNotice: undefined,
      };
      this.resetForm("form");
		},
		// 重置
		resetQuery() {
			this.resetForm("queryExam");
			this.handleQuery();
		},
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
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
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
            updateNotificationmethod(this.form).then(response => {
						this.$modal.msgSuccess("修改成功");
						this.open = false;
            this.$emit('getList')
						});
					} else {
            addNotificationmethod(this.form).then(response => {
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
