<!-- 登记 开发人：麦沃德科技 予安/半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
        <el-form-item label="体检号" prop="patientCode">
          <el-input v-model="queryParams.patientCode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="onSearchCodeEnter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-search" @click="handleResearch">查询</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-refresh-right" @click="handleRefresh">刷新</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="onClearCopy" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:clear']">清空</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleRecommend" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:recommend']">推项</el-button>
          </el-col>
          <el-col :span="1.5" >
            <el-button type="warning" plain size="mini" icon="el-icon-tickets" @click="onGroupUnit" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:group']">团检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="onLookSimilarPeople" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:repetition']">重复</el-button>
          </el-col>
          <template v-if="!isOnline">
            <!-- <template v-if="true"> -->
            <el-col :span="1.5">
              <el-button type="primary" plain size="mini" icon="el-icon-circle-check" @click="onJumpChargePage" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:charge']">收费</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" plain size="mini" icon="el-icon-circle-close" @click="handleReturn" :disabled="setReadonly.btncon && setReadonly.tuixiang" v-hasPermi="['reception:registration:return']">退项</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain size="mini" icon="el-icon-printer" @click="handlePrint" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:print']">速打</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-tag>打印{{ printCount || 0 }}次</el-tag>
            </el-col>
          </template>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-circle-check" @click="handleAppointment" :disabled="setReadonly.btncon" v-hasPermi="['reception:registration:appointment']">完成预约</el-button>
          </el-col>
          <el-col :span="1.5" v-if="isBuJian">
            <el-tag>补检</el-tag>
          </el-col>
        </el-form-item>
      </el-form>
    </el-row>
    <!-- 主体 -->
    <div class="table-box reception-registration">
      <el-row :gutter="10" class="mb8" style="height: 100%; display: flex">
        <!-- 左侧 -->
        <el-col :span="11" style="overflow-y: auto; height: 100%" class="reception-left">
          <el-form :model="formData" v-loading="mainLoading" ref="mainForm" size="mini" :inline="true" label-width="85px" label-position="right" style="min-width: 400px" :rules="rules" :class="setReadonly.maindiv ? 'main-readonly' : ''">
            <!-- 基本信息 -->
            <div class="base-info info-box">
              <el-row type="flex" justify="space-between">
                <el-col :span="isOnline ? 24 : 19">
                  <!-- <template v-if="!isOnline"> -->
                  <el-form-item label="订单号" prop="numorgresv">
                    <el-input v-model="formData.numorgresv" readonly style="width: 165px"></el-input>
                  </el-form-item>
                  <el-form-item label="团体名称" prop="orgName">
                    <el-tooltip effect="dark" :disabled="!formData.orgName" :content="formData.orgName" placement="top-start">
                      <el-input v-model="formData.orgName" readonly style="width: 165px"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="团体分组" prop="orgreservationgroupname">
                    <el-input v-model="formData.orgreservationgroupname" readonly style="width: 165px"></el-input>
                  </el-form-item>
                  <el-form-item label="体检套餐" prop="idTjtc">
                    <el-tooltip effect="dark" :disabled="!formData.tjtcmc" :content="formData.tjtcmc" placement="right-start">
                      <search-select ref="searchTjtc" :selectData="tjtcData" selectSize="mini" :showPrice="true" :initialValue="formData.tjtcmc" :selectWidth="165" :clickQuery="true" :selectDisabled="!setEnabled.idTjtc" @change="onTjtcChanged"></search-select>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="团体部门" prop="orgDepart">
                    <!-- <el-input v-model="formData.orgDepart" :disabled="!setReadonly.orgDepart" style="width: 165px"></el-input> -->
                    <el-input v-model="formData.orgDepart" style="width: 165px"></el-input>
                  </el-form-item>
                  <!-- </template> -->
                  <el-form-item label="开单医师" prop="idOpendoctor">
                    <input-select :selectData="doctorData" :selectWidth="165" @change="doctorChange" :disabled="formData.fusecodehiden == 1 || formData.fregistered == 1 || formData.idOpendoctor == '1788757788712371823'"></input-select>
                  </el-form-item>
                  <el-form-item label="预约时间" prop="dateguidancereturned">
                    <el-date-picker v-model="formData.dateguidancereturned" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" clearable style="width: 165px" :disabled="!setEnabled.dateguidancereturned"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="登记时间" prop="dateregister">
                    <el-date-picker v-model="formData.dateregister" type="date" value-format="yyyy-MM-dd" disabled placeholder="选择日期" clearable style="width: 165px"></el-date-picker>
                  </el-form-item>
                </el-col>
                <!-- 照片 -->
                <el-col :span="5">
                  <div class="photo">
                    <div class="image" v-if="formData.picture === defaultPicture">
                      <img :src="formData.picture" />
                    </div>
                    <div class="image" v-else>
                      <img v-if="typeof formData.picture === 'object' && formData.picture != null" :src="createObjectURL(formData.picture)" />
                      <img :src="formData.picture" v-else-if="isBase64(formData.picture)" />
                      <img :src="imgPath + formData.picture" v-else-if="formData.picture" />
                      <img :src="defaultPicture" v-else />
                    </div>
                    <el-button type="primary" size="mini" icon="el-icon-camera" @click="cap" :disabled="setReadonly.btncon" v-if="!isOnline">
                      {{ capTxt }}
                    </el-button>
                  </div>
                </el-col>
              </el-row>
              <div style="display: flex; flex-wrap: wrap">
                <el-form-item label="分中心" prop="hospitalcode">
                  <el-select v-model="formData.hospitalcode" :disabled="formData.fregistered == 1" placeholder="请选择" style="width: 165px" @change="fzxidChange">
                    <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
                  </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="note" class="row-item" style="flex: 1; min-width: 300px; margin-right: 0">
                  <el-input v-model="formData.note" clearable style="width: 100%"> </el-input>
                </el-form-item>
              </div>
              <div style="display: flex; flex-wrap: wrap">
                <el-form-item label="导引单备注" prop="guidancenote" class="row-item" style="flex: 1;">
                  <el-input v-model="formData.guidancenote" clearable style="width: 100%"> </el-input>
                </el-form-item>
                <el-form-item label="SABC等级" prop="sabc" style="flex: 1;">
                  <el-select v-model="formData.sabc" placeholder="请选择" style="width: 100px">
                    <el-option label="S" value="S"></el-option>
                    <el-option label="A" value="A"></el-option>
                    <el-option label="B" value="B"></el-option>
                    <el-option label="C" value="C"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </div>
            <!-- 切换 -->
            <ul class="main-nav" :style="{ '--theme': theme }">
              <li :class="{ active: formData.fusecodehiden == 0 }" @click="toSwitch(0)">
                <div class="bg"></div>
                <span>个检</span>
              </li>
              <li :class="{ active: formData.fusecodehiden == 1 }" @click="toSwitch(1)">
                <div class="bg"></div>
                <span>团检</span>
              </li>
            </ul>
            <!-- 主要信息 -->
            <div class="main-info info-box">
              <el-row type="flex" justify="space-between" style="flex-wrap: wrap">
                <el-form-item label="姓名" prop="patientname">
                  <!-- 2025-04-29 淮南提出 补检可以换人 换身份证号 -->
                  <el-input v-model="formData.patientname" placeholder="请输入姓名" clearable style="width: 130px"> </el-input>
                  <!-- <el-input v-model="formData.patientname" placeholder="请输入姓名" :disabled="isBuJian" clearable style="width: 130px"> </el-input> -->
                </el-form-item>
                <el-form-item label="会员类型" prop="idPatientclass">
                  <el-select v-model="formData.idPatientclass" placeholder="请选择" style="width: 130px">
                    <el-option v-for="item in memberTypeList" :key="item.levelId" :label="item.levelName" :value="item.levelId"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="体检类型" prop="idExamtype">
                  <el-select v-model="formData.idExamtype" placeholder="请选择" style="width: 130px" @change="onTypeChanged" :disabled="!setEnabled.idExamtype">
                    <el-option v-for="item in idExamtypeList" :key="item.id" :label="item.text" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="体检号" prop="patientcode">
                  <el-input v-model="formData.patientcode" readonly style="width: 130px"></el-input>
                </el-form-item>
                <el-form-item label="备单分类" prop="idExamclass">
                  <el-select v-model="formData.idExamclass" placeholder="请选择" style="width: 130px">
                    <el-option v-for="item in idExamclassList" :key="item.id" :label="item.text" :value="item.id"> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="性别" prop="idSex">
                  <el-select v-model="formData.idSex" placeholder="请选择" style="width: 130px" :disabled="!setEnabled.idSex" @change="onSexChanged">
                    <el-option label="男" :value="0"></el-option>
                    <el-option label="女" :value="1"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="籍贯" prop="idResarea">
                  <el-select v-model="formData.idResarea" filterable :filter-method="idResareaMethod" @change="idResareaChange" style="width: 130px">
                    <el-option v-for="item in idResareaShow" :key="item.id" :label="item.resarea" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="民族" prop="idNation">
                  <el-select v-model="formData.idNation" filterable :filter-method="idNationChange" style="width: 130px">
                    <el-option v-for="item in idNationShow" :key="item.id" :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="生日" prop="birthdate">
                  <el-date-picker v-model="formData.birthdate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" clearable style="width: 130px" @change="onBirthChanged"> </el-date-picker>
                </el-form-item>
                <el-form-item label="婚姻" prop="idMarriage">
                  <el-select v-model="formData.idMarriage" placeholder="请选择" style="width: 130px" clearable>
                    <el-option v-for="item in idMarriageList" :key="item.id" :label="item.text" :value="item.id"> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="手机" prop="phone">
                  <el-input ref="phone" type="tel" v-model="formData.phone" placeholder="请输入手机" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <el-form-item label="家人手机" prop="examsuiteAlias">
                  <el-input type="tel" v-model="formData.examsuiteAlias" placeholder="请输入家人手机" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <el-form-item label="证件类型" prop="countreportoccupationxml">
                  <el-select v-model="formData.countreportoccupationxml" placeholder="请选择" style="width: 130px" @change="handleCardChange">
                    <el-option v-for="item in countreportoccupationxmlList" :key="item.id" :label="item.text" :value="item.id"> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="证件号" prop="idcardno">
                  <!-- 2025-04-29 淮南提出 补检可以换人 换身份证号 -->
                  <el-input v-model="formData.idcardno" placeholder="请输入证件号" clearable style="width: 180px" @blur="onCardNoChanged" @clear="onCardNoChanged" @keyup.enter.native="onCardNoChanged()"> </el-input>
                  <!-- <el-input v-model="formData.idcardno" placeholder="请输入证件号" :disabled="isBuJian" clearable style="width: 180px" @blur="onCardNoChanged" @clear="onCardNoChanged" @keyup.enter.native="onCardNoChanged()"> </el-input> -->
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                  <el-input-number v-model="formData.age" controls-position="right" clearable :min="0" :max="130" style="width: 80px" @change="onAgeChanged"></el-input-number>
                </el-form-item>
                <el-form-item label="家庭住址" prop="address" class="row-item">
                  <el-input v-model="formData.address" clearable style="width: 100%"> </el-input>
                </el-form-item>
                <el-form-item label="替检" prop="countreportxml">
                  <el-radio-group class="radio-box" v-model="formData.countreportxml" @change="ontjchanged">
                    <el-radio :label="0" border>否</el-radio>
                    <el-radio :label="1" border>是</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="原名" prop="tjr" v-if="formData.countreportxml == 1">
                  <el-input v-model="formData.tjr" clearable style="width: 130px"></el-input>
                </el-form-item>
                <el-form-item label="自动已检" prop="autoExamined">
                  <el-checkbox v-model="formData.autoExamined" border :true-label="1" :false-label="0" style="width: 130px"></el-checkbox>
                </el-form-item>
                <el-form-item label="黑名单" prop="isHmd" class="row-item">
                  <el-checkbox v-model="formData.isHmd" :true-label="1" :false-label="0" @change="onHmdCheckedChanged">
                    <el-input v-model="formData.isHmdb" clearable style="width: 100%" placeholder="请输入黑名单备注" :disabled="!formData.isHmd"></el-input>
                  </el-checkbox>
                </el-form-item>
                <el-form-item label="通知方式" prop="idInformway">
                  <el-select v-model="formData.idInformway" placeholder="请选择" style="width: 130px">
                    <el-option v-for="item in idInformwayList" :key="item.id" :label="item.methodName" :value="item.id"> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="预付方式" prop="payway">
                  <el-input v-model="formData.payway" placeholder="请选择" readonly :disabled="!formData.patientcode || !setEnabled.idPayway" style="width: 130px">
                    <button type="button" slot="suffix" class="el-icon-more" :disabled="!formData.patientcode || !setEnabled.idPayway" :class="{ allow: formData.patientcode && setEnabled.idPayway }" @click.stop="onPayWayWindow"></button>
                  </el-input>
                </el-form-item>
                <el-form-item label="预付金额" prop="prepayment">
                  <el-input :value="formData.prepayment ? Number(formData.prepayment).toFixed(2) : '0.00'" readonly style="width: 130px"></el-input>
                </el-form-item>
                <el-form-item label="记账单位" prop="jzdw">
                  <el-autocomplete v-model="formData.jzdw" :fetch-suggestions="querySearchAsync" placeholder="请输入内容" value-key="jzdw" style="width: 130px"></el-autocomplete>
                </el-form-item>
                <el-form-item label="记账人" prop="jzdwr">
                  <el-input v-model="formData.jzdwr" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <el-form-item label="审批人" prop="spr">
                  <el-input v-model="formData.spr" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <template v-if="formData.idExamtype != '0'">
                  <el-form-item label="危害因素" prop="jhys">
                    <el-tooltip effect="dark" :content="formData.harmName" placement="top" :disabled="!formData.jhys">
                      <search-select ref="searchJhys" :selectData="hazardData" :multiple="true" selectWidth="130" @change="hazardChange"></search-select>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="体检类别" prop="medicaltype">
                    <el-select v-model="formData.medicaltype" placeholder="请选择" style="width: 130px" @change="ppzxtc">
                      <el-option v-for="item in medicalTypeList" :key="item.id" :label="item.text" :value="item.id"> </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="工种" prop="worktypeId">
                    <search-select ref="workType" :selectData="workTypeData" selectWidth="130" @change="workTypeChange"></search-select>
                  </el-form-item>
                  <el-form-item label="工号" prop="workno">
                    <el-input v-model="formData.workno" clearable style="width: 130px"> </el-input>
                  </el-form-item>
                  <el-form-item label="总工龄" prop="zgl">
                    <el-input-number v-model="formData.zgl" controls-position="right" style="width: 100px" :min="0" @change="zglChange"></el-input-number>
                    <span class="input-unit">月</span>
                  </el-form-item>
                  <el-form-item label="接害工龄" prop="jhgl">
                    <el-input-number v-model="formData.jhgl" controls-position="right" style="width: 100px" :min="0" :max="formData.zgl"></el-input-number>
                    <span class="input-unit">月</span>
                  </el-form-item>
                  <el-form-item label="参加工作" prop="workDate">
                    <el-date-picker v-model="formData.workDate" clearable style="width: 130px" value-format="yyyy-MM-dd" type="date" @change="workDateChange"> </el-date-picker>
                  </el-form-item>
                  <el-form-item label="从事工种" prop="harmDate">
                    <el-date-picker v-model="formData.harmDate" clearable style="width: 130px" value-format="yyyy-MM-dd" type="date" @change="harmDateChange"> </el-date-picker>
                  </el-form-item>
                </template>
                <el-form-item label="文化程度" prop="cultural">
                  <el-select v-model="formData.cultural" placeholder="请选择" style="width: 130px" clearable>
                    <el-option v-for="item in culturalList" :key="item.value" :label="item.label" :value="item.value" filterabl> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="邮政编码" prop="yzbm">
                  <el-input v-model="formData.yzbm" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <el-form-item label="系统关联ID" prop="thirdCode">
                  <el-input v-model="formData.thirdCode" clearable style="width: 130px"> </el-input>
                </el-form-item>
                <el-form-item label="邮寄地址" prop="yjaddress" class="row-item">
                  <el-input v-model="formData.yjaddress" clearable style="width: 100%" type="textarea" :rows="2"> </el-input>
                </el-form-item>
              </el-row>
            </div>
          </el-form>
          <!-- 预约列表 -->
          <el-row :span="24" v-if="!isOnline">
            <el-table border v-loading="reservLoading" :data="reservList" height="300px" @row-dblclick="onRowDblClick" :row-class-name="rowResergrid">
              <el-table-column label="序列" width="55" type="index" align="center" />
              <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip width="140" />
              <el-table-column label="姓名" prop="realName" align="center" show-overflow-tooltip width="140" />
              <el-table-column label="手机号码" prop="mobile" align="center" show-overflow-tooltip width="120" />
              <el-table-column label="预约时间" prop="reservationDate" align="center" show-overflow-tooltip width="170" />
              <el-table-column label="身份证号" prop="idcard" align="center" show-overflow-tooltip />
            </el-table>
            <pagination :total="reservTotal" :page.sync="reservCurrent" :limit.sync="reservSize" @pagination="getVBookData" />
          </el-row>
        </el-col>
        <!-- 右侧 -->
        <el-col :span="13" style="height: 100%" class="reception-right">
          <div class="flex-direction-column">
            <!-- 前台须知 -->
            <div class="notice">
              <el-tag type="danger" @click="toNotice">前台须知：{{ formData.qtxz }}</el-tag>
            </div>
            <!-- 表格信息 -->
            <div class="table-box flex-direction-column">
              <el-form size="mini" :inline="true" style="margin-top: 4px">
                <el-form-item style="margin-bottom: 0">
                  <el-col :span="1.5">
                    <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="addOneRow" :disabled="!setEnabled.addItemBtn || setReadonly.btncon || isBuJian" v-hasPermi="['registration:registration:add']">新增</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="!selectTable.length || setReadonly.btncon" @click="removeOneRow" v-hasPermi="['registration:registration:remove']">删除</el-button>
                  </el-col>
                  <el-col :span="1.5" v-if="!isOnline">
                    <el-button type="warning" plain size="mini" icon="el-icon-plus" @click="addItem" :disabled="setReadonly.btncon" v-hasPermi="['registration:registration:addItem']">加项</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="danger" plain size="mini" icon="el-icon-scissors" @click="handleDiscount" :disabled="setReadonly.btncon" v-hasPermi="['registration:registration:discount']">折扣</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="warning" plain size="mini" icon="el-icon-folder-add" @click="beforeSave(2, 0, 1, 1)" :disabled="setReadonly.btncon" v-hasPermi="['registration:registration:save']">保存</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="primary" plain size="mini" icon="el-icon-refresh-right" @click="reload" :disabled="setReadonly.btncon" >刷新</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="exports" :disabled="setReadonly.btncon" v-hasPermi="['registration:registration:export']">导出Excel</el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button type="success" plain size="mini" icon="el-icon-bank-card" @click="handleCard" :disabled="setReadonly.btncon" v-hasPermi="['registration:registration:card']">套餐卡</el-button>
                  </el-col>
                  <el-col :span="1.5" v-if="kxzsShow">
                    <el-tag>可选组数：{{ kxzsValue }}</el-tag>
                  </el-col>
                </el-form-item>
              </el-form>
              <div class="table-show">
                <el-table border ref="table" v-loading="loading" size="mini" :data="tableList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick" :row-class-name="rowClassName">
                  <el-table-column fixed type="selection" width="35" align="center" />
                  <el-table-column fixed label="序列" width="40" type="index" align="center" />
                  <el-table-column fixed label="收费项目" prop="examfeeitemName" align="left" width="200">
                    <template slot-scope="scope">
                      <input-select
                        :ref="'item' + scope.$index"
                        :selectData="itemData"
                        selectSize="mini"
                        :queryParams="{ thiredTj: formData.idExamtype, isBan: 0, thiredSex: formData.idSex, numorgresv: formData.numorgresv }"
                        @change="onExamfeeitemChanged"
                        :initialValue="scope.row.examfeeitemName"
                        :current-index="scope.$index"
                        :disabled="(scope.row.idPayway == 5 && scope.row.idExamfeeitem != '') || scope.row.FFeecharged == 1 || scope.row.isMintc == 1"
                        :notShowEmpty="!isOnline ? true : false"
                        :showTooltip="true"
                      ></input-select>
                    </template>
                  </el-table-column>
                  <el-table-column label="备选分组" prop="group" align="center" min-width="70" show-overflow-tooltip />
                  <el-table-column label="价格" align="center">
                    <el-table-column label="原价" prop="price" align="center" width="70">
                      <template slot-scope="scope">
                        {{ isNaN(scope.row.price) ? scope.row.price : Number(scope.row.price).toFixed(2) }}
                      </template>
                    </el-table-column>
                    <el-table-column label="优惠价" prop="factprice" align="center" width="70px">
                      <template slot-scope="scope">
                        <el-input-number v-model="scope.row.factprice" controls-position="right" size="mini" :min="0" :precision="2" style="width: 100%" @change="factpriceChange(scope.row.factprice, scope.$index)" :disabled="scope.row.FFeecharged == 1"></el-input-number>
                      </template>
                    </el-table-column>
                  </el-table-column>
                  <el-table-column label="付款方式" prop="idPayway" align="center" min-width="70">
                    <template slot-scope="scope">
                      <select class="select-option" v-model="scope.row.idPayway" :disabled="scope.row.FFeecharged == 1">
                        <option value="1">现金</option>
                        <option value="5" v-if="formData.fusecodehiden == 1">统收</option>
                      </select>
                    </template>
                  </el-table-column>
                  <el-table-column label="加项医师" prop="name" align="center" width="70">
                    <template slot-scope="scope">
                      <input-select :selectData="jxysData" selectSize="mini" :initialValue="scope.row.name" @change="jxysChange" :current-index="scope.$index" :disabled="scope.row.FExaminated == 1"></input-select>
                    </template>
                  </el-table-column>
                  <template v-if="!isOnline">
                    <el-table-column label="加项" prop="sfjx" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.sfjx == 1 }">
                          <i v-if="scope.row.sfjx == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="登记" prop="FRegistered" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FRegistered == 1 }">
                          <i v-if="scope.row.FRegistered == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="已收" prop="FFeecharged" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FFeecharged == 1 }">
                          <i v-if="scope.row.FFeecharged == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="已发" prop="FLabsendtolis" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FLabsendtolis == 1 }">
                          <i v-if="scope.row.FLabsendtolis == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="已检" prop="FExaminated" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FExaminated == 1 }">
                          <i v-if="scope.row.FExaminated == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="弃检" prop="FGiveup" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FGiveup == 1 }">
                          <i v-if="scope.row.FGiveup == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="迟检" prop="FDelayed" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.FDelayed == 1 }">
                          <i v-if="scope.row.FDelayed == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="拒检" prop="sfjj" align="center" width="40">
                      <template slot-scope="scope">
                        <div class="check-box" :class="{ select: scope.row.sfjj == 1 }">
                          <i v-if="scope.row.sfjj == 1" class="el-icon-check"></i>
                        </div>
                      </template>
                    </el-table-column>
                  </template>
                  <el-table-column label="备注" prop="feeitemdesc" align="center" width="185px">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.feeitemdesc" style="width: 100%" size="mini" :readonly="scope.row.FFeecharged == 1"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="科室" prop="ksName" align="center" show-overflow-tooltip width="120" />
                  <el-table-column label="登记人" prop="doctorregR" align="center" width="125px">
                    <template slot-scope="scope">
                      <span v-if="scope.row.FRegistered == 1">{{ scope.row.doctorregR }}</span>
                      <el-select v-model="scope.row.doctorregR" filterable remote clearable :remote-method="remoteMethod" :loading="remoteLoading" style="width: 100%" @change="regChange" size="mini" v-else>
                        <el-option v-for="(item, index) in regOptions" :key="index" :label="item.occupationSummary" :value="{ item: item, index: scope.$index }"> </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column label="创建时间" prop="createdate" align="center" show-overflow-tooltip width="120" />
                  <el-table-column label="查看图片" align="center" width="120">
                    <template slot-scope="scope">
                      <span class="check-pic" @click="checkPic(scope.row)">查看图片</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <el-image ref="previewImg" :src="chargeUrl" :preview-src-list="[chargeUrl]" style="display: none"></el-image>
            </div>
            <!-- 统计数据 -->
            <el-row type="flex" justify="space-between" style="width: 100%">
              <el-col :span="24" style="border: 1px solid #d4d6d9; margin-bottom: 8px">
                <div class="statistics">
                  <!-- <div class="item">
                    <div class="title">总数</div>
                    <div class="number" :style="{ '--theme': theme }">{{ statistics.examfeeitemCount }}</div>
                  </div> -->
                  <div class="item">
                    <div class="title">体检项目</div>
                    <div class="number" :style="{ '--theme': theme }">{{ statistics.examfeeitemCount }}</div>
                  </div>
                  <div class="item" :style="{ backgroundColor: tczsMismatch && isTczsItem ? 'red' : '#f7f8fa' }">
                    <div class="title" :style="{ color: tczsMismatch && isTczsItem ? 'white' : '#858586' }">套餐项目</div>
                    <div :style="{ color: tczsMismatch && isTczsItem ? 'white' : '#fe6939' }">{{ tczs }}</div>
                  </div>
                  <div class="item">
                    <div class="title">原始单价合计</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.priceTotal).toFixed(2) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">优惠单价</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.factpriceTotal).toFixed(2) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">套餐价格</div>
                    <div class="number" :style="{ '--theme': theme }">{{tcjg}}</div>
                  </div>
                  <div class="item">
                    <div class="title">待收费合计</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.waitprice).toFixed(2) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">实收</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.ss).toFixed(2) }}</div>
                  </div>
                  <div class="item">
                    <div class="title">应收</div>
                    <div class="number" :style="{ '--theme': theme }">{{ Number(statistics.total).toFixed(2) }}</div>
                  </div>
                </div>
              </el-col>
            </el-row>
            <!-- 操作按钮 -->
            <div class="operation" v-if="!isOnline">
              <div style="flex: 1"></div>
              <div
                class="item"
                v-for="(item, index) in operationList"
                :key="index"
                :style="[{ backgroundColor: item.color }, { boxShadow: item.boxShadow }, { opacity: setReadonly.btncon && (index != 3 || setReadonly.fandengji) ? 0.5 : 1 }, { cursor: setReadonly.btncon && (index != 3 || setReadonly.fandengji) ? 'not-allowed' : 'pointer' }]"
                @click="handleOperation(index)"
              >
                <!-- <i class="name" :class="'el-icon-' + item.icon" style="width: 32px; height: 25px; font-size: 30px"></i> -->
                <div class="name" :class="'el-icon-' + item.icon">{{ item.name }}</div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 推项弹窗 -->
    <recommend-dialog ref="recommendDialog" @setRecommend="setRecommend"></recommend-dialog>

    <!-- 团检弹窗 -->
    <group ref="group" @setGroupUnit="setGroupUnit"></group>

    <!-- 重复弹窗 -->
    <repetition ref="repetition" @setLookSimilarPeople="setLookSimilarPeople"></repetition>

    <!-- 打印弹窗 -->
    <el-dialog title="提醒" :visible.sync="showPrintBox" width="340px" append-to-body :close-on-click-modal="false">
      <div style="padding: 10px 20px">需要打印条码和导引单吗？</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fastPrint">速 打</el-button>
        <el-button plain type="primary" @click="print">打 印</el-button>
        <el-button @click="showPrintBox = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 折扣对话框 -->
    <discount ref="discount" @executeDiscount="executeDiscount"></discount>

    <!-- 打印对话框 -->
    <el-dialog title="打印" :visible.sync="openPrint" width="500px" destroy-on-close append-to-body style="overflow: hidden" :close-on-click-modal="false" @close="closePrint">
      <el-form :model="printParams" size="small" label-width="100px">
        <el-form-item label="模式" prop="mode">
          <el-radio-group v-model="printParams.mode" :disabled="setModel">
            <el-radio v-for="item in modelType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="导引单样式" prop="style">
          <el-radio-group v-model="printParams.style" :disabled="setDydStyle">
            <el-radio v-for="item in dydStyleType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-radio-group v-model="printParams.content">
            <el-radio v-for="item in radioType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="printConfirm">确定</el-button>
        <el-button type="" @click="openPrint = false">取消</el-button>
      </div>
      <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
        <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" />
      </object>
    </el-dialog>

    <!-- 选择打印条形码个数 -->
    <el-dialog title="打印条码个数" :visible.sync="barNumOpen" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="editForm" :rules="editRules" :model="editData" size="small" label-width="100px">
        <el-form-item label="条码个数" prop="doctorapply">
          <el-radio v-model="barNum" label="3">3个</el-radio>
          <el-radio v-model="barNum" label="6">6个</el-radio>
          <el-radio v-model="barNum" label="9">9个</el-radio>
          <el-radio v-model="barNum" label="12">12个</el-radio>
          <el-radio v-model="barNum" label="15">15个</el-radio>
          <el-radio v-model="barNum" label="18">18个</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleBarNum">确定</el-button>
        <el-button type="" @click="barNumOpen = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 拍照弹窗 -->
    <photograph ref="photograph" @setPicture="setPicture"></photograph>
    <!-- 预付方式 -->
    <prepayment ref="prepayment" @prepaymentSuccess="prepaymentSuccess"></prepayment>
    <!-- 打印缴费单 -->
    <payment-bills ref="paymentBills"></payment-bills>
    <!-- 速打 -->
    <print-html ref="printHtml"></print-html>
    <!-- 备单未登记列表弹窗 -->
    <order-list ref="orderList" @selectIdCard="selectIdCard" @cancelIdCard="cancelIdCard"></order-list>
    <!-- 完成预约 -->
    <appointment ref="appointment" :memberTypeList="memberTypeList" :fzxOptions="fzxOptions"></appointment>
    <!-- 修改信息 -->
    <el-dialog title="修改信息" :visible.sync="openEidt" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="editForm" :rules="editRules" :model="editData" size="small" label-width="100px">
        <el-form-item label="开单医师" prop="doctorapply">
          <input-select :selectData="doctorEdit" @change="doctorEditChange" :initialValue="editData.doctorapply" :disabled="true"></input-select>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="editData.phone" placeholder="" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input type="textarea" v-model="editData.note" placeholder="" style="width: 300px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveMsg">保存</el-button>
        <el-button type="primary" plain @click="resetMsg">重置</el-button>
        <el-button type="" @click="openEidt = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 同步订单数据 -->
    <el-dialog title="数据异常" :visible.sync="openAbnor" width="500px" append-to-body :close-on-click-modal="false">
      当前体检者的收费项目和绑定的体检套餐里的项目数量不一致，请检查确认体检者数据是否正常
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="completeTheProject">补全套餐项目</el-button>
        <el-button type="primary"  @click="pullOnlineData">拉取线上数据</el-button>
        <el-button type="" @click="openAbnor = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import store from '@/store'
import {
  getRecheckItems,
  getPatientData,
  getDataByIdcard,
  getCustomerData,
  getJzOrg,
  getKindItems,
  setUnRegister,
  middleDbInterface,
  getExamfeeitemData,
  getLqrData,
  savaRegister,
  getTcZhPrice,
  refundValidate,
  getMarriageData,
  getIssueWayData,
  getNationData,
  getAreaData,
  getTjTime,
  returnList,
  saveEdit,
  uploadImage,
  getPicture,
  getPatientcodeByIdcard,
  getVersion,
  completeTheProject,
  pullOnlineData
} from '@/api/reception/registration'
import { getReservation, saOrUp } from '@/api/subscribe/my_appointment/my_appointment'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import { getUserCid } from '@/api/system/branch.js'

import { getListData } from '@/api/subscribe/my_appointment/my_appointment'
import { getPpZxtcData } from '@/api/sale/create_offer'
import { getCultural, getJykId } from '@/utils/dataList.js'
import IDValidator from '@/utils/IDValidator.js'
import GB2260 from '@/utils/GB2260.js'
import sdk from '@/utils/idcard.js'
import Cookies from 'js-cookie'
import recommendDialog from './recommend/index.vue'

import group from './group.vue'
import repetition from './repetition.vue'
import discount from '../proceeds/discount.vue'
import photograph from './photograph.vue'
import prepayment from './prepayment.vue'
import paymentBills from '@/views/preview/payment_bills/index.vue'
//速打
import printHtml from './print.vue'
import { getCookie } from '@/utils/getCookie.js' //获取cookie数据
import defaultPicture from '@/assets/images/registration/default.png'
// 获取默认打印条数
import { barcodePrinter } from '@/api/reception/register_list'

//打印条形码接口及插件
import { printBarCode } from '@/utils/printBarCode.js'
// 备单未登记列表对话框
import orderList from './order_list.vue'
// 完成预约
import appointment from './appointment.vue'

export default {
  name: 'Registration',
  components: {
    recommendDialog,
    group,
    repetition,
    discount,
    photograph,
    prepayment,
    paymentBills,
    printHtml,
    orderList,
    appointment,
  },
  data() {
    // var ondepartvalidation = (rule, value, callback) => {
    //   if (this.setReadonly.orgDepart && this.formData.fusecodehiden == 1 && (this.formData.idExamtype == 1 || this.formData.idExamtype == 2) && !value) {
    //     callback(new Error('部门不能为空'))
    //   } else {
    //     callback()
    //   }
    // }
    var onIDCardsValidation = (rule, value, callback) => {
      if (this.formData.countreportoccupationxml == 1) {
        if (value) {
          // var Validator = new IDValidator(GB2260)
          // if (!Validator.isValid(value)) {
          //   callback(new Error('身份证号输入不合法'))
          // } else {
          // }
          callback()
        } else {
          callback(new Error('证件号不能为空'))
        }
      } else {
        callback()
      }
    }
    var onPostCodeValidation = (rule, value, callback) => {
      if ((value && value.length > 0) || this.formData.idInformway == 3) {
        var re = new RegExp('^[1-9][0-9]{5}$')
        if (!re.test(value)) {
          callback(new Error('格式不正确'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    var onPostValidation = (rule, value, callback) => {
      if (!value && (this.formData.yzbm || this.formData.idInformway == 3)) {
        callback(new Error('邮寄地址不能为空'))
      } else {
        callback()
      }
    }
    var onJzValidation = (rule, value, callback) => {
      if (this.formData.paywayName == '记账' && !value.trim()) {
        callback(new Error('记账人不能为空'))
      } else {
        callback()
      }
    }
    var onTjrValidation = (rule, value, callback) => {
      if (value.length == 0 && this.formData.countreportxml == 1) {
        callback(new Error('请填写原体检者姓名'))
      } else {
        callback()
      }
    }
    var onPhoneValidation = (rule, value, callback) => {
      if (value) {
        if (value[0] != 0 && value.length != 11) {
          callback(new Error('手机号请输入11位！'))
        } else {
          callback()
        }
      } else {
        if (rule.field == 'phone') {
          callback(new Error('请录入手机号码'))
        } else {
          callback()
        }
      }
    }
    var editPhoneValidation = (rule, value, callback) => {
      if (value) {
        if (value[0] != 0 && value.length != 11) {
          callback(new Error('手机号请输入11位！'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    var onJhysValidation = (rule, value, callback) => {
      if (!value && this.formData.idExamtype != '0') {
        callback(new Error('请选择危害因素！'))
      } else {
        callback()
      }
    }
    return {
      // 新增的状态变量
      tczsMismatch: false,
      // 用来标识当前item是套餐项目，可以根据需要调整
      isTczsItem: true,
      // 套餐总数
      tczs: undefined,
      // 只执行一次数据校验
      dataVerif:false,
      // 套餐价格
      tcjg: undefined,
      // 身份证照片
      idCardImg: undefined,
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 父页面传的id
      pid: '',
      // 筛选参数
      queryParams: {
        patientCode: undefined,
      },
      // 左侧参数
      // 表单数据
      formData: {
        hospitalcode:''
      },
      // 体检套餐搜索数据
      tjtcData: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '请输入套餐输入码', // 搜索placeholder
        value: '套餐名称',
        thirdName: '原价', //接口返回值对应第三列的参数名
        fourthName: '优惠价',
        url: '/reception/register/getZxtcsData', //请求连接
        secondName: 'tjtcmc', //接口返回值对应第二列的参数名
        thirdData: 'tcysjg',
        fourthData: 'zhjg',
        params: {
          apprddId: '',
          tjlx: '0',
          jhys: '',
          fzxid: '',
        },
        optionFlag: 'id',
        queryData: 'tjtcsrm',
      },
      // 开单医师
      doctorData: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        third: '部门', //第三列标题（没有传空）
        fourth: '体检中心', //第四列标题（没有传空）
        url: '/member/previewingTrack/getKdys', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
        thirdName: 'dname', //接口返回值对应第三列的参数名
        fourthName: 'cname', //接口返回值对应第四列的参数名
        queryData: 'key',
      },
      // 拍照按钮文字
      capTxt: '拍照',
      // 默认头像
      defaultPicture: defaultPicture,
      // 打印次数
      printCount: 0,
      // 是否执行套餐
      doTc: 0,
      // 表单加载
      mainLoading: true,
      // 分中心列表
      fzxOptions: [],
      // 会员类型
      memberTypeList: [],
      // 籍贯参数
      idResareaData: [],
      idResareaShow: [],
      // 民族参数
      idNationData: [],
      idNationShow: [],
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
      // 危害因素搜索数据
      hazardData: {
        placeholder: '请选择',
        inputTitle: '危害因素输入码', // 搜索标题
        inputPlaceholder: '请输入危害因素名称', // 搜索placeholder
        key: '输入码',
        value: '危害因素名称',
        url: '/abteilung/division/getJhysData', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
      },
      // 工种搜索数据
      workTypeData: {
        placeholder: '请选择',
        inputTitle: '工种名称', // 搜索标题
        inputPlaceholder: '请输入工种名称', // 搜索placeholder
        value: '工种名称',
        url: '/reception/order/getBaseWorktypeSql', //请求连接
        bindValue: '',
        firstName: 'typeName', //接口返回值对应第一列的参数名
        secondName: 'typeName', //接口返回值对应第二列的参数名
        optionFlag: 'id',
      },
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
      // 表格加载中
      reservLoading: false,
      // 表格数据
      reservList: [],
      // 总数
      reservTotal: 0,
      // 当前页码
      reservCurrent: 1,
      // 每页显示
      reservSize: 10,
      // 表单校验
      rules: {
        idPatientclass: [{ required: true, message: '会员类型不能为空', trigger: 'change' }],
        idExamtype: [{ required: true, message: '体检类型不能为空', trigger: 'change' }],
        hospitalcode: [{ required: true, message: '分中心不能为空', trigger: 'change' }],
        patientname: [{ required: true, message: '姓名不能为空', trigger: 'change' }],
        countreportoccupationxml: [{ required: true, message: '证件类型不能为空', trigger: 'change' }],
        idExamclass: [{ required: true, message: '备单分类不能为空', trigger: 'change' }],
        idSex: [{ required: true, message: '性别不能为空', trigger: 'change' }],
        age: [{ required: true, message: '年龄不能为空', trigger: 'change' }],
        idcardno: [
          { required: true, message: '证件号不能为空', trigger: 'change' },
          { validator: onIDCardsValidation, trigger: 'blur' },
        ],
        jhys: [{ validator: onJhysValidation, trigger: 'change' }],
        medicaltype: [{ required: true, message: '体检类别不能为空', trigger: 'change' }],
        worktypeId: [{ required: true, message: '工种不能为空', trigger: 'change' }],
        zgl: [{ required: true, message: '总工龄不能为空', trigger: 'change' }],
        jhgl: [{ required: true, message: '接害工龄不能为空', trigger: 'change' }],
        // orgDepart: [{ validator: ondepartvalidation, trigger: 'blur' }],
        yzbm: [{ validator: onPostCodeValidation, trigger: 'blur' }],
        yjaddress: [{ validator: onPostValidation, trigger: 'blur' }],
        jzdwr: [{ validator: onJzValidation, trigger: 'blur' }],
        tjr: [{ validator: onTjrValidation, trigger: 'blur' }],
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'change' },
          { validator: onPhoneValidation, trigger: 'blur' },
        ],
        examsuiteAlias: [{ validator: onPhoneValidation, trigger: 'blur' }],
      },
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
      // 操作列表
      operationList: [
        { name: '读身份证', color: '#0059FF', boxShadow: '0px 0px 10px rgba(0, 89, 255, 0.5)', icon: 'postcard' },
        { name: '完成登记', color: '#3CD495', boxShadow: '0px 0px 10px rgba(60, 212, 149, 0.5)', icon: 'check' },
        { name: '打印', color: '#FFAF15', boxShadow: '0px 0px 10px rgba(255, 186, 0, 0.5)', icon: 'printer' },
        { name: '反登记', color: '#F56C6C', boxShadow: '0px 0px 10px rgba(255, 146, 146, 0.5)', icon: 'refresh-left' },
        { name: '缴费单', color: '#1890FF', boxShadow: '0px 0px 10px rgba(24, 144, 255, 0.5)', icon: 'printer' },
        { name: '数据重发', color: '#FF7A00', boxShadow: '0px 0px 10px rgba(255, 122, 0, 0.5)', icon: 'refresh' },
        { name: '修改信息', color: '#3D45F3', boxShadow: '0px 0px 10px rgba(61, 69, 243, 0.5)', icon: 'edit-outline' },
      ],
      // 设置数据
      setData: {},
      // 表格加载
      loading: false,
      // 收费项目参数
      itemData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        third: '价格', //第三列标题
        url: '/items/page', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        firstName: 'sfxmsrm',
        secondName: 'examfeeitemName',
        thirdName: 'unitprice', //接口返回值对应第三列的参数名
        queryData: 'sfxmsrm',
      },
      // 加项医生参数
      jxysData: {
        placeholder: '',
        key: '输入码', //第一列标题
        value: '加项医师', //第二列标题
        url: '/reception/register/getJxys', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        firstName: 'inputCode',
        secondName: 'name',
        queryData: 'key',
      },
      // 登记人加载
      remoteLoading: false,
      // 登记人列表
      regOptions: [],
      // 表格数据
      tableList: [],
      // 表格选中的数据
      selectTable: [],
      // 表格删除的数据
      removeList: [],
      // 打印弹窗
      openPrint: false,
      // 打印参数
      printParams: {
        mode: '1',
        style: '1',
        content: '2',
      },
      // 修改弹窗
      openEidt: false,
       // 数据异常弹窗
      openAbnor: false,
      // 修改信息参数
      editData: {
        id: '',
        idOpendoctor: '',
        doctorapply: '',
        phone: '',
        note: '',
      },
      // 修改信息验证
      editRules: {
        phone: [{ validator: editPhoneValidation, trigger: 'blur' }],
      },
      // 修改信息开单医师
      doctorEdit: {
        placeholder: '请选择开单医师',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        third: '部门', //第三列标题（没有传空）
        fourth: '体检中心', //第四列标题（没有传空）
        url: '/member/previewingTrack/getKdys', //请求连接
        bindValue: '',
        selectWidth: 300, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'name', //接口返回值对应第二列的参数名
        thirdName: 'dname', //接口返回值对应第三列的参数名
        fourthName: 'cname', //接口返回值对应第四列的参数名
        queryData: 'key',
      },
      // 预览图片链接
      chargeUrl: '',
      // 是否显示可选组数
      kxzsShow: false,
      // 可选组数
      kxzsValue: 0,
      // 原始价格
      personpricelimit: 0,
      // 版本号
      version: null,
      // 数据验证
      msgContent: '',
      // 显示打印弹窗
      showPrintBox: false,
      // 禁用参数
      setEnabled: {
        idSex: true,
        addItemBtn: true,
        idTjtc: true,
        idPayway: true,
        idExamtype: true,
        dateguidancereturned: true,
      },
      // 只读参数
      setReadonly: {
        orgDepart: false,
        maindiv: false,
        btncon: false,
        fandengji: false,
        tuixiang: false,
      },

      //打印-- 条码/导引单选项
      modelType: [
        //模式
        { id: '0', text: '设计' },
        { id: '1', text: '打印' },
      ],
      dydStyleType: [
        //导引单样式
        { id: '0', text: '简单' },
        { id: '1', text: '复杂' },
        { id: '2', text: '普通' },
      ],
      radioType: [
        //内容
        { id: '0', text: '条码' },
        { id: '1', text: '导引单' },
        { id: '2', text: '条码和导引单' },
      ],
      setModel: false,
      setDydStyle: false,
      // 身份证读卡信息
      idCardInfo: {},
      // 上传加载中
      uploadLoading: undefined,
      // 清空时不清除表格内容
      isClear: false,
      // 组任选时记录初始套餐优惠价格
      totalMoney: 0,
      // 选择条码个数弹窗
      barNumOpen: false,
      // 条码个数
      barNum: '9',
      // 是否为线上登记
      isOnline: false,
      // 当前体检信息是否为补检
      isBuJian: false,
      // 保存防抖
      saveDebounce: false,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    watchContent() {
      return this.printParams.content
    },
  },
  watch: {
    watchContent: {
      handler(newVal, oldVal) {
        if (newVal == '0') {
          this.setModel = false
          this.setDydStyle = true
        } else if (newVal == '1') {
          this.setModel = false
          this.setDydStyle = false
        } else if (newVal == '2') {
          this.setModel = true
          this.setDydStyle = false
        }
      },
      immediate: true,
    },
  },
  created() {
    if (this.$getCookie('cid') == '2') {
      this.barNum = '12'
    }
    if (this.$getCookie('isOnline') == '1') {
      this.isOnline = true
    }

    // this.isOnline = false

    this.culturalList = getCultural()
    this.returnList()
    this.$getLevelList().then((res) => {
      res.data.forEach((el) => {
        el.levelId = el.levelId.toString()
      })
      this.memberTypeList = res.data
      if (!this.$route.params.patientcode) this.formData.idPatientclass = this.formData.idPatientclass ? this.formData.idPatientclass : this.memberTypeList[0].levelId
    })
    this.getMarriageData()
    this.getIssueWayData()
    this.getNationData()
    this.getAreaData()
    // 获取分中心列表
    getUserCid().then(({ data }) => {
      this.fzxOptions = data
    })
    // this.getReservationUser()
    this.getVBookData()
    if (this.$route.params.patientcode) {
      this.queryParams.patientCode = this.$route.params.patientcode
      this.pid = this.$route.params.id
      this.onSearchCodeEnter()
    } else {
      this.mainLoading = false
      this.loading = false
      this.reset()
    }

  },
  mounted() {
    this.keyDown()
  },
  activated() {
    if (this.formData.patientcode) {
      this.queryParams.patientCode = this.formData.patientcode
      getVersion({
        patientcode: this.formData.patientcode,
      }).then((res) => {
        if (res.data != this.version) {
          this.onSearchCodeEnter()
        }
      })
    }
  },
  methods: {
    // 选择最小体检套餐赋值分中心ID
    fzxidChange(value){
      this.tjtcData.params.fzxid = value
    },
    // 拉取线上数据
    pullOnlineData(){
      let dataParams = {
        patientcode: this.queryParams.patientCode
      }
      pullOnlineData(dataParams).then((res) => {
          if(res.code == 200){
            // 重新查询数据
            if(this.$route.params.patientcode){
              this.queryParams.patientCode = this.$route.params.patientcode
            }
            this.pid = this.$route.params.id
            this.onSearchCodeEnter()
            this.$modal.msgSuccess('线上数据拉取成功')
            this.openAbnor = false
          }else{
            this.$modal.msgWarning('线上数据拉取失败')
            this.openAbnor = false
          }
      })
    },
    // 补全套餐项目
    completeTheProject(){
      let dataParams = {
        patientcode: this.queryParams.patientCode
      }
      completeTheProject(dataParams).then((res) => {
          if(res.code == 200){
            // 重新查询数据
            if(this.$route.params.patientcode){
              this.queryParams.patientCode = this.$route.params.patientcode
            }
            this.pid = this.$route.params.id
            this.onSearchCodeEnter()
            this.$modal.msgSuccess('补全套餐项目成功')
            this.openAbnor = false
          }else{
            this.$modal.msgWarning('补全套餐项目失败')
            this.openAbnor = false
          }
      })
    },
    // 对比数据
    comparatData(){
      // 登记页面那个弹窗，体检项目数量和套餐价格不一致并且价格和套餐价格都不一致才弹出，
      // 个检不弹，已登记不弹，登记页面只弹一次（除非刷新）

      if(this.tczs && this.tcjg && !this.dataVerif && this.formData.fusecodehiden == 1){
          this.dataVerif = true

          if (Number(this.statistics.examfeeitemCount) != Number(this.tczs) && Number(this.statistics.tcprice) != Number(this.tcjg)) {
            this.openAbnor = true;
            this.tczsMismatch = true;
          } else {
            this.tczsMismatch = false;
          }

          // if (this.statistics.examfeeitemCount != this.tczs && this.statistics.tcprice != this.tcjg) {
          //   this.openAbnor = true
          //   this.tczsMismatch = true;
          // } else {
          //   this.tczsMismatch = false;
          // }
      }
    },
    // 查询
    handleResearch() {
      if (!this.queryParams.patientCode) {
        this.$alert('请输入体检号', '提示')
      } else {
        this.onSearchCodeEnter()
      }
    },
    // 刷新
    handleRefresh() {
      this.$tab.refreshPage()
    },
    handleCardChange() {
      if (this.formData.countreportoccupationxml != 1) {
        this.formData.idcardno = Date.now()
      }
    },
    // 体检号搜索
    onSearchCodeEnter(e, callback, isOrder) {
      var patientCode = this.queryParams.patientCode
      if (patientCode == '') {
        this.toggleTjr()
        this.setEnabled.addItemBtn = true
        return
      }
      this.mainLoading = true
      this.loading = true
      this.isBuJian = false
      getRecheckItems({ patientCode: patientCode, autoFill: true, key: '1' })
        .then((res) => {
          var data = res.data
          if (!data.success) {
            //被禁检
            if (data.limit && data.limit == 1) {
              this.getPatientDataForBd(data.patientData.patientcode, () => {
                if (callback) {
                  callback()
                }
                this.limit(true)
              })
            }
            this.$modal.alertWarning(data.msg)
            return
          } else if (data.location && data.location == 1) {
            //peispatientHistory 不存在此可能
            this.limit(true)
            this.getPatientDataForBd(data.patientData.patientcode, callback)
            return
          }
          patientCode = data.patientData.patientcode
          this.queryParams.patientCode = patientCode
          if (data.examfeeitemData == 1) {
            //补检或复查
            this.version = null
            if (data.patientData.numorgresv == -1) data.patientData.numorgresv = ''
            data.patientData.workType = data.workType
            if (data.patientData.fusecodehiden == 0 && !data.patientData.idInformway) {
              data.patientData.idInformway = '402881ae57e49a750157e49ce0e30008'
            }
            this.formData = data.patientData
            this.mainLoading = false
            this.loading = false
            // 订单ID
            this.formData.ddh = data.vation == null ? '' : data.vation.ddh
            // 团体分组
            this.formData.orgreservationgroupname = data.group == null ? '' : data.group.orgreservationgroupname
            this.formData.idOrgreservationgroup = data.patientData == null ? '' : data.patientData.idOrgreservationgroup
            this.formData.prepayment = 0
            // var row = {
            //   tcprice: 0
            // };
            // this.statistics = row
            this.statistics.tcprice = 0
            // 日期格式化
            this.dateFormat(data)
            this.formData.medicaldate = this.getToday() + ' 00:00:00'
            // this.formData.dateguidancereturned = this.getTomorrow()
            this.formData.dateguidancereturned = ''
            this.formData.idTjtc = ''
            this.formData.tjtcmc = ''
            this.formData.patientcode = ''
            this.formData.id = ''
            this.formData.moneyamount = 0
            this.formData.moneyamountpaid = 0
            this.formData.tMoney = 0
            this.formData.moreChangeMon = 0
            this.formData.fisforreserve = 0
            this.formData.fregistered = 0
            this.formData.dateregister = ''
            this.formData.idFeetype = ''
            this.formData.patientnamepinyin = null
            this.controlEnabled(true)
            if (data.type == 0) {
              this.$modal
                .confirm('该体检者存在复查项目，是否要展现复查项目？', '提醒')
                .then(() => {
                  var inpatientno = data.patientData.inpatientno ? data.patientData.inpatientno : patientCode
                  this.setEnabled.addItemBtn = true
                  getKindItems({ patientCode: patientCode, type: 0, inpatientno: inpatientno }).then((res) => {
                    var data1 = res.data
                    // 获取已关联的收费项目
                    var result = data1.examfeeitemData
                    if (result && result.length) {
                      result.forEach((el) => {
                        el.idPayway = el.idPayway ? el.idPayway.toString() : ''
                      })
                    }
                    this.tableList = result
                    this.formData.idExamtype = 3
                    //重置照片
                    // if (cameraObject) {
                    //   cameraObject.parentNode.removeChild(cameraObject);
                    // }
                    // this.captureInit();
                    // cameraObject.style.display = "block";
                    // pic.style.display = "none";
                    this.capTxt = '拍照'
                    this.$set(this.formData, 'picture', data1.picture ? data1.picture : this.defaultPicture)
                    this.formData.harmName = data.jhysNames
                    this.formData.typeName = data.workType
                    this.$nextTick(() => {
                      if (this.formData.jhys && this.formData.idExamtype != 0) {
                        var jhysList = this.formData.jhys.split(',')
                        var harmNameList = this.formData.harmName.split(',')
                        var jhysData = []
                        jhysList.forEach((el, i) => {
                          jhysData.push({
                            id: el,
                            label: harmNameList[i],
                          })
                        })
                        this.$refs.searchJhys.initData(jhysData)
                      }
                      if (this.formData.idExamtype != 0) {
                        this.$refs.workType.initData(this.formData.trades)
                      }
                      this.formData.idExamtype = '3'
                    })
                    this.formData.inpatientno = inpatientno
                    this.toggleTjr()
                    this.formData.guidancenote = data1.note
                    if (isOrder) {
                      if (this.idCardImg) {
                        // 读身份证赋值
                        this.handleNoOrder(this.idCardInfo)
                        this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + this.idCardImg)
                        this.idCardImg = undefined
                      }
                    }
                    return
                  })
                  return
                })
                .catch(() => {
                  // 加载体检者信息
                  this.getPatientDataForBd(patientCode, false)
                })
            } else if (data.type == 1) {
              this.$modal
                .confirm('该体检者存在补检项目，是否要展现补检项目？', '提醒')
                .then(() => {
                  // 存在补检收费项目
                  getKindItems({ patientCode: patientCode, type: 1 }).then((res) => {
                    var data1 = res.data
                    if (data1.noman == 0) {
                      this.$modal.alertWarning('该体检者不存在', '提醒')
                      return
                    }
                    this.isBuJian = true
                    this.formData.idOpendoctor = data1.bjid
                    this.formData.doctorapply = data1.bjname
                    this.formData.guidancenote2 = this.formData.idOpendoctor
                    this.formData.insuranceno = this.formData.insuranceno + ',' + this.formData.idOpendoctor
                    // 获取已关联的收费项目
                    var result = data1.examfeeitemData
                    if (result && result.length) {
                      result.forEach((el) => {
                        el.idPayway = el.idPayway ? el.idPayway.toString() : ''
                        el.factprice = 0
                      })
                    }
                    this.tableList = result
                    this.formData.typeName = data.workType
                    //重置照片
                    // if (cameraObject) {
                    //   cameraObject.parentNode.removeChild(cameraObject);
                    // }
                    // this.captureInit();
                    // cameraObject.style.display = "block";
                    // pic.style.display = "none";
                    this.capTxt = '拍照'
                    this.$set(this.formData, 'picture', data1.picture ? data1.picture : this.defaultPicture)
                    // 关联上次的体检号
                    this.formData.insuranceno = patientCode
                    this.toggleTjr()
                    this.setEnabled.addItemBtn = true
                    if (isOrder) {
                      if (this.idCardImg) {
                        this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + this.idCardImg)
                        this.idCardImg = undefined
                      }
                    }
                    return
                  })
                })
                .catch(() => {
                  // 加载体检者信息
                  this.getPatientDataForBd(patientCode, false)
                })
            } else if (data.type == 2) {
              //既有复查又有补查
              this.$modal
                .confirm('该体检者同时存在复查项目和补查项目，是否要展现？', '提醒')
                .then(() => {
                  var inpatientno = data.patientData.inpatientno ? data.patientData.inpatientno : patientCode
                  this.setEnabled.addItemBtn = true
                  getKindItems({ patientCode: patientCode, type: 2, inpatientno: inpatientno }).then((res) => {
                    var data1 = res.data
                    // 获取已关联的收费项目
                    var result = data1.examfeeitemData
                    if (result && result.length) {
                      result.forEach((el) => {
                        el.idPayway = el.idPayway ? el.idPayway.toString() : ''
                      })
                    }
                    this.tableList = result
                    this.formData.idExamtype = 2
                    //重置照片
                    // if (cameraObject) {
                    //   cameraObject.parentNode.removeChild(cameraObject);
                    // }
                    // this.captureInit();
                    // cameraObject.style.display = "block";
                    // pic.style.display = "none";
                    this.capTxt = '拍照'
                    this.$set(this.formData, 'picture', data1.picture ? data1.picture : this.defaultPicture)
                    this.formData.harmName = data.jhysNames
                    this.formData.typeName = data.workType
                    this.$nextTick(() => {
                      if (this.formData.jhys && this.formData.idExamtype != 0) {
                        var jhysList = this.formData.jhys.split(',')
                        var harmNameList = this.formData.harmName.split(',')
                        var jhysData = []
                        jhysList.forEach((el, i) => {
                          jhysData.push({
                            id: el,
                            label: harmNameList[i],
                          })
                        })
                        this.$refs.searchJhys.initData(jhysData)
                      }
                      if (this.formData.idExamtype != 0) {
                        this.$refs.workType.initData(this.formData.trades)
                      }
                      this.formData.idExamtype = '3'
                    })
                    this.toggleTjr()
                    this.formData.guidancenote = data1.note
                    this.formData.insuranceno = patientCode
                    this.formData.inpatientno = inpatientno
                    if (isOrder) {
                      if (this.idCardImg) {
                        this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + this.idCardImg)
                        this.idCardImg = undefined
                      }
                    }
                  })
                })
                .catch(() => {
                  // 加载体检者信息
                  this.getPatientDataForBd(patientCode, false)
                })
            } else if (data.type == 3) {
              this.$modal
                .confirm('存在复查，是否要展现？', '提醒')
                .then(() => {
                  this.getPatientDataForBd(data.fccode, false, isOrder, true)
                })
                .catch(() => {
                  // 加载体检者信息
                  this.getPatientDataForBd(patientCode, callback, isOrder)
                })
            }
          } else {
            // 加载体检者信息
            this.getPatientDataForBd(patientCode, callback, isOrder)
          }
        })
        .catch((error) => {
          console.error(error)
          this.mainLoading = false
          this.loading = false
        })
    },
    // 备单人员进行登记操作、登记信息查询
    getPatientDataForBd(patientcode, callback, isOrder, isFC) {
      var pid = this.pid
      if (patientcode == '') {
        this.version = null
        // if (r.status == "fail") {
        //   cameraObject.style.display = "none";
        //   pic.style.display = "block";
        // }
        return
      }
      this.capTxt = '重拍'
      // cameraObject.style.display = "none";
      // pic.style.display = "block";
      this.doTc = 1
      getPatientData({ patientCode: patientcode, id: pid }).then((res) => {
        var data = res.data
        // 套餐总数赋值
        this.tczs = data.tczs
        this.tcjg = data.tcjg

        setTimeout(() => {
          // 校验数值
          this.comparatData()
        }, 2000);


        if (data == '') {
          return
        }
        if (!data.success) {
          // 重新加载后session值不会清除
          if (data.type) {
            //直接刷新页面
            this.onClear()
            return
          }
          this.$modal.alertWarning(data.error)
          return
        }
        if (!data.patientData.dateregister && data.patientData.idOrg) {
          data.examfeeitemData.forEach((el) => {
            if (el.idPayway === null || el.idPayway === '' || el.idPayway === undefined) {
              if (data.patientData.idOrg) {
                el.idPayway = '5'
              } else {
                el.idPayway = '1'
              }
            }
          })
        }
        this.version = data.version
        if (data.patientData.fusecodehiden == 0 && !data.patientData.idInformway) {
          data.patientData.idInformway = '402881ae57e49a750157e49ce0e30008'
        }
        this.formData = data.patientData
        if (!this.formData.phone) {
          this.$nextTick(() => {
            this.$refs.phone.focus()
          })
        }
        this.mainLoading = false
        // 已归档人员信息不可编辑
        if (data.location == 1) {
          this.$modal.alertWarning('该体检者已经归档，不可操作')
          this.limit(true)
        } else if (data.FPaused == 1) {
          this.$modal.alertWarning('该体检者已经被禁检，不可操作')
          this.limit(true)
        } else if (data.limit && data.limit == 1) {
          this.$modal.alertWarning('该体检者所在分组已经被禁检，不可操作')
          this.limit(true)
        } else this.limit(false)
        this.$nextTick(() => {
          this.formData.tjtcmc = data.patientData.examsuiteName
        })
        this.formData.idPayway = data.patientData.idPayway
        this.formData.payway = data.patientData.payway
        // 黑名单
        this.onHmdCheckedChanged(null)
        this.$set(this.formData, 'picture', data.picture)
        if (isOrder) {
          if (this.idCardImg) {
            this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + this.idCardImg)
            this.idCardImg = undefined
          }
        }
        if (isFC) {
          this.formData.idExamtype = '3'
        }
        // 打印次数
        this.printCount = data.patientData.tmyd
        if (!this.formData.countreportoccupationxml) {
          this.formData.countreportoccupationxml = 1
        }
        this.formData.tMoney = data.tMoney
        this.formData.harmName = data.jhysn
        this.formData.typeName = data.workType
        if (this.formData.medicaltype == 'null') {
          this.formData.medicaltype = ''
        }
        if (!this.formData.hospitalcode && this.isOnline == false) {
          this.formData.hospitalcode = this.$getCookie('cid')
        }
        this.removeList = []
        this.$nextTick(() => {
          if (this.formData.jhys && this.formData.idExamtype != 0) {
            var jhysList = this.formData.jhys.split(',')
            var harmNameList = this.formData.harmName.split(',')
            var jhysData = []
            jhysList.forEach((el, i) => {
              jhysData.push({
                id: el,
                label: harmNameList[i],
              })
            })
            this.$refs.searchJhys.initData(jhysData)
          }
          this.doctorData.bindValue = data.sellname
          if (this.formData.worktypeId && this.formData.idExamtype != 0) {
            this.$refs.workType.initData(this.formData.trades)
          }
        })
        // 订单ID
        this.formData.ddh = data.idOrder
        // 团体分组
        this.formData.orgreservationgroupname = data.group == null ? '' : data.group.orgreservationgroupname
        this.formData.idOrgreservationgroup = data.patientData == null ? '' : data.patientData.idOrgreservationgroup
        // 开单医师
        this.formData.doctorapply = data.sellname
        // 获取体检套餐
        if (data.patientData && data.patientData.patientnamepinyin) {
          this.getPackageData(data, 2)
        } else {
          this.getPackageData(data, 2)
        }
        // 控件是否可以操作
        if (data.idOrder != '') {
          this.setReadonly.orgDepart = true
        } else {
          this.setReadonly.orgDepart = false
        }
        // 日期格式化
        this.dateFormat(data)
        // 计算最终价格
        this.getTcPrice()
        // 用于页面数据验证
        if (this.formData.idTjtc != '') {
          this.onTcSearchClick()
        }
        // 登记禁用套餐
        if (data.patientData.fregistered == 1) {
          this.controlEnabled(false)
        } else {
          this.controlEnabled(true)
          this.formData.medicaldate = this.getToday() + ' 00:00:00'
        }
        //防止没有选中团检、个检
        if (data.patientData.fusecodehiden === null) {
          if (data.patientData.idOrg) {
            this.formData.fusecodehiden = 1
          } else {
            this.formData.fusecodehiden = 0
          }
        }
        this.formData.numorgresv = data.patientData.numorgresv == -1 ? '' : data.patientData.numorgresv

        this.toggleTjr()
        if (callback) {
          callback()
        }
      })
    },
    // 按钮不可用
    limit(ty) {
      this.setReadonly.maindiv = ty
      this.setReadonly.btncon = ty
      //存在登记后禁检,需要反登记
      this.setReadonly.fandengji = false
      this.setReadonly.tuixiang = false
    },
    // 设置原名
    toggleTjr() {
      if (this.formData.countreportxml != 1) {
        this.formData.tjr = null
      }
      this.toggleDoctor()
    },
    //开单医生跨月变灰不可修改
    toggleDoctor() {
      var dateregister = this.formData.dateregister //日期类型
      if (dateregister) {
        var now = new Date()
        var pastTime = now.getTime() - new Date(dateregister).getTime()
        if (pastTime > 1000 * 60 * 60 * 24 * 31) {
          this.setEnabled.idOpendoctor = false
          return
        }
      }
      this.setEnabled.idOpendoctor = true
    },
    // 页面日期赋值
    dateFormat(data) {
      // 预约时间判断
      // if (!data.patientData.dateguidancereturned) this.formData.dateguidancereturned = this.getTomorrow()
      if (!data.patientData.dateguidancereturned) this.formData.dateguidancereturned = ''
      else this.formData.dateguidancereturned = data.patientData.dateguidancereturned
      this.formData.birthdate = data.patientData.birthdate ? data.patientData.birthdate.split(' ')[0] : ''
      this.formData.workDate = data.patientData.workDate ? data.patientData.workDate.split(' ')[0] : ''
      this.formData.harmDate = data.patientData.harmDate ? data.patientData.harmDate.split(' ')[0] : ''
    },
    // 登记是否禁用控件
    controlEnabled(enabled) {
      this.setEnabled.idTjtc = enabled
      this.setEnabled.idPayway = enabled
      this.setEnabled.idExamtype = enabled
      this.setEnabled.dateguidancereturned = enabled
    },
    // 体检套餐折后价格
    getTcPrice() {
      var id = this.formData.idTjtc
      if (id == null || id == '' || id == undefined) {
        this.editChange()
      } else {
        // 套餐优惠价格
        getTcZhPrice({ tcid: id }).then((res) => {
          var data = res.data
          if (data != '') {
            // 更新数据
            // var row = {
            //   tcprice: data.zhjg == null ? 0 : data.zhjg
            // };
            // this.statistics = row
            this.statistics.tcprice = data.zhjg == null ? 0 : data.zhjg
            this.editChange()
          }
        })
      }
    },
    // 设置表格行颜色
    rowClassName({ row }) {
      if (!row.examfeeitemName) return ''
      var sex = row.fFeechargedinttrans || row.FFeechargedinttrans || row.ffeechargedinttrans || row.xb || 0
      if (sex != 2) {
        if (sex != this.formData.idSex) {
          // 红色
          return 'sexchange_row'
        }
      }
      // 备选状态改变背景
      if (row.isbx == 1) {
        if (row.groupType == 1) {
          // 黄色
          return 'myrow'
        } else if (row.groupType == 2) {
          // 绿色
          return 'myrow3'
        } else {
          // 蓝色
          return 'myrow2'
        }
      }
      return ''
    },
    // 设置表格行颜色
    rowResergrid({ row }) {
      if (row.dateguidancereturned) {
        var now = new Date()
        var dateguidancereturned = new Date()
        dateguidancereturned.setTime(row.dateguidancereturned)
        if (now.getFullYear() == dateguidancereturned.getFullYear() && now.getMonth() == dateguidancereturned.getMonth() && now.getDate() == dateguidancereturned.getDate()) {
          return 'myrow4'
        }
      }
      return ''
    },
    // 刷新
    reload() {
      this.getPatientDataForBd(this.formData.patientcode)
    },
    // 清空
    onClearCopy() {
      // 判断体检号是否存在
      // if (this.formData.patientcode) {
      this.isBuJian = false
      this.isClear = true
      this.controlEnabled(true)
      this.version = null
      this.reset(1)
      this.setEnabled.addItemBtn = true
      this.queryParams.patientCode = ''
      this.toggleTjr()
      this.loading = false
      this.mainLoading = false
      // }
    },
    // 推项
    handleRecommend() {
      var data = {
        tjValue: this.formData.idExamtype,
        syxbValue: this.formData.idSex,
        id: this.formData.id,
        pId: this.pid,
      }

      this.$refs.recommendDialog.showDialog(data)
    },
    // 推荐
    setRecommend(data) {
      var r = []
      var itemGrid = this.tableList
      var existsItems = []
      for (var i = 0, l = data.length; i < l; i++) {
        let index = itemGrid.findIndex((row) => {
          if (row.idExamfeeitem == data[i].id) {
            return true
          }
        })
        if (index == -1) {
          var newRow = {
            idExamfeeitem: data[i].id,
            examfeeitemName: data[i].sfxmmc,
            fFeechargedinttrans: data[i].xb,
            count: 1,
            sfjx: 0,
            changeItem: 0,
            FMarkFeereturn: 0,
            isMintc: 0,
            idPayway: '1',
          }
          newRow.price = data[i].jg
          newRow.factprice = data[i].jg
          newRow.idKs = data[i].idKs
          r.push(newRow)
        } else {
          existsItems.push(data[i].sfxmmc)
        }
      }
      this.tableList = [...this.tableList, ...r]
      this.editChange()
      if (existsItems.length > 0) {
        this.$modal.alertWarning('收费项目：' + existsItems.join('、') + '已存在', '提醒')
      }
    },
    // 页面快捷键监听
    keyDown() {
      document.onkeydown = (e) => {
        // 兼容FF和IE和Opera
        var event = e || window.event
        var code = event.keyCode || event.which || event.charCode
        // Shift　+　Enter
        if (event.shiftKey && code == 13) {
          // 阻止浏览器自带快捷键设置
          e.preventDefault()
          e.returnValue = false
          // 收费项目增加
          this.addOneRow()
        }
        if (code == 120) {
          this.beforeReg(1, 1, 0)
        }
        if (code == 13 && this.openPrint) {
          this.printConfirm()
        }
      }
    },
    // 团检
    onGroupUnit() {
      if (this.formData.fregistered == 1) {
        this.$modal.alertWarning('当前体检者已登记，不能执行此操作！')
        return
      }
      this.$refs.group.showDialog()
    },
    // 团检弹窗返回函数
    setGroupUnit(data) {
      // 团体名称、团体分组赋值
      // this.formData.idOpendoctor = data.xsjl
      this.formData.idInformway = data.idInformway
      this.formData.idOrg = data.khdwmcid
      this.formData.orgName = data.orgName
      this.formData.fusecodehiden = 1
      this.formData.idOpendoctor = data.saleId

      this.formData.doctorapply = data.name
      this.doctorData.bindValue = data.name

      this.formData.numorgresv = data.num == -1 ? '' : data.num
      this.formData.idOrgreservationgroup = data.groupid
      this.formData.orgreservationgroupname = data.groupname
      this.setReadonly.orgDepart = true
      this.formData.idExamtype = data.idExamtype
      this.onTypeChanged(this.formData.idExamtype)
      this.formData.idExamclass = data.idExamclass
      this.$set(this.formData, 'jhys', data.jhys)
      this.formData.harmName = data.jhysn
      this.$nextTick(() => {
        if (this.formData.jhys && this.formData.idExamtype != 0) {
          var jhysList = this.formData.jhys.split(',')
          var harmNameList = this.formData.harmName.split(',')
          var jhysData = []
          jhysList.forEach((el, i) => {
            jhysData.push({
              id: el,
              label: harmNameList[i],
            })
          })
          this.$refs.searchJhys.initData(jhysData)
        }
      })
      this.formData.idTjtc = data.tcid
      this.formData.tjtcmc = data.tjtcmc
      // 订单id
      this.formData.ddh = data.orderId
      // 获取套餐
      this.getPackageData(data.tcid, 1)
    },
    // 重复按钮
    onLookSimilarPeople() {
      if (this.formData.fregistered == 1) {
        this.$modal.alertWarning('当前体检者已登记，不能执行此操作！')
        return
      }
      this.$refs.repetition.showDialog()
    },
    // 重复弹窗返回函数
    setLookSimilarPeople(data) {
      if (!this.formData.patientcode) {
        this.formData.patientcode = ''
        this.formData.id = ''
        this.formData.fisforreserve = 0
        this.formData.fregistered = 0
        this.formData.dateregister = ''
        this.formData.moneyamount = 0
        this.formData.moneyamountpaid = 0
        this.formData.tMoney = 0
        this.formData.moreChangeMon = 0
      }
      if (data) {
        // autoFill 是否需要补全体检号：0.否 1.是 type传0
        getCustomerData({ patientCode: data.patientcode, autoFill: 0, type: 0 }).then((res) => {
          var data = res.data
          this.tableList = []
          var newdata = data.examfeeitemData
          let array = []
          for (var i = 0, l = newdata.length; i < l; i++) {
            array.push({
              idPayway: '1',
              examfeeitemName: newdata[i].examfeeitemName,
              idExamfeeitem: newdata[i].idExamfeeitem,
              price: newdata[i].price,
              factprice: newdata[i].factprice,
              idKs: newdata[i].idKs,
              ksName: newdata[i].ksmc,
              FFeechargedinttrans: newdata[i].ffeechargedinttrans,
              sfjx: 0,
              count: 1,
              changeItem: 0,
              FMarkFeereturn: 0,
              isMintc: 0,
              qty: this.getNewQty(this.tableList),
            })
          }
          this.tableList = array
          // this.onClearCopy()
          this.editChange()
          this.onSexChanged()
        })
      }
    },
    // 速打
    handlePrint() {
      this.$nextTick(() => {
        if (!this.formData.id) {
          this.$modal.msgWarning('体检者不能为空！')
          return
        }
        let data = {
          id: getCookie('cid'), //分中心id
          ids: this.formData.patientcode, //体检者ids
          model: 1, //模板id
          dydStyle: 1, //导引单类型 0 简单 1 复杂 2 普通
          radio: 2, //内容
        }
        const query = {
          id: getCookie('cid'), //分中心id
          ids: [this.formData.id], //体检者ids
          model: 1, //模板id
        }
        // 获取默认打印个数
        let defaultNumber = 0
        barcodePrinter().then(({ data }) => {
            if(data.receptionNum){
              defaultNumber = data.receptionNum
            }else{
              defaultNumber = 9
            }
        })
        this.$refs.printHtml.getReport(data, query, defaultNumber)
      })
    },
    // 完成预约
    handleAppointment() {
      let error = ''
      if (!this.formData.patientcode) {
        error = '体检号不能为空'
      } else if (this.formData.fregistered != 0) {
        error = '当前体检号已登记,不能完成预约'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      let info = {
        levelId: this.formData.idPatientclass,
        levelName: '',
        realName: this.formData.patientname,
        sex: this.formData.idSex,
        idcard: this.formData.idcardno,
        mobile: this.formData.phone,
        remark: '',
        reservationDates: this.formData.dateguidancereturned || this.getTomorrow(),
        branchId: this.formData.hospitalcode,
        patientcode: this.formData.patientcode,
        status: 1,
        bizOrderNum: this.formData.numorgresv,
        idOrg: this.formData.idOrg,
        fUsecodehiden: this.formData.fusecodehiden,
        creator: this.$getCookie('userNo'),
      }

      this.memberTypeList.forEach((el) => {
        if (el.levelId == this.formData.idPatientclass) {
          info.levelName = el.levelName
        }
      })
      this.$refs.appointment.showDialog(info)
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
    // 套餐查询
    onTcSearchClick(fn) {
      this.$refs.searchTjtc.getData(fn)
    },
    // 搜索框清除 1套餐，2危害因素
    onClearClick(type) {
      if (type == 1) {
        this.formData.idTjtc = ''
        this.formData.tjtcmc = ''
        if (!this.isOnline) {
          this.$refs.searchTjtc.initData()
        }
      } else {
        this.$set(this.formData, 'jhys', '')
        this.formData.harmName = ''
        this.$refs.searchJhys.initData()
      }
      this.setEnabled.idSex = true
    },
    // 获取设置数据
    returnList() {
      returnList().then((res) => {
        this.setData = res.data
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
        this.formData.nation = this.formData.nation ? this.formData.nation : this.idNationData[0].name
        this.idNationShow = this.idNationData
      })
    },
    // 获取籍贯
    getAreaData() {
      getAreaData({ current: 1, size: 9999 }).then((res) => {
        this.idResareaData = res.data.records
        this.formData.idResarea = this.formData.idResarea ? this.formData.idResarea : this.getDefaultArea().id
        this.formData.resarea = this.formData.resarea ? this.formData.resarea : this.getDefaultArea().resarea
        this.idResareaShow = this.idResareaData
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
    // // 获取已预约客户
    // getReservationUser() {
    //   var query = {
    //     isReg: 1,
    //     current: 1,
    //     size: 10,
    //   }
    //   this.reservLoading = true
    //   getReservationUser(query).then((res) => {

    //   }).catch(error=>{
    //     this.reservLoading = false
    //   })
    // },
    // 获取已预约客户
    getVBookData() {
      let date = new Date()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let todate = date.getDate()
      var query = {
        startTime: year + '-' + month + '-' + todate + ' 00:00:00',
        endTime: year + '-' + month + '-' + todate + ' 23:59:59',
        current: this.reservCurrent,
        size: 10,
      }
      this.reservLoading = true
      getListData(query)
        .then((res) => {
          this.reservList = res.data.records
          this.reservTotal = res.data.total
          this.reservLoading = false
        })
        .catch((error) => {
          this.reservLoading = false
        })
    },
    // 危害因素选择
    hazardChange(value) {
      this.$set(this.formData, 'jhys', '')
      this.formData.harmName = ''
      this.hazardData.bindValue = value
      if (value.length == 0) this.onClearClick(2)
      value.forEach((el) => {
        if (!this.formData.harmName) {
          this.$set(this.formData, 'jhys', el.id)
          this.formData.harmName = el.label
        } else {
          this.formData.jhys += ',' + el.id
          this.formData.harmName += ',' + el.label
        }
      })
      this.tjtcData.params.jhys = this.formData.jhys
    },
    // 获取工种
    workTypeChange(value) {
      this.formData.worktypeId = value.id
      this.formData.typeName = value.typeName
      this.formData.trades = value.typeName
      this.workTypeData.bindValue = value.typeName
    },
    // 切换
    toSwitch(id) {
      this.formData.fusecodehiden = 0
      this.$nextTick(function () {
        this.formData.fusecodehiden = id
      })
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
    reset(skip) {
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
        // dateguidancereturned: this.getTomorrow(),
        dateguidancereturned: '',
        dateregister: '',
        medicaldate: this.getToday() + ' 00:00:00',
        picture: this.defaultPicture,
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
        hospitalcode: this.$getCookie('cid'),
      }
      this.tjtcData.params = {
        apprddId: '',
        tjlx: '0',
        jhys: '',
      }
      this.$nextTick(() => {
        if (!this.isOnline) {
          this.$refs.searchTjtc.initData()
        }
        this.hazardData.bindValue = []
        this.doctorData.bindValue = ''
        this.onTypeChanged(this.formData.idExamtype)
        this.onSexChanged()
      })
      this.resetForm('mainForm')
    },
    // 体检类型改变时方法
    onTypeChanged(value) {
      this.tjtcData.params.tjlx = value
      this.formData.idTjtc = ''
      this.formData.tjtcmc = ''
      // 切换收费项目删除
      if (value == 0) {
        this.formData.workno = ''
        this.formData.worktypeId = ''
        this.formData.workDate = null
        this.formData.harmDate = null
        this.formData.zgl = 0
        this.formData.jhgl = 0
        this.$set(this.formData, 'jhys', null)
        this.$set(this.formData, 'medicaltype', null)
        this.formData.harmName = undefined
        this.$refs.mainForm.clearValidate('phone')
      }
      var gData = this.tableList
      if (gData.length > 0) {
        if (!this.isClear) {
          this.selectTable = []
          this.tableList = []
        } else {
          let tableList = []
          this.tableList.forEach((el) => {
            if (el.id) {
              tableList.push({
                FDelayed: 0,
                FExaminated: 0,
                FFeecharged: 0,
                FFeechargedinttrans: 2,
                FGiveup: 0,
                FLabsendtolis: 0,
                FMarkFeereturn: 0,
                FRegistered: 0,
                changeItem: 0,
                count: 1,
                createdate: '',
                examfeeitemName: el.examfeeitemName,
                factprice: el.factprice,
                idExamfeeitem: el.idExamfeeitem,
                idKs: el.idKs,
                idPayway: '1',
                isMintc: el.isMintc,
                ksName: el.ksName,
                price: el.price,
                qty: el.qty,
                sfjj: 0,
                sfjx: 0,
              })
            }
          })
          this.tableList = tableList
        }
      }
      this.isClear = false
      this.setEnabled.idSex = true
      this.statistics = {
        examfeeitemCount: 0,
        priceTotal: 0,
        factpriceTotal: 0,
        waitprice: 0,
        ss: 0,
        total: 0,
      }
      this.$nextTick(() => {
        this.$refs.mainForm.clearValidate('jhys')
      })
      // 获取对应的套餐
      this.getPackageData()
    },
    // 性别改变
    onSexChanged() {
      for (var i in this.tableList) {
        if ((this.tableList[i].idPayway == '5' && this.tableList[i].idExamfeeitem) || this.tableList[i].FFeecharged == '1' || this.tableList[i].isMintc == '1') break
        this.$refs['item' + i] ? this.$refs['item' + i].initData(this.tableList[i].examfeeitemName) : ''
      }
    },
    // 年龄、生日转换
    onCardNoChanged(isSkip) {
      // 移除所有空格
      if(this.formData.idcardno){
        this.formData.idcardno = this.formData.idcardno.replace(/\s+/g, '');
      }

      var value = this.formData.idcardno
      if (this.formData.countreportoccupationxml != 1) {
        return
      }
      var cardNo = value
      if (!cardNo) {
        this.formData.age = 0
        this.formData.birthdate = this.getToday()
      } else {
        var Validator = new IDValidator(GB2260)
        if (!Validator.isValid(value)) {
          return
        }
        // 录入身份证号，关联上次个人信息，姓名、性别、年龄、手机
        getDataByIdcard({ patientcode: this.formData.patientcode, idcardno: cardNo }).then((res) => {
          var obj = res.data
          if (obj.isExists == 1) {
            var patientnameold = this.formData.patientname
            if (patientnameold.length > 0) {
              var patientname = obj.patientname
              if (patientname == patientnameold) {
                this.setEntryIdcardData(obj, isSkip)
              } else {
                this.$modal
                  .confirm('是否将' + patientnameold + '更新为' + patientname + '的信息？', '提示')
                  .then(() => {
                    this.setEntryIdcardData(obj, isSkip)
                  })
                  .catch(() => {})
              }
            } else {
              this.setEntryIdcardData(obj, isSkip)
            }
          }
        })
        // 籍贯
        if (cardNo.length >= 2) {
          var code = cardNo.substring(0, 2)
          var idResarea = this.idResareaData
          var areaId = null
          for (var i = 0; i < idResarea.length; i++) {
            if (idResarea[i].areaCode == code) {
              areaId = idResarea[i].id
              break
            }
          }
          if (areaId != null) this.formData.idResarea = areaId
        }
        if (cardNo.length < 15 || (cardNo.length > 15 && cardNo.length < 18)) return
        if (cardNo.length == 15) {
          cardNo = cardNo.substring(0, 6) + '19' + cardNo.substring(6) + 'x'
        }
        if (parseInt(cardNo.substr(16, 1)) % 2 == 1) {
          this.formData.idSex = 0
        } else {
          this.formData.idSex = 1
        }
        var birth = cardNo.substring(6, 10) + '-' + cardNo.substring(10, 12) + '-' + cardNo.substring(12, 14)
        var myDate = new Date()
        var curBirth = new Date(birth)
        this.formData.age = myDate.getFullYear() - curBirth.getFullYear()
        this.formData.birthdate = this.timestampToTime(curBirth)
      }
    },
    setEntryIdcardData(obj, isSkip) {
      this.formData.patientname = obj.patientname
      this.formData.idSex = obj.idSex
      // 2024-12-05 读身份证不覆盖手机号
      if (obj.phone && !this.formData.phone) {
        this.formData.phone = obj.phone
      }
      if (obj.picture && isSkip != 'skip') {
        this.formData.picture = obj.picture
      }
      if (obj.ishmd) {
        this.formData.isHmd = 1
        this.formData.isHmdb = obj.hmdbz
      }
      if (!this.formData.phone) {
        this.$nextTick(() => {
          this.$refs.phone.focus()
        })
      }
    },
    // 年龄改变、出生日期转换
    onAgeChanged(value) {
      var age = value
      var myDate = new Date()
      var year = myDate.getFullYear() - age
      this.formData.birthdate = year + '-' + myDate.getMonth() + '-' + myDate.getDate()
    },
    // 年龄、出生日期转换
    onBirthChanged() {
      var birth = this.formData.birthdate
      var myDate = new Date()
      var curBirth = new Date(birth)
      if (!curBirth) {
        return
      }
      if (curBirth.getTime() > myDate.getTime()) {
        this.formData.birthdate = myDate.getFullYear() + '-' + myDate.getMonth() + '-' + myDate.getDate()
        curBirth = myDate
      }
      this.formData.age = myDate.getFullYear() - curBirth.getFullYear()
    },
    // 替检改变
    ontjchanged(value) {
      this.toggleTjr()
      if (value == 1) {
        this.formData.tjr = this.formData.patientname
      }
    },
    // 根据接害因素、体检类别查找套餐的收费项目
    ppzxtc(value) {
      this.$set(this.formData, 'medicaltype', value)
      //获取接害因素数据
      var jhysValue = this.formData.jhys
      if (!jhysValue) return
      //获取职业体检类别的数据
      var zytjlbValue = value
      this.addOneRow()
      return
      //根据接害因素和职业体检类别加载收费项目
      var query = {
        jhId: jhysValue.split(','),
        zyValue: zytjlbValue,
      }
      getPpZxtcData(query).then((res) => {
        var data = res.data
        var r = []
        for (var i = 0, l = data.length; i < l; i++) {
          var old = this.tableList.findIndex((a) => {
            if (a.idExamfeeitem == data[i][0]) return true
          })
          if (old > -1) {
            continue
          }
          var newRow = {
            idExamfeeitem: data[i][0],
            examfeeitemName: data[i][1],
            FFeechargedinttrans: data[i][3],
            sfjx: 0,
            count: 1,
            changeItem: 0,
            FMarkFeereturn: 0,
            isMintc: 0,
            idPayway: '1',
          }
          newRow.price = data[i][5]
          newRow.factprice = data[i][5]
          newRow.idKs = data[i][9]
          newRow.qty = i + 1
          r.push(newRow)
        }
        if (r.length > 0) this.tableList.push(r)
        this.editChange()
      })
    },
    // 籍贯返回值
    idResareaMethod(value) {
      this.idResareaShow = []
      this.idResareaData.forEach((el) => {
        if (this.lowerCaseChange(el.inputCode).indexOf(this.lowerCaseChange(value)) > -1 || el.resarea.indexOf(value) > -1) {
          this.idResareaShow.push(el)
        }
      })
    },
    // 籍贯选择
    idResareaChange(id) {
      this.idResareaShow.forEach((el) => {
        if (el.id == id) {
          this.formData.resarea = el.resarea
        }
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
    // 字母转小写
    lowerCaseChange(arg) {
      var str = arg.split('')
      for (var i = 0; i < str.length; i++) {
        str[i] = str[i].toLowerCase()
      }
      return str.join('')
    },
    // 黑名单选中之后黑名单备注可用
    onHmdCheckedChanged() {
      if (this.formData.isHmd == 0) this.formData.isHmdb = ''
    },
    // 打开前台须知弹窗
    toNotice() {
      var qtxz = this.formData.qtxz || '暂无内容'
      this.$alert(qtxz, '前台须知', {
        confirmButtonText: '关闭',
      })
    },
    // 总工龄变化
    zglChange(val) {
      if (val < this.formData.jhgl) {
        this.formData.jhgl = val
      }
    },
    // 参加工作变化
    workDateChange(val) {
      let startMonth = val.slice(0, 7)
      let endMonth = this.$getDate('').slice(0, 7)
      if (startMonth && this.formData.medicaltype) {
        this.formData.zgl = this.reduMonths(startMonth, endMonth)
      }
    },
    // 从事工种改变
    harmDateChange(val) {
      let startMonth = val.slice(0, 7)
      let endMonth = this.$getDate('').slice(0, 7)
      if (startMonth && this.formData.medicaltype) {
        this.formData.jhgl = this.reduMonths(startMonth, endMonth)
      }
    },
    reduMonths(startMonth, endMonth) {
      let startY = startMonth.split('-')[0],
        startM = startMonth.split('-')[1],
        endY = endMonth.split('-')[0],
        endM = endMonth.split('-')[1]
      startMonth = startY + startM
      endMonth = endY + endM
      if (startMonth > endMonth) {
        let reduY = startY - endY,
          reduM = startM - endM
        return reduY * 12 + reduM
      } else if (startMonth < endMonth) {
        let reduY = endY - startY,
          reduM = endM - startM

        return reduY * 12 + reduM
      } else {
        return 0
      }
    },
    // 预付方式点击
    onPayWayWindow() {
      this.$refs.prepayment.showDialog(this.formData.patientcode, this.formData.patientname, this.version)
    },
    // 预付方式返回值
    prepaymentSuccess(payway, summation) {
      this.formData.payway = payway
      this.formData.prepayment = summation
    },
    // 记账单位请求数据
    querySearchAsync(queryString, callback) {
      var query = {
        current: 1,
        size: 99999,
        key: queryString,
      }
      getJzOrg(query).then((res) => {
        callback(res.data.records)
      })
    },
    // 选择表格项
    onRowDblClick(row) {
      if (!row) {
        this.$modal.alertWarning('请选中一条记录！')
        return
      }

      if (!row.patientcode) {
        this.$modal.alertWarning('请先完成预约')
        return
      } else {
        // this.formdata=row
        this.getPatientDataForBd(row.patientcode)
      }
    },
    // 增加
    addOneRow() {
      // 增加一行，设置标志
      var newRow = {
        idExamfeeitem: '',
        sfjx: 0,
        count: 1,
        changeItem: 0,
        FMarkFeereturn: 0,
        isMintc: 0,
        idPayway: '1',
        qty: this.getNewQty(this.tableList),
      }
      this.$refs.table.clearSelection()
      this.selectTable = []
      var leng = this.tableList.length
      if (leng > 0) {
        if (this.tableList[leng - 1].idExamfeeitem) {
          this.tableList.push(newRow)
        } else {
          newRow = this.tableList[leng - 1]
        }
      } else {
        this.tableList.push(newRow)
      }
      // 增加行编辑
      this.$nextTick(() => {
        this.$refs[`item${this.tableList.length - 1}`].setFocus()
        this.$refs.table.toggleRowSelection(newRow)
        this.$refs.table.bodyWrapper.scrollTop = this.$refs.table.bodyWrapper.scrollHeight
        this.selectTable = [newRow]
        this.statistics.examfeeitemCount = this.tableList.length
      })
    },
    getNewQty(gri) {
      var qty = 0
      gri.forEach((row) => {
        if (row.qty) {
          var q = Number(row.qty)
          if (q > qty) {
            qty = q
          }
        }
      })
      return qty + 1
    },
    // 移除收费项目行
    removeOneRow() {
      // 套餐内的收费项目
      var tcsfItem = ''
      var rows = this.selectTable
      if (rows.length == 0) {
        this.$alert('请选中一条或多条记录！', '提示', {
          confirmButtonText: '确定',
        })
        return
      } else {
        // 是否可以删除
        var isDelete = false
        var groups = [] //所有组间选分组（删除组间选分组内项目时，要同时删除组间选分组内其他项目）
        for (var i = 0; i < rows.length; i++) {
          // 已收费
          if (rows[i].FFeecharged == 1) {
            this.$alert(rows[i].examfeeitemName + ' 收费项目已经收费，不可以删除！', '提示')
            return
          }
          // 已检状态
          if (rows[i].FExaminated == 1) {
            this.$alert(rows[i].examfeeitemName + ' 收费项目已经检查，不可以删除！', '提示')
            return
          }
          // 弃检
          if (rows[i].FGiveup == 1) {
            this.$alert(rows[i].examfeeitemName + ' 收费项目已经弃检，不可以删除！', '提示')
            return
          }
          // 退项状态
          if (rows[i].changeItem == 1) {
            this.$alert(rows[i].examfeeitemName + ' 收费项目已经退项，不可以删除！', '提示')
            return
          }
          // 补检中附带的项目不可删除
          if (this.isBuJian && rows[i].sfjx != 1) {
            this.$alert(rows[i].examfeeitemName + '补检项目不可以删除！', '提示')
            return
          }
          // 套餐里的收费项目
          if (rows[i].isMintc == 1) {
            isDelete = true
            tcsfItem += rows[i].examfeeitemName + '、'
          }
          if (rows[i].groupType == 1) {
            groups.push(rows[i].group)
          }
        }
        var title = ''
        //删除组间选分组内项目给与提示
        if (groups.length > 0) {
          var zjxrows = []
          this.tableList.forEach((el) => {
            //不会出现分组内其他项目已收费、弃检、退项、检查，而该项目未收费的情况。
            if (groups.indexOf(el.group) != -1) {
              zjxrows.push(el)
            }
          })
          title += '所选项目中包含组间选分组内项目，将删除同组下所有项目：'
          for (var z = 0; z < zjxrows.length; z++) {
            title += zjxrows[z].examfeeitemName
            if (z == zjxrows.length - 1) {
              title += '。'
            } else {
              title += '、'
            }
            rows.push(zjxrows[z])
          }
        }
        // 删除套餐内项目给与提示
        if (isDelete) {
          if (title.length > 0) title += '<br/>'
          title += '收费项目：' + tcsfItem.substring(0, tcsfItem.length - 1) + ' 属于套餐。'
        }
        title += '确定删除？'
        this.$confirm(title, '提醒', {
          dangerouslyUseHTMLString: true,
          type: 'warning',
        })
          .then(() => {
            for (var i = 0; i < rows.length; i++) {
              // 个检
              // 未收费
              if (rows[i].FFeecharged == null || rows[i].FFeecharged == 0) {
                let index = this.tableList.findIndex((item) => {
                  if (JSON.stringify(rows[i]) == JSON.stringify(item)) {
                    return true
                  }
                })
                if (rows[i].id) {
                  rows[i]._state = 'removed'
                  this.removeList.push(rows[i])
                }
                this.$delete(this.tableList, index)
              }
            }
            this.$nextTick(() => {
              // this.tableList = this.computePrice(this.tableList, false)
              this.editChange()
            })
          })
          .catch(() => {})
      }
    },
    // 加项
    addItem() {
      var patientCode = this.formData.patientcode
      // 没有体检号不能进行操作
      if (!patientCode) {
        this.$modal.alertWarning('请先保存！', '提醒')
        return
      }
      var jxys = null
      var name = null
      var rows = []
      this.tableList.forEach((el) => {
        if (el.jxys) {
          rows.push(el)
        }
      })
      if (rows.length > 0) {
        jxys = rows[rows.length - 1].jxys
        name = rows[rows.length - 1].name
      }
      // 增加一行，设置标志
      var newRow = {
        sfjx: 1,
        fregistered: 0,
        changeItem: 0,
        FMarkFeereturn: 0,
        FFeecharged: 0,
        FLabsendtolis: 0,
        FExaminated: 0,
        FGiveup: 0,
        FDelayed: 0,
        isMintc: 0,
        price: 0,
        factprice: 0,
        jxys: jxys,
        name: name,
        qty: this.getNewQty(this.tableList),
        idPayway: '1',
      }
      this.$refs.table.clearSelection()
      this.selectTable = []
      this.tableList.push(newRow)
      var leng = this.tableList.length
      // if (leng&&this.tableList[leng - 1].idExamfeeitem) {
      // } else {
      // }
      newRow = this.tableList[leng - 1]
      // 加项行编辑
      this.$nextTick(() => {
        this.$refs[`item${this.tableList.length - 1}`].setFocus()
        this.$refs.table.toggleRowSelection(newRow)
        this.$refs.table.bodyWrapper.scrollTop = this.$refs.table.bodyWrapper.scrollHeight
        this.selectTable = [newRow]
        this.statistics.examfeeitemCount = this.tableList.length
      })
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
    // 折扣
    handleDiscount() {
      if (!this.tableList.length) {
        this.$alert('折扣失败：收费项目不存在！', '提示')
        return
      }
      let error = ''
      this.selectTable.forEach((el) => {
        if (!error && el.fFeecharged == 1) {
          error = '折扣失败：收费项目已收费！'
          return
        }
        if (!error && !el.price) {
          error = '折扣失败：收费项目不存在！'
          return
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs.discount.showDialog(this.selectTable, this.tableList)
    },
    // 执行折扣
    executeDiscount(data, responseText) {
      if (data.way == 1 && data.choose != 2) {
        this.tableList.forEach((el) => {
          if (el.examfeeitemName && el.price && el.FFeecharged != 1 && (data.choose == 4 ? true : data.choose == 3 ? el.idPayway != 5 : el.idPayway != 5 && responseText[el.idExamfeeitem])) {
            el.factprice = el.price * (1 - data.deRate * 0.01)
          }
        })
      } else if (data.way == 1 && data.choose == 2) {
        this.selectTable.forEach((val) => {
          this.tableList.forEach((el) => {
            if (val.idExamfeeitem == el.idExamfeeitem && el.examfeeitemName && el.price && el.FFeecharged != 1 && !(el.idPayway == 5 || !responseText[el.idExamfeeitem])) {
              el.factprice = el.price * (1 - data.deRate * 0.01)
            }
          })
        })
      } else {
        this.selectTable[0].factprice = data.truePrice
      }
      this.editChange()
    },
    // 保存
    beforeSave(isReservation, type, isreg, isSave) {
      var patientcode = this.formData.patientcode
      if (patientcode) {
        refundValidate({ patientCode: patientcode, autoFill: 0 }).then(() => {
          var rows = []
          this.tableList.forEach((el) => {
            if (this.formData.fregistered == '1' && el.idKs == getJykId() && !el.id && el.sfjx == 0) {
              rows.push(el)
            }
          })
          if (rows.length > 0) {
            this.$modal
              .confirm('新增项目中存在检验科项目，是否通知检验科', '提示')
              .then(() => {
                this.onFinishCharge(isReservation, type, isreg, 1, isSave)
              })
              .catch(() => {
                this.onFinishCharge(isReservation, type, isreg, 0, isSave)
              })
          } else {
            this.onFinishCharge(isReservation, type, isreg, 0, isSave)
          }
        })
      } else {
        this.onFinishCharge(isReservation, type, isreg, 0, isSave)
      }
    },
    // 读取身份证
    beforeReadIdCard(onlyPic) {
      const loading = this.$loading({
        lock: true,
        text: '正在读取...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      sdk
        .read_card()
        .then((data) => {
          this.idCardImg = data.identityPic
          if (onlyPic) {
            this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + this.idCardImg)
            this.idCardImg = undefined
            loading.close()
            return
          }
          var idcardno = data.certNumber
          var idcardnoold = this.formData.idcardno
          if (idcardnoold) {
            if (idcardnoold == idcardno) {
              this.setCardData(data)
            } else {
              let text = ''
              if (this.formData.patientname) {
                text = '是否将' + this.formData.patientname + '更新为' + data.partyName + '的信息？'
              } else {
                text = '是否将人员信息更新为' + data.partyName + '？'
              }
              this.$modal
                .confirm(text, '提示')
                .then(() => {
                  this.setCardData(data)
                })
                .catch(() => {})
            }
          } else {
            // this.idCardImg = data.identityPic
            this.setCardData(data)
          }
          loading.close()
        })
        .catch((err) => {
          loading.close()
          if (err.errorMsg) this.$modal.alertError(err.errorMsg, '提醒')
          else this.$modal.alertError('获取数据异常!', '提醒')
        })
    },
    setCardData(data) {
      this.idCardInfo = data
      getPatientcodeByIdcard(data.certNumber).then((res) => {
        if (res.data.length != 0) {
          // 读身份证
          this.$refs.orderList.handleShow(res.data)
        } else {
          this.handleNoOrder(data)
        }
      })
    },

    // 没有备单信息时读卡
    handleNoOrder(data) {
      // 页面信息赋值
      this.formData.countreportoccupationxml = 1
      this.formData.patientname = data.partyName
      this.formData.idSex = data.gender == '男' ? 0 : 1
      for (var i in this.idNationData) {
        var dd = this.idNationData[i]
        if (dd.name == data.nation + '族') {
          this.formData.idNation = dd.id
          break
        }
      }
      data.bornDay = data.bornDay.substring(0, 4) + '-' + data.bornDay.substring(4, 6) + '-' + data.bornDay.substring(6, 8)
      this.formData.birthdate = data.bornDay
      var curDate = new Date()
      var birth = new Date(data.bornDay)
      this.formData.age = curDate.getFullYear() - birth.getFullYear() + 1
      this.formData.idcardno = data.certNumber
      this.formData.address = data.certAddress

      if (!this.formData.phone) {
        this.$nextTick(() => {
          this.$refs.phone.focus()
        })
      }
      this.$set(this.formData, 'picture', 'data:image/jpeg;base64,' + data.identityPic)
      this.onSexChanged()
      this.onCardNoChanged('skip')
    },
    //选择备单未登记的体检号
    selectIdCard(patientcode) {
      this.queryParams.patientCode = patientcode
      this.onSearchCodeEnter(undefined, () => {}, 1)
    },
    // 不选择备单未登记的体检号
    cancelIdCard() {
      this.handleNoOrder(this.idCardInfo)
    },
    // 完成登记
    beforeReg(isReservation, type, isreg) {
      var phone = this.formData.phone
      if (phone && phone.length == 0) {
        this.$modal
          .confirm('您未录入手机号码，确定要保存吗？', '提示')
          .then(() => {
            this.onFinishCharge(isReservation, type, isreg)
          })
          .catch(() => {})
      } else {
        this.onFinishCharge(isReservation, type, isreg)
      }
    },
    // 预约、完成登记、保存操作
    // 登记  1，1，0
    onFinishCharge(isReservation, type, isreg, noticeJyk, isSave) {
      // 移除手机号所有空格
      if(this.formData.phone){
        this.formData.phone = this.formData.phone.replace(/\s+/g, '');
      }

      if (isReservation == 1) {
        if (this.formData.idcardno) {
          if (this.formData.countreportoccupationxml == 1) {
            var Validator = new IDValidator(GB2260)
            if (!Validator.isValid(this.formData.idcardno)) {
              this.$alert('身份证号输入不合法', '提示')
              return
            }
          }
        } else {
          this.$alert('证件号不能为空', '提示')
          return
        }
      }
      let err = ''
      this.tableList.forEach((el) => {
        let sex = el.fFeechargedinttrans || el.FFeechargedinttrans || el.ffeechargedinttrans || el.xb || 2
        if (sex != 2 && sex != this.formData.idSex) {
          err = '存在收费项目与体检者性别不匹配,请删除后再试'
        }
      })
      if (err) {
        this.$alert(err)
        return
      }
      if (this.formData.idExamtype != '0' && this.formData.medicaltype == 'null') {
        this.formData.medicaltype = ''
        this.$alert('请选择体检类别', '提示')
        return
      }
      this.$refs['mainForm'].validate((valid) => {
        if (valid) {
          //折扣率超过1.5，不能保存
          var ysj = this.statistics.priceTotal
          var yhj = this.statistics.factpriceTotal
          ysj = ysj ? Number(ysj) : 0
          yhj = yhj ? Number(yhj) : 0

          // 左侧数据验证
          var formdata = this.formData
          // 套餐价格
          var rowData = this.statistics
          var tcprice = rowData.tcprice
          formdata.tcprice = tcprice
          // 0:完成预约按钮点击；1：完成登记、保存按钮点击
          var xianjin = 0
          var tongjin = 0
          var gData = this.tableList
          var fAllTs = true //是否全部是统收
          var chargeZero = false
          if (gData.length > 0) {
            // 删除空行
            gData.forEach(() => {
              let index = this.tableList.findIndex((item) => {
                if (item.idExamfeeitem == undefined || item.idExamfeeitem == '') {
                  return true
                }
              })
              this.$delete(this.tableList, index)
            })
            this.editChange()
            // 获取列表数据
            var data = this.tableList
            // 判断加项项目的加项医生是否为空
            let jxysIndex = this.tableList.findIndex((item) => {
              if (item.sfjx == 1 && !item.jxys) {
                return true
              }
            })
            if (jxysIndex > -1) {
              this.$modal.alertWarning('加项医师不能为空,请修改后重新操作!', '提醒')
              return
            }
            // 剩余备选数量(兼容老套餐)
            var bxs = 0
            //备选分组数组
            var bxarr = []
            //组内选分组
            var znxarr = []
            // 判断grid是否存在相同的收费项目
            var hash = {}
            // 未收费情况
            var tongFei = 0
            var tenFei = 0
            var addCharge = 0 //新增项目总费用
            var hasAdd = false
            for (var i = 0; i < data.length; i++) {
              var row = data[i]
              //不是统收（统收自动完成收费）
              if (row.idPayway != 5 && row.samplemsgfromlis != '1') {
                fAllTs = false
              }
              if (row.id == '' || typeof row.id == 'undefined') {
                row._state = 'added'
                this.tableList[i] = row
              } else if (row._state != 'added') {
                row._state = 'modified'
                this.tableList[i] = row
              }
              if (row.idExamfeeitem != undefined && hash[row.idExamfeeitem] && row.changeItem != 1) {
                this.$alert("存在相同的收费项目：<font color='red'>" + row.examfeeitemName + '</font>', '提醒', {
                  dangerouslyUseHTMLString: true,
                })
                return
              }
              if (row.changeItem != 1) {
                hash[row.idExamfeeitem] = true
              }
              // 是否存在未收费情况
              if (row.FFeecharged == undefined || row.FFeecharged == null || row.FFeecharged == 0) {
                //如果是十周年项目，自动收费，收费方式免费
                if (row.idPayway == 1) {
                  xianjin = 1
                  hasAdd = true //全统收项目不用弹收费为0对话框
                  addCharge += Number(row.factprice)
                  // 统收未收费计算
                } else if (row.idPayway == 5) {
                  tongFei += Number(row.factprice)
                  tongjin = 1
                }
              }
              // 剩余备选数量
              if (row.isbx == 1) {
                if (row.groupType == 1) {
                  if (JSON.stringify(bxarr).indexOf(row.group) == -1) {
                    bxarr.push(row.group)
                  }
                  //组内选只能选一个
                } else if (row.groupType === 0 || row.groupType === '0') {
                  if (JSON.stringify(znxarr).indexOf(row.group) == -1) {
                    znxarr.push(row.group)
                  } else {
                    this.$modal.alertWarning('【组内选】类型每个备选分组只能选择一个项目。')
                    return
                  }
                  //兼容老套餐
                } else if (!row.groupType && row.groupType !== 0 && row.groupType !== '0') {
                  bxs++
                }
              }
            }
            if (hasAdd && addCharge == 0) {
              chargeZero = true
            }
            bxs = bxarr.length > 0 ? bxarr.length : bxs
            if (bxs > this.kxzsValue) {
              this.$modal.alertWarning('收费项目已经备选的数量多于可选组数,请删除多余的收费项目')
              return
            } else {
              formdata.bxcount = this.kxzsValue
            }
          } else if (type == 1 && this.tableList.length == 0) {
            this.$modal.alertWarning('收费项目不能为空！')
            return
          }
          var ssstp = this.formData.moneyamountpaid
          ssstp = ssstp == null || ssstp == '' ? 0 : ssstp
          var waitP = rowData.waitprice
          formdata.moneyamount = ssstp + waitP
          formdata.xianjin = xianjin
          // 团检
          if (this.formData.ddh != '') {
            formdata.org = 1
          }
          formdata.tongFei = tongFei
          formdata.tenFei = tenFei
          formdata.personpricelimit = this.personpricelimit
          formdata.resarea = this.formData.resarea
          formdata.itemList = [...this.removeList, ...this.tableList]

          // // 定义需要处理的字段列表
          // const fieldsToModify = [
          //     'FDelayed',
          //     'FExaminated',
          //     'FFeecharged',
          //     'FFeechargedinttrans',
          //     'FGiveup',
          //     'FLabsendtolis',
          //     'FMarkFeereturn',
          //     'FRegistered',
          //     'FTransferedhl7'
          //   ];
          //   // 遍历 itemList 并修改字段
          //   formdata.itemList.forEach((item) => {
          //       fieldsToModify.forEach((field) => {
          //         if (item.hasOwnProperty(field)) {
          //           const newField = field.charAt(0).toLowerCase() + field.slice(1); // 将首字母改为小写
          //           item[newField] = item[field]; // 创建新字段并赋值
          //           delete item[field]; // 删除原字段
          //         }
          //       });
          //   });

          formdata.isReservation = isReservation
          formdata.noticeJyk = noticeJyk
          formdata.version = this.version
          // 保存开单经理姓名
          formdata.kdzl_name = this.doctorData.bindValue
          this.idNationShow.forEach((el) => {
            if (el.id == formdata.idNation) {
              formdata.nation = el.name
            }
          })

          if (!formdata.picture) {
            formdata.picture = this.defaultPicture
          }
          if (this.formData.dateguidancereturned && this.formData.dateguidancereturned.indexOf(' ') == -1) {
            formdata.dateguidancereturned = this.formData.dateguidancereturned + ' 00:00:00'
          }
          if (this.formData.birthdate && this.formData.birthdate.indexOf(' ') == -1) {
            formdata.birthdate = this.formData.birthdate + ' 00:00:00'
          }
          if (this.formData.workDate && this.formData.workDate.indexOf(' ') == -1) {
            formdata.workDate = this.formData.workDate + ' 00:00:00'
          }
          if (this.formData.harmDate && this.formData.harmDate.indexOf(' ') == -1) {
            formdata.harmDate = this.formData.harmDate + ' 00:00:00'
          }
          var f = this.formData.fregistered == '1' && xianjin == 0 && tongjin == 1 //如果已完成登记，且未收费项目仅有统收，数据重发
          if (formdata.idOrg) {
            // formdata.moneyamountpaid = formdata.moneyamount
            let factprice = 0
            data.forEach((el) => {
              if (el.idPayway == '5' && el.FFeecharged != 1) {
                factprice += el.factprice
              }
            })
            formdata.tongFei = factprice
          }
          this.uploadLoading = this.$loading({
            lock: true,
            text: '保存中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          if (this.saveDebounce) {
            return
          }
          this.saveDebounce = true
          this.uploadImg(formdata.picture, (src) => {
            formdata.picture = JSON.parse(JSON.stringify(src))

            // 这两个没有值的时候，传null或者不传都可以，不要传空字符串
            if(formdata.insuranceno == ''){
              formdata.insuranceno = null
            }

            if(formdata.inpatientno == ''){
              formdata.inpatientno = null
            }

            savaRegister(formdata)
              .then((res) => {
                this.uploadLoading.close()
                this.saveDebounce = false
                var text = res.data
                if (text.patientcode) {
                  // 体检号赋值
                  var patientcode = text.patientcode

                  this.formData.patientcode = patientcode
                  // 档案id
                  this.formData.idPatientarchive = text.patientarchiveno
                  var stus = text.sataus
                  //超量提醒
                  var waitCheck = text.waitCheck
                  if (waitCheck == '1') {
                    this.$modal.msgSuccess('普通会员超量预约成功')
                  } else if (waitCheck == '2') {
                    this.$modal.msgWarning('VIP会员预约已达上限，需审核')
                    isReservation = 3
                  } else if (waitCheck == '3') {
                    this.$modal.msgWarning('VVIP会员预约已达上限，需审核')
                    isReservation = 3
                  }
                  // 保存中间库
                  this.msgContent = {
                    isReservation: isReservation,
                    isreg: isreg,
                    xianjin: xianjin,
                    chargeZero: chargeZero,
                  }

                  var afregistered = this.formData.fregistered
                  if ((this.msgContent.isreg == 0 || (afregistered != undefined && afregistered != null && afregistered == 1 && this.msgContent.isreg == 1)) && this.msgContent.chargeZero) {
                    this.onchargeZero(() => {
                      if (fAllTs && isReservation == 1) {
                        //如果收费项目都是统收并且是完成登记
                        this.chongfa(patientcode, stus)
                      } else if (f) {
                        this.chongfa(patientcode, 3)
                      }
                      // else if (chargeZero && (afregistered == '1' || isReservation == 1)) {
                      //   this.chongfa(patientcode, 3)
                      // }
                      this.mainPagetCtrl()
                    })
                  } else {
                    if (fAllTs && isReservation == 1) {
                      //如果收费项目都是统收并且是完成登记
                      this.chongfa(patientcode, stus)
                    } else if (f) {
                      this.chongfa(patientcode, 3)
                    }
                    //  else if (chargeZero && (afregistered == '1' || isReservation == 1)) {
                    //   this.chongfa(patientcode, 3)
                    // }
                    this.mainPagetCtrl()
                  }
                  // if (afregistered != '1' && isSave == 1) {
                  //   getReservation({ patientcode }).then(({ data }) => {
                  //     if (!data) {
                  //       let query = {
                  //         levelId: this.formData.idPatientclass,
                  //         levelName: '',
                  //         realName: this.formData.patientname,
                  //         sex: this.formData.idSex,
                  //         idcard: this.formData.idcardno,
                  //         mobile: this.formData.phone,
                  //         remark: '',
                  //         reservationDate: this.formData.dateguidancereturned.slice(0, 10) + ' 00:00:00',
                  //         branchId: this.formData.hospitalcode,
                  //         patientcode: this.formData.patientcode,
                  //         status: 1,
                  //         bizOrderNum: this.formData.numorgresv,
                  //         idOrg: this.formData.idOrg,
                  //         fUsecodehiden: this.formData.fusecodehiden,
                  //       }
                  //       this.memberTypeList.forEach((el) => {
                  //         if (el.levelId == this.formData.idPatientclass) {
                  //           query.levelName = el.levelName
                  //         }
                  //       })
                  //       saOrUp(query).then((res) => {})
                  //     }
                  //   })
                  // }
                }
              })
              .catch(() => {
                this.saveDebounce = false
                this.uploadLoading.close()
              })
          })
        } else {
          var phone = this.formData.phone
          if (!phone) {
            this.$modal.msgWarning('请输入手机号')
            this.$refs.phone.focus()
            return
          }
          this.$modal.alertWarning('页面存在必填项或者填写格式存在问题！')
        }
      })
    },
    onchargeZero(callback) {
      var patientcode = this.formData.patientcode
      if (!patientcode) return
      //直接去收费界面，手动添加一条
      callback()
    },
    // 上传图片
    uploadImg(picture, fn) {
      if (typeof picture == 'object') {
        let formData = new FormData()
        formData.append('file', picture)
        uploadImage(formData)
          .then((res) => {
            fn(res.data)
          })
          .catch(() => {
            this.uploadLoading.close()
          })
      } else if (this.isBase64(picture)) {
        var bytes = window.atob(picture.split(',')[1])
        var array = []
        for (var i = 0; i < bytes.length; i++) {
          array.push(bytes.charCodeAt(i))
        }
        var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
        var formData = new FormData()
        formData.append('file', blob)
        uploadImage(formData)
          .then((res) => {
            fn(res.data)
          })
          .catch(() => {
            this.uploadLoading.close()
          })
      } else if (picture === this.defaultPicture) {
        fetch(picture)
          .then((response) => response.blob())
          .then((path) => {
            let formData = new FormData()
            formData.append('file', path)
            uploadImage(formData).then((res) => {
              fn(res.data)
            })
          })
          .catch(() => {
            this.uploadLoading.close()
          })
      } else {
        fn(picture)
      }
    },
    // 时间戳转日期
    timestampToTime(date) {
      // 时间戳为10位需*1000，时间戳为13位不需乘1000
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      return Y + M + D
    },
    // 数据重发
    chongfa(patientCode, flag) {
      if (this.setData.closeChongfa != 1) {
        const msgId = this.$loading({
          lock: true,
          text: '正在提交中间库',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        middleDbInterface({ patientcode: patientCode })
          .then((res) => {
            msgId.close()
            if (res.code != 200) {
              // this.$confirm('中间库获取数据异常', '提醒', {
              //   confirmButtonText: '调用评价器',
              //   cancelButtonText: '取消',
              //   type: 'warning',
              // })
              //   .then(() => {
              //     this.mPageCtr()
              //     this.customerMark()
              //   })
              //   .catch(() => {
              //     this.mPageCtr()
              //   })
              this.$alert('中间库获取数据异常', '提醒').then(() => {
                this.mPageCtr()
              })
            } else {
              this.$modal.msgSuccess('数据重发成功', '提醒')
            }
          })
          .catch(() => {
            msgId.close()
          })
      }
      if (flag == '-1') {
        this.mainPagetCtrl()
        return
      }
    },
    // 体检者信息保存后处理
    mainPagetCtrl() {
      // 主页面操作
      var c = this.msgContent
      var alt = ['预约信息保存成功', '登记成功', '保存成功', '预约已达上限，需审核']
      if (c.isReservation == 1) {
        var archiveId = this.formData.idPatientarchive
        var patientId = this.formData.patientcode
        var data = {
          id: archiveId,
          patientId: patientId,
        }
        if (archiveId) {
          getTjTime(data).then((res) => {
            var result = res.data
            var message = '<span style="font-size:15px;color:red;font-weight:bold">' + result.patientClass + '</span>' + '登记成功<br>此顾客是第' + '<span style="font-size:18px;font-weight:bold">' + result.count + '</span>' + '次来本中心'
            this.$alert(message, '提醒', {
              dangerouslyUseHTMLString: true,
              type: 'success',
            })
              .then(() => {
                this.mPageCtr()
              })
              .catch(() => {
                this.reload()
              })
          })
          return
        }
      }
      this.$alert(alt[c.isReservation], '提醒', {
        dangerouslyUseHTMLString: true,
      }).then(() => {
        this.mPageCtr()
      })
    },
    // 主页操作
    mPageCtr() {
      var c = this.msgContent
      this.msgContent = ''
      // 重新加载grid数据
      this.reload()
      var afregistered = this.formData.fregistered
      //是调用登记方法   或  不是调用登记方法 但已经登记
      if (c.isreg == 0 || (afregistered == 1 && c.isreg == 1)) {
        if (c.chargeZero) {
          this.onJumpChargePage()
        } else if (c.xianjin == 1) {
          //如果有现金未收费的项目||
          this.onJumpChargePage()
        } else {
          // 团检没有增加收费项目情况(只有团检和现金两种方式，且必须有一个收费项目)
          if (c.xianjin == 0) {
            this.showPrintBox = true
          }
        }
      }
    },
    // 打印条码、导引单
    print() {
      var idValue = this.formData.id
      if (idValue == '') {
        this.$modal.alertWarning('体检者不能为空！')
        return
      }
      if (!this.formData.patientcode) {
        this.$modal.alertWarning('体检号不能为空！')
        return
      }

      let tjCode = this.formData.patientcode
      if (idValue == '') {
        this.$modal.msgWarning('体检者不能为空！')
        return
      }
      if (!tjCode) {
        this.$modal.msgWarning('体检号不能为空！')
        return
      }
      this.openPrint = true
      this.$nextTick(() => {
        this.showPrintBox = false
      })
    },
    // 速打 调用本地的打印，打印条码和导引单，条码数量配置文件配置
    fastPrint() {
      var idValue = this.formData.id
      if (idValue == '') {
        this.$modal.alertWarning('体检者不能为空！')
        return
      }
      if (!this.formData.patientcode) {
        this.$modal.alertWarning('体检号不能为空！')
        return
      }
      if (!this.formData.id) {
        this.$modal.msgWarning('体检者不能为空！')
        return
      }

      let data = {
        id: getCookie('cid'), //分中心id
        ids: this.formData.patientcode, //体检者ids
        model: 1, //模板id
        dydStyle: 1, //导引单类型 0 简单 1 复杂 2 普通
        radio: 2, //内容
      }
      const query = {
        id: getCookie('cid'), //分中心id
        ids: [this.formData.id], //体检者ids
        model: 1, //模板id
      }

      // 获取默认打印个数
      let defaultNumber = 0
        barcodePrinter().then(({ data }) => {
            if(data.receptionNum){
              defaultNumber = data.receptionNum
            }else{
              defaultNumber = 9
            }
        })
        this.$refs.printHtml.getReport(data, query, defaultNumber)

      this.$nextTick(() => {
        this.showPrintBox = false
      })
    },
    // 拍照
    cap() {
      this.$refs.photograph.handleShow()
      // if (this.formData.fregistered != 1) {
      // } else {
      //   this.$modal.msgWarning('该体检者已经完成登记，无法进行拍照')
      // }
    },

    // 拍照回调
    setPicture(src) {
      this.$set(this.formData, 'picture', src)
      this.capTxt = '重拍'
    },
    // 转换图片地址
    createObjectURL(value) {
      if (value && value != null) {
        return window.URL.createObjectURL(value);
      } else {
        console.error("传入的值必须是 Blob 或 File 对象，当前值:", value);
        return null;
      }
    },
    // 判断是否为base64格式
    isBase64(str) {
      let imgReg = RegExp(/data:image\/.*;base64,/)
      const res = imgReg.test(str)
      return res
    },
    // 跳转到费用管理界面
    onJumpChargePage() {
      // 判断体检号是否存在
      var patientcode = this.formData.patientcode
      const obj = { path: '/reception/proceeds', name: 'Proceeds' }
      const obj2 = { path: '/reception/return_item', name: 'ReturnItem' }
      if (patientcode) {
        refundValidate({ patientCode: patientcode, autoFill: 0 }).then(() => {
          // 跳转到费用管理界面
          this.$tab.closePage(obj2).then(() => {
            this.$tab.closePage(obj).then(() => {
              store.dispatch('tagsView/addView', { path: obj.path, meta: { title: '费用管理' } })
              this.$router.push({ name: obj.name, params: { patientCode: patientcode } })
            })
          })
        })
      } else {
        // 跳转到费用管理界面
        this.$tab.closePage(obj2).then(() => {
          this.$tab.closePage(obj).then(() => {
            store.dispatch('tagsView/addView', { path: obj.path, meta: { title: '费用管理' } })
            this.$router.push({ name: obj.name, params: { patientCode: patientcode } })
          })
        })
      }
    },
    // 退项
    handleReturn() {
      var patientCode = this.formData.patientcode
      // 跳转到费用管理界面
      const obj = { path: '/reception/proceeds', name: 'Proceeds' }
      const obj2 = { path: '/reception/return_item', name: 'ReturnItem' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.closePage(obj2).then(() => {
          this.$router.push({ name: obj2.name, params: { patientCode } })
        })
      })
    },
    // 刷新按钮
    onClear() {
      this.$tab.refreshPage()
    },
    // 套餐卡
    handleCard() {},
    // 导出体检者收费项目列表
    exports() {
      var searchData = {
        patientname: this.formData.patientname,
        patientCode: this.formData.patientcode,
      }
      this.download(
        '/reception/register/exportItems',
        {
          ...searchData,
        },
        `item_${new Date().getTime()}.xlsx`
      )
    },
    // 表格选中
    handleSelectionChange(val) {
      this.selectTable = val
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.table.clearSelection()
      this.$refs.table.toggleRowSelection(row)
    },
    // 收费项目选择
    onExamfeeitemChanged(data, index) {
      if (!data) {
        this.tableList[index].idExamfeeitem = ''
        this.tableList[index].examfeeitemName = ''
        this.tableList = [...this.tableList]
        return
      }
      //获取数据信息
      //根据行号获取相应的行
      var row = this.tableList[index]
      if (data != undefined) {
        // 判断是否存在相同项目
        var checkMsg = ''
        for (var i in this.tableList) {
          if (this.tableList[i].idExamfeeitem == data.id && this.tableList[i].changeItem != 1) {
            checkMsg = "存在相同的收费项目：<font color='red'>" + data.examfeeitemName + '</font>'
            break
          }
        }
        if (checkMsg) {
          this.tableList[index].idExamfeeitem = ''
          this.tableList[index].examfeeitemName = ''
          this.tableList = [...this.tableList]
          this.$refs[`item${index}`].initData()
          this.$alert(checkMsg, '提醒', {
            dangerouslyUseHTMLString: true,
          })
          return
        }
        // 判断是否与性别相符
        var itemTyepForSex = data.xb
        if (itemTyepForSex != 2 && itemTyepForSex != this.formData.idSex) {
          this.$modal.alertWarning('选择的收费项目不适用该体检者，性别不匹配！', '提醒')
          this.$refs[`item${index}`].initData()
          return
        }
        var examV = this.formData.idExamtype
        if (examV != 0 && examV != 2 && examV != 3 && data.tjlx != examV) {
          this.$modal.alertWarning('选择的收费项目不适用该体检者，体检类型不匹配！', '提醒')
          this.$refs[`item${index}`].initData()
          return
        }
        //传值
        row.idExamfeeitem = data.id
        row.examfeeitemName = data.examfeeitemName
        row.price = data.unitprice
        row.factprice = row.price
        row.count = 1
        row.idPayway = '1'
        row.idKs = data.idDepart
        row.ksName = data.departName
        row.FFeechargedinttrans = data.xb
        //更新行
        this.tableList[index] = row
        this.tableList = [...this.tableList]
        // 增加一行
        //如果已经登记都认为是加项，除非点击增加按钮
        this.$nextTick(() => {
          // if (this.formData.fregistered == 1) {
          //   this.addItem()
          // } else {
          //   this.addOneRow()
          // }
          if (this.tableList[this.tableList.length - 1].sfjx == 1) {
            this.addItem()
          } else {
            this.addOneRow()
          }
        })
        this.editChange()
      }
    },
    // 优惠价改变
    factpriceChange(value, index) {
      if (!value) this.tableList[index].factprice = 0
      this.editChange()
    },
    // 加项医生选择
    jxysChange(value, index) {
      this.tableList[index].jxys = value.id
      this.tableList[index].name = value.name
    },
    //远程选框的方法
    remoteMethod(keyword) {
      const query = {
        current: 1,
        size: 99999,
        srm: keyword,
      }
      if (keyword !== '') {
        this.remoteLoading = true
        getLqrData(query).then((res) => {
          this.remoteLoading = false
          this.regOptions = res.data.records
        })
      } else {
        this.regOptions = []
      }
    },
    // 登记人选择
    regChange({ item, index }) {
      this.tableList[index].idDoctorreg = item.id
      this.tableList[index].doctorregR = item.occupationSummary
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
    // 下方操作按钮
    handleOperation(index) {
      if (this.setReadonly.btncon && (index != 3 || this.setReadonly.fandengji)) return
      if (!this.formData) return
      let id = this.formData.id
      switch (index) {
        case 0:
          // 读身份证
          if (this.isBuJian) {
            // this.$alert('当前体检者状态为补检,不可修改身份信息', '提示')
            this.$confirm('当前体检者状态为补检,是否修改当前头像?')
              .then(() => {
                this.beforeReadIdCard(true)
              })
              .catch(() => {})
            return
          }
          this.beforeReadIdCard()
          break
        case 1:
          // 完成登记
          this.beforeReg(1, 1, 0)
          break
        case 2:
          // 打印
          let tjCode = this.formData.patientcode
          if (id == '') {
            this.$modal.msgWarning('体检者不能为空！')
            return
          }
          if (!tjCode) {
            this.$modal.msgWarning('体检号不能为空！')
            return
          }
          this.openPrint = true

          // var id = mini.get("id");
          //   var idValue = id.getValue();
          //   if (idValue == "") {
          //       mini.alert("体检者不能为空！");
          //       return;
          //   }
          //   if(!tjCode.getValue()){
          //   	mini.alert("体检号不能为空！");
          //   	return;
          //   }
          //   open("打印", "../receptionist/preregistration!print.action?ids="+idValue+"&cid=${cid}&dydStyle=${dydStyle}",
          //       460, 180, function(action, iFrame) {
          //           if (action == "ok") {
          //           }
          //       }
          //   );
          break
        case 3:
          // 反登记
          if (!this.formData.patientcode) {
            this.$modal.alertWarning('请先保存该体检者信息！')
          } else {
            this.$confirm('是否进行反登记', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            })
              .then(() => {
                this.onUnRegister()
              })
              .catch(() => {})
          }
          break
        case 4:
          // 缴费单
          if (!id) {
            this.$modal.alertWarning('请先保存体检者信息！')
            return
          }
          this.$confirm('是否打印缴费单', '打印', {
            type: 'warning',
          })
            .then(() => {
              this.$refs.paymentBills.showDialog(id)
            })
            .catch(() => {})
          break
        case 5:
          // 数据重发
          this.send()
          break
        case 6:
          // 修改信息
          if (!this.formData.patientcode) {
            this.$modal.alertWarning('存在体检号之后才能操作此项')
          } else {
            this.resetMsg()
            this.openEidt = true
          }
          break
        default:
          break
      }
    },
    // 反登记
    onUnRegister() {
      setUnRegister({ patientCode: this.formData.patientcode }).then((res) => {
        this.$modal.alertSuccess('反登记成功', '提醒')
        this.reload()
      })
    },
    // 关闭打印
    closePrint() {
      this.printParams = {
        mode: '1',
        style: '1',
        content: '2',
      }
    },
    // 确认打印
    printConfirm() {
      const query = {
        id: getCookie('cid'), //分中心id
        ids: [this.formData.id], //体检者ids
        model: this.printParams.mode, //模板id
      }
      if (this.printParams.content == '0') {
        this.barNum = localStorage.getItem('barNum') || this.barNum
        this.barNumOpen = true
      } else {
        let queryParams = {
          id: getCookie('cid'), //分中心id
          ids: this.formData.patientcode, //体检号
          model: this.printParams.mode, //模板id
          type: '0',
          dydStyle: this.printParams.style, //导引单类型 0 简单 1 复杂 2 普通
          printBar: undefined,
        }
        if (this.printParams.content == '2') {
          queryParams.printBar = JSON.stringify(query)
        }
        this.openPrint = false
        let routeUrl = this.$router.resolve({
          name: 'BillsModelList',
          query: queryParams,
        })
        window.open(routeUrl.href, '_blank')
      }
    },
    // 打印条码
    handleBarNum() {
      const query = {
        id: getCookie('cid'), //分中心id
        ids: [this.formData.id], //体检者ids
        model: this.printParams.mode, //模板id
      }
      printBarCode(
        query,
        () => {
          localStorage.setItem('barNum', this.barNum)
          this.barNumOpen = false
          this.openPrint = false
        },
        this.barNum
      )
    },
    // 修改信息-保存
    saveMsg() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          saveEdit(this.editData).then(() => {
            this.$alert('保存成功！', '提醒', {
              confirmButtonText: '确定',
              type: 'success',
              callback: () => {
                this.queryParams.patientCode = this.formData.patientcode
                this.onSearchCodeEnter()
                this.openEidt = false
              },
            })
          })
        }
      })
    },
    // 修改信息-重置
    resetMsg() {
      this.editData = {
        id: this.formData.id,
        idOpendoctor: this.formData.idOpendoctor,
        doctorapply: this.formData.doctorapply,
        phone: this.formData.phone,
        note: this.formData.note,
      }
      this.doctorEdit.bindValue = this.formData.doctorapply
      this.resetForm('editForm')
    },
    // 修改信息-开单医师改变
    doctorEditChange(value) {
      this.doctorEdit.bindValue = value.id
      this.editData.idOpendoctor = value.id
      this.editData.doctorapply = value.name
    },
    // 数据重发
    send() {
      var code = this.formData.patientcode
      if (!code) {
        this.$modal.alertWarning('请先完成登记')
        return
      }
      this.chongfa(code, 3)
    },
    // 根据体检类型获取相对应的体检套餐
    // ddid 团检专属卡 可以选择订单内的其他套餐
    getPackageData(t, k) {

      // 初始组任选优惠价格
      this.totalMoney = 0
      // 查找相对应的体检套餐
      var orgName = this.formData.orgName

      if (!orgName) {
        this.tjtcData.url = '/reception/register/getZxtcsData'
        this.tjtcData.params = {
          tjlx: this.formData.idExamtype,
          jhys: this.formData.jhys,
        }
      } else {
        this.tjtcData.url = '/reception/register/getApproveTjtcData'
        this.tjtcData.params = {
          apprddId: this.formData.ddh,
          tjlx: this.formData.idExamtype,
          jhys: this.formData.jhys,
        }
      }
      // 获取已关联的收费项目
      if (t && k) {
        if (k == 1) {
          this.onTcSearchClick((rows) => {
            if (rows.length > 0) {
              var row = {}
              rows.forEach((el) => {
                if (el.tcid == t) {
                  row = el
                }
              })
              this.formData.idTjtc = row.id
              this.formData.tjtcmc = row.tjtcmc
              if (!this.isOnline) {
                this.$refs.searchTjtc.initData(row.id)
              }
              this.onTjtcChanged(row)
            } else {
              this.$modal.alertWarning('会员卡绑定的套餐不存在！', '提醒')
            }
          })
        } else if (k == 2) {
          // this.onTcSearchClick(() => {
          //   this.$refs.searchTjtc.initData(this.formData.idTjtc)
          // })
          // 组任选，重新计算价格
          this.tableList = t.examfeeitemData
          // this.tableList = this.computePrice(t.examfeeitemData, true)
          if (t.addItemData) {
            this.tableList = [...this.tableList, ...t.addItemData]
          }
          this.loading = false
        }
      }
    },
    // 针对任意选计算价格
    computePrice(examfeeitemData, isFirst) {
      let isType3 = false
      examfeeitemData.forEach((el) => {
        if (el.groupType == 2 || this.totalMoney) {
          isType3 = true
        }
      })
      if (!isType3) {
        return examfeeitemData
      }
      // 套餐初始优惠价格，要始终等于这个
      let totalMoney = 0
      // 套餐非任意选原始价格和
      let originalMoney = 0
      // 套餐任意选项目优惠价格和，任意选不可打折
      let originalMoney2 = 0
      examfeeitemData.forEach((item) => {
        if (item.isMintc) {
          if (!item.groupType) {
            totalMoney += Number(item.factprice)
            originalMoney += Number(item.price)
          } else {
            originalMoney2 += Number(item.factprice)
          }
        }
      })
      if (isFirst) {
        this.totalMoney = totalMoney
      } else {
        totalMoney = this.totalMoney
      }
      let totalMoney2 = 0
      examfeeitemData.forEach((item) => {
        if (item.isMintc) {
          if (!item.groupType) {
            item.factprice = ((item.price / originalMoney) * (totalMoney - originalMoney2)).toFixed(2)
          }
          totalMoney2 += Number(item.factprice)
        }
      })
      if (totalMoney2 != totalMoney) {
        let lastIndex = undefined
        examfeeitemData.forEach((el, index) => {
          if (!el.groupType && el.isMintc) {
            lastIndex = index
          }
        })
        examfeeitemData[lastIndex].factprice = Number(examfeeitemData[lastIndex].factprice) + Number(totalMoney - totalMoney2)
      }
      return examfeeitemData
    },
    // 体检套餐改变时方法
    onTjtcChanged(sel) {
      if (!sel) {
        this.formData.idTjtc = ''
        this.formData.tjtcmc = ''
        this.onClearClick(1)
        return
      }
      // 判断套餐适用性别
      var idsex = this.formData.idSex
      var sysex = sel.syxb
      var isc = false
      // 年龄区间匹配
      var nlz = sel.nlz
      var nld = sel.nld
      var age = this.formData.age
      if (sysex != 2 && idsex != sysex) {
        this.$alert(
          `选中的套餐 <font color='red'>${sel.tjtcmc}</font> 不适用 <font color='red'>${idsex == 0 ? '男' : '女'}
          </font> 性别！确定选择该套餐吗？`,
          '提示',
          {
            dangerouslyUseHTMLString: true,
            showCancelButton: true,
          }
        )
          .then(() => {
            if (age < nlz || age > nld) {
              this.$alert("选中的套餐 <font color='red'>" + sel.tjtcmc + "</font> 需要体检者的年龄在 <font color='red'>" + nlz + "</font>岁 - <font color='red'>" + nld + '</font>岁 之间！', '提示', {
                dangerouslyUseHTMLString: true,
              })
              isc = true
            }

            //是否存在问题
            if (isc) {
              // 团体名称、团体分组赋值
              this.formData.idOrg = ''
              this.formData.orgName = ''
              this.formData.idOrgreservationgroup = ''
              this.formData.orgreservationgroupname = ''
              // 订单号
              this.formData.ddh = ''
              this.formData.idTjtc = ''
              this.formData.tjtcmc = ''
              if (!this.isOnline) {
                this.$refs.searchTjtc.initData()
              }
              if (this.tableList.length && this.formData.patientcode) {
                this.removeList = []
                this.tableList.forEach((el) => {
                  if (el.id) {
                    el._state = 'removed'
                    this.removeList.push(el)
                  }
                })
              }
              this.tableList = []
              return
            }
            this.formData.idTjtc = sel.id
            this.formData.tjtcmc = sel.tjtcmc
            // 接害因素
            this.$set(this.formData, 'jhys', sel.jhysV)
            this.$set(this.formData, 'idExamclass', sel.idExamclass || 0)
            this.formData.harmName = sel.jhys
            this.$nextTick(() => {
              if (this.formData.jhys && this.formData.idExamtype != 0) {
                var jhysList = this.formData.jhys.split(',')
                var harmNameList = this.formData.harmName.split(',')
                var jhysData = []
                jhysList.forEach((el, i) => {
                  jhysData.push({
                    id: el,
                    label: harmNameList[i],
                  })
                })
                this.$refs.searchJhys.initData(jhysData)
              }
            })
            // 设置体检类别
            var zytjlb = this.medicalTypeList
            for (var i in zytjlb.length) {
              if (zytjlb[i].text == sel.zytjlb) {
                this.$set(this.formData, 'medicaltype', zytjlb[i].id)
                break
              }
            }
            // 获取套餐的收费项目
            var query = {
              tcid: this.formData.idTjtc,
              idOrder: this.formData.ddh,
              idGroup: this.formData.idOrgreservationgroup,
            }
            getExamfeeitemData(query).then(({ data }) => {
              if (data.length > 0) {
                var gData = this.tableList
                if (gData.length > 0) {
                  if (this.tableList.length && this.formData.patientcode) {
                    this.removeList = []
                    this.tableList.forEach((el) => {
                      if (el.id) {
                        el._state = 'removed'
                        this.removeList.push(el)
                      }
                    })
                  }
                  this.$refs.table.clearSelection()
                  this.selectTable = []
                  this.tableList = []
                }
                this.tableList = data
                this.getTcPrice()
                this.formData.idOrgreservationgroup = data[0].idOrgreservationgroup
                this.formData.orgreservationgroupname = data[0].orgreservationgroupname
                this.onSexChanged()
              }
            })
          })
          .catch(() => {
            // this.$refs.searchTjtc.initData(this.formData.idTjtc)
            this.formData.patientnamepinyin = null
            this.formData.idFeetype = null
          })
      } else {
        if (age < nlz || age > nld) {
          this.$alert("选中的套餐 <font color='red'>" + sel.tjtcmc + "</font> 需要体检者的年龄在 <font color='red'>" + nlz + "</font>岁 - <font color='red'>" + nld + '</font>岁 之间！', '提示', {
            dangerouslyUseHTMLString: true,
          })
          isc = true
        }
        //是否存在问题
        if (isc) {
          // 团体名称、团体分组赋值
          this.formData.idOrg = ''
          this.formData.orgName = ''
          this.formData.idOrgreservationgroup = ''
          this.formData.orgreservationgroupname = ''
          // 订单号
          this.formData.ddh = ''
          this.formData.idTjtc = ''
          this.formData.tjtcmc = ''
          if (!this.isOnline) {
            this.$refs.searchTjtc.initData()
          }
          if (this.tableList.length && this.formData.patientcode) {
            this.removeList = []
            this.tableList.forEach((el) => {
              if (el.id) {
                el._state = 'removed'
                this.removeList.push(el)
              }
            })
          }
          this.tableList = []
          return
        }
        this.formData.idTjtc = sel.id
        this.formData.tjtcmc = sel.tjtcmc
        // 接害因素
        this.$set(this.formData, 'jhys', sel.jhysV)
        this.$set(this.formData, 'idExamclass', sel.idExamclass || 0)
        this.formData.harmName = sel.jhys
        this.$nextTick(() => {
          if (this.formData.jhys && this.formData.idExamtype != 0) {
            var jhysList = this.formData.jhys.split(',')
            var harmNameList = this.formData.harmName.split(',')
            var jhysData = []
            jhysList.forEach((el, i) => {
              jhysData.push({
                id: el,
                label: harmNameList[i],
              })
            })
            this.$refs.searchJhys.initData(jhysData)
          }
        })
        // 设置体检类别
        var zytjlb = this.medicalTypeList
        for (var i in zytjlb.length) {
          if (zytjlb[i].text == sel.zytjlb) {
            this.$set(this.formData, 'medicaltype', zytjlb[i].id)
            break
          }
        }
        // 获取套餐的收费项目
        var query = {
          tcid: this.formData.idTjtc,
          idOrder: this.formData.ddh,
          idGroup: this.formData.idOrgreservationgroup,
        }
        getExamfeeitemData(query).then((res) => {
          var data = res.data
          if (data.length > 0) {
            var gData = this.tableList
            if (gData.length > 0) {
              if (this.tableList.length && this.formData.patientcode) {
                this.removeList = []
                this.tableList.forEach((el) => {
                  if (el.id) {
                    el._state = 'removed'
                    this.removeList.push(el)
                  }
                })
              }
              this.$refs.table.clearSelection()
              this.selectTable = []
              this.tableList = []
            }
            this.tableList = data
            this.getTcPrice()
            this.formData.idOrgreservationgroup = data[0].idOrgreservationgroup
            this.formData.orgreservationgroupname = data[0].orgreservationgroupname
            this.onSexChanged()
          }
        })
      }
    },
    // 开单医师改变
    doctorChange(value) {
      this.doctorData.bindValue = value.id
      this.formData.idOpendoctor = value.id
      this.formData.doctorapply = value.name
    },
  },
}
</script>

<style lang="scss">
.reception-registration {
  .reception-left {
    .info-box {
      width: 100%;
      padding: 8px 16px;
      padding-bottom: 0px;
      border: 1px solid #d4d6d9;
      // .el-input__inner,
      // .el-input,
      // .el-form-item {
      //   height: 22px;
      //   line-height: 22px;
      // }

      .row-item {
        display: flex !important;
        width: 100%;
        margin-bottom: 4px;
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

      .el-icon-more {
        padding: 0 8px;
        height: 100%;
        border: 0.5px transparent solid;
        background-color: transparent;
        border-radius: 2px;

        &:hover {
          cursor: not-allowed;
        }

        &:hover.allow {
          cursor: pointer;
          background-color: #f0f0f0;
          border: 0.5px #ccc solid;
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
        border: 1px solid #d4d6d9;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }

      .photo {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
    }

    .main-nav {
      display: flex;
      list-style: none;
      padding: 0;
      margin: 8px 0 0;

      li {
        position: relative;
        padding: 4px 12px;
        margin-right: 8px;
        border-radius: 5px 5px 0px 0px;
        overflow: hidden;
        background: #f7f7f7;
        border: 1px solid #d4d6d9;
        border-bottom: none;
        cursor: pointer;

        .bg {
          background: #{'var(--theme)'};
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          opacity: 0;
        }

        span {
          position: relative;
          color: #666666;
          font-size: 14px;
          line-height: 20px;
        }

        &.active {
          .bg {
            opacity: 0.07;
          }

          span {
            color: #{'var(--theme)'};
          }
        }
      }
    }

    .main-info {
      border: 1px solid #d4d6d9;
      margin-top: 0;
      margin-bottom: 8px;

      .radio-box {
        width: 130px;
        height: 33px;
        display: flex;
        align-items: center;

        .el-radio {
          margin: 0px;
          margin-right: 4px;
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

    .main-readonly {
      .el-form-item__content,
      .el-input__suffix-inner {
        pointer-events: none;
      }
    }
  }

  .reception-right {
    .notice .el-tag {
      width: 100%;
      height: 40px;
      line-height: 40px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      cursor: pointer;
    }

    .table-show {
      flex: 1;
      overflow-y: auto;

      .el-table__header th.el-table__cell {
        height: auto;
      }
      .el-table__cell {
        padding: 0 !important;
        .cell {
          padding: 0;
        }
      }
      .el-input.is-disabled {
        .el-input__inner {
          color: #666 !important;
          border: none;
          border-color: transparent !important;
          background-color: transparent !important;
        }
      }
      .el-input {
        .el-input__inner {
          background-color: transparent !important;
        }
      }
      .el-input-number {
        .el-input-number__decrease,
        .el-input-number__increase {
          display: none;
        }
      }
      .el-input__inner {
        padding-left: 4px;
        padding-right: 4px;
      }

      .select-option {
        width: 100%;
        height: 28px;
        line-height: 28px;
        border-radius: 4px;
      }

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

      .check-box {
        width: 14px;
        height: 14px;
        display: inline-block;
        position: relative;
        border: 1px solid #666;
        border-radius: 2px;
        box-sizing: border-box;
        background-color: #fff;
        margin-top: 5px;
        font-size: 12px;
        line-height: 12px;

        &.select {
          background-color: #fe6939;
          border-color: #fe6939;
          color: #fff;
        }
      }
    }

    .statistics {
      display: flex;
      justify-content: space-between;
      // padding: 10px 12px;
      overflow-x: auto;

      .bg-red {
        background-color: red; /* 定义红色背景 */
      }

      .item {
        width: 100%;
        min-width: 100px;
        // padding: 8px 12px;
        background: #f7f8fa;
        border-radius: 5px;
        margin: 0 6px;
        display: flex;
        flex-direction: column;
        text-align: center;

        .title {
          font-size: 14px;
          line-height: 20px;
          color: #858586;
        }

        .number {
          font-weight: 600;
          font-size: 14px;
          line-height: 20px;
          color: #{'var(--theme)'} !important;
        }
      }
    }

    .operation {
      display: flex;
      margin-top: 2px;
      overflow-x: auto;

      .item {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100px;
        min-width: 100px;
        padding: 12px 0 10px;
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
