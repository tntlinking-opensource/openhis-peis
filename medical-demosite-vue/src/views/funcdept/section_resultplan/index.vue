<!-- 批量录入 开发人：麦沃德科技暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <!--左侧页面-->
    <el-row :gutter="20" style="height: 100%">
      <el-col :span="15" class="flex-direction-column" style="height: 100%">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
          <el-form-item label="选择日期">
            <el-date-picker v-model="queryParams.date" style="width: 350px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="体检科室" prop="depId">
            <input-select :selectData="depIdData" selectWidth="160" @change="depIdDataChange"></input-select>
          </el-form-item>
          <el-form-item label="审核状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 160px">
              <el-option label="未审核" :value="0" />
              <el-option label="已审核" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="医师" prop="doctor">
            <el-input v-model="queryParams.doctor" placeholder="请输入" clearable style="width: 170px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="团检单位" prop="orgId">
            <input-select :selectData="orgIdData" selectWidth="160" @change="orgIdDataChange"></input-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="55" align="center" />
            <el-table-column label="体检科室" prop="depName" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="体检号" prop="patientcode" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="idSex" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="年龄" prop="age" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="订单号" prop="ddh" min-width="160px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.ddh == -1"></div>
                <div v-else>{{ scope.row.ddh }}</div>
              </template>
            </el-table-column>
            <el-table-column label="团检单位" prop="orgName" min-width="160px" align="center" show-overflow-tooltip />
            <el-table-column label="部门" prop="orgDepart" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="登记日期" prop="dateregister" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="医师" prop="creater" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="审核情况" prop="status" min-width="100px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.status == 1">已审核</div>
              </template>
            </el-table-column>
            <el-table-column label="审核时间" prop="auditTime" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="小结" prop="conclusions" min-width="260px" align="center" show-overflow-tooltip />
          </el-table>
        </div>

        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>

      <!--右侧页面-->
      <el-col :span="9" class="flex-direction-column" style="height: 100%">
        <el-form :model="queryParam" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
          <el-form-item label="科室" prop="depIdAdd">
            <input-select :selectData="depIdDatas" selectWidth="160" @change="depIdDataChanges"></input-select>
          </el-form-item>
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParam.patientcode" placeholder="请输入体检号" clearable style="width: 160px" @keyup.enter.native="handleQuerys" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuerys">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
          <!-- 操作按钮 -->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="warning" plain size="mini" icon="el-icon-collection" @click="handleAdd" v-hasPermi="['funcdept:sectionResultplan:add']">保存 </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" v-hasPermi="['funcdept:sectionResultplan:remove']">删除 </el-button>
            </el-col>
          </el-row>
        </el-form>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table border v-loading="loading" :data="tableData" height="100%" stripe @selection-change="handleSelectionChange">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="55" align="center" />
            <el-table-column label="体检科室" prop="depName" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="体检号" prop="patientcode" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" min-width="100px" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </el-col>
    </el-row>

    <!-- 添加或修改对话框 -->
    <add-items ref="addItems"></add-items>
  </div>
</template>
<script>
import { checkCode, getListData, sectionResultPlan } from '@/api/funcdept/section_resultplan/section_resultplan.js'
import addItems from './add'
export default {
  components: { addItems },
  props: [],
  data() {
    return {
      depIdAddData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '', //请求连接
        bindValue: '',
      },
      //左侧科室
      depIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/abteilung/sectionResultPlan/getKs', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'srm', //向接口传递的参数名(不传默认为'inputCode')
      },
      //右侧科室
      depIdDatas: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/abteilung/sectionResultPlan/getKs', //请求连接
        bindValue: '',
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'srm', //向接口传递的参数名(不传默认为'inputCode')
      },
      orgIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团检单位', //第二列标题
        url: '/abteilung/sectionResultPlan/getOrgs', //请求连接`
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
        djdate: undefined,
        cldate: undefined,
        ycldate: undefined,
        depId: undefined,
        orgId: undefined,
      },
      queryParam: {
        depId: undefined,
        patientcode: undefined,
      },
      // 表格展示数据
      tableList: [],
      tableData: [],
      // 显示模态框
      showExam: false,
      showUpload: false,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    //右侧体检号回车查询
    handleQuerys() {

      checkCode(this.queryParam).then(({ data }) => {
        for (const key in this.tableData) {
          if (this.tableData[key].patientcode == data.patientcode && this.tableData[key].depName == data.depName) {
            this.$alert('当前数据已存在', '提示')
            return
          }
        }
        data.depId = this.queryParam.depId
        //对象转数组
        this.tableData = [...this.tableData, data]
        this.queryParam.patientcode = ''

      })

    },
    // 保存
    handleAdd() {
      let sectionResultPlans = []
      this.tableData.forEach((el) => {
        let data = {
          status: el.status,
          creater: el.creater,
          id: el.id,
          patientcode: el.patientcode,
          depId: el.depId,
        }
        sectionResultPlans.push(data)
      })
      sectionResultPlan(sectionResultPlans).then((response) => {
        this.$modal.msgSuccess('保存成功!')
        this.resetQuery()
      })
    },
    //团检单位
    orgIdDataChange(value) {
      this.queryParams.orgId = value.id
      this.orgIdData.bindValue = value
    },
    //体检科室
    depIdDataChange(value) {
      this.queryParams.depId = value.id
    },
    //右侧体检科室
    depIdDataChanges(value) {
      this.queryParam.depId = value.id
      this.depIdData.bindValue = value
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.current = 1
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
      this.tableData = []
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 删除
    handleDelete() {
      this.ids.forEach((val) => {
        this.tableData.forEach((el, index) => {
          if (val == el.id) {
            this.$delete(this.tableData, index)
          }
        })
      })
    },
  },
}
</script>
