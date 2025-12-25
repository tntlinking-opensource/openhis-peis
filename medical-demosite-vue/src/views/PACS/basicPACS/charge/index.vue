<!-- PACS收费项目 开发人：麦沃德科技矢北/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item prop="sfxmsrm" label="输入码">
        <el-input v-model="queryParams.sfxmsrm" clearable style="width:200px" placeholder="请输入"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item prop="examfeeitemName" label="收费项目名称">
        <el-input v-model="queryParams.examfeeitemName" clearable style="width:200px" placeholder="请输入"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item prop="xb" label="性别">
        <el-select v-model="queryParams.xb" clearable style="width:160px" placeholder="请选择">
          <el-option :label="item.text" :value="item.id" v-for="item in xbOptions" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="divisionId" label="科室">
        <input-select :selectData="deptData" :isTrim="true" @change="deptChange"></input-select>
      </el-form-item>
      <el-form-item prop="examfeeitemCode" label="接口代码">
        <el-input v-model="queryParams.examfeeitemCode" clearable placeholder="请输入"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
          v-hasPermi="['basicPACS:charge:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['basicPACS:charge:edit']">编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['basicPACS:charge:delete']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div class="table-box">
      <el-table ref="table" :data="tableList" v-loading="loading" border stripe height="100%"
        @selection-change="handleSelectionChange" @row-click="rowClick" @expand-change="expandChange"
        :row-key="getRowKeys" :expand-row-keys="expands">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column type="expand" label="展开" align="center">
          <el-table ref="tableExam" :data="examList" size="mini" stripe border v-loading="examLoading">
            <el-table-column prop="examitemName" label="检查项目名称" align="center" />
            <el-table-column prop="fMale" label="性别" align="center">
              <template slot-scope="scope">
                <div v-for="item in xbOptions" :key="item.id">
                  <span v-if="(item.id == scope.row.fMale)">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="type" label="类型" align="center">
              <template slot-scope="scope">
                <div v-for="item in tjlxOptions" :key="item.id">
                  <span v-if="(item.id == scope.row.type)">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="deptName" label="科室名称" align="center" />
          </el-table>
        </el-table-column>
        <el-table-column align="center" prop="examfeeitemName" label="收费项目名称" />
        <el-table-column align="center" prop="sfxmsrm" label="输入码" />
        <el-table-column align="center" prop="examfeeitemCode" label="接口代码" />
        <el-table-column align="center" prop="departName" label="所属科室名称" />
        <el-table-column label="性别" prop="xb" min-width="140" align="center">
          <template slot-scope="scope">
            <div v-for="item in xbOptions" :key="item.id">
              <span v-if="(item.id == scope.row.xb)">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="examfeeitemNameprn" label="收费项目打印名称" />
        <el-table-column align="center" prop="xh" label="打印排列序号" />
        <el-table-column align="center" prop="jcyy" label="检查意义" />
        <el-table-column class-name="small-padding fixed-width" width="200px" label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" v-hasPermi="['basicPACS:charge:edit']"
              @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" v-hasPermi="['basicPACS:charge:delete']"
              @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页器 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <addDialog ref="addDialog" @getList="getList"></addDialog>
  </div>
</template>
<script>
import { getItemsList, delItems, getSignList } from '@/api/PACS/basicPACS/charge.js'
import addDialog from './add.vue'
export default {
  components: {  addDialog },
  data() {
    return {
      //总数
      total: 0,
      // 遮罩层
      loading: false,
      //表格信息
      tableList: [],
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 选中数组
      ids: [],
      //查询参数
      queryParams: {
        current: 1,
        size: 10,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        xb: undefined,
        divisionId: undefined,
        examfeeitemCode: undefined,
      },
      // 是否显示搜索
      showSearch: true,
      // 科室名称
      deptData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '科室',
        url: '/pacs/exam/dept/list',
        bindValue: '', //初始值(必传)
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
      },
      // 性别
      xbOptions: [
        { id: 0, text: "男" },
        { id: 1, text: "女" },
        { id: 2, text: "通用" }
      ],
      // 体检类型
      tjlxOptions: [
        {
          id: 0,
          text: "健康体检"
        },
        {
          id: 1,
          text: "职业体检"
        },
        {
          id: 2,
          text: "综合"
        }
      ],
      // 显示模态框
      showExam: false,
      // 检查项目列表
      examList: [],
      // 遮罩层
      examLoading: false,
      // 要展开的行，数值的元素是row的key值
      expands: [],
    }
  },
  created() {
    this.getList();
  },
  methods: {
    ///搜素
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    // 查询列表
    getList() {
      this.loading = true;
      getItemsList(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 重置搜索
    resetQuery() {
      this.deptData.bindValue = '';
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 添加
    handleAdd() {
      this.$refs.addDialog.handleAdd()
    },
    // 编辑
    handleUpdate(row) {
      var id = row.id || this.ids[0]
      this.$refs.addDialog.handleUpdate(id)
    },
    // 删除
    handleDelete(row) {
      var ids = row.id || this.ids
      this.$modal.confirm('确定要删除所选记录吗？').then(() => {
        return delItems(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && (col.label == "操作" || col.type == "selection")) {
        return
      }
      var isSelect = false
      this.ids.forEach(el => {
        if (el == row.id) {
          isSelect = true
        }
      });
      if (isSelect) return
      this.$refs.table.clearSelection();
      this.$refs.table.toggleRowSelection(row);
      this.ids = [row.id];
    },
    // 科室选项
    deptChange(value) {
      this.queryParams.divisionId = value.id
      this.deptData.bindValue = value.id;
    },
    // 展开行
    expandChange(row) {
      this.examLoading = true
      this.examList = []
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id);
      } else {
        this.getSignList(row.id)
      }
    },
    // 获取检查项目
    getSignList(id) {
      getSignList({ id: id }).then((res) => {
        this.examLoading = false
        this.examList = res.data
        this.expands = []
        this.expands.push(id);
      })
    },
    // 获取row的key值
    getRowKeys(row) {
      return row.id;
    },
  }
}
</script>