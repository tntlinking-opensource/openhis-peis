<template>
  <el-col :span="11" style="overflow-y: auto; height: 100%" class="funcdept-reception-left">
    <el-form :readonly="readonly" :model="mainData" ref="queryForm" size="mini" :inline="true" label-width="90px" label-position="right" style="min-width: 400px">
      <!-- 基本信息 -->
      <div class="base-info info-box">
        <div class="title">基本信息</div>
        <el-row type="flex" justify="space-between">
          <el-col :span="21">
            <el-form-item label="体检号" prop="patientcode">
              <el-input :readonly="readonly" v-model="mainData.patientcode" placeholder="" clearable style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="patientname">
              <el-input :readonly="readonly" v-model="mainData.patientname" placeholder="" clearable style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="订单号" prop="numorgresv">
              <el-input :readonly="readonly" v-model="mainData.numorgresv" placeholder="" style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="团体名称" prop="orgName">
              <el-input :readonly="readonly" v-model="mainData.orgName" placeholder="" style="width: 130px"></el-input>
            </el-form-item>
            <el-form-item label="团体分组" prop="orgreservationgroupname">
              <el-input :readonly="readonly" v-model="mainData.orgreservationgroupname" placeholder="" style="width: 130px"></el-input>
            </el-form-item>
            <el-form-item label="体检套餐" prop="examsuiteName">
              <el-input :readonly="readonly" v-model="mainData.examsuiteName" placeholder="" style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="团体部门" prop="orgDepart">
              <el-input :readonly="readonly" v-model="mainData.orgDepart" style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="开单医师" prop="doctorapply">
              <el-input :readonly="readonly" v-model="mainData.doctorapply" placeholder="" style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="预约时间" prop="dateguidancereturned">
              <el-date-picker :readonly="readonly" v-model="mainData.dateguidancereturned" type="date" placeholder="" clearable style="width: 130px"> </el-date-picker>
            </el-form-item>
            <el-form-item label="体检时间" prop="medicaldate">
              <el-date-picker :readonly="readonly" v-model="mainData.medicaldate" type="date" placeholder="" style="width: 130px"> </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 照片 -->
          <el-col :span="3">
            <div class="photo">
              <div class="image">
                <img :src="url" v-show="imgShow" />
                <img :src="defaultImgUrl" v-show="!imgShow" />
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div style="height: 8px"></div>

      <div class="base-info info-box">
        <!-- 个检 -->
        <div class="title">主要信息</div>
        <el-row class="mb8" :span="24">
          <el-form-item label="体检者类型" prop="idPatientclass">
            <el-input :readonly="readonly" v-if="this.mainData.idPatientclass == 1" value="普通会员" placeholder="" clearable style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idPatientclass == 2" value="普通会员" placeholder="" clearable style="width: 130px"></el-input>
            <el-input style="width: 130px" value="" v-else placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="体检类型" prop="idExamtype">
            <div v-for="item in idExamtypeList" :key="item.id">
              <el-input :value="item.text" readonly style="width: 130px" v-if="mainData.idExamtype == item.id"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="性别" prop="idSex">
            <el-input :readonly="readonly" v-if="this.mainData.idSex == 0" value="男" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idSex == 1" value="女" style="width: 130px"></el-input>
            <el-input :readonly="readonly" style="width: 130px" value="" v-else></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input :readonly="readonly" type="number" v-model="mainData.age" style="width: 130px"> </el-input>
          </el-form-item>
          <el-form-item label="生日" prop="birthdate">
            <el-date-picker :readonly="readonly" v-model="mainData.birthdate" type="date" placeholder="" style="width: 130px"> </el-date-picker>
          </el-form-item>
          <el-form-item label="婚姻" prop="idMarriage">
            <el-input :readonly="readonly" v-if="this.mainData.idMarriage == 1" value="未婚" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idMarriage == 2" value="已婚" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idMarriage == 3" value="离异" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idMarriage == 4" value="丧偶" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.idMarriage == 5" value="其他" placeholder="" style="width: 130px"></el-input>
            <el-input style="width: 130px" :readonly="readonly" value="" v-else placeholder=""></el-input>
            <!-- <el-select :disabled="true" :readonly="readonly" v-model="mainData.idMarriage" placeholder=""
            style="width:130px"> -->
            <!-- <el-option  v-for="item in idMarriageList" :key="item.value" :label="item.text" :value="item.id" filterable>
            </el-option> -->
            <!-- <el-option label="已婚" :value="1"></el-option>
            <el-option label="未婚" :value="2"></el-option>
            <el-option label="离异" :value="3"></el-option>
            <el-option label="丧偶" :value="4"></el-option>
            <el-option label="其他" :value="5"></el-option>
          </el-select> -->
          </el-form-item>
          <el-form-item label="证件号" prop="idcardno">
            <el-input :readonly="readonly" type="tel" v-model="mainData.idcardno" placeholder="" style="width: 180px"> </el-input>
          </el-form-item>
          <el-form-item label="手机" prop="phone">
            <el-input :readonly="readonly" type="tel" v-model="mainData.phone" placeholder="" style="width: 130px"> </el-input>
          </el-form-item>
          <br />
          <el-form-item label="前台须知" class="row-item" prop="qtxz">
            <el-input :readonly="readonly" resize="none" type="textarea" :rows="3" style="width: 100%" v-model="mainData.qtxz"></el-input>
          </el-form-item>
          <el-form-item label="备注" class="row-item" prop="note">
            <el-input :readonly="readonly" resize="none" type="textarea" :rows="3" style="width: 100%" v-model="mainData.note"></el-input>
          </el-form-item>
          <el-form-item label="黑名单" class="row-item" prop="isHmd">
            <el-checkbox :true-label="1" @change="boxCheck" :false-label="0" v-model="mainData.isHmd">
              <el-input :value="mainData.isHmdb" readonly style="width: 100%" :disabled="!mainData.isHmd"></el-input>
            </el-checkbox>
          </el-form-item>
          <template v-if="mainData.idExamtype != '0'">
            <el-form-item label="危害因素" prop="harmNames" class="row-item">
              <el-input v-model="mainData.harmNames" readonly style="width: 100%"> </el-input>
            </el-form-item>
            <el-form-item label="体检类别" prop="medicaltypeName">
              <el-input v-model="mainData.medicaltypeName" readonly style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="工种" prop="workTypeName">
              <el-input v-model="mainData.workTypeName" readonly style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="工号" prop="workno">
              <el-input v-model="mainData.workno" readonly style="width: 130px"> </el-input>
            </el-form-item>
            <el-form-item label="总工龄" prop="zgl">
              <el-input-number v-model="mainData.zgl" controls-position="right" style="width: 100px" :min="0"></el-input-number>
              <span class="input-unit">月</span>
            </el-form-item>
            <el-form-item label="接害工龄" prop="jhgl">
              <el-input-number v-model="mainData.jhgl" controls-position="right" style="width: 100px" :min="0" :max="mainData.zgl"></el-input-number>
              <span class="input-unit">月</span>
            </el-form-item>
            <el-form-item label="参加工作" prop="workDate">
              <el-date-picker v-model="mainData.workDate" readonly style="width: 130px" value-format="yyyy-MM-dd" type="date"> </el-date-picker>
            </el-form-item>
            <el-form-item label="从事工种" prop="harmDate">
              <el-date-picker v-model="mainData.harmDate" readonly style="width: 130px" value-format="yyyy-MM-dd" type="date"> </el-date-picker>
            </el-form-item>
          </template>
          <el-form-item label="预付方式" prop="idPayway">
            <el-input :value="mainData.idPayway" readonly style="width: 130px"></el-input>
          </el-form-item>
          <el-form-item label="预付金额" prop="prepayment">
            <el-input :readonly="readonly" type="number" v-model="mainData.prepayment" clearable style="width: 130px"> </el-input>
          </el-form-item>
          <el-form-item label="通知方式" prop="idInformway" v-if="mainData.idInformway || mainData.idInformway == '0'">
            <div v-for="item in idInformwayList" :key="item.id">
              <el-input :value="item.methodName" readonly style="width: 130px" v-if="mainData.idInformway == item.id"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="通知方式" prop="idInformway" v-else>
            <el-input :value="mainData.idInformway" readonly style="width: 130px"></el-input>
          </el-form-item>
          <el-form-item label="民族" prop="idNation">
            <el-input :value="returnNation(mainData.idNation)" readonly style="width: 130px"></el-input>
          </el-form-item>
          <el-form-item label="邮政编码" prop="yzbm">
            <el-input :readonly="readonly" v-model="mainData.yzbm" rows="3" style="width: 130px"> </el-input>
          </el-form-item>
          <el-form-item label="邮寄地址" prop="yjaddress" class="row-item">
            <el-input :readonly="readonly" resize="none" v-model="mainData.yjaddress" style="width: 100%" type="textarea" :rows="2"> </el-input>
          </el-form-item>
          <el-form-item label="导引单备注" prop="guidancenote" class="row-item">
            <el-input :readonly="readonly" type="tel" v-model="mainData.guidancenote" style="width: 100%"> </el-input>
          </el-form-item>
          <el-form-item label="记账单位" prop="jzdw">
            <el-autocomplete v-model="mainData.jzdw" placeholder="" style="width: 130px" :readonly="readonly"></el-autocomplete>
          </el-form-item>
          <el-form-item label="记账人" prop="jzdwr">
            <el-input :readonly="readonly" v-model="mainData.jzdwr" clearable style="width: 130px"> </el-input>
          </el-form-item>
          <el-form-item label="审批人" prop="spr">
            <el-input :readonly="readonly" v-model="mainData.spr" clearable style="width: 130px"> </el-input>
          </el-form-item>
          <el-form-item label="文化程度" prop="cultural">
            <!-- <el-select :disabled="true" v-model="mainData.cultural" placeholder="" style="width:130px" clearable> -->
            <!-- <el-option v-for="item in culturalList" :key="item.value" :label="item.label" :value="item.value" filterabl>
            </el-option> -->
            <!-- <el-option :label="" :value=""></el-option>
            <el-option :label="" :value=""></el-option>
            <el-option :label="" :value=""></el-option> -->
            <!-- </el-select> -->
            <el-input :readonly="readonly" v-if="this.mainData.cultural == 0" value="小学" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 1" value="初中" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 2" value="技校" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 3" value="职高" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 4" value="高中" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 5" value="中专" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 6" value="大专" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 7" value="大学" placeholder="" style="width: 130px"></el-input>
            <el-input :readonly="readonly" v-else-if="this.mainData.cultural == 8" value="研究生" placeholder="" style="width: 130px"></el-input>
            <el-input style="width: 130px" :readonly="readonly" value="" v-else placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="家庭住址" class="row-item" prop="address">
            <el-input :readonly="readonly" resize="none" v-model="mainData.address" style="width: 100%" type="textarea" :rows="3"> </el-input>
          </el-form-item>
        </el-row>
      </div>
    </el-form>
  </el-col>
</template>

<script>
import Cookies from 'js-cookie'
import { getPaywayData } from '@/api/reception/prepare_order.js'
import { getPatientData, getNationData, getIssueWayData, getInspectData } from '@/api/funcdept/preregistration'
export default {
  props: ['patientCode'],
  data() {
    return {
      readonly: true,
      mainData: {},
      // 切换标签页
      activeName: 'first',
      // 会员类型参数
      selectData: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '体检者类型', //第二列标题
        url: '', //请求连接
        selectWidth: 220, //选择器宽度（选填，默认230）不加px
        bindValue: 'VIP',
      },
      // 籍贯参数
      selectData2: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '省份', //第二列标题
        url: '', //请求连接
        selectWidth: 220, //选择器宽度（选填，默认230）不加px
        bindValue: '山东省',
      },
      // 民族参数
      selectData3: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '民族', //第二列标题
        url: '', //请求连接
        selectWidth: 220, //选择器宽度（选填，默认230）不加px
        bindValue: '汉族',
      },
      // 体检类型
      idExamtypeList: [
        { id: '0', text: '健康体检' },
        { id: '1', text: '职业体检' },
        { id: '2', text: '综合' },
        { id: '3', text: '复查' },
      ],
      // 备单分类
      idExamclassList: [
        { id: '0', text: '健康类' },
        { id: '1', text: '职业类' },
        { id: '2', text: '综合类' },
        { id: '5', text: '入职类' },
        { id: '6', text: '疫苗类' },
        { id: '7', text: '其他类' },
      ],
      //  婚姻分类
      idMarriageList: [
        { text: '已婚', id: '1' },
        { text: '未婚', id: '2' },
        { text: '离异', id: '3' },
        { text: '丧偶', id: '4' },
        { text: '其他', id: '5' },
      ],
      // 证件类型
      countreportoccupationxmlList: [
        { text: '身份证', id: '1' },
        { text: '护照', id: '2' },
        { text: '军人证', id: '3' },
        { text: '港澳通行证/回乡证或台胞证', id: '4' },
      ],
      //  通知方式
      // idInformwayList: [{ text: '电子版报告', id: '1' }, { text: '短信', id: '2' }, { text: '电话', id: '3' }, { text: '快递', id: '4' }, { text: '团检发', id: '5' }, { text: '危急值团发', id: '6' }, { text: '个检发', id: '7' }, { text: '快递发', id: '8' }, { text: '团检按个发', id: '9' }, { text: '危急值个发', id: '10' },],
      //  文化程度
      culturalList: [],
      // 民族参数
      idNationData: [],
      idNationShow: [],
      // 主要信息数据
      mainInfo: {},
      //图片显示
      imgShow: false,
      //支付方式名单
      idInformwayList: [],
      // 表格加载中
      loading: false,
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      defaultImgUrl: undefined,
      //图片地址
      url: '',
      box: undefined,
      payWayList: [],
      payWayName: [],
    }
  },
  created() {
    this.getPaywayData()
    this.getNationData()
    this.getIssueWayData()
    this.bus.$on('refreshRight', (patientCode) => {
      this.getListData(patientCode, true)
    })
  },
  beforeDestroy() {
    this.bus.$off('refreshRight')
  },
  methods: {
    // 返回民族
    returnNation(val) {
      for (var i in this.idNationData) {
        if (this.idNationData[i].id == val) {
          return this.idNationData[i].name
        }
      }
    },
    // 获取通知方式
    getIssueWayData() {
      getIssueWayData().then((res) => {
        this.idInformwayList = res.data
      })
    },
    // 获取预付方式数据
    getPaywayData() {
      getPaywayData().then((res) => {
        this.payWayList = res.data
      })
    },
    // 获取表格数据
    getListData(patientCode, autoFill, noRefresh) {
      const queryParams = {
        patientCode,
        autoFill,
      }
      getInspectData(queryParams).then((response) => {
        if (response.code == 500) {
          this.$alert(response.msg, '提示')
          return
        }
        this.mainData = response.data.patientData
        this.box = this.mainData.isHmd
        if (response.data.group) {
          this.mainData.orgreservationgroupname = response.data.group.orgreservationgroupname
        }
        if (response) {
          if (response.data.patientData.idPayway != null) {
            var pay = response.data.patientData.idPayway.split(',')
            this.catchWay(pay, response)
          }
        }
        queryParams.patientCode = response.data.patientData.patientcode
        if (noRefresh != 1) {
          this.$emit('changeCode', queryParams.patientCode)
        } else {
          this.$emit('changeCode', queryParams.patientCode, true)
        }
      })
    },
    getInspect(patientCode, autoFill) {
      const queryParams = {
        patientCode,
        autoFill,
      }
      //抓取预付方式名称
      //图片数据
      getInspectData(queryParams).then((response) => {
        //团体名称
        if (response.code == 200) {
          this.mainData.examsuiteName = response.data.patientData.examsuiteName
          this.mainData.picture = response.data.picture
          this.url = this.imgPath + this.mainData.picture
          this.imgShow = true
          this.bus.$emit('getRightData', response)
        }
      })
    },
    catchWay(pay, response) {
      var payName
      for (var i in this.payWayList) {
        for (var j in pay) {
          if (this.payWayList[i].id == pay[j]) {
            if (j == 0) payName = this.payWayList[i].fzx
            else payName += ',' + this.payWayList[i].fzx
          }
        }
      }
      this.mainData.idPayway = payName
    },
    // 获取民族
    getNationData() {
      getNationData().then((res) => {
        this.idNationData = res.data

        this.idNationShow = this.idNationData
      })
    },
    // 民族返回值
    idNationChange(value) {
      this.idNationShow = []
      this.idNationData.forEach((el) => {
        if (this.lowerCaseChange(el.inputCode).indexOf(this.lowerCaseChange(value)) > -1 || el.name.indexOf(value) > -1) {
          this.idNationShow.push(el)
        }
      })
    },
    //锁定盒子的值
    boxCheck() {
      this.mainData.isHmd = this.box
    },
  },
}
</script>

<style lang="scss">
.funcdept-reception-left {
  .info-box {
    width: 100%;
    padding: 16px 24px;
    border: 1px solid #d4d6d9;

    .row-item {
      width: 98%;
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

  .input-unit {
    display: inline-block;
    width: 30px;
    text-align: center;
    font-size: 14px;
    color: #606266;
  }
}
</style>
