<template>
  <!-- 添加弹窗 -->
  <el-dialog title="添加重点询问症状" :visible.sync="open" width="1400px" append-to-body style="overflow: hidden" :close-on-click-modal="false" destroy-on-close>
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="危害因素" prop="harmname">
        <input-select ref="harmname" :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="disiaseType">
        <el-select v-model="queryParams.disiaseType" placeholder="请选择职业体检类别">
          <el-option v-for="item in examination" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled"> </el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-row type="flex" justify="space-between" style="width: 100%">
      <el-col :span="10" style="border: 1px solid #d4d6d9">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent style="padding: 16px; padding-bottom: 0">
          <el-form-item label="输入码" prop="inputCode">
            <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table ref="tableList" :data="tableList" stripe class="tableList" border height="450" v-loading="loading" @selection-change="handleSelectionChangeLeft" @row-click="rowClick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" align="center" width="100px" type="index" />
          <el-table-column label="症状代码" prop="symptomCode" align="center" show-overflow-tooltip />
          <el-table-column label="症状名称" prop="symptom" align="center" show-overflow-tooltip />
          <el-table-column label="输入码" prop="inputCode" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <el-pagination v-show="total > 0" :total="total" :current-page.sync="queryParams.current" small :page-size.sync="queryParams.size" style="padding: 10px" @current-change="currentChange" />
      </el-col>

      <el-col :span="2" class="middle">
        <div class="center-btn">
          <el-button type="primary" plain size="mini" @click="moveInData">移入选项<i class="el-icon-upload2 el-icon--right"></i></el-button>
          <el-button type="primary" plain size="mini" @click="moveOutData" style="margin: 24px 0 0 !important"><i class="el-icon-upload2 el-icon--left"></i>移出选项</el-button>
        </div>
      </el-col>

      <el-col :span="12" style="border: 1px solid #d4d6d9">
        <div class="grid-content bg-purple-light">
          <el-table :data="tempList" stripe @selection-change="handleSelectionChangeRight" border max-height="560">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" align="center" width="100px" type="index" />
            <el-table-column label="症状代码" prop="symptomCode" align="center" show-overflow-tooltip />
            <el-table-column label="症状名称" prop="symptom" align="center" show-overflow-tooltip />
            <el-table-column label="危害因素" prop="harmName" align="center" show-overflow-tooltip />
            <el-table-column label="体检类别" prop="disiaseType" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ ['上岗前', '在岗期间', '离职时', '离岗后', '应急'][scope.row.disiaseType] }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getListApi } from '@/api/basis/occupation/symptom_safeguard.js'
import { addSymptom } from '@/api/basis/occupation/factor_symptom.js'
export default {
  data() {
    return {
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        harmName: undefined,
        harmname: undefined,
        disiaseType: undefined,
        inputCode: undefined,
      },
      // 危害因素筛选数据
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '危害因素名称',
        url: '/whysqzfw/getAllHarmname',
        bindValue: '', //初始值(必传)
        secondName: 'harmName', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 左侧加载中
      loading: true,
      // 左侧搜索数据
      tableList: [],
      // 左侧表格中选择的数据
      leftSelect: [],
      // 总数
      total: 0,

      // 右侧筛选数据
      tempList: [],
      // 右侧表格中选择的数据
      rightSelect: [],
      // 职业体检类别
      examination: [
        { value: 0, label: '上岗前' },
        { value: 1, label: '在岗期间' },
        { value: 2, label: '离职时' },
        { value: 3, label: '离岗后' },
        { value: 4, label: '应急' },
      ],
      // 是否显示新增弹窗
      open: false,
    }
  },
  methods: {
    // 显示弹窗
    show() {
      this.open = true
      this.resetQuery()
      this.reset()
      this.getList()
    },
    // 查询分类列表
    getList() {
      this.loading = true
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 危害因素选择结果
    selectChange1(value) {
      this.queryParams.harmname = value.id
      this.queryParams.harmName = value.harmName
    },
    // 搜索
    handleQuery() {
      this.getList()
    },
    // 重置
    resetQuery() {
      this.tableList = []
      this.tempList = []
      this.resetForm('queryForm')
      this.queryParams.current = 1
      this.handleQuery()
    },
    // 左侧表格中选中的数据
    handleSelectionChangeLeft(val) {
      this.leftSelect = val.map((item) => item)
    },
    // 单击表格
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 移入选项
    moveInData() {
      if (this.leftSelect.length == 0) {
        this.$message({
          message: '请选择病症名称',
          type: 'warning',
        })
        return
      }
      if (!this.queryParams.harmname) {
        this.$message({
          message: '请选择危害因素',
          type: 'warning',
        })
        return
      }
      if (this.queryParams.disiaseType == undefined) {
        this.$message({
          message: '请选择职业体检类别',
          type: 'warning',
        })
        return
      }
      this.leftSelect.forEach((el) => {
        this.tableList.splice(this.tableList.indexOf(el), 1)
        if (!this.tempList.includes(el)) {
          el.disiaseType = this.queryParams.disiaseType
          el.harmname = this.queryParams.harmname
          el.harmName = this.queryParams.harmName
          this.tempList.push(el)
        } else {
          this.$alert('存在相同项目,请勿重复添加', '提示')
          return
        }
      })
    },
    // 右侧表格选中的数据
    handleSelectionChangeRight(val) {
      this.rightSelect = val.map((item) => item)
    },
    // 移出选项
    moveOutData() {
      this.rightSelect.forEach((el) => {
        this.tempList.splice(this.tempList.indexOf(el), 1)
        if (!this.tableList.includes(el)) {
          this.tableList.push(el)
        }
      })
    },
    // 分页器
    currentChange($event) {
      this.queryParams.current = $event
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 表单重置
    reset() {
      this.leftSelect = []
      this.tempList = []
      this.queryParams.harmname = undefined
      this.queryParams.disiaseType = undefined
      this.resetForm('queryForm')
      this.$nextTick(() => {
        this.$refs.harmname.initData()
      })
      this.resetQuery()
    },
    // 提交按钮
    submitForm: function () {
      if (this.tempList.length == 0) {
        this.$message({
          message: '请添加数据后再试',
          type: 'warning',
        })
      } else {
        this.tempList.forEach((el) => {
          // el.danagerId = el.harId
          el.symptomId = el.id
          el.symptomcode = el.id
          el.id = null
        })
        addSymptom(this.tempList).then((res) => {
          this.$emit('closeDialog')
          this.$modal.msgSuccess('添加成功')
          this.open = false
        })
      }
    },
  },
}
</script>

<style lang="scss">
.tableList {
  overflow-y: auto;
  height: 450px;
}

.middle {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 540px;

  .move {
    display: flex;
    align-items: center;
    padding: 7px 10px;
    margin-bottom: 24px;
    background: #e8f4ff;
    border-radius: 5px;
    font-size: 12px;
    line-height: 17px;
    color: #0059ff;

    &:hover {
      cursor: pointer;
    }

    img {
      margin-right: 5px;
    }
  }

  .el-pagination__total {
    display: none;
  }

  .center-btn {
    background: #fff;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .el-icon--left {
      transform: rotate(-90deg);
    }

    .el-icon--right {
      transform: rotate(90deg);
    }
  }
}
</style>
