<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style=" padding: 10px;">
		<form id="fm" method="post" action="">
			<input type="hidden" name="YWLSH" value="${mlph.YWLSH}" />
			<fieldset>
				<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />门楼牌号详细信息
				</legend>
				<table class="detail-table">
				<tr></tr>
					<tr>
						<th>地址:</th>
						<td colspan="5">
							<c:out value="${mlph.DZMC}" />
						</td>
					</tr>
					<tr>
						<th>区划内详细地址:</th>
						<td colspan="5">
							<c:out value="${mlph.QHNXXDZ}"></c:out>
						</td>
					</tr>
					<tr>
					<th>所属街路巷(小区)名称:</th>
						<td colspan="5">
							<c:out value="${mlph.SSJLXXQ_JLXXQMC}" />
						</td>
						
					</tr>
					
					<tr>
						<th>地址元素类型:</th>
						<td>
						    <km:dataDic dictCode="DZYSFL" value="${mlph.DZYSLXDM}"/>
							<%-- <input id="dzyslxdm" dict="dzyslxDict" disabled name="dzyslxdm" value="${mlph.DZYSLXDM}"  /> --%>
						</td>
						<th>门(楼)牌号:</th>
						<td>
							<c:out value="${mlph.MLPH}"></c:out>
						</td>
						<th>临时门（楼）牌标识:</th>
						<td>
						    <km:dataDic dictCode="yesorno" value="${mlph.LSMLPBS}"/>
							<%-- <input id="lsmlpbs" dict="yesornoDict" disabled name="lsmlpbs" value="${mlph.LSMLPBS}"  /> --%>
						</td>
					</tr>
					
					
					<tr>
						<th>办理单位名称:</th>
						<td>
							<c:out value="${mlph.BLDW_GAJGMC}" />
						</td>
						<th>办理人姓名:</th>
						<td>
							<c:out value="${mlph.BLR_XM}"></c:out>
						</td>
						<th>办理时间:</th>
						<td>
							${mlph.BLSJ}
						</td>
					</tr>
					
					
					<tr>
						<th>数据归属单位名称:</th>
						<td>
							<c:out value="${mlph.SJGSDWMC}" />
						</td>
						
						<th>警务区名称:</th>
						<td>
							<c:out value="${mlph.JWQMC}" />
						</td>
						<th>门楼牌号类型:</th>
						<td>
						    <km:dataDic dictCode="mlphlx" value="${mlph.MLPHLXID}"/>
							<%-- <input id="mlphlxid" dict="mlphlxDict" disabled name="mlphlxid" value="${mlph.MLPHLXID}" /> --%>
						</td>
					</tr>
					
					<tr>
						<th>所属市局代码 :</th>
						<td>
							<c:out value="${mlph.SSSJ}"></c:out>
						</td>
						<th>所属分局代码:</th>
						<td>
							<c:out value="${mlph.SSFJ}" />
						</td>
						<th>所属派出所代码:</th>
						<td>
							<c:out value="${mlph.SSPCS}" />
						</td>
					</tr>
					
					<tr>
						<th>审批结果 :</th>
						<td>
							<c:out value="${mlph.SPZT}"></c:out>
						</td>
						<th>中心点横坐标:</th>
						<td>
							<c:out value="${mlph.ZXDHZB}" />
						</td>
						<th>中心点纵坐标:</th>
						<td>
							<c:out value="${mlph.ZXDZZB}" />
						</td>
					</tr>
					
					<tr>
						<th>数据来源 :</th>
						<td>
							<c:choose>
								<c:when test="${mlph.FROMBY eq 0}">后台</c:when>
								<c:when test="${mlph.FROMBY eq 1}">终端</c:when>
								<c:when test="${mlph.FROMBY eq 2}">导入</c:when>
								<c:otherwise>
									<c:out value="${mlph.FROMBY}"></c:out>
								</c:otherwise>
							</c:choose>
						</td>
						<th>设备编号:</th>
						<td>
							<c:out value="${mlph.SBID}"></c:out>
						</td>
						<th>GPS:</th>
						<td>
							  <c:out value="${mlph.GPSX}" /> <c:out value="${mlph.GPSY}" />
						</td>
					</tr>
					
					<tr>
						<th>登记人:</th>
						<td>
							<c:out value="${mlph.DJR}"></c:out>
						</td>
						<th>登记单位:</th>
						<td>
							<c:out value="${mlph.DJDW}"></c:out>
						</td>
						<th>登记时间:</th>
						<td>
							${mlph.DJSJ}
						</td>
					</tr>
					
					<tr>
						<th>修改人:</th>
						<td>
							<c:out value="${mlph.XGR}"></c:out>
						</td>
						<th>修改单位:</th>
						<td>
							<c:out value="${mlph.XGDW}"></c:out>
						</td>
						<th>修改时间:</th>
						<td>
							${mlph.GXSJ}
						</td>
					</tr>
					
					<tr>
						<th>注销标志:</th>
						<td>
							<c:choose>
								<c:when test="${mlph.DELTAG eq 0}"><span class="tag-success">正常</span></c:when>
								<c:when test="${mlph.DELTAG eq 1}"><span class="tag-normal">已注销</span></c:when>
								<c:otherwise>
									<c:out value="${mlph.DELTAG}"></c:out>
								</c:otherwise>
							</c:choose>
						</td>
						<th>注销人:</th>
						<td>
							<c:out value="${mlph.DELUSER}"></c:out>
						</td>
						<th>注销时间:</th>
						<td>
							${mlph.DELTIME}
						</td>
					</tr>
					<tr>
						<th>注销原因:</th>
						<td  colspan="5">
							${mlph.CXYY}
						</td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
	
			<fieldset>
				<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />申请人信息
				</legend>
				<table class="detail-table">
				<tr></tr>
					<tr>
						<th>申请人姓名:</th>
						<td>
							<c:out value="${sqrxx.sqrxm}" />
						</td>
						<th>申请人身份证号:</th>
						<td>
							<c:out value="${sqrxx.sqrgmsfzhm}"></c:out>
						</td>
						<th>申请人联系电话:</th>
						<td>
							<c:out value="${sqrxx.sqrlxdh}"></c:out>
						</td>
					</tr>
					<tr>
						<th>申请单位名称:</th>
						<td>
							<c:out value="${sqrxx.sqdwmc}" />
						</td>
						<th>申请单位联系电话:</th>
						<td colspan="3">
							<c:out value="${sqrxx.sqdwlxdh}"></c:out>
						</td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>