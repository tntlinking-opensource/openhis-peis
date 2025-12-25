<!-- 创建套餐查看  开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="查看" :visible.sync="open" width="840px" append-to-body>
    <el-form :model="form" v-loading="loading" label-width="120px" :inline="true" hide-required-asterisk>
      <el-form-item label="体检套餐名称" prop="tjtcmc">
        <el-input style="width:260px;" v-model="form.tjtcmc" readonly></el-input>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx" v-if="form.tjlx || form.tjlx == 0">
        <div v-for="item in tjlxOptions" :key="item.id">
          <el-input style="width:260px;" :value="item.text" v-if="item.id == form.tjlx"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx" v-else>
        <el-input style="width:260px;" readonly></el-input>
      </el-form-item>
      <el-form-item label="体检套餐简称" prop="tjtcjc">
        <el-input style="width:260px;" v-model="form.tjtcjc" readonly></el-input>
      </el-form-item>
      <el-form-item label="体检套餐输入码" prop="tjtcsrm">
        <el-input style="width:260px;" v-model="form.tjtcsrm" readonly></el-input>
      </el-form-item>
      <el-form-item label="适用性别" prop="syxb">
        <el-input style="width:260px;" value="男" readonly v-if="form.syxb == 0"></el-input>
        <el-input style="width:260px;" value="女" readonly v-if="form.syxb == 1"></el-input>
        <el-input style="width:260px;" value="通用" readonly v-if="form.syxb == 2"></el-input>
      </el-form-item>
      <el-form-item label="项目原价合计" prop="tcysjg">
        <el-input style="width:260px;" v-model="form.tcysjg" readonly></el-input>
      </el-form-item>
      <el-form-item label="平均折扣" prop="tczk">
        <el-input style="width:260px;" v-model="form.tczk" readonly></el-input>
      </el-form-item>
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input style="width:260px;" v-model="form.khdwmc" readonly></el-input>
      </el-form-item>
      <el-form-item label="是否已备单" prop="sfybd">
        <el-input style="width:260px;" value="是" readonly v-if="form.sfybd == 1"></el-input>
        <el-input style="width:260px;" value="否" readonly v-else-if="form.sfybd == 0"></el-input>
        <el-input style="width:260px;" readonly v-else></el-input>
      </el-form-item>
      <el-form-item label="是否已婚套餐" prop="sfyhtc">
        <el-input style="width:260px;" value="是" readonly v-if="form.sfyhtc == 0"></el-input>
        <el-input style="width:260px;" value="否" readonly v-else-if="form.sfyhtc == 1"></el-input>
        <el-input style="width:260px;" value="通用" readonly v-else></el-input>
      </el-form-item>
      <el-form-item label="年龄自" prop="nlz">
        <el-input style="width:260px;" v-model="form.nlz" readonly></el-input>
      </el-form-item>
      <el-form-item label="年龄至" prop="nld">
        <el-input style="width:260px;" v-model="form.nld" readonly></el-input>
      </el-form-item>
      <el-form-item label="付款方式" prop="fkfs">
        <el-input style="width:260px;" value="统收" readonly v-if="form.fkfs == 0"></el-input>
        <el-input style="width:260px;" value="自费" readonly v-else-if="form.fkfs == 1"></el-input>
        <el-input style="width:260px;" readonly v-else></el-input>
      </el-form-item>
      <el-form-item label="团惠价" prop="zhjgapp">
        <el-input style="width:260px;" v-model="form.zhjgapp" readonly></el-input>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="zytjlb" v-if="form.zytjlb || form.zytjlb == 0">
        <div v-for="item in zytjlbOptions" :key="item.id">
          <el-input style="width:260px;" :value="item.text" v-if="item.id == form.zytjlb"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="zytjlb" v-else>
        <el-input style="width:260px;" readonly></el-input>
      </el-form-item>
      <el-form-item label="分中心" prop="fzxName">
        <el-input style="width:260px;" v-model="form.fzxName" readonly></el-input>
      </el-form-item>
      <el-form-item label="备选组数量" prop="kxsl">
        <el-input style="width:260px;" v-model="form.kxsl" readonly></el-input>
      </el-form-item>
      <el-form-item label="备选组价格" prop="groupPrice">
        <el-input style="width:260px;" v-model="form.groupPrice" readonly></el-input>
      </el-form-item>
      <el-form-item label="接害因素" prop="jhys">
        <el-input type="textarea" :autosize="autosize" style="width:650px;" v-model="form.jhys" readonly></el-input>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
import { getCreatemeal } from "@/api/sale/create_offer";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 表单
      form: {},
      // 文本域自适应内容高度参数
      autosize: { minRows: 4, maxRows: 4 },
      // 体检类型
      tjlxOptions: [
        { id: 0, text: "健康体检" },
        { id: 1, text: "职业体检" },
        { id: 2, text: "综合" }
      ],
      // 职业体检类别
      zytjlbOptions: [
        { 'id': '0', 'text': '上岗前' },
        { 'id': '1', 'text': '在岗期间' },
        { 'id': '2', 'text': '离岗时' },
        { 'id': '3', 'text': '离岗后' },
        { 'id': '4', 'text': '应急' }
      ],
      // 加载
      loading: false,
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
      getCreatemeal(id).then(res => {
        this.loading = false
        this.form = res.data;
      });
    },
  },
};
</script>
<style lang="scss">
.view-charge {
  .el-dialog {
    height: 100%;
    max-height: 700px;
  }
}
</style>