spring+redis 使用
配置redis

//baseuser 实体类
BaseUser baseuser = new BaseUser();
baseuser.setUsername(adminUser.getAdmin_loginname());//用户名
baseuser.setOrgna_name(adminUser.getOrgna_name());//组织机构名称
baseuser.setId(adminUser.getAdmin_id());//id

baseuser.setEnabled(adminUser.getEnabled());//是否可用标识字段

baseuser.setOrgna_id(adminUser.getOrgna_id());//组织机构代码

baseuser.setName(adminUser.getAdmin_name());//用户名称

baseuser.setSfzh(adminUser.getAdmin_sfzh());//用户公民身份号码

baseuser.setPorgna_id(adminUser.getPorgna_id());//父组织机构代码---

baseuser.setSsfj(adminUser.getSsfj());//所属分局--？
baseuser.setSspcs(adminUser.getSspcs());//所属派出所--？
baseuser.setSsjwq(adminUser.getSsjwq());//所属警务区--？

baseuser.setAdminFlag(true);//管理员标识（是否管理员）--？

baseuser.setYhlx("admin");//用户类型(公安用户为gayh，费公安用户为registered,管理员为admin)--？

baseuser.setSszz(adminUser.getOrgna_id());// 警察用户所属组织--？

//获取sessionid
String sessionid = request.getSession().getId();
//获取JedisPool
JedisPool pool = (JedisPool)SpringContextUtil.getBean("jedisPool");
//获取资源
Jedis jedis = pool.getResource();
byte[] byteuser = SerializeUtil.serialize(baseuser);
//放置session及设置session超时时间
jedis.set(sessionid.getBytes(), byteuser);
jedis.expire(sessionid.getBytes(), 1800);
//session超时后把连接放回连接池
pool.returnResource(jedis);