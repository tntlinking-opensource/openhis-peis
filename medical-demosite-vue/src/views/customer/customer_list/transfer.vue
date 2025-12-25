<!-- 客户转移  开发人：麦沃德科技半夏 -->
<template>
  <div class="transfer-container">
    <el-dialog title="客户转移" :visible.sync="open" class="transfer-items" width="865px" append-to-body>
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
          <el-form-item prop="type">
            <el-checkbox v-model="queryParams.type" :true-label="1" :false-label="2">未离职</el-checkbox>
          </el-form-item>
          <el-form-item prop="type">
            <el-checkbox v-model="queryParams.type" :true-label="2" :false-label="1">离职</el-checkbox>
          </el-form-item>
          <el-form-item label="销售人员名称" prop="key" style="margin-left: 24px">
            <el-input v-model="queryParams.key" placeholder="请输入销售人员名称" clearable style="width: 230px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loading" :data="tableList" height="500px" stripe
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="销售经理" prop="nickName" align="center" show-overflow-tooltip />
            <el-table-column label="分中心" prop="fzx" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current"
          :limit.sync="queryParams.size" @pagination="getList" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button  type="primary" @click="submitForm">转 移</el-button>
        <el-button  @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getMoveList,saveTransferData} from '@/api/customer/customer_list.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        xsrymc: undefined,
        type: 2,
        current: 1,
        size: 10,
      },
      //非单选禁用
      single:true,
      id:undefined,
      // 排检表格数据
      tableList: [
       
      ],
      // 是否显示弹出层
      open: false,
    }
  },
  computed: {},
  watch: {},
  created() { },

  methods: {
    // 查询列表
    getList() {
      getMoveList(this.queryParams).then(response=>{
        this.tableList = response.data.records
        this.total = response.data.total
      })
    },
    // 搜索
    handleQuery() {
      this.getList()
    },
    // 显示
    handleShow(ids) {
      this.ids=ids
      this.queryParams.current = 1;
      this.open = true;
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.id = selection.map((item) => item.id)
      this.single = selection.length != 1;
    },
    // 删除
    handleDelete() {
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () { })
        .then(() => {
          for (let i in this.ids) {
            let index = this.tableList.findIndex((item) => {
              if (this.ids[i] == item.id) {
                return true
              }
            })
            this.$delete(this.tableList, index)
          }
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 登记  
    submitForm() {  
      let ids=this.ids.join(',')
      if(this.id.length==0)
      {
        this.$modal.msgWarning('请至少选择一位销售经理')
      }else if(this.id.length>1){
        this.$modal.msgWarning('最多只能选择一位销售经理')
      } 
      else{
        const query = {
        id:this.id[0],
        ids:this.ids,
        key:this.queryParams.type
      }
      saveTransferData(query).then(response=>{
        this.$modal.msgSuccess('已经转移成功！！！')
        this.open=false
        this.$emit('getList')
      })
      }
  
    },
    // 取消
    cancel() {
      this.open = false;
    },
  },
}
</script>
<style lang="scss">
.transfer-items {
  .el-checkbox__input {
    line-height: 32px;
  }
}
</style>
