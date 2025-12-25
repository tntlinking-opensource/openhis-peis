<!-- 团体预约-添加 开发人：麦沃德科技 予安 -->
<template>
  <!-- 添加弹窗 -->
  <el-dialog title="添加" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false" @close="cancel">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" hide-required-asterisk v-loading="addLoading" inline>
      <el-form-item label="团体订单" prop="orderNum">
        <input-select :selectData="selectData2" :queryParams="{ size: '20' }" @change="selectChange2"></input-select>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjlId">
        <input-select :selectWidth="340" :selectData="saleSelectData" :disabled="true"></input-select>
      </el-form-item>
      <el-form-item label="套餐" prop="comboId">
        <input-select :selectWidth="340" :selectData="comboSelectData" :queryParams="{ idExamsuite: form.orderId }" :disabled="!form.orderId" @change="selectChange3"></input-select>
      </el-form-item>
      <el-form-item label="预约分中心" prop="branchId">
        <el-select ref="fzx" v-model="form.branchId" placeholder="请选择预约的分中心" clearable style="width: 340px" :disabled="!form.xsjlId || !form.orderNum">
          <el-option v-for="item in fzxOptions" :key="item.branchId" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="预约类型" prop="levelId">
        <el-select v-model="form.levelId" placeholder="请选择预约类型" clearable style="width: 340px" @change="levelChange">
          <el-option v-for="options in levelIdOptions" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="预约人数" prop="countAm">
        <el-input-number v-model="form.countAm" controls-position="right" :min="0" :precision="0" placeholder="请输入预约人数" style="width: 340px" />
      </el-form-item>
      <el-form-item label="预约开始日期" prop="startDate">
        <el-date-picker v-model="form.startDate" type="datetime" placeholder="选择开始日期" value-format="yyyy-MM-dd HH:mm:ss" style="width: 340px"> </el-date-picker>
      </el-form-item>
      <el-form-item label="预约结束日期" prop="endDate">
        <el-date-picker v-model="form.endDate" type="datetime" placeholder="选择结束日期" value-format="yyyy-MM-dd HH:mm:ss" style="width: 340px"> </el-date-picker>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" style="width: 810px" />
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-search" @click="checkDetails">查看当前时间段预约详情</el-button>
      </el-col>
    </el-row>

    <!--  表格部分 -->
    <el-tag style="width: 100%; font-size: 15px">请选择预约时间段。注：同单位每日预约上限5名VIP或贵宾，如超出名额，则超出的名额进入审批任务</el-tag>
    <el-table ref="tableList" style="width: 100%" border :data="tableList" v-loading="loading" height="300" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column prop="reservationDate" label="预约日期" align="center" />
      <el-table-column prop="levelName" label="预约类型" align="center" />
      <el-table-column prop="maxNumAm" label="人数上限" align="center" />
      <!-- <el-table-column prop="maxNumPm" label="人数上限(下午)" align="center" /> -->
      <el-table-column prop="doneNumAm" label="已预约人数" align="center" />
      <!-- <el-table-column prop="doneNumPm" label="已预约人数(下午)" align="center" /> -->
      <el-table-column prop="ableNumAm" label="剩余可预约人数" align="center" />
      <!-- <el-table-column prop="ableNumPm" label="剩余可预约人数(下午)" align="center" /> -->
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { addListData, groupList, getDetails, getReVipNumber } from '@/api/subscribe/group_appointment.js'
import { getListUserData } from '@/api/subscribe/my_appointment/my_appointment'

export default {
  data() {
    var checkDate = (rule, value, callback) => {
      let toData = new Date(new Date().toLocaleDateString()).getTime()
      let past7daysStart = toData + 7 * 3600 * 24 * 1000
      if (new Date(this.form.endDate) < new Date(this.form.startDate)) {
        callback(new Error('日期格式不正确'))
      }
      // else if (new Date(toData) > new Date(this.form.startDate)) {
      //   callback(new Error('预约日期要大于当前日期'))
      // }
      else {
        callback()
      }
    }
    return {
      // ############# 弹窗
      addLoading: false,
      // 表单参数
      form: {
        xsjlId: undefined,
        xsjlName: undefined,
        orderId: undefined,
        orderNum: undefined,
        orderName: undefined,
        branchId: undefined,
        comboId: undefined,
        levelId: 1,
        levelName: '普通会员',
        countAm: 0,
        // countPm: 0,
        startDate: undefined,
        endDate: undefined,
        remark: undefined,
      },
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        // xsjlId: [{ required: true, message: '销售经理不能为空', trigger: 'change' }],
        comboId: [{ required: true, message: '套餐不能为空', trigger: 'change' }],
        orderNum: [{ required: true, message: '团体订单不能为空', trigger: 'change' }],
        branchId: [{ required: true, message: '预约分中心不能为空', trigger: 'change' }],
        levelId: [{ required: true, message: '预约类型不能为空', trigger: 'change' }],
        countAm: [{ required: true, message: '预约人数不能为空', trigger: 'change' }],
        startDate: [
          { required: true, message: '预约开始日期不能为空', trigger: 'change' },
          { validator: checkDate, trigger: 'change' },
        ],
        endDate: [
          { required: true, message: '预约结束日期不能为空', trigger: 'change' },
          { validator: checkDate, trigger: 'change' },
        ],
      },
      //销售经理列表
      xsjlList: [],
      // 销售经理参数
      saleSelectData: {
        placeholder: '选择订单后自动填写',
        key: '输入码', //第一列标题
        value: '销售经理名称', //第二列标题
        url: '/system/user/getListByKey', //请求连接
        bindValue: '', //初始值
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 套餐参数
      comboSelectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '套餐名称', //第二列标题
        url: '/reception/order/getAllTcForOrder', //请求连接
        bindValue: '', //初始值
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        firstName: 'tjtcsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'tjtcmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 团体订单参数
      selectData2: {
        placeholder: '请输入订单号选择',
        key: '订单号', //第一列标题
        value: '订单名称', //第二列标题
        url: '/sell/createorder/getOrderInfo', //请求连接
        bindValue: '', //初始值
        selectWidth: 340, //选择器宽度（选填，默认230）不加px,传100%则为100%
        firstName: 'ddh', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'ddmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'ddh', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 预约分中心列表
      fzxOptions: [],
      // 预约类型列表
      levelIdOptions: [],
      // 表格加载中
      loading: false,
      // 表格信息
      tableList: [],
    }
  },
  methods: {
    //获取预约类型
    getUserData() {
      getListUserData().then((response) => {
        this.levelIdOptions = response.data
      })
    },
    // 销售经理改变
    saleSelectChange(value) {
      this.form.xsjlId = value.id
      this.form.xsjlName = value.username
    },
    // 团体订单改变
    selectChange2(value) {
      this.selectData2.bindValue = value.ddmc
      this.form.xsjlId = value.xsjlid
      this.form.xsjlName = value.xsjl
      this.saleSelectData.bindValue = value.xsjl
      this.form.orderId = value.id
      this.form.orderNum = value.ddh
      this.form.orderName = value.ddmc
      this.form.idOrg = value.khdwmcid
      this.fzxOptions = value.branchList
      if (value.branchList.length === 1) {
        this.form.branchId = value.branchList[0].branchId
      }
    },
    // 套餐名称改变
    selectChange3(value) {
      this.form.comboId = value.id
      this.form.tjtcmc = value.tjtcmc
      this.comboSelectData.bindValue = value.tjtcmc
    },
    // 预约类型改变
    levelChange() {
      if (this.form.levelId) {
        this.levelIdOptions.forEach((el) => {
          if (el.levelId == this.form.levelId) {
            this.form.levelName = el.levelName
          }
        })
      }
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.form.reservationDate = undefined
      } else if (selection.length == 1) {
        this.form.reservationDate = selection[0].reservationDate
      } else if (selection.length > 1) {
        this.$refs.tableList.clearSelection() //清空表格数据
        this.$refs.tableList.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 查看当前时间段预约详情
    checkDetails() {
      let error = ''
      if (!this.form.branchId) {
        error = '请先选择预约分中心!'
      } else if (!this.form.startDate) {
        error = '请先选择起始日期!'
      } else if (!this.form.endDate) {
        error = '请先选择结束日期!'
      } else if (new Date(this.form.endDate) < new Date(this.form.startDate)) {
        error = '日期格式不正确,请重新选择!'
      } else if (new Date() > new Date(this.form.startDate)) {
        error = '预约日期不能小于当前日期,请重新选择!'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.loading = true
      groupList(this.form)
        .then(({ data }) => {
          this.tableList = data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 添加
    handleAdd() {
      this.open = true
      this.reset()
      this.getUserData()
    },
    // 表单重置
    reset() {
      this.selectData2.bindValue = ''
      this.saleSelectData.bindValue = ''
      this.comboSelectData.bindValue = ''
      this.resetForm('form')
      this.tableList = []
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.countAm) {
            this.$alert('预约人数不能小于1', '提示')
            return
          }
          this.addLoading = true
          if (this.form.levelId != 1) {
            getReVipNumber({
              endDate: this.form.endDate,
              startDate: this.form.startDate,
              idOrg: this.form.idOrg,
            }).then(({ data }) => {
              let msg = []
              data.forEach((el) => {
                if (Number(el.reservationNum) >= Number(el.totalNum)) {
                  msg.push(el.reservationDate.slice(0, 10))
                }
              })
              if (msg.length) {
                this.$alert(`所选日期${msg.join('、') + '，' + (this.form.levelId == 2 ? 'vip' : 'vvip')}预约已超额,请提醒审批人进行审批`, '提示')
              }
              addListData(this.form)
                .then(() => {
                  this.addLoading = false
                  this.$modal.msgSuccess('添加成功')
                  this.open = false
                  this.$emit('getList')
                })
                .catch((error) => {
                  console.error(error)
                  this.addLoading = false
                })
            })
          } else {
            addListData(this.form)
              .then(() => {
                this.addLoading = false
                this.$modal.msgSuccess('添加成功')
                this.open = false
                this.$emit('getList')
              })
              .catch((error) => {
                console.error(error)
                this.addLoading = false
              })
          }
        }
      })
    },
  },
}
</script>
