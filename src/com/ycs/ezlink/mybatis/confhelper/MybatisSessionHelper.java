package com.ycs.ezlink.mybatis.confhelper;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * This is
 * Usage 
 * SqlSession session = <ThisClass>.openSession();<br/>
 * MyMapper specificMapper = session.getMapper(Clazz MyMapper.class); <br/>
 * specificMapper.crudMethods(...);<br/>
 * session.close();<br/>
 * 
 * @author Samarjit
 *
 */
public class MybatisSessionHelper {
	private static SqlSessionFactory sqlSessionFactory = null;
	
	 
	
	private MybatisSessionHelper() {
		String resource = "com/ycs/ezlink/mybatis/confhelper/configuration.xml";
		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader
//					 MybatisSessionHelper.class.getResourceAsStream("/org/jbpm/samarjit/dao/configuration.xml")
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MybatisSessionHelper eINSTANCE = new MybatisSessionHelper();
	
	public void addMapper(Class<?> mapperClass){
		sqlSessionFactory.getConfiguration().addMapper(  mapperClass);
	}
	
	public SqlSession openSession(){
		return sqlSessionFactory.openSession();
	}
	
	public SqlSession openSessionWithoutLogging(){
		sqlSessionFactory.setSessionLogging(false);
		return sqlSessionFactory.openSession();
	}
	
	public static class PrintType {
		public static String sqlToString(SqlSession session, String mappedStatementName){
			return "==>  Executing:"+session.getConfiguration().getMappedStatement(mappedStatementName).getBoundSql(null).getSql().replaceAll("[\\r\\n]", "").replace("\t", " ");
		}
		public static String paramValuesToString(SqlSession session, String mappedStatementName, Object parameterObject) throws SQLException {
			String ret = "";
			MappedStatement mappedStatement = session.getConfiguration().getMappedStatement(mappedStatementName);
			BoundSql boundSql = session.getConfiguration().getMappedStatement(mappedStatementName).getBoundSql(parameterObject);
			Configuration configuration = mappedStatement.getConfiguration();
			final TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();

			ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			if (parameterMappings != null) {
				MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
				for (int i = 0; i < parameterMappings.size(); i++) {
					ParameterMapping parameterMapping = parameterMappings.get(i);
					if (parameterMapping.getMode() != ParameterMode.OUT) {
						Object value;
						String propertyName = parameterMapping.getProperty();
						PropertyTokenizer prop = new PropertyTokenizer(propertyName);
						if (parameterObject == null) {
							value = null;
						} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
							value = parameterObject;
						} else if (boundSql.hasAdditionalParameter(propertyName)) {
							value = boundSql.getAdditionalParameter(propertyName);
						} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
							value = boundSql.getAdditionalParameter(prop.getName());
							if (value != null) {
								value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
							}
						} else {
							value = metaObject == null ? null : metaObject.getValue(propertyName);
						}
						TypeHandler typeHandler = parameterMapping.getTypeHandler();
						if (typeHandler == null) {
							throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
						}
						ret += (/*"" + i + 1 + " " +*/ value + "(" + parameterMapping.getJavaType().getSimpleName()+"), ");
					}
				}
			}
			if(ret.length() >2)
			ret = ret.substring(0, ret.length()-2);
			return "==> Parameters:"+ret;
		}
	}
}
