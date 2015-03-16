package com.mercury.chat.common.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Lists;

public class TestStatement extends Statement {

	static final Logger logger = LogManager.getLogger(TestStatement.class);
	private ApplicationContext context;
	private Statement baseStatement;
	private Description description;

	private DataSource dataSource;

	public TestStatement(ApplicationContext context, Statement baseStatement, Description description) {
		super();
		this.context = context;
		this.baseStatement = baseStatement;
		this.description = description;
	}

	@Override
	public void evaluate() throws Throwable {
		before();

		this.getBaseStatement().evaluate();

		after();
	}

	private void after() {
		//LOGGER.info("==evaluate after==");

	}

	private void before() throws SQLException, URISyntaxException, FileNotFoundException {
		DataPrepare dataPrepare = this.getDescription().getAnnotation(DataPrepare.class);
		if(dataPrepare != null){
			if (dataPrepare.dbType() == DbType.H2) {
				executeH2Scripts();
			} else if (dataPrepare.dbType() == DbType.MONGODB) {

			}
			logger.info("dbType:" + dataPrepare.dbType().name());
			logger.info("schema:" + Arrays.asList(dataPrepare.schema()).toString());
		}


	}
		private Server server;   
	  
	    public void startServer() {   
	        try {   
	            System.out.println("正在启动h2...");   
	            server = new Server();
	            server.runTool( new String[] { "-web", "-webAllowOthers","-webPort","8090" });
	        } catch (SQLException e) {   
	            throw new RuntimeException(e);   
	        }   
	    }   
	    
	    public void stopServer() {   
	        if (server != null) {   
	            System.out.println("正在关闭h2...");   
	            server.stop();   
	            System.out.println("关闭成功.");   
	        }   
	    }   

	private void executeH2Scripts() throws URISyntaxException, FileNotFoundException, SQLException {
		startServer();
		DataSource basicDataSource = (BasicDataSource)this.context.getBean("dataSource");
		DataPrepare dataPrepare = this.getDescription().getAnnotation(DataPrepare.class);
		URL url = dataPrepare.domainClass().getResource(".");
		executeDDL(basicDataSource, url, ".ddl");
		executeDDL(basicDataSource, url, ".dml");
//		this.stopServer();
	}

	private void executeDDL(DataSource basicDataSource, URL url, String regex)
			throws URISyntaxException, FileNotFoundException, SQLException {
		Collection<String> files = listFiles(url, regex);
		Connection connection = basicDataSource.getConnection();
		InputStreamReader reader = null;
		try {
			if (files != null) {
				for (String file : files) {
					String filePath = url.getPath()  + file;
					FileInputStream input = new FileInputStream(filePath);
					reader = new InputStreamReader(input,"UTF-8");
					ScriptRunner runner = new ScriptRunner(connection);
					runner.setAutoCommit(true);
					runner.runScript(reader);
					reader.close();
				}
			}
		} catch (Exception e) {
			logger.error("executeDDL Exception", e);
		}finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("executeDDL IOException", e1);
				}
			}
			connection.close();
		}
	}

	private Collection<String> listFiles(URL url, String regex) throws URISyntaxException {
		Collection<String> fileNames = Lists.newArrayList();
		if (url != null) {
			final File file = new File(url.toURI());
			String[] names = file.list();
			for(String name : names){
				if(name.endsWith(regex)){
					fileNames.add(name);
				}
			}
		}
		return fileNames;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public Statement getBaseStatement() {
		return baseStatement;
	}

	public Description getDescription() {
		return description;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
