<!-- 柜子管理 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="订单号:" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 160px" />
      </el-form-item>
      <el-form-item label="体检类型:" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择" clearable style="width: 160px;">
          <el-option label="健康" :value="0" />
          <el-option label="职业" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="单位:" prop="dwmc">
        <el-input v-model="queryParams.dwmc" placeholder="请输入单位" clearable style="width: 160px" />
      </el-form-item>
      <el-form-item label="柜子号:" prop="gzh">
        <el-input v-model="queryParams.gzh" placeholder="请输入柜子号" clearable style="width: 160px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
          v-hasPermi="['report:chestManage:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handleUpdate"
          v-hasPermi="['report:chestManage:edit']">编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['report:chestManage:del']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport"
          v-hasPermi="['report:chestManage:export']">导出Excel
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" @selection-change="handSelectionChange" :data="tableList" height="100%"
        stripe>
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="订单号" prop="ddh" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="tjlx" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.tjlx == 1">职业</span>
            <span v-if="scope.row.tjlx == 0">健康</span>
            <span v-if="scope.row.tjlx == 2">综合</span>
          </template>
        </el-table-column>
        <el-table-column label="单位名称" prop="dwmc" min-width="160px" align="center" show-overflow-tooltip />
        <el-table-column label="柜子号" prop="gzh" min-width="100px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <receive-items @getList="getList" ref="receiveItems"></receive-items>
  </div>
</template>
<script>
import { getListData, deleteList,exportList } from "@/api/report/chest_manage";
import receiveItems from './receive'


export default {
  name: "Chest_manage",
  components: { receiveItems },
  props: [],
  data() {
    return {
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
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
        chestNum: undefined
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
  mounted() {},
  methods: {

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
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.$refs.receiveItems.handleAdd()
    },

    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return deleteList(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
        .catch(() => { });
    },
    // 编辑
    handleUpdate() {
      const idd = this.ids
      
      this.$refs.receiveItems.handleUpdate(idd)
    },
    //选中表格发生变化
    handSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.auditStatus = selection.map(item => item.auditStatus)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },

    /** 导出按钮操作 */
    handleExport() {
      exportList('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
  },
};
</script>