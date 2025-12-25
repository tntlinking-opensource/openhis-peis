<template>
  <el-col :span="13" style="height: 100%" class="funcdept-reception-right">
    <div class="flex-direction-column">
      <!-- 中间表格信息 -->
      <div class="table-box flex-direction-column">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" @click="abandonCheck" :disabled="multiple" v-hasPermi="['funcdept:preregistration:abandonCheck']">弃检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="noabandonCheck" :disabled="multiple" v-hasPermi="['funcdept:preregistration:noabandonCheck']">反弃检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-edit-outline" @click="lateCheck" :disabled="multiple" v-hasPermi="['funcdept:preregistration:lateCheck']">迟检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="nolateCheck" :disabled="multiple" v-hasPermi="['funcdept:preregistration:nolateCheck']">反迟检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-edit-outline" @click="suppinspection" :disabled="multiple" v-hasPermi="['funcdept:preregistration:suppinspection']">补检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="nosuppinspection" :disabled="multiple" v-hasPermi="['funcdept:preregistration:nosuppinspection']">反补检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="rejectinspection" :disabled="multiple" v-hasPermi="['funcdept:preregistration:rejectinspection']">拒检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="norejectinspection" :disabled="multiple" v-hasPermi="['funcdept:preregistration:norejectinspection']">反拒检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleInspect(1)" :disabled="multiple" v-hasPermi="['funcdept:preregistration:rejectinspection']">已检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-edit-outline" @click="handleInspect(0)" :disabled="multiple" v-hasPermi="['funcdept:preregistration:norejectinspection']">未检</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-upload2" @click="handlePDF(1)" :disabled="multiple" v-hasPermi="['funcdept:preregistration:uploadPDF']">上传PDF报告</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-view" @click="handlePDF(2)" :disabled="single" v-hasPermi="['funcdept:preregistration:checkPDF']">查看PDF报告</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-view" @click="handleAllPDF()" v-hasPermi="['funcdept:preregistration:checkPDF']">查看全部PDF报告</el-button>
          </el-col>
        </el-row>
        <div class="table-show">
          <el-table class="el-table" ref="tableList" border v-loading="loading" :data="tableList" height="100%" :row-class-name="tableRowClassName" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column fixed type="selection" width="45" align="center" />
            <el-table-column fixed label="序列" min-width="60" type="index" align="center" />
            <el-table-column fixed label="收费项目" prop="examfeeitemName" align="center" min-width="180" show-overflow-tooltip> </el-table-column>
            <el-table-column label="职业必查" prop="zybj" align="center" show-overflow-tooltip min-width="100">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.zybj == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="登记" prop="fRegistered" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fRegistered == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="退项" prop="changeItem" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.changeItem == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="已退" prop="fMarkFeereturn" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fMarkFeereturn == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="已收" prop="fFeecharged" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fFeecharged == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="已发" prop="fLabsendtolis" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fLabsendtolis == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="已检" prop="fExaminated" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fExaminated == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="已做" prop="giveupnotelet" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.giveupnotelet == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="弃检" prop="fGiveup" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fGiveup == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="迟检" prop="fDelayed" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fDelayed == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="拒检" prop="sfjj" align="center" show-overflow-tooltip min-width="60">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.sfjj == 1"><i class="el-icon-check"></i> </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="补检" prop="fTransferedhl7" align="center" show-overflow-tooltip min-width="70">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.fTransferedhl7 == 0">待补检 </el-tag>
                <el-tag v-else-if="scope.row.fTransferedhl7 == 1">已补检 </el-tag>
                <span v-else></span>
              </template>
            </el-table-column>
            <el-table-column label="科室" prop="ksmc" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="弃检人" prop="qjr" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="弃检时间" prop="qjsj" align="center" show-overflow-tooltip min-width="130" />
            <el-table-column label="迟检人" prop="cjr" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="迟检时间" prop="cjsj" align="center" show-overflow-tooltip min-width="130" />
            <el-table-column label="补检人" prop="bjr" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="补检时间" prop="bjsj" align="center" show-overflow-tooltip min-width="130" />
            <el-table-column label="拒检人" prop="jjr" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="拒检时间" prop="jjsj" align="center" show-overflow-tooltip min-width="130" />
            <el-table-column label="价格" align="center">
              <el-table-column label="原始单价" prop="price" align="center" show-overflow-tooltip min-width="100" />
              <el-table-column label="优惠单价" prop="factprice" align="center" width="110px">
                <template slot-scope="scope">
                  <el-input :readonly="true" v-model="scope.row.factprice" style="width: 85px" type="number" size="mini"></el-input>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="付款方式" prop="idPayway" align="center" show-overflow-tooltip min-width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.idPayway == 1">现金</span>
                <span v-else-if="scope.row.idPayway == 5">统收</span>
                <span v-else></span>
              </template>
            </el-table-column>
            <el-table-column label="加项医师" prop="name" align="center" show-overflow-tooltip min-width="100" />
            <el-table-column label="登记人" prop="doctorregR" align="center" show-overflow-tooltip min-width="100" />
            <el-table-column label="收费时间" prop="feechargetime" align="center" show-overflow-tooltip min-width="100" />
            <el-table-column label="查看图片" prop="feeitemdesc" align="center" width="90px">
              <template slot-scope="scope">
                <span class="check-pic" @click="checkPic(scope.row)">查看图片</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-image ref="previewImg" :src="chargeUrl" :preview-src-list="[chargeUrl]" style="display: none"></el-image>
      </div>
      <!-- 下方统计数据 -->
      <el-row type="flex" justify="space-between" style="width: 100%">
        <el-col :span="24" style="border: 1px solid #d4d6d9">
          <div class="statistics">
            <div class="item" v-for="(item, index) in statisticsList" :key="index">
              <div class="title">{{ item.title }}</div>
              <div class="number" :style="{ '--theme': theme }">{{ index == 0 ? item.count : item.count ? Number(item.count).toFixed(2) : '0.00' }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
      <signature ref="signature" @saveSignPath="saveSignPath" @saveSignPath2="saveSignPath2"></signature>
    </div>
    <el-dialog :title="PdfTitle" :visible.sync="openUploadPDF" width="500px" append-to-body :close-on-click-modal="false">
      <div id="uploadLoading">
        <div style="margin-bottom: 12px">
          {{ PdfTitle == '上传PDF报告' ? '请确认上传的PDF文件为所选“' : '当前PDF文件为所选“' }}
          <span style="color: red">
            {{states ? states.map((item) => item.examfeeitemName).join(',') : ''}}
          </span>
          ”项目的报告
        </div>
        <file-upload v-if="PdfTitle == '上传PDF报告'" ref="uploadFile" :uploadData="uploadData" uploadWidth="100%" :requestType="2" @uploadFinish="uploadFinish"></file-upload>
        <div v-else>
          <div v-for="(item, index) in pdfUrlList" :key="index">
            <a :href="$getCookie('imgPath') + item" download style="color: #0059ff; cursor: pointer" target="_blank">查看报告</a>
            <span style="color: red; margin-left: 20px; cursor: pointer" @click="deletePDF(item, index)">删除</span>
          </div>
        </div>
        <div class="dialog-footer" style="text-align: right; margin-top: 12px">
          <el-button type="primary" @click="handleUploadFile">确 定</el-button>
          <el-button @click="openUploadPDF = false">取 消</el-button>
        </div>
      </div>
    </el-dialog>

    <el-dialog title="查看全部报告" :visible.sync="openAllPDF" width="600px" append-to-body :close-on-click-modal="false">
      <div style="width: 100%;height: 460px; overflow-y: auto;">
        <div style="padding-bottom: 16px;" v-for="(item, index) in allPdfData" :key="index">
          <div style="display: flex; align-items: center;">
            <div style="flex: 1;">{{ item.examfeeitemName }}</div>
            <a :href="$getCookie('imgPath') + item.pdfUrl" download style="color: #0059ff; padding-right: 16px;  cursor: pointer" target="_blank">查看报告</a>
            <div style="color: red; cursor: pointer" @click="deletePDF(item.pdfUrl, index)">删除</div>
          </div>
          <div style="padding-top: 16px;">
            <el-image style="width: 200px; height: 200px" :src="item.filePath[0]" :preview-src-list="item.filePath">
            </el-image>
          </div>
        </div>
      </div>
    </el-dialog>
  </el-col>
</template>

<script>
import Cookies from 'js-cookie'
import { getInspectData, giveUpCheck, buJian, juJian, fanJuJian, isDanger, getPayWay, getPicture, uploadFile, queryThirdReports, deleteThirdReports, modifyProjectStatus, viewThirdPartyImages } from '@/api/funcdept/preregistration'
import signature from '@/views/funcdept/section_list/list/dialog/signature'
export default {
  components: { signature },
  props: ['patientCode'],
  data() {
    return {
      queryParams: {},
      // 数据列表
      statisticsList: [
        { title: '总数', count: 0 },
        { title: '原始单价合计', count: 0 },
        { title: '待收费合计', count: 0 },
        { title: '套餐价格', count: 0 },
        { title: '合计', count: 0 },
      ],
      // 图片地址
      imgPath: Cookies.get('imgPath'),
      // 表格加载
      loading: false,
      // 收费项目参数
      selectData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        third: '价格', //第三列标题
        url: '', //请求连接
        selectWidth: 230, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(不传为空)
      },
      // 预览图片链接
      chargeUrl: '',
      // 弃检查参数
      paramsGiveUp: {
        //补检保存更新的数据
        data: {
          type: 0,
          value: undefined,
        },

        //支付方式
        payWayListL: [],
        //	保存更新数据
        formdata: {},
        //	保存表格数据
        griddata: [],
        //	0 预约 1 登记 2 保存
        intReservation: 2,
        //	1通知检验科
        noticeJyk: undefined,
        // 体检码（saveOrUpdateItem方法使用）
        patientCode: undefined,
        // 默认体检者照片
        picture: undefined,
      },
      //补检参数
      buJianParams: {
        //	保存表格数据
        formdata: {},
        // 新增数据
        data: {},
        // 体检号
        patientCode: undefined,
      },
      //拒检参数
      juJianParams: {
        ids: undefined,
        data: undefined,
      },
      //fan拒检参数
      fanJuJianParams: {
        ids: '',
      },
      // 总数据
      mainData: {},
      // 表格数据
      tableList: [],
      // 表格选中的数据
      states: [],
      selectTable: [],
      single: true,
      multiple: true,
      ids: [],
      R: [],
      saveSelection: [],
      selection: [],
      // 全部pdf弹窗
      openAllPDF: false,
      // 组件间传递参数
      resData: undefined,
      // 打开上传PDF对话框
      openUploadPDF: false,
      // pdf对话框名称
      PdfTitle: undefined,
      // 上传组件参数
      uploadData: {
        url: '/SignInInspection/uploadThirdReports', //文件上传地址
        multiple: false, //是否可以上传多个
        fileType: ['pdf'], //文件类型
        data: {}, //上传时附带的额外参数
        limit: 1,
      },
      // 上传的pdf列表
      pdfUrlList: [],
      // 上传加载中
      uploadLoading: undefined,
      // 全部报告数据
      allPdfData: []
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  created() {
    this.bus.$on('getRightData', (response) => {
      this.resData = response
      this.getListDataRight(response)
      this.getAllpdf()
    })
  },
  beforeDestroy() {
    this.bus.$off('getRightData')
  },
  methods: {
    // 查看全部报告
    handleAllPDF() {
      if (this.allPdfData.length == 0) {
        this.$modal.msgError(`暂无相关报告`)
        return
      }
      this.openAllPDF = true
    },
    // 查看全部pdf报告
    getAllpdf() {
      viewThirdPartyImages({ patientcode: this.patientCode }).then((response) => {
        this.allPdfData = response.data

        // 修正 filePath 为数组形式
        this.allPdfData.forEach(item => {
          if (item.filePath instanceof Set) {
            item.filePath = Array.from(item.filePath); // 将 Set 转换为 Array
          }
          // 遍历 filePath 数组，并为每个路径添加 imgPath 前缀
          item.filePath = item.filePath.map(path => `${this.imgPath}${path}`);
        });
      })
    },
    //表格颜色
    tableRowClassName({ row, rowIndex }) {
      //
      if (row.fExaminated != 1 && row.fGiveup != 1 && !row.fTransferedhl7 && row.fTransferedhl7 !== 0 && row.fTransferedhl7 !== 1 && row.sfjj !== 1 && row.sfjj != 1) {
        return 'warning-row'
      } else {
        return ''
      }
    },
    // 获取表格数据
    getListDataRight(response) {
      this.loading = true
      if (response.code == 200) {
        this.mainData = response.data.patientData
        // 弃检 迟检参数
        this.paramsGiveUp.formdata = response.data.patientData
        this.paramsGiveUp.patientCode = response.data.patientData.patientcode
        //补检参数
        //拒检参数
        this.tableList = response.data.examfeeitemData
        this.loading = false
        this.countPrice()
      } else {
        this.loading = false
      }
      this.danger()
    },
    //是否危机
    danger() {
      isDanger({ patientcode: this.paramsGiveUp.patientCode }).then((response) => {
        if (response.data == 1) {
          this.$modal
            .confirm('经检测，此体检号有相关的危急值提报未处理，是否现在处理？')
            .then(function () {
              return
            })
            .then(() => {
              this.enterRouter()
            })
            .then(() => {
              this.$modal.msgSuccess('跳转成功')
            })
            .catch(() => { })
        }
      })
    },
    //跳转路由
    enterRouter() {
      const obj = { path: '/funcdept/manage_crisis_value', name: 'Manage_crisis_value' }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name: 'Manage_crisis_value', params: { patientCode: this.paramsGiveUp.patientCode } })
      })
    },
    //计算价格
    countPrice() {
      for (var i in this.statisticsList) {
        this.statisticsList[i].count = 0
      }
      for (let i = 0; i < this.tableList.length; i++) {
        this.statisticsList[1].count += parseFloat(this.tableList[i].price)
        this.statisticsList[4].count += parseFloat(this.tableList[i].factprice)
      }
      this.statisticsList[0].count = this.tableList.length
    },
    // 弃检
    abandonCheck() {
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 弃检状态
            if (this.states[i].fGiveup == 1) {
              this.$message.error(this.states[i].examfeeitemName + '收费项目已经是弃检状态')
              return
            }
            // 补检状态
            if (this.states[i].fTransferedhl7 === 0) {
              this.$message.error(this.states[i].examfeeitemName + '收费项目待补检，无法进行弃检操作')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行弃检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + ' 收费项目已退项，不可以进行弃检操作')
          return
        }
      }
      this.paramsGiveUp.griddata = JSON.parse(JSON.stringify(this.saveSelection))
      for (var i = 0; i < this.states.length; i++) {
        // this.saveSelection[i].fGiveup = 1
        this.paramsGiveUp.griddata[i].fGiveup = 1
        this.paramsGiveUp.griddata[i].state = 'modified'
        if (i == this.states.length - 1) {
          this.$confirm('是否确认弃检并签名?', '提示', {
            distinguishCancelAndClose: true,
            cancelButtonText: '直接弃检',
            confirmButtonText: '签名弃检',
          })
            .then(() => {
              this.$refs.signature.signature('qj')
            })
            .catch((action) => {
              if (action === 'cancel') {
                giveUpCheck(this.paramsGiveUp).then((response) => {
                  this.$modal.msgSuccess('操作成功')
                  this.open = false
                  this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                })
              }
            })
        }
      }
    },
    //反弃检
    noabandonCheck() {
      var s = 0
      this.paramsGiveUp.griddata = JSON.parse(JSON.stringify(this.saveSelection))
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 弃检状态
            if (this.states[i].fGiveup == 1) {
              this.paramsGiveUp.griddata[i].fGiveup = 0
              this.paramsGiveUp.griddata[i].state = 'modified'
              if (i == this.states.length - 1) {
                s = 1
              }
              if (s == 1) {
                giveUpCheck(this.paramsGiveUp).then((response) => {
                  this.$modal.msgSuccess('操作成功')
                  this.open = false
                  this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                })
              }
            } else {
              this.$message.error(this.states[i].examfeeitemName + '收费项目已经是反弃检状态')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行反弃检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + ' 收费项目已退项，不可以进行反弃检操作')
          return
        }
      }
    },
    //迟检
    lateCheck() {
      var s = 0
      this.paramsGiveUp.griddata = JSON.parse(JSON.stringify(this.saveSelection))
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 弃检状态
            if (this.states[i].fGiveup != 1) {
              //迟检
              if (this.states[i].fDelayed != 1) {
                this.paramsGiveUp.griddata[i].fDelayed = 1
                this.paramsGiveUp.griddata[i].state = 'modified'
                if (i == this.states.length - 1) {
                  s = 1
                }

                if (s == 1) {
                  giveUpCheck(this.paramsGiveUp).then((response) => {
                    this.$modal.msgSuccess('操作成功')
                    this.open = false
                    this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                  })
                }
              } else {
                this.$message.warning(this.states[i].examfeeitemName + '收费项目已经是迟检状态，不可以迟检！！')
                return
              }
            } else {
              this.$message.warning(this.states[i].examfeeitemName + '收费项目已经是弃检状态，不可以迟检！！')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行迟检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + ' 收费项目已退项，不可以进行迟检操作')
          return
        }
      }
    },
    //反迟检
    nolateCheck() {
      var s = 0
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 迟检状态
            if (this.states[i].fGiveup != 1) {
              if (this.states[i].fDelayed == 1) {
                this.states[i].fDelayed = 0
                this.paramsGiveUp.griddata = this.states
                this.paramsGiveUp.griddata[i].state = 'modified'
                if (i == this.states.length - 1) {
                  s = 1
                }
                if (s == 1) {
                  giveUpCheck(this.paramsGiveUp).then((response) => {
                    this.$modal.msgSuccess('操作成功')
                    this.open = false
                    this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                  })
                }
              } else {
                this.$message.error(this.states[i].examfeeitemName + '收费项目是反迟检状态，无法进行反迟检')
                return
              }
            } else {
              this.$message.error(this.states[i].examfeeitemName + '收费项目是弃检状态，无法进行反弃')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行反迟检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + ' 收费项目已退项，不可以进行反迟检操作')
          return
        }
      }
    },
    //补检
    suppinspection() {
      var s = 0
      this.paramsGiveUp.griddata = JSON.parse(JSON.stringify(this.saveSelection))
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 弃检状态
            if (this.states[i].fGiveup != 1) {
              // 补检状态
              if (this.states[i].fTransferedhl7 != 0) {
                this.paramsGiveUp.data.value = 0
                this.paramsGiveUp.griddata[i].fTransferedhl7 = 0
                this.paramsGiveUp.griddata[i].state = 'modified'
                if (i == this.states.length - 1) {
                  s = 1
                }
                if (s == 1) {
                  buJian(this.paramsGiveUp).then((response) => {
                    this.$modal.msgSuccess('操作成功')
                    this.open = false
                    this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                  })
                }
              } else {
                this.$message.error(this.states[i].examfeeitemName + '收费项目已经是待补检状态')
                return
              }
            } else {
              this.$message.error(this.states[i].examfeeitemName + '收费项目已弃检，无法进行补检操作')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行补检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + ' 收费项目已退项，不可以进行补检操作')
          return
        }
      }
    },
    //反补检
    nosuppinspection() {
      var s = 0
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        if (this.states[i].changeItem != 1) {
          // 已检
          if (this.states[i].fExaminated != 1) {
            // 补检状态
            if (this.states[i].fTransferedhl7 == 0) {
              this.paramsGiveUp.data.value = 1
              this.paramsGiveUp.griddata = this.saveSelection
              this.paramsGiveUp.griddata[i].state = 'modified'
              if (i == this.states.length - 1) {
                s = 1
              }
              if (s == 1) {
                buJian(this.paramsGiveUp).then((response) => {
                  this.$modal.msgSuccess('操作成功')

                  this.open = false
                  this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
                })
              }
            } else {
              this.$message.error(this.states[i].examfeeitemName + '收费项目为非补检状态，无法进行反补检')
              return
            }
          } else {
            this.$message.error(this.states[i].examfeeitemName + '收费项目已检查，无法进行反补检操作')
            return
          }
        } else {
          this.$message.error(this.states[i].examfeeitemName + '收费项目已退项，不可以进行反补检操作')
          return
        }
      }
    },
    //拒检
    rejectinspection() {
      for (var i = 0; i < this.states.length; i++) {
        // 退项状态
        // 弃检状态
        this.states[i].sfjj = 1
      }
      this.$refs.signature.signature('jj')
    },
    //反拒检
    norejectinspection() {
      for (var i in this.states) {
        this.states[i].sfjj = 0
      }
      this.fanJuJianParams.ids = this.ids.join(',')
      fanJuJian(this.fanJuJianParams.ids).then((response) => {
        this.$modal.msgSuccess('操作成功')
        this.open = false
        this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
      })
    },
    // 设置已检未检
    handleInspect(fexaminated) {
      let text = ''
      this.selection.forEach((el) => {
        if (el.fExaminated == 1 && fexaminated == 1) {
          text = el.examfeeitemName + '已经是已检状态'
        } else if (el.fExaminated == 0 && fexaminated == 0) {
          text = el.examfeeitemName + '已经是未检状态'
        }
      })
      if (text) {
        this.$alert(text, '提示')
        return
      }
      modifyProjectStatus({
        fexaminated,
        ids: this.ids,
      }).then(() => {
        this.$modal.msgSuccess('操作成功')
        this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
      })
    },
    // 签名返回值
    saveSignPath(url, position) {
      var bytes = window.atob(url.split(',')[1])
      var array = []
      for (var i = 0; i < bytes.length; i++) {
        array.push(bytes.charCodeAt(i))
      }
      var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' })
      var formData = new FormData()
      formData.append('file', blob)
      this.loading = true
      uploadFile(formData)
        .then((res) => {
          if (position == 'jj') {
            // 进行拒检处理
            this.juJianParams.data = res.data
            this.juJianParams.ids = this.ids.join(',')
            juJian(this.juJianParams).then((response) => {
              this.$modal.msgSuccess('操作成功')
              this.open = false
              this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
            })
          } else {
            this.paramsGiveUp.path = res.data
            giveUpCheck(this.paramsGiveUp).then((response) => {
              this.$modal.msgSuccess('操作成功')
              this.open = false
              this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
            })
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 签名本地上传返回值
    saveSignPath2(url, position) {
      if (position == 'jj') {
        // 进行拒检处理
        this.juJianParams.data = url
        this.juJianParams.ids = this.ids.join(',')
        juJian(this.juJianParams).then((response) => {
          this.$modal.msgSuccess('操作成功')
          this.open = false
          this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
        })
      } else {
        this.paramsGiveUp.path = url
        giveUpCheck(this.paramsGiveUp).then((response) => {
          this.$modal.msgSuccess('操作成功')
          this.open = false
          this.bus.$emit('refreshRight', this.paramsGiveUp.patientCode)
        })
      }
    },
    //查看图片弹窗
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
    // 上传、查看PDF
    handlePDF(type) {
      let error = ''
      this.states.forEach((el) => {
        if (el.fReportalone != 1) {
          // error = `收费项目${el.examfeeitemName}不可以上传pdf,请重新选择`
        } else if (el.fExaminated != 1) {
          error = `收费项目${el.examfeeitemName}未检,请已检后重试`
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      if (type == 1) {
        this.uploadData.data = {
          feeItemId: this.ids.join(','),
          patientcode: this.paramsGiveUp.patientCode,
        }
        this.PdfTitle = '上传PDF报告'
        this.openUploadPDF = true
        this.$nextTick(() => {
          this.$refs.uploadFile.resetUpload()
        })
      } else {
        this.PdfTitle = '查看PDF报告'
        this.pdfUrlList = []
        queryThirdReports({
          feeItemId: this.ids[0],
          patientcode: this.paramsGiveUp.patientCode,
        }).then(({ data }) => {
          if (data) {
            this.pdfUrlList = data
            this.openUploadPDF = true
          } else {
            this.$alert(`收费项目${this.states[0].examfeeitemName}未上传PDF`, '提示')
          }
        })
      }
    },
    // 提交
    handleUploadFile() {
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.uploadLoading = this.$loading({ target: '#uploadLoading', text: '上传中' })
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
        this.loading = false
      }
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        if (res.data) {
          this.$modal.msgSuccess('上传成功')
          this.uploadLoading.close()
          this.openUploadPDF = false
        } else if (res.timeout) {
          this.$modal.msgWarning('后台上传中,请勿重复上传')
          this.uploadLoading.close()
          this.openUploadPDF = false
        } else {
          this.uploadLoading.close()
        }
      }
    },
    // 删除pdf
    deletePDF(filePath, index) {
      this.$confirm('确认删除上传的pdf文件吗?', '提示')
        .then(() => {
          deleteThirdReports({
            feeItemId: this.ids[0],
            patientcode: this.paramsGiveUp.patientCode,
            filePath,
          }).then(() => {
            this.$modal.msgSuccess('删除成功')
            this.$delete(this.pdfUrlList, index)
            if (!this.pdfUrlList.length) {
              this.PdfTitle = '上传PDF报告'
            }
          })
        })
        .catch()
    },
    // 表格选中
    handleSelectionChange(selection) {
      this.saveSelection = selection.slice(0)
      this.states = JSON.parse(JSON.stringify(selection))
      this.ids = selection.map((item) => item.id)
      this.auditStatus = selection.map((item) => item.auditStatus)
      this.selection = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
  },
}
</script>

<style lang="scss">
.funcdept-reception-right {
  .table-show {
    flex: 1;
    overflow-y: auto;
  }

  .statistics {
    display: flex;
    align-items: center;
    margin-right: 30px;

    .item {
      display: flex;
      align-items: center;
      height: 100%;
      padding: 4px;
      margin: 4px 8px;
      background: #f7f8fa;
      border-radius: 10px;

      .title {
        font-size: 16px;
        line-height: 17px;
        color: #000;
        margin-right: 8px;
      }

      .number {
        font-weight: 600;
        font-size: 24px;
        line-height: 34px;
        color: #{'var(--theme)'} !important;
      }
    }
  }

  .operation {
    display: flex;
    margin-top: 8px;

    .item {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100px;
      padding: 10px 0;
      margin-left: 16px;
      box-shadow: 0px 0px 10px rgba(0, 89, 255, 0.5);
      border-radius: 5px;

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

  .el-table .warning-row {
    background: #ffec8b;
  }

  .el-table .success-row {
    background: #ffec8b;
  }
}
</style>
<style scoped>
.funcdept-reception-right /deep/ .el-table .el-table__cell {
  height: auto;
  padding: 0;
}
</style>
