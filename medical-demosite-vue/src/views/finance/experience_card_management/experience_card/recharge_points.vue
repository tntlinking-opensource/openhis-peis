<!-- 财务管理-体检卡办理-充值 麦沃得科技 开发人:清风 -->
<template>
    <div class="add-container">
        <el-dialog title="添加" class="add-items" :visible.sync="open" width="760px" append-to-body :close-on-click-modal="false">
            <el-form :inline="true" label-width="80px">
                <el-form-item label="会员卡号">
                    <el-input style="width:260px;" v-model="form.patientcardno" placeholder="请输入卡号"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input style="width:260px;" v-model="form.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="剩余积分">
                    <el-input style="width:260px;" v-model="form.balanceJf" placeholder="剩余积分"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input style="width:260px;" v-model="form.patientname" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="充值积分" id="setNumber">
                    <el-input-number style="width:260px;" v-model="form.limit" controls-position="right" placeholder="充值积分"></el-input-number>
                </el-form-item>
                <el-form-item label="卡说明">
                    <el-input type="textarea" style="width:610px;" v-model="form.branchCenter" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" style="width:610px;" v-model="form.memotext" placeholder="请输入"></el-input>
                </el-form-item>
            </el-form>
            <div style="height:300px">
                <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" height="100%">
                    <el-table-column type="selection" align="center"></el-table-column>
                    <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                    <el-table-column prop="cardno" label="体检卡号" align="center"></el-table-column>
                    <el-table-column prop="money" label="付款金额" align="center"></el-table-column>
                    <el-table-column prop="memotext" label="备注" align="center"></el-table-column>
                    <el-table-column prop="balanceLimit" label="可用金额" align="center"></el-table-column>
                </el-table>
            </div>
            
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" >确 定</el-button>
                <el-button >取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data(){
        return{
            open: false,
            loading:false,
            form:{
                patientcardno:"",
                phone:"",
                balanceJf:"",
                patientname:"",
                limit:"",
                branchCenter:"",
                memotext:"",
            },
            tableData:[
                {
                    cardno:"",
                    money:"",
                    memotext:"",
                    balanceLimit:"",
                },
            ]
        }
    },
    methods:{
        rechargePointsWindow(){
            this.open = true;
        }
    }
}
</script>

<style scoped>
    #setNumber /deep/ .el-input__inner{
        text-align: left;
    }
</style>