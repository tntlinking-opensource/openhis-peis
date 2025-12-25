<!-- 职业报告领取通知-短信通知 开发人：麦沃德科技暴雨、予安 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="790px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :inline="true" label-width="100px" hide-required-asterisk
        :destroy-on-close="true" :rules="rules">
        <el-form-item label="短消息名称" prop="messageName">
          <input-select ref="smsType" :selectData="selectData" :queryParams="{ messageType: '1' }" @change="selectChange"></input-select>
        </el-form-item>
        <el-form-item label="短消息类型" prop="messageType">
          <el-select v-model="form.messageType" placeholder="请选择短消息类型" style="width: 600px" clearable disabled>
            <el-option v-for="item in typeOptions" :key="item.id" :label="item.typeName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="短消息正文" prop="visitNote">
          <el-input v-model="form.visitNote" readonly style="width: 600px" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="备注" prop="notifyMemo">
          <el-input v-model="form.notifyMemo" placeholder="请输入备注" clearable style="width: 600px" />
        </el-form-item>
        <el-form-item label="立即发送" prop="immediately">
          <el-checkbox v-model="form.immediately" style="width: 600px" border></el-checkbox>
        </el-form-item>
        <el-form-item label="选择时间" prop="sendTime">
          <el-date-picker v-model="form.sendTime" type="datetime" placeholder="选择日期时间" style="width: 600px"
            :disabled="form.immediately" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
      </el-form>

      <div style="min-height: 300px;">
        <el-form :inline="true" label-width="" hide-required-asterisk>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="patientcode" placeholder="请输入体检号" clearable style="width: 150px" />
          </el-form-item>
          <el-form-item>
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
                  v-hasPermi="['report:reportReceive:healthNotify:add']" :disabled="!patientcode">添加
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleremove"
                  v-hasPermi="['report:reportReceive:healthNotify:remove']" :disabled="multiple">删除
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>

          <el-table border :data="examList" stripe @selection-change="handleSelectionChange" v-loading="loading">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip />
            <el-table-column label="体检者姓名" prop="patientname" align="center" show-overflow-tooltip />
            <el-table-column label="开单医生" prop="doctorapply" align="center" show-overflow-tooltip />
            <el-table-column label="公司名称" prop="orgName" align="center" show-overflow-tooltip />
            <el-table-column label="订单号" prop="numorgresv" align="center" show-overflow-tooltip />
          </el-table>
        </el-form>

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getMessageTypeData, searchCode, sendMsg } from "@/api/report/report_receive/phone_notify.js";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 短消息名称数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题 
        value: '短消息名称',//第二列标题 
        url: '/shortmessage/getComboData',//请求连接
        selectWidth: 600,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'inputcode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'messageName',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',//向接口传递的参数名(不传默认为'inputCode')
      },
      // 短消息类型列表
      typeOptions: [],
      // 体检号
      patientcode: undefined,
      // 选择的id
      ids: [],
      multiple: true,
      // 选中数组
      examItems: [],
      selectIds: [],
      examList: [],
      // 表单参数
      form: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      rules: {
        messageName: [
          { required: true, message: '请选择短消息名称', trigger: 'change' }
        ],
      }
    };
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() { },
  methods: {
    // 短信通知
    handleSendMsg(patientList) {
      this.reset();
      this.open = true;
      this.title = "短信通知";
      this.examList = JSON.parse(JSON.stringify(patientList))
      getMessageTypeData().then(({ data }) => {

        this.typeOptions = data.records;
      });
      this.loading = false;
    },
    // change方法返回选中的值
    selectChange(value) {
      this.form.messageName = value.id
      this.form.messageType = value.messageType
      this.form.visitNote = value.messageText
    },
    // 选择表格
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.multiple = !selection.length;
    },
    //新增
    handleAdd() {
     
      this.loading = true
      searchCode(this.patientcode).then(({ data }) => {
     
        this.examList.push(data)
      }).finally(() => {
        this.loading = false;
      })
    },
    //删除
    handleremove() {
      this.ids.forEach(val => {
        this.examList.forEach((el, index) => {
          if (el.id == val) {
            this.$delete(this.examList, index)
          }
        })
      })
    },
    // 表单重置
    reset() {
      this.form = {
        messageName: undefined,
        messageType: undefined,
        visitNote: undefined,
        notifyMemo: undefined,
        immediately: true,
        sendTime: undefined,
      }
      this.patientcode = undefined
      this.resetForm("form");
      this.$nextTick(() => {
        this.$refs.smsType.initData()
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.examList.length == 0) {
            this.$modal.msgWarning('请添加体检者后再试')
            return
          }
          let ids = []
          this.examList.forEach(el => {
            ids.push(el.id)
          })
          let form = {
            "ids": ids,
            "immediately": this.form.immediately,
            "messageName": this.form.messageName,
            "notifyMemo": this.form.notifyMemo,
            "sendTime": this.form.sendTime,
          }
          sendMsg(form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.$emit('handleQuery')
          });
        }
      });
    },
  },
};
</script>
<style lang="scss">

</style>
