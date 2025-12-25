<template>
  <el-dialog :title="title" :visible.sync="open" width="428px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="72px" :inline="true" hide-required-asterisk
      v-loading="popLoading">
      <el-form-item prop="pid" label="父级" style="margin-right: 0;">
        <treeselect v-model="form.pid" :options="parentOptions" :normalizer="normalizer" style="width: 310px"
          placeholder="请选择父级" />
      </el-form-item>
      <el-form-item prop="partName" label="名称" style="margin-right: 0;">
        <el-input v-model="form.partName" style="width:310px" placeholder="请输入名称" @input="nameChange"></el-input>
      </el-form-item>
      <el-form-item prop="inputCode" label="输入码" style="margin-right: 0;">
        <el-input v-model="form.inputCode" style="width:310px" readonly placeholder="系统自动生成"></el-input>
      </el-form-item>
      <el-form-item prop="enName" label="英文名" style="margin-right: 0;">
        <el-input v-model="form.enName" style="width:310px" placeholder="请输入英文名"></el-input>
      </el-form-item>
      <el-form-item prop="partSort" label="序号" style="margin-right: 0;">
        <el-input-number v-model="form.partSort" controls-position="right" style="width:310px" :min="0"
          :max="999"></el-input-number>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getAutoCompleteData, getBasePart, saveOrUpdate } from '@/api/PACS/basicPACS/place_maintain.js'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import pinyin from "@/utils/pinyin.js";
export default {
  components: { Treeselect },
  data() {
    return {
      // 表单参数
      form: {},
      // 弹出层标题
      title: "",
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否显示弹出层
      open: false,
      // 父级选项
      parentOptions: [],
      // 表单校验
      rules: {
        partName: [{ required: true, message: "名称不能为空", trigger: "change" }],
      },
    }
  },
  methods: {
    // 名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 编辑
    handleUpdate(id) {
      this.popData = undefined
      this.reset();
      this.getOptions()
      this.popLoading = true
      this.open = true;
      this.title = "编辑";
      getBasePart(id).then(response => {
        response.data.pid = response.data.pid || 0
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.form = response.data;
        this.popLoading = false
      });
    },
    // 添加按钮 
    handleAdd() {
      this.popData = undefined;
      this.reset()
      this.title = '添加'
      this.open = true
      this.getOptions()
    },
    // 获取部位列表
    getOptions() {
      getAutoCompleteData().then(res => {
        this.parentOptions = []
        const menu = { id: 0, partName: '主类目', children: [] }
        menu.children = this.handleTree(res.data, 'id', 'pid')
        this.parentOptions.push(menu)
      });
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          saveOrUpdate(this.form).then(() => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.$emit("getList")
          });
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.popData) {
				this.form = JSON.parse(JSON.stringify(this.popData))
				return
			}
      this.form = {
        pid: 0,
        partName: undefined,
        inputCode: undefined,
        enName: undefined,
        partSort: undefined,
      }
      this.resetForm("form");
    },
    // 转换部门数据结构
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.partName,
        children: node.children
      };
    },
  }
}
</script>

<style></style>