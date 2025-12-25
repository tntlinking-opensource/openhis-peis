<!-- 财务管理-体检卡办理-发卡 麦沃得科技 开发人:清风/矢北 -->
<template>
  <div class="add-container">
    <el-dialog title="发卡" class="add-items" :visible.sync="open" width="1600px" append-to-body :close-on-click-modal="false">
      <div style="display:flex;height:440px ; flex-direction:row;">
        <div style=" max-width: 720px;flex: 1;overflow: auto;">
          <el-form ref="form" :rules="rule" :model="form" :inline="true" label-width="80px">
            <el-form-item label="售卡人" prop="sellName">
              <el-input style="width:260px;" v-model="form.sellName" :readonly="true" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="售卡时间" prop="sellTime">
              <el-input style="width:260px;" v-model="form.sellTime" :readonly="true" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="数量">
              <el-input style="width:260px;" :readonly="true" v-model="form.count"></el-input>
            </el-form-item>
            <el-form-item label="面值" prop="tcPrice">
              <el-input style="width:260px;" :readonly="true" v-model="form.tcPrice"></el-input>
            </el-form-item>
            <el-form-item label="卡类型" prop="typeId">
              <el-input style="width:260px;" :readonly="true" v-model="form.typeId"></el-input>
            </el-form-item>
            <el-form-item label="卡前缀" prop="cardPrefix">
              <el-input style="width:260px;" :readonly="true" v-model="form.cardPrefix"></el-input>
            </el-form-item>
            <el-form-item label="卡标识">
              <el-input style="width:260px;" :readonly="true" placeholder="0" v-model="form.cardLogo"></el-input>
            </el-form-item>
            <el-form-item label="有效期">
              <el-input style="width:260px;" :readonly="true" v-model="form.validity"></el-input>
            </el-form-item>
            <el-form-item label="卡说明" id="setTextarea">
              <el-input type="textarea" :readonly="true" style="width:610px;" placeholder="请输入" v-model="form.cardState"></el-input>
            </el-form-item>
            <el-form-item style="" label="套餐名称">
              <el-input style="width:260px;" :readonly="true" v-model="form.tjtcmc"></el-input>
            </el-form-item>
            <el-form-item label="售价">
              <el-input-number v-model="form.sellPrice" style="width:260px;" controls-position="right" @change="handleChange" :min="0" :max="999999"></el-input-number>
            </el-form-item>
            <el-form-item label="折扣">
              <el-input style="width:260px;" :readonly="true" v-model="form.zk"></el-input>
            </el-form-item>
            <el-form-item label="原价合计" prop="totalPrice">
              <el-input style="width:260px;" :readonly="true" v-model="form.totalPrice"></el-input>
            </el-form-item>
            <el-form-item label="售价合计">
              <el-input style="width:260px;" :readonly="true" v-model="form.totalSellPrice"></el-input>
            </el-form-item>
            <el-form-item label="购卡人" prop="purchaser">
              <el-input style="width:260px;" v-model="form.purchaser"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input style="width:260px;" v-model="form.phone"></el-input>
            </el-form-item>
          </el-form>

        </div>
        <div style="height:440px;flex: 1; border:1px solid rgb(221, 221, 221);">
          <!-- 操作按钮 -->
          <div style="margin: 10px">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-circle-check" size="mini" @click="handleAdd()">增加
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-circle-close" size="mini" @click="returnHanderWindow()">删除
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit-outline" size="mini" @click="editWindow()">会员卡
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit-outline" size="mini" @click="editWindow()">体检卡
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit-outline" size="mini" @click="editWindow()">刷新
                </el-button>
              </el-col>
            </el-row>
          </div>
          <div style="height:420px;">
            <el-table :data="tableData" :border="true" @selection-change="handleSelectionChange" v-loading="loading" :stripe="true" height="90%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaid" label="付款金额" align="center">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.moneyamountpaid" style="width: 120px;" controls-position="right" :min="0" :max="999999"></el-input-number>
                </template>
              </el-table-column>
              <el-table-column prop="idPayway" label="付款方式" align="center">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.idPayway" placeholder="">
                    <el-option v-for="item, index in payWay" :key="index" :label="item.paywayName" :value="item.id"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column prop="cardno" label="卡号" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.cardno" placeholder=""></el-input>
                </template>
              </el-table-column>
              <el-table-column prop="note" label="备注" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.note" placeholder=""></el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
        <el-button type="primary" @click="reset" plain>重 置</el-button>
        <el-button @click="handleCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPayWay, saveData, getDetail } from '@/api/finance/card/card_issuance.js'
export default {
  data() {
    return {
      payWay: [],
      loading: false,
      open: false,
      countMoney: 0,
      //表格数据
      tableData: [],
      //存储序号的数组 序号需要在确定某一行的内容时用到
      ids: [],
      //删除的索引
      index: [],
      //详情id
      queryId: undefined,
      formId: [],
      form: {},
      //规则
      // 表单效验
      rule: {
        phone: [{ required: true, message: "手机号不能为空", trigger: "change" }, { len: 11, message: '长度必须为11字符' }],
        purchaser: [{ required: true, message: "购买人不能为空", trigger: "change" }],
      },
    }
  },
  methods: {
    //获取支付方式
    getPayList() {
      //接口方法调用
      getPayWay().then(response => {
        this.payWay = response.data.records
      })
    },
    //打开页面调用
    sendcardsWindow(formData) {
      this.tableData = []
      this.queryId = formData[0]
      this.getDetail()
      this.open = true;
      this.getPayList()
    },
    //number-input值变化的时候
    handleChange() {
      this.form.totalSellPrice = this.form.sellPrice * this.form.count
      this.form.zk = (this.form.sellPrice / this.form.totalPrice * 10).toFixed(2)
    },
    //获取详情接口
    getDetail() {
      getDetail(this.queryId).then(response => {
        this.form = response.data
        this.form.count = 1
        this.form.totalPrice = 1000
      })
    },
    //增加行数
    handleAdd() {
      var list = {
        moneyamountpaid: 0,
        idPayway: undefined,
        idFeecharger: undefined,
        moneyamountpaiddate: undefined,
        cardno: undefined,
        note: undefined,
        moneyamountpaid: 0,
        isTotalcharge: 0,
        isCharged: 0
      }
      ///id就是序号 从1开始
      this.tableData.push(list)
      for (var i in this.tableData) {
        this.tableData[i].index = i
      }
    },
    // 选中改变
    handleSelectionChange(selection) {
  
      this.ids = selection.map((item) => item.id);
      this.index = selection.map((item) => item.index);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 删除选中
    returnHanderWindow() {
      this.index.forEach((val) => {
        this.tableData.forEach((item, i) => {
          if (item.index == val) {
            this.$delete(this.tableData, i)
          }
        })
      })
      for (var i in this.tableData) {
        this.tableData[i].index = Number(i) + Number(1)
      }
    },
    calPrice() {
    
      this.countMoney = 0
      //计算总价格并且查看收款是否核实
      for (var i in this.tableData) {
        this.countMoney += Number(this.tableData[i].moneyamountpaid)
      }
    },
    addState() {
      for (let i in this.tableData) {
        this.tableData[i].state = 'add'
      }
    },
    //提交按钮
    handleSubmit() {
      if (this.tableData.length < 1) {
        this.$message.warning('至少存在一条收费信息进行保存发卡')
        return;
      } else {
        //ids字段要求内容为数组对其进行重新复制 formId是数组，而queryId是内容
        this.formId.push(this.queryId)
        const form = this.form
        form.ids = this.formId
        form.sellName = this.form.sellId
        let state = 0
        //计算价格
        this.calPrice()
        //增加状态
        this.addState()
        const query = {
          formdata: form,
          griddata: this.tableData
        }
        //当不存在空行让状态等于1
        for (let i in this.tableData) {
          if (!this.tableData[i].idPayway || !this.tableData[i].cardno) {
            this.$message.warning('不能存在未选择支付方式或者卡号的行进行保存')
            state = 0
            return;
          } else {
            state = 1
          }
        }
        //只有不存在空行时进行判断是否内容价格是否相等
        if (state == 1) {
          if (this.countMoney != this.form.sellPrice) {
            this.$message.warning(`请检查收费内容，当前收款${this.countMoney}人民币,实际应收款${this.form.sellPrice}人民币`);
          } else {
            this.$refs["form"].validate(valid => {
              if (valid) {
                if (this.form.id != null) {
                  saveData(query).then(response => {
                    this.$modal.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  });
                } else {
                  saveData(query).then(response => {
                    this.$modal.msgSuccess("添加成功");
                    this.open = false;
                    this.getList();
                  });
                }
              }
            });
          }
        }
      }
    },
    //重置按钮
    reset() {
      this.examList = []
      this.selectList = []
      this.cidList = []
      this.examItems = []
      this.selectIds = []
      this.total = 0
      this.departData = []
      this.labTypeData = []
      this.resetForm("form")
      this.form = {}
    },
    //取消按钮
    handleCancel() {
      this.open = false
      this.reset()
    }
  }
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
  height: 100px;
}
</style>