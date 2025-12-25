<!-- 财务管理-卡充值-消费 麦沃得科技 开发人:清风/矢北 -->
<template>
	<div class="add-container">
		<el-dialog title="消费金额" class="add-items" :visible.sync="open" width="760px" append-to-body :close-on-click-modal="false">
			<el-form ref="form" :inline="true" v-loading="loading" :rules="rules" :model="form" label-width="80px">
				<el-form-item label="体检卡号" prop="cardId">
					<el-input style="width:260px;" v-model="form.cardId" @keyup.enter.native="handleQuery" placeholder="请输入卡号消费"></el-input>
				</el-form-item>
				<el-form-item label="卡类型" prop="cardTypeName" label-width="90px">
					<el-input style="width:260px;" readonly v-model="form.cardTypeName"></el-input>
				</el-form-item>
				<el-form-item label="可用金额" prop="kyje">
					<el-input style="width:260px;" readonly v-model="form.kyje"></el-input>
				</el-form-item>
				<el-form-item label="卡标识" prop="kbs" label-width="90px">
					<el-input style="width:260px;" readonly v-model="form.kbs"></el-input>
				</el-form-item>
				<el-form-item label="有效期" prop="yxq">
					<el-input style="width:260px;" readonly v-model="form.yxq"></el-input>
				</el-form-item>
				<el-form-item label="分中心" prop="branchCenter" label-width="90px">
					<el-select style="width:260px;" v-model="form.branchCenter">
						<el-option v-for="item, index in fzxs" :key="index" :label="item.fzx" :value="item.branchId"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="卡说明" prop="ksm" id="setTextarea">
					<el-input type="textarea" resize="none" readonly style="width:260px;" show-overflow-tooltip v-model="form.ksm"></el-input>
				</el-form-item>
				<el-form-item label="卡备注" prop="kbz" id="setTextarea" label-width="90px">
					<el-input type="textarea" resize="none" readonly style="width:260px;" show-overflow-tooltip v-model="form.kbz"></el-input>
				</el-form-item>
				<el-form-item label="体检号" prop="chargeId">
					<el-input style="width:260px;" v-model="form.chargeId" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="体检者姓名" prop="patientname" label-width="90px">
					<el-input style="width:260px;" v-model="form.patientname" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="结算费" prop="jsf">
					<el-input style="width:260px;" v-model="form.jsf" readonly></el-input>
				</el-form-item>
				<el-form-item label="消费金额" prop="limit" label-width="90px">
					<el-input-number style="width:260px;" controls-position="right" v-model="form.limit"></el-input-number>
				</el-form-item>
				<el-form-item label="消费类型" prop="consumeType">
					<el-select style="width:260px; margin-right: 300px;" controls-position="right" v-model="form.consumeType" placeholder="请选择">
						<el-option v-for="item, index in consumeType" :key="index" :label="item.text" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="备注" prop="memotext">
					<el-input type="textarea" resize="none" style="width:620px;" v-model="form.memotext" placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="saveData">确 定</el-button>
				<el-button type="primary" @click="reset" plain>重 置</el-button>
				<el-button @click="close">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import Cookies from 'js-cookie'
import { fzxData, getDetailData, spendDataSave } from '@/api/finance/card/card_recharge.js'
export default {
	data() {
		return {
			//消费类型数据
			consumeType: [
				{ id: 0, text: '体检' }, { id: 1, text: '药房' }, { id: 2, text: '口腔科' }, { id: 3, text: '眼镜店' }, { id: 4, text: '合作' }, { id: 5, text: '保健品' }
			],
			//分中心
			fzxs: [],
			open: false,
			ids: [],//选中的数组
			single: true,//必选且单选
			multiple: true,//必选
			total: 30,//总数
			queryParams: {
				pageNum: 1,//初始页数
				pageSize: 10,//页值
			},
			form: {},
			loading: false,
			//规则
			rules: {
				cardId: [{ required: true, message: "请输入客户体检号", trigger: "blur" }],
			}
		}
	},
	methods: {
		//入口方法
		consumptionWindow() {
			this.open = true;
			this.reset()
			this.fzxGetData()
		},
		//分中心类型数据
		fzxGetData() {
			//获取体检卡类型数据
			fzxData().then(response => {
				this.fzxs = response.data
				for (let i in this.fzxs) {
					if (this.fzxs[i].id == this.fzx) {
						this.form.branchCenter = this.fzxs[i].fzx
					}
				}
			})
		},
		close() {
			this.open = false
		},
		//获取详情
		handleQuery() {
			this.loading = true
			const query = {
				cardId: this.form.cardId
			}
			getDetailData(query).then(response => {
				this.loading = false
				if (response.data.sign == 1) {
					this.form.cardType = response.data.cardType
					this.form.cardTypeName = response.data.cardTypeName
					this.form.kyje = response.data.kyje
					this.form.kbs = response.data.kbs
					this.form.yxq = response.data.yxq
					this.form.ksm = response.data.ksm
					this.form.kbz = response.data.kbz
					this.form.jsf = response.data.jsf
				} else {
					this.$message.warning('请联系管理员或查看卡号填写是否正确')
				}
			})
		},
		saveData() {
			if (!this.form.cardType) {
				this.$message.warning('请按下回车进行卡号信息填写')
			} else {
				this.$refs["form"].validate(valid => {
					if (valid) {
						spendDataSave(this.form).then(response => {
							this.$modal.msgSuccess("消费成功");
							this.open = false;
							this.$emit("getList")
						});
					}
				});
			}
		},
		reset() {
			this.form = {
				cardId: undefined,
				branchCenter: Cookies.get("cid"),
				chargeId: undefined,
				patientname: undefined,
				limit: 0,
				consumeType: this.consumeType[0].id,
				memotext: undefined,
			}
			this.resetForm("form");
		},
	},
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
	height: 100px;
}
</style>