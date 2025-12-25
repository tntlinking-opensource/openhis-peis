<!-- 中心会员  麦沃德科技 开发人：清风/矢北/予安-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 搜索栏 -->
    <el-form size="small" :model="queryParams" ref="queryParams" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心" prop="fzx">
        <input-select ref="fzx" :selectData="fzxData" selectWidth="200" @change="fzxDataChange"></input-select>
      </el-form-item>
      <el-form-item label="会员卡号" prop="patientcardno">
        <el-input placeholder="请输入会员卡号" style="width: 200px" v-model="queryParams.patientcardno"></el-input>
      </el-form-item>
      <el-form-item label="会员姓名" prop="patientname">
        <el-input placeholder="请输入会员姓名" style="width: 200px" v-model="queryParams.patientname"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input placeholder="请输入联系电话" style="width: 200px" v-model="queryParams.phone"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno">
        <el-input placeholder="请输入身份证号" style="width: 200px" v-model="queryParams.idcardno"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addWindow()" v-hasPermi="['custservice:membershipManagement:centerMember:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="editWindow()" v-hasPermi="['custservice:membershipManagement:centerMember:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-user" size="mini" :disabled="multiple" @click="upgrade()" v-hasPermi="['custservice:membershipManagement:centerMember:memberUpgrade']">会员升级 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-user" size="mini" :disabled="multiple" @click="recharge()" v-hasPermi="['custservice:membershipManagement:centerMember:memberUpgrade']">积分充值 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-s-release" size="mini" :disabled="single" @click="reporting()" v-hasPermi="['custservice:membershipManagement:centerMember:memberLossReporting']">会员卡挂失 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="exportEx()" v-hasPermi="['custservice:membershipManagement:centerMember:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格数据 -->
    <div class="table-box" style="height: 100%">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="patientcardno" label="会员卡号" width="120" align="center"></el-table-column>
        <el-table-column prop="patientname" label="姓名" width="120" align="center"></el-table-column>
        <el-table-column prop="balanceJf" label="可用积分" width="120" align="center"></el-table-column>
        <el-table-column prop="idSex" label="性别" width="120" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.idSex == 0">男</el-tag>
            <el-tag v-else-if="scope.row.idSex == 1" type="danger">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="120" align="center"></el-table-column>
        <el-table-column prop="memberlevel" label="会员级别" width="120" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.memberlevel == 3" type="success">VVIP</el-tag>
            <el-tag v-else-if="scope.row.memberlevel == 2" type="danger">VIP</el-tag>
            <el-tag v-else>普通会员</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fzx" label="分中心" width="140" align="center"></el-table-column>
        <el-table-column prop="membercreate" label="创建人" width="120" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="khjl" label="客户经理" width="120" align="center"></el-table-column>
        <el-table-column prop="birthdate" label="生日" width="120" align="center">
          <template slot-scope="scope">
            {{ scope.row.birthdate ? scope.row.birthdate.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="idcardno" label="身份证号" width="180" align="center"> </el-table-column>
        <el-table-column prop="dw" label="单位" width="120" align="center"></el-table-column>
        <el-table-column prop="note" label="备注" width="130" align="center"></el-table-column>
        <el-table-column prop="createDate" label="创建日期" width="120" align="center"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <addItems @getList="getList" ref="addItems"></addItems>
    <recharge ref="recharge"></recharge>
    <mendRecharge ref="mendRecharge"> </mendRecharge>
  </div>
</template>

<script>
import { getListData, getDetail, memberUp, branchData } from '@/api/custservice/membership_management/center_member'
import recharge from './recharge.vue'
import addItems from './add.vue'
import mendRecharge from './mendRecharge.vue'
export default {
  components: { addItems, recharge, mendRecharge },
  data() {
    return {
      loading: false,
      ids: [], //选中的数组
      single: true, //必选且单选
      multiple: true, //必选
      total: 0, //总数
      queryParams: {
        current: 1, //初始页数
        size: 20, //页值
        fzx: undefined,
        patientcardno: undefined,
        patientname: undefined,
        phone: undefined,
        idcardno: undefined,
      },
      tableData: [],
      upData: {
        ids: undefined,
      },
      // fzx名称
      fzxData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '分中心',
        url: '/sell/monthtarget/getBranchData',
        bindValue: '', //初始值
        firstName: 'srm',
        secondName: 'fzx',
        queryData: 'key',
      },
      form: {},
      fzxOption: [],
      selection: {},
      fzx: '',
    }
  },
  created() {
    this.getBranchData()
  },
  methods: {
    // 分页查询
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
          for (var i in this.tableData) {
            for (var j in this.fzxOption) {
              if (this.tableData[i].fzx == this.fzxOption[j].branchId) {
                this.tableData[i].fzx = this.fzxOption[j].fzx
              }
            }
          }
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    addWindow() {
      this.$refs.addItems.addWindow(0)
    },
    editWindow() {
      this.$refs.addItems.addWindow(1, this.selection)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      } else {
        this.selection = {}
      }
    },
    //导出
    exportEx() {
      this.download(
        '/member/member/export',
        {
          ...this.queryParams,
        },
        `中心会员.xlsx`
      )
    },
    //获取分中心数据
    getBranchData() {
      branchData({ size: 100 }).then((response) => {
        this.fzxOption = response.data.records
        this.fzxOption.forEach((el) => {
          if(el.branchId == this.$getCookie('cid')){
            this.fzxData.bindValue = el.fzx
          }
        })
        this.getList()
      })
    },
    //会员升级
    upgrade() {
      var ids
      for (var i in this.ids) {
        if (i == 0) ids = this.ids[0]
        else ids += ',' + this.ids[i]
      }
      this.upData.ids = ids
      const upData = this.upData
      this.$modal
        .confirm('确定升级选中用户？')
        .then(function () {
          return memberUp(upData)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('升级成功')
        })
    },
    // 分中心变化选项
    fzxDataChange(value) {
      this.fzx = value.id
      this.fzxData.bindValue = value.name
      this.queryParams.fzx = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.fzxData.bindValue = undefined
      this.fzx = undefined
      this.$refs.fzx.initData()
      this.resetForm('queryParams')
      this.getList()
    },

    //会员卡挂失
    reporting() {
      if (this.selection.patientcardno == '' || this.selection.patientcardno == null || this.selection.patientcardno == undefined) {
        this.$message({
          message: '无会员卡，无法挂失',
          type: 'warning',
        })
      } else {
        const selections = this.selection
        this.$refs.mendRecharge.openMend(selections)
      }
    },
    //积分充值
    recharge() {
      this.$refs.recharge.addWindow(this.ids)
    },
  },
}
</script>
