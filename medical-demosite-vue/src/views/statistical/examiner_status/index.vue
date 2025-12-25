<!-- 每日体检量统计 开发人麦沃德科技 清风/予安 -->
<template>
  <div style="width: 100%; height: 100%">
    <div class="app-container flex-direction-column">
      <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
        <el-form-item prop="startTime" label="缴费日期">
          <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
        </el-form-item>
        <el-form-item prop="endTime" label="至">
          <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" icon="el-icon-search" type="primary" @click="handleQuery">搜索</el-button>
          <el-button size="mini" icon="el-icon-refresh" @click="resetList">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleexport" v-hasPermi="['statistical:examinerStatus:export']">导出Excel</el-button>
        </el-col>
      </el-row>
      <div class="table-box no-padding-table">
        <el-table border :data="tableList" height="100%" stripe v-loading="loading">
          <el-table-column type="selection" width="45"></el-table-column>
          <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
          <el-table-column label="执行日期" align="center">
            <el-table-column label="日期" prop="date" align="center" width="100"></el-table-column>
            <el-table-column label="星期" prop="dayForWeek" align="center" width="60"></el-table-column>
          </el-table-column>
          <el-table-column label="登记人数" align="center">
            <el-table-column label="个人登记人数" prop="regitsPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体登记人数" prop="regitsOrg" align="center" width="120"></el-table-column>
            <el-table-column label="登记总人数" prop="registAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="registAllMoney" align="center" width="100"></el-table-column>
          </el-table-column>
          <el-table-column label="开始体检人数" align="center">
            <el-table-column label="个人开检人数" prop="startPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体开始人数" prop="startOrg" align="center" width="120"></el-table-column>
            <el-table-column label="开检总人数" prop="startAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="startAllMoney" align="center" width="100"></el-table-column>
          </el-table-column>
          <el-table-column label="职业总检人数" align="center">
            <el-table-column label="个人总检人数" prop="zyzjPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体总检人数" prop="zyzjOrg" align="center" width="120"></el-table-column>
            <el-table-column label="总检总人数" prop="zyzjAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="zyzjAll" align="center" width="100"></el-table-column>
          </el-table-column>
          <el-table-column label="健康总检人数" align="center">
            <el-table-column label="个人总检人数" prop="jkzjPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体总检人数" prop="jkzjOrg" align="center" width="120"></el-table-column>
            <el-table-column label="总检总人数" prop="jkzjAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="jkzjMoney" align="center" width="100"></el-table-column>
          </el-table-column>
          <el-table-column label="职业领取报告数" align="center">
            <el-table-column label="个人领取数" prop="zylqPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体领取数" prop="zylqOrg" align="center" width="120"></el-table-column>
            <el-table-column label="领取总份数" prop="zylqAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="zylqMoney" align="center" width="100"></el-table-column>
          </el-table-column>
          <el-table-column label="健康领取报告数" align="center">
            <el-table-column label="个人领取数" prop="jklqPersonal" align="center" width="120"></el-table-column>
            <el-table-column label="团体领取数" prop="jklqOrg" align="center" width="120"></el-table-column>
            <el-table-column label="领取总份数" prop="jklqAll" align="center" width="120"></el-table-column>
            <el-table-column label="金额" prop="jklqMoney" align="center" width="100"></el-table-column>
          </el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      <div class="statistics-tag">
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人登记数</span> <span>{{ listResponse.regitsPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体登记人数</span> <span>{{ listResponse.regitsOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">登记总人数</span> <span>{{ listResponse.registAll }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人开检人数</span> <span>{{ listResponse.startPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体开检人数</span> <span>{{ listResponse.startOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">开检总人数</span> <span>{{ listResponse.startAll }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人总检人数(职业)</span> <span>{{ listResponse.zyzjPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体总检人数(职业)</span> <span>{{ listResponse.zyzjOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">总检总人数(职业)</span> <span>{{ listResponse.zyzjAll }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人总检人数(健康)</span> <span>{{ listResponse.jkzjPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体总检人数(健康)</span> <span>{{ listResponse.jkzjOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">总检总人数(健康)</span> <span>{{ listResponse.jkzjAll }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人领取数(职业)</span> <span>{{ listResponse.zylqPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体领取数(职业)</span> <span>{{ listResponse.zylqOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">领取总份数(职业)</span> <span>{{ listResponse.zylqAll }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">个人领取数(健康)</span> <span>{{ listResponse.jklqPersonal }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">团体领取数(健康)</span> <span>{{ listResponse.jklqOrg }}</span>
        </el-tag>
        <el-tag effect="plain" class="tag-text">
          <span class="color-span">领取总份数(健康)</span> <span>{{ listResponse.jklqAll }}</span>
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script>
import { page, total } from '@/api/statistical/examiner_status.js'
import { getCookie } from '@/utils/getCookie.js'

export default {
  data() {
    return {
      // 加载中
      loading: false,
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
      },
      total: 0,
      tableList: [],
      // 统计数据
      listResponse: [],
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
  },
  methods: {
    //导出
    handleexport() {
      this.download(
        '/statistics/everyPhysical/export',
        {
          ...this.queryParams,
          branchIds: getCookie('cid'),
        },
        `每日体检量统计表.xlsx`
      )
    },
    //获取数据
    getList() {
      this.loading = true
      page(this.queryParams)
        .then((res) => {
          this.tableList = res.data.records
          this.total = res.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
      total({
        ...this.queryParams,
        branchIds: getCookie('cid'),
      }).then((res) => {
        this.listResponse = res.data
      })
    },
    // 查询
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.startTime = this.queryParams.startTime ? this.queryParams.startTime.slice(0, 10) + ' 00:00:00' : undefined
      this.queryParams.endTime = this.queryParams.endTime ? this.queryParams.endTime.slice(0, 10) + ' 23:59:59' : undefined
      this.getList()
    },
    //重置
    resetList() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>

<style scoped lang="scss">
.statistics-tag {
  margin: 0 -20px -20px;
  display: flex;
  justify-content: flex-start;
  flex-flow: row wrap;
  background: #f5f5f5;
}
.tag-text {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 236px;
  height: 50px;
  padding: 8px;
  margin: 6px 10px;
  border: transparent;
  border-radius: 5px;
  span {
    font-size: 16px;
  }
  .color-span {
    color: #000;
  }
}
</style>
