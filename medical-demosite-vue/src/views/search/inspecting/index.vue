<!-- 在检人员信息 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column" style="height: 100%; overflow: auto">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="登记日期" prop="date">
        <el-date-picker v-model="queryParams.date" style="width: 366px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检号\" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="idExamtype">
        <el-select v-model="queryParams.idExamtype" placeholder="请选择体检类型" style="width: 160px">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select v-model="queryParams.idPatientclass" placeholder="体检者类型" style="width: 160px">
          <el-option v-for="item in levelList" :key="item.levelId" :label="item.levelName" :value="item.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="团队名称" prop="orgName">
        <input-select ref="typeName1" :selectData="selectData" @change="currencyDataChange" style="width: 160px"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:inspecting:export']">导出Excel </el-button>
      </el-col>
    </el-row>

    <!--页面-->
    <el-row :gutter="10" style="flex: 1">
      <!-- 左侧页面 -->
      <!--备注: 旧系统jsp文件未找到相应字段 暂未对 字段名 -->
      <el-col :span="8">
        <div class="table-box" ref="tableBox" style="margin-top: 15px; margin-bottom: 10px">
          <span style="font-weight: bolder">个人登记信息</span>
        </div>
        <el-col :span="24">
          <el-form :model="baseInfo" ref="formData" size="small" :inline="true" label-width="90px" label-position="right">
            <el-form-item label="分组类型" prop="key0">
              <el-input :readonly="true" v-model="baseInfo.key0" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="级别" prop="key1">
              <el-select v-model="baseInfo.key1" placeholder="" disabled style="width: 150px">
                <el-option v-for="item in levelList" :key="item.levelId" :label="item.levelName" :value="item.levelId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="团体" prop="key2">
              <el-input v-model="baseInfo.key2" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="体检号" prop="key3">
              <el-input v-model="baseInfo.key3" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="key4">
              <el-input v-model="baseInfo.key4" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="性别" prop="key5">
              <el-input v-model="baseInfo.key5" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="key6">
              <el-input v-model="baseInfo.key6" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="出生日期" prop="key7">
              <el-input v-model="baseInfo.key7" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="身份证号" prop="key8">
              <el-input v-model="baseInfo.key8" :readonly="true" placeholder="" style="width: 400px"> </el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="key9">
              <el-input v-model="baseInfo.key9" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="工作职位" prop="key10">
              <el-input v-model="baseInfo.key10" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="婚姻" prop="key12">
              <el-input :readonly="true" v-if="this.baseInfo.key12 == 1" value="已婚" placeholder="" style="width: 150px"></el-input>
              <el-input :readonly="true" v-else-if="this.baseInfo.key12 == 2" value="未婚" placeholder="" style="width: 150px"></el-input>
              <el-input :readonly="true" v-else-if="this.baseInfo.key12 == 3" value="离异" placeholder="" style="width: 150px"></el-input>
              <el-input :readonly="true" v-else-if="this.baseInfo.key12 == 4" value="丧偶" placeholder="" style="width: 150px"></el-input>
              <el-input :readonly="true" v-else-if="this.baseInfo.key12 == 5" value="其他" placeholder="" style="width: 150px"></el-input>
              <el-input style="width: 150px" :readonly="true" value="" v-else placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="体检类型" prop="key14">
              <el-input v-model="baseInfo.key14" :readonly="true" placeholder="" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="体检类别" prop="key15">
              <el-input v-if="baseInfo.key15 == 1" :readonly="true" value="上岗前" placeholder="" style="width: 150px"> </el-input>
              <el-input v-else-if="baseInfo.key15 == 2" :readonly="true" value="在岗期间" placeholder="" style="width: 150px"> </el-input>
              <el-input v-else-if="baseInfo.key15 == 3" :readonly="true" value="离岗时" placeholder="" style="width: 150px"> </el-input>
              <el-input v-else placeholder="" :readonly="true" style="width: 150px"> </el-input>
            </el-form-item>
            <el-form-item label="家庭住址" prop="key13">
              <el-input v-model="baseInfo.key13" :readonly="true" placeholder="" style="width: 400px"> </el-input>
            </el-form-item>
            <el-form-item label="照片" prop="key16">
              <img :src="baseInfo.key16" style="width: 120px; height: 160px; border: 1px solid #ccc" alt="" />
            </el-form-item>
          </el-form>
        </el-col>
      </el-col>

      <!-- 右侧页面 -->
      <el-col :span="16" style="padding-top: 45px; height: 100%">
        <!-- 表格 -->
        <div class="table-box" style="height: 100%" ref="tableBox">
          <el-table border ref="leftTable" v-loading="loading" :data="tableList" height="620px" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="体检号" prop="patientcode" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="姓名" prop="patientname" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="拼音码" prop="inputCode" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="登记日期" prop="dateregister" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="登记员" prop="doctorregName" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="idSex" min-width="80px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.idSex == 0">男</span>
                <span v-else-if="scope.row.idSex == 1">女</span>
                <span v-else></span>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" min-width="80px" align="center" show-overflow-tooltip />
            <el-table-column label="身份证号" prop="idcardno" min-width="160px" align="center" show-overflow-tooltip />
            <el-table-column label="婚姻状况" prop="idMarriage" min-width="100px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idMarriage] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="出生日期" prop="birthdate" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="手机号" prop="phone" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="团体" prop="orgName" min-width="140px" align="center" show-overflow-tooltip />
            <el-table-column label="体检类型" prop="idExamtype" min-width="120px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ ['健康体检', '职业体检', '综合'][scope.row.idExamtype] }}
              </template>
            </el-table-column>
            <el-table-column label="体检者类型" prop="idPatientclass" min-width="120px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-for="item in levelList" :key="item.levelId">
                  <span v-if="scope.row.idPatientclass == item.levelId">{{ item.levelName }}</span>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="工种(职位)" prop="trades" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="接害因素" prop="jhys" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="地址" prop="address" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="邮政编码" prop="yzbm" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="收费类型" prop="payway" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="分检完成" prop="FReadytofinal" min-width="80px" align="center" show-overflow-tooltip />
            <el-table-column label="总检锁定" prop="FFinallocked" min-width="80px" align="center" show-overflow-tooltip />
            <el-table-column label="职业总检完成" prop="zytjzt" min-width="120px" align="center" show-overflow-tooltip />
            <el-table-column label="健康总检完成" prop="jktjzt" min-width="120px" align="center" show-overflow-tooltip />
          </el-table>
          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import { getListData, getFormData, getMarriageData } from '@/api/search/inspecting'

export default {
  name: 'Inspecting',
  props: [],
  data() {
    return {
      // 基本信息数据
      baseInfo: {},
      // 表格展示数据
      tableList: [],
      //路径
      imgPath: Cookies.get('imgPath'),
      marriageList: [],
      //婚姻数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/query/inspecting/getOrgs', //请求连接
        firstName: 'khdwsrm',
        secondName: 'khdwmc',
        bindValue: '',
        queryData: 'key',
      },
      // 遮罩层
      loading: true,
      id: undefined,
      // 选中数组
      ids: [],
      // 总条数
      total: 10,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        patientcode: undefined,
        idExamtype: undefined,
        idPatientclass: undefined,
        orgName: undefined,
      },
      levelList: [],
    }
  },
  async created() {
    this.queryParams.date = [this.$getDate().split(' ')[0], this.$getDate().split(' ')[0]]
    this.levelList = (await this.$getLevelList()).data
    getMarriageData().then((response) => {
      this.marriageList = response.data
    })
    this.getList()
  },
  mounted() {},
  methods: {
    //选中改变
    handleSelectionChange(selection) {
      this.resetForm('formData')
      this.baseInfo.key16 = undefined
      if (selection.length == 1) {
        this.selection = selection[0]
        this.ids.push(selection[0].id)
        this.getFormData()
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.selection = []
        this.ids = []
      }
      this.DetailData = selection[0]
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    // 选择团体名称
    currencyDataChange(value) {
      this.queryParams.orgName = value.khdwmc
    },
    // 查询列表
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //获取左侧数据
    getFormData() {
      getFormData(this.ids[0]).then((response) => {
        response.data.key1 = Number(response.data.key1)
        response.data.key7 = response.data.key7 ? response.data.key7.split(' ')[0] : ''
        this.baseInfo = response.data
        this.baseInfo.key16 = this.imgPath + this.baseInfo.key16
        //判断性别
        if (this.baseInfo.key5 == 0) this.baseInfo.key5 = '男'
        else if (this.baseInfo.key5 == 1) this.baseInfo.key5 = '女'
        else this.baseInfo.key5 = ' '
        //判断体检类型
        if (this.baseInfo.key14 == 0) this.baseInfo.key14 = '健康体检'
        else if (this.baseInfo.key14 == 1) this.baseInfo.key14 = '职业体检'
        else if (this.baseInfo.key14 == 2) this.baseInfo.key14 = '综合体检'
        else this.baseInfo.key14 = ' '
      })
    },
    // 搜索
    handleQuery() {
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
      this.$refs.typeName1.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/query/chargeInfo/export',
        {
          ...this.queryParams,
        },
        `在检人员名单.xlsx`
      )
    },
  },
}
</script>
