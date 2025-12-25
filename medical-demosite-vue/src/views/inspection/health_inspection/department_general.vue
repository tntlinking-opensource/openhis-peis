<!-- 健康总检-查看详情-分科普通 麦沃德科技 开发人:清风 -->
<template>
	<div class="add-container" style="display:flex;flex-direction:row;">
		<div class="table-box" style="flex-grow: 1; margin-right: 16px;">
			<el-table :data="tableData" @row-click="rowClickHandle" v-loading="loading" :border="true" :stripe="true"
				@selection-change="handleSelectionChange" height="99%">
				<el-table-column type="selection" align="center"></el-table-column>
				<el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
				<el-table-column prop="co1" label="科室名称" align="center"></el-table-column>
				<el-table-column prop="co2" label="收费项目" align="center"></el-table-column>
				<el-table-column prop="co3" label="检查项目" align="center"></el-table-column>
				<el-table-column prop="co4" label="体征词" align="center"></el-table-column>
				<el-table-column prop="co5" label="结论词" align="center"></el-table-column>
				<el-table-column prop="co6" label="是否阳性" align="center">
					<template slot-scope="scope">
						<div v-for="item in isCo6">
							<div v-if="scope.row.co6 == item.id">{{ item.text }}</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column prop="co7" label="重症级" align="center">
					<template slot-scope="scope">
						<div v-if="scope.row.co7 == 0">无</div>
						<div v-else-if="scope.row.co7 >= 1 && scope.row.co7 <= 3">低</div>
						<div v-else-if="scope.row.co7 >= 4 && scope.row.co7 <= 6">低</div>
						<div v-else-if="scope.row.co7 >= 7 && scope.row.co7 <= 9">低</div>
						<div v-else></div>
					</template>
				</el-table-column>
				<el-table-column prop="co8" label="是否弃检" align="center">
					<template slot-scope="scope">
						<div v-for="item in isCo8">
							<div v-if="scope.row.co8 == item.id">{{ item.text }}</div>
							<div v-else-if="scope.row.co8 == item.id">{{ item.text }}</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column prop="co9" label="更新时间" align="center"></el-table-column>
			</el-table>
		</div>
		<div style="width:500px; height: 100%;">
			<el-form :inline="true" label-width="100px"
				style="width:500px; margin-bottom: 16px; border: 1px solid rgb(225, 225, 225);">
				<el-form-item>
					<template slot="label">
						<div style="font-size: 18px; ">科室小结</div>
					</template>
					<div style="width:480px; height: 520px; background-color: rgb(246, 247, 251); margin-left: 10px;">
						<div style="padding-left:16px;">{{ CommonSummary }}</div>
					</div>
				</el-form-item>
			</el-form>

			<div style="width: 500px; margin-bottom: 10px; border: 1px solid rgb(225, 225, 225);">
				<el-form :inline="true" label-width="80px" style="margin:16px;">
					<el-form-item label="录入人">
						<el-input placeholder="请输入" style="width:360px;" v-model="form.c1"></el-input>
					</el-form-item>
					<el-form-item label="录入时间">
						<el-input placeholder="请输入" style="width:360px;" v-model="form.c3"></el-input>
					</el-form-item>
					<el-form-item label="审核人">
						<el-input placeholder="请输入" style="width:360px;" v-model="form.c2"></el-input>
					</el-form-item>
					<el-form-item label="审核时间">
						<el-input placeholder="请输入" style="width:360px;" v-model="form.c4"></el-input>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
import { getcommon, getCommonData, getInspectChargeListData } from "@/api/inspection/health_inspection.js"

export default {
	data() {
		return {
			loading: false,
			tableData: [
				{
					co10: "",//科室Id
					co1: "",//科室名称
					co2: "",//收费项目
					co3: "",//检查项目
					co4: "",//体征词
					co5: "",//结论词
					co6: "",//是否阳性
					co7: "",//重症级
					co8: "",//是否弃检
					co9: "",//更新时间
				}
			],
			CommonSummary: "科室小结",//科室小结
			form: {
				c1: "",//录入人
				c2: "",//审核人
				c3: "",//录入时间
				c4: "",//审核时间
			},
			ids: [],
			selection: undefined,
			patientno: "",
			isCo6:[{
				id:"0",
				text: "否"
			},{
				id:"1",
				text: "是"
			}],
			isCo8:[{
				id:"0",
				text:"否"
			},{
				id:"1",
				text:"是"
			}]
		}
	},
	mounted() {

	},
	methods: {
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => { return item.id });
			if (selection.length == 1) {
				this.selection = selection;
			}
		},
		generalWindow(patientno) {
			this.loading = true;
			this.patientno = patientno;
			let obj = { patientno };
			getcommon(obj).then((res) => {
				if (res.code == 200) {
					getCommonData(obj).then((response) => {
						this.tableData = response.data;
						this.loading = false;
					})
				}
			})
			this.form.c1 = "";
			this.form.c2 = "";
			this.form.c3 = "";
			this.form.c4 = "";
			this.CommonSummary = "";
		},
		rowClickHandle(row) {
			var obj = {
				patientno: this.patientno,
				sectionId: row.co10
			}
			getInspectChargeListData(obj).then((res) => {
				if (res.code == 200) {
					//返回的ver2，ver4，ver5，ver6，ver7,就是c1，c2，c3，c4，CommonSummary
					this.form.c1 = res.data[0].ver2;
					this.form.c2 = res.data[0].ver4;
					this.form.c3 = res.data[0].ver5;
					this.form.c4 = res.data[0].ver6;
					this.CommonSummary = res.data[0].ver7;
				}
			})
		}
	}
}
</script>
