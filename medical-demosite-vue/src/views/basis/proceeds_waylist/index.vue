<!-- 收银收款方式 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="输入码:" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:proceedsWaylist:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleUpdate" :disabled="single" v-hasPermi="['basis:proceedsWaylist:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['basis:proceedsWaylist:remove']">删除 </el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" @click="handleJd" v-hasPermi="['basis:proceedsWaylist:Jd']">金蝶数据更新 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="收费方式名称" min-width="100px" prop="paywayName" align="center" show-overflow-tooltip />
        <el-table-column label="输入码" min-width="100px" prop="inputCode" align="center" show-overflow-tooltip />
        <el-table-column label="个检费用项目" min-width="100px" prop="thingKingdeePaywayname" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.thingKingdeePaywayname == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="个检费用项目-编号" prop="thingKingdeeNumber" min-width="130px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.thingKingdeeNumber == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="个检费用项目-开启状态" prop="thingKingdeeUseStatus" min-width="160px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.thingKingdeeUseStatus == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="团体费用项目" prop="groupKingdeePaywayname" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.groupKingdeePaywayname == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="团体费用项目-编号" prop="groupKingdeeNumber" min-width="130px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.groupKingdeeNumber == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="团体费用项目-开启状态" prop="groupKingdeeUseStatus" min-width="160px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.groupKingdeeUseStatus == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="记账结算项目" prop="posKingdeePaywayname" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.posKingdeePaywayname == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="记账结算-编号" prop="posKingdeeNumber" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.posKingdeeNumber == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="记账结算-开启状态" prop="posKingdeeUseStatus" min-width="140px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.posKingdeeUseStatus == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="上传公司名" prop="kingdeeCompany" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.kingdeeCompany == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="负责业务员" prop="kingdeeSaleer" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.kingdeeSaleer == 1">维护</el-tag>
            <el-tag type="danger" v-else>未维护</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:chargeCategory:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:chargeCategory:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 检查项目对话框 -->
    <el-dialog title="查看结论词" :visible.sync="showExam" width="1000px" append-to-body>
      <div style="min-height: 400px">
        <el-table border v-loading="loading" :data="examList" stripe>
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="结论词" prop="name" align="center" show-overflow-tooltip />
          <el-table-column label="科室" prop="jcxmlx" align="center" show-overflow-tooltip />
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="resetQuery">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems"></add-items>
  </div>
</template>
<script>
import { listDictpayway, delDictpayway, upDataJD } from '@/api/basis/proceedswaylist'
import addItems from './add'
export default {
  name: 'Proceeds_waylist',
  components: { addItems },
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
      },
      // 表格展示数据
      tableList: [],
      examList: {},
      // 显示模态框
      showExam: false,
      showUpload: false,
      selection: undefined,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.selection = selection[0]
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 有查询条件列表
    getList() {
      this.loading = true
      listDictpayway(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 取消按钮
    cancel() {
      this.showExam = false
      this.reset()
    },
    //金蝶
    handleJd() {
      upDataJD().then((response) => {
        this.$modal.msgSuccess('更新成功')
      })
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delDictpayway(ids)
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      let obj = {}
      if (row.id) {
        obj = row
      } else {
        obj = this.selection
      }
      this.$refs.addItems.handleUpdate(obj)
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // updatePrinttype(this.form).then(response => {
            this.$modal.msgSuccess('修改成功')
            this.showExam = false
            // 	this.getList();
            // });
          } else {
            // addPrinttype(this.form).then(response => {
            this.$modal.msgSuccess('添加成功')
            this.showExam = false
            // 	this.getList();
            // });
          }
        }
      })
    },
    // 查看结论词
    handleView(row) {
      this.examList = [
        {
          sex: 2,
          xb: '通用',
          ksmc: '健康问卷',
          lx: 2,
          tzxx: 0,
          lxmc: '综合',
          wjzsx: 0,
          jcxmlx: '全科',
          name: '院外收费',
          wjzxx: 0,
          tzsx: 0,
          cid: '1',
        },
      ]
      this.showExam = true
    },
  },
}
</script>
