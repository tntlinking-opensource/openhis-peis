<!-- 个人防护用品添加  开发人：麦沃德科技半夏 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="140px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="个人防护用品代码" prop="defendIndividualCode">
        <el-input v-model="form.defendIndividualCode" placeholder="请输入个人防护用品代码" clearable />
      </el-form-item>
      <el-form-item label="个人防护用品名称" prop="defendIndividual">
        <el-input v-model="form.defendIndividual" placeholder="请输入个人防护用品名称" clearable @input="nameChange" />
      </el-form-item>
      <el-form-item label="个人防护用品分类" prop="defendIndividualClass">
        <input-select :selectData="defendIndividualClassData" :initialValue="form.defendIndividualClass" :isTrim="true" @change="defendIndividualClassChange"></input-select>
      </el-form-item>
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="form.inputCode" placeholder="输入个人防护用品名称自动生成" readonly clearable />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { detailsProtectiveEquipment, editProtectiveEquipment } from '@/api/basis/occupation/protective_equipment'
import pinyin from '@/utils/pinyin.js'
export default {
  data() {
    return {
      // 个人防护用品分类
      defendIndividualClassData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '分类名称', //第二列标题
        url: '/data/ZyFhclGr/getAllPersonalType', //请求连接
        bindValue: '',
        secondName: 'defendIndividualClass',
        selectWidth: "100%",
      },
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        defendIndividualCode: [{ required: true, message: '个人防护用品代码不能为空', trigger: 'change' }],
        defendIndividual: [{ required: true, message: '个人防护用品名称不能为空', trigger: 'change' }],
        defendIndividualClass: [{ required: true, message: '个人防护用品分类不能为空', trigger: 'change' }],
      },
    }
  },
  methods: {
    // 个人防护用品分类选择
    defendIndividualClassChange(value) {
      this.form.defendIndividualClass = value.id || ''
      this.defendIndividualClassData.bindValue = value.name
    },
    // 添加
    handleAdd() {
      this.popData = undefined
      this.reset()
      this.open = true
      this.title = '添加个人防护用品'
    },
    // 编辑
    handleUpdate(row) {
      this.popData = undefined
      this.reset()
      const id = row.id || this.ids
      this.open = true
      this.title = '编辑个人防护用品'
      this.popLoading = true
      detailsProtectiveEquipment(id).then((response) => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data
        this.defendIndividualClassData.bindValue = this.form.defendIndividualClass
      })
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.defendIndividualClassData.bindValue = this.form.defendIndividualClass
        return
      }
      this.form = {
        defendIndividualCode: undefined,
        defendIndividual: undefined,
        defendIndividualClass: undefined,
        inputCode: undefined,
      }
      this.defendIndividualClassData.bindValue = undefined
      this.resetForm('form')
    },
    // 个人防护用品分类名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    //取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          editProtectiveEquipment(this.form).then(response => {
            if (!this.form.id) {
              this.$modal.msgSuccess("修改成功");
            } else {
              this.$modal.msgSuccess("添加成功");
            }
            this.open = false;
            this.$emit('getList')
          });
        }
      })
    },
  },
}
</script>