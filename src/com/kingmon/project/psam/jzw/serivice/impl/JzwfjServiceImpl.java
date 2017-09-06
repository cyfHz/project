package com.kingmon.project.psam.jzw.serivice.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.psam.jzw.JzwJgUtil;
import com.kingmon.project.psam.jzw.mapper.JzwfjChangeMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.JzwfjChange;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.serivice.IJzwfjChangeBizConfigService;
import com.kingmon.project.psam.jzw.serivice.IJzwfjService;
import com.kingmon.project.psam.jzw.view.JzwFjShowInPage;
import com.kingmon.project.psam.jzw.view.JzwfjView;
import com.kingmon.project.psam.jzw.view.JzwfjViewModel;
import com.kingmon.project.psam.sy.mapper.SyFwCzrkMapper;
import com.kingmon.project.psam.sy.mapper.SyFwJwryMapper;
import com.kingmon.project.psam.sy.mapper.SyFwLdrkMapper;
import com.kingmon.project.psam.sy.mapper.SyFwjbxxMapper;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.Syjwry;
@Service
public class JzwfjServiceImpl extends BaseService implements IJzwfjService{

	@Autowired
	private JzwfjMapper jzwfjMapper;
	@Autowired
	private JzwlcMapper jzwlcMapper;
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwfjChangeMapper jzwfjChangeMapper;
	@Autowired
	private OrganizationUserMapper organizationUserMapper;
	@Autowired
	private IJzwfjChangeBizConfigService jzwfjChangeBizConfigService;
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadJzwfjDataSet(Map<String, String> params) {
		return null;
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public Jzwfj findJzwfjByJzwfjid(String jzwfjid){
		if(jzwfjid==null||jzwfjid.isEmpty()){
			return null;
		}
		return jzwfjMapper.selectByPrimaryKey(jzwfjid);
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadJzwfjInfoDataSet(ParamObject po) {
		StringBuilder sql=new StringBuilder("");
		sql.append(" SELECT j.JZWFJID, ")
		.append(" j.JZWJGID, ")
		.append(" j.JZWDYID, ")
		.append(" j.JZWLCID, ")
		.append(" j.FJXH, ")
		.append(" j.FJMC ")
		.append(" @from DZ_JZWFJ j ")
		.append(" where 1=1 ");
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<Jzwfj> findJzwfjByJzwjgId(String jzwjgId){
		if(StringUtils.hasText(jzwjgId)){
			return jzwfjMapper.selectFjByJzwjgId(jzwjgId);
		}
		return null;
	}

	/**
	 * Map：JZWFJID, FJXH, FJMC,SHOWINFO,SHOWMODE
	 */
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<Map<String, Object>> findFjMapByJzwjgId(String jzwjgId) {
		if(StringUtils.hasText(jzwjgId)){
			return jzwfjMapper.selectFjShowMapByJzwjgId(jzwjgId);
		}
		return null;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> chaiFen(JzwfjView view) {
		//返回给页面的信息
		Map<String,Object> map=new HashMap<String, Object>();
		//获取被拆分房间信息
		Jzwfj jzwfj_o = jzwfjMapper.selectByPrimaryKey(view.getOldJzwfjIds()[0]);
		short fj_xh_cf=Short.parseShort(jzwfj_o.getFjxh());//当前被拆分房间的房间序号
		String userId = "";
//		if (view.getUserId()!=null) {
//			userId = view.getUserId();
//		}else if(SecurityUtils.getSessionUser()!=null){
//			userId = SecurityUtils.getSessionUser().getUserId();
//		}
		//设置原房间标志位为被拆分
		jzwfj_o.setChangeSign(KConstants.CHANGESIGN_CHAIFEN);
		jzwfj_o.setUpdated(new Date());
		jzwfj_o.setUpdatedby(userId);
		jzwfj_o.setZhgxrq(new Date());
//		jzwfj_o.setMacAddress(view.getMacAddress()==null?"":view.getMacAddress());
//		jzwfj_o.setImeiNum(view.getImeiNum()==null?"":view.getImeiNum());
//		jzwfj_o.setSimNum(view.getSimNum()==null?"":view.getSimNum());
//		jzwfj_o.setTfCardNum(view.getTfCardNum()==null?"":view.getTfCardNum());
//		if (view.getGpsX()!=null) {
//			jzwfj_o.setGpsX(view.getGpsX());
//		}
//		if (view.getGpsY()!=null) {
//			jzwfj_o.setGpsY(view.getGpsY());
//		}
		List<String> toJzwfjIdList=Lists.newArrayList();
		//生成新房间信息
		for (int i = 0; i < view.getJzwfjInfoList().size(); i++) {
			//判断序号
			String fj_xh=view.getJzwfjInfoList().get(i).getFjxh(); 
			String dy_id=jzwfj_o.getJzwdyid();
			List<Jzwfj> lists=jzwfjMapper.selectJzwfjByJzwDyId(dy_id);
			for(Jzwfj fj:lists){
				
				short fj_xh_from_db=Short.parseShort(fj.getFjxh());
				short fj_xh_from_param=Short.parseShort(fj_xh);
				if(fj_xh_from_db==fj_xh_from_param&&fj_xh_cf!=fj_xh_from_param){
					AlertSLEUtil.Error("该单元序号：【"+fj_xh+"】已经存在");
				}
			}
			
			
			JzwfjViewModel jzwfjViewModel = view.getJzwfjInfoList().get(i);
			Jzwfj jzwfj = new Jzwfj();
			jzwfj.setJzwfjid(UUIDUtil.uuid());
			jzwfj.setFjxh(jzwfjViewModel.getFjxh());
			jzwfj.setFjmc(jzwfjViewModel.getFjmc());
			jzwfj.setShowInfo(jzwfjViewModel.getShowInfo());
			
			jzwfj.setJzwjgid(jzwfj_o.getJzwjgid());
			//暂时使用原房间单元
			jzwfj.setJzwdyid(jzwfj_o.getJzwdyid());
			//暂时使用原房间楼层
			jzwfj.setJzwlcid(jzwfj_o.getJzwlcid());
			jzwfj.setCreated(new Date());
			jzwfj.setShowMode("1");
			jzwfj.setCreatedby(userId);
			//
//			jzwfj.setMacAddress(view.getMacAddress()==null?"":view.getMacAddress());
//			jzwfj.setImeiNum(view.getImeiNum()==null?"":view.getImeiNum());
//			jzwfj.setSimNum(view.getSimNum()==null?"":view.getSimNum());
//			jzwfj.setTfCardNum(view.getTfCardNum()==null?"":view.getTfCardNum());
//			if (view.getGpsX()!=null) {
//				jzwfj.setGpsX(view.getGpsX());
//			}
//			if (view.getGpsY()!=null) {
//				jzwfj.setGpsY(view.getGpsY());
//			}
			
			//添加新房间
			jzwfjMapper.insertSelective(jzwfj);
			//返回页面信息
			map.put(jzwfjViewModel.getKey(), jzwfj.getJzwfjid());
			
			//添加历史记录
			JzwfjChange jzwfjChange = new JzwfjChange();
			jzwfjChange.setId(UUIDUtil.uuid());
			jzwfjChange.setFromfjid(jzwfj_o.getJzwfjid());
			jzwfjChange.setTofjid(jzwfj.getJzwfjid());
			jzwfjChange.setChangesign(KConstants.CHANGESIGN_CHAIFEN);
			jzwfjChange.setCreated(new Date());
			jzwfjChange.setCreatedby(userId);
			if (userId!=null&&!("".equals(userId))) {
				OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(userId);
				if (user!=null) {
					jzwfjChange.setCreateddw(user.getOrgna_id());
				}
			}
//			jzwfjChange.setCreateddw(SecurityUtils.getSessionUser().getOrganizationId());
			jzwfjChangeMapper.insertSelective(jzwfjChange);
			
			toJzwfjIdList.add(jzwfj.getJzwfjid());
		}
		//更新原房间
		jzwfjMapper.updateByPrimaryKeySelective(jzwfj_o);
		
//		jzwfjChangeBizConfigService.processJzwfjChaiFen(jzwfj_o.getJzwfjid(), toJzwfjIdList,toJzwfjIdList.get(0));
		return map;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> heBing(JzwfjView view) {
		//返回给页面的信息
		Map<String,Object> map=new HashMap<String, Object>();
		String[] oldJzwfjIds = view.getOldJzwfjIds();
		String jzwlcId="";
		String jzwdyId="";
		String jzwjgId="";
		String userId = "";
//		if (view.getUserId()!=null) {
//			userId = view.getUserId();
//		}else if(SecurityUtils.getSessionUser()!=null){
//			userId = SecurityUtils.getSessionUser().getUserId();
//		}
		
		String newJzwfjId = UUIDUtil.uuid();
		List<String> fromJzwfjIdList=Lists.newArrayList();
		
		
		//将被合并的几个房间标识为改为被合并
		for (int i = 0; i < oldJzwfjIds.length; i++) {
			Jzwfj jzwfj_o = jzwfjMapper.selectByPrimaryKey(oldJzwfjIds[i]);

			//判断序号
			String fj_xh=view.getJzwfjInfoList().get(0).getFjxh(); 
			String dy_id=jzwfj_o.getJzwdyid();
			List<Jzwfj> lists=jzwfjMapper.selectJzwfjByJzwDyId(dy_id);
			for(Jzwfj fj:lists){
				short fj_xh_from_db=Short.parseShort(fj.getFjxh());//房间序号
				short fj_xh_from_param=Short.parseShort(fj_xh);//合并后的新序号
				
				boolean isTrue=true;
				for (int  j= 0; j < oldJzwfjIds.length; j++) {
					Jzwfj jzwfj = jzwfjMapper.selectByPrimaryKey(oldJzwfjIds[j]);
					Short fj_xh_hb=Short.parseShort(jzwfj.getFjxh());//被合并的房间序号
					if(fj_xh_hb==fj_xh_from_param){
						isTrue=false;
					}
				}
				if(fj_xh_from_db==fj_xh_from_param&&isTrue){
					AlertSLEUtil.Error("该单元序号：【"+fj_xh+"】已经存在");
				}
			}
			
			
			//设置原房间标志位为被合并
			jzwfj_o.setChangeSign(KConstants.CHANGESIGN_HEBING);
			jzwfj_o.setUpdated(new Date());
			jzwfj_o.setUpdatedby(userId);
			jzwfj_o.setZhgxrq(new Date());
//			jzwfj_o.setMacAddress(view.getMacAddress()==null?"":view.getMacAddress());
//			jzwfj_o.setImeiNum(view.getImeiNum()==null?"":view.getImeiNum());
//			jzwfj_o.setSimNum(view.getSimNum()==null?"":view.getSimNum());
//			jzwfj_o.setTfCardNum(view.getTfCardNum()==null?"":view.getTfCardNum());
//			if (view.getGpsX()!=null) {
//				jzwfj_o.setGpsX(view.getGpsX());
//			}
//			if (view.getGpsY()!=null) {
//				jzwfj_o.setGpsY(view.getGpsY());
//			}
			
			jzwlcId = jzwfj_o.getJzwlcid();
			jzwdyId = jzwfj_o.getJzwdyid();
			jzwjgId = jzwfj_o.getJzwjgid();
			
			jzwfjMapper.updateByPrimaryKeySelective(jzwfj_o);
			
			//添加历史记录
			JzwfjChange jzwfjChange = new JzwfjChange();
			jzwfjChange.setId(UUIDUtil.uuid());
			jzwfjChange.setFromfjid(jzwfj_o.getJzwfjid());
			jzwfjChange.setTofjid(newJzwfjId);
			jzwfjChange.setChangesign(KConstants.CHANGESIGN_HEBING);
			jzwfjChange.setCreated(new Date());
			jzwfjChange.setCreatedby(userId);
			if (userId!=null&&!("".equals(userId))) {
				OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(userId);
				if (user!=null) {
					jzwfjChange.setCreateddw(user.getOrgna_id());
				}
			}
//			jzwfjChange.setCreateddw(SecurityUtils.getSessionUser().getOrganizationId());
			jzwfjChangeMapper.insertSelective(jzwfjChange);
			fromJzwfjIdList.add(jzwfj_o.getJzwfjid());
		}
		
		//生成新房间
		JzwfjViewModel jzwfjViewModel = view.getJzwfjInfoList().get(0);
		Jzwfj jzwfj = new Jzwfj();
		jzwfj.setJzwfjid(newJzwfjId);
		jzwfj.setFjxh(jzwfjViewModel.getFjxh());
		jzwfj.setFjmc(jzwfjViewModel.getFjmc());
		jzwfj.setShowInfo(jzwfjViewModel.getShowInfo());
		
		jzwfj.setJzwjgid(jzwjgId);
		//暂时使用原房间单元
		jzwfj.setJzwdyid(jzwdyId);
		//暂时使用原房间楼层
		jzwfj.setJzwlcid(jzwlcId);
		jzwfj.setCreated(new Date());
		jzwfj.setShowMode("1");
//		jzwfj.setCreatedby(SecurityUtils.getSessionUser().getUserId());
//		jzwfj.setMacAddress(view.getMacAddress()==null?"":view.getMacAddress());
//		jzwfj.setImeiNum(view.getImeiNum()==null?"":view.getImeiNum());
//		jzwfj.setSimNum(view.getSimNum()==null?"":view.getSimNum());
//		jzwfj.setTfCardNum(view.getTfCardNum()==null?"":view.getTfCardNum());
//		if (view.getGpsX()!=null) {
//			jzwfj.setGpsX(view.getGpsX());
//		}
//		if (view.getGpsY()!=null) {
//			jzwfj.setGpsY(view.getGpsY());
//		}
		
		//添加新房间
		jzwfjMapper.insertSelective(jzwfj);

		map.put(jzwfjViewModel.getKey(), jzwfj.getJzwfjid());
		
//		jzwfjChangeBizConfigService.processJzwfjHeBing(fromJzwfjIdList, jzwfj.getJzwfjid());
		return map;
	}
	
	public void addJzwFj(Jzwfj targetJzwfj,Jzwfj refFj){
		
		
	}

	@Transactional(rollbackFor=Exception.class)
	@SuppressWarnings("rawtypes")
	@Override
	public KJSONMSG AddJzwFj(Map view) {
		String refJzwFjId=(String) view.get("refJzwFjId");
		String jzwjgId=(String) view.get("jzwjgId");
		String addfjmc=(String) view.get("addfjmc");
		String addfjxh=(String) view.get("addfjxh");
		//添加房间位于选中房间的方位  上：true 下:false
		
		Boolean isUpDown=Boolean.valueOf((String)view.get("isUpDown"));
		//选中房间
		Jzwfj refFj=jzwfjMapper.selectByPrimaryKey(refJzwFjId);
		
		int left=JzwJgUtil.getLeft(refFj);
		int top=JzwJgUtil.getTop(refFj);
		int width = JzwJgUtil.getWidth(refFj);;
		int refFjTopp=JzwJgUtil.getTop(refFj);
		int realTop=top;
		boolean isNotFirst=false;
		String lcdyId=refFj.getJzwdyid();
		
		
		//判断序号
		List<Jzwfj> lists=jzwfjMapper.selectJzwfjByJzwDyId(lcdyId);
		for(Jzwfj fj:lists){
			short fj_xh_from_db=Short.parseShort(fj.getFjxh());
			short fj_xh_from_param=Short.parseShort(addfjxh);
			if(fj_xh_from_db==fj_xh_from_param){
				AlertSLEUtil.Error("该单元序号：【"+addfjxh+"】已经存在");
			}
		}
		
		
		List<Jzwfj> fjList= jzwfjMapper.selectFjByJzwjgId(jzwjgId);
		Jzwlc reflc = jzwlcMapper.selectByPrimaryKey(refFj.getJzwlcid());//取得选中房间所在楼层
		int lcxh = Integer.parseInt(reflc.getLcxh());//楼层序号
		//楼层ID   
		String lcid="";
		//楼顶添加房间
		if(isUpDown){
			
			isNotFirst = isFirst(refFjTopp,fjList,isUpDown);
			if(isNotFirst){//如果该层不第一次添加房间，则房间不需要向下推移
				realTop=refFjTopp-KConstants.jzwHeight;
				for(Jzwfj fj :fjList){
					int topx=JzwJgUtil.getTop(fj);
					if(topx<refFjTopp&&(refFjTopp-KConstants.jzwHeight==topx)&&lcdyId.equals(fj.getJzwdyid())){
						//楼层房间不能全部删除，此处需要删除房间时进行控制
						lcid = fj.getJzwlcid();
						break ;
					}
				}
			}
			if(!isNotFirst){//如果该层第一次添加房间，则新添加的房间占据选中房间的位置，下面所有房间均要向下推移一个楼层的高度,还要创建楼层
				//向下推移
				translationPostion(fjList);
				//创建楼层
				Jzwlc jzwlc=JzwJgUtil.constructJzwLc(jzwjgId,refFj.getJzwdyid(),(lcxh+1)+"",(lcxh+1)+"层");
				jzwlcMapper.insertSelective(jzwlc);
				lcid = jzwlc.getJzwlcid();
				
				//如果新增一个楼层，修改到结构的楼层数内 20160714
				Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
				short newLcs=(short) (lcxh+1);//楼层数 
				jzwjg.setLcs(newLcs);
				jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
			}
			
			JzwFjShowInPage fjinfo=new JzwFjShowInPage( 1,left,realTop,width, KConstants.jzwHeight);
			Jzwfj newFj=JzwJgUtil.constructJzwFj(jzwjgId, refFj.getJzwdyid(),lcid, addfjxh, addfjmc,fjinfo);
			
			jzwfjMapper.insertSelective(newFj);
		}else{
			isNotFirst = isFirst(refFjTopp,fjList,isUpDown);
			
			for(Jzwfj fj :fjList){
				int topx=JzwJgUtil.getTop(fj);
				if(topx>refFjTopp&&(refFjTopp+KConstants.jzwHeight==topx)&&lcdyId.equals(fj.getJzwdyid())){
					//楼层房间不能全部删除，此处需要删除房间时进行控制
					lcid = fj.getJzwlcid();
					break ;
				}
			}
			if(!isNotFirst){//如果该层第一次添加房间，则需要创建楼层
				//创建新的地下楼层 ，且地下房间必须是按单元规则分布的
				int lcxhx=lcxh;
				if(lcxh==1){
					lcxhx=0;
				}
				Jzwlc jzwlc=JzwJgUtil.constructJzwLc(jzwjgId,refFj.getJzwdyid(),(lcxhx-1)+"",(lcxhx-1)+"层");
				jzwlcMapper.insertSelective(jzwlc);
				lcid = jzwlc.getJzwlcid();
				Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
				jzwjg.setDxcs(new Integer((lcxhx-1)*-1).shortValue());
				jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
			}
			
			//地下楼层添加时不需要向下推移，直接计算高度即可
			realTop=refFjTopp+KConstants.jzwHeight;
			JzwFjShowInPage fjinfo=new JzwFjShowInPage( 1,left,realTop,width, KConstants.jzwHeight);
			Jzwfj newFj=JzwJgUtil.constructJzwFj(jzwjgId, refFj.getJzwdyid(),lcid, addfjxh, addfjmc,fjinfo);
			jzwfjMapper.insertSelective(newFj);
		}
		
		return null;
	}
	private boolean isFirst(int refFjTopp,List<Jzwfj> fjList,boolean isUpDown){
		boolean isNotFirst = false;
		for(Jzwfj fj :fjList){
			int topx=JzwJgUtil.getTop(fj);
			if (isUpDown) {
				if(topx<refFjTopp){
					isNotFirst=true;
					break ;
				}
			}else{
				if(topx>refFjTopp){
					isNotFirst=true;
					break ;
				}
			}
			
		}
		
		 return isNotFirst;
	}
	
	private void translationPostion(List<Jzwfj> fjList){
		for(Jzwfj fj :fjList){
			int topx=JzwJgUtil.getTop(fj);
			String shwoInfo=fj.getShowInfo();
			String[] sds=shwoInfo.split(",");
			sds[1]=""+(topx+KConstants.jzwHeight);
			fj.setShowInfo(""+sds[0]+","+sds[1]+","+sds[2]+","+sds[3]); 
			jzwfjMapper.updateByPrimaryKeySelective(fj);
		}
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Object checkCanDelete(String jzwfjId) {
		if (jzwfjId==null||"".equals(jzwfjId)) {
			return new KJSONMSG(300, "请选择一个房间", false);
		}
		Jzwfj jzwfj_del = jzwfjMapper.selectByPrimaryKey(jzwfjId);
		//楼层房间不能全部删除
//		Map<String, String> params = new HashMap<String,String>();
//		params.put("jzwlcid", jzwfj_del.getJzwlcid());
		Long jzwfjCount = jzwfjMapper.selectJzwfjCountByJzwLcId(jzwfj_del.getJzwlcid());
		if (jzwfjCount<=1) {
			return new KJSONMSG(300, "该楼层不能再删除房间!", false);
		}
		return new KJSONMSG(200, "", true);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteJzwFj(String jzwfjid) {
		Map<String, Object> params = new HashMap<String, Object>();
		Jzwfj jzwfj = jzwfjMapper.selectByPrimaryKey(jzwfjid);
		jzwfj.setUpdated(new Date());
//		jzwfj.setUpdatedby(SecurityUtils.getSessionUser().getUserId());
		jzwfj.setDeltag("1");
		jzwfj.setDeltime(new Date());
//		jzwfj.setDeluser(SecurityUtils.getSessionUser().getUserId());
		jzwfjMapper.updateByPrimaryKeySelective(jzwfj);
	}

	@Override
	public Map<String, String> calculateJzwWidthAndHeight(List<Map<String, Object>> list) {
		int maxWidth = 0;
		int maxHeight = 0;
		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			String showInfo = (String) map.get("SHOWINFO");
			if (showInfo != null) {
				int left = getValue(showInfo, ",", 0);
				int width = getValue(showInfo, ",", 2);
				if (left + width > maxWidth) {
					maxWidth = left + width;
				}
				int top = getValue(showInfo, ",", 1);
				int height = getValue(showInfo, ",", 3);
				if (top + height > maxHeight) {
					maxHeight = top + height;
				}
			}
		}
		Map<String, String> widthAndHeight = new HashMap<>();
		widthAndHeight.put("maxWidth", maxWidth+"");
		widthAndHeight.put("maxHeight", maxHeight+"");
		
		return widthAndHeight;
	}
	
	private int getValue(String str,String split,int index){
		int value;
		String[] strArray = str.split(split);
		value = strArray.length==0?0:Integer.parseInt(strArray[index]);
		return value;
	}
	
	@Autowired
	SyFwCzrkMapper  syFwCzrkMapper;
	@Autowired
	SyFwJwryMapper syFwJwryMapper;
	@Autowired
	SyFwLdrkMapper syFwLdrkMapper;
	@Autowired
	SyFwjbxxMapper syFwjbxxMapper;
	@Override
	public KJSONMSG iSjzwfjHaveDpendInfo(String jwzfjId){
		SyFwjbxx fwjbxx=syFwjbxxMapper.selectByJwzfjId(jwzfjId);
		if(fwjbxx!=null){
			return new KJSONMSG(300,"该房间已经存在房屋信息");
		}
		List<SyFwCzrk> syFwCzrkList=syFwCzrkMapper.selectSyFwCzrkByjzwfjId(jwzfjId);
		if(syFwCzrkList!=null&&syFwCzrkList.size()>0){
			return new KJSONMSG(300,"该房间已经存在常住人口信息");
		}
		List<SyFwLdrk> syFwLdrkList=syFwLdrkMapper.selectSyFwLdrkByjzwfjId(jwzfjId);
		if(syFwLdrkList!=null&&syFwLdrkList.size()>0){
			return new KJSONMSG(300,"该房间已经存在流动人口信息");
		}
		List<Syjwry> syjwryList=syFwJwryMapper.selectSyjwryByjzwfjId(jwzfjId);
		if(syjwryList!=null&&syjwryList.size()>0){
			return new KJSONMSG(300,"该房间已经存在境外人口信息");
		}
		return new KJSONMSG(200,"该房间已未存在依赖信息");
	}
	
	public KJSONMSG iSjzwfjHaveDpendInfo(List<String> jwzfjIdList){
		
		
		return null;
		
	}
}
