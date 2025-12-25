<!-- 个检危机值回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="700px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form"  :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item label="姓名:" prop="patientname">
          <el-input v-model="form.patientname" placeholder="暂无数据" clearable style="width: 200px" :disabled="true" />
        </el-form-item>
        <el-form-item label="性别:" prop="sex">
          <el-select v-model="form.sex" style="width: 200px" :disabled="true">
            <el-option label="男" :value="0"> </el-option>
            <el-option label="女" :value="1"> </el-option>
            <el-option label="通用" :value="2"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄:" prop="age">
          <el-input v-model="form.age" placeholder="暂无数据" clearable style="width: 200px" :disabled="true"/>
        </el-form-item>
        <el-form-item label="电话:" prop="phone">
          <el-input v-model="form.phone" placeholder="暂无数据" clearable style="width: 200px" :disabled="true"/>
        </el-form-item>
        <el-form-item label="体检号:" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="暂无数据"   clearable style="width: 200px" :disabled="true"/>
        </el-form-item>
        <el-form-item label="体检类型:" prop="idExamtype">
          <el-select v-model="form.idExamtype" style="width: 200px" :disabled="true">
            <el-option label="健康" :value="'0'"> </el-option>
            <el-option label="职业" :value="'1'"> </el-option>
            <el-option label="综合" :value="'2'"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开单医生:" prop="doctorapply">
          <el-input v-model="form.doctorapply" placeholder="暂无数据"  clearable style="width: 200px" :disabled="true"/>
        </el-form-item>
        <el-form-item label="体检时间:" prop="dateregister">
          <el-input v-model="form.dateregister" placeholder="暂无数据"   clearable style="width: 200px" :disabled="true"/>
        </el-form-item>
        <el-form-item label="阳性检查项目:" prop="gwxm">
          <el-input v-model="form.gwxm" placeholder="暂无数据"   clearable style="width: 200px"   type="textarea"  :rows="3" :disabled="true"/>
        </el-form-item>
        <el-form-item label="阳性结果:" prop="wjzxj">
          <el-input v-model="form.wjzxj" placeholder="暂无数据"   clearable style="width: 200px"   type="textarea"  :rows="3" :disabled="true"/>
        </el-form-item>
        <el-form-item label="回访时间:" prop="visitTime">
          <el-date-picker v-model="form.visitTime" style="width: 200px;" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="回访备注:" prop="memo">
          <el-input v-model="form.memo" placeholder="请输入内容"   clearable style="width: 520px"   type="textarea"  :rows="3"/>
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
  import { peispatient , saveOrUpdate } from '@/api/custservice/customerservice/positive_track/positive_track.js'
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
			// 遮罩层
			loading: true,
			// 选中数组
			examItems: [],
			selectIds: [],
			// 总条数
			total: 0,
			// 表单参数
			form: {
        patientname: undefined,
        sex: undefined,
        age: undefined,
        phone: undefined,
        patientcode: undefined,
        idExamtype: undefined,
        doctorapply: undefined,
        dateregister: undefined,
        gwxm: undefined,
        wjzxj: undefined,
        visitTime: undefined,
        memo: undefined,
        id:undefined
      },
			// 弹出层标题
			title: "",
			// 是否显示弹出层
			open: false,
      //选中行id
      xid:0
		};
	},
	computed: {},
	watch: {},
	created() {

	},
	mounted() { },
	methods: {
		// 添加
		handleAdd(ids) {
      this.xid=ids
      peispatient(ids).then(response => {
        this.form = response.data;
        //带着回访时间的，传参数的时候带着id，没有回访的不传
        if(this.form.visitTime != undefined){
          this.form.id=this.xid.toString()
        }
      });
			this.open = true;
			this.title = "个检危机值回访";
		},
    // 提交按钮
    submitForm() {
      if(this.form.visitTime == undefined) {
        this.$modal.msgWarning("回访时间不能为空!");
      }else if(this.form.memo == undefined){
        this.$modal.msgWarning("回访备注不能为空!");
      }else{
        saveOrUpdate(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.$emit('getList')
          this.reset();
        });
      }

    },
		// 表单重置
		reset() {
			  this.form.visitTime = undefined,
        this.form.memo = undefined
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

	},
};
</script>
