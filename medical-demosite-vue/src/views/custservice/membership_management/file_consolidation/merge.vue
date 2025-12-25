<!-- 档案合并-档案合并 麦沃德科技 开发人:清风 -->
<template>
    <div class="add-container">
        <el-dialog id="dialogBody" title="档案合并" class="add-items" :visible.sync="open" width="400px" height="auto" append-to-body :close-on-click-modal="false">
            <el-form size="small" :inline="true" :model="form" label-width="80px" style="margin-top: 20px;margin-left: 10px">
                <el-form-item label="姓名">
                  <el-input placeholder="请输入姓名" style="width:266px;" v-model="form.patientname" clearable></el-input>
                </el-form-item>
                <el-form-item label="电话">
                  <el-input placeholder="请输入电话" style="width:266px;" v-model="form.phone" clearable></el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                  <el-input placeholder="请输入身份证号" style="width:266px;" v-model="form.idcardno" clearable></el-input>
                </el-form-item>
            </el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="onOk()">保存</el-button>
				<el-button @click="onCancel()">取消</el-button>
			</div>
        </el-dialog>

    </div>
</template>
<script>
  import { merge, saveMerge } from '@/api/custservice/membership_management/file_consolidation/file_consolidation'

   export default{
       data(){
           return{
               idss:"",
               open:false,
               tableData:[
                   {
                       patientcode:"体检号",
                       patientname:"姓名",
                       phone:"电话",
                       idcardno:"身份证号",
                       address:"地址",
                   }
               ],
               form:{
                patientname:"",
                phone:"",
                idcardno:"",
                 ids:""
               }
           }
       },
       methods:{
            mergeWindow(multiple,ids){
                if(multiple){
                      this.form.ids=ids.join(",")
                      this.idss=ids
                      merge(this.form).then(response => {
                        this.form.patientname=response.data.archiveList[0].patientname
                        this.form.phone=response.data.archiveList[0].phone
                        this.form.idcardno=response.data.archiveList[0].idcardno
                        this.open=true;
                    });
                } else {
                    this.$confirm('请选择两条需要合并的档案!', '合并提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(()=>{}).catch(() => {})
                }
            },
            onOk(){
              this.form.ids=this.idss
              saveMerge(this.form).then(response => {
                this.$modal.msgSuccess("合并成功!");
                this.$emit("getList")
                this.open = false;
              });
            },
            onCancel(){
                this.open=false;
            }
       }
   }
</script>

<style scoped>
    #dialogBody /deep/ .el-dialog__body{
        padding: 0;
    }
</style>
