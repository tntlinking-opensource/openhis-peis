<template>
    <div class="add-container">
        <el-dialog title="查看" class="add-items" :visible.sync="open" width="488px" height="auto" append-to-body :close-on-click-modal="false">
            <el-form size="small" :inline="true" label-width="140px" >
                <el-form-item label="客户单位名称" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.khdwmc" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户联系电话" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.khlxdh" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="上次体检开始日期" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.sctjksrq" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="沟通结果" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.gtjg" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="沟通日期" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.gtrq" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="下次沟通日期" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.xcgtrq" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="本次沟通方式" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.bcgtfs" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="销售经理" style="margin-right:0;">
                    <el-input placeholder="暂无数据" style="width:300px;" v-model="form.xsjl" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="备注" style="margin-right:0;">
                    <el-input type="textarea" placeholder="暂无数据" style="width:300px;" v-model="form.bz" :readonly="true"></el-input>
                </el-form-item>
            </el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="onCancel()">取消</el-button>
			</div>
        </el-dialog>

    </div>
</template>

<script>
  import { listPrinttype, unicate } from '@/api/customer/tracking_record'

    export default{
        data(){
            return{
                open:false,
                form:{
                    khdwmc:"",
                    khlxdh:"",
                    sctjksrq:"",
                    gtjg:"",
                    gtrq:"",
                    xcgtrq:"",
                    bcgtfs:"",
                    xsjl:"",
                    bz:""
                }
            }
        },
        methods:{
            queryWindow(id){
              unicate(id).then(response => {
                this.form = response.data;
              });
              this.open = true;
            },
            onCancel(){
                this.form = {
                        khdwmc:"",
                        khlxdh:"",
                        sctjksrq:"",
                        gtjg:"",
                        gtrq:"",
                        xcgtrq:"",
                        bcgtfs:"",
                        xsjl:"",
                        bz:""
                    };
                this.open = false;
            }
        }
    }
</script>
