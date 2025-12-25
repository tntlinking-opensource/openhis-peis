<!-- 创建套餐-设置平安ID  开发人：麦沃德科技半夏 -->
<template>
  <el-dialog title="设置平安ID" width="auto" :visible.sync="open">
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item label="请输入平安套餐ID" prop="pinganId">
        <el-input v-model="form.pinganId" placeholder="请输入平安套餐ID" clearable style="width: 260px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { setPinganId } from "@/api/sale/create_offer";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 表单
      form: {},
      // 表单校验
			rules: {
				pinganId: [{ required: true, message: "平安套餐ID不能为空", trigger: "change" }],
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
      this.reset();
      this.open = true;
      this.form.id = id
    },
    // 表单重置
    reset() {
      this.form = {
        pinganId: undefined,
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
      setPinganId(this.form).then(() => {
        this.$modal.msgSuccess("操作成功！");
        this.open = false;
        this.$emit("getList")
      });
    },
  },
};
</script>
<style scoped>
.el-form-item {
  display: flex;
}
</style>