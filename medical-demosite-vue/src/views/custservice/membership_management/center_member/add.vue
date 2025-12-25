<!-- 中心会员-添加  麦沃德科技 开发人：清风/矢北-->
<template>
    <div class="add-container">
        <el-dialog :title="title" :visible="open"  :show-close=false width="760px" append-to-body
            :close-on-click-modal="false">
            <el-form label-width="80px" ref="form" :rules="rule" :model="form" :inline="true" hide-required-asterisk>
                <el-form-item label="姓名" prop="patientname" style="margin-right:32px;">
                    <el-input placeholder="请输入姓名" style="width:260px;" v-model="form.patientname"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="idSex" style="margin-right:0;">
                    <el-select placeholder="请选择" style="width:260px;" v-model="form.idSex">
                        <el-option label="男" :value="0"></el-option>
                        <el-option label="女" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="单位名称" prop="dw" style="margin-right:32px;">
                    <input-select :selectData="dwData" v-model="form.dw" selectWidth="260"
                        @change="dwDataChange"></input-select>
                </el-form-item>
                <el-form-item label="电话" prop="phone" style="margin-right:0;">
                    <el-input placeholder="请输入电话" style="width:260px;" v-model="form.phone"></el-input>
                </el-form-item>

                <el-form-item label="生日" prop="birthdate" style="margin-right:32px;">
                    <el-date-picker :clearable='false' type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" style="width:260px;"
                        v-model="form.birthdate"></el-date-picker>
                </el-form-item>
                <el-form-item label="分中心" prop="fzx" style="margin-right:0;">
                    <input-select :selectData="fzxData" v-model="form.fzx" selectWidth="260"
                        @change="fzxDataChange"></input-select>
                </el-form-item>

                <el-form-item label="会员卡号" prop="patientcardno" style="margin-right:32px;">
                    <el-input placeholder="请输入卡号" style="width:260px;" v-model="form.patientcardno"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" prop="idcardno" style="margin-right:0;">
                    <el-input placeholder="请输入身份证号" style="width:260px;" v-model="form.idcardno"></el-input>
                </el-form-item>

                <el-form-item label="会员级别" prop="memberlevel" style="margin-right:32px;">
                    <el-select placeholder="请选择" style="width:260px;" v-model="form.memberlevel">
                        <el-option  value="1" label="普通会员"></el-option>
                        <el-option  value="2" label="VIP"></el-option>
                        <el-option  value="3" label="VVIP"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="客户经理" prop="khjl" style="margin-right:0;">
                    <input-select :selectData="khjldata" @change="khjldataChange"></input-select>
                </el-form-item>
                <el-form-item label="套餐" prop="order" style="margin-right:32px;">
                    <!-- <el-input placeholder="请输入套餐输入码" style="width:260px;" v-model="form.tcId"></el-input> -->
                    <input-select :selectData="departData" v-model="form.order" selectWidth="260"
                        @change="departChange"></input-select>
                </el-form-item>
                <el-form-item label="复查额度" prop="recheckMoney" style="margin-right:0;">
                    <el-input-number style="width:260px;" placeholder="0" controls-position="right" :min="0" :max="10"
                        v-model="form.recheckMoney"></el-input-number>
                </el-form-item>

                <el-form-item label="体验卡" prop="cards" style="margin-right:0;">
                    <input-select :selectData="cardData" v-model="form.cards" selectWidth="260"
                        @change="cardsChange"></input-select>
                </el-form-item>
                <el-form-item label="备注" prop="note" style="margin-right:0;" id="setTextarea">
                    <el-input type="textarea" style="width:632px;" placeholder="请输入" v-model="form.note"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="beforeOk">保存</el-button>
                <el-button type="primary" plain @click="reset">重置</el-button>
                <el-button @click="onCancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

import { getDetail, saveData } from '../../../../api/custservice/membership_management/center_member';
export default {
    data() {
        return {
            title: "添加",
            closed: false,
            open: false,
            form: {

            },
            memberlevel: [
                {
                       text:'普通会员',id:1
                }, 
                {
                       text:'VIP',id:2
                },
                {
                       text:'VVIP',id:3
                },
            ],
                // dw名称
                dwData: {
                placeholder: '请输入输入码选择',
                key: '拼音码',
                value: '单位名称',
                url: '/data/dwHarm/getAllCompany',
                bindValue: '', //初始值(必传)
                firstName: 'khdwsrm',
                secondName: 'khdwmc',
                queryData: 'key',
            },
            // fzx名称
            fzxData: {
                placeholder: '请输入输入码选择',
                key: '拼音码',
                value: '分中心',
                url: '/sell/monthtarget/getBranchData',
                bindValue: '', //初始值(必传)
                firstName: 'jm',
                secondName: 'fzx',
                queryData: 'key',
            },
            // 客户经理名称
            khjldata: {
                placeholder: '请输入输入码选择',
                key: '拼音码',
                value: '客户经理',
                url: '/report/healthGetReport/getAllUserData',
                bindValue: '', //初始值(必传)
                firstName: 'inputCode',
                secondName: 'username',
                queryData: 'key',
                
            },
            queryParams: {
                id: undefined
            },
            // 科室列表
            departData: {
                placeholder: '请输入输入码选择',
                key: '套餐ID',//第一列标题 
                value: '套餐名称',//第二列标题 
                url: '/member/member/getAutoCompleteData',//请求连接
                bindValue: '',
                firstName: 'id',
                secondName: 'tjtcjc',
                queryData: 'key',
            },
            // 领卡信息
            cardData: {
                placeholder: '请输入输入码选择',
                key: '输入码',//第一列标题 
                value: '卡号',//第二列标题 
                url: '/member/member/getMedicalCardAutoComData',//请求连接
                bindValue: '',
                firstName: 'tjtcjc',
                secondName: 'cardNo',
                queryData: 'key',
            },
            rule: {

                patientname: [{ required: true, message: "姓名不能为空", trigger: "change" }],
                phone: [{ required: true, message: "手机号不能为空", trigger: "change" }],
                idcardno: [{ required: true, message: "身份证号码不能为空", trigger: "change" }],
                fzx:[{require:true,message:"分中心不能为空", trigger: "change" }],
                idSex: [{ required: true, message: "性别不能为空", trigger: "change" }],
            }
        }
    },
    methods: {
           // 分中心变化选项
           dwDataChange(value) {
        
            this.dwData.bindValue = value.name
            this.form.fzx = value.name
        },

        // 分中心变化选项
        fzxDataChange(value) {
        
            this.fzxData.bindValue = value.name
            this.form.fzx = value.name
        },
        // 客户经理选项
        khjldataChange(value) {

            this.khjldata.bindValue = value.name
            this.form.khjl = value.name
        },
        // 选择科室
        departChange(value) {
     
            this.departData.bindValue = value.tjtcjc
            this.form.order = value.tjtcjc

        },
        // 选择卡领取
        cardsChange(value) {
            
            this.cardData.bindValue = value.cardNo
            this.form.cards = value.cardNo
        },
        addWindow(context, row) {
            this.form = {
                patientname: undefined,
                idSex: undefined,
                dw: undefined,
                phone: undefined,
                birthdate: undefined,
                fzx: undefined,
                patientcardno: undefined,
                idcardno: undefined,
                memberlevel: undefined,
                khjl: undefined,
                recheckMoney: undefined,
                cards: undefined,
                note: undefined,
            }
            this.departData.bindValue = undefined,
                this.cardData.bindValue = undefined
            //套餐清空
            
            this.resetForm("form");
            this.open = true;
            //新增
            if (context == 0) {
                this.title = '新增';
            } else {
                this.queryParams.id = row.id
                //编辑
                this.title = '编辑';
                getDetail(this.queryParams).then(response => {
                    this.form = response.data.peisPatientArchive;
                    this.khjldata.bindValue=response.data.peisPatientArchive.khjl
                    this.dwData.bindValue=response.data.peisPatientArchive.dw
                    this.fzxData.bindValue=response.data.peisPatientArchive.fzx
                })
               
            }
        },
        beforeOk() {
            if(this.form.birthdate)
            {
                this.form.birthdate=this.form.birthdate+" 00:00:00"
            }
            else{
                this.form.birthdate=undefined
            }
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        saveData(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.$emit('getList');
                        });
                    } else {
                        saveData(this.form).then(response => {
                            this.$modal.msgSuccess("添加成功");
                            this.open = false;
                            this.$emit('getList');
                        });
                    }
                }
            });
        },
        reset() {
            this.form = {
                patientname: undefined,
                idSex: undefined,
                dw: undefined,
                phone: undefined,
                birthdate: undefined,
                fzx: undefined,
                patientcardno: undefined,
                idcardno: undefined,
                memberlevel: undefined,
                khjl: undefined,
                recheckMoney: undefined,
                cards: undefined,
                note: undefined,
            }
            this.departData.bindValue = undefined,
                this.cardData.bindValue = undefined
            //套餐清空
            
            this.resetForm("form");
            getDetail(this.queryParams).then(response => {
                    this.form = response.data.peisPatientArchive;
                })
             
            
        },
        onCancel() {
            
            this.open = false;


        },
    }
}
</script>

<style scoped>

</style>