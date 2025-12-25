<!-- 每日检验科明细 开发人 麦沃德科技 清风 -->
<template>
    <div class="app-container flex-direction-column">
        <el-form :inline="true" label-width="80px">
            <el-form-item label="登记日期">
                <el-date-picker value-format="yyyy-MM-dd" type="daterange" v-model="queryParams.valueData"
					range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="体检团体">
                <el-input v-model="queryParams.idOrg" style="width:160px;" placeholder="请输入体检团体"></el-input>
            </el-form-item>
            <el-form-item label="订单号">
                <el-input v-model="queryParams.ddh" style="width:160px;" placeholder="请输入体检团体"></el-input>
            </el-form-item>
            <el-form-item label="体检号">
                <el-input v-model="queryParams.patientcode" style="width:160px;" placeholder="请输入体检团体"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="queryParams.patientname" style="width:160px;" placeholder="请输入体检团体"></el-input>
            </el-form-item>
            <el-form-item label="已检/未检" label-width="90px">
                <el-input v-model="queryParams.useExamHiden" style="width:160px;" placeholder="请输入体检团体"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="mini">搜索</el-button>
                <el-button>重置</el-button>
            </el-form-item>
        </el-form>
        <div class="table-box" style="display:flex;flex-direction:row;">
            <div class="table-box" style="flex:2;display:flex;flex-direction:column">
                <div class="table-box">
                    <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" stripe border
                        height="100%">
                        <el-table-column type="selection" fixed="left" align="center" />
                        <el-table-column label="序列" align="center" fixed="left" type="index" />
                        <el-table-column label="体检号" align="center" min-width="140px" prop="patientcode" fixed="left" show-overflow-tooltip />
                        <el-table-column label="姓名" align="center" min-width="100px" prop="patientname" fixed="left" show-overflow-tooltip />
                        <el-table-column label="订单号" align="center" min-width="140px" prop="ddh" show-overflow-tooltip />
                        <el-table-column label="体检团队" align="center" min-width="160px" prop="orgName" show-overflow-tooltip />
                        <el-table-column label="性别" align="center" min-width="80px" prop="idSex" show-overflow-tooltip />
                        <el-table-column label="年龄" align="center" min-width="80px" prop="age" show-overflow-tooltip />
                        <el-table-column label="登记" align="center" min-width="120px" prop="FRegistered" show-overflow-tooltip />
                        <el-table-column label="体检分类" align="center" min-width="120px" prop="useCodeHiden" show-overflow-tooltip />
                        <el-table-column label="登记时间" align="center" min-width="160px" prop="dateregister" show-overflow-tooltip />
                        <el-table-column label="登记员" align="center" min-width="120px" prop="idDoctorreg" show-overflow-tooltip />
                        <el-table-column label="开单医师" align="center" min-width="120px" prop="doctorapply" show-overflow-tooltip />
                        <el-table-column label="电话" align="center" min-width="120px" prop="phone" show-overflow-tooltip />
                        <el-table-column label="套餐" align="center" min-width="240px" prop="idTjtc" show-overflow-tooltip />
                    </el-table>
                </div>
                <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
                    @pagination="getList" />
            </div>
            <div class="table-box" style="flex:1;display:flex;flex-direction:column">
                <rightTableItems />
            </div>
        </div>
    </div>
</template>

<script>
import rightTableItems from './right_table.vue';
export default{
    components:{ rightTableItems },
    data(){
        return{
            loading:false,
            total:30,
            queryParams:{
                current:1,
                size:10,
                idOrg:"",
                ddh:"",
                patientcode:"",
                patientname:"",
                useExamHiden:"",
            },
            loading:false,
            tableData:[
                {

                }
            ],
        }
    },
    methods:{
        handleSelectionChange(){
            
        },
        getList(){

        }
    }
}
</script>