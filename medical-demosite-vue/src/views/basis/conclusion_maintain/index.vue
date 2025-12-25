<!-- 总检结论词维护  开发人：麦沃德科技矢北\予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item prop="idDepart" label="科室名称">
        <input-select :selectData="labTypeData" @change="labTypeChange"></input-select>
      </el-form-item>
      <el-form-item prop="inputCode" label="输入码">
        <el-input style="width: 230px" v-model="queryParams.inputCode" placeholder="请输入输入码"></el-input>
      </el-form-item>
      <el-form-item prop="auditStatus" label="审核状态">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择">
          <el-option style="width: 230px" v-for="options in typeOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" v-hasPermi="['search:conclusionMaintain:add']" plain size="mini" icon="el-icon-plus" @click="showDialog">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" v-hasPermi="['search:conclusionMaintain:edit']" plain size="mini" icon="el-icon-edit" :disabled="single" @click="editList">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" v-hasPermi="['search:conclusionMaintain:delete']" plain size="mini" :disabled="multiple" @click="handleDelete" icon="el-icon-delete">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" v-hasPermi="['search:conclusionMaintain:review']" plain size="mini" @click="review" :disabled="multiple" icon="el-icon-edit">审核 </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="warning" @click="handleUpload" plain size="mini"><i class="el-icon-upload el-icon--right"></i>上传 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--  表格部分 -->
    <div class="table-box">
      <el-table ref="tableList" style="width: 100%" border :data="tableList" v-loading="loading" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="depName" label="科室" align="center" />
        <el-table-column prop="name" label="结论词" align="center" show-overflow-tooltip />
        <el-table-column prop="inputCode" label="输入码" align="center" show-overflow-tooltip />
        <el-table-column prop="fzx" label="分中心" align="center">
          <!-- <template slot-scope="scope">
            <el-tag v-if="scope.row.isPublic == 1">公共</el-tag>
            <el-tag v-if="scope.row.isPublic == 0" type="danger">非公共</el-tag>
          </template> -->
        </el-table-column>
        <el-table-column prop="creater" label="维护人" align="center" />
        <el-table-column prop="auditStatus" label="审核状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.auditStatus == 1" type="success">已审核</el-tag>
            <el-tag v-else-if="scope.row.auditStatus == 0" type="danger">未审核</el-tag>
            <span v-else> </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="editList(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加修改弹窗部分 -->
    <el-dialog :title="title" :visible.sync="open" class="add-items" width="900px" :close-on-click-modal="false" append-to-body>
      <h3>结论词设置</h3>
      <el-form v-loading="popLoading" ref="form" :rules="rules" :model="form" :inline="true" label-width="102px" hide-required-asterisk>
        <el-form-item prop="name" label="结论词 ">
          <el-input v-model="form.name" placeholder="请输入结论词" style="width: 308px"></el-input>
        </el-form-item>
        <el-form-item prop="depName" label="科室 ">
          <input-select ref="select1" :selectData="selectData1" :initialValue="form.depName" @change="selectChange1" style="width: 308px"></input-select>
        </el-form-item>
        <el-form-item prop="isLong" label="是否进总检">
          <el-select v-model="form.isLong" style="width: 308px" placeholder="请选择">
            <el-option :value="0" label="否"></el-option>
            <el-option :value="1" label="是"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="isPublic" label="是否进公共">
          <el-checkbox border v-model="form.isPublic" style="width: 308px" @change="isPublicChange" placeholder="请选择"> </el-checkbox>
        </el-form-item>
        <el-form-item label="分中心:" prop="fzx">
          <input-select ref="select2" placeholder="请输入" :disabled="form.isPublic == 1" :selectData="selectData2" @change="selectChange2" :initialValue="form.fzx" style="width: 308px"></input-select>
        </el-form-item>
        <el-form-item prop="isInCommonUse" label="是否常用">
          <el-select v-model="form.isInCommonUse" style="width: 308px" placeholder="请选择">
            <el-option :value="0" label="否"></el-option>
            <el-option :value="1" label="是"></el-option>
          </el-select>
        </el-form-item>
        <h3>建议维护</h3>
        <el-form-item label="总检建议" prop="suggestion">
          <textarea v-model="form.suggestion" placeholder="请输入总检建议" style="width: 308px; resize: vertical; "></textarea>
        </el-form-item>
        <el-form-item label="疾病解释" prop="depiction">
          <el-input v-model="form.depiction" placeholder="请输入疾病解释" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="团检建议" prop="suggestiongroup">
          <el-input v-model="form.suggestiongroup" placeholder="请输入团检建议" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="健康建议" prop="advice">
          <el-input v-model="form.advice" placeholder="请输入健康建议" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="饮食指导" prop="dietguide">
          <el-input v-model="form.dietguide" placeholder="请输入饮食指导" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="运动指导" prop="sportguide">
          <el-input v-model="form.sportguide" placeholder="请输入运动指导" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="健康知识" prop="knowledge">
          <el-input v-model="form.knowledge" placeholder="请输入健康知识" resize="none" type="textarea" style="width: 308px" />
        </el-form-item>
        <el-form-item label="备  注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" resize="none" type="textarea" clearable style="width: 308px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button type="primary" @click="reset" plain>重置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBasConclusion, addBasConclusion, delBasConClusion, detailBasConclusion, reviewConclusion } from '@/api/basis/conclusion_maintain'
export default {
  name: 'ConclusionMaintain',
  data() {
    return {
      auditStatus: [],
      ids: [],
      loading: true,
      popLoading: false,
      // 科室名称
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
        bindValue: '',
      },
      //分中心
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '分中心码',
        value: '分中心名称',
        queryData: 'key',
        url: '/sell/createorder/getBranchData',
        firstName: 'jm',
        secondName: 'fzx',
      },
      // 科室名称
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
      },
      popData: undefined,
      able: false,
      // 显示搜索条件
      showSearch: true,
      multiple: true,
      single: true,
      // 是否显示弹出层
      open: false,
      tableList: [],
      typeOptions: [
        { id: 0, text: '未审核' },
        { id: 1, text: '已经审核' },
      ],
      // 对话框表单内容
      form: {
        name: undefined,
        depName: undefined,
        divisionId: undefined,
        isLong: 0,
        isPublic: true,
        fzx: undefined,
        isInCommonUse: 0,
        suggestion: undefined,
        depiction: undefined,
        suggestiongroup: undefined,
        advice: undefined,
        dietguide: undefined,
        sportguide: undefined,
        knowledge: undefined,
        note: undefined,
      },
      patientcode: undefined,
      ///查询参数
      queryParams: {
        current: 1,
        size: 20,
        inputCode: null,
        idDepart: null,
        auditStatus: undefined,
      },
      total: 0,
      // 表单效验
      rules: {
        name: [{ required: true, message: '结论词名称不能为空', trigger: 'change' }],
        depName: [{ required: true, message: '科室不能为空', trigger: 'change' }],
      },
      formLabelWidth: '120px',
      title: '',
      ///分中心选项
      fzxOptions: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //审核
    review() {
      const auditStatus = this.auditStatus
      const ids = this.ids
      for (var i = 0; i < auditStatus.length; i++) {
        if (auditStatus[i] != 0) {
          this.message()
          return
        }
        this.$modal
          .confirm('您确认已审核该数据项？')
          .then(function () {
            return reviewConclusion(ids)
          })
          .then(() => {
            this.getList()
            this.$modal.msgSuccess('审核成功')
          })
          .catch(() => { })
      }
    },
    // 科室选项
    labTypeChange(value) {
      this.queryParams.depName = value.name
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.auditStatus = selection.map((item) => item.auditStatus)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delBasConClusion(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 添加按钮
    showDialog() {
      this.popData = undefined
      this.title = '添加'
      this.open = true
      this.reset()
    },
    // 科室名称选择结果
    selectChange1(value) {
      this.form.depName = value.name
      this.form.divisionId = value.id
      this.selectData1.bindValue = value.name
    },
    // 科室名称选择结果
    selectChange2(value) {
      this.form.fzxIds = value.branchId
      this.form.fzx = value.fzx
    },
    // 是否公共变化
    isPublicChange() {
      if (this.form.isPublic) {
        this.selectData1.bindValue = undefined
        this.form.depName = undefined
      }
    },
    // 查询列表
    getList() {
      this.loading = true
      listBasConclusion(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 编辑
    editList(row) {
      const id = row.id || this.ids
      this.popLoading = true
      this.open = true
      this.reset()
      this.title = '编辑'
      this.popLoading = false
      detailBasConclusion(id).then((response) => {
        if (response.data.isPublic == 1) {
          response.data.isPublic = true
        } else {
          response.data.isPublic = false
        }
        if (response.data.fzx) {
          let fzxArray = response.data.fzx.split(',')
          for (let i = fzxArray.length - 1; i >= 0; i--) {
            if (fzxArray[i] == '') {
              this.$delete(fzxArray, i)
            }
          }
          response.data.fzx = fzxArray.join(',')
        }
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.form = response.data
      })
    },
    // 重置数据
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        return
      }
      this.resetForm('form')
      this.form.isPublic = this.form.isPublic == 1 ? true : false
      // this.selectData1.bindValue = undefined
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 健康报告
    handleHealth() {
      let routeUrl = this.$router.resolve({
        name: 'GroupHealthReport',
        query: { analyzeId: 1614898629045108737, orderId: 23, dh: 0 },
      })
      window.open(routeUrl.href)
    },

    ///搜素
    handleQuery() {
      this.queryParams.current = 1
      if (this.queryParams.date1) {
        this.queryParams.startTime1 = this.queryParams.date1[0] + ' 00:00:00'
        this.queryParams.endTime1 = this.queryParams.date1[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime1 = undefined
        this.queryParams.endTime1 = undefined
      }
      if (this.queryParams.date2) {
        this.queryParams.startTime2 = this.queryParams.date2[0] + ' 00:00:00'
        this.queryParams.endTime2 = this.queryParams.date2[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime2 = undefined
        this.queryParams.endTime2 = undefined
      }
      this.getList()
    },
    // 是否公共变化
    isPublicChange() {
      if (this.form.isPublic) {
        this.form.fzxIds = undefined
      }
    },
    handleUpload() { },
    // 提交按钮
    submitForm() {
      if (this.form.isPublic == true) {
        this.form.isPublic = 1
      } else {
        this.form.isPublic = 0
      }

      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            addBasConclusion(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addBasConclusion(this.form).then((response) => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    ///消息提醒
    message() {
      this.$alert('请选择审核状态为待审核的记录！', '错误提示', {
        confirmButtonText: '确定',
        callback: (action) => {
          this.$message({
            type: 'info',
            message: '已取消审核',
          })
        },
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.table-box {
  margin-top: 20px;
}

h3 {
  font-weight: 600;
}
</style>
