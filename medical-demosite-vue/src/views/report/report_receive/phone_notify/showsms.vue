<!-- 职业报告领取通知-查看短信 开发人：麦沃德科技暴雨、予安 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog class="add-container" :title="title" :visible.sync="open" width="790px" append-to-body>
    <el-table border :data="examList" stripe height="400px" v-loading="loading">
      <el-table-column fixed type="selection" align="center" />
      <el-table-column label="序列" type="index" width="65" align="center" />
      <el-table-column label="短信内容" prop="notifyContent" align="center" show-overflow-tooltip />
      <el-table-column label="发送时间" prop="notifyTime" align="center" show-overflow-tooltip />
      <el-table-column label="发送状态" prop="notifyResult" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ ['通知失败', '已取消', '等待通知', '已通知'][scope.row.notifyResult] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" prop="creater" align="center" show-overflow-tooltip />
    </el-table>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
  </el-dialog>
</template>
<script>
import { getSmsData } from "@/api/report/report_receive/phone_notify.js";
export default {
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      queryParams: {
        current: 1,
        size: 10,
        patientcode:undefined
      },
      // 选中数组
      examList: [],
      // 总条数
      total: 10,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
    };
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() { },
  methods: {
    // 查看短信
    handleViewsms(code) {
      this.open = true;
      this.title = "短信查看";
      this.getList(code)
    },
    // 查询列表
    getList(code) {
      this.loading = true;
      this.queryParams.patientcode = code
      getSmsData(this.queryParams).then(({ data }) => {
      
        this.examList = data.records;
        this.total = data.total;
        this.loading = false;
      });
    },
  },
};
</script>