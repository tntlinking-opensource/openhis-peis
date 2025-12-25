<!-- 客户管理添加  开发人：麦沃德科技 半夏/予安/矢北 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-customer" width="95%" :close-on-click-modal="false" append-to-body @close="cancel">
    <el-descriptions :column="3" border :style="{ '--theme': theme }">
      <el-descriptions-item label="客户单位名称" labelClassName="add-labels required">
        <el-input v-model="form.khdwmc" :readonly="read" placeholder="请输入" clearable @input="nameChange" />
      </el-descriptions-item>
      <el-descriptions-item label="客户单位输入码" labelClassName="add-labels required">
        <el-input v-model="form.khdwsrm" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="客户单位联系人" labelClassName="add-labels required">
        <el-input v-model="form.khdwlxr" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="客户电话" labelClassName="add-labels required">
        <el-input v-model="form.khdh" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="法人单位名称" labelClassName="add-label">
        <el-input v-model="form.frdwmc" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="上级主管单位" labelClassName="add-label">
        <el-input v-model="form.sjzgdw" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="单位机构代码" labelClassName="add-label">
        <el-input v-model="form.dwjgdm" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="销售经理" labelClassName="add-label">
        <el-input v-model="form.xsjl"  placeholder="请输入" clearable />
        <!-- <el-input v-model="form.xsjl" :readonly="true" placeholder="请输入" clearable /> -->
      </el-descriptions-item>
      <el-descriptions-item label="职业病危害作业场所数" labelClassName="add-label">
        <el-input v-model="form.zybwhzycss" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="职业病危害因素类别" prop="zybwhyslb" labelClassName="add-label">
        <el-tooltip effect="dark" :content="form.fzx" placement="top" :disabled="!form.fzx">
          <el-select class="branch-select" v-model="form.zybwhyslb" placeholder=" 请输入" clearable style="width: 100%" filterable multiple @change="fzxChange">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.name" :value="item.name" />
          </el-select>
        </el-tooltip>
      </el-descriptions-item>
      <el-descriptions-item label="职业病危害因素" prop="zybwhys" labelClassName="add-labels required">
        <search-select ref="searchSelect" :initialValue="iniZybwhys" :selectData="selectData" :multiple="true" @change="change1" clearable style="width: 100%"> </search-select>
      </el-descriptions-item>
      <el-descriptions-item label="工艺流程" labelClassName="add-label">
        <el-input v-model="form.gylc" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="主要原、辅料" labelClassName="add-label">
        <el-input v-model="form.zyyfl" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="主要产品" labelClassName="add-label">
        <el-input v-model="form.zycp" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="体检团体类型" labelClassName="add-label">
        <el-select v-model="form.tjttlx" :readonly="read" placeholder="请选择" style="width: 100%">
          <el-option v-for="options in groupOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-descriptions-item>
      <el-descriptions-item label="企业环境监测报告" style="display: flex" labelClassName="add-labels required">
        <file-upload ref="uploadFile" :uploadData="uploadData" :uploadModel="2" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
        <div style="width: 100%; display: flex">
          <div style="cursor: pointer" @click="downLoadList" v-if="form.filePaths">点击下载已经保存文件</div>
          <div style="width: 50%; margin-left: 10px" v-if="form.filePaths">{{ this.fileName }}</div>
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="客户上次体检单位地址" labelClassName="add-label">
        <el-input v-model="form.khsctjdwdz" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="金蝶名字" labelClassName="add-labels">
        <search-select ref="kingdeeData" :selectData="kingdeeData" :readonly="read" :showTooltip="true" selectWidth="100%" :initialValue="form.jindieId" @change="kingdeeSearchChange"> </search-select>
      </el-descriptions-item>
      <el-descriptions-item label="执照名称" labelClassName="add-labels required">
        <el-input v-model="form.licenseName" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="备注" labelClassName="add-label">
        <el-input v-model="form.bz" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions :column="3" border :style="{ '--theme': theme }" style="margin-top: 16px">
      <el-descriptions-item label="省/市/区/街道" labelClassName="add-labels required" :span="3">
        <div class="address-box">
          <span class="address-title">省</span>
          <el-select v-model="form.province" placeholder="请选择省" @change="provinceChange" style="width: 100%">
            <el-option v-for="options in provinceOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">市</span>
          <el-select v-model="form.city" placeholder="请选择市" :disabled="form.province == undefined" @change="cityChange" style="width: 100%">
            <el-option v-for="options in cityOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">区</span>
          <el-select v-model="form.district" placeholder="请选择区" :disabled="form.city == undefined" @change="districtChange" style="width: 100%">
            <el-option v-for="options in districtOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">街道</span>
          <el-select v-model="form.street" @change="streetChange" placeholder="请选择街道" :disabled="form.district == undefined" style="width: 100%">
            <el-option v-for="options in streetOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="行业类别" labelClassName="add-label" :span="3">
        <div class="address-box">
          <span class="address-title">门类</span>
          <el-select v-model="form.indusTypeCode1" placeholder="请选择门类" @change="classChange" style="width: 100%">
            <el-option v-for="options in classOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">大类</span>
          <el-select v-model="form.indusTypeCode2" placeholder="请选择大类" :disabled="!form.indusTypeCode1" @change="majorChange" style="width: 100%">
            <el-option v-for="options in majorOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">中类</span>
          <el-select v-model="form.indusTypeCode3" placeholder="请选择中类" :disabled="!form.indusTypeCode2" @change="middleChange" style="width: 100%">
            <el-option v-for="options in middleOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">小类</span>
          <el-select v-model="form.indusTypeCode" placeholder="请选择小类" :disabled="!form.indusTypeCode3" style="width: 100%">
            <el-option v-for="options in subclassOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="社会信用代码" labelClassName="add-labels required">
        <el-input v-model="form.socialCreditCode" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="经济类型" labelClassName="add-labels required">
        <el-select v-model="form.economyCode" placeholder="请选择" clearable style="width: 100%">
          <el-option v-for="options in economyOptions" :key="options.economyCode" :label="options.economyName" :value="options.economyCode" />
        </el-select>
      </el-descriptions-item>
      <el-descriptions-item label="企业规模" labelClassName="add-labels required">
        <el-select v-model="form.qygm" placeholder="请选择" clearable style="width: 100%">
          <el-option v-for="options in scaleOptions" :key="options.crptSizeCode" :label="options.crptSizeName" :value="options.crptSizeCode" />
        </el-select>
      </el-descriptions-item>
      <el-descriptions-item label="职工人数" labelClassName="add-labels required">
        <el-input-number :disabled="read" style="width: 100%" v-model="form.workForce" clearable controls-position="right" :min="1" :max="1000000"></el-input-number>
      </el-descriptions-item>
      <el-descriptions-item label="接触危害因素人数" labelClassName="add-labels required">
        <el-input-number :readonly="read" :disabled="read" style="width: 100%" v-model="form.holdCardMan" clearable controls-position="right" :min="1" :max="1000000"></el-input-number>
      </el-descriptions-item>
      <el-descriptions-item label="单位注册地址" labelClassName="add-labels required">
        <el-input v-model="form.khdwzcdz" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="法人联系电话" labelClassName="add-label">
        <el-input v-model="form.phone" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="法人代表" labelClassName="add-label">
        <el-input v-model="form.fddbr" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="生产工人数" labelClassName="add-label">
        <el-input v-model="form.workmanNum" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="接触职业病危害因素女工人数" labelClassName="add-label">
        <el-input v-model="form.workmistressNum" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="单位注册邮编" labelClassName="add-label">
        <el-input v-model="form.yzbm" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="经营面积" labelClassName="add-label">
        <el-input v-model="form.workArea" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="注册资金" labelClassName="add-label">
        <el-input v-model="form.registerFund" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="职业卫生安全负责人" labelClassName="add-label">
        <el-input v-model="form.safetyPrincipal" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="建厂日期" labelClassName="add-label">
        <el-date-picker v-model="form.buildDate" :readonly="read" style="width: 100%" value-format="yyyy-MM-dd HH:MM:SS" type="datetime" key="datetime" :class="{ 'el-date-hover': form.buildDate }" placeholder="选择日期"> </el-date-picker>
      </el-descriptions-item>
      <el-descriptions-item label="检测联系人" labelClassName="add-label">
        <el-input v-model="form.linkman1" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="检测联系人职务" labelClassName="add-label">
        <el-input v-model="form.position1" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="检测联系人电话" labelClassName="add-label">
        <el-input v-model="form.linkphone1" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="体检联系人" labelClassName="add-labels required">
        <el-input v-model="form.linkman2" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="体检联系人职务" labelClassName="add-label">
        <el-input v-model="form.position2" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="体检联系人电话" labelClassName="add-labels required">
        <el-input v-model="form.linkphone2" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="职业卫生安全联系人职务" labelClassName="add-label">
        <el-input v-model="form.safeposition" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="职业卫生安全联系人电话" labelClassName="add-label">
        <el-input v-model="form.safephone" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="隶属关系" labelClassName="add-label">
        <el-input v-model="form.lsgx" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="作业场所地址" labelClassName="add-label">
        <el-input v-model="form.enrolAddress" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="作业场所邮政编码" labelClassName="add-label">
        <el-input v-model="form.enrolPostalcode" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="职业卫生管理机构" labelClassName="add-label">
        <el-input v-model="form.occManaOffice" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="用人单位所属区全名称" labelClassName="add-labels required">
        <el-select v-model="form.unitarea" placeholder="请选择" clearable filterable :filter-method="filterMethod" style="width: 100%">
          <el-option v-for="options in unitOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
        </el-select>
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions :column="3" border :style="{ '--theme': theme }" style="margin-top: 16px">
      <el-descriptions-item label="用工单位的省/市/区/街道" labelClassName="add-label" :span="3">
        <div class="address-box">
          <span class="address-title">省</span>
          <el-select v-model="form.rauProvince" placeholder="请选择省" @change="rauProvinceChange" style="width: 100%">
            <el-option v-for="options in ruaProvinceOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">市</span>
          <el-select v-model="form.rauCity" placeholder="请选择市" :disabled="!form.rauProvince" @change="rauCityChange" style="width: 100%">
            <el-option v-for="options in ruaCityOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">区</span>
          <el-select v-model="form.rauDistrict" placeholder="请选择区" :disabled="!form.rauCity" @change="rauDistrictChange" style="width: 100%">
            <el-option v-for="options in ruaDistrictOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <span class="address-title">街道</span>
          <el-select v-model="form.rauStreet" placeholder="请选择街道" :disabled="!form.rauDistrict" style="width: 100%">
            <el-option v-for="options in ruaStreetOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="用工单位行业类别" labelClassName="add-label" :span="3">
        <div class="address-box">
          <span class="address-title">门类</span>
          <el-select v-model="form.rauIndusTypeCode" placeholder="请选择门类" @change="rauClassChange" style="width: 100%">
            <el-option v-for="options in ruaClassOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">大类</span>
          <el-select v-model="form.rauIndusTypeCode1" placeholder="请选择大类" :disabled="!form.rauIndusTypeCode" @change="rauMajorChange" style="width: 100%">
            <el-option v-for="options in ruaMajorOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">中类</span>
          <el-select v-model="form.rauIndusTypeCode2" placeholder="请选择中类" :disabled="!form.rauIndusTypeCode1" @change="rauMiddleChange" style="width: 100%">
            <el-option v-for="options in ruaMiddleOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
          <span class="address-title">小类</span>
          <el-select v-model="form.rauIndusTypeCode3" placeholder="请选择小类" :disabled="!form.rauIndusTypeCode2" style="width: 100%">
            <el-option v-for="options in ruaSubclassOptions" :key="options.indusTypeCode" :label="options.indusTypeName" :value="options.indusTypeCode" />
          </el-select>
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="用工单位社会信用代码" labelClassName="add-label">
        <el-input v-model="form.rauSocialCreditCode" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="用工单位经济类型" labelClassName="add-label">
        <el-select v-model="form.rauEconomyCode" placeholder="请选择" clearable style="width: 100%">
          <el-option v-for="options in economyOptions" :key="options.economyCode" :label="options.economyName" :value="options.economyCode" />
        </el-select>
      </el-descriptions-item>
      <el-descriptions-item label="用工单位企业规模" labelClassName="add-label">
        <el-select v-model="form.rauQygm" placeholder="请选择" clearable style="width: 100%">
          <el-option v-for="options in scaleOptions" :key="options.crptSizeCode" :label="options.crptSizeName" :value="options.crptSizeCode" />
        </el-select>
      </el-descriptions-item>
      <el-descriptions-item label="用工单位名称" labelClassName="add-label">
        <el-input v-model="form.rauKhdwmc" :readonly="read" placeholder="请输入" clearable />
      </el-descriptions-item>
      <el-descriptions-item label="用工单位所属区全名称" labelClassName="add-label">
        <el-select v-model="form.rauUnitarea" placeholder="请选择" clearable style="width: 100%">
          <el-option v-for="options in unitOptions2" :key="options.id" :label="options.zoneName" :value="options.zoneCode" />
        </el-select>
      </el-descriptions-item>
    </el-descriptions>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import pinyin from '@/utils/pinyin.js'
import { getSellcustomer, addSellcustomer, updateSellcustomer, isRepeatName, getfzxList, getIndusData, getZoneData, getEconomyCode, getUnitArea, getJhysList } from '@/api/customer/customer_list.js'
import { filterMethod } from '@/utils/filterMethod.js'
import Cookies from 'js-cookie'
export default {
  data() {
    return {
      fileName: undefined,
      //设置禁用
      disable: true,
      //设置只读
      read: undefined,
      // 关联搜索
      selectData: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '', // 搜索placeholder
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/SignInInspection/getJhysData', //请求连接
        firstName: 'harmCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
        params: {},
        queryData: 'key',
        bindValue: [],
      },
      // 关联搜索
      kingdeeData: {
        placeholder: '请选择',
        inputTitle: '名称', // 搜索标题
        inputPlaceholder: '', // 搜索placeholder
        key: '金蝶ID', //第一列标题
        value: '金蝶账名', //第二列标题
        url: '/finance/kingdee/getKingdeeCustomerData', //请求连接
        firstName: 'kingdeeAccountNo', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'kingdeeAccountName', //接口返回值对应第二列的参数名(不传默认为'name')
        thirdName: '金蝶状态', // 三四列数据标题
        fourthName: '所属组织',
        thirdData: 'kingdeeUseStatus', // 三四列接口返回值对应的参数名
        fourthData: 'centerOrgName',
      },
      // 上传组件参数
      uploadData: {
        url: '/sell/customer/uploads', //文件上传地址
        multiple: true, //是否可以上传多个
        data: {}, //上传时附带的额外参数
      },
      // 职业病危害因素类别
      sortOptions: [],
      // 职业病危害因素
      sortOptions2: [],
      //危害因素,
      zybwhys: [],
      // 体检团体类型
      groupOptions: [
        { id: 0, text: '待定客户' },
        { id: 1, text: '普通客户' },
        { id: 2, text: 'vip客户' },
        { id: 3, text: '流失客户' },
      ],
      // 省数据
      provinceOptions: [],
      // 市数据
      cityOptions: [],
      // 区数据
      districtOptions: [],
      // 街道数据
      streetOptions: [],
      // 用人单位省数据
      ruaProvinceOptions: [],
      // 用人单位市数据
      ruaCityOptions: [],
      // 用人单位区数据
      ruaDistrictOptions: [],
      // 用人单位街道数据
      ruaStreetOptions: [],
      ///危害因素是否展示
      show: true,
      //分中心数据
      fzxOptions: [],

      // 省市区街道参数
      province: '',
      city: '',
      district: '',
      street: '',
      // 用工单位的省/市/区/街道参数
      rauProvince: '',
      rauCity: '',
      rauDistrict: '',
      rauStreet: '',
      // 门类数据
      classOptions: [],
      // 大类数据
      majorOptions: [],
      // 中类数据
      middleOptions: [],
      // 小类数据
      subclassOptions: [],
      // 门类数据
      ruaClassOptions: [],
      // 大类数据
      ruaMajorOptions: [],
      // 中类数据
      ruaMiddleOptions: [],
      // 小类数据
      ruaSubclassOptions: [],
      // 行业类别参数
      indusTypeCode: '',
      indusTypeCode1: '',
      indusTypeCode2: '',
      indusTypeCode3: '',
      // 用工单位行业类别 
      rauIndusTypeCode: '',
      rauIndusTypeCode1: '',
      rauIndusTypeCode2: '',
      rauIndusTypeCode3: '',
      // 经济类型
      economyOptions: [],
      i: 0,
      // 企业规模
      scaleOptions: [
        { crptSizeCode: '10000', crptSizeName: '大' },
        { crptSizeCode: '10001', crptSizeName: '中' },
        { crptSizeCode: '10002', crptSizeName: '小' },
        // { crptSizeCode: '10003', crptSizeName: '不详' },
        { crptSizeCode: '10004', crptSizeName: '微型' },
      ],
      resetId: undefined,
      // 用人单位所属区全名称
      unitOptions: [],
      filterList: [],
      //用工单位所属区全名称
      unitOptions2: [],
      iniZybwhys: [],
      // 表单参数
      form: {
        fzxid: undefined,
      },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      isRepeat: {
        cusName: undefined,
      },
      rule: {},
      //当前分中心
      currentFzx: undefined,
      //当前职业病
      currentZyb: '',
      //决定是否可以保存
      isOK: undefined,
      // 加载
      loading: null,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  methods: {
    // 搜索选择
    kingdeeSearchChange(value) {
      this.form.jindieId = value.kingdeeAccountName
    },
    // 省改变
    provinceChange(zoneCode) {
      const type = 1
      this.form.city = undefined
      this.form.district = undefined
      this.form.street = undefined
      const leave = 2
      const zone = zoneCode
      this.form.province = zoneCode
      this.getZoneData(leave, zone, type)
    },
    // 市改变
    cityChange(zoneCode) {
      const type = 1
      this.form.district = undefined
      this.form.street = undefined
      const leave = 3
      const zone = zoneCode
      this.form.city = zoneCode
      this.getZoneData(leave, zone, type)
    },
    // 区改变
    districtChange(zoneCode) {
      const type = 1
      this.form.street = undefined
      const leave = 4
      const zone = zoneCode
      this.form.district = zoneCode
      this.getZoneData(leave, zone, type)
    },
    //街道改变
    streetChange(zoneCode) {
      this.form.street = zoneCode
    },
    // 用工单位省改变
    rauProvinceChange(zoneCode) {
      const type = 2
      this.form.rauCity = undefined
      this.form.rauDistrict = undefined
      this.form.rauStreet = undefined
      const leave = 2
      const zone = zoneCode
      this.form.rauProvince = zoneCode
      this.getZoneData(leave, zone, type)
    },
    // 用工单位市改变
    rauCityChange(zoneCode) {
      const type = 2
      this.form.rauDistrict = undefined
      this.form.rauStreet = undefined
      const leave = 3
      const zone = zoneCode
      this.form.rauCity = zoneCode
      this.getZoneData(leave, zone, type)
    },
    // 用工单位区改变
    rauDistrictChange(zoneCode) {
      const type = 2
      this.form.rauStreet = undefined
      this.street = undefined
      const leave = 4
      const zone = zoneCode
      this.form.rauDistrict = zoneCode
      this.getZoneData(leave, zone, type)
    },

    // 用工单位街道改变
    rauStreetChange(zoneCode) {
      this.form.rauStreet = zoneCode
    },
    // 门类改变
    classChange(insCode) {
      this.form.indusTypeCode2 = undefined
      this.form.indusTypeCode3 = undefined
      this.form.indusTypeCode = undefined
      const type = 1
      const leave = 2
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 大类改变
    majorChange(insCode) {
      this.form.indusTypeCode3 = undefined
      this.form.indusTypeCode = undefined
      const type = 1
      const leave = 3
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 中类改变
    middleChange(insCode) {
      this.form.indusTypeCode = undefined
      const type = 1
      const leave = 4
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 用工单位门类改变
    rauClassChange(insCode) {
      this.form.rauIndusTypeCode1 = undefined
      this.form.rauIndusTypeCode2 = undefined
      this.form.rauIndusTypeCode3 = undefined
      const type = 2
      const leave = 2
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 用工单位大类改变
    rauMajorChange(insCode) {
      this.form.rauIndusTypeCode3 = undefined
      this.form.rauIndusTypeCode2 = undefined
      const type = 2
      const leave = 3
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 用工单位中类改变
    rauMiddleChange(insCode) {
      this.form.rauIndusTypeCode3 = undefined
      const type = 2
      const leave = 4
      const zone = insCode
      this.getIndusData(leave, zone, type)
    },
    // 分中心选择
    fzxChange(value) {
      var fzx = ''
      this.fzxOptions.forEach((el) => {
        if (value.length <= 0) {
          fzx = undefined
        } else if (value.length == 1) {
          fzx = value[0]
        } else {
          fzx = value.join(',')
        }
      })

      this.form.fzx = fzx
    },
    // sele的点击事件
    closeChange() {
      this.show = false
    },

    //分中心列表
    getfzxList() {
      getfzxList({
        current: 1,
        size: 99999,
      }).then(({ data }) => {
        this.fzxOptions = data
      })
    },
    change1(value) {
      for (var i in value) {
        if (i == 0) this.form.zybwhys = value[i].id
        else this.form.zybwhys += ',' + value[i].id
      }
      if (value.length > 0) {
        this.form.zybwhys = this.form.zybwhys.split(',')
      }
    },
    getSellerName() {
      this.form.xsjl = decodeURIComponent(Cookies.get('username'))
    },
    // 添加
    handleAdd() {
      this.title = '新增客户单位'
      this.reset()
      this.open = true
      this.getSellerName()
      this.update()
      //获取接害数据

      //获取门类数据
      this.getIndusData(1)
      //获取地区全名数据
      this.getUnitArea()
      //获取经济类数据
      this.getEconomyCode()
      //获取职业病分类数据 暂定为卡中心
      this.getfzxList()
      //获取地区数据
      this.getZoneData(1)
    },
    //重复名称
    // repeatName() {
    //   this.isRepeat.cusName = this.form.khdwmc;
    //   const repeat = this.isRepeat
    //   if (repeat.cusName != null && repeat.cusName != '' && repeat.cusName != undefined) {
    //     isRepeatName(repeat).then(response => {
    //       if (response.code != 200) { this.$modal.msgWarning("不可以重复使用客户单位"); this.isOK = false; }
    //       else {
    //         this.isOK = true
    //       }
    //     }).catch(() => { })
    //   }
    // },
    ///查看内容
    lookupDia(row) {
      this.handleUpdate(row)
      this.read = true
    },
    //获取地区全名称
    getUnitArea() {
      getUnitArea().then((response) => {
        this.unitOptions = response.data
        this.filterList = JSON.parse(JSON.stringify(response.data))
        for (var i in this.unitOptions) {
          this.unitOptions[i]['id'] = i
        }
        this.unitOptions2 = response.data
        for (var i in this.unitOptions2) {
          this.unitOptions2[i]['id'] = i
        }
      })
    },
    // 体检科室条件查询选择
    filterMethod(value) {
      this.unitOptions = this.filterList
      this.unitOptions = filterMethod(value, this.unitOptions, 'zoneName')
    },
    //获取接害因素数据
    getJhysList() {},
    // 更新危害因素数据``````````````````````````````````````````````````````````````````````````````````
    update() {
      if (this.i == 0) {
        this.$nextTick(() => {
          for (var i = 0; i < this.sortOptions2.length; i++) {
            for (var j = 0; j < this.form.zybwhys.length; j++) {
              if (this.sortOptions2[i].id == this.form.zybwhys[j]) {
                var obj = {
                  value: this.sortOptions2[i].harmName,
                  id: this.sortOptions2[i].id,
                }
                this.selectData.bindValue.push(obj)
              }
            }
          }
        })
      }
      this.i++
    },
    //获取经济数据
    getEconomyCode() {
      getEconomyCode().then((response) => {
        this.economyOptions = response.data
      })
    },
    //根据门类获取数据 
    getIndusData(level, zoneCode, type) {
      const params = {
        size: 9999,
        current: 1,
        level: level,
        indusTypeCode: zoneCode,
      }
      console.log("门类获取执行了",level);
      
      getIndusData(params).then((response) => {
        if (type == 1) {
          if (level == 1) {
            this.classOptions = response.data
          } else if (level == 2) {
            this.majorOptions = response.data
            console.log("大类数据",this.majorOptions);

          } else if (level == 3) {
            this.middleOptions = response.data
            console.log("中类数据",this.middleOptions);

          } else {
            this.subclassOptions = response.data
            console.log("小类数据是",this.subclassOptions);

          }
        } else if (type == 2) {
          if (level == 1) {
            this.ruaClassOptions = response.data
          } else if (level == 2) {
            this.ruaMajorOptions = response.data
          } else if (level == 3) {
            this.ruaMiddleOptions = response.data
          } else {
            this.ruaSubclassOptions = response.data
          }
        } else {
          if (level == 1) {
            this.classOptions = response.data
            this.ruaClassOptions = response.data
          } else if (level == 2) {
            this.majorOptions = response.data
            this.ruaMajorOptions = response.data
          } else if (level == 3) {
            this.middleOptions = response.data
            this.ruaMiddleOptions = response.data
          } else {
            this.subclassOptions = response.data
            this.ruaSubclassOptions = response.data
          }
        }
      })
    },
    //获取地区数据
    getZoneData(level, zoneCode, type) {
      const params = {
        size: 9999,
        current: 1,
        level: level,
        zoneCode: zoneCode,
      }

      //type 对 是省份或是用工单位省份判断 1 省份 2 用工单位省份
      if (type == 1) {
        getZoneData(params).then((response) => {
          if (level == 1) {
            this.provinceOptions = response.data
          } else if (level == 2) {
            this.cityOptions = response.data
          } else if (level == 3) {
            this.districtOptions = response.data
          } else {
            this.streetOptions = response.data
          }
        })
      } else if (type == 2) {
        getZoneData(params).then((response) => {
          if (level == 1) {
            this.ruaProvinceOptions = response.data
          } else if (level == 2) {
            this.ruaCityOptions = response.data
          } else if (level == 3) {
            this.ruaDistrictOptions = response.data
          } else {
            this.ruaStreetOptions = response.data
          }
        })
      } else {
        getZoneData(params).then((response) => {
          if (level == 1) {
            this.provinceOptions = response.data
            this.ruaProvinceOptions = response.data
          } else if (level == 2) {
            this.cityOptions = response.data
            this.ruaCityOptions = response.data
          } else if (level == 3) {
            this.districtOptions = response.data
            this.ruaDistrictOptions = response.data
          } else {
            this.streetOptions = response.data
            this.ruaStreetOptions = response.data
          }
        })
      }
    },
    downLoadList() {
      for (var i in this.form.filePaths) {
        var url = this.form.filePaths[i]
        var first = Cookies.get('imgPath')

        var index = url.lastIndexOf('/')
        var name = url.substring(index + 1, url.length)

        this.downloadFile(first + url, name)
      }
      // 使用示例 
    },
    //下载文件 
    downloadFile(url, filename) {
      var xhr = new XMLHttpRequest()
      xhr.open('GET', url, true)
      xhr.responseType = 'blob'
      xhr.onload = function () {
        if (xhr.status === 200) {
          var blob = xhr.response
          var link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = filename
          link.click()
        }
      }
      xhr.send()
    },

    // 编辑
    handleUpdate(row) {
      this.resetId = row
      this.title = '编辑客户单位'
      this.open = true
      this.reset()
      // this.read = false

      // this.update()
      // //获取接害数据

      // //获取门类数据
      // this.getIndusData(1)
      // //获取地区全名数据
      // this.getUnitArea()
      // //获取经济类数据
      // this.getEconomyCode()
      // //获取职业病分类数据 暂定为卡中心
      // this.getfzxList()
      // //获取地区数据
      // this.getZoneData(1)

      // const id = row
      // this.show = true
      // // const id = row.id || this.ids
      // // getPrinttype(id).then(response => {
      // // 	this.form = response.data;
      // // 	this.open = true;
      // // 	this.title = "编辑";
      // // });
      // // this.form = {};
      // this.open = true
      // this.$nextTick(() => {
      //   getSellcustomer(id).then((response) => {
      //     this.getJhysList()

      //     this.form = response.data
      //     if (response.data.zybwhyName) {
      //       this.iniZybwhys = response.data.zybwhyName.split(',')
      //     }

      //     if (this.form.filePaths.length > 0) {
      //       var index = this.form.filePaths[0].lastIndexOf('/')
      //       var name = this.form.filePaths[0].substring(index + 1, this.form.filePaths[0].length) + '...'
      //       this.fileName = name
      //     }
      //     if (this.form.zybwhyslb != '' && this.form.zybwhyslb != undefined && this.form.zybwhyslb != null) {
      //       this.form.zybwhyslb = this.form.zybwhyslb.split(',')
      //     }
      //     if (this.form.zybwhys != '' && this.form.zybwhys != undefined && this.form.zybwhys != null) {
      //       this.form.zybwhys = this.form.zybwhys.split(',')
      //     }
      //     if (this.form.city) {
      //       this.getZoneData(2, this.form.province, 1)
      //     }
      //     if (this.form.district) {
      //       this.getZoneData(3, this.form.city, 1)
      //     }
      //     if (this.form.street) {
      //       this.getZoneData(4, this.form.district, 1)
      //     }
      //     if (this.form.rauCity) {
      //       this.getZoneData(2, this.form.rauProvince, 2)
      //     }
      //     if (this.form.rauDistrict) {
      //       this.getZoneData(3, this.form.rauCity, 2)
      //     }
      //     if (this.form.rauStreet) {
      //       this.getZoneData(4, this.form.rauDistrict, 2)
      //     }
      //     if (this.form.indusTypeCode1) {
      //       this.getIndusData(2, this.form.indusTypeCode, 1)
      //     }
      //     if (this.form.indusTypeCode2) {
      //       this.getIndusData(3, this.form.indusTypeCode1, 1)
      //     }
      //     if (this.form.rauIndusTypeCode3) {
      //       this.getIndusData(4, this.form.indusTypeCode2, 1)
      //     }
      //     if (this.form.rauIndusTypeCode1) {
      //       this.getIndusData(2, this.form.rauIndusTypeCode, 2)
      //     }
      //     if (this.form.rauIndusTypeCode2) {
      //       this.getIndusData(3, this.form.rauIndusTypeCode1, 2)
      //     }
      //     if (this.form.rauIndusTypeCode3) {
      //       this.getIndusData(4, this.form.rauIndusTypeCode2, 2)
      //     }
      //   })
      // })
    },
    // 表单重置
    reset() {
      this.form = {
        khdwmc: undefined,
        khdwsrm: undefined,
        khdwlxr: undefined,
        khdh: undefined,
        frdwmc: undefined,
        sjzgdw: undefined,
        dwjgdm: undefined,
        xsjl: undefined,
        zybwhzycss: undefined,
        zybwhyslb: undefined,
        zybwhys: undefined,
        gylc: undefined,
        zyyfl: undefined,
        zycp: undefined,
        tjttlx: undefined,
        file: undefined,
        khsctjdwdz: undefined,
        licenseName: undefined,
        kingdeeAccountName: undefined,
        bz: undefined,
        province: undefined,
        city: undefined,
        district: undefined,
        street: undefined,
        indusTypeCode1: undefined,
        indusTypeCode2: undefined,
        indusTypeCode3: undefined,
        indusTypeCode: undefined,
        socialCreditCode: undefined,
        economyCode: undefined,
        crptSizeCode: undefined,
        workForce: undefined,
        holdCardMan: undefined,
        khdwzcdz: undefined,
        fzxid: undefined,
        phone: undefined,
        fddbr: undefined,
        workmanNum: undefined,
        workmistressNum: undefined,
        yzbm: undefined,
        workArea: undefined,
        registerFund: undefined,
        safetyPrincipal: undefined,
        buildDate: undefined,
        linkman1: undefined,
        position1: undefined,
        linkphone1: undefined,
        linkman2: undefined,
        position2: undefined,
        zyb: this.currentZyb,
        linkphone2: undefined,
        safeposition: undefined,
        safephone: undefined,
        subjeCon: undefined,
        enrolAddress: undefined,
        enrolPostalcode: undefined,
        occManaOffice: undefined,
        unitarea: undefined,
        rauProvince: undefined,
        rauCity: undefined,
        rauDistrict: undefined,
        rauStreet: undefined,
        rauIndusTypeCode1: undefined,
        rauIndusTypeCode2: undefined,
        rauIndusTypeCode3: undefined,
        rauIndusTypeCode: undefined,
        rauSocialCreditCode: undefined,
        rauEconomyCode: undefined,
        rauCrptSizeCode: undefined,
        rauKhdwmc: undefined,
        rauUnitArea: undefined,
        jindieId: undefined,
        fzx: this.currentFzx,
      }
      this.iniZybwhys = []
      this.$nextTick(() => {
        this.$refs.kingdeeData.initData()
        this.$refs.uploadFile.resetUpload()
      })
      this.resetForm('form')
      if (this.title == '编辑客户单位') {
        this.read = false

        this.update()
        //获取接害数据

        //获取门类数据
        this.getIndusData(1)
        //获取地区全名数据
        this.getUnitArea()
        //获取经济类数据
        this.getEconomyCode()
        //获取职业病分类数据 暂定为卡中心
        this.getfzxList()
        //获取地区数据
        this.getZoneData(1)

        const id = this.resetId
        this.show = true
        // const id = row.id || this.ids
        // getPrinttype(id).then(response => {
        // 	this.form = response.data;
        // 	this.open = true;
        // 	this.title = "编辑";
        // });  
        // this.form = {}; 
        this.$nextTick(() => {
          getSellcustomer(id).then((response) => {
            this.getJhysList()
            this.form = response.data
            if (response.data.zybwhyName) {
              this.iniZybwhys = response.data.zybwhyName.split(',')
            }

            if (this.form.filePaths.length > 0) {
              var index = this.form.filePaths[0].lastIndexOf('/')
              var name = this.form.filePaths[0].substring(index + 1, this.form.filePaths[0].length) + '...'
              this.fileName = name
            }
            if (this.form.zybwhyslb != '' && this.form.zybwhyslb != undefined && this.form.zybwhyslb != null) {
              this.form.zybwhyslb = this.form.zybwhyslb.split(',')
            }
            if (this.form.zybwhys != '' && this.form.zybwhys != undefined && this.form.zybwhys != null) {
              this.form.zybwhys = this.form.zybwhys.split(',')
            }
            if (this.form.city) {
              this.getZoneData(2, this.form.province, 1)
            }
            if (this.form.district) {
              this.getZoneData(3, this.form.city, 1)
            }
            if (this.form.street) {
              this.getZoneData(4, this.form.district, 1)
            }
            if (this.form.rauCity) {
              this.getZoneData(2, this.form.rauProvince, 2)
            }
            if (this.form.rauDistrict) {
              this.getZoneData(3, this.form.rauCity, 2)
            }
            if (this.form.rauStreet) {
              this.getZoneData(4, this.form.rauDistrict, 2)
            }
            if (this.form.indusTypeCode2) {
              this.getIndusData(2, this.form.indusTypeCode1, 1)
              console.log(444444);

            }
            if (this.form.indusTypeCode3) {
              this.getIndusData(3, this.form.indusTypeCode2, 1)
              console.log(555555);

            }
            if (this.form.indusTypeCode) {
              this.getIndusData(4, this.form.indusTypeCode3, 1)
              console.log(666666);

            }
            // if (this.form.rauIndusTypeCode) {
            //   this.getIndusData(4, this.form.indusTypeCode3, 1)
            //   console.log(666666);

            // }
            if (this.form.rauIndusTypeCode1) {
              this.getIndusData(2, this.form.rauIndusTypeCode, 2)
              console.log(777777);

            }
            if (this.form.rauIndusTypeCode2) {
              this.getIndusData(3, this.form.rauIndusTypeCode1, 2)
              console.log(888888);

            }
            if (this.form.rauIndusTypeCode3) {
              this.getIndusData(4, this.form.rauIndusTypeCode2, 2)
              console.log(999999);
              
            }
          })
        })
      }
    },
    // 客户单位名称改变 
    nameChange(value) {
      // this.repeatName()
      this.form.khdwsrm = pinyin(value)
    },
    // 取消按钮
    cancel() {
      this.resetId = undefined
      this.open = false
      this.form = {
        khdwmc: undefined,
        khdwsrm: undefined,
        khdwlxr: undefined,
        khdh: undefined,
        frdwmc: undefined,
        sjzgdw: undefined,
        dwjgdm: undefined,
        xsjl: undefined,
        zybwhzycss: undefined,
        zybwhyslb: undefined,
        zybwhys: undefined,
        gylc: undefined,
        zyyfl: undefined,
        zycp: undefined,
        tjttlx: undefined,
        file: undefined,
        khsctjdwdz: undefined,
        licenseName: undefined,
        kingdeeAccountName: undefined,
        bz: undefined,
        province: undefined,
        city: undefined,
        district: undefined,
        street: undefined,
        indusTypeCode1: undefined,
        indusTypeCode2: undefined,
        indusTypeCode3: undefined,
        indusTypeCode: undefined,
        socialCreditCode: undefined,
        economyCode: undefined,
        crptSizeCode: undefined,
        workForce: undefined,
        holdCardMan: undefined,
        khdwzcdz: undefined,
        fzxid: undefined,
        phone: undefined,
        fddbr: undefined,
        workmanNum: undefined,
        workmistressNum: undefined,
        yzbm: undefined,
        workArea: undefined,
        registerFund: undefined,
        safetyPrincipal: undefined,
        buildDate: undefined,
        linkman1: undefined,
        position1: undefined,
        linkphone1: undefined,
        linkman2: undefined,
        position2: undefined,
        zyb: this.currentZyb,
        linkphone2: undefined,
        safeposition: undefined,
        safephone: undefined,
        subjeCon: undefined,
        enrolAddress: undefined,
        enrolPostalcode: undefined,
        occManaOffice: undefined,
        unitarea: undefined,
        rauProvince: undefined,
        rauCity: undefined,
        rauDistrict: undefined,
        rauStreet: undefined,
        rauIndusTypeCode1: undefined,
        rauIndusTypeCode2: undefined,
        rauIndusTypeCode3: undefined,
        rauIndusTypeCode: undefined,
        rauSocialCreditCode: undefined,
        rauEconomyCode: undefined,
        rauCrptSizeCode: undefined,
        rauKhdwmc: undefined,
        rauUnitArea: undefined,
        fzx: this.currentFzx,
      }
      this.iniZybwhys = []
      this.$nextTick(() => {
        this.$refs.uploadFile.resetUpload()
      })
      this.resetForm('form')
    },
    // 上传文件成功
    uploadFinish(value, res, subForm) {
      if (value == 1) {
        if (res == '') {
          subForm.filePaths = undefined
        } else {
          res.data.urlList[0] = '#' + res.data.urlList[0]
          for (var i in res.data.urlList) {
            if (i == res.data.urlList.length - 1) {
              res.data.urlList[i] = res.data.urlList[i] + '#'
            }
          }
          subForm.filePaths = res.data.urlList
        }

        if (this.form.id != null) {
          updateSellcustomer(subForm, this.form.filePaths).then((response) => {
            this.$modal.msgSuccess('修改成功')
            this.open = false
            this.$emit('getList')
            this.$confirm('是否前往合同管理进行签约合同?', '提示')
              .then(() => {
                this.$router.push({ name: 'Contract_management' })
              })
              .catch(() => {})
          })
        } else {
          addSellcustomer(subForm, this.form.filePaths).then(() => {
            this.$modal.msgSuccess('添加成功')
            this.open = false
            this.$emit('getList')
            this.$confirm('是否前往合同管理进行签约合同?', '提示')
              .then(() => {
                this.$router.push({ name: 'Contract_management' })
              })
              .catch(() => {})
          })
        }
      }
      this.loading.close()
    },
    // 提交按钮
    submitForm() {
      if (this.form.khdwmc == undefined || this.form.khdwmc == null || this.form.khdwmc == '') {
        this.$modal.msgWarning('客户单位名称不能为空')
      } else if (this.form.khdwsrm == undefined || this.form.khdwsrm == null || this.form.khdwsrm == '') {
        this.$modal.msgWarning('客户单位输入码不能为空')
      } else if (this.form.khdwlxr == undefined || this.form.khdwlxr == null || this.form.khdwlxr == '') {
        this.$modal.msgWarning('客户单位联系人不能为空')
      } else if (this.form.khdh == undefined || this.form.khdh == null || this.form.khdh == '') {
        this.$modal.msgWarning('客户电话不能为空')
      }
      // else if (!this.form.jindieId) {
      //   this.$modal.msgWarning('金蝶名称不能为空')
      // }
      else if (this.form.licenseName == undefined || this.form.licenseName == null || this.form.licenseName == '') {
        this.$modal.msgWarning('执照名称不能为空')
      } else if (this.form.socialCreditCode == undefined || this.form.socialCreditCode == null || this.form.socialCreditCode == '') {
        this.$modal.msgWarning('社会信用代码不能为空')
      } else {
        const subForm = this.form

        if (subForm.zybwhyslb != null) {
          if (subForm.zybwhyslb.length > 0) {
            subForm.zybwhyslb = subForm.zybwhyslb.join(',')
          } else {
            subForm.zybwhyslb = ''
          }
        } else {
          this.form.zybwhyslb = ''
        }
        if (subForm.zybwhys != null) {
          if (subForm.zybwhys.length > 0) {
            subForm.zybwhys = subForm.zybwhys.join(',')
          } else {
            this.subForm.zybwhys = ''
          }
        } else {
          this.form.zybwhys = ' '
        }
        this.form.crptSizeCode = this.form.qygm
        // if (this.isOK != false) {
        var msg = this.$refs.uploadFile.isUpload()
        this.loading = this.$loading({
          lock: true,
          text: '正在提交',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        if (msg === true) {
          this.$refs.uploadFile.handelUpload(subForm)
        } else {
          this.uploadFinish(1, '', subForm)
        }
        // } else {
        //   this.$modal.msgWarning("无法使用重复客户单位名称");
        //   this.$emit('getList')
        //   this.open = false
        // }
      }
    },
  },
}
</script>
<style lang="scss">
.add-customer {
  .el-dialog {
    min-width: 800px;
  }

  .el-input__inner {
    padding: 0 8px;
    background: transparent;
    border-color: #dcdfe6;

    &:hover {
      border-color: #dcdfe6;
    }

    &:focus {
      border-color: #{'var(--theme)'} !important;
    }
  }

  .el-select:hover .el-input__inner {
    border-color: #dcdfe6;
  }

  .add-label {
    width: 120px;
    color: #333 !important;
    background: #f6f9ff !important;
    font-size: 14px;
  }

  .add-labels {
    width: 120px;
    color: #ff2727 !important;
    background: #f6f9ff !important;
    font-size: 14px;
  }

  .required::before {
    content: '*';
    display: inline-block;
    position: relative;
    top: 2px;
    color: #ff2727;
  }

  .el-scrollbar {
    .el-input__inner {
      border-color: #dcdfe6 !important;

      &:focus {
        border-color: #{'var(--theme)'} !important;
      }
    }
  }

  .el-select__tags {
    cursor: pointer;
  }

  .address-box {
    display: flex;
    align-items: center;

    .address-title,
    .el-select {
      margin-right: 12px;
    }

    .el-select {
      flex: 1;

      .el-input__inner {
        border-color: #dcdfe6 !important;

        &:focus {
          border-color: #{'var(--theme)'} !important;
        }
      }
    }
  }

  .branch-select .el-select__tags {
    flex-wrap: nowrap;
    overflow: hidden;
  }

  .el-date-editor {
    &.el-date-hover:hover .el-input__prefix {
      display: none;
    }

    .el-input__inner {
      padding-left: 8px;
    }

    .el-input__prefix {
      left: auto;
      right: 5px;
    }
  }
}
</style>
