<!-- 预约短信回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="450px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form"  :inline="true" label-width="120px" hide-required-asterisk>
        <el-form-item label="模板名称" prop="templateId">
          <input-select ref="inputSelect" :selectData="selectData" @change="selectChange" style="width: 280px;"></input-select>
        </el-form-item>
          <el-form-item label="模板内容:" prop="messageText">
            <el-input v-model="form.messageText" placeholder="请输入内容"   clearable style="width: 280px"   type="textarea"  :rows="8"/>
          </el-form-item>
          <el-form-item label="立即发出:" prop="immediately">
            <el-checkbox v-model="form.immediately" @change="cleartime"></el-checkbox>
          </el-form-item>
        <el-form-item label="发出时间:" prop="sendTime" v-if="form.immediately==false">
          <el-date-picker v-model="form.sendTime" style="width: 280px;" placeholder="请选择时间"
                          type="datetime" value-format="yyyy-MM-dd HH:mm:ss" >
          </el-date-picker>
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
  import { saveGroupData } from '@/api/custservice/customerservice/record_manage/record_manage.js'
import pinyin from "@/utils/pinyin.js";
export default {
	components: {},
	props: [],
	data() {
		return {
			// 遮罩层
			loading: true,
			// 表单参数
			form: {
        idTemplate:undefined,
        immediately:true,
        messageText:undefined,
        id:undefined,
        patientcode:undefined,
        sendTime:undefined
      },
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
      // 输入选择
      selectData: {
        placeholder: '请输入输入码',
        key: '输入码',//第一列标题
        value: '模板名称',//第二列标题
        url: '/member/smsFollowUp/getComboData',//请求连接
        bindValue: '', //初始值(必传)
        firstName: 'inputcode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'messageName',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',//向接口传递的参数名(不传默认为'inputCode')
      },
		};
	},
	computed: {},
	created() {},
	mounted() {},
	methods: {
    // 提交按钮
    submitForm() {
      saveGroupData(this.form).then(response => {
        this.$modal.msgSuccess("保存成功");
        this.open = false;
      });
    },
		// 添加
		handleAdd(patientcode) {
      this.form.patientcode=patientcode
			this.open = true;
			this.title = "群发短信编辑";
		},
    //组件查询赋值
    selectChange(value){
      this.form.templateId = value.templateId
      this.form.idTemplate = value.id
      this.form.messageText = value.messageText
    },
    // 表单重置
		reset() {
			this.form = {
				examfeeitemName: undefined,
				sfxmsrm: undefined,
			},
      this.$refs.inputSelect.initData('')
		},
		// 取消按钮
		cancel() {
			this.open = false;
			this.reset();
		},
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 清除时间
    cleartime(){
      this.form.sendTime=undefined
    }
	},
};
</script>
