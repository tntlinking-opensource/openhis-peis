<!-- 骨密度 开发人： 麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column bone-density" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <div class="charge-item">
      <h3 style="margin-top: 0">
        【骨密度检测】
         <el-button type="primary" size="mini" icon="el-icon-sort" @click="switchZ">切换Z值</el-button>
         <el-button type="primary" size="mini" icon="el-icon-sort" @click="switchT">切换T值</el-button>
      </h3>
      <el-form size="small" :inline="true" @submit.native.prevent width="100%" id="setWidth" label-width="100px">
        <el-form-item label="是否弃检" style="width: 50%; margin-right: 0">
          <el-select v-model="tz.isUnchecked" :disabled="mainDisabled" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in isUnchecked" :label="item.text" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="危急值" style="width: 50%; margin-right: 0">
          <el-select v-model="tz.isDanger" :disabled="mainDisabled" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in isDanger" :label="item.text" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="T值" id="setWidthAll" v-if="modelType == 1">
          <el-input v-model="tz.inputResult" @change="changeInput" :readonly="mainDisabled" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="Z值" id="setWidthAll" v-if="modelType == 2">
          <el-input v-model="tz.inputResult" @change="changeInputZ" :readonly="mainDisabled" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="检查描述" id="setWidthAll">
          <el-input v-model="tz.ms" :readonly="mainDisabled" type="textarea" placeholder="请输入" clearable />
        </el-form-item>
      </el-form>
    </div>
    <div class="charge-item">  
      <h3 style="margin-top: 0">【骨密度检测：骨密度诊断】</h3>
      <el-form size="small" :inline="true" @submit.native.prevent id="gmdzdWidth" label-width="100px">
        <el-form-item prop="checkList" label="" style="width: 100%">
          <el-checkbox-group v-model="gmdzd.nodule" style="pointer-events: none" @change="itemChange()">
            <el-checkbox label="正常" :value="zcCheckList.id" style="min-width: 150px" v-if="modelType == 1"></el-checkbox>
            <el-checkbox label="骨量减少" :value="gljsCheckList.id" style="min-width: 150px" v-if="modelType == 1"></el-checkbox>
            <el-checkbox label="骨质疏松" :value="gzssCheckList.id" style="min-width: 150px" v-if="modelType == 1"></el-checkbox>
            <el-checkbox label="正常" :value="zcCheckList.id" style="min-width: 150px" v-if="modelType == 2"></el-checkbox>
            <el-checkbox label="轻度骨强度不足" :value="gljsCheckList.id" style="min-width: 150px" v-if="modelType == 2"></el-checkbox>
            <el-checkbox label="骨强度不足" :value="gzssCheckList.id" style="min-width: 150px" v-if="modelType == 2"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="检查描述" style="width: 100%">
          <el-input v-model="gmdzd.ms" :readonly="mainDisabled" type="textarea" placeholder="请输入" clearable />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { dmbshenheGmd, getBmdCheckedDataGmd, caseReverseGmd } from '@/api/funcdept/section_list/index.js'
import { getDeptDataConfig } from '@/api/system/config.js'

export default {
  props: ['leftData', 'mainDisabled'],
  data() {
    return {
      gmd_low: -2,
      gmd_high: -1,
      // T值 Z值
      modelType:1,
      // 检查参数
      gmdzd: {
        nodule: [],
        ms: undefined,
      },
      tz: {
        isUnchecked: '0',
        isDanger: '0',
        inputResult: null,
        ms: undefined,
      },
      main: {},
      options: [
        {
          value: '选项1',
          label: '良好',
        },
        {
          value: '选项2',
          label: '差',
        },
        {
          value: '选项3',
          label: '一般',
        },
      ],
      // 表格加载中
      loading: false,
      // 选中的数据
      selectData: [],
     
      zcCheckList: {},
      gljsCheckList: {},
      gzssCheckList: {},
      isUnchecked: [
        { id: '1', text: '是' },
        { id: '0', text: '否' },
      ],
      isDanger: [
        { id: '0', text: '无' },
        { id: '1', text: '低' },
        { id: '2', text: '中' },
        { id: '3', text: '高' },
      ],
      // 审核时禁用按钮
      forbiddenAudit: false,
    }
  },
  created() {
    // 如果是华欧医院,gmd_low为-2.5
    // console.log("分中心id",this.$getCookie('cid'));
    //  if (this.$getCookie('cid') == "ff8080817f6855ac017f7c4d04940f5d") {
    //   this.gmd_low = -2.5
    // }
    //获取骨密度范围
    getDeptDataConfig()
      .then((res) => {
        if (res.code != 200) {
            this.$alert('获取数据异常', '提醒').then(() => {
            })
          } else {
            this.gmd_low = res.data.rangeData.find(item => item.deptName == '骨密度检测').low;
            console.log("获取骨密度gmd_low值是",res.data.rangeData.find(item => item.deptName == '骨密度检测').low);
          }
        })  
      .catch(() => {  
    })
    this.bus.$off('boneDensityHandleGmd')
    this.bus.$off('conclusionsHandleGmd')
    this.bus.$off('caseReverseHandleGmd')
    //审核
    this.bus.$on('boneDensityHandleGmd', (rightData) => {
      this.emitAudit(rightData)
    })
    //小结
    this.bus.$on('conclusionsHandleGmd', () => {
      this.conclusionsHandle()
    })

    this.bus.$on('caseReverseHandleGmd', () => {
      this.emitUnAudit()
    })
    if (this.leftData.bmdData) {
      this.zcCheckList = this.leftData.bmdData.zc
      this.gljsCheckList = this.leftData.bmdData.gljs
      this.gzssCheckList = this.leftData.bmdData.gzss
    }
  },
  watch: {
    leftData: {
      handler(value) {

        if (value) {
          this.gmd_high = value.gmd_high
          // this.gmd_low = value.gmd_low
          this.zcCheckList = value.bmdData.zc
          this.gljsCheckList = value.bmdData.gljs
          this.gzssCheckList = value.bmdData.gzss
        }
      },
      immediate: false,
      deep: true,
    },
  },
  methods: {
    // 切换Z值
    switchZ(){
        this.clearLeftData10()
        console.log("切换Z值");
        this.modelType = 2
        this.gmd_low = -2
        this.gmdzd.nodule = []; 
    },
    // 切换T值  
    switchT(){
        this.clearLeftData10()
        console.log("切换T值");
        this.modelType = 1
        this.gmdzd.nodule = []; 
    },
    //changeInput事件 T值  
    changeInput() { 
      let newVal = this.tz.inputResult
      if (newVal === '') return
      let _newVal = Number(newVal)

      // if (!(_newVal >= 0 || _newVal <= -1)) return
      this.tz.ms = 'T值：' + _newVal + ';'
      this.gmdzd.nodule = []
      if (_newVal >= this.gmd_high) {
        this.gmdzd.ms = '骨密度诊断：正常;'
        this.gmdzd.nodule.push(this.leftData.bmdData.zc.name)
      } else if (_newVal >= this.gmd_low) {
        this.gmdzd.ms = '骨密度诊断：骨量减少;'
        this.gmdzd.nodule.push(this.leftData.bmdData.gljs.name)
      } else if (_newVal < this.gmd_low) {
        this.gmdzd.ms = '骨密度诊断：骨质疏松;'
        this.gmdzd.nodule.push(this.leftData.bmdData.gzss.name)
      }
      console.log("T值gljsCheckList的值",this.gljsCheckList);
      console.log("T值gmdzd.nodule的值",this.gmdzd.nodule);


    },
    //changeInput事件 Z值
    changeInputZ() {
      let newVal = this.tz.inputResult
      if (newVal === '') return
      let _newVal = Number(newVal)

      // if (!(_newVal >= 0 || _newVal <= -1)) return
      this.tz.ms = 'Z值：' + _newVal + ';'
      this.gmdzd.nodule = []
      if (_newVal >= this.gmd_high) {
        this.gmdzd.ms = '骨密度诊断：正常;'
        this.gmdzd.nodule.push(this.leftData.bmdData.zc.name)
      } else if (_newVal >= this.gmd_low) {
        this.gmdzd.ms = '骨密度诊断：轻度骨强度不足;'
        this.gmdzd.nodule.push("轻度骨强度不足")
      } else if (_newVal < this.gmd_low) {
        this.gmdzd.ms = '骨密度诊断：骨强度不足;'
        this.gmdzd.nodule.push('骨强度不足')
      }
      console.log("Z值gljsCheckList的值",this.gljsCheckList);
      console.log("Z值gmdzd.nodule的值",this.gmdzd.nodule);

      
    },
    //清空反显值
    clearLeftData10() {
      this.tz.inputResult = ''
      this.tz.ms = ''
      this.gmdzd.ms = ''
      this.gmdzd.nodule = ''
    },
    getDetailGmd(ksId, patientcode) {
      console.log(1233);
      
      getBmdCheckedDataGmd({ ksId, patientcode }).then((response) => {
        if (response.data) {
          let tz = {
            inputResult: '',
            isDanger: '0',
            isUnchecked: '0',
            ms: '',
          }
          let gmdzd = {
            ms: '',
            nodule: '',
          }
          if (response.data.gmdzd) {
            gmdzd = Object.assign(gmdzd, {
              nodule: response.data.gmdzd.nodule,
              ms: response.data.gmdzd.ms,
            })
          }
          if (response.data.tz) {
            
            // 如果是Z值,则赋值2
            if(response.data.tz.ms.charAt(0) == 'Z'){
              this.modelType = 2
            }else{
              this.modelType = 1
            }

            tz = Object.assign(tz, {
              inputResult: response.data.tz.inputResult || undefined,
              isDanger: response.data.tz.isDanger || '0',
              isUnchecked: response.data.tz.isUnchecked || '0',
              ms: response.data.tz.ms,
            })
          }
          if(this.modelType == 1){
            if (gmdzd.nodule == this.gzssCheckList.id) {
              gmdzd.nodule = ['骨质疏松']
            } else if (gmdzd.nodule == this.gljsCheckList.id) {
              gmdzd.nodule = ['骨量减少']
            } else if (gmdzd.nodule == this.zcCheckList.id) {
              gmdzd.nodule = ['正常']
            }
          }else if(this.modelType == 2){
            if (gmdzd.nodule == this.gzssCheckList.id) {
              gmdzd.nodule = ['骨强度不足']
            } else if (gmdzd.nodule == this.gljsCheckList.id) {
              gmdzd.nodule = ['轻度骨强度不足']
            } else if (gmdzd.nodule == this.zcCheckList.id) {
              gmdzd.nodule = ['正常']
            }
          }
          
          this.gmdzd = gmdzd
          this.tz = tz
          let auditInfo = localStorage.getItem('section' + this.$route.meta.ksID) ? JSON.parse(localStorage.getItem('section' + this.$route.meta.ksID)) : ''
          this.main = response.data.main || {}
          this.main.writeName = response.data && response.data.lrr ? response.data.lrr : auditInfo ? auditInfo.writeName : decodeURIComponent(this.getCookie('username'))
          this.main.writeId = response.data.main && response.data.main.lrrId ? response.data.main.lrrId : auditInfo ? auditInfo.writeId : this.getCookie('userNo')
          this.main.writeTime = response.data.main && response.data.main.writeTime ? response.data.main.writeTime : this.$getDate()
          this.main.rummagerName = response.data.main && response.data.main.rummagerName ? response.data.main.rummagerName : auditInfo ? auditInfo.rummagerName : decodeURIComponent(this.getCookie('username'))
          this.main.rummagerId = response.data.main && response.data.main.rummagerId ? response.data.main.rummagerId : auditInfo ? auditInfo.rummagerId : this.getCookie('userNo')
          this.main.rummagerTime = response.data.main && response.data.main.rummagerTime ? response.data.main.rummagerTime : this.$getDate()
          this.bus.$emit('auditRightData', this.main)
        }
      })
    },
    //小结
    conclusionsHandle() {
      let conclusions = this.tz.ms + this.gmdzd.ms
      this.bus.$emit('conclusionsResultGmd', conclusions)
    },
    //审核
    emitAudit(rightData) {
      if (this.forbiddenAudit) {
        return
      }
      this.forbiddenAudit = true
      if (!rightData.sectionResultMain.rummagerId) {
        this.$modal.msgError('请选择检查人')
        return
      } else if (!rightData.sectionResultMain.rummagerTime) {
        this.$modal.msgError('请选择检查时间')
        return
      }
      let griddata = {}
      if (this.tz.inputResult >= -1) {
        griddata = {
          jcxmId: this.leftData.bmdData.zc.inspectId,
          jcxm: this.leftData.bmdData.zc.bodyDetail,
          tzc: this.leftData.bmdData.zc.name,
          jlcId: this.leftData.bmdData.zc.resultId,
          tzcId: this.leftData.bmdData.zc.id,
          name: this.leftData.bmdData.zc.name,
        }
      } else if (this.tz.inputResult == -2) {
        griddata = {
          jcxmId: this.leftData.bmdData.gljs.inspectId,
          jcxm: this.leftData.bmdData.gljs.bodyDetail,
          tzc: this.leftData.bmdData.gljs.name,
          jlcId: this.leftData.bmdData.gljs.resultId,
          tzcId: this.leftData.bmdData.gljs.id,
          name: this.leftData.bmdData.gljs.name,
        }
      } else if (this.tz.inputResult < -2) {
        griddata = {
          jcxmId: this.leftData.bmdData.gzss.inspectId,
          jcxm: this.leftData.bmdData.gzss.bodyDetail,
          tzc: this.leftData.bmdData.gzss.name,
          jlcId: this.leftData.bmdData.gzss.resultId,
          tzcId: this.leftData.bmdData.gzss.id,
          name: this.leftData.bmdData.gzss.name,
        }
      }
      rightData.griddata = [griddata]
      if (!rightData.sectionResultMain.conclusions) {
        this.$confirm('小结为空,是否生成小结?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.conclusionsHandle()
          })
          .then(() => {
            this.executeAudit(rightData)
          })
          .catch((error) => {
            this.forbiddenAudit = false
            console.error(error)
          })
      } else {
        this.executeAudit(rightData)
      }
    },
    // 执行审核
    executeAudit(rightData) {
      var getKey = [
        {
          key: '886',
          value: this.tz.inputResult,
        },
        {
          key: '3913',
          value: this.tz.inputResult >= -1 ? 'true' : 'false',
        },
        {
          key: '3915',
          value: this.tz.inputResult == -2 ? 'true' : 'false',
        },
        {
          key: '5592',
          value: this.tz.inputResult < -2 ? 'true' : 'false',
        },
        {
          key: '886qj',
          value: this.tz.isUnchecked,
        },
        {
          key: '886wjz',
          value: this.tz.isDanger,
        },
        {
          key: '886textarea',
          value: this.tz.ms,
        },
        {
          key: '887textarea',
          value: this.gmdzd.ms,
        },
      ]
      dmbshenheGmd({
        gmdzd_ms: this.gmdzd.ms,
        griddata: rightData.griddata,
        jcr: rightData.sectionResultMain.rummagerId,
        jcsj: rightData.sectionResultMain.rummagerTime,
        jsondata: getKey, //左栏数据集合
        ksID: this.$route.meta.ksID,
        lrr: rightData.sectionResultMain.writeId,
        lrsj: rightData.sectionResultMain.writeTime,
        patientCode: rightData.peispatient.patientcode,
        tz_ms: this.tz.ms,
        xjdata: rightData.sectionResultMain.conclusions,
      })
        .then(() => {
          localStorage.setItem(
            'section' + this.$route.meta.ksID,
            JSON.stringify({
              rummagerName: rightData.sectionResultMain.rummagerName,
              rummagerId: rightData.sectionResultMain.rummagerId,
              writeName: rightData.sectionResultMain.writeName,
              writeId: rightData.sectionResultMain.writeId,
            })
          )
          this.$emit('handleAudit')
          this.$modal.msgSuccess('审核成功')
          this.forbiddenAudit = false
        })
        .catch((error) => {
          console.error(error)
          this.forbiddenAudit = false
        })
    },
    //反审核
    emitUnAudit() {
      caseReverseGmd({
        ksID: this.$route.meta.ksID,
        patientcode: this.leftData.peispatient.patientcode,
      }).then((res) => {
        this.$emit('handleReAudit')
        this.$modal.msgSuccess('反审核成功')
      })
    },
    // 获取cookie值
    getCookie(cookieName) {
      const strCookie = document.cookie
      const cookieList = strCookie.split(';')
      for (let i = 0; i < cookieList.length; i++) {
        const arr = cookieList[i].split('=')
        if (cookieName === arr[0].trim()) {
          return arr[1]
        }
      }
      return ''
    },
  },
}
</script>

<style lang="scss">
.bone-density {
  h3 {
    margin: 5px 0 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .charge-item {
    padding: 20px;
    padding-bottom: 0;

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 10px;
    }

    .el-checkbox-group:first-child {
      margin-left: 100px;
    }
  }
}
#setWidth {
  .el-form-item__content {
    width: calc(100% - 100px);
  }
  #setWidthAll {
    width: 100%;
    .el-form-item__content {
      width: calc(100% - 100px);
    }
  }
  .el-textarea__inner {
    min-height: 58px !important;
  }
}
#gmdzdWidth {
  .el-form-item__content {
    width: calc(100% - 100px);
  }
  .el-textarea__inner {
    min-height: 58px !important;
  }
}
</style>
