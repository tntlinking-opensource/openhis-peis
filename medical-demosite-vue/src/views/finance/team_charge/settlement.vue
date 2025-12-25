<!-- 团体结算-结算  开发人：麦沃德科技 予安 -->
<template>
  <el-dialog title="结算" class="add-items" :visible.sync="open" width="1183px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :inline="true" label-width="80px" hide-required-asterisk size="small">
      <el-form-item prop="orderNum" label="订单号">
        <el-input style="width: 470px" :readonly="true" v-model="form.orderNum"></el-input>
      </el-form-item>
      <el-form-item label="团体名称">
        <el-input style="width: 470px" :readonly="true" v-model="form.orgName"></el-input>
      </el-form-item>
      <el-form-item label="应收金额">
        <el-input style="width: 470px" :readonly="true" v-model="form.ys"></el-input>
      </el-form-item>
      <el-form-item label="结算金额">
        <el-input style="width: 470px" :readonly="true" v-model="form.sf"></el-input>
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
            <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleMembers">会员卡 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleExperience">体检卡 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh">刷新 </el-button>
          </el-col>
        </el-row>
      </div>
      <el-table ref="tableList" height="460px" :data="tableData" border v-loading="loading" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="moneyamountpaid" label="结算金额" align="center" min-width="130">
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1">{{ scope.row.moneyamountpaid ? parseFloat(scope.row.moneyamountpaid).toFixed(2) : '0.00' }}</span>
            <el-input-number v-else v-model="scope.row.moneyamountpaid" :precision="2" controls-position="right" style="width: 100%" size="mini"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="paywayName" label="付款方式" align="center"
          ><template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1">{{ scope.row.paywayName }}</span>
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
        <el-table-column prop="cardno" label="卡号" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1">{{ scope.row.cardno }}</span>
            <el-input v-else v-model="scope.row.cardno" style="width: 100%" size="mini"></el-input> </template
        ></el-table-column>
        <el-table-column prop="note" label="备注" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isCharged == 1">{{ scope.row.note }}</span>
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

      <!-- 会员卡弹窗 -->
      <member-card ref="memberCard" @paySuccess="paySuccess"></member-card>
      <!-- 体检卡弹窗 -->
      <exam-card ref="examCard" @paySuccess="paySuccess"></exam-card>
    </div>
  </el-dialog>
</template>
<script>
import { getBillingData, saveOrUpdate } from '@/api/finance/team_charge.js'

import memberCard from './card/member_card.vue'
import examCard from './card/exam_card.vue'
export default {
  components: {
    memberCard,
    examCard,
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
      if (data) {
        data.ys = data.ys ? parseFloat(data.ys).toFixed(2) : '0.00'
        data.sf = data.sf ? parseFloat(data.sf).toFixed(2) : '0.00'
      }
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
      this.tableData.push(list)
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
      this.tableData.forEach((el) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          el.isPay = 1
          el.payway = name
          el.cardno = cardno
          el._state = 'modified'
          el.isCharged = 1
          el.userName = decodeURIComponent(this.$getCookie('username'))
          el.moneyamountpaiddate = this.$getDate()
          el.note = note
        }
      })
      this.$nextTick(() => {
        this.tableData = JSON.parse(JSON.stringify(this.tableData))
        this.handleCharge(() => {
          this.$emit('settlementSuccess')
        })
      })
    },
    // 刷新
    handleRefresh() {
      this.getTableList()
    },
    // 支付方式返回
    selectChange($event, index) {
      this.tableData[index].idPayway = $event.id
      this.tableData[index].paywayName = $event.name
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 确定
    handleSave() {
      let error = ''
      let errorIndex = undefined
      this.tableData.forEach((el, index) => {
        if (!error && !el.idPayway) {
          error = '支付方式不能为空'
          errorIndex = index
        }
        if (!error && el.moneyamountpaid == 0) {
          error = '金额不能为空'
          errorIndex = index
        }
        if (!error && el.isCharged != 1 && el.idPayway == 6) {
          error = '第' + (index + 1) + '条数据为会员积分支付,请先手动结算'
        } else if (!error && el.isCharged != 1 && el.idPayway == 7) {
          error = '第' + (index + 1) + '条数据为体检卡支付,请先手动结算'
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
      formdata.realityMoney = formdata.sf
      formdata.shouldMoney = formdata.ys
      saveOrUpdate({
        formdata,
        griddata: this.tableData,
      })
        .then(() => {
          if (fn) {
            fn()
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
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
