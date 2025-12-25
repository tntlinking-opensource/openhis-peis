<!-- 创建套餐  开发人：麦沃德科技矢北/半夏 -->
<template>
  <div class="app-container flex-direction-column create-offer" style="min-height: 380px">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="套餐名称" prop="tjtcmc">
        <el-input style="width: 230px" clearable v-model="queryParams.tjtcmc" placeholder="请输入套餐名称" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="输入码" prop="inputCode">
        <el-input style="width: 230px" clearable v-model="queryParams.inputCode" placeholder="请输入输入码" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" clearable placeholder="请选择">
          <el-option style="width: 230px" v-for="options in tjlxOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="适用性别" prop="syxb">
        <el-select v-model="queryParams.syxb" clearable placeholder="请选择">
          <el-option style="width: 230px" v-for="options in xbOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="平安套餐ID" prop="pinganId">
        <el-input style="width: 200px" clearable v-model="queryParams.pinganId" placeholder="请输入平安套餐ID" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" v-hasPermi="['sale:createOffer:add']" @click="handleAdd">增加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" :disabled="single" icon="el-icon-edit" v-hasPermi="['sale:createOffer:edit']" @click="handleUpdate">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" v-hasPermi="['sale:createOffer:delete']" icon="el-icon-delete" @click="handleDelete">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" :disabled="single" v-hasPermi="['sale:createOffer:lookup']" icon="el-icon-view" @click="handleLook">查看 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" :disabled="single" v-hasPermi="['sale:createOffer:repeat']" icon="el-icon-files" @click="handleRepeat">重复 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="multiple" v-hasPermi="['sale:createOffer:exportOffer']" icon="el-icon-upload2" @click="handleExport">导出套餐</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" :disabled="multiple" icon="el-icon-upload2" v-hasPermi="['sale:createOffer:exportAgreementOffer']" @click="handleExportOffer">导出协议套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" v-hasPermi="['sale:createOffer:disable']" icon="el-icon-lock" @click="handleDisable(1)">禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" v-hasPermi="['sale:createOffer:disable']" icon="el-icon-unlock" @click="handleDisable(0)"> 反禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" :disabled="single" v-hasPermi="['sale:createOffer:set']" icon="el-icon-setting" @click="handleSet">设置平安ID </el-button>
      </el-col>
    </el-row>

    <drag-row left-size="50%" right-size="50%">
      <template #leftBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table border ref="tableData" :data="tableList" v-loading="loading" @selection-change="handleSelectionChange" height="100%" :row-class-name="rowClassName" @row-dblclick="getItemsData" @row-click="rowClick" :key="tableKey">
              <el-table-column type="selection" width="45" fixed align="center" class-name="no-drag" />
              <el-table-column label="序列" width="50" type="index" align="center" class-name="no-drag" />
              <!-- 动态列区域 - 可以拖动排序 -->
              <el-table-column v-for="column in dynamicColumns" :key="column.prop" :prop="column.prop" :label="column.label" :min-width="column.minWidth" :align="column.align || 'center'" :show-overflow-tooltip="column.showOverflowTooltip" :fixed="column.fixed" :class-name="column.className" :type="column.type" resizable>
                <template slot-scope="scope">
                  <!-- 根据列属性动态显示内容 -->
                  <template v-if="column.prop === 'tjtcmc'">{{ scope.row.tjtcmc }}</template>
                  <template v-else-if="column.prop === 'tjlx'">
                    <div v-for="item in tjlxOptions" :key="item.id">
                      <span v-if="item.id == scope.row.tjlx">{{ item.text }}</span>
                    </div>
                  </template>
                  <template v-else-if="column.prop === 'tcysjg'">{{ scope.row.tcysjg }}</template>
                  <template v-else-if="column.prop === 'tczk'">{{ scope.row.tczk }}</template>
                  <template v-else-if="column.prop === 'zhjg'">
                    {{ scope.row.zhjg ? Number(scope.row.zhjg).toFixed(2) : '0.00' }}
                  </template>
                  <template v-else-if="column.prop === 'costprice'">
                    {{ scope.row.costprice ? Number(scope.row.costprice).toFixed(2) : '' }}
                  </template>
                  <template v-else-if="column.prop === 'varCostRate'">{{ scope.row.varCostRate }}</template>
                  <template v-else-if="column.prop === 'jhysName'">{{ scope.row.jhysName }}</template>
                  <template v-else-if="column.prop === 'syxb'">
                    <span v-if="scope.row.syxb == 0">男</span>
                    <span v-if="scope.row.syxb == 1">女</span>
                    <span v-if="scope.row.syxb == 2">通用</span>
                  </template>
                  <template v-else-if="column.prop === 'sfwc'">
                    <el-tag v-if="scope.row.sfwc == 1">是</el-tag>
                    <el-tag type="danger" v-else>否</el-tag>
                  </template>
                  <template v-else-if="column.prop === 'forbidden'">
                    <el-tag v-if="scope.row.forbidden == 1">是</el-tag>
                    <el-tag type="danger" v-else>否</el-tag>
                  </template>
                  <template v-else-if="column.prop === 'tjtcjc'">{{ scope.row.tjtcjc }}</template>
                  <template v-else-if="column.prop === 'khdwmc'">{{ scope.row.khdwmc }}</template>
                  <template v-else-if="column.prop === 'sfybd'">
                    <el-tag v-if="scope.row.forbidden == 1">是</el-tag>
                    <el-tag type="danger" v-else>否</el-tag>
                  </template>
                  <template v-else-if="column.prop === 'sfyhtc'">
                    <span v-if="scope.row.sfyhtc == 0">是</span>
                    <span v-else-if="scope.row.sfyhtc == 1">否</span>
                    <span v-else>通用</span>
                  </template>
                  <template v-else-if="column.prop === 'nlz'">{{ scope.row.nlz }}</template>
                  <template v-else-if="column.prop === 'nld'">{{ scope.row.nld }}</template>
                  <template v-else-if="column.prop === 'fkfs'">
                    <span v-if="scope.row.fkfs == 0">统收</span>
                    <span v-if="scope.row.fkfs == 1">现金</span>
                  </template>
                  <template v-else-if="column.prop === 'zytjlb'">
                    <div v-for="item in zytjlbOptions" :key="item.id">
                      <span v-if="item.id == scope.row.zytjlb">{{ item.text }}</span>
                    </div>
                  </template>
                  <template v-else-if="column.prop === 'kxsl'">{{ scope.row.kxsl }}</template>
                  <template v-else-if="column.prop === 'fzxName'">{{ scope.row.fzxName }}</template>
                  <template v-else-if="column.prop === 'pinganId'">{{ scope.row.pinganId }}</template>
                  <template v-else-if="column.prop === 'tjtcsrm'">{{ scope.row.tjtcsrm }}</template>
                </template>
              </el-table-column>
              <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width no-drag">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" style="color: #6b9eff" @click="handleView(scope.row)">查看项目 </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        </div>
      </template>
      <template #rightBox>
        <div class="item-box flex-direction-column">
          <div class="table-box">
            <el-table border v-loading="itemsLoading" :data="itemsList" height="100%" stripe>
              <el-table-column label="序列" width="55" type="index" align="center" />
              <el-table-column label="收费项目名称" min-width="200" prop="sfxmmc" align="center" show-overflow-tooltip />
              <el-table-column label="性别" min-width="90" prop="xb" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.xb == 0">男</span>
                  <span v-if="scope.row.xb == 1">女</span>
                  <span v-if="scope.row.xb == 2">通用</span>
                </template>
              </el-table-column>
              <el-table-column label="销售打印分类" min-width="120" prop="printType" align="center" show-overflow-tooltip />
              <el-table-column label="价格" min-width="120" prop="jg" align="center" show-overflow-tooltip />
              <el-table-column label="检查意义" min-width="180" prop="jcyy" align="center" show-overflow-tooltip />
              <el-table-column label="备注" min-width="180" prop="bz" align="center" show-overflow-tooltip />
              <el-table-column label="体检类型" min-width="120" prop="tjlx" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <div v-for="item in tjlxOptions" :key="item.id">
                    <span v-if="item.id == scope.row.tjlx">{{ item.text }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="所属科室" min-width="120" prop="ssks" align="center" show-overflow-tooltip />
              <el-table-column label="是否备选" min-width="120" prop="sfbx" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.sfbx == 0">否</span>
                  <span v-if="scope.row.sfbx == 1">是</span>
                </template>
              </el-table-column>
              <el-table-column label="备选分组" min-width="120" prop="group" align="center" show-overflow-tooltip />
              <el-table-column label="组类型" min-width="120" prop="groupType" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.groupType == 0">组内选</span>
                  <span v-if="scope.row.groupType == 1">组间选</span>
                  <span v-if="scope.row.groupType == 2">组任选</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" style="color: #6b9eff" @click="handleView(scope.row)">查看项目 </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </template>
    </drag-row>

    <!-- 查看项目对话框 -->
    <el-dialog title="查看项目" :visible.sync="viewOpen" width="600px" custom-class="view-dialog" append-to-body>
      <div class="flex-direction-column">
        <div class="table-box">
          <el-table size="mini" border v-loading="viewLoading" :data="viewList" height="100%" stripe style="min-height: 220px">
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column property="examitemName" label="名称" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="viewTotal > 0" :total="viewTotal" :page.sync="viewParams.current" :limit.sync="viewParams.size" @pagination="getViewList" />
      </div>
    </el-dialog>

    <!-- 重复对话框 -->
    <repeat-item ref="repeatItem" @getList="getList"></repeat-item>
    <!-- 查看对话框 -->
    <view-item ref="viewItem"></view-item>
    <!-- 平安ID设置 -->
    <set-item ref="setItem" @getList="getList"></set-item>
  </div>
</template>

<script>
import { listCreatemeal, getItemsData, getExamsByItemId, isEdit, isRemove, delCreatemeal, setBan } from '@/api/sale/create_offer'
import { isCaiWu } from '@/api/finance/tally/bank_account.js'
import dragRow from '@/components/DragRow'
import viewItem from './view'
import setItem from './set'
import repeatItem from './repeat'
import Sortable from 'sortablejs'; // 导入SortableJS

export default {
  name: 'Create_offer',
  components: { dragRow, viewItem, setItem, repeatItem },
  data() {
    return {
      // 遮罩层
      loading: false,
      itemsLoading: false,
      viewLoading: false,
      // 选中数组
      ids: [],
      // 双击选中id
      dbId: null,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 体检类型
      tjlxOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
      // 性别
      xbOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      // 职业体检类别
      zytjlbOptions: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      // 分中心列表
      branchOptions: [
        {
          id: 2,
          fzx: 'QDITHC健康管理中心',
        },
      ],
      ///查询参数
      queryParams: {
        current: 1,
        size: 20,
        tjtcmc: undefined,
        inputCode: undefined,
        tjlx: undefined,
        syxb: undefined,
        pinganId: undefined,
      },
      viewParams: {
        current: 1,
        size: 10,
        id: undefined,
      },
      // 总条数
      total: 0,
      viewTotal: 0,
      // 套餐表格数据
      tableList: [],
      // 收费项目表格数据
      itemsList: [],
      // 查看项目表格数据
      viewList: [],
      // 显示模态框
      open: false,
      viewOpen: false,
      // 是否为财务
      isCW: false,
      // 动态列定义
      dynamicColumns: [
        { prop: "tjtcmc", label: "套餐名称", minWidth: 200, showOverflowTooltip: true },
        { prop: "tjlx", label: "体检类型", minWidth: 90 },
        { prop: "tcysjg", label: "原始价格", minWidth: 90 },
        { prop: "tczk", label: "套餐折扣", minWidth: 90 },
        { prop: "zhjg", label: "优惠价", minWidth: 90 },
        { prop: "costprice", label: "成本价", minWidth: 90, show: false },
        { prop: "varCostRate", label: "变动成本率", minWidth: 100 },
        { prop: "jhysName", label: "接害因素", minWidth: 120, showOverflowTooltip: true },
        { prop: "syxb", label: "适用性别", minWidth: 90 },
        { prop: "sfwc", label: "是否外出", minWidth: 90 },
        { prop: "forbidden", label: "已禁用", minWidth: 90 },
        { prop: "tjtcjc", label: "套餐简称", minWidth: 160, showOverflowTooltip: true },
        { prop: "khdwmc", label: "客户单位名称", minWidth: 200, showOverflowTooltip: true },
        { prop: "sfybd", label: "已备单", minWidth: 90 },
        { prop: "sfyhtc", label: "已婚套餐", minWidth: 90 },
        { prop: "nlz", label: "年龄自", minWidth: 90 },
        { prop: "nld", label: "年龄至", minWidth: 90 },
        { prop: "fkfs", label: "付款方式", minWidth: 90 },
        { prop: "zytjlb", label: "职业体检类别", minWidth: 110 },
        { prop: "kxsl", label: "可选数量", minWidth: 90 },
        { prop: "fzxName", label: "分中心", minWidth: 90, showOverflowTooltip: true },
        { prop: "pinganId", label: "平安套餐ID", minWidth: 100 },
        { prop: "tjtcsrm", label: "体检套餐输入码", minWidth: 140, showOverflowTooltip: true }
      ],
      sortable: null, // Sortable实例
      tableKey: Date.now() // 强制刷新的key
    }
  },
  created() {
    isCaiWu().then((res) => {
      if (res && res.data == 'success') {
        this.isCW = true
      }
    }).catch(error => {
      console.error('Error in isCaiWu:', error);
    });

    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.initSortable();
    });
  },
  activated() {
    this.getList()
  },
  methods: {
    // 初始化可排序功能 - 修复数据列跟随问题
    // 初始化可排序功能（确保DOM更新后重新绑定）
    initSortable() {
      // 销毁旧实例
      if (this.sortable) {
        this.sortable.destroy();
        this.sortable = null;
      }

      // 确保DOM存在
      if (!this.$refs.tableData) return;
      const el = this.$refs.tableData.$el.querySelector('.el-table__header-wrapper thead > tr');
      if (!el) return;

      // 创建新实例
      this.sortable = new Sortable(el, {
        animation: 300,
        handle: ".cell",
        filter: ".no-drag", // 不处理固定列
        preventOnFilter: false,
        draggable: "th",
        ghostClass: "sortable-ghost",
        chosenClass: "sortable-chosen",
        dragClass: "sortable-drag",
        onEnd: (evt) => {
          // 计算实际动态列索引（排除固定列）
          const oldIndex = evt.oldIndex;
          const newIndex = evt.newIndex;
          const fixedColCount = 2; // 选择列+序列号列

          // 排除固定列操作
          if (oldIndex < fixedColCount || newIndex < fixedColCount) return;

          // 计算动态列索引
          const dynamicOldIndex = oldIndex - fixedColCount;
          const dynamicNewIndex = newIndex - fixedColCount;

          // 确保索引有效
          if (
            dynamicOldIndex >= this.dynamicColumns.length ||
            dynamicNewIndex >= this.dynamicColumns.length
          ) return;

          // 更新列数据源
          const movedColumn = this.dynamicColumns[dynamicOldIndex];
          this.dynamicColumns.splice(dynamicOldIndex, 1);
          this.dynamicColumns.splice(dynamicNewIndex, 0, movedColumn);

          // 强制重新渲染表格
          this.tableKey = Date.now();

          // 重新初始化Sortable
          this.$nextTick(this.initSortable);
        }
      });
    },

    // 刷新页面
    resetPage(id) {
      this.getList()
      if (id == this.dbId) {
        this.getItemsData({ id: id })
      }
    },
    // 查询列表
    getList() {
      this.loading = true
      listCreatemeal(this.queryParams).then((response) => {
        if (response && response.data) {
          this.tableList = response.data.records || []
          this.total = response.data.total || 0
        } else {
          this.tableList = []
          this.total = 0
        }
        this.loading = false

        // 确保表格渲染完成后初始化拖动
        this.$nextTick(() => {
          this.initSortable();
        });
      }).catch(error => {
        console.error('Error in getList:', error);
        this.loading = false
      })
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
    // 获取收费项目信息
    getItemsData(row) {
      if (!row || !row.id) return;

      this.dbId = row.id
      this.itemsLoading = true
      getItemsData(row.id).then((res) => {
        this.itemsList = res.data || []
        this.itemsLoading = false
      }).catch(error => {
        console.error('Error in getItemsData:', error);
        this.itemsLoading = false
      })
    },
    // 更换表格颜色
    rowClassName({ row }) {
      if (!row.zhjg) {
        return 'danger-row-index'
      } else {
        return ''
      }
    },
    // 增加
    handleAdd() {
      const obj = { path: 'sale/create_offer_add', name: 'Create_offer_add' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('新增套餐', '/sale/create_offer_add')
      })
    },
    // 编辑
    handleUpdate() {
      if (this.ids.length !== 1) return;

      isEdit(this.ids[0]).then((res) => {
        if (res.data === '0') {
          const obj = { path: 'sale/create_offer_add', name: 'Create_offer_add' }
          this.$tab.closePage(obj).then(() => {
            this.$tab.openPage('编辑套餐', '/sale/create_offer_add', { id: this.ids[0] })
          })
        } else {
          this.$alert("该套餐已与订单<font color='red'>" + res.data + '</font>关联,不能再编辑！', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
            dangerouslyUseHTMLString: true,
          })
        }
      }).catch(error => {
        console.error('Error in handleUpdate:', error);
      })
    },
    // 删除
    handleDelete() {
      if (this.ids.length === 0) return;

      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(() => {
          return isRemove(this.ids)
        })
        .then((res) => {
          if (res.data == 'success') {
            delCreatemeal(this.ids).then(() => {
              this.getList()
              if (this.ids.indexOf(this.dbId) > -1) {
                this.itemsList = []
              }
              this.$modal.msgSuccess('删除成功')
            })
          } else {
            this.$alert(res.data, '提醒', {
              confirmButtonText: '确定',
              type: 'warning',
              dangerouslyUseHTMLString: true,
            })
          }
        })
        .catch(() => { })
    },
    // 查看
    handleLook() {
      if (this.ids.length !== 1) return;

      this.$refs.viewItem.handleShow(this.ids[0])
    },
    // 重复
    handleRepeat() {
      if (this.ids.length !== 1) return;

      this.$refs.repeatItem.handleShow(this.ids[0])
    },
    // 查看项目
    handleView(row) {
      if (!row || !row.id) return;

      this.viewOpen = true
      this.viewParams.id = row.id
      this.getViewList()
    },
    // 查询列表
    getViewList() {
      if (!this.viewParams.id) return;

      this.viewLoading = true
      getExamsByItemId(this.viewParams).then((response) => {
        if (response && response.data) {
          this.viewList = response.data.records || []
          this.viewTotal = response.data.total || 0
        } else {
          this.viewList = []
          this.viewTotal = 0
        }
        this.viewLoading = false
      }).catch(error => {
        console.error('Error in getViewList:', error);
        this.viewLoading = false
      })
    },
    // 导出套餐
    handleExport() {
      if (this.ids.length === 0) return;

      let tcIds = ''
      for (let i = 0; i < this.ids.length; i++) {
        tcIds = tcIds + this.ids[i] + ','
      }
      tcIds = tcIds.substring(0, tcIds.length - 1)

      this.download('/sell/createmeal/export', { tcIds }, '导出套餐.xls')
    },
    // 导出协议套餐
    handleExportOffer() {
      if (this.ids.length === 0) return;

      let tcId = this.ids.join(',')
      this.download('/sell/createmeal/exportXy', { tcId }, '导出协议套餐.xlsx')
    },
    // 禁用
    handleDisable(state) {
      if (this.ids.length === 0) return;

      var ids = this.ids.join(',')
      this.$modal
        .confirm(state == 1 ? '确定要禁用所选套餐吗？' : '确定要反禁用所选套餐吗？', '提示')
        .then(() => {
          return setBan({ ids, state })
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('操作成功！')
        })
        .catch(() => { })
    },
    // 设置平安ID
    handleSet() {
      if (this.ids.length !== 1) return;

      this.$refs.setItem.handleShow(this.ids[0])
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
  }
}
</script>
<style lang="scss">
.create-offer {
  .view-dialog {
    height: 100%;
    max-height: 600px;
  }

  /* SortableJS拖动样式 */
  .sortable-ghost {
    background: #f8f8ff;
    opacity: 0.8;
    color: #fff;
    border: 1px dashed #409eff;
  }

  .el-table th {
    user-select: none;

    &:hover {
      background: #f5f7fa;
    }
  }

  .el-table th.sortable-chosen {
    background: #ecf5ff;
  }

  .drag-handle {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 10px;
    background: #409eff;
    opacity: 0;
    transition: opacity .3s;
  }

  th:hover .drag-handle {
    opacity: 0.3;
  }
}

.create-offer .el-table .danger-row-index {
  background-color: #ec4242;
}

.el-table th:hover .cell:after {
  opacity: 1;
}
</style>