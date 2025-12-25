<!-- 档案合并 麦沃德科技 开发人:清风 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 搜索栏 -->
    <el-form size="small" ref="queryForm" :model="queryParams" :inline="true" label-width="80px;" class="no-margin-bottom">
      <el-form-item label="姓名" prop="patientname" style="margin-right: 16px">
        <el-input placeholder="请输入姓名" style="width: 230px" v-model="queryParams.patientname" clearable @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone" style="margin-right: 16px">
        <el-input placeholder="请输入联系电话" style="width: 230px" v-model="queryParams.phone" clearable @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode" style="margin-right: 16px">
        <el-input placeholder="请输入体检号" style="width: 230px" v-model="queryParams.patientcode" clearable @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno" style="margin-right: 16px">
        <el-input placeholder="请输入身份证号" style="width: 230px" v-model="queryParams.idcardno" clearable @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item style="margin-right: 0">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="mergeWindow()" v-hasPermi="['custservice:membershipManagement:fileConsolidation:merge']">合并 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="editWindow()" v-hasPermi="['custservice:membershipManagement:fileConsolidation:edit']">编辑 </el-button>
      </el-col>
    </el-row>
    <!-- 表格数据 -->
    <div class="table-box">
      <el-table ref="tableData" :data="tableData" v-loading="loading" border stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="patientarchiveno" label="档案号" min-width="200" align="center"></el-table-column>
        <el-table-column prop="patientname" label="姓名" min-width="120" align="center"></el-table-column>
        <el-table-column prop="idSex" label="性别" min-width="100" align="center">
          <template slot-scope="scope">
            {{ ['男', '女', '通用'][scope.row.idSex] }}
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" min-width="100" align="center"></el-table-column>
        <el-table-column prop="phone" label="电话" min-width="160" align="center"></el-table-column>
        <el-table-column prop="idcardno" label="身份证号" min-width="200" align="center"></el-table-column>
        <el-table-column prop="idMarriage" label="婚姻" min-width="140" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.idMarriage == 1">未婚</div>
            <div v-else-if="scope.row.idMarriage == 2">已婚</div>
            <div v-else-if="scope.row.idMarriage == 3">离异</div>
            <div v-else-if="scope.row.idMarriage == 4">丧偶</div>
            <div v-else-if="scope.row.idMarriage == 5">其他</div>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址" min-width="300" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="resarea" label="籍贯" min-width="140" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="birthdate" label="出生日期" min-width="140" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="patientclass" label="会员等级" min-width="140" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" min-width="140" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="queryWindow(scope.row)" v-hasPermi="['custservice:membershipManagement:fileConsolidation:query']">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog id="dialogBody" title="编辑" class="add-items" :visible.sync="open" width="400px" height="auto" append-to-body :close-on-click-modal="false">
      <el-form size="small" :inline="true" label-width="80px" :model="editInput">
        <el-form-item label="姓名">
          <el-input placeholder="请输入姓名" style="width: 260px" v-model="editInput.patientname"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input placeholder="请输入电话" style="width: 260px" v-model="editInput.phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onOk">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <queryItems ref="queryItems"></queryItems>
    <mergeItems ref="mergeItems" @getList="getList"></mergeItems>
  </div>
</template>

<script>
import queryItems from './query'
import mergeItems from './merge'
import { getListData, saveOrUpdate } from '@/api/custservice/membership_management/file_consolidation/file_consolidation'
export default {
  components: { queryItems, mergeItems },
  data() {
    return {
      open: false,
      editInput: {
        id: '',
        phone: '',
        patientname: '',
      },
      loading: false,
      ids: [], //选中数组
      single: true, //单条禁用
      multiple: false, //多条禁用
      total: 0, //总数
      queryParams: {
        current: 1, //初始页数
        size: 20, //页值
        patientname: '', //体检姓名
        phone: '', //联系电话
        patientcode: '', //体检号
        idcardno: '',
      },
      tableData: [],
      form: {
        patientname: '', //体检姓名
        phone: '', //联系电话
        patientcode: '', //体检号
      },
      selection: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    //合并
    mergeWindow() {
      let multiple = this.multiple
      let idss = this.ids
      this.$refs.mergeItems.mergeWindow(multiple, idss)
    },
    //编辑
    editWindow() {
      this.open = true
      this.editInput.id = this.selection.id
      this.editInput.phone = this.selection.phone
      this.editInput.patientname = this.selection.patientname
    },
    //关闭
    onCancel() {
      this.open = false
    },
    //查看
    queryWindow(row) {
      this.$refs.queryItems.queryWindow(row.patientarchiveno)
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    //弹窗提交
    onOk() {
      saveOrUpdate(this.editInput).then((response) => {
        this.$modal.msgSuccess('编辑成功!')
        this.getList()
        this.open = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
        this.multiple = false
      } else if (selection.length == 2) {
        this.selection = {}
        this.multiple = true
      } else {
        this.multiple = !selection.length
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
  },
}
</script>
