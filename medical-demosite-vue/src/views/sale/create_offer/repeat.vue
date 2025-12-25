<!-- 创建套餐-复制  开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="复制" width="460px" :visible.sync="open">
    <el-form ref="form" v-loading="loading" :model="form" label-width="120px" :rules="rules" hide-required-asterisk>
      <el-form-item label="适用性别" prop="syxb">
        <el-select v-model="form.syxb" placeholder="请选择适用性别" style="width: 280px">
          <el-option :key="0" label="男" :value="0" />
          <el-option :key="1" label="女" :value="1" />
          <el-option :key="2" label="通用" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="接害因素" prop="jhId">
        <el-tooltip effect="dark" :content="form.jhys" placement="top" :disabled="!form.jhId" v-if="tjlx != 0">
          <search-select ref="searchSelect" :selectData="jhysData" :multiple="true" @change="jhysChange" :selectWidth="280"></search-select>
        </el-tooltip>
        <el-input disabled style="width: 280px" v-else></el-input>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="zyValue">
        <el-select v-model="form.zyValue" :placeholder="tjlx == 0 ? '' : '请选择职业体检类别'" style="width: 280px" :disabled="tjlx == 0">
          <el-option v-for="item in zytjlbOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getCreatemeal, repeatCreatemeal } from "@/api/sale/create_offer";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 遮罩层
      loading: false,
      // 表单
      form: {},
      // 体检类型
      tjlx: undefined,
      // 接害因素搜索数据
      jhysData: {
        placeholder: '请选择...',
        inputTitle: '危害因素',// 搜索标题
        inputPlaceholder: '请输入输入码搜索',// 搜索placeholder
        key: '输入码',
        value: '危害因素名称',
        bindValue: "",
        url: '/sell/createmeal/getJhysDataByFzx',//请求连接
        firstName: 'inputCode',//接口返回值对应第一列的参数名
        secondName: 'harmName',//接口返回值对应第二列的参数名
      },
      // 职业体检类别
      zytjlbOptions: [
        { 'id': 0, 'text': '上岗前' },
        { 'id': 1, 'text': '在岗期间' },
        { 'id': 2, 'text': '离岗时' },
        { 'id': 3, 'text': '离岗后' },
        { 'id': 4, 'text': '应急' }
      ],
      // 表单校验
      rules: {
        syxb: [{ required: true, message: "适用性别不能为空", trigger: "change" }],
        zyValue: [{ required: true, message: "职业体检类别不能为空", trigger: "change" }],
      },
    };
  },
  computed: {},
  watch: {},
  created() { },
  mounted() { },
  methods: {
    // 显示
    handleShow(id) {
      this.loading = true
      this.open = true;
      this.form.id = id
      this.$nextTick(() => {
        this.reset();
      })
      getCreatemeal(id).then(res => {
        this.loading = false
        this.form = {
          syxb: res.data.syxb,
          jhId: res.data.jhys || undefined,
          jhys: res.data.jhysName || undefined,
          zyValue: res.data.zytjlb || undefined,
          tcId: res.data.id,
        }
        this.tjlx = res.data.tjlx
        if (res.data.jhysName) this.jhysData.bindValue = res.data.jhysName.split(",")
      });
    },
    // 接害因素选择 
    jhysChange(value) {
      this.form.jhId = ""
      this.form.jhys = ""
      console.log("值是",value);
      console.log("form.jhId是",this.form.jhId);
      

      value.forEach(el => {
        if (!this.form.jhId) {
          this.form.jhId = el.id
          this.form.jhys = el.label
        } else {
          this.form.jhId += "," + el.id
          this.form.jhys += "," + el.label
        }
      })

      console.log("form是",this.form);

    },
    // 表单重置
    reset() {
      this.form = {
        syxb: undefined,
        jhId: undefined,
        jhys: undefined,
        zyValue: undefined,
        tcId: undefined,
      }
      if (this.tjlx != 0) {
        this.$refs.searchSelect.initData()
        this.jhysData.bindValue = undefined
      }
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      repeatCreatemeal(this.form).then(() => {
        this.$modal.msgSuccess("重复成功！");
        this.open = false;
        this.$emit("getList")
      });
    },
  },
};
</script>