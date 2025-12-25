<!-- 加项情况查询 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收费日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="patientName">
        <el-input v-model="queryParams.patientName" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="收费人" prop="idFeecharger">
        <input-select ref="idFeecharger" :selectData="departData" selectWidth="160" style="width: 160px" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="加项项目" prop="examname">
        <el-input v-model="queryParams.examname" placeholder="请输入加项项目" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="加项医生" prop="jxys">
        <input-select :selectData="addItemData" selectWidth="160" style="width: 160px" @change="selectChange3"></input-select>
      </el-form-item>
      <el-form-item label="开单医生" prop="idOpendoctor">
        <input-select :selectData="departData" selectWidth="160" style="width: 160px" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:totalAdd:export']">导出Excel </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" v-hasPermi="['search:chargeCollection:total']">总金额:{{ totalMoney }} </el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip width="130" />
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ ['男', '女'][scope.row.idSex] }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" align="center" show-overflow-tooltip />
        <el-table-column label="团体名称" prop="orgName" align="center" show-overflow-tooltip width="150" />
        <el-table-column label="订单号" prop="ddh" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="收费人" prop="idFeecharger" align="center" show-overflow-tooltip />
        <el-table-column label="加项时间" prop="createdate" align="center" show-overflow-tooltip width="180" />
        <el-table-column label="加项医生" prop="jxys" align="center" show-overflow-tooltip width="90" />
        <el-table-column label="项目名称" prop="examfeeitemName" align="center" show-overflow-tooltip width="160" />
        <el-table-column label="项目科室" prop="ksName" align="center" show-overflow-tooltip width="100" />
        <el-table-column label="项目价格" prop="price" align="center" show-overflow-tooltip width="100" />
        <el-table-column label="优惠价" prop="factprice" align="center" show-overflow-tooltip width="90" />
        <el-table-column label="开单医生" prop="doctorapply" align="center" show-overflow-tooltip width="90" />
        <el-table-column label="备注" prop="feeitemdesc" align="center" show-overflow-tooltip width="120" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :page-sizes="[20, 50, 100, 200 ,500]" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData, getPageTotal } from '@/api/search/total_add'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'
export default {
  name: 'Total_add',
  data() {
    return {
      //总金额
      totalMoney: 0,
      // 分中心列表
      fzxOptions: [],
      departData: {
        placeholder: '请输入输入码选择',
        value: '收费人',
        url: '/query/totalAdd/getAllUserData',
        bindValue: '', //初始值(必传)
        firstName: 'username',
        secondName: 'username',
      },
      // 表格展示数据
      tableList: [],
      // 加项医师
      addItemData: {
        placeholder: '请输入输入码选择',
        value: '医生名称', //第二列标题
        url: '/reception/register/getJxys', //请求连接
        secondName: 'username',
        // queryData: 'key',
      },
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        patientcode: '',
        patientName: '',
        idFeecharger: '',
        patientcode: '',
        examname: '',
        jxys: '',
        idOpendoctor: '',
        ddh: '',
        branchIds: undefined,
      },
       // 是否为线上 
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = this.$getCookie('cid')
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.getList()
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
  },
  methods: {
    selectChange1(value) {
      this.queryParams.idFeecharger = value.id
    },
    selectChange2(value) {
      this.queryParams.idOpendoctor = value.id
    },
    selectChange3(value) {
      this.queryParams.jxys = value.id
      this.addItemData.bindValue = value.username
    },
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.totalMoney = 0
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
        // for (var i in this.tableList) {  
        //   this.totalMoney += Number(this.tableList[i].factprice)
        // }
      })
      // 获取总计金额
      getPageTotal(this.queryParams).then((response) => {
        console.log("总计金额",response.data);
        this.totalMoney = response.data
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.departData.bindValue = undefined
      this.$refs.idFeecharger.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('query/totalAdd/export', this.queryParams, `加项查询.xlsx`)
    },
  },
}
</script>
