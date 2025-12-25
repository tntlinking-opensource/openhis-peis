<!-- 职业总检-查看详情-分科职业问诊 麦沃德科技 开发人:清风/半夏 -->
<template>
  <div class="add-container flex-direction-column">
    <div class="slection-list">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
        <el-form-item label="体检号" prop="patientcode">
          <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 230px"
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item prop="autoFill">
          <el-checkbox v-model="queryParams.autoFill" :true-label="1" :false-label="0">是否补全</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="handleQuery">读卡</el-button>
        </el-form-item>
        <el-form-item v-if="this.leftData.isVIP">
          <el-tag :type="this.leftData.isVIP == '普通会员' ? 'info' : this.leftData.isVIP == 'VIP' ? 'danger' : ''"><i
              class="el-icon-user"></i> {{ this.leftData.isVIP }}</el-tag>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <div class="table-box">
      <greetings ref="greetings" :mainDisabled="true" :leftData="leftData"></greetings>
    </div>
    <!-- 查看提醒对话框 -->
    <remind ref="remind"></remind>
  </div>
</template>
<script>
import { getRemindStr, getInquiryData } from '@/api/funcdept/section_list/index.js'
import greetings from "@/views/funcdept/section_list/list/index_left/professional_greetings.vue"
import remind from "@/views/funcdept/section_list/list/dialog/remind.vue";
export default {
  components: {
    greetings,
    remind,
  },
  props: [],
  data() {
    return {
      // 查询参数
      queryParams: {
        patientcode: undefined,
        autoFill: 1,
        ksID: 124
      },
      leftData: {},
    };
  },
  mounted() { },
  methods: {
    // 读卡
    handleQuery() {
      if (!this.queryParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.getOccupational(this.queryParams.patientcode);
    },
    // 获取详情
    getOccupational(patientcode) {
      this.queryParams.patientcode = patientcode
      getRemindStr({
        ksID: this.queryParams.ksID,
        patientcode: patientcode
      }).then(({ data }) => {
        if (data == 'true') {
          this.$refs.indexHeader.handleRemind()
        }
      })
      const clLoading = this.$loading({ target: '#sectionIndex' })
      getInquiryData(this.queryParams).then(({ data }) => {
        if (data.flag) {
          clLoading.close()
          this.$alert(data.flag, '提示', {
            confirmButtonText: '确定'
          })
          return
        }
        this.leftData = data
        this.leftData.ksID = this.queryParams.ksID
        if (data.peispatientConsultation.noKissTheCup == "1") {
          this.leftData.peispatientConsultation.kiss = "0"
        } else if (data.peispatientConsultation.betweenKissTheCup == "1") {
          this.leftData.peispatientConsultation.kiss = "1"
        } else if (data.peispatientConsultation.abstainLostKiss == "1") {
          this.leftData.peispatientConsultation.kiss = "2"
        } else if (data.peispatientConsultation.evermoreKiss == "1") {
          this.leftData.peispatientConsultation.kiss = "3"
        }
        clLoading.close()
      }).catch(err=>{
        console.log(err)
        clLoading.close()
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.slection-list {
  padding: 16px 0 0 0;
  border-bottom: #F6F7FB 4px solid;
}
</style>