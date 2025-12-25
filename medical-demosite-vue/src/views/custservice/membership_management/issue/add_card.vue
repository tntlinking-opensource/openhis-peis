<template>
    <div class="add-container">
        <el-dialog :title="title" :visible.sync="open" width="820px" append-to-body :close-on-click-modal="false">
            <el-form label-width="80px" ref="form" :rules="rule" :model="form" :inline="true" hide-required-asterisk>
                <el-form-item label="卡类型" prop="typeId" style="margin-right:32px;">
                    <input-select :selectData="typeData" :selectWidth='260' :queryParams="params"
                        @change="typeChange"></input-select>
                </el-form-item>
                <el-form-item label="前缀" prop="cardPrefix" label-width="110px" style="margin-right:0;">
                    <el-input placeholder="" :readonly="true" style="width:260px;" v-model="form.cardPrefix"></el-input>
                </el-form-item>
                <el-form-item label="卡标识" prop="cardLogo" style="margin-right:32px;">
                    <el-input placeholder="" :readonly="true" style="width:260px;" v-model="form.cardLogo"></el-input>
                </el-form-item>
                <el-form-item label="有效期" prop="validity" label-width="110px" style="margin-right:32px;">
                    <el-date-picker :clearable="false" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间" style="width:260px;" v-model="form.validity"></el-date-picker>
                </el-form-item>
                <el-form-item label="积分" prop="balanceJf" style="margin-right:32px;">
                    <el-input-number style="width:260px;" placeholder="0" controls-position="right" :min="0" :max="100000"
                        v-model="form.balanceJf"></el-input-number>
                </el-form-item>
                <el-form-item label="现有最大卡号" label-width="110px" prop="zdkh" style="margin-right:0;">
                    <el-input placeholder="" :readonly="true" style="width:260px;" v-model="form.zdkh"></el-input>
                </el-form-item>

                <el-form-item label="起始卡号" prop="qskh" style="margin-right:32px;">
                    <el-input placeholder="" style="width:260px;" v-model="form.qskh"></el-input>
                </el-form-item>
                <el-form-item label="发卡数量" label-width="110px" prop="sl" style="margin-right:0;">
                    <el-input-number  :precision="0" style="width:260px;" placeholder="0" controls-position="right" :min="1" :max="10"
                        v-model="form.sl"></el-input-number>
                </el-form-item>
                <el-form-item label="领取人" prop="getterId" style="margin-right:32px;">
                    <input-select step-strictly :selectData="khjldata" style="width:260px;" @change="khjldataChange"
                        v-model="form.getterId"></input-select>
                </el-form-item>
                <el-form-item label="领取时间" label-width="110px" prop="getTime" style="margin-right:32px;">
                    <el-date-picker :clearable='false' type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间" style="width:260px;" v-model="form.getTime"></el-date-picker>
                </el-form-item>
                <el-form-item v-if="this.form.typeId==60" label="金额">
                    <el-input-number v-model="form.balanceMoney" controls-position="right" clearable style="width: 260px"  :min="1" :max="999999"></el-input-number>
                </el-form-item>
                <el-form-item label="卡说明" prop="cardState" style="margin-right:0;">
                    <el-input type="textarea" :readonly='true' resize="none" rows="3" style="width:662px;" placeholder="请输入"
                        v-model="form.cardState"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="memo" style="margin-right:0;">
                    <el-input type="textarea" resize="none" rows="3" style="width:662px;" placeholder="请输入"
                        v-model="form.memo"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="beforeOk">保存</el-button>
                <el-button type="primary" plain @click="reset">重置</el-button>
                <el-button @click="oncancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { getCardDetail, saveCard } from '../../../../api/custservice/membership_management/issue'
export default {
    data() {
        return {

            // 科室名称
            typeData: {
                placeholder: '请输入输入码选择',
                key: '卡ID',
                value: '卡类型',
                url: '/finance/sendCard/getTypeData',
                bindValue: '', //初始值(必传)
                firstName: 'id',
                secondName: 'text'
            },

            //卡类型参数
            params: {
                type: 1
            },
            title: '会员卡发放',
            //打开
            open: false,
            form: {
            },
            id: undefined,
            readonly: true,
            //规则
            rule: {
                typeId: [{ required: true, message: "卡类型不能为空", trigger: "change" }],
                qskh: [{ required: true, message: "起始卡号不能为空", trigger: "change" }],
                getterId: [{ required: true, message: "领取人不能为空", trigger: "change" }],
                getTime: [{ required: true, message: "领取时间不能为空", trigger: "change" }],
            },
            cardData: {
                typeId: undefined,
            },
            //查询更改卡信息参数
            query: {
                typeId: undefined
            },
            memberlevel: [
                {

                },
            ],
            khjldata: {
                placeholder: '',
                key: '部门',
                value: '姓名',
                url: '/finance/sendCard/getLqrData',
                bindValue: '', //初始值(必传)
                firstName: 'inputCode',
                secondName: 'occupationSummary'
            }
        }
    },
    watch: {

    },
    methods: {
        //输入框内容发生变化
        typeChange(value) {
            this.form.balanceMoney=undefined
       
            this.form.typeId = value.id
            this.typeData.bindValue = value.text;
            this.id = value.id
            if(this.form.typeId==60){
                this.form.balanceMoney=1
            }
            this.form.sl=1
           
            //给查询卡信息的参数辅助
            this.query.typeId = value.id
            this.getCard()
        },
        //获取卡信息
        getCard() {
            getCardDetail(this.query).then(response => {
                this.form.cardLogo = response.data.cardLogo
                this.form.cardPrefix = response.data.cardPrefix
                this.form.cardState = response.data.cardState
                this.form.qskh = response.data.qskh
                this.form.type = response.data.type
                this.form.zdkh = response.data.zdkh
            })
        },
        //打开弹窗
        openDia() {
            this.open = true
            this.reset()
        },
        //获取信息
        getList() {

        },
        //保存v
        beforeOk() {
            const form = this.form;
            form.typeId = this.id

            this.$refs["form"].validate(valid => {
                if (valid) {
                    saveCard(form).then(response => {
                        this.$modal.msgSuccess("办理成功");
                        this.open = false
                        this.$emit("getList")
                    })
                }
            });
        },
        reset() {
            this.form = {
                typeId: undefined,
                cardPrefix: undefined,
                cardLogo: undefined,
                validity: undefined,
                balanceLimit: undefined,
                zdkh: undefined,
                qskh: undefined,
                sl: undefined,
                getterId: undefined,
                getTime: undefined,
                cardState: undefined,
                memo: undefined,
            }
            this.typeData.bindValue = undefined
            this.resetForm("form");
        },
        //取消按钮自
        oncancel() {
            this.open = false
            this.reset()
        },
   
        ///领取人信息更改
        khjldataChange(value) {
            
            this.form.getterId = value.id
            this.khjldata.bindValue = value.occupationSummary
        },
    }


}
</script>

<style></style>