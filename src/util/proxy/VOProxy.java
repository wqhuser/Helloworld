package util.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.annotation.Foreign;
import util.annotation.ManyToMany;
import util.annotation.OneToMany;
import util.db.BaseDAO;
import util.db.BaseVO;
import util.db.StringUtil;

public class VOProxy extends BaseDAO implements MethodInterceptor {

	public BaseVO target;

	public BaseVO bind(BaseVO vo) {
		this.target = vo;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		// 回调方法
		enhancer.setCallback(this);
		// 创建代理对象
		return (BaseVO) enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] param,
			MethodProxy proxy) throws Throwable {
		String methodName = method.getName();
		if (methodName.startsWith("get")) {
			String fieldName = StringUtil.lowercaseFirstChar(methodName
					.substring(3));
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			if (field.get(target) == null) {
				Foreign an = field.getAnnotation(Foreign.class);
				if (an != null) {// 带Foreign注解
					Field foreginField = target.getClass().getDeclaredField(
							an.foreginKey());
					foreginField.setAccessible(true);
					Object foreignKey = foreginField.get(target);// 取出该对象对应foreignkey的值
					if (foreignKey == null)
						throw new Exception("foreignKey 为空");
					else {
						Map map = new HashMap();
						map.put(target.getPKName(), foreignKey);
						BaseVO vo = (BaseVO) field.getType().newInstance();
						vo = searchByField(map, vo.getClass()).get(0);
						field.set(target, vo);
						return vo;
					}
				} else {
					OneToMany oneToMany = (OneToMany) getAnnotation(field, OneToMany.class);
					if (oneToMany != null) {// 带OneToMany注解
						Field foreginField = target.getClass().getDeclaredField(
								target.getPKName());
						foreginField.setAccessible(true);
						Object foreignKey = foreginField.get(target);// 取出该对象对应foreignkey的值
						if (foreignKey == null)
							throw new Exception("foreignKey 为空");
						else {
							Map map = new HashMap();
							map.put(StringUtil.colNameConvert(oneToMany.foreginKey()), foreignKey);
							List<BaseVO> list = (List<BaseVO>) searchByField(map, oneToMany.Type());
							field.set(target, list);
							return list;
						}
					}

					else {
						ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
						if (manyToMany != null) {
							String foreignKey = manyToMany.foreignKey();
							String tableName = manyToMany.tableName();
							String key = manyToMany.key();
							String dependKey = manyToMany.dependKey();
							Class<? extends BaseVO> type = manyToMany.type();
							BaseVO temp = type.newInstance();
							StringBuilder sb = new StringBuilder();
							sb.append("select * from ")
									.append(type.getMethod("getDBTableName",
											null).invoke(temp, null))
									.append(" where ")
									.append(type.getMethod("getPKName", null)
											.invoke(temp, null))
									.append(" in (select ").append(key)
									.append(" from ").append(tableName)
									.append(" where ").append(dependKey)
									.append(" = :foreignKey)");
							Map map = new HashMap();
							Field foreginField = target.getClass().getDeclaredField(
									foreignKey);
							foreginField.setAccessible(true);
							map.put("foreignKey", foreginField.get(target));
							List<BaseVO> list = searchByField(sb.toString(), map, type, false);
							field.set(target, list);
							return list;
						}
					}
				}
			}

		}
		return method.invoke(target, param);
	}
	
	private Annotation getAnnotation(Field field, Class<? extends Annotation> anno) {
		Annotation[] annos = field.getDeclaredAnnotations();
		for(Annotation a : annos)
			if(a.annotationType().equals(anno))
				return a;
		return null;
				
	}
}
