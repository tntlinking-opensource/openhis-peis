<!-- 不合格样本 麦沃德科技 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检号">
        <el-input placeholder="请输入体检号搜索" v-model="queryParams.patientcode"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input placeholder="请输入姓名" v-model="queryParams.patientname"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchByTime">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" @click="addWindow()" plain v-hasPermi="['funcdept:sample:unqualifiedSamples:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeList()" :disabled="multiple" plain v-hasPermi="['funcdept:sample:unqualifiedSamples:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="handleExport" v-hasPermi="['funcdept:sample:unqualifiedSamples:export']">导出Excel</el-button>
      </el-col>
    </el-row>
    <!-- 表格数据 -->
    <div class="table-box">
      <el-table ref="tableData" :data="tableData" v-loading="loading" border :stripe="true" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
        <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
        <el-table-column label="性别" align="center">
          <template slot-scope="scope">
            {{ scope.row.idSex == 0 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="belowquestion" label="样本不合格原因" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.belowquestion == 1">溶血</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 2">脂血</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 3">凝血</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 4">多采集</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 5">容器不符</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 6">标本量错误</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 7">标识不清</el-tag>
            <el-tag v-else-if="scope.row.belowquestion == 8">TCT</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="note" label="备注" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="是否处理" align="center">
          <template slot-scope="scope">
            <el-tag size="small" v-if="scope.row.isHandled == true">已处理</el-tag>
            <el-tag type="danger" size="small" v-else>未处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" align="center"></el-table-column>
      </el-table>
    </div>
    <!-- 弹窗 -->
    <el-dialog :visible.sync="showAddDialog" title="添加" width="868px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :rules="rules" :model="queryParam" :inline="true" label-width="90px" hide-required-asterisk>
        <el-form-item label="体检号" prop="personcode">
          <el-input placeholder="请输入体检号回车查询" v-model="queryParam.personcode" style="width: 308px" @keyup.enter.native="handleQuerys"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input placeholder="请输入体检号回车查询后生成" v-model="queryParam.patientname" :disabled="true" style="width: 308px"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input placeholder="请输入体检号回车查询后生成" v-model="queryParam.idSex" :disabled="true" style="width: 308px"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input placeholder="请输入体检号回车查询后生成" v-model="queryParam.phone" style="width: 308px" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="录入时间">
          <el-date-picker :clearable="false" v-model="queryParam.createDate" type="datetime" placeholder="选择日期时间" style="width: 308px"></el-date-picker>
        </el-form-item>
        <el-form-item label="不合格原因" prop="belowquestion">
          <el-select v-model="queryParam.belowquestion" style="width: 308px">
            <el-option label="溶血" value="1" />
            <el-option label="脂血" value="2" />
            <el-option label="凝血" value="3" />
            <el-option label="多采集" value="4" />
            <el-option label="容器不符" value="5" />
            <el-option label="标本量错误" value="6" />
            <el-option label="标识不清" value="7" />
            <el-option label="TCT" value="8" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" placeholder="请输入内容" v-model="queryParam.note" :rows="5" style="width: 715px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button type="primary" plain @click="reset">重置</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>

<script>
import { DeleteList, getListData, getTjzData, saveOrUpdate } from '@/api/funcdept/sample/unqualified_samples'
import { delItems } from '@/api/basis/charge'

export default {
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 遮罩层
      loading: false,
      // 非多个禁用
      multiple: true,
      showAddDialog: false,
      total: 0,
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        patientname: undefined,
      },
      //弹窗Form
      queryParam: {
        personcode: undefined,
        patientname: undefined,
        idSex: undefined,
        phone: undefined,
        createDate: undefined,
        belowquestion: undefined,
        note: undefined,
      },
      tableData: [],
      // 新增键值对
      patientcode: undefined,
      patientname: undefined,
      idSex: undefined,
      phone: undefined,
      createDate: new Date(),
      belowquestion: undefined,
      note: undefined,
      // 表单校验
      rules: {
        personcode: [{ required: true, message: '体检号不能为空', trigger: 'change' }],
        belowquestion: [{ required: true, message: '请选择不合格原因', trigger: 'change' }],
      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 分页查询
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //保存
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          let qt = this.queryParam
          qt.patientcode = this.queryParam.personcode
          saveOrUpdate(qt).then((response) => {
            this.$modal.msgSuccess('添加成功!')
            this.loading = false
            this.cancel()
            this.getList()
          })
        }
      })
    },
    //弹窗中体检号回车搜索
    handleQuerys() {
      this.loading = true
      getTjzData({ ...this.queryParam, key: true }).then((response) => {
        this.queryParam.idSex = response.data.idSex == 0 ? '男' : '女'
        this.queryParam.personcode = response.data.patientcode
        this.queryParam.patientname = response.data.patientname
        this.queryParam.phone = response.data.phone
        this.loading = false
      })
    },
    //新增
    addWindow() {
      this.showAddDialog = true
      this.reset()
    },
    //取消
    cancel() {
      this.showAddDialog = false
    },
    //重置
    reset() {
      this.queryParam.personcode = undefined
      this.queryParam.patientname = undefined
      this.queryParam.idSex = undefined
      this.queryParam.phone = undefined
      this.queryParam.createDate = this.$getDate()
      this.queryParam.note = undefined
      this.queryParam.belowquestion = undefined
    },
    resetQuery() {
      this.queryParams.patientcode = undefined
      this.queryParams.patientname = undefined
      this.searchByTime()
    },
    //删除
    removeList() {
      let ids = this.ids.join(',')
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return DeleteList(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    //搜索
    searchByTime() {
      this.queryParams.current = 1
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //导出
    handleExport() {
      this.download('/handle/visitMain/export', this.queryParams, `不合格样本.xlsx`)
    },
  },
}
</script>
