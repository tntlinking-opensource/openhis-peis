<!-- 电测听-左  开发人：麦沃德科技 予安/半夏 -->
<template>
  <div class="app-container flex-direction-column electro-audiometry" style="height:100%;min-height:auto;padding: 0;overflow-y: auto;">
    <div class="table-box" style="min-height: 150px;">
      <el-table ref="tableData" id="setTable" border v-loading="loading" :data="tableData9.tableData" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column type="index" label="序列" width="55" align="center" />
        <el-table-column prop="patientcode" label="体检号" min-width="120" align="center" />
        <el-table-column prop="patientname" label="姓名" min-width="80" align="center" />
        <el-table-column prop="idSex" label="性别" min-width="60" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.idSex == 0">男</span>
            <span v-if="scope.row.idSex == 1">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" min-width="60" align="center" />
        <el-table-column prop="idExamtype" label="体检类别" min-width="100" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.idExamtype == 0">健康体检</span>
            <span v-if="scope.row.idExamtype == 1">职业体检</span>
            <span v-if="scope.row.idExamtype == 2">综合</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="体检状态" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">已检</el-tag>
            <el-tag type="danger" v-else>未检</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="padding: 0 16px;">
      <pagination v-show="tableData9.total > 0" :total="tableData9.total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getListData">
      </pagination>
    </div>
    <div class="show-top">
      <h3>【基本信息】</h3>
      <div class="basic">
        <el-form class="basic-info" :model="basicInfo" ref="queryForm" size="small" :inline="true" label-width="70px">
          <el-form-item label="姓名" prop="patientname">
            <el-input :value="basicInfo.patientname" readonly />
          </el-form-item>
          <el-form-item label="性别" prop="idSex">
            <el-input value="男" readonly v-if="basicInfo.idSex == 0" />
            <el-input value="女" readonly v-else-if="basicInfo.idSex == 1" />
            <el-input readonly v-else />
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input :value="basicInfo.age" readonly />
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input :value="basicInfo.phone" readonly />
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input :value="basicInfo.patientcode" readonly />
          </el-form-item>
          <el-form-item label="类型" prop="idExamtype">
            <el-input value="健康体检" readonly v-if="basicInfo.idExamtype == 0" />
            <el-input value="职业体检" readonly v-else-if="basicInfo.idExamtype == 1" />
            <el-input value="综合" readonly v-else-if="basicInfo.idExamtype == 2" />
            <el-input readonly v-else />
          </el-form-item>
        </el-form>
        <img class="basic-picture" :src="leftData.picture ? imgPath + leftData.picture : leftData.picture">
      </div>
    </div>
  </div>
</template>

<script>
import { getRankData } from '@/api/funcdept/section_list/electro_audiometry.js'
import Cookies from 'js-cookie'
export default {
  props: ['leftData', 'tableData9'],
  data() {
    return {
      // 加载
      loading: false,
      // 选中数组
      selectRow: [],
      // 基本信息
      basicInfo: {},
      // 查询参数
      queryParams: {
        current: 1,
        size: 20
      },
      //图片地址
      imgPath: Cookies.get("imgPath"),
    }
  },
  watch: {
    leftData: {
      handler(value) {
        this.basicInfo = value.patient || {}
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    // 电测听列队
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
    // 表格项被选中
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.$emit('getSectionData', selection[0].patientcode, this.$route.meta.ksID)
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
      this.single = selection.length != 1
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
    }
  }
}
</script>
<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
<style lang="scss">
.electro-audiometry {
  .show-top {
    padding: 12px 20px 0;
    border-top: 4px #f6f7fb solid;

    h3 {
      margin-top: 0;
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
    }

    .basic {
      display: flex;

      .basic-info {
        flex: 1;

        .el-form-item {
          width: 50%;
          margin-right: 0;
          padding-right: 10px;
          min-width: 200px;

          .el-form-item__content {
            width: calc(100% - 70px);
            min-width: 120px;
          }
        }
      }

      .basic-picture {
        width: auto;
        height: 135px;
        margin-left: 10px;
      }
    }
  }
}
</style>