<!-- 线上备单-编辑 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container">
    <el-dialog title="编辑" :visible.sync="open" width="90%" append-to-body @close="reset" :close-on-click-modal="false">
      <el-form :inline="true" label-width="120px" :model="form">
        <el-form-item label="订单号">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.ordernum" readonly></el-input>
        </el-form-item>
        <el-form-item label="客户单位名称">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.orgName" readonly></el-input>
        </el-form-item>
        <el-form-item label="体检团体类型">
          <div v-for="item in idOrgclass" :key="item.id">
            <el-input placeholder="请输入" v-if="item.id == form.idOrgclass" style="width: 200px" :value="item.text" readonly></el-input>
          </div>
        </el-form-item>
        <el-form-item label="销售人员">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.saleName" readonly></el-input>
        </el-form-item>
        <el-form-item label="已结束">
          <div class="setinput">
            <el-checkbox placeholder="请输入" style="width: 200px; pointer-events: none" v-model="form.fFinished"></el-checkbox>
          </div>
        </el-form-item>
        <el-form-item label="任务预定日期">
          <el-date-picker placeholder="请输入" style="width: 200px" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" v-model="form.datereservation" readonly></el-date-picker>
        </el-form-item>
        <el-form-item label="计划结束日期">
          <el-date-picker placeholder="请输入" style="width: 200px" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" v-model="form.planenddate" readonly></el-date-picker>
        </el-form-item>
        <el-form-item label="预计人数">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.countexaminee" readonly></el-input>
        </el-form-item>
        <el-form-item label="团体联系方式">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.phone" readonly></el-input>
        </el-form-item>
        <el-form-item label="团检码">
          <el-input placeholder="请输入" style="width: 200px" v-model="form.tjm" readonly></el-input>
        </el-form-item>
        <el-form-item label="团队地址" id="setTextarea">
          <el-input type="textarea" height="100px" placeholder="请输入" style="width: 530px" v-model="form.address" readonly></el-input>
        </el-form-item>
        <el-form-item label="前台须知" id="setTextarea">
          <el-input type="textarea" height="100px" placeholder="请输入" :disabled="false" style="width: 860px" v-model="form.qtxz" readonly></el-input>
        </el-form-item>
      </el-form>
      <div class="add-container">
        <div style="width: 40%; height: 480px; display: inline-block; margin-right: 1%">
          <!-- 操作按钮 -->
          <div class="flex-direction-column">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="addLeftWindow()" v-hasPermi="['sale:orderPreparation:edit:add']">新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="danger" icon="el-icon-delete" :disabled="multipleLeft" plain @click="deleteLeftWindow()" v-hasPermi="['sale:orderPreparation:edit:delete']">删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-s-order" plain @click="saveWindow()" v-hasPermi="['sale:orderPreparation:edit:save']">保存</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-bell" plain @click="confirmWindow()" v-hasPermi="['sale:orderPreparation:edit:confirm']">备单确认</el-button>
              </el-col>
            </el-row>
            <!-- 表格数据 -->
            <div class="table-box">
              <el-table ref="tableLeft" id="tableLeftData" :data="tableData" v-loading="loadingLeft" :border="true" :stripe="true" @selection-change="handleLeftSelectionChange" row-key="id" height="100%" @row-click="rowClickHandle">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序号" align="center"></el-table-column>
                <el-table-column prop="orgreservationgroupname" width="100px" label="分组名称" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.orgreservationgroupname"></el-input>
                  </template>
                </el-table-column>
                <el-table-column prop="idExamsuite" width="120px" label="体检套餐" align="center">
                  <template slot-scope="scope">
                    <input-select :selectData="mealData" :hiddenClear="true" :queryParams="{ idExamsuite: id }" :initialValue="scope.row.tcName" :tjlxType="true" selectWidth="100%" :current-index="scope.row.sort" @change="mealChange"></input-select>
                  </template>
                </el-table-column>
                <el-table-column prop="idPatientclass3" width="100px" label="状态" align="center">
                  <template slot-scope="scope">
                    <el-tag type="primary" readonly v-if="scope.row.idPatientclass3 == '1'">已确认</el-tag>
                    <el-tag type="danger" readonly v-else>未确认</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="idExamtype" width="100px" label="体检类型" align="center">
                  <template slot-scope="scope">
                    <div v-for="item in idExamtype" :key="item.id">
                      <el-input readonly v-if="item.id == scope.row.idExamtype" :value="item.text"></el-input>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="fzxId" width="130px" label="分中心" align="center">
                  <template slot-scope="scope">
                    <el-tooltip placement="top" :disabled="!scope.row.fzx">
                      <div slot="content">
                        <span>{{ scope.row.fzx }}</span>
                      </div>
                      <el-select v-model="scope.row.fzxId" @click.native="getFocus(scope.row)" @change="changeHandle($event, scope.$index)" style="width: 100%">
                        <el-option v-for="item in fzxArray" :key="item.id" :label="item.fzx" :value="item.fzxId"> </el-option>
                      </el-select>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column prop="zhjg" width="100px" label="优惠价" align="center"></el-table-column>
                <el-table-column label="分组条件" align="center">
                  <el-table-column prop="grouptype" width="80px" label="组类" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.grouptype"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="fMale" width="80px" label="男" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.fMale" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column prop="fFemale" width="80px" label="女" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.fFemale" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column prop="fHasmarried" width="80px" label="已婚" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.fHasmarried" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column prop="fNevermarried" width="80px" label="未婚" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.fNevermarried" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column prop="dispOrder" width="80px" label="优先" align="center">
                    <template slot-scope="scope">
                      <el-input-number v-model="scope.row.dispOrder" style="width: 100%; text-align: center" :controls="false" :min="0"></el-input-number>
                    </template>
                  </el-table-column>
                  <el-table-column prop="agemin" width="80px" label="年龄自" align="center"></el-table-column>
                  <el-table-column prop="agemax" width="80px" label="年龄至" align="center"></el-table-column>
                </el-table-column>
                <el-table-column prop="dateexamplanned" width="160px" label="计划日期" align="center">
                  <template slot-scope="scope">
                    <!-- <el-tooltip placement="top">
                                    <div slot="content">{{ scope.row.dateexamplanned }}</div> -->
                    <el-date-picker v-model="scope.row.dateexamplanned" :key="scope.$index" :clearable="true" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
                    <!-- </el-tooltip> -->
                  </template>
                </el-table-column>
                <el-table-column prop="xzfzx" width="120px" label="已下载分中心" align="center"></el-table-column>
                <!-- harmName harmGroup -->
                <el-table-column prop="harmName" width="120px" label="危害分组" show-overflow-tooltip align="center"> </el-table-column>
                <el-table-column prop="idPayway" width="120px" label="付款方式" align="center">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.idPayway">
                      <div style="display: flex; justify-content: space-between; padding: 0 10px 10px">
                        <div style="margin-right: 10px">输入码</div>
                        <div style="margin-left: 10px">支付方式</div>
                      </div>
                      <el-option v-for="item in idPayway" :key="item.id" :label="item.paywayName" :value="item.id">
                        <div style="display: flex; flex-direction: row; justify-content: space-between">
                          <div style="margin-right: 10px">{{ item.paywayCode }}</div>
                          <div style="margin-left: 10px">{{ item.paywayName }}</div>
                        </div>
                      </el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column prop="sfzx" width="120px" label="是否自选" align="center">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.sfzx == '1'">是</el-tag>
                    <el-tag type="danger" v-else>否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="forbidden" width="120px" label="已禁检" align="center">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.forbidden" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column prop="isShowMoney" width="120px" label="是否隐藏金额" align="center">
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.isShowMoney" :true-label="1" :false-label="0" style="pointer-events: none"></el-checkbox>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>

        <div style="width: 59%; height: 480px; display: inline-block">
          <!-- 操作按钮 -->
          <div class="flex-direction-column">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="addRightWindow()" v-hasPermi="['sale:orderPreparation:edit:ads']">新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="danger" icon="el-icon-delete" :disabled="multipleRight" plain @click="deleteRightWindow()" v-hasPermi="['customer:trackingRecord:deletes']">删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="danger" icon="el-icon-delete" plain @click="removeWindow()" v-hasPermi="['customer:trackingRecord:removes']">清除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-upload2" plain @click="importWindow()" v-hasPermi="['customer:trackingRecord:imports']">导入</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-bell" plain @click="preRregistration()" v-hasPermi="['customer:trackingRecord:registration']">预登记</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-bell" plain @click="buildfor()" v-hasPermi="['customer:trackingRecord:notgenerated']">未生成</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button size="mini" type="warning" icon="el-icon-s-order" plain @click="savesWindow" v-hasPermi="['customer:orderPreparation:edit:saves']">保存</el-button>
              </el-col>
            </el-row>
            <div class="table-box">
              <el-form ref="tableRules" style="height: 100%" :model="modelTableList" :rules="tableRules">
                <el-table ref="tableList" id="tableRightList" :data="modelTableList.tableList" v-loading="loadingRight" :border="true" :stripe="true" @selection-change="handleRightSelectionChange" style="min-height: 444px" height="100%" @row-click="rowClickRight" @row-dblclick="dbclick">
                  <el-table-column fixed="left" type="selection" align="center"></el-table-column>
                  <el-table-column fixed="left" type="index" label="序列" width="55" align="center"></el-table-column>
                  <el-table-column fixed="left" width="120px" prop="groupName" label="分组名称" align="center"></el-table-column>
                  <el-table-column fixed="left" width="100px" label="已生成" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.checkboxcolumn" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column fixed="left" width="120px" prop="patientcode" label="体检号" align="center"></el-table-column>
                  <el-table-column fixed="left" width="100px" prop="patientname" label="姓名" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.patientname" @blur="blurHandle($event.target.value, scope.row)" style="width: 100%"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="pinyin" width="100px" label="拼音" align="center"></el-table-column>
                  <el-table-column prop="idcardno" width="180px" label="身份证号" align="center">
                    <template slot-scope="scope">
                      <el-form-item :prop="'tableList.' + scope.$index + '.idcardno'" :rules="tableRules.idcardno">
                        <el-input v-model="scope.row.idcardno" placeholder="请输入" style="width: 100%" />
                      </el-form-item>
                    </template>
                  </el-table-column>
                  <el-table-column prop="idSex" width="100px" label="性别" align="center">
                    <template slot-scope="scope">
                      <el-select v-model="scope.row.idSex">
                        <el-option v-for="item in idSex" :key="item.id" :label="item.text" :value="item.id"></el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column prop="age" width="120px" label="年龄" align="center">
                    <template slot-scope="scope">
                      <el-input-number v-model="scope.row.age" :controls="false" style="width: 100%" :min="0"></el-input-number>
                    </template>
                  </el-table-column>
                  <el-table-column prop="phone" width="120px" label="手机号码" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.phone" style="width: 100%"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="birthdate" width="140px" label="出生日期" align="center">
                    <template slot-scope="scope">
                      <el-tooltip placement="top">
                        <div slot="content">{{ scope.row.birthdate }}</div>
                        <el-date-picker v-model="scope.row.birthdate" :key="scope.$index + 'create'" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
                      </el-tooltip>
                    </template>
                  </el-table-column>
                  <el-table-column width="120px" prop="idMarriage" label="婚姻" align="center">
                    <template slot-scope="scope">
                      <el-form-item :prop="'tableList.' + scope.$index + '.idMarriage'">
                        <el-select v-model="scope.row.idMarriage">
                          <el-option v-for="item in idMarriage" :key="item.id" :label="item.text" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </template>
                  </el-table-column>
                  <el-table-column prop="idNation" width="120px" label="民族" align="center">
                    <template slot-scope="scope">
                      <el-select v-model="scope.row.idNation">
                        <el-option v-for="item in idNation" :key="item.id" :label="item.name" :value="item.id"></el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column prop="cultural" width="120px" label="文化程度" align="center">
                    <template slot-scope="scope">
                      <el-select v-model="scope.row.cultural">
                        <el-option v-for="item in cultural" :key="item.id" :label="item.text" :value="item.id"></el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column prop="orgDepart" width="120px" label="部门" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.orgDepart" style="width: 100%"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="workno" width="120px" label="工号" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.workno" style="width: 100%"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="trades" width="120px" label="工种" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.trades" style="width: 100%"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="workDate" width="140px" label="参加工作时间" align="center">
                    <template slot-scope="scope">
                      <el-tooltip placement="top">
                        <div slot="content">{{ scope.row.workDate }}</div>
                        <el-date-picker v-model="scope.row.workDate" style="width: 100%" :key="scope.$index + 'work'" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
                      </el-tooltip>
                    </template>
                  </el-table-column>
                  <el-table-column prop="zgl" width="120px" label="总工龄(月)" align="center">
                    <template slot-scope="scope">
                      <el-input-number v-model="scope.row.zgl" :controls="false" style="width: 100%" :min="0"></el-input-number>
                    </template>
                  </el-table-column>
                  <el-table-column prop="harmDate" width="140px" label="从事工种时间" align="center">
                    <template slot-scope="scope">
                      <el-tooltip placement="top">
                        <div slot="content">{{ scope.row.harmDate }}</div>
                        <el-date-picker v-model="scope.row.harmDate" :key="scope.$index + 'harm'" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
                      </el-tooltip>
                    </template>
                  </el-table-column>
                  <el-table-column prop="jhgl" width="120px" label="接害工龄(月)" align="center">
                    <template slot-scope="scope">
                      <el-input-number v-model="scope.row.jhgl" :controls="false" style="width: 100%" :min="0"></el-input-number>
                    </template>
                  </el-table-column>
                  <el-table-column prop="medicaltype" width="120px" label="体检类别" align="center">
                    <template slot-scope="scope">
                      <el-select v-model="scope.row.medicaltype">
                        <el-option v-for="item in medicaltype" :key="item.id" :value="item.id" :label="item.text"></el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column prop="xzfzx" width="120px" label="已下载分中心" align="center"></el-table-column>
                  <el-table-column prop="note" width="120px" label="备注" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.note" ref="notes"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column prop="wechatCode" width="120px" label="体检码" align="center"></el-table-column>
                  <el-table-column prop="isWechatNoticed" width="120px" label="已通知" align="center">
                    <template slot-scope="scope">
                      <el-checkbox v-model="scope.row.isWechatNoticed" style="pointer-events: none"></el-checkbox>
                    </template>
                  </el-table-column>
                </el-table>
              </el-form>
            </div>
          </div>
        </div>
      </div>
      <el-dialog id="dialogMsg" title="注意" :visible.sync="deleteWarningMsg" width="300px" append-to-body :close-on-click-modal="false">
        <div>注意：【 <span style="color: red">一旦清除无法恢复</span> 】</div>
        <div>请输入以下内容:</div>
        <div>清除该订单下所有未体检的数据</div>
        <el-input type="textarea" v-model="cleanUp"></el-input>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelMsg">取 消</el-button>
          <el-button type="primary" @click="readClean">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 导入 -->
      <el-dialog id="importDialog" title="导入人员信息" :visible.sync="importWindowDialog" width="440px" append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <el-form ref="dialogForm" :model="dialogForm" v-loading="formLoading" element-loading-text="导入中">
          <el-form-item label="模板类型">
            <el-select v-model="dialogForm.modelType" style="width: 330px">
              <el-option v-for="item in modelTypeOptions" :label="item.text" :value="item.id" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请选择上传文件" label-position="top">
            <file-upload ref="uploadFile" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelHandleDialog">关 闭</el-button>
          <el-button type="primary" @click="uploadHandle">导 入</el-button>
        </div>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import IDValidator from '@/utils/IDValidator.js'
import GB2260 from '@/utils/GB2260.js'
import { editOrder, getGroupDataOrder, saveOrUpdatePatientBcOrder, saveOrUpdateGroupOrder, getPayWayDataOrder, getBranchDataOrder, getPatientDataOrder, getMarriageDataOrder, getNationDataOrder, removeAllOrder, groupConfirmOrder, saveOrUpdatePatientOrder } from '@/api/sale/order_preparation.js'
import pinyin from '@/utils/pinyin.js'

export default {
  data() {
    let idcardno = (rule, value, callback) => {
      if (value) {
        var Validator = new IDValidator(GB2260)
        if (!Validator.isValid(value)) {
          callback(new Error('身份证号输入不合法'))
        } else {
          callback()
        }
      } else {
        callback(new Error('证件号不能为空'))
      }
    }
    let idMarriage = (rule, value, callback) => {
      if (!value) {
        callback(new Error('婚姻选项不能为空'))
      } else {
        callback()
      }
    }
    return {
      open: false,
      form: {
        ddh: '',
        idOrg: '',
        idOrgclass: '',
        idSalesperson: '',
        fFinished: '',
        datereservation: '',
        planenddate: '',
        countexaminee: '',
        phone: '',
        tjm: '',
        address: '',
        qtxz: '',
      },
      tableData: [],
      modelTableList: {
        tableList: [],
      },
      idsLeft: [],
      singleLeft: true,
      multipleLeft: true,
      idsRight: [],
      singleRight: true,
      multipleRight: true,
      // 显示搜索条件
      showSearch: true,
      loadingLeft: false,
      loadingRight: false,
      // 查询参数
      total: 30,

      queryParams: {
        current: 1,
        size: 10,
      },
      //体检套餐 input-select
      mealData: {
        placeholder: '请输入输入码选择',
        key: '套餐名称简称', //第一列标题
        value: '体检类型', //第二列标题
        url: '/sell/itemListOnline/getAllTcForOrder', //请求连接
        bindValue: '',
        firstName: 'tjtcmc',
        secondName: 'tjlx',
      },
      //付款方式 input-select
      fzxArray: [], //分中心
      zffsArray: [],
      id: undefined,
      idOrgclass: [
        {
          id: '0',
          text: '普通客户',
        },
        {
          id: '1',
          text: 'vip客户',
        },
        {
          id: '2',
          text: '流失客户',
        },
      ],
      idExamtype: [
        {
          id: '1',
          text: '职业',
        },
        {
          id: '2',
          text: '综合',
        },
        {
          id: '0',
          text: '健康',
        },
      ],
      idPayway: [], //付款方式
      griddata: [],
      selection: [],
      rowId: undefined,
      idSex: [
        {
          id: 0,
          text: '男',
        },
        {
          id: 1,
          text: '女',
        },
      ],
      idMarriage: [],
      idNation: undefined,
      cultural: [
        {
          id: 0,
          text: '小学',
        },
        {
          id: 1,
          text: '初中',
        },
        {
          id: 2,
          text: '技校',
        },
        {
          id: 3,
          text: '职高',
        },
        {
          id: 4,
          text: '高中',
        },
        {
          id: 5,
          text: '中专',
        },
        {
          id: 6,
          text: '大专',
        },
        {
          id: 7,
          text: '大学',
        },
        {
          id: 8,
          text: '研究生以上',
        },
      ],
      medicaltype: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      griddataRight: [],
      deleteWarningMsg: false,
      cleanUp: '',
      rightSelection: [],
      //表单验证--身份证非空验证
      tableRules: {
        idcardno: [{ validator: idcardno, required: true, trigger: 'blur' }],
      },
      //导入模板
      importWindowDialog: false,
      modelTypeOptions: [
        { id: 4, text: '职业模板' },
        { id: 3, text: '健康模板' },
      ],
      dialogForm: {
        id: '',
        modelType: 4,
      },
      formLoading: false,
      // 上传组件参数
      uploadData: {
        url: '/reception/order/importPatientBatch', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: ['xls', 'xlsx'], //文件类型
        data: {}, //上传时附带的额外参数
      },

      //新增右边点击行
      rightRow: [],
      idOrgRw: undefined,
    }
  },
  methods: {
    //getNationDataOrder
    goGetNationDataOrder() {
      getNationDataOrder().then((res) => {
        if (res.code == 200) {
          this.idNation = res.data
        }
      })
    },
    //婚姻下拉列表
    goGetMarriageDataOrder() {
      getMarriageDataOrder().then((res) => {
        if (res.code == 200) {
          this.idMarriage = []
          let id = Object.keys(res.data)
          let text = Object.values(res.data)
          for (var i = 0; i < id.length; i++) {
            let objs = {}
            objs.id = Number(id[i])
            objs.text = text[i]
            this.idMarriage.push(objs)
          }
        }
      })
    },
    //右表失去焦点获取拼音
    blurHandle(value, row) {
      let pinyinStr = ''
      for (var index in this.modelTableList.tableList) {
        if (row.id == undefined && this.modelTableList.tableList[index].sort == row.sort) {
          pinyinStr = pinyin(value)
          this.$delete(this.modelTableList.tableList[index], 'pinyin')
          this.$set(this.modelTableList.tableList[index], 'pinyin', pinyinStr)
        } else if (this.modelTableList.tableList[index].sort == row.sort && row.id != undefined) {
          pinyinStr = pinyin(value)
          this.$delete(this.modelTableList.tableList[index], 'pinyin')
          this.$set(this.modelTableList.tableList[index], 'pinyin', pinyinStr)
        }
      }
    },
    //点击行获取焦点且获取分组下相对应的人员信息
    rowClickHandle(row) {
      this.loadingRight = true
      if (this.selection.indexOf(row) != -1 && this.selection.length >= 1) {
        this.loadingRight = false
        return
      }
      this.$refs.tableLeft.clearSelection()
      this.$refs.tableLeft.toggleRowSelection(row, true)
      if (row) {
        this.rowId = row.id
      }
      getPatientDataOrder({
        idOrgreservationgroup: this.rowId,
      }).then((res) => {
        if (res.code == 200) {
          this.loadingRight = false
          this.modelTableList.tableList = res.data.records
          this.modelTableList.tableList.forEach((el, index) => {
            this.$set(this.modelTableList.tableList[index], 'groupName', this.selection[0].orgreservationgroupname)
            this.$set(this.modelTableList.tableList[index], 'sort', index + 1)
            if (el.patientcode) {
              this.$set(this.modelTableList.tableList[index], 'checkboxcolumn', true)
            }
            this.$set(this.modelTableList.tableList[index], 'pinyin', pinyin(el.patientname))
          })
          let array = JSON.parse(JSON.stringify(res.data.records))
          this.griddataRight = array.map((el) => {
            if (el.id) {
              el.checkboxcolumn = true
            }
            el.pinyin = pinyin(el.patientname)
            return el
          })
        }
      })
    },
    //点击右表获取当前行
    rowClickRight(row) {
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row, true)
      this.rightRow = row
    },
    // 双击行跳转
    dbclick(row, column, event) {
      
      this.$router.push({ name: 'Registration', params: { patientcode: row.patientcode } })
    },
    //选中值发生改变事件
    changeHandle(e, index) {
      let obj = {}
      obj = this.fzxArray.find((item) => {
        return item.id == e
      })
      //展示
      this.tableData.forEach((el) => {
        if (el.sort == index) {
          this.$set(this.tableData[index], 'fzx', obj.fzx)
          this.$set(this.tableData[index], 'fzxId', obj.fzxId)
        }
      })
      //交互
      this.griddata.forEach((el) => {
        if (el.sort == index) {
          this.$set(this.griddata[index], 'fzx', obj.fzx)
          this.$set(this.griddata[index], 'fzxId', obj.fzxId)
        }
      })
    },
    //每一次获取焦点后调用套餐分中心数据
    getFocus(row) {
     
      this.griddata.forEach((el) => {
        if (row.id == el.id) {
          if (el.idExamsuite) {
          } else {
            this.$alert('请先选择套餐！', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
          }
        }
      })
    },
    //关闭回调
    reset() {
      this.form = {}
      this.tableData = []
      this.modelTableList.tableList = []
    },
    editWindow(id, context) {
      this.open = true
      if (context == '0') {
        this.id = id[0]
      } else if (context == '1') {
        this.id = id
      }
      this.getEditOrder()
      this.goGetPayWayDataOrder()
      this.goGetMarriageDataOrder()
      this.goGetNationDataOrder()
    },
    //获取套餐的分中心
    goGetBranchDataOrder(id) {
      let obj = {
        id,
      }
      getBranchDataOrder(obj)
        .then((res) => {
          if (res.code == 200) {
            this.fzxArray = []
            res.data.forEach((el) => {
              el.fzxId = el.id.toString()
              this.fzxArray.push(el)
            })
          }
        })
        .then(() => {})
        .catch(() => {})
    },
    //获取收款方式
    goGetPayWayDataOrder() {
      let obj = { size: 9999 }
      getPayWayDataOrder(obj).then((res) => {
        if (res.code == 200) {
          this.idPayway = res.data.records
          this.idPayway.push({
            id: '0',
            paywayCode: 'TS',
            paywayName: '统收',
          })
        }
      })
    },
    //根据id获取详情
    getEditOrder() {
      let obj = {
        id: '',
      }
      obj.id = this.id
      editOrder(obj).then((res) => {
        if (res.code == 200) {
          if (res.data.peisorgreservation) {
            this.form = res.data.peisorgreservation
            this.idOrgRw = res.data.peisorgreservation.id
            this.goGetGroupDataOrder()
          } else {
            this.form = res.data
            this.tableData = []
          }
          this.form.tjm = res.data.tjm
          this.form.fFinished = res.data.fFinished || res.data.FFinished
          this.form.saleName = res.data.saleName
          this.form.ordernum = res.data.ordernum
        }
      })
    },
    //根据idORgRw(体检团体任务ID)获取体检团体分组信息//左表
    goGetGroupDataOrder() {
      this.loadingLeft = true
      let obj = {
        idOrgRw: '',
      }
      obj.idOrgRw = this.form.id || this.idOrgRw
      getGroupDataOrder(obj)
        .then((res) => {
          if (res.data.data) {
            this.tableData = res.data.data

            let array = res.data.data.slice()
            this.griddata = array.map((el, index) => {
              el.state = 'modified'
              el.sort = index
              this.fzxArray.push({ fzx: el.fzx, fzxId: el.fzxId })
              return el
            })
          }
        })
        .finally(() => {
          for (var i = 0; i < this.fzxArray.length; i++) {
            for (var j = 1; j < this.fzxArray.length; j++) {
              if (this.fzxArray[i].fzxId == this.fzxArray[j].fzxId) {
                this.fzxArray.splice(i, 1)
              }
            }
          }
          this.loadingLeft = false
        })
    },
    getList() {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 500)
    },
    handleLeftSelectionChange(selection) {
      this.idsLeft = selection.map((item) => item.id)
      this.singleLeft = selection.length != 1
      this.multipleLeft = !selection.length
      if (selection.length != 0) {
        this.selection = selection
      } else {
        this.selection = []
      }
    },
    handleRightSelectionChange(selection) {
      this.idsRight = selection.map((item) => item.sort)
      this.singleRight = selection.length != 1
      this.multipleRight = !selection.length
      if (selection) {
        this.rightSelection = selection
      } else {
        this.rightSelection = []
      }
    },
    //左表新增列
    addLeftWindow() {
      let sort = 0
      if (this.griddata.length > 0) sort = this.griddata.length
      let obj = {
        sort,
        dispOrder: 1,
        state: 'added',
        idPayway: undefined,
        forbidden: 0,
        isShowMoney: 0,
        idPatientclass3: 0,
        sfzx: 0,
        fkfs: undefined,
        fzxId: undefined,
        fzx: undefined,
      }
      this.tableData.push(obj)
      this.griddata.push(obj)
    },
    //右表新增列
    addRightWindow() {
      if (this.form.fFinished == '1') {
        this.$message({
          message: '订单已结束，不能添加人员。',
          type: 'warning',
        })
        return
      }
      var row = this.selection[0]
      var groupName = ''
      if (row) {
        if (!row.id) {
          this.$message({
            message: '请先保存分组信息',
            type: 'warning',
          })
          return
        } else groupName = row.orgreservationgroupname
      } else {
        this.$message({
          message: '请先选择分组信息',
          type: 'warning',
        })
        return
      }
      let sort = 0
      if (this.griddataRight.length > 0) sort = this.griddataRight.length + 1
      let obj = { idSex: 0, state: 'added', groupName, age: null, jhgl: null, zgl: null, idMarriage: undefined, sort }
      this.griddataRight.push(obj)
      this.modelTableList.tableList.push(obj)
    },
    //左表删除列
    deleteLeftWindow() {
      this.idsLeft.forEach((val) => {
        this.tableData.forEach((el, index) => {
          if (el.id == val) {
            this.$delete(this.tableData, index)
          }
        })
        this.griddata.forEach((el, index) => {
          if (el.id == val) {
            this.$set(this.griddata[index], 'state', 'removed')
          }
        })
      })
    },
    //右表删除列
    deleteRightWindow() {
      this.idsRight.forEach((val) => {
        for (var index in this.modelTableList.tableList) {
          if (this.modelTableList.tableList[index].sort == val) {
            if (this.modelTableList.tableList[index].patientcode) {
              this.$alert(`	${this.modelTableList.tableList[index].patientname} 已经生成体检号，不可删除`, '提示', {
                confirmButtonText: '确认',
                type: 'warning',
              })
              return
            }
            this.$delete(this.modelTableList.tableList, index)
          }
        }
        for (var index in this.griddataRight) {
          if (this.griddataRight[index].sort == val) {
            this.$set(this.griddataRight[index], 'state', 'removed')
          }
        }
      })
    },
    // 选择科室 input-select
    mealChange(value, sort) {
      this.goGetBranchDataOrder(value.id)
      for (let i in this.griddata) {
        if (sort == this.griddata[i].sort) {
          this.$set(this.griddata[i], 'tcid', value.id)
          this.$set(this.griddata[i], 'idExamsuite', value.id)
          this.$set(this.griddata[i], 'tcName', value.tjtcmc)

          this.$set(this.griddata[i], 'idExamtype', value.tjlx) //体检类型
          this.$set(this.griddata[i], 'zhjg', value.zhjg) //优惠价格
          this.$set(this.griddata[i], 'agemax', value.nld) //年龄至
          this.$set(this.griddata[i], 'agemin', value.nlz) //年龄自
          //fFemale 女//fMale 男
          if (value.syxb == 0 || value.syxb == 1) {
            this.$set(this.griddata[i], 'fFemale', value.syxb == 1 ? 1 : 0)
            this.$set(this.griddata[i], 'fMale', value.syxb == 0 ? 1 : 0)
          } else {
            this.$set(this.griddata[i], 'fFemale', 1)
            this.$set(this.griddata[i], 'fMale', 1)
          }
          //fNevermarried 未婚//fHasmarried 已婚
          if (value.sfyhtc == 0 || value.sfyhtc == 1) {
            this.$set(this.griddata[i], 'fNevermarried', value.sfyhtc == 1 ? 1 : 0)
            this.$set(this.griddata[i], 'fHasmarried', value.sfyhtc == 0 ? 1 : 0)
          } else {
            this.$set(this.griddata[i], 'fNevermarried', 1)
            this.$set(this.griddata[i], 'fHasmarried', 1)
          }
          this.$set(this.griddata[i], 'harmName', value.jhysn) //harmGroup jhysn 危害分组
          this.$set(this.griddata[i], 'idPayway', value.fkfs) //harmGroup jhysn 付款方式
          return
        }
      }
    },
    //取消清除
    cancelMsg() {
      this.cleanUp = ''
      this.deleteWarningMsg = false
    },
    //确认清除
    readClean() {
      let input = '清除该订单下所有未体检的数据'
      if (this.cleanUp != input) {
        this.$message.warning('输入内容错误')
        return
      }
      var ids = null
      var len = this.griddataRight.length
      var rows = this.griddataRight
      if (len > 0) {
        if (rows[0].id != undefined) {
          ids = rows[0].id
        }
        for (var i = 1; i < len; i++) {
          if (rows[i].id != undefined) {
            ids = ids + ',' + rows[i].id
          }
        }
      }
      // ids = ids.toString();
      removeAllOrder({
        id: this.selection[0].id,
        ids: ids || '',
      })
        .then((res) => {
          if (res.code == 200) {
            this.$message.success('清空成功')
            this.deleteWarningMsg = false
            this.getRightList()
          }
        })
        .catch(() => {})
        .finally(() => {
          this.cleanUp = ''
          this.deleteWarningMsg = false
        })
    },
    //清除
    removeWindow() {
      var row = this.selection
      if (row.length !== 0) {
        row.forEach((el) => {
          if (!el.id) {
            this.$message.warning('请先保存分组信息')
            return
          }
        })
      } else {
        this.$alert('请先选择分组信息', '提示', {
          confirmButtonText: '确认',
          type: 'warning',
        })
        return
      }
      this.$confirm('确定要删除体检者列表中的数据？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteWarningMsg = true
        })
        .catch(() => {})
    },
    //导入窗口
    importWindow() {
      if (this.form.fFinished == '1') {
        this.$message.warning('订单已结束，不能导入人员。')
        return
      }
      this.dialogForm.id = this.form.id
      this.importWindowDialog = true
    },
    // 上传文件成功
    uploadFinish(value) {
      if (value == 1) {
        if (this.selection[0] && this.selection[0].id) {
          this.getRightList()
        }
        this.$modal.msgSuccess('导入人员信息成功', '提醒')
        this.importWindowDialog = false
      }
      this.formLoading = false
    },
    //导入方法
    uploadHandle() {
      var msg = this.$refs.uploadFile.isUpload()

      if (msg === true) {
        this.formLoading = true
        this.uploadData.data = this.dialogForm
        this.$refs.uploadFile.handelUpload()
        this.getRightList()
      } else {
        this.$modal.msgWarning(msg, '提醒')
        this.getRightList()
      }
    },
    //关闭导入接口
    cancelHandleDialog() {
      this.importWindowDialog = false
    },
    //预登记
    preRregistration() {
      this.$refs['tableRules'].validate((val) => {
        if (val) {
          var row = this.selection[0]
          if (row) {
            if (!row.id) {
              this.$message({
                message: '请先保存分组信息',
                type: 'warning',
              })
              return
            }
          } else {
            this.$message({
              message: '请先选择分组信息',
              type: 'warning',
            })
            return
          }
          if (this.rightSelection.length == 0) {
            this.$alert('没有需要预登记的体检者！', '标题名称', {
              confirmButtonText: '确认',
              type: 'warning',
            })
            return
          }
          var changeRow = []
          this.rightSelection.forEach((el) => {
            if (el.id == undefined) {
              changeRow.push(el)
            }
          })
          if (changeRow.length == 0) {
            this.$alert('没有需要预登记的体检者！', '标题名称', {
              confirmButtonText: '确认',
              type: 'warning',
            })
            return
          } else if (changeRow.length > 1) {
            for (var index in changeRow) {
              if (!changeRow[index].patientname) {
                this.$alert('姓名:不能为空,请修改后重新操作!', '提示', {
                  confirmButtonText: '确认',
                  type: 'warning',
                })
                return
              }
            }
          }
          var obj = {
            formdata: this.form,
            griddata: this.rightSelection,
            id: this.rowId,
          }
          saveOrUpdatePatientOrder(obj).then((res) => {
            if (res.code == 200) {
              this.$message.success('预登记成功')
              this.getRightList()
            }
          })
        } else {
          this.$alert('页面存在必填项或者填写格式存在问题！', '提示', {
            confirmButtonText: '确定',
          })
        }
      })
    },
    //未生成
    buildfor() {
      this.modelTableList.tableList.forEach((el) => {
        if (!el.checkboxcolumn) {
          this.$refs.tableList.toggleRowSelection(el, true)
        }
      })
    },
    //右表保存
    savesWindow() {
      // 判断是否编辑过
      for (let i = 0; i < this.modelTableList.tableList.length; i++) {
        for (let j = 0; j < this.griddataRight.length; j++) {
          if (this.modelTableList.tableList[i].id == this.griddataRight[j].id) {
            if (JSON.stringify(this.modelTableList.tableList[i]) != JSON.stringify(this.griddataRight[j])) {
              this.modelTableList.tableList[i].state = 'modified'
            }
          }
        }
      }
      var allData = this.modelTableList.tableList
      
      var hash = {}
      var sex = false
      var hasRepeated = false
      var repeatRows = []
      for (var z = 0; z < allData.length; z++) {
        var row = allData[z]
        // 判断人员重复录入
        var compare = ''
        if (row.idcardno != null) {
          compare += row.idcardno + '&'
        } else {
          compare = row.idSex + '&' + row.age + '&' + (row.phone ? row.phone : null) + '&' + (row.orgDepart ? row.orgDepart : null) + '&' + (row.trades ? row.trades : null) + '&' + (row.patientname ? row.patientname : null)
        }

        var s = this.getIdCard(allData[z].idcardno, 2)
        if (s == '男') {
          this.modelTableList.tableList[z].idSex = 0
        } else if (s == '女') {
          this.modelTableList.tableList[z].idSex = 1
        }

        if (hash[compare] && (row.patientcode == null || row.patientcode == '')) {
          if (row.idcardno != null) {
            this.modelTableList.tableList.forEach((r, i) => {
              if (r.idcardno == row.idcardno) {
                r.cla = 'red-row'
                repeatRows.push(row)
                this.modelTableList.tableList[i] = r
              }
            })
          } else {
            this.modelTableList.tableList.forEach((r, i) => {
              if (r.patientname == row.patientname) {
                r.cla = 'red-row'
                repeatRows.push(row)
                this.modelTableList.tableList[i] = r
              }
            })
          }
          hasRepeated = true
        }
        hash[compare] = true
      }
      if ((sex, name)) {
        this.$message.error('存在人员信息性别与身份证不符')
      }
      if (hasRepeated) {
        this.$alert('存在信息相同的人员！')
        return
      }
      let data = []
      data = this.modelTableList.tableList.filter((row) => {
        if (row.state == 'modified' || !row.id) {
          return true
        }
      })
      if (data.length == 0) {
        this.$alert('没有需要保存的体检者！')
        return
      }
      if (data.length == 0) {
        this.$alert('体检者信息为空，不能保存！')
        return
      }
      for (var i = 0, l = data.length; i < l; i++) {
        //遍历data
        var row = data[i]
        // if(row.id == ""||typeof(row.id)== "undefined"){
       
        //     row.state="added";
        //     // this.modelTableList.tableList[i] = Object.assign(this.modelTableList.tableList[i], row);
        //     this.$set(this.modelTableList.tableList[i],"state","added")
        // }
        if (!row.workDate) {
          row.workDate = new Date().getTime()
        }
        if (!row.harmDate) {
          row.harmDate = new Date().getTime()
        }
      }
      this.$refs['tableRules'].validate((val) => {
        if (val) {
          this.form = {
            address: this.form.address,
            countexaminee: this.form.countexaminee,
            datereservation: this.form.datereservation + ' 00:00:00',
            ddh: this.form.ddh,
            fFinished: this.form.fFinished,
            fzxId: this.form.fzxId,
            id: this.form.id,
            idOrg: this.form.idOrg,
            idOrgclass: this.form.idOrgclass,
            idSalesperson: this.form.idSalesperson,
            ordernum: this.form.ordernum,
            phone: this.form.phone,
            planenddate: this.form.planenddate + ' 23:59:59',
            qtxz: this.form.qtxz,
            saleName: this.form.saleName,
            // "id": this.id[0]
          }
          let _data = []
          _data = this.modelTableList.tableList.filter((row) => {
            if (typeof row.state != 'undefined') {
              return true
            }
          })
          let obj = {
            griddata: _data,
            formdata: this.form,
            id: this.rowId,
          }
          saveOrUpdatePatientBcOrder(obj)
            .then((res) => {
              if (res.code == 200) {
                this.$message.success('保存成功')
                this.getRightList()
              }
            })
            .catch(() => {
              this.getRightList()
            })
        } else {
          this.$alert('页面存在必填项或者填写格式存在问题！', '提示', {
            confirmButtonText: '确定',
          })
        }
      })
    },
    getIdCard(IdCard, type) {
      if (type === 1) {
        //获取出生日期
        birthday = IdCard.substring(6, 10) + '-' + IdCard.substring(10, 12) + '-' + IdCard.substring(12, 14)
        return birthday
      }
      if (type === 2) {
        //获取性别
        if (parseInt(IdCard.substr(16, 1)) % 2 == 1) {
          return '男'
        } else {
          return '女'
        }
      }
      if (type === 3) {
        //获取年龄
        var ageDate = new Date()
        var month = ageDate.getMonth() + 1
        var day = ageDate.getDate()
        var age = ageDate.getFullYear() - IdCard.substring(6, 10) - 1
        if (IdCard.substring(10, 12) < month || (IdCard.substring(10, 12) == month && IdCard.substring(12, 14) <= day)) {
          age++
        }
        if (age <= 0) {
          age = 1
        }
        return age
      }
    },

    //右表数据
    getRightList() {
      this.loadingRight = true
      this.$refs.tableRules.clearValidate()
      getPatientDataOrder({
        idOrgreservationgroup: this.rowId,
      }).then((res) => {
        this.modelTableList.tableList = res.data.records
        this.modelTableList.tableList.forEach((el, index) => {
          this.$set(this.modelTableList.tableList[index], 'groupName', this.selection[0].orgreservationgroupname)
          this.$set(this.modelTableList.tableList[index], 'sort', index + 1)
          if (el.patientcode) {
            this.$set(this.modelTableList.tableList[index], 'checkboxcolumn', true)
          }
          this.$set(this.modelTableList.tableList[index], 'pinyin', pinyin(el.patientname))
        })
        let array = JSON.parse(JSON.stringify(res.data.records))
        this.griddataRight = array.map((el) => {
          if (el.id) {
            el.checkboxcolumn = true
          }
          el.pinyin = pinyin(el.patientname)
          return el
        })
        this.loadingRight = false
        this.deleteWarningMsg = false
      })
    },
    //备单确认
    confirmWindow() {
      var rows = this.selection
      if (rows.length == 0) {
        this.$alert('请选择分组', '提醒', {
          confirmButtonText: '确认',
          type: 'warning',
        })
        return
      }
      var ids = []
      for (var i = 0; i < rows.length; i++) {
        var row = rows[i]
        if (!row.id) {
          this.$alert('请先保存分组', '提醒', {
            confirmButtonText: '确认',
            type: 'warning',
          })
          return
        }
        ids.push(row.id)
      }
      this.$confirm('确定要确认备单吗?', '提醒', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          var obj = { ids: '' }
          for (var i in ids) {
            obj.ids += ids[i] + ','
          }
          obj.ids = obj.ids.substring(0, obj.ids.length - 1)
          groupConfirmOrder(obj).then((res) => {
            if (res.code == 200) {
              this.$message.success('操作成功！')
              this.getEditOrder()
            }
          })
        })
        .catch(() => {})
    },
    //左表保存
    saveWindow() {
      if (!this.form.datereservation.includes('00:00:00')) {
        this.form.datereservation += ' 00:00:00'
      }
      if (!this.form.planenddate.includes('00:00:00')) {
        this.form.planenddate += ' 00:00:00'
      }

      
      for (var i in this.tableData) {
        if (!this.tableData[i].idPayway) {
          this.$alert('支付方式:不能为空,请修改后重新操作', '提示')
          return
        }
        if (this.tableData[i].tcName && this.tableData[i].orgreservationgroupname) {
          if (i == this.tableData.length - 1) {
            this.form = {
              address: this.form.address,
              countexaminee: this.form.countexaminee,
              datereservation: this.form.datereservation,
              ddh: this.form.ddh,
              fFinished: this.form.fFinished,
              fzxId: this.form.fzxId,
              id: this.form.id,
              idOrg: this.form.idOrg,
              idOrgclass: this.form.idOrgclass,
              idSalesperson: this.form.idSalesperson,
              ordernum: this.form.ordernum,
              phone: this.form.phone,
              planenddate: this.form.planenddate,
              qtxz: this.form.qtxz,
              saleName: this.form.saleName,
              tjm: this.form.tjm,
              orgName: this.form.orgName,
              // "id": this.id[0]
            }

            this.griddata.forEach((el) => {
              if (el.idPayway == '0') {
                el.idPayway = '5'
              }
            })
            let obj = {
              griddata: this.griddata,
              formdata: this.form,
            }
            saveOrUpdateGroupOrder(obj)
              .then((res) => {
                if (res.code == 200) {
                  this.$message.success('保存成功!')
                  this.getEditOrder()
                }
              })
              .finally(() => {
                this.modelTableList.tableList = []
              })
          }
        } else {
          this.$message.error('无法在有未选择体检套餐或者分组名称的款项时进行保存!')
          return
        }
      }
    },
  },
}
</script>

<style scoped>
.setinput {
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 200px;
}

#setTextarea /deep/ .el-textarea__inner {
  height: 100px;
}

/* #tableLeftData /deep/ .el-table__header .el-checkbox {
  display: none;
} */
#tableLeftData /deep/ .el-table__cell,
#tableRightList /deep/ .el-table__cell {
  padding: 0;
}

#tableLeftData /deep/ .cell,
#tableRightList /deep/ .cell {
  border: none;
  padding: 0;
}

#tableLeftData /deep/ .el-input__inner {
  padding: 0 15px;
}

#tableLeftData /deep/ .el-input-number__decrease,
#tableRightList /deep/ .el-input-number__decrease {
  width: 24px;
}

#tableLeftData /deep/ .el-input-number__increase,
#tableRightList /deep/ .el-input-number__increase {
  width: 24px;
}

#tableLeftData /deep/ .el-date-editor .el-input__inner {
  padding-left: 30px;
}

/* #tableRightList /deep/ .el-table__header .el-checkbox {
  display: none;
} */
#tableRightList /deep/ .el-input__inner {
  height: 36px;
  max-height: 36px !important;
  overflow-y: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

#dialogMsg /deep/ .el-dialog__body {
  padding-top: 0;
  padding-bottom: 0;
}

#tableRightList /deep/ .el-form-item--medium {
  margin-bottom: 0px;
}

#tableRightList /deep/ .el-form-item__error {
  display: none;
}

#importDialog /deep/ .el-input__inner {
  border-radius: 0px;
}
</style>
