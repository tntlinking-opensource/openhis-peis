<!-- 创建套餐添加  开发人：麦沃德科技半夏 -->
<template>
  <!-- 添加或修改对话框 -->
  <div class="app-container flex-direction-column add-create-offer">
    <el-form ref="form" v-loading="popLoading" size="mini" :model="form" :rules="rules" :inline="true" label-width="106px">
      <el-form-item label="套餐名称" prop="tjtcmc">
        <el-input v-model="form.tjtcmc" placeholder="请输入套餐名称" clearable style="width: 280px" @input="nameChange" />
      </el-form-item>
      <el-form-item label="套餐输入码" prop="tjtcsrm">
        <el-input v-model="form.tjtcsrm" placeholder="输入套餐名称后自动生成" clearable style="width: 280px" readonly />
      </el-form-item>
      <el-form-item label="单位名称" prop="khdwmc">
        <el-tooltip class="item" effect="dark" :content="form.khdwmc" placement="top" :disabled="!form.khdwmc">
          <input-select :selectData="unitData" selectWidth="280" @change="unitChange"></input-select>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="form.tjlx" placeholder="请选择体检类型" style="width: 280px" @change="onTjlxChanged">
          <el-option v-for="item in tjlxOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-form-item label="适用性别" prop="syxb" style="margin: 0">
          <el-select v-model="form.syxb" placeholder="请选择适用性别" style="width: 110px" @change="onValueChanged">
            <el-option :key="0" label="男" :value="0" />
            <el-option :key="1" label="女" :value="1" />
            <el-option :key="2" label="通用" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="婚否" prop="sfyhtc" style="margin: 0" label-width="60px">
          <el-select v-model="form.sfyhtc" placeholder="请选择婚否" style="width: 110px">
            <el-option key="0" label="是" value="0" />
            <el-option key="1" label="否" value="1" />
            <el-option key="2" label="通用" value="2" />
          </el-select>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-form-item label="年龄自" prop="nlz" style="margin: 0 12px 0 0">
          <el-input-number v-model="form.nlz" :min="0" :max="Number(form.nld)" :controls="false" style="width: 121px" />
        </el-form-item>
        <el-form-item label="至" prop="nld" style="margin: 0" label-width="26px">
          <el-input-number v-model="form.nld" :min="0" @change="nldChange" :controls="false" style="width: 121px" />
        </el-form-item>
      </el-form-item>
      <el-form-item label="接害因素" prop="jhys">
        <el-tooltip effect="dark" :content="jhysTips" placement="top" :disabled="!form.jhys" v-if="!jhysDisable">
          <search-select ref="jhysData" :selectData="jhysData" :multiple="true" :selectWidth="280" :initialValue="form.jhysName" @change="jhysChange"></search-select>
        </el-tooltip>
        <el-input style="width: 280px" disabled v-else />
      </el-form-item>
      <el-form-item label="职业类别" prop="zytjlb">
        <el-select v-model="form.zytjlb" placeholder="请选择职业类别" style="width: 280px" @change="ppzxtc" :disabled="zytjlbDisable">
          <el-option v-for="item in zytjlbOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="套餐优惠价" prop="zhjg">
        <el-input-number v-model="form.zhjg" placeholder="请输入" :min="0" :controls="false" style="width: 280px" @change="computeYh" />
      </el-form-item>
      <el-form-item label="平均折扣" prop="tczk">
        <el-input-number v-model="form.tczk" placeholder="请输入" :controls="false" style="width: 280px" @change="onZkchange" />
      </el-form-item>
      <el-form-item label="付款方式" prop="fkfs">
        <el-select v-model="form.fkfs" placeholder="请选择付款方式" style="width: 280px" filterable>
          <el-option v-for="item in fkfsOptions" :key="item.id" :label="item.fzx" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="分中心" prop="fzxid">
        <el-tooltip effect="dark" :content="form.fzx" placement="top" :disabled="!form.fzx">
          <el-select class="branch-select" v-model="form.fzxid" placeholder="请输入分中心名称选择" style="width: 280px" filterable multiple @change="fzxChange">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
          </el-select>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="外出" prop="sfwc">
        <el-select v-model="form.sfwc" placeholder="请选择是否外出" style="width: 280px">
          <el-option :key="0" label="否" :value="0" />
          <el-option :key="1" label="是" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="原价合计" prop="tcysjg">
        <el-input v-model="form.tcysjg" placeholder="请添加收费项目" readonly style="width: 280px" />
      </el-form-item>
      <el-form-item label="成本合计" prop="totalCostprice">
        <el-input v-model="form.totalCostprice" placeholder="请添加收费项目" readonly style="width: 280px" />
      </el-form-item>
      <el-form-item label="平安套餐ID" prop="pinganId">
        <el-input v-model="form.pinganId" placeholder="平安好医生APP合作" clearable style="width: 280px" />
      </el-form-item>
      <el-form-item label="是否自选" prop="sfzx">
        <el-select v-model="form.sfzx" placeholder="请选择是否自选" style="width: 280px" @change="onsfzxchange">
          <el-option v-for="item in sfzxOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="自选折扣" prop="zxzk">
        <el-input-number v-model="form.zxzk" :controls="false" :min="0" style="width: 280px" />
      </el-form-item>
      <el-form-item label="备选组数量" prop="kxsl">
        <el-input-number v-model="form.kxsl" controls-position="right" :min="0" style="width: 280px" @change="computeYh" />
      </el-form-item>
      <el-form-item label="备选组价格" prop="groupPrice">
        <el-input-number v-model="form.groupPrice" controls-position="right" :min="0" style="width: 280px" @change="computeYh" />
      </el-form-item>
      <el-form-item label="APP团惠价" prop="zhjgapp">
        <el-input-number v-model="form.zhjgapp" :controls="false" :min="0" style="width: 280px" />
      </el-form-item>
      <el-form-item label="APP大套餐" prop="isBig">
        <el-select v-model="form.isBig" placeholder="请选择是否APP大套餐" style="width: 280px">
          <el-option :key="0" label="否" :value="0" />
          <el-option :key="1" label="是" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="bz">
        <el-input v-model="form.bz" placeholder="请输入备注信息" style="width: 676px" />
      </el-form-item>
    </el-form>
    <div class="add-table">
      <!-- 搜索 -->
      <div class="flex" style="align-items: center">
        <el-form size="mini" :model="queryProject" ref="queryProject" :inline="true" style="width: 50%" class="no-margin-bottom">
          <el-form-item label="收费项目名称" prop="examfeeitemName">
            <el-input v-model="queryProject.examfeeitemName" placeholder="请输入收费项目名称" clearable style="width: 180px" @keyup.enter.native="searchProject" />
          </el-form-item>
          <el-form-item label="收费项目输入码" prop="sfxmsrm">
            <el-input ref="sfxmsrmInput" v-model="queryProject.sfxmsrm" placeholder="请输入收费项目输入码" clearable style="width: 180px" @keyup.enter.native="searchProject" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="searchProject">搜索</el-button>
          </el-form-item>
        </el-form>
        <el-button type="success" icon="el-icon-refresh" plain size="mini" @click="handleRefresh" style="margin-left: 50px; margin-right: 24px">刷新</el-button>
        <span :style="{ color: theme }" v-if="tipsAlternative" v-html="tipsAlternative"></span>
      </div>
      <!-- 表格 -->
      <div class="flex">
        <div ref="leftTable" class="left-table">
          <div class="flex">
            <div class="table-box">
              <el-table border ref="projectTable" size="mini" v-loading="projectLoading" :data="projectList" height="300px" style="width: 100%" :row-class-name="projectRowClassName" @selection-change="projectChange" @row-click="rowClick" @row-dblclick="projectDblclick">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序列" type="index" width="65" align="center" />
                <el-table-column label="收费项目名称" prop="sfxmmc" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="原价" prop="jg" min-width="100" align="center" />
                <el-table-column label="成本价" prop="costprice" min-width="100" align="center" v-if="isCW" />
                <el-table-column label="性别" prop="xb" min-width="90" align="center">
                  <template slot-scope="scope">
                    <div v-for="item in xbOptions" :key="item.id">
                      <span v-if="item.id == scope.row.xb">{{ item.text }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="体检类型" prop="tjlx" min-width="120" align="center">
                  <template slot-scope="scope">
                    {{ tjlx(scope.row.tjlx) }}
                  </template>
                </el-table-column>
                <el-table-column label="检查意义" prop="jcyy" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="备注" prop="bz" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="所属科室" prop="ssks" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="检查次数" prop="sycstj" min-width="120" align="center" />
                <el-table-column label="收费项目输入码" prop="sfxmsrm" min-width="150" align="center" show-overflow-tooltip />
                <el-table-column label="查看图片" width="120" align="center">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" :style="{ color: theme }" @click="handleViewImg(scope.row.inputcodee)">查看图片 </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination background :pager-count="5" :current-page="queryProject.current" hide-on-single-page :page-sizes="[10, 20, 30, 50]" :page-size="queryProject.size" layout="sizes, prev, pager, next, jumper ,total" :total="projectTotal" @size-change="projectSizeChange" @current-change="projectCurrentChange">
              </el-pagination>
            </div>
            <div class="center-btn">
              <el-button type="primary" plain size="mini" class="el-icon-arrow-right" :disabled="!projectSelect.length" @click="selectOption"> </el-button>
              <el-button type="primary" plain size="mini" class="el-icon-arrow-left" :disabled="!selectIds.length" @click="removeOption" style="margin: 24px 0 0 !important"></el-button>
            </div>
          </div>
          <el-form size="mini" :model="queryPackage" ref="queryPackage" :inline="true" class="no-margin-bottom">
            <el-form-item label="套餐名称" prop="tjtcmc">
              <el-input v-model="queryPackage.tjtcmc" placeholder="体检套餐名称" clearable style="width: 150px" @keyup.enter.native="searchPackage" />
            </el-form-item>
            <el-form-item label="输入码" prop="tjtcsrm">
              <el-input v-model="queryPackage.tjtcsrm" placeholder="体检套餐输入码" clearable style="width: 150px" @keyup.enter.native="searchPackage" />
            </el-form-item>
            <el-form-item label="体检类型" prop="tjlx">
              <el-select v-model="queryPackage.tjlx" placeholder="请选择" clearable style="width: 120px">
                <el-option v-for="item in tjlxOptions" :key="item.id" :label="item.text" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="searchPackage">搜索</el-button>
            </el-form-item>
          </el-form>
          <div class="flex">
            <div class="table-box">
              <el-table size="mini" ref="packageTable" border v-loading="packageLoading" :data="packageList" height="300px" style="width: 100%" @row-click="rowClick2" @selection-change="packageChange">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序列" type="index" width="65" align="center" />
                <el-table-column label="体检套餐名称" prop="tjtcmc" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="体检套餐输入码" prop="tjtcsrm" min-width="140" align="center" show-overflow-tooltip />
                <el-table-column label="体检套餐简称" prop="tjtcjc" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="套餐原始价格" prop="tcysjg" min-width="120" align="center" />
                <el-table-column label="套餐折扣" prop="tczk" min-width="120" align="center" />
                <el-table-column label="套餐折后价格" prop="zhjg" min-width="120" align="center" />
                <el-table-column label="体检类型" prop="ftjlx" min-width="120" align="center">
                  <template slot-scope="scope">
                    {{ tjlx(scope.row.ftjlx) }}
                  </template>
                </el-table-column>
                <el-table-column label="是否已婚套餐" prop="sfyhtc" min-width="120" align="center">
                  <template slot-scope="scope">
                    <span v-if="scope.row.sfyhtc == 0">是</span>
                    <span v-if="scope.row.sfyhtc == 1">否</span>
                    <span v-if="scope.row.sfyhtc == 2">通用</span>
                  </template>
                </el-table-column>
                <el-table-column label="接害因素" prop="jhys" min-width="120" align="center" show-overflow-tooltip />
                <el-table-column label="职业体检类别" prop="zytjlb" min-width="120" align="center">
                  <template slot-scope="scope">
                    <div v-for="item in zytjlbOptions" :key="item.id">
                      <span v-if="item.id == scope.row.zytjlb">{{ item.text }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="适用性别" prop="syxb" min-width="120" align="center">
                  <template slot-scope="scope">
                    <div v-for="item in xbOptions" :key="item.id">
                      <span v-if="item.id == scope.row.syxb">{{ item.text }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="年龄自" prop="nlz" min-width="120" align="center" />
                <el-table-column label="年龄至" prop="nld" min-width="120" align="center" />
              </el-table>
              <el-pagination background :pager-count="5" :current-page="queryPackage.current" hide-on-single-page :page-sizes="[10, 20, 30, 50]" :page-size="queryPackage.size" layout="sizes, prev, pager, next, jumper ,total" :total="packageTotal" @size-change="packageSizeChange" @current-change="packageCurrentChange">
              </el-pagination>
            </div>
            <div class="center-btn">
              <el-button type="primary" plain size="mini" class="el-icon-arrow-right" :disabled="!packageSelect.length" @click="addsForCombo"> </el-button>
              <el-button type="primary" plain size="mini" class="el-icon-arrow-left" :disabled="!selectIds.length" @click="removeForCombo" style="margin: 24px 0 0 !important"></el-button>
            </div>
          </div>
        </div>
        <div class="right-table">
          <el-table size="mini" border ref="selectTable" row-key="sort" height="100%" v-loading="selectLoading" :data="selectList" :row-class-name="selectRowClassName" style="width: 100%" @selection-change="selectChange" @row-dblclick="selectDblclick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="顺序" type="index" width="60" align="center" />
            <el-table-column label="备选" prop="sfbx" min-width="60" align="center">
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.sfbx" :true-label="1" :false-label="0" @change="sfbxChange" />
              </template>
            </el-table-column>
            <el-table-column label="项目名称" prop="sfxmmc" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="xb" min-width="120" align="center">
              <template slot-scope="scope">
                <div v-for="item in xbOptions" :key="item.id">
                  <span v-if="item.id == scope.row.xb">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="销售分类" prop="dysx" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="原价" prop="jg" min-width="120" align="center" />
            <el-table-column label="成本价" prop="costprice" min-width="120" align="center" v-if="isCW" />
            <el-table-column label="必检/选检" prop="sfbj" min-width="120" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.sfbj == 1">必检</span>
                <span v-if="scope.row.sfbj == 0">选检</span>
              </template>
            </el-table-column>
            <el-table-column label="优惠价" prop="yhj" min-width="120" align="center">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.yhj" size="mini" controls-position="right" :min="0" v-if="scope.row.sfbx == 1" style="width: 100%" @change="computeYh"></el-input-number>
                <span v-else>{{ scope.row.yhj }}</span>
              </template>
            </el-table-column>
            <el-table-column label="备选分组" prop="group" min-width="120" align="center">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.group" size="mini" controls-position="right" :min="0" v-if="scope.row.sfbx == 1" style="width: 100%" @change="computeYh"></el-input-number>
                <span v-else>{{ scope.row.group }}</span>
              </template>
            </el-table-column>
            <el-table-column label="组类型" prop="groupType" min-width="120" align="center">
              <template slot-scope="scope">
                <el-select v-model="scope.row.groupType" style="width: 100%" size="mini" v-if="scope.row.sfbx == 1" @change="computeYh">
                  <el-option :key="0" label="组内选" :value="0" />
                  <el-option :key="1" label="组间选" :value="1" />
                  <el-option :key="2" label="组任选" :value="2" />
                </el-select>
                <span v-else>{{ scope.row.groupType }}</span>
              </template>
            </el-table-column>
            <el-table-column label="检查意义" prop="jcyy" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="备注" prop="bz" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="体检类型" prop="tjlx" min-width="120" align="center">
              <template slot-scope="scope">
                {{ tjlx(scope.row.tjlx) }}
              </template>
            </el-table-column>
            <el-table-column label="所属科室" prop="ssks" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="收费项目输入码" prop="sfxmsrm" min-width="120" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
    </div>
    <div class="add-create-offer-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <!-- <el-button @click="cancel">取 消</el-button> -->
    </div>
    <el-dialog title="查看图片" :visible.sync="previewOpen" class="preview-image" width="800px" append-to-body>
      <el-image v-for="(item, index) in previewList" :key="index" :src="item" :preview-src-list="previewList"></el-image>
    </el-dialog>
    <el-dialog title="提醒" :visible.sync="tipsOpen" class="tips-dialog" width="600px" append-to-body>
      <div class="tips-content">
        <p v-html="tipsContent"></p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="tipsOpen = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getSfxm, getSfxmsData, getSeasonZk, getListDataAddMeal, getPpZxtcData, getValidaZkLeader, getSfbj, isJcxmReport, getCreatemeal, getItemsDataAjax, saveOrUpdate } from '@/api/sale/create_offer'
import { getfzxList } from '@/api/sale/order_customization.js'
import { getUserCid } from '@/api/system/branch.js'
import { getPaywayData } from '@/api/reception/prepare_order.js'
import { isCaiWu } from '@/api/finance/tally/bank_account.js'

import pinyin from '@/utils/pinyin.js'
import Cookies from 'js-cookie'
export default {
  name: 'Create_offer_add',
  data() {
    var onPriceValidation = (rule, value, callback) => {
      if (value < 0) {
        callback(new Error('套餐原始价格不合法'))
      } else {
        callback()
      }
    }
    var onAgeValidation = (rule, value, callback) => {
      if (value === undefined || value === null || value === '') {
        callback(new Error('年龄不能为空'));
      } else if (value < 0) {
        callback(new Error('年龄不能小于0'));
      } else {
        callback();
      }
    }
    var onZkValidation = (rule, value, callback) => {
      if (this.onValidaZkLeader) {
        callback(new Error(this.onValidaZkLeader))
      } else {
        callback()
      }
    }
    var onJhysValidation = (rule, value, callback) => {
      if ((this.form.tjlx == 1 || this.form.tjlx == 2) && !this.form.jhys) {
        callback(new Error('请选择接害因素'))
      } else {
        callback()
      }
    }
    var onZylbValidation = (rule, value, callback) => {
      if ((this.form.tjlx == 1 || this.form.tjlx == 2) && (this.form.zytjlb === undefined || this.form.zytjlb === null)) {
        callback(new Error('请选择职业类别'))
      } else {
        callback()
      }
    }
    return {
      // 单位名称
      unitData: {
        placeholder: '请输入客户单位输入码',
        key: '销售经理', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/customer/getKhdwmcData', //请求连接
        bindValue: '',
        firstName: 'xsjl', //接口返回值对应第一列的参数名
        secondName: 'khdwmc', //接口返回值对应第二列的参数名
        queryData: 'key',
      },
      // 接害因素搜索数据
      jhysData: {
        placeholder: '请选择...',
        inputTitle: '危害因素', // 搜索标题
        inputPlaceholder: '请输入输入码搜索', // 搜索placeholder
        key: '输入码',
        value: '危害因素名称',
        bindValue: '',
        url: '/sell/createmeal/getJhysDataByFzx', //请求连接
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
      },
      // 分中心
      fzxOptions: [],
      // 当前分中心
      currentFzx: '',
      // 体检类型
      tjlxOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
      // 接害因素禁用
      jhysDisable: true,
      // 职业类别禁用
      zytjlbDisable: true,
      // 职业类别
      zytjlbOptions: [
        { id: 0, text: '上岗前' },
        { id: 1, text: '在岗期间' },
        { id: 2, text: '离岗时' },
        { id: 3, text: '离岗后' },
        { id: 4, text: '应急' },
      ],
      // 付款方式
      fkfsOptions: [
        { fzx: '现金', id: '1' },
        { fzx: '统收', id: '0' },
      ],
      fkfsTemp: [],
      // 是否自选
      sfzxOptions: [
        { id: 0, text: '固定套餐' },
        { id: 1, text: '完全自选' },
        { id: 2, text: '部分自选' },
      ],
      // 性别
      xbOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      // 折扣范围判断
      onValidaZkLeader: false,
      // 查询参数-收费项目
      queryProject: {
        current: 1,
        size: 50,
        examfeeitemName: undefined,
        sfxmsrm: undefined,
      },
      // 查询参数-套餐
      queryPackage: {
        current: 1,
        size: 20,
        tjtcmc: undefined,
        tjtcsrm: undefined,
        tjlx: undefined,
      },
      // 查看图片
      previewOpen: false,
      // 预览图片
      previewList: [],
      // 总条数
      projectTotal: 0,
      packageTotal: 0,
      // 表格数据-收费项目
      projectList: [],
      // 表格数据-套餐
      packageList: [],
      // 表格数据-选中
      selectList: [],
      // 备选选中提示
      tipsAlternative: '',
      // 遮罩层
      projectLoading: true,
      packageLoading: true,
      selectLoading: false,
      // 选中数组-收费项目
      projectSelect: [],
      packageSelect: [],
      selectIds: [],
      // 表单参数
      form: {},
      // 接害因素提示文字
      jhysTips: undefined,
      // 左侧盒子高度
      leftTableHeight: 'auto',
      // 提醒弹窗文字
      tipsContent: '',
      // 是否显示提醒弹窗
      tipsOpen: false,
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      popList: [],
      // 季度最低折扣
      seasonZk: 0,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        tjlx: [{ required: true, message: '体检类型不能为空', trigger: 'change' }],
        tjtcmc: [{ required: true, message: '套餐名称不能为空', trigger: 'change' }],
        tjtcsrm: [{ required: true, message: '套餐输入码不能为空', trigger: 'change' }],
        fzxid: [{ required: true, message: '分中心不能为空', trigger: 'change' }],
        zhjg: [{ required: true, message: '套餐优惠价不能为空', trigger: 'blur' }],
        fkfs: [{ required: true, message: '付款方式不能为空', trigger: 'blur' }],
        nlz: [{ validator: onAgeValidation, trigger: 'change' }],
        nld: [{ validator: onAgeValidation, trigger: 'change' }],
        tcysjg: [{ validator: onPriceValidation, trigger: 'blur' }],
        tczk: [
          { required: true, message: '平均折扣不能为空', trigger: 'change' },
          { validator: onZkValidation, trigger: 'blur' },
        ],
        jhys: [{ validator: onJhysValidation, trigger: 'change' }],
        zytjlb: [{ validator: onZylbValidation, trigger: 'change' }],
      },
      // 是否为财务
      isCW: false,
    }
  },
  created() {
    let id = this.$route.query.id
    let title = ''
    isCaiWu().then((res) => {
      if (res.data == 'success') {
        this.isCW = true
      }
    })
    if (id) {
      title = '编辑套餐'
      this.handleUpdate(id)
    } else {
      title = '新增套餐'
      this.handleAdd()
    }
    const obj = Object.assign({}, this.$route, { title })
    this.$tab.updatePage(obj)
  },
  mounted() {
    window.onresize = () => {
      return (() => {
        if (this.open) {
          this.leftTableHeight = this.$refs.leftTable.offsetHeight
        }
      })()
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  methods: {
    // 获取分中心列表及付款方式
    getFzxList() {
      getUserCid({
        current: 1,
        size: 99999,
      }).then(({ data }) => {
        data.forEach((el) => {
          el.id = el.id.toString()
          if (el.id == Cookies.get('cid')) {
            this.form.fzx = el.fzx
            this.currentFzx = el.fzx
          }
        })
        if (!this.form.fzxid) {
          this.form.fzxid = [Cookies.get('cid').toString()]
        }
        this.fzxOptions = data

      })
      // 如果用getfzxList接口则用下面
      // getfzxList({
      //   current: 1,
      //   size: 99999,
      // }).then(({ data }) => {
      //   data.records.forEach((el) => {
      //     el.id = el.id.toString()
      //     if (el.id == Cookies.get('cid')) {
      //       this.form.fzx = el.fzx
      //       this.currentFzx = el.fzx
      //     }
      //   })
      //   this.form.fzxid = [Cookies.get('cid').toString()]
      //   this.fzxOptions = data.records

      // })
      // getPaywayData().then(({ data }) => {
      //   let array = []
      //   data.forEach((el) => {
      //     if (el.fzx == '现金' || el.fzx == '统收') {
      //       array.push(el)
      //     }
      //   })
      //   this.fkfsOptions = array
      //   this.fkfsTemp = JSON.parse(JSON.stringify(array))
      // })
    },
    // 静态查询支付方式
    // handelFilterable(val) {
    //   this.fkfsOptions = this.fkfsTemp
    //   if (val !== '') {
    //     let array = []
    //     this.fkfsOptions.forEach((el) => {
    //       if (el.inputCode.includes(pinyin(val))) {
    //         array.push(el)
    //       }
    //     })
    //     this.fkfsOptions = array
    //   }
    // },
    // blurFilt() {
    //   this.fkfsOptions = this.fkfsTemp
    // },
    // 获取季度最低折扣
    getSeasonZk() {
      getSeasonZk().then((response) => {
        this.seasonZk = response.data
      })
    },
    // 根据体检类型加载不同的类型的最小套餐
    onTjlxChanged(val) {
      if (val == 0) {
        //体检类型为健康,接害因素和职业体检类别不能填写
        this.jhysDisable = true
        if (this.form.jhys != '') {
          this.form.jhys = ''
        }
        this.form.harmName = ''
        this.zytjlbDisable = true
        if (this.form.zytjlb != '') {
          this.form.zytjlb = ''
        }
      } else {
        //体检类型为职业和综合,取消控件失效
        this.jhysDisable = false
        this.zytjlbDisable = false
      }
      this.queryPackage.tjlx = this.form.tjlx
      this.getProject()
      this.getPackage()
    },
    // 根据性别加载不同收费项目
    onValueChanged() {
      //性别不同显示收费项目也不同
      this.getProject()
    },
    // 根据危害因素和职业体检类别确定最小套餐,并将项目加载到右边
    ppzxtc(val) {

      if (this.form.sfzx == 1) return
      // this.selectList = []
      //获取接害因素数据
      var jhysValue = this.form.jhys
      //获取职业体检类别的数据
      var zytjlbValue = val
      if (jhysValue) {
        var query = {
          jhId: jhysValue.split(','),
          zyValue: zytjlbValue,
          flag: 0,
        }
        query.flag = 1
        this.getPpZxtcData(query)
      }
    },
    // 根据接害因素和职业体检类别获取关联的收费项目
    getPpZxtcData(query) {
      getPpZxtcData(query).then((res) => {
        //回调
        // 24-08-27 不管有无数据都会弹窗
        // if (res.data.length > 0) {
        this.$modal
          .confirm('是否加入最小套餐中的选检项目?', '选择')
          .then(() => {
            //判断已选项目是否已经存在收费项目,存在收费项目判断是否有重复的,存在重复的不添加
            var rows = this.selectList
            var data = res.data
            //获取页面原先的收费项目id,存入数组
            var ys = []
            //遍历获取页面原有的收费项目id
            for (var i = 0; i < rows.length; i++) {
              ys.push(rows[i].id)
            }
            //获取要添加的收费项目id
            var hq = []
            for (var i = 0; i < data.length; i++) {
              hq.push(data[i].id)
            }
            //比较两个for循环中是否有重复的收费项目,有重复的不添加
            var r = []
            for (var i = 0; i < hq.length; i++) {
              let mou = 0
              if (ys.includes(hq[i])) {
                mou = 1
              }
              if (mou == 0) {
                var newRow = {
                  id: data[i].id,
                  sfxmmc: data[i].examfeeitemName,
                  sfxmsrm: data[i].sfxmsrm,
                }
                // if (data[i].xb == '0') {
                //   newRow.xb = '男'
                // } else if (data[i].xb == '1') {
                //   newRow.xb = '女'
                // } else if (data[i].xb == '2') {
                //   newRow.xb = '通用'
                // }
                newRow.xb = data[i].xb
                newRow.jcyy = data[i].jcyy
                newRow.jg = data[i].unitprice
                newRow.bz = data[i].bz
                // if (data[i].tjlx == '0') {
                //   newRow.tjlx = '健康'
                // } else if (data[i].tjlx == '1') {
                //   newRow.tjlx = '职业'
                // } else if (data[i].tjlx == '2') {
                //   newRow.tjlx = '综合'
                // }
                newRow.tjlx = data[i].tjlx
                newRow.ssks = data[i].departName
                newRow.sfbx = 0
                newRow.sfbj = data[i].sfbj
                newRow.fDiscountdisabled = data[i].fdiscountdisabled
                newRow.costprice = data[i].costprice
                r.push(newRow)
              }
            }
            this.addItems(r)
            this.computeYh()
          })
          .catch(() => {
            // this.getPpZxtcData(query)
          })
        // }
      })
    },
    // 选择是否自选
    onsfzxchange(val) {
      if (val == 1) {
        this.selectList = []
        this.form.tcysjg = null
      }
    },
    // 平均折扣验证
    onZkchange() {
      this.computeYh()
      if (!isNaN(this.form.tczk) && this.form.tczk !== '') {
        getValidaZkLeader({ discount: this.form.tczk }).then((res) => {
          // if (res.data != 'error') {
          //   this.onValidaZkLeader = '超出折扣范围！' + res.data.replace("<font color='red'>", '').replace('</font>', '')
          //   this.$refs.form.validateField('tczk')
          //   this.$alert('超出折扣范围！' + res.data, '提醒', {
          //     confirmButtonText: '确定',
          //     type: 'warning',
          //     dangerouslyUseHTMLString: true,
          //   })
          // } else {
          this.onValidaZkLeader = false
          this.$refs.form.validateField('tczk')
          // }
        })
      }
    },
    // 先将优惠价设定好再去改体检项目，改完项目后套餐原价和折扣依然自动显示，防止最后忘记变动价格
    // 所有计算优惠价、折扣方法
    computeYh() {
      var tip = this.checkGroup()
      if (tip != 'success') {
        this.tipsAlternative = tip
        return
      } else {
        this.tipsAlternative = ''
      }
      var tczk = this.form.tczk //折扣
      var zhjg = this.form.zhjg //优惠价

      var totalCostprice = 0.0 //成本价合计

      var gridData = this.selectList

      var fbxyj = 0 //所有非备选项目原价
      var zwxyj = 0 //所有组外选原价
      var zwxgroups = {} //所有组外选分组
      var kxsl = this.form.kxsl //可选组数
      var znxgroups = {} //所有组内选分组  group:{yjhj原价合计,xms项目数}

      for (var i = 0; i < gridData.length; i++) {
        var row = gridData[i]
        if (row.sfbx == 1) {
          if (row.groupType == 1) {
            zwxyj += Number(row.jg)
            zwxgroups[row.group] = 1
          } else if (row.groupType === 0) {
            var znxgroup = znxgroups[row.group]
            if (znxgroup) {
              znxgroup.xms++
              znxgroup.yjhj += Number(row.jg)
            } else {
              znxgroups[row.group] = {
                xms: 1,
                yjhj: Number(row.jg),
              }
            }
          }
        } else {
          fbxyj += Number(row.jg)
        }

        if (row.costprice) {
          totalCostprice += row.costprice
        }
        this.form.totalCostprice = totalCostprice.toFixed(2)
      }

      var zxwgs = Object.keys(zwxgroups).length //组外选组数
      //平均折扣=优惠价/(非备选项目原价+（组内1组原价合计/组内1组项目数+组内2组...）+所有组外项目原价*可选组数/所有组数)
      var zn = 0 //（组内1组原价合计/组内1组项目数+组内2组...）
      for (var key in znxgroups) {
        var znx = znxgroups[key]
        zn += znx['yjhj'] / znx['xms']
      }
      var tcysjg = fbxyj + zn + (zxwgs == 0 ? 0 : (zwxyj * Number(kxsl || 0)) / zxwgs)

      // 确保 tcysjg 是有效的数字，避免 NaN
      if (isNaN(tcysjg)) {
        tcysjg = 0
      }

      // 无论折扣和优惠价是否录入，都要正确设置 tcysjg
      this.form.tcysjg = tcysjg.toFixed(1)

      if (tczk === '' && zhjg === '') {
        //如果折扣、优惠价都没有录入，仅返回
        return
      } else {
        if (zhjg !== '') {
          if (tcysjg > 0) { // 避免除以0的情况
            tczk = (Number(zhjg) * 10) / tcysjg
            this.form.tczk = tczk.toFixed(1)
          }
        } else {
          if (!isNaN(Number(tczk))) { // 确保 tczk 是有效数字
            zhjg = (tcysjg * Number(tczk)) / 10
            this.form.zhjg = zhjg.toFixed(1)
          }
        }
      }
    },
    // 检查备选信息是否正确录入
    checkGroup() {
      var groupPrice = this.form.groupPrice
      var bxrows = []
      for (var i in this.selectList) {
        if (this.selectList[i].sfbx == 1) {
          bxrows.push(this.selectList[i])
        }
      }
      var map = {} //组间选
      var znx = {} //组内选只能选一个，组内所有项目价格应相同（为体检者收费项目分配价格）。
      var fz = {} //key:组 value:分组类型
      var fzdata = ['【组内选】', '【组间选】', '【组任选】']
      for (var i in bxrows) {
        var row = bxrows[i]
        var group = row.group
        if (!group && group !== 0) {
          return '备选项目必须录入备选分组'
        }
        var price = row.yhj
        if (!price && price !== 0) {
          return '备选项目必须录入优惠价'
        }
        if (row.groupType == 1) {
          if (map[group]) {
            map[group] = map[group] + price
          } else {
            map[group] = price
          }
        } else if (row.groupType === 0 || row.groupType === '0') {
          if (znx[group]) {
            if (znx[group] != price) {
              return '【组内选】分组内所有收费项目的优惠价必须相同'
            }
          } else {
            znx[group] = price
          }
        } else if (!row.groupType) {
          return '备选项目必须选择组类型'
        }
        var oldType = fz[group]
        if (!oldType && oldType !== 0) {
          fz[group] = row.groupType
        } else if (oldType != row.groupType) {
          return fzdata[row.groupType] + '和' + fzdata[oldType] + '中不能存在相同的组名：' + group
        }
      }
      var groups = Object.keys(map).length //组间选分组数
      if (groups && !groupPrice && groupPrice !== 0) {
        return "有【<font color='red'>组间选</font>】类型的备选项目时,必须录入【<font color='red'>备选组价格</font>】！"
      }
      for (var key in map) {
        if (map[key] != groupPrice) {
          return '备选分组' + key + '的项目总优惠价不等于备选组价格'
        }
      }
      var kxsl = this.form.kxsl
      if (!isNaN(kxsl)) {
        if ((kxsl > 0 || kxsl < 0) && groups == 0) {
          return "没有【<font color='red'>组间选</font>】类型的备选项目,【<font color='red'>可选数量</font>】应该为0！"
        }
        if (kxsl == 0 && groups != 0) {
          return "有【<font color='red'>组间选</font>】类型的备选项目时,【<font color='red'>可选数量</font>】不能为0！"
        }
        if (kxsl > groups) {
          return "【<font color='red'>备选组数量</font>】不能大于【<font color='red'>组间选</font>】类型的分组数！"
        }
      } else if (groups > 0) {
        return "有【<font color='red'>组间选</font>】类型的备选项目时,必须录入【<font color='red'>备选组数量</font>】！"
      }
      return 'success'
    },
    // 体检类型
    tjlx(value) {
      if (value == 0) {
        return '健康体检'
      } else if (value == 1) {
        return '职业体检'
      } else if (value == 2) {
        return '综合'
      }
    },
    // 年龄自变化
    nldChange(val) {
      if (val < this.form.nlz) {
        this.form.nlz = val
      }
    },
    // 单位名称选择
    unitChange(value) {
      this.form.khdwmcid = value.id
      this.form.khdwmc = value.khdwmc
      this.unitData.bindValue = value.khdwmc
    },
    // 接害因素选择
    jhysChange(value) {
      this.form.zytjlb = null
      // this.selectList = []
      this.form.jhys = ''
      this.form.harmName = ''
      value.forEach((el) => {
        if (!this.form.harmName) {
          this.form.jhys = el.id
          this.form.harmName = el.label
          this.jhysTips = el.label
        } else {
          this.form.jhys += ',' + el.id
          this.form.harmName += ',' + el.label
          this.jhysTips += ',' + el.label
        }
      })
      this.selectList = []
      this.getProject()
      this.getPackage()
    },
    // 分中心选择
    fzxChange(value) {
      var fzx = []
      this.fzxOptions.forEach((el) => {
        if (value.indexOf(el.id) > -1) {
          fzx.push(el.fzx)
        }
      })
      this.form.fzx = fzx.join(',')
    },
    // 查看图片
    handleViewImg(value) {
      if (value) {
        this.previewOpen = true
        this.previewList[0] = value
      } else {
        this.$alert('该收费项目的检查仪器图片还未上传,请及时上传图片再查看！', '提示')
      }
    },
    // 添加
    handleAdd() {
      this.getFzxList()
      this.getSeasonZk()
      this.popList = []
      this.popData = undefined
      this.title = '添加套餐'
      this.reset()
      this.open = true
      this.queryPackage.tjlx = this.form.tjlx
      this.$nextTick(() => {
        this.leftTableHeight = this.$refs.leftTable.offsetHeight
      })
    },
    // 编辑
    handleUpdate(id) {
      this.getFzxList()
      this.getSeasonZk()
      this.popList = []
      this.popData = undefined
      this.title = '编辑套餐'
      this.reset()
      this.open = true
      this.$nextTick(() => {
        this.leftTableHeight = this.$refs.leftTable.offsetHeight
      })
      this.popLoading = true
      getCreatemeal(id).then((response) => {
        response.data.fzxid = response.data.fzxid.split(',')
        response.data.fzx = response.data.fzxName
        this.popLoading = false
        this.jhysTips = response.data.jhysName
        if (response.data.jhysName) {
          response.data.jhysName = response.data.jhysName.split(',')
        }
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.form = response.data
        this.unitData.bindValue = response.data.khdwmc
        if (this.form.tjlx == 0) {
          this.jhysDisable = true
          this.zytjlbDisable = true
        } else {
          this.jhysDisable = false
          this.zytjlbDisable = false
        }
      })
      this.selectLoading = true
      getSfxmsData(id).then((response) => {
        for (var i = 0; i < response.data.length; i++) {
          response.data[i].showColor = 1
        }
        this.popList = JSON.parse(JSON.stringify(response.data || []))
        this.selectLoading = false
        this.selectList = response.data || []
      })
    },
    // 表单重置
    reset() {
      this.projectList = []
      this.packageList = []
      this.selectList = JSON.parse(JSON.stringify(this.popList))
      this.projectSelect = []
      this.packageSelect = []
      this.selectIds = []
      this.total = 0
      if (this.title == '添加套餐') {
        this.form = {
          tjtcmc: undefined,
          tjtcsrm: undefined,
          khdwmcid: undefined,
          khdwmc: undefined,
          syxb: 2,
          sfyhtc: '2',
          nlz: 0,
          nld: 100,
          zytjlb: undefined,
          jhys: undefined,
          jhysName: undefined,
          harmName: undefined,
          zhjg: undefined,
          tczk: undefined,
          fkfs: undefined,
          fzxid: [Cookies.get('cid').toString()],
          fzx: this.currentFzx,
          sfwc: 0,
          tcysjg: undefined,
          totalCostprice: undefined,
          pinganId: undefined,
          sfzx: 0,
          zxzk: undefined,
          kxsl: 0,
          groupPrice: 0,
          zhjgapp: undefined,
          isBig: 0,
        }
        this.zytjlbDisable = true
        this.jhysDisable = true
      } else if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.jhysTips = this.form.jhysName.join(',')
        this.unitData.bindValue = this.form.khdwmc
        this.searchProject()
        this.searchPackage()
        if (this.form.tjlx) {
          this.zytjlbDisable = false
          this.jhysDisable = false
        } else {
          this.zytjlbDisable = true
          this.jhysDisable = true
        }
        return
      }

      this.unitData.bindValue = undefined
      this.tipsAlternative = ''
      this.resetForm('form')
      this.resetForm('queryProject')
      this.resetForm('queryPackage')
      this.searchProject()
      this.searchPackage()
    },
    // 套餐名称改变
    nameChange(value) {
      this.form.tjtcsrm = pinyin(value)
    },
    // 搜索-收费项目
    searchProject() {
      this.queryProject.current = 1
      this.getProject()
    },
    // 搜索-套餐
    searchPackage() {
      this.queryPackage.current = 1
      this.getPackage()
    },
    // 刷新
    handleRefresh() {
      if (this.form.tcysjg || this.form.sfzx) {
        //编辑
        this.selectList = JSON.parse(JSON.stringify(this.popList))
        this.computeYh()
      } else {
        //新增
        this.selectList = []
        //设置套餐原始价格为0
        this.form.tcysjg = null
        this.form.zhjg = null
        this.form.zhjgapp = null
        this.form.totalCostprice = null
      }
    },
    // 查询列表-收费项目
    getProject() {
      var queryParams = this.queryProject
      if (this.form.isBig == '1') {
        queryParams.syxb = undefined
      } else {
        queryParams.syxb = this.form.syxb
      }
      queryParams.syxb = this.form.syxb
      queryParams.tjlx = this.form.tjlx
      queryParams.fzxid = this.form.fzxid
      this.projectLoading = true
      getSfxm(queryParams).then((response) => {
        this.projectList = response.data.records
        this.projectTotal = response.data.total
        this.projectLoading = false
      })
    },
    // 查询列表-套餐
    getPackage() {
      var queryParams = this.queryPackage
      queryParams.cid = this.form.fzxid
      this.packageLoading = true
      getListDataAddMeal(queryParams).then((response) => {
        this.packageList = response.data.records
        this.packageTotal = response.data.total
        this.packageLoading = false
      })
    },
    // 分页条数发生改变
    projectSizeChange(val) {
      if (this.queryProject.current * val > this.projectTotal) {
        this.queryProject.current = 1
      }
      this.queryProject.size = val
      this.getProject()
    },
    packageSizeChange(val) {
      if (this.queryPackage.current * val > this.packageTotal) {
        this.queryPackage.current = 1
      }
      this.queryPackage.size = val
      this.getPackage()
    },
    // 分页页码发生改变
    projectCurrentChange(val) {
      this.queryProject.current = val
      this.getProject()
    },
    packageCurrentChange(val) {
      this.queryPackage.current = val
      this.getPackage()
    },
    // 移入选项-收费项目
    selectOption() {
      if (this.form.sfzx == 1) {
        this.$modal.alertWarning('自选套餐不能加收费项目', '提醒')
        return
      }
      var rows = this.projectSelect
      if (rows.length == 0) {
        this.$modal.alertWarning('请先选择一条或多条收费项目！', '提醒')
        return
      }
      //判断是否添加了重复的收费项目
      var text = this.validaSfxmRepeat(this.selectList, rows)
      if (text[0]) {
        this.tipsContent = text[1]
        this.tipsOpen = true
        return
      }
      for (var i in this.projectSelect) {
        let index = this.projectList.findIndex((item) => {
          if (this.projectSelect[i].id == item.id) {
            return true
          }
        })
        this.$delete(this.projectList, index)
      }
      var jhyss = this.form.jhys
      var zytjlb = this.form.zytjlb
      if (jhyss && zytjlb) {
        var itemIds = []
        for (var m = 0, lm = rows.length; m < lm; m++) {
          itemIds.push(rows[m]['id'])
        }
        var query = {
          itemsIds: itemIds,
          jhId: jhyss.split(','),
          zyValue: zytjlb,
        }
        getSfbj(query).then((res) => {
          if (res.data.length) {
            var sfbjs = res.data
            for (var m = 0, lm = rows.length; m < lm; m++) {
              rows[m]['sfbj'] = sfbjs[m]
            }
          }

          //对于添加的收费项目,存在重复的检查项目给予提示
          this.isJcxmReport(this.selectList, rows)
          //操作套餐价格变换
          // var message = this.selectList;
          // var num = 0;
          // for (var i = 0; i < message.length; i++) {
          //   var jg = message[i].jg;
          //   if (typeof message[i].jg != "number") {
          //     jg = jg.replace("<font color='red'>", "").replace("</font>", "");
          //   }
          //   jg = (Number)(jg);
          //   this.form.tcysjg = num+=jg*1
          // }
          this.addItems(rows)
          this.queryProject.examfeeitemName = undefined
          this.queryProject.sfxmsrm = undefined
          this.$refs.sfxmsrmInput.focus()
          this.computeYh()
        })
      } else {
        //对于添加的收费项目,存在重复的检查项目给予提示
        this.isJcxmReport(this.selectList, rows)

        //操作套餐价格变换
        // var message = this.selectList;
        // var num = 0;
        // for (var i = 0; i < message.length; i++) {
        //   var jg = message[i].jg;
        //   if (typeof message[i].jg != "number") {
        //     jg = jg.replace("<font color='red'>", "").replace("</font>", "");
        //   }
        //   jg = (Number)(jg);
        //   this.form.tcysjg = num+=jg*1
        // }
        this.addItems(rows)
        this.queryProject.examfeeitemName = undefined
        this.queryProject.sfxmsrm = undefined
        this.$refs.sfxmsrmInput.focus()
        this.computeYh()
      }
    },
    // 移出选项-收费项目
    removeOption() {
      for (var i in this.selectIds) {
        let projectIndex = this.projectList.findIndex((item) => {
          if (this.selectIds[i] == item.id) {
            return true
          }
        })
        let index = this.selectList.findIndex((item) => {
          if (this.selectIds[i] == item.id) {
            return true
          }
        })
        if (projectIndex > -1) {
          this.$delete(this.selectList, index)
          continue
        }
        this.projectList.push(this.selectList[index])
        this.$delete(this.selectList, index)
      }
      this.computeYh()
    },
    // 移入选项-最小套餐
    addsForCombo() {
      if (this.form.sfzx == 1) {
        this.$modal.alertWarning('自选套餐不能加收费项目', '提醒')
        return
      }
      var mrows = this.packageSelect
      if (mrows.length == 0) {
        this.$modal.alertWarning('请先选择一条或多条最小套餐！', '提醒')
        return
      }
      var ids = ''
      for (var i = 0; i < mrows.length; i++) {
        ids += mrows[i]['id'] + ','
      }
      ids = ids.substring(0, ids.length - 1)
      getItemsDataAjax({ ids: ids })
        .then((res) => {
          if (!res.data) {
            return
          }
          var rows = res.data
          if (rows.length == 0) {
            return
          }
          rows = this.validaSfxmRepeat2(this.selectList, rows)
          var jhyss = this.form.jhys
          var selected = this.form.zytjlb
          if (jhyss && selected) {
            var zytjlb = selected.id
            var itemIds = []
            for (var m = 0, lm = rows.length; m < lm; m++) {
              itemIds.push(rows[m]['id'])
            }
            var query = {
              itemsIds: itemIds,
              jhId: jhyss.split(','),
              zyValue: zytjlb,
            }
            getSfbj(query).then((res) => {
              if (res.data.length) {
                var sfbjs = res.data
                for (var m = 0, lm = rows.length; m < lm; m++) {
                  rows[m]['sfbj'] = sfbjs[m]
                }
                this.addItems(rows)
                //对于添加的收费项目,存在重复的检查项目给予提示
                this.isJcxmReport(this.selectList, rows)
                //操作套餐价格变换
                // var message = this.selectList;
                // var num = 0;
                // for(var i=0;i<message.length;i++){
                //   this.form.tcysjg = num+=(message[i].jg)*1
                // }
                this.computeYh()
              }
            })
          } else {
            this.addItems(rows)
            //对于添加的收费项目,存在重复的检查项目给予提示
            this.isJcxmReport(this.selectList, rows)
            //操作套餐价格变换
            // var message = this.selectList;
            // var num = 0;
            // for(var i=0;i<message.length;i++){
            //   this.form.tcysjg = num+=(message[i].jg)*1
            // }
            this.computeYh()
          }
        })
        .catch(() => { })
    },
    // 移出选项-最小套餐
    removeForCombo() {
      var rows = this.selectIds
      if (rows.length > 0) {
        for (var i in this.selectIds) {
          let index = this.selectList.findIndex((item) => {
            if (this.selectIds[i] == item.id) {
              return true
            }
          })
          this.$delete(this.selectList, index)
        }
        this.computeYh()
      }
    },
    // 双击移入
    projectDblclick(row) {
      var isSelect = false
      this.projectSelect.forEach((el) => {
        if (el.id == row.id) {
          isSelect = true
          this.selectOption()
        }
      })
      if (isSelect) return
      this.$refs.projectTable.clearSelection()
      this.$refs.projectTable.toggleRowSelection(row)
      this.projectSelect = [row]
      this.selectOption()
    },
    // 双击移出
    selectDblclick(row, col) {
      if (col && (col.label == '备选' || (row.sfbx == 1 && (col.label == '优惠价' || col.label == '备选分组' || col.label == '组类型')))) {
        return
      }
      var isSelect = false
      this.selectIds.forEach((el) => {
        if (el == row.id) {
          isSelect = true
          this.removeOption()
        }
      })
      if (isSelect) return
      this.$refs.selectTable.clearSelection()
      this.$refs.selectTable.toggleRowSelection(row)
      this.selectIds = [row.id]
      this.removeOption()
    },
    // 判断添加的收费项目是否重复
    validaSfxmRepeat(grid, rows) {
      var text = ''
      var state = false
      //判断grid是否已经有数据存在
      if (grid.length != 0) {
        for (var j = 0; j < grid.length; j++) {
          for (var i = 0; i < rows.length; i++) {
            //判断是否存在相同的收费项目id
            if (rows[i].id == grid[j].id) {
              text = text + "<font color='red'>★</font>不能添加重复的收费项目【<font color='red'>" + rows[i].sfxmmc + '</font>】<br>'
              state = true
            }
          }
        }
      }
      return [state, text]
    },
    validaSfxmRepeat2(grid, rows) {
      //判断grid是否已经有数据存在
      var newrows
      if (grid.length != 0) {
        newrows = []
        for (var i = 0; i < rows.length; i++) {
          var f = true
          for (var j = 0; j < grid.length; j++) {
            //判断是否存在相同的收费项目id
            if (rows[i].id == grid[j].id) {
              f = false
              break
            }
          }
          if (f) {
            newrows.push(rows[i])
          }
        }
      } else {
        newrows = rows
      }
      return newrows
    },
    // 左侧表格添加数据
    addItems(rows) {
      var data = this.selectList
      var index = 1
      for (var i = 0; i < data.length; i++) {
        if (data[i].sort && Number(data[i].sort) >= index) {
          index = Number(data[i].sort) + 1
        }
      }
      for (var i = 0; i < rows.length; i++) {
        rows[i].sort = index++
        rows[i].showColor = 1
      }
      this.selectList = [...this.selectList, ...rows]
    },
    // 对于添加的收费项目,存在重复的检查项目给予提示
    isJcxmReport(grid, rows) {
      if (this.form.isBig == '1') return ''
      //获取已经添加上的收费项目的id
      var gridId = ''
      //存储选择行的收费项目id
      var rowsId = ''
      //分别拼接id
      for (var i = 0; i < grid.length; i++) {
        //拼接字符串
        gridId += grid[i].id + ','
      }
      //截取字符串
      gridId = gridId.substring(0, gridId.length - 1)
      for (var i = 0; i < rows.length; i++) {
        //拼接字符串
        rowsId += rows[i].id + ','
      }
      //截取字符串
      rowsId = rowsId.substring(0, rowsId.length - 1)
      var ids = rowsId + ',' + gridId
      if (!gridId) ids = ids.substring(0, ids.length - 1)
      //对于添加的收费项目,存在重复的检查项目给予提示
      isJcxmReport({ gridId: ids })
        .then((res) => {
          //返回提示信息
          if (res.data) {
            this.tipsContent = res.data
            this.tipsOpen = true
          }
        })
        .catch(() => { })
    },
    // 更换表格颜色
    projectRowClassName({ row }) {
      if (row.showColor == 1) {
        return row.isRed == 1 ? 'warning-row-red' : 'warning-row'
      }
      if (row.isRed == 1) {
        return 'row-red'
      }
      return ''
    },
    // 当前行颜色判断
    selectRowClassName({ row }) {
      if (row.showColor == 1) {
        if (this.form.syxb == 0 && row.xb == 1) return row.isRed == 1 ? 'danger-row-red' : 'danger-row'
        if (this.form.syxb == 1 && row.xb == 0) return row.isRed == 1 ? 'danger-row-red' : 'danger-row'
        // if (this.form.syxb == 2 && row.xb != 2) return row.isRed == 1 ? 'danger-row-red' : 'danger-row'
        if (this.form.tjlx == 0 && row.tjlx == 1) return row.isRed == 1 ? 'danger-row-red' : 'danger-row'
        if (this.form.tjlx == 1 && row.tjlx == 0) return row.isRed == 1 ? 'danger-row-red' : 'danger-row'
        return row.isRed == 1 ? 'warning-row-red' : 'warning-row'
      }
      if (row.isRed == 1) {
        return 'row-red'
      }
      if (row) return ''
    },
    // 多选框选中数据-收费项目
    projectChange(selection) {
      this.projectSelect = selection
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.projectTable.clearSelection()
      this.$refs.projectTable.toggleRowSelection(row)
    },
    // 多选框选中数据-套餐
    packageChange(selection) {
      this.packageSelect = selection
    },
    // 表格单击事件
    rowClick2(row) {
      this.$refs.packageTable.clearSelection()
      this.$refs.packageTable.toggleRowSelection(row)
    },
    // 多选框选中数据-选中
    selectChange(selection) {
      this.selectIds = selection.map((item) => item.id)
    },
    // 备选改变
    sfbxChange() {
      this.computeYh()
      var tips = this.checkGroup()
      if (tips != 'success') {
        this.tipsAlternative = tips
      } else {
        this.tipsAlternative = ''
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          //拼接要保存的收费项目id
          var gridId = ''
          //获取表单
          //判断是否添加了收费项目
          if (this.form.sfzx != 1 && this.selectList.length == 0) {
            //提示添加收费项目
            this.$modal.alertWarning('请添加收费项目！', '提醒')
            return false
          }
          //判断备选信息是否正确
          var checkstr = this.checkGroup()
          if (checkstr != 'success') {
            this.tipsAlternative = checkstr
            return
          } else {
            this.tipsAlternative = ''
          }
          //获取表单数据
          var data = this.form
          //获取选择的收费项目
          var sfxm = this.selectList
          //对于存在相同检查项目的套餐不能保存
          //分别拼接id
          for (var i = 0; i < sfxm.length; i++) {
            //拼接字符串
            gridId += sfxm[i].id + ','
          }
          gridId = gridId.substring(0, gridId.length - 1)
          //获取添加的收费项目数据
          var gridData = this.selectList
          //判断是否存在与适用性别不一致的收费项目(男：允许存在男、通用;女：允许存在女、通用;通用：允许存在通用的)
          var binTjlx = false
          var binXb = false
          for (var i = 0; i < gridData.length; i++) {
            //判断体检类型
            if ((this.form.tjlx == 0 && gridData[i].tjlx == 1) || (this.form.tjlx == 1 && gridData[i].tjlx != 1)) {
              binTjlx = true
            }
            if ((this.form.syxb == 0 && gridData[i].xb == 1) || (this.form.syxb == 1 && gridData[i].xb == 0) || (this.form.syxb == 2 && gridData[i].xb != 2)) {
              binXb = true
            }
          }
          if (binTjlx) {
            this.$modal.alertWarning('存在与体检类型不一致的收费项目！不能进行保存,请重新勾选！', '提醒')
            return
          }
          if (binXb && this.form.isBig != '1') {
            //大套餐不判断
            this.$modal.alertWarning('存在与适用性别不一致的收费项目！不能进行保存,请重新勾选！', '提醒')
            return
          }
          var tczk = Number(this.form.tczk)
          if (this.seasonZk !== '' && tczk < Number(this.seasonZk)) {
            this.$alert(`该套餐折扣低于本季度要求最低折扣<font color='red'>${this.seasonZk}折</font>，将无法加入到订单中,确定要保存吗？`, '提醒', {
              type: 'warning',
              showCancelButton: true,
              dangerouslyUseHTMLString: true,
            })
              .then(() => {
                this.saveData(gridId, data, sfxm)
              })
              .catch(() => { })
          } else {
            this.$modal
              .confirm('确认要保存吗？', '确认')
              .then(() => {
                this.saveData(gridId, data, sfxm)
              })
              .catch(() => { })
          }
        }
      })
    },
    saveData(gridId, data, sfxm) {
      var isBig = this.form.isBig
      //大套餐不验证重复项目
      if (isBig == '1') {
        this.saveData2(data, sfxm)
      } else {
        isJcxmReport({ gridId })
          .then((res) => {
            if (res.data == '') {
              this.saveData2(data, sfxm)
            } else {
              this.tipsContent = res.data
              this.tipsOpen = true
            }
          })
          .catch(() => { })
      }
    },
    saveData2(data, sfxm) {
      data.fzxid = data.fzxid.join(',')

      var formData = {
        json: data,
        jsonSfxm: sfxm,
      }
      saveOrUpdate(formData).then((res) => {
        this.$modal.msgSuccess('保存成功！')

        // this.$emit('getList', this.form.id)
        // this.$confirm('是否前往订单制定?', '提示')
        //   .then(() => {
        //     this.$router.push({ name: 'Order_customization' })
        //   })
        //   .catch(() => {
        //   })
        this.$tab.refreshPage()
        data.fzxid = data.fzxid.split(',')

      })
        .catch((res) => {
          data.fzxid = data.fzxid.split(',')

        })
    },
  },
}
</script>
<style lang="scss" scoped>
.add-create-offer {
  overflow-y: auto;

  .add-create-offer-footer {
    position: fixed;
    bottom: 8px;
    z-index: 2;
    width: 100%;
    text-align: center;
    background-color: #fff;
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #ebeef5;
    min-width: 1342px;
    padding-bottom: 30px;

    .el-form {
      padding: 6px 16px 0;

      .el-form-item {
        margin-bottom: 16px;
      }
    }

    .el-table__empty-block {
      width: 100% !important;
      min-height: 120px;
    }

    .el-table__body-wrapper {
      min-height: 120px;
    }

    .el-pagination {
      padding: 16px 6px 24px;
    }

    .flex {
      display: flex;
    }

    .table-box {
      width: calc(100% - 82px);
    }

    .left-table {
      width: calc(50% + 41px);
      margin: -1px;

      .flex {
        border: 1px solid #ebeef5;

        .table-box {
          margin: -1px;
        }
      }
    }

    .center-btn {
      width: 82px;
      background: #fff;
      height: auto;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-left: 1px solid #ebeef5;

      .el-button {
        padding: 0;
        width: 40px;
        height: 40px;
        font-size: 20px;
        border-radius: 5px;
      }
    }

    .right-table {
      width: calc(50% - 41px);
      flex: 1;
      margin: -1px;

      .el-table .cell {
        line-height: 28px;
      }

      .el-table--mini .el-table__cell {
        padding: 3px 0;
      }
    }

    .warning-row {
      background: #fefee9;
    }

    .warning-row-red {
      background: #fefee9;
      color: red;
    }

    .danger-row {
      background: #ff0000;
    }

    .danger-row-red {
      background: #ff0000;
      color: red;
    }

    .row-red {
      color: red;
    }
  }

  .footer {
    text-align: center;
  }
}

.preview-image {
  .el-dialog__body {
    padding: 0;
    margin: 20px;
  }
}

.tips-dialog .el-dialog__body {
  max-height: 600px;
}

.branch-select .el-select__tags {
  flex-wrap: nowrap;
  overflow: hidden;
}
</style>
