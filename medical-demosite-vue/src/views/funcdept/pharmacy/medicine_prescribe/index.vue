<!-- 审核发药 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名  " clearable style="width: 180px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item v-model="queryParams.usernameData" label="开药医生" prop="username">
        <input-select v-model="queryParams.username" :selectData="usernameData" selectWidth="160"
          @change="usernameDataChange"></input-select>
      </el-form-item>
      <el-form-item label="开药日期">
        <el-date-picker v-model="queryParams.date" style="width: 350px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="药品" prop="drugId">
        <input-select :selectData="drugIdData" selectWidth="160" @change="drugIdDataChange"></input-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable style="width: 180px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="isFinished">
        <el-select v-model="queryParams.isFinished" placeholder="请选择" clearable style="width: 180px;">
          <el-option label="待处理" :value="0" />
          <el-option label="未成交" :value="1" />
          <el-option label="成交" :value="2" />
          <el-option label="退药" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
        <el-table-column label="手机号" prop="phone" align="center" show-overflow-tooltip />
        <el-table-column label="开药医生" prop="username" align="center" show-overflow-tooltip />
        <el-table-column label="开药时间" prop="prescribeTime" align="center" show-overflow-tooltip />
        <el-table-column label="药品" prop="drugName" align="center" show-overflow-tooltip />
        <el-table-column label="规格" prop="drugStandard" align="center" show-overflow-tooltip />
        <el-table-column label="数量" prop="num" align="center" show-overflow-tooltip />
        <el-table-column label="开药原因" prop="reason" align="center" show-overflow-tooltip />
        <el-table-column label="状态" prop="isFinished" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.isFinished == 0">未处理</el-tag>
            <el-tag type="success" v-if="scope.row.isFinished == 1">已成交</el-tag>
            <el-tag type="warning" v-if="scope.row.isFinished == 2">未成交</el-tag>
            <el-tag type="danger" v-if="scope.row.isFinished == 3">退药</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="成交价" prop="price" align="center">
          <template slot-scope="scope">
            <el-input-number
              :disabled="scope.row.change || scope.row.isFinished == 1 || scope.row.isFinished == 2 || scope.row.isFinished == 3"
              style="width: 100px;" :min="0" :max="9999999" v-if="scope.row.price != null && scope.row.price != undefined"
              controls-position="right" v-model="scope.row.price" placeholder=""></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="note" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input v-if="scope.row.price != null && scope.row.price != undefined"
              :readonly="scope.row.isFinished == 1 || scope.row.isFinished == 2 || scope.row.isFinished == 3"
              v-model="scope.row.note"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="操作" prop="drugPrice" align="center">
          <template slot-scope="scope">
            <el-button size="mini"
              v-if="scope.row.unShow != true && scope.row.isFinished != 1 && scope.row.isFinished != 3" type="text"
              style="color: #ffba00" @click="editList(scope)">取药</el-button>
            <el-button size="mini" v-if="scope.row.unShow && scope.row.isFinished != 3" type="text" style="color: #67C23A"
              @click="handleTakeDrug(scope.row)">成交</el-button>
            <el-button size="mini" v-if="scope.row.unShow" type="text" style="color: #ff2727"
              @click="unHandleTakeDrug(scope.row)">未成交</el-button>
            <el-button size="mini" v-if="scope.row.unShow" type="text" style="color: #409EFF"
              @click="handleCancel(scope.row)">取消</el-button>
            <el-button size="mini" v-if="scope.row.isFinished == 1" type="text" style="color: #ff2727"
              @click="handBack(scope.row)">退药</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems"></add-items>
  </div>
</template>
<script>
import { getListData, takeDrug, getBackDrug } from "@/api/funcdept/pharmacy/medicine_prescribe";
import addItems from './add'
export default {
  components: { addItems, },
  props: [],
  data() {
    return {
      usernameData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '医生姓名',//第二列标题
        url: '/drugstore/prescribe/getDoctor',//请求连接
        bindValue: '',
        firstName: 'inputCode',
        lastName: 'username',
        queryData: 'key',
      },
      drugIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '名称',//第二列标题
        url: '/drugstore/drug/getSelectData',//请求连接
        bindValue: '',
        queryData: 'key',
      },
      prpPrice: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        date: [],
        patientname: '',
        usernameData: '',
        phone: '',
        username: '',
        isFinished: '',
      },
      // 表格展示数据
      tableList: [
      ],
      // 显示模态框
      showExam: false,
      showUpload: false,
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getList();
  },
  mounted() { },
  methods: {
    usernameDataChange(value) {
      this.queryParams.username = value.username
      this.usernameData.bindValue = value.username
    },
    drugIdDataChange(value) {
      this.queryParams.idLabtype = value
      this.drugIdData.bindValue = value
    },
    //取消按钮
    handleCancel(row) {
      for (var i in this.tableList) {
        //判断 能否更改备注以及价格
        this.tableList[i].change = true
        //判断是否展示展开的功能键
        this.tableList[i].unShow = false
        //让未处理状态的那一行价格变为空
        if (this.tableList[i].isFinished == 0) {
          this.tableList[i].price = null
          this.tableList[i].note = ''
      
        }
      }
    
      row.unShow = false
    },
    // 取药
    editList(scope) {
      for (var i in this.tableList) {
        //判断 能否更改备注以及价格
        this.tableList[i].change = true
        //判断是否展示展开的功能键
        this.tableList[i].unShow = false
        //让未处理状态的那一行价格变为空
        if (this.tableList[i].isFinished == 0) {
          this.tableList[i].price = null
          this.tableList[i].note = ''
   
        }
      }
      if (scope.row.price == null && scope.row.price == undefined) {
        // row.price = 0
        this.tableList[scope.$index].price = 0
      }

      //input框的内容
      // row.change = false
      // row.unShow = true
      this.tableList[scope.$index].change = false
      this.tableList[scope.$index].unShow = true
      this.tableList = JSON.parse(JSON.stringify(this.tableList))
    },
    //取药
    handleTakeDrug(row) {
      const data = row
      data.isFinished = 1
      takeDrug(data).then(response => {
        this.getList()
      })
    },
    //未取药
    unHandleTakeDrug(row) {
      const data = row

      if (data.note == '' || data.note == null) {
        this.$modal.msgWarning('请给未成交的记录填写备注')
      } else {
        data.isFinished = 2
        takeDrug(data).then(response => {
          this.getList()
        })
      }

    },
    // 处理
    handleeditRow() {
      this.$modal
        .confirm('确定要处理？')
        .then(function () {
          // return delPrinttype(ids);
          return;
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("处理成功");
        })
        .catch(() => { });
    },
    // 反处理
    handlenoeditRow() {
      this.$modal
        .confirm('确定要反处理？')
        .then(function () {
          // return delPrinttype(ids);
          return;
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("反处理成功");
        })
        .catch(() => { });
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
    // 搜索
    handleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置
    resetQuery() {

      this.usernameData.bindValue = ''
      this.drugIdData.bindValue = ''
      
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    //退药
    handBack(row) {
     
      const data = row
      this.$modal
        .confirm('是否确认退药？')
        .then(() => {
          getBackDrug(data).then(response => {
            this.$modal.msgSuccess("退药成功");
            this.getList()
          })

        })



    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          // return delPrinttype(ids);
          return;
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
  },
};
</script>
