<!-- 问诊检查-右  开发人：麦沃德科技 予安/半夏 -->
<template>
  <div class="app-container flex-direction-column section-list-right-c" style="height: 100%; min-height: 100px; padding: 0; border-left: #f6f7fb 4px solid; overflow: auto">
    <div class="charge-item">
      <h3>【检查信息】</h3>
      <el-form :model="form" ref="queryMain" size="small" :inline="true" style="padding: 10px 0">
        <el-form-item label="检查人" prop="jcr" style="margin-right: 32px">
          <el-input :value="form.jcrxm" readonly :style="fromTo == 'inspection' ? 'width:180px;' : 'width:310px;'" v-if="mainDisabled" />
          <input-select :selectData="selectData" :queryParams="queryParams" :initialValue="form.jcrxm" :selectWidth="fromTo == 'inspection' ? '180' : '310'" @change="selectChange" v-else></input-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="jcsj" style="margin-right: 0">
          <el-input prefix-icon="el-icon-time" :value="form.jcsj" readonly :style="fromTo == 'inspection' ? 'width:180px;' : 'width:310px;'" v-if="mainDisabled" />
          <el-date-picker v-model="form.jcsj" type="datetime" placeholder="选择日期时间" :style="fromTo == 'inspection' ? 'width:180px;' : 'width:310px;'" value-format="yyyy-MM-dd HH:mm:ss" v-else></el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="lrr" style="margin-right: 32px">
          <el-input v-model="form.lrr" readonly :style="fromTo == 'inspection' ? 'width:180px;' : 'width:310px;'" v-if="mainDisabled" />
          <input-select :selectData="selectData1" :queryParams="queryParams" :initialValue="form.lrr" :selectWidth="fromTo == 'inspection' ? '180' : '310'" @change="selectChange1" v-else></input-select>
        </el-form-item>
        <el-form-item label="录入时间" prop="lrsj" style="margin-right: 0">
          <el-input prefix-icon="el-icon-time" v-model="form.lrsj" readonly :style="fromTo == 'inspection' ? 'width:180px;' : 'width:310px;'" />
        </el-form-item>
      </el-form>
    </div>
    <div class="table-box" style="min-height: 150px">
      <el-table border v-loading="loading" :data="tableData8.tableData" height="100%" stripe @row-click="rowClick">
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="patientcode" label="体检号" min-width="120" align="center" />
        <el-table-column prop="patientname" label="姓名" min-width="80" align="center" />
        <el-table-column prop="idSex" label="性别" min-width="60" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.idSex == 0">男</span>
            <span v-if="scope.row.idSex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="isAudit" label="体检状态" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isAudit == 1">已检</el-tag>
            <el-tag type="danger" v-else>未检</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="padding: 0 16px">
      <pagination v-show="tableData8.total > 0" :total="tableData8.total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getListData"> </pagination>
    </div>
  </div>
</template>

<script>
import { getRankData } from '@/api/funcdept/section_list/professional_greetings.js'
export default {
  props: ['rightData', 'mainDisabled', 'fromTo', 'tableData8'],
  data() {
    return {
      // 表单信息
      form: {},
      // 加载
      loading: false,
      // 选中数组
      selectRow: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        ksID: this.$route.meta.ksID,
      },
      // 检查人
      selectData: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
      // 录入人
      selectData1: {
        placeholder: ' ',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/abteilung/division/allDoctors', //请求连接
        bindValue: '', //初始值(必传)
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的输入码参数名(不传默认为'inputCode')
      },
    }
  },
  created() {},
  watch: {
    rightData: {
      handler(value) {
        this.form = value
        this.selectData.bindValue = value.jcrxm
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    // 检查人返回选中的值
    selectChange(value) {
      this.form.jcr = value.id
      this.form.jcrxm = value.username
      this.selectData.bindValue = value.id
    },
    // 录入人返回选中的值
    selectChange1(value) {
      this.form.lrrId = value.id
      this.form.lrr = value.username
      this.selectData1.bindValue = value.id
    },
    // 问诊检查列队
    searchTableData(date, status) {
      this.queryParams.ksID = this.$route.meta.ksID
      if (date) {
        this.queryParams.startTime = date[0]
        this.queryParams.endTime = date[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.status = status
      this.queryParams.current = 1
      this.loading = true
      getRankData(this.queryParams).then(({ data }) => {
        this.$emit('getRankData', data.records, data.total)
        this.loading = false
      })
    },
    getListData() {
      this.loading = true
      getRankData(this.queryParams).then(({ data }) => {
        this.$emit('getRankData', data.records, data.total)
        this.loading = false
      })
    },
    //单击事件选中当前行
    rowClick(row) {
      this.$emit('getSectionData', row.patientcode, this.$route.meta.ksID)
    },
  },
}
</script>

<style lang="scss">
.section-list-right-c {
  .charge-item {
    padding: 20px 20px 0;
  }

  h3 {
    margin: 0 0 10px 0;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .el-textarea__inner {
    background-color: #f6f7fb;
  }

  .show-top {
    height: 200px;
    padding: 10px;
  }

  .show-middle {
    padding: 10px;
    border-top: #f6f7fb 4px solid;
    border-bottom: #f6f7fb 4px solid;
  }

  .show-bottom {
    height: auto;
    padding: 10px;
    padding-bottom: 0;

    .el-textarea__inner {
      background-color: #f6f7fb;
    }

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 8px;
    }
  }
}
</style>
<style scoped>
/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {
  body,
  .el-input__inner,
  .el-textarea__inner,
  .el-button,
  .el-form .el-form-item__label,
  .checkbox {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
