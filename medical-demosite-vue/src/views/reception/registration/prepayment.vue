<!-- 预付方式 麦沃德科技 予安 -->
<template>
  <el-dialog title="预付方式" :visible.sync="open" width="1280px" append-to-body :close-on-click-modal="false" class="prepayment-main">
    <!-- 按钮 -->
    <el-form size="small" :inline="true" style="padding: 0">
      <el-form-item style="margin-bottom: 5px">
        <el-row>
          <el-button type="success" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['reception:proceeds:add']">添加</el-button>
          <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleRemove" v-hasPermi="['reception:proceeds:remove']" :disabled="multiple">删除</el-button>
          <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleMembers" v-hasPermi="['reception:proceeds:member']">会员卡</el-button>
          <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleExperience" v-hasPermi="['reception:proceeds:experience']">体检卡</el-button>
          <!-- <el-button type="primary" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleFamily" v-hasPermi="['reception:proceeds:experience']">家庭卡</el-button> -->
          <el-button type="danger" plain icon="el-icon-bank-card" size="mini" :disabled="single" @click="handleRefund" v-hasPermi="['reception:proceeds:refund']">误操作退款</el-button>
          <el-button type="success" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh" v-hasPermi="['reception:proceeds:refresh']">刷新</el-button>
        </el-row>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table border ref="tableData" id="setTable" v-loading="loading" :data="tableList" height="485px" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="金额" prop="moneyamountpaid" align="center" min-width="125px">
        <template slot-scope="scope">
          <div v-if="scope.row.ffeecharged == 1 || scope.row.id" style="padding: 0 8px">{{ scope.row.moneyamountpaid ? Number(scope.row.moneyamountpaid).toFixed(2) : '' }}</div>
          <el-input-number :ref="'prepayment' + scope.$index" v-else v-model="scope.row.moneyamountpaid" controls-position="right" :min="0" :precision="2" size="small" class="text-left" style="width: 100%; text-align: left" />
        </template>
      </el-table-column>
      <el-table-column label="付款方式" prop="payway" align="center" show-overflow-tooltip min-width="120px">
        <template slot-scope="scope">
          <span v-if="scope.row.ffeecharged == 1 || scope.row.id">{{ scope.row.payway }}</span>
          <input-select :ref="'payway' + scope.$index" v-else :selectData="selectData" @change="selectChange($event, scope.$index)" selectSize="small" :initialValue="scope.row.payway"></input-select>
        </template>
      </el-table-column>
      <el-table-column label="统收" prop="isTotalcharge" align="center" min-width="60px">
        <template slot-scope="scope">
          <el-checkbox :value="scope.row.isTotalcharge == 1"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="已收已退" prop="ffeecharged" align="center" min-width="100px">
        <template slot-scope="scope">
          <el-checkbox :value="scope.row.ffeecharged == 1"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="收费员" prop="feechargerName" align="center" show-overflow-tooltip min-width="100px" />
      <el-table-column label="收退费时间" prop="feechargetime" align="center" show-overflow-tooltip min-width="160px" />
      <el-table-column label="卡号" prop="cardno" align="center" show-overflow-tooltip min-width="150px">
        <template slot-scope="scope">
          <span v-if="scope.row.ffeecharged == 1 || scope.row.id || scope.row.idPayway == 6 || scope.row.idPayway == 7 || scope.row.idPayway == 22 || scope.row.idPayway == 18 || scope.row.idPayway == 19">{{ scope.row.cardno || scope.row.txTradeNo }}</span>
          <el-input v-else v-model="scope.row.cardno" size="small" clearable></el-input>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="note" align="center" min-width="280px">
        <template slot-scope="scope">
          <span v-if="scope.row.ffeecharged == 1">{{ scope.row.note }}</span>
          <el-input :ref="'note' + scope.$index" v-else v-model="scope.row.note" size="small" type="textarea" :rows="1"></el-input>
        </template>
      </el-table-column>
    </el-table>
    <div class="">
      合计: <span style="color: #74bcff">{{ summation }}</span>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSave('show')">保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
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
import { getChargeData, refundApi } from '@/api/reception/proceeds.js'
import { advanceRefund, getVersion } from '@/api/reception/registration.js'
import memberCard from '../proceeds/card/member_card.vue'
import examCard from '../proceeds/card/exam_card.vue'
import familyCard from '../proceeds/card/family_card.vue'
import WXPay from '../proceeds/WX_pay.vue'
export default {
  components: {
    memberCard,
    examCard,
    WXPay,
  },
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 体检号
      patientcode: undefined,
      // 体检者姓名
      patientname: undefined,
      // 版本号
      version: undefined,
      // 付款方式参数
      selectData: {
        placeholder: '请选择',
        key: '输入码', //第一列标题
        value: '支付方式', //第二列标题
        url: '/reception/register/getPayWayData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        secondName: 'paywayName', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表格加载中
      loading: false,
      // 表格数据
      tableList: [],
      tableListTemp: [],
      // 选中数据
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格索引
      tableIndex: 0,
      // 合计
      summation: 0,
    }
  },
  watch: {
    tableList: {
      handler(newValue) {
        let sum = 0
        newValue.forEach((el) => {
          sum += Number(el.moneyamountpaid)
        })
        this.summation = sum.toFixed(2)
      },
      deep: true,
    },
  },
  methods: {
    // 打开弹窗
    showDialog(patientcode, patientname, version) {
      this.patientcode = patientcode
      this.patientname = patientname
      this.version = version
      this.getList()
      this.open = true
    },
    // 获取表格数据
    getList(isSave) {
      this.loading = true
      getChargeData({ patientcode: this.patientcode }).then(({ data }) => {
        data.forEach((el) => {
          el.tableIndex = 'tableIndex' + this.tableIndex++
          el._state = 'modified'
        })
        this.tableList = data
        this.tableListTemp = JSON.parse(JSON.stringify(data))
        this.loading = false
        if (isSave == 'save') {
          this.handleSave()
        }
      })
    },
    // 表格选中
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    // 付款方式返回参数
    selectChange($event, index) {
      this.tableList[index].idPayway = $event.id
      this.tableList[index].payway = $event.paywayName
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(this.tableList[index], true)
      if ($event.id == 6) {
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          this.handleMembers()
        })
      } else if ($event.id == 7) {
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          this.handleExperience()
        })
      } else if ($event.id == 22 || $event.id == 44  || $event.id == 100) { // id 22通联支付 44随行付 100通联支付2
        this.tableList[index].cardno = undefined
        this.$nextTick(() => {
          if (!this.ids[0].moneyamountpaid) {
            this.$alert('支付金额不能为零', '提示')
            this.tableList[index].idPayway = undefined
            this.tableList[index].payway = undefined
            this.$refs['payway' + index].initData()
            this.$nextTick(() => {
              this.tableList = JSON.parse(JSON.stringify(this.tableList))
            })
            return
          }
          this.handleWXPay($event.id)
        })
      }
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
    // 添加
    handleAdd() {
      var obj = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        idPayway: undefined,
        isTotalcharge: 0,
        ffeecharged: 0,
        moneyamountpaid: 0,
        cardno: undefined,
        note: '预收',
        feechargerName: decodeURIComponent(this.$getCookie('username')),
        _state: 'added',
      }
      this.tableList.push(obj)
    },
    // 删除
    handleRemove() {
      this.ids.forEach((el) => {
        for (let i = this.tableList.length - 1; i >= 0; i--) {
          if (this.tableList[i].tableIndex == el.tableIndex) {
            let alert = ''
            if (!this.tableList[i].id) {
              this.$delete(this.tableList, i)
            } else {
              if (this.tableList[i].ffeecharged == 1 && (this.tableList[i].idPayway == 6 || this.tableList[i].idPayway == 7 || this.tableList[i].idPayway == 18 || this.tableList[i].idPayway == 19 || this.tableList[i].idPayway == 22)) {
                this.$alert('选择的收费信息中第 ' + (i + 1) + ' 条收费信息请使用误操作退款功能退款', '提示')
                return
              } else {
                this.tableListTemp.forEach((el) => {
                  if (el.id == this.tableList[i].id) {
                    el._state = 'removed'
                  }
                })
                this.$delete(this.tableList, i)
              }
            }
            if (!alert && this.tableList[i].ffeecharged == 1) {
              alert = '选择的收费信息中第 ' + (i + 1) + ' 条收费信息已经收费，不能删除'
            }
          }
        }
      })
    },
    // 会员卡
    handleMembers() {
      if (this.ids[0].idPayway != 6) {
        this.$alert('请选择会员积分付款方式')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作')
        return
      }
      let info = {
        patientcode: this.patientcode,
        patientname: this.patientname,
      }
      if (this.ids[0].id) {
        this.$confirm('会员卡误消费操作?', '提示')
          .then(() => {
            this.$refs.memberCard.dialogShow(info, this.ids[0], this.version, true)
          })
          .catch(() => {})
      } else {
        this.$refs.memberCard.dialogShow(info, this.ids[0], this.version, true)
      }
    },
    // 体验卡
    handleExperience() {
      if (this.ids[0].idPayway != 7) {
        this.$alert('请选择体检卡付款方式')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作')
        return
      }
      let info = {
        patientcode: this.patientcode,
        patientname: this.patientname,
      }
      if (this.ids[0].id) {
        this.$confirm('体检卡误消费操作?', '提示')
          .then(() => {
            this.$refs.examCard.dialogShow(info, this.ids[0], this.version, true)
          })
          .catch(() => {})
      } else {
        this.$refs.examCard.dialogShow(info, this.ids[0], this.version, true)
      }
    },
    // 家庭卡
    handleFamily() {
      if (this.ids[0].idPayway != 18 || this.ids[0].idPayway != 19) {
        this.$alert('请选择家庭卡付款方式')
        return
      }
      if (this.ids[0].id) {
        this.$alert('该数据已收费,请勿重复操作')
        return
      }
      let info = {
        patientcode: this.patientcode,
        patientname: this.patientname,
      }
      if (this.ids[0].id) {
        this.$confirm('家庭卡误消费操作?', '提示')
          .then(() => {
            this.$refs.familyCard.dialogShow(info, this.ids[0], this.version, true)
          })
          .catch(() => {})
      } else {
        this.$refs.familyCard.dialogShow(info, this.ids[0], this.version, true)
      }
    },
    // 通联支付
    handleWXPay(idPayway) {
      let info = {
        patientcode: this.patientcode,
        patientname: this.patientname,
      }
      this.$refs.WXPay.dialogShow(info, this.ids[0], this.version, 'prepayment', idPayway)
    },
    // 卡及通联支付成功
    paySuccess(id, cardno, version, name, ffeecharged, moneyamountpaid) {
      this.tableList.forEach((el) => {
        if (el.tableIndex == this.ids[0].tableIndex) {
          el.id = id
          el.payway = name
          el.cardno = cardno
          el.ffeecharged = ffeecharged
          if (moneyamountpaid) {
            el.moneyamountpaid = moneyamountpaid
          }
          el._state = 'modified'
          if (!el.feechargetime) {
            el.feechargetime = this.$getDate()
          }
        }
      })
      if(version){
        this.version = version
      }
      this.$nextTick(() => {
        this.tableList = JSON.parse(JSON.stringify(this.tableList))
        let payWay = []
        this.tableList.forEach((el) => {
          if (el.ffeecharged) {
            payWay.push(el.payway)
          }
        })
        this.$emit('prepaymentSuccess', payWay.join(','), this.summation)
      })
    },
    // 卡及通联支付退费
    handleRefund() {
      let type = this.ids[0].idPayway
      if (type == 6 || type == 7 || type == 22 || type == 18 || type == 19 || type == 44 || type == 100) {
        this.$confirm('是否执行误操作退款?', '提示')
          .then(() => {
            this.loading = true
            refundApi({
              id: this.ids[0].id,
              version: this.version,
              patientname: this.patientname,
              cardId: this.ids[0].cardno,
              jsf: this.ids[0].moneyamountpaid,
              limit: this.ids[0].moneyamountpaid,
              branchId: this.$getCookie('cid'),
              consumeType: '0',
              kkfs: type == 6 ? 2 : type == 7 ? 1 : type == 22 ? 9 : type == 18 ? 7 : type == 19 ? 6 : type == 44 ? 10 : type == 100 ? 11 : '',
              memotext: '退款',
              sfys: 1,
            })
              .then(() => {
                this.getList('save')
                this.$modal.msgSuccess('退款成功')
                getVersion({
                  patientcode: this.patientcode,
                }).then((res) => {
                  this.version = res.data
                  this.$emit('updateVersion', res.data)
                })
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
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    handleSave(showMsg) {
      let error = ''
      let chargeItems = JSON.parse(JSON.stringify(this.tableListTemp))
      this.tableList.forEach((el, index) => {
        if (!error) {
          if (!el.moneyamountpaid) {
            error = '预付金额不能为零,请修改后重新操作'
            this.$refs['prepayment' + index].focus()
            return
          } else if (!el.idPayway) {
            error = '付款方式:不能为空,请修改后重新操作'
            this.$refs['payway' + index].setFocus()
            return
          } else if (el.ffeecharged != 1 && !el.note.includes('预收')) {
            error = '预付时备注中需包含"预付"二字,请重新输入'
            this.$refs['note' + index].focus()
            return
          }
          if (['6', '7', '18', '19','ff80808172a5f6210172a809a9cb2222', '17', '9', '24'].includes(el.idPayway) && !el.cardno) {
            error = '第' + (index + 1) + '条数据卡号不能为空,请修改后重新操作'
          }
        }
        if (el._state == 'added') {
          chargeItems.push(el)
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      if (showMsg) {
        this.$confirm('确定要保存吗？', '提示')
          .then(() => {
            this.executeSave(chargeItems, showMsg)
          })
          .catch(() => {})
      } else {
        this.executeSave(chargeItems)
      }
    },
    // 执行保存
    executeSave(chargeItems, showMsg) {
      advanceRefund({
        patientcode: this.patientcode,
        chargeItems,
      }).then(() => {
        let payWay = []
        this.tableList.forEach((el) => {
          // if (el.ffeecharged) {
          payWay.push(el.payway)
          // }
        })
        this.$emit('prepaymentSuccess', payWay.join(','), this.summation)
        if (showMsg == 'show') {
          this.$modal.msgSuccess('预收费成功')
          this.open = false
        }
      })
    },
  },
}
</script>

<style lang="scss">
.prepayment-main {
  .button-list {
    margin-bottom: 8px;
  }
}
</style>
<style lang="scss">
.prepayment-main .el-table__row .el-table__cell {
  padding: 0;
  height: 32px;
  .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
    background: transparent;
  }
  .text-left .el-input__inner {
    text-align: left !important;
  }
  .el-input__inner {
    padding: 0 8px;
    border-width: 0;
    text-align: center;
    background: transparent;
    &:focus {
      border-width: 1px;
      text-align: left;
    }
  }
}
</style>
