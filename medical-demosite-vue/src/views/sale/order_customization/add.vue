<!-- 订单定制添加  开发人：麦沃德科技 矢北/予安 -->
<template>
	<!-- 添加或修改对话框 -->
	<div class="app-container flex-direction-column order-customization-add" style="overflow: auto">
		<el-row>
			<el-col :span="19">
				<el-form ref="form" size="small" :model="form" :rules="formRules" :inline="true" label-width="140px" hide-required-asterisk :style="isCheck ? 'pointer-events:none;' : ''">
					<el-form-item label="客户单位名称" prop="khdwmcid">
						<el-tooltip popper-class="tooltip-item" effect="dark" :content="selectedOrderName" :disabled="!selectedOrderName" placement="left">
							<input-select :selectData="selectData" @change="selectChange" :initialValue="form.khdwmc" :disabled="isAlter ? true : false"></input-select>
						</el-tooltip>
					</el-form-item>
					<el-form-item label="订单名称" prop="ddmc">
						<el-input v-model="form.ddmc" placeholder="请输入订单名称" clearable style="width: 280px" :readonly="isAlter ? true : false" />
					</el-form-item>
					<el-form-item label="提醒方式" prop="txfs">
						<el-select v-model="form.txfs" placeholder="请选择提醒方式" style="width: 280px">
							<el-option label="首页提醒" :value="0" />
							<el-option label="短信提醒" :value="1" />
						</el-select>
					</el-form-item>
					<el-form-item label="发送短信手机号" prop="fsdxsjh">
						<el-input v-model="form.fsdxsjh" placeholder="输入用户拼音码查询" clearable style="width: 280px" :disabled="true" v-if="form.txfs == 0" />
						<input-select v-else-if="form.txfs == 1" :selectData="choosesjh" @change="choosesjhChange" :initialValue="form.fsdxsjh"></input-select>
					</el-form-item>
					<el-form-item label="客户单位电话" prop="khdwdh">
						<el-input v-model="form.khdwdh" placeholder="请输入客户单位电话" clearable style="width: 280px" />
					</el-form-item>
					<el-form-item label="分中心" prop="fzxid">
						<el-select v-model="form.fzxid" placeholder="请输入分中心名称选择" style="width: 280px" filterable multiple @change="dateChange">
							<el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
						</el-select>
					</el-form-item>
					<el-form-item label="计划检期从" prop="jhjqc">
						<el-date-picker v-model="form.jhjqc" type="date" placeholder="选择日期" style="width: 280px" @change="dateChange" value-format="yyyy-MM-dd"> </el-date-picker>
					</el-form-item>
					<el-form-item label="计划检期到" prop="jhjqd">
						<el-date-picker v-model="form.jhjqd" type="date" placeholder="选择日期" style="width: 280px" @change="dateChange" value-format="yyyy-MM-dd"> </el-date-picker>
					</el-form-item>
					<el-form-item label="是否已签订合同" prop="sfyqdht">
						<el-select v-model="form.sfyqdht" placeholder="请选择" @change="sfyqdhtChange" style="width: 280px">
							<el-option label="是" value="0" />
							<el-option label="否" value="1" />
						</el-select>
					</el-form-item>
					<el-form-item label="合同编号" prop="htbh">
						<input-select :selectData="selectData3" @change="selectChange3" :disabled="disabled3" :initialValue="form.htbh"></input-select>
					</el-form-item>
					<el-form-item label="男性体检人数" prop="nxtjrs">
						<el-input-number v-model="form.nxtjrs" placeholder="请输入男性体检人数" controls-position="right" clearable style="width: 280px" :min="0" @blur="chargeChange" />
					</el-form-item>
					<el-form-item label="女性体检人数" prop="vxtjrs">
						<el-input-number v-model="form.vxtjrs" placeholder="请输入女性体检人数" controls-position="right" clearable style="width: 280px" :min="0" @blur="chargeChange" />
					</el-form-item>
					<el-form-item label="订单折扣" prop="ddzk">
						<el-input-number v-model="form.ddzk" controls-position="right" placeholder="请输入订单折扣" :min="0" style="width: 280px" />
					</el-form-item>
					<el-form-item label="套餐平均价" prop="ddyhj">
						<el-input-number v-model="form.ddyhj" controls-position="right" clearable placeholder="请输入套餐平均价" :min="0" style="width: 280px" @blur="chargeChange" />
					</el-form-item>
					<el-form-item label="费用预算" prop="ddjg">
						<el-input-number v-model="form.ddjg" placeholder="请输入费用预算" controls-position="right" clearable style="width: 280px" />
					</el-form-item>
					<el-form-item label="销售电话" prop="xsdh">
						<el-input type="tel" v-model="form.xsdh" placeholder="请输入销售电话" clearable :min="0" style="width: 280px" />
					</el-form-item>
					<el-form-item label="体检形式" prop="tjxs">
						<el-select v-model="form.tjxs" placeholder="请选择体检形式" style="width: 280px">
							<el-option label="内检" :value="0" />
							<el-option label="外检" :value="1" />
							<el-option label="内检加外检" :value="2" />
						</el-select>
					</el-form-item>
					<el-form-item label="发放方式" prop="idInforway">
						<el-select v-model="form.idInforway" placeholder="请选择" style="width: 280px">
							<el-option v-for="item in fffsOptions" :key="item.id" :label="item.text" :value="item.id" />
						</el-select>
					</el-form-item>
					<el-form-item label="体检类型" prop="tjlx">
						<el-select v-model="form.tjlx" placeholder="请选择" style="width: 280px" no-data-text="请先添加体检套餐">
							<el-option v-for="(item, index) in tjlxOptions" :key="index" :label="item.text" :value="item.id" />
						</el-select>
					</el-form-item>
					<template v-if="!isCheck">
						<el-form-item label="柜子号" prop="chestNum">
							<el-input v-model="form.chestNum" placeholder="请输入柜子号" style="width: 280px" />
						</el-form-item>
						<el-form-item label="复查收费方式" prop="reviewPayway">
							<el-select v-model="form.reviewPayway" placeholder="请选择" style="width: 280px" clearable>
								<el-option label="现金" value="1" />
								<el-option label="统收" value="5" />
								<el-option label="其他" value="1679299202779451392" disabled />
							</el-select>
						</el-form-item>
						<el-form-item label="复查折扣" prop="reviewZk">
							<el-input-number v-model="form.reviewZk" controls-position="right" :min="0" style="width: 280px" :precision="1" />
						</el-form-item>
						<el-form-item label="报告摸板" prop="templateJm">
							<el-select v-model="form.templateJm" placeholder="请选择" style="width: 280px">
								<el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.id" />
							</el-select>
						</el-form-item>
						<el-form-item label="开单助理" prop="kdzlName">
							<search-select :selectData="selectDatakdzl" @change="selectChangekdzl" selectWidth="280" :initialValue="form.kdzlName" :multiple="true"></search-select>
						</el-form-item>
						<el-form-item label="执行市价" prop="isMarket">
							<el-select v-model="form.isMarket" placeholder="请选择" style="width: 280px">
								<el-option label="否" :value="0" />
								<el-option label="是" :value="1" />
							</el-select>
						</el-form-item>
					</template>
				</el-form>
			</el-col>
			<el-col :span="5">
				<el-form :model="form" size="small" label-width="140px" hide-required-asterisk label-position="top" style="padding-right: 20px">
					<el-form-item label="前台须知" prop="qtxz">
						<el-input v-model="form.qtxz" :rows="isCheck ? 13 : 15" type="textarea" placeholder="请输入" resize="none" :readonly="isCheck == 1"></el-input>
					</el-form-item>
				</el-form>
			</el-col>
		</el-row>
		<div class="add-table">
			<el-row :gutter="10" class="table-btn" style="padding: 10px">
				<template v-if="!isCheck">
					<el-col :span="1.5">
						<el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddRow">新增</el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDeleteRow">删除 </el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="minioffer"> 引用最小套餐 </el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="primary" plain size="mini" icon="el-icon-bell" @click="addremind"> 增加提醒 </el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="primary" plain size="mini" icon="el-icon-s-operation" @click="batchSetExamClass"> 批量设置备单类型 </el-button>
					</el-col>
				</template>
				<template v-else>
					<el-col :span="1.5">
						<el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="single" @click="handleChangeShow(1)">隐藏</el-button>
					</el-col>
					<el-col :span="1.5">
						<el-button type="primary" plain size="mini" icon="el-icon-edit-outline" :disabled="single" @click="handleChangeShow(0)">展示</el-button>
					</el-col>
				</template>
			</el-row>
			<el-row>
				<!-- 左半部分表格 -->
				<el-col :span="12" style="height: 700px">
					<el-table border ref="tableData" :data="tableList1" height="100%" v-loading="leftLoading" @selection-change="handleSelectionChange1" @row-click="rowClick" @row-dblclick="rowDblclick" :row-class-name="forbid2Red">
						<el-table-column type="selection" width="45" align="center" />
						<el-table-column label="序列" type="index" width="55" align="center"> </el-table-column>
						<el-table-column label="体检套餐名称" prop="tjtcmc" min-width="240" align="center">
							<template slot-scope="scope">
								<input-select :selectData="selectData2" @change="selectChange2($event, scope.row)" :selectSize="'mini'" :showTooltip="true" :initialValue="scope.row.tjtcmc" v-if="!isCheck && scope.row.spzt != 1"></input-select>
								<span v-else>{{ scope.row.tjtcmc }}</span>
							</template>
						</el-table-column>
						<el-table-column label="显示状态" prop="show" align="center" min-width="120px" v-if="isCheck">
							<template slot-scope="scope">
								<span>{{ ['展示', '隐藏'][scope.row.show] || '展示' }}</span>
							</template>
						</el-table-column>
						<el-table-column label="审批状态" prop="spzt" min-width="100" align="center">
							<template slot-scope="scope">
								<el-tag type="success" v-if="scope.row.spzt == 1">已审核</el-tag>
								<el-tag type="danger" v-else>未审核</el-tag>
							</template>
						</el-table-column>
						<el-table-column label="备单类型" prop="idExamclass" min-width="130" align="center">
							<template slot-scope="scope">
								<el-select v-if="!isCheck" v-model="scope.row.idExamclass" placeholder="请选择" ref="idExamclass" size="mini" style="width: 100px">
									<el-option v-for="options in bdlxList" :key="options.id" :label="options.text" :value="options.id" />
								</el-select>
								<span v-else>{{ ['健康类', '职业类', '综合类', '入职类', '疫苗类', '其他类'][scope.row.idExamclass] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="体检类型" prop="tjlx" align="center" min-width="120px">
							<template slot-scope="scope">
								<span>{{ ['健康体检', '职业体检', '综合体检'][scope.row.tjlx] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="分中心" prop="fzxName" min-width="120" align="center" />
						<el-table-column label="折后价格" prop="zhjg" min-width="120" align="center" />
						<el-table-column label="原始价格" prop="tcysjg" min-width="120" align="center" />
						<el-table-column label="适用性别" prop="syxb" min-width="100" align="center">
							<template slot-scope="scope">
								<span>{{ ['男', '女', '通用'][scope.row.syxb] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="接害因素" prop="jhysName" min-width="120" align="center" show-overflow-tooltip />
						<el-table-column label="是否外出" prop="sfwc" min-width="120" align="center">
							<template slot-scope="scope">
								<span>{{ ['内检', '外检'][scope.row.sfwc] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="套餐折扣" prop="tczk" min-width="100" align="center" />
						<el-table-column label="客户单位名称" prop="khdwmc" min-width="160" align="center" show-overflow-tooltip />
						<el-table-column label="是否已备单" prop="sfybd" min-width="120" align="center" />
						<el-table-column label="已婚套餐" prop="sfyhtc" min-width="120" align="center">
							<template slot-scope="scope">
								<span>{{ ['是', '否'][scope.row.sfyhtc] || (scope.row.sfyhtc ? '通用' : '') }}</span>
							</template>
						</el-table-column>
						<el-table-column label="年龄自" prop="nlz" min-width="80" align="center" />
						<el-table-column label="年龄至" prop="nld" min-width="80" align="center" />
						<el-table-column label="付款方式" prop="fkfs" min-width="100" align="center">
							<template slot-scope="scope">
								<span>{{ ['统收', '现金'][scope.row.fkfs] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="职业体检类别" prop="zytjlb" min-width="120" align="center">
							<template slot-scope="scope">
								<span>{{ ['上岗前', '在岗期间', '离岗时', '离岗后', '应急'][scope.row.zytjlb] }}</span>
							</template>
						</el-table-column>
						<el-table-column label="体检套餐输入码" prop="tjtcsrm" min-width="120" align="center" show-overflow-tooltip />
						<el-table-column label="体检套餐简称" prop="tjtcjc" min-width="140" align="center" show-overflow-tooltip />
						<el-table-column label="禁用" prop="forbidden" min-width="80" align="center">
							<template slot-scope="scope">
								<el-checkbox :value="scope.row.forbidden == 1 ? true : false" style="pointer-events: none"></el-checkbox>
							</template>
						</el-table-column>
					</el-table>
				</el-col>

				<!-- 右表格 -->
				<el-col :span="12" style="height: 700px; padding-right: 1px">
					<el-table border :inline="true" :data="tableList2" style="margin-left: 3px" height="100%" stripe @selection-change="handleSelectionChange2" v-loading="tableList2Loading">
						<el-table-column type="selection" min-width="120" width="55" align="center" />
						<el-table-column label="序列" type="index" width="65" align="center"> </el-table-column>
						<el-table-column prop="sfxmmc" min-width="140" label="收费项目名称" align="center"> </el-table-column>
						<el-table-column prop="sfxmsrm" min-width="140" label="收费项目输入码" align="center"> </el-table-column>
						<el-table-column prop="xb" min-width="80" label="性别" align="center">
							<template slot-scope="scope">
								<span>{{ ['男', '女'][scope.row.xb] || '通用' }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="printType" min-width="120" label="销售打印分类" align="center"> </el-table-column>
						<el-table-column prop="yhj" min-width="120" label="优惠价" align="center"> </el-table-column>
						<el-table-column prop="jg" min-width="120" label="价格" align="center"> </el-table-column>
						<el-table-column prop="jcyy" min-width="120" label="检查意义" align="center" show-overflow-tooltip> </el-table-column>
						<el-table-column prop="bz" min-width="120" label="备注" align="center" show-overflow-tooltip> </el-table-column>
						<el-table-column prop="tjlx" min-width="120" label="体检类型" align="center">
							<template slot-scope="scope">
								<span>{{ ['健康', '职业', '综合'][scope.row.tjlx] }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="ssks" min-width="120" label="所属科室" align="center"> </el-table-column>
					</el-table>
				</el-col>
			</el-row>
		</div>
		<div slot="footer" class="order-add-footer">
			<el-button type="primary" @click="diasubmitForm" v-if="!isCheck">保 存</el-button>
			<el-button type="primary" plain @click="reset" v-if="!isCheck">重 置</el-button>
			<!-- <el-button @click="cancel">关 闭</el-button> -->
		</div>
		<el-dialog title="计划检期内已有订单" :visible.sync="openOrder" width="980px" class="add-container order-customization-add-dialog" append-to-body :close-on-click-modal="false">
			<!-- <div v-for="(item, index) in hadOrderList" :key="index" style="font-size: 16px">
        {{ `${item.fzxs} ${item.jhjqc ? item.jhjqc.split(' ')[0] : ''}至${item.jhjqd ? item.jhjqd.split(' ')[0] : ''} ${item.khdwmc} 男性人数:${item.nxtjrs} 女性人数:${item.vxtjrs}` }}
      </div> -->
			<el-table border :inline="true" :data="hadOrderList" height="600px" stripe v-loading="orderListLoading">
				<el-table-column type="selection" width="45" align="center" />
				<el-table-column label="序列" type="index" width="55" align="center"> </el-table-column>
				<el-table-column prop="fzxs" min-width="80" label="分中心" align="center" show-overflow-tooltip> </el-table-column>
				<el-table-column prop="jhjqc" min-width="80" label="计划检期从" align="center">
					<template slot-scope="scope">
						<span>{{ scope.row.jhjqc ? scope.row.jhjqc.split(' ')[0] : '' }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="jhjqd" min-width="80" label="计划检期到" align="center">
					<template slot-scope="scope">
						<span>{{ scope.row.jhjqd ? scope.row.jhjqd.split(' ')[0] : '' }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="khdwmc" min-width="120" label="客户单位名称" align="center" show-overflow-tooltip> </el-table-column>
				<el-table-column prop="nxtjrs" min-width="60" label="男性人数" align="center"> </el-table-column>
				<el-table-column prop="vxtjrs" min-width="60" label="女性人数" align="center"> </el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="openOrder = false">确 定</el-button>
			</div>
		</el-dialog>
		<!-- 最小套餐 -->
		<minOffer ref="minOffer" @minCombo="minCombo"></minOffer>
		<!-- 添加提醒 -->
		<addRemind ref="addRemind"></addRemind>
		<el-dialog title="变更备注" :visible.sync="alterOpen" width="380px" class="add-container order-customization-add-dialog alter-note" append-to-body :close-on-click-modal="false" :show-close="false">
			<div class="alter-world">请输入变更备注：</div>
			<el-input v-model="form.bgmemo" :rows="4" type="textarea" placeholder="请输入"></el-input>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="handleConfirmAlter">确 定</el-button>
				<el-button @click="handleCancelAlter">取 消</el-button>
			</div>
		</el-dialog>
		<!-- 批量设置备单类型对话框 -->
		<el-dialog title="批量设置备单类型" :visible.sync="batchSetDialog.visible" width="400px" append-to-body>
			<el-form>
				<el-form-item label="备单类型">
					<el-select v-model="batchSetDialog.examClass" placeholder="请选择备单类型" style="width: 100%">
						<el-option v-for="item in bdlxList" :key="item.id" :label="item.text" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancelBatchSet">取 消</el-button>
				<el-button type="primary" @click="confirmBatchSet">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import minOffer from './min_offer.vue'
import addRemind from './add_remind.vue'

import { getKhdwdhAndTcs, getfzxList, checkDate, getOrderNotifycation, getcombo, getOrderDetails, saveOrUpdateOrder, getTjtcData, showOrHide, getUserCid } from '@/api/sale/order_customization.js'

export default {
	name: 'Order_add',
	components: {
		minOffer,
		addRemind,
	},
	data() {
		var validatePass = (rule, value, callback) => {
			let jhjqc = new Date(this.form.jhjqc).getTime()
			let jhjqd = new Date(this.form.jhjqd).getTime()
			if (jhjqc > jhjqd) {
				callback(new Error('日期格式不正确'))
			} else {
				callback()
			}
		}
		return {
			// 标题
			title: undefined,
			// 是否为复制(1为是)
			isCopy: 0,
			// 是否为查看(1为是)
			isCheck: 0,
			// 是否为变更(1为是)
			isAlter: 0,
			// 打开变更对话框
			alterOpen: false,
			// 订单ID
			orderId: undefined,
			// 客户单位名称输入码选择
			selectData: {
				placeholder: '请输入输入码选择',
				key: '销售经理', //第一列标题
				value: '客户单位名称', //第二列标题
				url: '/sell/createorder/getKhdwmcData', //请求连接
				selectWidth: 280, //选择器宽度（选填，默认230）不加px
				bindValue: '',
				firstName: 'xsjl', //接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
				queryData: 'key', //向接口传递的参数名
			},
			// 已选单位名称
			selectedOrderName: '',
			// 合同编号参数
			selectData3: {
				placeholder: '请输入输入码选择',
				key: '合同编号', //第一列标题
				value: '合同名称', //第二列标题
				url: '/sell/createorder/getHtbhData', //请求连接
				selectWidth: 280, //选择器宽度（选填，默认230）不加px
				firstName: 'htbh', //接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'htmc', //接口返回值对应第二列的参数名(不传默认为'name')
				queryData: 'key', //向接口传递的参数名
			},
			choosesjh: {
				placeholder: '请输入输入码选择',
				key: '姓名', //第一列标题
				value: '电话', //第二列标题
				url: '/sell/createorder/getLeaderTel', //请求连接
				selectWidth: 280, //选择器宽度（选填，默认230）不加px
				firstName: 'name', //接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'text', //接口返回值对应第二列的参数名(不传默认为'name')
				queryData: 'key', //向接口传递的参数名
			},
			// 开单助理数据
			selectDatakdzl: {
				placeholder: '请选择...',
				inputTitle: '拼音搜索', // 搜索标题
				inputPlaceholder: ' ', // 搜索placeholder
				key: '输入码', //第一列标题
				value: '助理姓名', //第二列标题
				url: '/sell/createorder/getKdzl', //请求连接
				bindValue: '', //初始值(必传)
				firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'userName', //接口返回值对应第二列的参数名(不传默认为'name')
				thirdName: '所属分中心',
				fourthName: '所属部门',
				thirdData: 'ssFzx',
				fourthData: 'ssDep',
				// optionFlag:'id',
				valueKey: 'id',
			},
			// 分中心列表
			fzxOptions: [],
			// 发放方式列表
			fffsOptions: [],
			// 提交表单
			form: {},
			// 体检类型列表
			tjlxOptions: [
				{ id: 0, text: '健康' },
				{ id: 1, text: '职业' },
				{ id: 2, text: '综合' },
			],
			// 表单校验
			formRules: {
				khdwmcid: [{ required: true, message: '客户单位名称不能为空', trigger: 'change' }],
				ddmc: [{ required: true, message: '订单名称不能为空', trigger: 'change' }],
				khdwdh: [{ required: true, message: '客户单位电话不能为空', trigger: 'change' }],
				fzxid: [{ required: true, message: '请选择分中心', trigger: 'blur' }],
				jhjqc: [
					{ required: true, message: '计划检期从不能为空', trigger: 'change' },
					{ validator: validatePass, trigger: 'change' },
				],
				jhjqd: [
					{ required: true, message: '计划检期到不能为空', trigger: 'change' },
					{ validator: validatePass, trigger: 'change' },
				],
				nxtjrs: [{ required: true, message: '男性体检人数不能为空', trigger: 'blur' }],
				vxtjrs: [{ required: true, message: '女性体检人数不能为空', trigger: 'blur' }],
				ddzk: [{ required: true, message: '订单折扣不能为空', trigger: 'change' }],
				ddyhj: [{ required: true, message: '套餐平均价不能为空', trigger: 'blur' }],
				ddjg: [{ required: true, message: '费用预算不能为空', trigger: 'change' }],
				xsdh: [{ required: true, message: '销售电话不能为空', trigger: 'change' }],
				idInforway: [{ required: true, message: '发放方式不能为空', trigger: 'change' }],
				tjlx: [{ required: true, message: '体检类型不能为空', trigger: 'change' }],
				kdzlName: [{ required: true, message: '开单助理不能为空', trigger: 'blur' }],
			},
			// 检期内已有订单弹窗
			openOrder: false,
			// 已有订单列表
			hadOrderList: [],
			orderListLoading: false,

			// #region *********************下方表格
			// 体检套餐名称参数
			selectData2: {
				placeholder: '请输入输入码选择',
				key: '体检套餐输入码', //第一列标题
				value: '体检套餐名称', //第二列标题
				url: '/sell/createorder/getTcData', //请求连接
				selectWidth: '100%', //选择器宽度（选填，默认230）不加px
				firstName: 'tjtcsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
				secondName: 'tjtcmc', //接口返回值对应第二列的参数名(不传默认为'name')
				queryData: 'key', //向接口传递的参数名
			},
			// 左侧表格
			tableList1: [],
			// 表格索引
			tableIndex: 0,
			// 左侧选中数据列表
			ids: [],
			selectList: [],
			// 详情时临时表格
			tempList: [],
			// 左侧表格加载中
			leftLoading: false,
			// 右侧表格
			tableList2: [],
			// 右侧表格加载中
			tableList2Loading: false,
			// 非多个禁用
			multiple: true,
			// 非单个禁用
			single: true,
			// 备单类型
			bdlxList: [
				{ id: 0, text: '健康类' },
				{ id: 1, text: '职业类' },
				{ id: 2, text: '综合类' },
				{ id: 5, text: '入职类' },
				{ id: 6, text: '疫苗类' },
				{ id: 7, text: '其他类' },
			],
			// 批量设置备单类型弹窗相关
			batchSetDialog: {
				visible: false,
				examClass: undefined
			},
			// #endregion
		}
	},
	computed: {
		// 禁用合同编号
		disabled3() {
			if (this.form.sfyqdht == '1') {
				return true
			} else {
				return false
			}
		},
	},
	created() {
		let orderId = this.$route.query.orderId
		if (orderId) {
			let isCopy = this.$route.query.isCopy
			let isCheck = this.$route.query.isCheck
			let isAlter = this.$route.query.isAlter
			this.isCopy = isCopy
			this.isCheck = isCheck
			this.isAlter = isAlter
			this.orderId = orderId
			this.title = (!isCopy && !isCheck ? '编辑' : !isCheck ? '新增' : '查看') + '订单'
			this.title = isAlter ? '变更订单' : this.title
			this.title = isCopy ? '复制订单' : this.title

			this.initForm()
			this.handleFzxlist()
			this.handleFffsList()
			if (!isCopy) {
				const loading = this.$loading({ target: '#order-customization-add' })
				// 获取订单详情
				getOrderDetails(orderId)
					.then(({ data }) => {
						// 查询并返回未审核的套餐 查看时不调用
						this.handleTjtc(isCopy).then(() => {
							if (data.khdwmcid && this.isCheck != 1) this.handleAddTjtc(data.khdwmcid)
						})

						this.form = data
						this.form.fzxid = this.form.fzxid.split(',')
						this.form.kdzlName = this.form.kdzlName.split(',')
						if (isAlter) {
							this.alterOpen = true
						}
						loading.close()
					})
					.catch(() => {
						this.handleTjtc(isCopy)
						loading.close()
					})
			}
		} else {
			this.title = '新增订单'
			this.reset()
			this.isCopy = 0
			this.isCheck = 0
			this.isAlter = 0
			this.handleFzxlist()
			this.handleFffsList()
		}
		const obj = Object.assign({}, this.$route, { title: this.title })
		this.$tab.updatePage(obj)
	},
	methods: {
		// 批量设置备单类型
		batchSetExamClass() {
			if (this.selectList.length === 0) {
				this.$message.warning('请先选择要设置的套餐');
				return;
			}
			this.batchSetDialog.visible = true;
		},

		// 确认批量设置备单类型
		confirmBatchSet() {
			if (this.batchSetDialog.examClass === undefined) {
				this.$message.warning('请选择备单类型');
				return;
			}

			this.selectList.forEach(item => {
				item.idExamclass = this.batchSetDialog.examClass;
			});

			this.batchSetDialog.visible = false;
			this.batchSetDialog.examClass = undefined;
			this.$message.success('批量设置备单类型成功');
		},

		// 取消批量设置
		cancelBatchSet() {
			this.batchSetDialog.visible = false;
			this.batchSetDialog.examClass = undefined;
		},
		// 打开添加
		handleAdd() {
			this.title = '新增订单'
			this.reset()
			this.isCopy = 0
			this.isCheck = 0
			this.isAlter = 0
			this.handleFzxlist()
			this.handleFffsList()
		},
		// 获取分中心列表
		handleFzxlist() {
			getUserCid({
				current: 1,
				size: 99999,
			}).then(({ data }) => {
				data.forEach((el) => {
					el.id = el.id.toString()
				})
				this.fzxOptions = data
			})
		},
		// 获取发放方式列表
		handleFffsList() {
			getOrderNotifycation().then(({ data }) => {
				this.fffsOptions = data
			})
		},
		// 编辑、复制
		handleUpdate(id, isCopy = 0, isCheck = 0, isAlter = 0) {
			this.isCopy = isCopy
			this.isCheck = isCheck
			this.isAlter = isAlter
			this.orderId = id
			this.title = (!isCopy && !isCheck ? '编辑' : !isCheck ? '新增' : '查看') + '订单'
			this.title = isAlter ? '变更订单' : this.title

			this.initForm()
			this.handleFzxlist()
			this.handleFffsList()
			if (!isCopy) {
				const loading = this.$loading({ target: '#order-customization-add' })
				// 获取订单详情
				getOrderDetails(id)
					.then(({ data }) => {
						this.form = data
						this.form.fzxid = this.form.fzxid.split(',')
						this.form.kdzlName = this.form.kdzlName.split(',')
						if (isAlter) {
							this.alterOpen = true
						}
						loading.close()
					})
					.catch(() => {
						loading.close()
					})
			}
			this.handleTjtc(isCopy)
		},
		// 加载订单关联的套餐
		handleTjtc(isCopy = 0) {
			return new Promise(resolve => {
				this.leftLoading = true
				getTjtcData({
					size: 999,
					ddId: this.orderId,
					isCopy,
					current: 1,
				})
					.then(({ data }) => {
						data.records.forEach((el) => {
							el.tableIndex = this.tableIndex++
						})
						this.tableList1 = data.records
						this.tempList = JSON.parse(JSON.stringify(data.records))
						this.leftLoading = false
						this.tjlxLimit(this.isCopy ? 0 : 1)
						resolve()
					})
					.catch(() => {
						this.leftLoading = false
						resolve()
					})
			})
		},
		// 查看订单
		handleCheck(id) {
			this.handleUpdate(id, 0, 1)
		},
		// 变更订单
		handleAlter(id) {
			this.handleUpdate(id, 0, 0, 1)
		},
		// 确定变更订单
		handleConfirmAlter() {
			if (!this.form.bgmemo) {
				this.$modal.msgWarning('变更备注不能为空')
			} else {
				this.alterOpen = false
			}
		},
		// 取消变更订单
		handleCancelAlter() {
			this.alterOpen = false
		},
		// 禁检设置底色为红
		forbid2Red({ row }) {
			if (row.groupLimit == 1) {
				return 'limit-yellow'
			} else if (row.forbid == 1) {
				return 'limit-red'
			} else {
				return ''
			}
		},
		// 客户单位名称返回值
		selectChange(value) {
			this.form.khdwmcid = value.id
			this.form.khdwmc = value.khdwmc
			this.form.ddmc = value.khdwmc + new Date().getFullYear()
			this.form.khdwdh = value.khdh
			this.selectedOrderName = value.khdwmc
			if (value.id) this.handleAddTjtc(value.id)
		},
		// 根据选择的客户单位添加套餐
		handleAddTjtc(khdwdhId) {
			this.leftLoading = true
			getKhdwdhAndTcs({ khdwdhId })
				.then(({ data }) => {
					let tableList1 = this.tableList1.map((item) => (item.tcid ? item.tcid : item.id))
					data.forEach((el) => {
						if (!tableList1.includes(el.id)) {
							if (!this.tableList1[0].id) {
								this.$delete(this.tableList1, 0)
							}
							el.tableIndex = this.tableIndex++
							this.tableList1.push(el)
						}
					})
					this.leftLoading = false
				})
				.catch((error) => {
					console.error(error)
					this.leftLoading = false
				})
		},
		// 合同编号返回值
		selectChange3(value) {
			this.form.htbh = value.id
		},
		choosesjhChange(value) {
			this.form.fsdxsjh = value.text
		},
		// 是否签订合同改变
		sfyqdhtChange($event) {
			if ($event == '1') {
				this.form.htbh = undefined
			}
		},
		// 选择开单助理
		selectChangekdzl(value) {
			this.form.kdzlName = []
			value.forEach((el) => {
				if (!this.form.kdzlName) {
					this.form.kdzlName = [el.label]
				} else {
					this.form.kdzlName.push(el.label)
				}
			})
		},
		// 计划检期内已有
		dateChange() {
			if (this.form.jhjqc && this.form.jhjqd && new Date(this.form.jhjqc).getTime() <= new Date(this.form.jhjqd).getTime() && this.form.fzxid.length) {
				this.orderListLoading = true
				checkDate({
					fzxs: this.form.fzxid.join(','),
					id: this.form.id,
					jhjqc: this.form.jhjqc,
					jhjqd: this.form.jhjqd,
				})
					.then(({ data }) => {
						if (data.length) {
							this.hadOrderList = data
							this.openOrder = true
						}
						this.orderListLoading = false
					})
					.catch((error) => {
						console.error(error)
						this.orderListLoading = false
					})
			}
		},
		// 生成费用预算
		chargeChange() {
			if ((this.form.nxtjrs || this.form.nxtjrs === 0) && (this.form.vxtjrs || this.form.vxtjrs === 0) && this.form.ddyhj) {
				this.form.ddjg = (this.form.nxtjrs + this.form.vxtjrs) * this.form.ddyhj
			}
		},

		// 下方表格*******************
		// 最小套餐引用
		minioffer() {
			this.$refs.minOffer.showDialog()
		},
		// 增加提醒
		addremind() {
			this.$refs.addRemind.showDialog(this.form.khdwmcid, this.form.khdwmc)
		},
		// 体检套餐名称返回值
		selectChange2($event, row) {
			this.tableList1.forEach((el) => {
				if (row.tableIndex == el.tableIndex) {
					el = Object.assign(el, $event, { idExamclass: undefined })
				}
			})
			this.tjlxLimit()
		},
		// 增加行
		handleAddRow() {
			var list = {
				tableIndex: this.tableIndex++,
				tjtcmc: undefined,
				spzt: undefined,
				idExamclass: undefined,
				tjlx: undefined,
				syxb: undefined,
				jhys: undefined,
				sfwc: undefined,
				zhjg: undefined,
				fzxName: undefined,
				tcysjg: undefined,
				tczk: undefined,
				khdwmc: undefined,
				sfybd: undefined,
				sfyhtc: undefined,
				nlz: undefined,
				nld: undefined,
				fkfs: undefined,
				zytjlb: undefined,
				tjtcsrm: undefined,
				tjtcjc: undefined,
				forbid: undefined,
			}
			this.tableList1.push(list)
		},
		// 返回的最小套餐数据
		minCombo(data) {
			data.forEach((el) => {
				// 1023隐藏
				// el.idExamclass = undefined
				el.tableIndex = this.tableIndex++
			})
			this.tableList1 = [...data, ...this.tableList1]
			this.tjlxLimit()
		},
		// 填写体检套餐后限制表单中体检类型
		tjlxLimit(flag = 0) {
			let classArray = []
			let jk = false
			let zy = false
			this.tableList1.forEach((el) => {
				if (!jk && el.tjlx == 0) {
					classArray.push({ id: 0, text: '健康' })
					jk = true
				} else if (!zy && el.tjlx == 1) {
					classArray.push({ id: 1, text: '职业' })
					zy = false
				}
			})
			for (let i = classArray.length - 1; i >= 1; i--) {
				if (classArray[i].id == classArray[i - 1].id) {
					this.$delete(classArray, i)
				}
			}
			classArray.push({ id: 2, text: '综合' })
			if (classArray.length > 2) {
				classArray = [{ id: 2, text: '综合' }]
			}
			this.tjlxOptions = classArray
			if (flag == 0) {
				this.form.tjlx = classArray.length != 0 ? classArray[0].id : undefined
			}
		},
		// 单击选中某行
		rowClick(row, col) {
			if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
			this.$refs.tableData.toggleRowSelection(row, true)
		},
		// 双击获取关联收费项目
		rowDblclick($event) {
			if (!$event.tcid) {
				if (!$event.id) {
					return this.$modal.msgWarning('请先选择套餐')
				} else {
					$event.tcid = $event.id
				}
			}
			this.tableList2Loading = true
			getcombo($event.tcid, $event.combostate)
				.then(({ data }) => {
					this.tableList2 = data.records
					this.tableList2Loading = false
				})
				.catch(() => {
					this.tableList2Loading = false
				})
		},
		// 左侧多选框选中数据
		handleSelectionChange1(selection) {
			this.ids = selection.map((item) => item)
			this.selectList = selection.map((item) => item)
			this.multiple = !selection.length
			this.single = selection.length != 1
		},
		// 删除选中
		handleDeleteRow() {
			this.ids.forEach((val) => {
				for (let i = this.tableList1.length - 1; i >= 0; i--) {
					if (this.tableList1[i].tableIndex == val.tableIndex) {
						this.$delete(this.tableList1, i)
					}
				}
			})
		},
		// 查看订单时展示隐藏体检套餐
		handleChangeShow(paused) {
			this.$confirm('确定要' + (paused == 0 ? '展示' : '隐藏') + '吗?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			})
				.then(() => {
					this.leftLoading = true
					let ids = ''
					this.selectList.forEach((el) => {
						ids = ids.concat(el.tcid)
					})
					showOrHide({
						ddId: this.orderId,
						ids,
						paused,
					}).then(({ data }) => {
						this.handleTjtc()
					})
				})
				.catch(() => { })
		},
		// 多选框选中数据
		handleSelectionChange2(selection) {
			this.ids = selection.map((item) => item.id)
			this.multiple = !selection.length
		},

		// 表单重置
		reset() {
			if (this.title == '新增订单' && !this.isCopy) {
				this.initForm()
			} else {
				this.handleUpdate(this.orderId, this.isCopy)
			}
		},
		initForm() {
			this.tableList1 = [
				{
					tableIndex: this.tableIndex++,
					tjtcmc: undefined,
					spzt: undefined,
					idExamclass: undefined,
					tjlx: undefined,
					syxb: undefined,
					jhys: undefined,
					sfwc: undefined,
					zhjg: undefined,
					fzxName: undefined,
					tcysjg: undefined,
					tczk: undefined,
					khdwmc: undefined,
					sfybd: undefined,
					sfyhtc: undefined,
					nlz: undefined,
					nld: undefined,
					fkfs: undefined,
					zytjlb: undefined,
					tjtcsrm: undefined,
					tjtcjc: undefined,
					forbid: undefined,
				},
			]
			this.tableList2 = []
			this.resetForm('form')
			this.form = {
				khdwmcid: undefined,
				ddmc: undefined,
				txfs: 0,
				qtxz: '体检时间：' + this.$getDate().slice(0, 4) + '年  月  日——' + this.$getDate().slice(0, 4) + '年  月  日\n收费方式：统收  自费 \n关于替检：可替  不可替\n报告发放：团发  个发\n危急值发放  个发  分类发  高危： 中危： 低危：\n关于加项：统收  自费\n报告柜子：    号\n其他情况：',
				fsdxsjh: undefined,
				khdwdh: undefined,
				fzxid: [this.$getCookie('cid')],
				jhjqc: undefined,
				jhjqd: undefined,
				sfyqdht: '1',
				htbh: undefined,
				nxtjrs: undefined,
				vxtjrs: undefined,
				ddzk: undefined,
				ddyhj: undefined,
				ddjg: undefined,
				xsdh: undefined,
				tjxs: 0,
				idInforway: undefined,
				tjlx: 0,
				chestNum: undefined,
				reviewPayway: undefined,
				reviewZk: 10.0,
				templateJm: undefined,
				kdzlName: '',
				isMarket: 0,
			}
		},
		// 保存提交
		diasubmitForm() {
			this.$refs['form'].validate((valid) => {
				if (valid) {
					let form = JSON.parse(JSON.stringify(this.form))
					form.fzxid = form.fzxid.join(',')
					form.jhjqc = form.jhjqc.concat(' 00:00:00')
					form.jhjqd = form.jhjqd.concat(' 23:59:59')
					form.tjtcId = []
					this.tableList1.forEach((el) => {
						if (el.id) {
							form.tjtcId.push(el)
						}
					})
					if (form.tjtcId.length == 0) {
						this.$modal.msgWarning('请添加体检套餐')
						return
					} else {
						let error = false
						form.tjtcId.forEach((el) => {
							if (!el.idExamclass && el.idExamclass != 0) {
								this.$modal.msgWarning('请为' + el.tjtcmc + '选择备单类型')
								error = true
							}
						})
						if (error) return
					}
					// if (form.tjtcId.length > 1) {
					//   for (let i = 0; i < form.tjtcId.length - 1; i++) {
					//     for (let j = i + 1; j < form.tjtcId.length; j++) {
					//       if (form.tjtcId[i].tjtcjc == form.tjtcId[j].tjtcjc) {
					//         this.$alert(`套餐<font color='red'>${form.tjtcId[i].tjtcmc}、${form.tjtcId[j].tjtcmc}</font>存在简称相同的套餐简称`, '提示', {
					//           dangerouslyUseHTMLString: true,
					//         })
					//         return
					//       }
					//     }
					//   }
					// }
					// 25-11-03修改 forbid为1禁用跳过检查
					if (form.tjtcId.length > 1) {

						for (let i = 0; i < form.tjtcId.length - 1; i++) {
							// 如果当前套餐已被禁用，则跳过检查
							if (form.tjtcId[i].forbid === 1) continue;

							for (let j = i + 1; j < form.tjtcId.length; j++) {
								// 如果比较的套餐已被禁用，则跳过
								if (form.tjtcId[j].forbid === 1) continue;

								if (form.tjtcId[i].tjtcjc == form.tjtcId[j].tjtcjc) {
									this.$alert(`套餐<font color='red'>${form.tjtcId[i].tjtcmc}、${form.tjtcId[j].tjtcmc}</font>存在简称相同的套餐简称`, '提示', {
										dangerouslyUseHTMLString: true,
									})
									return
								}
							}
						}
					}
					form.comboState = form.tjtcId.map((item) => item.combostate).join(',')
					form.fchange = this.isAlter
					for (let i = form.kdzlName.length - 1; i >= 0; i--) {
						if (!form.kdzlName[i]) {
							this.$delete(form.kdzlName, i)
						}
					}
					form.kdzlName = form.kdzlName.join(',')
					if (!this.tempList.length || this.isCopy == 1) {
						form.tjtcId.forEach((el) => {
							el.state = 'added'
							if (this.isCopy == 1) {
								el.id = el.tcid
							}
						})
					} else {
						console.log(this.tempList)
						console.log(form.tjtcId)
						this.tempList.forEach((el) => {
							let change = false
							form.tjtcId.forEach((val) => {
								if (el.id == val.id) {
									val.state = 'modified'
									change = true
								}
							})
							if (!change) {
								el.state = 'removed'
								form.tjtcId.unshift(el)
							}
						})
						form.tjtcId.forEach((val) => {
							let have = false
							this.tempList.forEach((el) => {
								if (el.id == val.id) {
									have = true
								}
							})
							if (!have) {
								val.state = 'added'
							} else {
								val.oacid = JSON.parse(JSON.stringify(val.id))
								val.id = val.tcid
							}
						})
					}

					saveOrUpdateOrder(form).then(() => {
						if (this.form.id != null) {
							this.$modal.msgSuccess('修改成功')
							this.cancel()
							this.$emit('getList')
						} else {
							this.$modal.msgSuccess('添加成功')
							this.cancel()
							this.$emit('getList')
						}
					})
				}
			})
		},
		// 取消按钮
		cancel() {
			this.open = false
			this.initForm()
		},
	},
}
</script>

<style lang="scss">
.order-customization-add {
	padding-bottom: 40px;

	.limit-red {
		background-color: rgba($color: #ff0000, $alpha: 0.8);
	}

	.limit-yellow {
		background-color: rgba($color: #cf602c, $alpha: 0.8);
	}

	.order-add-footer {
		position: fixed;
		bottom: 8px;
		z-index: 2;
		width: 100%;
		text-align: center;
		background-color: #fff;
	}

	.order-customization-add-dialog {

		.el-form-item--mini.el-form-item,
		.el-form-item--small.el-form-item {
			margin-bottom: 15px !important;
		}

		.add-table {
			border: 1px solid #d4d6d9;

			.table-btn {
				padding: 16px 20px;
			}

			.el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
				background: transparent;
			}

			.el-input__inner {
				padding: 0 8px;
				border-width: 0;
				text-align: center;
				background: transparent;

				&:focus {
					border-width: 1px;
					text-align: left;
				}
			}
		}
	}
}
</style>
