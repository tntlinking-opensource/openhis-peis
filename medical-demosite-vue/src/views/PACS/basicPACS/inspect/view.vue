<!-- PACS检查项目-查看 开发人：麦沃德科技矢北/半夏 -->
<template>
  <el-dialog title="查看检查项目" :visible.sync="open" class="PACS-inspect" width="85%" append-to-body
    :close-on-click-modal="false">
    <el-form ref="form" :model="form" size="mini" :inline="true" hide-required-asterisk v-loading="formLoading">
      <el-form-item prop="examitemName" label="名称">
        <el-input :value="form.examitemName" readonly></el-input>
      </el-form-item>
      <el-form-item prop="inputCode" label="输入码">
        <el-input :value="form.inputCode" readonly></el-input>
      </el-form-item>
      <el-form-item prop="fMale" label="男/女标示">
        <el-input value="男" readonly v-if="form.fMale == 0"></el-input>
        <el-input value="女" readonly v-else-if="form.fMale == 1"></el-input>
        <el-input value="通用" readonly v-else-if="form.fMale == 2"></el-input>
      </el-form-item>
      <el-form-item prop="type" label="类别">
        <el-input value="健康体检" readonly v-if="form.type == 0"></el-input>
        <el-input value="职业体检" readonly v-else-if="form.type == 1"></el-input>
        <el-input value="综合" readonly v-else-if="form.type == 2"></el-input>
      </el-form-item>
      <el-form-item prop="deptName" label="科室">
        <el-input :value="form.deptName" readonly></el-input>
      </el-form-item>
    </el-form>
    <div class="table-box">
      <el-table border v-loading="tableLoading" :data="tableList" height="100%" stripe>
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="顺序" min-width="60" prop="orderindex" align="center" />
        <el-table-column label="体征词名称" min-width="160" prop="name" align="center" show-overflow-tooltip />
        <el-table-column label="是否阳性结果" width="100" prop="isPositive" align="center">
          <template slot-scope="scope">
            <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isPositive == 1"><i class="el-icon-check"></i>
            </el-tag>
            <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
          </template>
        </el-table-column>
        <el-table-column label="体征详细描述" min-width="180" prop="bodyDetail" align="center" show-overflow-tooltip />
        <el-table-column label="结论词" min-width="120" prop="resultName" align="center" show-overflow-tooltip />
        <el-table-column label="可疑疾病重症级0到9级" min-width="100" prop="intensiveLevel" align="center" />
        <el-table-column label="默认" width="100" prop="isDefault" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag effect="dark" class="tag-checkbox" v-if="scope.row.isDefault == 1"><i class="el-icon-check"></i>
            </el-tag>
            <el-tag effect="dark" type="info" class="tag-checkbox" v-else></el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" plain @click="cancel">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getExamData, getSignList } from '@/api/PACS/basicPACS/inspect.js'
export default {
  data() {
    return {
      //遮罩层
      formLoading: false,
      tableLoading: false,
      // 表单
      form: {},
      // 开关
      open: false,
      // 表格数据
      tableList: [],
    }
  },
  methods: {
    // 查看按钮 
    handleView(id) {
      this.open = true
      this.formLoading = true
      this.tableLoading = true
      this.getExamData(id)
      this.getSignList(id)

    },
    // 获取检查项目详情
    getExamData(id) {
      getExamData(id).then((res) => {
        this.formLoading = false
        this.form = res.data
      })
    },
    // 获取结论词列表
    getSignList(id) {
      getSignList({ id: id }).then((res) => {
        this.tableLoading = false
        this.tableList = res.data
      })
    },
    //取消按钮
    cancel() {
      this.open = false;
    },
  }
}
</script>

<style lang="scss">
.PACS-inspect {
  .el-dialog {
    max-width: 1640px;
    height: 85%;

    .el-dialog__body {
      display: flex;
      flex-direction: column;

      .table-box {
        flex: 1;
      }
    }
  }
}
</style>