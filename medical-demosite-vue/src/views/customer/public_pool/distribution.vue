<!-- 公共池-领导分配 麦沃德科技开发人:清风/矢北 -->
<template>
  <div class="add-container">
    <el-dialog title="领导分配" class="add-items" :visible.sync="open" width="860px" height="auto" append-to-body
      :close-on-click-modal="false">
      <el-form :model="form" ref="form" size="small" :inline="true" label-width="110px" @submit.native.prevent>
        <el-form-item label="销售人员名称">
          <el-input placeholder="请输入销售人员名称" style="width:260px;" v-model="form.key"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="table-box">
        <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true"
          @selection-change="handleSelectionChange" row-key="id" height="100%">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
          <el-table-column prop="userName" label="销售经理" align="center"></el-table-column>
          <el-table-column prop="fzxmc" label="分中心" align="center"></el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="onReload()">分配</el-button>
        <el-button @click="onCancel()">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
//是否分配   
import { getSaleList, isDistribution, isAllocation, saveData } from '@/api/customer/public_pool.js'
export default {
  data() {
    return {
      khId: [],
      open: false,
      loading: "",
      form: {
        key: ""
      },
      xsId: [],
      queryparams: {
        saleNmae: undefined
      },
      tableData: [

      ]
    }
  },
  methods: {
    getList() {
      getSaleList(this.form).then(response => {
       
        this.tableData = response.data
      })
    },
    handleQuery() {
      this.getList();
    },
    showDialog(id) {
      this.khId = id
      this.open = true;
      this.getList()
    },
    handleSelectionChange(selection) {
      this.xsId = selection.map((item) => item.userNo);
     
    },
    onCancel() {
      this.open = false;
    },
    reset() {
      this.form.key = undefined
    },
    ///,isDistribution,isAllocation,saveData
    onReload() {
      const query = {
        clientIds: this.khId.join(','),
        xsIds: this.xsId.join(',')
      }
      if (this.xsId.length != 0) {
        isDistribution(query).then(response => {
          if (response.data == true) {
            isAllocation(query).then(response => {
              if (response) {
                if (response.data == true) {
                  saveData(query).then(response => {
                    this.$message.success('已经成功分配')
                    this.open=false
                    this.$emit('getList')
                  })
                } else {
                  this.$message.warning('该用户了已经被领导分配过了')
                }
              }
            })
          } else {
            this.$message.warning('您已经领取过该用户了！')
          }
        })
      } else (
        this.$message.warning('至少选择一位销售经理！！！')
      )


    },
  }
}
</script>