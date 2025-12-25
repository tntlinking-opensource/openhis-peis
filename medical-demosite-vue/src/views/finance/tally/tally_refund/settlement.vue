<!-- 记账结算-结算  开发人：麦沃德科技 予安 -->
<template>
  <el-dialog title="结算" class="add-items" :visible.sync="open" width="1183px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :inline="true" label-width="80px" hide-required-asterisk size="small">
      <el-form-item prop="patientcode" label="体检号">
        <el-input style="width: 470px" :readonly="true" v-model="form.patientcode"></el-input>
      </el-form-item>
      <el-form-item label="体检者" prop="name">
        <el-input style="width: 470px" :readonly="true" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="体检团体" prop="orgName">
        <el-input style="width: 470px" :readonly="true" v-model="form.orgName"></el-input>
      </el-form-item>
      <el-form-item label="记账金额">
        <el-input style="width: 470px" :readonly="true" v-model="form.jzje"></el-input>
      </el-form-item>
    </el-form>
    <div style="flex: 1; border: 1px solid rgb(221, 221, 221)">
      <!-- 操作按钮 -->
      <div style="margin: 10px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handerAdd">增加 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="single" @click="handleDelete">删除 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-bank-card" size="mini" @click="handleMembers" :disabled="single">会员卡 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-bank-card" size="mini" @click="handleExperience" :disabled="single">体检卡 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleRefund">误操作退款 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh">刷新 </el-button>
          </el-col>
        </el-row>
      </div>
      <el-table ref="tableList" style="" height="460px" :data="tableData" border v-loading="loading" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="moneyamountpaid" label="结算金额" align="center" min-width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1 || scope.row.isPay == 1">{{ scope.row.moneyamountpaid ? parseFloat(scope.row.moneyamountpaid).toFixed(2) : '0.00' }}</span>
            <el-input-number v-else v-model="scope.row.moneyamountpaid" :precision="2" controls-position="right" style="width: 100%" size="mini"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="paywayName" label="付款方式" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1 || scope.row.isPay == 1">{{ scope.row.paywayName }}</span>
            <input-select v-else :selectData="selectData" @change="selectChange($event, scope.$index)" :initialValue="scope.row.paywayName" selectSize="mini"></input-select>
          </template>
        </el-table-column>
        <el-table-column prop="isCharged" label="已收已退" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.isCharged == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="收费员" align="center"></el-table-column>
        <el-table-column prop="moneyamountpaiddate" label="收费时间" align="center" min-width="160"></el-table-column>
        <el-table-column prop="cardno" label="卡号" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1 || scope.row.isPay == 1">{{ scope.row.cardno }}</span>
            <el-input v-else v-model="scope.row.cardno" style="width: 100%" size="mini"></el-input> </template
        ></el-table-column>
        <el-table-column prop="note" label="备注" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1 || scope.row.isPay == 1">{{ scope.row.note }}</span>
            <el-input v-else v-model="scope.row.note" type="textarea" :rows="1" style="width: 100%" size="mini"></el-input> </template
        ></el-table-column>
      </el-table>
      <div style="font-size: 16px; padding-left: 8px">
        合计: <span style="color: #1890ff"> {{ priceSum }}</span> 元
      </div>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSave">确 定</el-button>
      <el-button @click="cancle">取 消</el-button>
    </div>

    <!-- 会员卡弹窗 -->
    <member-card ref="memberCard" @paySuccess="paySuccess"></member-card>
    <!-- 体检卡弹窗 -->
    <exam-card ref="examCard" @paySuccess="paySuccess"></exam-card>
    <!-- 通联支付弹窗 -->
    <WXPay ref="WXPay" @clearSelect="clearSelect" @paySuccess="paySuccess"></WXPay>
  </el-dialog>
</template>
<script>
import { getBillingData, saveOrUpdate, refundApi } from '@/api/finance/tally/tally_refund.js'

import memberCard from './card/member_card.vue'
import examCard from './card/exam_card.vue'
import WXPay from '@/views/reception/proceeds/WX_pay.vue'

export default {
  components: {
    memberCard,
    examCard,
    WXPay,
  },
  data() {
    return {
      open: false,
      // 订单数据
      form: {},
      // 表格加载中
      loading: false,
      // 表格数据
      tableData: [],
      // 表格索引
      tableIndex: 0,
      // 选中的数据
      ids: [],
      single: true,
      // 支付方式参数
      selectData: {
        key: '输入码', //第一列标题
        value: '支付方式', //第二列标题
        url: '/reception/register/getPayWayData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'paywayCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'paywayName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 合计价格
      priceSum: 0,
    }
  },
  watch: {
    tableData: {
      handler(newVal) {
        let sum = 0
        newVal.forEach((el) => {
          if (el.moneyamountpaid) {
            sum += el.moneyamountpaid
          }
        })
        this.priceSum = Number(sum).toFixed(2)
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    //打开
    showDialog(data) {
      this.form = data
      this.getTableList()
      this.open = true
    },
    // 获取表格数据
    getTableList() {
      this.loading = true
      getBillingData({
        id: this.form.id,
      })
        .then(({ data }) => {
          data.forEach((el) => {
            el.tableIndex = 'tableIndex' + this.tableIndex++
            el.state = 'modified'
          })
          this.tableData = data
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //增加行
    handerAdd() {
      var list = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        moneyamountpaid: 0,
        idPayway: undefined,
        idFeecharger: undefined,
        moneyamountpaiddate: undefined,
        cardno: undefined,
        note: undefined,
        isCharged: 0,
        state: 'added',
      }
      this.tableData.unshift(list)
    },
    // 删除行
    handleDelete() {
      let error = ''
      this.ids.forEach((el, index) => {
        if (el.isCharged == 1) {
          error = '序号为' + (index + 1) + '的数据已收已退,不能删除'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.ids.forEach((el) => {
        for (let i = this.tableData.length - 1; i >= 0; i--) {
          if (el.tableIndex == this.tableData[i].tableIndex) {
            this.$delete(this.tableData, i)
            continue
          }
        }
      })
      this.tableData = JSON.parse(JSON.stringify(this.tableData))
    },
    // 会员卡
    handleMembers() {
      if (this.ids[0].idPayway != 6) {
        this.$alert('请选择会员积分付款方式', '提示')
        return
      }
      if (!this.ids[0].moneyamountpaid) {
        this.$alert('金额不能为0', '提示')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作', '提示')
      } else {
        this.$refs.memberCard.dialogShow(this.form, this.ids[0])
      }
    },
    // 体验卡
    handleExperience() {
      if (this.ids[0].idPayway != 7) {
        this.$alert('请选择体检卡付款方式', '提示')
        return
      }
      if (!this.ids[0].moneyamountpaid) {
        this.$alert('金额不能为0', '提示')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作', '提示')
      } else {
        this.$refs.examCard.dialogShow(this.form, this.ids[0])
      }
    },
    // 卡支付成功
    paySuccess(cardno, name, note) {
      this.getTableList()
      this.$emit('settlementSuccess')
    },
    // 通联支付失败
    clearSelect() {
      let index = undefined
      this.tableList.forEach((el, index1) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          index = index1
        }
      })
      this.$refs['payway' + index].initData()
    },
    // 卡及通联支付退费
    handleRefund() {
      let type = this.ids[0].idPayway
      if (type == 6 || type == 7 || type == 22 || type == 18 || type == 19 || type == 44) {
        this.$confirm('是否执行误操作退款?', '提示')
          .then(() => {
            this.loading = true
            refundApi({
              id: this.ids[0].id,
              // version: this.version,
              patientname: this.form.name,
              cardId: this.ids[0].cardno,
              jsf: this.ids[0].moneyamountpaid,
              limit: this.ids[0].moneyamountpaid,
              branchId: this.$getCookie('cid'),
              consumeType: '0',
              kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 22 ? 9 : type == 18 ? 7 : type == 19 ? 6 : type == 44 ? 10 : '',
              memotext: '退款',
              sfys: 1,
            })
              .then(() => {
                this.getTableList()
                this.$modal.msgSuccess('退款成功')
                this.loading = false
              })
              .catch(() => {
                this.loading = false
              })
          })
          .catch(() => {})
      } else {
        this.$alert('该付款方式不支持误操作退款', '提示')
      }
    },
    // 刷新
    handleRefresh() {
      this.getTableList()
    },
    // 支付方式返回
    selectChange($event, index) {
      this.tableData[index].idPayway = $event.id
      this.tableData[index].paywayName = $event.name
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(this.tableData[index])
      this.$nextTick(() => {
        if ($event.id == '6') {
          this.handleMembers()
        } else if ($event.id == '7') {
          this.handleExperience()
        } else if ($event.id == 22 || $event.id == 44) {
          this.tableData[index].cardno = undefined
          this.$nextTick(() => {
            if (!this.ids[0].moneyamountpaid) {
              this.$alert('支付金额不能为零', '提示')
              this.tableData[index].idPayway = undefined
              this.tableData[index].payway = undefined
              this.$refs['payway' + index].initData()
              this.$nextTick(() => {
                this.tableData = JSON.parse(JSON.stringify(this.tableData))
              })
              return
            }
            this.handleWXPay($event.id)
          })
        }
      })
    },
    // 通联支付
    handleWXPay(idPayway) {
      let info = {
        patientcode: this.form.patientcode,
        patientname: this.form.name,
      }
      this.$refs.WXPay.dialogShow(info, this.ids[0], undefined, 'settlement', idPayway)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 确定保存
    handleSave() {
      let error = ''
      this.tableData.forEach((el, index) => {
        if (el.isCharged != 1) {
          if (!error && !el.idPayway) {
            error = '支付方式不能为空'
          }
          if (!error && el.moneyamountpaid == 0) {
            error = '金额不能为空'
          }
          if (!error && (el.idPayway == 22 || el.idPayway == 44) && el.isCharged != 1) {
            error = '请先完成扫码支付,退款请使用误操作退款按钮'
          }
          if (!error && el.isCharged != 1 && el.idPayway == 6) {
            error = '第' + (index + 1) + '条数据为会员积分支付,请先手动结算'
          } else if (!error && el.isCharged != 1 && el.idPayway == 7) {
            error = '第' + (index + 1) + '条数据为体检卡支付,请先手动结算'
          }
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$confirm('确定要结算吗?', '提示')
        .then(() => {
          this.handleCharge(() => {
            this.$emit('settlementSuccess')
            this.$modal.msgSuccess('结算成功')
            this.open = false
          })
        })
        .catch(() => {})
    },
    // 结算
    handleCharge(fn) {
      this.loading = true
      let formdata = JSON.parse(JSON.stringify(this.form))
      let griddata = JSON.parse(JSON.stringify(this.tableData))
      griddata.forEach((el) => {
        el.isCharged = 1
      })
      saveOrUpdate({
        formdata,
        griddata,
      })
        .then(() => {
          if (fn) {
            fn()
          }
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 取消
    cancle() {
      this.form = {}
      this.open = false
    },
  },
}
</script>
<style lang="scss">
.add-items {
  .el-dialog {
    min-width: 800px;
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #d4d6d9;

    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px;
      }
    }
  }
}
</style>
