<template>
  <el-col :span="9" style="overflow-y: auto; height: 795px" class="check-query-left">
    <el-row class="mb8" :span="24">
      <!-- 基本信息 -->
      <div class="base-info info-box">
        <div class="title">基本信息</div>
        <el-row type="flex" class="row-bg" justify="space-between">
          <el-col :span="24">
            <el-form :model="patientInfo" ref="queryForm" size="small" :inline="true" label-width="90px" label-position="right">
              <el-form-item label="团体名称" prop="orgName">
                <div style="width: 210px">{{ patientInfo.orgName }}</div>
              </el-form-item>
              <el-form-item label="团体部门" prop="orgDepart">
                <div style="width: 210px">{{ patientInfo.orgDepart }}</div>
              </el-form-item>
              <el-form-item label="团体分组" prop="orgreservationgroupname">
                <div style="width: 210px">{{ groupInfo ? groupInfo.orgreservationgroupname : '' }}</div>
              </el-form-item>
              <el-form-item label="体检套餐" prop="examsuiteName">
                <el-input v-model="patientInfo.examsuiteName" readonly style="width: 210px"> </el-input>
              </el-form-item>
              <el-form-item label="登记时间" prop="dateregister">
                <el-input v-model="patientInfo.dateregister" readonly style="width: 210px"> </el-input>
              </el-form-item>
              <el-form-item label="开单医师" prop="doctorreg">
                <el-input v-model="patientInfo.doctorreg" readonly style="width: 210px"> </el-input>
              </el-form-item>
              <el-form-item label="预约时间" prop="dateguidancereturned">
                <el-input v-model="patientInfo.dateguidancereturned" readonly style="width: 210px"> </el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </el-row>
    <!-- 主要信息 -->
    <div class="base-info info-box" style="width: 100%; margin-top: 8px">
      <!-- 个检 -->
      <div class="title">主要信息</div>
      <el-form :model="patientInfo" ref="patientInfo" size="small" :inline="true" label-width="80px" label-position="right">
        <el-form-item label="体检号" prop="patientcode">
          <div style="width: 210px">{{ patientInfo.patientcode }}</div>
        </el-form-item>
        <el-form-item label="会员类型" prop="idPatientclass">
          <el-input v-model="patientInfo.idPatientclass" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="patientname">
          <el-input v-model="patientInfo.patientname" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="体检类型" prop="idExamtype">
          <el-input v-model="patientInfo.idExamtype" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="性别" prop="idSex">
          <el-input v-model="patientInfo.idSex" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="patientInfo.age" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birthdate">
          <el-input :value="patientInfo.birthdate ? patientInfo.birthdate.split(' ')[0] : ''" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="婚姻" prop="idMarriage">
          <el-input v-model="patientInfo.idMarriage" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="证件号" prop="idcardno">
          <el-input type="tel" v-model="patientInfo.idcardno" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input type="tel" v-model="patientInfo.phone" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="前台须知" prop="qtxz">
          <div class="qtxz" @click="toNotice">{{ patientInfo.qtxz }}</div>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input type="textarea" :rows="3" resize="none" v-model="patientInfo.note" readonly style="width: 510px"> </el-input>
        </el-form-item>
        <el-form-item label="黑名单" prop="isHmd">
          <el-checkbox :value="patientInfo.isHmd">
            <el-input v-model="patientInfo.isHmdb" readonly style="width: 485px" placeholder="" :disabled="!patientInfo.isHmd"></el-input>
          </el-checkbox>
        </el-form-item>
        <el-form-item label="籍贯" prop="resarea">
          <el-input v-model="patientInfo.resarea" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="预付方式" prop="idPayway">
          <el-input v-model="patientInfo.idPayway" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="预付金额" prop="prepayment">
          <el-input v-model="patientInfo.prepayment" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="通知方式" prop="idInformway">
          <el-input v-model="patientInfo.idInformway" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="民族" prop="idNation">
          <el-input v-model="patientInfo.idNation" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="邮政编码" prop="yzbm">
          <el-input v-model="patientInfo.yzbm" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="邮寄地址" prop="yjaddress">
          <el-input v-model="patientInfo.yjaddress" readonly style="width: 510px" type="textarea" :rows="2" resize="none"> </el-input>
        </el-form-item>
        <el-form-item label="导引单备注" prop="guidancenote">
          <el-input v-model="patientInfo.guidancenote" readonly style="width: 510px" type="textarea" :rows="3" resize="none"> </el-input>
        </el-form-item>
        <el-form-item label="记账单位" prop="jzdw">
          <el-input v-model="patientInfo.jzdw" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="记账人" prop="jzdwr">
          <el-input v-model="patientInfo.jzdwr" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="审批人" prop="spr">
          <el-input v-model="patientInfo.spr" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="文化程度" prop="cultural">
          <el-input v-model="patientInfo.cultural" readonly style="width: 210px"> </el-input>
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="patientInfo.address" readonly style="width: 510px" type="textarea" :rows="2" resize="none"> </el-input>
        </el-form-item>
      </el-form>
    </div>
  </el-col>
</template>

<script>
import { physicalType, getCultural, marriageType } from '@/utils/dataList.js'
export default {
  props: ['patientData', 'payOptions', 'noticeOptions', 'nationOptions','memberOptions'],
  data() {
    return {
      // 体检者数据
      patientInfo: {},
      // 体检者团体数据
      groupInfo: {},
      // 体检类型列表
      physicalOptions: physicalType(),
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
      idMarriageList: marriageType(),
      //  文化程度
      culturalList: getCultural(),
    }
  },
  watch: {
    patientData: {
      handler(trueValue) {
        if (trueValue.patientData) {
          let newValue = JSON.parse(JSON.stringify(trueValue))
          this.memberOptions.forEach((el) => {
            if (el.levelId == newValue.patientData.idPatientclass) {
              newValue.patientData.idPatientclass = el.levelName
            }
          })
          this.physicalOptions.forEach((el) => {
            if (el.value == newValue.patientData.idExamtype) {
              newValue.patientData.idExamtype = el.label
            }
          })
          this.idExamclassList.forEach((el) => {
            if (el.id == newValue.patientData.idExamclass) {
              newValue.patientData.idExamclass = el.text
            }
          })
          this.idMarriageList.forEach((el) => {
            if (el.value == newValue.patientData.idMarriage) {
              newValue.patientData.idMarriage = el.label
            }
          })
          let idPayway = []
          this.payOptions.forEach((el) => {
            newValue.patientData.idPayway.split(',').forEach((val) => {
              if (el.id == val) {
                idPayway.push(el.fzx)
              }
            })
          })
          newValue.patientData.idPayway = idPayway.join(',')
          this.noticeOptions.forEach((el) => {
            if (el.id == newValue.patientData.idInformway) {
              newValue.patientData.idInformway = el.methodName
            }
          })
          this.nationOptions.forEach((el) => {
            if (el.id == newValue.patientData.idNation) {
              newValue.patientData.idNation = el.name
            }
          })
          this.culturalList.forEach((el) => {
            if (el.value == newValue.patientData.cultural) {
              newValue.patientData.cultural = el.label
            }
          })
          newValue.patientData.idSex = newValue.patientData.idSex ? '女' : '男'
          this.patientInfo = newValue.patientData
          this.groupInfo = newValue.group
        }
      },
    },
  },
  methods: {
    // 打开前台须知弹窗
    toNotice() {
      this.$alert(this.patientInfo.qtxz, '前台须知')
    },
  },
}
</script>

<style lang="scss">
.check-query-left {
  .info-box {
    width: 100%;
    padding: 16px 24px;
    border: 1px solid #d4d6d9;
  }

  .title {
    margin-bottom: 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
    text-align: left;
  }

  .qtxz {
    width: 510px;
    overflow: hidden; /*超出部分隐藏*/
    text-overflow: ellipsis; /* 超出部分显示省略号 */
    white-space: nowrap; /*规定段落中的文本不进行换行 */
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
}
</style>
