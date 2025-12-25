<!-- 健康问卷  开发人：麦沃德科技 半夏/矢北 -->
<template>
  <el-dialog title="健康体检自测问卷" :visible.sync="open" width="1800px" class="health-questionnaire" append-to-body>
    <div class="flex-direction-column">
      <!-- 头部工具栏 -->
      <div class="mini-toolbar" style="border:0;padding:0px; border-bottom:solid 1px #99bce8;">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
          <el-form-item>
            <el-input 
              v-model="queryParams.physicalNo" 
              placeholder="体检号" 
              style="width:230px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" @click="handleQuery">读卡</el-button>
          </el-form-item>
          <el-form-item>
            <el-tag v-if="headParams.isVIP" :type="headParams.isVIP === 'VIP' ? 'danger' : 'warning'">
              {{ headParams.isVIP }}
            </el-tag>
          </el-form-item>
          <el-form-item>
            <el-button type="success" size="mini" @click="onOk" icon="el-icon-check">保存问卷</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <!-- 问卷内容 -->
      <div class="mini-fit" style="overflow-y: auto; height: calc(100vh - 200px);">
        <el-form ref="form" :model="form" :rules="rules" label-width="140px">
          <!-- 标题 -->
          <div style="text-align: center;">
            <h1>沃德国际体检中心</h1>
            <h1>健 康 体 检 自 测 问 卷</h1>
          </div>

          <!-- 一、基本信息 -->
          <el-descriptions title="一、基本信息" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="姓名：" prop="patientname">
                  <el-input v-model="form.patientname" size="small" style="width: 170px" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别：" prop="idSex">
                  <el-radio-group v-model="form.idSex">
                    <el-radio :label="0">男</el-radio>
                    <el-radio :label="1">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出生日期：" prop="birthdate">
                  <el-date-picker 
                    v-model="form.birthdate" 
                    type="date" 
                    size="small"
                    style="width: 170px"
                    value-format="yyyy-MM-dd"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="身份证号：" prop="idcardno">
                  <el-input v-model="form.idcardno" size="small" style="width: 170px" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="民族：" prop="isHan">
                  <el-radio-group v-model="form.isHan">
                    <el-radio :label="1">汉族</el-radio>
                    <el-radio :label="0">少数民族</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="16">
                <el-form-item label="常住地：" prop="idProvince">
                  <el-select 
                    v-model="form.idProvince" 
                    filterable
                    size="small"
                    style="width: 200px"
                  >
                    <el-option
                      v-for="item in provinceOptions"
                      :key="item.id"
                      :label="item.resarea"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="婚姻状况：" prop="idMarriage">
                  <el-radio-group v-model="form.idMarriage">
                    <el-radio :label="1">未婚</el-radio>
                    <el-radio :label="2">已婚（含同居）</el-radio>
                    <el-radio :label="3">丧偶</el-radio>
                    <el-radio :label="4">离异</el-radio>
                    <el-radio :label="5">其他</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="填表人联系电话：" prop="phone">
                  <el-input v-model="form.phone" size="small" style="width: 170px" />
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 二、家族史 -->
          <el-descriptions title="二、家族史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您的父母或兄弟姐妹是否患有明确诊断的疾病？" prop="family1">
                  <el-radio-group v-model="form.family1">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.请选择疾病的名称：（可多选）" prop="family2">
                  <el-checkbox-group v-model="form.family2">
                    <el-row>
                      <el-col :span="6" v-for="item in familyOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.family2Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写疾病名称"
                    v-if="form.family2.includes('26')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.请确定所患的恶性肿瘤名称:（可多选）" prop="family3">
                  <el-checkbox-group v-model="form.family3">
                    <el-row>
                      <el-col :span="6" v-for="item in tumorOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.family3Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写恶性肿瘤名称"
                    v-if="form.family3.includes('19')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您的父亲是否在55岁、母亲在65岁之前患有上述疾病吗？" prop="family4">
                  <el-radio-group v-model="form.family4">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 三、现病史 -->
          <el-descriptions title="三、现病史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您是否患有明确诊断的疾病或异常？" prop="present1">
                  <el-radio-group v-model="form.present1">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.请您确认具体疾病或异常的名称：（可多选）" prop="present2">
                  <el-checkbox-group v-model="form.present2">
                    <el-row>
                      <el-col :span="6" v-for="item in presentOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.present2Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写疾病名称"
                    v-if="form.present2.includes('26')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.请确定所患的恶性肿瘤名称:（可多选）" prop="present21">
                  <el-checkbox-group v-model="form.present21">
                    <el-row>
                      <el-col :span="6" v-for="item in tumorOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.present21Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写恶性肿瘤名称"
                    v-if="form.present21.includes('19')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.请填写您被诊断患有上述疾病或异常时的年龄" prop="present3">
                  <el-input-number 
                    v-model="form.present3" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 岁
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 四、过敏史 -->
          <el-descriptions title="四、过敏史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您是否出现过过敏？" prop="allergy1">
                  <el-radio-group v-model="form.allergy1">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.请选择过敏源：（可多选）" prop="allergy2">
                  <el-checkbox-group v-model="form.allergy2">
                    <el-row>
                      <el-col :span="6" v-for="item in allergyOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 五、用药史 -->
          <el-descriptions title="五、用药史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您是否长期服用药物？（连续服用6个月以上，平均每日服用一次以上）" prop="medication1">
                  <el-radio-group v-model="form.medication1">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您长期服用哪些药物？（可多选）" prop="medication2">
                  <el-checkbox-group v-model="form.medication2">
                    <el-row>
                      <el-col :span="6" v-for="item in medicationOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.medication2Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写药物名称"
                    v-if="form.medication2.includes('5')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 六、手术史 -->
          <el-descriptions title="六、手术史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您是否因病进行过手术治疗？" prop="operation1">
                  <el-radio-group v-model="form.operation1">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                  <el-input 
                    v-model="form.operation1Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写手术名称"
                    v-if="form.operation1 === 1"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.请您选择手术的部位?（可多选）" prop="operation2">
                  <el-checkbox-group v-model="form.operation2">
                    <el-row>
                      <el-col :span="6" v-for="item in operationOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.operation2Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写手术名称"
                    v-if="form.operation2.includes('18')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 七、月经生育史 -->
          <el-descriptions title="七、月经生育史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您第一次来月经的年龄" prop="birth1">
                  <el-input-number 
                    v-model="form.birth1" 
                    :min="0" 
                    :max="99" 
                    size="small" 
                    style="width: 100px" 
                  /> 岁
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您是否绝经？" prop="birth2">
                  <el-radio-group v-model="form.birth2">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                  <template v-if="form.birth2 === 1">
                    绝经年龄
                    <el-input-number 
                      v-model="form.birth2Other" 
                      :min="0" 
                      :max="99" 
                      size="small" 
                      style="width: 100px" 
                    /> 岁
                  </template>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您的结婚年龄" prop="birth3">
                  <el-input-number 
                    v-model="form.birth3" 
                    :min="0" 
                    :max="99" 
                    size="small" 
                    style="width: 100px" 
                  /> 岁
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您是否生育过？" prop="birth4">
                  <el-radio-group v-model="form.birth4">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <template v-if="form.birth4 === 1">
              <el-row :gutter="20">
                <el-col :span="24">
                  <el-form-item label="初产年龄" prop="birth5">
                    <el-input-number 
                      v-model="form.birth5" 
                      :min="0" 
                      :max="99" 
                      size="small" 
                      style="width: 100px" 
                    /> 岁, 
                    生产
                    <el-input-number 
                      v-model="form.birth6" 
                      :min="0" 
                      :max="99" 
                      size="small" 
                      style="width: 100px" 
                    /> 次, 
                    流产总次数
                    <el-input-number 
                      v-model="form.birth7" 
                      :min="0" 
                      :max="99" 
                      size="small" 
                      style="width: 100px" 
                    /> 次
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="24">
                  <el-form-item label="您的孩子是母乳喂养吗？" prop="birth8">
                    <el-radio-group v-model="form.birth8">
                      <el-radio :label="1">是</el-radio>
                      <el-radio :label="0">否</el-radio>
                    </el-radio-group>
                    <template v-if="form.birth8 === 1">
                      哺乳时间
                      <el-input-number 
                        v-model="form.birth8Other" 
                        :min="0" 
                        :max="99" 
                        size="small" 
                        style="width: 100px" 
                      /> 月
                    </template>
                  </el-form-item>
                </el-col>
              </el-row>
            </template>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您是否曾患有妊娠糖尿病？" prop="birth9">
                  <el-radio-group v-model="form.birth9">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您是否曾患有妊娠高血压？" prop="birth10">
                  <el-radio-group v-model="form.birth10">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 八、躯体症状（最近3个月） -->
          <el-descriptions title="八、躯体症状（最近3个月）" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <!-- 问题1 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您感觉身体总体健康状况如何?" prop="body1">
                  <el-radio-group v-model="form.body1">
                    <el-radio :label="1">好</el-radio>
                    <el-radio :label="2">一般</el-radio>
                    <el-radio :label="3">差</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题2 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您感到疲劳乏力或周身明显不适吗？" prop="body1">
                  <el-radio-group v-model="form.body2">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题3 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您视力有下降吗？" prop="body1">
                  <el-radio-group v-model="form.body21">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">轻微</el-radio>
                    <el-radio :label="3">明显</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题4 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您听力有下降吗？" prop="body1">
                  <el-radio-group v-model="form.body22">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">轻微</el-radio>
                    <el-radio :label="3">明显</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题5 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您有鼻出血或浓血鼻涕吗？" prop="body1">
                  <el-radio-group v-model="form.body23">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题6 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您出现过吞咽不适、哽噎感吗？" prop="body1">
                  <el-radio-group v-model="form.body24">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题7 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="7.您有明显的咳嗽、咳痰吗？" prop="body1">
                  <el-radio-group v-model="form.body25">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题8 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="8.您有过咳痰带血或咯血吗？" prop="body1">
                  <el-radio-group v-model="form.body26">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题9 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="9.您感到胸痛或心前区憋闷不适吗？" prop="body1">
                  <el-radio-group v-model="form.body3">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题10 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="10.您感到有胸闷气喘或呼吸困难吗？" prop="body1">
                  <el-radio-group v-model="form.body31">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题11 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="11.您感到低热（体温偏高）吗？" prop="body1">
                  <el-radio-group v-model="form.body32">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题12 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="12.您感到头晕或头昏吗？" prop="body1">
                  <el-radio-group v-model="form.body33">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题13 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="13.您感到恶心、反酸或上腹部不适吗？" prop="body1">
                  <el-radio-group v-model="form.body4">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题14 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="14.您有过食欲不振、消化不良或腹胀吗？" prop="body1">
                  <el-radio-group v-model="form.body41">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题15 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="15.您有过不明原因跌倒或晕倒吗？" prop="body1">
                  <el-radio-group v-model="form.body5">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题16 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="16.您感到明显的手足发麻或刺痛吗？" prop="body1">
                  <el-radio-group v-model="form.body6">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题17 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="17.您双下肢水肿吗？" prop="body1">
                  <el-radio-group v-model="form.body7">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题18 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="18.您排尿困难吗？" prop="body1">
                  <el-radio-group v-model="form.body71">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题19 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="19.您有尿频、尿急、尿痛及尿血吗？" prop="body1">
                  <el-radio-group v-model="form.body72">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题20 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="20.您有腹泻、腹痛或大便习惯改变（入厕时间、次数、形状等）吗？" prop="body1">
                  <el-radio-group v-model="form.body73">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题21 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="21.您出现过柏油样便或便中带血吗？" prop="body1">
                  <el-radio-group v-model="form.body8">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题22 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="22.您出现过不明原因的身体消瘦或体重减轻吗?（体重减轻超过原体重的 10%）？" prop="body1">
                  <el-radio-group v-model="form.body81">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题23 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="23.您是否发现乳房有包块,并伴有胀痛吗（与月经周期无关）？" prop="body1">
                  <el-radio-group v-model="form.body82">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题24 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="24.您有不明原因的阴道出血、白带异常吗？" prop="body1">
                  <el-radio-group v-model="form.body9">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题25 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="25.您身体有过明显的疼痛吗?（外伤除外）？" prop="body1">
                  <el-radio-group v-model="form.body91">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题26 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="26.疼痛的部位？" prop="body92">
                  <el-checkbox-group v-model="form.body92">
                    <el-row>
                      <el-col :span="6" v-for="item in bodyPainOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 九、饮食习惯 -->
          <el-descriptions title="九、饮食习惯" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <!-- 问题1 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您通常能够按时吃三餐吗？" prop="eat1">
                  <el-radio-group v-model="form.eat1">
                    <el-radio :label="1">能</el-radio>
                    <el-radio :label="2">基本能</el-radio>
                    <el-radio :label="0">不能</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题2 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您常暴饮暴食吗？" prop="eat2">
                  <el-radio-group v-model="form.eat2">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题3 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您常吃夜宵吗？" prop="eat4">
                  <el-radio-group v-model="form.eat4">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃</el-radio>
                    <el-radio :label="3">经常吃</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- 问题4 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您参加请客吃饭（应酬）情况？" prop="eat5">
                  <el-radio-group v-model="form.eat5">
                    <el-radio :label="1">不参加或偶尔参加（1~2 次/月）</el-radio>
                    <el-radio :label="2">比较多（1~2 次/周）</el-radio>
                    <el-radio :label="3">经常参加（3~5 次/周）</el-radio>
                    <el-radio :label="4">非常频繁（＞5 次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题5 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您的饮食口味？" prop="eat6">
                  <el-radio-group v-model="form.eat6">
                    <el-radio :label="1">清淡</el-radio>
                    <el-radio :label="2">咸</el-radio>
                    <el-radio :label="3">甜</el-radio>
                    <el-radio :label="4">高油脂</el-radio>
                    <el-radio :label="5">辛辣</el-radio>
                    <el-radio :label="6">热烫</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题6 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您的饮食偏好？" prop="eat7">
                  <el-radio-group v-model="form.eat7">
                    <el-radio :label="1">熏制、腌制类</el-radio>
                    <el-radio :label="2">油炸食品</el-radio>
                    <el-radio :label="3">甜点</el-radio>
                    <el-radio :label="4">吃零食（适量坚果除外）</el-radio>
                    <el-radio :label="5">吃快餐</el-radio>
                    <el-radio :label="6">喝粥（N2次/天）</el-radio>
                    <el-radio :label="7">其它</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题7 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="7.您的主食结构如何？" prop="eat8">
                  <el-radio-group v-model="form.eat8">
                    <el-radio :label="1">细粮为主</el-radio>
                    <el-radio :label="2">粗细搭配</el-radio>
                    <el-radio :label="3">粗粮为主</el-radio>
                    <el-radio :label="4">不好说</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题8 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="8.您喝牛奶吗？" prop="eat9">
                  <el-radio-group v-model="form.eat9">
                    <el-radio :label="1">不喝</el-radio>
                    <el-radio :label="2">偶尔喝（1~2次/周）</el-radio>
                    <el-radio :label="3">经常喝（3~5次/周）</el-radio>
                    <el-radio :label="4">每天都喝（>5次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题9 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="9.您吃鸡蛋吗？" prop="eat10">
                  <el-radio-group v-model="form.eat10">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃（1~2次/周）</el-radio>
                    <el-radio :label="3">经常吃（3~5次/周）</el-radio>
                    <el-radio :label="4">每天都吃（>5次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题10 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="10.您吃豆类及豆制品吗？" prop="eat11">
                  <el-radio-group v-model="form.eat11">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃（1~2次/周）</el-radio>
                    <el-radio :label="3">经常吃（3~5次/周）</el-radio>
                    <el-radio :label="4">每天都吃（>5次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题11 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="11.您吃水果吗？" prop="eat12">
                  <el-radio-group v-model="form.eat12">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃（1~2次/周）</el-radio>
                    <el-radio :label="3">经常吃（3~5次/周）</el-radio>
                    <el-radio :label="4">每天都吃（>5次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题12 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="12.您平均每天吃多少蔬菜？" prop="eat13">
                  <el-radio-group v-model="form.eat13">
                    <el-radio :label="1">小于100g</el-radio>
                    <el-radio :label="2">100~200g</el-radio>
                    <el-radio :label="3">200~500g</el-radio>
                    <el-radio :label="4">大于500g</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题13 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="13.您平均每天吃多少肉（猪、牛、羊、禽）？" prop="eat14">
                  <el-radio-group v-model="form.eat14">
                    <el-radio :label="1">小于50g</el-radio>
                    <el-radio :label="2">50~100g</el-radio>
                    <el-radio :label="3">100~250g</el-radio>
                    <el-radio :label="4">大于250g</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题14 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="14.您吃肥肉吗？" prop="eat15">
                  <el-radio-group v-model="form.eat15">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃一点</el-radio>
                    <el-radio :label="3">经常吃</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题15 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="15.您吃动物内脏吗？" prop="eat16">
                  <el-radio-group v-model="form.eat16">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃（1~2次/周）</el-radio>
                    <el-radio :label="3">经常吃（>3 次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题16 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="16.您吃鱼肉或海鲜吗？" prop="eat17">
                  <el-radio-group v-model="form.eat17">
                    <el-radio :label="1">不吃</el-radio>
                    <el-radio :label="2">偶尔吃（1~2次/周）</el-radio>
                    <el-radio :label="3">经常吃（>3 次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题17 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="17.您喝咖啡吗？" prop="eat18">
                  <el-radio-group v-model="form.eat18">
                    <el-radio :label="1">不喝</el-radio>
                    <el-radio :label="2">偶尔喝（1~2次/周）</el-radio>
                    <el-radio :label="3">经常喝（>3 次/周）</el-radio>
                    <el-radio :label="4">每天都喝（>5 次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
             <!-- 问题18 -->
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="18.您喝含糖饮料（果汁、可乐等）吗？" prop="eat19">
                  <el-radio-group v-model="form.eat19">
                    <el-radio :label="1">不喝</el-radio>
                    <el-radio :label="2">偶尔喝（1~2次/周）</el-radio>
                    <el-radio :label="3">经常喝（>3 次/周）</el-radio>
                    <el-radio :label="4">每天都喝（>5 次/周）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十、吸烟史 -->
          <el-descriptions title="十、吸烟史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您吸烟吗？（持续吸烟1年以上）" prop="smoke1">
                  <el-radio-group v-model="form.smoke1">
                    <el-radio :label="1">不吸</el-radio>
                    <el-radio :label="2">吸烟</el-radio>
                    <el-radio :label="3">吸烟，已戒（戒烟1年以上）</el-radio>
                    <el-radio :label="4">被动吸烟（每天累计15分钟以上，且每周1天以上）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您通常每天吸多少支烟？（含戒烟前）" prop="smoke2">
                  <el-input-number 
                    v-model="form.smoke2" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 支
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您持续吸烟的年限？（含戒烟前）" prop="smoke3">
                  <el-input-number 
                    v-model="form.smoke3" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 年
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您戒烟多长时间了？" prop="smoke4">
                  <el-input-number 
                    v-model="form.smoke4" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 年
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十一、饮酒史 -->
          <el-descriptions title="十一、饮酒史" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您喝酒吗？（平均每周饮酒1次以上）" prop="drink1">
                  <el-radio-group v-model="form.drink1">
                    <el-radio :label="1">不喝</el-radio>
                    <el-radio :label="2">喝</el-radio>
                    <el-radio :label="3">以前喝，现已戒酒（戒酒1年以上）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您一般喝什么酒？" prop="drink2">
                  <el-radio-group v-model="form.drink2">
                    <el-radio :label="1">白酒</el-radio>
                    <el-radio :label="2">啤酒</el-radio>
                    <el-radio :label="3">红酒</el-radio>
                    <el-radio :label="4">什么都喝</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您每次喝几两？（ 1 两相当于 50 ml 白酒,100 ml 红酒,300 ml 啤酒）？" prop="drink3">
                  <el-radio-group v-model="form.drink3">
                    <el-radio :label="1">1-2两</el-radio>
                    <el-radio :label="2">2-3两</el-radio>
                    <el-radio :label="3">3-4两</el-radio>
                    <el-radio :label="4">5两以上</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您持续喝酒的年限?（含戒酒前）" prop="drink4">
                  <el-input-number 
                    v-model="form.drink4" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 年
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您戒酒多长时间了" prop="drink5">
                  <el-input-number 
                    v-model="form.drink5" 
                    :min="0" 
                    :max="999" 
                    size="small" 
                    style="width: 100px" 
                  /> 年
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十二、运动锻炼 -->
          <el-descriptions title="十二、运动锻炼" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您参加运动锻炼吗？" prop="sport1">
                  <el-radio-group v-model="form.sport1">
                    <el-radio :label="1">不参加</el-radio>
                    <el-radio :label="2">偶然参加</el-radio>
                    <el-radio :label="3">经常参加（平均每周锻炼3次及以上，每次锻炼＞30分钟）</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您常采用的运动锻炼方式：（可多选）" prop="sport11">
                  <el-checkbox-group v-model="form.sport11">
                    <el-row>
                      <el-col :span="6" v-for="item in sportOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您每周锻炼几次？" prop="sport2">
                  <el-radio-group v-model="form.sport2">
                    <el-radio :label="1">1～2次</el-radio>
                    <el-radio :label="2">3～5次</el-radio>
                    <el-radio :label="3">＞5次</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您每次锻炼多长时间？" prop="sport3">
                  <el-radio-group v-model="form.sport3">
                    <el-radio :label="1">＜30分钟</el-radio>
                    <el-radio :label="2">30～60分钟</el-radio>
                    <el-radio :label="3">＞60分钟</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您坚持锻炼多少年了" prop="sport5">
                  <el-input-number 
                    v-model="form.sport5" 
                    :min="0" 
                    :max="99" 
                    size="small" 
                    style="width: 100px" 
                  /> 年
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您工作中的体力强度？" prop="sport6">
                  <el-radio-group v-model="form.sport6">
                    <el-radio :label="1">脑力劳动为主</el-radio>
                    <el-radio :label="2">轻体力劳动</el-radio>
                    <el-radio :label="3">中度体力劳动</el-radio>
                    <el-radio :label="4">重体力劳动</el-radio>
                    <el-radio :label="5">不工作</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="7.您每周工作几天？" prop="sport7">
                  <el-radio-group v-model="form.sport7">
                    <el-radio :label="1">＜3天</el-radio>
                    <el-radio :label="2">3~5天</el-radio>
                    <el-radio :label="3">＞5天</el-radio>
                    <el-radio :label="4">不工作</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="8.您每天平均工作多长时间" prop="sport8">
                  <el-input-number 
                    v-model="form.sport8" 
                    :min="0" 
                    :max="24" 
                    size="small" 
                    style="width: 100px" 
                  /> 小时
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="9.除工作、学习时间外，您每天坐着（如看电视、上网、打麻将、打牌等）的时间是？" prop="sport9">
                  <el-radio-group v-model="form.sport9">
                    <el-radio :label="1">＜2小时</el-radio>
                    <el-radio :label="2">2~4小时</el-radio>
                    <el-radio :label="3">4~6小时</el-radio>
                    <el-radio :label="4">＞6小时</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十三、生活工作环境 -->
          <el-descriptions title="十三、生活工作环境" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您的工作/生活场所经常会接触到哪些有害物质？" prop="environment1">
                  <el-checkbox-group v-model="form.environment1">
                    <el-row>
                      <el-col :span="6" v-for="item in environmentOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十四、精神压力（最近两周） -->
          <el-descriptions title="十四、精神压力（最近两周）" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您感到闷闷不乐,情绪低落吗？" prop="spirit1">
                  <el-radio-group v-model="form.spirit1">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您容易情绪激动或生气吗？" prop="spirit2">
                  <el-radio-group v-model="form.spirit2">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您感到精神紧张,很难放松吗？" prop="spirit3">
                  <el-radio-group v-model="form.spirit3">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您比平常容易紧张和着急吗？" prop="spirit4">
                  <el-radio-group v-model="form.spirit4">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您容易发脾气,没有耐性吗？" prop="spirit5">
                  <el-radio-group v-model="form.spirit5">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您感到心力枯竭，对人对事缺乏热情吗？" prop="spirit6">
                  <el-radio-group v-model="form.spirit6">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="7.您容易焦虑不安、心烦意乱吗？" prop="spirit7">
                  <el-radio-group v-model="form.spirit7">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="8.您感觉压抑或沮丧吗？" prop="spirit8">
                  <el-radio-group v-model="form.spirit8">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="9.您注意力集中有困难吗？" prop="spirit9">
                  <el-radio-group v-model="form.spirit9">
                    <el-radio :label="1">没有</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十五、睡眠健康 -->
          <el-descriptions title="十五、睡眠健康" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.最近1个月，您的睡眠如何？" prop="sleep1">
                  <el-radio-group v-model="form.sleep1">
                    <el-radio :label="1">好</el-radio>
                    <el-radio :label="2">一般</el-radio>
                    <el-radio :label="3">差</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您睡眠差的主要表现：" prop="sleep2">
                  <el-checkbox-group v-model="form.sleep2">
                    <el-row>
                      <el-col :span="6" v-for="item in sleepProblemOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.sleep2Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写其他表现"
                    v-if="form.sleep2.includes('3')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.影响您睡眠差的主要原因：" prop="sleep3">
                  <el-checkbox-group v-model="form.sleep3">
                    <el-row>
                      <el-col :span="8" v-for="item in sleepCauseOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                  <el-input 
                    v-model="form.sleep3Other" 
                    size="small" 
                    style="width: 300px; margin-top: 10px;"
                    placeholder="请填写其他原因"
                    v-if="form.sleep3.includes('8')"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您每天平均睡眠时间：（不等于卧床时间）" prop="sleep4">
                  <el-radio-group v-model="form.sleep4">
                    <el-radio :label="1">＜5小时</el-radio>
                    <el-radio :label="2">5~7小时</el-radio>
                    <el-radio :label="3">7~9小时</el-radio>
                    <el-radio :label="4">＞9小时</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 十六、健康素养 -->
          <el-descriptions title="十六、健康素养" :column="1" :colon="false" border></el-descriptions>
          <div style="margin: 15px 0;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="1.您多长时间做一次体检？" prop="examination1">
                  <el-radio-group v-model="form.examination1">
                    <el-radio :label="1">从来不做</el-radio>
                    <el-radio :label="2">半年</el-radio>
                    <el-radio :label="3">1年</el-radio>
                    <el-radio :label="4">2～3年</el-radio>
                    <el-radio :label="5">＞3年</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="2.您是否主动获取医疗保健知识？" prop="examination2">
                  <el-radio-group v-model="form.examination2">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="3.您获取医疗保健知识的途径？" prop="examination3">
                  <el-checkbox-group v-model="form.examination3">
                    <el-row>
                      <el-col :span="6" v-for="item in knowledgeSourceOptions" :key="item.id">
                        <el-checkbox :label="item.id">{{ item.text }}</el-checkbox>
                      </el-col>
                    </el-row>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="4.您入厕观察二便（大小便）吗？" prop="examination4">
                  <el-radio-group v-model="form.examination4">
                    <el-radio :label="1">从不</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="5.您自测血压、心率吗？" prop="examination5">
                  <el-radio-group v-model="form.examination5">
                    <el-radio :label="1">从不</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="6.您出差或旅游带常用或急救药品吗？" prop="examination6">
                  <el-radio-group v-model="form.examination6">
                    <el-radio :label="1">从不</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="7.您乘坐私家车或出租车时系安全带吗？" prop="examination7">
                  <el-radio-group v-model="form.examination7">
                    <el-radio :label="1">从来不系</el-radio>
                    <el-radio :label="2">有时系</el-radio>
                    <el-radio :label="3">每次都系</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="8.您经常晒太阳吗？" prop="examination8">
                  <el-radio-group v-model="form.examination8">
                    <el-radio :label="1">从不</el-radio>
                    <el-radio :label="2">偶尔</el-radio>
                    <el-radio :label="3">经常</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="9.您认为以下血压值哪个最理想？" prop="examination9">
                  <el-radio-group v-model="form.examination9">
                    <el-radio :label="1">140/90mmHg</el-radio>
                    <el-radio :label="2">120/80mmHg</el-radio>
                    <el-radio :label="3">150/100 mmHg</el-radio>
                    <el-radio :label="4">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="10.您认为成年人腋下体温最理想的范围是？" prop="examination10">
                  <el-radio-group v-model="form.examination10">
                    <el-radio :label="1">35~36℃</el-radio>
                    <el-radio :label="2">36~37℃</el-radio>
                    <el-radio :label="3">37~38℃</el-radio>
                    <el-radio :label="4">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="11.您认为安静状态下成年人最理想的脉搏次数是？" prop="examination11">
                  <el-radio-group v-model="form.examination11">
                    <el-radio :label="1">30~50 次/分钟</el-radio>
                    <el-radio :label="2">51~70 次/分钟</el-radio>
                    <el-radio :label="3">71~90 次/分钟</el-radio>
                    <el-radio :label="4">>90 次/分钟</el-radio>
                    <el-radio :label="5">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="12.您认为成年人每天最佳食盐量不要超过多少克？" prop="examination12">
                  <el-radio-group v-model="form.examination12">
                    <el-radio :label="1">6 克</el-radio>
                    <el-radio :label="2">8 克</el-radio>
                    <el-radio :label="3">10 克</el-radio>
                    <el-radio :label="4">12 克</el-radio>
                    <el-radio :label="5">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="13.您认为成年人正常体重指数是（体重指数=体重kg/身高㎡）？" prop="examination13">
                  <el-radio-group v-model="form.examination13">
                    <el-radio :label="1">18.5</el-radio>
                    <el-radio :label="2">18.5-24.9</el-radio>
                    <el-radio :label="3">25 - 29.9</el-radio>
                    <el-radio :label="4">30 以上</el-radio>
                    <el-radio :label="5">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="14.您认为成年人(男性)正常腰围是？" prop="examination14">
                  <el-radio-group v-model="form.examination14">
                    <el-radio :label="1">WOcm</el-radio>
                    <el-radio :label="2">W85 cm</el-radio>
                    <el-radio :label="3">W90cm</el-radio>
                    <el-radio :label="4">W95 cm</el-radio>
                    <el-radio :label="5">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="15.您认为成年人(女性)正常腰围是？" prop="examination15">
                  <el-radio-group v-model="form.examination15">
                    <el-radio :label="1">W70cm</el-radio>
                    <el-radio :label="2">W75 cm</el-radio>
                    <el-radio :label="3">W80 cm</el-radio>
                    <el-radio :label="4">W85 cm</el-radio>
                    <el-radio :label="5">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="16.您认为成人三酰甘油正常值是？" prop="examination16">
                  <el-radio-group v-model="form.examination16">
                    <el-radio :label="1">0.56 mmol/L</el-radio>
                    <el-radio :label="2">0.56 ~ 1.7 mmol/L</el-radio>
                    <el-radio :label="3">>1.7 mmol/L</el-radio>
                    <el-radio :label="4">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="17.您认为成人总胆固醇理想值是？" prop="examination17">
                  <el-radio-group v-model="form.examination17">
                    <el-radio :label="1">5.2 mmol/L</el-radio>
                    <el-radio :label="2">5.2 ~ 6.1 mmol/L</el-radio>
                    <el-radio :label="3">>6.1 mmol/L</el-radio>
                    <el-radio :label="4">不知道</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="18.答完该问卷后，您对自己的健康状态感觉如何？" prop="examination18">
                  <el-radio-group v-model="form.examination18">
                    <el-radio :label="1">很好</el-radio>
                    <el-radio :label="2">比较好</el-radio>
                    <el-radio :label="3">一般（还可以）</el-radio>
                    <el-radio :label="4">不好或较差</el-radio>
                    <el-radio :label="5">不好说</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="19.您对该健康自测问卷的总体印象是？" prop="examination19">
                  <el-radio-group v-model="form.examination19">
                    <el-radio :label="1">很好</el-radio>
                    <el-radio :label="2">比较好</el-radio>
                    <el-radio :label="3">一般（还可以）</el-radio>
                    <el-radio :label="4">不好或较差</el-radio>
                    <el-radio :label="5">不好说</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <!-- 保存按钮 -->
          <div style="text-align: center; margin-top: 20px;">
            <el-button type="primary" size="medium" @click="onOk">保存问卷</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import { getListData, saveListData, getByCode } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 是否显示弹出层
      open: false,
      headParams: {
        isVIP: undefined,
      },
      patientcode: '',
      listQuery: {
        //患者号
        patientCode: undefined,
        //科室id
        ksId: undefined,
      },
      // 筛选参数
      queryParams: {
        physicalNo: undefined,
        supplement: undefined,
      },
      sportOptions: [
        { id: '1', text: '散步' },
        { id: '2', text: '慢跑' },
        { id: '3', text: '游泳' },
        { id: '4', text: '骑自行车' },
        { id: '5', text: '爬楼梯' },
        { id: '6', text: '球类' },
        { id: '7', text: '交谊舞' },
        { id: '8', text: '瑜伽' },
        { id: '9', text: '健身操' },
        { id: '10', text: '力量锻炼' },
        { id: '11', text: '登山' },
        { id: '12', text: '太极拳' },
        { id: '13', text: '其他' }
      ],
      environmentOptions: [
        { id: '1', text: '无或很少' },
        { id: '2', text: '噪音、震动' },
        { id: '3', text: '电磁辐射' },
        { id: '4', text: '粉尘' },
        { id: '5', text: '化学污染' },
        { id: '6', text: '空气污染' },
        { id: '7', text: '建筑装修污染' },
        { id: '8', text: '烹饪油烟' },
        { id: '9', text: '其他' }
      ],
      sleepProblemOptions: [
        { id: '1', text: '入睡困难' },
        { id: '2', text: '多梦或噩梦中惊醒' },
        { id: '3', text: '其他' }
      ],
      sleepCauseOptions: [
        { id: '1', text: '工作压力过大' },
        { id: '2', text: '负性生活事件' },
        { id: '3', text: '环境干扰（如噪音、配偶或室友打鼾等）' },
        { id: '4', text: '身体不适或疾病' },
        { id: '5', text: '气候变化' },
        { id: '6', text: '药物' },
        { id: '7', text: '倒班或倒时差' },
        { id: '8', text: '其他' }
      ],
      knowledgeSourceOptions: [
        { id: '1', text: '电视' },
        { id: '2', text: '广播' },
        { id: '3', text: '图书和报刊杂志' },
        { id: '4', text: '上网' },
        { id: '5', text: '卫生机构及医生' },
        { id: '6', text: '其他' }
      ],
      familyOptions: [
          { id: '1', text: '高血压病' },
          { id: '2', text: '脑卒中' },
          // ...其他选项
          { id: '26', text: '其他' }
      ],
      tumorOptions: [
          { id: '1', text: '肺癌' },
          { id: '2', text: '肝癌' },
          // ...其他选项
          { id: '19', text: '其他' }
      ],
      // 数据
      form: {
        // 初始化所有数组字段为空数组
        family2: [],
        family3: [],
        present2: [],
        present21: [],
        allergy2: [],
        medication2: [],
        operation2: [],
        sleep2: [],
        sleep3: [],
        sport11: [],
        environment1: [],
        examination3: [],
        // 基本信息
        patientcode: '',
        patientname: '',
        idSex: null,
        birthdate: '',
        idcardno: '',
        isHan: null,
        idProvince: '',
        idMarriage: null,
        phone: '',
        
        // 家族史
        family1: null,
        family2Other: '',
        family3Other: '',
        family4: null,
        
        // 现病史
        present1: null,
        present2Other: '',
        present21Other: '',
        present3: null,
        
        // 过敏史
        allergy1: null,
        allergy2Other: '',
        
        // 用药史
        medication1: null,
        medication2Other: '',
        
        // 手术史
        operation1: null,
        operation1Other: '',
        operation2Other: '',
        
        // 月经生育史
        birth1: null,
        birth2: null,
        birth2Other: '',
        birth3: null,
        birth4: null,
        birth5: null,
        birth6: null,
        birth7: null,
        birth8: null,
        birth8Other: '',
        birth9: null,
        birth10: null,
        
        // 躯体症状
        body1: null,
        body2: null,
        body21: null,
        body22: null,
        body23: null,
        body24: null,
        body25: null,
        body26: null,
        body3: null,
        body31: null,
        body32: null,
        body33: null,
        body4: null,
        body41: null,
        body5: null,
        body6: null,
        body7: null,
        body71: null,
        body72: null,
        body73: null,
        body8: null,
        body81: null,
        body82: null,
        body9: null,
        body91: null,
        body92: [],
        
        // 饮食习惯
        eat1: null,
        eat2: null,
        eat4: null,
        eat5: null,
        eat6: [],
        eat7: [],
        eat8: null,
        eat9: null,
        eat10: null,
        eat11: null,
        eat12: null,
        eat13: null,
        eat14: null,
        eat15: null,
        eat16: null,
        eat17: null,
        eat18: null,
        eat19: null,
        
        // 吸烟史
        smoke1: null,
        smoke2: null,
        smoke3: null,
        smoke4: null,
        
        // 饮酒史
        drink1: null,
        drink2: [],
        drink3: null,
        drink4: null,
        drink5: null,
        
        // 运动锻炼
        sport1: null,
        sport11: [],
        sport2: null,
        sport3: null,
        sport5: null,
        sport6: null,
        sport7: null,
        sport8: null,
        sport9: null,
        
        // 生活工作环境
        environment1: [],
        
        // 精神压力
        spirit1: null,
        spirit2: null,
        spirit3: null,
        spirit4: null,
        spirit5: null,
        spirit6: null,
        spirit7: null,
        spirit8: null,
        spirit9: null,
        
        // 睡眠健康
        sleep1: null,
        sleep2: [],
        sleep2Other: '',
        sleep3: [],
        sleep4: null,
        
        // 健康素养 
        examination1: null,
        examination2: null,
        examination3: null,
        examination4: null,
        examination5: null,
        examination6: null,
        examination7: null,
        examination8: null,
        examination9: null,
        examination10: null,
        examination11: null,
        examination12: null,
        examination13: null,
        examination14: null,
        examination15: null,
        examination16: null,
        examination17: null,
        examination18: null,
        examination19: null
      },
      provinceOptions: [
        {id: '1', resarea: '北京市'},
        {id: '2', resarea: '上海市'},
        // 其他省份...
      ],
      presentOptions: [
        {id: '1', text: '高血压'},
        {id: '2', text: '脑卒中'},
        // 其他选项...
        {id: '26', text: '其他'}
      ],
      allergyOptions: [
        {id: '1', text: '青霉素'},
        {id: '2', text: '磺胺类'},
        {id: '26', text: '其他'}
      ],
      medicationOptions: [
        {id: '1', text: '降压药'},
        {id: '2', text: '降糖药'},
        {id: '5', text: '其他'}
      ],
      operationOptions: [
        {id: '1', text: '头颅（含脑）'},
        {id: '2', text: '眼'},
        {id: '18', text: '其他'}
      ],
      bodyPainOptions: [
        {id: '1', text: '头'},
        {id: '2', text: '颈肩'},
      ],
      // 性别选项
      sexList: [
        { value: '0', text: '男' },
        { value: '1', text: '女' },
      ],
      // 民族选项 
      nationList: [
        { value: '1', text: '汉' },
        { value: '0', text: '少数民族' },
      ],
      // 文化程度选项
      culturalList: [
        { value: 0, text: '小学及以下' },
        { value: 1, text: '初中、高中、中专、技校' },
        { value: 2, text: '大学本科或专科' },
        { value: 3, text: '研究生及以上' },
      ],
      // 婚姻状况选项
      marriageList: [
        { value: 1, text: '未婚' },
        { value: 2, text: '已婚' },
        { value: 3, text: '离婚' },
        { value: 4, text: '丧偶' },
        { value: 5, text: '其他' },
      ],
      // 职业状况选项
      careerList: [
        { value: '1', text: '国家公务员' },
        { value: '2', text: '专业技术人员' },
        { value: '3', text: '职员' },
        { value: '4', text: '企业管理人员' },
        { value: '5', text: '工人' },
        { value: '6', text: '农民' },
        { value: '7', text: '学生' },
        { value: '8', text: '现役军人' },
        { value: '9', text: '自由职业者' },
        { value: '10', text: '个体经营者' },
        { value: '11', text: '无业人员' },
        { value: '12', text: '退(离)休人员' },
        { value: '13', text: '其他' },
      ],
      range: [
        { value: '0', text: '很好' },
        { value: '1', text: '好' },
        { value: '2', text: '一般' },
        { value: '3', text: '不好' },
        { value: '4', text: '很不好' },
      ],
      range2: [
        { value: '0', text: '无' },
        { value: '1', text: '有' },
      ],
      range3: [
        { value: '0', text: '按照医嘱按时服药' },
        { value: '1', text: '家族史（父母亲及兄弟姐妹）' },
        { value: '2', text: '已经用药' },
      ],
      range4: [
        { value: '0', text: '吸烟' },
        { value: '1', text: '不吸烟但有被动吸烟' },
        { value: '2', text: '不吸烟且无被动吸烟' },
        { value: '3', text: '已戒烟' },
      ],
      range5: [
        { value: '0', text: '不' },
        { value: '1', text: '已戒' },
        { value: '2', text: '每月少于1次' },
        { value: '3', text: '每月1~10次' },
        { value: '4', text: '每月超过10次' },
      ],
      range6: [
        { value: '0', text: '烈性酒' },
        { value: '1', text: '啤酒' },
        { value: '2', text: '葡萄酒、米酒或黄酒' },
        { value: '3', text: '其他' },
      ],
      range7: [
        { value: '0', text: '荤素均衡' },
        { value: '1', text: '荤食为主' },
        { value: '2', text: '素食为主' },
      ],
      range8: [
        { value: '0', text: '多油' },
        { value: '1', text: '多盐' },
        { value: '2', text: '多糖' },
        { value: '3', text: '辛辣' },
        { value: '4', text: '清淡' },
      ],
      range9: [
        { value: '0', text: '较好' },
        { value: '1', text: '一般' },
        { value: '2', text: '较差' },
      ],
      range10: [
        { value: '0', text: '否' },
        { value: '1', text: '是' },
      ],
      range11: [
        { value: '0', text: '无' },
        { value: '1', text: '1~2次/周' },
        { value: '2', text: '3~5次/周' },
        { value: '3', text: '＞5次/周' },
      ],
      range12: [
        { value: '0', text: '很小' },
        { value: '1', text: '较小' },
        { value: '2', text: '适中' },
        { value: '3', text: '较大' },
        { value: '4', text: '很大' },
      ],
      range13: [
        { value: '0', text: '积极乐观' },
        { value: '1', text: '平静不易改变' },
        { value: '2', text: '非常容易受外界影响' },
        { value: '3', text: '消极低落' },
      ],
      range14: [
        { value: '1', text: '没有' },
        { value: '0', text: '有' },
        { value: '2', text: '不知道' },
      ],
      range15: [
        { value: '0', text: '没有' },
        { value: '1', text: '有' },
      ],
      // 表单校验
      rules: {
        // patientname: [{ required: true, message: '姓名不能为空', trigger: 'change' }],
      },
    }
  },
  created() {
    this.form.ksId = 'ff80808178f705d001790c1d17b453e2'
  },
  methods: { 
     // 保存问卷 
    onOk() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.save();
        }
      })
    },
    //保存 
    save() {
      // 将所有数组字段转换为字符串
      const arrayFields = ['family2', 'family3', 'present2', 'present21', 
                          'allergy2', 'medication2', 'operation2', 
                          'sleep2', 'sleep3', 'sport11', 'environment1', 
                          'examination3'];
      
      arrayFields.forEach(field => {
        if (this.form[field] && Array.isArray(this.form[field])) {
          this.form[field] = this.form[field].join(',');
        }
      });
      this.form.ksId = 'ff80808178f705d001790c1d17b453e2'


      console.log("转换后的提交值",this.form);
      
      // 使用转换后的表单数据 
      saveListData(this.form).then((response) => {
        if(response.code == 200){
           this.$modal.msgSuccess('保存成功')
        }
        this.getData();
      });
    },
    // 显示弹窗
    handleShow(patientcode) {
      this.reset()
      this.form.patientcode = patientcode
      this.patientcode = patientcode
      this.open = true
      this.getData()
    },
    // 获取详情
    getData() {
      getListData({
        ksId: 'ff80808178f705d001790c1d17b453e2',
        patientCode: this.patientcode,
      }).then((res) => {
        if (res.code !== 200 || !res.data || !res.data.second) {
          // this.$message({
          //   message: '本次体检用户已放弃填写问卷',
          //   type: 'warning',
          // });
          this.form = this.getInitialFormData();
        } else {
          
          this.form = { ...res.data.second }; 
          
          // 确保所有数组属性都被初始化为数组
          const arrayFields = [
            'family2', 'family3', 'present2', 'present21', 
            'allergy2', 'medication2', 'operation2', 'sleep2', 
            'sleep3', 'sport11', 'environment1', 'examination3'
          ];
          
          arrayFields.forEach(field => {
            // 如果字段不存在、为 null 或 undefined，初始化为空数组
            if (this.form[field] === null || this.form[field] === undefined) {
              this.form[field] = [];
            } 
            // 如果是字符串类型（如接口返回逗号分隔的字符串），转换为数组
            else if (typeof this.form[field] === 'string') {
              this.form[field] = this.form[field].split(',');
            }
          });


          // 2. 非数组字段类型修复 
          const typeFixFields = {
            
            // 家族史
            family1: 'number',
            
            // 现病史
            present1: 'number',
            present3: 'number',
            
            // 过敏史
            allergy1: 'number',
            
            // 用药史
            medication1: 'number',
            
            // 手术史
            operation1: 'number',
            
            // 月经生育史
            birth1: 'number',
            birth2: 'number',
            birth3: 'number',
            birth4: 'number',
            birth5: 'number',
            birth6: 'number',
            birth7: 'number',
            birth8: 'number',
            birth9: 'number',
            birth10: 'number',

            // 躯体症状
            body1: 'number',
            body2: 'number',
            body21: 'number',
            body22: 'number',
            body23: 'number',
            body24: 'number',
            body25: 'number',
            body26: 'number',
            body3: 'number',
            body31: 'number',
            body32: 'number',
            body33: 'number',
            body4: 'number',
            body41: 'number',
            body5: 'number',
            body6: 'number',
            body7: 'number',
            body71: 'number',
            body72: 'number',
            body73: 'number',
            body8: 'number',
            body81: 'number',
            body82: 'number',
            body9: 'number',
            body91: 'number',
            
            // 饮食习惯
            eat1: 'number',
            eat2: 'number',
            eat4: 'number',
            eat5: 'number',
            eat8: 'number',
            eat9: 'number',
            eat10: 'number',
            eat11: 'number',
            eat12: 'number',
            eat13: 'number',
            eat14: 'number',
            eat15: 'number',
            eat16: 'number',
            eat17: 'number',
            eat18: 'number',
            eat19: 'number',
            
            // 吸烟史
            smoke1: 'number',
            smoke2: 'number',
            smoke3: 'number',
            smoke4: 'number',
            
            // 饮酒史
            drink1: 'number',
            drink3: 'number',
            drink4: 'number',
            drink5: 'number',
            
            // 运动锻炼
            sport1: 'number',
            sport2: 'number',
            sport3: 'number',
            sport5: 'number',
            sport6: 'number',
            sport7: 'number',
            sport8: 'number',
            sport9: 'number',
            
            
            // 精神压力
            spirit1: 'number',
            spirit2: 'number',
            spirit3: 'number',
            spirit4: 'number',
            spirit5: 'number',
            spirit6: 'number',
            spirit7: 'number',
            spirit8: 'number',
            spirit9: 'number',

            // 睡眠健康
            sleep1: 'number',
            sleep4: 'number',


            // 健康素养 
            examination1: 'number',
            examination2: 'number',
            examination3: 'number',
            examination4: 'number',
            examination5: 'number',
            examination6: 'number',
            examination7: 'number',
            examination8: 'number',
            examination9: 'number',
            examination10: 'number',
            examination11: 'number',
            examination12: 'number',
            examination13: 'number',
            examination14: 'number',
            examination15: 'number',
            examination16: 'number',
            examination17: 'number',
            examination18: 'number',
            examination19: 'number'

          };

          Object.keys(typeFixFields).forEach(key => {
            const targetType = typeFixFields[key];
            if (this.form[key] !== null && typeof this.form[key] === 'string') {
              // 移除非数字字符后转换
              const num = parseInt(this.form[key].replace(/\D/g, ''), 10);
              this.form[key] = isNaN(num) ? null : num;
            }
          });

          // 3. 特殊逻辑处理（如性别、婚姻状况等）
          this.form.idSex = this.form.idSex === '1' ? 1 : 0; // 假设后端返回'1'表示男
          this.form.idMarriage = parseInt(this.form.idMarriage, 10);

          console.log('修复后的数据:', this.form);
        }
      })
    },
    // 搜索
    handleQuery() {
      this.getData()
    },
    // 表单重置
    reset() {
      this.form = this.getInitialFormData();
      this.resetForm('form');
    },
    // 获取初始表单数据
    getInitialFormData() {
      return {
        // 基本信息 
        patientcode: '',
        patientname: '',
        idSex: null,
        birthdate: '',
        idcardno: '',
        isHan: null,
        idProvince: '',
        idMarriage: null,
        phone: '',
        
        // 家族史
        family1: null,
        family2: [],
        family2Other: '',
        family3: [],
        family3Other: '',
        family4: null,
        
        // 现病史
        present1: null,
        present2: [],
        present2Other: '',
        present21: [],
        present21Other: '',
        present3: null,
        
        // 过敏史
        allergy1: null,
        allergy2: [],
        allergy2Other: '',
        
        // 用药史 
        medication1: null,
        medication2: [],
        medication2Other: '',
        
        // 手术史
        operation1: null,
        operation1Other: '',
        operation2: [],
        operation2Other: '',
        
        // 月经生育史
        birth1: null,
        birth2: null,
        birth2Other: '',
        birth3: null,
        birth4: null,
        birth5: null,
        birth6: null,
        birth7: null,
        birth8: null,
        birth8Other: '',
        birth9: null,
        birth10: null,
        
        // 躯体症状
        body1: null,
        body2: null,
        body21: null,
        body22: null,
        body23: null,
        body24: null,
        body25: null,
        body26: null,
        body3: null,
        body31: null,
        body32: null,
        body33: null,
        body4: null,
        body41: null,
        body5: null,
        body6: null,
        body7: null,
        body71: null,
        body72: null,
        body73: null,
        body8: null,
        body81: null,
        body82: null,
        body9: null,
        body91: null,
        body92: [],
        
        // 饮食习惯
        eat1: null,
        eat2: null,
        eat4: null,
        eat5: null,
        eat6: [],
        eat7: [],
        eat8: null,
        eat9: null,
        eat10: null,
        eat11: null,
        eat12: null,
        eat13: null,
        eat14: null,
        eat15: null,
        eat16: null,
        eat17: null,
        eat18: null,
        eat19: null,
        
        // 吸烟史
        smoke1: null,
        smoke2: null,
        smoke3: null,
        smoke4: null,
        
        // 饮酒史
        drink1: null,
        drink2: [],
        drink3: null,
        drink4: null,
        drink5: null,
        
        // 运动锻炼
        sport1: null,
        sport11: [],
        sport2: null,
        sport3: null,
        sport5: null,
        sport6: null,
        sport7: null,
        sport8: null,
        sport9: null,
        
        // 生活工作环境
        environment1: [],
        
        // 精神压力
        spirit1: null,
        spirit2: null,
        spirit3: null,
        spirit4: null,
        spirit5: null,
        spirit6: null,
        spirit7: null,
        spirit8: null,
        spirit9: null,
        
        // 睡眠健康
        sleep1: null,
        sleep2: [],
        sleep2Other: '',
        sleep3: [],
        sleep4: null,
        
        // 健康素养 
        examination1: null,
        examination2: null,
        examination3: null,
        examination4: null,
        examination5: null,
        examination6: null,
        examination7: null,
        examination8: null,
        examination9: null,
        examination10: null,
        examination11: null,
        examination12: null,
        examination13: null,
        examination14: null,
        examination15: null,
        examination16: null,
        examination17: null,
        examination18: null,
        examination19: null
      };
    },
  },
}
</script>
<style lang="scss">
.health-questionnaire {
  .el-dialog {
    height: 100%;

    .el-dialog__body {
      padding: 20px 32px;
    }
  }

   /* 优化表单标签宽度和间距 */
  .el-form-item {
      align-items: center; /* 顶部对齐 */

    margin-bottom: 8px; /* 减少表单项间距 */
    
    .el-form-item__label {
      width: 300px !important; /* 增加标签宽度 */
      line-height: 1.4; /* 更好的换行效果 */
      margin-right: 12px; /* 标签右侧增加间距 */
      color: #333;
      font-weight: normal;
      white-space: normal; /* 允许多行显示 */
    }
    
    .el-form-item__content {
      justify-content: center;
      margin-left: 120px !important; /* 与标签宽度匹配 */
    }
  }

  // 顶部筛选   
  .no-margin-bottom .el-form-item {
    .el-form-item__label {
      color: #000 !important;
    }
  }
  .el-radio__input.is-checked .el-radio__inner {
    border-color: #000;
    background: #000;
  }
  .el-radio.is-checked .el-radio__label {
    color: #000;
    font-weight: 600;
  }

   .questionnaire-title {
    text-align: center;
    font-size: 22px;
    line-height: 1.6;
    color: #1a2b5a;
    margin-bottom: 20px;
  }
  .table-box {
    .el-descriptions-item__label {
      margin: 0;
    }
    .el-descriptions__title {
      font-size: 24px;
    }
    .el-form-item__label,
    .el-descriptions-item__label,
    .el-input__inner {
      font-weight: 400;
      font-size: 22px;
      color: #333333;
    }
    .el-radio__label,
    .el-checkbox__label {
      font-size: 22px;
    }

    .flex {
      display: flex;
      align-items: flex-end;

      .el-checkbox-group {
        flex: 1;
        line-height: normal;

        .el-checkbox {
          width: 136px;
          margin-right: 0;
          line-height: 36px;
        }
      }
    }
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
