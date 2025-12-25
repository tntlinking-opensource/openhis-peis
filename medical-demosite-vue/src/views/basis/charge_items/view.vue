<!-- 检查项目设置查看  开发人：麦沃德科技半夏/清风 -->
<template>
  <!-- 查看对话框 -->
  <el-dialog title="查看检查项目" :visible.sync="open" width="1000px" class="view-charge" append-to-body>
    <div class="flex-direction-column">
      <div class="table-box">
        <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="检查项目名称" prop="name" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="检查项目类型" prop="jcxmlx" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="性别" prop="sex" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-for="item in sexOptions" :key="item.id">
                <span v-if="item.id == scope.row.sex">{{ item.text }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="类型" prop="lx" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-for="item in lxOptions" :key="item.id">
                <span v-if="item.id == scope.row.lx">{{ item.text }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="科室名称" prop="ksmc" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="接口代码" prop="interfaceCode" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="体征上限" prop="tzsx" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="体征下限" prop="tzxx" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="危急值上限" prop="wjzsx" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="危急值下限" prop="wjzxx" min-width="120" align="center" show-overflow-tooltip />
        </el-table>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import { getListDatas } from "@/api/basis/charge.js";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
      },
      // 是否显示弹出层
      open: false,
      // 表格列表
      tableList: [],
      sexOptions:[
        {
					id: 0,
					text: "男"
				},
				{
					id: 1,
					text: "女"
				},
				{
					id: 2,
					text: "通用"
				}
      ],
      lxOptions:[
        {
					id: "0",
					text: "健康检查类型"
				}, {
					id: "1",
					text: "职业检查类型"
				}, {
					id: "2",
					text: "综合检查类型"
				}
      ]
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    // 显示
    handleShow(data) {
      this.open = true;
      this.loading = true;
      this.rowId = data.id;
      this.queryParams = Object.assign(this.queryParams,{
        id: data.id
      })
			getListDatas(this.queryParams).then(response => {
				this.tableList = response.data.records;
				this.total = response.data.total;
				this.loading = false;
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