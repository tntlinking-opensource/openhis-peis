<!-- 科室-头部按钮等  开发人：麦沃德科技 予安 -->
<template>
  <div class="section-list section-header">
    <div class="section-list-top">
      <!-- 第一排 -->
      <div class="flex" style="padding: 10px 20px 0">
        <!-- 筛选 -->
        <el-form :model="headParams" ref="headParams" size="small" :inline="true" @submit.native.prevent>
          <el-form-item label="体检号" prop="patientcode">
            <input ref="patientcode" v-model="headParams.patientcode" placeholder="请输入体检号" clearable class="input-style" style="width: 230px" @keyup.enter="handleRead" />
          </el-form-item>
          <el-form-item prop="autoFill">
            <el-checkbox v-model="headParams.autoFill">是否补全</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button class="section-btn-style1" @click="handleRead">查询</el-button>
          </el-form-item>
          <!-- 肺功能科室专属 dataType = 5 -->
          <el-form-item v-if="$route.meta.dataType == '5'">
            <el-button class="section-btn-style1" @click="handlePort">连接串口</el-button>
          </el-form-item>
          <el-form-item label="身高(cm)" v-if="$route.meta.dataType == '5'">
            <input ref="sg" v-model="queryParams.sg" placeholder="请输入身高" clearable class="input-style" style="width: 99px" />
          </el-form-item>
          <el-form-item label="体重(kg)" v-if="$route.meta.dataType == '5'">
            <input ref="tz" v-model="queryParams.tz" placeholder="请输入体重" clearable class="input-style" style="width: 99px" />
          </el-form-item>
          <el-form-item v-if="$route.meta.dataType == '5'">
            <el-button class="section-btn-style1" @click="handleStart">开始检查</el-button>
          </el-form-item>
          <el-form-item style="margin: 0 10px" v-if="headParams.isVIP">
            <el-tag :type="headParams.isVIP == '普通会员' ? 'info' : headParams.isVIP == 'VIP' ? 'danger' : ''"><i class="el-icon-user"></i> <span v-html="headParams.isVIP"></span></el-tag>
            <!-- <el-tag type="danger"><i class="el-icon-user"></i> 普通会员</el-tag>  -->
            <!-- <el-tag ><i class="el-icon-user"></i> 普通会员</el-tag> -->
          </el-form-item>
          <el-form-item style="margin: 0 10px">
            <span class="audit-state green" v-if="headParams.isAudit">已审核</span>
            <span class="audit-state red" v-if="headParams.isAudit === false">未审核</span>
            <!-- <span style="font-size: 20px" :style="{ color: headParams.isAudit ? 'green' : 'red' }">{{ headParams.isAudit ? '已审核' : '未审核' }}</span> -->
          </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5" v-if="$route.meta.dataType != '1' && $route.meta.dataType != '9'">
            <el-button class="section-btn-style2" icon="el-icon-refresh-right" @click="handleReset" :disabled="lockDisable || mainDisabled || (headParams.patientcode && headParams.isAudit)">重置 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '1'">
            <el-button class="section-btn-style2" icon="el-icon-search" @click="handleOutReport">查看外送报告</el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '1' || $route.meta.dataType == '4' || $route.meta.dataType == '6' || $route.meta.dataType == '7' || $route.meta.dataType == '10' || $route.meta.dataType == '11'">
            <el-button class="section-btn-style2" icon="el-icon-s-custom" @click="handleQueue">列队</el-button>
          </el-col>
          <template v-if="$route.meta.dataType == '1'">
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-edit-outline" @click="handleSetting">设置</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-plus" @click="handleGetLis">获取Lis数据</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-circle-close" @click="handleClear">清除数据</el-button>
            </el-col>
          </template>
          <el-col :span="1.5" v-if="$route.meta.dataType != '8' && $route.meta.dataType != '9' && $route.meta.dataType != '10'">
            <el-button class="section-btn-style2" icon="el-icon-suitcase" @click="handleSection">科室 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '6' || $route.meta.dataType == '7'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleSectionAdd" :disabled="lockDisable">科室加项 </el-button>
          </el-col>
          <!-- <el-col :span="1.5" v-if="['6', '300'].includes($route.meta.sectionNo)">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleProfessional">职业性问诊 </el-button>
          </el-col> -->
          <!-- 2025-8-20 健康问卷科室不显示神经内科和内科按钮 -->
          <!-- <el-col :span="1.5" v-if="['6'].includes($route.meta.sectionNo) || $route.meta.dataType == '8'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleNeurology">神经内科 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="['300'].includes($route.meta.sectionNo) || $route.meta.dataType == '8'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleInternalMedicine">内科 </el-button>
          </el-col> -->
          <el-col :span="1.5" v-if="$route.meta.dataType == '8'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleNeurology">神经内科 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '8'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleInternalMedicine">内科 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '12'">
            <el-button class="section-btn-style2" icon="el-icon-folder" @click="handleQuestionnaire" v-hasPermi="['funcdept:section:questionnaire']">问卷
            </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '9'">
            <el-button class="section-btn-style2" icon="el-icon-picture-outline" @click="handleMakeImage(false)">生成图像 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '9'">
            <el-button class="section-btn-style2" icon="el-icon-picture-outline" @click="handleLookImage">查看图像 </el-button>
          </el-col>
          <template v-if="$route.meta.dataType == '7' || $route.meta.dataType == '10'">
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-upload2" @click="handleTransmitPic">传图</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style1" icon="el-icon-picture-outline" @click="handlePic">看图</el-button>
            </el-col>
            <el-col :span="1.5" v-if="$route.meta.dataType != '10'">
              <el-button class="section-btn-style2" icon="el-icon-upload2" @click="handleUploadPic">上传图片</el-button>
            </el-col>
          </template>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '11'">
            <el-button class="section-btn-style2" icon="el-icon-folder" @click="handleRecord">档案 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '11' || $route.meta.dataType == '9'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleCaution">我要提醒 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '9' || $route.meta.dataType == '11'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleRemind">查看提醒 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '6' || $route.meta.dataType == '7' || $route.meta.dataType == '8' || $route.meta.dataType == '9' || $route.meta.dataType == '11'">
            <!-- <el-button class="section-btn-style2" icon="el-icon-tickets" @click="handleQuestionnaire">问卷 </el-button> -->
            <el-popover placement="bottom-start" trigger="hover" width="500" class="question-popover">
              <div class="question-popover" v-if="headParams.patientcode && form.question1 !== ''">
                <el-form ref="form" :model="form" label-position="left" hide-required-asterisk>
                  <el-descriptions title="二、健康状况及家族史" direction="vertical" :column="1" :colon="false">
                    <el-descriptions-item label="1.近一年内，您觉得您的健康状况怎么样？">
                      <el-form-item prop="question1">
                        <el-radio v-model="form.question1" :label="item.value" v-for="item in range" :key="item.value">{{ item.text }}</el-radio>
                      </el-form-item>
                    </el-descriptions-item>
                    <el-descriptions-item label="2.本人手术史">
                      <el-form-item prop="question2">
                        <el-radio v-model="form.question2.answer" :label="item.value" v-for="item in range2" :key="item.value">{{ item.text }}</el-radio>
                        <el-input v-if="form.question2.answer == 1" size="mini" v-model="form.question2.name" placeholder="请输入手术史" style="width: 340px" />
                      </el-form-item>
                    </el-descriptions-item>
                    <el-descriptions-item label="3.输血史">
                      <el-form-item prop="question3">
                        <el-radio v-model="form.question3" :label="item.value" v-for="item in range2" :key="item.value">{{ item.text }}</el-radio>
                      </el-form-item>
                    </el-descriptions-item>
                    <el-descriptions-item label="4.药物过敏史">
                      <el-form-item prop="question4">
                        <el-input size="mini" v-model="form.question4" placeholder="如有过敏药,请输入药名" style="width: 340px" />
                      </el-form-item>
                    </el-descriptions-item>
                    <el-descriptions-item label="5.本人疾病史">
                      <el-form-item prop="question5">
                        <el-radio v-model="form.question5" :label="item.value" v-for="item in range2" :key="item.value">{{ item.text }}</el-radio>
                      </el-form-item>
                      <template v-if="form.question5 == 1">
                        <div style="font-size: 20px">疾病名称（释义）</div>
                        <div class="border-bottom" v-for="(item, index) in form.question6" :key="index">
                          <div class="flex align-items-center" style="margin-top: 8px">
                            <el-checkbox-group v-model="item.main">
                              <el-checkbox :label="item.value" v-for="item in [{ value: '1', text: item.title }]" :key="item.value"> {{ item.text }}</el-checkbox>
                            </el-checkbox-group>
                            <div style="flex: 3; display: flex; align-items: center; font-size: 22px">
                              <el-input size="mini" v-show="item.main[0] == 1" style="padding: 4px; width: 80px; text-align: right; border-radius: 5px" type="number" v-model="item.year" />
                              <div v-show="item.main[0] == 1">年</div>
                            </div>
                          </div>
                          <el-checkbox-group v-model="item.list" v-show="item.main[0] == 1">
                            <el-checkbox :label="item.value" v-for="item in range3" :key="item.value"> {{ item.text }}</el-checkbox>
                          </el-checkbox-group>
                        </div>
                      </template>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions title="三、生活方式" direction="vertical" :column="1" :colon="false">
                    <el-descriptions-item label="1.您吸烟吗？">
                      <el-form-item>
                        <el-radio v-model="form.question7.answer" :label="item.value" v-for="item in range4" :key="item.value">{{ item.text }}</el-radio>
                      </el-form-item>
                      <div class="topic-item border-bottom" v-show="form.question7.answer === '0' || form.question7.answer == 3">
                        <div class="topic-title flex align-items-center" style="flex-wrap: wrap; font-size: 22px">
                          在/戒烟（6个月以上）前，您已吸烟
                          <el-input size="mini" style="padding: 4px; width: 80px; text-align: right; border-radius: 5px" type="number" v-model="form.question7.day" />
                          <div>年，</div>
                          每日
                          <input size="mini" style="padding: 4px; width: 80px; text-align: right; border-radius: 5px" type="number" v-model="form.question7.sum" />
                          支
                        </div>
                      </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="2.您过去一年饮酒？">
                      <el-form-item>
                        <el-radio v-model="form.question8.answer" :label="item.value" v-for="item in range5" :key="item.value">{{ item.text }}</el-radio>
                      </el-form-item>
                      <template v-if="form.question8.answer > 1">
                        <div style="font-size: 22px">您饮酒主要种类？</div>
                        <div class="type1">
                          <el-radio v-model="form.question8.type" :label="item.value" v-for="item in range6" :key="item.value">{{ item.text }}</el-radio>
                          <el-input v-if="form.question8.type == 3" size="mini" style="padding: 4px; width: 340px; border-radius: 5px" type="text" v-model="form.question8.other" placeholder="请输入酒类型" />
                        </div>
                      </template>
                    </el-descriptions-item>
                  </el-descriptions>
                </el-form>
              </div>
              <div v-else style="text-align: center; height: 50px; line-height: 50px">暂未答卷</div>
              <!-- <el-button slot="reference" class="section-btn-style2" icon="el-icon-tickets" @click="handleQuestionnaire" @mouseenter.native="handleQueryQuestion">问卷</el-button> -->
            </el-popover>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '5' || $route.meta.dataType == '11' || $route.meta.dataType == '9'">
            <el-button class="section-btn-style2" icon="el-icon-tickets" @click="handleHealth" v-if="headParams.diagnosticReportH">健康报告</el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '5' || $route.meta.dataType == '9' || $route.meta.dataType == '11'">
            <el-button class="section-btn-style2" icon="el-icon-tickets" @click="handleOccupation" v-if="headParams.diagnosticReportD">职业报告 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '8'">
            <el-button class="section-btn-style2" icon="el-icon-tickets" @click="handleOccupation" :disabled="!headParams.diagnosticReportD">职业报告 </el-button>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '1' || $route.meta.dataType == '6' || $route.meta.dataType == '7' || $route.meta.dataType == '10'">
            <el-button class="section-btn-style2" icon="el-icon-bell" @click="handleCaution">提醒 </el-button>
          </el-col>
          <el-col :span="1.5" style="padding-top: 5px" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '9' || $route.meta.dataType == '11'">
            <el-checkbox v-model="isRegenerate">重新生成</el-checkbox>
          </el-col>
          <el-col :span="1.5" v-if="$route.meta.dataType == '1' || $route.meta.dataType == '6' || $route.meta.dataType == '7' || $route.meta.dataType == '10'">
            <el-dropdown trigger="click">
              <el-button class="section-btn-style2"> 辅助功能<i class="el-icon-arrow-down el-icon--right"></i> </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleAssist(1)" v-if="$route.meta.dataType != '1' && $route.meta.dataType != '10'">快捷开药</el-dropdown-item>
                <el-dropdown-item @click.native="handleSection" v-if="$route.meta.dataType == '10'">科室</el-dropdown-item>
                <el-dropdown-item @click.native="handleAssist(2)" v-if="$route.meta.dataType != '1'">问卷记录</el-dropdown-item>
                <el-dropdown-item @click.native="handleAssist(3)">查看提醒</el-dropdown-item>
                <el-dropdown-item @click.native="handleAssist(4)">健康报告</el-dropdown-item>
                <el-dropdown-item @click.native="handleAssist(5)">职业报告</el-dropdown-item>
                <el-dropdown-item @click.native="handleAssist(6)" v-if="$route.meta.dataType != '1' && $route.meta.dataType != '10'">科室咨询</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 第二排 -->
    <div class="flex" style="padding: 0 20px">
      <!-- 肺功能专属 -->
      <el-form ref="queryForm" size="small" :inline="true" @submit.native.prevent v-if="$route.meta.dataType == '5'">
        <el-form-item label="科室" prop="section" style="margin-bottom: 0">
          <el-select v-model="queryParams.section" placeholder="请选择" clearable>
            <el-option v-for="item in sectionOptions" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-upload2" @click="handleUpload">上传数据</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-upload2" @click="handleUploadAll">传图</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-upload2" @click="handleUploadPic">手动传图</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button class="section-btn-style2" icon="el-icon-picture-outline" @click="handlePic">看图</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-if="$route.meta.dataType == '3' || $route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '9' || $route.meta.dataType == '11'">
        <el-form-item label="检查状态" v-if="$route.meta.dataType == '9'" style="margin-bottom: 4px">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 100px" @change="handleSearch">
            <el-option label="未检查" :value="0"></el-option>
            <el-option label="已检查" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择日期" style="margin-bottom: 4px" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '9'">
          <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleSearch"></el-date-picker>
        </el-form-item>
        <el-form-item style="margin-bottom: 4px" v-if="$route.meta.dataType == '3' || $route.meta.dataType == '5' || $route.meta.dataType == '8' || $route.meta.dataType == '9'">
          <el-button class="section-btn-style1" @click="handleSearch">搜索</el-button>
        </el-form-item>
        <el-form-item style="margin-bottom: 4px" v-if="$route.meta.dataType == '4' || $route.meta.dataType == '5' || $route.meta.dataType == '11'">
          <el-button class="section-btn-style1" @click="handleSummary" :disabled="lockDisable || mainDisabled || (headParams.patientcode && headParams.isAudit)">小结</el-button>
        </el-form-item>
        <el-form-item style="margin-bottom: 4px">
          <el-button class="section-btn-style1" @click="handleAudit" :disabled="lockDisable || mainDisabled || headParams.isAudit">审核</el-button>
        </el-form-item>
        <el-form-item style="margin-bottom: 4px">
          <el-button class="section-btn-style1" @click="handleReAudit" :disabled="lockDisable || !mainDisabled || !headParams.isAudit || !headParams.patientcode">反审核</el-button>
        </el-form-item>
        <el-form-item style="margin-bottom: 4px" v-if="$route.meta.dataType == '9'">
          <el-button class="section-btn-style1" @click="handleAudiometryUploadPopup">上传</el-button>
          <!-- <el-button class="section-btn-style1" @click="handleUploadAudiometry">上传电测听文件</el-button> -->
        </el-form-item>
      </el-form>
    </div>

    <!-- 队列 -->
    <queue ref="queue"></queue>
    <!-- 设置 -->
    <setting ref="setting"></setting>
    <!-- 查看提醒弹窗 -->
    <remind ref="remind"></remind>
    <!-- 传图 -->
    <transmit ref="transmit"></transmit>
    <!-- 电测听上传 -->
    <transmitAudiometry ref="transmitAudiometry"></transmitAudiometry>
    <!-- 上传图片-->
    <upload ref="upload"></upload>
    <!-- 看图 -->
    <check-pic ref="checkPic"></check-pic>
    <el-image-viewer v-if="showPic" ref="previewImg" class="section-header-viewer" :url-list="viewList" :on-close="closeviewer"></el-image-viewer>

    <!-- 科室 -->
    <sections ref="section"></sections>
    <!-- 科室加项 -->
    <section-add-item ref="sectionAddItem"></section-add-item>
    <!-- 查看档案 -->
    <checkRecord ref="checkRecord"></checkRecord>
    <!-- 问卷 -->
    <questionnaire ref="questionnaire"></questionnaire>
    <!-- 提醒 -->
    <caution ref="caution"></caution>
    <!-- 快捷开药 -->
    <prescribe ref="prescribe"></prescribe>
    <!-- 查看问卷历史 -->
    <checkQuentionnaire ref="checkQuentionnaire"></checkQuentionnaire>
    <!-- 科室咨询 -->
    <sectionCounsel ref="sectionCounsel"></sectionCounsel>
    <!-- 查看外送报告 -->
    <out-report ref="outReport"></out-report>
  </div>
</template>

<script>
import queue from './dialog/queue.vue'
import setting from './dialog/setting.vue'
import remind from './dialog/remind.vue'
import sections from './dialog/section.vue'
import sectionAddItem from './dialog/section_add_item.vue'
import checkRecord from './dialog/check_record.vue'
import questionnaire from './dialog/questionnaire.vue'
import caution from './dialog/caution.vue'
import transmit from './dialog/transmit.vue'
import transmitAudiometry from './dialog/transmitAudiometry.vue'
import upload from './dialog/upload.vue'
import checkPic from './dialog/check_pic.vue'
import prescribe from './dialog/prescribe.vue'
import checkQuentionnaire from './dialog/check_quentionnaire.vue'
import sectionCounsel from './dialog/section_counsel.vue'
import outReport from './dialog/out_report.vue'
import elImageViewer from 'element-ui/packages/image/src/image-viewer'

import { handleClearData, getViewImg, getByCode, upLoadFilePic, uploadData } from '@/api/funcdept/section_list/dialog.js'
import axios from 'axios'

export default {
  components: {
    queue,
    setting,
    remind,
    sections,
    sectionAddItem,
    questionnaire,
    checkRecord,
    caution,
    transmit,
    transmitAudiometry,
    upload,
    checkPic,
    prescribe,
    checkQuentionnaire,
    sectionCounsel,
    elImageViewer,
    outReport,
  },
  props: ['headParams', 'mainDisabled', 'lockDisable', 'patientData', 'sectionOptions'],
  data() {
    return {
      value: '',
      value1: '',
      value2: '',
      // 科室选择
      // sectionOptions: [
      //   { value: '选项1', label: '肺功能室1' },
      //   { value: '选项2', label: '肺功能室2' },
      //   { value: '选项3', label: '肺功能室3' },
      //   { value: '选项4', label: '肺功能室4' },
      //   { value: '选项5', label: '肺功能室5(测试)' },
      // ],
      ///检查状态
      sectionOptions2: [
        { value: '选项1', label: '已检查' },
        { value: '选项2', label: '未检查' },
      ],
      // 查询参数
      queryParams: {
        status: undefined,
        date: undefined,
      },
      // 是否重新生成
      isRegenerate: true,
      // 心电图科室直接展示图片
      viewList: [],
      // 展示心电图大图
      showPic: false,
      // 问卷内容
      form: {
        question1: '',
        question2: {
          answer: '',
          name: '',
        },
        question3: '',
        question4: '',
        question5: '',
        question6: [
          {
            title: '糖尿病',
            main: [],
            year: '',
            list: [],
          },
          {
            title: '高血压',
            main: [],
            year: '',
            list: [],
          },
          {
            title: '血脂异常',
            main: [],
            year: '',
            list: [],
          },
          {
            title: '心脏病',
            main: [],
            year: '',
            list: [],
          },
          {
            title: '脑血管病',
            main: [],
            year: '',
            list: [],
          },
          {
            title: '恶性肿瘤',
            main: [],
            year: '',
            list: [],
          },
        ],
        question7: {
          answer: '',
          day: '',
          sum: '',
        },
        question8: {
          answer: '',
          type: '',
          other: '',
        },
      },

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
      // 展示答卷
      showQuestion: false,
    }
  },
  watch: {
    headParams: {
      handler(newVal) {
        this.$emit('changeHeadData', newVal)
      },
      deep: true,
    },
  },
  created() {
    let date = new Date()
    //获取当前时间的年份转为字符串
    let year = date.getFullYear().toString() //'2019'
    //获取月份，由于月份从0开始，此处要加1，判断是否小于10，如果是在字符串前面拼接'0'
    let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1).toString() : (date.getMonth() + 1).toString() //'04'
    //获取天，判断是否小于10，如果是在字符串前面拼接'0'
    let da = date.getDate() < 10 ? '0' + date.getDate().toString() : date.getDate().toString() //'1
    let day = year + '-' + month + '-' + da
    this.queryParams.date = [day, day]
  },
  methods: {
    // #region 电测听专属
    ///这是查看图像
    handleLookImage() {
      this.bus.$emit('handleMakeImage', 1)
    },
    ///这是生成图像 auto:审核自动生成图像
    handleMakeImage(auto) {
      this.bus.$emit('handleMakeImage', 0, auto)
    },
    // #endregion

    // 读卡
    handleRead() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.showQuestion = false
      const name = 'rankDb' + this.$route.meta.ksID
      const patientcode = this.headParams.patientcode
      this.bus.$emit(name, patientcode, this.$route.meta.ksID)
      if (this.$route.meta.dataType == '12') {
        this.$refs.questionnaire.handleShow(this.headParams.patientcode)
      }
    },
    //
    handlePort() {
      this.$emit('handlePort')
    },
    // 搜索
    handleSearch(notRefresh) {
      // this.bus.$emit('searchTableData' + this.$route.meta.dataType, this.queryParams.date, this.queryParams.status)
      this.$emit('searchTableData', this.queryParams.date, this.queryParams.status, notRefresh)
    },
    // 小结
    handleSummary() {
      if (this.$route.meta.dataType == 5) {
        this.$emit('handleSummaryPF')
      } else {
        this.$emit('handleSummary')
      }
    },
    // 审核
    handleAudit() {
      if (this.$route.meta.dataType == '11') {
        this.bus.$emit('conclusionsHandle', true)
        // this.bus.$emit('handleAuditSljc')
      } else {
        this.$emit('handleAudit')
      }
    },
    // 反审核
    handleReAudit() {
      this.$emit('handleReAudit')
    },
    // 电测听上传弹窗
    handleAudiometryUploadPopup() {
      this.$refs.transmitAudiometry.showDialog()
    },
    // 上传本地电测听文件
    handleUploadAudiometry() {
      const loading = this.$loading({
        lock: true,
        text: '正在读取...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      axios({
        method: 'get',
        url: 'http://localhost:8090/uploadEl/upload',
        timeout: 600000,
      })
        .then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess('上传成功')
            loading.close()
          } else {
            this.$modal.msgError(res.msg)
            loading.close()
          }
        })
        .catch((err) => {
          console.error(err)
          loading.close()
        })
    },
    // 查看外送报告
    handleOutReport() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.$refs.outReport.showDialog(this.headParams.patientcode)
    },
    // 列队
    handleQueue() {
      this.$refs.queue.showDialog()
    },
    // 设置
    handleSetting() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      // let alertMsg = ''
      // if (this.patientData.peispatient.jktjzt == 1) {
      //   alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.doctorfinalNameR == null ? '' : this.patientData.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
      // } else if (this.patientData.peispatient.zytjzt == 1) {
      //   alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.patientnameencoded == null ? '' : this.patientData.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
      // } else if (this.patientData.peispatient.ffinallocked == 1) {
      //   alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.idDoctorapply == null ? '' : this.patientData.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
      // } else if (this.patientData.peispatient.idGuidenurse == 1) {
      //   alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.parsedassignedsuiteandfi == null ? '' : this.patientData.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
      // } else if (this.patientData.sectionResultMain && this.patientData.sectionResultMain.isAudit == 1) {
      //   alertMsg = '该体检号已审核，不能修改！'
      // }
      // if (alertMsg) {
      //   this.$alert(alertMsg, '提示', {
      //     confirmButtonText: '确定',
      //   })
      //   return
      // }
      this.$refs.setting.showDialog(this.headParams.patientcode)
    },
    // 获取Lis数据
    handleGetLis() {
      this.$emit('handleLisData')
    },
    // 清除数据
    handleClear() {
      // if (!this.headParams.patientcode) {
      //   this.$modal.msgWarning('请输入体检号后再试')
      //   return
      // }
      let alertMsg = ''
      if (this.patientData.peispatient.jktjzt == 1) {
        alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.doctorfinalNameR == null ? '' : this.patientData.peispatient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。'
      } else if (this.patientData.peispatient.zytjzt == 1) {
        alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.patientnameencoded == null ? '' : this.patientData.peispatient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。'
      } else if (this.patientData.peispatient.ffinallocked == 1) {
        alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.idDoctorapply == null ? '' : this.patientData.peispatient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。'
      } else if (this.patientData.peispatient.idGuidenurse == 1) {
        alertMsg = '本体检者检查结果已被' + (this.patientData.peispatient.parsedassignedsuiteandfi == null ? '' : this.patientData.peispatient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。'
      }
      // else if (this.patientData.sectionResultMain && this.patientData.sectionResultMain.isAudit == 1) {
      //   alertMsg = '该体检号已审核，不能修改！'
      // }
      if (alertMsg) {
        this.$alert(alertMsg, '提示', {
          confirmButtonText: '确定',
        })
        return
      }
      this.$confirm('确定要清除体检号<text style="color:red;">' + this.headParams.patientcode + '</text>在本科室的所有检查数据吗？', '提示', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          handleClearData({
            patientCode: this.headParams.patientcode,
          }).then(() => {
            const name = 'rankDb' + this.$route.meta.ksID
            this.bus.$emit(name, this.headParams.patientcode, this.$route.meta.ksID)
            this.$message({
              type: 'success',
              message: '清除成功!',
            })
          })
        })
        .catch(() => { })
    },
    // 重置
    handleReset() {
      this.$emit('clearData')
    },
    // 科室
    handleSection() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.$refs.section.showDialog(this.headParams.patientcode, this.headParams.patientname)
    },
    // 科室加项
    handleSectionAdd() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.$refs.sectionAddItem.showDialog(this.headParams)
    },
    // 职业问诊
    handleProfessional() {
      this.$router.push({ name: 'section124', params: { patientcode: this.headParams.patientcode } })
    },
    // 神经内科
    handleNeurology() {
      this.$router.push({ name: 'section125', params: { patientcode: this.headParams.patientcode } })
    },
    // 内科
    handleInternalMedicine() {
      this.$router.push({ name: 'section6', params: { patientcode: this.headParams.patientcode } })
    },
    // 问卷
    handleQuestionnaire() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.$refs.questionnaire.handleShow(this.headParams.patientcode)
    },
    // 请求问卷
    handleQueryQuestion() {
      if (this.headParams.patientcode && !this.showQuestion) {
        this.form = {
          question1: '',
          question2: {
            answer: '',
            name: '',
          },
          question3: '',
          question4: '',
          question5: '',
          question6: [
            {
              title: '糖尿病',
              main: [],
              year: '',
              list: [],
            },
            {
              title: '高血压',
              main: [],
              year: '',
              list: [],
            },
            {
              title: '血脂异常',
              main: [],
              year: '',
              list: [],
            },
            {
              title: '心脏病',
              main: [],
              year: '',
              list: [],
            },
            {
              title: '脑血管病',
              main: [],
              year: '',
              list: [],
            },
            {
              title: '恶性肿瘤',
              main: [],
              year: '',
              list: [],
            },
          ],
          question7: {
            answer: '',
            day: '',
            sum: '',
          },
          question8: {
            answer: '',
            type: '',
            other: '',
          },
        }
        getByCode({
          patientcode: this.headParams.patientcode,
        })
          .then((res) => {
            this.showQuestion = true
            if (res.code !== 200 || !res.data) {
              this.$message({
                message: '本次体检用户已放弃填写问卷',
                type: 'warning',
              })
            } else {
              this.form.question1 = res.data.question1
              this.form.question2 = {
                answer: res.data.question2.split('-')[0],
                name: res.data.question2.split('-')[1],
              }
              this.form.question3 = res.data.question3
              this.form.question4 = res.data.question4
              this.form.question5 = res.data.question5
              if (this.form.question5 == 1) {
                res.data.question6.split(',').forEach((el) => {
                  let title = el.split('-')[0]
                  let year = el.split('-')[1]
                  let list = el.split('-')[2].split('#')
                  this.form.question6.forEach((val) => {
                    if (val.title == title) {
                      val.main = ['1']
                      val.year = year
                      val.list = list
                    }
                  })
                })
              }
              this.form.question7 = {
                answer: res.data.question7.split('-')[0],
                day: res.data.question7.split('-')[1],
                sum: res.data.question7.split('-')[2],
              }
              this.form.question8 = {
                answer: res.data.question8.split('-')[0],
                type: res.data.question8.split('-')[1],
                other: res.data.question8.split('-')[2],
              }
            }
          })
          .catch((err) => {
            console.error(err)
            this.showQuestion = true
          })
      }
    },
    // 提醒
    handleCaution() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      this.$refs.caution.showDialog(this.headParams.patientcode)
    },
    // 传图
    handleTransmitPic() {
      this.$refs.transmit.showDialog()
    },
    // 档案
    handleRecord() {
      if (this.headParams.patientcode) {
        this.$refs.checkRecord.showDialog(this.headParams.patientcode)
      } else {
        this.$modal.msgWarning('请输入体检号后再试')
      }
    },
    // 查看提醒
    handleRemind() {
      this.$refs.remind.showDialog(this.headParams.patientcode)
    },
    // 健康报告
    handleHealth() {
      let routeUrl = this.$router.resolve({
        name: 'SectionReport',
        query: { dh: 0, ksID: this.$route.meta.ksID, dataType: this.$route.meta.dataType, patientcode: this.headParams.patientcode, title: this.$route.meta.title },
      })
      window.open(routeUrl.href)
    },
    // 职业报告
    handleOccupation() {
      let routeUrl = this.$router.resolve({
        name: 'SectionReport',
        query: { dh: 0, ksID: this.$route.meta.ksID, dataType: this.$route.meta.dataType, patientcode: this.headParams.patientcode, title: this.$route.meta.title },
      })
      window.open(routeUrl.href)
    },
    // #endregion
    // 辅助功能
    handleAssist(index) {
      if (!this.headParams.patientcode && index != 3) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      switch (index) {
        case 1:
          this.$refs.prescribe.showDialog(this.headParams.patientcode)
          break
        case 2:
          this.$refs.checkQuentionnaire.showDialog(this.headParams.patientcode)
          break
        case 3:
          this.handleRemind()
          break
        case 4:
          this.handleHealth()
          break
        case 5:
          this.handleOccupation()
          break
        case 6:
          this.$refs.sectionCounsel.showDialog(this.headParams.patientcode)
          break
      }
    },

    // #region 肺功能专属
    // 上传
    handleUpload() {
      if (!this.queryParams.section) {
        this.$alert('请选择科室', '提示', {
          confirmButtonText: '确定',
        })
        return
      }
      const loading = this.$loading({
        lock: true,
        text: '上传中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.1)',
      })
      uploadData({
        id: this.queryParams.section,
      })
        .then((res) => {
          this.$modal.msgSuccess(res.data)
          loading.close()
        })
        .catch((err) => {
          console.error(err)
          loading.close()
        })
    },
    // 上传全部
    handleUploadAll() {
      if (!this.queryParams.section) {
        this.$alert('请选择科室', '提示', {
          confirmButtonText: '确定',
        })
        return
      }
      const loading = this.$loading({
        lock: true,
        text: '上传中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.1)',
      })
      upLoadFilePic({
        id: this.queryParams.section,
      })
        .then((res) => {
          this.$modal.msgSuccess(res.data)
          loading.close()
        })
        .catch((err) => {
          console.error(err)
          loading.close()
        })
    },
    // 上传图片
    handleUploadPic() {
      this.$refs.upload.showDialog()
    },
    // 看图
    handlePic() {
      if (!this.headParams.patientcode) {
        this.$modal.msgWarning('请输入体检号后再试')
        return
      }
      if (this.$route.meta.ksID == 9) {
        this.queryParams.ksID = this.$route.meta.ksID
        getViewImg({
          patientCode: this.headParams.patientcode,
          ksID: this.$route.meta.ksID,
        }).then((res) => {
          if (res.data.records.length) {
            let viewList = []
            res.data.records.forEach((el) => {
              viewList.push(this.$getCookie('imgPath') + el.filePath)
            })
            this.viewList = viewList
            if (this.viewList.length == 1) {
              // this.$refs.previewImg.showViewer = true
              this.$refs.checkPic.showDialog(this.headParams.patientcode, this.viewList[0], res.data.records[0].id)
              // this.showPic = true
            } else {
              this.$refs.checkPic.showDialog(this.headParams.patientcode, false)
            }
          } else {
            this.$alert('未查询到图片', '提示')
          }
        })
      } else {
        this.$refs.checkPic.showDialog(this.headParams.patientcode)
      }
    },
    // 关闭心电图看图
    closeviewer() {
      this.showPic = false
    },
    // 保存上传
    handleSave() {
      this.openUpload = false
    },
    // 开始检查
    handleStart() {
      this.$emit('startCheck', {
        patientcode: this.headParams.patientcode,
        section: this.queryParams.section,
        age: this.headParams.age,
        sex: this.headParams.idSex,
        sg: this.queryParams.sg,
        tz: this.queryParams.tz,
      });
    },
    // async handleStart() {
    //   // 校验输入参数
    //   if (!this.headParams.patientcode) {
    //     this.$alert('请输入体检号', '提示', { confirmButtonText: '确定' });
    //     return;
    //   }
    //   if (!this.queryParams.section) {
    //     this.$alert('请选择科室', '提示', { confirmButtonText: '确定' });
    //     return;
    //   }
    //   if (!this.queryParams.sg) {
    //     this.$alert('请输入身高', '提示', { confirmButtonText: '确定' });
    //     return;
    //   }
    //   if (!this.queryParams.tz) {
    //     this.$alert('请输入体重', '提示', { confirmButtonText: '确定' });
    //     return;
    //   }

    //   try {
    //     // 确保串口已连接
    //     if (!this.port || !this.port.readable) {
    //       await this.handlePort();
    //       if (!this.port) {
    //         this.$modal.msgError('串口未连接，请先连接串口');
    //         return;
    //       }
    //     }

    //     // 构造发送数据帧
    //     const patientCode = this.formatPatientCode(this.headParams.patientcode); // 格式化体检号
    //     const age = String(this.queryParams.age).padStart(4, '0'); // 年龄
    //     const sex = this.queryParams.idSex === '男' ? '1' : '2'; // 性别
    //     const height = String(this.queryParams.sg).padStart(4, '0'); // 身高
    //     const weight = String(this.queryParams.tz).padStart(4, '0'); // 体重

    //     const dataToSend = `${patientCode}${age}${sex}${height}${weight}`;

    //     // 打开写入流
    //     const writer = this.port.writable.getWriter();
    //     const encoder = new TextEncoder(); // 将字符串编码为 Uint8Array
    //     const dataArray = encoder.encode(dataToSend);

    //     // 发送数据
    //     await writer.write(dataArray);
    //     writer.releaseLock();
    //     this.$modal.msgSuccess('参数发送成功');

    //     // 接收设备响应
    //     await this.receiveDeviceData();
    //   } catch (error) {
    //     console.error('串口通信失败:', error);
    //     this.$modal.msgError('串口通信失败');
    //   }
    // },
    // // 格式化体检号，不足15位的部分用 '2F' 填充
    // formatPatientCode(patientCode) {
    //   let formattedCode = patientCode.padEnd(15, '2F');
    //   formattedCode = formattedCode.slice(0, 15); // 确保长度为15
    //   return formattedCode;
    // },

    // // 接收设备返回的数据
    // async receiveDeviceData() {
    //   try {
    //     const reader = this.port.readable.getReader();
    //     let receivedData = '';

    //     while (true) {
    //       const { value, done } = await reader.read();
    //       if (done) {
    //         reader.releaseLock();
    //         break;
    //       }

    //       const decoder = new TextDecoder(); // 解码 Uint8Array 为字符串
    //       receivedData += decoder.decode(value);

    //       // 检查是否接收到完整数据帧
    //       if (receivedData.startsWith('AA') && receivedData.length >= 148) {
    //         reader.releaseLock();

    //         // 解析数据
    //         this.parseDeviceResponse(receivedData);
    //         break;
    //       }
    //     }
    //   } catch (error) {
    //     console.error('接收设备数据失败:', error);
    //     this.$modal.msgError('接收设备数据失败');
    //   }
    // },

    // // 解析设备返回的数据
    // parseDeviceResponse(data) {
    //   try {
    //     // 去掉起始标志 AA
    //     const payload = data.slice(2);

    //     // 提取测量值
    //     const fvc = parseInt(payload.slice(27, 31), 16); // FVC
    //     const fev1 = parseInt(payload.slice(41, 45), 16); // FEV1
    //     const mvv = parseFloat(payload.slice(3, 9)) / 100; // MVV
    //     const diagnosis = parseInt(payload.slice(223, 225), 16); // 诊断结果

    //     // 显示解析结果

    //     this.$modal.msgSuccess('数据解析成功');
    //   } catch (error) {
    //     console.error('数据解析失败:', error);
    //     this.$modal.msgError('数据解析失败');
    //   }
    // },
    // #endregion
  },
}
</script>

<style lang="scss">
.section-list.section-header {
  padding: 0;
  border-bottom: #f6f7fb 4px solid;

  .section-list-top {

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 5px;
    }
  }

  .flex {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  h3 {
    margin: 5px 0 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  // .el-button {
  //   &:hover {
  //     color: #d41318;
  //     background-color: rgba(255, 0, 0, 0.1);
  //     border-color: #d41318;
  //     opacity: 0.8;
  //   }
  // }
  // .el-button--primary {
  //   color: #d41318;
  //   background-color: #fff;
  //   border-color: #d41318;
  //   &:hover {
  //     color: #d41318;
  //     background-color: rgba(255, 0, 0, 0.1);
  //     border-color: #d41318;
  //   }
  //   &.is-disabled {
  //     color: #ea898c;
  //     background-color: #fff;
  //     border-color: #ea898c;
  //     &:hover {
  //       color: #ea898c;
  //       background-color: #fff;
  //       border-color: #d41318;
  //       opacity: 0.8;
  //     }
  //   }
  // }
  .section-list-top {
    .audit-state {
      padding: 2px 8px;
      font-size: 20px;
      color: #fff;
      font-weight: 600;
      border-radius: 4px;

      &.green {
        background-color: green;
      }

      &.red {
        background-color: #d41318;
      }
    }
  }
}
</style>
<style scoped>
.section-header-viewer /deep/ .el-image-viewer__img {
  width: 60%;
  max-height: none !important;
  max-width: none !important;
  margin-bottom: -50%;
}

.question-popover {
  height: 600px;
  overflow-y: auto;
}
</style>
<style lang="scss">
.question-popover {
  .el-descriptions-item__label {
    margin: 0;
  }

  .el-descriptions__title {
    font-size: 24px !important;
  }

  .el-form-item__label,
  .el-descriptions-item__label,
  .el-input__inner {
    font-weight: 400;
    font-size: 22px !important;
    color: #333333;
  }

  .el-radio__label,
  .el-checkbox__label {
    font-size: 22px !important;
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

.question-popover .el-radio__input.is-checked .el-radio__inner {
  border-color: #000 !important;
  background: #000 !important;
}

.question-popover.el-radio.is-checked .el-radio__label,
.question-popover .el-radio__input.is-checked+.el-radio__label {
  color: #000 !important;
  font-weight: 600 !important;
}
</style>
