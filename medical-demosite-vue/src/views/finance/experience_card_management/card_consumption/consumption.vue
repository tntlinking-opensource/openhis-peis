<!-- 财务管理-卡消费明细-消费 麦沃得科技暴雨 -->
<template>
    <div class="add-container">
        <el-dialog title="添加消费金额" class="add-items" :visible.sync="open" width="760px" append-to-body :close-on-click-modal="false">
            <el-form :inline="true" label-width="80px">
                <el-form-item label="体检卡号">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入卡号消费"></el-input>
                </el-form-item>
                <el-form-item label="卡类型" label-width="90px">
                    <el-input style="width:260px;" v-model="form.ad"  placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="可用金额">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="卡标识" label-width="90px">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="有效期">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="分中心" label-width="90px">
                    <el-select style="width:260px;" v-model="form.ad" placeholder="请输入"></el-select>
                </el-form-item>
                <el-form-item label="卡说明" id="setTextarea">
                    <el-input type="textarea" style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="卡备注" id="setTextarea" label-width="90px">
                    <el-input type="textarea" style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="体检号">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="体检者姓名" label-width="90px">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="结算费">
                    <el-input style="width:260px;" v-model="form.ad" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="消费金额" label-width="90px">
                    <el-input-number style="width:260px;" controls-position="right" v-model="form.ad" placeholder="0"></el-input-number>
                </el-form-item>
                <el-form-item label="消费类型">
                    <el-select style="width:260px; margin-right: 300px;" controls-position="right" v-model="form.ad" placeholder="请选择"></el-select>
                </el-form-item>
                <el-form-item label="备注" id="setTextarea">
                    <el-input type="textarea" style="width:620px;" placeholder="请输入"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" >确 定</el-button>
                <el-button type="primary" plain >重 置</el-button>
                <el-button >取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default{
    data(){
        return{
            open:false,
            showSearch:true,
            ids:[],//选中的数组
            single:true,//必选且单选
            multiple:true,//必选
            total:30,//总数
            queryParams:{
                pageNum:1,//初始页数
                pageSize:10,//页值
            },
            form:{

            },
            tableData:[
                {}
            ],
        }
    },
    methods:{
        consumptionWindow(){
            this.open = true;
        }
    }
}
</script>

<style scoped>
#setTextarea /deep/ .el-textarea__inner{
    height: 100px;
}
</style>
