<template>
  <el-dialog title="地址选择" :visible.sync="open" class="address-select" width="1000px" :append-to-body="appendToBody">
    <!-- 百度地图 -->
    <div class="main-map">
      <div id="map-container" :class="{ 'map-style': mapStyle }" style="width: 700px; height: 700px;"></div>
      <div class="map-info">
        <el-form>
          <el-form-item>
            <span>请输入查询地址：</span>
            <el-input type="text" id="searchAddress" v-model="searchAddress" placeholder="请输入地址" style="width: 100%">
            </el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" style="width: 100%" @click="selectLocation">选择位置</el-button>
        <ul>
          <li>经度（BD09）：{{ lngBD }}</li>
          <li>经度（BD09）：{{ latBD }}</li>
          <li>经度（GCJ02）：{{ lngGCJ }}</li>
          <li>纬度（GCJ02）：{{ latGCJ }}</li>
          <li>地址：{{ address }}</li>
        </ul>
        <div class="info-btn">
          <el-button type="primary" @click="confirm">提 交</el-button>
          <el-button @click="cancel">返 回</el-button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: ["appendToBody"],
  data() {
    return {
      // 搜索地址
      searchAddress: "",
      open: false,
      map: "",
      mapStyle: false,
      marketpoint: "",
      mkrTool: "",
      lngBD: "",
      latBD: "",
      lngGCJ: "",
      latGCJ: "",
      address: ""
    }
  },
  mounted() {
    window.getMarker = this.getMarker
  },
  methods: {
    // 初始化百度地图
    initBaiduMap() {
      let that = this;
      this.$nextTick(function () {
        /* 初始化地图 start */
        var map = new BMap.Map("map-container", { enableMapClick: false }) // 创建地图实例
        this.map = map
        var point = new BMap.Point(120.39, 36.1); // 设置中心点坐标
        map.centerAndZoom(point, 11); // 地图初始化，同时设置地图展示级别
        map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
        /* 初始化地图 end */
        /** 搜索地址Start */
        // 建立一个自动完成的对象
        var ac = new BMap.Autocomplete({
          input: "searchAddress",
          location: map,
        });
        // 鼠标点击下拉列表后的事件
        ac.addEventListener("onconfirm", function (e) {
          var myValue = e.item.value.province + e.item.value.city + e.item.value.district + e.item.value.street + e.item.value.business;
          map.clearOverlays();
          var local = new BMap.LocalSearch(map, {
            // 智能搜索
            onSearchComplete(res) {
              let poi = res.getPoi(0); // 获取第一个智能搜索的结果
              var searchpt = poi.point; // 获取坐标
              map.centerAndZoom(searchpt, 18);
              map.addOverlay(new BMap.Marker(searchpt));
              that.lngBD = searchpt.lng;
              that.latBD = searchpt.lat;
              that.address = myValue;
              that.marketpoint = new BMap.Point(searchpt.lng, searchpt.lat);
              var convertor = new BMapGL.Convertor();
              convertor.translate(
                [new BMapGL.Point(searchpt.lng, searchpt.lat)], 5, 3,
                function (data) {
                  if (data.status === 0) {
                    that.lngGCJ = data.points[0].lng;
                    that.latGCJ = data.points[0].lat;
                  } else {
                    that.$modal.msgError('转换GCJ02坐标失败，请联系管理员');
                  }
                }
              );
            }
          });
          // 搜索词
          var searchValue = e.item.value;
          local.search(
            searchValue.province +
            searchValue.city +
            searchValue.district +
            searchValue.street +
            searchValue.business
          )
        });
        /** 搜索地址End */
        // 添加带有定位的导航控件
        var navigationControl = new BMap.NavigationControl({
          // 靠左上角位置
          anchor: BMAP_ANCHOR_TOP_LEFT,
          // LARGE类型
          type: BMAP_NAVIGATION_CONTROL_LARGE,
          // 启用显示定位
          enableGeolocation: true
        });
        map.addControl(navigationControl);
        // 添加定位控件
        // var geolocationControl = new BMap.GeolocationControl();
        // geolocationControl.addEventListener("locationSuccess", function (e) {
        //   // 定位成功事件
        //   var address = '';
        //   address += e.addressComponent.province;
        //   address += e.addressComponent.city;
        //   address += e.addressComponent.district;
        //   address += e.addressComponent.street;
        //   address += e.addressComponent.streetNumber;
        //   alert("当前定位地址为：" + address);
        // });
        // geolocationControl.addEventListener("locationError", function (e) {
        //   // 定位失败事件
        //   alert(e.message);
        // });
        // map.addControl(geolocationControl);
      })
    },
    // 选择位置
    selectLocation() {
      var that = this
      this.mapStyle = true
      this.mkrTool = new BMapLib.MarkerTool(this.map, {
        followText: "添加一个点"
      });
      //地图标注 
      this.mkrTool.open();
      var marker = new BMap.Marker(); // 创建标注
      this.mkrTool.addEventListener("markend", function (e) {
        marker = e.marker;
        this.marketpoint = marker.getPosition(); //获取标注位置
        marker.enableDragging(); //设置标注可拖拽
        marker.addEventListener("click", function () {
          this.marketpoint = new BMap.Point(this.getPosition().lng, this.getPosition().lat);
          that.openInfo(this, this.marketpoint, null); //打开信息窗口
        });
        that.openInfo(marker, this.marketpoint, null); //打开信息窗口
      });
    },
    //打开信息窗口 
    openInfo(marker, marketpoint, id) {
      this.mapStyle = false
      // 根据坐标得到地址描述 
      var map = this.map
      var myGeo = new BMap.Geocoder();
      myGeo.getLocation(
        marketpoint, function (result) {
          if (result) {
            var address = result.address;
            var sContent =
              `
                <p style="margin:0 0 5px 0;font-size:13px;font-weight:bold ;padding:0.2em 0;">新增地图位置</p>
                <p style="margin:0;line-height:1.5;">地址：${address}</p>
                </br>
                <button style="width: 100%;box-sizing: border-box;" onclick="getMarker(${marketpoint.lng},${marketpoint.lat},'${address}')" >确定</button>
              `
            var infoWindow = new BMap.InfoWindow(sContent); // 创建信息窗口对象 
            map.openInfoWindow(infoWindow, marketpoint); //开启信息窗口 
            marker.addEventListener("click",
              function () {
                map.openInfoWindow(infoWindow, marketpoint); //开启信息窗口 
              });
          }
        }
      );
    },
    //获取标记地点
    getMarker(lng, lat, address) {
      this.lngBD = lng;
      this.latBD = lat;
      this.address = address;
      this.marketpoint = new BMap.Point(lng, lat);
      var that = this
      var convertor = new BMapGL.Convertor();
      convertor.translate(
        [new BMapGL.Point(lng, lat)], 5, 3,
        function (data) {
          if (data.status === 0) {
            that.lngGCJ = data.points[0].lng;
            that.latGCJ = data.points[0].lat;
          } else {
            that.$modal.msgError('转换GCJ02坐标失败，请联系管理员');
          }
        }
      );

      this.mkrTool.close();
      this.map.closeInfoWindow();
    },
    /** 打开地图选择 */
    show() {
      this.open = true;
      this.initBaiduMap();
    },
    /** 确认选择 */
    confirm() {
      if (this.lngBD == null || this.lngBD.length <= 0 || this.lngBD == false) {
        this.$modal.msgError("经度（BD09）不能为空!");
        return;
      }
      if (this.latBD == null || this.latBD.length <= 0 || this.latBD == false) {
        this.$modal.msgError("纬度（BD09）不能为空!");
        return;
      }
      if (this.lngGCJ == null || this.lngGCJ.length <= 0 || this.lngGCJ == false) {
        this.$modal.msgError("经度（GCJ02）不能为空!");
        return;
      }
      if (this.latGCJ == null || this.latGCJ.length <= 0 || this.latGCJ == false) {
        this.$modal.msgError("纬度（GCJ02）不能为空!");
        return;
      }
      var addressInfo = {
        latitude: this.latBD, //纬度
        longitude: this.lngBD, //经度
        latitudeGcj: this.latGCJ, //纬度
        longitudeGcj: this.lngGCJ, //经度
        address: this.address
      };
      this.$emit("confirmMapAddress", addressInfo);
      this.open = false;
    },
    cancel() {
      this.open = false;
    }
  },
}
</script>
<style lang="scss">
.address-select {
  .el-dialog__body {
    padding-top: 8px;
  }

  .main-map {
    display: flex;

    #map-container {
      border: 1px solid rgba($color: #000000, $alpha: 0.2);

      .BMap_cpyCtrl {
        display: none !important;
      }

      &.map-style {
        >div {
          cursor: none !important;
        }
      }
    }

    .map-info {
      flex: 1;
      padding-left: 20px;

      ul {
        padding: 0;
        margin: 10px 0 0 0;
        list-style-type: none;

        li {
          font-size: 12px;
          line-height: 35px;
        }
      }

      .info-btn {
        display: flex;
        margin: 10px 0;

        .el-button {
          width: 100%;
        }
      }

      .el-form {
        span {
          font-size: 12px;
          display: inline-block;
          margin-bottom: 8px;
        }
      }
    }
  }
}

// 防止地图自动完成的对象被遮挡
.tangram-suggestion {
  z-index: 9999;
}
</style>