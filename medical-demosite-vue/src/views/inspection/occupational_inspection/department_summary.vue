<!-- 职业总检-查看详情-科室小结 麦沃德科技 开发人:清风 -->
<template>
    <div class="add-container flex-direction-column" style="height: 760px;">
        <div class="table-box">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" :row-style="{height:'100px'}" 
                :cell-style="{height:'100px',padding:'0px'}" @selection-change="handleSelectionChange" height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="ver1" label="科室名称" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver2" label="录入人" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver3" label="医师姓名" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver4" label="审核人" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver5" label="录入时间" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver6" label="审核时间" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column prop="ver7" label="小结" show-overflow-tooltip align="center" ></el-table-column>
                <el-table-column label="重症级别" align="center">
                    <template slot-scope="scope">
                        <div v-if="scope.row.ver8 == 0">无</div>
                        <div v-else-if="scope.row.ver8 >= 1 && scope.row.ver8 <= 3">低</div>
                        <div v-else-if="scope.row.ver8 >= 4 && scope.row.ver8 <= 6">中</div>
                        <div v-else-if="scope.row.ver8 >= 7 && scope.row.ver8 <= 9">高</div>
                    </template>
                </el-table-column>
                <el-table-column prop="ver9" label="阳性小结" show-overflow-tooltip align="center"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { verdict, getVerdictData } from "@/api/inspection/occupational_inspection.js"
export default{
    data(){
        return{
            tableData:[
                {
                    id:"",
                    ver1:"",//科室名称
                    ver2:"",//录入人
                    ver3:"",//医师姓名
                    ver4:"",//审核人
                    ver5:"",//录入时间
                    ver6:"",//审核时间
                    ver7:"",//小结
                    ver8:"",//重症级别
                    ver9:"",//阳性小结
                }
            ],
            loading:false,
            ids:[],
        }
    },
    methods:{
        handleSelectionChange(selection){
            this.ids = selection.map((item)=>{return item.id});
        },
        getVerdict(patientno){
            this.loading = true;
            var obj = {patientno}
            verdict(obj).then((res)=>{
                if(res.code == 200){
                    getVerdictData(obj).then((response)=>{
                        if(response.code == 200 ){
                            this.tableData = response.data;
                            this.loading = false;
                        } else {
                            this.$message({
                                message: response.msg,
                                type:"warning"
                            })
                        }
                    }).then(()=>{
                        this.loading = false;
                    })
                } else {
                    this.$message({
                        message: res.msg,
                        type:"warning"
                    })
                }
            })
        }
    }
}
</script>