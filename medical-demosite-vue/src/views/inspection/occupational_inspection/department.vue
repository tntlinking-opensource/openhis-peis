<!-- 职业总检-科室小结 麦沃德科技 开发人:清风 -->
<template>
	<el-dialog :title="title" class="department-item" :visible.sync="open" width="95%" append-to-body>
		<el-tabs v-model="activeName" id="setTabs" @tab-click="handleClick">
			<el-tab-pane label="科室小结" name="1">
				<summary-items ref="summaryItems" />
			</el-tab-pane>
			<el-tab-pane label="分科-普通" name="2">
				<general-items ref="generalItems" />
			</el-tab-pane>
			<el-tab-pane label="分科-检验" name="3">
				<divisional-items ref="divisionalItems" />
			</el-tab-pane>
			<el-tab-pane label="分科-肺功能" name="4">
				<pulmonary-items ref="pulmonaryItems" />
			</el-tab-pane>
			<el-tab-pane label="职业问诊" name="5">
				<occupational-items ref="occupationalItems" />
			</el-tab-pane>
			<el-tab-pane label="外送项目" name="6">
				<outgoing-items ref="outgoingItems" />
			</el-tab-pane>
			<el-tab-pane label="科室图片" name="7">
				<picture-items ref="pictureItems" />
			</el-tab-pane>
		</el-tabs>
	</el-dialog>
</template>

<script>
import summaryItems from "./department_summary.vue"//科室小结
import generalItems from "./department_general.vue"//分科-普通
import divisionalItems from "./department_divisional.vue"//分科-检验
import outgoingItems from "./department_outgoing.vue"//外送项目
import pulmonaryItems from "./department-pulmonary.vue"//分科肺功能
import occupationalItems from "./department-occupational.vue"//分科职业问诊
import pictureItems from "./department_picture.vue"//分科图片

export default {
	components: {
		summaryItems,
		generalItems,
		divisionalItems,
		outgoingItems,
		pulmonaryItems,
		occupationalItems,
		pictureItems
	},
	data() {
		return {
			open: false,
			activeName: "first",
			title: "科室结果",
		}
	},
	methods: {
		departmentResultsWindow(patientno) {
			this.open = true;
			this.patientno = patientno;
			this.activeName = '1';
			this.$nextTick(() => {
				this.$refs.summaryItems.getVerdict(this.patientno);
			})
		},
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => { return item.id });
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
		handleClick(tab, event) {
			if (tab.name == '1') {
				this.activeName == tab.name;
				this.title = '科室小结';
				this.$refs.summaryItems.getVerdict(this.patientno);
			} else if (tab.name == '2') {
				this.activeName == tab.name;
				this.title = '分科-普通';
				this.$refs.generalItems.generalWindow(this.patientno);
			} else if (tab.name == '3') {
				this.activeName == tab.name;
				this.title = '分科-检验';
				this.$refs.divisionalItems.divisionalWindow(this.patientno);
			} else if (tab.name == '4') {
				this.activeName == tab.name;
				this.title = '分科-肺功能';
				this.$refs.pulmonaryItems.getPulmonaryWindow(this.patientno);
			} else if (tab.name == '5') {
				this.activeName == tab.name;
				this.title = '职业问诊';
				this.$refs.occupationalItems.getOccupational(this.patientno);
			} else if (tab.name == '6') {
				this.activeName == tab.name;
				this.title = '外送项目';
				this.$refs.outgoingItems.getOgData(this.patientno);
			} else if (tab.name == '7') {
				this.activeName == tab.name;
				this.title = '科室图片';
				this.$refs.pictureItems.getpicture(this.patientno);
			}
		},
	}
}
</script>

<style scoped>
#setTabs /deep/ .el-tabs__header {
	margin-bottom: 0;
}

#setTabs /deep/ .el-tab-pane {
	height: 100%;
}

#setTabs /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
	box-shadow: none;
}
</style>
<style>
.department-item .el-dialog {
	min-width: 1280px;
	max-width: 1600px;
}
</style>