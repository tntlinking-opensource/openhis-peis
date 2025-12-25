<!-- 每日检验科明细-右表 开发人 麦沃德科技 清风 -->
<template>
    <div class="table-box" style="height:100%;display:flex; flex-direction:column">
        <div style="flex:1; display:flex; flex-direction:column">
            <div class="table-box">
                <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" stripe border
                    height="100%">
                    <el-table-column type="selection" align="center" />
                    <el-table-column label="序列" align="center" type="index" />
                    <el-table-column label="科室" align="center" prop="idKs" show-overflow-tooltip />
                    <el-table-column label="收费项目" align="center" prop="idExamfeeitem" show-overflow-tooltip />
                    <el-table-column label="收费" align="center" prop="FFeecharged" show-overflow-tooltip />
                    <el-table-column label="项目弃检" align="center" prop="FGiveup" show-overflow-tooltip />
                    <el-table-column label="项目已检" align="center" prop="FExaminated" show-overflow-tooltip />
                    <el-table-column label="外送机构" align="center" prop="idExamfeeitem" show-overflow-tooltip />
                </el-table>
            </div>
            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
                @pagination="getList" />
        </div>

        <div style="flex:1; display:flex; flex-direction:column">
            <div class="table-box">
                <el-table v-loading="loadings" :data="tableDatas" stripe border
                    height="100%">
                    <el-table-column label="收费项目" align="center" mix-width="160px" prop="examItemNameR" show-overflow-tooltip />
                    <el-table-column label="检验结果" align="center">
                        <el-table-column label="数值" align="center" mix-width="80px" prop="examItemValuesNumber" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="结果" align="center" mix-width="80px" prop="examItemValuesShort" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="报告结果" align="center" mix-width="80px" prop="examItemValuesReport" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="正常" align="center" mix-width="80px" prop="status" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="单位" align="center" mix-width="80px" prop="units" show-overflow-tooltip ></el-table-column>
                    </el-table-column>
                    <el-table-column label="参考范围" align="center">
                        <el-table-column label="LIS范围" align="center" mix-width="80px" prop="refRange" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="报告范围" align="center" mix-width="80px" prop="reportRange" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="最小值" align="center" mix-width="80px" prop="refLow" show-overflow-tooltip ></el-table-column>
                        <el-table-column label="最大值" align="center" mix-width="80px" prop="refHigh" show-overflow-tooltip ></el-table-column>
                    </el-table-column>
                    <el-table-column label="检验师" align="center" mix-width="120px" prop="inspectName" show-overflow-tooltip ></el-table-column>
                    <el-table-column label="审核医师" align="center" mix-width="120px" prop="auditName" show-overflow-tooltip ></el-table-column>
                    <el-table-column label="检验时间" align="center" mix-width="120px" prop="examDateTime" show-overflow-tooltip ></el-table-column>
                </el-table>
            </div>
            <pagination v-show="totals > 0" :total="total" :page.sync="queryParamsSecond.current" :limit.sync="queryParamsSecond.size"
                @pagination="getListSecond" />
        </div>
    </div>
</template>

<script>
export default{
    data(){
        return{
            loading:false,
            loadings:false,
            total:10,
            totals:10,
            queryParams:{
                current:1,
                size:10,
            },
            queryParamsSecond:{
                current:1,
                size:10,
            },
            tableData:[

            ],
            tableDatas:[

            ]
        }
    },
    methods:{
        handleSelectionChange(selection){
        
        },
        getList(){},
        getListSecond(){},
    }
}
</script>