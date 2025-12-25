<!-- 记账结算-结算  开发人：麦沃德科技 矢北 -->
<template>
  <el-dialog title="结算" class="add-items" :visible.sync="open" width="1183px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :inline="true" label-width="80px" hide-required-asterisk size="small">
      <el-form-item prop="transactionnumber" label="流水号">
        <el-input style="width: 470px" :readonly="true" v-model="form.transactionnumber"></el-input>
      </el-form-item>
      <el-form-item label="汇款单位" prop="remitter">
        <el-input style="width: 470px" :readonly="true" v-model="form.remitter"></el-input>
      </el-form-item>
      <el-form-item label="汇款日期" prop="transdate">
        <el-input style="width: 470px" :readonly="true" v-model="form.transdate"></el-input>
      </el-form-item>
      <el-form-item label="汇款金额" prop="income">
        <el-input style="width: 470px" :readonly="true" v-model="form.income"></el-input>
      </el-form-item>
    </el-form>
    <div style="flex: 1; border: 1px solid rgb(221, 221, 221)">
      <!-- 操作按钮 -->
      <div style="margin: 10px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handerAdd" :disabled="auditState !== '0'">增加 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="!multiple || auditState !== '0'" @click="handleDelete">删除 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-refresh-right" size="mini" @click="refresh">刷新 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-s-check" size="mini" @click="handleAudit(1)" :disabled="auditState !== '0'">审核 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-s-check" size="mini" @click="handleAudit(2)" :disabled="auditState === '0'">反审 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-upload2" size="mini" @click="bank">上传银行流水 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-refresh-right" size="mini" @click="kingDee">金蝶客户数据更新 </el-button>
          </el-col>
        </el-row>
      </div>
      <el-table ref="tableList" style="" height="460px" :data="tableData" border v-loading="loading" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="payWayName" label="结算方式（先选择）" min-width="160" align="center">
          <template slot-scope="scope">
            <input-select :selectData="JSData" :currentIndex="scope.row.tableIndex" @change="JSChange" :initialValue="scope.row.payWayName"></input-select>
          </template>
        </el-table-column>
        <el-table-column prop="idCustomer" label="ID号" align="center" min-width="160">
          <template slot-scope="scope">
            <el-input v-if="scope.row.idRemittanceway == 'ZKGJ9908'" v-model="scope.row.idCustomer" placeholder="请输入"></el-input>
            <input-select v-else :selectData="IDData" :queryParams="{ way: scope.row.idRemittanceway }" style="text-align: center" :currentIndex="scope.row.tableIndex" @change="IDChange" :disabled="scope.row.idRemittanceway ? false : true" :initialValue="scope.row.idCustomer"></input-select>
          </template>
        </el-table-column>
        <el-table-column prop="customername" label="客户名称" align="center" min-width="260">
          <template slot-scope="scope">
            <el-input v-model="scope.row.customername" placeholder="请输入" v-if="scope.row.idRemittanceway == 'ZKGJ9908'"></el-input>
            <el-tooltip v-else effect="dark" :content="scope.row.customername" placement="top-start">
              <input-select :queryParams="{ way: scope.row.idRemittanceway }" :isTrim="true" :showTooltip="true" :selectData="DWData" :currentIndex="scope.row.tableIndex" @change="DWChange" :disabled="scope.row.idRemittanceway ? false : true" :initialValue="scope.row.customername"></input-select>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="moneyamountpaid" label="金额" align="center" width="180">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.moneyamountpaid" :min="0" controls-position="right" :precision="2" clearable style="width: 150px" placeholder="" @change="changePrice(scope.$index)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="xsjl" label="销售经理" align="center" min-width="200">
          <template slot-scope="scope">
            <input-select
              :selectData="XSData"
              :queryParams="{ way: scope.row.idRemittanceway, id: scope.row.idRemittanceway == 'ZKGJ9921' ? scope.row.idCustomer : undefined }"
              style="text-align: center"
              :currentIndex="scope.row.tableIndex"
              :disabled="scope.row.idRemittanceway || (scope.row.idRemittanceway == 'ZKGJ9921' && scope.row.customername) ? false : true"
              @change="XSChange"
              :initialValue="scope.row.xsjl"
            ></input-select>
          </template>
        </el-table-column>
        <el-table-column prop="note" label="备注" align="center" min-width="160">
          <template slot-scope="scope">
            <el-input placeholder="请输入备注" style="width: 100%" v-model="scope.row.note"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="creatorName" label="创建人" align="center" min-width="160"> </el-table-column>
        <el-table-column prop="changeName" label="修改人" align="center" min-width="160"> </el-table-column>
        <el-table-column prop="auditName" label="审核人" align="center" min-width="160"> </el-table-column>
        <el-table-column prop="isAudit" label="审核状态" align="center" min-width="160">
          <template slot-scope="scope">
            <span style="color: red" v-if="scope.row.isAudit == 0">未审核</span>
            <span style="color: green" v-else>已审核</span>
          </template>
        </el-table-column>
        <el-table-column prop="isUpdate" label="同步状态" align="center" min-width="160">
          <template slot-scope="scope">
            <span style="color: red" v-if="scope.row.isUpdate == 0">未同步</span>
            <span style="color: green" v-else>已同步</span>
          </template>
        </el-table-column>
        <el-table-column prop="createdate" label="创建时间" align="center" min-width="160"> </el-table-column>
        <el-table-column prop="modifydate" label="修改时间" align="center" min-width="160"> </el-table-column>
        <el-table-column prop="auditdate" label="审核日期" align="center" min-width="160"> </el-table-column>
      </el-table>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" v-if="auditState === '0'" @click="handleSave">确 定</el-button>
      <el-button @click="cancle">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getDetailData, getBilData, getPayList, updateData, upLoadBank, kingDeeUpdate, isCaiWu, approveApi, unapproveApi } from '@/api/finance/tally/bank_account.js'

export default {
  components: {},
  data() {
    return {
      open: false,
      // 订单数据
      form: {},
      // 表格加载中
      loading: false,
      // 表格数据
      tableData: [],
      tempData: [],
      // 表格索引
      tableIndex: 0,
      payWay: [],
      // 选中的数据
      ids: [],
      multiple: false,
      // 支付方式参数
      selectData: {
        key: '输入码', //第一列标题
        value: '结算方式', //第二列标题
        url: '/finance/bankSettlement/getKingdeeReserWay', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'accountName', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'accountName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 结算方式参数
      JSData: {
        key: '金蝶ID', //第一列标题
        value: '结算方式', //第二列标题
        url: '/finance/bankSettlement/getKingdeeReserWay', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'accountNo', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'accountName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 单位参数
      DWData: {
        //第一列标题
        value: '客户名称', //第二列标题
        url: '/finance/bankSettlement/getNameNumber', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'name', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
        selectWidth: 230,
      },
      //禁用
      isuse: true,
      // ID参数
      IDData: {
        key: '名称', //第一列标题
        value: 'id', //第二列标题
        url: '/finance/bankSettlement/getIdCustomer', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'name', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'id', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // ID参数
      XSData: {
        //第一列标题
        value: 'ID', //第二列标题
        url: '/finance/bankSettlement/getFeeChargerData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'userNo', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 合计价格
      priceSum: 0,
      // 方式的组数
      wayList: [],
      id: undefined,
      // 审核状态
      auditState: '',
      isProcessing: false,
    }
  },
  watch: {
    tableData: {
      handler(newVal) {
        // 添加防重复处理标志
        if (this.isProcessing) return;
        
        this.isProcessing = true;
        this.$nextTick(() => {
          let sum = 0
          newVal.forEach((el) => {
            if (el.moneyamountpaid) {
              sum += el.moneyamountpaid
            }
          })
          this.priceSum = Number(sum).toFixed(2)
          this.isProcessing = false;
        });
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    //打开
    showDialog(id, auditState) {
      this.id = id
      this.auditState = auditState
      this.tableData = []
      this.tableIndex = 0
      this.getFormData(id)
      this.payListData()
      this.getTableList(id)
      this.open = true
    },
    refresh() {
      this.getTableList(this.id)
    },
    payListData() {
      getPayList().then((response) => {
        this.payWay = response.data.records
      })
    },
    //获取表单数据
    getFormData(id) {
      getDetailData(id).then(({ data }) => {
        data.income = Number(data.income).toFixed(2)
        this.form = data
      })
    },
    // 获取表格数据
    getTableList(data) {
      this.loading = true
      getBilData({
        transactionNumber: data,
      })
        .then(({ data }) => {
          data.records.forEach((el) => {
            el.state = 'modified'
          })
          this.tableData = data.records
          this.tempData = JSON.parse(JSON.stringify(data.records))
          for (var i in this.tableData) {
            this.tableData[i].tableIndex = this.tableIndex++
            this.wayList.push(this.tableData[i].idRemittanceWay)
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    JSChange(value, tableIndex) {
      this.tableData[tableIndex].idRemittanceway = value.accountNo
      this.tableData[tableIndex].customername = ''
      this.tableData[tableIndex].idCustomer = ''
    },
    DWChange(value, tableIndex) {
      this.tableData[tableIndex].customername = value.name
      this.tableData[tableIndex].idCustomer = value.id
    },
    IDChange(value, tableIndex) {
      this.tableData[tableIndex].idCustomer = value.id
      this.tableData[tableIndex].customername = value.inputCode
    },
    XSChange(value, tableIndex) {
      this.tableData[tableIndex].idFeecharger = value.userNo
      this.tableData[tableIndex].displayIdFeecharger = value.username
    },
    // 修改金额，不能大于回款金额
    changePrice(index) {
      let sum = 0
      this.tableData.forEach((el) => {
        sum = Math.round((sum + el.moneyamountpaid) * 100) / 100
      })
      if (sum > this.form.income) {
        this.$alert('结算金额不能大于汇款金额', '提示')
        this.tableData[index].moneyamountpaid = 0
        return
      }
    },
    //增加行
    handerAdd() {
      var list = {
        isAudit: 0,
        isUpdate: 0,
        idRemittanceway: undefined,
        customername: undefined,
        idCustomer: undefined,
        moneyamountpaid: undefined,
        idFeecharger: undefined,
        displayIdFeecharger: undefined,
        note: undefined,
        state: 'added',
        tableIndex: this.tableIndex++,
      }
      this.tableData.push(list)
    },
    // 删除行
    handleDelete() {
      this.index.forEach((val) => {
        this.tableData.forEach((item, i) => {
          if (item.tableIndex == val) {
            this.$delete(this.tableData, i)
          }
        })
      })
    },
    //银行流水上传
    bank() {
      let total = 0
      for (var i in this.tableData) {
        if (this.tableData[i].isAudit == 1) {
          total = Math.round((total + this.tableData[i].moneyamountpaid) * 100) / 100
        }
      }
      if (total != Number(this.form.income)) {
        this.$modal.msgWarning('已审核的流水详情总金额不等于银行交易流水金额，不能上传')
      } else {
        this.loading = true
        isCaiWu()
          .then(({ data }) => {
            if (data == 'error') {
              this.loading = false
              this.$alert('没有权限进行此操作', '提示')
              return
            }
            upLoadBank({ transactionNumber: this.form.transactionnumber })
              .then((response) => {
                if (response.code == 200) {
                  this.$modal.msgSuccess('上传成功')
                  this.loading = false
                } else {
                  this.loading = false
                }
              })
              .catch((err) => {
                this.loading = false
                console.error(err)
              })
          })
          .catch((err) => {
            console.error(err)
            this.loading = false
          })
      }
    },
    //金蝶客户数据更新
    kingDee() {
      kingDeeUpdate().then((response) => {
        if (response.code == 200) {
          this.$modal.msgSuccess(response.data)
        }
      })
    },
    // 审核
    handleAudit(type) {
      if (!this.tableData.length) {
        this.$alert('当前没有需要审核的数据', '提示')
        return
      }
      let total = 0
      for (var i in this.tableData) {
        total = Math.round((total + this.tableData[i].moneyamountpaid) * 100) / 100
      }
      if (total != Number(this.form.income) && type == 1) {
        this.$modal.msgWarning('结算金额不等于流水金额,不能审核')
      } else {
        //调用接口
        // this.getTableList()
        this.loading = true
        isCaiWu()
          .then(({ data }) => {
            if (data == 'error') {
              this.loading = false
              this.$alert('没有权限进行此操作', '提示')
              return
            }
            let rowsId = []
            this.tableData.forEach((el) => {
              rowsId.push(el.id)
            })
            if (type == 1) {
              approveApi({ rowsId: rowsId.join(',') })
                .then(() => {
                  this.$modal.msgSuccess('审核成功')
                  this.loading = false
                  this.open = false
                  this.$emit('getList')
                })
                .catch((err) => {
                  console.error(err)
                  this.loading = false
                })
            } else {
              unapproveApi({ rowsId: rowsId.join(',') })
                .then(() => {
                  this.$modal.msgSuccess('反审核成功')
                  this.loading = false
                  this.open = false
                  this.$emit('getList')
                })
                .catch((err) => {
                  console.error(err)
                  this.loading = false
                })
            }
          })
          .catch((err) => {
            console.error(err)
            this.loading = false
          })
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.index = selection.map((item) => item.tableIndex)
      this.ids = selection.map((item) => item.id)
      this.multiple = selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 确定保存
    handleSave() {
      let error = undefined
      if (!this.tableData.length) {
        error = '没有需要保存的数据'
      }
      this.tableData.forEach((el, tableIndex) => {
        if (!el.idRemittanceway) {
          error = '第' + (tableIndex + 1) + '条数据为还未选择结算方式'
        } else if (!el.customername) {
          error = '第' + (tableIndex + 1) + '条数据为还未选择客户'
        } else if (!el.moneyamountpaid) {
          error = '第' + (tableIndex + 1) + '条数据结算金额不能为0。'
        } else if (!el.idFeecharger) {
          error = '第' + (tableIndex + 1) + '条数据为还未选择销售经理'
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      let griddata = []
      let dataList = []
      this.tableData.forEach((el) => {
        griddata.push(el)
        if (el.id) {
          dataList.push(el.id)
        }
      })
      this.tempData.forEach((el) => {
        if (!dataList.includes(el.id) && !griddata.map((item) => item.id).includes(el.id)) {
          el.state = 'removed'
          griddata.push(el)
        }
      })
      // griddata = [...griddata, ...this.tempData]
      updateData({ formdata: this.form, griddata }).then((response) => {
        this.$modal.msgSuccess('修改成功')
        this.open = false
        this.$emit('getList')
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
