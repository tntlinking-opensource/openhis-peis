<!-- PACS检查项目 开发人：麦沃德科技矢北/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch">
      <el-form-item label="名称" prop="examitemName">
        <el-input v-model="queryParams.examitemName" placeholder="请输入名称" clearable style="width: 200px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 200px;"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="性别" prop="forMale">
        <el-select v-model="queryParams.forMale" placeholder="请选择性别" clearable style="width: 160px;">
          <el-option :label="item.text" :value="item.id" v-for="item in xbOptions" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择体检类型" clearable style="width: 160px;">
          <el-option :label="item.text" :value="item.id" v-for="item in tjlxOptions" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="科室" prop="divisionId">
        <input-select :selectData="deptData" :isTrim="true" @change="deptChange"></input-select>
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
          v-hasPermi="['basicPACS:inspect:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['basicPACS:inspect:edit']">编辑
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" v-hasPermi="['basicPACS:inspect:view']" :disabled="single"
          @click="handleView" icon="el-icon-view">查看
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['basicPACS:inspect:delete']">删除
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
        <el-table-column type="expand" label="展开" align="center" width="55">
          <el-table ref="tableSign" :data="signList" size="mini" stripe border v-loading="signLoading">
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="顺序" min-width="60" prop="orderindex" align="center" />
            <el-table-column label="体征词名称" min-width="160" prop="name" align="center" show-overflow-tooltip />
            <el-table-column label="是否阳性结果" width="100" prop="isPositive" align="center">
              <template slot-scope="scope">
                <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isPositive == 1"><i class="el-icon-check"></i>
                </el-tag>
                <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
              </template>
            </el-table-column>
            <el-table-column label="体征详细描述" min-width="180" prop="bodyDetail" align="center"
              show-overflow-tooltip />
            <el-table-column label="结论词" min-width="120" prop="resultName" align="center" show-overflow-tooltip />
            <el-table-column label="可疑疾病重症级0到9级" min-width="100" prop="intensiveLevel" align="center" />
          </el-table>
        </el-table-column>
        <el-table-column prop="examitemName" label="名称" align="center" />
        <el-table-column prop="inputCode" label="输入码" align="center" />
        <el-table-column prop="type" label="类型" align="center">
          <template slot-scope="scope">
            <div v-for="item in tjlxOptions" :key="item.id">
              <span v-if="(item.id == scope.row.type)">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="deptName" label="科室" align="center" />
        <el-table-column prop="fMale" label="男/女标示" align="center">
          <template slot-scope="scope">
            <div v-for="item in xbOptions" :key="item.id">
              <span v-if="(item.id == scope.row.fMale)">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column class-name="small-padding fixed-width" label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" v-hasPermi="['basicPACS:inspect:edit']"
              @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" v-hasPermi="['basicPACS:inspect:delete']"
              @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList()" />
    <add-dialog ref="addDialog" @getList="getList"></add-dialog>
    <view-dialog ref="viewDialog"></view-dialog>
  </div>
</template>

<script>
import { getExamList, getSignList, delExam } from '@/api/PACS/basicPACS/inspect.js'
import addDialog from './add.vue'
import viewDialog from './view.vue'
export default {
  components: { addDialog, viewDialog },
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
        size: 20,
        examitemName: undefined,
        inputCode: undefined,
        forMale: undefined,
        type: undefined,
        divisionId: undefined,
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
      showSign: false,
      // 检查项目列表
      signList: [],
      // 遮罩层
      signLoading: false,
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
      getExamList(this.queryParams).then(response => {
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
        return delExam(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    //查看
    handleView(row) {
      var id = row.id || this.ids[0]
      this.$refs.viewDialog.handleView(id)
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
      this.signLoading = true
      this.signList = []
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id);
      } else {
        this.getSignList(row.id)
      }
    },
    // 获取体征词
    getSignList(id) {
      getSignList({ id: id }).then((res) => {
        this.signLoading = false
        this.signList = res.data
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