<!-- 健康团检样本 开发人：麦沃德科技暴雨/予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!--页面-->
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
      <el-form-item label="团体名称" prop="orgName">
        <input-select ref="orgName" :selectData="orgNameData" selectWidth="230" :showTooltip="true" @change="orgNameDataChange"></input-select>
      </el-form-item>
      <el-form-item label="样本名称" prop="sampleName">
        <el-input v-model="queryParams.sampleName" placeholder="请输入样本名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建日期从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" class="form-item-width" value-format="yyyy-MM-dd" type="date" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" class="form-item-width" value-format="yyyy-MM-dd" type="date" :picker-options="pickerOptions" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['groupreport:healthProfession:add']">新增 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleUpdate" :disabled="isPass" v-hasPermi="['groupreport:healthProfession:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="isPass" v-hasPermi="['groupreport:healthProfession:remove']">删除 </el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="warning" plain size="mini" icon="el-icon-plus" @click="handleJoiners" :disabled="isPass" v-hasPermi="['groupreport:healthProfession:Joiners']">加入人员 </el-button>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="height: 90%">
      <!--左侧页面-->
      <el-col :span="15" style="height: 100%" class="flex-direction-column">
        <!-- 表格 -->
        <div class="table-box">
          <el-table border id="setTable" ref="tableData" v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="样本名称" prop="sampleName" align="center" show-overflow-tooltip />
            <el-table-column label="样本类型" prop="sampleType" align="center" show-overflow-tooltip />
            <el-table-column label="团队名称" prop="orgName" align="center" show-overflow-tooltip />
            <el-table-column label="订单号" prop="orderId" align="center" show-overflow-tooltip />
            <el-table-column label="开始登记时间" prop="beginTime" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.beginTime ? scope.row.beginTime.split(' ')[0] : '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="结束登记时间" prop="endTime" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.endTime ? scope.row.endTime.split(' ')[0] : '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="报告状态" prop="status" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span :style="{ color: scope.row.status == 2 || scope.row.status == 7 ? 'green' : scope.row.status == 1 || scope.row.status == 5 || scope.row.status == 6 ? 'orange' : '' }">{{ ['草稿', '提交', '通过', '不通过', '撤回', '主检未审', '主检开审', '主检已审'][scope.row.status] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否已打印" prop="isPrint" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag v-if="scope.row.finish == 1">已打印</el-tag>
                <el-tag v-else>未打印</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>
      <!--右侧页面-->
      <el-col :span="9" style="height: 100%" class="flex-direction-column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-input v-model="queryParamsR.patientcode" placeholder="请输入体检号" clearable style="width: 140px" @keyup.enter.native="handlerefresh" @clear="handlerefresh" />
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-refresh-right" @click="handlerefresh" v-hasPermi="['groupreport:healthProfession:refresh']" :disabled="this.ids.length == 0">刷新 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="rightHandleAdd" v-hasPermi="['groupreport:healthProfession:add']" :disabled="this.ids.length == 0 || isPass">新增 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="rightHandleDelete" v-hasPermi="['groupreport:healthProfession:remove']" :disabled="multipleR">移除 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-s-management" @click="handlesave" v-hasPermi="['groupreport:healthProfession:save']" :disabled="this.ids.length == 0 || isPass">保存 </el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['groupreport:healthProfession:export']" :disabled="this.ids.length == 0">导出 </el-button>
          </el-col>
        </el-row>
        <!-- 表格 -->
        <div class="table-box" ref="tableBox">
          <el-table ref="addTable" class="add-table" border v-loading="rightLoading" :data="rightTableList" height="100%" stripe @selection-change="handleSelectionChangeR" @row-click="rowClickR">
            <el-table-column fixed type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="体检号" prop="patientcode" min-width="140px" align="center">
              <template slot-scope="scope">
                <input-select :ref="'inputSelect' + scope.row.index" :selectData="selectData" @change="selectChange" :queryParams="queryParams1" :initialValue="scope.row.patientcode" :current-index="scope.row.index"></input-select>
              </template>
            </el-table-column>
            <el-table-column label="姓名" prop="patientname" min-width="140px" align="center">
              <template slot-scope="scope">
                <!-- <el-input size="mini" v-model="scope.row.patientname" placeholder="请输入" style="width: 100%" /> -->
                <input-select :selectData="selectDatas" @change="selectChange" :queryParams="queryParams2" :initialValue="scope.row.patientname" :current-index="scope.row.index"></input-select>
              </template>
            </el-table-column>
            <el-table-column label="健康状态" prop="jktjzt" min-width="120px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.jktjzt > 0">总检完成</div>
                <div v-else>-</div>
              </template>
            </el-table-column>
            <el-table-column label="职业状态" prop="zytjzt" min-width="120px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.zytjzt > 0">总检完成</div>
                <div v-else>-</div>
              </template>
            </el-table-column>
            <el-table-column label="性别" prop="idSex" min-width="100px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.idSex == 0">男</div>
                <div v-if="scope.row.idSex == 1">女</div>
              </template>
            </el-table-column>
            <el-table-column label="已开始体检" prop="fexamstarted" min-width="120px" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.fexamstarted == 0">未开始</div>
                <div v-if="scope.row.fexamstarted == 1">已开始</div>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" min-width="100px" align="center" show-overflow-tooltip />
            <el-table-column label="团体名称" prop="orgName" min-width="160px" align="center" show-overflow-tooltip />
            <el-table-column label="部门名称" prop="orgDepart" min-width="120px" align="center" show-overflow-tooltip />
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="rightTotal > 0" :page-sizes="[20, 50, 100, 200 ,500]" :total="rightTotal" :page.sync="queryParamsR.current" :limit.sync="queryParamsR.size" @pagination="getListR" />
      </el-col>
    </el-row>

    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList" @handleUpdate="handleUpdate"></add-items>
  </div>
</template>
<script>
import { getListData, removeData, addPerson, loadPersonData, saveData, removePersons } from '@/api/groupreport/health_profession.js'
import addItems from './add'
export default {
  name: 'Health_profession',
  components: { addItems },
  data() {
    return {
      // 日期选择参数
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
          {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      orgNameData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '团体名称', //第二列标题
        url: '/pacs/itemList/getOrgs', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },
      // 遮罩层
      loading: false,
      // 右侧表格加载中
      rightLoading: false,
      // 选中数组
      ids: [],
      // 选中的数据
      selectItem: undefined,
      // 选中的报告状态是否已经通过
      isPass: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        flag: 0,
        orgName: undefined,
        sampleName: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 表格展示数据
      tableList: [],

      // 右侧表格数据
      rightTableList: [],
      rightTotal: 0,
      queryParamsR: {
        current: 1,
        size: 50,
        patientcode: '',
      },
      // 右侧临时index
      tempId: 0,
      // 右侧选中多个
      multipleR: true,
      // 右侧选中数组
      idsR: [],
      index: 0,
      // 右侧体检号数据
      selectData: {
        placeholder: '请输入体检号',
        key: '姓名', //第一列标题
        value: '体检号', //第二列标题
        url: '/group/groupHealth/addSamplePersonData', //请求连接
        selectWidth: 115, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'patientname', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'patientcode', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      selectDatas: {
        placeholder: '请输入拼音码',
        key: '体检号', //第一列标题
        value: '姓名', //第二列标题
        url: '/group/groupHealth/addSamplePersonData', //请求连接
        selectWidth: 115, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'patientcode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'patientname', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      queryParams1: undefined,
      queryParams2: undefined,
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      getListData(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 团体名称选择返回结果
    orgNameDataChange(value) {
      this.queryParams.orgName = value.khdwmc
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.queryParams.startTime = this.$getDate().split(' ')[0]
      this.queryParams.endTime = this.$getDate().split(' ')[0]
      this.rightTableList = []
      this.$refs.orgName.initData()
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 删除
    handleDelete() {
      const ids = this.ids[0]
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return removeData(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 编辑
    handleUpdate() {
      this.$refs.addItems.handleUpdate(this.selectItem.id)
    },
    //加入人员
    handleJoiners() {
      const ids = this.ids[0]
      this.$modal
        .confirm('确定要加入人员吗？')
        .then(function () {
          return addPerson(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('加入人员成功')
        })
        .catch(() => {})
    },
    // 单击某行
    rowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      
      this.ids = selection.map((item) => item.id)
      this.selectItem = selection.map((item) => item)[0]
      console.log("selectItem的值", this.selectItem);

      let item = selection.map((item) => item)[0]
      if (selection.length) {
        if (item.status == 0 || item.status == 3 || item.status == 4) {
          this.isPass = false
        } else {
          this.isPass = true
        }
      } else {
        this.isPass = true
        this.rightTableList = []
      }
      if (selection.length == 1) {
        // this.selection = selection;
        this.getListR()
        this.queryParams1 = {
          flag: 'patientcode',
          groupId: item.groupId,
          idOrgid: item.orderId,
          reportId: item.id,
        }
        this.queryParams2 = {
          flag: 'patientname',
          groupId: item.groupId,
          idOrgid: item.orderId,
          reportId: item.id,
        }
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
      this.tempId = 0
    },

    // ***********************右侧表格相关
    //刷新
    handlerefresh() {
      this.queryParamsR.current = 1
      this.getListR()
    },
    // 获取右侧数据
    getListR() {
      this.rightLoading = true
      let selectItems = {
        reportId:this.selectItem.id
      }
      loadPersonData(selectItems, this.queryParamsR)
        .then(({ data }) => {
          this.rightTableList = data.records
          this.rightTotal = data.total
          this.rightLoading = false
        })
        .catch((err) => {
          console.error(err)
          this.rightLoading = false
        })
    },
    //组件查询赋值
    selectChange(value, index) {
      if (value.patientcode) {
        for (let i in this.rightTableList) {
          if (value.patientcode == this.rightTableList[i].patientcode) {
            this.$modal.msgWarning('请勿重复添加相同人员!')
            return
          }
        }
        for (let i in this.rightTableList) {
          if (index == this.rightTableList[i].index) {
            this.rightTableList[i] = value
            this.rightTableList[i].index = this.index
            this.rightTableList = this.rightTableList.slice(0)
            this.rightTableList[i].isChange = true
            return
          }
        }
      }
    },

    //右侧按钮新增
    rightHandleAdd() {
      this.rightTableList.unshift({
        index: this.index++,
        patientcode: undefined,
        patientname: undefined,
        jktjzt: '-',
        zytjzt: '-',
        idSex: '空',
        fexamstarted: '未开始',
        age: undefined,
        orgName: undefined,
        orgDepart: undefined,
      })
    },
    // 右侧移除按钮
    rightHandleDelete() {
      if (this.idR[0] != undefined) {
        removePersons(this.idR).then(() => {
          this.$modal.msgSuccess('移除成功!')
          this.handlerefresh()
        })
      } else {
        this.idsR.forEach((val) => {
          this.rightTableList.forEach((el, index) => {
            if (val == el.index) {
              this.$delete(this.rightTableList, index)
            }
          })
          this.$modal.msgSuccess('移除成功!')
        })
      }
    },
    // 右侧表格选中
    handleSelectionChangeR(selection) {
      this.idR = selection.map((item) => item.sampleid)
      this.idsR = selection.map((item) => item.index)
      this.multipleR = !selection.length
    },
    // 右侧表格单击事件
    rowClickR(row, col) {
      if (col.type != 'selection') this.$refs.addTable.clearSelection()
      this.$refs.addTable.toggleRowSelection(row)
    },
    //右侧保存
    handlesave() {
      for (let i in this.rightTableList) {
        if (this.rightTableList[i].jktjzt == '-') {
          this.$modal.msgWarning('请勿保存未完成的记录!')
          return
        }
      }

      let griddata = []
      this.rightTableList.forEach((el) => {
        if (el.isChange) {
          let data = {
            FExamstarted: el.fexamstarted,
            orgName: el.orgName,
            groupId: this.selectItem.groupId,
            patientcode: el.patientcode,
            patientname: el.patientname,
            orgDepart: el.orgDepart,
            id: el.id,
            idSex: el.idSex,
            age: el.age,
          }
          griddata.push(data)
        }
      })
      let parameter = {
        groupId: this.selectItem.groupId,
        reportId: this.selectItem.id,
        griddata,
      }

      saveData(parameter).then(() => {
        this.$modal.msgSuccess('保存成功!')
        this.handlerefresh()
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        `/group/groupHealth/export/${this.selectItem.groupId}/${this.selectItem.id}`,
        {
          groupId: this.selectItem.groupId,
          reportId: this.selectItem.id,
        },
        `健康团检样本.xlsx`
      )
    },
  },
}
</script>
<style scoped>
#setTable /deep/ .el-table__fixed-header-wrapper .el-checkbox {
  display: none;
}

.add-table /deep/ .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
  background: transparent;
}

.add-table /deep/ .el-input__inner {
  padding: 0 8px;
  border-width: 0;
  text-align: center;
  background: transparent;
}

.add-table /deep/ .el-input__inner:focus {
  border-width: 1px;
  text-align: left;
}
</style>
