<!-- 咨询与随访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="450px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="queryParams"  :inline="true" label-width="100px" hide-required-asterisk>
        <el-form-item label="加载信息:" prop="patientcode">
          <input-select ref="inputSelect" :selectData="loadData" selectWidth="290" @change="doctorChange"></input-select>
        </el-form-item>
        <el-form-item label="姓名:" prop="consultName" v-if="queryParams.consultName" >
          <el-input v-model="queryParams.consultName" placeholder="查询后生成姓名" clearable :disabled="true" style="width: 290px"/>
        </el-form-item>
        <el-form-item label="电话:" prop="consultPhone">
          <el-input v-model="queryParams.consultPhone" placeholder="查询后生成电话" clearable :disabled="true" style="width: 290px"/>
        </el-form-item>
        <el-form-item label="类型:" prop="consultType">
          <el-select v-model="queryParams.consultType" placeholder="请选择类型" clearable style="width: 290px;">
            <el-option label="现场咨询" :value="1" />
            <el-option label="来电咨询" :value="2" />
            <el-option label="电话回访" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容:" prop="consultContent">
          <el-input v-model="queryParams.consultContent" placeholder="请输入内容"  type="textarea" :rows="3" clearable style="width: 290px"/>
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

  import { consulation, listPrinttype, saveOrUpdate } from '@/api/custservice/consulation/consulation.js'
export default {
	components: {},
	props: [],
	data() {
		return {
			// 查询参数
      queryParams: {
				pageNum: 1,
				pageSize: 10,
        patientcode: undefined,
        consultName: undefined,
        consultType:undefined,
        consultContent:undefined,
        consultPhone:undefined
			},
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
      //row
      row:{},
			// 表单参数
			form: {},
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
      // 表单校验
      // rules: {
      //   patientcode: [{ required: true, message: "加载信息不能为空", trigger: "change" }],
      //   consultName: [{ required: true, message: "姓名不能为空", trigger: "change" }],
      //   consultPhone: [{ required: true, message: "电话不能为空", trigger: "change" }],
      // },
      loadData: {
        placeholder: '请输入姓名、手机号或体检号,回车查询',
        queryData:'key',
        key: '体检号',//第一列标题
        value: '姓名',//第二列标题
        third: '手机号',//第三列标题
        firstName:'patientcode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName:'patientname',//接口返回值对应第二列的参数名(不传默认为'name')
        thirdName:'phone',//接口返回值对应第三列的参数名
        url: '/member/consulation/loadPeople',//请求连接
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
    // 提交按钮
    submitForm() {
      let param = {
        consultContent:this.queryParams.consultContent,
        consultName:this.queryParams.consultName,
        consultPhone:this.queryParams.consultPhone,
        consultType:this.queryParams.consultType,
        id:this.queryParams.id,
        patientcode:this.queryParams.patientcode,
      }
      saveOrUpdate(param).then(() => {
        this.$modal.msgSuccess("修改成功!");
        this.open = false;
        this.resets()
        this.reset()
        this.$emit('getList')
      });
    },
    // 选择类型
    doctorChange(value) {
      this.queryParams.patientcode = value.patientcode
      this.queryParams.consultName = value.patientname
      this.queryParams.consultPhone = value.phone
    },
		// 添加
		handleAdd(ids) {
      consulation(ids).then(response => {
        this.queryParams = response.data;
      });
			this.open = true;
			this.title = "咨询与随访";
		},
    handleAdds(){
      this.open = true;
      this.title = "咨询与随访";
    },

		// 表单重置
		resets() {
      this.$refs.inputSelect.initData('')
		},
    reset() {
        this.queryParams.patientcode=undefined,
        this.queryParams.consultName=undefined,
        this.queryParams.consultType=undefined,
        this.queryParams.consultContent=undefined,
        this.queryParams.consultPhone=undefined
    },
		// 重置
		resetQuery() {
			this.resetForm("queryParams");
			this.handleQuery();
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
      this.resets();
		},

	},
};
</script>
