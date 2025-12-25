// 新建的LodopFuncs.js文件

//==本JS是加载Lodop插件及CLodop服务的综合示例，可直接使用，建议看懂后融进自己页面程序==
import {
  Message,
  MessageBox
} from 'element-ui'

var CreatedOKLodopObject, CLodopIsLocal, CLodopJsState;

//==判断是否需要CLodop(那些不支持插件的浏览器):==
function needCLodop() {
  try {
    var ua = navigator.userAgent;
    if (ua.match(/Windows\sPhone/i))
      return true;
    if (ua.match(/iPhone|iPod|iPad/i))
      return true;
    if (ua.match(/Android/i))
      return true;
    if (ua.match(/Edge\D?\d+/i))
      return true;

    var verTrident = ua.match(/Trident\D?\d+/i);
    var verIE = ua.match(/MSIE\D?\d+/i);
    var verOPR = ua.match(/OPR\D?\d+/i);
    var verFF = ua.match(/Firefox\D?\d+/i);
    var x64 = ua.match(/x64/i);
    if ((!verTrident) && (!verIE) && (x64))
      return true;
    else if (verFF) {
      verFF = verFF[0].match(/\d+/);
      if ((verFF[0] >= 41) || (x64))
        return true;
    } else if (verOPR) {
      verOPR = verOPR[0].match(/\d+/);
      if (verOPR[0] >= 32)
        return true;
    } else if ((!verTrident) && (!verIE)) {
      var verChrome = ua.match(/Chrome\D?\d+/i);
      if (verChrome) {
        verChrome = verChrome[0].match(/\d+/);
        if (verChrome[0] >= 41)
          return true;
      }
    }
    return false;
  } catch (err) {
    return true;
  }
}

//==加载引用CLodop的主JS,用双端口8000和18000(以防其中一个被占):==
function loadCLodop() {
  if (CLodopJsState == "loading" || CLodopJsState == "complete") return;
  CLodopJsState = "loading";
  var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
  var JS1 = document.createElement("script");
  var JS2 = document.createElement("script");
  JS1.src = "http://localhost:8000/CLodopfuncs.js?priority=1";
  JS2.src = "http://localhost:18000/CLodopfuncs.js";
  JS1.onload = JS2.onload = function () {
    CLodopJsState = "complete";
  }
  JS1.onerror = JS2.onerror = function (evt) {
    CLodopJsState = "complete";
  }
  head.insertBefore(JS1, head.firstChild);
  head.insertBefore(JS2, head.firstChild);
  // CLodopIsLocal = !!((JS1.src + JS2.src).match(///localho|//127.0.0./i));
  CLodopIsLocal = !!((JS1.src + JS2.src).match(/\/\/localho|\/\/127.0.0./i));
}

if (needCLodop()) {
  loadCLodop();
}//加载

//==获取LODOP对象主过程,判断是否安装、需否升级:==
function getLodop(oOBJECT, oEMBED) {
  var strHtmInstall = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
  var strHtmUpdate = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
  var strHtm64_Install = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
  var strHtm64_Update = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
  var strHtmFireFox = "<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
  var strHtmChrome = "<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
  var strCLodopInstall_1 = "<br><font color='#FF00FF'>Web打印服务CLodop未安装启动，点击这里<a href='http://www.lodop.net/download/CLodop_Setup_for_Win64NT_4.127EN.zip' target='_self'>下载windows64版执行安装</a>";
  var strCLodopInstall_2 = "<br>若您的电脑是windows32，可<a href='http://www.lodop.net/download/Lodop6.226_Clodop4.127.zip' target='_self'>点这里直接下载安装</a>";
  var strCLodopInstall_3 = "，成功后请刷新本页面。</font>";
  var strCLodopUpdate = "<br><font color='#FF00FF'>Web打印服务CLodop需升级!点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
  var LODOP;
  try {
    var ua = navigator.userAgent;
    var isIE = !!(ua.match(/MSIE/i)) || !!(ua.match(/Trident/i));
    if (needCLodop()) {
      try {
        LODOP = getCLodop();
      } catch (err) {
      }
      // if (!LODOP && CLodopJsState !== "complete") {
      //   if (CLodopJsState == "loading") alert("网页还没下载完毕，请稍等一下再操作."); else alert("没有加载CLodop的主js，请先调用loadCLodop过程.");
      //   return;
      // }
      if (!LODOP) {
        let aa = strCLodopInstall_1 + (CLodopIsLocal ? strCLodopInstall_2 : "") + strCLodopInstall_3;
        // document.body.innerHTML = strCLodopInstall_1 + (CLodopIsLocal ? strCLodopInstall_2 : "") + strCLodopInstall_3 + document.body.innerHTML;
        MessageBox.alert(aa, "温馨提示", {
          dangerouslyUseHTMLString: true
        })
        return;
      } else {
        // if (CLODOP.CVERSION < "4.1.1.3") {
          if (CLODOP.CVERSION < "2.0.9.0") {
          document.body.innerHTML = strCLodopUpdate + document.body.innerHTML;
        }
        if (oEMBED && oEMBED.parentNode)
          oEMBED.parentNode.removeChild(oEMBED); //清理旧版无效元素
        if (oOBJECT && oOBJECT.parentNode)
          oOBJECT.parentNode.removeChild(oOBJECT);
      }
    } else {
      var is64IE = isIE && !!(ua.match(/x64/i));
      //==如果页面有Lodop就直接使用,否则新建:==
      if (oOBJECT || oEMBED) {
        if (isIE)
          LODOP = oOBJECT;
        else
          LODOP = oEMBED;
      } else if (!CreatedOKLodopObject) {
        LODOP = document.createElement("object");
        LODOP.setAttribute("width", 0);
        LODOP.setAttribute("height", 0);
        LODOP.setAttribute("style", "position:absolute;left:0px;top:-100px;width:0px;height:0px;");
        if (isIE)
          LODOP.setAttribute("classid", "clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
        else
          LODOP.setAttribute("type", "application/x-print-lodop");
        document.documentElement.appendChild(LODOP);
        CreatedOKLodopObject = LODOP;
      } else
        LODOP = CreatedOKLodopObject;
      //==Lodop插件未安装时提示下载地址:==
      if ((!LODOP) || (!LODOP.VERSION)) {
        if (ua.indexOf('Chrome') >= 0)
          document.body.innerHTML = strHtmChrome + document.body.innerHTML;
        if (ua.indexOf('Firefox') >= 0)
          document.body.innerHTML = strHtmFireFox + document.body.innerHTML;
        document.body.innerHTML = (is64IE ? strHtm64_Install : strHtmInstall) + document.body.innerHTML;
        return LODOP;
      }
    }
    // if (LODOP.VERSION < "6.2.2.6") {
    //   if (!needCLodop())
    //     document.body.innerHTML = (is64IE ? strHtm64_Update : strHtmUpdate) + document.body.innerHTML;
    // }
    //===如下空白位置适合调用统一功能(如注册语句、语言选择等):==
    LODOP.SET_LICENSES("青岛一凌网集成有限公司","818FEC6D453B7FDCEA47C8A096DA3745","","");

    //=======================================================
    return LODOP;
  } catch (err) {
    alert("getLodop出错:" + err);
  }
}

export {getLodop}


// var CreatedOKLodop7766=null;

// //====判断是否需要安装CLodop云打印服务器:====
// function needCLodop(){
//     try{
// 	var ua=navigator.userAgent;
// 	if (ua.match(/Windows\sPhone/i) !=null) return true;
// 	if (ua.match(/iPhone|iPod/i) != null) return true;
// 	if (ua.match(/Android/i) != null) return true;
// 	if (ua.match(/Edge\D?\d+/i) != null) return true;
	
// 	var verTrident=ua.match(/Trident\D?\d+/i);
// 	var verIE=ua.match(/MSIE\D?\d+/i);
// 	var verOPR=ua.match(/OPR\D?\d+/i);
// 	var verFF=ua.match(/Firefox\D?\d+/i);
// 	var x64=ua.match(/x64/i);
// 	if ((verTrident==null)&&(verIE==null)&&(x64!==null)) 
// 		return true; else
// 	if ( verFF !== null) {
// 		verFF = verFF[0].match(/\d+/);
// 		if ((verFF[0]>= 42)||(x64!==null)) return true;
// 	} else 
// 	if ( verOPR !== null) {
// 		verOPR = verOPR[0].match(/\d+/);
// 		if ( verOPR[0] >= 32 ) return true;
// 	} else 
// 	if ((verTrident==null)&&(verIE==null)) {
// 		var verChrome=ua.match(/Chrome\D?\d+/i);		
// 		if ( verChrome !== null ) {
// 			verChrome = verChrome[0].match(/\d+/);
// 			if (verChrome[0]>=42) return true;
// 		};
// 	};
//         return false;
//     } catch(err) {return true;};
// };

// //====页面引用CLodop云打印必须的JS文件：====
// if (needCLodop()) {
// 	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
// 	var oscript = document.createElement("script");
// 	oscript.src ="http://localhost:8000/CLodopfuncs.js?priority=1";
// 	head.insertBefore( oscript,head.firstChild );

// 	//引用双端口(8000和18000）避免其中某个被占用：
// 	oscript = document.createElement("script");
// 	oscript.src ="http://localhost:18000/CLodopfuncs.js?priority=0";
// 	head.insertBefore( oscript,head.firstChild );
// };

// //====获取LODOP对象的主过程：====
// function getLodop(oOBJECT,oEMBED){
//     var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='../system/xiazai!fileDownload.action?name=lodop_32' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
//     var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='../system/xiazai!fileDownload.action?name=lodop_32' target='_self'>执行升级</a>,升级后请重新进入。</font>";
//     var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='../system/xiazai!fileDownload.action?name=lodop_64' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
//     var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='../system/xiazai!fileDownload.action?name=lodop_64' target='_self'>执行升级</a>,升级后请重新进入。</font>";
//     var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
//     var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
//     var strCLodopInstall="<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='../system/xiazai!fileDownload.action?name=CLodop' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
//     var strCLodopUpdate="<br><font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='../system/xiazai!fileDownload.action?name=CLodop' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
//     var LODOP;
//     try{
//         var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
//         if (needCLodop()) {
//             try{ LODOP=getCLodop();} catch(err) {};
//             if (!LODOP && document.readyState!=="complete") {throw new Error(strCLodopInstall);};
//             if (!LODOP) {
//                  if (isIE) {
//                      throw new Error(strCLodopInstall);
//                  } else {
//                      throw new Error(strCLodopInstall);
//                  }
//                  return;
//             } else {
//                 if (CLODOP.CVERSION<"2.0.9.0") {
//                     if (isIE) {
//                         throw new Error(strCLodopUpdate);
//                     } else {
//                         throw new Error(strCLodopUpdate);
//                     }
//                 }
//                 if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
//                 if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);	
//             }
//         } else {
//             var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
//             //=====如果页面有Lodop就直接使用，没有则新建:==========
//             if (oOBJECT!=undefined || oEMBED!=undefined) {
//                 if (isIE) LODOP=oOBJECT; else  LODOP=oEMBED;
//             } else if (CreatedOKLodop7766==null){
//                 LODOP=document.createElement("object");
//                 LODOP.setAttribute("width",0);
//                 LODOP.setAttribute("height",0);
//                 LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");
//                 if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
//                 else LODOP.setAttribute("type","application/x-print-lodop");
//                 document.documentElement.appendChild(LODOP);
//                 CreatedOKLodop7766=LODOP;
//              } else LODOP=CreatedOKLodop7766;
//             //=====Lodop插件未安装时提示下载地址:==========
//             if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
//                  if (navigator.userAgent.indexOf('Chrome')>=0) {
//                      throw new Error(strHtmChrome);
//                  } else if (navigator.userAgent.indexOf('Firefox')>=0) {
//                      throw new Error(strHtmFireFox);
//                  } else if (is64IE) {
//                      throw new Error(strHtm64_Install);
//                  } else if (isIE) {
//                      throw new Error(strHtmInstall);
//                  } else {
//                      throw new Error(strHtmInstall);
//                  }
//                  return LODOP;
//             };
//         };
//         /**
//         if (LODOP.VERSION<"6.2.1.5") {
//             if (needCLodop()) throw new Error(strCLodopUpdate);
//             else if (is64IE) throw new Error(strHtm64_Update);
//             else if (isIE) throw new Error(strHtmUpdate);
//             else throw new Error(strHtmUpdate);
//             return LODOP;
//         };*/
//         //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===
//         LODOP.SET_LICENSES("青岛一凌网集成有限公司","818FEC6D453B7FDCEA47C8A096DA3745","","");

//         //===========================================================
//         return LODOP;
//     } catch(err) {throw err;};
// };
// export {getLodop}

