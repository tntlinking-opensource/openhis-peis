import { getLodop } from '@/utils/LodopFuncs.js'

export default function printDyd(operation) {
  LODOP=getLodop(); 
	var DYD="";
	var printer=$("#printer").html().trim();
	if(printer){
		var config=mini.decode(printer);
		DYDs = config.DYD;
		if(DYDs){
			 var dyd_arr=DYDs.split(",");
			 var printerNum=Number(getPrinterCount(LODOP));
		}
		for(var i=0;i<printerNum;i++){
    		for(var j=0,l=dyd_arr.length;j<l;j++){
    			//console.log(dyd_arr[j].trim());
    			//alert(getPrinterName(i,LODOP));
    			if(getPrinterName(i,LODOP)==dyd_arr[j].trim()){
    				DYD=dyd_arr[j].trim();
    				break;
    			}
    		}
    		if(DYD){break;}
    	}
	}else{
		mini.alert("未配置导引单打印机!");
		return;
	}
	if(!DYD){
		mini.alert("未找到指定的导引单打印机!");
		return;
	}
	var type=mini.get("type").getValue();
	if (type=="0") {
		LODOP.SET_PRINT_PAGESIZE(1,0,0, "A4");
	} else {
		LODOP.SET_PRINT_PAGESIZE(2,0,0, "A5");
	}
	var address=mini.get("address").getValue();
	var today=mini.get("today").getValue();
	var dydNo=$("#dyd_iframe").contents().find("[id$=_dyd]").length;
	var dydStyle=mini.get("dydStyle").getValue();
	var hideProcess=mini.get("hideProcess").getValue();
	var qrCodePath=mini.get('qrCodePath').getValue();
	var dbpatientcode=$("#dyd_iframe").contents().find("#"+(1)+"_patientcode").html().trim();
	var liucheng='<div style="width:697px; margin-top:35px; ">'+
					'<table  style="width:100%;margin:0px auto;margin-top:8px;font-weight:normal;" border="0"'+
						'cellpadding="0" cellspacing="0">'+
						'<tr>'+
							(qrCodePath?
								('<td style="text-align:left;padding-left:1px;font-size:16px;font-weight:bold" rowspan="3" width="80px" valign="top">'+
									"<img border='0' style='width:110px;' src='"
									+qrCodePath+"' />"+
								'</td>')
								:'')+
							'<td style="text-align:left;padding-left:40px;font-size:16px;font-weight:bold" valign="top">'+
							/*	'个人体检（电子）报告查询流程：'+*/
								'体检排队查询入口：'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="text-align:left;padding-left:40px;font-size:14px;" valign="top">'+
					/*			'①苹果和安卓手机APP搜索下载<span style="font-weight:bold">（沃德国际）</span>，'
							     +'新注册用户必须使用登记体检录入的手机号码登录注册，'+
							     '进入APP首页点击<span style="font-weight:bold">“报告档案”</span>进行查询个人体检电子报告。' +*/							  
								'第一步：使用<span style="font-weight:bold">「微信」</span>扫描左方二维码；'+
									'跳转至<span style="font-weight:bold">「QDITHC健康管理中心」</span>小程序；&#12288&#12288&#12288&#12288&#12288'
								 +
								  '第二步：点击<span style="font-weight:bold">「我的排队」</span>即可'+	'点击<span style="font-weight:bold">「导入排队信息」</span>，'
							+'输入<span style="font-weight:bold"></span>体检号:<span style="font-weight:bold">「'+dbpatientcode+'」</span>或扫一扫导引单上方条形码，点击<span style="font-weight:bold">确认</span>，即可查询排队信息。'+
							'</td>'+
						'</tr>'+
					/*'<tr>'+
						'<td valign="middle" rowspan="3" style="font-size:16px;width:40px;border-right:1px solid #000;">体检</br>流程</td>'+
						'<td style="text-align:left;padding-left:4px;font-size:14px" valign="top">'+
							'①总台登记照相→上2楼体检(请严格按照排队系统的分配及导检人员安排有序的进行体检)。'+
						'</td>'+
					'</tr>'+
					'<tr>'+
						'<td style="text-align:left;padding-left:4px;font-size:14px" valign="top">'+
							'②体检结束后请将此单交至2楼前台。'+
						'</td>'+
					'</tr>'+
					'<tr>'+
						'<td style="text-align:left;padding-left:4px;font-size:14px" valign="top">'+
							'③如有疑问请向导诊护士咨询。'+
						'</td>'+
					'</tr>'+
					*/
					'</table>'+
				'</div>';
	for(var i=0;i<dydNo;i++){
		//LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,"");	
		LODOP.ADD_PRINT_HTM('4%',50,'100%',"79%",$("#dyd_iframe").contents().find("#"+(i+1)+"_dyd").html());
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
		var patientcode=$("#dyd_iframe").contents().find("#"+(i+1)+"_patientcode").html().trim();
		LODOP.ADD_PRINT_BARCODE("23mm","40mm",126,30,"128C",patientcode);// 条形码
		//LODOP.ADD_PRINT_BARCODE("13mm","25%",136,30,"128A",$("#dyd_iframe").contents().find("#"+(i+1)+"_patientcode").html().trim());// 条形码
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);// 不显示数字
		//LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		//LODOP.ADD_PRINT_HTM(0,20,'100%',"97%",document.getElementById("dyd").contentWindow.document.body.innerHTML);
		LODOP.ADD_PRINT_IMAGE(120,43,"104px","140px",$("#dyd_iframe").contents().find("#"+(i+1)+"_picture").html());	
		LODOP.ADD_PRINT_IMAGE('24%',50,'100%',"79%",$("#dyd_iframe").contents().find("#"+(i+1)+"_div1").html());
		LODOP.ADD_PRINT_IMAGE('26%',50,'100%',"79%",$("#dyd_iframe").contents().find("#"+(i+1)+"_div3").html());
		
	/*	if(i>0){
			LODOP.ADD_PRINT_HTM('4%',50,'100%',"79%",$("#dyd_iframe").contents().find("#"+(i+1)+"_dyd").html());
			LODOP.ADD_PRINT_TABLE('29%',"5%","90%",314,$("#dyd_iframe").contents().find("#"+(i+1)+"_div2").html());//研究div
		}else{
			LODOP.ADD_PRINT_TABLE('29%',"5%","90%",314,$("#dyd_iframe").contents().find("#"+(i+1)+"_div2").html());//研究div
		}*/
		LODOP.ADD_PRINT_HTM('29%',"4%","92%",570,$("#dyd_iframe").contents().find("#"+(i+1)+"_div2").html());//研究div
		var left="2%";
		/*
		if(qrCodePath){
			LODOP.ADD_PRINT_IMAGE('90%','2%','60px','60px'
					,"<img border='0' style='width:60px;height:60px' src='"
						+qrCodePath+"' />");
			left='100px';
		}*/
		if(dydStyle!=="0"&&hideProcess!="1"){//如果是简单，不要页脚
			LODOP.ADD_PRINT_HTM("85%",left,"100%","15%",
					liucheng+
/*					'<hr style="width:100%;border:none;border-top:2px solid #000;"/>'+
					'<div style="width:697px; text-align:center;"><span style="font-size:15px; margin-right:35px;">呵护健康，从沃德开始</span>'+
					'<span style="font-size:10px; margin-right:30px;">地址：'+address+'</span>'+
					'<span style="font-size:10px; margin-right:30px;">'+patientcode+'</span>'+
					'<span style="font-size:10px; margin-right:30px;">'+today+'</span>'+
					'<span style="font-size:10px;" tdata="pageNO">第#页</span></div>');*/
			'<hr style="width:100%;border:none;border-top:2px solid #000;"/>'+
			'<div style="width:750px; text-align:left;"><span style="font-size:13px">❤温馨提示❤：以上项目检查完后，请将此单交给导医台！</span>');
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		}else{
			LODOP.ADD_PRINT_HTM("90%",left,"96%","10%",
					//liucheng+
					'<hr style="width:100%;border:none;border-top:2px solid #000;"/>'+
					'<div style="width:697px; text-align:center;">'+
					'<span style="font-size:10px; margin-right:60px;">'+patientcode+'</span>'+
					'<span style="font-size:10px;" tdata="pageNO">第#页</span>'+
					'</div>');
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		}
		LODOP.SET_PRINTER_INDEX(DYD);// 指定打印机
		if(operation==2){
			LODOP.PRINT();
		}else if(operation==0){
			LODOP.PRINT_DESIGN();
			break;
		}else if(operation==1){
			LODOP.PREVIEW();
		}
	}
}


