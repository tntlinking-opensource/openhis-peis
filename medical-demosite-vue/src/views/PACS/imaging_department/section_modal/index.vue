<!-- PACS科室模板 麦沃德科技 开发人 清风、予安 -->
<template>
  <el-row class="pacs-section-main">
    <!-- AI诊断弹窗 -->
    <AiChatBox :visible.sync="aiChatVisible" :initial-text="aiInitialText" :title="`AI ${$route.meta.title || 'PACS'}结果诊断`" :close-on-click-modal="false" @result="handleAiResult" />
    <canvas id="myCanvas" width="2560" height="1920" style="position: absolute; opacity: 0; z-index: -1"></canvas>
    <!-- 彩超科室左侧图片列 -->
    <el-col :span="3" class="pacs-section-pic" v-if="$route.meta.deptNo == 143">
      <div class="pic-list">
        <div class="pic-item" v-for="(item, index) in screenshot" :key="item.indexId" @click="selectPic(item.indexId)" @dblclick="blowUpPic(index)">
          <img :src="item.imgPath" alt="" style="width: 100%; border: 1px transparent solid" :style="item.inReport == 1 ? 'border:1px #d41318 solid' : ''" />
          <i class="el-icon-success" v-if="item.inReport == 1"></i>
          <!-- <i class="el-icon-error" @click.stop="deletePic(item.indexId)" v-else></i> -->
        </div>
      </div>
    </el-col>
    <el-image ref="previewImgCC" style="display: none" :src="imgSrc" :preview-src-list="screenshotPath" :initial-index="imgIndex"></el-image>
    <!-- 左侧 -->
    <el-col :span="$route.meta.deptNo != 143 ? 15 : 11" class="pacs-section-left">
      <div class="left-box">
        <!-- 左上部分 -->
        <!-- 彩超科室 -->
        <div class="color-ultrasound" v-if="$route.meta.deptNo == 143">
          <div style="overflow-x: auto; flex: 2; display: flex; height: 225px">
            <div class="video-box">
              <!-- <video id="videoItem" :src="require('@/assets/images/movie.mp4')" autoplay loop muted></video> -->
              <video id="videoItem" autoplay muted></video>
            </div>
            <!-- 操作按钮 -->
            <el-row :gutter="10" class="mb8" style="width: 100px; margin: 16px 0 0 16px">
              <el-col :span="1.5">
                <el-button size="mini" type="primary" :icon="cutBtn == '截图' ? 'el-icon-scissors' : 'el-icon-upload'" plain @click="cutPicture">{{ cutBtn }}</el-button>
              </el-col>
              <!-- <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-upload2" plain @click="handleSavePic" v-hasPermi="['PACS:department:upload']">上传</el-button>
              </el-col> -->
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-time" plain :disabled="!idsP.length" @click="historyWindow">历史</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-plus" plain :disabled="!idsP.length" @click="handleCopy">复制</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="danger" icon="el-icon-search" plain @click="handlePreview" :disabled="!idsP.length">预览</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-printer" plain @click="generateReport" :disabled="!idsP.length">生成</el-button>
              </el-col>
            </el-row>
          </div>
          <div style="flex: 1">
            <el-input class="textarea" v-model="approvalForm.history" readonly type="textarea" resize="none"></el-input>
          </div>
        </div>
        <!-- 其他科室 -->
        <el-row v-else type="flex" class="other-section">
          <!-- 左上左 -->
          <el-col :span="14" style="margin-right: 4px; background-color: #ffffff">
            <!-- 操作按钮 -->
            <el-row :gutter="10" class="mb8" style="margin: 16px 0 0 16px">
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-picture-outline" plain :disabled="!imgList.length" @click="handleHD">高清屏</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-picture-outline" :disabled="!imgList.length" plain @click="bigPhotoWindow">看大图</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-zoom-in" :disabled="!imgList.length" plain @click="bigPhotoWindow1">放大查看</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-thumb" :disabled="!imgList.length" plain @click="handleXT">报告选图</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-thumb" :disabled="!imgList.length" plain @click="handleXTQX">取消选图</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-delete" plain :disabled="!selectImgId" @click="deleteImgOpen = true">单张删除</el-button>
                <!-- 11-29 去掉已审不能单张删除逻辑 -->
                <!-- <el-button size="mini" type="primary" icon="el-icon-delete" plain :disabled="!selectImgId || !canDelete" @click="deleteImgOpen = true">单张删除</el-button> -->
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="danger" icon="el-icon-circle-close" plain :disabled="!imgList.length" @click="clearPhoto">清除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-upload2" plain :disabled="!idsI.length" @click="uploadWindow">上传DICOM</el-button>
              </el-col>
            </el-row>
            <div style="padding: 0 20px; width: 100%">
              <ul class="photo-ul bar-bgc">
                <li @click="changeImg(index, item.id)" v-for="(item, index) in imgList" :key="index" class="photo-li">
                  <div v-if="isSelected(item.id)" class="positionDiv">
                    <img class="li-img-change" :src="item.src" alt="" style="width: 180px; height: 180px" />
                    <div style="width: 28px; height: 28px; overflow: hidden" class="positionBox">
                      <input type="checkbox" checked />
                    </div>
                  </div>
                  <div v-else style="width: 180px; height: 180px; border: 1px transparent solid">
                    <img class="li-img" :src="item.src" alt="" style="width: 180px; height: 180px" />
                  </div>
                </li>
                <div class="no-pic" v-if="!imgList || !imgList.length">暂未上传图片~</div>
              </ul>
            </div>
          </el-col>
          <!-- 左上右 -->
          <el-col :span="10" class="right-box">
            <!-- 操作按钮 -->
            <el-row :gutter="10" class="mb8" style="margin: 16px 0 0 16px">
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-plus" plain :disabled="!idsP.length" @click="handleCopy">复制</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-time" plain :disabled="!idsP.length" @click="historyWindow">历史</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-plus" plain @click="queryWindow()">获取平安接口</el-button>
              </el-col>
            </el-row>
            <div style="flex: 1" class="pacs-history-input">
              <el-input class="textarea" v-model="approvalForm.history" readonly type="textarea" :rows="8" resize="none" style="height: 208px"></el-input>
            </div>
          </el-col>
        </el-row>
        <div class="pacs-person-info-main" ref="infoList">
          <!-- 左中 -->
          <div class="flex-direction-column pacs-person-info">
            <el-form class="no-weight" inline :model="physicalExaminationForm" ref="formFrist" size="small" style="margin: 0 20px">
              <el-form-item label="姓名" style="margin-right: 10px; margin-bottom: 0px">
                <div class="pacs-person-item" v-text="physicalExaminationForm.patientname" style="min-width: 70px"></div>
              </el-form-item>
              <el-form-item label="体检号" style="margin-right: 10px; margin-bottom: 0px">
                <div class="pacs-person-item" v-text="physicalExaminationForm.patientcode" style="min-width: 140px"></div>
              </el-form-item>
              <el-form-item label="性别" style="margin-right: 10px; margin-bottom: 0px">
                <div class="pacs-person-item" style="min-width: 50px" v-if="physicalExaminationForm.idSex || physicalExaminationForm.idSex === '0'" v-text="physicalExaminationForm.idSex == 0 ? '男' : '女'"></div>
              </el-form-item>
              <el-form-item label="年龄" style="margin-right: 10px; margin-bottom: 0px">
                <div class="pacs-person-item" v-text="physicalExaminationForm.age"></div>
              </el-form-item>
              <el-form-item label="" style="margin-left: 10px; margin-bottom: 0px">
                <span class="pacs-person-item" :style="physicalExaminationForm.idPatientClass != '普通会员' ? 'color:red' : ''">{{ physicalExaminationForm.idPatientClass }}</span>
              </el-form-item>
            </el-form>
            <div class="table-box">
              <el-table ref="tableDataItem" :data="tableDataItem" v-loading="loadingI" border stripe height="120px" @selection-change="handleSelectionChangeI" @row-click="rowClickI">
                <el-table-column type="selection" align="center" min-width="60"></el-table-column>
                <el-table-column type="index" align="center" label="序列" width="55"></el-table-column>
                <el-table-column prop="itemName" align="center" min-width="220" label="收费项目" show-overflow-tooltip></el-table-column>
                <el-table-column prop="fExamstarted	" align="center" min-width="80" label="状态">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.fExamstarted == '1'" type="success">已检</el-tag>
                    <el-tag v-else type="danger">未检</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="chargeTime" align="center" min-width="140" label="收费时间" show-overflow-tooltip></el-table-column>
                <el-table-column prop="partName" align="center" min-width="200" label="检查部位" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
          </div>
          <!-- 左下 -->
          <div style="width: 100%; flex: auto; background: #ffffff; display: flex; flex-direction: column; overflow-y: auto" id="leftHeight">
            <el-form inline :model="queryParams" ref="formSecond" size="small" style="margin: 8px 0 0" class="no-margin-bottom">
              <el-form-item label="体检号" style="margin-bottom: 8px; margin-left: 10px">
                <el-input v-model="queryParams.patientcode" clearable style="width: 155px" size="small" @keyup.enter.native="handleSearchP(true)"></el-input>
              </el-form-item>
              <el-form-item label="" style="margin-bottom: 8px; margin-left: 10px">
                <el-input v-model="queryParams.patientname" clearable style="width: 120px" size="small" @keyup.enter.native="handleSearchP(true)" placeholder="请输入姓名"></el-input>
              </el-form-item>
              <el-form-item label="选择日期" style="margin-bottom: 8px">
                <el-date-picker v-model="queryParams.date" style="width: 260px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleSearchP(false)"></el-date-picker>
              </el-form-item>
              <template v-if="$route.meta.deptNo != 143">
                <el-form-item label="有图" label-width="50px" style="margin-bottom: 8px">
                  <el-checkbox v-model="queryParams.pic" @change="handleHavePic(1)"></el-checkbox>
                </el-form-item>
                <el-form-item label="无图" label-width="50px" style="margin-bottom: 8px">
                  <el-checkbox v-model="queryParams.noPic" @change="handleHavePic(0)"></el-checkbox>
                </el-form-item>
              </template>
              <el-form-item>
                <el-button type="primary" size="mini" @click="handleSearchP(true)" icon="el-icon-search">搜索</el-button>
              </el-form-item>
            </el-form>
            <div id="table-person" style="flex: 1">
              <el-table ref="tableDataPerson" v-if="leftHeight" :height="leftHeight ? leftHeight + 'px' : '100%'" :data="tableDataPerson" v-loading="loadingP" border stripe @selection-change="handleSelectionChangeP" @row-click="rowClickP">
                <el-table-column type="selection" align="center" min-width="60"></el-table-column>
                <el-table-column prop="rownum" align="center" label="序列" width="55"></el-table-column>
                <el-table-column prop="patientcode" align="center" min-width="140" label="体检号"></el-table-column>
                <el-table-column prop="patientname" align="center" min-width="80" label="姓名" show-overflow-tooltip></el-table-column>
                <el-table-column prop="idSex" align="center" min-width="60" label="性别">
                  <template slot-scope="scope">
                    <span>{{ ['男', '女'][scope.row.idSex] }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="age" align="center" min-width="60" label="年龄"></el-table-column>
                <el-table-column prop="exams" align="center" min-width="200" label="检查项目" show-overflow-tooltip></el-table-column>
                <el-table-column prop="idExamtype" align="center" min-width="100" label="体检类型">
                  <template slot-scope="scope">
                    <span>{{ ['健康体检', '职业体检', '综合', '复查'][scope.row.idExamtype] }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="status" align="center" min-width="90" label="体检状态">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == 1" type="success">已审</el-tag>
                    <el-tag v-else type="danger">未审</el-tag>
                  </template>
                </el-table-column>
                <template v-if="$route.meta.deptNo != 143">
                  <el-table-column prop="jcstatus" align="center" min-width="90" label="检查状态">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.jcstatus == '0'" type="danger">无图</el-tag>
                      <el-tag v-else type="success">有图</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="img" align="center" min-width="80" label="图片"></el-table-column>
                  <el-table-column prop="orgName" align="center" min-width="166" label="团队名称" show-overflow-tooltip></el-table-column>
                </template>
              </el-table>
            </div>
            <el-form v-if="$route.meta.deptNo == 143" inline :model="physicalExaminationForm" ref="formFrist" size="small" style="margin: 0 20px">
              <el-form-item label="合计共" style="margin-right: 44px; margin-bottom: 0px">
                <div>
                  <span style="color: #0059ff; width: 100px"> {{ personTotal.total }} </span> 人
                </div>
              </el-form-item>
              <el-form-item label="已检" style="margin-right: 44px; margin-bottom: 0px">
                <div>
                  <span style="color: #0059ff; width: 100px"> {{ personTotal.examined }} </span> 人
                </div>
              </el-form-item>
              <el-form-item label="未检" style="margin-right: 44px; margin-bottom: 0px">
                <div>
                  <span style="color: #0059ff; width: 100px"> {{ personTotal.unexamined }} </span> 人
                </div>
              </el-form-item>
            </el-form>
            <!-- 分页 -->
            <pagination :page-sizes="[20, 50, 100, 200, 500]" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList(false)" />
          </div>
        </div>
      </div>
    </el-col>
    <!-- 右侧 -->
    <el-col :span="$route.meta.deptNo != 143 ? 9 : 10" class="pacs-section-right">
      <!-- 右上表格 -->
      <div class="right-table">
        <el-form inline size="small" :model="conclusionForm" style="padding-left: 16px">
          <el-form-item label="体征词" style="margin-bottom: 8px">
            <el-input placeholder="请输入体征词" v-model="conclusionForm.tzc" style="width: 140px" @keyup.enter.native="handleSearchJ"></el-input>
          </el-form-item>
          <el-form-item label="结论词" style="margin-bottom: 8px">
            <el-input placeholder="请输入结论词" v-model="conclusionForm.jlc" style="width: 140px" @keyup.enter.native="handleSearchJ"></el-input>
          </el-form-item>
          <el-form-item style="margin-bottom: 8px">
            <el-button type="primary" size="mini" icon="el-icon-search" @click="handleSearchJ">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQueryJ">重置</el-button>
          </el-form-item>
        </el-form>
        <div class="table-box" id="tableDataJLC" style="flex: 1; overflow: auto">
          <el-table ref="tableDataJLC" :data="tableDataJLC" v-loading="loadingJLC" border stripe height="100%" @selection-change="handleSelectionChangeJLC" @row-click="rowClickJLC" @select="selectJLC">
            <el-table-column type="selection" align="center" :selectable="checkSelectable" min-width="60"></el-table-column>
            <el-table-column type="index" align="center" label="序列" width="55"></el-table-column>
            <el-table-column prop="tzcname" align="center" min-width="150" label="体征词" show-overflow-tooltip> </el-table-column>
            <el-table-column prop="bodyDetail" align="center" min-width="158" label="描述" show-overflow-tooltip></el-table-column>
            <el-table-column prop="jlcName" align="center" min-width="120" label="结论词" show-overflow-tooltip></el-table-column>
            <el-table-column prop="isPositive" align="center" min-width="70" label="阳性">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.isPositive == 1"> </el-checkbox>
              </template>
            </el-table-column>
            <el-table-column prop="intensiveLevel" align="center" min-width="70" label="重症级"></el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 右下描述 -->
      <div class="right-description">
        <div style="display: flex">
          <div class="right-title" style="flex: 1">描述</div>
          <div class="right-title"><el-button size="mini" type="primary" @click="toDetail">查看</el-button></div>
        </div>
        <el-input class="textarea" v-model="approvalForm.description" :readonly="approvalForm.isAudit == 1" type="textarea" :rows="8" resize="none"></el-input>
        <div style="display: flex; align-items: center">
          <div class="summary-header">
            <div class="right-title">小结</div>
            <el-button type="primary" size="mini" icon="el-icon-cpu" class="ai-analysis-btn" @click="openAiAnalysis" :disabled="!idsP.length">AI诊断</el-button>
          </div>
          <!-- 操作按钮 -->
          <el-row :gutter="10" class="mb8" style="margin: 16px 0 0 16px">
            <el-col :span="1.5">
              <el-button size="mini" type="primary" plain @click="handleSave(1)" :disabled="!idsI.length || approvalForm.isAudit == 1">保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="primary" plain @click="handleSave(2)" :disabled="!idsI.length || approvalForm.isAudit == 1">审核</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="danger" plain @click="handleReSave" :disabled="!idsI.length || approvalForm.isAudit == 0">反审核</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="success" plain @click="handlePreview" :disabled="!idsP.length" v-if="$route.meta.deptNo != 143">预览</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="warning" plain @click="generateReport" :disabled="!idsP.length" v-if="$route.meta.deptNo != 143">生成报告</el-button>
            </el-col>
          </el-row>
        </div>
        <el-input class="textarea" v-model="approvalForm.conclusions" :readonly="approvalForm.isAudit == 1" type="textarea" :rows="4" resize="none"></el-input>
        <el-form inline size="small" :model="approvalForm" style="margin-top: 16px" label-width="70px">
          <el-form-item label="检查人">
            <input-select :disabled="approvalForm.isAudit == 1" :selectData="selectData" @change="selectChangeR" :initialValue="approvalForm.rummager" :queryParams="{ deptNo: $route.meta.deptNo }"></input-select>
          </el-form-item>
          <el-form-item label="检查时间" label-width="80px">
            <el-date-picker size="medium" style="width: 230px" v-model="approvalForm.rummagerTime" :readonly="approvalForm.isAudit == 1" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-form-item>
          <br />
          <el-form-item label="审核人">
            <input-select :disabled="approvalForm.isAudit == 1" :selectData="selectData1" @change="selectChangeW" :initialValue="approvalForm.writer" :queryParams="{ deptNo: $route.meta.deptNo }"></input-select>
          </el-form-item>
          <el-form-item label="审核时间" label-width="80px">
            <el-date-picker size="medium" style="width: 230px" v-model="approvalForm.writeTime" :readonly="approvalForm.isAudit == 1" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
    <!-- 看大图 -->
    <div v-show="ksListOpen1" style="background: #fff; color: #fff; width: 1180px;height: 500px;position: fixed;top: 0; z-index: 9999;">
      <div style="margin-top: 16px;"></div>
      <img :src="bigImgList[currentImageIndex]" style="width: 100%;height: 100%;object-fit: contain;" />
      <div style="text-align: center;">
        <el-button size="mini" type="primary" @click="prevImage">上一张</el-button>
        <el-button size="mini" type="primary" @click="ksListOpen1 = false">关闭图片</el-button>
        <el-button size="mini" type="primary" @click="nextImage">下一张</el-button>
      </div>
    </div>
    <el-image ref="previewImg" style="width: 500px;" :src="bigImgList[bigImgIndex || 0]" :preview-src-list="bigImgList"></el-image>
    <!-- 查看历史列表 -->
    <historyItem ref="historyItem"></historyItem>
    <!-- 查看详情列表 -->
    <look-detail-item ref="lookDetailItem" @update-data="handleUpdateData"></look-detail-item>
    <!-- 上传 -->
    <uploadItem ref="uploadItem" :ksID="$route.meta.deptNo" @getItemPic="getItemPic"></uploadItem>
    <!-- 选择打印技术弹窗 -->
    <generateReportItem ref="generateReportItem" :patientcode="physicalExaminationForm.patientcode"></generateReportItem>
    <!-- 选择彩超科室列表对话框 -->
    <el-dialog title="科室列表" :visible.sync="ksListOpen" width="500px" append-to-body :close-on-click-modal="false" destroy-on-close>
      <el-form v-loading="ksLoading">
        <el-form-item label="科室名称">
          <el-select v-model="ksIpId" placeholder="请选择当前所在科室">
            <el-option :label="item.name" :value="item.id" v-for="item in ksIpList" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="selectKsId">确 定</el-button>
        <el-button @click="cancelKs">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 删除单张图片对话框 -->
    <el-dialog title="删除图片" class="delete-image" :visible.sync="deleteImgOpen" width="830px" append-to-body :close-on-click-modal="false">
      <div class="delete-image-content">
        <ul class="photo-ul">
          <li @click="selectDelImg(item.id)" v-for="(item, index) in imgList" :key="index" class="photo-li">
            <div v-if="deleteIds.indexOf(item.id) > -1" class="positionDiv">
              <img class="li-img-change" :src="item.src" alt="" style="width: 180px; height: 180px" />
              <div style="width: 28px; height: 28px; overflow: hidden" class="positionBox">
                <input type="checkbox" checked />
              </div>
            </div>
            <div v-else style="width: 180px; height: 180px; border: 1px transparent solid">
              <img class="li-img" :src="item.src" alt="" style="width: 180px; height: 180px" />
            </div>
          </li>
        </ul>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="selectDelImgAll">全 选</el-button>
        <el-button type="primary" plain @click="selectDelImgCancel">取消选中</el-button>
        <el-button type="primary" @click="handleDelete">删 除</el-button>
        <el-button @click="deleteImgOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>

<script>
import { getListData, getTotalData, checkApi, examItemApi, searchApi, tzcListApi, imgListApi, saveOrUpdate, reverseApi, deleteImageApi, clearImageApi, uploadScreenshot, getKsIdList, readImages, setInReport } from '@/api/PACS/section_modal.js'
import { getData } from '@/api/PACS/section_modal.js'
import { getDate } from '@/utils/getDate.js'
import lookDetailItem from './look_detail.vue'
import historyItem from './history.vue'
import uploadItem from './upload.vue'
import generateReportItem from './generate_report.vue'
import AiChatBox from '@/components/AiChatBox'

export default {
  components: { historyItem, uploadItem, generateReportItem, lookDetailItem, AiChatBox },
  data() {
    return {
      // AI诊断相关
      aiChatVisible: false,
      aiInitialText: '',
      aiAnalysisResult: '',
      selectedImgIds: [], // 多选选中状态数组
      ksListOpen1: false,
      currentImageIndex: 0, // 当前显示的图片索引
      // 体检者信息
      physicalExaminationForm: {
        patientname: '',
        patientcode: '',
        age: '',
        idSex: '',
        idPatientClass: '',
      },
      // 左下表格筛选
      queryParams: {
        current: 1,
        size: 50,
        autoFill: true,
        patientcode: undefined,
        patientname: undefined,
        date: undefined,
        pic: false,
        noPic: false,
      },
      // 分页总数
      total: 0,
      // 左下体检者表格
      tableDataPerson: [],
      // 体检人数合计
      personTotal: {
        total: 0,
        examined: 0,
        unexamined: 0,
      },
      // 选中的数据
      idsP: [],
      // 加载中
      loadingP: false,
      // 左中收费项目表格
      tableDataItem: [],
      // 选中的数据
      idsI: [],
      // 选中的id，用于保存后重新选中
      idsIIndex: undefined,
      // 加载中
      loadingI: false,
      // 右上结论词筛选
      conclusionForm: {
        tzc: undefined,
        jlc: undefined,
      },
      // 右上结论词表格数据
      tableDataJLC: [],
      // 记录全部数据，用于表格筛选
      tempDataJLC: [],
      // 记录选中的id
      tempIdsJLC: [],
      // 结论词表格选中
      idsJLC: [],
      // 加载中
      loadingJLC: false,
      // 检查项目是否锁定
      isLock: false,
      // 检查人相关数据
      approvalForm: {
        // 检查项目是否审核
        isAudit: 0,
        // 历史描述及小结
        history: '',
        // 描述
        description: '',
        // 小结
        conclusions: '',
        rummagerId: undefined,
        rummager: undefined,
        rummagerTime: undefined,
        writeId: undefined,
        writer: undefined,
        writeTime: undefined,
      },
      // 检查人参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/pacs/sysytem/pacsAbteilungs/doctor/list', //请求连接
        selectWidth: 120, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'inputCode', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 审核人参数
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/pacs/sysytem/pacsAbteilungs/doctor/list', //请求连接
        selectWidth: 120, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'inputCode', //向接口传递的参数名(不传默认为'inputCode')
      },
      //
      // 截图列表
      screenshot: [],
      // 截图路径列表
      screenshotPath: [],
      imgSrc: '',
      imgIndex: 0,
      // 上传截图列表
      fileList: [],

      tableData: [],
      loading: false,
      change: false,
      // 图片列表
      imgList: [],
      // 选中图片id
      selectImgId: null,
      // 已检不能执行单张删除
      canDelete: true,
      // 打开删除单张图片对话框
      deleteImgOpen: false,
      // 删除对话框选中id
      deleteIds: [],
      // 是否连接完成采集卡
      isConnect: false,
      // 是否截图中
      cutId: 1,
      // 截图按钮名称
      cutBtn: '截图',
      // 打开科室列表对话框
      ksListOpen: false,
      // 选择科室加载中
      ksLoading: false,
      // 传图科室id列表
      ksIpList: [],
      // 传图科室id
      ksIpId: undefined,
      // 切换pacs图片节流操作
      isToggle: false,
      // 双击延时
      dbTimer: undefined,
      // 沃德审核后自动切换到下一项目/人
      nextState: false,
      // 审核及保存计时器
      auditTimer: false,
      // 看大图列表
      bigImgList: [],
      // 索引
      bigImgIndex: 0,
      // 保存当前选择的图片列表
      tempPicList: [],
      // 当前科室编号
      deptNo: '',
      // 左下表格高度
      leftHeight: 0,
    }
  },
  watch: {
    screenshot: {
      immediate: true,
      handler(newVal) {
        this.screenshotPath = newVal.map((item) => item.imgPath)
      },
    },
  },
  created() {
    localStorage.setItem('PACS_Index', 1)
    this.deptNo = this.$route.meta.deptNo
    this.queryParams.date = [getDate().split(' ')[0], getDate().split(' ')[0]]
    this.approvalForm.rummager = decodeURIComponent(this.$getCookie('username'))
    this.approvalForm.rummagerTime = getDate()
    this.approvalForm.writer = decodeURIComponent(this.$getCookie('username'))
    this.approvalForm.writeTime = getDate()
    this.connectCard()
    if (this.$route.params.patientcode) {
      this.queryParams.patientcode = this.$route.params.patientcode
      this.queryParams.date = []
      this.getList(true)
    } else {
      this.getList(false)
    }
  },
  mounted() {
    document.addEventListener('keydown', this.handleWatch)
    this.leftHeight = document.getElementById('table-person').offsetHeight - 50
  },
  activated() {
    this.connectCard()
    if (this.$route.meta.deptNo == '24') {
      localStorage.setItem('DRChange', true)
    } else {
      localStorage.setItem('DRChange', false)
    }
  },
  methods: {
    // 打开AI诊断弹窗
    openAiAnalysis() {
      // 检查是否有患者信息
      if (!this.idsP || this.idsP.length === 0) {
        this.$message.warning('没有顾客信息，无法进行AI诊断')
        return
      }

      // 收集科室和体检者信息
      const departmentInfo = this.$route.meta.title || 'PACS'
      const patientInfo = this.physicalExaminationForm

      // 收集检查结果数据
      const description = this.approvalForm.description || ''
      const conclusions = this.approvalForm.conclusions || ''

      // 收集结论词数据
      let resultsTable = ''
      // if (this.tableDataJLC && this.tableDataJLC.length > 0) {
      //   resultsTable = '检查项目 | 体征词 | 结论词 | 阳性 | 重症级\n'
      //   resultsTable += '--- | --- | --- | --- | ---\n'

      //   this.tableDataJLC.forEach(item => {
      //     resultsTable += `${item.bodyPart || ''} | ${item.tzcname || ''} | ${item.jlcName || ''} | ${item.isPositive == 1 ? '是' : '否'} | ${item.intensiveLevel || ''}\n`
      //   })
      // }

      // 如果没有表格数据，但有描述或小结，也使用这些数据
      if (resultsTable.trim() === '' && (description || conclusions)) {
        if (description) {
          resultsTable += `描述：\n${description}\n\n`
        } else {
          if (conclusions) {
            resultsTable += `小结：\n${conclusions}`
          }
        }

      }

      // 准备AI诊断的提示文本
      const promptText = `您好，请对以下${departmentInfo}结果进行诊断和分析：

顾客信息：
- 姓名：${patientInfo.patientname || '未知'}
- 性别：${patientInfo.idSex == 0 ? '男' : patientInfo.idSex == 1 ? '女' : '未知'}
- 年龄：${patientInfo.age || '未知'}

检查结果：
${resultsTable}

请对以上检查结果进行诊断，并提供以下内容：

## 小结
请列出所有异常值及其偏离正常范围的程度。

## 结论
这些异常值可能指向的健康问题和趋势，以及是否需要进一步检查或注意事项。

## 健康建议
有哪些具体的建议可以改善这些指标，包括生活方式、饮食和可能的药物或治疗方案。`;

      // 设置初始文本并打开对话框
      this.aiInitialText = promptText;
      this.aiChatVisible = true;
    },

    // 处理AI诊断结果
    handleAiResult(result) {
      this.aiAnalysisResult = result;

      // 如果小结为空，则直接将AI结果应用到小结中
      if (!this.approvalForm.conclusions) {
        this.approvalForm.conclusions = result;
        this.$message.success('AI诊断结果已应用到小结');
      } else {
        // 否则弹出确认框询问是否覆盖原有小结
        this.$confirm('是否将AI诊断结果追加到当前小结中？', '提示', {
          confirmButtonText: '追加',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.approvalForm.conclusions += '\n\n--- AI诊断结果 ---\n' + result;
          this.$message.success('AI诊断结果已追加到小结');
        }).catch(() => {
          this.$message.info('已取消应用AI诊断结果');
        });
      }
    },

    // 处理修改后的值
    handleUpdateData(description, conclusions) {
      this.approvalForm.description = description
      this.approvalForm.conclusions = conclusions
    },
    // 查看描述详情
    toDetail() {
      this.$refs.lookDetailItem.toDetail(this.approvalForm.description, this.approvalForm.conclusions)
    },
    // 查看历史
    historyWindow() {
      this.$refs.historyItem.historyWindow(this.idsP[0].patientcode)
    },
    // 监视键盘按下事件
    handleWatch(e) {
      var key = window.event ? e.keyCode : e.which
      if (this.$route.meta.deptNo == 143) {
        if (this.cutBtn == '截图') {
          if (key === 120) {
            this.cutPicture()
            // } else if (key === 121) {
          } else if (key === 119) {
            // this.handleSavePic()
            this.handleSave(1)
          }
        }
      } else {
        if (!this.isToggle) {
          if (this.$route.meta.deptNo != this.deptNo) {
            return
          }
          this.isToggle = true
          setTimeout(() => {
            if (key == 38 || key == 37) {
              this.handleTogglePic('up')
            } else if (key == 40 || key == 39) {
              this.handleTogglePic('down')
            }
            this.isToggle = false
          }, 20)
        }
      }
    },
    // 连接采集卡
    connectCard() {
      if (this.$route.meta.deptNo == 143) {
        this.isConnect = false
        this.$nextTick(() => {
          navigator.mediaDevices
            .getUserMedia({
              video: true,
              audio: false,
            })
            .then(async (stream) => {
              const video = document.getElementById('videoItem')
              // 兼容性监测
              if ('srcObject' in video) {
                video.srcObject = stream
              } else {
                // 在支持srcObject的浏览器上，不再支持使用这种方式
                video.src = URL.createObjectURL(stream)
              }
              await video.play()
              this.isConnect = true
              this.cutBtn = '截图'
            })
            .catch((error) => {
              console.error(error)
              this.$alert('采集卡连接失败,将采用手动传图方式上传截图。', '提示')
              this.cutBtn = '传图'
            })
        })
      }
    },
    // 获取体检列表
    getList(select, selectFirst) {
      this.loadingP = true
      let query = JSON.parse(JSON.stringify(this.queryParams))
      if (this.queryParams.date) {
        query.startDate = this.queryParams.date[0] + ' 00:00:00'
        query.endDate = this.queryParams.date[1] + ' 23:59:59'
      } else {
        query.startDate = undefined
        query.endDate = undefined
      }
      if (this.queryParams.pic) {
        query.jcstatus = 1
      } else if (this.queryParams.noPic) {
        query.jcstatus = 0
      } else {
        query.jcstatus = undefined
      }
      getListData({
        deptNo: this.$route.meta.deptNo,
        ...query,
      })
        .then(({ data }) => {
          this.tableDataPerson = data.records
          this.total = data.total
          this.loadingP = false
          if (select && this.queryParams.patientcode && data.records.length) {
            this.$nextTick(() => {
              this.queryParams.patientcode = data.records[0].patientcode
              this.$refs.tableDataPerson.clearSelection()
              if (this.tableDataPerson.length) {
                this.$refs.tableDataPerson.toggleRowSelection(this.tableDataPerson[0], true)
              }
            })
          }
          if (selectFirst) {
            this.$nextTick(() => {
              this.$refs.tableDataPerson.clearSelection()
              if (this.tableDataPerson.length) {
                this.$refs.tableDataPerson.toggleRowSelection(this.tableDataPerson[0], true)
              }
            })
          }
          localStorage.setItem('PACS_Index', query.current)
          localStorage.setItem('PACS_DeptNo', this.$route.meta.deptNo)
          if (this.$route.meta.deptNo == '24') {
            let DRList = []
            data.records.forEach((el) => {
              if (el.dicomImgs) {
                DRList = [...DRList, ...el.dicomImgs.split(',')]
              }
            })
            localStorage.setItem('DRChange', true)
            localStorage.setItem('DRList', JSON.stringify(DRList))
          } else {
            localStorage.setItem('DRChange', false)
          }
        })
        .catch(() => {
          this.loadingP = false
        })
      if (this.$route.meta.deptNo == 143) {
        getTotalData({
          deptNo: this.$route.meta.deptNo,
          ...query,
        }).then(({ data }) => {
          this.personTotal = data
        })
      }
    },
    // 变更有图、无图
    handleHavePic(state) {
      if (state) {
        this.queryParams.noPic = false
      } else {
        this.queryParams.pic = false
      }
    },
    // 执行体检者列表搜索
    handleSearchP(select, selectFirst) {
      this.queryParams.current = 1
      this.getList(select, selectFirst)
    },
    // 选中体检者表格
    handleSelectionChangeP(selection) {
      this.idsP = selection.map((item) => item)
      this.single = selection.length != 1
      this.idsIIndex = undefined
      if (selection.length == 1) {
        this.$nextTick(() => {
          this.physicalExaminationForm = this.idsP[0]
          this.handleCheck()
          this.$refs.infoList.scrollTop = 0
        })
      } else if (selection.length > 1) {
        this.$refs.tableDataPerson.clearSelection() //清空表格数据
        this.$refs.tableDataPerson.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 单击体检者表格
    rowClickP(row) {
      this.$refs.tableDataPerson.clearSelection() //清空表格数据
      this.$refs.tableDataPerson.toggleRowSelection(row, true)
    },
    // 查询体检者状态，如：体检者已总检不能修改
    handleCheck() {
      // this.$refs.tableDataItem.clearSelection() //清空表格数据
      // this.idsIIndex = undefined
      checkApi({
        patientcode: this.idsP[0].patientcode,
      })
        .then(() => {
          this.handleExamItemApi()
        })
        .catch(() => {
          this.isLock = true
          this.tableDataItem = []
          this.handleExamItemApi()
        })
    },
    // 查询体检者检查项目
    handleExamItemApi() {
      this.loadingI = true
      examItemApi({
        patientcode: this.idsP[0].patientcode,
        deptNo: this.$route.meta.deptNo,
      }).then(({ data }) => {
        this.tableDataItem = data
        this.loadingI = false
        if (this.idsIIndex) {
          if (!this.nextState) {
            this.tableDataItem.forEach((el) => {
              if (this.idsIIndex == el.itemId) {
                this.$nextTick(() => {
                  this.$refs.tableDataItem.toggleRowSelection(el, true)
                })
              }
            })
          } else {
            let allReady = true
            let nextIndex = 0
            this.tableDataItem.forEach((el, index) => {
              if (el.fExamstarted != 1) {
                allReady = false
                nextIndex = index
              }
            })
            if (!allReady) {
              this.nextState = false
              this.$nextTick(() => {
                this.$refs.tableDataItem.toggleRowSelection(this.tableDataItem[nextIndex], true)
              })
            } else {
              this.$refs.tableDataPerson.clearSelection()
              this.$refs.tableDataItem.clearSelection()
              this.handleSearchP(false, true)
              // this.tableDataPerson.forEach((val, index2) => {
              //   if (val.id == this.idsP[0].id) {
              //     if (index2 == this.tableDataPerson.length - 1) {
              //       return
              //     } else if (this.nextState) {
              //       this.$refs.tableDataPerson.toggleRowSelection(this.tableDataPerson[index2 + 1], true) //最后一条数据
              //       this.nextState = false
              //     }
              //   }
              // })
            }
            localStorage.setItem('PACS_DeptNo', this.$route.meta.deptNo)
            if (this.$route.meta.deptNo == '24') {
              localStorage.setItem('DRChange', true)
            } else {
              localStorage.setItem('DRChange', false)
            }
          }
        } else {
          this.$nextTick(() => {
            if (this.tableDataItem.length) {
              this.$refs.tableDataItem.toggleRowSelection(this.tableDataItem[0], true)
            }
          })
        }
      })
    },
    // 选中收费项目表格
    handleSelectionChangeI(selection) {
      this.idsI = selection.map((item) => item)
      if (selection.length == 1) {
        this.tableDataItem.forEach((el) => {
          if (el.itemId == this.idsI[0].itemId) {
            this.idsIIndex = el.itemId
          }
        })
      } else if (selection.length > 1) {
        this.$refs.tableDataItem.clearSelection() //清空表格数据
        this.$refs.tableDataItem.toggleRowSelection(selection.pop()) //最后一条数据
      }
      if (this.idsI.length) {
        if (this.idsI[0].fExamstarted == 1) {
          this.canDelete = false
        } else {
          this.canDelete = true
        }
        // 选择收费项目，展示小结描述等信息
        searchApi({
          feeitemId: this.idsI[0].id,
        }).then(({ data }) => {
          this.approvalForm = data.pacsAbteilungItemSearchMainVo
          this.approvalForm.history = '历史描述:\n' + (data.pacsAbteilungItemSearchHistoryVo.description || '暂无') + '\n历史小结:\n' + (data.pacsAbteilungItemSearchHistoryVo.conclusions || '暂无')
          if (!this.approvalForm.rummagerTime) {
            this.approvalForm.rummagerTime = getDate()
          }
          if (!this.approvalForm.rummager) {
            this.approvalForm.rummagerId = this.$getCookie('userNo')
            this.approvalForm.rummager = decodeURIComponent(this.$getCookie('username'))
            this.approvalForm.rummagerName = decodeURIComponent(this.$getCookie('username'))
            let pacsRummagerInfo = localStorage.getItem('pacsRummagerInfo')
            if (pacsRummagerInfo) {
              this.approvalForm.rummagerId = JSON.parse(pacsRummagerInfo).rummagerId
              this.approvalForm.rummager = JSON.parse(pacsRummagerInfo).rummager
              this.approvalForm.rummagerName = JSON.parse(pacsRummagerInfo).rummagerName
            }
          }
          if (!this.approvalForm.writeTime) {
            this.approvalForm.writeTime = getDate()
          }
          if (!this.approvalForm.writer || !data.pacsAbteilungItemSearchMainVo.isAudit) {
            this.approvalForm.writeId = this.$getCookie('userNo')
            this.approvalForm.writer = decodeURIComponent(this.$getCookie('username'))
            let pacsWriteInfo = localStorage.getItem('pacsWriteInfo')
            if (pacsWriteInfo) {
              this.approvalForm.writeId = JSON.parse(pacsWriteInfo).writeId
              this.approvalForm.writer = JSON.parse(pacsWriteInfo).writer
            }
          }

          // 选择收费项目，展示体征词列表查询
          this.loadingJLC = true
          tzcListApi({
            patientcode: this.idsP && this.idsP.length ? this.idsP[0].patientcode : this.physicalExaminationForm.patientcode,
            itemId: this.idsI[0].itemId,
          })
            .then(({ data }) => {
              this.tableDataJLC = data
              this.tempDataJLC = JSON.parse(JSON.stringify(data))
              this.conclusionForm = {
                tzc: undefined,
                jlc: undefined,
              }
              this.$nextTick(() => {
                this.$refs.tableDataJLC.clearSelection() //清空表格数据
                let isSelect = false
                if (!this.approvalForm.description) {
                  this.approvalForm.description = ''
                }
                if (!this.approvalForm.conclusions) {
                  this.approvalForm.conclusions = ''
                }
                let description = JSON.parse(JSON.stringify(this.approvalForm.description))
                let conclusions = JSON.parse(JSON.stringify(this.approvalForm.conclusions))
                this.tableDataJLC.forEach((el) => {
                  if (el.isSelected == 1) {
                    this.$refs.tableDataJLC.toggleRowSelection(el, true)
                    this.tempIdsJLC.push(el.tzcid)
                    isSelect = true
                    setTimeout(() => {
                      if (el.bodyDetail != null && !description) {
                        this.approvalForm.description += el.bodyDetail
                        if (!el.bodyDetail.slice(-2).includes('\n')) {
                          this.approvalForm.description += '\n'
                        }
                      }
                      if (el.tzcname != null && !conclusions) {
                        this.approvalForm.conclusions += el.tzcname + '\n'
                      }
                    }, 500)
                  }
                })

                if (!isSelect) {
                  this.tableDataJLC.forEach((el) => {
                    if (el.isDefault == 1) {
                      this.$refs.tableDataJLC.toggleRowSelection(el, true)
                      this.tempIdsJLC.push(el.tzcid)
                      setTimeout(() => {
                        if (el.bodyDetail != null && !description) {
                          this.approvalForm.description += el.bodyDetail
                          if (!el.bodyDetail.slice(-2).includes('\n')) {
                            this.approvalForm.description += '\n'
                          }
                        }
                        if (el.tzcname != null && !conclusions) {
                          this.approvalForm.conclusions += el.tzcname + '\n'
                        }
                      }, 500)
                    }
                  })
                }
                // this.selectJLC()
              })
              this.loadingJLC = false
            })
            .catch(() => {
              this.loadingJLC = false
            })
        })
        this.imgList = []
        // 选择收费项目，获取当前检查项目图片
        this.getItemPic()
      } else {
        this.idsIIndex = undefined
        this.tableDataJLC = []
        this.approvalForm = {
          // 检查项目是否审核
          isAudit: 0,
          // 描述
          description: '',
          // 小结
          conclusions: '',
          rummagerId: undefined,
          rummager: undefined,
          rummagerTime: undefined,
          writeId: undefined,
          writer: undefined,
          writeTime: undefined,
        }
        this.imgList = []
      }
    },
    // 选择收费项目，获取当前检查项目图片
    getItemPic() {
      const imgLoading = this.$loading({ target: '.photo-ul' })
      const imgUrl = this.$getCookie('imgPath')
      imgListApi({
        feeitemId: this.idsI[0].id,
      })
        .then(({ data }) => {
          if (this.$route.meta.deptNo == 143) {
            data.forEach((el) => {
              el.indexId = this.cutId++
              el.imgPath = imgUrl + el.src
              this.fileList.push(el)
            })
            this.screenshot = data.reverse()
          } else {
            data.forEach((el) => {
              el.src = imgUrl + el.src
              if (el.dcmsrc) {
                var dcmData = []
                dcmData = el.dcmsrc.split(',')
                el.dcmsrc = dcmData[0]
                if (dcmData.length > 1) {
                  el.dcmInfo = dcmData.splice(1, dcmData.length).join(',')
                }
                // el.windowWidth = dcmData[1]
                // el.windowCenter = dcmData[2]
              }
            })

            this.imgList = data
            this.selectedImgIds = []
            // 遍历 imgList，处理回显选中状态
            this.imgList.forEach((item) => {
              if (item.inReport == 1) {
                // 如果 inReport 为 1，则将 id 添加到 selectedImgIds
                this.selectedImgIds.push(item.id);
              }
            });
            if (data.length) {
              this.tempPicList = data
              localStorage.setItem('is_update_Dicom', 1)
              localStorage.setItem('PACS_Dicom', JSON.stringify(data))
            }
            if (this.imgList && this.imgList.length > 0) {
              // this.changeImg(0, this.imgList[0].id)
              // 隐藏默认选中第一个图片
            }
          }
          imgLoading.close()
        })
        .catch((error) => {
          console.error(error)
          imgLoading.close()
        })
    },
    // 单击收费项目表格
    rowClickI(row) {
      this.$refs.tableDataItem.clearSelection() //清空表格数据
      this.$refs.tableDataItem.toggleRowSelection(row, true)
    },
    // 选中结论词表格
    handleSelectionChangeJLC(selection) {
      this.idsJLC = selection.map((item) => item)
    },
    // 单击结论词表格
    rowClickJLC(row, col) {
      if (this.approvalForm.isAudit == 1 || this.isLock) {
        return
      }
      if (this.idsJLC.map((item) => item.tzcid).includes(row.tzcid)) {
        return
      } else {
        this.selectJLC(row)
      }
      if (col?.type != 'selection') this.$refs.tableDataJLC.clearSelection() //清空表格数据
      this.$refs.tableDataJLC.toggleRowSelection(row, true)
    },
    // 勾选结论词
    selectJLC(row) {
      let _row = {}
      let idsJLC = this.idsJLC.map((item) => item.tzcid)
      if (this.approvalForm.isAudit == 1 || this.isLock) {
        return
      }
      if (Array.isArray(row)) {
        if (row.length > this.idsJLC.length) {
          _row = row[row.length - 1]
        } else {
          if (!row.length || idsJLC.includes(row[row.length - 1].tzcid)) {
            return
          } else {
            _row = row[row.length - 1]
          }
        }
      } else {
        _row = row
      }
      let jlcID = this.tableDataJLC.map((item) => item.tzcid)
      if (this.approvalForm.description === null) {
        this.approvalForm.description = ''
      }
      this.approvalForm.description += _row.bodyDetail || ''
      if (!_row.bodyDetail.slice(-2).includes('\n')) {
        this.approvalForm.description += '\n'
      }
      // }
      if (_row.tzcname != null) {
        if (this.approvalForm.conclusions === null) {
          this.approvalForm.conclusions = ''
        }
        this.approvalForm.conclusions += _row.tzcname + '\n'
      }
      if (!this.tempIdsJLC.includes(idsJLC[idsJLC.length - 1])) {
        this.tempIdsJLC.push(idsJLC[idsJLC.length - 1])
      }
    },
    // 禁用勾选结论词
    checkSelectable() {
      if (this.approvalForm.isAudit == 1) {
        return false
      } else {
        return true
      }
    },
    // 结论词搜索
    handleSearchJ() {
      if (!this.conclusionForm.tzc && !this.conclusionForm.jlc) {
        this.tableDataJLC = this.tempDataJLC
        this.tempIdsJLC.forEach((val) => {
          this.tableDataJLC.forEach((el) => {
            if (el.tzcid == val) {
              this.$nextTick(() => {
                this.$refs.tableDataJLC.toggleRowSelection(el, true)
              })
            }
          })
        })
        return
      }
      let tableData = []
      if (this.conclusionForm.tzc && this.conclusionForm.jlc) {
        this.tempDataJLC.forEach((el) => {
          if ((el?.tzcname || '').includes(this.conclusionForm.tzc) && el.jlcName.includes(this.conclusionForm.jlc)) {
            tableData.push(el)
          }
        })
      } else if (this.conclusionForm.tzc) {
        this.tempDataJLC.forEach((el) => {
          if ((el?.tzcname || '').includes(this.conclusionForm.tzc)) {
            tableData.push(el)
          }
        })
      } else if (this.conclusionForm.jlc) {
        this.tempDataJLC.forEach((el) => {
          if ((el?.jlcName || '').includes(this.conclusionForm.jlc)) {
            tableData.push(el)
          }
        })
      }
      this.tableDataJLC = tableData
      this.tempIdsJLC.forEach((val) => {
        this.tableDataJLC.forEach((el) => {
          if (el.tzcid == val) {
            this.$nextTick(() => {
              this.$refs.tableDataJLC.toggleRowSelection(el, true)
            })
          }
        })
      })
    },
    // 结论词重置搜索
    resetQueryJ() {
      this.conclusionForm = {
        tzc: undefined,
        jlc: undefined,
      }
      this.handleSearchJ()
    },
    // 检查人返回值
    selectChangeR(value) {
      this.approvalForm.rummagerId = value.id
      this.approvalForm.rummager = value.username
      this.approvalForm.rummagerName = value.username
    },
    // 审核人返回值
    selectChangeW(value) {
      this.approvalForm.writeId = value.id
      this.approvalForm.writer = value.username
    },
    // 保存、审核
    handleSave(type) {
      if (this.auditTimer) {
        return
      }
      this.auditTimer = true
      let world = ''
      if (!this.idsP.length) {
        world = '请选择体检者后再试'
      } else if (!this.idsI.length) {
        world = '请选择收费项目后再试'
      } else if (!this.approvalForm.rummagerId) {
        world = '请选择检查人后再试'
      } else if (!this.approvalForm.writeId) {
        world = '请选择审核人后再试'
      }
      if (world) {
        this.$modal.msgWarning(world)
        this.auditTimer = false
        return
      }
      let jlcdata = this.idsJLC
      // this.tempDataJLC.forEach((el) => {
      //   if (this.tempIdsJLC.includes(el.tzcid)) {
      //     el.isSelected = 1
      //     jlcdata.push(el)
      //   }
      // })
      this.approvalForm.rummagerName = this.approvalForm.rummager
      if (!this.approvalForm.description || !this.approvalForm.conclusions) {
        this.$modal.msgError('描述和小结不能为空,请填写后再试')
        this.auditTimer = false
        this.approvalForm.description = JSON.parse(JSON.stringify(this.approvalForm.description))
        this.approvalForm.conclusions = JSON.parse(JSON.stringify(this.approvalForm.conclusions))
        return
      }
      saveOrUpdate({
        deptNo: this.$route.meta.deptNo,
        feeitemId: this.idsI[0].id,
        jlcdata,
        ...this.approvalForm,
        type,
        imgs: this.screenshot,
      })
        .then(() => {
          let world = ''
          if (type == 1) {
            world = '保存'
          } else {
            world = '审核'
          }
          this.$modal.msgSuccess(world + '成功')
          // 审核成功后自动刷新页面 24-9-26 隐藏审核后刷新
          // this.getList(false)
          setTimeout(() => {
            this.auditTimer = false
          }, 2000)
          this.handleCheck()
          let pacsRummagerInfo = {
            rummagerId: this.approvalForm.rummagerId,
            rummager: this.approvalForm.rummager,
            rummagerName: this.approvalForm.rummagerName,
          }
          localStorage.setItem('pacsRummagerInfo', JSON.stringify(pacsRummagerInfo))
          let pacsWriteInfo = {
            writeId: this.approvalForm.writeId,
            writer: this.approvalForm.writer,
          }
          localStorage.setItem('pacsWriteInfo', JSON.stringify(pacsWriteInfo))
          if (this.$getCookie('cid') == '2' && type == '2') {
            this.nextState = true
          }
          this.screenshot = []
        })
        .catch((error) => {
          console.error(error)
          setTimeout(() => {
            this.auditTimer = false
          }, 200)
        })
    },
    // 反审核
    handleReSave() {
      this.$confirm('确定要反审核吗？', '提示', {})
        .then(() => {
          reverseApi({
            feeitemId: this.idsI[0].id,
          }).then(() => {
            this.$modal.msgSuccess('反审核成功')
            // 反审核成功后自动刷新页面  24-9-26 隐藏反审核后刷新
            // this.getList(false)
            this.handleCheck()
            this.tableDataPerson.forEach((el) => {
              if (el.id == this.idsP[0].id) {
                el.status = 0
              }
            })
          })
        })
        .catch(() => { })
    },
    // 高清屏
    handleHD() {
      const routeUrl = this.$router.resolve({
        name: 'HDScreen', //这里是跳转页面的name
        query: {
          //要传的参数
          feeitemId: this.idsI[0].id,
        },
      })
      // myHDWindow 不新开页面
      window.open(routeUrl.href, 'myHDWindow')
      // _blank 新开页面
      // window.open(routeUrl.href, '_blank')

    },
    // 彩超科室选中图片
    selectPic(indexId) {
      clearTimeout(this.dbTimer)
      this.dbTimer = setTimeout(() => {
        this.screenshot.forEach((el) => {
          if (el.indexId == indexId) {
            if (el.inReport) {
              el.inReport = 0
            } else {
              el.inReport = 1
            }
          }
        })
        this.fileList = []
        this.screenshot.forEach((el) => {
          if (el.inReport) {
            this.fileList.push(el)
          }
        })
      }, 200)
    },
    // 删除彩超科室图片
    deletePic(indexId) {
      for (let i = this.screenshot.length - 1; i >= 0; i--) {
        if (this.screenshot[i].indexId == indexId) {
          this.$delete(this.screenshot, i)
        }
      }
    },
    // 双击放大图片
    blowUpPic(index) {
      clearTimeout(this.dbTimer)
      this.imgIndex = index
      this.imgSrc = this.screenshot[index].imgPath
      this.$refs.previewImgCC.showViewer = true
    },
    // 判断图片是否被选中
    isSelected(id) {
      return this.selectedImgIds.includes(id);
    },
    // 选图进报告
    handleXT() {
      setInReport({
        ids: this.selectedImgIds,
        inReport: 1
      })
        .then(({ data }) => {
          if (data == true) {
            this.$modal.msgSuccess('选图成功')
          }
        })
        .catch((error) => {
          console.error(error)
          loading.close()
        })
    },
    // 选图进取消
    handleXTQX() {
      setInReport({
        ids: this.selectedImgIds,
        inReport: 0
      })
        .then(({ data }) => {
          if (data == true) {
            this.$modal.msgSuccess('取消选图成功')
          }
        })
        .catch((error) => {
          console.error(error)
          loading.close()
        })
    },
    // 其他科室选图
    changeImg(index, id) {

      // 更新多选状态
      const selectedIndex = this.selectedImgIds.indexOf(id);
      if (selectedIndex === -1) {
        // 如果未选中，则添加到选中数组
        this.selectedImgIds.push(id);
      } else {
        // 如果已选中，则从选中数组中移除
        this.selectedImgIds.splice(selectedIndex, 1);
      }

      // 兼容单选逻辑
      if (!this.imgList[index].dcmsrc) return;

      localStorage.setItem('PACS_Dicom_active', index);
      localStorage.setItem('is_update_Dicom', 1);

      let deptNo = this.$route.meta.deptNo;
      if (deptNo != localStorage.getItem('PACS_DeptNo')) {
        localStorage.setItem('PACS_Dicom', JSON.stringify(this.tempPicList));
        localStorage.setItem('PACS_DeptNo', this.$route.meta.deptNo);
      }

      if (this.$route.meta.deptNo == '24') {
        localStorage.setItem('DRChange', true);
      } else {
        localStorage.setItem('DRChange', false);
      }

      // 更新单选相关变量
      this.bigImgIndex = index;
      this.selectImgId = id;
    },
    // 删除对话框选中
    selectDelImg(id) {
      if (this.deleteIds.indexOf(id) > -1) {
        this.$delete(this.deleteIds, this.deleteIds.indexOf(id))
      } else {
        this.deleteIds.push(id)
      }
    },
    // 删除对话框全选
    selectDelImgAll() {
      this.deleteIds = this.imgList.map((item) => item.id)
    },
    // 删除对话框取消选中
    selectDelImgCancel() {
      this.deleteIds = []
    },
    //删除单张
    handleDelete() {
      if (!this.deleteIds.length) {
        this.$modal.alertWarning('请选择一张或多张图片！')
        return
      }
      this.$modal
        .confirm('是否删除当前选中的' + this.deleteIds.length + '张图片')
        .then(() => {
          return deleteImageApi(this.deleteIds.join())
        })
        .then(() => {
          this.$modal.msgSuccess('删除成功')
          this.deleteImgOpen = false
          this.getItemPic()
        })
        .catch(() => { })
    },
    //清除图片
    clearPhoto() {
      this.$alert('是否清除当前所有图片', '提示').then(() => {
        clearImageApi(this.idsI[0].id).then(() => {
          this.imgList = []
          this.selectImgId = null
          this.$modal.msgSuccess('清除成功')
        })
      })
    },
    //查看大图
    bigPhotoWindow() {
      const loading = this.$loading({
        lock: true,
        text: '加载中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      imgListApi({
        feeitemId: this.idsI[0].id,
      })
        .then(({ data }) => {
          data.forEach((el) => {
            el.src = this.$getCookie('imgPath') + el.src
          })
          this.bigImgList = data.map((item) => item.src)
          loading.close()
        })
        .catch((error) => {
          console.error(error)
          loading.close()
        })
      this.$refs.previewImg.showViewer = true
    },

    //放大查看
    bigPhotoWindow1() {
      imgListApi({
        feeitemId: this.idsI[0].id,
      })
        .then(({ data }) => {
          data.forEach((el) => {
            el.src = this.$getCookie('imgPath') + el.src
          })
          this.bigImgList = data.map((item) => item.src)
        })
        .catch((error) => {
          console.error(error)
          loading.close()
        })
      this.ksListOpen1 = true
    },
    prevImage() {
      if (this.currentImageIndex > 0) {
        this.currentImageIndex--; // 上一张
      } else {
        this.currentImageIndex = this.bigImgList.length - 1; // 循环到最后一张
      }
    },
    nextImage() {
      if (this.currentImageIndex < this.bigImgList.length - 1) {
        this.currentImageIndex++; // 下一张
      } else {
        this.currentImageIndex = 0; // 循环到第一张
      }
    },
    // 截图
    cutPicture() {
      if (!this.idsI.length) {
        this.$alert('请先选择收费项目', '提示')
        return
      }
      if (this.isConnect) {
        var v = document.getElementById('videoItem')
        let canvas = document.getElementById('myCanvas')
        canvas.setAttribute('crossOrigin', 'Anonymous')
        var ctx = canvas.getContext('2d', { desynchronized: true, alpha: false })
        ctx.drawImage(v, 0, 0, canvas.width, canvas.height)
        var oGrayImg = canvas.toDataURL('image/jpeg', 1.0)
        this.screenshot.unshift({
          indexId: this.cutId++,
          imgPath: oGrayImg,
          inReport: 1,
        })
        this.$nextTick(() => {
          this.screenshot = JSON.parse(JSON.stringify(this.screenshot))
        })
        uploadScreenshot({
          base64: oGrayImg,
          feeitemId: this.idsI[0].id,
        })
          .then(() => {
            this.$modal.msgSuccess('上传截图成功')
            this.getItemPic()
          })
          .catch((error) => {
            console.error(error)
          })
      } else if (this.cutBtn != '传图') {
        this.$modal.msgWarning('采集卡连接中,请稍后再试', '提示')
      } else {
        this.ksListOpen = true
        this.ksLoading = true
        getKsIdList(this.$route.meta.deptNo)
          .then(({ data }) => {
            this.ksIpList = data
            this.ksLoading = false
          })
          .catch((error) => {
            console.error(error)
            this.ksLoading = false
          })
      }
    },
    // 确定选择上传科室
    selectKsId() {
      if (!this.ksIpId) {
        this.$alert('请先选择所在科室', '提示')
        return
      }
      this.ksLoading = true
      readImages(this.idsI[0].id, this.ksIpId)
        .then(() => {
          this.$modal.msgSuccess('图片上传成功')
          this.ksLoading = false
          this.cancelKs()
          this.getItemPic()
        })
        .catch((error) => {
          console.error(error)
          this.ksLoading = false
        })
    },
    // 取消选择上传科室
    cancelKs() {
      this.ksIpId = undefined
      this.ksListOpen = false
    },
    // 保存截图
    handleSavePic() {
      if (this.fileList.length == 0) {
        this.$alert('请选择要上传的截图', '提示')
        return
      }
      let base64 = this.fileList.map((item) => item.imgPath)
      for (let i = base64.length - 1; i >= 0; i--) {
        if (!base64[i].includes('base64')) {
          this.$delete(base64, i)
        }
      }
      if (!base64.length) {
        this.$modal.msgSuccess('上传截图成功')
        return
      } else {
        base64 = base64.join(',')
      }
      uploadScreenshot({
        base64,
        feeitemId: this.idsI[0].id,
      })
        .then(() => {
          this.$modal.msgSuccess('上传截图成功')
        })
        .catch((error) => {
          console.error(error)
        })
    },
    // 切换图片
    handleTogglePic(type) {
      if (!this.selectImgId) {
        return
      }
      let nowIndex = 0
      this.imgList.forEach((el, index) => {
        if (!nowIndex && this.selectImgId == el.id) {
          nowIndex = index
        }
      })
      if (type == 'up') {
        nowIndex--
      } else {
        nowIndex++
      }
      if (nowIndex >= 0 && nowIndex < this.imgList.length) {
        this.changeImg(nowIndex, this.imgList[nowIndex].id)
      }
    },
    // 复制历史描述及小结
    handleCopy() {
      let description = this.approvalForm.history.split('\n')[1]
      let conclusions = this.approvalForm.history.split('\n')[3]
      if (description == '暂无' && conclusions == '暂无') {
        this.$alert('没有可以复制的内容', '提示')
        return
      }
      this.$confirm('是否复制历史描述与小结至当前结果', '提示')
        .then(() => {
          if (description != '暂无') {
            this.approvalForm.description = description
          }
          if (conclusions != '暂无') {
            this.approvalForm.conclusions = conclusions
          }
        })
        .catch(() => { })
    },
    // 上传Dicom文件
    uploadWindow() {
      this.$refs.uploadItem.uploadWindow(this.idsI[0].id)
    },
    // 预览报告
    handlePreview() {
      if (!this.idsI.length) {
        this.$alert('请选择收费项目后再试', '提示')
        return
      }
      if (!this.approvalForm.isAudit) {
        this.$alert('请审核后再试', '提示')
        return
      }
      let query = {
        idExamtype: this.idsP[0].idExamtype,
        patientcode: this.idsP[0].patientcode,
        ksId: this.$route.meta.deptNo,
        reportType: 7,
      }
      getData(query).then((res) => {
        if (!res.data) {
          this.$modal.alertWarning(res.msg, '提醒')
          return
        }
        const routeUrl = this.$router.resolve({
          name: 'pacsReport', //这里是跳转页面的name
          query: res.data,
        })
        window.open(routeUrl.href, '_blank')
      })
    },
    //生成报告
    generateReport() {
      if (!this.idsI.length) {
        this.$alert('请选择收费项目后再试', '提示')
        return
      }
      if (!this.approvalForm.isAudit) {
        this.$alert('请审核后再试', '提示')
        return
      }
      this.$refs.generateReportItem.generateReportWindow()
    },
    queryWindow() {
      // 平安接口未对接
    },
  },
}
</script>

<style lang="scss" scoped>
.summary-header {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;

  .right-title {
    margin: 0;
  }

  .ai-analysis-btn {
    margin-left: 10px;
  }
}
</style>
<style scoped>
body::-webkit-scrollbar {
  display: none;
}

.photo-ul {
  list-style: none;
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 0 0;
  margin: 0;
  margin-bottom: 8px;
  width: 100%;
  height: 200px;
  background-color: #f6f7fb;
}

.photo-ul .no-pic {
  flex: 1;
  line-height: 200px;
  text-align: center;
  color: #888;
}

.photo-li {
  margin-right: 20px;
}

.photo-ul>li:last-child {
  margin-right: 0;
}

.positionDiv {
  position: relative;
  top: 0;
  left: 0;
  height: 180px;
  width: 180px;
  border: 1px solid #0059ff;
  border-radius: 5px;
  overflow: hidden;
}

.positionBox {
  position: absolute;
  bottom: 0;
  right: 0;
  border-radius: 5px 0px;
  height: 28px;
  width: 28px;
}

.positionBox input {
  width: 100%;
  height: 100%;
  margin: 0;
}

.li-img {
  display: inline-block;
  cursor: pointer;
  border-radius: 5px;
  width: 100%;
}

.li-img-change {
  display: inline-block;
  width: 100%;
  cursor: pointer;
  border-radius: 5px;
  background-size: contain;
  background-color: #fff;
}

.delete-image .delete-image-content {
  min-height: 50vh;
}

.delete-image .delete-image-content .photo-ul {
  flex-wrap: wrap;
  height: 100%;
  overflow: initial;
  margin: 0;
  padding: 0;
  background: transparent;
  margin-top: -12px;
}

.delete-image .delete-image-content .photo-ul .photo-li {
  margin-top: 12px;
}

.delete-image .delete-image-content .photo-ul .photo-li:nth-child(4n) {
  margin-right: 0;
}
</style>
<style lang="scss">
.pacs-section-main {
  overflow: hidden;
  height: 100%;
  background-color: #f6f7fb;

  .pacs-section-pic {
    overflow-y: auto;
    height: 100%;

    .pic-list {
      padding-top: 4px;
      height: 100%;
      background-color: #fff;
      margin-right: 8px;

      .pic-item {
        position: relative;
        margin: 0 5%;
        margin-top: 16px;
        // text-align: center;
        background-color: #eee;
        cursor: pointer;

        &:first-child {
          margin-top: 0;
        }

        i {
          color: #d41318;
          border-radius: 50%;
          background-color: #fff;

          &.el-icon-success {
            position: absolute;
            right: 2px;
            bottom: 5px;
          }

          &.el-icon-error {
            position: absolute;
            top: 3px;
            right: 2px;
          }
        }
      }
    }
  }

  .pacs-section-left {
    height: 100%;
    overflow-y: auto;

    .left-box {
      display: flex;
      flex-direction: column;
      height: 100%;
      margin-right: 8px;

      .color-ultrasound {
        display: flex;
        margin-bottom: 4px;
        background-color: #fff;

        .pic-list {
          margin-left: 8px;
          width: 650px;
          overflow-x: auto;

          img {
            width: 180px;
            height: 180px;
            margin: 8px;
          }
        }

        .textarea {
          flex: 2;
          padding: 8px;
          height: 100%;

          .el-textarea__inner {
            height: 100%;
          }
        }

        .video-box {
          flex: 1;
          height: 100%;

          video {
            width: 100%;
            height: calc(100% - 8px);
          }
        }
      }

      .other-section {
        margin-bottom: 4px;
        background-color: #f5f5f5;

        .right-box {
          display: flex;
          flex-direction: column;
          height: 100%;
          // max-height: 260px;
          background-color: #ffffff;

          .textarea {
            height: 100%;
            padding: 2px 8px 8px;

            .el-textarea__inner {
              height: 100%;
            }
          }
        }
      }

      .pacs-person-info-main {
        flex: 1;
        overflow-y: auto;
        display: flex;
        flex-direction: column;
        background-color: #fff;

        .pacs-person-info {
          width: 100%;
          height: auto;
          background: #ffffff;
          margin-bottom: 4px;

          .pacs-person-item {
            height: 32px;
            color: black;
            // width: 70px;
            font-size: 22px;
            font-weight: 600;
          }
        }
      }
    }
  }

  .pacs-section-right {
    overflow: hidden;
    height: 100%;
    overflow-y: auto;
    display: flex;
    flex-direction: column;

    .right-table {
      padding: 16px 0 0;
      flex: 1;
      min-height: 220px;
      background: #ffffff;
      display: flex;
      flex-direction: column;
    }

    .right-description {
      margin-top: 4px;
      padding: 0 16px;
      background: #ffffff;

      .right-title {
        margin: 8px 0;
        font-size: 16px;
        font-weight: 600;
      }

      // .textarea {
      // }
    }
  }
}

.el-image-viewer__img {
  width: auto;
  height: 100%;
}
</style>
<style scoped>
/* 表格去内边距 */
.pacs-section-left /deep/ .el-table__cell,
.pacs-section-right /deep/ .el-table__cell {
  padding: 2px 0;
  height: auto;
}

.pacs-section-right /deep/ .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
  background-color: rgba(212, 19, 24, 0.5);
}

.pacs-section-left /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.pacs-section-main /deep/.el-textarea__inner {
  background-color: #f6f7fb;
  font-size: 20px;
  color: #000;
  font-weight: 600;
  font-family: 'SimHei';
}

.pacs-section-main .pacs-history-input /deep/.el-textarea__inner {
  font-weight: 400;
  font-family: '';
}

.pacs-section-main /deep/ .el-button span {
  font-size: 16px;
}

.pacs-section-main /deep/ .el-table__row .cell {
  font-size: 16px;
  color: #000;
  cursor: default;
}

.pacs-person-info /deep/ .el-form-item__label {
  font-size: 18px;
}

.pacs-section-main .other-section .bar-bgc::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.7);
}

.pacs-section-main .pacs-person-info .no-weight /deep/ .el-form-item__label {
  font-weight: normal;
}

/* 去除体征词多选 */
.pacs-section-main #tableDataJLC /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.pacs-person-info /deep/ .el-form {
  height: 34px;
}
</style>
