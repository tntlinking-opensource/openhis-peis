<!-- 样本录入 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="870px" append-to-body>
      <el-form ref="form" :model="form" :inline="true" label-width="110px" hide-required-asterisk v-loading="loading"
        :rules="rules">
        <el-form-item label="体检号" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入体检号后按回车" clearable style="width: 240px"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="姓名" prop="patientname">
          <el-input v-model="form.patientname" readonly style="width: 240px" />
        </el-form-item>
        <el-form-item label="性别" prop="idSex">
          <el-input v-model="form.idSex" readonly style="width: 240px" />
        </el-form-item>
        <el-form-item label="登记时间" prop="sendDate">
          <el-date-picker v-model="form.sendDate" style="width: 240px;" value-format="yyyy-MM-dd" type="date">
          </el-date-picker>
        </el-form-item>
        <div class="itemList">项目列表</div>
        <div style="min-height: 400px;">
          <el-table border :data="examList" stripe v-loading="tableLoading" height="500px"
            @selection-change="handleSelectionChange">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="项目名称" prop="itemName" align="center" show-overflow-tooltip />
            <el-table-column label="样本类型" prop="yblx" align="center" show-overflow-tooltip />
            <el-table-column label="试管颜色" prop="bz" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </el-form>
      <!-- 分页 -->
      <pagination :total="total" :page.sync="current" :limit.sync="size" @pagination="getItemList" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getTjzData, getItemData, saveOrUpdate } from "@/api/funcdept/sample/sample_entry.js";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 表单参数
      form: {},
      examList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单校验  
      rules: {
        patientcode: [{ required: true, message: '请输入体检号', trigger: 'blur' }],
      },
      total: 0,
      current: 1,
      size: 50,
      tableLoading: false,

      ids: [],
    };
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() { },
  methods: {
    // 添加
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增样本";
    },
    // 加急
    handleUpdate(row) {
      this.reset();
      this.open = true;
      this.title = "加急";
    },
    // 输入体检号获得体检信息及项目列表
    handleQuery() {
      this.loading = true;
      getTjzData(this.form.patientcode).then(({ data }) => {
        this.form.patientname = data.patientname;
        if (data.idSex == 0) {
          this.form.idSex = '男'
        } else {
          this.form.idSex = '女'
        }
        this.loading = false;
      }).catch(()=>{
        this.loading = false;
      });
      this.getItemList()
    },
    getItemList() {
      this.current = 1
      let query = {
        patientcode: this.form.patientcode,
        current: this.current,
        size: this.size,
      }
      this.tableLoading = true
      getItemData(query).then(({ data }) => {
        this.examList = data.records;
        this.total = data.total;
        this.tableLoading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
    },
    // 表单重置
    reset() {
      this.form = {
        patientcode: undefined,
        patientname: undefined,
        idSex: undefined,
        sendDate: new Date(new Date(new Date().getTime())),
      }
      this.examList = []
      this.total = 0
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      if(!this.form.patientname){
        this.$modal.msg('请输入体检号查询信息!')
        return
      }
      if (this.ids.length == 0) {
        this.$modal.msg('请选择一条记录!')
        return
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          let query = {
            idSex: `${this.form.idSex == '男' ? 0 : 1}`,
            patientcode: this.form.patientcode,
            patientname: this.form.patientname,
            ids: this.ids,
          }
          saveOrUpdate(query).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.$emit('getList')
          });
        }
      });
    },
  },
};
</script>
<style lang="scss">
.itemList {
  width: 100%;
  padding: 10px 24px;
  font-weight: 600;
  font-size: 14px;
  line-height: 20px;
  color: #0059FF;
  background: #F2F9FF;
}
</style>
