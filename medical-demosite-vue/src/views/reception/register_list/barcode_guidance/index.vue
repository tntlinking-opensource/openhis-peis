<!-- 批量打印与导引单 开发人麦沃德科技 清风 -->
<template>
  <el-dialog title="批量打印" :visible.sync="open" width="460px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="100px" :rules="rules" hide-required-asterisk>
      <el-form-item label="模式">
        <el-radio-group v-model="form.model" :disabled="setModel">
          <el-radio v-for="item in modelType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="导引单样式">
        <el-radio-group v-model="form.dydStyle" :disabled="setDydStyle">
          <el-radio v-for="item in dydStyleType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="内容">
        <el-radio-group v-model="form.radio">
          <el-radio v-for="item in radioType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(1)">确定</el-button>
      <el-button type="primary" plain @click="cancel">取消</el-button>
      <el-button type="success" @click="submitForm(2)">应急</el-button>
    </div>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
      <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" />
    </object>
  </el-dialog>
</template>

<script>
import { printBarCode } from '@/utils/printBarCode.js'

export default {
  data() {
    return {
      open: false,
      form: {
        model: '1',
        dydStyle: '1',
        radio: '2',
      },
      rules: {},
      setModel: false,
      setDydStyle: false,
      //模式
      modelType: [
        { id: '0', text: '设计' },
        { id: '1', text: '打印' },
      ],
      //导引单样式
      dydStyleType: [
        { id: '0', text: '简单' },
        { id: '1', text: '复杂' },
        { id: '2', text: '普通' },
      ],
      //内容
      radioType: [
        { id: '0', text: '条码' },
        { id: '1', text: '导引单' },
        { id: '2', text: '条码和导引单' },
      ],
      // 体检号数组
      ids: [],
      // 体检id数组
      idPs: [],
      selection: undefined,
    }
  },
  computed: {
    watchRadio() {
      return this.form.radio
    },
  },
  watch: {
    watchRadio: {
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
  mounted() {
    // let script = document.createElement('script')
    // script.type = 'text/javascript'
    // script.src = 'http://localhost:8000/CLodopfuncs.js?'
    // document.body.appendChild(script)
  },
  methods: {
    openBarcodeGuidance(selection) {
      this.open = true
      this.form.model = '1'
      this.form.dydStyle = '1'
      this.form.radio = '2'
      this.ids = selection.map((item) => item.patientcode)
      this.idPs = selection.map((item) => item.id)
      this.selection = selection
    },
    //获取cookie
    getCookie(cookieName) {
      const strCookie = document.cookie
      const cookieList = strCookie.split(';')
      for (let i = 0; i < cookieList.length; i++) {
        const arr = cookieList[i].split('=')
        if (cookieName === arr[0].trim()) {
          return arr[1]
        }
      }
      return ''
    },
    //确定
    submitForm(type) {
      const query = {
        id: this.getCookie('cid'), //分中心id
        ids: this.idPs, //体检者ids
        model: this.form.model, //模板id
      }
      let value = this.form.radio
      if (value == '0') {
        printBarCode(query, () => {
          this.openPrint = false
        })
      } else {
        let queryParams = {
          id: this.getCookie('cid'), //分中心id
          ids: this.ids, //体检者ids
          model: this.form.model, //模板id
          type: '0',
          dydStyle: this.form.dydStyle, //导引单类型 0 简单 1 复杂 2 普通
          printBar: undefined,
        }
        if (value == '2') {
          queryParams.printBar = JSON.stringify(query)
        }
        let routeUrl = this.$router.resolve({
          name: 'BillsModelList',
          query: queryParams,
        })
        window.open(routeUrl.href, '_blank')
        this.openPrint = false
      }
    },
    cancel() {
      this.open = false
    },
  },
}
</script>
