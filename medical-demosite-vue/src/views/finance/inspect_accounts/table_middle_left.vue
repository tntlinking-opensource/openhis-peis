<!-- 体检结账单-中左表格  开发人：麦沃德科技清风 -->
<template>
  <div class="table-container flex-direction-column" style="flex-shrink: 1">
    <el-form id="form" :inline="true" size="mini" style="margin-top: 5px" class="no-margin-bottom">
      <el-form-item label="登记日期">
        <el-date-picker style="width: 166px" value-format="yyyy-MM-dd" type="date" v-model="form.startRegTime" :picker-options="pickerOptions" @change="handleBlur"></el-date-picker>
      </el-form-item>
      <el-form-item label="至">
        <el-date-picker style="width: 166px" value-format="yyyy-MM-dd" type="date" v-model="form.endRegTime" :picker-options="pickerOptions" @change="handleBlur"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="form.containUnchecked" :true-label="1" :false-label="0" @change="handleBlur">含未检</el-checkbox>
        <el-checkbox v-model="form.containBj" :true-label="1" :false-label="0" @change="handleBlur">含补检</el-checkbox>
        <el-checkbox v-model="form.containBan" :true-label="1" :false-label="0" @change="handleBlur">含禁检</el-checkbox>
        <el-checkbox v-model="form.containOldSystem" :true-label="1" :false-label="0" @change="handleBlur">含老系统</el-checkbox>
      </el-form-item>
      <el-form-item label="体检号" label-width="60px">
        <el-input style="width: 130px" v-model="form.ddinputCode" @keyup.enter.native="handleBlur" placeholder="请输入体检号"></el-input>
      </el-form-item>
      <el-form-item label="姓名" label-width="60px">
        <el-input style="width: 130px" v-model="form.ddinputName" @keyup.enter.native="handleBlur" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="结账状态">
        <el-select style="width: 130px" v-model="form.checkoutStatus" clearable @change="handleBlur" placeholder="请选择结账状态">
          <el-option v-for="item in checkoutStatus" :key="item.id" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <!-- <el-col :span="1.5">
				    <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleRefresh"
					  v-hasPermi="['finance:inspectAccounts:middleLeft:sendCards']">发卡</el-button>
			    </el-col> -->
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-refresh" :disabled="single" @click="handleRefresh">刷新 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-download" @click="exportTable" v-hasPermi="['finance:inspectAccounts:middleLeft:export']">导出 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="handleAble(1)" v-hasPermi="['finance:inspectAccounts:middleLeft:disable']">禁检 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleAble(0)" v-hasPermi="['finance:inspectAccounts:middleLeft:disable']">反禁检 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="settleAccounts" v-hasPermi="['finance:inspectAccounts:middleLeft:closing']">已结账 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-coin" :disabled="multiple" @click="unsettleAccounts" v-hasPermi="['finance:inspectAccounts:middleLeft:closing']">反结账 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="unHandleAble(1)" v-hasPermi="['finance:inspectAccounts:middleLeft:unchecked']">未检禁检 </el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <div class="table-box">
      <el-table id="tableList" ref="tableList" border v-loading="loading" :data="tableList" height="100%" :row-class-name="rowClass" @selection-change="handleSelectionChange" @row-click="handleRowClick" @row-dblclick="handleRowDblClick">
        <el-table-column type="selection" width="40" align="center" fixed="left" />
        <el-table-column label="序列" width="50" type="index" align="center" fixed="left" show-overflow-tooltip />
        <el-table-column label="分中心" min-width="120px" prop="fzx" align="center" fixed="left" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="体检号" min-width="120px" prop="patientcode" fixed="left" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="体检状态" min-width="80px" prop="line" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="禁检" min-width="60px" prop="fpaused" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.fpaused" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="禁检时间" min-width="120px" prop="modifydate" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            {{ scope.row.fpaused == 1 ? scope.row.modifydate : '' }}
          </template>
        </el-table-column>
        <el-table-column label="分组" min-width="120px" prop="groupName" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="姓名" min-width="120px" prop="patientname" align="center" fixed="left" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="登记" min-width="60px" prop="fregistered" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.fRegistered" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="性别" min-width="80px" prop="sex" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <div v-for="item in sexOptions" :key="item.id">
              <span v-if="item.id == scope.row.sex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="婚姻" min-width="120px" prop="idMarriage" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <div v-for="item in marriageName" :key="item.id">
              <span v-if="item.id == scope.row.idMarriage">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="年龄" min-width="80px" prop="age" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="电话" min-width="120px" prop="phone" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="是否替检" min-width="80px" prop="countreportxml" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.countreportxml" :true-label="1" :false-label="0" class="triggerFailure"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="原体检者" min-width="120px" prop="tjr" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="体检类型" min-width="120px" prop="idExamtype" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <div v-for="item in jclb" :key="item.id">
              <span v-if="item.id == scope.row.idExamtype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="套餐" min-width="100px" prop="examName" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="原价" min-width="100px" prop="price" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="套餐原价" min-width="100px" prop="tcyj" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="套餐优惠价" min-width="100px" prop="tcyhj" align="center" show-overflow-tooltip class-name="draggable-header" />

        <el-table-column label="部门" min-width="120px" prop="orgDepart" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="体检时间" min-width="120px" prop="medicaldate" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="登记员" min-width="120px" prop="doctorreg" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="付款方式" min-width="120px" prop="chargePayway" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="输入码" min-width="120px" prop="inputCode" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="档案号" min-width="120px" prop="chiveNo" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="身份证号" min-width="120px" prop="idcardno" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="备注" min-width="120px" prop="note" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="体检号生成人" min-width="120px" prop="guidancenote2" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="结账日期" min-width="120px" prop="checkoutDate" align="center" show-overflow-tooltip class-name="draggable-header" />
        <el-table-column label="结账状态" min-width="120px" prop="checkoutStatus" align="center" show-overflow-tooltip class-name="draggable-header">
          <template slot-scope="scope">
            <span v-if="scope.row.checkoutStatus == 1">已结账</span>
            <span v-else>未结账</span>
          </template>
        </el-table-column>


        <!-- 固定列（不可拖动） -->
        <el-table-column label="报告状态" min-width="200px" align="center" show-overflow-tooltip class-name="no-drag-header">
          <el-table-column label="健康" align="center" min-width="100px" prop="jktjzt" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-for="item in tjztrender" :key="item.id">
                <span v-if="item.id == scope.row.jktjzt">{{ item.text }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="职业" align="center" min-width="100px" prop="zytjzt" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-for="item in tjztrender" :key="item.id">
                <span v-if="item.id == scope.row.zytjzt">{{ item.text }}</span>
              </div>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="实收" min-width="400px" align="center" show-overflow-tooltip class-name="no-drag-header">
          <el-table-column label="统收" min-width="80px" align="center" prop="ssts" show-overflow-tooltip></el-table-column>
          <el-table-column label="康涛" min-width="80px" align="center" prop="sskt" show-overflow-tooltip></el-table-column>
          <el-table-column label="其他" min-width="80px" align="center" prop="ssqt" show-overflow-tooltip></el-table-column>
          <el-table-column label="合计" min-width="80px" align="center" prop="sshj" show-overflow-tooltip></el-table-column>
          <el-table-column label="加项" min-width="80px" align="center" prop="ssAdd" show-overflow-tooltip></el-table-column>
        </el-table-column>

        <el-table-column label="记账统计" min-width="80px" align="center" show-overflow-tooltip class-name="draggable-header">
          <el-table-column label="记账" min-width="100px" align="center" prop="jz" show-overflow-tooltip></el-table-column>
          <el-table-column label="记账人" min-width="100px" align="center" prop="jzr" show-overflow-tooltip></el-table-column>
        </el-table-column>

      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" :page-sizes="[20, 50, 100, 200, 500]" @pagination="getList" />
  </div>
</template>
<script>
import Sortable from 'sortablejs'; // 导入Sortable.js
import { getAccountsInfoData, savePausedStatus, unSavePausedStatus, finishStatus, unfinishStatus, exportAccountsInfoData } from '@/api/finance/inspect_accounts.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
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
      // 查询参数
      queryParams: {
        current: 1,
        size: 100,
      },
      // 排检表格数据
      tableList: [],
      //form表单
      form: {
        ddinputCode: '',
        ddinputName: '',
        checkoutStatus: '',
        startRegTime: '',
        endRegTime: '',
        containBj: '',
        containBan: '',
        containUnchecked: '',
      },
      idOrder: [],
      //结账状态
      checkoutStatus: [
        { id: 0, text: '未结账' },
        { id: 1, text: '已结账' },
      ],
      //性别状态
      sexOptions: [
        { id: '0', text: '男' },
        { id: '1', text: '女' },
        { id: '2', text: '通用' },
      ],
      //婚姻状态
      marriageName: [
        { id: '1', text: '未婚' },
        { id: '2', text: '已婚' },
        { id: '3', text: '离异' },
        { id: '4', text: '丧偶' },
        { id: '5', text: '其他' },
      ],
      //报告状态--健康/职业
      tjztrender: [
        { id: 0, text: '总检开始' },
        { id: 1, text: '总检完成' },
        { id: 2, text: '报告已打印' },
        { id: 3, text: '报告一审通过' },
        { id: 4, text: '报告一审不通过' },
        { id: 5, text: '二审通过' },
        { id: 6, text: '二审不通过' },
        { id: 7, text: '终审通过' },
        { id: 8, text: '终审不通过' },
        { id: 9, text: '报告已交接' },
        { id: 10, text: '报告已通知' },
        { id: 11, text: '报告已领取' },
        { id: null, text: '总检未开始' },
      ],
      //体检类型
      jclb: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
        { id: 3, text: '复查' },
      ],
      //选中的表格rows
      selection: [],
      // 分中心id
      branchIds: '',

      //导出判断行
      rowSelection: {},
      //定义新变量-当前表格高度
      scrollTop: null,
      sortableInstance: null, // 存储Sortable实例
      columnWidths: {}, // 存储列宽信息
    }
  },
  mounted() {
    this.bus.$off('updateGetList')
    this.bus.$on('updateGetList', (idOrder) => {
      this.idOrder = idOrder
      this.getList()
    })
    this.$nextTick(() => {
      this.initColumnDrag();
    });
  },
  activated() {
    this.saveScroll()
  },
  beforeDestroy() {
    this.$refs.tableList.bodyWrapper.removeEventListener(
      'scroll',
      (res) => {
        let height = res.target
        this.scrollTop = height.scrollTop
      },
      false
    )
    this.bus.$off('updateGetList')
  },
  //计算函数
  computed: {
    tableListWatch() {
      return this.tableList
    },
  },
  //监听
  watch: {
    tableListWatch(newVal, oldVal) {
      this.bus.$emit('leftButtonHandler', this.form, this.branchIds)
    },
  },
  methods: {
    // 初始化列拖动
    initColumnDrag() {
      const table = this.$refs.tableList;
      if (!table) return;

      // 获取表头包装元素
      const wrapper = table.$el.querySelector('.el-table__header-wrapper');
      // 获取表头行
      const headerRow = wrapper.querySelector('.el-table__header tr');
      // 获取表格体包装元素
      const bodyWrapper = table.$el.querySelector('.el-table__body-wrapper');
      // 获取表格体行
      const bodyRows = bodyWrapper.querySelectorAll('.el-table__body tbody tr');

      if (!headerRow || !bodyRows.length) return;

      // 销毁现有的Sortable实例
      if (this.headerSortable) {
        this.headerSortable.destroy();
      }
      if (this.bodySortables) {
        this.bodySortables.forEach(sortable => sortable.destroy());
        this.bodySortables = [];
      }

      // 保存所有列的宽度
      this.saveColumnWidths();

      // 创建表头拖动实例
      this.headerSortable = Sortable.create(headerRow, {
        animation: 150,
        ghostClass: 'sortable-ghost',
        draggable: '.draggable-header',
        filter: '.no-drag-header',
        onMove: (evt) => {
          return !evt.related.classList.contains('no-drag-header');
        },
        onStart: (evt) => {
          // 记录原始列宽
          this.draggedColumnWidth = evt.item.offsetWidth;
        },
        onEnd: (evt) => {
          const { oldIndex, newIndex } = evt;
          if (oldIndex !== newIndex) {
            // 同步拖动表格体中的列
            this.syncBodyColumns(oldIndex, newIndex);

            // 恢复列宽
            this.restoreColumnWidths();

            // 更新表格布局
            this.$nextTick(() => {
              this.$refs.tableList.doLayout();
            });

          }
        }
      });

      // 为表格体的每一行创建拖动实例
      this.bodySortables = [];
      bodyRows.forEach(row => {
        const sortable = Sortable.create(row, {
          animation: 150,
          ghostClass: 'sortable-ghost',
          draggable: '.el-table__cell',
          disabled: true, // 默认禁用表格体行的拖动
          onEnd: (evt) => {
            // 这里可以添加额外的处理逻辑
          }
        });
        this.bodySortables.push(sortable);
      });
    },

    // 保存所有列的宽度
    saveColumnWidths() {
      const table = this.$refs.tableList;
      if (!table) return;

      const headerRow = table.$el.querySelector('.el-table__header tr');
      if (!headerRow) return;

      const headerCells = headerRow.querySelectorAll('.el-table__cell');
      this.columnWidths = {};

      headerCells.forEach((cell, index) => {
        this.columnWidths[index] = {
          width: cell.offsetWidth,
          minWidth: cell.style.minWidth || cell.offsetWidth + 'px'
        };
      });
    },

    // 恢复所有列的宽度
    restoreColumnWidths() {
      const table = this.$refs.tableList;
      if (!table) return;

      const headerRow = table.$el.querySelector('.el-table__header tr');
      const bodyRows = table.$el.querySelectorAll('.el-table__body tbody tr');

      if (!headerRow || !bodyRows.length) return;

      const headerCells = headerRow.querySelectorAll('.el-table__cell');
      const bodyCells = bodyRows[0].querySelectorAll('.el-table__cell');

      // 确保列数一致
      if (headerCells.length !== bodyCells.length) return;

      // 恢复列宽
      headerCells.forEach((cell, index) => {
        if (this.columnWidths[index]) {
          cell.style.width = `${this.columnWidths[index].width}px`;
          cell.style.minWidth = this.columnWidths[index].minWidth;
        }
      });

      bodyRows.forEach(row => {
        const cells = row.querySelectorAll('.el-table__cell');
        cells.forEach((cell, index) => {
          if (this.columnWidths[index]) {
            cell.style.width = `${this.columnWidths[index].width}px`;
            cell.style.minWidth = this.columnWidths[index].minWidth;
          }
        });
      });
    },


    // 同步表格体中的列位置
    syncBodyColumns(oldIndex, newIndex) {
      const table = this.$refs.tableList;
      if (!table) return;

      const bodyWrapper = table.$el.querySelector('.el-table__body-wrapper');
      const bodyRows = bodyWrapper.querySelectorAll('.el-table__body tbody tr');

      bodyRows.forEach(row => {
        const cells = row.querySelectorAll('.el-table__cell');
        if (cells.length > Math.max(oldIndex, newIndex)) {
          const cell = cells[oldIndex];
          if (newIndex > oldIndex) {
            row.insertBefore(cell, cells[newIndex].nextSibling);
          } else {
            row.insertBefore(cell, cells[newIndex]);
          }
        }
      });
    },



    // 设置表格滚动条位置
    saveScroll() {
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollTop = this.$el.querySelector('.el-table__body-wrapper')
          scrollTop.scrollTop = this.scrollTop
        }, 50)
      })
    },
    //手动监听
    rowClass({ row, index }) {
      // 自费
      if (row.ssqt > 0) {
        return 'self_charge'
      }

      if (row.line == '未检') {
        return 'noexam'
      }
    },
    // 父组件执行查询
    handleGetList(id, rowSelection, branchIds) {
      if (id) {
        this.idOrder = id
      }
      if (rowSelection) {
        this.rowSelection = rowSelection
      }
      this.branchIds = branchIds
      this.getList()
    },
    // 查询列表
    getList() {
      this.loading = true
      let startRegTime = ''
      let endRegTime = ''
      startRegTime = this.form.startRegTime ? this.form.startRegTime.slice(0, 10) + ' 00:00:00' : undefined
      endRegTime = this.form.endRegTime ? this.form.endRegTime.slice(0, 10) + ' 23:59:59' : undefined
      let obj = {
        branchIds: this.branchIds,
        idOrder: this.idOrder[0],
        startRegTime,
        endRegTime,
        containUnchecked: this.form.containUnchecked || 0,
        containBj: this.form.containBj || 0,
        containBan: this.form.containBan || 0,
        containOldSystem: this.form.containOldSystem || 0,
        checkoutStatus: this.form.checkoutStatus,
        current: this.queryParams.current,
        size: this.queryParams.size,
      }
      getAccountsInfoData(obj)
        .then((res) => {
          this.tableList = res.data.records
          this.loading = false
          this.total = res.data.total

          // 确保表格渲染后重新初始化拖动
          this.$nextTick(() => {
            this.initColumnDrag();

          });
          this.$refs.tableList.bodyWrapper.addEventListener(
            'scroll',
            (res) => {
              let height = res.target
              this.scrollTop = height.scrollTop
            },
            false
          )
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 分页条数发生改变
    handleSizeChange(val) {
      if (this.queryParams.current * val > this.total) {
        this.queryParams.current = 1
      }
      this.queryParams.size = val
      this.getList()
    },
    // 分页页码发生改变
    handleCurrentChange(val) {
      this.queryParams.current = val
      this.getList()
    },
    // 刷新
    handleRefresh() {
      this.queryParams.current = 1
      this.getList()
    },
    // 导出
    exportTable() {
      let startTime = ''
      let endTime = ''
      let idOrder = this.idOrder[0]
      if (!idOrder.length) {
        this.$alert('请先选择订单', '提示')
        return
      }
      var row = this.rowSelection
      if (row.length > 0) {
        startTime = this.form.startRegTime ? this.form.startRegTime.slice(0, 10) + ' 00:00:00' : undefined
        endTime = this.form.endRegTime ? this.form.endRegTime.slice(0, 10) + ' 23:59:59' : undefined
        var searchData = {
          ddinputName: this.form.ddinputName,
          ddinputCode: this.form.ddinputCode,
          containUnchecked: this.form.containUnchecked || 0,
          containBj: this.form.containBj || 0,
          containBan: this.form.containBan || 0,
          containOldSystem: this.form.containOldSystem || 0,
          checkoutStatus: this.form.checkoutStatus,
          idOrder,
          startRegTime: startTime,
          endRegTime: endTime,
          branchIds: this.branchIds,
        }
        // exportAccountsInfoData(searchData).then((res)=>{
        // 	this.$modal.msgSuccess(res.msg)
        // })
        this.download('/finance/physicalCheckout/exportAccountsInfoData', searchData, `体检人员表.xlsx`)
      } else {
        this.$alert('请选择一条体检者', '提醒', {
          confirmButtonText: '确认',
          type: 'warning',
        })
      }
    },
    // 禁检--反禁检
    handleAble(context) {
      let obj = {
        ids: '',
        paused: context,
      }
      this.ids.forEach((el) => {
        obj.ids += el + ','
      })
      obj.ids = obj.ids.substring(0, obj.ids.length - 1)
      //禁检
      if (context == 1) {
        savePausedStatus(obj).then((res) => {
          this.$modal.msgSuccess(res.msg)
          this.getList()
        })
        //反禁检
      } else if (context == 0) {
        savePausedStatus(obj).then((res) => {
          this.$modal.msgSuccess(res.msg)
          this.getList()
        })
      }
    },
    //未检--禁检
    unHandleAble(ispaused) {
      var rows = this.selection
      if (rows.length == 0) {
        this.$alert('请选择一条记录!', '提醒', {
          confirmButtonText: '确认',
          type: 'warning',
        })
        return
      }
      for (var i = 0; i < rows.length; i++) {
        if (rows[i].fregistered == 1) {
          this.$alert('含有已检记录，请重新选择', '提醒', {
            confirmButtonText: '确认',
            type: 'warning',
          })
          return
        }
      }
      var c = ''
      if (ispaused == 1) {
        c = '确定要未检禁检吗?'
      } else {
        c = '确定要未检反禁检吗?'
      }
      this.$alert(c, '提示', {
        confirmButtonText: '确 认',
        type: 'warning',
        callback: () => {
          let obj = {
            ids: '',
            paused: ispaused,
          }
          this.ids.forEach((el) => {
            obj.ids += el + ','
          })
          obj.ids = obj.ids.substring(0, obj.ids.length - 1)
          unSavePausedStatus(obj).then((res) => {
            this.$modal.msgSuccess(res.msg)
            this.getList()
          })
        },
      })
    },
    //已结账
    settleAccounts() {
      var rows = this.selection
      if (rows.length == 0) {
        this.$modal.msgWarning('请选择一条记录!')
        return
      }
      this.$confirm('确认要结账码', '提醒', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          let obj = { ids: '' }
          this.ids.forEach((el) => {
            obj.ids += el + ','
          })
          obj.ids = obj.ids.substring(0, obj.ids.length - 1)
          finishStatus(obj).then((res) => {
            this.$modal.msgSuccess(res.msg)
            this.getList()
          })
        })
        .catch(() => { })
    },
    //反结账
    unsettleAccounts() {
      var rows = this.selection
      if (rows.length == 0) {
        this.$modal.msgWarning('请选择一条记录!')
        return
      }
      this.$confirm('确定要反结账吗', '提醒', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          let obj = { ids: '' }
          this.ids.forEach((el) => {
            obj.ids += el + ','
          })
          obj.ids = obj.ids.substring(0, obj.ids.length - 1)
          unfinishStatus(obj).then((res) => {
            this.$modal.msgSuccess(res.msg)
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.selection = selection
    },
    //失去焦点
    handleBlur() {
      if (!this.idOrder || (this.idOrder && !this.idOrder.length)) return
      this.loading = true
      let obj = {
        branchIds: this.branchIds,
        idOrder: this.idOrder[0],
        ...this.form,
        ...this.queryParams,
        containBj: this.form.containBj || 0,
        containBan: this.form.containBan || 0,
        containUnchecked: this.form.containUnchecked || 0,
        containOldSystem: this.form.containOldSystem || 0,
      }
      obj.startRegTime = this.form.startRegTime ? this.form.startRegTime.slice(0, 10) + ' 00:00:00' : undefined
      obj.endRegTime = this.form.endRegTime ? this.form.endRegTime.slice(0, 10) + ' 23:59:59' : undefined
      getAccountsInfoData(obj)
        .then((res) => {
          this.tableList = res.data.records
          this.loading = false
          this.total = res.data.total
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    //行双击
    handleRowDblClick(row) {
      this.bus.$emit('rightMiddleHandler', row.patientcode)
      this.bus.$emit('rightButtonHandler', row.patientcode)
    },
    //行单击
    handleRowClick(row, column) {
      if (column.className == 'el-table-column--selection') {
        this.$refs.tableList.toggleRowSelection(row, true)
      } else {
        this.$refs.tableList.clearSelection()
        this.$refs.tableList.toggleRowSelection(row, true)
      }
    },
  },
}
</script>

<style scoped>
#tableList /deep/ .el-table__cell {
  padding: 5px 0;
}

#tableList /deep/ .noexam {
  background: #9acd32;
}

#tableList /deep/ .self_charge {
  background: #fffacd;
}

.triggerFailure {
  pointer-events: none;
}
</style>
