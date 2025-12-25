<!-- 卡发放  麦沃德科技 开发人：矢北-->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" v-model="form" :inline="true" label-width="80px;">
      <el-form-item label="体验卡号" prop="cardNo" style="margin-right: 16px;">
        <el-input placeholder="请输入会员卡号" style="width:230px;" v-model="form.cardNo"></el-input>
      </el-form-item>
      <el-form-item label="卡类型" prop="typeId" style="margin-left:20px; margin-right: 16px;">
        <input-select :selectData="typeData" :queryParams="params" v-model="form.typeId" @change="typeChange"></input-select>
      </el-form-item>
      <el-form-item label="领取时间">
        <el-date-picker v-model="form.date" style="width: 300px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item style="margin-right: 10px;">
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-help"  @click="handleView"
          v-hasPermi="['custservice:satisfaction:dissatisfied:view']">发 卡
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-takeaway-box" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['custservice:satisfaction:dissatisfied:filter']">删 除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handleExport"
          v-hasPermi="['custservice:satisfaction:dissatisfied:export']">导 出
        </el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" :v-loading="loading" height="100%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column fixed label="序列" width="55" type="index" align="center" />
        <el-table-column label="卡号" prop="cardNo" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="卡类型" prop="cardName" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="剩余积分" prop="balanceJf" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="剩余金额" prop="balanceMoney" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="领取人" prop="getterId" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="领取时间" prop="getTime" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="有效期" prop="validity" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="memo" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="创建人" prop="createName" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="创建时间" prop="createDate" min-width="120" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size"
      @pagination="getList" />
    <addCard ref="addCard" @getList="getList"></addCard>
  </div>
</template>

<script>
import { getListData,deleteList ,} from '../../../../api/custservice/membership_management/issue'
import addCard from './add_card.vue'
export default {
  components: { addCard },
  data() {
    return {
      // 科室名称
      typeData: {
        placeholder: '请输入输入码选择',
        key: '',
        value: '卡类型',
        url: '/finance/sendCard/getTypeData',
        bindValue: '', //初始值(必传)
        firstName:'text',
        secondName:'text',
        queryData: 'key',

      },
      //卡类型参数
      params:{
        type:1
      },
      loading: true,
      //总数
      total: 0,
      form: {
        date: undefined,
        statTime: undefined,
        endTime: undefined,
      },
      tableList: [

      ],
      //卡类型
      type: 1,
      single: false,
      multiple:true,
      loading: false,
      ids:[],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      getListData(this.form).then(response => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //查看
    handleView() {
        this.$refs.addCard.openDia();
    },
    //删除
    handleDelete() {
      var ids;
      for(var i in this.ids)
      {
        if(i==0) ids=this.ids[0]
        else ids+=','+this.ids[i]
      }
      var delList={
        ids:undefined
      }
      delList.ids=ids
   
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return  deleteList(delList);
      }).then(() => { this.getList();
        this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    //导出
    handleExport() {
      this.download('/finance/sendCard/export', {
                ...this.form
            }, `会员卡发放.xlsx`)
    },
    //查询
    handleQuery() {
      if (this.form.date) {
        this.form.startTime = this.form.date[0] + " 00:00:00"
        this.form.endTime = this.form.date[1] + " 23:59:59"
      }else
      {
        this.form.startTime = undefined
        this.form.endTime = undefined
      }
      this.form.current = 1
      this.getList()
      this.$modal.msgSuccess("搜索成功");
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
    
      this.auditStatus = selection.map(item => item.auditStatus)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //重置
    reset() {
      this.form={
      }
      this.typeData.bindValue=''
      this.resetForm("form");
      this.getList()
    },
    typeChange(value) {
    
       this.form.typeId = value.id
       this.typeData.bindValue = value.name;
    },

  },

}
</script>

<style></style>