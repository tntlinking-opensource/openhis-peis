<!-- 家庭会员-编辑 麦沃德科技 开发人:清风/暴雨 -->
<template>
    <div class="add-container">
        <el-dialog title="编辑" :visible.sync="open" width="856px" :close-on-click-modal="false">
          <el-form size="small" :inline="true" label-width="80px" :model="form">
            <el-form-item label="姓名" style="margin-right:32px;">
              <el-input style="width:308px; height: 40px;" placeholder="请输入姓名" v-model="form.patientname"></el-input>
            </el-form-item>
            <el-form-item label="性别" style="margin-right:0;">
              <el-select style="width:308px; height: 40px;" placeholder="请选择" v-model="form.sex">
                <el-option key="0" label="男" value="0"/>
                <el-option key="1" label="女" value="1"/>
              </el-select>
            </el-form-item>
            <el-form-item label="单位名称" style="margin-right:32px;">
              <el-input style="width:308px;" placeholder="请输入单位名称" v-model="form.dw"></el-input>
            </el-form-item>
                <el-form-item label="电话" style="margin-right:0;">
                    <el-input style="width:308px;" placeholder="请输入电话"  v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="生日" style="margin-right:32px;">
                    <el-date-picker style="width:308px;" placeholder="请选择日期" v-model="form.birthdate"></el-date-picker>
                </el-form-item>
                <el-form-item label="分中心" style="margin-right:0;" >
                  <el-input style="width:308px;" v-model="form.fzx"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" style="margin-right:32px;">
                  <el-input style="width:308px;" placeholder="请输入电话" v-model="form.idcardno"></el-input>
                </el-form-item>
                <el-form-item label="客户经理" style="margin-right:0;">
                    <!-- <el-input id="cardState" placeholder="请输入输入码或用户名" style="width:308px;"  v-model="form.khjl"></el-input> -->
                    <input-select :selectData="departData" selectWidth="308" @change="departDataChange"></input-select>
                </el-form-item>
                <el-form-item label="会员级别" style="margin-right:32px;">
                  <el-select id="cardState" placeholder="请选择" style="width:308px;" v-model="form.memberlevel">
                    <el-option label="普通会员" value="1"></el-option>
                    <el-option label="VIP会员" value="2"></el-option>
                    <el-option label="VVIP会员" value="3"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="复查额度" style="margin-right:0;" id="setNumber">
                    <el-input-number id="cardState" controls-position="right" placeholder="0" :min="0" :max="999999" style="width:308px; text-align: left;"  v-model="form.recheckMoney"></el-input-number>
                </el-form-item>
                <el-form-item label="套餐" style="margin-right:32px;">
                  <input-select ref="inputSelect" :selectData="revPersonIdData" clearable selectWidth="308"
                                @change="issueIdChange"
                  ></input-select>
                </el-form-item>
            <el-form-item label="体检卡" style="margin-right:0;">
              <el-select id="cardState" placeholder="请选择要绑定的体检卡" style="width:308px;" v-model="form.cards">
                <el-option v-for="item in this.cardss" :label="item.cardNo" :key="item.id" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
                <el-form-item label="备注" style="margin-right:auto;">
                  <el-input type="textarea" id="note" placeholder="请输入内容" style="width:728px;" v-model="form.note"
                            :rows="5"
                  ></el-input>
                </el-form-item>
            </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveCharge">保存</el-button>
            <el-button type="primary" plain @click="saveCharge">保存</el-button>
            <el-button @click="onCancel()">取消</el-button>
          </div>
        </el-dialog>
    </div>
</template>

<script>
  import {
    getdetails,
    getMedicalCardAutoComData, saveOrUpdate
  } from '@/api/custservice/family_members/family_members/family_members'

  export default {
    data() {
      return {
        open: false,
        form: {
          id: undefined,
          patientname: undefined,
          idSex: undefined,
          dw: undefined,
          phone: undefined,
          birthdate: undefined,
          fzx: undefined,
          patientcardno: undefined,
          khjl: undefined,
          memberlevel: undefined,
          recheckMoney: undefined,
          tcId: undefined,
          cards: undefined,
          note: undefined,
          idcardno: undefined

        },
        ids:0,
        //体检卡搜索
        cardss: [],
        // 客户经理查询
        departData: {
          placeholder: '请输入输入码选择',
          key: '输入码',//第一列标题
          value: '客户经理',//第二列标题
          url: '/report/healthGetReport/getAllUserData',//请求连接
          bindValue: '',
          firstName: 'inputCode',
          secondName: 'username',
          queryData: 'key'
        },
        revPersonIdData: {
          placeholder: '请输入输入码选择',
          key: '输入码',//第一列标题
          value: '套餐名称',//第二列标题
          url: '/member/familyMember/getAutoCompleteData',//请求连接
          bindValue: '',
          queryData: 'key',
          firstName: 'tjtcsrm',
          secondName: 'tjtcjc'
        }
      }
        },
        methods:{
          //开启
            addWindow(id){
              this.ids=id.toString()
              this.open = true
              this.getComData()
              getdetails(this.ids).then(response => {
                this.form = response.data.peispatientarchive
              })
            },
          //保存
          saveCharge() {
            this.form.idcardno = this.form.patientcardno
            saveOrUpdate(this.form).then(response => {
              this.$modal.msgSuccess('编辑成功')
              this.onCancel()
              this.$emit("getList")
            })
          },
          //关闭
          onCancel() {
            this.open = false
          },
          //套餐选项
          issueIdChange(value) {
            this.queryParams.tcId = value.id
          },
          //客户经理选项
          departDataChange(value) {
            this.queryParams.khjl = value.id
          },
          //体检卡搜索
          getComData() {
            getMedicalCardAutoComData().then(response => {
              this.cardss = response.data
            })
          }
        }
    }
</script>

<style scoped>
#cardState /deep/ .el-textarea__inner {
    min-height: 100px !important;
}
#note /deep/ .el-textarea__inner {
    min-height: 100px !important;
}
#setNumber /deep/ .el-input-number .el-input__inner{
  text-align: left;
}
</style>
