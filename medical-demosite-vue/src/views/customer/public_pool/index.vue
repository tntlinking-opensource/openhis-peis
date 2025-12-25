<!-- 公共池 麦沃德科技 开发人:清风/予安 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input style="width:230px" v-model="queryParams.khdwmc" placeholder="请输入客户单位名称" clearable></el-input>
      </el-form-item>
      <el-form-item label="客户单位输入码" prop="khdwsrm">
        <el-input style="width:230px" v-model="queryParams.khdwsrm" placeholder="请输入客户单位输入码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="addWindow"
          v-hasPermi="['customer:publicPool:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" :disabled="single" plain @click="editWindow"
          v-hasPermi="['customer:publicPool:edit']">编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-delete" plain :disabled="multiple" @click="handleDelete"
          v-hasPermi="['customer:publicPool:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="single" plain @click="queryWindow"
          v-hasPermi="['customer:publicPool:check']">查看</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-sell" plain @click="receiveWindow" :disabled="single"
          v-hasPermi="['customer:publicPool:receive']">客户领取</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-price-tag" plain :disabled="multiple"
          @click="distributionWindow" v-hasPermi="['customer:publicPool:distribution']">领导分配</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" :disabled="single" @click="LeaderRelease" icon="el-icon-unlock" plain
          v-hasPermi="['customer:publicPool:release']">领导释放</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-download" plain @click="exportWindow"
          v-hasPermi="['customer:publicPool:importDownLoad']">导入模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="warning" icon="el-icon-download" plain @click="uploadWindow"
          v-hasPermi="['customer:publicPool:import']">导入</el-button>
      </el-col>

    </el-row>
    <!-- 表格数据 -->
    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" border stripe @selection-change="handleSelectionChange"
        height="100%" :row-key="getRowKeys" :expand-row-keys="expands" @expand-change="expandChange">
        <el-table-column type="selection" align="center" width="55"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column label="#" type="expand" align="center" width="55">
          <template slot-scope="scope">
            <div style="padding:0 30px;">
              <el-table :data="dataList" align="center" border stripe v-loading="expandLoading"
                @selection-change="handleSelectionChange2">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column align="center" prop="xs" label="销售经理"> </el-table-column>
                <el-table-column align="center" prop="fzx" label="分中心"> </el-table-column>
                <el-table-column align="center" prop="lqrq" label="操作日期"> </el-table-column>
                <el-table-column align="center" prop="czzt" label="客户领取/领导分配">
                  <template slot-scope="scope">
                    <span v-if="scope.row.czzt==0">领取</span>
                    <span v-else-if="scope.row.czzt==1">分配</span>
                    <span v-else></span>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination v-show="total2 > 0" :total="total2" :page.sync="queryParams2.current"
                :limit.sync="queryParams2.size" @pagination="getLqryData(scope.row.id)" />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center" min-width="300"></el-table-column>
        <el-table-column prop="khdwsrm" label="客户单位输入码" align="center" min-width="200"></el-table-column>
        <el-table-column prop="khdwlxr" label="客户单位联系人" align="center" min-width="160"></el-table-column>
        <el-table-column prop="khdh" label="客户电话" align="center" min-width="160"></el-table-column>
        <el-table-column prop="khdwdz" label="客户单位地址" align="center" min-width="330"></el-table-column>
        <el-table-column prop="sshy" label="所属行业" align="center" min-width="140"></el-table-column>
        <el-table-column prop="dwjgdm" label="单位机构代码" align="center" min-width="200"></el-table-column>
        <el-table-column prop="lqcstj" label="客户领取次数" align="center" min-width="160"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <!-- 增改查对话框 -->
    <addItems ref="addItems" @getList="getList"></addItems>
    <distributionItems @getList="getList" ref="distributionItems"></distributionItems>
    <uploadItems ref="uploadItems"></uploadItems>
  </div>
</template>

<script>
import addItems from './add.vue'
import distributionItems from './distribution.vue'
import uploadItems from './upload.vue'

import { listPool, delPool, isLqAndFp, isReceive, receive, getLqryData, leaderRelease,isLeader,} from '@/api/customer/public_pool.js'
export default {
  name:'Public_pool',
  components: { addItems, distributionItems, uploadItems },
  data() {
    return {
      //领取表格
      dataList: [],
      queryParams: {
        current: 1,//初始页数
        size: 20,//页值
        khdwmc: "",
        khdwsrm: ""
      },
      releaseParams: {
        clientId: undefined,
        xsIds: undefined
      },
      total: 0,//总数
      ids: [],//选中的数组
      single: true,//必选且单选
      multiple: true,//必选
      xsId: [],
      loading: false,
      tableData: [],
      selection: {},

      // 获取row的key值
      getRowKeys(row) {
        return row.id;
      },
      //客户id
      khid:undefined,
      // 要展开的行，数值的元素是row的key值
      expands: [],
      // 表格展开时加载
      expandLoading: false,
      // 展开表格内的参数
      queryParams2: {
        current: 1,//初始页数
        size: 20,//页值
        clientId: undefined
      },
      total2: 0,//总数
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取数据
    getList() {
      this.loading = true;
      listPool(this.queryParams).then(({ data }) => {
        
        this.tableData = data.records
        this.total = data.total
        this.loading = false;
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.expands = []
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {

      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      if (selection.length == 1) {
        this.selection = selection[0];
      }
    },
    //领导释放
    LeaderRelease() {
      isLeader().then(response=>{
        if(response.data==true)
        {

          if(this.xsId.length<1){
            this.$modal.msgWarning('请选择需要释放的销售人员')
          }else{
            var  xsId;
          for (var i in this.xsId) {
        if (i == 0) xsId = this.xsId[0]
        else xsId += "," + this.xsId[i]
      }
   
      this.releaseParams.clientId = this.khid
      this.releaseParams.xsIds = xsId
      leaderRelease(this.releaseParams).then(response => {
        this.$modal.msgSuccess(response.msg)
        this.getList()
      })
          }
     
        }else{
          this.$modal.msgWarning('仅有领导可以进行此操作！！！')
        }
      })
   
    },
    //添加
    addWindow() {
      this.$refs.addItems.addWindow(1);
    },
    //编辑
    editWindow() {
    
      const id = this.selection.id
      this.$refs.addItems.addWindow(0, id,);
    },
    // 删除
    handleDelete() {
      const ids = this.ids;
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delPool(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    //查看
    queryWindow() {
      this.$refs.addItems.addWindow(2, this.selection);
    },
    //客户领取
    receiveWindow() {
   
      let ids = {
        clientIds: this.ids.join(',')
      }
      isLqAndFp(ids).then(({ data }) => {
        if (!data) {
          this.$confirm('确定领取选中的记录？', '提醒', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let id = {
              ids: this.ids[0]
            }
            isReceive(id).then(({ data }) => {
              if (!data) {
                receive(id).then(() => {
                  this.$modal.msgSuccess('领取成功')
                  this.getList()
                  this.loading = false
                }).catch(() => {
                  this.$modal.msgError('领取失败,要领取的信息或被删除！')
              
                })
              } else {
                this.$modal.msgWarning('不能重复领取同一条记录！')
                this.loading = false
              }
            }).catch(() => {
              this.loading = false
            })
          }).catch(()=>{})
        } else {
          this.$modal.msgWarning('当前客户已被领导强制分配过,不能再进行领取！请重新选择！')
          this.loading = false
        }
      }).catch(() => {
        this.loading = false
      })
    },
    // 展开行时发起请求
    expandChange(row, expandedRows) {
      if (expandedRows.length) {
        this.expands = []
        if (row) {
     
          this.expands.push(row.id)
          this.getLqryData(row.id)
        }
      } else {
        this.expands = []
      }
    },
    // 获取领取人员的信息
    getLqryData(id) {
      this.queryParams2.clientId = id
      this.expandLoading = true
      getLqryData(this.queryParams2).then(({ data }) => {
        this.dataList = data
     
        this.expandLoading = false

      }).catch(() => {
        this.expandLoading = false
      })
    },
    // 表格展开的多选
    handleSelectionChange2(selection) {
      this.khid=selection[0].clientid
      this.xsId = selection.map((item) => item.xsjlid);
    },
    // 领导分配
    distributionWindow() {
      
      isLeader().then(response=>{
       if(response.data==true)
       {
        this.$refs.distributionItems.showDialog(this.ids);
       }else{
        this.$modal.msgWarning('仅有领导可以进行此操作')
       }
      })
      
    },
    // 导入
    uploadWindow() {
      this.$refs.uploadItems.showDialog();
    },
    //导入模板下载
    exportWindow() { 
      this.downloadStatic('/static/stencil/clientModel_kh.xlsx', '客户公共池导入模板.xlsx')
    },
  }
}
</script>

<style scoped>
::v-deep .el-table__expand-icon .el-icon-arrow-right:before {
  content: "+";
  color: #1890ff;
}
</style>