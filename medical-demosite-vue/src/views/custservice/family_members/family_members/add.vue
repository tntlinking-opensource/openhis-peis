<!-- 家庭会员-家庭卡办理 麦沃德科技 开发人:清风 / 暴雨 -->
<template>
  <!-- <div class="add-container"> -->
  <el-dialog id="outsideDialog" title="家庭卡办理" :visible.sync="open" width="1850px" :close-on-click-modal="false">
    <div class="insideBox">
      <div class="insideBoxChild" style="overflow-x: hidden; overflow-y: scroll; margin-right: 32px">
        <div style="width: 600px; height: auto">
          <el-form size="small" :inline="true" label-width="100px" style="max-width: 600px" :model="form">
            <el-form-item>
              <el-tag style="width: 600px; height: 40px; line-height: 40px">家庭卡信息</el-tag>
            </el-form-item>
            <el-form-item label="售卡人" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.sellId" style="width: 500px; height: 40px" placeholder="admin"></el-input>
            </el-form-item>
            <el-form-item label="售卡时间" style="margin: 0 0 16px 0; padding: 0">
              <el-date-picker v-model="form.sellTime" style="width: 500px; height: 40px" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="选择日期时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="密码" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.password" style="width: 500px; height: 40px" placeholder="设置卡密码"></el-input>
            </el-form-item>
            <el-form-item label="密码确认" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.passwordConfirm" style="width: 500px; height: 40px" placeholder="重新输入卡密码"></el-input>
            </el-form-item>
            <el-form-item label="家庭卡号" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.cardno" style="width: 500px; height: 40px" placeholder="输入家庭卡号 , 按回车加载卡信息" @keyup.enter.native="LoadCard">
                <el-button style="margin: -4px" slot="suffix" type="primary" @click="LoadCard">加载卡信息</el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="金额" style="margin-right: 0">
              <el-input-number v-model="form.balanceMoney" style="width: 500px" controls-position="right" :min="0" :max="9999999" placeholder="0"></el-input-number>
            </el-form-item>
            <el-form-item label="积分" style="margin-right: 0">
              <el-input-number v-model="form.balanceJf" style="width: 500px" controls-position="right" :min="0" :max="9999999" placeholder="0"></el-input-number>
            </el-form-item>
            <el-form-item label="卡类型" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.typeName" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="卡前缀" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.cardPrefix" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="卡标志" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.cardLogo" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="有效期" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.validity" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="卡说明" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.cardState" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="卡备注" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.memo" style="width: 500px; height: 40px" :readonly="true"></el-input>
            </el-form-item>

            <el-form-item>
              <el-tag style="width: 600px; height: 40px; line-height: 40px">主持卡人信息</el-tag>
            </el-form-item>
            <el-form-item label="身份证号" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.idcardno" placeholder="请输入现有档案的身份证号,点击加载按钮" style="width: 305px; height: 40px"></el-input>
              <el-button style="width: 72px; margin-left: 16px" type="primary" size="mini" icon="el-icon-loading" plain @click="loadingText">加载</el-button>
              <el-button size="mini" type="warning" icon="el-icon-folder-add" plain @click="addText">新增档案</el-button>
            </el-form-item>
            <el-form-item label="姓名" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.patientname" :readonly="true" style="width: 500px; height: 40px"></el-input>
            </el-form-item>
            <el-form-item label="电话" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-model="form.phone" :readonly="true" style="width: 500px; height: 40px"></el-input>
            </el-form-item>
            <el-form-item label="性别" style="margin: 0 0 16px 0; padding: 0">
              <el-input v-if="form.idSex!=undefined" :value="form.idSex== 0 ? '男' : '女'" :readonly="true" style="width: 500px; height: 40px"></el-input>
              <el-input v-else  :readonly="true" style="width: 500px; height: 40px"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <div style="overflow-x: hidden; overflow-y: scroll">
        <div style="width: 1103px; max-height: 800px">
          <el-form size="small" :inline="true" label-width="100px" style="max-width: 1103px; max-height: 100px" :model="forms">
            <el-form-item>
              <el-tag style="width: 1103px; height: 40px; line-height: 40px">其他家庭成员信息</el-tag>
            </el-form-item>
            <el-form-item>
              <!-- 操作按钮 -->
              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <el-input style="width: 240px; height: 32px" v-model="formss.memberIdcardno" placeholder="请输入现有档案身份找号搜索"></el-input>
                </el-col>
                <el-col :span="1.5">
                  <el-button size="mini" type="primary" icon="el-icon-upload2" @click="sosoData">搜索</el-button>
                </el-col>
                <el-col :span="1.5">
                  <el-button style="width: 72px" size="mini" type="primary" icon="el-icon-plus" plain @click="addChilderWindow()" v-hasPermi="['custservice:familyMembers:dialogTable:add']">新增</el-button>
                </el-col>
                <el-col :span="1.5">
                  <el-button style="width: 72px" size="mini" type="danger" icon="el-icon-delete" plain @click="deleteData()" v-hasPermi="['custservice:familyMembers:dialogTable:delete']">删除</el-button>
                </el-col>
              </el-row>
            </el-form-item>
          </el-form>

          <div class="table-box" style="width: 1103px; max-height: 303px">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChanges" height="240px">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
              <el-table-column prop="idcardno" label="身份证号" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="idSex" label="性别" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.idSex == 0">男</span>
                  <span v-if="scope.row.idSex == 1">女</span>
                </template>
              </el-table-column>
              <el-table-column prop="phone" label="电话" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="memberlevel" label="会员级别" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.memberlevel == 1">普通会员</span>
                  <span v-if="scope.row.memberlevel == 2">VIP会员</span>
                  <span v-if="scope.row.memberlevel == 3">VVIP会员</span>
                </template>
              </el-table-column>
              <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="khjl" label="客户经理" align="center"></el-table-column>
              <el-table-column prop="birthdate" label="生日" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="dw" label="单位" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="note" label="备注" align="center" show-overflow-tooltip></el-table-column>
              <el-table-column prop="createDate" label="创建日期" align="center" show-overflow-tooltip></el-table-column>
            </el-table>
          </div>

          <el-form size="small" :inline="true" label-width="100p x" style="max-width: 1103px; max-height: 100px">
            <el-form-item>
              <el-tag style="width: 1103px; height: 40px; line-height: 40px; margin-top: 5px">收费信息</el-tag>
            </el-form-item>
            <el-form-item>
              <!-- 操作按钮-->
              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <el-button size="mini" type="primary" icon="el-icon-plus" @click="addData()" v-hasPermi="['custservice:familyMembers:paymoney:add']">新增</el-button>
                </el-col>
                <el-col :span="1.5">
                  <el-button style="width: 72px" size="mini" type="danger" icon="el-icon-delete" plain @click="removeData()" v-hasPermi="['custservice:familyMembers:paymoney:remove']">删除</el-button>
                </el-col>
              </el-row>
            </el-form-item>
          </el-form>

          <div class="table-box" style="width: 1103px; max-height: 280px">
            <el-table class="add-table" :data="tableDatas" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChanges" height="210px">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="moneyamountpaid" label="付款金额" align="center">
                <template slot-scope="scope">
                  <el-input size="mini" v-model="scope.row.moneyamountpaid" placeholder="请输入" style="width: 100%" />
                </template>
              </el-table-column>
              <el-table-column prop="idPayWay" label="付款方式" align="center">
                <template slot-scope="scope">
                  <el-select style="width: 100%" placeholder="请选择" v-model="scope.row.idPayWay" size="mini">
                    <el-option v-for="item in idExamsuite" :key="item.id" :value="item.id" :label="item.paywayName"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column prop="feecharger" label="收费员" align="center">
                <template slot-scope="scope">
                  <el-input size="mini" v-model="scope.row.feecharger" placeholder="请输入" style="width: 100%" />
                </template>
              </el-table-column>
              <el-table-column prop="moneyamountpaiddate" label="收费日期" align="center">
                <template slot-scope="scope">
                  <el-date-picker style="width: 100%" :clearable="false" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择日期" size="mini" v-model="scope.row.moneyamountpaiddate"></el-date-picker>
                </template>
              </el-table-column>
              <el-table-column prop="note" label="备注" align="center">
                <template slot-scope="scope">
                  <el-input size="mini" v-model="scope.row.note" placeholder="请输入" style="width: 100%" />
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="onOk">保存</el-button>
      <el-button type="primary" plain @click="onReload">重置</el-button>
      <el-button @click="onCancel">取消</el-button>
    </div>

    <!--弹窗-->
    <addChilderItems ref="addChilderItems" @closedialog="closedialog"></addChilderItems>
    <addOtherItems ref="addOtherItems" @closedialogs="closedialogs"></addOtherItems>
  </el-dialog>
  <!-- </div> -->
</template>

<script>
import addChilderItems from './add_childer.vue'
import addOtherItems from './add_other.vue'
import {
  getcardData,
  SaOrUp,
  getChargeInfoData,
  chargeLoadCard,
  searchIdcardno,
  getBranchData,
  getPayWayData,
  getInfoListData
} from '@/api/custservice/family_members/family_members/family_members'
import log from '@/views/monitor/job/log'
import { saveOrUpdate } from '@/api/report/health_handover/health_handover'
export default {
  components: { addChilderItems, addOtherItems },
  data() {
    return {
      // 是否为加载的著持卡人数据
      zckr:false,
      // 右侧临时index
      tempId: 0,
      // 右侧选中数组
      idsR: [],
      // 按照身份证查找档案
      departData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '', //请求连接
        bindValue: '',
      },
      memberIdcardno: '',
      loading: false,
      ids: [], //选中的数组
      single: '', //必选且单选
      multiple: '', //必选
      open: false,
      isTrue: false,
      forsm: {
        cardno: undefined,
      },
      //主持卡人信息,家庭卡信息
      form: {
        sellId: undefined,
        sellTime: undefined,
        password: undefined,
        passwordConfirm: undefined,
        cardno: undefined,
        balanceMoney: undefined,
        balanceJf: undefined,
        typeName: undefined,
        cardPrefix: undefined,
        cardLogo: undefined,
        validity: undefined,
        cardState: undefined,
        memo: undefined,
        idcardno: undefined,
        patientname: undefined,
        phone: undefined,
        idSex: undefined,
      },
      //其他家庭成员信息
      forms: {},
      tableData: [],
      //收费信息
      tableDatas: [],
      //获取付款方式
      idExamsuite: [],
      qian:0,
      //其它家庭成员
      formss:{
        memberIdcardno:''
      },
      //校验
      rules: {
        userEmail: [
          {
            pattern: /^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\.[a-z]{2,}$/,
            message: '请输入正确格式的邮箱',
            trigger: 'blur',
          },
        ],
        userPhone: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确格式的手机号码',
            trigger: 'blur',
          },
        ],
        idcardno: [
          { required: true, message: '身份证号不能为空', trigger: 'blur' },
          {
            pattern: /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
            message: '请输入正确格式的身份证号',
            trigger: 'blur',
          },
        ],
      },
    }
  },
  methods: {
    //开启弹窗
    //弹窗外层模块
    addWindow(cardno) {
      this.form = {
        sellId: undefined,
        sellTime: undefined,
        password: undefined,
        passwordConfirm: undefined,
        cardno: undefined,
        balanceMoney: undefined,
        balanceJf: undefined,
        typeName: undefined,
        cardPrefix: undefined,
        cardLogo: undefined,
        validity: undefined,
        cardState: undefined,
        memo: undefined,
        idcardno: undefined,
        patientname: undefined,
        phone: undefined,
        idSex: undefined,
      }
      this.tableData = []
      this.tableDatas = []
      this.open = true
      this.isTrue = true
      this.getfkfs()
      if (cardno.length > 1) {
        this.forsm.cardno = cardno
        //获取家庭卡信息模块数据
        getcardData(this.forsm).then((response) => {
          this.form = response.data.card
          this.form.cardno = response.data.card.cardNo
          //售卡人
          this.form.sellId = response.data.username
          //主持卡人信息
          this.form.patientname = response.data.archive.patientname
          this.form.phone = response.data.archive.phone
          this.form.idSex = response.data.archive.idSex
          this.form.idcardno = response.data.archive.idcardno
          this.form.sellTime = response.data.archive.createdate
        })
        //获取其它家庭成员信息模块数据
        getInfoListData(this.forsm).then((response) => {
          this.tableData = response.data
        })
        //获取收费项目信息
        getChargeInfoData(this.forsm).then((response) => {
          this.tableDatas = response.data.records
        })
      }
    },
    //获取分中心
    getfkfs() {
      getPayWayData().then((response) => {
        this.idExamsuite = response.data.records
      })
    },
    //重置页面
    onReload() {
      if (this.forsm.cardno) {
        this.addWindow(this.forsm.cardno)
      } else {
        this.form = {
          sellId: undefined,
          sellTime: undefined,
          password: undefined,
          passwordConfirm: undefined,
          cardno: undefined,
          balanceMoney: undefined,
          balanceJf: undefined,
          typeName: undefined,
          cardPrefix: undefined,
          cardLogo: undefined,
          validity: undefined,
          cardState: undefined,
          memo: undefined,
          idcardno: undefined,
          patientname: undefined,
          phone: undefined,
          sex: undefined,
        }
        this.tableData = []
        this.tableDatas = []
      }
    },

    //保存按钮
    onOk() {
      //家庭卡信息
      let archiveData = undefined
      if ( this.zckr == true|| this.form.id == undefined ) {
        archiveData = this.form
      }
      let formdata = {
        ...this.form,
        archiveData,
      }
      //其他家庭成员信息参数
      let memberGriddata = []
      this.tableData.forEach((el) => {
        let data = {
          balanceJf: el.balanceJf,
          balanceMoney: el.balanceMoney,
          birthdate: el.birthdate,
          createdate: el.createdate,
          dw: el.dw,
          fzx: el.fzx,
          fzxName: el.fzxName,
          idSex: el.idSex,
          idcardno: el.idcardno,
          inputCode: el.inputCode,
          isMain: el.isMain,
          khjl: el.khjl,
          khjlName: el.khjlName,
          levelName: el.levelName,
          memberlevel: el.memberlevel,
          note: el.note,
          patientarchiveno: el.patientarchiveno,
          patientcardno: el.patientcardno,
          patientname: el.patientname,
          phone: el.phone,
          state: el.state,
          typeName: el.typeName,
          id:el.id
        }
        memberGriddata.push(data)
      })

      let queryParams = {
        chargeGriddata: this.tableDatas,
        formdata,
        memberGriddata,
      }

      //遍历计算总金额
      this.qian=0
      this.tableDatas.forEach((vm) =>{
        this.qian += parseFloat(vm.moneyamountpaid)
      })

    

      if(this.form.balanceMoney != this.qian){
        this.$alert('卡余额与收费信息不符合,请查实修改!', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
      }else if (this.form.cardno == undefined) {
        this.$alert('请检查家庭卡信息模块信息不能为空!', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
      } else if (this.form.password != this.form.passwordConfirm) {
        this.$alert('两次输入的密码不一致,请重新输入密码!', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
      } else if (this.form.password == undefined || this.form.passwordConfirm == undefined) {
        this.$modal.msgWarning('密码和确认密码不能为空!')
      } else if (this.form.patientname == undefined || this.form.phone == undefined) {
        this.$alert('主持卡人信息模块信息不完善,请完善后提交!', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
      } else {
        SaOrUp(queryParams).then((response) => {
          this.$modal.msgSuccess('办理成功!')
          this.$emit('getList')
          this.open = false
        })
      }
    },
    //取消关闭页面
    onCancel() {
      // this.onReload()
      this.open = false
    },
    // 多选框选中数据
    handleSelectionChanges(selection, index) {
      this.ids = selection.map((item) => item.idss)
      this.idsR = selection.map((item) => item.index)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },

    // 家庭卡信息模块
    // 家庭卡信息模块 - 加载按钮
    LoadCard() {
      this.forsm.cardno = this.form.cardno
      chargeLoadCard(this.forsm).then((response) => {
        if (response.data.status == 'failed') {
          this.$modal.alertWarning(response.data.msg)
        } else {
         
          this.form = response.data.msg
          delete this.form.id;
       
          this.form.cardno = response.data.msg.cardNo
          this.$modal.msgSuccess('加载成功')
        }
      })
    },

    // 其他家庭成员信息模块
    // 其他家庭成员信息模块 - 新增按钮
    addChilderWindow() {
      this.$refs.addOtherItems.addWindow()
    },
    // 其他家庭成员信息模块 - 编辑按钮
    editData() {},
    // 其他家庭成员信息模块 - 搜索按钮
    sosoData() {
      this.forsm.cardno = this.formss.memberIdcardno
      searchIdcardno(this.forsm).then((response) => {
        if (response.data.status == 'failed') {
          this.$modal.alertWarning(response.data.msg)
        } else {
          this.tableData = response.data.msg
          this.$modal.msgSuccess('加载成功')
        }
      })
    },
    // 其他家庭成员信息传参
    closedialogs(models) {
      var zhi = {
        idss: this.tableData.length + 1,
        patientname: models.patientname,
        idcardno: models.idcardno,
        idSex: models.idSex,
        phone: models.phone,
        memberlevel: models.memberlevel,
        fzx: models.fzx,
        khjl: models.khjl,
        dw: models.dw,
        note: models.note,
        createDate: models.createDate,
        birthdate: models.birthdate,
      }
      this.tableData.push(zhi)
    },

    // 其他家庭成员信息模块 - 删除按钮
    deleteData() {
      this.ids.forEach((val) => {
        this.tableData.forEach((item, i) => {
          if (item.idss == val) {
            this.$delete(this.tableData, i)
          }
        })
      })
    },

    // 收费信息模块
    // 收费信息模块 - 新增数据
    addData() {
      this.tableDatas.unshift({
        index: this.tempId++,
        moneyamountpaid: undefined,
        idPayway: undefined,
        feecharger: undefined,
        moneyamountpaiddate: undefined,
        note: undefined,
        state: 'added',
      })
    },

    // 收费信息模块 - 删除新增行
    removeData() {
      this.idsR.forEach((val) => {
        this.tableDatas.forEach((el, index) => {
          if (val == el.index) {
            this.$delete(this.tableDatas, index)
          }
        })
        this.$modal.msgSuccess('移除成功!')
      })
    },

    // 主持卡人信息模块
    // 主持卡人信息模块 - 查询、新增档案-
    loadingText() {
      this.forsm.cardno = this.form.idcardno
      this.zckr=true
      searchIdcardno(this.forsm).then((response) => {
        if (response.data.status == 'failed') {
          this.$modal.alertWarning(response.data.msg)
        } else {
          this.form.patientname = response.data.patientname
          this.form.phone = response.data.phone
          this.form.idSex = response.data.idSex
          this.form.id = response.data.id
          // this.form.birthdate = response.data.birthdate
          this.$modal.msgSuccess('加载成功')
        }
      })
    },
    // 主持卡人传参
    closedialog(model) {
      this.form.idcardno = model.idcardno
      this.form.patientname = model.patientname
      this.form.idSex = model.idSex
      this.form.phone = model.phone
      this.form.dw = model.dw
      this.form.birthdate = model.birthdate
      this.form.memberlevel = model.memberlevel
      this.form.birthdate = model.birthdate

    },

    // 主持卡人信息模块 - 新增档案
    addText() {
      this.$refs.addChilderItems.addWindow()
    },
  },
}
</script>

<style scoped>
#outsideDialog /deep/ .el-dialog {
  height: 942px;
  position: absolute;
  left: 50px;
  top: 10px;
  overflow: hidden;
}
.insideBox {
  width: 1783px;
  height: 650px;
  display: flex;
  flex-direction: row;
}
.insideBoxChild::-webkit-scrollbar {
  display: none;
}
</style>
