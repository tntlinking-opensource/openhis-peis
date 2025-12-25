<!-- 问诊检查-家族病史  开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="家族病史" :visible.sync="open" width="1230px" append-to-body :close-on-click-modal="false" class="family-disease">
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
            <el-table-column label="输入码" prop="inputCode" align="center" />
            <el-table-column label="检查结果" prop="familyOfDisease" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </div>
      <div class="main-input">
        <h3 class="title">内容</h3>
        <el-input class="input" type="textarea" resize="none" v-model="familyOfDisease"></el-input>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="submitForm">确定</el-button>
      <el-button class="section-btn-style2" @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getJzbData } from '@/api/funcdept/section_list/professional_greetings.js'
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
        size: 10,
        inputCode: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 内容
      familyOfDisease: undefined,
    }
  },
  methods: {
    // 显示弹窗
    handleShow(value) {
      this.open = true
      this.familyOfDisease = value || ''
      this.resetQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      getJzbData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        var selectList = this.familyOfDisease.split('、')
        this.$nextTick(() => {
          this.tableList.forEach((el) => {
            if (selectList.indexOf(el.familyOfDisease) > -1) {
              this.$refs.tableData.toggleRowSelection(el, true)
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
        if (this.familyOfDisease.length > 0) this.familyOfDisease += '、' + row.familyOfDisease
        else this.familyOfDisease += row.familyOfDisease
      } else {
        this.familyOfDisease = this.familyOfDisease.replace('、' + row.familyOfDisease, '')
        this.familyOfDisease = this.familyOfDisease.replace(row.familyOfDisease, '')
        if (this.familyOfDisease[0] === '、') this.familyOfDisease = this.familyOfDisease.substring(1)
      }
    },
    // 全选/取消全选
    handleSelectAll(selection) {
      if (selection.length > 0) {
        selection.forEach((el) => {
          if (!this.familyOfDisease.includes(el.familyOfDisease)) {
            if (this.familyOfDisease.length > 0) this.familyOfDisease += '、' + el.familyOfDisease
            else this.familyOfDisease += el.familyOfDisease
          }
        })
      } else {
        this.tableList.forEach((el) => {
          this.familyOfDisease = this.familyOfDisease.replace('、' + el.familyOfDisease, '')
          this.familyOfDisease = this.familyOfDisease.replace(el.familyOfDisease, '')
          if (this.familyOfDisease[0] === '、') this.familyOfDisease = this.familyOfDisease.substring(1)
        })
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 提交按钮
    submitForm() {
      this.open = false
      this.$emit('familyChange', this.familyOfDisease)
    },
  },
}
</script>
<style lang="scss">
.family-disease {
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

      .main-input {
        width: 450px;
        padding: 16px;
        background: #ffffff;
        border: 1px solid #c4c4c4;
        margin-left: 16px;

        .title {
          margin: 0 0 12px;
          font-weight: 600;
          font-size: 16px;
          line-height: 22px;
          color: #333333;
        }

        .input {
          width: 100%;
          height: calc(100% - 34px);

          .el-textarea__inner {
            height: 100% !important;
            background: #f6f7fb;
            border: none;
          }
        }
      }
    }
  }
}
</style>
