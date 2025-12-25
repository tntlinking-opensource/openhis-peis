<!-- 收费项目设置添加  开发人：麦沃德科技半夏/清风 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-charge" width="95%" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="140px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="收费项目名称" prop="examfeeitemName">
        <el-input v-model="form.examfeeitemName" placeholder="请输入收费项目名称" clearable style="width: 220px" @input="nameChange" />
      </el-form-item>
      <el-form-item label="收费项目输入码" prop="sfxmsrm">
        <el-input v-model="form.sfxmsrm" placeholder="输入项目名称后自动生成" readonly clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="是否启用" prop="sysmanmark">
        <el-select v-model="form.sysmanmark" placeholder="请选择是否启用" style="width: 220px">
          <el-option :key="1" label="是" value="1" />
          <el-option :key="0" label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="项目打印名称" prop="examfeeitemNameprn">
        <el-input v-model="form.examfeeitemNameprn" placeholder="请输入项目打印名称" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="隐私项目" prop="bgdybs">
        <el-checkbox v-model="form.bgdybs" style="width: 220px" true-label="1" false-label="0" border />
      </el-form-item>
      <el-form-item label="导引单分组" prop="dydfz">
        <el-input v-model="form.dydfz" placeholder="请输入导引单分组" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="打印排列序号" prop="xh">
        <el-input v-model="form.xh" placeholder="请输入打印排列序号" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="导引单打印标示" prop="dyddybs">
        <el-select v-model="form.dyddybs" placeholder="请选择导引单打印标示" style="width: 220px">
          <el-option :key="1" label="是" value="1" />
          <el-option :key="0" label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属科室名称" prop="idDepart">
        <input-select :selectData="departData" selectWidth="220" :initialValue="form.departName" @change="departChange"></input-select>
      </el-form-item>
      <el-form-item label="外送机构" prop="idCooporg">
        <input-select :selectData="coopData" selectWidth="220" :initialValue="form.wsjgName" @change="coopChange"></input-select>
      </el-form-item>
      <el-form-item label="价格" prop="unitprice">
        <el-input-number v-model="form.unitprice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="折扣(%)" prop="zk">
        <el-input-number v-model="form.zk" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="套餐价格" prop="suiteprice">
        <el-input-number v-model="form.suiteprice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="最低销售价" prop="specialPrice">
        <el-input-number v-model="form.specialPrice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="试管颜色" prop="xybdm">
        <el-input v-model="form.xybdm" placeholder="请输入试管颜色" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="外宾价格" prop="wbjg">
        <el-input-number v-model="form.wbjg" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="优待价格" prop="ydjg">
        <el-input-number v-model="form.ydjg" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="内部价" prop="nbj">
        <el-input-number v-model="form.nbj" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="耗材价格" prop="materialprice">
        <el-input-number v-model="form.materialprice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="成本价" prop="costprice">
        <el-input-number v-model="form.costprice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="市场价" prop="coopprice">
        <el-input-number v-model="form.coopprice" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="标本类型" prop="idLabtype">
        <input-select :selectData="labTypeData" :initialValue="form.idLabtype" selectWidth="220" @change="labTypeChange"></input-select>
      </el-form-item>
      <el-form-item label="标本种类" prop="specimenTypeName">
        <input-select :selectData="labTypeData2" :initialValue="form.specimenTypeName" selectWidth="220" @change="labTypeChange2"></input-select>
      </el-form-item>
      <el-form-item label="独立标示" prop="dlbs">
        <el-select v-model="form.dlbs" placeholder="请选择独立标示" style="width: 220px">
          <el-option :key="1" label="是" value="1" />
          <el-option :key="0" label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="费用类型" prop="fylx">
        <input-select :selectData="fylxData" :initialValue="form.fylxName" selectWidth="220" @change="fylxChange"></input-select>
      </el-form-item>
      <el-form-item label="餐序" prop="cx">
        <el-select v-model="form.cx" placeholder="请选择餐序" style="width: 220px">
          <el-option v-for="item in cxOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="销售打印分类" prop="xsdyfl">
        <input-select :selectData="xsdyflData" :initialValue="form.xsdyflName" selectWidth="220" @change="xsdyflChange"></input-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="form.tjlx" placeholder="请选择体检类型" @change="Lxchange($event)" style="width: 220px">
          <el-option v-for="item in tjlxOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-select v-model="form.xb" placeholder="请选择性别" @change="Xbchange($event)" style="width: 220px">
          <el-option v-for="item in xbOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="标示" prop="bs">
        <el-select v-model="form.bs" placeholder="请选择标示" style="width: 220px">
          <el-option v-for="item in bsOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="接口代码" prop="examfeeitemCode">
        <el-input v-model="form.examfeeitemCode" placeholder="请输入接口代码" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="检查次数" prop="jccs">
        <el-input-number v-model="form.jccs" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="禁止打折" prop="fdiscountdisabled">
        <el-select v-model="form.fdiscountdisabled" placeholder="请选择禁止打折" style="width: 220px">
          <el-option :key="1" label="是" :value="1" />
          <el-option :key="0" label="否" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="项目ID" prop="examfeeitemid">
        <el-input type="number" v-model="form.examfeeitemid" placeholder="请输入项目ID" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="是否公共" prop="isPublic">
        <el-checkbox v-model="form.isPublic" :true-label="1" :false-label="0" style="width: 220px" border @change="publicChange" />
      </el-form-item>
      <el-form-item label="分中心" prop="fzxIds">
        <el-select v-model="form.fzxIds" placeholder="请选择分中心" multiple style="width: 220px" :disabled="branchDisabled">
          <el-option v-for="item in branchOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务类别" prop="bsCatId">
        <!-- <el-select v-model="form.bsCatId" placeholder="请选择" style="width: 220px">
          <el-option v-for="item in patientType" :key="item.levelId" :label="item.levelName" :value="item.levelId" />
        </el-select> -->
        <input-select ref="bsCatId" :selectWidth="220" :selectData="typeSelectData" @change="typeSelectChange" :initialValue="form.bsCatName"></input-select>
      </el-form-item>
      <el-form-item label="在APP显示" prop="examfeeitemCodex">
        <el-checkbox v-model="form.examfeeitemCodex" true-label="1" false-label="0" style="width: 220px" border />
      </el-form-item>
      <el-form-item label="上限" prop="preUpperLimit">
        <el-input-number v-model="form.preUpperLimit" placeholder="请输入上限" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="App分类" prop="labtypeSub">
        <input-select :selectData="appTypeData" selectWidth="220" @change="appTypeChange"></input-select>
      </el-form-item>
      <el-form-item label="复查注意事项" prop="reviewMatters">
        <el-input v-model="form.reviewMatters" placeholder="请输入复查注意事项" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="温馨提示" prop="bz">
        <el-input v-model="form.bz" placeholder="请输入温馨提示" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="必选项目" prop="guidesheetcode">
        <search-select ref="guidesheetData" :selectData="searchData" :multiple="true" :selectWidth="220" :initialValue="getGuidesheetcode()" @change="searchChange"></search-select>
      </el-form-item>
      <el-form-item label="条码打印名称" prop="barcodeName">
        <el-input v-model="form.barcodeName" placeholder="请输入条码打印名称" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="条码打印次数" prop="barcodeCount">
        <el-input-number v-model="form.barcodeCount" controls-position="right" :min="1" style="width: 220px" @blur="barcodeCountBlur" />
      </el-form-item>
      <el-form-item label="条码打印分类" prop="examfeeitemClass">
        <el-select v-model="form.examfeeitemClass" placeholder="请选择条码打印分类" style="width: 220px">
          <el-option v-for="item in tmdyflOptions" :key="item.id" :label="item.className" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="检查意义" prop="jcyy">
        <el-input v-model="form.jcyy" placeholder="请输入检查意义" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="职业团检报告序号" prop="groupOrder">
        <el-input-number v-model="form.groupOrder" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="是否独立报告" prop="freportalone">
        <el-select v-model="form.freportalone" placeholder="请选择是否独立报告" style="width: 220px">
          <el-option :key="1" label="是" :value="1" />
          <el-option :key="0" label="否" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="艾迪康代码" prop="examfeeitemCode2">
        <el-input v-model="form.examfeeitemCode2" placeholder="请输入艾迪康代码" clearable style="width: 220px" />
      </el-form-item>
    </el-form>
    <div class="add-table">
      <!-- 搜索 -->
      <el-form :model="queryParams" ref="queryExam" size="small" :inline="true" label-width="110px">
        <el-form-item label="检查项目名称" prop="examName">
          <!-- examName -- examitemName -->
          <el-input v-model="queryParams.examitemName" placeholder="请输入检查项目名称" clearable style="width: 220px; margin-right: 18px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="检查项目输入码" prop="examInputCode">
          <!-- examInputCode -- inputCode -->
          <el-input v-model="queryParams.inputCode" placeholder="请输入检查项目输入码" clearable style="width: 220px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <div class="flex">
        <div class="left-table">
          <el-table border ref="examList" v-loading="loading" :data="examList" height="100%" style="width: 100%" stripe @selection-change="examChange" @row-click="rowClick">
            <el-table-column fixed type="selection" width="55" align="center" />
            <el-table-column fixed label="检查项目名称" prop="examitemName" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="检查项目类型" prop="examitemtypeName" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="fmale" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-for="item in xbOptions" :key="item.id">
                  <span v-if="item.id == scope.row.fmale">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="类型" prop="type" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-for="item in tjlxOptions" :key="item.id">
                  <span v-if="item.id == scope.row.type">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="科室名称" prop="name" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="接口代码" prop="interfaceCode" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="体征上限" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="{ row }">
                <div v-if="row.fmale == '0'">
                  <span>{{ row.valuemalemax }}</span>
                </div>
                <div v-else-if="row.fmale == '1'">
                  <span>{{ row.valuemalemax }}</span>
                </div>
                <div v-else-if="row.fmale == '2'">
                  <span>{{ row.valuefemalemax }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="体征下限" prop="tzxx" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="{ row }">
                <div v-if="row.fmale == '0'">
                  <span>{{ row.valuemalemin }}</span>
                </div>
                <div v-else-if="row.fmale == '1'">
                  <span>{{ row.valuemalemin }}</span>
                </div>
                <div v-else-if="row.fmale == '2'">
                  <span>{{ row.valuefemalemin }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="危急值上限" prop="valuedangerousmax" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="危急值下限" prop="valuedangerousmin" width="120" align="center" show-overflow-tooltip />
          </el-table>
          <el-pagination background :pager-count="5" :current-page="queryParams.current" :page-sizes="[10, 20, 30, 50]" :page-size="20" layout="sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"> </el-pagination>
        </div>
        <div class="center-btn">
          <el-button type="primary" plain size="mini" @click="selectOption">移入选项<i class="el-icon-upload2 el-icon--right"></i></el-button>
          <el-button type="primary" plain size="mini" @click="removeOption" style="margin: 24px 0 0 !important"><i class="el-icon-upload2 el-icon--left"></i>移出选项</el-button>
        </div>
        <div class="right-table">
          <el-table ref="rightTable" border :data="selectList" height="100%" style="width: 100%" stripe @selection-change="selectChange" @row-click="rowClick">
            <el-table-column fixed type="selection" width="55" align="center" />
            <el-table-column fixed label="序列" type="index" width="65" align="center" />
            <!-- examitemName -- ksmc  -->
            <el-table-column fixed label="检查项目名称" prop="examitemName" width="120" align="center" show-overflow-tooltip />
            <!-- examitemtypeName --jcxmlx -->
            <el-table-column label="检查项目类型" prop="examitemtypeName" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="性别" prop="fmale" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <!-- 原 fmale 现sex -->
                <div v-for="item in xbOptions" :key="item.id">
                  <span v-if="item.id == scope.row.fmale">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="类型" prop="type" width="120" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <!-- 原 type  现 lx -->
                <div v-for="item in tjlxOptions" :key="item.id">
                  <span v-if="item.id == scope.row.type">{{ item.text }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="科室名称" prop="ksmc" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="接口代码" prop="interfaceCode" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="体征上限" prop="tzsx" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="体征下限" prop="tzxx" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="危急值上限" prop="valuedangerousmax" width="120" align="center" show-overflow-tooltip />
            <el-table-column label="危急值下限" prop="valuedangerousmin" width="120" align="center" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset('1')">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getItems, addItems, updateItems, getListDatas, getItemsBarcodeClass } from '@/api/basis/charge'
import { listBasexamltem } from '@/api/basis/inspect'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js'
import Cookies from 'js-cookie'
import pinyin from '@/utils/pinyin.js'
export default {
  components: {},
  props: [],
  data() {
    return {
      // 科室
      departData: {
        placeholder: '请输入输入码选择',
        key: '名称', //第一列标题
        value: '输入码', //第二列标题
        url: '/system/keshi/list', //请求连接
        bindValue: '',
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 外送机构
      coopData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/data/wsjg/list', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 标本类型
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/data/yblx/list', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 标本种类
      labTypeData2: {
        placeholder: '请输入输入码选择',
        key: '代码', //第一列标题
        value: '名称', //第二列标题
        url: 'specimenType/page', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'code', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'specimenName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'pinyinCode', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 费用类型
      fylxData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/items/getFylx', //请求连接
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        bindValue: '',
        queryData: 'key',
      },
      // 销售打印分类
      xsdyflData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/items/getXsdyfl', //请求连接
        bindValue: '',
        queryData: 'key',
        secondName: 'printName', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 签单类型输入码选择
      typeSelectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '体检类别', //第二列标题
        url: 'data/businessCat/list', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'typeId', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'typeName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // APP分类
      appTypeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/items/getAppTypeData', //请求连接
        bindValue: '',
      },
      // 关联搜索数据
      searchData: {
        placeholder: '',
        inputTitle: '', // 搜索标题
        inputPlaceholder: '', // 搜索placeholder
        value: '收费项目', //第一列标题
        secondName: 'examfeeitemName',
        url: '/items/getAllItemsData', //请求连接
        bindValue: '',
      },
      // 体检类型列表
      tjlxOptions: [
        {
          id: 0,
          text: '健康体检',
        },
        {
          id: 1,
          text: '职业体检',
        },
        {
          id: 2,
          text: '综合',
        },
      ],

      // 餐序列表
      cxOptions: [
        { id: '0', text: '餐前' },
        { id: '1', text: '餐中' },
        { id: '2', text: '餐后' },
      ],
      // 性别列表
      xbOptions: [
        {
          id: 0,
          text: '男',
        },
        {
          id: 1,
          text: '女',
        },
        {
          id: 2,
          text: '通用',
        },
      ],
      // 标示列表
      bsOptions: [
        {
          id: '0',
          text: '健康标示',
        },
        {
          id: '1',
          text: '职业标示',
        },
      ],
      // 分中心列表
      branchOptions: [],
      // 分中心可选
      branchDisabled: false,
      // 条码打印分类列表
      tmdyflOptions: [
        {
          id: '0',
          text: '其他',
        },
      ],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        examName: undefined,
        examInputCode: undefined,
      },
      patientType: [],
      // 检查项目表格数据
      examList: [],
      selectList: [],
      cidList: [],
      // 遮罩层
      loading: false,
      // 选中数组
      examItems: [],
      selectIds: [],
      // 总条数
      total: 0,
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        examfeeitemName: [{ required: true, message: '收费项目名称不能为空', trigger: 'change' }],
        examfeeitemNameprn: [{ required: true, message: '项目打印名称不能为空', trigger: 'change' }],
        dydfz: [{ required: true, message: '导引单分组不能为空', trigger: 'change' }],
        idDepart: [{ required: true, message: '所属科室名称不能为空', trigger: 'change' }],
        fylx: [{ required: true, message: '费用类型不能为空', trigger: 'change' }],
        xsdyfl: [{ required: true, message: '销售打印分类不能为空', trigger: 'change' }],
        examfeeitemid: [{ required: true, message: '项目ID不能为空', trigger: 'change' }],
      },
      rowData: undefined,
      inspectChargeList: [],

      //补充
      cid: '', //当前用户的分中心
    }
  },
  async created() {
    this.patientType = (await this.$getLevelList()).data
  },
  methods: {
    //条码打印分类
    goGetItemsBarcodeClass() {
      getItemsBarcodeClass().then((res) => {
        this.tmdyflOptions = res.data
      })
    },
    // 业务类别分类
    //性别选择判断
    Xbchange(value) {
      // var rsex = this.form.xb;用不到
      var sex = value
      var data = this.selectList
      var l = data.length
      if (l > 0) {
        for (var i = 0; i < l; i++) {
          var row = data[i]
          var cid = row.cid
          if (cid != null && typeof cid != undefined) {
            var xb = row.sex
            if (sex == '2') {
              //通用
              if (sex != xb) {
                this.$modal.msgWarning('性别类型必须和子表一致1')
                this.$delete(this.form, 'xb')
                this.$set(this.form, 'xb', xb)
                return
              }
            } else {
              if (xb != '2') {
                if (sex != xb) {
                  this.$modal.msgWarning('性别类型必须和子表一致')
                  this.$delete(this.form, 'xb')
                  this.$set(this.form, 'xb', xb)
                  return
                }
              }
            }
          }
        }
      }
    },
    //
    Lxchange(value) {
      // var rtjlx = mini.get("tjlx"); 用不上
      var tjlx = value
      var data = this.selectList
      var l = data.length
      if (l > 0) {
        for (var i = 0; i < l; i++) {
          var row = data[i]
          var cid = row.cid
          if (cid != null && typeof cid != undefined) {
            var lx = row.lx || row.type
            if (tjlx == '2') {
              //通用 综合不需要判断
              /*
              if(tjlx!=lx){
                mini.alert("体检类型必须和子表一致");
                rtjlx.setValue(lx);
                return;
              }
              */
            } else {
              if (lx != '2') {
                if (tjlx != lx) {
                  this.$modal.msgWarning('体检类型必须和子表一致')
                  this.$delete(this.form, 'tjlx')
                  this.$set(this.form, 'tjlx', lx)
                  return
                }
              }
            }
          }
        }
      }
    },
    // 科室选择
    departChange(value) {
      this.form.idDepart = value.id
      this.departData.bindValue = value.name
    },
    // 外送机构选择
    coopChange(value) {
      this.form.idCooporg = value.id || ''
      this.coopData.bindValue = value.name
    },
    // 标本类型选择
    labTypeChange(value) {
      this.form.idLabtype = value.id || ''
      this.labTypeData.bindValue = value.name
    },
    // 标本种类选择
    labTypeChange2(value) {
      this.form.specimenTypeCode = value.code
      this.form.specimenTypeName = value.specimenName
      this.labTypeData2.bindValue = value.specimenName
    },
    // 费用类型选择
    fylxChange(value) {
      this.form.fylx = value.id || ''
      this.form.fylxName = value.name
      this.fylxData.bindValue = value.name
    },
    // 销售打印分类选择
    xsdyflChange(value) {
      this.form.xsdyfl = value.id || ''
      this.form.xsdyflName = value.printName
      this.xsdyflData.bindValue = value.name
    },
    // APP分类选择
    appTypeChange(value) {
      this.form.labtypeSub = value.id || ''
      this.appTypeData.bindValue = value.name
    },
    // 搜索选择
    searchChange(value) {
      this.form.guidesheetcode = value.map((item) => item.label).join(',')
    },
    // 获取必选项目
    getGuidesheetcode() {
      if (this.form.guidesheetcode) {
        return this.form.guidesheetcode.split(',')
      }
      return []
    },
    // 是否公共改变
    publicChange(value) {
      if (value == 1) {
        this.branchDisabled = true
        this.form.fzxIds = []
        // 如果是公共,需要清空分中心id
        // this.form.fzxIds = this.cid
      } else {
        this.branchDisabled = false
      }
    },
    // 业务类别返回值
    typeSelectChange(value) {
      this.form.bsCatId = value.typeId
    },
    // 添加
    handleAdd() {
      this.popData = undefined
      getBranchData().then((res) => {
        this.branchOptions = res.data
      })
      // this.cid = Number(Cookies.get('cid'))
      this.cid = Cookies.get('cid')
      this.reset()
      this.open = true
      this.title = '添加收费项目'
    },
    // 编辑
    handleUpdate(row) {
      if (row) {
        this.rowData = row
      }
      this.popData = undefined
      getBranchData().then((res) => {
        this.branchOptions = res.data
      })
      // this.cid = Number(Cookies.get("cid"));
      this.reset()
      const id = row.id
      this.open = true
      this.title = '编辑'
      this.popLoading = true
      getItems(id).then((response) => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data
        // this.form.fzxIds = response.data.fzxIds
        this.form.fzxIds = response.data.fzxIds.split(',')
        this.departData.bindValue = this.form.departName
        this.coopData.bindValue = this.form.idCooporg
        this.labTypeData.bindValue = this.form.labtypeR
        this.fylxData.bindValue = this.form.fylx
        this.xsdyflData.bindValue = this.form.xsdyfl
        this.appTypeData.bindValue = this.form.labtypeSub
        this.publicChange(this.form.isPublic)
      })
      let obj = {
        id,
        size: 9999,
      }
      getListDatas(obj).then((res) => {
        if (res.code == 200) {
          this.selectList = res.data.records

          this.selectList.forEach((el) => {
            el._state = 'modified'
            el.examitemName = el.name
            el.examitemtypeName = el.jcxmlx
            el.type = el.lx
            el.fmale = el.sex
          })
          this.inspectChargeList = res.data.records.map((el) => {
            el._state = 'modified'
            el.examitemName = el.name
            el.examitemtypeName = el.jcxmlx
            el.type = el.lx
            el.fmale = el.sex
            return el
          })
          // this.cidList = this.inspectChargeList.map((el)=> el.id);
        }
      })
    },
    // 表单重置
    reset(data) {
      if (this.popData && data == '1') {
        this.handleUpdate(this.rowData)
      }
      this.goGetItemsBarcodeClass()
      this.examList = []
      this.selectList = []
      this.cidList = []
      this.examItems = []
      this.inspectChargeList = []
      this.selectIds = []
      this.total = 0
      this.branchDisabled = false
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.departData.bindValue = this.form.departName
        this.coopData.bindValue = this.form.idCooporg
        this.labTypeData.bindValue = this.form.labtypeR
        this.labTypeData2.bindValue = this.form.specimenTypeName
        this.fylxData.bindValue = this.form.fylx
        this.xsdyflData.bindValue = this.form.xsdyfl
        this.appTypeData.bindValue = this.form.labtypeSub
        this.publicChange(this.form.isPublic)
        return
      }
      this.form = {
        examfeeitemName: undefined,
        sfxmsrm: undefined,
        sysmanmark: '1',
        examfeeitemNameprn: undefined,
        bgdybs: 0,
        dydfz: undefined,
        xh: undefined,
        dyddybs: '1',
        idDepart: undefined,
        idCooporg: undefined,
        unitprice: 0,
        zk: 0,
        suiteprice: 0,
        xybdm: undefined,
        wbjg: 0,
        ydjg: 0,
        nbj: 0,
        materialprice: 0,
        costprice: 0,
        coopprice: 0,
        idLabtype: undefined,
        dlbs: '1',
        fylx: undefined,
        cx: '0',
        xsdyfl: undefined,
        tjlx: 2,
        xb: 2,
        bs: '0',
        examfeeitemCode: undefined,
        jccs: 0,
        fdiscountdisabled: 0,
        examfeeitemid: undefined,
        isPublic: 0,
        fzxIds: [this.cid],
        examfeeitemCodex: '1',
        preUpperLimit: undefined,
        labtypeSub: undefined,
        reviewMatters: undefined,
        bz: undefined,
        guidesheetcode: undefined,
        barcodeName: undefined,
        barcodeCount: 1,
        examfeeitemClass: undefined,
        jcyy: undefined,
        groupOrder: 0,
        freportalone: 0,
      }
      this.departData.bindValue = undefined
      this.coopData.bindValue = undefined
      this.labTypeData.bindValue = undefined
      this.labTypeData2.bindValue = undefined
      this.fylxData.bindValue = undefined
      this.xsdyflData.bindValue = undefined
      this.appTypeData.bindValue = undefined
      this.$nextTick(() => {
        this.$refs.bsCatId.initData()
      })
      this.queryParams = {
        current: 1,
        size: 20,
        examName: undefined,
        examInputCode: undefined,
      }
      this.resetForm('form')
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.sfxmsrm = pinyin(value)
    },
    // 条码打印次数失去焦点
    barcodeCountBlur(value) {
      if (isNaN(value.target.ariaValueNow)) {
        this.form.barcodeCount = 1
      }
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryParams.forMale = this.form.xb
      this.queryParams.type = this.form.tjlx
      // this.queryParams.divisionId = this.form.idDepart;
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryExam')
      this.handleQuery()
    },
    // 查询列表
    getList() {
      this.loading = true
      listBasexamltem(this.queryParams).then((response) => {
        this.examList = response.data.records
        this.total = response.data.total
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
    // 移入选项
    selectOption() {
      let obj = []
      let cid = []
      for (var i in this.examItems) {
        if (this.cidList.indexOf(this.examItems[i].cid) == -1) {
          for (var j in this.inspectChargeList) {
            if (this.examItems[i].examitemName == this.inspectChargeList[j].examitemName && !(this.inspectChargeList[j]._state == 'removed')) {
              cid = []
              this.$modal.msgWarning('检查项目不能重复!')
              return
            }
          }
          cid.push(this.examItems[i].cid)
          obj.push({ ...this.examItems[i], ...{ _state: 'added', inspectId: this.examItems[i].cid, id: '' } })
        }
      }
      this.cidList.push(...cid)
      this.selectList.push(...obj)
      this.inspectChargeList.push(...obj)
      this.$refs.examList.clearSelection()
    },
    // 移出选项
    removeOption() {
      for (let i in this.selectIds) {
        let index = this.inspectChargeList.filter((item) => this.selectIds[i] == item.cid)
        let index2 = this.selectList.filter((item) => this.selectIds[i] == item.cid)
        this.cidList.forEach((el, cidIndex) => {
          if (el == this.selectIds[i]) {
            this.cidList.splice(cidIndex, 1)
          }
        })
        for (var j = 0; j < this.selectList.length; j++) {
          if (this.selectList[j].cid == index2[0].cid) {
            this.$delete(this.selectList, j)
          }
        }
        for (var j = 0; j < this.inspectChargeList.length; j++) {
          if (this.inspectChargeList[j].cid == index[0].cid) {
            this.$delete(this.inspectChargeList[j], '_state')
            this.$set(this.inspectChargeList[j], '_state', 'removed')
          }
        }
      }
    },
    // 多选框选中数据
    examChange(selection) {
      this.examItems = selection
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.examList.clearSelection()
      this.$refs.examList.toggleRowSelection(row)
    },
    selectChange(selection) {
      this.selectIds = selection.map((item) => item.cid)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.rightTable.clearSelection()
      this.$refs.rightTable.toggleRowSelection(row)
    },
    //取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      let tjlx = this.form.tjlx
      let xb = this.form.xb
      let bs = this.form.bs
      if (tjlx != 2) {
        if (tjlx != bs) {
          this.$modal.msgWarning('当体检类型不是"综合"时,体检类型和标示必须一致!')
          return
        }
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          for (var i = 0; i < this.inspectChargeList.length; i++) {
            var row = this.inspectChargeList[i]

            var sex = row.fmale //性别 xb -->fmale
            if (xb == '2') {
              //通用
              //
              // if(xb!=sex){
              // 	mini.alert("主表、子表性别必须一致");
              // 	return ;
              // }
              //
            } else {
              if (sex != '2') {
                if (sex != xb) {
                  this.$modal.msgWarning('主表、子表性别必须一致')
                  return
                }
              }
            }
            var lx = row.lx || row.type
            if (tjlx == '2') {
              //通用
              /*if(tjlx!=lx){
                mini.alert("主表、子表体检类型必须一致");
                return;
              }
              */
            } else {
              if (lx != '2' && row._state != 'removed') {
                if (tjlx != lx) {
                  this.$modal.msgWarning('主表、子表体检类型必须一致')
                  return
                }
              }
            }
            var jcxm = row.examitemName
            var state = row._state
            if (i > 0) {
              for (var j = 0; j < i; j++) {
                var row1 = this.inspectChargeList[j]
                if (jcxm == row1.examitemName && row1.examitemName != undefined && row1._state != 'removed' && state != 'removed') {
                  this.$modal.msgWarning('检查项目不能重复!')
                  return
                }
              }
            }
            //新增且未删除的数据
            if ((row.id == '' || typeof row.id == 'undefined') && row._state != 'removed') {
              row._state = 'added'
              this.$set(this.inspectChargeList, i, row)
              //编辑且未删除的数据
            } else if (row._state != 'removed') {
              row._state = 'modified'
              this.$set(this.inspectChargeList, i, row)
            } else if (row._state == 'removed') {
              row._state = 'removed'
              this.$set(this.inspectChargeList, i, row)
            }
          }
          if (this.form.id != null) {
            let obj = {
              ...this.form,
              inspectChargeList: [],
            }
            this.inspectChargeList.forEach((element, index) => {
              if (element._state == 'added') {
                obj.inspectChargeList.push({
                  _state: element._state,
                  chargeId: this.rowData.id, //收费项目ID 新增为空
                  createdate: element.createdate,
                  inspectId: element.cid, //检查项目ID--必选
                  // id: element.id,//检查项目ID--新增不选
                  isDelete: element.isDelete,
                  modifydate: element.modifydate,
                  orderIndex: index + 1,
                })
              } else {
                obj.inspectChargeList.push({
                  _state: element._state,
                  chargeId: this.rowData.id, //收费项目ID 新增为空
                  createdate: element.createdate,
                  inspectId: element.cid, //检查项目ID--必选
                  id: element.id, //检查项目ID--非新增必选
                  isDelete: element.isDelete,
                  modifydate: element.modifydate,
                  orderIndex: index + 1,
                })
              }
            })
            if (obj.fzxIds) {
              obj.fzxIds = obj.fzxIds.join(",")
            }
            updateItems(obj).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            let obj = {
              ...this.form,
              inspectChargeList: [],
            }
            this.inspectChargeList.forEach((element) => {
              obj.inspectChargeList.push({
                _state: element._state,
                chargeId: element.chargeId,
                createdate: element.createdate,
                id: element.id,
                inspectId: element.cid,
                isDelete: element.isDelete,
                modifydate: element.modifydate,
                orderIndex: element.orderIndex,
              })
            })
            if (obj.fzxIds) {
              obj.fzxIds = obj.fzxIds.join(",")
            }
            addItems(obj).then(() => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss">
.add-charge {
  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #d4d6d9;
    min-width: 1342px;

    .el-form {
      padding: 16px 16px 0;

      .el-form-item {
        margin-bottom: 24px;
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

    .left-table {
      width: 35%;
      min-width: 600px;
      max-height: 600px;
      margin: -1px;
    }

    .center-btn {
      width: 140px;
      background: #fff;
      height: auto;
      border: 1px solid #dfe6ec;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .el-icon--left {
        transform: rotate(-90deg);
      }

      .el-icon--right {
        transform: rotate(90deg);
      }
    }

    .right-table {
      width: calc(65% - 140px);
      min-width: 600px;
      max-height: 600px;
      flex: 1;
      margin: -1px;
    }
  }
}
</style>
