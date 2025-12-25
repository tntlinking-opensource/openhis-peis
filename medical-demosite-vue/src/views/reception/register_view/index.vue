<!-- 查看 开发人：麦沃德科技 予安/半夏 -->
<template>
  <div class="app-container flex-direction-column register-view">
    <!-- 主体 -->
    <div class="table-box">
      <el-row :gutter="10" class="mb8" style="height: 100%; display: flex">
        <!-- 左侧 -->
        <el-col :span="9" style="overflow-y: auto; height: 100%" class="reception-left">
          <el-form :model="formData" v-loading="mainLoading" ref="mainForm" size="small" :inline="true" label-width="90px" label-position="right" style="min-width: 400px">
            <!-- 基本信息 -->
            <div class="base-info info-box">
              <div class="title">基本信息</div>
              <el-row type="flex" justify="space-between">
                <el-col :span="19">
                  <el-form-item label="订单号" prop="numorgresv">
                    <el-input :value="formData.numorgresv" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="团体名称" prop="orgName">
                    <el-input :value="formData.orgName" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="团体分组" prop="orgreservationgroupname">
                    <el-input :value="formData.orgreservationgroupname" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="体检套餐" prop="tjtcmc">
                    <el-input :value="formData.tjtcmc" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="团体部门" prop="orgDepart">
                    <el-input :value="formData.orgDepart" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="开单医师" prop="doctorapply">
                    <el-input :value="formData.doctorapply" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="预约时间" prop="dateguidancereturned">
                    <el-input :value="formData.dateguidancereturned" prefix-icon="el-icon-date" readonly style="width: 145px"></el-input>
                  </el-form-item>
                  <el-form-item label="登记时间" prop="dateregister">
                    <el-input :value="formData.dateregister" prefix-icon="el-icon-date" readonly style="width: 145px"></el-input>
                  </el-form-item>
                </el-col>
                <!-- 照片 -->
                <el-col :span="5">
                  <div class="photo">
                    <div class="image">
                      <img :src="imgPath + formData.picture" v-if="formData.picture" />
                      <img :src="defaultPicture" v-else />
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!-- 主要信息 -->
            <div class="main-info info-box">
              <div class="title">主要信息</div>
              <el-row type="flex" justify="space-between" style="flex-wrap: wrap">
                <el-form-item label="体检号" prop="patientcode">
                  <el-input :value="formData.patientcode" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="会员类型" prop="idPatientclass">
                  <div v-for="item in memberTypeList" :key="item.levelId">
                    <el-input :value="item.levelName" readonly style="width: 215px" v-if="formData.idPatientclass == item.levelId"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="体检类型" prop="idExamtype">
                  <div v-for="item in idExamtypeList" :key="item.id">
                    <el-input :value="item.text" readonly style="width: 215px" v-if="formData.idExamtype == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="姓名" prop="patientname">
                  <el-input :value="formData.patientname" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="备单分类" prop="idExamclass">
                  <div v-for="item in idExamclassList" :key="item.id">
                    <el-input :value="item.text" readonly style="width: 215px" v-if="formData.idExamclass == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="性别" prop="idSex">
                  <el-input value="男" readonly style="width: 215px" v-if="formData.idSex == 0"></el-input>
                  <el-input value="女" readonly style="width: 215px" v-if="formData.idSex == 1"></el-input>
                </el-form-item>
                <el-form-item label="籍贯" prop="resarea">
                  <el-input :value="formData.resarea" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="民族" prop="idNation">
                  <el-input :value="returnNation(formData.idNation)" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="登记时间" prop="birthdate">
                  <el-input :value="formData.birthdate" prefix-icon="el-icon-date" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="婚姻" prop="idMarriage" v-if="formData.idMarriage || formData.idMarriage == '0'">
                  <div v-for="item in idMarriageList" :key="item.id">
                    <el-input :value="item.text" readonly style="width: 215px" v-if="formData.idMarriage == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="婚姻" prop="idMarriage" v-else>
                  <el-input :value="formData.idMarriage" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="phone">
                  <el-input :value="formData.phone" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="家人手机" prop="examsuiteAlias">
                  <el-input :value="formData.examsuiteAlias" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="证件类型" prop="countreportoccupationxml">
                  <div v-for="item in countreportoccupationxmlList" :key="item.id">
                    <el-input :value="item.text" readonly style="width: 215px" v-if="formData.countreportoccupationxml == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="证件号" prop="idcardno">
                  <el-input :value="formData.idcardno" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                  <el-input :value="formData.age" readonly style="width: 215px"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="家庭住址" prop="address" class="row-item">
                  <el-input :value="formData.address" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="替检" prop="countreportxml">
                  <el-radio-group class="radio-box" :value="formData.countreportxml">
                    <el-radio :label="0" border>否</el-radio>
                    <el-radio :label="1" border>是</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="原名" prop="tjr" v-if="formData.countreportxml == 1">
                  <el-input :value="formData.tjr" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="自动已检" prop="autoExamined">
                  <el-checkbox :value="formData.autoExamined" border :true-label="1" :false-label="0" style="width: 215px"></el-checkbox>
                </el-form-item>
                <el-form-item label="黑名单" prop="isHmd" class="row-item">
                  <el-checkbox :value="formData.isHmd" :true-label="1" :false-label="0">
                    <el-input :value="formData.isHmdb" readonly style="width: 100%" :disabled="!formData.isHmd"></el-input>
                  </el-checkbox>
                </el-form-item>
                <el-form-item label="前台须知" prop="qtxz" class="row-item">
                  <el-input :value="formData.qtxz" readonly type="textarea" :rows="2" style="width: 100%"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="note" class="row-item">
                  <el-input :value="formData.note" readonly style="width: 100%"></el-input>
                </el-form-item>
                <el-form-item label="导引单备注" prop="guidancenote" class="row-item">
                  <el-input :value="formData.guidancenote" readonly style="width: 100%"></el-input>
                </el-form-item>
                <el-form-item label="通知方式" prop="idInformway" v-if="formData.idInformway || formData.idInformway == '0'">
                  <div v-for="item in idInformwayList" :key="item.id">
                    <el-input :value="item.methodName" readonly style="width: 215px" v-if="formData.idInformway == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="通知方式" prop="idInformway" v-else>
                  <el-input :value="formData.idInformway" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="预付方式" prop="payway">
                  <el-input :value="formData.payway" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="预付金额" prop="prepayment">
                  <el-input :value="formData.prepayment" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="记账单位" prop="jzdw">
                  <el-input :value="formData.jzdw" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="记账人" prop="jzdwr">
                  <el-input :value="formData.jzdwr" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="审批人" prop="spr">
                  <el-input :value="formData.spr" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="危害因素" prop="harmName" v-if="formData.idExamtype != 0">
                  <el-tooltip effect="dark" :content="formData.harmName" placement="top" :disabled="!formData.harmName">
                    <el-input :value="formData.harmName" readonly style="width: 215px"></el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="体检类别" prop="medicaltype" v-if="formData.idExamtype != 0">
                  <div v-for="item in medicalTypeList" :key="item.id">
                    <el-input :value="item.text" readonly style="width: 215px" v-if="formData.medicaltype == item.id"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="工种" prop="typeName" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.typeName" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="工号" prop="workno" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.workno" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="总工龄" prop="zgl" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.zgl" readonly style="width: 185px"></el-input>
                  <span class="input-unit">月</span>
                </el-form-item>
                <el-form-item label="接害工龄" prop="jhgl" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.jhgl" readonly style="width: 185px"></el-input>
                  <span class="input-unit">月</span>
                </el-form-item>
                <el-form-item label="参加工作" prop="workDate" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.workDate" prefix-icon="el-icon-date" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="从事工种" prop="harmDate" v-if="formData.idExamtype != 0">
                  <el-input :value="formData.harmDate" prefix-icon="el-icon-date" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="文化程度" prop="cultural" v-if="formData.cultural || formData.cultural == '0'">
                  <div v-for="item in culturalList" :key="item.value">
                    <el-input :value="item.label" readonly style="width: 215px" v-if="formData.cultural == item.value"></el-input>
                  </div>
                </el-form-item>
                <el-form-item label="文化程度" prop="cultural" v-else>
                  <el-input :value="formData.cultural" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="邮政编码" prop="yzbm">
                  <el-input :value="formData.yzbm" readonly style="width: 215px"></el-input>
                </el-form-item>
                <el-form-item label="邮寄地址" prop="yjaddress" class="row-item">
                  <el-input :value="formData.yjaddress" readonly style="width: 100%" type="textarea" :rows="2"></el-input>
                </el-form-item>
              </el-row>
            </div>
          </el-form>
        </el-col>
        <!-- 右侧 -->
        <el-col :span="15" style="height: 100%" class="reception-right">
          <div class="flex-direction-column">
            <!-- 上方统计数据 -->
            <el-row type="flex" justify="space-between" style="width: 100%">
              <el-col :span="24" style="border: 1px solid #d4d6d9; margin-bottom: 8px">
                <div class="statistics">
                  <div class="item">
                    <div class="title">总数</div>
                    <div class="number" :style="{ '--theme': theme }">{{ statistics.examfeeitemCount }}</div>
                  </div>
                  <div class="item">
                    <div class="title">原始单价合计</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.priceTotal).toFixed(1) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">优惠单价</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.factpriceTotal).toFixed(1) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">待收费合计</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.waitprice).toFixed(1) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">实收</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.ss).toFixed(1) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">应收</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.total).toFixed(1) }}</div>
                  </div>
                </div>
              </el-col>
            </el-row>
            <!-- 中间表格信息 -->
            <div class="table-box flex-direction-column">
              <div class="table-show">
                <el-table border ref="table" v-loading="loading" size="mini" :data="tableList" height="100%" stripe>
                  <el-table-column fixed label="序列" width="55" type="index" align="center" />
                  <el-table-column fixed label="收费项目" prop="examfeeitemName" align="center" width="255">
                    <template slot-scope="scope">
                      {{ scope.row.examfeeitemName }}
                    </template>
                  </el-table-column>
                  <el-table-column label="备选分组" prop="group" align="center" min-width="120" />
                  <el-table-column label="价格" align="center">
                    <el-table-column label="原价" prop="price" align="center" min-width="120">
                      <template slot-scope="scope">
                        {{ isNaN(scope.row.price) ? scope.row.price : Number(scope.row.price).toFixed(1) }}
                      </template>
                    </el-table-column>
                    <el-table-column label="优惠价" prop="factprice" align="center" width="140px">
                      <template slot-scope="scope">
                        {{ scope.row.factprice }}
                      </template>
                    </el-table-column>
                  </el-table-column>
                  <el-table-column label="付款方式" prop="idPayway" align="center" min-width="100">
                    <template slot-scope="scope">
                      <span v-if="scope.row.idPayway == 1">现金</span>
                      <span v-if="scope.row.idPayway == 5">统收</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="加项医师" prop="name" align="center" width="125">
                    <template slot-scope="scope">
                      {{ scope.row.name }}
                    </template>
                  </el-table-column>
                  <el-table-column label="登记" prop="FRegistered" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FRegistered == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="已收" prop="FFeecharged" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FFeecharged == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="已发" prop="FLabsendtolis" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FLabsendtolis == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="已检" prop="FExaminated" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FExaminated == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="弃检" prop="FGiveup" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FGiveup == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="迟检" prop="FDelayed" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.FDelayed == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="拒检" prop="sfjj" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.sfjj == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="加项" prop="sfjx" align="center" width="60">
                    <template slot-scope="scope">
                      <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.sfjx == 1"><i class="el-icon-check"></i> </el-tag>
                      <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="备注" prop="feeitemdesc" align="center" width="185px">
                    <template slot-scope="scope">
                      {{ scope.row.feeitemdesc }}
                    </template>
                  </el-table-column>
                  <el-table-column label="科室" prop="examfeeitemName" align="center" show-overflow-tooltip width="120" />
                  <el-table-column label="登记人" prop="doctorregR" align="center" width="125px">
                    <template slot-scope="scope">
                      {{ scope.row.doctorregR }}
                    </template>
                  </el-table-column>
                  <el-table-column label="创建时间" prop="createdate" align="center" show-overflow-tooltip width="120" />
                  <el-table-column label="查看图片" align="center" width="150px">
                    <template slot-scope="scope">
                      <span class="check-pic" @click="checkPic(scope.row)">查看图片</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <el-image ref="previewImg" :src="chargeUrl" :preview-src-list="[chargeUrl]" style="display: none"></el-image>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 查看图片对话框 -->
    <el-dialog title="查看图片" :visible.sync="openPic" width="380px" append-to-body style="overflow: hidden">
      <div>暂未查询到相关图片</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="openPic = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPatientData, getTcZhPrice, getMarriageData, getIssueWayData, getNationData, getAreaData, getPicture } from '@/api/reception/registration'
import { getCultural } from '@/utils/dataList.js'
import Cookies from 'js-cookie'
export default {
  name: 'RegisterView',
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 父页面传的id
      pid: '',
      // 父页面传的体检号
      patientCode: undefined,
      // 左侧参数
      // 表单数据
      formData: {},
      // 预览图片链接
      chargeUrl: '',
      // 表单加载
      mainLoading: true,
      // 会员类型
      memberTypeList: [],
      // 籍贯参数
      idResareaData: [],
      // 民族参数
      idNationData: [],
      // 体检类型
      idExamtypeList: [
        { id: '0', text: '健康体检' },
        { id: '1', text: '职业体检' },
        { id: '2', text: '综合' },
        { id: '3', text: '复查' },
      ],
      // 备单分类
      idExamclassList: [
        { id: 0, text: '健康类' },
        { id: 1, text: '职业类' },
        { id: 2, text: '综合类' },
        { id: 5, text: '入职类' },
        { id: 6, text: '疫苗类' },
        { id: 7, text: '其他类' },
      ],
      //  婚姻分类
      idMarriageList: [],
      // 证件类型
      countreportoccupationxmlList: [
        { text: '身份证', id: 1 },
        { text: '护照', id: 2 },
        { text: '军人证', id: 3 },
        { text: '港澳通行证/回乡证或台胞证', id: 6 },
      ],
      //  通知方式
      idInformwayList: [],
      // 体检类别
      medicalTypeList: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      //  文化程度
      culturalList: [],
      // 右侧参数
      // 数据列表
      statistics: {
        examfeeitemCount: 0,
        priceTotal: 0,
        factpriceTotal: 0,
        waitprice: 0,
        ss: 0,
        total: 0,
      },
      // 默认头像
      defaultPicture: require('@/assets/images/registration/default.png'),
      // 表格加载
      loading: false,
      // 表格数据
      tableList: [],
      // 查看图片弹窗
      openPic: false,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  created() {
    this.culturalList = getCultural()
    this.$getLevelList().then((res) => {
      this.memberTypeList = res.data
      if (!this.$route.params.patientcode) this.formData.idPatientclass = this.formData.idPatientclass ? this.formData.idPatientclass : this.memberTypeList[0].levelId
    })
    this.getMarriageData()
    this.getIssueWayData()
    this.getNationData()
    this.getAreaData()
    if (this.$route.params.patientcode) {
      this.patientCode = this.$route.params.patientcode
      this.pid = this.$route.params.id
      this.getPatientDataForBd()
    } else {
      this.mainLoading = false
      this.reset()
    }
  },
  methods: {
    // 备单人员进行登记操作、登记信息查询
    getPatientDataForBd() {
      getPatientData({ patientCode: this.patientCode, id: this.pid }).then((res) => {
        var data = res.data
        if (data == '') {
          return
        }
        if (!data.success) {
          // 重新加载后session值不会清除
          if (data.type) {
            return
          }
          this.$modal.msgWarning(data.error)
          return
        }
        this.formData = data.patientData
        this.mainLoading = false
        this.loading = false
        this.formData.tjtcmc = data.patientData.examsuiteName
        this.formData.picture = data.picture
        this.formData.tMoney = data.tMoney
        this.formData.harmName = data.jhysn
        this.formData.typeName = data.workType
        this.formData.autoExamined = data.patientData.autoExamined || 0
        // 订单ID
        this.formData.ddh = data.idOrder
        // 团体分组
        this.formData.orgreservationgroupname = data.group == null ? '' : data.group.orgreservationgroupname
        this.formData.idOrgreservationgroup = data.group == null ? '' : data.group.id
        // 开单医师
        this.formData.doctorapply = data.sellname
        // 获取体检套餐
        this.tableList = data.examfeeitemData
        // 日期格式化
        this.dateFormat(data)
        // 计算最终价格
        this.getTcPrice()
      })
    },
    // 页面日期赋值
    dateFormat(data) {
      this.formData.dateguidancereturned = data.patientData.dateguidancereturned ? data.patientData.dateguidancereturned.split(' ')[0] : ''
      this.formData.dateregister = data.patientData.dateregister ? data.patientData.dateregister.split(' ')[0] : ''
      this.formData.birthdate = data.patientData.birthdate ? data.patientData.birthdate.split(' ')[0] : ''
      this.formData.workDate = data.patientData.workDate ? data.patientData.workDate.split(' ')[0] : ''
      this.formData.harmDate = data.patientData.harmDate ? data.patientData.harmDate.split(' ')[0] : ''
    },
    // 体检套餐折后价格
    getTcPrice() {
      var id = this.formData.idTjtc
      if (id == null || id == '' || id == undefined) return
      // 套餐优惠价格
      getTcZhPrice({ tcid: id }).then((res) => {
        var data = res.data
        if (data != '') {
          // 更新数据
          this.statistics.tcprice = data.zhjg == null ? 0 : data.zhjg
          this.editChange()
        }
      })
    },
    // 获取婚姻状况
    getMarriageData() {
      getMarriageData().then((res) => {
        this.idMarriageList = []
        for (var i in res.data) {
          this.idMarriageList.push({ id: Number(i), text: res.data[i] })
        }
      })
    },
    // 获取通知方式
    getIssueWayData() {
      getIssueWayData().then((res) => {
        this.idInformwayList = res.data
      })
    },
    // 获取民族
    getNationData() {
      getNationData().then((res) => {
        this.idNationData = res.data
        this.formData.idNation = this.formData.idNation ? this.formData.idNation : this.idNationData[0].id
      })
    },
    // 返回民族
    returnNation(val) {
      for (var i in this.idNationData) {
        if (this.idNationData[i].id == val) {
          return this.idNationData[i].name
        }
      }
    },
    // 获取籍贯
    getAreaData() {
      getAreaData({ current: 1, size: 9999 }).then((res) => {
        this.idResareaData = res.data.records
        this.formData.idResarea = this.formData.idResarea ? this.formData.idResarea : this.getDefaultArea().id
        this.formData.resarea = this.formData.resarea ? this.formData.resarea : this.getDefaultArea().resarea
      })
    },
    // 获取默认籍贯
    getDefaultArea() {
      var defaultArea = {}
      this.idResareaData.forEach((el) => {
        if (el.resarea.indexOf('山东') > -1) {
          defaultArea = el
          return
        }
      })
      return defaultArea
    },
    // 获取当前日期
    getToday() {
      var dd = new Date()
      var y = dd.getFullYear()
      var m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1
      var d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate()
      return y + '-' + m + '-' + d
    },
    // 获取一天后日期
    getTomorrow() {
      var dd = new Date()
      dd.setDate(dd.getDate() + 1)
      var y = dd.getFullYear()
      var m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1
      var d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate()
      return y + '-' + m + '-' + d
    },
    // 表单重置
    reset() {
      this.formData = {
        numorgresv: '',
        ddh: '',
        idOrg: '',
        orgName: '',
        idOrgreservationgroup: '',
        orgreservationgroupname: '',
        idTjtc: '',
        tjtcmc: '',
        orgDepart: '',
        idOpendoctor: '',
        doctorapply: '',
        dateguidancereturned: this.getTomorrow(),
        dateregister: '',
        medicaldate: this.getToday() + ' 00:00:00',
        picture: '',
        fusecodehiden: 0,
        fisforreserve: 0,
        fregistered: 0,
        moneyamount: 0,
        moneyamountpaid: 0,
        idFeetype: '',
        patientnamepinyin: '',
        tMoney: 0,
        moreChangeMon: 0,
        patientcode: '',
        inpatientno: '',
        insuranceno: '',
        idPatientclass: this.memberTypeList.length ? this.memberTypeList[0].levelId : '',
        idExamtype: '0',
        idPatientarchive: '',
        patientarchiveno: '',
        patientname: '',
        idExamclass: 0,
        idSex: 0,
        idResarea: '',
        resarea: '',
        idNation: '',
        birthdate: this.getToday(),
        idMarriage: '',
        phone: '',
        examsuiteAlias: '',
        countreportoccupationxml: 1,
        idcardno: '',
        age: 0,
        address: '',
        countreportxml: 0,
        tjr: '',
        autoExamined: 0,
        isHmd: 0,
        isHmdb: '',
        qtxz: '',
        note: '',
        guidancenote: '',
        idInformway: '',
        idPayway: '',
        payway: '',
        prepayment: 0,
        jzdw: '',
        jzdwr: '',
        spr: '',
        jhys: '',
        worktypeId: '',
        zgl: 0,
        jhgl: 0,
        workno: '',
        workDate: '',
        harmDate: '',
        cultural: undefined,
        yzbm: '',
        yjaddress: '',
        tcprice: 0,
        xianjin: 0,
        personpricelimit: 0,
      }
      this.resetForm('mainForm')
    },
    // 计算总行数、原始价格、优惠价格
    editChange() {
      // 原始价钱
      var priceTotal = 0
      // 优惠价钱
      var factpriceTotal = 0
      // 退项价钱
      var tuiTotal = 0
      // 待收费价钱
      var waitprice = 0
      // 存在没有退费的已退项
      var isExitItem = false
      // 备选数量
      var bxCount = 0

      var result = this.tableList
      var ys = 0.0 //应收（所有优惠价合计）
      for (var i = 0; i < result.length; i++) {
        if (result[i].factprice) {
          ys += Number(result[i].factprice)
        }
        // 没有收费项目时不计算
        if (result[i].idExamfeeitem == undefined || result[i].idExamfeeitem == '') {
          continue
        }
        // 换项、弃项
        if (result[i].idPayway != 5 && result[i].factprice < 0 && result[i].FMarkFeereturn != 1) {
          // 退项的优惠价格
          tuiTotal = parseFloat(tuiTotal) + parseFloat(result[i].factprice * result[i].count)
          isExitItem = true
        }
        priceTotal += parseFloat(result[i].price * result[i].count)
        factpriceTotal += parseFloat(result[i].factprice * result[i].count)
        if (result[i].FFeecharged == null || result[i].FFeecharged == 0) {
          waitprice += parseFloat(result[i].factprice * result[i].count)
        }
        // 备选数量
        if (result[i].bxcount != null && result[i].bxcount != '') {
          bxCount = result[i].bxcount
        }
      }
      // 备选数量
      this.kxzsValue = bxCount

      // 团体
      if (this.formData.ddh != '') {
        this.kxzsShow = true
      } else {
        this.kxzsShow = false
      }
      waitprice = waitprice + tuiTotal
      // 退费成功
      if (this.formData.tMoney == waitprice && !isExitItem) {
        waitprice = 0
      }
      // 总计
      var mPaid = this.formData.moneyamountpaid
      this.personpricelimit = priceTotal
      // 更新数据
      this.statistics = {
        examfeeitemCount: result.length,
        priceTotal: priceTotal,
        factpriceTotal: factpriceTotal,
        waitprice: waitprice,
        ss: mPaid ? mPaid : 0,
        total: ys,
      }
    },
    // 查看图片弹窗
    checkPic(row) {
      if (row.idExamfeeitem) {
        getPicture(row.idExamfeeitem).then((res) => {
          if (!res.data) {
            this.$modal.alertWarning('暂未查询到相关图片', '提醒')
          } else {
            this.chargeUrl = res.data ? this.imgPath + res.data : res.data
            this.$refs.previewImg.showViewer = true
          }
        })
      } else {
        this.$modal.alertWarning('暂未查询到相关图片', '提醒')
      }
    },
  },
}
</script>

<style lang="scss">
.register-view {
  .reception-left {
    .info-box {
      width: 100%;
      padding: 16px 24px;
      border: 1px solid #d4d6d9;

      .row-item {
        width: 100%;
        display: flex !important;

        .el-form-item__content {
          flex: 1;

          .el-checkbox {
            width: 100%;
            display: flex !important;
            align-items: center;

            .el-checkbox__label {
              flex: 1;
            }
          }
        }
      }
    }

    .title {
      margin-bottom: 10px;
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
      text-align: left;
    }

    .base-info {
      .image {
        overflow: hidden;
        width: 110px;
        height: 150px;
        margin-bottom: 5px;
        border-radius: 5px;

        img {
          width: 100%;
          height: 100%;
          object-fit: contain;
        }
      }

      .photo {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
    }

    .main-info {
      padding: 16px 24px;
      border: 1px solid #d4d6d9;
      margin: 8px 0;

      .radio-box {
        width: 215px;
        height: 33px;
        display: flex;
        align-items: center;

        .el-radio {
          margin-right: 16px;
        }
      }

      .input-unit {
        display: inline-block;
        width: 30px;
        text-align: center;
        font-size: 14px;
        color: #606266;
      }
    }
  }

  .reception-right {
    .table-show {
      flex: 1;
      overflow-y: auto;

      .myrow {
        background: #fceee2;
      }

      .myrow2 {
        background: #afeeee;
      }

      .myrow3 {
        background: #71c671;
      }

      .myrow4 {
        background: red;
      }

      .sexchange_row {
        background: #ff0000;
      }
    }

    .statistics {
      display: flex;
      justify-content: space-between;
      height: 96px;
      padding: 10px 20px;
      overflow-x: auto;

      .item {
        min-width: 150px;
        height: 100%;
        padding: 12px 12px;
        background: #f7f8fa;
        border-radius: 10px;
        margin-left: 12px;

        &:first-child {
          margin-left: 0;
        }

        .title {
          font-size: 12px;
          line-height: 17px;
          color: #858586;
          margin-bottom: 5px;
        }

        .number {
          font-weight: 600;
          font-size: 24px;
          line-height: 34px;
          color: #{'var(--theme)'} !important;
        }
      }
    }

    .operation {
      display: flex;
      margin-top: 8px;
      overflow-x: auto;

      .item {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100px;
        min-width: 100px;
        padding: 10px 0;
        margin-right: 16px;
        box-shadow: 0px 0px 10px rgba(0, 89, 255, 0.5);
        border-radius: 5px;

        &:last-child {
          margin-right: 0;
        }

        &:hover {
          cursor: pointer;
        }

        .name {
          line-height: 20px;
          color: #ffffff;
        }
      }
    }

    .check-pic {
      color: #0059ff;

      &:hover {
        cursor: pointer;
      }
    }
  }
}
</style>
