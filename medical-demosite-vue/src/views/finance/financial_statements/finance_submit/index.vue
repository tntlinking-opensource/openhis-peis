<!-- 财务提报  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column general-inspection" style="min-height: auto; overflow: auto">
    <el-form label-width="174px" inline ref="postData" :model="postData" style="height: 100%">
      <el-form-item label-width="120px" label="选择日期">
        <el-date-picker value-format="yyyy-MM" type="month" @change="handleQuery" v-model="queryData.choosedate" placeholder=""></el-date-picker>
      </el-form-item>
      <div class="main-box" style="position: relative">
        <div class="post-box">
          <div style="padding: 12px; overflow: auto">
            <!-- 基本信息表单 -->
            <span class="label-title">产值情况</span>
            <el-form-item prop="outputvalueGoals" label="全年目标（不含疫苗）：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.outputvalueGoals" placeholder=""></el-input>
            </el-form-item>
            <span class="label-title">利润情况</span>
            <el-form-item prop="profitsituationProfit" label="利润：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.profitsituationProfit" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="profitsituationGoals" label="全年目标（不含疫苗）：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.profitsituationGoals" placeholder=""></el-input>
            </el-form-item>
            <span class="label-title">现金流情况</span>
            <el-form-item prop="cashflowFlow" label="现金净流量：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.cashflowFlow" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="cashflowCordon" label="资金安全警戒线：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.cashflowCordon" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="cashflowOutputvalue" label="产值（不含疫苗）：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.cashflowOutputvalue" placeholder=""></el-input>
            </el-form-item>
          </div>
          <div style="padding: 12px; overflow: auto">
            <span class="label-title">销售数据情况</span>
            <el-form-item prop="saleoverdueAccounts" label="超期应收账款（万元）：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.saleoverdueAccounts" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="saleoverdueRateOfRecovery" label="当期应收账款回收率：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.saleoverdueRateOfRecovery" placeholder=""> <span slot="suffix" style="padding-right: 6px; font-size: 14px"> % </span></el-input>
            </el-form-item>
            <el-form-item prop="saleoverdueDiscountRate" label="报扣率：">
              <el-input style="width: 416px" oninput="value=value.replace(/[^0-9]/g,'')" v-model="postData.saleoverdueDiscountRate" placeholder=""> <span slot="suffix" style="padding-right: 6px; font-size: 14px"> % </span></el-input>
            </el-form-item>
            <span class="label-title">费用占比情况</span>
            <el-form-item prop="costproportionSale" label="销售费用：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionSale" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionManage" label="管理费用：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionManage" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionHousing" label="房屋折旧、设备租赁：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionHousing" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionPeople" label="人工成本：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionPeople" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionCommission" label="提成：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionCommission" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionConsumables" label="试剂耗材：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.aim" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="costproportionConsumablesNo" label="试剂耗材（不含疫苗）：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.costproportionConsumablesNo" placeholder=""></el-input>
            </el-form-item>
          </div>
          <div style="padding: 12px; overflow: auto">
            <span class="label-title">人均情况</span>
            <el-form-item prop="peopleNumber" label="月平均人数（含兼职）：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.peopleNumber" placeholder=""></el-input>
            </el-form-item>
            <span class="label-title">客户情况</span>
            <el-form-item prop="customerNew" label="新单金额：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerNew" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerLose" label="丢单金额：">
              <el-input style="width: 416px" v-model="postData.customerLose" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerLoseAccounting" label="丢单金额(个检记账)：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerLoseAccounting" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerBig50" label="大单金额(50万以上)：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerBig50" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerBig50Count" label="50万以上大单个数：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerBig50Count" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerBig20" label="大单金额(20万-50万)：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerBig20" placeholder=""></el-input>
            </el-form-item>
            <el-form-item oninput="value=value.replace(/[^0-9]/g,'')" prop="customerBig20Count" label="20万-50万大单个数：">
              <el-input style="width: 416px" v-model="postData.customerBig20Count" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customerAccounting" label="个检记账：">
              <el-input oninput="value=value.replace(/[^0-9]/g,'')" style="width: 416px" v-model="postData.customerAccounting" placeholder=""></el-input>
            </el-form-item>
            <el-form-item prop="customer_newCount" label="新单人数：">
              <el-input-number style="width: 416px" v-model="postData.customer_newCount" placeholder="" controls-position="right" :min="0" precision="0"></el-input-number>
            </el-form-item>
          </div>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">提交</el-button>
          <el-button type="primary" @click="reset" plain>重 置</el-button>
        </div>
        <div style="height: 2px"></div>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getReportData, saveReportData, updateReportData, lastData } from '@/api/finance/financial_statements/finance_submit.js'
import cookie from 'js-cookie'
export default {
  name: 'detail',
  data() {
    return {
      queryData: {
        current: 1,
        size: 10,
        cid: '',
        choosedate: '',
      },
      postData: {
        time: undefined,
      },
    }
  },
  created() {
    this.queryData.cid = cookie.get('cid')
  },
  methods: {
    //查询
    handleQuery() {
      this.queryData.choosedate = this.queryData.choosedate + '-01 00:00:00'
      this.reset()
      this.getList()
    },
    getList() {
      if (!this.queryData.choosedate) {
        return
      } else {
        getReportData(this.queryData).then((response) => {
          if (response.data.records.length >= 1) {
            this.postData = response.data.records[0]
          }
          lastData(this.queryData).then((response) => {
            this.postData.outputvalueGoals = response.data.outputvalueGoals
            this.postData.profitsituationGoals = response.data.profitsituationGoals
          })
        })
      }
    },
    handleSubmit() {
      this.$refs['postData'].validate((valid) => {
        if (valid) {
          if (this.postData.id != null) {
            saveReportData(this.postData).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            updateReportData(this.postData).then((response) => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
    handleSelectionChange(selection) {},
    reset() {
      this.resetForm('postData')
      this.getList()
    },
  },
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 15px;

  .main-box {
    height: 93%;
    width: 100%;
    padding: 0;
    border: 1px solid #d4d6d9;
    background-color: #fbfbfb;

    .post-box {
      height: 93%;
      width: 100%;
      padding: 0;
      overflow: auto;
    }
  }
}

.dialog-footer {
  position: absolute;
  bottom: 8px;
  right: 16px;
  float: right;
}

.label-title {
  display: block;
  font-family: PingFang SC;
  font-size: 16px;
  font-weight: 600;
  line-height: 22px;
  letter-spacing: 0.04em;
  text-align: left;
  margin-bottom: 18px;
}
</style>
<style lang="scss" scoped>
.ziti .el-form-item__label {
  font-size: 10px;
}
</style>
