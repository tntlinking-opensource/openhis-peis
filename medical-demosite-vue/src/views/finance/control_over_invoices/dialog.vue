<!-- 发票管理-子页弹窗 开发人：麦沃德科技清风 -->
<template>
  <div class="add-container">
    <el-dialog :title="title" :visible.sync="open" width="800px" class="view-charge" append-to-body :close-on-click-modal="false">
      <el-form :inline="true" ref="form" :model="form" :rules="rules" label-width="80px" size="mini">
        <el-form-item label="订单号" prop="orderId">
          <!-- <el-input style="width:280px;" v-model="form.orderId" placeholder="请输入订单号"></el-input> -->
          <el-input style="width: 280px" v-model="form.orderId" v-if="readonly" readonly></el-input>
          <input-select :selectData="orderIdData" v-else selectWidth="280" :tjlxType="true" :initialValue="form.orderId" @change="orderIdChange"></input-select>
        </el-form-item>
        <el-form-item label="分中心" prop="fzxid">
          <el-select style="width: 280px" :disabled="readonly" v-model="form.fzxid" placeholder="请选择">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label="发票类型">
          <el-select style="width: 280px" :disabled="readonly" v-model="form.idReceipttype" @change="handleChange" placeholder="请输入">
            <el-option v-for="item in typeOptions" :key="item.id" :label="item.receiptTypeName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发票抬头">
          <el-input style="width: 280px" :readonly="readonly" v-model="form.fptt" placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="发票面额">
          <el-input-number style="width: 280px" :disabled="readonly" v-model="form.receiptcorenumber" @change="handleReceiptcorenumber" controls-position="right" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" id="setTextarea">
          <el-input type="textarea" style="width: 650px" :readonly="readonly" v-model="form.receiptsheetno" placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="反审备注" v-show="fsbs">
          <el-input type="textarea" style="width: 650px" v-model="form.unauditNote" placeholder="请输入"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="title == '新增发票申请'">发票申请</el-button>
        <el-button type="primary" @click="submitForm" v-else-if="title == '编辑'">发票申请</el-button>
        <el-button type="primary" @click="submitForm" v-else-if="title == '复制'">发票申请</el-button>
        <el-button type="primary" @click="passForm(0)" v-else-if="title == '审核'">通过</el-button>
        <el-button type="primary" @click="passForm(2)" v-else-if="title == '审核不通过'">不通过</el-button>
        <el-button type="primary" @click="passForm(1)" v-else-if="title == '反审核'">反审</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getTypeData, saveOrUpdate, detailsTable, examine, unauditSave, unapprove } from '@/api/finance/control_over_invoices'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'

export default {
  data() {
    return {
      title: '',
      open: false,
      form: {
        orderId: '',
        idReceipttype: '',
        fptt: '',
        receiptcorenumber: '',
        receiptsheetno: '',
        unauditNote: '', //反审核备注
      },
      typeOptions: [],
      // 分中心列表
      fzxOptions: [],
      // 外送机构
      orderIdData: {
        placeholder: '请输入订单号选择',
        key: '订单号', //第一列标题
        value: '单位', //第二列标题
        url: '/finance/invoiceRequest/getDdhData', //请求连接--获取订单号下拉
        bindValue: '', //初始值(必传)
        firstName: 'ddh', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'ddh', //向接口传递的参数名(不传默认为'inputCode')
      },
      ddhjg: '', //订单号价格
      //form表单验证
      rules: {
        orderId: [{ required: true, trigger: 'blur', message: '请输入订单号查询' }],
        fzxid: [{ required: true, trigger: 'blur', message: '请选择分中心' }],
      },
      readonly: false, //是否只读
      fsbs: false, //反审合备注
    }
  },
  methods: {
    async handleAdd(context, row) {
      this.form = {}
      this.open = true
      getBranchData().then(({ data }) => {
        this.fzxOptions = data
      })
      if (row) {
        this.rowId = row.id
      }
      await this.getBasicData()
      if (context == 0) {
        this.title = '新增发票申请'
        this.form = {
          orderId: '',
          idReceipttype: '',
          fptt: '',
          receiptcorenumber: '',
          receiptsheetno: '',
        }
      } else if (context == 1) {
        this.title = '编辑'
        detailsTable(row.id).then((res) => {
          this.form = res.data
          this.form.receiptsheetno = res.data.note
          this.ddjg = res.data.ddjg
        })
      } else if (context == 2) {
        this.title = '审核'
        this.readonly = true
        this.fsbs = false
        detailsTable(row.id).then((res) => {
          this.form = res.data
          this.form.receiptsheetno = res.data.note
          this.ddjg = res.data.ddjg
        })
      } else if (context == 3) {
        this.title = '审核不通过'
        this.readonly = true
        this.fsbs = false
        detailsTable(row.id).then((res) => {
          this.form = res.data
          this.form.receiptsheetno = res.data.note
          this.ddjg = res.data.ddjg
        })
      } else if (context == 4) {
        this.title = '复制'
        this.form = {
          idReceipttype: row.idReceipttype,
          fptt: row.fptt,
          receiptsheetno: row.note,
        }
      } else if (context == 5) {
        this.title = '反审核'
        this.fsbs = true
        this.readonly = true
        detailsTable(row.id).then((res) => {
          this.form = res.data
          this.form.receiptsheetno = res.data.note
          this.ddjg = res.data.ddjg
        })
      }
    },
    //基础数据
    async getBasicData() {
      getTypeData().then((res) => {
        this.typeOptions = res.data
      })
    },
    //订单号事件
    orderIdChange(value) {
      this.form.orderId = value.ddh
      this.ddhjg = value.ddjg
    },
    //发票类型
    handleChange(value) {
      this.form.idReceipttype = value
    },
    //发送申请保存
    submitForm() {
      this.$refs.form.validate((val) => {
        if (val) {
          let obj = {
            id: this.form.id || '',
            orderId: this.form.orderId,
            idReceipttype: this.form.idReceipttype,
            fptt: this.form.fptt,
            receiptcorenumber: this.form.receiptcorenumber,
            note: this.form.receiptsheetno,
            fzxid: this.form.fzxid,
          }
          saveOrUpdate(obj).then((res) => {
            this.$modal.msgSuccess(res.msg)
            this.$emit('getList')
            this.open = false
          })
        }
      })
    },
    //通过
    passForm(context) {
      let obj = {
        id: this.form.id,
        unauditNote: this.form.unauditNote || '',
      }
      //0--审核 1--反审核 2--审核不通过
      if (context == 0) {
        this.$confirm('确定审核通过吗？', '确认', {
          confirmButtonText: '确 认',
          cancelButtonText: '取 消',
          type: 'warning',
        })
          .then(() => {
            examine(obj).then((res) => {
              this.$modal.msgSuccess(res.msg)
              this.open = false
              this.$emit('getList')
            })
          })
          .catch(() => {})
      } else if (context == 1) {
        this.$confirm('确定反审核吗？', '确认', {
          confirmButtonText: '确 认',
          cancelButtonText: '取 消',
          type: 'warning',
        })
          .then(() => {
            unauditSave(obj).then((res) => {
              this.$modal.msgSuccess(res.msg)
              this.open = false
              this.$emit('getList')
            })
          })
          .catch(() => {})
      } else if (context == 2) {
        this.$confirm('确定审核不通过吗？', '提醒', {
          confirmButtonText: '确 认',
          cancelButtonText: '取 消',
          type: 'warning',
        })
          .then(() => {
            unapprove(obj).then((res) => {
              this.$modal.msgSuccess(res.msg)
              this.open = false
              this.$emit('getList')
            })
          })
          .catch(() => {})
      }
    },
    //改变值事件
    handleReceiptcorenumber(value) {
      // if (value > this.ddhjg) {
      //   this.$alert('发票金额,不能大于订单金额!', '提示', {
      //     confirmButtonText: '确认',
      //     type: 'warning',
      //   })
      //   this.form.receiptcorenumber = ''
      // }
    },
    reset() {
      if (this.rowId) {
        detailsTable(this.rowId).then((res) => {
          this.form = res.data
          this.form.receiptsheetno = res.data.note
          this.ddjg = res.data.ddjg
        })
      } else {
        this.form = {
          orderId: '',
          idReceipttype: '',
          fptt: '',
          receiptcorenumber: '',
          receiptsheetno: '',
          unauditNote: '', //反审核备注
        }
      }
    },
    cancel() {
      this.open = false
    },
  },
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
  height: 100px;
}
</style>
