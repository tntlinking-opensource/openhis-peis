<!-- 外送结果上传 开发人：麦沃德科技暴雨/矢北 -->
<template>
	<!-- 添加或修改对话框 -->
	<el-dialog :title="title" class="add-delivery" :visible.sync="open" :close-on-press-escape="false"
		:close-on-click-modal="false" width="90%" append-to-body>
		<el-form ref="form" :model="queryParams" :inline="true" label-width="80px" hide-required-asterisk>
			<el-form-item label="体检号" prop="patientcode">
				<el-input :readonly="read" v-model="queryParams.patientcode" placeholder="" clearable style="width: 180px;" />
			</el-form-item>
			<el-form-item label="姓名" prop="patientname">
				<el-input :readonly="read" v-model="queryParams.patientname" placeholder="" clearable style="width: 180px;" />
			</el-form-item>
		</el-form>
		<div class="delivery-top">
			<div class="delivery-top-column">
				<!--上方左侧页面-->
				<div class="table-box">
					<!-- 操作按钮 -->
					<!--表格-->
					<el-table border :data="examList" ref="examList" height="100%" stripe>
						<el-table-column type="selection" align="center" />
						<el-table-column label="序列" type="index" width="65" align="center" />
						<el-table-column label="收费项目" align="center" prop="itemName">
						</el-table-column>
						<el-table-column label="科室名称" prop="ksName" align="center" show-overflow-tooltip />
						<el-table-column label="状态" prop="wsjgId" align="center" show-overflow-tooltip>
							<template slot-scope="scope">
								<el-tag v-if="scope.row.dyddybs == 1">处理中</el-tag>
								<el-tag type="danger" v-else>未处理</el-tag>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</div>
			<!--上方右侧页面-->
			<div class="delivery-top-column">
				<!-- 操作按钮 -->
		
				<div class="image-box">
					<el-image class="img" fit="contain" :src="src" :preview-src-list="imgUrl" v-for="(src, index) in imgUrl"
						:key="index"></el-image>
				</div>
			</div>
		</div>
		<!-- 下方页面 -->
		<div class="delivery-bottom">
			<!--表格-->
			<div class="table-box">
				<el-table border :data="examList2" size="mini" height="100%" stripe>
					<el-table-column type="selection" align="center" />
					<el-table-column label="序列" type="index" width="65" align="center" />
					<el-table-column label="收费项目" prop="idFee" align="center">
						<template slot-scope="scope">
							<input-select v-if="scope.row.index !== undefined" :selectData="selectData2" selectSize="mini"
								:queryParams="selectParams" selectWidth="100%" :currentIndex="scope.row.index" v-model="examList2.idFee"
								:initialValue="scope.row.idFee" @change="DataChange2"></input-select>
							<el-input v-model="scope.row.idFee" size="mini" readonly v-else></el-input>
						</template>
					</el-table-column>
					<el-table-column label="检查项目" prop="Check" align="center" show-overflow-tooltip />
					<el-table-column label="结果" prop="resultHand" align="center" show-overflow-tooltip>
						<template slot-scope="scope">
							<el-input v-model="scope.row.resultHand" readonly style="width: 100%;" size="mini"></el-input>
						</template>
					</el-table-column>
					<el-table-column label="提示" prop="ts" align="center" show-overflow-tooltip />
					<el-table-column label="参考低值" prop="ckdz" align="center" show-overflow-tooltip />
					<el-table-column label="参考高值" prop="ckgz" align="center" show-overflow-tooltip />
				</el-table>
			</div>
		</div>
		<div slot="footer" class="dialog-footer">
			<el-button @click="cancel">取 消</el-button>
		</div>
	</el-dialog>
</template>
<script>
import { getPictureData, getEditData, } from "@/api/funcdept/delivery_manage/delivery_upload";
export default {

	props: [],
	data() {
		return {

			// 查询参数
			queryParams: {
				current: 1,
				size: 10,
				patientcode: undefined,

			},
			imgUrl: [],
			read: true,
			//中部分左半表格
			examList: [],
			//下部分表格
			examList2: [],
			// 显示模态框
			showUpload: false,
			//弹窗数据
			diaList: [],
			// 非单选禁用
			single: undefined,
			// 表单参数
			form: {},
			//弹窗中弹窗标题
			diaTitle: undefined,
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			//查询参数
			tableQuery: {
				patientcode: undefined
			},
			tableQueryDown: {
			},
			// 科室的查询
			departQuery: {
				idChargeFee: undefined,
				patientcode: undefined,
				itemId: undefined
			},
			imgUp: [],
			//弹窗表格操作
			diaIds: [],
			//左半部分选中的行的id
			ids: [],
			// 下部分选中行的id
			downIds: [],
			leftLength: undefined,
			downLength: undefined,

		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		openDia(queryParams) {
			this.open = true
			this.queryParams.patientcode = queryParams.patientcode
			this.queryParams.patientname = queryParams.patientname
			this.tableQuery.patientcode = queryParams.patientcode
			this.getPictureData()
			this.getEditData()
		},

		//获取左中部分表格数据
		getPictureData() {
			getPictureData(this.tableQuery).then(response => {
				this.examList = response.data
				this.loading = false;
				this.leftLength = this.examList.length
			}).catch(() => { });
		},
		//获取下部分表格数据
		getEditData() {
			getEditData(this.tableQuery).then(response => {
				this.examList2 = response.data
				for (var i = 0; i < this.examList2.length; i++) {
					this.$set(this.examList2[i], "state", "ssss")
				}
				this.loading = false;
				this.downLength = this.examList2.length
				
			});
		},

		handleAddRow() {
			var list = {
			}
			this.examList.push(list)
			for (var i in this.examList) {
				this.examList[i].index = i
			}
		},
		handleDeleteRow() {

		},
		// 删除
		// handleDelete(row) {
		// 	const ids = row.id || this.ids;
		// 	this.$modal
		// 		.confirm('是否确认删除该数据项？')
		// 		.then(function () {
		// 			// return delPrinttype(ids);
		// 			return;
		// 		})
		// 		.then(() => {
		// 			this.getList();
		// 			this.$modal.msgSuccess("删除成功");
		// 		})
		// 		.catch(() => { });
		// },
		// 表单重置
		reset() {
			this.examList = []
			this.examList2 = []
			this.selectList = []
			this.cidList = []
			this.selectIds = []
			this.total = 0
			this.departData = []
			this.labTypeData = []
			this.resetForm("queryExam")
			this.queryParams = {
				patientcode: undefined,
				date: undefined,
				startTime: undefined,
				endTime: undefined,
				patientname: undefined,
				current: 1,
				size: 10
			},
				this.resetForm("form");

		},
		// 取消按钮
		cancel() {

			this.open = false;
			this.reset();
		},



	}
};
</script>
<style lang="scss">
.add-delivery {
	.el-dialog {
		height: 90%;

		.el-dialog__body {
			display: flex;
			flex-direction: column;
		}
	}

	.delivery-top {
		display: flex;
		height: calc(50% - 1px);
		min-height: 200px;

		.delivery-top-column {
			border: 1px solid #C4C4C4;
			width: 50%;
			display: flex;
			flex-direction: column;
			padding-top: 5px;

			.el-row {
				padding: 0 12px;
			}

			.table-box {
				flex: 1;
				margin: 1px;
				overflow: hidden;
			}

			.image-box {
				display: flex;
				flex-wrap: wrap;
				padding: 0 6px;
				overflow: auto;

				.img {
					width: 100px;
					height: 100px;
					margin: 6px;
				}
			}

			&:last-child {
				margin-left: 10px;
			}
		}
	}

	.delivery-bottom {
		height: calc(50% - 35px);
		min-height: 200px;
		border: 1px solid #C4C4C4;
		margin-top: 10px;
		padding-top: 12px;
		display: flex;
		flex-direction: column;

		.el-row {
			padding: 0 12px;
		}

		.table-box {
			flex: 1;
			margin: 1px;
			overflow: hidden;
		}
	}
}</style>