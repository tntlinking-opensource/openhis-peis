<!-- 预约来检回访 开发人：麦沃德科技暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog title="预约来检回访" :visible.sync="open"  width="800px" append-to-body :close-on-click-modal="false" >
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" hide-required-asterisk >
        <el-form-item label="是否来检:" prop="isInspects">
          <el-select v-model="form.isInspects" placeholder="请选择提醒方式" style="width: 230px">
            <el-option label="是" value="0" />
            <el-option label="否" value="1" />
            <el-option label="再通知" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约来检时间:" prop="inspectTimes">
          <el-date-picker v-model="form.inspectTimes" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="暂无数据"clearable :readonly="true" style="width: 230px"></el-date-picker>
        </el-form-item>
        <el-form-item label="回访时间:" prop="visitTime">
          <el-date-picker v-model="form.visitTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择日期"clearable  style="width: 230px"></el-date-picker>
        </el-form-item>
        <el-form-item label="回访备注:" prop="memos">
          <el-input v-model="form.memos" placeholder="请输入内容"   clearable style="width: 590px"   type="textarea"  :rows="8"/>
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
  import {advanceVisitWrite , saveOrUpdate} from "@/api/custservice/customerservice/appoint_return/appoint_return";
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
        // 表单校验
        rules: {},
        sid:0,
        // 遮罩层
        loading: true,
        // 选中数组
        examItems: [],
        selectIds: [],
        // 总条数
        total: 0,
        // 是否显示弹出层
        open: false,
        // 表单参数
        form: {
          patientcode:undefined,
          patientname:undefined,
          idSex:undefined,
          phone:undefined,
          dateregister:undefined,
          count:undefined,
          belowquestion:undefined,
          visitTime:undefined,
          noticeAgain:true,
          memo:undefined,
          id:undefined,
          isInspect:undefined
        },
      };
    },
    computed: {},
    watch: {},
    created() {},
    mounted() { },
    methods: {
      // 提交按钮
      submitForm() {
        let param = {
          id:this.sid[0],
          memo:this.form.memos,
          visitTime:this.form.visitTime,
          isInspect:this.form.isInspects
        }
        saveOrUpdate(param).then(() => {
          this.$modal.msgSuccess("回访成功!");
          this.open = false;
          this.$emit('getList')
        });
      },
      // 添加
      handleAdd(id) {
        this.sid=id
        this.open = true;
        this.loading = true;
        advanceVisitWrite(this.sid).then(response => {
          this.form = response.data;
          this.form.noticeAgain = this.form.noticeAgain=='1'?true:false
          this.form = JSON.parse(JSON.stringify(this.form))
          this.loading = false;
        });
      },
      // 表单重置
      reset() {
        advanceVisitWrite(this.sid).then(response => {
          this.form = response.data;
          this.form.noticeAgain = this.form.noticeAgain=='1'?true:false
          this.form = JSON.parse(JSON.stringify(this.form))
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
    },
  };
</script>
