<!-- 上传材料 开发人：麦沃德科技半夏 -->
<template>
	<el-dialog title="上传材料" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false" :destroy-on-close="true">
		<el-form :inline="true" v-loading="loading" element-loading-text="上传中" label-position="top">
			<el-form-item label="上传文件" style="width: 100%;">
				<file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button type="primary" @click="submitForm()">上 传</el-button>
			<el-button type="primary" plain @click="reset()">重 置</el-button>
			<el-button @click="cancel()">取 消</el-button>
		</div>
	</el-dialog>
</template>

<script>
import { saveClUrl } from '@/api/sale/order_customization.js'
export default {
	data() {
		return {
			id: null,
			// 打开弹窗
			open: false,
			// 上传组件参数
			uploadData: {
				url: '/sell/createorder/uploads',//文件上传地址
				multiple: true,//是否可以上传多个
				data: {},//上传时附带的额外参数
			},
			// 加载
			loading: false,
		}
	},
	methods: {
		// 打开弹窗
		showDialog(id) {
			this.id = id
			this.open = true
			this.loading = false
		},
		// 上传文件成功
		uploadFinish(value, res) {
			if (value == 1) {
				saveClUrl({ id: this.id, urls: res.data.urlList.join() }).then(() => {
					this.$modal.msgSuccess("保存成功", "提醒");
          this.$emit('getList')
					this.open = false
					this.loading = false
				}).catch(() => {
					this.loading = false
				})
			} else {
				this.loading = false
			}
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
		// 重置
		reset() {
			this.$refs.uploadFile.resetUpload()
		},
	}
}
</script>