<!-- 公共池-客户导入 麦沃德科技开发人:清风/半夏 -->
<template>
	<el-dialog title="客户导入" :visible.sync="open" width="446px" append-to-body :close-on-click-modal="false" :destroy-on-close="true">
		<el-form :inline="true" v-loading="loading" element-loading-text="导入中" label-position="top">
			<el-form-item label="请选择客户模板文件" style="width: 100%;">
				<file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button type="primary" plain @click="submitForm()">导入</el-button>
			<el-button @click="cancel()">取消</el-button>
		</div>
	</el-dialog>
</template>

<script>
export default {
	data() {
		return {
			// 打开弹窗
			open: false,
			// 上传组件参数
      uploadData: {
        url: '/crm/clientcommon/beachSave',//文件上传地址
        multiple: false,//是否可以上传多个
        limit: 1,//文件上传个数限制
        fileType: ['xls', 'xlsx'],//文件类型
        data: {},//上传时附带的额外参数
      },
			// 加载
      loading: false,
		}
	},
	methods: {
		// 打开弹窗
    showDialog() {
      this.open = true
    },
    // 上传文件成功
    uploadFinish(value) {
      if (value == 1) {
        this.$modal.msgSuccess("导入成功", "提醒");
        this.open = false
      }
      this.loading = false
    },
    // 提交
    submitForm() {
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.loading = true
        this.uploadData.data = this.form
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, "提醒");
      }
    },
    // 取消
    cancel() {
      this.open = false
    },
	}
}
</script>