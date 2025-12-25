<!-- 短信模板维护 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" hide-required-asterisk>
				<el-form-item label="短消息类型:" prop="messageType">
					<!-- <el-input v-model="form.messageType" placeholder="请输入短消息类型" clearable style="width: 280px"/> -->
					<input-select :selectData="messageData" selectWidth="280" :initialValue="form.messageType"
						@change="messageChange"></input-select>
				</el-form-item>
        <el-form-item label="短消息名称:" prop="messageName">
          <el-input v-model="form.messageName" placeholder="请输入短消息名称" clearable style="width: 280px" @input="nameChange"/>
        </el-form-item>
        <el-form-item label="输入码:" prop="inputcode">
          <el-input v-model="form.inputcode" placeholder="请输入名称后自动获取输入码" readonly clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="模板ID:" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入模板ID" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="AppID:" prop="appid">
          <el-input v-model="form.appid" placeholder="请输入AppID" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="参数:" prop="params">
          <el-input v-model="form.params" placeholder="请输入参数" clearable style="width: 280px"/>
        </el-form-item>
        <el-form-item label="短消息正文:" prop="messageText">
          <el-input v-model="form.messageText" placeholder="请输入内容" resize="none" rows="3"  type="textarea" clearable style="width: 280px"/>
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
import { addshortmessage, getshortmessage, updateshortmessage } from '@/api/basis/smstemplate'
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
        messageType: [{ required: true, message: "短消息类型不能为空", trigger: "change" }],
        messageName: [{ required: true, message: "短消息名称不能为空", trigger: "change" }],
        inputcode: [{ required: true, message: "输入码不能为空", trigger: "change" }],
        templateId: [{ required: true, message: "模板ID不能为空", trigger: "change" }],
        appid: [{ required: true, message: "AppID不能为空", trigger: "change" }],
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
			//短消息类型
			messageData: {
				placeholder: '请输入输入码选择',
				key: '输入码',//第一列标题 
				value: '短信消息类型',//第二列标题 
				url: '/shortmessage/getMessageTypeData',//请求连接
				bindValue: '',
				firstName: 'inputcode',//接口返回值对应第二列的参数名(不传默认为'name')
				secondName: 'typeName',//接口返回值对应第二列的参数名(不传默认为'name')
				// queryData: "key", 不做短信筛选
			},
		};
	},
	computed: {},
	watch: {},
	created() {
		this.getList();
	},
	mounted() { },
	methods: {
		messageChange(data){
			this.form.messageType = data.id;
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
      getshortmessage(id).then(response => {
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
        messageType: undefined,
        messageName: undefined,
        inputcode: undefined,
        templateId: undefined,
        appId: undefined,
        params: undefined,
        messageText: undefined,
      };
      this.resetForm("form");
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.inputcode = pinyin(value)
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
    // 查询列表
    getList() {
      this.loading = true;
      // listPrinttype(this.queryParams).then(response => {
      // 	this.printtypeList = response.rows;
      // 	this.total = response.total;

      this.loading = false;
      // });
    },
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
					if (this.form.id != null) {
            updateshortmessage(this.form).then(response => {
						this.$modal.msgSuccess("修改成功");
						this.open = false;
						this.$emit("getList");
						});
					} else {
            addshortmessage(this.form).then(response => {
						this.$modal.msgSuccess("添加成功");
						this.open = false;
            this.$emit("getList");
						});
					}
				}
			});
		},
	},
};
</script>
