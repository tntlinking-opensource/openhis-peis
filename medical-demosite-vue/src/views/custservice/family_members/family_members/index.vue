<!-- 家庭会员 麦沃德科技 开发人:清风/暴雨 -->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" label-width="50px" :model="queryParams">
          <el-form-item label-width="74px" label="会员卡号" style="margin-right:16px;">
            <el-input style="width:230px" v-model="queryParams.cardno" placeholder="请输入会员卡号" clearable></el-input>
          </el-form-item>
          <el-form-item label="姓名" style="margin-right:16px;">
            <el-input style="width:230px" v-model="queryParams.patientname" placeholder="请输入姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="电话" style="margin-right:16px;">
            <el-input style="width:230px" v-model="queryParams.phone" placeholder="请输入电话" clearable></el-input>
          </el-form-item>
          <el-form-item label-width="74px" label="身份证号" style="margin-right:16px;">
            <el-input style="width:230px" v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable></el-input>
            </el-form-item>
            <el-form-item label="金额" style="margin-right:24px;">
              <el-input-number controls-position="right" style="width:100px;" :min="0" :max="99999999"
                               v-model="queryParams.startMoney"
              ></el-input-number>
                -
              <el-input-number controls-position="right" style="width:100px;" :min="0" :max="99999999"
                               v-model="queryParams.endMoney"
              ></el-input-number>
            </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-document-add" plain @click="addWindow" :disabled="ser"
                           v-hasPermi="['custservice:familyMembers:handle']"
                >家庭卡办理
                </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="success" icon="el-icon-document-add" plain @click="addWindow" :disabled="single"
                         v-hasPermi="['custservice:familyMembers:addman']"
              >新增家庭卡成员
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="warning" icon="el-icon-coin" plain @click="chargeBtn"
                         v-hasPermi="['custservice:familyMembers:recharge']"
              >家庭卡充值
              </el-button>
            </el-col>
<!--            <el-col :span="1.5">-->
<!--              <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain-->
<!--                         @click="editWindow"-->
<!--                         v-hasPermi="['custservice:familyMembers:edit']"-->
<!--              >编辑-->
<!--              </el-button>-->
<!--            </el-col>-->
            <el-col :span="1.5">
              <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="handleExport"
                         v-hasPermi="['custservice:familyMembers:export']"
              >导出家庭会员信息
              </el-button>
            </el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格数据 -->
        <div class="table-box">
          <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"
                    @selection-change="handleSelectionChange" height="100%"
          >
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="patientcardno" label="家庭卡号" align="center" min-width="80px"></el-table-column>
            <el-table-column prop="balanceJf" label="积分" align="center" min-width="60px"></el-table-column>
            <el-table-column prop="balanceMoney" label="金额" align="center" min-width="60px"></el-table-column>
            <el-table-column prop="patientarchiveno" label="档案号" align="center" min-width="80px"></el-table-column>
            <el-table-column prop="patientname" label="姓名" align="center" min-width="60px"></el-table-column>
            <el-table-column prop="idSex" label="性别" align="center" min-width="60px">
              <template slot-scope="scope">
                <div v-if="scope.row.idSex == 0">男</div>
                <div v-if="scope.row.idSex == 1">女</div>
                <div v-if="scope.row.idSex == 2">通用</div>
              </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话" align="center" min-width="80px"></el-table-column>
            <el-table-column prop="idcardno" label="身份证号" align="center" show-overflow-tooltip min-width="110px"
            ></el-table-column>
            <el-table-column prop="birthdate" label="出生日期" align="center" show-overflow-tooltip min-width="110px"
            ></el-table-column>
            <el-table-column prop="patientclass" label="会员等级" align="center" min-width="60px"></el-table-column>
            <el-table-column prop="dw" label="单位" align="center" min-width="100px" show-overflow-tooltip></el-table-column>
<!--            <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip min-width="100px"></el-table-column>-->
            <el-table-column prop="typeName" label="卡类型" align="center" min-width="60px"></el-table-column>
            <el-table-column label="是否主持人" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-tag type="primary" v-if="scope.row.isMain == true">是</el-tag>
                <el-tag type="warning" v-else>否</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
                @pagination="getList"
    />
        <handleItems ref="handleItems" @getList="getList"></handleItems>
        <chargeItems ref="chargeItems" @getList="getList"></chargeItems>
        <editItems ref="editItems" @getList="getList"></editItems>
    </div>
</template>

<script>
    import handleItems from './add.vue'
    import chargeItems from './charge.vue'
    import editItems from './edit.vue'
    import { getListData } from '@/api/custservice/family_members/family_members/family_members'
    export default{
        components: { handleItems,chargeItems,editItems },
        data(){
            return{
                ser:false,
                showSearch:true,
                loading:false,
                ids:[],//选中的数组
                single:true,//必选且单选
                multiple:true,//必选
                total:10,//总数
                queryParams: {
                  current: 1,//初始页面
                  size: 10,//页值
                  cardno: undefined,
                  patientname: undefined,
                  phone: undefined,
                  idcardno: undefined,
                  startMoney: undefined,
                  endMoney: undefined
                },
              tableData: [],
              selection: {}
            }
        },
      created() {
        this.getList()
      },
      methods: {
        //查询列表
        getList() {
          this.loading = true
          getListData(this.queryParams).then(response => {
            this.tableData = response.data.records
            this.total = response.data.total
            this.loading = false
          })
        },
        // 搜索
        handleQuery() {
          this.queryParams.current = 1
          if (this.queryParams.date) {
            this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
            this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
          } else {
            this.queryParams.startTime = undefined
            this.queryParams.endTime = undefined
          }
          this.getList()
        },
        // 重置
        resetQuery() {
          this.resetForm('queryParams')
          this.handleQuery()
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
          this.ser=selection.length
          this.patientcardno = selection.map((item) => item.patientcardno)
          this.ids = selection.map((item) => item.id)
          this.single = selection.length != 1
          this.multiple = !selection.length
          if (selection.length == 1) {
            this.selection = selection[0]
          }
        },
        //家庭卡办理
        addWindow() {
          this.$refs.handleItems.addWindow(this.patientcardno?this.patientcardno.toString() : '')
        },
        //编辑
        editWindow() {
          this.$refs.editItems.addWindow( this.ids)
        },
        //充值
        chargeBtn() {
          this.$refs.chargeItems.addWindow()
        },
        // /** 导出按钮操作 */
        handleExport() {
          this.download('/report/healthAssociate/export', {}, `家庭会员.xlsx`)
        }
      }
    }
</script>
