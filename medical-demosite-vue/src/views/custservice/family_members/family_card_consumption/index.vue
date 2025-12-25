<!-- 家庭卡消费 麦沃德科技 开发人:清风 / 暴雨-->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" label-width="74px" width="100%" :model="search" style="text-align:center;">
              <el-form-item label="家庭卡号" style="margin-right:16px;">
                <el-input placeholder="请输入家庭卡号" style="width:230px;" v-model="search.cardno" clearable
                          @keyup.enter.native="loadings"
                ></el-input>
              </el-form-item>
            <el-form-item label="身份证号" style="margin-right:16px;">
                <el-input placeholder="请输入身份证号" style="width:230px;" v-model="search.idcardno" clearable @keyup.enter.native="loadings"></el-input>
            </el-form-item>
            <el-form-item label="电话" label-widtrh="50px" style="margin-right:16px;">
                <el-input placeholder="请输入电话" style="width:230px;" v-model="search.phone" clearable @keyup.enter.native="loadings"></el-input>
            </el-form-item>
          <el-form-item style="margin-right:0;">
            <el-button type="primary" @click="loadings" icon="el-icon-loading">加载</el-button>
            <el-button @click="resets" icon="el-icon-refresh">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 数据表单 -->
        <el-form size="small" :inline="true" label-width="100px" width="100%" style="text-align:center;">
                <el-form-item class="setTag" id="setTag">
                  <el-tag style="width:100%;font-size: 16px;font-weight: bold">家庭卡消费</el-tag>
                </el-form-item>
                <el-form-item label="家庭卡号" style="margin-right:32px;">
                    <el-input style="width:500px;"  v-model="form.cardNo" clearable :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="家庭卡密码" style="margin-right:0;">
                    <el-input style="width:500px;"  v-model="form.password" clearable show-password></el-input>
                </el-form-item>
                <el-form-item label="消费金额" style="margin-right:32px;" id="setNumber">
                    <el-input-number style="width:500px;" controls-position="right" :min="0" :max="9999999" placeholder="0" v-model="form.money" clearable></el-input-number>
                </el-form-item>
                <el-form-item label="消费积分" style="margin-right:0;" id="setNumber">
                    <el-input-number style="width:500px;" controls-position="right" :min="0" :max="9999999" placeholder="0" v-model="form.jf" clearable></el-input-number>
                </el-form-item>
                <el-form-item label="剩余金额" style="margin-right:32px;">
                    <el-input style="width:500px;"  v-model="form.balanceMoney" clearable :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="剩余积分" style="margin-right:0;">
                    <el-input style="width:500px;"  v-model="form.balanceJf" clearable :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="卡说明" style="margin-right:0;" id="setTextarea">
                    <el-input type="textarea" style="width:1132px;height: 100px;" v-model="form.cardState" clearable :readonly="true" placeholder="当前卡暂无说明"></el-input>
                </el-form-item>
                <el-form-item label="消费类型" style="margin-right:0; width: 1232px; text-align: left;">
                    <el-select style="width:500px; " placeholder="请选择" v-model="form.consumeType" clearable>
                          <el-option :key="0" label="体检" :value="0" />
                          <el-option :key="1" label="药房" :value="1" />
                          <el-option :key="2" label="口腔科" :value="2" />
                          <el-option :key="3" label="眼镜店" :value="3" />
                          <el-option :key="4" label="合作" :value="4" />
                          <el-option :key="5" label="保健品" :value="5" />
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" style="margin-right:0;" id="setTextarea">
                    <el-input type="textarea" style="width:1132px;height: 100px;" placeholder="请输入备注" v-model="form.note" clearable></el-input>
                </el-form-item>
                <el-form-item class="centerUse">
                    <el-button type="primary" @click="saveCharge()" style="width:160px;height: 40px;font-size: 16px;font-weight: bold"
                    v-hasPermi="['custservice:familyCardConsumption:charge']">消费</el-button>
                </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import { getListData , saveConsumex , getLoginName } from '@/api/custservice/family_members/family_card_consumption/family_card_consumption'

    export default{
        data(){
            return{
                search:{
                    current:1,//初始页数
                    size:10,//页值
                    cardno:undefined,//家庭卡号
                    idcardno:undefined,//身份证号
                    phone:undefined,//手机号
                },
                form:{
                    cardNo:undefined,
                    password:undefined,
                    chargeUsername:undefined,
                    money:undefined,
                    jf:undefined,
                    balanceMoney:undefined,
                    balanceJf:undefined,
                    cardState:undefined,
                    consumeType:undefined,
                    note:undefined,
                    type:undefined
                },
                forms:{
                  cardNo:undefined,
                  password:undefined,
                  chargeUsername:undefined,
                  money:undefined,
                  jf:undefined,
                  consumetype:undefined,
                  note:undefined,
                  type:1
                },
            }
        },
        created() {
          this.getlogin()
        },
      methods:{
            //消费按钮
            saveCharge(){
              if(this.form.consumeType == undefined){
                  this.$modal.msg("请先选择消费类型!");
              }else if (this.form.cardNo == undefined && this.form.password == undefined){
                  this.$modal.msgError("请先加载家庭卡信息后进行消费操作!");
              }else{
                  this.$confirm('确定要消费吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(()=>{
                      this.forms.cardNo=this.form.cardNo,
                      this.forms.password=this.form.password,
                      this.forms.money=this.form.money,
                      this.forms.jf=this.form.jf,
                      this.forms.consumetype=this.form.consumeType.toString(),
                      this.forms.note=this.form.note,
                      saveConsumex(this.forms).then(response => {
                        this.resets()
                        this.$message({
                          type: 'success',
                          message: '家庭卡消费成功!'
                        });
                      });
                  }).catch(() => {
                    this.$message({
                      type: 'info',
                      message: '家庭卡消费失败!'
                    });
                  });
              }
            },
            //加载信息
            loadings(){
                if (this.search.cardno == undefined && this.search.idcardno == undefined && this.search.phone == undefined){
                  this.$modal.msgError("请输入家庭卡号后加载家庭卡信息!");
                  // this.$modal.msgError("请至少输入家庭卡号、身份证号、电话号码中的一个数据后加载!");
                }else{
                  getListData(this.search).then(response => {
                    if( response.data != undefined){
                      this.form = response.data;
                      }else{
                      this.$modal.msgError("没有找到匹配的家庭卡!");
                    }
                  });
                }
            },
            //重置
            resets(){
                this.search.phone=undefined,
                this.search.idcardno=undefined,
                this.search.cardno=undefined,
                this.form.cardNo=undefined,
                this.form.password=undefined,
                this.form.chargeUsername=undefined,
                this.form.money=undefined,
                this.form.jf=undefined,
                this.form.balanceMoney=undefined,
                this.form.balanceJf=undefined,
                this.form.cardState=undefined,
                this.form.consumeType=undefined,
                this.form.note=undefined,
                this.form.type=undefined
            },
            //获取登录用户姓名
            getlogin(){
              getLoginName().then(response => {
                this.forms.chargeUsername = response.data;
              });
            },
        }
    }
</script>

<style scoped>
.setTag{
    display:block;
    width: 100%;
}
#setTag /deep/ .el-form-item__content{
    width: 100%;
}
#setTextarea /deep/ .el-textarea__inner{
    height: 100px;
}
.centerUse{
    width: 100%;
    text-align: center;
    display: block;
    margin-right:32px;
}
#setNumber /deep/ .el-input-number .el-input__inner{
  text-align: left;
}
</style>
