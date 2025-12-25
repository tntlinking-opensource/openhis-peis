<!-- 体检基础套餐维护-重复弹窗 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <!-- 重复弹窗 -->
  <el-dialog title="重复" :visible.sync="open" width="450px" append-to-body>
    <el-form ref="form" :model="form" :inline="true" label-width="110px" hide-required-asterisk>
      <el-form-item label="适用性别" prop="syxb">
        <el-select style="width: 260px" v-model="form.syxb" placeholder="">
          <el-option label="男" :value="0"></el-option>
          <el-option label="女" :value="1"></el-option>
          <el-option label="通用" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接害因素" prop="jhId">
        <search-select ref="searchSelect" :selectData="selectData" @change="change1" :selectWidth="260"> </search-select>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="zyValue">
        <el-select style="width: 260px" v-model="form.zyValue" placeholder="">
          <el-option label="上岗期间" value="0"></el-option>
          <el-option label="在岗期间" value="1"></el-option>
          <el-option label="离岗时" value="2"></el-option>
          <el-option label="离岗后" value="3"></el-option>
          <el-option label="应急" value="4"></el-option>
        </el-select>
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
import { getDetailData, copyOrder, repeatData } from '@/api/basis/healthsmall'
export default {
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 遮罩层
      loading: true,
      // 关联搜索
      selectData: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/sell/createcombo/getJhysData', //请求连接
        firstName: 'harmCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
        queryData: 'key',
        bindValue: '',
      },
      // 表单参数
      form: {
        tcId: '',
        syxb: '',
        jhId: '',
        zyValue: '',
      },
    }
  },
  methods: {
    // 添加
    handleAd(id) {
      this.reset()
      this.open = true
      this.form.tcId = id
      this.getDetailData()
    },
    //获取详情数据
    getDetailData() {
      repeatData({ id: this.form.tcId }).then(({ data }) => {
        this.form.syxb = data.syxb
        this.form.zyValue = data.zytjlb
      })
    },
    // 表单重置
    reset() {
      this.selectData.bindValue = undefined
      this.resetForm('form')
    },
    change1(value) {
      this.selectData.bindValue = value.harmName
      this.form.jhId = value.id
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      copyOrder(this.form).then(() => {
        this.$modal.msgSuccess('修改成功')
        this.open = false
        this.$emit('getList')
      })
    },
  },
}
</script>
<style lang="scss">
.add-items {
  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #d4d6d9;
    .el-form {
      padding: 16px 16px 0;

      .el-form-item {
        margin-bottom: 24px;
      }
    }

    .el-table__empty-block {
      width: 100% !important;
      min-height: 120px;
    }

    .el-table__body-wrapper {
      min-height: 120px;
    }

    .el-pagination {
      padding: 16px 6px 24px;
    }

    .flex {
      display: flex;
    }

    .left-table {
      width: 35%;
      min-width: 600px;
      margin: 0 -1px;
    }

    .center-btn {
      width: 140px;
      background: #fff;
      height: auto;
      border: 1px solid #dfe6ec;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .el-icon--left {
        transform: rotate(-90deg);
      }

      .el-icon--right {
        transform: rotate(90deg);
      }
    }

    .right-table {
      width: calc(65% - 140px);
      min-width: 600px;
      flex: 1;
      margin: 0 -1px;
    }
  }
}
</style>
