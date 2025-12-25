<!-- 职业总检-查看详情-外送项目 麦沃德科技 开发人:清风 -->
<template>
	<div style="height:100%; display: flex; flex-direction:row ">
		<div style="width:665px;">
			<div style="display: flex;flex-direction: column; height: 240px; margin-bottom: 16px;">
				<el-table :data="tableData" v-loading="loadingData" :border="true" :stripe="true" style="flex:1;"
					@selection-change="handleSelectionChangeUp" height="100%">
					<el-table-column type="selection" align="center"></el-table-column>
					<el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
					<el-table-column prop="itemName" label="收费项目" align="center"></el-table-column>
					<el-table-column prop="ksName" label="科室名称" align="center"></el-table-column>
					<el-table-column prop="status" label="状态" align="center"></el-table-column>
				</el-table>
			</div>
			<div style="height: 240px; padding: 10px; margin-bottom: 16px; border: 1px solid rgb(225, 225, 225);">
				<el-form :inline="true">
					<el-form-item>
						<el-button type="primary" icon="el-icon-plus" @click="gainHanlde">获取</el-button>
						<el-button type="success" icon="el-icon-edit-outline" @click="copeAndCancel">复制并关闭</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="display: flex;flex-direction: column; height: 252px; margin-bottom: 16px;">
				<el-form :inline="true" style="height:40px;">
					<el-form-item>
						<template solt="label">
							<div style="margin-bottom:12px;font-size: 16px;">项目列表</div>
						</template>
					</el-form-item>
				</el-form>
				<el-table :data="tableList" v-loading="loadingList" :border="true" :stripe="true" style="flex:1;"
					@selection-change="handleSelectionChangeDown" height="100%">
					<el-table-column type="selection" align="center"></el-table-column>
					<el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
					<el-table-column prop="idFee" label="收费项目" align="center"></el-table-column>
					<el-table-column prop="Check" label="检查" align="center"></el-table-column>
					<el-table-column prop="resultHand" label="结果" align="center"></el-table-column>
					<el-table-column prop="ts" label="提示" align="center"></el-table-column>
					<el-table-column prop="ckdz" label="参考低值" align="center"></el-table-column>
					<el-table-column prop="ckgz" label="参考高值" align="center"></el-table-column>
				</el-table>
			</div>
		</div>
		<div style="flex-shrink: 0; width:155px; height:764px; margin: 0 16px; border: 1px solid rgb(225, 225, 225);"></div>
		<div style="flex-grow: 1; width:700px; height:764px; border: 1px solid rgb(225, 225, 225);"></div>
	</div>
</template>

<script>
import { getEditData, getPictureData } from "@/api/inspection/occupational_inspection.js"
export default {
	data() {
		return {
			//loading
			loadingData: false,
			loadingList: false,
			// 选中数组
			ids: [],
			// 非单个禁用
			single: true,
			// 非多个禁用
			multiple: true,
			// 显示搜索条件
			showSearch: true,
			open: false,
			total: 30,
			queryParams: {
				pageNum: 1,
				pageSize: 30,
			},
			model: {
				patientcode: "",
				patientname: "",
				searchcode: "",
				valueDate: "",//startTime endTime
			},
			tableData: [],
			tableList: [],
			activeName: "first",
			title: "科室结果",
		}
	},
	methods: {
		//表格选中
		handleSelectionChangeUp() {

		},
		handleSelectionChangeDown() {

		},
		getOgData(patientno) {
			this.patientno = patientno;
			this.obtainEditData();
			this.obtainPictureData();
		},
		//获取已保存项目
		obtainEditData() {
			this.loadingList = true;
			var obj = { patientcode: this.patientno }
			getEditData(obj).then((res) => {
				if (res.code == 200) {
					this.tableList = res.data;
				}
			}).finally(() => {
				this.loadingList = false;
			})
		},
		//获取与图片结果关联项目
		obtainPictureData() {
			this.loadingData = true;
			var obj = { patientcode: this.patientno }
			getPictureData(obj).then((res) => {
				if (res.code == 200) {
					this.tableData = res.data;
				}
			}).finally(() => {
				this.loadingData = false;
			})
		},
		//获取接口
		gainHanlde(){
			this.$alert("外送厂家接口,暂时不对接!!!","提醒",{
				confirmButtonText:"确认",
				type:"warning",
			})
			return
			let rows = this.headSelection;
			if (rows.length == 0) {
				this.$alert('请选择要获取的项目！',"提醒",{
					confirmButtonText:"确认",
					type:"warning"
				});
				return;
			}
			let itemIds = [];
			for (let i = 0; i < rows.length; i++) {
				let row = rows[i];
				itemIds.push(row.idCharge);
			}
			let patientcode = this.patientno;
			//接口调用
		},
		//复制并关闭
		copeAndCancel(){
			
		}
	}
}
</script>