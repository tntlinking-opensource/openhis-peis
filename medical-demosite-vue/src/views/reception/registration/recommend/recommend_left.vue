<template>
  <div style="width: 40%;" class="tableLeft">
    <!-- 上部分 -->
    <el-row style="margin-bottom:10px">
      <el-col :span="24" style="border: 1px solid #D4D6D9;">
        <!-- 表格 -->
        <el-table ref="tableTop" :data="tableListTop" size="mini" stripe class="tableList" @selection-change="handleSelectionChangeTop"
          border height="220" v-loading="loadingTop" @row-dblclick="addsSf" @row-click="rowClickTop">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" align="center" type="index" />
          <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip />
          <el-table-column label="性别" prop="xb" align="center">
            <template slot-scope="scope">
              <div v-for="item in xbOptions" :key="item.id">
                <span v-if="item.id == scope.row.xb">{{ item.text }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="价格" prop="jg" align="center" />
          <el-table-column label="体检类型" prop="tjlx" align="center">
            <template slot-scope="scope">
              {{ tjlx(scope.row.tjlx) }}
            </template>
          </el-table-column>
          <el-table-column label="人次" prop="sycstj" align="center" />
        </el-table>
        <!-- 分页 -->
        <el-pagination v-show="totalTop > 0" :total="totalTop" :page.sync="pageNumTop" small :limit.sync="pageSizeTop"
          @size-change="topSizeChange" @current-change="topCurrentChange" :page-sizes="[10, 20, 30, 40]"
          layout="sizes, prev, pager, next, jumper" :page-size="100" style="padding: 10px;" />
      </el-col>
    </el-row>

    <!-- 下部分 -->
    <el-row>
      <el-col :span="24" style="border: 1px solid #D4D6D9;">
        <!-- 筛选 -->
        <el-form :model="queryParamsBelow" ref="queryFormBelow" size="mini" :inline="true" @submit.native.prevent
          style="padding:16px;padding-bottom: 0;">
          <el-form-item label="体检套餐输入码" prop="tjtcsrm">
            <el-input v-model="queryParamsBelow.tjtcsrm" placeholder="请输入体检套餐输入码" clearable style="width: 230px"
              @keyup.enter.native="handleQueryBelow"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBelow">搜索</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableListBelow" stripe class="tableList" size="mini" border height="220"
          @expand-change="expandChange" :row-key="getRowKeys" :expand-row-keys="expands">
          <el-table-column label="序列" align="center" width="100px" type="index" />
          <el-table-column type="expand" label="展开" align="center">
            <el-table ref="tableBelow" :data="expandItems" size="mini" @selection-change="handleSelectionChangeBelow" stripe border
              v-loading="loadingExpand" @row-dblclick="addsTc" @row-click="rowClickBelow">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip />
              <el-table-column label="性别" prop="xb" align="center">
                <template slot-scope="scope">
                  <div v-for="item in xbOptions" :key="item.id">
                    <span v-if="item.id == scope.row.xb">{{ item.text }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="价格" prop="jg" align="center" />
              <el-table-column label="体检类型" prop="tjlx" align="center">
                <template slot-scope="scope">
                  {{ tjlx(scope.row.tjlx) }}
                </template>
              </el-table-column>
              <el-table-column label="人次" prop="sycstj" align="center" />
            </el-table>
          </el-table-column>
          <el-table-column label="套餐名称" prop="tjtcmc" align="center" show-overflow-tooltip />
          <el-table-column label="原价" prop="tcysjg" align="center" />
          <el-table-column label="优惠价" prop="zhjg" align="center" />
        </el-table>
        <!-- 分页 -->
        <el-pagination v-show="totalBelow > 0" :total="totalBelow" :page.sync="pageNumBelow" small
          :limit.sync="pageSizeBelow" :page-sizes="[10, 20, 30, 40]"
          @size-change="belowSizeChange" @current-change="belowCurrentChange"
          layout="sizes, prev, pager, next, jumper" :page-size="100" style="padding: 10px;" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getSfxmData, getListData, getItems } from "@/api/reception/registration";

export default {
  data() {
    return {
      // 上方表格****************
      // 数据列表
      tableListTop: [],
      // 总数
      totalTop: 0,
      // 当前页
      pageNumTop: 1,
      // 每页大小
      pageSizeTop: 10,
      // 加载中
      loadingTop: false,
      // 选中的数据组
      topSelect: [],
      // 性别
      xbOptions: [
        { id: 0, text: "男" },
        { id: 1, text: "女" },
        { id: 2, text: "通用" }
      ],

      // 下方表格******************
      // 筛选参数
      queryParamsBelow: {
        tjtcsrm: undefined
      },
      // 表格数据
      tableListBelow: [],
      // 获取row的key值
      getRowKeys(row) {
        return row.id;
      },
      // 要展开的行，数值的元素是row的key值
      expands: [],
      // 选中的数据组
      belowSelect: [],
      // 加载中
      loadingBelow: false,
      // 总数
      totalBelow: 0,
      // 当前页
      pageNumBelow: 1,
      // 每页大小
      pageSizeBelow: 10,
      // 展开行数据
      expandItems: [],
      // 展开行加载中
      loadingExpand: false,
    }
  },
  created() {},
  methods: {
    // 上方表格****************
    // 请求数据
    getListTop(data) {
      var query = data || {}
      query.current = this.pageNumTop
      query.size = this.pageSizeTop
      this.loadingTop = true;
      getSfxmData(query).then(response => {
        this.tableListTop = response.data.records;
        this.totalTop = response.data.total;
        this.loadingTop = false;
      });
    },
    // 搜索
    handleQueryTop(data, current) {
      this.pageNumTop = current || 1
      this.getListTop(data, current)
    },
    // 表格中选中的数据
    handleSelectionChangeTop(val) {
      this.topSelect = val
      this.$emit('selectionChangeTop', this.topSelect)
    },
    // 分页条数发生改变
    topSizeChange(val) {
      if (this.pageNumTop * val > this.totalTop) {
				this.pageNumTop = 1
			}
			this.pageSizeTop = val
			this.$emit("handleSearch")
    },
    // 分页页码发生改变
    topCurrentChange(val) {
      this.pageNumTop = val
			this.$emit("handleSearch", val)
    },

    // 下方表格*****************
    // 请求数据
    getListBelow() {
      var query = {
        current: this.pageNumBelow,
        size: this.pageSizeBelow,
        tjtcsrm: this.queryParamsBelow.tjtcsrm
      }
      this.loadingBelow = true;
      getListData(query).then(response => {
        this.tableListBelow = response.data.records;
        this.totalBelow = response.data.total;
        this.loadingBelow = false;
      });
    },
    // 搜索
    handleQueryBelow() {
      this.pageNumBelow = 1
      this.getListBelow()
    },
    // 展开行
    expandChange(row) {
      this.loadingExpand = true
      this.expandItems = []
      this.belowSelect = []
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id);
        this.$emit('selectionChangeBelow', this.belowSelect)
      } else {
        this.getItems(row.id)
      }
    },
    // 获取套餐下的收费项目
    getItems(id) {
      getItems({ tcId: id }).then((res) => {
        this.loadingExpand = false
        res.data.forEach(el => {
          el.id = el.sfxmId
        });
        this.expandItems = res.data
        this.$emit('selectionChangeBelow', this.belowSelect)
        this.expands = []
        this.expands.push(id);
      })
    },
    // 表格中选中的数据
    handleSelectionChangeBelow(val) {
      this.belowSelect = val
      this.$emit('selectionChangeBelow', this.belowSelect)
    },
    // 分页条数发生改变
    belowSizeChange(val) {
      if (this.pageNumBelow * val > this.totalBelow) {
				this.pageNumBelow = 1
			}
			this.pageSizeBelow = val
      this.getListBelow()
    },
    // 分页页码发生改变
    belowCurrentChange(val) {
      this.pageNumBelow = val
			this.getListBelow()
    },

    // 体检类型
    tjlx(value) {
      if (value == 0) {
        return "健康体检";
      } else if (value == 1) {
        return "职业体检";
      } else if (value == 2) {
        return "综合";
      }
    },
    // 重置
    reset() {
      this.tableListTop = []
      this.pageNumTop = 1
      this.pageSizeTop = 10
      this.topSelect = []
      this.queryParamsBelow.tjtcsrm = ""
      this.tableListBelow = []
      this.expands = []
      this.belowSelect = []
      this.pageNumBelow = 1
      this.pageSizeBelow = 10
      this.expandItems = []
      this.getListTop()
      this.getListBelow()
    },
    // 移入选项-收费
    addsSf(row, col) {
      if (col && (col.label == "操作" || col.type == "selection")) {
        return
      }
      var isSelect = false
      this.topSelect.forEach(el => {
        if (el.id == row.id) {
          isSelect = true
          this.$emit('selectionChangeTop', this.topSelect)
          this.$emit("adds", 1)
        }
      });
      if (isSelect) return
      this.$refs.tableTop.clearSelection();
      this.$refs.tableTop.toggleRowSelection(row);
      this.topSelect = [row];
      this.$emit('selectionChangeTop', this.topSelect)
      this.$emit("adds", 1)
    },
    // 移入选项-套餐
    addsTc(row, col) {
      if (col && (col.label == "操作" || col.type == "selection")) {
        return
      }
      var isSelect = false
      this.belowSelect.forEach(el => {
        if (el.id == row.id) {
          isSelect = true
          this.$emit('selectionChangeBelow', this.belowSelect)
          this.$emit("adds", 2)
        }
      });
      if (isSelect) return
      this.$refs.tableBelow.clearSelection();
      this.$refs.tableBelow.toggleRowSelection(row);
      this.belowSelect = [row];
     
      this.$emit('selectionChangeBelow', this.belowSelect)
      this.$emit("adds", 2)
    },
    // 单击某行-收费
    rowClickTop(row, col) {
      if (col && (col.label == "操作" || col.type == "selection")) {
        return
      }
			var isSelect = false
			this.topSelect.forEach(el => {
				if (el.id == row.id) {
					isSelect = true
				}
			});
			if (isSelect) return
			this.$refs.tableTop.clearSelection();
			this.$refs.tableTop.toggleRowSelection(row);
			this.topSelect = [row];
    },
    // 单击某行-套餐
    rowClickBelow(row, col) {
      if (col && (col.label == "操作" || col.type == "selection")) {
        return
      }
			var isSelect = false
			this.belowSelect.forEach(el => {
				if (el.id == row.id) {
					isSelect = true
				}
			});
			if (isSelect) return
			this.$refs.tableBelow.clearSelection();
			this.$refs.tableBelow.toggleRowSelection(row);
			this.belowSelect = [row];
    },
  },
}
</script>

<style></style>