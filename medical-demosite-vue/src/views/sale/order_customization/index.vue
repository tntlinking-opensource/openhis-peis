<!-- 订单制定  开发人：麦沃德科技 矢北/予安 -->
<template>
  <div class="app-container flex-direction-column order-customization">
    <el-form size="mini" :inline="true" ref="queryForm" :model="queryParams" class="no-margin-bottom">
      <el-form-item label="订单号" prop="ddh">
        <el-input style="width: 230px" v-model="queryParams.ddh" placeholder="请输入订单编号" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="客户单位输入码" prop="khdwsrm">
        <el-input style="width: 230px" v-model="queryParams.khdwsrm" placeholder="请输入输入码" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="订单名称" prop="ddmc">
        <el-input style="width: 230px" v-model="queryParams.ddmc" placeholder="请输入订单名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="订单状态" prop="spzt">
        <el-select v-model="queryParams.spzt" placeholder="请选择" clearable>
          <el-option style="width: 230px" v-for="options in orderstate" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="fzx">
        <el-select v-model="queryParams.fzx" placeholder="请选择" clearable ref="fzx">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择" clearable>
          <el-option label="健康" :value="0" />
          <el-option label="职业" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="团检号" prop="tjhm">
        <el-input style="width: 230px" v-model="queryParams.tjhm" placeholder="请输入团检号" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl">
        <el-input style="width: 230px" v-model="queryParams.xsjl" placeholder="请输入销售经理名称" @keyup.enter.native="handleQuery" clearable></el-input>
      </el-form-item>
      <el-form-item prop="sortByOrder">
        <el-checkbox style="width: 120px" :true-label="1" :false-label="0" v-model="queryParams.sortByOrder" label="按订单号排序"></el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="showAddDialog" v-hasPermi="['sale:orderCustomization:add']">增加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleEdit" :disabled="single" v-hasPermi="['sale:orderCustomization:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleRemove" :disabled="multiple" v-hasPermi="['sale:orderCustomization:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-copy-document" @click="handleCopy" :disabled="single" v-hasPermi="['sale:orderCustomization:copy']">复制 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-search" @click="handleView" :disabled="single" v-hasPermi="['sale:orderCustomization:view']">查看 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleSubmit" :disabled="multiple" v-hasPermi="['sale:orderCustomization:submit']">提交 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleWithdraw" :disabled="multiple" v-hasPermi="['sale:orderCustomization:withdraw']">撤回 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handleExport" v-hasPermi="['sale:orderCustomization:export']">导出 </el-button>
      </el-col>
      <!-- <el-col :span="1.5" v-if="openFlow"> -->
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="handleOrderSchedule" icon="el-icon-s-check" :disabled="single" v-hasPermi="['sale:orderCustomization:approval']">查看审批进度</el-button>
      </el-col>
      <!-- <el-col :span="1.5" v-else> -->
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="handleOrderExam" icon="el-icon-s-check" :disabled="single" v-hasPermi="['sale:orderCustomization:approval']">订单审批 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" @click="handleOrderReExam(0)" icon="el-icon-s-check" :disabled="single" v-hasPermi="['sale:orderCustomization:reapproval']">订单反审 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="exportOffer" :disabled="single" v-hasPermi="['sale:orderCustomization:exportoffer']">导出套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="exportprotocol" :disabled="single" v-hasPermi="['sale:orderCustomization:exportprotocol']">导出协议套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-printer" @click="printopen" :disabled="single" v-hasPermi="['sale:orderCustomization:print']">打印套餐 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="uploadList" v-hasPermi="['sale:orderCustomization:alter']">上传名单 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="downloadList" v-hasPermi="['sale:orderCustomization:alter']">下载名单 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" :disabled="single" @click="orderSummary" v-hasPermi="['sale:orderCustomization:orderSummary']">总结 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="alter" v-hasPermi="['sale:orderCustomization:alter']">变更 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-s-check" :disabled="multiple" @click="alterSubmit" v-hasPermi="['sale:orderCustomization:altersubmit']">变更提交 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-s-check" :disabled="multiple" @click="alterWithdraw" v-hasPermi="['sale:orderCustomization:alterwithdraw']">变更撤回 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-s-check" :disabled="single" @click="alterApproval" v-hasPermi="['sale:orderCustomization:alterapproval']">变更审批 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-s-check" :disabled="single" @click="handleOrderReExam(1)" v-hasPermi="['sale:orderCustomization:alterreapproval']">变更反审 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="opendata" :disabled="single" v-hasPermi="['sale:orderCustomization:upinformation']">上传材料 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="downloadCl" v-hasPermi="['sale:orderCustomization:downinformation']">下载材料 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="multiple" @click="handleMaterialsPass" v-hasPermi="['sale:orderCustomization:passinformation']">材料通过 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-s-check" :disabled="multiple" @click="handleMaterialsReject" v-hasPermi="['sale:orderCustomization:informationrejection']">材料驳回 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" @click="handImport" icon="el-icon-upload2" :disabled="single" v-hasPermi="['sale:orderCustomization:handImport']">导入名单 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="multiple" @click="diareviseopen" v-hasPermi="['sale:orderCustomization:send']">修改发放方式 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="diafrontopen" v-hasPermi="['sale:orderCustomization:changecontent']">变更前台须知 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="offeropen" v-hasPermi="['sale:orderCustomization:editoffer']">编辑开单助理 </el-button>
      </el-col>
    </el-row>

    <!-- 表格部分 -->
    <div class="table-box">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe 
                @selection-change="handleSelectionChangem" @row-click="rowClick" :key="tableKey">
        <!-- 固定列（不可拖拽） -->
        <el-table-column type="selection" width="45" align="center" fixed class-name="no-drag" />
        <el-table-column label="序列" width="50" type="index" align="center" fixed class-name="no-drag" />
        
        <!-- 动态列区域 - 可以拖动排序 -->
        <el-table-column v-for="column in dynamicColumns" 
            :key="column.prop" 
            :prop="column.prop" 
            :label="column.label" 
            :min-width="column.minWidth"
            :align="column.align || 'center'"
            :show-overflow-tooltip="column.showOverflowTooltip"
            :fixed="column.fixed"
            :class-name="column.className">
          <template slot-scope="scope">
            <!-- 根据列属性动态显示内容 -->
            <template v-if="column.prop === 'ddh'">{{ scope.row.ddh }}</template>
            <template v-else-if="column.prop === 'tjm'">{{ scope.row.tjm }}</template>
            <template v-else-if="column.prop === 'ddmc'">{{ scope.row.ddmc }}</template>
            <template v-else-if="column.prop === 'tjlx'">
              <span>{{ ['健康', '职业', '综合'][scope.row.tjlx] }}</span>
            </template>
            <template v-else-if="column.prop === 'spzt'">
              <el-tag type="danger" v-if="scope.row.spzt == 0">审核未通过</el-tag>
              <el-tag type="warning" v-else-if="scope.row.spzt == 1">已撤回</el-tag>
              <el-tag type="info" v-else-if="scope.row.spzt == 2">草稿</el-tag>
              <el-tag type="primary" v-else-if="scope.row.spzt == 3">已提交</el-tag>
              <el-tag type="success" v-else-if="scope.row.spzt == 4">审核通过</el-tag>
            </template>
            <template v-else-if="column.prop === 'mdzt'">
              <el-tag type="danger" v-if="scope.row.mdzt == 0">未上传</el-tag>
              <el-tag type="success" v-else-if="scope.row.mdzt == 1">已上传</el-tag>
            </template>
            <template v-else-if="column.prop === 'varCostRate'">
              <span>{{ scope.row.varCostRate ? Math.round(scope.row.varCostRate * 100) + '%' : '-' }}</span>
            </template>
            <template v-else-if="column.prop === 'zjyssh'">{{ scope.row.zjyssh }}</template>
            <template v-else-if="column.prop === 'bgzt'">
              <el-tag type="danger" v-if="scope.row.bgzt == 0">审核未通过</el-tag>
              <el-tag type="success" v-else-if="scope.row.bgzt == 2">已变更</el-tag>
              <el-tag type="warning" v-else-if="scope.row.bgzt == 3">变更已提交</el-tag>
              <el-tag type="success" v-else-if="scope.row.bgzt == 4">变更审核通过</el-tag>
              <el-tag type="primary" v-else>未变更</el-tag>
            </template>
            <template v-else-if="column.prop === 'tjxs'">
              <span>{{ ['内检', '外检', '内检加外检'][scope.row.tjxs] }}</span>
            </template>
            <template v-else-if="column.prop === 'fzx'">{{ scope.row.fzx }}</template>
            <template v-else-if="column.prop === 'spr'">{{ scope.row.spr }}</template>
            <template v-else-if="column.prop === 'spyj'">{{ scope.row.spyj }}</template>
            <template v-else-if="column.prop === 'clspzt'">
              <el-tag type="success" v-if="scope.row.clspzt == 1">通过</el-tag>
              <el-tag type="danger" v-else-if="scope.row.clspzt == 2">驳回</el-tag>
              <el-tag type="primary" v-else>未审核</el-tag>
            </template>
            <template v-else-if="column.prop === 'clspr'">{{ scope.row.clspr }}</template>
            <template v-else-if="column.prop === 'clspyj'">{{ scope.row.clspyj }}</template>
            <template v-else-if="column.prop === 'bdqk'">
              <span :style="!scope.row.bdqk ? 'color:red;' : ''">{{ scope.row.bdqk ? scope.row.bdqk + '已备单' : '未备单' }}</span>
            </template>
            <template v-else-if="column.prop === 'jhjqc'">
              <span>{{ scope.row.jhjqc ? scope.row.jhjqc.split(' ', 1)[0] : '' }}</span>
            </template>
            <template v-else-if="column.prop === 'jhjqd'">
              <span>{{ scope.row.jhjqd ? scope.row.jhjqc.split(' ', 1)[0] : '' }}</span>
            </template>
            <template v-else-if="column.prop === 'nxtjrs'">{{ scope.row.nxtjrs }}</template>
            <template v-else-if="column.prop === 'vxtjrs'">{{ scope.row.vxtjrs }}</template>
            <template v-else-if="column.prop === 'ddjg'">{{ scope.row.ddjg }}</template>
            <template v-else-if="column.prop === 'ddzk'">{{ scope.row.ddzk }}</template>
            <template v-else-if="column.prop === 'ddyhj'">{{ scope.row.ddyhj }}</template>
            <template v-else-if="column.prop === 'xsjl'">{{ scope.row.xsjl }}</template>
            <template v-else-if="column.prop === 'kdzl'">{{ scope.row.kdzl }}</template>
            <template v-else-if="column.prop === 'khdwmc'">{{ scope.row.khdwmc }}</template>
            <template v-else-if="column.prop === 'khdwdh'">{{ scope.row.khdwdh }}</template>
            <template v-else-if="column.prop === 'cjddrq'">
              <span>{{ scope.row.cjddrq.split(' ', 1)[0] }}</span>
            </template>
            <template v-else-if="column.prop === 'txfs'">
              <span>{{ ['首页提醒', '短信提醒'][scope.row.txfs] }}</span>
            </template>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page-sizes="[20, 50, 100, 200 ,500]" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 弹窗 -->
    <!-- 订单审批 -->
    <order-approval ref="orderApproval" :isApproval="isApproval" :readOnly="true" @getList="getList"></order-approval>
    <printOffer ref="printOffer"> </printOffer>
    <excUpLoad ref="excUpLoad"></excUpLoad>
    <!-- 总结 -->
    <orderSummary ref="orderSummary"> </orderSummary>
    <!-- 上传材料 -->
    <upLoadData ref="offerdata" @getList="getList"> </upLoadData>
    <importList ref="importList"></importList>
    <!-- 编辑开单助理 -->
    <openoffer ref="openoffer" @getList="getList"></openoffer>
    <!-- 前台变更须知 -->
    <front ref="front"></front>
    <revise ref="revise" @getList="getList"> </revise>
    <query ref="query"> </query>
    <!-- 材料驳回 -->
    <el-dialog title="材料驳回" :visible.sync="clRejectOpen" width="780px" class="add-container order-customization-add alter-note" append-to-body :close-on-click-modal="false" @close="handleCancelcl">
      <div class="alter-world">审批意见</div>
      <el-input v-model="clRejectSpyj" :rows="4" type="textarea" placeholder="请输入审批意见"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleConfirmcl">驳 回</el-button>
        <el-button @click="handleCancelcl">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 选择审批人 - 提交 --> 
    <el-dialog title="审批人选择" :visible.sync="examineOpen" width="360px" class="add-container order-customization-add alter-note" append-to-body :close-on-click-modal="false" @close="handleCancelcl">
      <div style="padding:32px 16px;">
        <!-- 动态渲染每一层的审批人选择 -->
        <div v-for="(levelData, index) in dynamicExamine" :key="index" style="margin-bottom: 20px;">
          <div style="font-size: 16px;padding: 12px 0;">{{ '第' + levelData.level + '层审批人' }}</div>  
          <el-select v-model="levelData.selected" :placeholder="'请选择第' + levelData.level + '层审批人'" clearable style="width: 100%;">
            <el-option v-for="option in levelData.options" :key="option.id" :label="option.text" :value="option.id" />
          </el-select> 
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleNeedChoose">提 交</el-button>
        <el-button @click="handleCancelcl1">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 选择审批人 - 变更提交 --> 
    <el-dialog title="审批人选择" :visible.sync="examineOpenBg" width="360px" class="add-container order-customization-add alter-note" append-to-body :close-on-click-modal="false" @close="handleCancelcl">
      <div style="padding:32px 16px;">
        <!-- 动态渲染每一层的审批人选择 -->
        <div v-for="(levelData, index) in dynamicExamineBg" :key="index" style="margin-bottom: 20px;">
          <div style="font-size: 16px;padding: 12px 0;">{{ '第' + levelData.level + '层审批人' }}</div>  
          <el-select v-model="levelData.selected" :placeholder="'请选择第' + levelData.level + '层审批人'" clearable style="width: 100%;">
            <el-option v-for="option in levelData.options" :key="option.id" :label="option.text" :value="option.id" />
          </el-select>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleNeedChooseBg">提 交</el-button>
        <el-button @click="handleCancelcl1Bg">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import orderApproval from './order_approval.vue'
import upLoadData from './upload'
import orderSummary from './order_summary'
import importList from './import_list'
import openoffer from './open_offer'
import front from './front'
import revise from './revise.vue'
import printOffer from './print_offer.vue'
import query from './query.vue'
import excUpLoad from './excUpLoad'
import Sortable from 'sortablejs'; // 导入SortableJS

import { listOrder, isLeader, isTjOrShtg, isRemove, removeApi, isCommitAndSptg, orderCommitApi, isWithdrawApi, withdrawApi, downLoad, isCheckApi, undoOrder, commitChange, undoChange, unauditChange, clpassOrUmpass, rejectSave, getGroup,needChoose } from '@/api/sale/order_customization.js'
import { getInfoByBzId, isOpenFlow } from '@/api/approval/approval_quest.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'

export default {
  name: 'Order_customization',
  components: {
    printOffer,
    orderApproval,
    orderSummary,
    importList,
    upLoadData,
    openoffer,
    front,
    revise,
    query,
    excUpLoad,
  },
  data() { 
    return {  // 
      queryParams: {
        current: 1,
        size: 20, 
        khdwsrm: undefined, 
        ddmc: undefined,
        spzt: undefined,
        fzx: undefined,
        tjlx: undefined,
        ddh: undefined,
        xsjl: undefined,
        tjhm: undefined,
        ordercheck: undefined,
        sortByOrder: '0',
      },
      total: 0,
      // 分中心列表 
      fzxOptions: [],
      // 遮罩层 
      loading: true,
      // 多选 
      ids: [],
      selectList: [],
      single: true,
      multiple: true,
      // 表格数据
      tableList: [],
      // 打开材料驳回弹窗
      clRejectOpen: false,
      // 选择审批人弹窗 - 提交
      examineOpen:false,
      // 选择审批人弹窗 - 变更提交
      examineOpenBg:false,
      // 审批意见
      clRejectSpyj: '',

      // 订单审批
      diarevise: '修改发放方式',
      // 订单状态选项
      orderstate: [
        { id: 0, text: '审核未通过' },
        { id: 1, text: '已撤回' },
        { id: 2, text: '草稿' },
        { id: 3, text: '已提交' },
        { id: 4, text: '审核通过' },
      ],
      // 是否展示审批流程
      isApproval: false,
      // 是否开启审批流
      openFlow: false,
      // 动态审批人配置 - 提交
      dynamicExamine: [
        // 根据后端返回的数据动态填充
      ],
      // 动态审批人配置 - 变更提交
      dynamicExamineBg: [
        // 根据后端返回的数据动态填充
      ],
      // 选择审批人提交用到的订单id
      ddcomIdSPR:'',

      // 动态列定义
      dynamicColumns: [
        { prop: "ddh", label: "订单号", minWidth: 100, fixed: true },
        { prop: "tjm", label: "团检码", minWidth: 100 },
        { prop: "ddmc", label: "订单名称", minWidth: 260, showOverflowTooltip: true },
        { prop: "tjlx", label: "体检类型", minWidth: 90 },
        { prop: "spzt", label: "订单状态", minWidth: 110 },
        { prop: "mdzt", label: "名单状态", minWidth: 110 },
        { prop: "varCostRate", label: "平均变动成本率", minWidth: 90 },
        { prop: "zjyssh", label: "主检医生审核", minWidth: 140 },
        { prop: "bgzt", label: "变更状态", minWidth: 140 },
        { prop: "tjxs", label: "体检形式", minWidth: 100 },
        { prop: "fzx", label: "分中心", minWidth: 140, showOverflowTooltip: true },
        { prop: "spr", label: "审批人", minWidth: 100, showOverflowTooltip: true },
        { prop: "spyj", label: "审批意见", minWidth: 120, showOverflowTooltip: true },
        { prop: "clspzt", label: "材料状态", minWidth: 90 },
        { prop: "clspr", label: "材料审批人", minWidth: 120, showOverflowTooltip: true },
        { prop: "clspyj", label: "材料审批意见", minWidth: 120, showOverflowTooltip: true },
        { prop: "bdqk", label: "备单情况", minWidth: 140, showOverflowTooltip: true },
        { prop: "jhjqc", label: "计划检期从", minWidth: 120, showOverflowTooltip: true },
        { prop: "jhjqd", label: "计划检期到", minWidth: 120, showOverflowTooltip: true },
        { prop: "nxtjrs", label: "男性人数", minWidth: 100, showOverflowTooltip: true },
        { prop: "vxtjrs", label: "女性人数", minWidth: 100, showOverflowTooltip: true },
        { prop: "ddjg", label: "订单价格", minWidth: 120, showOverflowTooltip: true },
        { prop: "ddzk", label: "折扣", minWidth: 120, showOverflowTooltip: true },
        { prop: "ddyhj", label: "套餐平均价", minWidth: 120, showOverflowTooltip: true },
        { prop: "xsjl", label: "销售经理", minWidth: 120, showOverflowTooltip: true },
        { prop: "kdzl", label: "开单助理", minWidth: 120, showOverflowTooltip: true },
        { prop: "khdwmc", label: "客户单位名称", minWidth: 160, showOverflowTooltip: true },
        { prop: "khdwdh", label: "客户联系电话", minWidth: 120, showOverflowTooltip: true },
        { prop: "cjddrq", label: "创建订单日期", minWidth: 120, showOverflowTooltip: true },
        { prop: "txfs", label: "提醒方式", minWidth: 120 }
      ],
      sortable: null, // Sortable实例
      tableKey: Date.now() // 强制刷新的key
    }
  },
 
  mounted() {
    this.getList()
    // 获取分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })

    // 确保表格渲染完成后初始化拖动
    this.$nextTick(() => {
      this.initSortable();
    });
  },
  beforeRouteEnter(to, from, next) {
      next(vm => {
        vm.getList(); // 每次进入该路由时调用
      });
  },
  // created() { 
  //   this.getList()
  //   // 判断是否开启审批流
  //   // isOpenFlow({
  //   //   fzxid: this.$getCookie('cid'),
  //   //   typeFlag: 'ORDER_FLOW',
  //   // }).then(({ data }) => {
  //   //   this.openFlow = data
  //   // })
  //   // 获取分中心列表
  //   getBranchData().then(({ data }) => {
  //     this.fzxOptions = data
  //   })
  // },
  methods: {
     // 初始化可排序功能
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
    // 查询列表
    getList() {
      this.loading = true
      listOrder(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false

          // 确保表格渲染完成后初始化拖动
          this.$nextTick(() => {
            this.initSortable();
          });
        })
        .catch(() => {
          this.loading = false
        })
    },
    
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 单击选中
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 多选框选中数据
    handleSelectionChangem(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectList = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 添加
    showAddDialog() {
      const obj = { path: 'sale/order_add', name: 'Order_add' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('新增订单', '/sale/order_add')
      })
    },
    // 编辑
    handleEdit() {
      this.loading = true
      isTjOrShtg({
        isTjOrShtgId: this.ids[0],
      })
        .then(({ data }) => {
          this.loading = false
          if (data) {
            const obj = { path: 'sale/order_add', name: 'Order_add' }
            this.$tab.closePage(obj).then(() => {
              this.$tab.openPage('编辑订单', '/sale/order_add', { orderId: this.ids[0] })
            })
          } else {
            this.$alert('当前订单状态为【<text style="color:red;">' + ['', '', '', '已提交', '审核通过'][this.selectList[0].spzt] + '</text>】状态,不能再进行编辑！', '提示', {
              dangerouslyUseHTMLString: true,
            })
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 删除
    handleRemove() {
      this.loading = true
      isRemove({
        isRemoveId: this.ids.join(','),
      })
        .then(({ data }) => {
          this.loading = false
          if (data) {
            this.$alert(data, '提示', {
              dangerouslyUseHTMLString: true,
            })
          } else {
            this.$confirm('确定要删除选中的订单？', '提示', {
              type: 'warning',
            })
              .then(() => {
                removeApi(this.ids.join(',')).then(() => {
                  this.$modal.msgSuccess('删除成功')
                  this.getList()
                })
              })
              .catch(() => {})
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    //复制
    handleCopy() {
      const obj = { path: 'sale/order_add', name: 'Order_add' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('复制订单', '/sale/order_copy', { orderId: this.ids[0], isCopy: 1 })
      })
    },
    //查看
    handleView() {
      const obj = { path: 'sale/order_add', name: 'Order_add' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('查看订单', '/sale/order_view', { orderId: this.ids[0], isCheck: 1 })
      })
    },
    //提交
    handleSubmit() {
      this.loading = true
      isCommitAndSptg({
        isCommitAndSptgId: this.ids.join(','),
      })
        .then(({ data }) => {
          this.loading = false
          if (data) {
            this.$alert(data, '提示', {
              dangerouslyUseHTMLString: true,
            })
          } else {
            // 订单id
            let ddcomId = ''
            // 文件路径(名单)
            let msg = ''
            // 	材料文件路径s（|分隔）
            let clMsg = ''
            this.selectList.forEach((el) => {
              if (!el.urls) {
                msg += el.ddmc + '、'
              }
              if (el.tjlx != 0 && !el.clurls) {
                clMsg += el.ddmc + '、'
              }
            })
            ddcomId = this.ids.join(',')
            if (clMsg) {
              this.$confirm("订单<font color='red'>" + clMsg.substring(0, clMsg.length - 1) + '</font>未上传材料，确定要提交吗？', '提示', {
                dangerouslyUseHTMLString: true,
                type: 'warning',
              })
                .then(() => {
                  this.commitConfirm(msg, ddcomId, clMsg)
                })
                .catch(() => {})
            } else {
              this.commitConfirm(msg, ddcomId, clMsg)
            }
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 提交-判断有无上传名单
    commitConfirm(msg, ddcomId, clMsg) {
      if (msg) {
        this.$confirm("订单<text color='red'>" + msg.substring(0, msg.length - 1) + '</text>未上传名单，确定要提交吗？', '提示', {
          dangerouslyUseHTMLString: true,
          type: 'warning',
        })
          .then(() => {
            this.commitAjax(ddcomId)
          })
          .catch(() => {})
      } else {
        if (!clMsg) {
          this.$confirm('确定要提交吗？', '提示', {
            type: 'warning',
          })
            .then(() => {
              this.commitAjax(ddcomId)
            })
            .catch(() => {})
        } else {
          this.commitAjax(ddcomId)
        }
      }
    },
    // 提交-确认提交 
    commitAjax(ddcomId) {
      this.loading = true
      // 选择审批人提交用到的订单id
      this.ddcomIdSPR = ddcomId
      //判断是否需要选择 
      needChoose(ddcomId,0)
        .then(({ data }) => {
          this.loading = false
          console.log("获取审批人",data);
          if(data){
            this.dynamicExamine = [];
            // 使用一个临时对象来聚合相同级别的审批人
            let tempLevels = {};
            
            data.forEach(item => {
              if (!tempLevels[item.level]) {
                tempLevels[item.level] = [];
              }
              tempLevels[item.level].push({ id: item.itemId, text: item.userName });
            });

            // 将临时对象转换为dynamicExamine数组格式
            for (let level in tempLevels) {
              let options = tempLevels[level];
              let selectedValue = ''; // 初始化选中值

              // 如果仅有一个选项，则直接选中它
              if (options.length === 1) {
                selectedValue = options[0].id;
              }

              this.dynamicExamine.push({
                level: parseInt(level),
                selected: selectedValue, // 设置选中的值
                options: options
              });
            }

            console.log("审批人数据", this.dynamicExamine);
            this.examineOpen = true;
          }else{
            // 无审批人直接提交 
            orderCommitApi({
              ddcomId:ddcomId
            })
            .then(() => {
              this.loading = false
              this.$modal.msgSuccess('提交成功')
              this.getList()
            })
            .catch(() => {
              this.loading = false
            })
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 选择审批人提交
    handleNeedChoose() {
      // 验证所有层级是否都已选择审批人
      const isAllSelected = this.dynamicExamine.every(examine => examine.selected);
      
      if (!isAllSelected) {
        this.$message.warning('请为所有审批层级选择审批人');
        return;
      }

      // 初始化一个空数组用于收集审批人ID
      const approverIdsArray = this.dynamicExamine.map(examine => examine.selected).filter(id => id);
      const approverIds = approverIdsArray.join(',');
      
      orderCommitApi({
        ddcomId:this.ddcomIdSPR,
        approverIds:approverIds
      })
      .then(() => {
        this.getList()
        this.ddcomIdSPR = ''
        this.loading = false
        this.examineOpen = false;
        this.$modal.msgSuccess('提交成功')
      })
      .catch(() => {
        this.loading = false
      })      
    
    },
   
    // 选择审批人取消
    handleCancelcl1() {
      this.examineOpen = false
    },
    handleCancelcl1Bg() {
      this.examineOpenBg = false
    },
    // 撤回 
    handleWithdraw() {
      this.loading = true
      isWithdrawApi({
        isChId: this.ids.join(','),
      })
        .then(({ data }) => {
          this.loading = false
          if (data) {
            this.$alert(data, '提示', {
              dangerouslyUseHTMLString: true,
            })
          } else {
            this.$confirm('确定要撤回选择的订单？', '提示', {
              type: 'warning',
            })
              .then(() => {
                withdrawApi(this.ids.join(',')).then(() => {
                  this.$modal.msgSuccess('撤回成功')
                  this.getList()
                })
              })
              .catch(() => {})
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 导出
    handleExport() {
      this.download(
        '/sell/createorder/export',
        {
          ...this.queryParams,
        },
        `订单导出.xlsx`
      )
    },
    // 查看审批进度
    handleOrderSchedule() {
      this.loading = true
      getInfoByBzId({ bzId: this.ids[0], typeFlag: this.selectList[0].tjlx == '0' ? 'ORDER_FLOW' : 'ORDER_FLOW_OCCUPATION' })
        .then(({ data }) => {
          this.isApproval = true
          this.$refs.orderApproval.showDialog(this.ids[0], 0, data.itemList)
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 打开订单审批
    handleOrderExam() {
      this.loading = true
      isLeader()
        .then(({ data }) => {
          this.loading = false
          if (data) {
            this.loading = true
            isCheckApi({ isCheckId: this.ids[0] })
              .then(({ data }) => {
                if (data == 'error') {
                  this.isApproval = false
                  this.$refs.orderApproval.showDialog(this.ids[0])
                } else {
                  this.$alert(`请选择订单状态为【<font color='red'><b>已提交</b></font>】的订单进行审批`, '提示', {
                    dangerouslyUseHTMLString: true,
                  })
                }
                this.loading = false
              })
              .catch(() => {
                this.loading = false
              })
          } else {
            this.$alert('您不是领导,没有该操作权限', '提示')
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 订单反审、变更反审
    handleOrderReExam(isAlter) {
      if (this.selectList[0].bdqk) {
        this.$alert(`订单已备单，不能反审核`, '提示')
        return
      }
      let msg = this.selectList[0].xzqk ? "该订单已被<font color='red'>" + this.selectList[0].xzqk + '</font>下载但未备单，如果<反审修改>，销售员必须主动联系前台，重新下载，确保备单时线上线下套餐一致。' : '确定要反审核吗？'
      this.$confirm(msg, '提示', {
        dangerouslyUseHTMLString: true,
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          if (isAlter == 1) {
            unauditChange({ id: this.ids[0] })
              .then(() => {
                this.$modal.msgSuccess('反审核成功')
                this.getList()
              })
              .catch(() => {
                this.loading = false
              })
          } else {
            undoOrder({ id: this.ids[0] })
              .then(() => {
                this.$modal.msgSuccess('反审核成功')
                this.getList()
              })
              .catch(() => {
                this.loading = false
              })
          }
        })
        .catch(() => {})
    },
    //总结
    orderSummary() {
      if (this.selectList[0].spzt != 4) {
        this.$alert(`请选择【<font color='red'><b>审核通过</b></font>】的订单`, '提示', {
          dangerouslyUseHTMLString: true,
        })
      } else {
        this.$refs.orderSummary.handleShow(this.ids[0])
      }
    },
    //变更
    alter() {
      if (this.selectList[0].spzt != 4) {
        this.$alert(`请选择【<font color='red'><b>审核通过</b></font>】的订单`, '提示', {
          dangerouslyUseHTMLString: true,
        })
      } else {
        const obj = { path: 'sale/order_add', name: 'Order_add' }
        this.$tab.closePage(obj).then(() => {
          this.$tab.openPage('变更订单', '/sale/order_add', { orderId: this.ids[0], isAlter: 1 })
        })
      }
    },
    //变更提交
    alterSubmit() {
      let ddcomId = ''
      let msg = ''
      let clmsg = ''
      let isError = false
      this.selectList.forEach((el) => {
        if (el.bgzt != 2) {
          this.$alert("请选择【<font color='red'><b>已变更</b></font>】的订单！", '提示', {
            dangerouslyUseHTMLString: true,
          })
          isError = true
        }
        if (!el.urls) {
          msg += el.ddmc + '、'
        }
        if (el.tjlx != 0 && !el.clurls) {
          clmsg += el.ddmc + '、'
        }
      })
      if (isError) return
      ddcomId = this.ids.join(',')
      if (clmsg) {
        this.$confirm("订单<font color='red'>" + clmsg.substring(0, clmsg.length - 1) + '</font>未上传材料，确定要变更提交吗？', '提示', {
          dangerouslyUseHTMLString: true,
          type: 'warning',
        })
          .then(() => {
            this.bgCommitConfirm(msg, ddcomId, clmsg)
          })
          .catch(() => {})
      } else {
        this.bgCommitConfirm(msg, ddcomId, clmsg)
      }
    },
    // 变更提交-判断有无上传名单
    bgCommitConfirm(msg, ddcomId, clmsg) {
      if (msg) {
        this.$confirm("订单<font color='red'>" + msg.substring(0, msg.length - 1) + '</font>未上传名单，确定要提交吗？', '提示', {
          dangerouslyUseHTMLString: true,
          type: 'warning',
        })
          .then(() => {
            this.bgCommitAjax(ddcomId)
          })
          .catch(() => {})
      } else {
        if (!clmsg) {
          this.$confirm('确定要提交吗？', '提示', {
            type: 'warning',
          })
            .then(() => {
              this.bgCommitAjax(ddcomId)
            })
            .catch(() => {})
        } else {
          this.bgCommitAjax(ddcomId)
        }
      }
    },
    // 变更提交-确认提交
    bgCommitAjax(ids) {
      this.loading = true

      // 选择审批人提交用到的订单id
      this.ddcomIdSPR = ids
      //判断是否需要选择 
      //变更提交传1
      needChoose(ids,1)
        .then(({ data }) => {
          this.loading = false
          console.log("获取审批人",data);
          if(data){
            console.log(11111);

            this.dynamicExamineBg = [];
            // 使用一个临时对象来聚合相同级别的审批人
            let tempLevels = {};
            
            data.forEach(item => {
              if (!tempLevels[item.level]) {
                tempLevels[item.level] = [];
              }
              tempLevels[item.level].push({ id: item.itemId, text: item.userName });
            });

            // 将临时对象转换为dynamicExamine数组格式
            for (let level in tempLevels) {
              let options = tempLevels[level];
              let selectedValue = ''; // 初始化选中值

              // 如果仅有一个选项，则直接选中它
              if (options.length === 1) {
                selectedValue = options[0].id;
              }

              this.dynamicExamineBg.push({
                level: parseInt(level),
                selected: selectedValue, // 设置选中的值
                options: options
              });
            }

            console.log("审批人数据", this.dynamicExamineBg);
            this.examineOpenBg = true;
          }else{
            console.log(22222);
            
            // 无审批人直接提交 
            commitChange({
              ids: ids
            })
            .then(({ data }) => {
              this.loading = false
              this.$modal.msgSuccess('提交成功')
              this.getList()
            })
            .catch(() => {
              this.loading = false
            })
          }
        })
        .catch(() => {
          this.loading = false
        })
      
    },
      // 选择审批人 变更提交
      handleNeedChooseBg() {
        // 验证所有层级是否都已选择审批人
        const isAllSelected = this.dynamicExamineBg.every(examine => examine.selected);
        
        if (!isAllSelected) {
          this.$message.warning('请为所有审批层级选择审批人');
          return;
        }

        // 初始化一个空数组用于收集审批人ID
        const approverIdsArray = this.dynamicExamineBg.map(examine => examine.selected).filter(id => id);
        const approverIds = approverIdsArray.join(',');
        
        commitChange({
          ids:this.ddcomIdSPR,
          approverIds:approverIds
        })
        .then(() => {
          this.getList()
          this.loading = false
          this.examineOpenBg = false;
          this.$modal.msgSuccess('提交成功')
        })
        .catch(() => {
          this.loading = false
        })      
    },

    //变更撤回
    alterWithdraw() {
      let error = this.selectList.some((el) => {
        if (el.bgzt != 3) {
          this.$alert("请选择已【<font color='red'><b>变更提交</b></font>】的订单！", '提示', {
            dangerouslyUseHTMLString: true,
          })
          return true
        }
      })
      if (error) return
      this.$confirm('确定要撤回选择的订单？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          undoChange(this.ids.join(','))
            .then(() => {
              this.$modal.msgSuccess('撤回成功')
              this.getList()
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {})
    },
    //变更审批
    alterApproval() {
      this.loading = true
      isLeader()
        .then(({ data }) => {
          this.loading = false
          let row = this.selectList[0]
          if (data) {
            if ((row.tjlx == 1 || row.tjlx == 2) && row.clspzt != 1) {
              this.$alert(`该订单未通过材料审核，不能审核`, '提示')
              return
            }
            if (row.bgzt != 3) {
              this.$alert("请选择已【<font color='red'><b>变更提交</b></font>】的订单", '提示', {
                dangerouslyUseHTMLString: true,
              })
              return
            }
            this.isApproval = false
            this.$refs.orderApproval.showDialog(this.ids[0], 1)
          } else {
            this.$alert('您不是领导,没有该操作权限', '提示')
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 变更反审 handleOrderReExam
    // 材料通过
    handleMaterialsPass() {
      let error = this.selectList.some((el) => {
        if (el.spzt != 3 && el.bgzt != 3) {
          this.$alert("请选择【<font color='red'><b>已提交</b></font>】或【<font color='red'><b>待审核</b></font>】的订单", '提示', {
            dangerouslyUseHTMLString: true,
          })
          return true
        }
      })
      if (error) return
      this.$confirm('确定要材料审核通过吗？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          clpassOrUmpass({ ids: this.ids.join(',') })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.getList()
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 材料驳回
    handleMaterialsReject() {
      let error = this.selectList.some((el) => {
        if (el.spzt != 3 && el.bgzt != 3) {
          this.$alert("请选择【<font color='red'><b>已提交</b></font>】或【<font color='red'><b>待审核</b></font>】的订单", '提示', {
            dangerouslyUseHTMLString: true,
          })
          return true
        }
      })
      if (error) return
      this.clRejectOpen = true
    },
    // 材料驳回确认
    handleConfirmcl() {
      if (!this.clRejectSpyj) {
        this.$modal.msgWarning('请输入审批意见')
        return
      }
      this.$confirm('确定要驳回选择的订单？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          rejectSave({
            clspyj: this.clRejectSpyj,
            ids: this.ids.join(','),
          })
            .then(() => {
              this.$modal.msgSuccess('驳回成功')
              this.getList()
              this.loading = false
              this.clRejectOpen = false
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 材料驳回取消
    handleCancelcl() {
      this.clRejectSpyj = ''
      this.clRejectOpen = false
    },
    
    //修改发送方式
    diareviseopen() {
      this.$refs.revise.showDialog(this.ids.join(','))
    },
    ///变更前台须知开启按钮
    diafrontopen() {
      this.$refs.front.showDialog(this.ids[0])
    },
    //编辑开单助理
    offeropen() {
      this.$refs.openoffer.showDialog(this.ids[0])
    },

    // **************************************************************
    // 导出套餐*
    exportOffer() {
      let tcId = this.ids.join(',')
      this.download(
        '/sell/createorder/exportTc',
        {
          ...{ tcIds: tcId },
        },
        '导出套餐.xls'
      )
    },
    //导出协议套餐
    exportprotocol() {
      let tcId = this.ids.join(',')
      this.download('/sell/createorder/exportXyTc', { tcId }, '导出协议套餐.xlsx')
    },
    // 打印套餐*
    printopen() {
      this.$refs.printOffer.showDialog(this.ids[0])
    },

    //上传材料
    opendata() {
      var row = this.selectList[0]
      if (row.clspzt == 1) {
        this.$modal.alertWarning('所选订单已通过材料审核，不能修改上传材料。', '提醒')
        return
      }
      var spzt = row.spzt
      var bgzt = row.bgzt
      if (!(spzt === 0 || spzt == 1 || spzt == 2 || bgzt === 0 || bgzt == 2)) {
        this.$modal.alertWarning('所选订单已提交或审核通过，不能修改上传材料。', '提醒')
        return
      }
      this.$refs.offerdata.showDialog(this.ids[0])
    },
    // 下载材料
    downloadCl() {
      if (!this.selectList[0].clurls) {
        this.$modal.alertWarning('所选订单还未上传材料！', '提醒')
        return
      }
      this.downloadZip('/sell/createorder/downCl?id=' + this.ids[0])
    },
    ///导入名单打开方法
    handImport() {
      getGroup({ id: this.ids[0] }).then(({ data }) => {
        if (!data.length) {
          this.$alert('请先前往备单页面保存体检者任务分组', '提示')
          return
        } else {
          this.$refs.importList.handleShow(this.ids[0], data)
        }
      })
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    revisecancel() {
      this.reviseopen = false
      this.reset()
    },
    // 表单重置
    revisereset() {
      this.form = {}
      this.resetForm('form')
    },
    // 健康导入模板下载
    phyImport() {
      this.downloadStatic('/static/stencil/bdModel_jk.xlsx', '健康体检名单模板.xlsx')
    },
    //职业导入模板下载
    proinprot() {
      this.downloadStatic('/static/stencil/bdModel_zy.xlsx', '职业体检名单模板.xlsx')
    },
    // 上传名单
    uploadList() {
      let id = this.ids[0]
      this.$refs.excUpLoad.showDialog(id)
    },
    // 下载名单
    downloadList() {
      var query = {
        id: this.ids[0],
      }
      this.download(
        '/sell/createorder/filesDownload',
        {
          ...query,
        },
        `收费信息查询.zip`
      )
    },

    // 表单重置
    forntreset() {
      this.form = {}
      this.resetForm('form')
    },
    //修改方式弹窗提交
    revisediasubmitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            // updatePrinttype(this.form).then(response => {
            this.$modal.msgSuccess('修改成功')
            this.reviseopen = false
            // 	this.getList();
            // });
          } else {
            // addPrinttype(this.form).then(response => {
            this.$modal.msgSuccess('加急成功')
            this.reviseopen = false
            // 	this.getList();
            // });
          }
        }
      })
    },
  },
}
</script>

<style lang="scss">
.order-customization {
  .el-dialog {
    height: 200px;
    min-height: auto !important;
  }
  // .mb8 {
  //   padding: 0;
  // }
}
</style>
<style scoped>
.alter-note /deep/ .el-dialog .el-dialog__body {
  padding-top: 0;
}

.alter-world {
  font-size: 15px;
  margin-bottom: 8px;
}
</style>
