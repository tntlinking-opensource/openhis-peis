<!-- PACS项目检查添加界面 开发人：麦沃德科技矢北/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" class="PACS-inspect" width="85%" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" size="mini" :inline="true" hide-required-asterisk v-loading="formLoading">
      <el-form-item prop="examitemName" label="名称">
        <el-input v-model="form.examitemName" clearable placeholder="请输入项目名称" @change="nameChange"></el-input>
      </el-form-item>
      <el-form-item prop="inputCode" label="输入码">
        <el-input v-model="form.inputCode" clearable placeholder="系统自动生成"></el-input>
      </el-form-item>
      <el-form-item prop="fMale" label="男/女标示">
        <el-select v-model="form.fMale" style="width: 220px" placeholder="请选择">
          <el-option :value="item.id" :label="item.text" v-for="item in xbOptions" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="type" label="类别">
        <el-select v-model="form.type" style="width: 220px" placeholder="请选择">
          <el-option :value="item.id" :label="item.text" v-for="item in tjlxOptions" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="deptName" label="科室">
        <input-select :selectData="deptData" :isTrim="true" @change="deptChange"></input-select>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="addSubRow">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="editSubRow">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" icon="el-icon-delete" @click="removeRow">删除 </el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table ref="table" border v-loading="tableLoading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="顺序" min-width="60" prop="orderindex" align="center" />
        <el-table-column label="体征词名称" min-width="160" prop="name" align="center" show-overflow-tooltip />
        <el-table-column label="是否阳性结果" width="100" prop="isPositive" align="center">
          <template slot-scope="scope">
            <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isPositive == 1"><i class="el-icon-check"></i> </el-tag>
            <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
          </template>
        </el-table-column>
        <el-table-column label="体征详细描述" min-width="180" prop="bodyDetail" align="center" show-overflow-tooltip />
        <el-table-column label="结论词" min-width="120" prop="resultName" align="center" show-overflow-tooltip />
        <el-table-column label="可疑疾病重症级0到9级" min-width="100" prop="intensiveLevel" align="center" />
        <el-table-column label="默认" width="100" prop="isDefault" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isDefault == 1"><i class="el-icon-check"></i> </el-tag>
            <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
    <!-- 增加体征词 -->
    <el-dialog title="增加" :visible.sync="signOpen" width="566px" style="min-height: 300px" append-to-body :close-on-click-modal="false">
      <el-form ref="signForm" :model="signForm" :rules="signRules" :inline="true" label-width="160px" hide-required-asterisk>
        <el-form-item prop="orderindex" label="顺序">
          <el-input-number v-model="signForm.orderindex" controls-position="right" style="width: 350px" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item prop="name" label="体征词名称">
          <el-input v-model="signForm.name" clearable placeholder="请输入体征词名称" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item prop="isPositive" label="是否阳性结果">
          <el-checkbox v-model="signForm.isPositive" border :true-label="1" :false-label="0" style="width: 350px"></el-checkbox>
        </el-form-item>
        <el-form-item prop="bodyDetail" label="体征详细描述">
          <el-input type="textarea" rows="5" v-model="signForm.bodyDetail" placeholder="请输入体征详细描述" clearable style="width: 350px" />
        </el-form-item>
        <el-form-item prop="resultName" label="结论词">
          <input-select :selectData="resultData" :isTrim="true" :selectWidth="350" @change="resultChange"></input-select>
        </el-form-item>
        <el-form-item prop="intensiveLevel" label="可疑疾病重症级0到9级">
          <el-input-number v-model="signForm.intensiveLevel" controls-position="right" style="width: 350px" :min="0" :max="9" @change="intChange"></el-input-number>
        </el-form-item>
        <el-form-item prop="isDefault" label="默认">
          <el-checkbox v-model="signForm.isDefault" border :true-label="1" :false-label="0" style="width: 350px"></el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubOk">确 定</el-button>
        <el-button @click="signOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>
<script>
import { getExamData, getSignList, saveExam, updateExam } from '@/api/PACS/basicPACS/inspect.js'
import pinyin from '@/utils/pinyin.js'
export default {
  data() {
    return {
      // 表单参数
      form: {},
      signForm: {},
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      formLoading: false,
      tableLoading: false,
      // 弹出层临时数据
      popData: undefined,
      popList: undefined,
      // 是否显示弹出层
      open: false,
      signOpen: false,
      // 表格数据
      tableList: [],
      // 删除表格数据
      removeList: [],
      // 选中数组
      selectRows: [],
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 性别
      xbOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      // 体检类型
      tjlxOptions: [
        {
          id: 0,
          text: '健康体检',
        },
        {
          id: 1,
          text: '职业体检',
        },
        {
          id: 2,
          text: '综合',
        },
      ],
      // 科室名称
      deptData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '科室',
        url: '/pacs/exam/dept/list',
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
      },
      // 结论词
      resultData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '科室',
        url: '/pacs/exam/conclusion/list',
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
      },
      // 表单校验
      rules: {
        examitemName: [{ required: true, message: '名称不能为空', trigger: 'change' }],
        inputCode: [{ required: true, message: '输入码不能为空', trigger: 'change' }],
        fMale: [{ required: true, message: '男/女标示不能为空', trigger: 'change' }],
        type: [{ required: true, message: '类别不能为空', trigger: 'change' }],
        deptName: [{ required: true, message: '科室不能为空', trigger: 'change' }],
      },
      signRules: {
        name: [{ required: true, message: '体征词名称不能为空', trigger: 'change' }],
      },
    }
  },
  methods: {
    // 添加
    handleAdd() {
      this.popData = undefined
      this.popList = undefined
      this.reset()
      this.title = '新增检查项目'
      this.open = true
    },
    // 编辑
    handleUpdate(id) {
      this.popData = undefined
      this.popList = undefined
      this.reset()
      this.formLoading = true
      this.tableLoading = true
      this.open = true
      this.title = '编辑检查项目'
      this.getExamData(id)
      this.getSignList(id)
    },
    // 获取检查项目详情
    getExamData(id) {
      getExamData(id).then((res) => {
        this.popData = JSON.parse(JSON.stringify(res.data))
        this.form = res.data
        this.deptData.bindValue = res.data.deptName
        this.formLoading = false
      })
    },
    // 获取结论词列表
    getSignList(id) {
      getSignList({ id: id }).then((res) => {
        var list = res.data
        list.forEach((el, index) => {
          el.temp_id = index + 1
          el._state = 'modified'
        })
        this.popList = JSON.parse(JSON.stringify(list))
        this.tableList = list
        this.tableLoading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectRows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && (col.label == '操作' || col.type == 'selection')) {
        return
      }
      var isSelect = false
      this.selectRows.forEach((el) => {
        if (el == row.id) {
          isSelect = true
        }
      })
      if (isSelect) return
      this.$refs.table.clearSelection()
      this.$refs.table.toggleRowSelection(row)
      this.selectRows = [row]
    },
    // 重置
    reset() {
      this.removeList = []
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.deptData.bindValue = this.popData.deptName
      } else {
        this.form = {
          examitemName: undefined,
          inputCode: undefined,
          fMale: 2,
          type: 2,
          divisionId: undefined,
          deptName: undefined,
        }
        this.deptData.bindValue = undefined
      }
      if (this.popList && this.popList.length) {
        this.tableList = JSON.parse(JSON.stringify(this.popList))
      } else {
        this.tableList = []
      }
      this.resetForm('form')
    },
    // 名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 科室选项
    deptChange(value) {
      this.form.divisionId = value.id
      this.form.deptName = value.name
      this.deptData.bindValue = value.name
    },
    // 新增体征词
    addSubRow() {
      this.signReset()
      this.signOpen = true
    },
    // 编辑体征词
    editSubRow() {
      this.signReset()
      this.signForm = JSON.parse(JSON.stringify(this.selectRows[0]))
      this.resultData.bindValue = this.signForm.resultName
      this.signOpen = true
    },
    // 删除体征词
    removeRow() {
      this.selectRows.forEach((el) => {
        this.tableList.forEach((item, i) => {
          if (item.temp_id == el.temp_id) {
            this.$delete(this.tableList, i)
            if (item._state == 'modified') {
              item._state = 'removed'
              this.removeList.push(item)
            }
          }
        })
      })
    },
    // 保存体征词
    onSubOk() {
      this.$refs['signForm'].validate((valid) => {
        if (valid) {
          if (this.signForm.temp_id) {
            var index = this.tableList.findIndex((item) => {
              if (item.temp_id == this.signForm.temp_id) {
                return true
              }
            })
            this.$set(this.tableList, index, JSON.parse(JSON.stringify(this.signForm)))
          } else {
            if (this.tableList.length) {
              this.signForm.temp_id = Number(this.tableList[this.tableList.length - 1].temp_id) + 1
            } else {
              this.signForm.temp_id = 1
            }
            this.tableList.push(JSON.parse(JSON.stringify(this.signForm)))
          }
          this.signOpen = false
          this.signReset()
        }
      })
    },
    // 重置体征词
    signReset() {
      this.signForm = {
        orderindex: 1,
        name: undefined,
        isPositive: 0,
        bodyDetail: undefined,
        resultId: undefined,
        resultName: undefined,
        intensiveLevel: 0,
        isDefault: 0,
        _state: 'added',
      }
      this.resultData.bindValue = undefined
      this.resetForm('signForm')
    },
    // 结论词选项
    resultChange(value) {
      this.signForm.resultId = value.id
      this.signForm.resultName = value.name
      this.resultData.bindValue = value.name
    },
    // 保留整数
    intChange(val) {
      this.signForm.intensiveLevel = val.toFixed(0)
    },
    // 提交按钮
    submitForm() {
      // 判断grid是否存在相同的体征词名称
      var hash = {}
      for (var i in this.tableList) {
        var row = this.tableList[i]
        if (row.name != undefined && hash[row.name]) {
          this.$alert("存在相同的<font color='red'>" + row.name + '</font>体征词', '提醒', {
            dangerouslyUseHTMLString: true,
          })
          return
        }
        hash[row.name] = true
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var data = this.form
          data.fmale = data.fMale
          data.signs = [...this.removeList, ...this.tableList]
          data.signs.forEach((el) => {
            if (el.isDefault === null) {
              el.isDefault = 0
            }
            if (el.resultId === null) {
              delete el['resultId']
            }
            if (el.resultName === null) {
              delete el['resultName']
            }
          })
          if (this.form.id) {
            updateExam(data).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            saveExam(data).then(() => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>

<style lang="scss">
.PACS-inspect {
  .el-dialog {
    max-width: 1640px;
    height: 85%;

    .el-dialog__body {
      display: flex;
      flex-direction: column;

      .table-box {
        flex: 1;
      }
    }
  }
}
</style>
