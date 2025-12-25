<!-- 问诊检查-症状 开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="症状" :visible.sync="open" width="1400px" append-to-body :close-on-click-modal="false" class="symptom-select">
    <div class="container">
      <div class="main-table flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
          <el-form-item label="输入码" prop="inputCode">
            <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button class="section-btn-style1" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            <el-button class="section-btn-style2" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table border ref="tableData" v-loading="loading" :data="tableList" height="100%" stripe @select="handleSelect" @select-all="handleSelectAll">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="症状代码" prop="symptomCode" align="center" />
            <el-table-column label="症状" prop="symptom" align="center" />
            <el-table-column label="重点询问" prop="keyPoint" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.key == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="输入码" prop="inputCode" align="center" />
          </el-table>
        </div>
      </div>
      <div class="main-select">
        <el-table border :data="selectList" height="100%" stripe>
          <el-table-column label="症状" prop="symptom" align="center" show-overflow-tooltip />
          <el-table-column label="程度" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.degree" placeholder="请选择">
                <el-option :label="item.degreeName" :value="item.id" v-for="item in degreeType" :key="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getSymptom } from '@/api/funcdept/section_list/professional_greetings.js'
export default {
  data() {
    return {
      // 是否显示弹出层  
      open: false,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 100000,
        inputCode: undefined,
      },
      patientCode: undefined,
      // 表格数据
      tableList: [],
      // 已选择表格数据
      selectList: [],
      // 程度
      degreeType: [
        { id: '-', degreeName: '无' },
        { id: '+-', degreeName: '偶有' },
        { id: '+', degreeName: '较轻' },
        { id: '++', degreeName: '中等' },
        { id: '+++', degreeName: '明显' },
      ],
    }
  },
  methods: {
    // 显示弹窗
    handleShow(value, patientCode) {
      this.open = true
      this.selectList = value || []
      this.patientCode = patientCode
      this.resetQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getSymptom({ ...this.queryParams, patientCode: this.patientCode }).then((response) => {
        this.tableList = response.data
        this.$nextTick(() => {
          var symptom = this.selectList.map((item) => item.symptom)
          for (var i in this.tableList) {
            if (symptom.indexOf(this.tableList[i].symptom) > -1) {
              this.$refs.tableData.toggleRowSelection(this.tableList[i], true)
            }
          }
          this.tableList.forEach((el, index) => {
            if (el.key == '1' && !this.selectList.map((item) => item.symptom).includes(el.symptom)) {
              this.$refs.tableData.toggleRowSelection(this.tableList[index], true)
              // el.degree = '-'
              this.selectList.push({
                symptom: el.symptom,
                degree: '-',
              })
            }
          })
        })
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
    // 多选框选中数据
    handleSelect(selection, row) {
      if (selection.includes(row)) {
        this.$set(row, 'degree', '-')
        this.selectList.push(row)
      } else {
        for (var i in this.selectList) {
          if (this.selectList[i].symptom == row.symptom) {
            this.$delete(this.selectList, i)
          }
        }
      }
    },
    // 全选/取消全选
    handleSelectAll(selection) {
      if (selection.length > 0) {
        selection.forEach((el) => {
          if (!this.selectList.includes(el)) {
            this.$set(el, 'degree', '-')
            this.selectList.push(el)
          }
        })
      } else {
        this.selectList = []
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.open = false
      this.$emit('symptomChange', this.selectList)
    },
  },
}
</script>
<style lang="scss">
.symptom-select {
  .el-dialog {
    height: 720px;

    .container {
      display: flex;
      height: 100%;

      .main-table {
        flex: 1;
        padding: 16px;
        background: #ffffff;
        border: 1px solid #c4c4c4;
      }

      .main-select {
        width: 600px;
        padding: 16px;
        background: #ffffff;
        border: 1px solid #c4c4c4;
        margin-left: 16px;
      }
    }
  }
}
</style>
