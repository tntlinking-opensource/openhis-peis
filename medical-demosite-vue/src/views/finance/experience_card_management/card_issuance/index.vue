<!-- 卡发放  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体验卡号" prop="cardNo">
        <el-input v-model="queryParams.cardNo" placeholder="请输入输入码" clearable style="width: 230px"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="卡类型" prop="typeId">
        <el-select v-model="queryParams.typeId" clearable placeholder="请选择">
          <el-option style="width: 230px" v-for="options in typeOptions" :key="options.id" :label="options.text"
            :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="选择日期" prop="data">
        <el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="sendcard"
          v-hasPermi="['finance:card_issuance:send']">发卡</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="exclusivecard"
          v-hasPermi="['finance:card_issuance:exclusive']">团检专属发卡
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-paperclip" :disabled="multiple" @click="edit"
          v-hasPermi="['finance:card_issuance:edit']">修改领取人
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="delete1"
          v-hasPermi="['finance:card_issuance:delete']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="single" icon="el-icon-edit" @click="bind"
          v-hasPermi="['finance:card_issuance:bind']">绑定
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="exportdata"
          v-hasPermi="['finance:card_issuance:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="60" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="卡号" prop="cardNo" width="200" align="center" />
        <el-table-column label="卡类型" prop="cardName" width="160" align="center" />
        <el-table-column label="剩余金额" prop="balanceLimit" width="140" align="center" />
        <el-table-column label="初始金额" prop="tcPrice" width="140" align="center" />
        <el-table-column label="领取人" prop="getterId" width="140" align="center" />
        <el-table-column label="领取时间" prop="getTime" width="160" align="center" />
        <el-table-column label="有效期" prop="validity" width="160" align="center" />
        <el-table-column label="备注" prop="memo" width="140" align="center" />
        <el-table-column label="创建人" prop="createName" width="140" align="center" />
        <el-table-column label="创建时间" prop="createDate" width="140" align="center" />
        <el-table-column label="密码" prop="password" width="142" align="center" />
        <el-table-column label="绑定用户" prop="appUser" width="140" align="center" />
        <el-table-column label="订单" tprop="ddmc" width="140" align="center" />
        <el-table-column label="套餐" prop="tcmc" width="140" align="center" />
      </el-table>
    </div>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <addItems @getList="getList" ref="addItems"></addItems>
    <addTeamItems @getList="getList" ref="addTeamItem"></addTeamItems>
    <revise ref="revise" @getList="getList"></revise>
    <bind ref="bind"></bind>
  </div>
</template>

<script>
import addItems from './add'
import addTeamItems from './team_add'
import revise from './revise'
import bind from './bind'
import { getListData ,deleteCard,cardTypeData} from '@/api/finance/card/experience_card_management.js'

export default {
  components: { addItems, addTeamItems, revise, bind },

  data() {
    return {
      loading: false,
      // 显示搜索条件
      showSearch: true,
         //是否单选
         single: true,
      ///是否多选
      multiple: true,
      tableList: [
      ],
      ids:[],
      total: 50,
      typeOptions: [
 
      ],
      ///查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typeId:'',
        cardNo:'',
        date:[],

      }
    }
  },
  created() {
    cardTypeData({type:0}).then(response=>{
  
      this.typeOptions=response.data
    })
    this.getList();
    
  },
  methods: {
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
      }
      this.getList();
    },
    //重置按钮
    resetQuery(){
      this.resetForm("queryForm");
      this.queryParams.date=undefined
   
            this.queryParams.startTime=undefined
            this.queryParams.endTime=undefined
			this.handleQuery();
    },
    // 查询列表
    getList() {
      this.loading = true;
     
      getListData(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
     
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    ///导出
    exportdata() {
      this.download('/finance/sendCard/export', {
        ...this.queryParams
      }, `体检卡发放_${new Date().getTime()}.xlsx`)
    },
    ///绑定
    bind() {
      this.$refs.bind.handleAdd(this.ids)
    },
    //发卡
    sendcard() {

      this.$refs.addItems.handleAdd()

    },
    //团检专属发卡
    exclusivecard() {
      this.$refs.addTeamItem.handleAdd()
    },
    //编辑
    edit() {
      this.$refs.revise.handleAdd(this.ids)
    },
    //删除
    delete1() {
      const ids = this.ids.join(',');
      const data={
        ids:ids
      }
    
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
           return deleteCard(data);
      
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
  }
}
</script>

<style></style>