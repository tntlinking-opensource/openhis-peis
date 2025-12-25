<!-- 职业结果告知书 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open"  width="30%" append-to-body>
			<el-form ref="form" :model="form"  :inline="true" label-width="120px"  style="text-align:center;" hide-required-asterisk>
          <el-form-item prop="modelUrls">
            <div class="stencil-upload">
              <el-upload class="upload-files" drag :action='process.env.VUE_APP_BASE_API+"/common/upload"' :multiple="false" show-file-list
								accept=".dot,.doc,.docx" :limit="uploadLimit" :on-exceed="uploadExceed" :headers="headers" :on-success="uploadSuccess" ref="uploadFile">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  将文件拖到此处，或<em>点击上传</em>
                </div>
                <div class="upload-tip">
                  最多可上传{{ uploadLimit }}个附件，每个附件大小不能超过3M
                </div>
              </el-upload>
            </div>
          </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">确 定</el-button>
				<el-button @click="cancel">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>
  import {
    getListData,
    create, upload
  } from '@/api/report/print_notice.js'

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
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
      uploadUrls:"",
			// 总条数
			total: 0,
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
      // 文件上传数量限制
      uploadLimit: 10,
      //headers
      headers: { Authorization: this.$store.getters.token },
			// 是否显示弹出层
			open: false,
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
		// 上传
    handleUpload() {
			this.open = true;
			this.title = "文件上传";
		},

    // 文件超出
    uploadExceed() {
      this.$modal.msgError("最多可上传" + this.uploadLimit + "个附件")
    },
    // 上传成功
    uploadSuccess(response) {
      this.form.modelUrls = response
		
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
      this.$refs.uploadFile.clearFiles();
			this.reset();
		},
		//提交按钮
		submitForm() {
			upload(this.form.modelUrls.data).then((res)=>{
				this.$modal.msgSuccess(res.msg)
			})
      this.cancel()
    },
	},
};
</script>
