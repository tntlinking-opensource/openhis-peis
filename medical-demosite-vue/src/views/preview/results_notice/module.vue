<template>
  <div style="font-family: Simsun">
    <!-- 复查通知书 -->
    <div class="review-print" ref="reportData3" v-if="type == 3">
      <div class="title">职业健康检查复查通知书(用人单位)</div>
      <div class="info-list">
        <div class="info-item">
          <div class="show-key">体检编号:</div>
          <div class="show-value">{{ printData.patientcode }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">用人单位名称:</div>
          <el-input v-model="printData.company" placeholder="" v-if="isModify" style="width: 400px; text-indent: 0"></el-input>
          <div class="show-value" v-else>{{ printData.company }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">劳动者姓名:</div>
          <div class="show-value">{{ printData.name }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">身份证号:</div>
          <div class="show-value">{{ printData.idcardno }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">工种:</div>
          <div class="show-value">{{ printData.trades || '无' }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">接害工龄:</div>
          <div class="show-value" style="text-align: center">{{ printData.jhgln }}</div>
          年
          <div class="show-value" style="text-align: center">{{ printData.jhgly }}</div>
          月
        </div>
        <div class="info-item" style="width: 100%">
          <div class="show-key">职业病危害因素:</div>
          <div class="show-value">{{ printData.harmName }}</div>
        </div>
        <div class="info-item" style="width: 100%">
          <div class="show-key">职业健康检查种类:</div>
          <div class="show-value">{{ printData.medicaltype }}</div>
        </div>
      </div>
      <div>
        <span class="underline">{{ printData.name }}</span
        >于{{ printData.dateStart }}经我机构职业健康检查，发现与接触职业病危害因素<span class="underline">{{ printData.harmName }}</span>
        指标异常， 建议 15 个工作日之内完成
        <el-input v-model="printData.items" placeholder="" v-if="isModify" style="width: 150px; text-indent: 0"></el-input>
        <span class="show-value" v-else>{{ printData.items }}</span>
        复查项目。
      </div>
      <div>特此告知。</div>
      <div style="text-align: right; margin-right: 50px">
        <span>职业健康检查机构</span>
      </div>
      <div style="text-align: right; margin-right: 70px">（盖章）</div>
      <div style="text-align: right; display: flex; margin-right: 50px">
        <div style="flex: 1"></div>
        <div>年</div>
        <div style="width: 40px"></div>
        <div>月</div>
        <div style="width: 40px"></div>
        <div>日</div>
      </div>
      <div style="display: flex">
        <div class="show-key">签收人:</div>
        <div class="underline"></div>
      </div>
      <div style="display: flex; margin-top: 8px">
        <div style="width: 50px"></div>
        <div>年</div>
        <div style="width: 40px"></div>
        <div>月</div>
        <div style="width: 40px"></div>
        <div>日</div>
      </div>
      <div style="font-size: 12px; margin-left: 50px; margin-bottom: 30px">（本通知书一式两份，查体机构留存一份）</div>

      <div class="title">职业健康检查复查通知书(劳动者)</div>
      <div class="info-list">
        <div class="info-item">
          <div class="show-key">体检编号:</div>
          <div class="show-value">{{ printData.patientcode }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">用人单位名称:</div>
          <el-input v-model="printData.company" placeholder="" v-if="isModify" style="width: 400px; text-indent: 0"></el-input>
          <div class="show-value" v-else>{{ printData.company }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">劳动者姓名:</div>
          <div class="show-value">{{ printData.name }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">身份证号:</div>
          <div class="show-value">{{ printData.idcardno }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">工种:</div>
          <div class="show-value">{{ printData.trades || '无' }}</div>
        </div>
        <div class="info-item">
          <div class="show-key">接害工龄:</div>
          <div class="show-value" style="text-align: center">{{ printData.jhgln }}</div>
          年
          <div class="show-value" style="text-align: center">{{ printData.jhgly }}</div>
          月
        </div>
        <div class="info-item" style="width: 100%">
          <div class="show-key">职业病危害因素:</div>
          <div class="show-value">{{ printData.harmName }}</div>
        </div>
        <div class="info-item" style="width: 100%">
          <div class="show-key">职业健康检查种类:</div>
          <div class="show-value">{{ printData.medicaltype }}</div>
        </div>
      </div>
      <div>
        <span class="underline">{{ printData.name }}</span
        >于{{ printData.dateStart }}经我机构职业健康检查，发现与接触职业病危害因素<span class="underline">{{ printData.harmName }}</span>
        指标异常， 建议 15 个工作日之内完成
        <el-input v-model="printData.items" placeholder="" v-if="isModify" style="width: 150px; text-indent: 0"></el-input>
        <span class="show-value" v-else>{{ printData.items }}</span>
        复查项目。
      </div>
      <div>特此告知。</div>
      <div style="text-align: right; margin-right: 50px">
        <span>职业健康检查机构</span>
      </div>
      <div style="text-align: right; margin-right: 70px">（盖章）</div>
      <div style="text-align: right; display: flex; margin-right: 50px">
        <div style="flex: 1"></div>
        <div>年</div>
        <div style="width: 40px"></div>
        <div>月</div>
        <div style="width: 40px"></div>
        <div>日</div>
      </div>
      <div style="display: flex">
        <div class="show-key">签收人:</div>
        <div class="underline"></div>
      </div>
      <div style="display: flex; margin-top: 8px">
        <div style="width: 50px"></div>
        <div>年</div>
        <div style="width: 40px"></div>
        <div>月</div>
        <div style="width: 40px"></div>
        <div>日</div>
      </div>
      <div style="font-size: 12px; margin-left: 50px">（本通知书一式两份，查体机构留存一份）</div>
    </div>
    <!-- 禁忌症告知书 -->
    <div ref="reportData2" v-else-if="type == 2">
      <template v-if="isJinDu">
        <div class="contraindication">
          <div class="title">职业禁忌证告知书</div>
          <div class="info-list">
            <div class="info-item">
              <div class="info-item-title">体检编号：{{ printData.patientcode }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">
                用人单位名称：
                <el-input v-if="isModify" v-model="printData.orgname" placeholder="" style="width: 300px; text-indent: 0"></el-input>
                <span v-else>{{ printData.orgname }}</span>
              </div>
            </div>
            <div class="info-item">
              <div class="info-item-title">劳动者姓名：{{ printData.name }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">身份证号：{{ printData.idcardno }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">工种：{{ printData.trades }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">接害工龄：{{ printData.jhgl }}月</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">
                职业病危害因素：
                <el-input v-if="isModify" v-model="printData.reason" placeholder="" style="width: 200px; text-indent: 0"></el-input>
                <span v-else>{{ printData.reason }}</span>
              </div>
            </div>
            <div class="info-item">
              <div class="info-item-title">职业健康检查种类：{{ printData.medicaltype }}</div>
            </div>
          </div>
          <div style="text-indent: 2em">
            劳动者{{ printData.name }}，{{ printData.year }} 年 {{ printData.month }} 月 {{ printData.day }} 日经我机构职业健康检查，发现其所从事岗位职业病危害因素
            <el-input v-if="isModify" v-model="printData.reason" placeholder="" style="width: 200px; text-indent: 0"></el-input>
            <span v-else>{{ printData.reason }}</span>
            的职业禁忌证，根据《职业健康检查管理办法》第十八条“职业健康检查机构发现职业禁忌的，应当及时告知用人单位和劳动者”特下此告知书， 你单位应按相关法律法规的要求对该劳动者进行妥善安置。
          </div>
          <div style="text-indent: 2em">特此告知。</div>
          <div style="display: flex; margin-top: 20px">
            <div style="flex: 1"></div>
            <div style="max-width: 90%; margin-right: 30px">职业健康检查机构{{ printData.branch ? ':' + printData.branch : '' }}（盖章）</div>
          </div>
          <div style="display: flex">
            <div style="flex: 9"></div>
            <div>{{ printData.sysdate }}</div>
            <div style="flex: 2"></div>
          </div>
          <div style="display: flex; margin-top: 50px">
            <div style="flex: 9">签收人：</div>
            <!-- <div>{{ printData.sysdate }}</div> -->
            <div style="flex: 1"></div>
          </div>
          <div class="note">
            <div>注:</div>
            <div>1、本告知书一式三份：劳动者一份，用人单位一份，体检机构存档一份。</div>
            <div style="display: flex">
              <div>2、</div>
              <div>体检机构发放告知书时，应有当事人（劳动者和用人单位）或授权委托人签收登记，签 收登记本应记录当事人或授权委托人的姓名，身份证号，日期等信息。</div>
            </div>
          </div>
        </div>
      </template>
      <template v-else>
        <div class="contraindication">
          <div class="title">职业禁忌证告知书（用人单位）</div>
          <div class="info-list">
            <div class="info-item">
              <div class="info-item-title">体检编号：{{ printData.patientcode }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">
                用人单位名称：
                <el-input v-if="isModify" v-model="printData.orgname" placeholder="" style="width: 300px; text-indent: 0"></el-input>
                <span v-else>{{ printData.orgname }}</span>
              </div>
            </div>
            <div class="info-item">
              <div class="info-item-title">劳动者姓名：{{ printData.name }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">身份证号：{{ printData.idcardno }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">工种：{{ printData.trades }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">接害工龄：{{ printData.jhgl }}月</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">
                职业病危害因素：
                <el-input v-if="isModify" v-model="printData.reason" placeholder="" style="width: 200px; text-indent: 0"></el-input>
                <span v-else>{{ printData.reason }}</span>
              </div>
            </div>
            <div class="info-item">
              <div class="info-item-title">职业健康检查种类：{{ printData.medicaltype }}</div>
            </div>
          </div>
          <div style="text-indent: 2em">
            劳动者{{ printData.name }}，{{ printData.year }} 年 {{ printData.month }} 月 {{ printData.day }} 日经我机构职业健康检查，发现: {{ printData.result }} 属其所从事岗位职业病危害因素
            <el-input v-if="isModify" v-model="printData.reason" placeholder="" style="width: 200px; text-indent: 0"></el-input>
            <span v-else>{{ printData.reason }}</span>
            的职业禁忌证，根据《职业健康检查管理办法》第十八条“职业健康检查机构发现职业禁忌的，应当及时告知用人单位和劳动者”特下此告知书， 你单位应按相关法律法规的要求对该劳动者进行妥善安置。
          </div>
          <div style="text-indent: 2em">特此告知。</div>
          <div style="display: flex; margin-top: 20px">
            <div style="flex: 1"></div>
            <div style="max-width: 90%; margin-right: 30px">职业健康检查机构{{ printData.branch ? ':' + printData.branch : '' }}（盖章）</div>
          </div>
          <div style="display: flex">
            <div style="flex: 9"></div>
            <div>{{ printData.sysdate }}</div>
            <div style="flex: 2"></div>
          </div>
          <div style="display: flex; margin-top: 50px">
            <div style="flex: 9">签收人：</div>
            <!-- <div>{{ printData.sysdate }}</div> -->
            <div style="flex: 1"></div>
          </div>
          <div class="note">
            <div>注: 《职业禁忌证告知书》一式两份， 用人单位、职业健康检查机构各一份。</div>
          </div>
        </div>
        <div class="contraindication">
          <div class="title">职业禁忌证告知书（劳动者）</div>
          <div class="info-list">
            <div class="info-item">
              <div class="info-item-title">体检编号：{{ printData.patientcode }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">
                用人单位名称：
                <el-input v-if="isModify" v-model="printData.orgname" placeholder="" style="width: 300px; text-indent: 0"></el-input>
                <span v-else>{{ printData.orgname }}</span>
              </div>
            </div>
            <div class="info-item">
              <div class="info-item-title">劳动者姓名：{{ printData.name }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">身份证号：{{ printData.idcardno }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">工种：{{ printData.trades }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">接害工龄：{{ printData.jhgl }}</div>
            </div>
            <div class="info-item">
              <div class="info-item-title">职业病危害因素：{{ printData.reason }}</div>
              <div class="info-item-text"></div>
            </div>
            <div class="info-item">
              <div class="info-item-title">职业健康检查种类：{{ printData.medicaltype }}</div>
            </div>
          </div>
          <div style="text-indent: 2em">
            劳动者{{ printData.name }}，{{ printData.year }} 年 {{ printData.month }} 月 {{ printData.day }} 日经我机构职业健康检查，发现: {{ printData.result }} 属于其所从事岗位职业病危害因素
            <el-input v-if="isModify" v-model="printData.reason" placeholder="" style="width: 200px; text-indent: 0"></el-input>
            <!-- <span v-else>{{ printData.reason }}</span> 25-1-14 添加有result显示result,没有result显示reason -->
            <span v-else>{{ printData.reason }}</span>


            的职业禁忌证，根据《职业健康检查管理办法》第十八条“职业健康检查机构发现职业禁忌的，应当及时告知用人单位和劳动者”特下此告知书， 你单位应按相关法律法规的要求对该劳动者进行妥善安置。
          </div>
          <div style="text-indent: 2em">特此告知。</div>
          <div style="display: flex; margin-top: 20px">
            <div style="flex: 1"></div>
            <div style="max-width: 90%; margin-right: 30px">职业健康检查机构{{ printData.branch ? ':' + printData.branch : '' }}（盖章）</div>
          </div>
          <div style="display: flex">
            <div style="flex: 9"></div>
            <div>{{ printData.sysdate }}</div>
            <div style="flex: 2"></div>
          </div>
          <div style="display: flex; margin-top: 50px">
            <div style="flex: 9">签收人：</div>
            <!-- <div>{{ printData.sysdate }}</div> -->
            <div style="flex: 1"></div>
          </div>
          <div class="note">
            <div>注: 《职业禁忌证告知书》一式两份， 劳动者、职业健康检查机构各一份。</div>
          </div>
        </div>
      </template>
    </div>
    <!-- 疑似职业病告知书 -->
    <div class="contraindication" ref="reportData1" v-else-if="type == 1">
      <div class="title" style="font-size: 26px">疑似职业病告知书（GBZ/T 325—2022）</div>
      <div>
        <el-input v-if="isModify" v-model="printData.orgname" placeholder="" style="width: 300px; text-indent: 0"></el-input>
        <span v-else style="border-bottom: 1px solid #000">{{ printData.orgname }}</span
        >、 <span style="border-bottom: 1px solid #000">{{ printData.name }}</span
        >：
      </div>
      <div style="text-indent: 2em">
        <span style="border-bottom: 1px solid #000">{{ printData.year }}</span>
        年 <span style="border-bottom: 1px solid #000">{{ printData.month }}</span> 月 <span style="border-bottom: 1px solid #000">{{ printData.day }}</span> 日本机构在职业健康检查中发现 <span style="border-bottom: 1px solid #000">{{ printData.name }}</span
        >、
        <span style="border-bottom: 1px solid #000">{{ printData.idcardno }}</span>、
        <el-input v-if="isModify" v-model="printData.posistiveDepts" placeholder="" style="width: 300px; text-indent: 0"></el-input>
        <span v-else style="border-bottom: 1px solid #000">{{ ' ' + printData.posistiveDepts + ' ' }}</span>
        等。根据目前材料，界定<span style="border-bottom: 1px solid #000">{{ printData.name }}</span
        >为
        <el-input v-if="isModify" v-model="printData.result" placeholder="" style="width: 300px; text-indent: 0"></el-input>
        <span v-else style="border-bottom: 1px solid #000">{{ ' ' + printData.result + ' ' }}</span>
        疑似职业病病人。
      </div>
      <div style="text-indent: 2em">你单位应当在 30 日内安排对疑似职业病病人进行职业病诊断；在疑似职业病病人诊断或者医学观察期间，不得解除或者终止与其订立的劳动合同。疑似职业病病人在诊断、医学观察期间的费用， 由用人单位承担。</div>
      <div style="text-indent: 2em">劳动者可以在用人单位所在地、本人户籍所在地或者经常居住地依法承担职业病诊断的医疗卫生机构进行职业病诊断。</div>
      <div style="text-indent: 2em">特此告知。</div>
      <div style="display: flex; margin-top: 20px">
            <div style="flex: 1"></div>
            <div style="max-width: 90%; margin-right: 30px">职业健康检查机构{{ printData.branch ? ':' + printData.branch : '' }}（盖章）</div>
      </div>
      <div style="text-align: right; display: flex; margin-right: 15px">
        <div style="flex: 1"></div>
        <div>年</div>
        <div style="width: 20px"></div>
        <div>月</div>
        <div style="width: 20px"></div>
        <div>日</div>
      </div>
      <div style="display: flex; margin-top: 20px">
        <div class="show-key" style="width: 70px; white-space: nowrap">签收人:</div>
        <div style="border-bottom: 1px solid #000; width: 100px; margin-left: 10px"></div>
        <div style="flex: 1">（用人单位名称）</div>
        <div style="text-align: right; display: flex; margin-right: 15px">
          <div>年</div>
          <div style="width: 20px"></div>
          <div>月</div>
          <div style="width: 20px"></div>
          <div>日</div>
        </div>
      </div>
      <div style="display: flex; margin-top: 10px">
        <div class="show-key" style="width: 70px"></div>
        <div style="border-bottom: 1px solid #000; width: 100px; margin-left: 10px"></div>
        <div style="flex: 1">（劳动者姓名）</div>
        <div style="text-align: right; display: flex; margin-right: 15px">
          <div>年</div>
          <div style="width: 20px"></div>
          <div>月</div>
          <div style="width: 20px"></div>
          <div>日</div>
        </div>
      </div>

      <div class="note">注： 《疑似职业病告知书》一式三份， 用人单位、劳动者、职业健康检查机构各一份。</div>
    </div>

    <!-- 打印按钮 -->
    <div style="height: 40px; width: 100%" class="placeholder"></div>
    <div class="print-fixed">
      <el-button type="primary" @click="handleModify" v-if="isModify">修改</el-button>
      <el-button type="primary" @click="handlePrint" v-else>打印</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['printData', 'type', 'isModify', 'centerName', 'isJinDu', 'currentDate'],
  methods: {
    // 执行修改
    handleModify() {
      this.$emit('handleUpdata')
    },
    // 执行打印
    handlePrint() {
      if (this.type == 3) {
        this.$print(this.$refs.reportData3)
      } else if (this.type == 2) {
        this.$print(this.$refs.reportData2)
      } else {
        this.$print(this.$refs.reportData1)
      }
    },
  },
}
</script>

<style lang="scss">
@media print {
  .review-print,
  .contraindication {
    display: block;
    page-break-after: always;
  }
  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }
}

.print-fixed {
  position: fixed;
  left: 50%;
  bottom: 10px;
  transform: translateX(-50%);
  text-align: center;
  width: 260px;
  height: 35px;
  cursor: pointer;
}

.review-print {
  min-height: 800px;
  padding: 30px;
  font-size: 18px;
  line-height: 30px;
  .title {
    margin-bottom: 20px;
    font-size: 22px;
    font-weight: 600;
    text-align: center;
  }
  .show-key {
    margin-right: 5px;
  }
  .show-value {
    flex: 1;
    // text-decoration: underline;
    border-bottom: 1px solid #000;
  }
  .info-list {
    display: flex;
    flex-wrap: wrap;
    .info-item {
      display: flex;
      width: 55%;
      &:nth-child(2n) {
        width: 45%;
      }
    }
  }

  .underline {
    width: 100px;
    border-bottom: 1px solid #000;
    line-height: 0;
  }

  .info-list {
    display: flex;
    flex-wrap: wrap;
    .info-item {
      display: flex;
      width: 55%;
      &:nth-child(2n) {
        width: 45%;
      }
      .info-item-title {
        margin-left: 2em;
      }
    }
  }

  .info-item-title {
    width: 100px;
    font-weight: 600;
  }
}

.contraindication {
  min-height: 800px;
  padding: 50px 80px 0;
  font-size: 18px;
  line-height: 30px;
  .title {
    margin-bottom: 20px;
    font-size: 20px;
    font-weight: 600;
    text-align: center;
  }
  .note {
    margin-top: 40px;
    font-size: 16px;
  }
  .margin-top20 {
    margin-top: 20px;
  }
  .info-list {
    display: flex;
    flex-wrap: wrap;
    .info-item {
      display: flex;
      width: 45%;
      &:nth-child(2n) {
        width: 55%;
      }
      .info-item-title {
        margin-left: 2em;
      }
    }
  }
}
</style>
