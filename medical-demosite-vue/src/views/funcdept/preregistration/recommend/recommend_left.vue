<template>
  <el-col :span="9" class="tableLeft">
    <!-- 上部分 -->
    <el-row style="margin-bottom:10px">
      <el-col :span="24" style="border: 1px solid #D4D6D9;">
        <!-- 表格 -->
        <el-table :data="tableListTop" stripe class="tableList" @selection-change="handleSelectionChangeTop" border
          height="220" v-loading="loadingTop">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" align="center" width="100px" type="index" />
          <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip />
          <el-table-column label="性别" prop="xb" align="center" show-overflow-tooltip />
          <el-table-column label="价格" prop="jg" align="center" show-overflow-tooltip />
          <el-table-column label="体检类型" prop="tjlx" align="center" show-overflow-tooltip />
          <el-table-column label="人次" prop="sycstj" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <el-pagination v-show="totalTop > 0" :total="totalTop" :page.sync="pageNumTop" small :limit.sync="pageSizeTop"
          @pagination="getListTop" :page-sizes="[10, 20, 30, 40]" layout="sizes, prev, pager, next, jumper"
          :page-size="100" style="padding: 10px;" />
      </el-col>
    </el-row>

    <!-- 下部分 -->
    <el-row>
      <el-col :span="24" style="border: 1px solid #D4D6D9;">
        <!-- 筛选 -->
        <el-form :model="queryParamsBelow" ref="queryFormBelow" size="small" :inline="true" @submit.native.prevent
          style="padding:16px;padding-bottom: 0;">
          <el-form-item label="体检套餐输入码" prop="tcsrm">
            <el-input v-model="queryParamsBelow.tcsrm" placeholder="请输入体检套餐输入码" clearable style="width: 230px"
              @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableListBelow" stripe class="tableList" border height="220" @expand-change="expandChange"
          :row-key="getRowKeys" :expand-row-keys="expands">
          <el-table-column label="序列" align="center" width="100px" type="index" />
          <el-table-column type="expand" label="展开" align="center">
            <template slot-scope="props">
              <el-table :data="props.row.introduce" @selection-change="handleSelectionChangeBelow" stripe border
                v-loading="loadingBelow">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip />
                <el-table-column label="性别" prop="xb" align="center" show-overflow-tooltip />
                <el-table-column label="价格" prop="jg" align="center" show-overflow-tooltip />
                <el-table-column label="体检类型" prop="tjlx" align="center" show-overflow-tooltip />
                <el-table-column label="人次" prop="sycstj" align="center" show-overflow-tooltip />
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="套餐名称" prop="tjtcmc" align="center" show-overflow-tooltip />
          <el-table-column label="原价" prop="tcysjg" align="center" show-overflow-tooltip />
          <el-table-column label="优惠价" prop="yhj" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <el-pagination v-show="totalBelow > 0" :total="totalBelow" :page.sync="pageNumBelow" small
          :limit.sync="pageSizeBelow" @pagination="getListBelow" :page-sizes="[10, 20, 30, 40]"
          layout="sizes, prev, pager, next, jumper" :page-size="100" style="padding: 10px;" />
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  data() {
    return {
      // 上方表格****************
      // 数据列表
      tableListTop: [
        { "idKs": "19", "tjlx": "健康", "jcyy": "检测有无痛风的重要标准。", "sycstj": 74431, "xb": "通用", "id": "358", "sfxmmc": "尿酸", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "NS" }, { "idKs": "19", "tjlx": "健康", "jcyy": "谷氨酰转肽酶，谷丙转氨酶", "bz": "红/BD", "sycstj": 44225, "xb": "通用", "id": "689", "sfxmmc": "肝功2项", "jg": 16.0, "ssks": "检验科", "sfxmsrm": "GG2X" }, { "idKs": "19", "tjlx": "健康", "jcyy": "肾炎、肾病综合征、泌尿系统肿瘤、泌尿系统感染、自身免疫性疾病、高血压引起肾功能损害等情况，均可在尿常规检查中有所体现。", "bz": "(女性经期请勿留尿)", "sycstj": 43718, "xb": "通用", "id": "62", "sfxmmc": "尿常规11项", "jg": 9.0, "ssks": "检验科", "sfxmsrm": "NCG11X" }, { "idKs": "19", "tjlx": "健康", "sycstj": 31161, "xb": "通用", "id": "537", "sfxmmc": "谷丙转氨酶", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "GBZAM" }, { "idKs": "402848e35b0f8a67015b3d0b1acf06c0", "tjlx": "健康", "jcyy": "诊断有无幽门螺旋杆菌感染，敏感性高，特异性强，快速简单安全。", "bz": "(孕妇及半年内有生育计划的 夫妻不宜检查)", "sycstj": 17271, "xb": "通用", "id": "402848e35b40a77f015b4755d56e0037", "sfxmmc": "C14", "jg": 140.0, "ssks": "14C", "sfxmsrm": "C14" }, { "idKs": "19", "tjlx": "健康", "jcyy": "甲型胎儿蛋白（AFP）：肝脏肿瘤筛查首选标志物，对原发性肝癌的诊断、疗效观察和预后评估有重要的临床意义。在卵巢、胃、胰腺癌、睾丸癌等肿瘤及肝炎、肝硬化等疾病也有异常发现。", "sycstj": 10784, "xb": "通用", "id": "563", "sfxmmc": "甲胎蛋白(电发光法)", "jg": 80.0, "ssks": "检验科", "sfxmsrm": "JTDBDFGF" }, { "idKs": "19", "tjlx": "健康", "sycstj": 8970, "xb": "通用", "id": "767", "sfxmmc": "谷氨酰氨基转移酶(GGT)", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "GAXAJZYM" }, { "idKs": "19", "tjlx": "健康", "jcyy": "谷氨酰转肽酶，谷丙转氨酶，总胆红素，直接胆红素", "bz": "红/BD", "sycstj": 7965, "xb": "通用", "id": "690", "sfxmmc": "肝功4项", "jg": 32.0, "ssks": "检验科", "sfxmsrm": "GG4X" }, { "idKs": "19", "tjlx": "健康", "jcyy": "比浊法。HCY,为心脑血管病、冠心病、心肌梗塞的危险评价，浓度与疾病危险性成正比。", "sycstj": 6475, "xb": "通用", "id": "1025", "sfxmmc": "同型半胱氨酸", "jg": 120.0, "ssks": "检验科", "sfxmsrm": "TXBGAS" }, { "idKs": "19", "tjlx": "健康", "jcyy": "癌胚抗原（CEA）：系广谱性肿瘤标志物，对大肠癌、胰腺癌的筛查、疗效观察和预后评估有重要的临床意义。在胃、乳腺、肺癌等也可升高。对消化系统肿瘤筛查的广泛意义，同时也是宫颈癌筛查的首选标志物。甲型胎儿蛋白（AFP）：肝脏肿瘤筛查首选标志物，对原发性肝癌的诊断、疗效观察和预后评估有重要的临床意义。在卵巢、胃、胰腺癌、睾丸癌等肿瘤及肝炎、肝硬化等疾病也有异常发现。", "bz": "红/BD", "sycstj": 6065, "xb": "通用", "id": "966", "sfxmmc": "肿瘤标志物2项(T)", "jg": 80.0, "ssks": "检验科", "sfxmsrm": "ZLBZW2XT" }, { "idKs": "19", "tjlx": "健康", "sycstj": 5271, "xb": "通用", "id": "657", "sfxmmc": "谷草转氨酶", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "GCZAM" }, { "idKs": "19", "tjlx": "健康", "jcyy": "血液生化全套34项，不包括血常规、糖化血红蛋白。", "sycstj": 5055, "xb": "通用", "id": "1001", "sfxmmc": "生化34项", "jg": 375.0, "ssks": "检验科", "sfxmsrm": "SH34X" }, { "idKs": "19", "tjlx": "健康", "jcyy": "丙氨酸氨基转移酶、天门冬氨酸氨基转移酶、γ-谷氨酰转移酶、总蛋白、白蛋白、球蛋白、白蛋白/球蛋白比率、总胆红素、直接胆红素、间接胆红素、碱性磷酸酶、乳酸脱氢酶、胆碱酯酶更好地了解肝胆系统功能状况。 肝细胞损伤越大ALT、 AST、GGT等酶就越高。急慢性肝炎、脂肪肝、肝硬化、肝癌、胆管炎等疾病均可引起ALT、 AST、GGT等肝酶升高。", "bz": "红/BD", "sycstj": 4913, "xb": "通用", "id": "65", "sfxmmc": "肝功13项", "jg": 104.0, "ssks": "检验科", "sfxmsrm": "GG13X" }, { "idKs": "19", "tjlx": "健康", "sycstj": 4909, "xb": "通用", "id": "929", "sfxmmc": "谷草/谷丙", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "GCGB" }, { "idKs": "19", "tjlx": "健康", "sycstj": 4898, "xb": "通用", "id": "834", "sfxmmc": "间接胆红素", "jg": 8.0, "ssks": "检验科", "sfxmsrm": "JJDHS" }
      ],
      // 总数
      totalTop: 30,
      // 当前页
      pageNumTop: 1,
      // 每页大小
      pageSizeTop: 10,
      // 加载中
      loadingTop: false,
      // 选中的数据组
      topSelect: [],

      // 下方表格******************
      // 筛选参数
      queryParamsBelow: {
        tcsrm: undefined
      },
      // 表格数据
      tableListBelow: [
        {
          id: 1,
          tjtcmc: '白领套餐男士',
          tcysjg: '132',
          yhj: '123',
          introduce: [
            { "jcyy": "通过视、触、叩、听体格检查方法，检查心、肺、肝、脾等重要脏器及神经系统基本状况，检查浅表淋巴结、皮肤黏膜、脊椎、四肢、关节活动、甲状腺等有无异常，有无手术和家族疾病史，发现常见疾病的重要征兆，或初步排除常见疾病。", "xb": "通用", "costprice": 15.36, "sfxmmc": "内外科", "sfxmsrm": "NWK", "sfxmid": "1053", "idKs": "189", "tjlx": "健康", "dysx": 1, "sycstj": 288, "id": "1053", "jg": 20.0, "ssks": "全科咨询检查", "fDiscountdisabled": 0.0, "xsdyfl": "科室检查" }, { "jcyy": "检测白细胞/红细胞/血小板的数量、体积等指标，诊断有无白血病、贫血、炎症等异常。", "xb": "通用", "costprice": 0.0, "sfxmmc": "五分类血细胞分析", "sfxmsrm": "WFLXXBFX", "sfxmid": "61", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 319, "id": "61", "jg": 24.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "丙氨酸氨基转移酶、天门冬氨酸氨基转移酶、γ-谷氨酰转移酶、总蛋白、白蛋白、球蛋白、白蛋白/球蛋白比率、总胆红素、直接胆红素、间接胆红素、碱性磷酸酶、乳酸脱氢酶、胆碱酯酶更好地了解肝胆系统功能状况。 肝细胞损伤越大ALT、 AST、GGT等酶就越高。急慢性肝炎、脂肪肝、肝硬化、肝癌、胆管炎等疾病均可引起ALT、 AST、GGT等肝酶升高。", "xb": "通用", "costprice": 62.19, "sfxmmc": "肝功13项", "sfxmsrm": "GG13X", "sfxmid": "65", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 4913, "id": "65", "jg": 104.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检查血糖指标有无异常，进行糖尿病早期筛查。", "xb": "通用", "costprice": 4.78, "sfxmmc": "空腹血糖GLU", "sfxmsrm": "KFXTGLU", "sfxmid": "464", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 103, "id": "464", "jg": 5.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检测有无痛风的重要标准。", "xb": "通用", "costprice": 4.78, "sfxmmc": "尿酸", "sfxmsrm": "NS", "sfxmid": "358", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 74431, "id": "358", "jg": 8.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "测定血清中胆固醇/甘油三酯/高低密度脂蛋白胆固醇.用于评价受检者的脂肪代谢水平，血脂代谢紊乱评价、动脉粥样硬化性疾病危险性预测和营养学评价。", "xb": "通用", "costprice": 44.85, "sfxmmc": "血脂4项", "sfxmsrm": "XZ4X", "sfxmid": "66", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 27218, "id": "66", "jg": 51.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "癌胚抗原（CEA）：系广谱性肿瘤标志物，对大肠癌、胰腺癌的筛查、疗效观察和预后评估有重要的临床意义。在胃、乳腺、肺癌等也可升高。对消化系统肿瘤筛查的广泛意义，同时也是宫颈癌筛查的首选标志物", "xb": "通用", "costprice": 47.84, "sfxmmc": "癌胚抗原(电发光法)", "sfxmsrm": "APKYDFGF", "sfxmid": "564", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 11272, "id": "564", "jg": 80.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "甲型胎儿蛋白（AFP）：肝脏肿瘤筛查首选标志物，对原发性肝癌的诊断、疗效观察和预后评估有重要的临床意义。在卵巢、胃、胰腺癌、睾丸癌等肿瘤及肝炎、肝硬化等疾病也有异常发现。", "xb": "通用", "costprice": 47.84, "sfxmmc": "甲胎蛋白(电发光法)", "sfxmsrm": "JTDBDFGF", "sfxmid": "563", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 10784, "id": "563", "jg": 80.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "诊断有无幽门螺旋杆菌感染，敏感性高，特异性强，快速简单安全。", "xb": "通用", "costprice": 40.7, "sfxmmc": "C14", "sfxmsrm": "C14", "sfxmid": "402848e35b40a77f015b4755d56e0037", "idKs": "402848e35b0f8a67015b3d0b1acf06c0", "tjlx": "健康", "dysx": 3, "bz": "(孕妇及半年内有生育计划的 夫妻不宜检查)", "sycstj": 17271, "id": "402848e35b40a77f015b4755d56e0037", "jg": 140.0, "ssks": "14C", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "肾炎、肾病综合征、泌尿系统肿瘤、泌尿系统感染、自身免疫性疾病、高血压引起肾功能损害等情况，均可在尿常规检查中有所体现。", "xb": "通用", "costprice": 6.58, "sfxmmc": "尿常规11项", "sfxmsrm": "NCG11X", "sfxmid": "62", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "(女性经期请勿留尿)", "sycstj": 43718, "id": "62", "jg": 9.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检测肝、胆、胰、脾等实体脏器的外部形态和内部结构的改变。用于肝硬变、脂肪肝、胆结石、胆囊息肉、肾结石、肾积水、肾萎缩、脾肿大、胰腺囊肿及上述脏器的肿瘤的辅助诊断。", "xb": "通用", "costprice": 0.0, "sfxmmc": "肝胆胰脾双肾彩超", "sfxmsrm": "GDYPSSCC", "sfxmid": "497", "idKs": "143", "tjlx": "健康", "dysx": 3, "bz": "(请空腹检查)", "sycstj": 325, "id": "497", "jg": 80.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "用于甲状腺结节、甲状腺囊肿、亚急性或慢性甲状腺炎、甲状腺机能亢进和甲状腺癌的诊断。", "xb": "通用", "costprice": 0.0, "sfxmmc": "甲状腺彩超", "sfxmsrm": "JZXCC", "sfxmid": "48", "idKs": "143", "tjlx": "健康", "dysx": 3, "sycstj": 291, "id": "48", "jg": 50.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "检查前列腺大小，形态及结构，判断有无前列腺增大，囊肿，结石，恶性病变等（需憋尿）。（需憋尿）", "xb": "男", "costprice": 29.3, "sfxmmc": "前列腺彩超(需憋尿)", "sfxmsrm": "QLXCC", "sfxmid": "678", "idKs": "143", "tjlx": "健康", "dysx": 3, "sycstj": 25867, "id": "678", "jg": 50.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "了解心律及心肌供血情况等。", "xb": "通用", "costprice": 7.03, "sfxmmc": "十二导联同步心电图", "sfxmsrm": "XDT", "sfxmid": "5", "idKs": "9", "tjlx": "健康", "dysx": 3, "sycstj": 131589, "id": "5", "jg": 12.0, "ssks": "心电图室", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "可以选择(周三周四下午2：00-3：00)检查。较准确检查肺部各种病变和早期肺癌筛查。", "xb": "通用", "costprice": 0.0, "sfxmmc": "胸部CT", "sfxmsrm": "XBCT", "sfxmid": "552", "idKs": "173", "tjlx": "健康", "dysx": 3, "sycstj": 277, "id": "552", "jg": 180.0, "ssks": "放射科(CT)", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "xb": "通用", "costprice": 0.0, "sfxmmc": "会员活动基因检测", "sfxmsrm": "HYHDJYJC", "sfxmid": "ff8080818214499d0183585ba3ab72ac", "idKs": "134", "tjlx": "综合", "dysx": 5, "sycstj": 0, "id": "ff8080818214499d0183585ba3ab72ac", "jg": 398.0, "ssks": "健康管理部", "fDiscountdisabled": 0.0, "xsdyfl": "其他" }, { "jcyy": "采血材料成本费                           仅为固定成本费", "xb": "通用", "costprice": 2.16, "sfxmmc": "静脉采血", "sfxmsrm": "JMCX", "sfxmid": "137", "idKs": "38", "tjlx": "健康", "dysx": 5, "sycstj": 161745, "id": "137", "jg": 5.0, "ssks": "采样室", "fDiscountdisabled": 1.0, "xsdyfl": "其他" }, { "xb": "通用", "costprice": 0.0, "sfxmmc": "早餐(开发区)", "sfxmsrm": "ZC(KFQ)", "sfxmid": "402848e369e78426016a38faac4574a0", "idKs": "178", "tjlx": "综合", "dysx": 5, "bz": "以上项目还未全部完成的顾客", "sycstj": 4116, "id": "402848e369e78426016a38faac4574a0", "jg": 5.0, "ssks": "营养餐厅", "fDiscountdisabled": 0.0, "xsdyfl": "其他" }, { "xb": "通用", "costprice": 1.3, "sfxmmc": "个检报告工本费", "sfxmsrm": "GJBGGBF", "sfxmid": "1067", "idKs": "16", "tjlx": "健康", "dysx": 5, "sycstj": 16413, "id": "1067", "jg": 3.0, "ssks": "报告室", "fDiscountdisabled": 1.0, "xsdyfl": "其他" }, { "jcyy": "检查血尿素氮/血肌酸酐，评估肾脏功能有无异常。", "xb": "通用", "costprice": 9.57, "sfxmmc": "肾功2项", "sfxmsrm": "SG2X", "sfxmid": "691", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 100, "id": "691", "jg": 0.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }
          ]
        },
        {
          id: 2,
          tjtcmc: '白领套餐女士士',
          tcysjg: '100',
          yhj: '85',
          introduce: [
            { "jcyy": "通过视、触、叩、听体格检查方法，检查心、肺、肝、脾等重要脏器及神经系统基本状况，检查浅表淋巴结、皮肤黏膜、脊椎、四肢、关节活动、甲状腺等有无异常，有无手术和家族疾病史，发现常见疾病的重要征兆，或初步排除常见疾病。", "xb": "通用", "costprice": 15.36, "sfxmmc": "内外科", "sfxmsrm": "NWK", "sfxmid": "1053", "idKs": "189", "tjlx": "健康", "dysx": 1, "sycstj": 288, "id": "1053", "jg": 20.0, "ssks": "全科咨询检查", "fDiscountdisabled": 0.0, "xsdyfl": "科室检查" }, { "jcyy": "检测白细胞/红细胞/血小板的数量、体积等指标，诊断有无白血病、贫血、炎症等异常。", "xb": "通用", "costprice": 0.0, "sfxmmc": "五分类血细胞分析", "sfxmsrm": "WFLXXBFX", "sfxmid": "61", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 319, "id": "61", "jg": 24.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "丙氨酸氨基转移酶、天门冬氨酸氨基转移酶、γ-谷氨酰转移酶、总蛋白、白蛋白、球蛋白、白蛋白/球蛋白比率、总胆红素、直接胆红素、间接胆红素、碱性磷酸酶、乳酸脱氢酶、胆碱酯酶更好地了解肝胆系统功能状况。 肝细胞损伤越大ALT、 AST、GGT等酶就越高。急慢性肝炎、脂肪肝、肝硬化、肝癌、胆管炎等疾病均可引起ALT、 AST、GGT等肝酶升高。", "xb": "通用", "costprice": 62.19, "sfxmmc": "肝功13项", "sfxmsrm": "GG13X", "sfxmid": "65", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 4913, "id": "65", "jg": 104.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检查血糖指标有无异常，进行糖尿病早期筛查。", "xb": "通用", "costprice": 4.78, "sfxmmc": "空腹血糖GLU", "sfxmsrm": "KFXTGLU", "sfxmid": "464", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 103, "id": "464", "jg": 5.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检测有无痛风的重要标准。", "xb": "通用", "costprice": 4.78, "sfxmmc": "尿酸", "sfxmsrm": "NS", "sfxmid": "358", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 74431, "id": "358", "jg": 8.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "测定血清中胆固醇/甘油三酯/高低密度脂蛋白胆固醇.用于评价受检者的脂肪代谢水平，血脂代谢紊乱评价、动脉粥样硬化性疾病危险性预测和营养学评价。", "xb": "通用", "costprice": 44.85, "sfxmmc": "血脂4项", "sfxmsrm": "XZ4X", "sfxmid": "66", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 27218, "id": "66", "jg": 51.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "癌胚抗原（CEA）：系广谱性肿瘤标志物，对大肠癌、胰腺癌的筛查、疗效观察和预后评估有重要的临床意义。在胃、乳腺、肺癌等也可升高。对消化系统肿瘤筛查的广泛意义，同时也是宫颈癌筛查的首选标志物", "xb": "通用", "costprice": 47.84, "sfxmmc": "癌胚抗原(电发光法)", "sfxmsrm": "APKYDFGF", "sfxmid": "564", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 11272, "id": "564", "jg": 80.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "甲型胎儿蛋白（AFP）：肝脏肿瘤筛查首选标志物，对原发性肝癌的诊断、疗效观察和预后评估有重要的临床意义。在卵巢、胃、胰腺癌、睾丸癌等肿瘤及肝炎、肝硬化等疾病也有异常发现。", "xb": "通用", "costprice": 47.84, "sfxmmc": "甲胎蛋白(电发光法)", "sfxmsrm": "JTDBDFGF", "sfxmid": "563", "idKs": "19", "tjlx": "健康", "dysx": 2, "sycstj": 10784, "id": "563", "jg": 80.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "诊断有无幽门螺旋杆菌感染，敏感性高，特异性强，快速简单安全。", "xb": "通用", "costprice": 40.7, "sfxmmc": "C14", "sfxmsrm": "C14", "sfxmid": "402848e35b40a77f015b4755d56e0037", "idKs": "402848e35b0f8a67015b3d0b1acf06c0", "tjlx": "健康", "dysx": 3, "bz": "(孕妇及半年内有生育计划的 夫妻不宜检查)", "sycstj": 17271, "id": "402848e35b40a77f015b4755d56e0037", "jg": 140.0, "ssks": "14C", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "肾炎、肾病综合征、泌尿系统肿瘤、泌尿系统感染、自身免疫性疾病、高血压引起肾功能损害等情况，均可在尿常规检查中有所体现。", "xb": "通用", "costprice": 6.58, "sfxmmc": "尿常规11项", "sfxmsrm": "NCG11X", "sfxmid": "62", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "(女性经期请勿留尿)", "sycstj": 43718, "id": "62", "jg": 9.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }, { "jcyy": "检测肝、胆、胰、脾等实体脏器的外部形态和内部结构的改变。用于肝硬变、脂肪肝、胆结石、胆囊息肉、肾结石、肾积水、肾萎缩、脾肿大、胰腺囊肿及上述脏器的肿瘤的辅助诊断。", "xb": "通用", "costprice": 0.0, "sfxmmc": "肝胆胰脾双肾彩超", "sfxmsrm": "GDYPSSCC", "sfxmid": "497", "idKs": "143", "tjlx": "健康", "dysx": 3, "bz": "(请空腹检查)", "sycstj": 325, "id": "497", "jg": 80.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "用于甲状腺结节、甲状腺囊肿、亚急性或慢性甲状腺炎、甲状腺机能亢进和甲状腺癌的诊断。", "xb": "通用", "costprice": 0.0, "sfxmmc": "甲状腺彩超", "sfxmsrm": "JZXCC", "sfxmid": "48", "idKs": "143", "tjlx": "健康", "dysx": 3, "sycstj": 291, "id": "48", "jg": 50.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "检查前列腺大小，形态及结构，判断有无前列腺增大，囊肿，结石，恶性病变等（需憋尿）。（需憋尿）", "xb": "男", "costprice": 29.3, "sfxmmc": "前列腺彩超(需憋尿)", "sfxmsrm": "QLXCC", "sfxmid": "678", "idKs": "143", "tjlx": "健康", "dysx": 3, "sycstj": 25867, "id": "678", "jg": 50.0, "ssks": "彩超", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "了解心律及心肌供血情况等。", "xb": "通用", "costprice": 7.03, "sfxmmc": "十二导联同步心电图", "sfxmsrm": "XDT", "sfxmid": "5", "idKs": "9", "tjlx": "健康", "dysx": 3, "sycstj": 131589, "id": "5", "jg": 12.0, "ssks": "心电图室", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "jcyy": "可以选择(周三周四下午2：00-3：00)检查。较准确检查肺部各种病变和早期肺癌筛查。", "xb": "通用", "costprice": 0.0, "sfxmmc": "胸部CT", "sfxmsrm": "XBCT", "sfxmid": "552", "idKs": "173", "tjlx": "健康", "dysx": 3, "sycstj": 277, "id": "552", "jg": 180.0, "ssks": "放射科(CT)", "fDiscountdisabled": 0.0, "xsdyfl": "医技检查" }, { "xb": "通用", "costprice": 0.0, "sfxmmc": "会员活动基因检测", "sfxmsrm": "HYHDJYJC", "sfxmid": "ff8080818214499d0183585ba3ab72ac", "idKs": "134", "tjlx": "综合", "dysx": 5, "sycstj": 0, "id": "ff8080818214499d0183585ba3ab72ac", "jg": 398.0, "ssks": "健康管理部", "fDiscountdisabled": 0.0, "xsdyfl": "其他" }, { "jcyy": "采血材料成本费                           仅为固定成本费", "xb": "通用", "costprice": 2.16, "sfxmmc": "静脉采血", "sfxmsrm": "JMCX", "sfxmid": "137", "idKs": "38", "tjlx": "健康", "dysx": 5, "sycstj": 161745, "id": "137", "jg": 5.0, "ssks": "采样室", "fDiscountdisabled": 1.0, "xsdyfl": "其他" }, { "xb": "通用", "costprice": 0.0, "sfxmmc": "早餐(开发区)", "sfxmsrm": "ZC(KFQ)", "sfxmid": "402848e369e78426016a38faac4574a0", "idKs": "178", "tjlx": "综合", "dysx": 5, "bz": "以上项目还未全部完成的顾客", "sycstj": 4116, "id": "402848e369e78426016a38faac4574a0", "jg": 5.0, "ssks": "营养餐厅", "fDiscountdisabled": 0.0, "xsdyfl": "其他" }, { "xb": "通用", "costprice": 1.3, "sfxmmc": "个检报告工本费", "sfxmsrm": "GJBGGBF", "sfxmid": "1067", "idKs": "16", "tjlx": "健康", "dysx": 5, "sycstj": 16413, "id": "1067", "jg": 3.0, "ssks": "报告室", "fDiscountdisabled": 1.0, "xsdyfl": "其他" }, { "jcyy": "检查血尿素氮/血肌酸酐，评估肾脏功能有无异常。", "xb": "通用", "costprice": 9.57, "sfxmmc": "肾功2项", "sfxmsrm": "SG2X", "sfxmid": "691", "idKs": "19", "tjlx": "健康", "dysx": 2, "bz": "红/BD", "sycstj": 100, "id": "691", "jg": 0.0, "ssks": "检验科", "fDiscountdisabled": 0.0, "xsdyfl": "实验室检查" }
          ]
        },
      ],
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
      totalBelow: 30,
      // 当前页
      pageNumBelow: 1,
      // 每页大小
      pageSizeBelow: 10,
    }
  },
  methods: {
    // 上方表格****************
    // 请求数据
    getListTop() {
      this.loadingTop = false
    },
    // 表格中选中的数据
    handleSelectionChangeTop(val) {
      this.topSelect = val.map(item => item)
      this.bus.$emit('selectionChangeTop', this.topSelect)
    },

    // 下方表格*****************
    // 请求数据
    getListBelow() {
      this.loadingBelow = false
    },
    // 搜索
    handleQuery() {
      // 
    },
    // 展开行
    expandChange(row, expandedRows) {
    
      if (this.expands.includes(row.id)) {
        this.expands = this.expands.filter(val => val !== row.id);
      } else {
        //判断是否已经存在展开的行
        if (this.expands.length != 0) {
          //如果存在展开行,清空expands数组,使它关闭
          this.expands.splice(0, this.expands.length);
          //打开点击的行
          this.expands.push(row.id);
        } else {
          //如果不存在展开行,直接push打开点击的行
          this.expands.push(row.id);
        }
      }
    },
    // 表格中选中的数据
    handleSelectionChangeBelow(val) {
      this.belowSelect = val.map(item => item)
      this.bus.$emit('selectionChangeBelow', this.belowSelect)
    },
  },
}
</script>

<style>

</style>