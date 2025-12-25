<!-- 财务管理-卡充值-充值 麦沃得科技 开发人:清风/矢北 -->
<template>
  <el-dialog title="充值" :visible.sync="open" width="1600px" append-to-body :close-on-click-modal="false">
    <div style="display:flex; flex-direction:row; height: 100%;">
      <div style="width: 720px;">
        <el-form :inline="true" ref="form" :model="form" :rules="rule" label-width="80px" style="height:100%;">
          <el-form-item label="体检卡号" prop="cardId">
            <el-input style="width:260px;" v-model="form.cardId" @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item label="卡类型" prop="cardTypeName">
            <el-input style="width:260px;" :readonly="true" v-model="form.cardTypeName"></el-input>
          </el-form-item>
          <el-form-item label="可用金额" prop="kyje">
            <el-input style="width:260px;" :readonly="true" v-model="form.kyje"></el-input>
          </el-form-item>
          <el-form-item label="卡标识" prop="kbs">
            <el-input style="width:260px;" :readonly="true" v-model="form.kbs"></el-input>
          </el-form-item>
          <el-form-item label="有效期" prop="yxq">
            <el-input style="width:260px;" :readonly="true" v-model="form.yxq"></el-input>
          </el-form-item>
          <el-form-item label="卡说明" prop="ksm">
            <el-input style="width:260px;" :readonly="true" v-model="form.ksm"></el-input>
          </el-form-item>
          <el-form-item label="卡备注" prop="kbz">
            <el-input style="width:260px;" :readonly="true" v-model="form.kbz"></el-input>
          </el-form-item>
          <el-form-item label="充值金额" prop="limit">
            <el-input style="width:260px;" :readonly="true" v-model="form.limit"></el-input>
          </el-form-item>
          <el-form-item label="分中心" prop="branchCenter">
            <el-select style="width:260px;" v-model="form.branchCenter">
              <el-option v-for="item, index in fzxOptions" :key="index" :label="item.fzx" :value="item.branchId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" prop="memotext" id="setTextarea">
            <el-input type="textarea" style="width:610px;" placeholder="请输入" v-model="form.memotext"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="flex: 1; border:1px solid rgb(221, 221, 221);">
        <!-- 操作按钮 -->
        <div style="margin: 10px">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" plain icon="el-icon-circle-check" size="mini" @click="handleWindow()">增加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" plain icon="el-icon-circle-close" size="mini" @click="returnHandleWindow()">删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain icon="el-icon-edit-outline" size="mini">会员卡</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain icon="el-icon-edit-outline" size="mini">体检卡</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain icon="el-icon-edit-outline" size="mini" @click="handleRefresh()">刷新</el-button>
            </el-col>
          </el-row>
        </div>
        <el-table :data="tableData" border @selection-change="handleSelectionChange" v-loading="loading" :stripe="true" height="600px">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
          <el-table-column prop="moneyamountpaid" label="付款金额" align="center">
            <template slot-scope="scope">
              <el-input-number @change="calMoney" v-model="scope.row.moneyamountpaid" style="width: 120px;" controls-position="right" :min="0" :max="999999"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="idPayway" label="付款方式" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.idPayway">
                <el-option v-for="item, index in payWay" :key="index" :label="item.paywayName" :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="editor" label="收费员" align="center">
            <template slot-scope="scope">
              <el-input :readonly="true" v-model="scope.row.editor"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="cardno" label="卡号" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.cardno"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="note" label="备注" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.note"></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
      <el-button type="primary" @click="reset" plain>重 置</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import Cookies from 'js-cookie'
import { fzxData, getDetailData, getPayWay, saveOrUpdate } from '@/api/finance/card/card_recharge.js'
export default {
  data() {
    return {
      //支付方式
      payWay: [],
      ids: [],
      //分中心数据
      fzxOptions: [],
      loading: false,
      open: false,
      tableData: [],
      form: {},
      rule: {
        cardId: [{ required: true, message: "体检号不能为空", trigger: "change" }, { len: 10, message: '长度必须为10数字' }],
      },
    }
  },
  methods: {
    rechargeWindow() {
      this.reset()
      this.open = true;
      getPayWay().then(response => {
        this.payWay = response.data.records
      })
      this.getFzxData()
    },
    handleQuery() {
      const query = {
        cardId: this.form.cardId
      }
      getDetailData(query).then(response => {
        if (response.data.sign == 0) {
          this.$message.warning('卡号不存在，请检查输入的是否正确')
        } else if (response.data.sign == 2) {
          this.$message.warning('该类型体检卡不能充值')
        } else if (response.data.sign == 3) {
          this.$message.warning('会员卡不能在此充值')
        } else if (response.data.sign == 1) {
          this.form = JSON.parse(JSON.stringify(response.data))
          this.$set(this.form, "cardId", query.cardId)
          this.$set(this.form, "limit", 0)
          this.$set(this.form, "branchCenter", Cookies.get("cid"))
          this.$set(this.form, "memotext", "")
          this.calMoney()
        }
      })
    },
    close() {
      this.open = false
    },
    //获取分中心数据
    getFzxData() {
      fzxData().then(response => {
        this.fzxOptions = response.data
      })
    },
    calMoney() {
      this.form.limit = 0
      let num = 0
      for (var i in this.tableData) {
        num += Number(this.tableData[i].moneyamountpaid)
      }
      this.$set(this.form, "limit", num)
    },
    //增加按钮
    handleWindow() {
      var list = {
        moneyamountpaid: 0,
        idPayway: undefined,
        idFeecharger: undefined,
        moneyamountpaiddate: undefined,
        cardno: undefined,
        note: undefined,
        moneyamountpaid: 0,
        isTotalcharge: 0,
        isCharged: 0,
        editor: undefined
      }
      ///id就是序号 从1开始
      this.tableData.push(list)
      for (var i in this.tableData) {
        this.tableData[i].id = Number(i) + Number(1)
      }
    },
    //选中时变化
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //删除行
    returnHandleWindow() {
      this.form.limit = 0
      this.ids.forEach((val) => {
        this.tableData.forEach((item, i) => {
          if (item.id == val) {
            this.$delete(this.tableData, i)
          }
        })
      })
      for (var i in this.tableData) {
        this.tableData[i].id = Number(i) + Number(1)
        this.form.limit += this.tableData[i].moneyamountpaid
      }
    },
    // 刷新
    handleRefresh() {
      this.tableData = []
      this.form.limit = 0
    },
    //提交按钮
    handleSubmit() {
      const form = this.form
      form.ids = this.form.id
      form.sellName = this.form.sellId
      let state = 0
      //增加状态
      this.addState()
      const query = {
        formdata: form,
        griddata: this.tableData
      }
      //不存在空行前判断 不能让带有卡字的卡类型缺少填写的卡号
      //当不存在空行让状态等于1
      for (let i in this.tableData) {
        // if (!this.tableData[i].idPayway || !this.tableData[i].cardno) {
        if (!this.tableData[i].idPayway) {
          this.$message.warning('付款方式不能为空')
          state = 0
          return;
        } else {
          state = 1
        }
      }
      if (this.tableData.length < 1) {
        this.state = 0
        this.$message.warning('请在保存前至少填写一条收款信息')
      }
      //只有不存在空行时进行判断是否内容价格是否相等
      if (state == 1) {
        if (this.countMoney != this.form.sellPrice) {
          this.$message.warning(`请检查收费内容，当前收款${this.countMoney}人民币,实际应收款${this.form.sellPrice}人民币`);
        } else {
          this.$refs["form"].validate(valid => {
            if (valid) {
              saveOrUpdate(query).then(response => {
                this.$modal.msgSuccess("充值成功");
                this.open = false;
                this.$emit("getList")
              });
            }
          });
        }
      }
    },
    reset() {
      this.tableData = []
      this.form = {
        cardId: "",
        limit: 0,
        branchCenter: Cookies.get("cid"),
        memotext: "",
      }
      this.resetForm("form");
    },
    //给每一条数据附加状态
    addState() {
      for (let i in this.tableData) {
        this.tableData[i].state = 'add'
      }
    }
  }
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
  height: 150px;
}
</style>