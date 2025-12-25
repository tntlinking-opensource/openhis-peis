<!-- 财务管理-卡消费明细-充值 麦沃得科技暴雨 -->
<template>
  <div class="add-container">
    <el-dialog title="添加" class="add-items" :visible.sync="open" width="1600px" append-to-body :close-on-click-modal="false">
      <div style="display:flex; flex-direction:row;">
        <div style="height:440px; max-width: 720px;flex: 1;">
          <el-form :inline="true" label-width="80px" style="height:100%;">
            <el-form-item label="读卡">
              <el-select style="width:260px;" v-model="form.sellId">
                <el-option v-for="item in form.sellId" :value="item" :key="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="售卡时间">
              <el-select style="width:260px;" v-model="form.sellTime">
                <el-option v-for="item in form.sellTime" :value="item" :key="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="体检卡号">
              <el-input style="width:260px;" v-model="form.cardNo"></el-input>
            </el-form-item>
            <el-form-item label="面值">
              <el-input style="width:260px;" v-model="form.balanceLimit"></el-input>
            </el-form-item>
            <el-form-item label="卡类型">
              <el-input-number style="width:260px;" :min="0" placeholder="0" controls-position="right" v-model="form.typeId"></el-input-number>
            </el-form-item>
            <el-form-item label="卡前缀">
              <el-input-number style="width:260px;" :min="0" placeholder="0" controls-position="right" v-model="form.cardPrefix"></el-input-number>
            </el-form-item>
            <el-form-item label="卡标识">
              <el-input style="width:260px;" placeholder="0" v-model="form.cardLogo"></el-input>
            </el-form-item>
            <el-form-item label="有效期">
              <el-input style="width:260px;" v-model="form.validity"></el-input>
            </el-form-item>
            <el-form-item label="卡说明" id="setTextarea">
              <el-input type="textarea" style="width:610px;" placeholder="请输入" v-model="form.cardState"></el-input>
            </el-form-item>
            <el-form-item label="备注" id="setTextarea">
              <el-input type="textarea" style="width:610px;" placeholder="请输入" v-model="form.memo"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div style="height:440px;flex: 1; border:1px solid rgb(221, 221, 221);">
          <!-- 操作按钮 -->
          <div style="margin: 10px">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-circle-check" size="mini" @click="handerWindow()">增加
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
            <el-table :data="tableData" :border="true" v-loading="loading" :stripe="true" height="90%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaid" label="付款金额" align="center"></el-table-column>
              <el-table-column prop="idPayway" label="付款方式" align="center"></el-table-column>
              <el-table-column prop="idFeecharger" label="收费员" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaiddate" label="收费时间" align="center"></el-table-column>
              <el-table-column prop="kashou" label="卡号" align="center"></el-table-column>
              <el-table-column prop="note" label="备注" align="center"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">确 定</el-button>
        <el-button type="primary" plain>重 置</el-button>
        <el-button>取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      open: false,
      tableData: [{
        moneyamountpaid: "",
        idPayway: "",
        idFeecharger: "",
        moneyamountpaiddate: "",
        kashou: "",
        note: "",
      }
      ],
      form: {
        sellId: "",
        sellTime: "",
        cardNo: "",
        balanceLimit: "",
        typeId: "",
        cardPrefix: "",
        cardLogo: "",
        validity: "",
        cardState: "",
        memo: "",
      }
    }
  },
  methods: {
    rechargeWindow() {
      this.open = true;
    }
  }
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner {
  height: 100px;
}
</style>
