<!-- 报告存储配置 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open"  width="460px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="90px" hide-required-asterisk>
				<el-form-item label="标示:" prop="flag">
					<el-input v-model="form.flag" placeholder="请输入标示" clearable style="width: 280px"/>
				</el-form-item>
        <el-form-item label="AETITLE:" prop="aetitle">
          <el-input v-model="form.aetitle" placeholder="请输入AETITLE" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="IP:" prop="ip">
          <el-input v-model="form.ip" placeholder="请输入IP" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="端口:" prop="port">
          <el-input v-model="form.port" placeholder="请输入端口" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="实际路径:" prop="realPath">
          <el-input v-model="form.realPath" placeholder="请输入实际路径" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="映射路径:" prop="mappingPath">
          <el-input v-model="form.mappingPath" placeholder="请输入映射路径" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="访问路径:" prop="visitPath">
          <el-input v-model="form.visitPath" placeholder="请输入访问路径" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="备注:" prop="memo">
          <el-input v-model="form.memo" placeholder="请输入备注" type="textarea" clearable style="width: 280px"/>
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
import { addBaseAttachmentConfig, getBaseAttachmentConfig, updateBaseAttachmentConfig } from '@/api/basis/reportstorage'
export default {
	components: {},
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
        aetitle: [{ required: true, message: "aetitle不能为空", trigger: "change" }],
        ip: [{ required: true, message: "ip不能为空", trigger: "change" }],
        port: [{ required: true, message: "端口不能为空", trigger: "change" }],
        realPath: [{ required: true, message: "实际路径不能为空", trigger: "change" }],
        mappingPath: [{ required: true, message: "映射路径不能为空", trigger: "change" }],
        visitPath: [{ required: true, message: "访问路径不能为空", trigger: "change" }],
      },
			// 检查项目表格数据
			examList: [],
			selectList: [],
			cidList: [],
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
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
		this.getList()
	},
	mounted() { },
	methods: {
    // 查询列表
    getList() {
      this.loading = true;
      // listPrinttype(this.queryParams).then(response => {
      // 	this.printtypeList = response.rows;
      // 	this.total = response.total;
      this.loading = false;
      // });
    },

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
      getBaseAttachmentConfig(id).then(response => {
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
        flag: undefined,
        aetitle: undefined,
        ip: undefined,
        port: undefined,
        realPath: undefined,
        mappingPath: undefined,
        visitPath: undefined,
        memo: undefined,
      };
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
            updateBaseAttachmentConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('getList')
            });
          } else {
            addBaseAttachmentConfig(this.form).then(response => {
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
