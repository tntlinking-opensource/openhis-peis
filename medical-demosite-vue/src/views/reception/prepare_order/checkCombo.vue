<template>
  <!-- 添加或修改对话框 -->
  <el-dialog title="查看套餐" :visible.sync="open" width="1400px" append-to-body>
    <el-table ref="tableData" border :data="tableData" style="margin: 0 -1px -1px" height="650">
      <el-table-column label="序列" type="index" width="65" align="center" />
      <el-table-column label="分组名称" prop="orgreservationgroupname" min-width="160" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.orgreservationgroupname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="体检套餐" prop="tcid" min-width="300" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.tcName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="体检类型" prop="idExamtype" min-width="100" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ ['健康', '职业', '综合', '复查'][scope.row.idExamtype] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优惠价" prop="zhjg" min-width="120" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.zhjg }}</span>
        </template>
      </el-table-column>
      <el-table-column label="付款方式" prop="idPayway" min-width="150" align="center">
        <template slot-scope="scope">
          <span>{{ payOptions[scope.row.zhjg] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分组条件" align="center">
        <el-table-column label="组类" prop="grouptype" min-width="100" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.grouptype }}</span>
          </template>
        </el-table-column>
        <el-table-column label="男" prop="fMale" min-width="80" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.fMale == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="女" prop="fFemale" min-width="80" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.fFemale == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="已婚" prop="fHasmarried" min-width="80" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.fHasmarried == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="未婚" prop="fNevermarried" min-width="80" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.fNevermarried == 1"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="优先" prop="dispOrder" min-width="120" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.dispOrder }}</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄自" prop="nlz" min-width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.agemin }}</span>
          </template>
        </el-table-column>
        <el-table-column label="年龄至" prop="nld" min-width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.agemax }}</span>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="计划日期" prop="dateexamplanned" min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.dateexamplanned ? scope.row.dateexamplanned.split(' ')[0] : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="危害分组" prop="harmGroup" min-width="160" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.harmGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column label="可重复" prop="idPatientclass2" min-width="80" align="center">
        <template slot-scope="scope">
          <el-checkbox :value="scope.row.idPatientclass2 == 1"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="已禁检" prop="forbidden" min-width="80" align="center">
        <template slot-scope="scope">
          <el-checkbox :value="scope.row.forbidden == 1"></el-checkbox>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
import { getGroupData, editApi, getPaywayData } from '@/api/reception/prepare_order.js'

export default {
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 加载中
      loading: false,
      // 表格数据
      tableData: [],
      // 付款方式列表
      payOptions: [],
    }
  },
  methods: {
    showDialog(id) {
      this.open = true
      this.loading = true
      editApi({ id })
        .then(({ data }) => {
          getGroupData({
            idOrgRw: data.id,
          })
            .then(({ data }) => {
              if (data && data.data) {
                this.tableData = data.data
              }
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {
          this.loading = false
        })

      getPaywayData().then(({ data }) => {
        this.payOptions = data
      })
    },
  },
}
</script>

<style></style>
