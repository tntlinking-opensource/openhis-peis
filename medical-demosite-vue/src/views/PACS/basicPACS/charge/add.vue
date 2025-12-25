<!-- PACS收费项目-添加/编辑 开发人：麦沃德科技矢北/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" class="add-PACS-charge" width="95%" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :rules="rules" :model="form" size="mini" :inline="true" label-width="120px" hide-required-asterisk v-loading="formLoading">
      <el-form-item prop="examfeeitemName" label="收费项目名称">
        <el-input v-model="form.examfeeitemName" clearable style="width: 220px" placeholder="请输入收费项目名称" @input="nameChange"></el-input>
      </el-form-item>
      <el-form-item prop="sfxmsrm" label="收费项目输入码">
        <el-input v-model="form.sfxmsrm" style="width: 220px" placeholder="系统自动生成"></el-input>
      </el-form-item>
      <el-form-item prop="sysmanmark" label="是否启用">
        <el-select v-model="form.sysmanmark" style="width: 220px" placeholder="请选择">
          <el-option value="1" label="是"></el-option>
          <el-option value="0" label="否"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="examfeeitemNameprn" label="项目打印名称">
        <el-input v-model="form.examfeeitemNameprn" clearable style="width: 220px" placeholder="请输入项目打印名称"></el-input>
      </el-form-item>
      <el-form-item prop="xh" label="打印排列序号">
        <el-input-number v-model="form.xh" controls-position="right" style="width: 220px" @blur="xhBlur" />
      </el-form-item>
      <el-form-item prop="xb" label="性别">
        <el-select v-model="form.xb" style="width: 220px" placeholder="请选择" @change="XbChange">
          <el-option :value="item.id" :label="item.text" v-for="item in xbOptions" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="examfeeitemCode" label="接口代码">
        <el-input v-model="form.examfeeitemCode" clearable style="width: 220px" placeholder="请输入接口代码"></el-input>
      </el-form-item>
      <el-form-item prop="examfeeitemCodehm" label="检查部位">
        <el-tooltip effect="dark" :content="form.jcbwmc" placement="top" :disabled="!form.jcbwmc">
          <treeselect v-model="placeSelect" :multiple="true" :options="placeList" :normalizer="normalizer" style="width: 220px" placeholder="请选择检查部位" valueFormat="object" @input="placeChange" />
        </el-tooltip>
      </el-form-item>
      <el-form-item prop="departName" label="所属科室名称">
        <input-select :selectData="deptData" :isTrim="true" :selectWidth="220" @change="deptChange"></input-select>
      </el-form-item>
      <el-form-item prop="jcyy" label="检查意义" class="form-item">
        <el-input v-model="form.jcyy" clearable style="width: 100%" placeholder="请输入检查意义"></el-input>
      </el-form-item>
    </el-form>
    <div class="add-table">
      <el-form ref="queryForm" :model="queryParams" size="mini" :inline="true">
        <el-form-item prop="examitemName" label="检查项目名称">
          <el-input v-model="queryParams.examitemName" style="width: 220px" placeholder="请输入检查项目名称" @keyup.enter.native="handleQuery"></el-input>
        </el-form-item>
        <el-form-item prop="inputCode" label="检查项目输入码">
          <el-input v-model="queryParams.inputCode" style="width: 220px" placeholder="请输入检查项目输入码" @keyup.enter.native="handleQuery"></el-input>
        </el-form-item>
        <el-form-item style="margin-right: 0">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="flex">
        <div class="left-table">
          <div class="table-box">
            <el-table ref="itemTable" border size="mini" v-loading="itemLoading" :data="tableList" height="480px" @row-click="itemClick" @row-dblclick="selectOption" @selection-change="itemChange">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="检查项目名称" prop="examitemName" min-width="260" align="center" show-overflow-tooltip />
              <el-table-column label="性别" prop="fMale" min-width="80" align="center">
                <template slot-scope="scope">
                  <div v-for="item in xbOptions" :key="item.id">
                    <span v-if="item.id == scope.row.fMale">{{ item.text }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="类型" prop="type" min-width="120" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  {{ tjlx(scope.row.type) }}
                </template>
              </el-table-column>
              <el-table-column label="科室名称" prop="deptName" min-width="180" align="center" show-overflow-tooltip />
            </el-table>
          </div>
          <el-pagination background :pager-count="5" :current-page="queryParams.current" hide-on-single-page :page-sizes="[10, 20, 30, 50]" :page-size="queryParams.size" layout="sizes, prev, pager, next, jumper" :total="total" @size-change="itemSizeChange" @current-change="itemCurrentChange">
          </el-pagination>
        </div>
        <div class="center-btn">
          <el-button type="primary" plain size="mini" class="el-icon-arrow-right" :disabled="!itemRows.length" @click="selectOption"> </el-button>
          <el-button type="primary" plain size="mini" class="el-icon-arrow-left" :disabled="!selectRows.length" @click="removeOption" style="margin: 24px 0 0 !important"></el-button>
        </div>
        <div class="right-table">
          <el-table ref="selectTable" border v-loading="selectLoading" :data="selectList" height="480px" size="mini" @row-click="selectClick" @row-dblclick="removeOption" @selection-change="selectChange" :row-class-name="tableRowClassName">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="顺序" prop="orderIndex" width="120" align="center" class-name="sort-column">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.orderIndex" size="mini" style="width: 100%" controls-position="right" />
              </template>
            </el-table-column>
            <el-table-column label="检查项目名称" prop="examitemName" min-width="200" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="fMale" min-width="80" align="center">
              <template slot-scope="scope">
                <div v-for="item in xbOptions" :key="item.id">
                  <span v-if="item.id == scope.row.fMale">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="类型" prop="type" min-width="100" align="center">
              <template slot-scope="scope">
                {{ tjlx(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column label="科室名称" prop="deptName" min-width="160" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getChargeData, getSignList, saveItems, updateItems } from '@/api/PACS/basicPACS/charge.js'
import { getExamList } from '@/api/PACS/basicPACS/inspect.js'
import { getAutoCompleteData } from '@/api/PACS/basicPACS/place_maintain.js'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import pinyin from '@/utils/pinyin.js'
export default {
  components: { Treeselect },
  data() {
    return {
      // 表格数据-收费项目
      tableList: [],
      // 表格数据-已添加
      selectList: [],
      // 表格数据-已删除
      removeList: [],
      // 选中数据-收费项目
      itemRows: [],
      // 选中数据-已添加
      selectRows: [],
      // 遮罩层
      formLoading: false,
      itemLoading: false,
      selectLoading: false,
      // 标题
      title: '',
      // 开关
      open: false,
      // 表单
      form: {},
      // 总条数
      total: 0,
      //暂存数据
      popData: undefined,
      popList: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        examitemName: undefined,
        inputCode: undefined,
      },
      // 科室名称
      deptData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '名称',
        url: '/pacs/items/dept/list',
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
      },
      // 性别
      xbOptions: [
        { id: '0', text: '男' },
        { id: '1', text: '女' },
        { id: '2', text: '通用' },
      ],
      // 部位
      placeList: [],
      // 已选择部位
      placeSelect: [],
      // 表单校验
      rules: {
        examfeeitemName: [{ required: true, message: '收费项目名称不能为空', trigger: 'change' }],
        examfeeitemNameprn: [{ required: true, message: '项目打印名称不能为空', trigger: 'change' }],
        departName: [{ required: true, message: '所属科室名称不能为空', trigger: 'change' }],
      },
    }
  },
  methods: {
    // 添加
    handleAdd() {
      this.getAutoCompleteData()
      this.handleQuery()
      this.popData = undefined
      this.popList = []
      this.reset()
      this.title = '新增收费项目'
      this.open = true
    },
    // 编辑
    handleUpdate(id) {
      this.getAutoCompleteData()
      this.handleQuery()
      this.popData = undefined
      this.popList = []
      this.reset()
      this.formLoading = true
      this.selectLoading = true
      this.open = true
      this.title = '编辑收费项目'
      this.getChargeData(id)
      this.getSignList(id)
    },
    // 获取收费项目详情
    getChargeData(id) {
      getChargeData(id).then((res) => {
        this.popData = JSON.parse(JSON.stringify(res.data))
        this.form = res.data
        this.deptData.bindValue = res.data.departName
        this.formLoading = false
        if ((this.placeList && this.placeList.length > 0) && this.form.examfeeitemCodehm) {
          var list = []
          list = this.form.examfeeitemCodehm.split(",")
          for (var i in list) {
            this.placeSelect.push(this.getTreeName(this.placeList, list[i]))
          }
        }
      })
    },
    // 获取检查项目列表
    getSignList(id) {
      getSignList({ id: id }).then((res) => {
        var list = res.data
        list.forEach((el) => {
          el._state = 'modified'
        })
        this.popList = JSON.parse(JSON.stringify(list))
        this.selectList = list
        this.selectLoading = false
      })
    },
    // 获取检查项目
    getList() {
      this.itemLoading = true
      getExamList(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.itemLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.itemLoading = false
        })
    },
    // 重置搜索
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 搜素
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 获取部位数据
    getAutoCompleteData() {
      getAutoCompleteData().then((res) => {
        this.placeList = this.handleTree(res.data, 'id', 'pid')
        if ((this.placeList && this.placeList.length > 0) && this.form.examfeeitemCodehm) {
          var list = []
          list = this.form.examfeeitemCodehm.split(",")
          for (var i in list) {
            this.placeSelect.push(this.getTreeName(this.placeList, list[i]))
          }
        }
      })
    },
    // 获取部位数据指定项
    getTreeName(list, id) {
      for (var i in list) {
        if (list[i].id == id) {
          return list[i]
        } else if (list[i].children && list[i].children.length > 0) {
          let res = this.getTreeName(list[i].children, id)
          if (res) return res
        }
      }
    },
    // 转换部门数据结构
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.id,
        label: node.partName,
        children: node.children,
      }
    },
    // 检查部位改变
    placeChange(val) {
      var idList = val.map((item) => item.id)
      var nameList = val.map((item) => item.partName)
      this.form.examfeeitemCodehm = idList.join()
      this.form.jcbwmc = nameList.join()
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.sfxmsrm = pinyin(value)
    },
    // 体检类型
    tjlx(value) {
      if (value == 0) return '健康体检'
      else if (value == 1) return '职业体检'
      else if (value == 2) return '综合'
    },
    // 性别改变
    XbChange(value) {
      var sex = value
      var data = this.selectList
      if (data.length > 0) {
        for (var i in data) {
          var row = data[i]
          if (row.inspectId != null && typeof row.inspectId != undefined) {
            var xb = row.fMale
            if (sex == '2') {
              //通用
              if (sex != xb) {
                this.$modal.alertWarning('性别必须和子表一致')
                this.form.xb = xb
                return
              }
            } else {
              if (xb != '2') {
                if (sex != xb) {
                  this.$modal.alertWarning('性别必须和子表一致')
                  this.form.xb = xb
                  return
                }
              }
            }
          }
        }
      }
    },
    xhBlur(e) {
      this.form.xh = parseFloat(e.target.value).toFixed(0)
    },
    // 多选框选中数据-收费项目
    itemChange(selection) {
      this.itemRows = selection
    },
    // 多选框选中数据-选中
    selectChange(selection) {
      this.selectRows = selection
    },
    // 分页条数发生改变
    itemSizeChange(val) {
      if (this.queryParams.current * val > this.total) {
        this.queryParams.current = 1
      }
      this.queryParams.size = val
      this.getList()
    },
    // 分页页码发生改变
    itemCurrentChange(val) {
      this.queryParams.current = val
      this.getList()
    },
    // 科室选项
    deptChange(value) {
      this.form.idDepart = value.id
      this.form.departName = value.name
      this.deptData.bindValue = value.name
    },
    // 移入选项
    selectOption() {
      var rows = this.itemRows
      if (rows.length == 0) {
        this.$modal.alertWarning('请选择要添加的检查项目！')
        return
      }
      var xb = this.form.xb
      for (var i in rows) {
        var row = rows[i]
        var old = this.selectList.findIndex((item) => {
          if (row.id == item.inspectId) {
            return true
          }
        })
        if (old > -1) {
          continue
        }
        var sex = row.fMale
        if (xb != '2' && sex != '2') {
          if (sex != xb) {
            this.$modal.alertWarning('主表、子表性别必须一致')
            return
          }
        }
        var new_row = {}
        for (var attr in row) {
          if (attr != '_id' && attr != '_uid') {
            new_row[attr] = row[attr]
          }
        }
        new_row.inspectId = row.id
        new_row.id = ''
        new_row._state = 'added'
        this.selectList.push(new_row)
      }
    },
    // 移出选项
    removeOption(row, col) {
      if (col && col.label == '顺序') {
        return
      }
      this.selectRows.forEach((el) => {
        this.selectList.forEach((item, i) => {
          if (item.inspectId == el.inspectId) {
            this.$delete(this.selectList, i)
            if (item._state == 'modified') {
              item._state = 'removed'
              this.removeList.push(item)
            }
          }
        })
      })
    },
    //单击事件选中当前行
    itemClick(row, col) {
      if (col && (col.label == '操作' || col.type == 'selection')) {
        return
      }
      var isSelect = false
      this.itemRows.forEach((el) => {
        if (el.id == row.id) {
          isSelect = true
        }
      })
      if (isSelect) return
      this.$refs.itemTable.clearSelection()
      this.$refs.itemTable.toggleRowSelection(row)
      this.itemRows = [row]
    },
    //单击事件选中当前行
    selectClick(row, col) {
      if (col && col.label == '顺序') {
        return
      }
      var isSelect = false
      this.selectRows.forEach((el) => {
        if (el.inspectId == row.inspectId) {
          isSelect = true
        }
      })
      if (isSelect) return
      this.$refs.selectTable.clearSelection()
      this.$refs.selectTable.toggleRowSelection(row)
      this.selectRows = [row]
    },
    // 改变表格类名
    tableRowClassName({ row }) {
      if (!row.orderIndex) {
        return 'warning-row'
      }
      return ''
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var isValid = false
          this.selectList.forEach((el) => {
            if (!el.orderIndex) {
              isValid = true
            }
          })
          if (isValid) {
            this.$modal.alertWarning('顺序不能为空,请修改后重新操作!', '提醒')
            return
          }
          var xb = this.form.xb
          var gridData = this.selectList
          for (var i = 0, l = gridData.length; i < l; i++) {
            var row = gridData[i]
            var sex = row.sex
            if (xb != '2') {
              if (sex != '2') {
                if (sex != xb) {
                  this.$modal.alertWarning('主表、子表性别必须一致', '提醒')
                  return
                }
              }
            }
            var jcxm = row.examitemName
            if (i > 0) {
              for (var j = 0; j < i; j++) {
                var row1 = gridData[j]
                if (jcxm == row1.examitemName) {
                  this.$modal.alertWarning('检查项目不能重复!', '提醒')
                  return
                }
              }
            }
          }
          var data = this.form
          data.exams = [...this.removeList, ...this.selectList]
          if (this.form.id) {
            updateItems(data).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            saveItems(data).then(() => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.removeList = []
      this.selectList = []
      this.tableList = []
      this.selectRows = []
      this.itemRows = []
      this.placeSelect = []
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.deptData.bindValue = this.form.departName
        if (this.form.examfeeitemCodehm) {
          var list = []
          list = this.form.examfeeitemCodehm.split(",")
          for (var i in list) {
            this.placeSelect.push(this.getTreeName(this.placeList, list[i]))
          }
        }
      } else {
        this.form = {
          examfeeitemName: undefined,
          sfxmsrm: undefined,
          sysmanmark: '1',
          examfeeitemNameprn: undefined,
          xh: undefined,
          xb: '2',
          examfeeitemCode: undefined,
          examfeeitemCodehm: undefined,
          idDepart: undefined,
          departName: undefined,
          jcyy: undefined,
        }
        this.deptData.bindValue = undefined
      }
      if (this.popList && this.popList.length) {
        this.selectList = JSON.parse(JSON.stringify(this.popList))
      }
      this.resetForm('form')
    },
  },
}
</script>

<style lang="scss">
.add-PACS-charge {
  .el-dialog {
    height: 95%;

    .el-dialog__body {
      display: flex;
      flex-direction: column;
    }
  }

  .vue-treeselect {
    .vue-treeselect__control {
      height: 28px;
      display: flex;
      align-items: center;

      .vue-treeselect__placeholder,
      .vue-treeselect__single-value {
        line-height: 28px;
        font-size: 12px;
      }

      &>div {
        display: block;
      }

      .vue-treeselect__value-container {
        flex: 1;
        height: 100%;
        overflow: hidden;
      }

      .vue-treeselect__input-container {
        display: none;
      }

      .vue-treeselect__multi-value {
        height: 100%;
        vertical-align: initial;
        overflow: hidden;
        margin-bottom: 0;
        cursor: pointer;
        white-space: nowrap;
      }

      .vue-treeselect__multi-value-item-container {
        height: 100%;
        padding: 3px 0;
        margin-right: 6px;
        display: inline-block;

        .vue-treeselect__multi-value-item {
          padding: 0;
          vertical-align: initial;
          border: none;
          display: flex;
          align-items: center;
          background: #f4f4f5;
          color: #909399;
          height: 100%;
          border-radius: 2px;

          .vue-treeselect__value-remove {
            color: #909399;
          }
        }
      }
    }
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .el-form {
    .el-form-item {
      margin-bottom: 16px;
    }
  }

  .add-table {
    border: 1px solid #ebeef5;
    flex: 1;
    display: flex;
    flex-direction: column;

    .flex {
      display: flex;
      flex: 1;
    }

    .el-form {
      padding: 16px 16px 0;

      .el-form-item {
        margin-bottom: 16px;
      }
    }

    .el-table__empty-block {
      width: 100% !important;
      min-height: 120px;
    }

    .el-table__body-wrapper {
      min-height: 120px;
    }

    .el-pagination {
      padding: 12px 6px;
    }

    .left-table {
      width: calc(50% - 41px);
      margin: -1px;
      display: flex;
      flex-direction: column;

      .table-box {
        width: 100%;
        flex: 1;
      }
    }

    .center-btn {
      width: 82px;
      background: #fff;
      height: auto;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-left: 1px solid #ebeef5;
      border-right: 1px solid #ebeef5;

      .el-button {
        padding: 0;
        width: 40px;
        height: 40px;
        font-size: 20px;
        border-radius: 5px;
      }
    }

    .right-table {
      width: calc(50% - 41px);
      margin: -1px;

      .sort-column {
        .cell {
          padding: 0 6px;
        }
      }

      .warning-row {
        .sort-column {
          .el-input__inner {
            border-color: #d41318;
          }
        }
      }
    }
  }

  .form-item {
    display: flex !important;

    .el-form-item__content {
      flex: 1;
    }
  }
}
</style>
