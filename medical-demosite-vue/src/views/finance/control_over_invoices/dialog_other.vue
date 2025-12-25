<!-- 发票管理-子页弹窗2 开发人：麦沃德科技清风 -->
<template>
	<div class="add-container">
		<el-dialog :title="title" :visible.sync="open" width="800px" class="view-charge" append-to-body @close="reset" :close-on-click-modal="false">
			<el-form :inline="true" label-width="80px" size="mini" ref="form" :model="form" :rules="rules" v-if="exchange==0">
				<el-form-item label="订单号">
					<el-input style="width:280px;" :readonly="readonly" v-model="form.orderId" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="体检团体">
					<el-input style="width:280px;" :readonly="readonly" v-model="form.idReceipttype" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="发票抬头">
					<el-input style="width:280px;" :readonly="readonly" v-model="form.fptt" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="金额">
					<el-input style="width:280px;" :readonly="readonly" v-model="form.receiptcorenumber" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="申请日期">
					<el-date-picker style="width:280px;" v-model="form.applicationTime" placeholder="请输入" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
				</el-form-item>
				<el-form-item label="发票号" prop="receiptsheetno">
					<el-input style="width:280px;" v-model="form.receiptsheetno" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="备注" id="setTextarea">
					<el-input type="textarea" style="width:650px;" v-model="form.note" placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<el-form :inline="true" label-width="80px" size="mini" ref="form" :model="form" :rules="rulesExchange" v-else-if="exchange == 1">
				<el-form-item label="订单号">
					<el-input style="width:280px;" :readonly="noteRead" v-model="form.orderId" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="体检团体">
					<el-input style="width:280px;" :readonly="noteRead" v-model="form.idReceipttype" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="发票抬头">
					<el-input style="width:280px;" :readonly="noteRead" v-model="form.fptt" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="金额">
					<el-input style="width:280px;" :readonly="noteRead" v-model="form.receiptcorenumber" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="申请日期">
					<el-date-picker style="width:280px;" :readonly="noteRead" v-model="form.applicationTime" placeholder="请输入" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
				</el-form-item>
				<el-form-item label="发票号" prop="receiptsheetno">
					<el-input style="width:280px;" :readonly="noteRead" v-model="form.receiptsheetno" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="换票原因" id="setTextarea" prop="ttyy">
					<el-input type="textarea" style="width:650px;" v-model="form.ttyy" placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<el-form :inline="true" label-width="80px" size="mini" :model="form" v-else-if="exchange == 2 || exchange == 3">
				<el-form-item label="订单号">
					<el-input style="width:280px;" :readonly="auditRead" v-model="form.orderId" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="体检团体">
					<el-input style="width:280px;" :readonly="auditRead" v-model="form.idReceipttype" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="发票抬头">
					<el-input style="width:280px;" :readonly="auditRead" v-model="form.fptt" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="金额">
					<el-input style="width:280px;" :readonly="auditRead" v-model="form.receiptcorenumber" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="申请日期">
					<el-date-picker style="width:280px;" :readonly="auditRead" v-model="form.applicationTime" placeholder="请输入" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
				</el-form-item>
				<el-form-item label="发票号">
					<el-input style="width:280px;" :readonly="auditRead" v-model="form.receiptsheetno" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="换票原因" id="setTextarea">
					<el-input type="textarea" :readonly="auditRead" style="width:650px;" v-model="form.ttyy" placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<el-form :inline="true" label-width="80px" size="mini" ref="form" :model="form" :rules="rulesConfirm" v-else-if="exchange == 4">
				<el-form-item label="订单号">
					<el-input style="width:280px;" :readonly="confirm" v-model="form.orderId" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="体检团体">
					<el-input style="width:280px;" :readonly="confirm" v-model="form.idReceipttype" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="发票抬头">
					<el-input style="width:280px;" :readonly="confirm" v-model="form.fptt" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="金额">
					<el-input style="width:280px;" :readonly="confirm" v-model="form.receiptcorenumber" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="发票号" prop="receiptsheetno">
					<el-input style="width:280px;" v-model="form.receiptsheetno" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="原发票号">
					<el-input style="width:280px;" :readonly="confirm" v-model="form.firstReceiptsheetno" placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="换票原因" id="setTextarea">
					<el-input type="textarea" :readonly="confirm" style="width:650px;" v-model="form.ttyy" placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm" v-if="exchange==0">发票申请</el-button>
				<el-button type="primary" @click="exchangeForm" v-if="exchange==1">换票申请</el-button>
				<el-button type="primary" @click="auditForm" v-if="exchange==2">审核通过</el-button>
				<el-button type="primary" @click="unauditForm" v-if="exchange==3">反审核</el-button>
				<el-button type="primary" @click="confirmForm" v-if="exchange==4">换票</el-button>
				<el-button @click="cancel">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { saveOrUpdatePrint,saveReturnApply,saveReturnAudit,saveReturnUnaudit,saveReturnConfirm } from "@/api/finance/control_over_invoices"

export default {
	data() {
		return {
			title: "",
			open: false,
			form: {
				orderId: "",
				idReceipttype: "",
				fptt: "",
				receiptcorenumber: "",
				note: "",
				unauditNote: "",
			},
			readonly:false,//只读
			noteRead:false,//备注只读
			auditRead:false,//审核只读
			confirm:false,//换票只读
			rules:{
				receiptsheetno:[{required:true, trigger:"blur", message:"请编辑发票号后,进行操作"}]
			},
			exchange:undefined,
			rulesExchange:{
				ttyy:[{required:true, trigger:"blur", message:"请编辑换票原因后,进行操作"}]
			},
			rulesConfirm:{
				receiptsheetno:[{required:true, trigger:"blur", message:"请编辑发票号后,进行操作"}]
			}
		}
	},
	methods: {
		setBasicForm(context,row) {
			this.open = true;
			//0--出票
			if(context == 0){
				this.exchange = context;
				this.readonly = true;
				this.form = {
					id:row.id,
					orderId: row.orderId,
					idReceipttype: row.idReceipttype,//发票类型ID
					fptt: row.fptt,//	发票抬头
					receiptcorenumber: row.receiptcorenumber,//	发票面额
					applicationTime: row.applicationTime,//发表申请时间
					receiptsheetno: row.receiptsheetno || "",//	发票号码
					unauditNote: row.unauditNote,//
					note: row.note,//	备注
					firstReceiptsheetno: row.firstReceiptsheetno || "",//原发票号
					ttjt: row.ttjt,//	体检团体
					ttyy: row.ttyy//	换票原因
				}
			//0--换票申请
			} else if(context == 1) {
				this.exchange = context;
				this.noteRead = true;
				this.form = {
					id:row.id,
					orderId: row.orderId,
					idReceipttype: row.idReceipttype,//发票类型ID
					fptt: row.fptt,//	发票抬头
					receiptcorenumber: row.receiptcorenumber,//	发票面额
					applicationTime: row.applicationTime,//发表申请时间
					receiptsheetno: row.receiptsheetno || "",//	发票号码
					unauditNote: row.unauditNote,//
					note: row.note,//	备注
					firstReceiptsheetno: row.firstReceiptsheetno || "",//原发票号
					ttjt: row.ttjt,//	体检团体
					ttyy: row.ttyy//	换票原因
				}
			} else if(context == 2 || context == 3) {
				this.exchange = context;
				this.auditRead = true;
				this.form = {
					id:row.id,
					orderId: row.orderId,
					idReceipttype: row.idReceipttype,//发票类型ID
					fptt: row.fptt,//	发票抬头
					receiptcorenumber: row.receiptcorenumber,//	发票面额
					applicationTime: row.applicationTime,//发表申请时间
					receiptsheetno: row.receiptsheetno || "",//	发票号码
					unauditNote: row.unauditNote,//
					note: row.note,//	备注
					firstReceiptsheetno: row.firstReceiptsheetno || "",//原发票号
					ttjt: row.ttjt,//	体检团体
					ttyy: row.ttyy//	换票原因
				}
			} else if( context == 4) {
				this.exchange = context;
				this.confirm = true;
				this.form = {
					id:row.id,
					orderId: row.orderId,
					idReceipttype: row.idReceipttype,//发票类型ID
					fptt: row.fptt,//	发票抬头
					receiptcorenumber: row.receiptcorenumber,//	发票面额
					applicationTime: row.applicationTime,//发表申请时间
					receiptsheetno: "",//	发票号码 row.receiptsheetno --> ""
					unauditNote: row.unauditNote,//
					note: row.note,//	备注
					firstReceiptsheetno: row.firstReceiptsheetno || "",//原发票号
					ttjt: row.ttjt,//	体检团体
					ttyy: row.ttyy//	换票原因
				}
			}
		},
		//清除表单验证
		reset(){
			this.resetForm("form");
			if(this.exchange == 2 || this.exchange == 3) return;
			this.$refs.form.clearValidate();
		},
		//出票
		submitForm() {
			this.$refs.form.validate((val)=>{
				if(val) {
					let obj = {
						"applicationTime": this.form.applicationTime,
						"firstReceiptsheetno": this.form.firstReceiptsheetno,
						"fptt": this.form.fptt,
						"id": this.form.id,
						"idReceipttype": this.form.idReceipttype,
						"note": this.form.note,
						"orderId": this.form.orderId,
						"receiptcorenumber": this.form.receiptcorenumber,
						"receiptsheetno": this.form.receiptsheetno,
						"ttjt": this.form.ttjt,
						"ttyy": this.form.ttyy,
					}
					saveOrUpdatePrint(obj).then((res)=>{
						this.$modal.msgSuccess(res.msg);
						this.$emit("getList");
						this.open = false;
					})
				}
			})
		},
		//换票
		exchangeForm(){
			this.$refs.form.validate((val)=>{
				if(val) {
					let obj = {
						"applicationTime": this.form.applicationTime,
						"firstReceiptsheetno": this.form.firstReceiptsheetno,
						"fptt": this.form.fptt,
						"id": this.form.id,
						"idReceipttype": this.form.idReceipttype,
						"note": this.form.note,
						"orderId": this.form.orderId,
						"receiptcorenumber": this.form.receiptcorenumber,
						"receiptsheetno": this.form.receiptsheetno,
						"ttjt": this.form.ttjt,
						"ttyy": this.form.ttyy,
					}
					saveReturnApply(obj).then((res)=>{
						this.$modal.msgSuccess(res.msg);
						this.$emit("getList");
						this.open = false;
					})
				}
			})
		},
		//换票审核
		auditForm(){
			let obj = {
				"applicationTime": this.form.applicationTime,
				"firstReceiptsheetno": this.form.firstReceiptsheetno,
				"fptt": this.form.fptt,
				"id": this.form.id,
				"idReceipttype": this.form.idReceipttype,
				"note": this.form.note,
				"orderId": this.form.orderId,
				"receiptcorenumber": this.form.receiptcorenumber,
				"receiptsheetno": this.form.receiptsheetno,
				"ttjt": this.form.ttjt,
				"ttyy": this.form.ttyy,
			}
			saveReturnAudit(obj).then((res)=>{
				this.$modal.msgSuccess(res.msg);
				this.$emit("getList");
				this.open = false;
			})
		},
		//换票反审核
		unauditForm(){
			let obj = {
				"applicationTime": this.form.applicationTime,
				"firstReceiptsheetno": this.form.firstReceiptsheetno,
				"fptt": this.form.fptt,
				"id": this.form.id,
				"idReceipttype": this.form.idReceipttype,
				"note": this.form.note,
				"orderId": this.form.orderId,
				"receiptcorenumber": this.form.receiptcorenumber,
				"receiptsheetno": this.form.receiptsheetno,
				"ttjt": this.form.ttjt,
				"ttyy": this.form.ttyy,
			}
			saveReturnUnaudit(obj).then((res)=>{
				this.$modal.msgSuccess(res.msg);
			
				this.$emit("getList");
				this.open = false;
			})
		},
		//换票
		confirmForm(){
			this.$refs.form.validate((val)=>{
				if(val){
					let obj = {
						"applicationTime": this.form.applicationTime,
						"firstReceiptsheetno": this.form.firstReceiptsheetno,
						"fptt": this.form.fptt,
						"id": this.form.id,
						"idReceipttype": this.form.idReceipttype,
						"note": this.form.note,
						"orderId": this.form.orderId,
						"receiptcorenumber": this.form.receiptcorenumber,
						"receiptsheetno": this.form.receiptsheetno,
						"ttjt": this.form.ttjt,
						"ttyy": this.form.ttyy,
					}
					saveReturnConfirm(obj).then((res)=>{
						this.$modal.msgSuccess(res.msg);
						this.$emit("getList");
						this.open = false;
					})
				}
			})
		},
		cancel() {
			this.open = false;
		},
	}
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
	height: 100px;
}
</style>