<!-- 职业健康检查复查人员名单  开发人：麦沃德科技暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form size="small" :model="queryParams" ref="queryForm" :inline="true" class="no-margin-bottom">
      <el-form-item label="订单号" prop="ddh">
				<el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 230px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus"  @click="showDialog">创建复查名单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="multiple" icon="el-icon-plus"  @click="handleCreatePdf">生成PDF</el-button>
      </el-col>
    </el-row>
    <!-- 表格部分 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="ddh" label="订单号" align="center" />
        <el-table-column prop="orgName" label="团体名称" align="center" />
        <el-table-column prop="startDate" label="开始时间" align="center" />
        <el-table-column prop="endDate" label="结束时间" align="center" />
        <el-table-column prop="creator" label="创建人" align="center" />
        <el-table-column prop="errorMsg" min-width="200" label="生成PDF错误信息" align="center" />
        <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleView(scope.row)">查看PDF</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页器 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 弹窗部分 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" label-width="140px" hide-required-asterisk>
        <el-form-item label="订单号" prop="ddh">
          <el-input v-model="form.ddh" style="width:200px;" clearable placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="单位名称" prop="idOrg">
          <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.idOrg" style="width: 200px;"></input-select>
        </el-form-item>
        <el-form-item prop="fzxId" label="分中心">
        <el-select v-model="queryParams.fzxId" placeholder="请选择分中心" clearable style="width: 200px" >
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
        <el-form-item label="开始时间" prop="startDate">
          <el-date-picker v-model="form.startDate" style="width: 200px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
        </el-form-item>
        <el-form-item label="到期时间" prop="endDate">
          <el-date-picker v-model="form.endDate" style="width: 200px" value-format="yyyy-MM-dd" type="date"></el-date-picker>
        </el-form-item>
      </el-form>
      <!-- 尾部 -->
      <div slot="footer" class="dialog-footer" v-if="title == '创建复查名单'">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <div slot="footer" class="dialog-footer" v-if="title == '编辑'">
        <el-button type="primary" @click="defaultForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListData, saveOrUpdate, createPdf } from '@/api/statistical/review_list'
import { getListApi as getBranchData } from '@/api/applet/center_list.js'

export default {
  data() {
    return {
      // 选中数组
      ids: [],
      selection: [],
      // 分中心列表
      fzxOptions: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      title: '创建复查名单',
      value: '',
      value2: '',
      tableList: [],
      //页码信息
      queryParams: {
        current: 1,
        size: 20,
        ddh: undefined,
      },
      // 表单参数
      form: {
        levelId: undefined,
        branchId: undefined,
        maxNum: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined,
        remark: undefined,
        openDay: [],
      },
      //预约类型
      levelIdOptions: [],
      //分中心
      branchIdOptions: [],
      //信息总条数
      total: 0,
      // 单位名称筛选参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '单位名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 订单号
      ddhData: {
        placeholder: '请输入订单号',
        key: '订单号',//第一列标题 
        value: '订单名称',//第二列标题 
        third: '单位名称',//第三列标题（没有传空）
        url: '/reception/register/getDdhData',//请求连接
        bindValue: '',
        firstName: 'ddh',//接口返回值对应第一列的参数名
        secondName: 'ddmc',//接口返回值对应第二列的参数名
        thirdName: 'customername',//接口返回值对应第三列的参数名
        // queryData: "key",
      },
    }
  },
  created() {
    getBranchData({ size: 100 }).then(({ data }) => {
      this.fzxOptions = data.records
    })
    this.queryParams.branchId = this.$getCookie('cid')
    this.getList()
  },
  methods: {
    // 查看pdf
    handleView(row) {
      if(!row.url){
        this.$alert('请先生成PDF', '提示')
        return
      }
      if (row.url) {
        let url = this.$getCookie('imgPath') + row.url
        window.open(url, '_blank')
        return
      }
    },
    // 生成pdf
    handleCreatePdf() {
      console.log("selection的数据为",this.selection);
      console.log("ids的数据为",this.ids);
      createPdf(this.ids).then((response) => {
        if(response.code == 200){
          this.$modal.msgSuccess('创建成功')
          this.getList()
        }
        
      }) 
    },
    // 单位名称返回值
    selectChange(value) {
      this.form.customerId = value.id
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
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    //新增提交
    submitForm() {
      if (this.form.ddh == undefined || this.form.endDate == undefined || this.form.startDate == undefined || this.form.customerId == undefined) {
        this.$modal.msgWarning('请完善全部信息后提交!')
      } else {
        let form = JSON.parse(JSON.stringify(this.form))
        form.startDate = form.startDate + ' 00:00:00'
        form.endDate = form.endDate + ' 23:59:59'
        saveOrUpdate(form).then((response) => {
          this.$modal.msgSuccess('创建成功')
          this.open = false
          this.getList()
        })
      }
    },
    //编辑提交
    defaultForm() {
      let form = JSON.parse(JSON.stringify(this.form))
      form.openDay = this.form.openDay.join(',')
      defaultData(form).then((response) => {
        this.$modal.msgSuccess('编辑成功')
        this.open = false
        this.getList()
      })
    },
    // 编辑
    editList(row) {
      this.reset()
      this.loading = true
      const ids = row.id || this.ids
      getData(ids).then((response) => {
        this.form = response.data
        this.form.openDay = this.form.openDay.split(',')
        this.form.openDay = this.form.openDay.map((el) => parseInt(el))
        this.loading = false
      })
      this.open = true
      this.title = '编辑'
    },
    //获取分中心
    getfzxData() {
      getBranchData().then((response) => {
        this.branchIdOptions = response.data
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    //删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return deleteData(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        ddh: undefined,
        startDate: undefined,
        endDate: undefined,
        customerId: undefined,
      }
    },
    showDialog() {
      this.reset()
      this.open = true
      this.title = '创建复查名单'
    },
    ///取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
  },
}
</script>

<style></style>
