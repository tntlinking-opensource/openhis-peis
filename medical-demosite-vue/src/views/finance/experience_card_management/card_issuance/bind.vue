<!-- 绑定领取人页面  开发人：麦沃德科技矢北 -->
<template>
	<div class="add-container">
		<el-dialog :title="title" :visible.sync="open" class="add-charge" width="446px" append-to-body>
			<el-form ref="form" label-position="top" :model="form" label-width="110px">
				<el-form-item :label="`绑定用户:(已选择${num}张 )`" prop="inputCode">
					<input-select :selectData="departData" :selectWidth="396" @change="departChange"></input-select>
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
import { reviseGetter } from '@/api/finance/card/experience_card_management.js'
export default {
	data() {
		return {
			ids: [],
			//选择卡数
			num: 0,
			open: false,
			title: '绑定体检卡',
			form: {},
			// 领取人信息
			departData: {
				placeholder: '请输入输入码选择',
				key: '部门',//第一列标题 
				value: '领取人',//第二列标题 
				url: '/finance/sendCard/getAutoCompleteData',//请求连接
				bindValue: '',
				queryData: 'srm',
				secondName: 'occupationSummary'
			},
		}
	},
	methods: {
		handleAdd(ids) {
			this.reset();
			this.ids = ids
			this.open = true;
			this.num=ids.length
		},
		// 选择科室
		departChange(value) {
			this.form.inputCode = value.id
			this.departData.bindValue = value.occupationSummary
		},
		// 表单重置
		reset() {
			this.examList = []
			this.selectList = []
			this.cidList = []
			this.examItems = []
			this.selectIds = []
			this.total = 0
			this.labTypeData = []
			this.resetForm("queryExam")
			this.form = {
				examfeeitemName: undefined,
				srm: undefined,
				agerange: 1,
				occupationtype: 1,
			}
		},
		submitForm() {

			const query = {
				ids: this.ids,
				id:this.form.inputCode
			}
			query.ids=this.ids.join(',')
				reviseGetter(query).then(response => {
			
					this.$modal.msgSuccess("修改成功");
					this.open = false;
				});
				this.$emit('getList')


			},
				cancel() {
					this.open = false;
					this.reset();
				}
		}
	}
</script>

<style></style>