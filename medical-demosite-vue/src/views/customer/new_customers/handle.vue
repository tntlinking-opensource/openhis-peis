<!-- 新增客户-处理 麦沃德科技 开发人: 暴雨 -->
<template>
    <div class="add-container">
        <el-dialog title="查看" class="add-items" :visible.sync="open"  height="auto" append-to-body :close-on-click-modal="false">
            <el-form size="small" :inline="true" label-width="140px" :model="form"  ref="headlerForm">
                <el-form-item label="客户单位名称" style="margin-right:0;" prop="khdwmc">
                    <el-input  style="width:300px;" v-model="form.khdwmc" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户单位输入码" style="margin-right:0;" prop="khdwsrm">
                    <el-input  style="width:300px;" v-model="form.khdwsrm" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户单位联系人" style="margin-right:0;" prop="khdwlxr">
                    <el-input  style="width:300px;" v-model="form.khdwlxr" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户电话" style="margin-right:0;" prop="khdh">
                    <el-input  style="width:300px;" v-model="form.khdh" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="销售经理" style="margin-right:0;" prop="xsjl">
                    <el-input  style="width:300px;" v-model="form.xsjl" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户单位注册地址" style="margin-right:0;" prop="khdwzcdz">
                    <el-input  style="width:300px;"  v-model="form.khdwzcdz" :readonly="true"></el-input>
                </el-form-item>
            </el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="onCancel()">关闭</el-button>
			</div>
        </el-dialog>

    </div>
</template>

<script>
  import { getListData, info } from '@/api/customer/new_customers'

    export default{
        data(){
            return{
                open:false,
                form:{
                    khdwmc:undefined,
                    khlxdh:undefined,
                    sctjksrq:undefined,
                    gtjg:undefined,
                    bcgtrq:undefined,
                    xcgtrq:undefined,
                    bcgtfs:undefined,
                    xsjl:undefined,
                    bz:undefined,
                    khdwzcdz:undefined,
                    khdwsrm:undefined,
                    khdh:undefined
                },
                arr:{},

            }
        },
        methods:{
            handleWindow(ids){
              info(ids).then(({ data }) => {
                this.form = data
                this.open = true;
              })
            },
            onCancel(){
                this.form = this.arr;
                this.open = false;
            }
        }
    }
</script>
