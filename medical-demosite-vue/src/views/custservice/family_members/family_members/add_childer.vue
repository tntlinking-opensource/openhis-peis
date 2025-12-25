<!-- 家庭会员-家庭卡办理-主持卡人信息添加 麦沃德科技 开发人:清风 -->
<template>
    <!-- <div class="add-container"> -->
        <el-dialog title="新增主持卡人信息" :visible.sync="open" width="856px" :close-on-click-modal="false" append-to-body>
            <el-form size="small" :inline="true" label-width="80px" :model="form" :rules="rules" >
                <el-form-item label="身份证号" style="margin-right:32px;" prop="idcardno">
                    <el-input style="width:308px; height: 40px;" placeholder="请输入身份证号" v-model="form.idcardno"  @blur="checkId"></el-input>
                </el-form-item>
                <el-form-item label="姓名" style="margin-right:0;">
                    <el-input style="width:308px; height: 40px;" placeholder="请输入姓名"  v-model="form.patientname"></el-input>
                </el-form-item>
                <el-form-item label="性别" style="margin-right:32px;">
                  <el-select style="width:308px; height: 40px;" placeholder="请选择"  v-model="form.idSex">
                    <el-option :key="0" label="男" :value="0" />
                    <el-option :key="1" label="女" :value="1" />
                  </el-select>
                </el-form-item>
                <el-form-item label="单位名称" style="margin-right:0;">
                    <input-select :selectData="UnitData" :selectWidth="308" @change="unitChange"></input-select>
                </el-form-item>
                <el-form-item label="电话" style="margin-right:32px;" prop="phone">
                    <el-input style="width:308px; height: 40px;" placeholder="请输入电话"  v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="生日" style="margin-right:0;" >
                    <el-date-picker style="width:308px; height: 40px;"   placeholder="请选择日期"  v-model="form.birthdate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                </el-form-item>
                <el-form-item label="分中心" style="margin-right:32px;">
                    <el-select style="width:308px; height: 40px;" placeholder="请选择"  v-model="form.fzx">
                      <el-option v-for="item in idExamsuite" :key="item.id" :value="item.id" :label="item.fzx"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="会员级别" style="margin-right:0;">
                    <el-select style="width:308px; height: 40px;" placeholder="请选择"  v-model="form.memberlevel">
                      <el-option :key="1" label="普通会员" :value="1" />
                      <el-option :key="2" label="VIP会员" :value="2" />
                      <el-option :key="3" label="VVIP会员" :value="3" />
                    </el-select>
                </el-form-item>
                <el-form-item label="客户经理" style="margin-right:32px;">
                    <input-select :selectData="labTypeData" :selectWidth="308" @change="labTypeChange"></input-select>
                </el-form-item>
                <el-form-item label="备注" style="margin-right:auto;">
                    <el-input type="textarea" placeholder="请输入内容" style="width:728px"  v-model="form.note" :rows="4"></el-input>
                </el-form-item>
            </el-form>

			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="onOk">保存</el-button>
				<el-button @click="onCancel">取消</el-button>
			</div>
        </el-dialog>
    <!-- </div> -->
</template>

<script>
  import { checkIdcardno, getBranchData } from '@/api/custservice/family_members/family_members/family_members'

    export default{
        data(){
            return{
                open:false,
                form:{
                    idcardno:undefined,
                    patientname:undefined,
                    idSex:undefined,
                    dw:undefined,
                    phone:undefined,
                    birthdate:undefined,
                    fzx:undefined,
                    memberlevel:undefined,
                    khjl:undefined,
                    note:undefined,
                    cardno:undefined
                },
                // 获取客户经理
                labTypeData: {
                  placeholder: '请输入输入码选择',
                  key: '输入码',//第一列标题
                  value: '名称',//第二列标题
                  url: '/report/healthGetReport/getAllUserData',//请求连接
                  bindValue: '', //初始值(必传)
                  firstName: 'inputCode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
                  secondName: 'username',//接口返回值对应第二列的参数名(不传默认为'name')
                  queryData:'key',
                },
                // 获取单位名称
                UnitData: {
                  placeholder: '请输入输入码选择',
                  key: '输入码',//第一列标题
                  value: '名称',//第二列标题
                  url: '/data/dwHarm/getAllCompany',//请求连接
                  bindValue: '', //初始值(必传)
                  firstName: 'khdwsrm',//接口返回值对应第一列的参数名(不传默认为'inputCode')
                  secondName: 'khdwmc',//接口返回值对应第二列的参数名(不传默认为'name')
                  queryData:'key',
                },
                //获取分中心
                idExamsuite:{},
                //校验
                rules: {
                  phone: [
                    { required: true, message: '手机号码不能为空', trigger: 'change' },
                    {
                      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                      message: '请输入正确格式的手机号码',
                      trigger: 'change'
                    }
                  ],
                  idcardno: [
                    { required: true, message: '身份证号不能为空', trigger: 'change' },
                    {
                      pattern: /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
                      message: '请输入正确格式的身份证号',
                      trigger: 'change'
                    }
                  ]
                }
            }
        },
        methods:{
            addWindow(){
                this.open = true;
                this.getfzx()
            },
            onOk(){
            
                this.$emit("closedialog",this.form)
                this.onCancel()
            },
            onCancel(){
                this.open = false;
                this.onreset()
            },
            onreset(){
                this.form.idcardno=undefined,
                this.form.patientname=undefined,
                this.form.idSex=undefined,
                this.form.dw=undefined,
                this.form.phone=undefined,
                this.form.birthdate=undefined,
                this.form.fzx=undefined,
                this.form.memberlevel=undefined,
                this.form.khjl=undefined,
                this.form.note=undefined,
                this.form.cardno=undefined
            },
            //检查是否有重复的身份证号
            checkId(){
              this.form.cardno=this.form.idcardno
              checkIdcardno(this.form).then(response => {
              }) .catch(() => {
                this.onreset()
              });
            },
            // 选择客户经理
            labTypeChange(value) {
              this.queryParams.idLabtype = value.id
              this.labTypeData.bindValue = value.name
            },
            // 选择单位
            unitChange(value) {
              this.form.dw = value.khdwmc
            },
            //获取分中心
            getfzx(){
              getBranchData().then(response => {
                this.idExamsuite=response.data
              })
            }
        }
    }
</script>
