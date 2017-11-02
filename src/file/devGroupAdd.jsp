<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>设备分组管理</title>
  <%@ include file="/screen/su/dwr.jsp"%>
  <%@ include file="/screen/su/branch_selector.jsp"%>
  <%@ include file="/screen/su/select.jsp"%>
  <link rel="stylesheet" type="text/css" href="<%=path%>/screen/su/searchStyle.css" />
  <script type='text/javascript' src='<%=path%>/dwr/interface/SafeBoxDevMng.js'></script>
 </head>
 <body>
  <center>
    <table width="800px" border="0" align="center" cellpadding="0" cellspacing="0" id="dev1">
     <tr>
      <td align="center">
       <table border="0px" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr id="topTr">
         <td class="top">
          <div class="top1">
           <div class="top3">
            <div class="top2">
             新增分组——查询设备
            </div>
           </div>
          </div>
         </td>
        </tr>
        <tr>
         <td>
          <table class="title" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="center">
             <table class="title-main" width="96%" border="0" cellspacing="1" cellpadding="0">
              <tr>
               <td>
                <div class="topBorder"></div>
                <div>
                 <table id="roleTable" class="title-tb" style="background-color: #fff;" width="100%"
                  border="0" cellspacing="1" cellpadding="1">
                  <tr>
                   <td class="title-tb-td1">
                    分行:
                   </td>
                   <td class="title-tb-td" style="padding-left: 8px;">
                    <span id="getBranchSelects"></span>
                    <span style="color: red;">*</span>
                   </td>
                  </tr>
                 </table>
                 <table id="roleTable" class="title-tb" style="background-color: #fff;" width="100%"
                  border="0" cellspacing="1" cellpadding="1">
                  <tr>
                   <td class="title-tb-td1">
                    设备品牌:
                   </td>
                   <td class="title-tb-td" style="padding-left: 8px;">
                    <select id="dev_make" name="dev_make" style="width: 100px">
                    </select>
                   </td>
                   <td class="title-tb-td1">
                    设备号:
                   </td>
                   <td class="title-tb-td" style="padding-left: 8px;">
                    <input type="text" name="DEV_CODE.input" id="DEV_CODE.input" onKeyUp="checkKey(event)" />
                   </td>
                  </tr>
                 </table>
                 <table id="roleTable" class="title-tb" style="background-color: #fff;" width="100%"
                  border="0" cellspacing="1" cellpadding="1">
                  <tr>
                   <td class="title-tb-td1">
                    设备类型:
                   </td>
                   <td class="title-tb-td" style="padding-left: 8px;">
                   	<input type="checkbox" name="dev_class" value="1" checked="checked"/>ATM&#160;
                   	<input type="checkbox" name="dev_class" value="2" checked="checked"/>CDM&#160;
                   	<input type="checkbox" name="dev_class" value="3" checked="checked"/>CDS&#160;
                   	<input type="checkbox" name="dev_class" value="4" checked="checked"/>CRS&#160;
                   	<input type="checkbox" name="dev_class" value="5" />CEM&#160;
                   	<input type="checkbox" name="dev_class" value="6" />BSM&#160;
                   	<input type="checkbox" name="dev_class" value="7" />NET&#160;
                   	<input type="checkbox" name="dev_class" value="8" />MOB&#160;
                   	<input type="checkbox" name="dev_class" value="9" />OTH
                    <!--<select id="dev_class" style="width: 100px"></select>-->
                    </td>
                    <td class="title-tb-td"/>
                    <input type="checkbox" class="checkbox" name="devType" id="devType" value=0>
                    <span style="font-size: 14px">跨平台设备</span>
                    <input type="text" style="display:none" id="branch_code"/>
                   </td>
                  </tr>
                 </table>
                </div>
               </td>
              </tr>
             </table>
            </td>
           </tr>
           <tr>
            <td>
             <table class="buttom-table">
              <tr>
               <td>
                &#160;
               </td>
               <td id="addButtonTD" width="120" style="display: none">
                <div class="buttom-up" onclick="commit()" onmouseover="this.className='buttom-over'"
                 onmousedown="this.className='buttom-clickdown'" onmouseup="this.className='buttom-over'"
                 onMouseOut="this.className='buttom-up'">
                 <div class="buttom1"></div>
                 <div class="buttom2">
                  <img src="<%=path%>/screen/su/images/save.gif" width="16" height="16" class="buttom-ing" />
                  提交选中
                 </div>
                 <div class="buttom3"></div>
                </div>
               </td>
               <td id="searchButtonTD" width="120">
                <div class="buttom-up" onclick="devquery()" onmouseover="this.className='buttom-over'"
                 onmousedown="this.className='buttom-clickdown'" onmouseup="this.className='buttom-over'"
                 onMouseOut="this.className='buttom-up'">
                 <div class="buttom1"></div>
                 <div class="buttom2">
                  <img src="<%=path%>/screen/su/images/search.gif" width="16" height="16" class="buttom-ing" />
                  查询设备
                 </div>
                 <div class="buttom3"></div>
                </div>
               </td>
               <td id="delButtonTD" width="100">
                <div class="buttom-up" onclick="location='devGroupMng.jsp'" onmouseover="this.className='buttom-over'"
                 onmousedown="this.className='buttom-clickdown'" onmouseup="this.className='buttom-over'"
                 onMouseOut="this.className='buttom-up'">
                 <div class="buttom1"></div>
                 <div class="buttom2">
                  <img src="<%=path%>/screen/su/images/returnback.gif" width="16" height="16" class="buttom-ing" />
                  返回
                 </div>
                 <div class="buttom3"></div>
                </div>
               </td>
              </tr>
             </table>
            </td>
           </tr>
           <tr>
            <td>
             <div id="devresult"></div>
            </td>
           </tr>
          </table>
         </td>
        </tr>
       </table>
      </td>
     </tr>
    </table>
    <table width="800px" border="0" align="center" cellpadding="0" cellspacing="0" id="div2" style="display: none">
     <tr>
      <td align="center">
       <table border="0px" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
         <td class="top">
          <div class="top1">
           <div class="top3">
            <div class="top2">
             新增设备分组
            </div>
           </div>
          </div>
         </td>
        </tr>
        <tr>
         <td>
          <table class="title" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td align="center">
             <table class="title-main" width="96%" border="0" cellspacing="1" cellpadding="0">
              <tr>
               <td>
                <div class="topBorder"></div>
                <div>
                 <table id="roleTable" class="title-tb" style="background-color: #fff;" width="100%"
                  border="0" cellspacing="1" cellpadding="1">
                  <tr>
                   <td class="title-tb-td1">
                    设备分组ID：
                   </td>
                   <td class="title-tb-td">
                    <div class="div-position noNull">
                    	<span id="bcode"></span><input type="text" id="group_id" size="4" maxlength="3"/>
                    </div>
                   </td>
                   <td class="title-tb-td1">
                    设备分组名：
                   </td>
                   <td class="title-tb-td">
                   <div class="div-position noNull">
                    <input type="text" id="group_name" size="20" maxlength="20"/>
                    </div>
                   </td>
                  </tr>
                 </table>
                </div>
               </td>
              </tr>
             </table>
            </td>
           </tr>
           <tr>
            <td>
             <table class="buttom-table">
              <tr>
               <td style="text-align: center">
                <span style="color: red">即将为下列设备归入新的分组</span>
               </td>
               <td id="searchBTN" width="120">
                <div class="buttom-up" onclick="saveAppend()" onmouseover="this.className='buttom-over'"
                 onmousedown="this.className='buttom-clickdown'" onmouseup="this.className='buttom-over'"
                 onMouseOut="this.className='buttom-up'">
                 <div class="buttom1"></div>
                 <div class="buttom2">
                  <img src="<%=path%>/screen/su/images/add.gif" class="buttom-ing" />
                  新增分组
                 </div>
                 <div class="buttom3"></div>
                </div>
               </td>
               <td id="backBTN" width="110">
                <div class="buttom-up" onclick="doBack()" onmouseover="this.className='buttom-over'"
                 onmousedown="this.className='buttom-clickdown'" onmouseup="this.className='buttom-over'"
                 onMouseOut="this.className='buttom-up'">
                 <div class="buttom1"></div>
                 <div class="buttom2">
                  <img src="<%=path%>/screen/su/images/returnback.gif" class="buttom-ing" />
                  上一步
                 </div>
                 <div class="buttom3"></div>
                </div>
               </td>
              </tr>
             </table>
            </td>
           </tr>
           <tr>
            <td>
             <div id="showSelectData" style="width: 780px; padding: 0 10px 10px 10px; word-break: break-all;text-align:left"></div>
             <hr/>
             <div id="showTable" style="width: 100%"></div>
            </td>
           </tr>
          </table>
         </td>
        </tr>
       </table>
      </td>
     </tr>
    </table>
  </center>
 </body>
 <script type="text/javascript">
var devs = new Array();
showSelector("dev_make", "DEV_MAKE", "DEV_MAKE_NAME", "dev_make_list", "","DEV_MAKE");
showBranchSelector("getBranchSelects", "no", 1, 3, 2);
//showSelector("dev_class", "DEV_CLASS", "DEV_CLASS_NAME", "dev_class_list", "","DEV_CLASS");

function devquery(){
	if($("searchButtonTD").disabled) return;
	var bCODE = $("branchSelector").value;
	var dev_code = $('DEV_CODE.input').value.trim();
  if(bCODE.length < 11){
		alert("请至少选择一级分行。");
		return;
	}
	$("branch_code").value = bCODE;
	var devtype = 0;
  if ($('devType').checked){
      devtype = 1;
  }
  var numExp = /^[0-9]{4,12}$/;
  if(dev_code.length>0){
  	if(!numExp.test(dev_code)){
      alert("设备编号请输入4至12位的数字。");
      return;
  	}
  }
  var dev_class = document.getElementsByName("dev_class");
  var devclass = new Array();
  for(var i = 0;i < dev_class.length;i++){
  	if(dev_class[i].checked){
  		devclass.push(dev_class[i].value);
  	}
  }
  if(devclass.length<1){
  	alert("请选择“设备类型”！");
  	return;
  }
  $("searchButtonTD").disabled = true;
  $('devresult').innerHTML = "设备查找中，请稍后...";
  SafeBoxDevMng.outHtmlString(bCODE, $('dev_make').value, devclass.toString(),
			dev_code, devtype, function (data){
				$('devresult').innerHTML = data ;
    		$("addButtonTD").style.display = "inline";
    		noloading();
    		$("searchButtonTD").disabled = false;
			});
	loading();//进度条
 }

 function selectAll() {
  var sels = document.getElementsByName("devList");
  for(var i = 0;i<sels.length;i++){
  	if(!sels[i].disabled){
  		sels[i].checked = true;
  	}
	}
}
 
 function unSelectAll(count){
  var sels = document.getElementsByName("devList");
  for(var i = 0;i<sels.length;i++){
  	sels[i].checked = false;
	}
 }
 
function deSelect(){
	var sels = document.getElementsByName("devList");
  for(var i = 0;i<sels.length;i++){
  	if(!sels[i].disabled){
  		if(sels[i].checked){
  			sels[i].checked = false;
  		}else{
  			sels[i].checked = true;
  		}
  	}
	}
}

function commit(){
 if($("addButtonTD").disabled) return;
 devs = new Array();
 var checkBox = document.getElementsByName("devList");
 for(var i = 0; i < checkBox.length; i++){
  if(checkBox[i].checked){
   devs.push(checkBox[i].value);
  }
 }
 if(devs.length==0){
  alert("无被选取的设备,请先查询后选择对应的设备再提交。");
  return;
 }
 $("addButtonTD").disabled = true;
 $("div2").style.display = "block";
 $("dev1").style.display = "none";
 $("bcode").innerHTML = document.getElementById("branch_code").value.trim().split("=")[1];
 $("showSelectData").innerHTML = devs.toString().replace(/\,/g,", ");
 $("addButtonTD").disabled = false;
 getGroup();
}

function doBack(){
 $("div2").style.display = "none";
 $("dev1").style.display = "block";
}

function saveAppend(){
 if($("searchBTN").disabled) return;
 var bcode = $("bcode").innerHTML.trim();
 var group_id = bcode+$("group_id").value.trim();
 var group_name = $("group_name").value.trim();
 if(group_name.length<1){
 	alert("请输入“设备分组名”！");
 	return;
 }
 var numExp = /^[0-9]{12}$/;
 if(!numExp.test(group_id)){
 	window.alert("请输入三位的分组ID补充数字，分组ID必须为12位的数字！");
 	return;
 }
 $("searchBTN").disabled = true;
 SafeBoxDevMng.isGroupIdUsing(group_id,function (data){
  if(data){
  	alert("已存在相同的“分组ID”，请输入其他字符！");
  	$("searchBTN").disabled = false;
  	return;
  }else{
  	SafeBoxDevMng.addGroup(group_id, group_name, devs, function (str){
  		alert(str.split("||")[1]);
  		if(str.split("||")[0]=="success"){
				window.location = "devGroupMng.jsp";
			}
			noloading();
			$("searchBTN").disabled = false;
  	});
  	loading();
  }
 });
}

function getGroup(){
	SafeBoxDevMng.getGroup(function (data){
  		$("showTable").innerHTML = data;
  	});
}
</script>
</html>