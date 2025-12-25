<!-- 职业总检-查看详情-分科检验 麦沃德科技 开发人:清风 -->
<template>
	<div class="add-container department-divisional">
		<div style="height:500px;">
			<el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"
				@selection-change="handleSelectionChange" height="99%"  :row-class-name="changRed">
				<el-table-column type="selection" align="center"></el-table-column>
				<el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
				<el-table-column prop="examFeeItem" label="收费项目" align="center"></el-table-column>
				<el-table-column prop="examItemNameR" label="检验项目" align="center"></el-table-column>
				<el-table-column prop="lisCode" label="LIS代码" align="center"></el-table-column>
				<el-table-column label="检验结果" align="center">
					<el-table-column prop="examitemvaluesnumber" label="数值" align="center"></el-table-column>
					<el-table-column prop="examitemvaluesshort" label="结果" align="center"></el-table-column>
					<el-table-column prop="examitemvaluesreport" label="报告结果" align="center"></el-table-column>
					<el-table-column prop="status" label="正常" align="center"></el-table-column>
					<el-table-column prop="units" label="单位" align="center"></el-table-column>
				</el-table-column>
				<el-table-column label="参考范围" align="center">
					<el-table-column prop="refrange" label="LIS范围" align="center"></el-table-column>
					<el-table-column prop="reportRange" label="报告范围" align="center"></el-table-column>
					<el-table-column prop="reflow" label="最小值" align="center"></el-table-column>
					<el-table-column prop="refhigh" label="最大值" align="center"></el-table-column>
				</el-table-column>
				<el-table-column prop="inspectName" label="检验师" align="center"></el-table-column>
				<el-table-column prop="auditName" label="审核医生" align="center"></el-table-column>
				<el-table-column prop="examDateTime" label="检验时间" align="center"></el-table-column>
				<el-table-column prop="lisybbh" label="LIS样本编号" width="120px" align="center"></el-table-column>
			</el-table>
		</div>
		<div style="display: flex;">
			<div style="display:inline-block;width: 770px; vertical-align: top;">
				<el-form :inline="true" label-width="120px"
					style="height: 246px; margin: 0 16px 16px 0; border: 1px solid rgb(225, 225, 225);">
					<el-form-item id="setBlock">
						<template slot="label">
							<div style="font-size: 18px;">【科室小结】</div>
						</template>
						<div style="width:730px; height: 160px; background-color: rgb(246, 247, 251); margin-left: 10px;">
							<div style="padding-left:16px;">{{ conclusions }}</div>
						</div>
					</el-form-item>
				</el-form>
			</div>
			<div style="flex: 1; overflow: auto;">
				<el-form :inline="true" label-width="70px"
					style="overflow: auto; height: 246px;width: 780px;margin-bottom: 16px; border: 1px solid rgb(225, 225, 225);">
					<div style="display: inline-block; width: 80%; vertical-align: bottom; overflow: hidden;">
						<el-form-item style="display:block;" label-width="120px">
							<template slot="label">
								<div style="font-size: 18px; ">【基本信息】</div>
							</template>
						</el-form-item>
						<el-form-item label="姓名">
							<el-input placeholder="读取体检号自动获取" readonly v-model="form.patientname" style="width:200px;"></el-input>
						</el-form-item>
						<el-form-item label="性别">
							<!-- <el-input placeholder="读取体检号自动获取" readonly :value="item.text" v-if="form.idSex == item.id" v-for="item in idSex" style="width:200px;"></el-input> -->
							<el-select placeholder="读取体检号自动获取" disabled v-model="form.idSex" style="width:200px;">
								<el-option v-for="item in idSex" :key="item.id" :value="item.id" :label="item.text"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="年龄">
							<el-input placeholder="读取体检号自动获取" readonly v-model="form.age" style="width:200px;"></el-input>
						</el-form-item>
						<el-form-item label="电话">
							<el-input placeholder="读取体检号自动获取" readonly v-model="form.phone" style="width:200px;"></el-input>
						</el-form-item>
						<el-form-item label="体检号">
							<el-input placeholder="读取体检号自动获取" readonly v-model="form.patientcode" style="width:200px;"></el-input>
						</el-form-item>
						<el-form-item label="类型">
							<el-select placeholder="读取体检号自动获取" disabled v-model="form.idExamtype" style="width:200px;">
								<el-option v-for="item in idExamtype" :key="item.id" :value="item.id" :label="item.text"></el-option>
							</el-select>
						</el-form-item>
					</div>
					<div style="display: inline-block; width: 20%; vertical-align: bottom; position: relative; top:12px;left:0;">
						<el-form-item align="center" style="position: relative; top:0;left:0;">
							<el-image style="width: 120px; height: 152px; border-radius: 5px;" :src="picture" fit="cover">
								<template slot="error">
									<div style="background: rgb(246, 247, 251); height: 100%; line-height: 152px;">暂无头像</div>
								</template>
							</el-image>
						</el-form-item>
					</div>
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
import { test, searchFkJy  } from "@/api/inspection/occupational_inspection.js"
import {  getgriddata } from '@/api/inspection/health_inspection.js'

import Cookies from "js-cookie";
export default {
	data() {
		return {
			picture:"",
			loading: false,
			tableData: [
			],
			form: {
				patientname: "",
				idSex: "",
				age: "",
				phone: "",
				patientcode: "",
				idExamtype: "",
			},
			conclusions: "",
			ids: [],
			idSex: [
				{
					id: 0,
					text: "男",
				},
				{
					id: 1,
					text: "女",
				},
				{
					id: 2,
					text: "通用",
				}
			],
			idExamtype: [
				{
					id: "0",
					text: "健康体检",
				},
				{
					id: "1",
					text: "职业体检",
				},
				{
					id: "2",
					text: "综合",
				},
			]
		}
	},
	methods: {
		// 异常行标红
		changRed({ row }) {
			console.log("异常行变红", row);

			// 检查 status 字段
			if (row.status) {
				console.log("Status value:", row.status);
				console.log(1111);
				
				if (row.status.indexOf('↑') != -1 || row.status.indexOf('↓') != -1) {
				console.log("Matched status condition, returning 'row-red'");
				return 'row-red';
				}
			}

			// 检查 examitemvaluesshort 字段
			if (row.examitemvaluesshort) {
				console.log(2222);

				const value = row.examitemvaluesshort.trim(); // 去除空格
				console.log('Trimmed value of examitemvaluesshort:', JSON.stringify(value));

				if (value.indexOf('阳性') !== -1 || value.indexOf('+') !== -1) {
				console.log('Matched examitemvaluesshort condition, returning "row-red"');
				return 'row-red';
				}
			} else {
				console.log(3333);

				console.log("examitemvaluesshort is undefined or null");
			}
			},
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => { return item.id });
		},
		divisionalWindow(patientno) {
			this.loading = true;
			this.patientno = patientno;
			this.getsearchFkJy();
		},
		gettest() {
			var obj = { patientno: this.patientno };
			test(obj).then((res) => {
				if (res.code == 200) {
					this.form = res.data.peispatient;
				} else {
					this.$message({
						message: res.msg,
						type: "warning"
					})
				}
			})
		},
		getsearchFkJy() {
			this.loading = true;
			var setAutoFill = false;
			if (this.patientno.length < 12) {
				setAutoFill = true;
			}
			var obj = {
				autoFill: setAutoFill,
				KsId: 19,//检验科:19/查询科室列表数据/abteilung/division/list
				patientCode: this.patientno
			};
			searchFkJy(obj).then((res) => {
				if(res.code == 500){
          let str = new RegExp('error@')
          let _msg = res.msg.replace(str, '')
					this.$alert(`${_msg}`,"提示",{
						confirmButtonText:"确认",
						type:"warning"
					})
					this.loading = false;
					this.gettest();
					return;
				} else {
					let url = Cookies.get('imgPath');
					this.picture = url + res.data.picture;
					this.form = res.data.peispatient;
					this.conclusions = res.data.sectionResultMain.conclusions;
				}
				this.loading = false;
			}).catch(() => {

			})
			getgriddata({ ...obj, size: 999 }).then((res) => {
				// this.computeCell(res.data.records) 
				this.$nextTick(() => {
					this.tableData = res.data.records
					console.log("this.tableData的值",this.tableData[0]);
					
					this.loading = false
				})
			})
		},
		//获取cookie
		getCookie(cookieName) {
			const strCookie = document.cookie
			const cookieList = strCookie.split(';')
			for (let i = 0; i < cookieList.length; i++) {
					const arr = cookieList[i].split('=')
					if (cookieName === arr[0].trim()) {
							return arr[1]
					}
			}
			return ''
		},
	}
}
</script>

<style scoped>
#setBlock /deep/ .el-form-item__label {
	display: block;
}

#setBlock /deep/ .el-form-item__content {
	display: block;
}</style>

<style lang="scss">
.department-divisional {
  .row-red .cell {
    color: red !important;
  }
}
</style>
