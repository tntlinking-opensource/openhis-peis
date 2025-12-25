<!-- 体检卡 麦沃德科技 予安 -->
<template>
  <el-dialog title="体检卡消费" :visible.sync="open" width="780px" append-to-body :close-on-click-modal="false" @close="cancel" destroy-on-close>
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="100px" :rules="rules" hide-required-asterisk v-loading="loading">
      <el-form-item label="体检卡号" prop="cardId">
        <el-input ref="cardno" v-model="queryParams.cardId" placeholder="请输入体检卡号" clearable :readonly="readOnly" style="width: 250px" @keyup.enter.native="handleCardNum" @change="cardnoChange"></el-input>
      </el-form-item>
      <el-form-item label="卡类型" prop="cardTypeName">
        <el-input v-model="queryParams.cardTypeName" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="可用金额" prop="kyje">
        <el-input :value="queryParams.kyje ? Number(queryParams.kyje).toFixed(2) : '0.00'" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="卡标识" prop="kbs">
        <el-input v-model="queryParams.kbs" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="有效期" prop="yxq">
        <el-input v-model="queryParams.yxq" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="分中心" prop="fzx">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="center"></input-select>
      </el-form-item>
      <el-form-item label="卡说明" prop="ksm">
        <el-input v-model="queryParams.ksm" type="textarea" :rows="3" resize="none" readonly style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="卡备注" prop="kbz">
        <el-input type="textarea" :rows="3" resize="none" v-model="queryParams.kbz" placeholder="请输入备注" readonly style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" :readonly="readOnly" style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="体检者姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" :readonly="readOnly" style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="结算费" prop="jsf">
        <el-input v-model="queryParams.jsf" readonly style="width: 250px"> </el-input>
      </el-form-item>
      <el-form-item label="消费金额" prop="limit">
        <el-input type="number" v-model="queryParams.limit" readonly style="width: 250px" @blur="changeLimit"> </el-input>
      </el-form-item>
      <el-form-item label="消费类型" prop="consumeType">
        <el-select v-model="queryParams.consumeType" style="width: 250px">
          <el-option v-for="item in consumeTypeOptions" :key="item.id" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="memotext">
        <el-input type="textarea" :rows="3" resize="none" v-model="queryParams.memotext" placeholder="请输入备注" clearable style="width: 600px"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getCardData, getBranchData } from '@/api/reception/proceeds.js'
import { saveOrUpdateFee } from '@/api/finance/tally/tally_refund.js'

import { consumeType } from '@/utils/dataList.js'

export default {
  data() {
    return {
      // 弹窗是否显示
      open: false,
      // 加载中
      loading: false,
      // 查询参数
      queryParams: {
        cardId: undefined,
        cardTypeName: undefined,
        kyje: undefined,
        kbs: undefined,
        yxq: undefined,
        fzx: undefined,
        ksm: undefined,
        kbz: undefined,
        patientcode: undefined,
        patientname: undefined,
        jsf: undefined,
        limit: undefined,
        consumeType: '0',
        memotext: undefined,
      },
      // 分中心参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '分中心', //第二列标题
        url: '/sell/createorder/getBranchData', //请求连接
        selectWidth: 250, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'fzx', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 消费类型
      consumeTypeOptions: consumeType(),
      // 校验
      rules: {
        cardId: [{ required: true, message: '体检卡号不能为空', trigger: 'change' }],
        fzx: [{ required: true, message: '请选择分中心', trigger: 'blur' }],
        patientcode: [{ required: true, message: '体检号不能为空', trigger: 'change' }],
        patientname: [{ required: true, message: '体检者姓名不能为空', trigger: 'change' }],
      },
      // 退款时只读
      readOnly: false,
      // 所在默认分中心
      center: '',
    }
  },
  created() {},
  methods: {
    dialogShow(info, ids) {
      this.readOnly = false
      getBranchData({ size: 100 }).then(({ data }) => {
        let centerId = this.$getCookie('cid')
        data.records.forEach((el) => {
          if (el.branchId == centerId) {
            this.center = el.fzx
          }
        })
      })
      this.queryParams = {
        fzx: this.$getCookie('cid'),
        patientcode: info.patientcode,
        patientname: info.name,
        idPayway: '7',
        jsf: ids.moneyamountpaid.toFixed(2),
        limit: ids.moneyamountpaid.toFixed(2),
        chargeParam: {
          moneyamount: ids.moneyamount,
          moneyamountpaid: ids.moneyamountpaid,
          isTotalcharge: 0,
          idPayway: '7',
          isChange: 1,
          _state: 'added',
        },
        consumeType: '0',
        kkfs: 1,
        memotext: undefined,
      }
      if (ids.id) {
        this.readOnly = true
        this.queryParams.cardId = ids.cardno
        this.queryParams.memotext = '退金额'
        this.queryParams.jsf = 0 - this.queryParams.jsf
        this.queryParams.limit = 0 - this.queryParams.limit
        if (this.queryParams.chargeParam.moneyamount) {
          this.queryParams.chargeParam.moneyamount = 0 - this.queryParams.chargeParam.moneyamount
        }
        this.queryParams.chargeParam.moneyamountpaid = 0 - this.queryParams.chargeParam.moneyamountpaid
        this.queryParams.chargeParam.cardno = this.queryParams.cardId
        this.handleCardNum()
      }
      this.open = true
      this.$nextTick(() => {
        this.$refs.cardno.focus()
      })
    },
    // 充值积分保留1位小数
    changeLimit() {
      if (this.queryParams.limit) {
        this.$nextTick(() => {
          this.queryParams.limit = Number(this.queryParams.limit).toFixed(2)
          this.queryParams.jsf = Number(this.queryParams.limit).toFixed(2)
          this.queryParams.chargeParam.moneyamountpaid = Number(this.queryParams.limit).toFixed(2)
        })
      }
    },
    // 查询体检卡号
    handleCardNum() {
      if (!this.queryParams.cardId) {
        this.$alert('请输入体检卡号后再试', '提示')
        return
      }
      this.loading = true
      getCardData({
        cardId: this.queryParams.cardId,
      })
        .then(({ data }) => {
          if (data.sign === 0) {
            this.$alert('体检卡号不存在,请重新输入')
          } else {
            this.queryParams = {
              ...data,
              ...this.queryParams,
            }
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 体检号改变则提交中的体检号也要改变
    cardnoChange() {
      this.queryParams.chargeParam.cardno = this.queryParams.cardId
    },
    // 分中心返回值
    selectChange(value) {
      this.queryParams.fzx = value.id
    },
    // 确定
    handleConfirm() {
      this.$refs['queryForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.$confirm('确定要操作?', '提示')
            .then(() => {
              let queryParams = JSON.parse(JSON.stringify(this.queryParams))
              queryParams.limit = 0 - queryParams.limit
              queryParams.branchId = queryParams.fzx
              saveOrUpdateFee(queryParams)
                .then(({ data }) => {
                  this.$modal.msgSuccess('保存成功')
                  this.loading = false
                  this.$emit('paySuccess', this.queryParams.cardId, '体检卡', this.queryParams.memotext)
                  this.cancel()
                })
                .catch((err) => {
                  console.error(err)
                  this.loading = false
                })
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
            })
        }
      })
    },
    // 重置
    reset() {
      this.$refs['queryForm'].resetFields()
    },
    // 取消按钮
    cancel() {
      this.reset()
      this.open = false
    },
  },
}
</script>
