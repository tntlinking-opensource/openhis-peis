<!-- 收银收款方式 开发人：麦沃德科技暴雨 -->
<template>
	<div class="add-container">
		<!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="open"  width="476px" destroy-on-close append-to-body :close-on-click-modal="false">
			<el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="140px" hide-required-asterisk>
				<el-form-item label="收费方式名称:" prop="paywayName">
					<el-input v-model="form.paywayName" placeholder="请输入" clearable style="width: 260px" @input="nameChange"/>
				</el-form-item>
        <el-form-item label="输入码:" prop="inputCode">
          <el-input v-model="form.inputCode" placeholder="请输入方式名后自动生成拼音码" readonly clearable style="width: 260px"/>
        </el-form-item>
        <el-form-item label="上传的公司名:" prop="kingdeeCompany">
          <el-input v-model="form.kingdeeCompany" placeholder="请输入" clearable style="width: 260px"/>
        </el-form-item>
        <el-form-item label="业务员:" prop="kingdeeSaleer">
          <el-input v-model="form.kingdeeSaleer" placeholder="请输入" clearable style="width: 260px"/>
        </el-form-item>
        <el-form-item label="金蝶个检-费用项目:" prop="thingKingdeeNumber">
					<search-select :selectData="selectData1" @change="selectChange1" :initialValue="form.thingKingdeeNumber" :selectWidth="260"></search-select>
        </el-form-item>
        <el-form-item label="金蝶团检-费用项目:" prop="groupKingdeeNumber">
					<search-select :selectData="selectData2" @change="selectChange2" :initialValue="form.groupKingdeeNumber" :selectWidth="260"></search-select>
        </el-form-item>
        <el-form-item label="金蝶个团-记账结算:" prop="posKingdeeNumber">
					<search-select :selectData="selectData3" @change="selectChange3" :initialValue="form.posKingdeeNumber" :selectWidth="260"></search-select>
        </el-form-item>
        <el-form-item label="是否支持疫苗统计:" prop="vaccine">
          <el-select v-model="form.vaccine" placeholder="请选择" style="width: 260px">
            <el-option  label="是" :value="1" />
            <el-option  label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注:" prop="note">
          <el-input v-model="form.note" placeholder="请输入" clearable style="width: 260px" type="textarea"  :rows="3"/>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">确 定</el-button>
				<el-button type="primary" plain @click="reset">重 置</el-button>
				<el-button @click="cancel">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>

import pinyin from "@/utils/pinyin.js";
import { addDictpayway, getDictpayway, updateDictpayway } from '@/api/basis/proceedswaylist'
export default {
	components: {},
	props: [],
	data() {
		return {
			// 查询参数
			queryExamination: {
        current: 1,
        size: 10,
				examName: undefined,
				examInputCode: undefined,
			},
      // 表单校验
      rules: {
        paywayName: [{ required: true, message: "收费方式名称不能为空", trigger: "change" }],
        inputCode: [{ required: true, message: "输入码不能为空", trigger: "change" }],
        kingdeeCompany: [{ required: true, message: "上传的公司名不能为空", trigger: "change" }],
      },
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      //表格数据
      examList:{
        conclusionId:"",
        depName:""
      },
			selectData1:{
				placeholder: '请选择',
				inputTitle: '输入码',// 搜索标题
				inputPlaceholder: '',// 搜索placeholder
				key: '金蝶ID',//第一列标题 
				value: '金蝶用户名',//第二列标题 
				thirdName: '金蝶用户状态',//第三列标题
				url: '/kdPayway/getKingdeeData',//请求连接
				firstName: 'accountNo',//接口返回值对应第一列的参数名
				secondName: 'accountName',//接口返回值对应第二列的参数名
				thirdData: 'useStatusId',//第三列参数名
				isKingDee: 'kingDee',
				params: {},
				bindValue: '',
			},
			selectData2:{
				placeholder: '请选择',
				inputTitle: '输入码',// 搜索标题
				inputPlaceholder: '',// 搜索placeholder
				key: '金蝶ID',//第一列标题 
				value: '金蝶用户名',//第二列标题 
				thirdName: '金蝶用户状态',//第三列标题
				url: '/kdPayway/getKingdeeData',//请求连接
				firstName: 'accountNo',//接口返回值对应第一列的参数名
				secondName: 'accountName',//接口返回值对应第二列的参数名
				thirdData: 'useStatusId',//第三列参数名
				isKingDee: 'kingDee',
				params: {},
				bindValue: '',
			},
			selectData3:{
				placeholder: '请选择',
				inputTitle: '输入码',// 搜索标题
				inputPlaceholder: '',// 搜索placeholder
				key: '金蝶ID',//第一列标题 
				value: '金蝶用户名',//第二列标题 
				thirdName: '金蝶用户状态',//第三列标题
				url: '/kdPayway/getKingdeeData',//请求连接
				firstName: 'accountNo',//接口返回值对应第一列的参数名
				secondName: 'accountName',//接口返回值对应第二列的参数名
				thirdData: 'useStatusId',//第三列参数名
				isKingDee: 'kingDee',
				params: {},
				bindValue: '',
			},
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		selectChange1(value){
			this.form.thingKingdeeNumber = value.accountNo;
		},
		selectChange2(value){
			this.form.groupKingdeeNumber = value.accountNo;
		},
		selectChange3(value){
			this.form.posKingdeeNumber = value.accountNo;
		},
		// 添加
		handleAdd() {
      this.popData = undefined
			this.reset();
			this.open = true;
			this.title = "添加";
		},
		// 编辑
		handleUpdate(row) {
			
			this.reset();
			const id = row.id
      getDictpayway(id).then(response => {
				// this.form.paywayName = response.data.paywayName;
				// this.form.inputCode = response.data.inputCode;
				// this.form.kingdeeCompany = response.data.kingdeeCompany;
				// this.form.kingdeeSaleer = response.data.kingdeeSaleer;
				// this.form.thingKingdeeNumber = response.data.thingKingdeeNumber;
				// this.form.groupKingdeeNumber = response.data.groupKingdeeNumber;
				// this.form.posKingdeeNumber = response.data.posKingdeeNumber;
				// this.form.text = response.data.text;
				// this.form.note = response.data.note;
				// this.open = true;
				// this.title = "编辑";
				this.form=response.data
			});
			this.open=true
		},
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.form = {
        paywayName: undefined,
        inputCode: undefined,
        kingdeeCompany: undefined,
        kingdeeSaleer: undefined,
        thingKingdeeNumber: undefined,
        groupKingdeeNumber: undefined,
        posKingdeeNumber: undefined,
        text: undefined,
        note: undefined,
      };
      this.resetForm("form");
    },
		// 重置
		resetQuery() {
			this.resetForm("queryExam");
			this.handleQuery();
		},
    // 删除
    handleDelete() {
      this.resetForm("queryExam");
      this.handleQuery();
    },
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
    // 输入码自动生成
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				if (valid) {
          this.form.payway_code = this.form.input_code
					if (this.form.id != null) {
            updateDictpayway(this.form).then(response => {
						this.$modal.msgSuccess("修改成功");
						this.open = false;
						this.getList();
						});
					} else {
            addDictpayway(this.form).then(response => {
						this.$modal.msgSuccess("添加成功");
						this.open = false;
						this.getList();
						});
					}
				}
			});
		},
	},
};
</script>
