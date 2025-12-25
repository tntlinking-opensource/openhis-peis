<!-- 体检基础套餐维护 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="体检套餐名称" prop="tjtcmc">
        <el-input v-model="queryParams.tjtcmc" placeholder="请输入体检套餐名称" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检套餐输入码" prop="tjtcsrm">
        <el-input v-model="queryParams.tjtcsrm" placeholder="请输入输入码" clearable style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" clearable style="width: 180px" @change="handleQuery">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
          <el-option label="复查" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="branchIds">
        <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 180px" @change="handleQuery">
          <el-option v-for="options in branchOptions" :key="options.branchId" :label="options.fzx" :value="options.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:healthSmall:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" @click="handleUpdate" :disabled="single" v-hasPermi="['basis:healthSmall:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-view" @click="handleUpdate" :disabled="single">查看</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['basis:healthSmall:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh" :disabled="single" @click="handlecf" v-hasPermi="['basis:healthSmall:copy']">重复 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="handlesynchronous" v-hasPermi="['basis:healthSmall:synchronous']">同步 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="handleSetCombo(1, 1)" v-hasPermi="['basis:healthSmall:setActive']">活动套餐 </el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handleSetCombo(0, 1)" v-hasPermi="['basis:healthSmall:nosetActive']">取消活动套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="handleSetCombo(1, 2)" v-hasPermi="['basis:healthSmall:setRecomend']">推荐套餐 </el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handleSetCombo(0, 2)" v-hasPermi="['basis:healthSmall:nosetRecomend']">取消推荐套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handleSetBan" v-hasPermi="['basis:healthSmall:setBan']">禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handlenosetBan" v-hasPermi="['basis:healthSmall:nosetBan']">反禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="single" v-hasPermi="['basis:healthSmall:setAPP']" icon="el-icon-setting" @click="handleSetApp">设为APP套餐</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe :row-key="getRowKeys" :expand-row-keys="expands" @expand-change="expandChange" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="扩展内容" type="expand" align="center" width="90">
          <template slot-scope="scope">
            <div style="padding: 0 30px">
              <el-table :data="dataList" align="center" border stripe v-loading="expandLoading" @selection-change="handleSelectionChange2">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column align="center" prop="sfxmmc" label="收费项目名称"> </el-table-column>
                <el-table-column align="center" prop="sfxmsrm" label="收费项目输入码"> </el-table-column>
                <el-table-column align="center" prop="xb" label="性别">
                  <template slot-scope="scope">
                    <span v-if="scope.row.xb == 0">男</span>
                    <span v-else-if="scope.row.xb == 1">女</span>
                    <span v-else>通用</span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="jcyy" label="检查意义"> </el-table-column>
                <el-table-column align="center" prop="jg" label="价格"> </el-table-column>
                <el-table-column align="center" prop="bz" label="备注"> </el-table-column>
                <el-table-column align="center" prop="tjlx" label="体检类型">
                  <template slot-scope="scope">
                    <span v-if="scope.row.tjlx == 0">健康体检</span>
                    <span v-else-if="scope.row.tjlx == 1">职业体检</span>
                    <span v-else-if="scope.row.tjlx == 2">综合体检</span>
                    <span v-else-if="scope.row.tjlx == 3">综合体检</span>
                    <span v-else-if="scope.row.tjlx == 4">复查</span>
                    <span v-else> </span>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="ssks" label="所属科室"> </el-table-column>
              </el-table>
              <!-- 分页 -->
              <pagination v-show="total2 > 0" :total="total2" :page.sync="queryParams2.current" :limit.sync="queryParams2.size" @pagination="getLqryData(scope.row.id)" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="体检套餐名称" prop="tjtcmc" align="center" min-width="200px" show-overflow-tooltip />
        <el-table-column label="体检套餐输入码" prop="tjtcsrm" align="center" min-width="140px" show-overflow-tooltip />
        <el-table-column label="体检套餐简称" prop="tjtcjc" align="center" min-width="120px" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="tjlx" align="center" min-width="100px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.tjlx == 0">健康体检</span>
            <span v-else-if="scope.row.tjlx == 1">职业体检</span>
            <span v-else-if="scope.row.tjlx == 2">综合体检</span>
            <span v-else-if="scope.row.tjlx == 3">综合体检</span>
            <span v-else-if="scope.row.tjlx == 4">复查</span>
            <span v-else> </span>
          </template>
        </el-table-column>
        <el-table-column label="套餐原始价格" prop="tcysjg" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="套餐折扣" prop="tczk" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="优惠价" prop="zhjg" align="center" min-width="100px" show-overflow-tooltip />
        <el-table-column label="禁用" prop="isBan" align="center" min-width="100px" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.isBan == 1">是</el-tag>
            <el-tag v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否APP套餐" prop="appId" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.appId">是</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否已婚套餐" prop="sfyhtc" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.sfyhtc == 0">是</el-tag>
            <el-tag type="danger" v-else-if="scope.row.sfyhtc == 1">否</el-tag>
            <el-tag type="success" v-else>通用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="接害因素" prop="jhysmc" min-width="120px" align="center" show-overflow-tooltip />
        <el-table-column label="职业体检类别" prop="zytjlb" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.zytjlb == 0">上岗前</span>
            <span v-else-if="scope.row.zytjlb == 1">在岗期间 </span>
            <span v-else-if="scope.row.zytjlb == 2">离岗时 </span>
            <span v-else-if="scope.row.zytjlb == 3">离岗后</span>
            <span v-else-if="scope.row.zytjlb == 4">应急</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="适用性别" prop="syxb" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.syxb == 0">男</el-tag>
            <el-tag type="danger" v-else-if="scope.row.syxb == 1">女</el-tag>
            <el-tag type="success" v-else>通用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="年龄从" prop="nlz" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="年龄至" prop="nld" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="分中心" prop="fzx" min-width="120px" align="center" show-overflow-tooltip> </el-table-column>
        <el-table-column label="已关联检查项目数量" prop="num" min-width="150px" align="center" show-overflow-tooltip />
        <el-table-column label="是否推荐套餐" prop="isRecommended" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isRecommended == 1">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否活动套餐" prop="isActive" min-width="120px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isActive == 1">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="修改人" prop="modifier" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="修改时间" prop="modifydate" min-width="160px" align="center" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <add-items ref="addItems" @getList="getList"></add-items>
    <!-- 重复对话框 -->
    <repeat ref="repeat" @getList="getList"></repeat>
  </div>
</template>
<script>
import { listCreatecombo, delCreatecombo, tjList, setBan, expendListData, recommendData, activeData, synchronous } from '@/api/basis/healthsmall'
import { setAppMeal } from '@/api/sale/create_offer'
import addItems from './add'
import repeat from './repeat'
import { listBranch } from '@/api/system/branch'
export default {
  name: 'HealthSmall',
  components: { addItems, repeat },
  data() {
    return {
      // 分中心列表
      branchOptions: [],
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      selections: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格展开时加载
      expandLoading: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        tcmc: '',
        tjtcsrm: '',
        tjlx: '',
        branchIds: '',
      },
      // 表格展示数据
      tableList: [],
      dataList: [],
      //扩展表格的查询参数
      queryParams2: {},
      tjlxType: [],
      total2: 0,
      // 获取row的key值
      getRowKeys(row) {
        return row.id
      },
      // 要展开的行，数值的元素是row的key值
      expands: [],
      queryBranch: {
        current: 1,
        size: 100,
      },
    }
  },
  created() {
    this.getBranch()
    this.getList()
  },
  methods: {
    // 初始化列表
    getList() {
      this.loading = true
      listCreatecombo(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          for (let i in this.branchOptions) {
            for (let j in this.tableList) {
              if (this.branchOptions[i].branchId == this.tableList[j].fzxid) {
                this.tableList[j].fzxid = this.branchOptions[i].fzx
              }
            }
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 有查询条件列表
    gettjList() {
      this.loading = true
      tjList(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.tjlxType = selection.map((item) => item.tjlx)
      this.selections = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表格展开的多选
    handleSelectionChange2(selection) {
      this.xsId = selection.map((item) => item.id)
    },
    // 同步
    handlesynchronous() {
      var id = this.ids.join(',')
      this.$confirm('是否确认同步数据?', '同步提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          synchronous(id).then((response) => {
            if (response.code == 200) {
              this.$modal.msgSuccess('操作成功')
            }
          })
        })
        .catch(() => { })
    },
    /** 查询分中心维护列表 */
    getBranch() {
      listBranch(this.queryBranch).then((response) => {
        this.branchOptions = response.data.records
      })
    },
    // 展开行时发起请求
    expandChange(row, expandedRows) {
      if (expandedRows.length) {
        this.expands = []
        if (row) {
          this.expands.push(row.id)
          this.getLqryData(row.id)
        }
      } else {
        this.expands = []
      }
    },
    // 获取领取人员的信息
    getLqryData(id) {
      this.queryParams2.test = id
      this.expandLoading = true
      expendListData(this.queryParams2)
        .then((response) => {
          this.dataList = response.data.records
          this.expandLoading = false
        })
        .catch(() => {
          this.expandLoading = false
        })
    },
    // 设置活动套餐/取消设置，设置推荐套餐/取消设置
    handleSetCombo(state, type) {
      for (var i in this.tjlxType) {
        if (this.tjlxType[i] != 0) {
          this.$modal.msgWarning('请选择健康类型的最小套餐')
          return
        }
      }
      this.$confirm(state == 1 ? `确定将所选项目设置为${type == 1 ? '活动' : '推荐'}套餐吗` : `确定要取消${type == 1 ? '活动' : '推荐'}套餐吗`, '提示', {
        type: 'warning',
      })
        .then(() => {
          const ids = this.ids.join(',')
          const params = {
            comboIds: ids,
            state,
          }
          this.loading = true
          if (type == 1) {
            activeData(params)
              .then((response) => {
                this.$modal.msgSuccess('操作成功')
                this.getList()
                this.loading = false
              })
              .catch((error) => {
                console.error(error)
                this.loading = false
              })
          } else {
            recommendData(params)
              .then((response) => {
                this.$modal.msgSuccess('操作成功')
                this.getList()
                this.loading = false
              })
              .catch((error) => {
                console.error(error)
                this.loading = false
              })
          }
        })
        .catch(() => { })
    },
    // 禁用
    handleSetBan() {
      this.$confirm('确定禁用所选项目吗?', '禁用提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const ids = this.ids.join(',')
          const params = {
            comboIds: ids,
            state: 1,
          }
          setBan(params).then((response) => {
            this.$modal.msgSuccess('操作成功')
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 反禁用
    handlenosetBan() {
      this.$confirm('确定反禁用所选项目吗?', '反禁用提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const ids = this.ids.join(',')
          const params = {
            comboIds: ids,
            state: 0,
          }
          setBan(params).then((response) => {
            this.$modal.msgSuccess('操作成功')
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 设置为APP套餐
    handleSetApp() {
      let err = ''
      this.selections.forEach((el) => {
        if (el.appId) {
          err = '所选套餐已经设置为APP套餐,不可重复设置'
        }
      })
      if (err) {
        this.$alert(err, '提示')
        return
      }
      this.$modal
        .confirm('是否将所选套餐设置为APP套餐？', '提示')
        .then(() => {
          this.loading = true
          setAppMeal({
            id: this.ids.join(','),
          })
            .then(() => {
              this.getList()
              this.$modal.msgSuccess('操作成功！')
              this.loading = false
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
            })
        })
        .catch(() => { })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.expands = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addItems.handleAdd()
    },
    // 取消按钮
    cancel() {
      this.reset()
    },
    //重复
    handlecf() {
      const id = this.ids[0]
      this.$refs.repeat.handleAd(id)
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delCreatecombo(ids)
          return
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      this.$refs.addItems.handleUpdate(row, this.ids)
    },
  },
}
</script>
