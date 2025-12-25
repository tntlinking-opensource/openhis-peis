<!-- 问诊检查 开发人： 麦沃德科技 矢北/半夏 -->
<template>
  <div class="app-container professional-greetings" style="height: 100%; min-height: auto; padding: 0 20px 20px; overflow-y: auto">
    <el-form :model="formData" ref="form" size="mini" :inline="true" label-width="110px" :rules="rules">
      <!-- 基本资料 -->
      <div class="charge-item">
        <h3>【基本资料】</h3>
        <div>
          <el-form-item label="姓名" prop="formdata.patientname">
            <el-input v-model="formData.formdata.patientname" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="性别" prop="formdata.idSex">
            <template>
              <el-input value="男" style="width: 180px" readonly v-if="formData.formdata.idSex == 0" />
              <el-input value="女" style="width: 180px" readonly v-else-if="formData.formdata.idSex == 1" />
              <el-input value="" style="width: 180px" readonly v-else />
            </template>
          </el-form-item>
          <el-form-item label="民族" prop="formdata.nation">
            <el-input v-model="formData.formdata.nation" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="身份证号" prop="formdata.idcardno">
            <el-input v-model="formData.formdata.idcardno" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="邮政编码" prop="formdata.yzbm">
            <el-input v-model="formData.formdata.yzbm" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="联系电话" prop="formdata.phone">
            <el-input v-model="formData.formdata.phone" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="职业危害因素" prop="formdata.jhysName">
            <el-input v-model="formData.formdata.jhys" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="总工龄(月)" prop="formdata.zgl">
            <el-input-number v-model="formData.formdata.zgl" controls-position="right" style="width: 180px" :min="0" @change="zglChange" v-if="!mainDisabled"> </el-input-number>
            <el-input v-model="formData.formdata.zgl" style="width: 180px" readonly v-else />
          </el-form-item>
          <el-form-item label="接害工龄(月)" prop="formdata.jhgl">
            <el-input-number v-model="formData.formdata.jhgl" controls-position="right" style="width: 180px" :min="0" :max="formData.formdata.zgl" v-if="!mainDisabled"> </el-input-number>
            <el-input v-model="formData.formdata.jhgl" style="width: 180px" readonly v-else />
          </el-form-item>
          <el-form-item label="籍贯" prop="formdata.resarea">
            <el-input v-model="formData.formdata.resarea" style="width: 180px" readonly />
          </el-form-item>
          <el-form-item label="家庭住址" prop="formdata.address">
            <el-input v-model="formData.formdata.address" style="width: 480px" readonly />
          </el-form-item>
        </div>
      </div>
      <!-- 职业史 -->
      <div class="charge-item">
        <h3>【职业史】</h3>
        <!-- 2024-09-23 删除按钮权限 -->
        <!-- <el-row style="margin-top: 16px" :gutter="10" class="mb8" v-if="!mainDisabled"> -->
        <el-row style="margin-top: 16px" :gutter="10" class="mb8">
          <!-- <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-plus" @click="occupationAdd" v-hasPermi="['funcdept:section:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-delete" :disabled="multipleOccupation" @click="occupationRemove" v-hasPermi="['funcdept:section:remove']">删除</el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-plus" @click="occupationAdd" >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-delete" @click="occupationRemove" >删除</el-button>
          </el-col>
        </el-row>
        <!-- 可编辑 -->
        <el-table border v-loading="zysLoading" :data="formData.griddata" stripe @selection-change="handleOccupationChange" v-if="!mainDisabled">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="起日期" prop="startDate" align="center" width="125">
            <template slot-scope="scope">
              <el-form-item :prop="'griddata.' + scope.$index + '.startDate'" :rules="rules.startDate">
                <el-date-picker class="hide-clear" size="mini" v-model="scope.row.startDate" clearable style="width: 100%" value-format="yyyy-MM-dd" type="date" key="date" placeholder="请选择日期"> </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="止日期" prop="stopDate" align="center" width="125">
            <template slot-scope="scope">
              <el-form-item :prop="'griddata.' + scope.$index + '.stopDate'" :rules="rules.stopDate">
                <el-date-picker class="hide-clear" size="mini" v-model="scope.row.stopDate" clearable style="width: 100%" value-format="yyyy-MM-dd" type="date" key="date" placeholder="请选择日期"> </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="工作单位" prop="dept" align="center" min-width="110">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.dept" placeholder="请输入"></el-input>
            </template>
            <!-- <template slot-scope="scope">
              <el-form-item :prop="'griddata.' + scope.$index + '.dept'">
                <input-select class="hide-clear" :selectData="workData" :initialValue="scope.row.dept" selectSize="mini" selectWidth="100%" :current-index="scope.$index" @change="workChange"></input-select>
              </el-form-item>
            </template> -->
          </el-table-column>
          <el-table-column label="车间" prop="branch" align="center" min-width="110">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.branch" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="工种" prop="typeOfWork" align="center" min-width="110">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.typeOfWork" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="危害因素 " prop="harmName" align="center" min-width="110">
            <template slot-scope="scope">
              <el-form-item :prop="'griddata.' + scope.$index + '.harmName'" :rules="rules.harmName">
                <el-tooltip effect="dark" :content="scope.row.harmName" placement="top" :disabled="!scope.row.harmName">
                  <search-select class="show-clear" :selectData="hazardData" :initialValue="scope.row.jhys" selectSize="mini" selectWidth="100%" :current-index="scope.$index" :multiple="true" @change="hazardChange"></search-select>
                </el-tooltip>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="有无防护措施" prop="occupationDefend" align="center" min-width="110">
            <template slot-scope="scope">
              <el-select class="show-clear" v-model="scope.row.occupationDefend" clearable size="mini" placeholder="请选择">
                <el-option label="有" value="1" />
                <el-option label="无" value="0" />
              </el-select>
            </template>
          </el-table-column>
        </el-table>
        <!-- 仅展示 -->
        <el-table border v-loading="zysLoading" :data="formData.griddata" stripe v-else>
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="起日期" prop="startDate" align="center" width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.startDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="止日期" prop="stopDate" align="center" width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.stopDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="工作单位" prop="dept" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.dept }}</span>
            </template>
          </el-table-column>
          <el-table-column label="车间" prop="branch" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.branch }}</span>
            </template>
          </el-table-column>
          <el-table-column label="工种" prop="typeOfWork" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.typeOfWork }}</span>
            </template>
          </el-table-column>
          <el-table-column label="危害因素 " prop="harmName" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.harmName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="有无防护措施" prop="occupationDefend" align="center" min-width="110">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.occupationDefend == 1">有</el-tag>
              <el-tag type="danger" v-if="scope.row.occupationDefend == 0">无</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 职业病史 -->
      <div class="charge-item">
        <h3>【职业病史】</h3>
        <!-- 2025-06-10 删除隐藏按钮 -->
        <!-- <el-row style="margin-top: 16px" :gutter="10" class="mb8" v-if="!mainDisabled"> -->
        <el-row style="margin-top: 16px" :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-plus" @click="diseasesAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button class="section-btn-style2" icon="el-icon-delete" :disabled="multipleDiseases" @click="diseasesRemove">删除</el-button>
          </el-col>
        </el-row>
        <!-- 可编辑 -->
        <el-table border v-loading="zybsLoading" :data="formData.data" stripe @selection-change="handleDiseasesChange" v-if="!mainDisabled">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="病名" prop="occupationDiseastName" align="center" min-width="125">
            <template slot-scope="scope">
              <el-form-item :prop="'data.' + scope.$index + '.occupationDiseastName'" :rules="rules.occupationDiseastName">
                <input-select class="hide-clear" :selectData="diseaseData" :initialValue="scope.row.occupationDiseastName" selectSize="mini" selectWidth="100%" :current-index="scope.$index" @change="diseaseChange"></input-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="诊断日期" prop="diagnosisDate" align="center" min-width="125">
            <template slot-scope="scope">
              <el-date-picker class="hide-clear" size="mini" v-model="scope.row.diagnosisDate" clearable style="width: 100%" value-format="yyyy-MM-dd" type="date" key="date" placeholder="请选择日期"> </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="诊断单位" prop="diagnosisDept" align="center" min-width="125">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.diagnosisDept" placeholder="请输入"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="是否痊愈" prop="status" align="center" min-width="125">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.status" :true-label="1" :false-label="0"></el-checkbox>
            </template>
          </el-table-column>
        </el-table>
        <!-- 仅展示 -->
        <el-table border v-loading="zybsLoading" :data="formData.data" stripe v-else>
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="病名" prop="occupationDiseastName" align="center" min-width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.occupationDiseastName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="诊断日期" prop="diagnosisDate" align="center" min-width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.diagnosisDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="诊断单位 " prop="diagnosisDept" align="center" min-width="125">
            <template slot-scope="scope">
              <span>{{ scope.row.diagnosisDept }}</span>
            </template>
          </el-table-column>
          <el-table-column label="是否痊愈" prop="status" align="center" min-width="125">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == 1">是</el-tag>
              <el-tag type="danger" v-if="scope.row.status == 0">否</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 既往病史 -->
      <div class="charge-item">
        <h3>【既往病史】</h3>
        <!-- 存在数据选择框 -->
        <div>
          <el-form-item prop="formdata.everOfDisease" label="病名" label-width="60px">
            <el-tooltip class="item" effect="dark" :content="formData.formdata.everOfDisease" placement="top" :disabled="!formData.formdata.everOfDisease" v-if="!mainDisabled">
              <search-select :selectData="everOfData" selectSize="mini" selectWidth="320" :multiple="true" @change="everOfChange"></search-select>
            </el-tooltip>
            <el-input style="width: 320px" v-model="formData.formdata.everOfDiseaseName" readonly v-else></el-input>
          </el-form-item>
          <el-form-item prop="formdata.everOfDiseaseRemark" label="备注" label-width="60px">
            <el-input style="width: 350px" v-model="formData.formdata.everOfDiseaseRemark" clearable placeholder="请输入" :readonly="mainDisabled"></el-input>
          </el-form-item>
        </div>
      </div>
      <!-- 月经及绝育史 -->
      <div class="charge-item">
        <h3>【月经及绝育史】</h3>
        <!-- 存在数据选择框 -->
        <div>
          <el-form-item prop="formdata.ccnl" label="初潮">
            <el-input type="number" v-model="formData.formdata.ccnl" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.jq" label="经期">
            <el-input type="number" v-model="formData.formdata.jq" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.zq" label="周期">
            <el-input type="number" v-model="formData.formdata.zq" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.tjnl" label="停经年龄">
            <el-input type="number" v-model="formData.formdata.tjnl" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.familyNumber" label="现有子女">
            <el-input type="number" v-model="formData.formdata.familyNumber" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.lc" label="流产">
            <el-input type="number" v-model="formData.formdata.lc" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.zc" label="早产">
            <el-input type="number" v-model="formData.formdata.zc" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.sc" label="死产">
            <el-input type="number" v-model="formData.formdata.sc" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.ywrc" label="异常胎">
            <el-input type="number" v-model="formData.formdata.ywrc" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
          <el-form-item prop="formdata.jt" label="先天畸形">
            <el-input type="number" v-model="formData.formdata.jt" style="width: 180px" :min="0" :disabled="formData.formdata.idSex != 1" clearable :readonly="mainDisabled"></el-input>
          </el-form-item>
        </div>
      </div>
      <!-- 吸烟史 --> 
      <div class="charge-item">
        <h3>【吸烟史】</h3>
        <div>
          <el-form-item prop="formdata.abstainSmokeNote" style="padding-left: 40px" :class="{ 'read-only': mainDisabled }">
            <el-radio-group v-model="formData.formdata.abstainSmokeNote" @input="smokeChange">
              <el-radio label="0">从不吸烟</el-radio>
              <el-radio label="1">偶尔吸烟</el-radio>
              <el-radio label="2">曾吸烟，已戒烟</el-radio>
              <el-radio label="3">经常吸烟</el-radio>
            </el-radio-group>
          </el-form-item>
          <br />
          <el-form-item label="经常吸" prop="formdata.everydaySmokeN">
            <el-input-number v-model="formData.formdata.everydaySmokeN" controls-position="right" style="width: 100px" :min="0" :disabled="formData.formdata.abstainSmokeNote != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.everydaySmokeN" style="width: 100px" :disabled="formData.formdata.abstainSmokeNote != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">支/天</span>
          </el-form-item>
          <el-form-item label="共计" prop="formdata.smokeYear">
            <el-input-number v-model="formData.formdata.smokeYear" controls-position="right" style="width: 100px" :min="0" :disabled="formData.formdata.abstainSmokeNote != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.smokeYear" style="width: 100px" :disabled="formData.formdata.abstainSmokeNote != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">年</span>
            <el-input-number v-model="formData.formdata.smokeMonth" controls-position="right" style="width: 100px" :min="0" :max="12" :disabled="formData.formdata.abstainSmokeNote != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.smokeMonth" style="width: 100px" :disabled="formData.formdata.abstainSmokeNote != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">月</span>
          </el-form-item>
        </div>
      </div>
      <!-- 饮酒史 -->
      <div class="charge-item">
        <h3>【饮酒史】</h3>
        <div>
          <el-form-item prop="formdata.kiss" style="padding-left: 40px" :class="{ 'read-only': mainDisabled }">
            <el-radio-group v-model="formData.formdata.kiss" @input="kissChange">
              <el-radio label="0">从不饮酒</el-radio>
              <el-radio label="1">偶尔饮酒</el-radio>
              <el-radio label="2">曾喝酒，已戒酒</el-radio>
              <el-radio label="3">经常喝酒</el-radio>
            </el-radio-group>
          </el-form-item>
          <br />
          <el-form-item label="经常喝" prop="formdata.kissAmount">
            <el-input-number v-model="formData.formdata.kissAmount" controls-position="right" style="width: 100px" :min="0" :disabled="formData.formdata.kiss != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.kissAmount" style="width: 100px" :disabled="formData.formdata.kiss != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">ml/天</span>
          </el-form-item>
          <el-form-item label="共计" prop="formdata.kissYearN">
            <el-input-number v-model="formData.formdata.kissYearN" controls-position="right" style="width: 100px" :min="0" :disabled="formData.formdata.kiss != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.kissYearN" style="width: 100px" :disabled="formData.formdata.kiss != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">年</span>
            <el-input-number v-model="formData.formdata.kissMonth" controls-position="right" style="width: 100px" :min="0" :max="12" :disabled="formData.formdata.kiss != 3" v-if="!mainDisabled"></el-input-number>
            <el-input v-model="formData.formdata.kissMonth" style="width: 100px" :disabled="formData.formdata.kiss != 3" readonly v-else />
            <span style="line-height: 20px; color: #666666; margin-left: 5px">月</span>
          </el-form-item>
          <el-form-item label="饮酒种类" prop="formdata.kissType">
            <el-select :disabled="formData.formdata.kiss != 3" v-model="formData.formdata.kissType" clearable placeholder="无" v-if="!mainDisabled">
              <el-option :label="item.typeName" :value="item.typeName" v-for="item in alcoholType" :key="item.id"></el-option>
            </el-select>
            <el-input v-model="formData.formdata.kissType" style="width: 120px" :disabled="formData.formdata.kiss != 3" readonly v-else />
          </el-form-item>
        </div>
      </div>
      <!-- 家族病史 -->
      <div class="charge-item">
        <h3>【家族病史】</h3>
        <div @dblclick="showFamily">
          <el-input ref="familyInput" type="textarea" v-model="formData.formdata.familyOfDisease" style="width: 100%" :readonly="mainDisabled" placeholder="请输入家族病史或双击后选择"></el-input>
        </div>
      </div>
      <!-- 症状 -->
      <div class="charge-item">
        <h3>【症状】</h3>
        <div @dblclick="showSymptom">
          <el-input type="textarea" v-model="formData.formdata.symptom" style="width: 100%" readonly placeholder="请输入症状或双击后选择"></el-input>
        </div>
      </div>
      <!-- 其他 -->
      <div class="charge-item">
        <h3>【其他】</h3>
        <div>
          <el-input type="textarea" v-model="formData.formdata.other" style="width: 100%" :readonly="mainDisabled" placeholder="请输入其他事项"></el-input>
        </div>
      </div>
      <!-- 签名 -->
      <div class="charge-item" style="-moz-padding-bottom: 100px">
        <h3>【签名】</h3>
        <el-button class="section-btn-style1" icon="el-icon-edit" @click="signName" :disabled="mainDisabled">签名</el-button>
        <div style="margin-top: 10px">
          <img :src="signResult" v-if="signResult" style="width: 100%; height: auto; max-width: 300px" />
          <img :src="imgPath + formData.url" v-else-if="formData.url" style="width: 100%; height: auto; max-width: 300px" />
        </div>
      </div>
    </el-form>
    <signature ref="signature" @saveSignPath="saveSignPath" @saveSignPath2="saveSignPath2"></signature>
    <family-disease ref="familyDisease" @familyChange="familyChange"></family-disease>
    <symptom ref="symptom" @symptomChange="symptomChange"></symptom>
    <el-dialog :title="title" :visible.sync="open" width="565px" append-to-body class="show-content">
      <div class="container">
        {{ popData }}
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button class="section-btn-style1" @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getDrinkType, saveOrUpdateInquiry, getZybsData, getZysData, uploadFile } from '@/api/funcdept/section_list/professional_greetings.js'
import signature from '@/views/funcdept/section_list/list/dialog/signature'
import familyDisease from '@/views/funcdept/section_list/list/dialog/family_disease.vue'
import symptom from '@/views/funcdept/section_list/list/dialog/symptom.vue'
import Cookies from 'js-cookie'
export default {
  props: ['leftData', 'mainDisabled'],
  components: { signature, familyDisease, symptom },
  data() {
    return {
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 选中数组
      occupationIndex: undefined,
      diseasesIndex: undefined,
      // 非多个禁用
      multipleOccupation: true,
      multipleDiseases: true,
      // 遮罩层
      zysLoading: false,
      zybsLoading: false,
      ///参数
      formData: {},
      // 工作单位
      workData: {
        placeholder: '请输入',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/createorder/getKhdwmcData', //请求连接
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 危害因素搜索数据
      hazardData: {
        placeholder: '请选择',
        inputTitle: '危害因素名称', // 搜索标题
        inputPlaceholder: '请输入危害因素名称', // 搜索placeholder
        key: '输入码',
        value: '危害因素名称',
        url: '/abteilung/division/getJhysData', //请求连接
        firstName: 'harmCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
        params: {},
      },
      // 既往病史搜索数据
      everOfData: {
        placeholder: '无',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '请输入', // 搜索placeholder
        key: '输入码',
        value: '疾病名称',
        bindValue: '', //初始值
        url: '/abteilung/division/getJwbData', //请求连接
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'occupationDiseast', //接口返回值对应第二列的参数名
      },
      // 职业病名称
      diseaseData: {
        placeholder: '请输入',
        key: '输入码', //第一列标题
        value: '职业病名称', //第二列标题
        url: '/abteilung/division/getAutoCompleteData', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'occupationDiseast', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 饮酒种类
      alcoholType: [],
      // 签名图片
      signResult: '',
      // 表单校验
      rules: {
        startDate: [{ required: true, message: '', trigger: 'change' }],
        stopDate: [{ required: true, message: '', trigger: 'change' }],
        harmName: [{ required: true, message: '', trigger: 'change' }],
        occupationDiseastName: [{ required: true, message: '', trigger: 'change' }],
      },
      // 弹窗标题
      title: '',
      // 是否打开弹窗
      open: false,
      // 弹窗数据
      popData: '',
      // 表格删除数据
      occupationDel: [],
      diseasesDel: [],
      // 加载动画
      clLoading: null,
      // 签名类型
      signType: 0,
    }
  },
  created() {
    this.getDrinkType()
  },
  watch: {
    leftData: {
      handler(value) {
        if (value.peispatient && value.peispatient.patientcode) {
          this.initData()
        } else {
          this.reset()
        }
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    // 初始化数据
    initData() {
      this.formData = {
        formdata: {
          id: this.leftData.peispatient.id,
          patientname: this.leftData.peispatient.patientname,
          idSex: this.leftData.peispatient.idSex,
          nation: this.leftData.peispatient.nation,
          idcardno: this.leftData.peispatient.idcardno,
          yzbm: this.leftData.peispatient.yzbm,
          phone: this.leftData.peispatient.phone,
          jhys: this.leftData.jhys,
          zgl: this.leftData.peispatient.zgl,
          jhgl: this.leftData.peispatient.jhgl,
          idResarea: this.leftData.peispatient.idResarea,
          resarea: this.leftData.peispatient.resarea,
          address: this.leftData.peispatient.address,
          everOfDisease: this.leftData.peispatientConsultation.everOfDisease || undefined,
          everOfDiseaseName: this.leftData.peispatientConsultation.everOfDiseaseName || undefined,
          everOfDiseaseRemark: this.leftData.peispatientConsultation.everOfDiseaseRemark,
          ccnl: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.ccnl : undefined,
          jq: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.jq : undefined,
          zq: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.zq : undefined,
          tjnl: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.tjnl : undefined,
          familyNumber: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.familyNumber : undefined,
          lc: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.lc : undefined,
          zc: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.zc : undefined,
          sc: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.sc : undefined,
          ywrc: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.ywrc : undefined,
          jt: this.leftData.peispatient.idSex == 1 ? this.leftData.peispatientConsultation.jt : undefined,
          abstainSmokeNote: this.leftData.peispatientConsultation.abstainSmokeNote || '0',
          everydaySmokeN: this.leftData.peispatientConsultation.abstainSmokeNote == '3' ? this.leftData.peispatientConsultation.everydaySmokeN : undefined,
          smokeYear: this.leftData.peispatientConsultation.abstainSmokeNote == '3' ? this.leftData.peispatientConsultation.smokeYear : undefined,
          smokeMonth: this.leftData.peispatientConsultation.abstainSmokeNote == '3' ? this.leftData.peispatientConsultation.smokeMonth : undefined,
          kiss: this.leftData.peispatientConsultation.kiss || '0',
          kissAmount: this.leftData.peispatientConsultation.kiss == '3' ? this.leftData.peispatientConsultation.kissAmount : undefined,
          kissYearN: this.leftData.peispatientConsultation.kiss == '3' ? this.leftData.peispatientConsultation.kissYearN : undefined,
          kissMonth: this.leftData.peispatientConsultation.kiss == '3' ? this.leftData.peispatientConsultation.kissMonth : undefined,
          kissType: this.leftData.peispatientConsultation.kiss == '3' ? this.leftData.peispatientConsultation.kissType : undefined,
          familyOfDisease: this.leftData.peispatientConsultation.familyOfDisease,
          symptom: this.leftData.peispatientConsultation.symptom,
          other: this.leftData.peispatientConsultation.other,
        },
        griddata: [],
        data: [],
        url: this.leftData.peispatientConsultationPic ? this.leftData.peispatientConsultationPic.signPicture : undefined,
      }
      this.signResult = ''
      this.signType = 0
      this.$nextTick(() => {
        if (this.leftData.peispatientConsultation.everOfDisease) {
          this.everOfData.bindValue = this.leftData.peispatientConsultation.everOfDisease.split(',')
        } else {
          this.everOfData.bindValue = []
        }
      })
      if (this.leftData.peispatient.patientcode) {
        this.zysLoading = true
        this.getZysData()
      } else {
        this.zysLoading = false
      }
      if (this.leftData.peispatient.patientarchiveno) {
        this.zybsLoading = true
        this.getZybsData(this.leftData.peispatient.patientarchiveno)
      } else {
        this.zybsLoading = false
      }
    },
    // 重置
    clearData() {
      var formdata = {
        id: this.leftData.peispatient.id,
        patientname: this.leftData.peispatient.patientname,
        idSex: this.leftData.peispatient.idSex,
        nation: this.leftData.peispatient.nation,
        idcardno: this.leftData.peispatient.idcardno,
        yzbm: this.leftData.peispatient.yzbm,
        phone: this.leftData.peispatient.phone,
        jhys: this.leftData.jhys,
        zgl: this.leftData.peispatient.zgl,
        jhgl: this.leftData.peispatient.jhgl,
        idResarea: this.leftData.peispatient.idResarea,
        resarea: this.leftData.peispatient.resarea,
        address: this.leftData.peispatient.address,
        everOfDisease: '',
        everOfDiseaseRemark: '',
        ccnl: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        jq: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        zq: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        tjnl: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        familyNumber: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        lc: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        zc: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        sc: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        ywrc: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        jt: this.leftData.peispatient.idSex == 1 ? '' : undefined,
        abstainSmokeNote: '0',
        everydaySmokeN: undefined,
        smokeYear: undefined,
        kiss: '0',
        kissAmount: undefined,
        kissYearN: undefined,
        kissType: undefined,
        familyOfDisease: '',
        symptom: '',
      }
      this.$set(this.formData, 'formdata', formdata)
      for (var i in this.formData.griddata) {
        if (this.formData.griddata[i].state === 'modified') {
          this.formData.griddata[i].state = 'removed'
          this.occupationDel.push(this.formData.griddata[i])
        }
      }
      this.$set(this.formData, 'griddata', [])
      for (var i in this.formData.data) {
        if (this.formData.data[i].state === 'modified') {
          this.formData.data[i].state = 'removed'
          this.diseasesDel.push(this.formData.data[i])
        }
      }
      this.$set(this.formData, 'data', [])
    },
    // 获取职业史数据
    getZysData() {
      var query = {
        key: this.leftData.peispatient.patientcode,
      }
      getZysData(query).then((res) => {
        this.zysLoading = false
        for (var i in res.data) {
          res.data[i].startDate = res.data[i].startDate.split(' ')[0]
          res.data[i].stopDate = res.data[i].stopDate.split(' ')[0]
          res.data[i].jhys = res.data[i].harmName.split(',')
          res.data[i].index = i
          res.data[i].state = 'modified'
        }
        this.formData.griddata = res.data
      })
    },
    // 获取职业病史数据
    getZybsData(key) {
      var query = {
        key: key,
      }
      getZybsData(query).then((res) => {
        this.zybsLoading = false
        for (var i in res.data) {
          res.data[i].diagnosisDate = res.data[i].diagnosisDate.split(' ')[0]
          res.data[i].index = i
          res.data[i].state = 'modified'
        }
        this.formData.data = res.data
      })
    },
    // 表格选中
    handleOccupationChange(selection) {
      this.occupationIndex = selection.map((item) => item.index)
      this.multipleOccupation = !selection.length
    },
    handleDiseasesChange(selection) {
      this.diseasesIndex = selection.map((item) => item.index)
      this.multipleDiseases = !selection.length
    },
    // 总工龄变化
    zglChange(val) {
      if (val < this.formData.formdata.jhgl) {
        this.formData.formdata.jhgl = val
      }
    },
    // 新增职业史
    occupationAdd() {
      let state = 'added'
      let index = 0
      if (this.formData.griddata.length > 0) index = this.formData.griddata[this.formData.griddata.length - 1].index + 1
      let obj = { index, state }
      this.formData.griddata.push(obj)
    },
    // 新增职业病史
    diseasesAdd() {
      let state = 'added'
      let index = 0
      let status = 0
      if (this.formData.data.length > 0) index = this.formData.data[this.formData.data.length - 1].index + 1
      let obj = { index, state, status }
      this.formData.data.push(obj)
    },
    // 删除职业史
    occupationRemove() {
      for (let i in this.occupationIndex) {
        let index = this.formData.griddata.findIndex((item) => {
          if (this.occupationIndex[i] == item.index) {
            return true
          }
        })
        if (this.formData.griddata[index].state === 'modified') {
          this.formData.griddata[index].state = 'removed'
          this.occupationDel.push(this.formData.griddata[index])
        }
        this.$delete(this.formData.griddata, index)
      }
    },
    // 删除职业病史
    diseasesRemove() {
      for (let i in this.diseasesIndex) {
        let index = this.formData.data.findIndex((item) => {
          if (this.diseasesIndex[i] == item.index) {
            return true
          }
        })
        if (this.formData.data[index].state === 'modified') {
          this.formData.data[index].state = 'removed'
          this.diseasesDel.push(this.formData.data[index])
        }
        this.$delete(this.formData.data, index)
      }
    },
    // 表单重置
    reset() {
      this.formData = {
        formdata: {
          patientname: undefined,
          idSex: undefined,
          nation: undefined,
          idcardno: undefined,
          yzbm: undefined,
          phone: undefined,
          jhys: undefined,
          zgl: undefined,
          jhgl: undefined,
          idResarea: undefined,
          resarea: undefined,
          address: undefined,
          everOfDisease: undefined,
          everOfDiseaseRemark: undefined,
          ccnl: undefined,
          jq: undefined,
          zq: undefined,
          tjnl: undefined,
          familyNumber: undefined,
          lc: undefined,
          zc: undefined,
          sc: undefined,
          ywrc: undefined,
          jt: undefined,
          abstainSmokeNote: '0',
          everydaySmokeN: undefined,
          smokeYear: undefined,
          kiss: '0',
          kissAmount: undefined,
          kissYearN: undefined,
          kissType: undefined,
          familyOfDisease: undefined,
          symptom: undefined,
        },
        griddata: [],
        data: [],
      }
      this.signResult = ''
      this.resetForm('form')
    },
    // 选择工作单位
    workChange(value, index) {
      this.$set(this.formData.griddata[index], 'deptId', value.id)
      this.$set(this.formData.griddata[index], 'dept', value.khdwmc)
    },
    // 危害因素选择
    hazardChange(value, index) {
      var ids = value.map((item) => item.id)
      var harmName = value.map((item) => item.label)
      this.$set(this.formData.griddata[index], 'occupationHarm', ids.join())
      this.$set(this.formData.griddata[index], 'harmName', harmName.join())
      this.$set(this.formData.griddata[index], 'jhys', harmName)
    },
    // 既往病史选择
    everOfChange(value) {
      this.$set(this.formData.formdata, 'everOfDisease', '')
      var everOfDisease = ''
      value.forEach((el) => {
        if (!everOfDisease) {
          everOfDisease = el.id
        } else {
          everOfDisease += ',' + el.id
        }
      })
      this.$set(this.formData.formdata, 'everOfDisease', everOfDisease)
    },
    // 选择职业病名称
    diseaseChange(value, index) {
      this.$set(this.formData.data[index], 'occupationDiseast', value.id)
      this.$set(this.formData.data[index], 'occupationDiseastName', value.occupationDiseast)
    },
    // 性别变化
    idSexChange(value) {
      if (value != 1) {
        this.$set(this.formData.formdata, 'ccnl', undefined)
        this.$set(this.formData.formdata, 'jq', undefined)
        this.$set(this.formData.formdata, 'zq', undefined)
        this.$set(this.formData.formdata, 'tjnl', undefined)
        this.$set(this.formData.formdata, 'familyNumber', undefined)
        this.$set(this.formData.formdata, 'lc', undefined)
        this.$set(this.formData.formdata, 'zc', undefined)
        this.$set(this.formData.formdata, 'sc', undefined)
        this.$set(this.formData.formdata, 'ywrc', undefined)
        this.$set(this.formData.formdata, 'jt', undefined)
      }
    },
    // 吸烟史变化
    smokeChange(value) {
      if (value != 3) {
        this.$set(this.formData.formdata, 'everydaySmokeN', undefined)
        this.$set(this.formData.formdata, 'smokeYear', undefined)
      }
    },
    // 饮酒史变化
    kissChange(value) {
      if (value != 3) {
        this.$set(this.formData.formdata, 'kissAmount', undefined)
        this.$set(this.formData.formdata, 'kissYearN', undefined)
        this.$set(this.formData.formdata, 'kissType', undefined)
      }
    },
    // 获取饮酒种类 
    getDrinkType() {
      getDrinkType().then((res) => {
        this.alcoholType = res.data
      })
    },
    // 展示家族病史弹窗
    showFamily() {
      if (this.mainDisabled) {
        if (this.formData.formdata.familyOfDisease) {
          this.title = '家族病史'
          this.popData = this.formData.formdata.familyOfDisease
          this.open = true
        }
      } else {
        this.$refs.familyInput.blur()
        this.$refs.familyDisease.handleShow(this.formData.formdata.familyOfDisease)
      }
    },
    // 选择家族病史
    familyChange(value) {
      this.$set(this.formData.formdata, 'familyOfDisease', value)
    },
    // 展示症状弹窗
    showSymptom() {
      if (this.mainDisabled) {
        if (this.formData.formdata.symptom) {
          this.title = '症状'
          this.popData = this.formData.formdata.symptom
          this.open = true
        }
      } else {
        if (this.formData.formdata.symptom) {
          var symptom = this.formData.formdata.symptom.split(',')
          var selectList = []
          symptom.forEach((el) => {
            selectList.push({
              symptom: el.split(':')[0],
              degree: el.split(':')[1],
            })
          })
          this.$refs.symptom.handleShow(selectList, this.leftData.peispatient.patientcode)
        } else {
          this.$refs.symptom.handleShow('', this.leftData.peispatient.patientcode)
        }
      }
    },
    // 选择症状
    symptomChange(list) {
      var symptom = ''
      list.forEach((el) => {
        if (symptom.length > 0) symptom += ',' + el.symptom + ':' + el.degree
        else symptom += el.symptom + ':' + el.degree
      })
      this.$set(this.formData.formdata, 'symptom', symptom)
    },
    // 签名
    signName() {
      if (this.formData.isAudit == 1) {
        this.$modal.alertWarning('数据已审核,不能修改!')
        return
      }
      this.$refs.signature.signature()
    },
    // 签名返回值
    saveSignPath(url) {
      this.signType = 0
      this.signResult = url
    },
    // 本地上传签名
    saveSignPath2(url) {
      this.signType = 2
      this.signResult = url
    },
    // 审核
    handleAudit(personData) {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.formData.formdata.symptom) {
            this.$alert('请填写症状！', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
            return
          }
          
          var formData = JSON.parse(JSON.stringify(this.formData))
          formData.griddata = [...this.occupationDel, ...this.formData.griddata]
          formData.data = [...this.diseasesDel, ...this.formData.data]
          formData.ksID = this.leftData.ksID
          formData.personData = personData
          if (formData.formdata.kiss == '0') {
            formData.formdata.noKissTheCup = '1'
            formData.formdata.betweenKissTheCup = '0'
            formData.formdata.abstainLostKiss = '0'
            formData.formdata.evermoreKiss = '0'
          } else if (formData.formdata.kiss == '1') {
            formData.formdata.noKissTheCup = '0'
            formData.formdata.betweenKissTheCup = '1'
            formData.formdata.abstainLostKiss = '0'
            formData.formdata.evermoreKiss = '0'
          } else if (formData.formdata.kiss == '2') {
            formData.formdata.noKissTheCup = '0'
            formData.formdata.betweenKissTheCup = '0'
            formData.formdata.abstainLostKiss = '1'
            formData.formdata.evermoreKiss = '0'
          } else if (formData.formdata.kiss == '3') {
            formData.formdata.noKissTheCup = '0'
            formData.formdata.betweenKissTheCup = '0'
            formData.formdata.abstainLostKiss = '0'
            formData.formdata.evermoreKiss = '1'
          }
          for (var i in formData.griddata) {
            formData.griddata[i].startDate += ' 00:00:00'
            formData.griddata[i].stopDate += ' 00:00:00'
          }
          for (var i in formData.data) {
            if (formData.data[i].diagnosisDate) {
              formData.data[i].diagnosisDate = formData.data[i].diagnosisDate.slice(0, 10) + ' 00:00:00'
            }else{
              formData.data[i].diagnosisDate = ''
            }
          }
          this.clLoading = this.$loading({ target: '#sectionIndex' })
          if (this.signResult) {
            if (this.signType == 2) {
              formData.url = this.signResult
              this.saveInquiry(formData)
            } else {
              this.uploadSign(formData)
            }
          } else {
            this.saveInquiry(formData)
          }
        } else {
          this.$alert('页面存在必填项或者填写格式存在问题！', '提示')
        }
      })
    },
    // 上传签名图片
    uploadSign(formData) {
      var bytes = window.atob(this.signResult.split(',')[1])
      var array = []
      for (var i = 0; i < bytes.length; i++) {
        array.push(bytes.charCodeAt(i))
      }
      var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
      var data = new FormData()
      data.append('file', blob)
      uploadFile(data)
        .then((res) => {
          formData.url = res.data
          this.saveInquiry(formData)
        })
        .catch(() => {
          this.clLoading.close()
        })
    },
    // 审核接口
    saveInquiry(formData) {
          
      // if (!formData.url) {
      //       this.$alert('请进行签名！', '提示', {
      //         confirmButtonText: '确定',
      //         type: 'warning',
      //       })
      //       this.clLoading.close()
      //       return
      // }    
      saveOrUpdateInquiry(formData)
        .then(() => {
            localStorage.setItem(
              'section' + this.$route.meta.ksID,
              JSON.stringify({
                rummagerName: formData.personData.jcrxm,
                rummagerId: formData.personData.jcr,
                writeName: formData.personData.lrr,
                writeId: formData.personData.lrrId,
              })
            )
          // this.$tab.refreshPage()
          this.$modal.msgSuccess('审核成功')
          this.$emit('greetingRefresh')
          this.$emit('changeAudit')
          this.clLoading.close()
        })
        .catch(() => {
          this.$emit('changeAudit')
          this.clLoading.close()
        })
    },
  },
}
</script>

<style lang="scss">
.professional-greetings {
  h3 {
    margin: 0 0 10px 0;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .charge-item {
    padding: 20px 0;
    padding-bottom: 0;

    .el-table {
      .el-input .el-input__inner {
        padding: 0 10px !important;
      }

      .hide-clear .el-input__suffix {
        padding: 2px 0;

        .el-icon-circle-close {
          background: #fff;
          line-height: 24px;
          display: block;
          transition: none;
        }
      }

      .show-clear .el-input__inner {
        padding: 0 30px 0 10px !important;
      }

      .el-date-editor {
        .el-input__inner {
          padding-left: 30px !important;
        }
      }

      .el-form-item {
        margin: 0 !important;

        .el-form-item__error {
          display: none;
        }
      }
    }

    .read-only .el-radio {
      pointer-events: none;
    }
  }
}

.show-content {
  .container {
    font-size: 16px;
    line-height: 22px;
    min-height: 150px;
    max-height: 300px;
  }
}
</style>
