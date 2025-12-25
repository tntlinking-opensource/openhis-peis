<!-- 客户消息-处理 麦沃德科技 开发人: 暴雨 -->
<template>
    <div class="add-container">
        <el-dialog title="新增"  :visible.sync="open"  append-to-body :close-on-click-modal="false">
            <el-form size="small" :inline="true" label-width="120px" :model="form"  ref="headlerForm">
                <el-form-item label="体检号" prop="khdwmc">
                    <el-input style="width:300px;" v-model="form.khdwmc" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="姓名"  prop="khlxdh">
                    <el-input  style="width:300px;" v-model="form.khlxdh" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="sctjksrq">
                    <el-input  style="width:300px;" v-model="form.sctjksrq" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="类型选择"  prop="bcgtfs">
                  <el-select placeholder="请选择本次沟通方式" style="width:300px;" v-model="form.bcgtfs">
                    <el-option :key="0" label="电话" :value="0" />
                    <el-option :key="1" label="QQ" :value="1" />
                    <el-option :key="2" label="面对面" :value="2" />
                    <el-option :key="3" label="其它" :value="3" />
                  </el-select>
                </el-form-item>
                <el-form-item label="通知时间"  prop="bcgtrq">
                  <el-date-picker v-model="form.bcgtrq"  style="width: 300px;" value-format="yyyy-MM-dd "
                                  type="date" placeholder="请选择本次沟通日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="内容"  prop="bz">
                    <el-input type="textarea" placeholder="请输入" style="width:740px;" :rows="3" v-model="form.bz"></el-input>
                </el-form-item>
            </el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="onSave" type="primary">保存</el-button>
				<el-button @click="onReturn()" type="primary" plain>重置</el-button>
				<el-button @click="onCancel()">取消</el-button>
			</div>
        </el-dialog>

    </div>
</template>

<script>
  import { saveKhgtData } from '@/api/customer/inspection_tracking'

    export default{
        data(){
            return{
                open:false,
                form:{
                  khdwmc : undefined,
                  khlxdh : undefined,
                  sctjksrq : undefined,
                  gtjg : undefined,
                  bcgtrq : undefined,
                  xcgtrq : undefined,
                  bcgtfs : undefined,
                  xsjl : undefined,
                  bz : undefined,
                  id : undefined
                },

            }
        },
        methods:{
            handleWindow(){

              this.open = true;
            },
            onSave(){
             if(this.form.xcgtrq==undefined){
               this.$modal.msgError("下次沟通日期不能为空!");
               return
             }
              if(this.form.bcgtrq==undefined){
                this.$modal.msgError("沟通日期不能为空!");
                return
              }
              saveKhgtData(this.form).then(response => {
                this.$modal.msgSuccess("保存成功!");
                this.$emit("getList")
                this.onCancel()
              });
            },
            onReturn(){
                this.form = {
                  khdwmc : undefined,
                  khlxdh : undefined,
                  sctjksrq : undefined,
                  gtjg : undefined,
                  bcgtrq : undefined,
                  xcgtrq : undefined,
                  bcgtfs : undefined,
                  xsjl : undefined,
                  bz : undefined,
                  id : undefined
                };
            },
            onCancel(){
               this.onReturn()
               this.open = false;
            }
        }
    }
</script>
