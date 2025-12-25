<!-- 职业团体报告 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-group-disease-report">
    <div class="group-report" v-if="reportData">
      <!-- 页眉 -->
      <div class="header">
        <span>{{ reportData.template_0.DEP_NAME }}职业健康检查总结报告</span>
        <span>{{ reportData.jz }}</span>
      </div>
      <!-- 封面 -->
      <div class="report-cover">
        <div class="cover-header">
          <div class="image">
            <div class="flex">
              <img class="logo" :src="imgPath + configData.logo" />
              <img class="code" :src="imgPath + configData.qRCode" />
            </div>
          </div>
          <div class="title">职业健康检查总结报告</div>
          <div class="subtitle">
            <span>{{ reportData.jz }} </span>
          </div>
        </div>
        <div class="cover-info">
          <div class="info-column">
            <div class="info-title"><span>受检单位</span>：</div>
            <div class="info-cont">{{ reportData.template_0.DEP_NAME }}</div>
          </div>
          <div class="info-column">
            <div class="info-title"><span>检查种类</span>：</div>
            <div class="info-cont">{{ reportData.template_0.CHECK_TYPE }} <span style="opacity: 0">1</span></div>
          </div>
          <div class="info-column">
            <div class="info-title"><span>检查机构</span>：</div>
            <div class="info-cont">{{ configData.name }}</div>
          </div>
          <div class="info-column">
            <div class="info-title"><span>报告发送时间</span>：</div>
            <div class="info-cont">{{ reportData.template_0.SEND_TIME }}</div>
          </div>
        </div>
      </div>
      <!-- 前言 -->
      <div class="report-preface">
        <div class="title">报告说明：</div>
        <div class="content">
          <p>1、本报告不得作为宣传资料。</p>
          <p>2、本报告涂改无效。</p>
          <p>3、本报告无主检医师、审核人及签发人签字无效。</p>
          <p>4、本报告无本单位盖章无效。</p>
          <p>5、本报告只对本次体检有效。</p>
          <p>6、本报告正文 xx 页（报告一式二份，用人单位一份，职业健康体检机构存档一份）。</p>
          <p>7、若对本报告有异议，请于收到报告书之日起十五日内向本机构提出，逾期不予受理。</p>
        </div>
        <div class="content" style="margin-top: 80px">
          <p>职业健康检查机构信息：</p>
          <p>名 称：{{ configData.name }}</p>
          <p>地 址：{{ configData.address }}</p>
          <p>电 话：{{ configData.phone }}</p>
          <p>电子邮箱：{{ configData.email }}</p>
          <p>邮 编：{{ configData.postalCode }}</p>
          <p>传 真：{{ configData.fax }}</p>
          <p>备案编号：{{ configData.certificateNo }}</p>
        </div>
      </div>
      <!-- 内容 -->
      <table>
        <thead>
          <tr>
            <td>
              <div class="page-header"></div>
            </td>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td>
              <div class="page-footer"></div>
            </td>
          </tr>
        </tfoot>
        <tbody>
          <tr>
            <td>
              <!-- 内容 -->
              <div class="report-main">
                <p style="text-indent: 2em">受{{ reportData.template_0.DEP_NAME }}的委托，{{ configData.name }}根据《职业健康监护技术规范》有关规定，对从事接触职业病危害因素的作业人员进行{{ reportData.template_0.ON_JOB_STATUS }}职业健康检查，现将检查结果汇总分析报告如下：</p>
                <div class="main-line"></div>
                <p>
                  一、检查时间：
                  <span v-if="!isModify">{{ reportData.template_0.INSPECT_TIME }}</span>
                  <el-input v-else v-model="reportData.template_0.INSPECT_TIME"></el-input>
                </p>
                <p>二、检查地点：{{ configData.address }}</p>
                <p style="display: flex">
                  三、检查人数：
                  <span v-if="!isModify">{{ reportData.template_0.ACTUAL_NUM }}</span>
                  <el-input v-else v-model="reportData.template_0.ACTUAL_NUM" style="width: 100px"></el-input>
                  人。
                </p>
                <p>四、主要职业病危害因素：</p>
                <p>
                  <span v-if="!isModify">{{ reportData.template_0.HARM_CLASS }}</span>
                  <el-input v-else v-model="reportData.template_0.HARM_CLASS" type="textarea" :rows="3"></el-input>
                </p>
                <p>五、检查项目：</p>
                <p>
                  <span v-if="!isModify">{{ reportData.template_0.HARM_DEPARTMENT }}</span>
                  <el-input v-else v-model="reportData.template_0.HARM_DEPARTMENT" type="textarea" :rows="3"></el-input>
                </p>
                <p>
                  六、职业健康检查类别：
                  <span v-if="!isModify">{{ reportData.template_0.ON_JOB_STATUS }}</span>
                  <el-input v-else v-model="reportData.template_0.ON_JOB_STATUS"></el-input>
                </p>
                <p>七、职业健康检查与评价依据：</p>
                <p>1.《中华人民共和国职业病防治法》（中华人民共和国主席令第 24 号）</p>
                <p>2.《职业健康检查管理办法》 国家卫生健康委员会令第2号</p>
                <p>3.《职业病危害因素分类目录》国卫疾控发[2015]92号</p>
                <p>4.《职业健康监护技术规范》 GBZ188-2014</p>
                <p>5.《工作场所有害因素接职业触限值》 GBZ2.1-2019、GBZ2.2-2007</p>
                <p>6. 相关职业病诊断标准：</p>
                <p style="padding-left: 1em">{{ reportData.template_0.DIAGNOSIS_FROM }}</p>
                <div class="main-line"></div>
                <p>八、本次职业健康查体的目的：</p>
                <p style="text-indent: 2em">
                  本次职业健康检查为{{
                    reportData.template_0.ON_JOB_STATUS
                  }}检查，其主要目的是早期发现职业病或疑似职业病患者或劳动者的其他健康异常改变；及时发现有职业禁忌的劳动者；评价健康变化与职业病危害的关系。通过动态观察劳动者的健康变化，评价工作场所职业病危害因素的控制效果。定期健康检查的周期应根据不同职业病危害
                  因素的性质、工作场所有害因素的浓度或强度、目标疾病的潜伏期和防护措施等因素决定。
                </p>
                <p>九、本次职业健康检查（岗中）的目标疾病：</p>
                <table border="0" cellspacing="0">
                  <tr>
                    <th style="width: 140px" class="table-head">
                      <div class="line"></div>
                      <div class="word1">目标疾病</div>
                      <div class="word2">职业病危害因素</div>
                    </th>
                    <th style="width: 30%">职业病</th>
                    <th style="width: auto">职业禁忌证</th>
                  </tr>
                  <tr v-for="(item, index) in reportData.template_0.HARM_LIST" :key="index">
                    <td>{{ item.HAGARD }}</td>
                    <td>{{ item.INDUSTRIAL_DISEASE }}</td>
                    <td>{{ item.PROFESSIONAL_CONTRAINDICATED }}</td>
                  </tr>
                </table>
                <p>十、受检单位的基本情况：</p>
                <p style="padding-left: 2em; display: flex">
                  <span v-if="!isModify">{{ reportData.template_0.DEP_NAME }}</span>
                  <el-input v-else v-model="reportData.template_0.DEP_NAME" style="width: 300px"></el-input>
                  ，现有员工
                  <span v-if="!isModify">{{ reportData.template_0.STAFF_NUM }}</span>
                  <el-input v-else v-model="reportData.template_0.STAFF_NUM" style="width: 100px"></el-input>
                  人
                </p>
                <p style="padding-left: 2em; display: flex">
                  主要原材料是
                  <span v-if="!isModify">{{ reportData.template_0.MATERIAL_NAME }}</span>
                  <el-input v-else v-model="reportData.template_0.MATERIAL_NAME" style="width: 300px"></el-input>
                  。
                </p>
                <p style="padding-left: 2em">
                  主要产品是
                  <span v-if="!isModify">{{ reportData.template_0.PRODUCT_NAME }}</span>
                  <el-input v-else v-model="reportData.template_0.PRODUCT_NAME" style="width: 300px"></el-input>
                  。
                </p>
                <p style="padding-left: 2em">
                  在生产中的主要职业危害因素有
                  <span v-if="!isModify">{{ reportData.template_0.HAGARD_NAMES }}</span>
                  <el-input v-else v-model="reportData.template_0.HAGARD_NAMES" style="width: 300px"></el-input>
                  等。
                </p>
                <p style="padding-left: 2em">
                  主要工艺流程
                  <span v-if="!isModify">{{ reportData.template_0.TECHNOLOGICAL_PROCESS }}</span>
                  <el-input v-else v-model="reportData.template_0.TECHNOLOGICAL_PROCESS" style="width: 300px"></el-input>
                  。
                </p>
                <p style="padding-left: 2em">职业病防护情况：</p>
                <p style="padding-left: 2em">工作环境职业病危害因素检测情况：</p>
                <p>十一、本次职业健康检查的结果及处理意见：</p>
                <p style="padding-left: 2em">1.疑似职业病检出情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.SUSPECTED_OCCUPATIONAL_DISEASE }}</span>
                  <el-input v-else v-model="reportData.template_0.SUSPECTED_OCCUPATIONAL_DISEASE" type="textarea" :rows="3"></el-input>
                </p>
                <p style="padding-left: 2em">2.职业禁忌证检出情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.PROFESSIONAL_CONTRAINDICATED_T }}</span>
                  <el-input v-else v-model="reportData.template_0.PROFESSIONAL_CONTRAINDICATED_T" type="textarea" :rows="3"></el-input>
                </p>
                <p style="padding-left: 2em">3.职业健康检查职业病危害效应相关指标异常需要复查人员情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.REVIEW_OF_THE_SITUATION }}</span>
                  <el-input v-else v-model="reportData.template_0.REVIEW_OF_THE_SITUATION" type="textarea" :rows="3"></el-input>
                </p>
                <p style="padding-left: 2em">4.其他异常结果人员情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.OTHER_EXCEPTIONS }}</span>
                  <el-input v-else v-model="reportData.template_0.OTHER_EXCEPTIONS" type="textarea" :rows="3"></el-input>
                </p>
                <p style="padding-left: 2em">5.职业健康检查未见异常人员情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.NO_ABNORMALITY_SEEN }}</span>
                  <el-input v-else v-model="reportData.template_0.NO_ABNORMALITY_SEEN" type="textarea" :rows="3"></el-input>
                </p>
                <p style="padding-left: 2em">6.本次职业健康检查漏检人员及漏检项目人员情况</p>
                <p style="padding-left: 3em">
                  <span v-if="!isModify">{{ reportData.template_0.LEAK_DETECTION }}</span>
                  <el-input v-else v-model="reportData.template_0.LEAK_DETECTION" type="textarea" :rows="3"></el-input>
                </p>
                <p>十二、本次职业健康检查结果分析与评价：</p>
                <p style="margin: 12px 0">
                  <span v-if="!isModify">{{ reportData.template_0.THIS_CHECK_STATUS }}</span>
                  <el-input v-else v-model="reportData.template_0.THIS_CHECK_STATUS" type="textarea" :rows="3"></el-input>
                </p>
                <p>按职业健康检查接害种类分别归纳分析：</p>
                <p v-if="!isModify" v-html="reportData.template_2.HAGARD_DESCRIBE"></p>
                <el-input v-else v-model="reportData.template_2.HAGARD_DESCRIBE" type="textarea" :rows="3"></el-input>
                <p>十三、告知义务：</p>
                <p style="text-indent: 2em">按照《中华人民共和国职业病防治法》及《职业健康检查管理办法》的要求，单位应及时将检查结果书面告知职工，并按有关规定做好后续处理工作。</p>
                <p>十四、本次健康检查结果及处理意见：</p>
                <p>详见《职业健康检查结果一览表》。</p>
                <div class="main-info">
                  <div class="info-top">
                    <div class="item">
                      <span>报告编制</span>：
                      <image-upload v-if="isModify" class="upload-report-setting" ref="upload1" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFile1" @uploadFinish="uploadFinish1" />
                      <img :src="signList.sign1" alt="" />
                      <span class="delete-word" v-if="isModify && signList.sign1" @click="deletePic(1)">删除</span>
                    </div>
                    <div class="item">
                      <span style="width: auto">报告审核（主检医师）</span>：
                      <image-upload v-if="isModify" class="upload-report-setting" ref="upload2" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFile2" @uploadFinish="uploadFinish2" />
                      <img :src="signList.sign2" alt="" />
                      <span class="delete-word" v-if="isModify && signList.sign2" @click="deletePic(2)">删除</span>
                    </div>
                    <div class="item">
                      <span>签发人</span>：
                      <image-upload v-if="isModify" class="upload-report-setting" ref="upload3" :uploadData="uploadData" :typeChoose="1" @submitFile="submitFile3" @uploadFinish="uploadFinish3" />
                      <img :src="signList.sign3" alt="" />
                      <span class="delete-word" v-if="isModify && signList.sign3" @click="deletePic(3)">删除</span>
                    </div>
                  </div>
                  <div class="info-bottom">
                    <!-- <div class="item"> -->
                    <!-- <span>签发人</span>： -->
                    <!-- <img src="@/assets/images/report/sign_issue.png" /> -->
                    <!-- </div> -->
                    <div class="item"><span>体检机构</span>：（公章）</div>
                    <div class="item"><span>签发日期</span>：<b style="margin-left: 50px">年</b><b style="margin-left: 30px">月</b><b style="margin-left: 30px">日</b></div>
                  </div>
                </div>
                <p>十五：职业健康检查总结报告应包含以下附件：</p>
                <div class="main-annex">
                  <p>1. 疑似职业病人员一览表</p>
                  <p>2. 职业禁忌证人员一览表</p>
                  <p>3. 职业病危害效应相关指标异常需复查人员一览表</p>
                  <p>4. 其他疾病异常结果人员一览表</p>
                  <p>5. 本次职业健康检查未见异常人员一览表</p>
                  <p>6. 本次职业健康检查漏检人员及漏检项目人员一览表</p>
                  <p class="highlight"><span>7. 疑似职业病告知书</span></p>
                  <p class="highlight"><span>8. 职业禁忌证告知书</span></p>
                  <p class="highlight"><span>9. 委托协议书复印件</span></p>
                  <p class="highlight"><span>10. 职业健康检查机构备案回执</span></p>
                  <p>11. 职业健康检查机构实验室检查项目正常参考值（成人）</p>
                  <p>12. 职业健康检查机构主要技术人员资质一览表</p>
                </div>
              </div>
              <!-- 附件 -->
              <div class="report-annex">
                <!-- 附件1 -->
                <div class="annex-item">
                  <h3>附件 1：疑似职业病人员一览表<span v-if="!reportData.list_1.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_1.count">
                    <tr>
                      <th style="width: 38px">序号</th>
                      <th style="width: 95px">身份证号</th>
                      <th style="width: 50px">姓名</th>
                      <th style="width: 32px">性别</th>
                      <th style="width: 42px">年龄</th>
                      <th style="width: 72px">工种</th>
                      <th style="width: 40px">接害工龄</th>
                      <th style="width: 75px">职业病危害因素</th>
                      <th style="width: 85px">检查结果</th>
                      <th style="width: 85px">结论</th>
                      <th>处理意见</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_1.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.result }}</td>
                      <td>{{ item.verdict }}</td>
                      <td>{{ item.idea }}</td>
                    </tr>
                  </table>
                  <div class="empty" v-else></div>
                  <p v-if="reportData.list_1.count">注：本次检查发现的疑似职业病 {{ reportData.list_1.count }} 人，建议用人单位提交详细职业史证明材料到职业病诊断机构进一步检查，明确诊断。</p>
                </div>
                <!-- 附件2 -->
                <div class="annex-item">
                  <h3>附件2：职业禁忌证人员一览表<span v-if="!reportData.list_2.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_2.count">
                    <tr>
                      <th style="width: 38px">序号</th>
                      <th style="width: 95px">身份证号</th>
                      <th style="width: 50px">姓名</th>
                      <th style="width: 32px">性别</th>
                      <th style="width: 42px">年龄</th>
                      <th style="width: 72px">工种</th>
                      <th style="width: 40px">接害工龄</th>
                      <th style="width: 75px">职业病危害因素</th>
                      <th style="width: 85px">检查结果</th>
                      <th style="width: 85px">结论</th>
                      <th>处理意见</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_2.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.result }}</td>
                      <td>{{ item.verdict }}</td>
                      <td>{{ item.idea }}</td>
                    </tr>
                  </table>
                  <div class="empty" v-else></div>
                  <p v-if="reportData.list_2.count">注：以上人员存在职业禁忌证，不宜从事现工作岗位，建议调离。</p>
                </div>
                <!-- 附件3 -->
                <div class="annex-item">
                  <h3>附件3：职业病危害因素相关指标需复查人员一览表<span v-if="!reportData.list_3.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_3.count">
                    <tr>
                      <th style="width: 38px">序号</th>
                      <th style="width: 95px">身份证号</th>
                      <th style="width: 50px">姓名</th>
                      <th style="width: 32px">性别</th>
                      <th style="width: 42px">年龄</th>
                      <th style="width: 72px">工种</th>
                      <th style="width: 40px">接害工龄</th>
                      <th style="width: 75px">职业病危害因素</th>
                      <th style="width: 85px">检查结果</th>
                      <th style="width: 85px">结论</th>
                      <th>处理意见</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_3.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.result }}</td>
                      <td>{{ item.verdict }}</td>
                      <td>{{ item.idea }}</td>
                    </tr>
                  </table>
                  <div class="empty" v-else></div>
                  <p v-if="reportData.list_3.count">注：本次检出职业病危害效应相关指标异常需复查人员{{ reportData.list_3.count }} 人。（视具体情况提出复查时间，复查内容、地点等）</p>
                  <p v-if="reportData.list_3.count">{{ reportData.list_3.reviewattention }}</p>
                </div>
                <!-- 附件4 -->
                <div class="annex-item">
                  <h3>附件4：其他疾病异常结果人员一览表<span v-if="!reportData.list_4.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_4.count">
                    <tr>
                      <th style="width: 38px">序号</th>
                      <th style="width: 95px">身份证号</th>
                      <th style="width: 50px">姓名</th>
                      <th style="width: 32px">性别</th>
                      <th style="width: 42px">年龄</th>
                      <th style="width: 72px">工种</th>
                      <th style="width: 40px">接害工龄</th>
                      <th style="width: 75px">职业病危害因素</th>
                      <th style="width: 85px">检查结果</th>
                      <th style="width: 85px">结论</th>
                      <th>处理意见</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_4.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.result }}</td>
                      <td>{{ item.verdict }}</td>
                      <td>{{ item.idea }}</td>
                    </tr>
                  </table>
                  <div class="empty" v-else></div>
                  <p v-if="reportData.list_4.count">注：本次职业健康检查发现其他疾病或异常的人员，属常见病症，建议到综合性医院复查或诊治。</p>
                </div>
                <!-- 附件5 -->
                <div class="annex-item">
                  <h3>附件5：本次职业健康检查未见异常人员一览表<span v-if="!reportData.list_5.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_5.count">
                    <tr>
                      <th style="width: 40px">序号</th>
                      <th style="width: 110px">身份证号</th>
                      <th style="width: 62px">姓名</th>
                      <th style="width: 40px">性别</th>
                      <th style="width: 45px">年龄</th>
                      <th style="width: 110px">工种</th>
                      <th style="width: 60px">接害工龄</th>
                      <th style="width: 110px">职业病危害因素</th>
                      <th>结论</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_5.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.verdict }}</td>
                    </tr>
                  </table>
                  <div class="empty" v-else></div>
                </div>
                <!-- 附件6 -->
                <div class="annex-item">
                  <h3>附件6：本次职业健康检查漏检人员及漏检项目人员一览表<span v-if="!reportData.list_6.count">：无</span></h3>
                  <table border="0" cellspacing="0" v-if="reportData.list_6.count">
                    <tr>
                      <th>序号</th>
                      <th>身份证号</th>
                      <th>姓名</th>
                      <th>性别</th>
                      <th>年龄</th>
                      <th>工种</th>
                      <th>接害工龄</th>
                      <th>职业病危害因素</th>
                      <th>漏检项目</th>
                      <th>处理意见</th>
                    </tr>
                    <tr v-for="(item, index) in reportData.list_6.list" :key="index">
                      <td>{{ item.num }}</td>
                      <td>{{ item.idCardNo }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.age }}</td>
                      <td>{{ item.craft }}</td>
                      <td>{{ item.years }}</td>
                      <td>{{ item.reason }}</td>
                      <td>{{ item.missobject }}</td>
                      <td></td>
                    </tr>
                  </table>
                </div>
                <!-- 附件7 -->
                <div class="annex-item page-break">
                  <h3>附件 7：职业健康检查机构备案回执</h3>
                  <img class="receipt" :src="imgPath + configData.pic" />
                </div>
                <!-- 附件8 -->
                <div class="annex-item page-break">
                  <h3>附件8：{{ $getCookie('cid') == '2' ? '沃德' : configData.name }}体检实验室检查项目正常参考值范围（成人）</h3>
                  <table border="0" cellspacing="0">
                    <tr>
                      <th style="width: 17%">总项目</th>
                      <th style="width: 30%">具体项目</th>
                      <th style="width: 15%">单位</th>
                      <th style="width: 38%">正常值参考范围</th>
                    </tr>
                    <tr v-for="item in referList" :key="item.id">
                      <td style="text-align: center" v-if="item.id == 1" rowspan="7">血常规</td>
                      <td style="text-align: center" v-if="item.id == 8" rowspan="16">生化检验</td>
                      <td>{{ item.name }}</td>
                      <td v-html="item.unit"></td>
                      <td v-html="item.range"></td>
                    </tr>
                  </table>
                </div>
                <!-- 附件9-->
                <div class="annex-item page-break">
                  <h3>附件9：{{ $getCookie('cid') == '2' ? '沃德' : configData.name }}体检主要技术人员资质一览表</h3>
                  <table border="0" cellspacing="0">
                    <tr>
                      <th style="width: 12%">姓名</th>
                      <th style="width: 8%">性别</th>
                      <th style="width: 15%">职务/职称</th>
                      <th style="width: 12%">科室</th>
                      <th style="width: 13%">专业</th>
                      <th style="width: 10%">专业工作年限</th>
                      <th style="width: 30%">资格证书编号<sup>*</sup></th>
                    </tr>
                    <tr v-for="item in credentialList" :key="item.id">
                      <td>{{ item.name }}</td>
                      <td>{{ item.sex }}</td>
                      <td>{{ item.post }}</td>
                      <td>{{ item.ks }}</td>
                      <td>{{ item.major }}</td>
                      <td>{{ item.workYears }}</td>
                      <td>{{ item.number }}</td>
                    </tr>
                  </table>
                  <p>* 卫生厅颁发的医/技人员资格证书或职业病诊断医师资格证编号。</p>
                </div>
              </div>
              <!-- 参考资料 -->
              <div class="report-material">
                <h3>参考资料</h3>
                <h5>一、职业健康监护定义：</h5>
                <p>
                  以预防为目的，根据劳动者的职业接触史，通过定期或不定期的医学健康检查和健康相关资料的收集，连续性地监测劳动者的健康状况，分析劳动者健康变化与所接触的职业病危害因素的关系，并及时地将健康检查和资料分析结果报告给用人单位和劳动者本人，以便及时采取干预措施，保护劳动者健康。职业健康监护主要包括职业健康检查、离岗后健康检查、应急健康检查和职业健康监护档案管理等内容。
                </p>
                <h5>二、职业健康检查定义：</h5>
                <p>
                  通过医学手段和方法，针对劳动者所接触的职业病危害因素可能产生的健康影响和健康损害进行临床医学检查，了解受检者健康状况，早期发现职业病、职业禁忌证和可能的其他疾病和健康损害的医疗行为。职业健康检查是职业健康监护的重要内容和主要的资料来源。职业健康检查包括上岗前、在岗期间、离岗时健康检查。
                </p>
                <h5>三、职业病危害因素对健康的影响：</h5>
                <p>{{ reportData.endpage.FOR_PERSON_INFLUENCE }}</p>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 封底 -->
      <div class="report-end">
        <img class="end-logo" :src="imgPath + configData.logo" />
        <img class="end-code" :src="imgPath + configData.qRCode" />
        <img v-if="isJD" class="end-logo2" :src="imgPath + configData.miniProgramCode" />
        <div class="end-info">
          <p>咨询电话：{{ configData.phone }}</p>
          <p>FAX：{{ configData.fax }}</p>
          <p>网址：<a href="http://www.chihm.com" target="_blank">www.chihm.com</a></p>
          <p>地址：{{ configData.address }}</p>
          <div class="image">
            <img src="@/assets/images/report/bar_code.png" />
          </div>
        </div>
      </div>
    </div>
    <div :style="isMobile() ? 'height: 82px;' : ''" class="placeholder"></div>
    <div class="print-fixed">
      <el-button class="mobile" type="primary" @click="handlePrint()" v-if="isMobile()">下 载</el-button>
      <el-button type="primary" @click="handlePrint()" v-else-if="!isModify">打 印</el-button>
      <template v-if="!isMobile()">
        <el-button type="primary" @click="modifyReport">{{ isModify ? '确认修改' : '修改' }}</el-button>
        <el-button type="primary" v-if="isModify" @click="cancelModify">取消修改</el-button>
      </template>
    </div>
  </div>
</template>

<script>
import { getReportData, getBranchConfig } from '@/api/preview/group_report.js'
import { updateReportData } from '@/api/preview/contrast_report.js'

import { getReferData, getCredentialData, getCredentialData2 } from '@/utils/dataList.js'
export default {
  name: 'GroupDiseaseReport',
  data() {
    return {
      // 查询参数
      queryParams: {},
      // 遮罩层
      loading: null,
      // 报告数据
      reportData: undefined,
      reportData2: undefined,
      // 附件8参考值
      referList: getReferData(),
      // 附件9参考值
      credentialList: [],
      // 是否为修改报告
      isModify: false,
      // 分中心配置参数
      configData: {},
      // 图片路径
      imgPath: this.$getCookie('imgPath'),
      // 上传图片配置参数
      uploadData: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1,
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        picture: '',
      },
      // 签名列表
      signList: {
        sign1: '',
        sign2: '',
        sign3: '',
      },
      // 是否为锦都
      isJD: false,
    }
  },
  created() {
    document.title = '职业团体报告'
    this.queryParams = this.$route.query
    // this.isModify = this.queryParams.isModify
    if (this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c' || window.location.href.includes('http://XXX.XXX.XXX')) {
      this.isJD = true
      // this.credentialList = getCredentialData2()
    } else {
      this.isJD = false
      // this.credentialList = getCredentialData()
    }
    this.loading = this.$loading({
      lock: true,
      text: '加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    this.getReport()
    // this.getConfig()
  },
  mounted() {
    this.signList = localStorage.getItem('signList')
      ? JSON.parse(localStorage.getItem('signList'))
      : {
          sign1: '',
          sign2: '',
          sign3: '',
        }
    const metaTags = document.getElementsByTagName('meta')
    for (let i = 0; i < metaTags.length; i++) {
      if (metaTags[i].getAttribute('name') === 'viewport') {
        metaTags[i].setAttribute('content', 'width=device-width, initial-scale=0.42, maximum-scale=1.0')
        break
      }
    }
  },
  methods: {
    // 判断是否为手机
    isMobile() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag
    },
    // 查看职业报告数据
    getReport() {
      getReportData(this.queryParams)
        .then((res) => {
          this.reportData = res.data
          this.configData = res.data.branchConfig
          this.credentialList = res.data.branchConfig.personAptitude
          this.reportData2 = JSON.stringify(res.data)
          this.loading.close()
        })
        .catch((error) => {
          console.error(error)
          this.loading.close()
        })
    },
    // 获取分中心报告配置
    getConfig() {
      getBranchConfig({
        branchId: this.$getCookie('cid'),
      }).then((res) => {
        this.configData = res.data
      })
    },
    // 打印报告
    handlePrint() {
      window.print()
    },
    // 修改按钮
    modifyReport() {
      if (this.isModify) {
        this.$confirm('是否确认修改当前报告?', '提示')
          .then(() => {
            this.loading = this.$loading({
              lock: true,
              text: '加载中',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            localStorage.setItem('signList', JSON.stringify(this.signList))
            updateReportData({ ...this.queryParams, content: this.reportData })
              .then(() => {
                this.$modal.msgSuccess('修改成功', '提示')
                this.loading.close()
                this.isModify = false
              })
              .catch(() => {
                this.loading.close()
              })
          })
          .catch(() => {})
      } else {
        this.isModify = true
      }
    },
    // 删除签名图片
    deletePic(type) {
      if (type == 1) {
        this.signList.sign1 = ''
      } else if (type == 2) {
        this.signList.sign2 = ''
      } else {
        this.signList.sign3 = ''
      }
      // this.signList['sign' + type] = ''
      this.$refs['upload' + type].resetUpload()
    },
    // 取消修改
    cancelModify() {
      this.reportData = JSON.parse(this.reportData2)
      this.isModify = false
    },
    // 修改报告编制
    submitFile1() {
      var msg = this.$refs.upload1.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload1.handelUpload()
      } else {
        this.uploadFinish1(1)
      }
    },
    //接收报告编制返回地址
    uploadFinish1(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.signList.sign1 = this.$getCookie('imgPath') + res.data
    },
    // 修改报告编制
    submitFile2() {
      var msg = this.$refs.upload2.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload2.handelUpload()
      } else {
        this.uploadFinish2(1)
      }
    },
    //接收报告编制返回地址
    uploadFinish2(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.signList.sign2 = this.$getCookie('imgPath') + res.data
    },
    // 修改报告编制
    submitFile3() {
      var msg = this.$refs.upload3.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload3.handelUpload()
      } else {
        this.uploadFinish3(1)
      }
    },
    //接收报告编制返回地址
    uploadFinish3(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.loading.close()
      this.loading = false
      this.signList.sign3 = this.$getCookie('imgPath') + res.data
    },
  },
}
</script>

<style lang="scss" scoped>
.print-fixed {
  position: fixed;
  z-index: 999;
  left: 50%;
  bottom: 20px;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-evenly;
  width: 260px;
  height: 35px;
  cursor: default;
  text-align: center;

  .mobile {
    transform: scale(1.8);
    transform-origin: bottom center;
  }
}

.preview-group-disease-report {
  background: rgba($color: #000000, $alpha: 0.06);
  min-height: 100%;
  padding: 20px 10px;

  .placeholder {
    width: 100%;
    height: 50px;
  }
}

.group-report {
  width: 210mm;
  margin: 0 auto;

  * {
    font-family: 'song';
  }

  .report-cover {
    page-break-after: always;
    height: 297mm;
    background: #fff;
    padding: 40px 10%;

    .cover-header {
      .title {
        font-size: 34px;
        text-align: center;
        margin-top: 40px;
        font-weight: bold;
      }

      .subtitle {
        font-size: 24px;
        display: flex;
        justify-content: space-around;
        margin-top: 40px;
        font-weight: bold;
      }

      .image {
        display: flex;
        justify-content: flex-end;

        .flex {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }

        .logo {
          width: auto;
          height: 130px;
        }

        .code {
          width: auto;
          height: 50px;
        }
      }
    }

    .cover-info {
      margin-top: 200px;

      .info-column {
        display: flex;
        align-items: center;

        .info-title {
          font-size: 18px;
          font-weight: bold;

          span {
            display: inline-block;
            width: 112px;
            text-align: justify;
            text-align-last: justify;
          }
        }

        .info-cont {
          font-size: 18px;
          border-bottom: 1px solid #000;
          flex: 1;
          height: 46px;
          line-height: 46px;
          font-weight: bold;
        }
      }
    }
  }

  .report-preface {
    page-break-after: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 15%;
    height: 297mm;

    .title {
      margin-top: 20px;
      font-size: 18px;
      font-weight: bold;
    }

    .content {
      font-size: 16px;
      margin-top: 8px;
      line-height: 32px;

      p {
        margin: 0;
      }
    }
  }

  .report-main {
    page-break-after: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 80px;

    p {
      font-size: 16px;
      line-height: 32px;
      margin: 0;
      white-space: pre-wrap;
    }

    .main-line {
      width: 100%;
      height: 0;
      border-top: 1px solid #000;
      margin: 10px 0;
    }

    table {
      font-size: 16px;
      width: 100%;
      margin-top: 12px;
      margin-bottom: 8px;
      border: 1px solid #000;
      border-collapse: collapse;

      th,
      td {
        word-break: break-all;
        border: 1px solid #000;
        padding: 3px 8px;
        line-height: 23px;
        white-space: pre-wrap;
      }

      .table-head {
        position: relative;
        overflow: hidden;
        height: 50px;

        .line {
          border-top: 1px solid #000;
          position: absolute;
          top: 24px;
          left: -29px;
          right: -29px;
          transform: rotate(19deg);
        }

        .word1 {
          font-size: 12px;
          line-height: 12px;
          position: absolute;
          top: 8px;
          right: 10px;
        }

        .word2 {
          font-size: 12px;
          line-height: 12px;
          position: absolute;
          left: 5px;
          bottom: 5px;
        }
      }
    }

    .main-info {
      font-size: 16px;
      margin-top: 16px;
      overflow: hidden;
      page-break-after: always;

      .info-top,
      .info-bottom {
        .item {
          font-size: 16px;
          line-height: 48px;
          height: 48px;
          display: flex;

          span {
            width: 66px;
            display: inline-block;
            text-align: justify;
          }

          span::after {
            content: '';
            display: inline-block;
            width: 100%;
          }

          .delete-word {
            width: auto;
            margin-left: 8px;
            font-size: 12px;
            color: red;
            cursor: pointer;
          }

          img {
            width: auto;
            height: 44px;
          }
        }
      }

      .info-bottom {
        float: right;
        min-width: 50%;
      }
    }

    .highlight span {
      position: relative;
      z-index: 1;
      overflow: hidden;
      background: yellow;
    }
  }

  .report-annex {
    page-break-after: always;
    background: #fff;
    padding: 40px;

    .annex-item {
      padding-bottom: 24px;

      h3 {
        margin: 0;
        line-height: 30px;
        font-weight: 600;
      }

      p {
        font-size: 15px;
        line-height: 34px;
        margin: 0;
      }

      table {
        font-size: 12px;
        width: 100%;
        margin-top: 12px;
        margin-bottom: 8px;
        border: 1px solid #000;
        border-collapse: collapse;

        th,
        td {
          word-break: break-all;
          border: 1px solid #000;
          padding: 2px 4px;
          line-height: 22px;
          white-space: pre-wrap;
        }
      }

      .empty {
        width: 100%;
        height: 60px;
      }

      .receipt {
        width: 100%;
        max-height: 240mm;
      }

      sup {
        font-size: 12px;
      }
    }

    .page-break {
      page-break-before: always;
    }
  }

  .report-material {
    margin: 20px auto 0;
    background: #fff;
    padding: 40px 15%;

    h3 {
      margin-top: 20px;
      font-size: 22px;
      font-weight: bold;
      text-align: center;
    }

    h5,
    p {
      margin: 0;
      font-size: 20px;
      margin-top: 8px;
      line-height: 42px;
    }
  }

  .report-end {
    page-break-before: always;
    margin: 20px auto 0;
    background: #fff;
    padding: 40px;
    height: 297mm;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .end-logo {
      width: auto;
      height: 240px;
      object-fit: contain;
    }

    .end-code {
      width: 60px;
      height: 60px;
      object-fit: contain;
      margin-top: 2px;
    }
    .end-logo2 {
      height: 150px;
      object-fit: contain;
    }

    .end-info {
      margin-top: 160px;

      p {
        margin: 0;
        font-size: 18px;
        line-height: 26px;
        color: #333333;
      }

      .image {
        margin: 40px auto 0;
        width: 350px;
        height: 66px;
        overflow: hidden;

        img {
          width: 100%;
          height: auto;
        }
      }
    }
  }
}

// 分页打印的问题
table {
  width: 100%;
}

.header {
  display: none;
}

@media print {
  .preview-group-disease-report {
    padding: 0;
    background: #fff;
  }

  .report-cover,
  .report-preface,
  .report-end {
    margin: 0 !important;
    position: relative;
    z-index: 99;
  }

  // .group-report .report-end {
  //   position: relative;
  //   z-index: 99;
  //   height: 297mm;
  // }

  .report-main {
    padding-top: 0 !important;
    padding-bottom: 0 !important;
    margin-top: 0 !important;
  }

  .placeholder {
    display: none;
  }

  .print-fixed {
    display: none;
  }

  .header,
  .page-header {
    height: 60px;
    margin-bottom: 10px;
  }

  .footer,
  .page-footer {
    height: 50px !important;
    margin-top: 10px !important;
  }

  .header {
    position: fixed;
    top: 0;
    left: 60px;
    right: 60px;
    z-index: 9;
    border-bottom: 2px solid #333333;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    font-size: 14px;
    font-size: #333;
    font-weight: normal;
  }

  @page {
    margin: 0;
  }
}
</style>
<!-- 修改上传签名样式 -->
<style scoped>
.preview-group-disease-report /deep/ .el-upload-dragger {
  min-width: auto;
  padding: 0;
  border: none;
}
.preview-group-disease-report /deep/ .el-upload-list__item {
  display: none;
}
</style>
