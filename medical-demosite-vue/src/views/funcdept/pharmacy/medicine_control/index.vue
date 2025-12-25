<!-- 药品管理 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title"  :visible.sync="open"  width="30%" append-to-body>
      <el-form ref="form"  :rules="rules" :model="form"  :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item   label="类别代号:" prop="drugClass" >
					<input-select :selectData="selectData1"  style="width: 380px"   @change="DataChange1" ></input-select>
        </el-form-item>
        <el-form-item label="商品名:" prop="drugName">
          <el-input :readonly="read" v-model="form.drugName" placeholder="请输入" clearable style="width: 380px"/>
        </el-form-item>
        <el-form-item label="规格:" prop="drugStandard">
          <el-input :readonly="read" v-model="form.drugStandard" placeholder="请输入" clearable style="width: 380px"/>
        </el-form-item>
        <el-form-item label="产地:" prop="drugPlace">
          <el-input :readonly="read" v-model="form.drugPlace" placeholder="请输入" clearable style="width: 380px"/>
        </el-form-item>
        <el-form-item label="剂型:" prop="drugType">
          <el-input :readonly="read" v-model="form.drugType" placeholder="请输入"   clearable style="width: 380px"/>
        </el-form-item>
        <el-form-item label="单位:" prop="drugUnit">
          <el-input :readonly="read" v-model="form.drugUnit" placeholder="请输入"   clearable style="width: 380px"/>
        </el-form-item>
        <el-form-item label="零售价:" prop="retailPrice">
          <el-input-number :disabled="read" v-model="form.retailPrice" controls-position="right" style="width: 380px"  :min="1" :max="10000" ></el-input-number>
        </el-form-item>
        <el-form-item label="成本价:" prop="costPrice">
          <el-input-number :disabled="read" v-model="form.costPrice" controls-position="right" style="width: 380px"  :min="1" :max="1000"></el-input-number>
        </el-form-item>
        <el-form-item label="库存:" prop="stock">
          <el-input-number :disabled="read" v-model="form.stock" controls-position="right" style="width: 380px"  :min="1" :max="1000"></el-input-number>
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
 import {saveListData,  detailListData} from "@/api/funcdept/pharmacy/medicine_control";
import pinyin from "@/utils/pinyin.js";
export default {
	components: {},
	props: [],
	data() {
		return {
			// 查询参数
			queryExamination: {
				pageNum: 1,
				pageSize: 10,
				examName: undefined,
				examInputCode: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
			},
			read:true,
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
			//类名数据
			selectData1: {
				placeholder: '',
				key: '序号',//第一列标题
				value: '类别',//第二列标题
				url: '/drugstore/drug/getClassCheckData',//请求连接
				bindValue: '',
				firstName: 'id',
				secondName: 'text',

			},
			// 表单参数
			form: {


				drugClass:undefined
			},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
			rules:{
				drugClass: [{ required: true, message: "类别代号不能为空", trigger: "change" }],
        drugName: [{ required: true, message: "商品名不能为空", trigger: "change" }],
			}
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() {},
	methods: {
		// 添加
		handleAdd() {

			this.reset();
			this.selectData1.bindValue=null
			this.open = true;
			this.title = "添加";
			this.read=false;
		},
		//查看
		handleView(id)
		{
				this.reset()
	
				this.handleUpdate(id)
				this.read=true;
		},
		// 编辑
		handleUpdate(ids) {
			this.reset();
			this.read=false;
		
			detailListData(ids).then(response => {
				this.form = response.data;
				this.selectData1.bindValue=response.data.drugClass
			});
			this.form = {};
			this.open = true;
			this.title = "编辑柜子";

		},
		// 表单重置
		reset() {
	
			this.selectData1.bindValue=''
			this.resetForm("form")
		
		
		},
		// 搜索框内容变化
		DataChange1(value) {
	
			this.form.drugClass = value.id
	
		},
		// 重置
		resetQuery() {
			this.resetForm("queryExam");
			this.handleQuery();
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
		// 提交按钮
		submitForm() {
			this.$refs["form"].validate(valid => {
				this.form.drugPrice=this.form.retailPrice
				if (valid) {
					if (this.form.id != null) {
				
						saveListData(this.form).then(response => {
							this.$modal.msgSuccess("修改成功");
							this.open = false;
							this.$emit('getList')
						});
					} else {
						saveListData(this.form).then(response => {
							this.$modal.msgSuccess("添加成功");
							this.open = false;
							this.$emit('getList')
						});
					}
				}
			});
		},
	},
};
</script>