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

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Timeout;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class TestStatement2 extends Statement {

	static final Logger logger = LogManager.getLogger(TestStatement2.class);
	private ApplicationContext context;
	private Statement baseStatement;
	private Description description;

	private DataSource dataSource;

	private Server server;
	private MongodExecutable mongodExe;
    private MongodProcess mongod;
//    private Mongo mongo;

	public TestStatement2(ApplicationContext context, Statement baseStatement, Description description) {
		super();
		this.context = context;
		this.baseStatement = baseStatement;
		this.description = description;
	}

	@Override
	public void evaluate() throws Throwable {
		before();
		//TODO back up h2
		this.getBaseStatement().evaluate();
		after();
		//TODO restore h2
	}

	protected void before() throws Exception {
			DataPrepare2 dataPrepare = this.getDescription().getAnnotation(DataPrepare2.class);
			if (dataPrepare != null) {
				Collection<DbType> dbTypes = Lists.newArrayList(dataPrepare.dbTypes());
				if (dbTypes.contains(DbType.H2)) {
					executeH2Scripts();
				}
				if (dbTypes.contains(DbType.MONGODB)) {
					MongodStarter runtime = MongodStarter.getDefaultInstance();
			    	mongodExe = runtime.prepare(new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net(27017, Network.localhostIsIPv6())).timeout(new Timeout(5000L)).build());
			        mongod = mongodExe.start();
	//		        mongo = new MongoClient("localhost", 12345);
				}
	//			LOGGER.info("dbType:" + dataPrepare.dbType().name());
				logger.info("schema:" + Arrays.asList(dataPrepare.schema()).toString());
			}
	
		}

	protected void after() {
		if (this.mongod != null) {
			logger.info("Stop mongo..");
            this.mongod.stop();
            this.mongodExe.stop();
        }
	}

	private void executeH2Scripts() throws URISyntaxException, FileNotFoundException, SQLException {
//		startServer();
		DataSource basicDataSource = (BasicDataSource)this.context.getBean("dataSource");
		DataPrepare2 dataPrepare = this.getDescription().getAnnotation(DataPrepare2.class);
		URL url = null;
		for (Class clazz : dataPrepare.domainClasses()) {
			url = clazz.getResource("./");
			executeDDL(basicDataSource, url, "h2", ".ddl");
			executeDDL(basicDataSource, url, "h2", ".dml");
		}
		
//		this.stopServer();
	}

	private void executeDDL(DataSource basicDataSource, URL url, String regex, String endRegex)
			throws URISyntaxException, FileNotFoundException, SQLException {
		Collection<String> files = listFiles(url, regex, endRegex);
		Connection connection = basicDataSource.getConnection();
		InputStreamReader reader = null;
		try {
			for (String file : files) {
				String filePath = url.getPath() + file;
				FileInputStream input = new FileInputStream(filePath);
				reader = new InputStreamReader(input, "UTF-8");
				ScriptRunner runner = new ScriptRunner(connection);
				runner.setAutoCommit(true);
				runner.runScript(reader);
				reader.close();
			}
		} catch (Exception e) {
			logger.error("executeDDL Exception", e);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("executeDDL IOException", e1);
				}
			}
			connection.close();
		}
	}

	private Collection<String> listFiles(URL url, String regex, String endRegex) throws URISyntaxException {
		Collection<String> fileNames = Lists.newArrayList();
		if (url != null) {
			final File file = new File(url.toURI());
			String[] names = file.list();
			for (String name : names) {
				if (name.contains(regex) && name.endsWith(endRegex)) {
					fileNames.add(name);
				}
			}
		}
		return fileNames;
	}

	//Use for H2 debug
	private void startServer() {
		try {
			System.out.println("正在启动h2...");
			server = new Server();
			server.runTool(new String[] { "-web", "-webAllowOthers", "-webPort", "8090" });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unused")
	private void stopServer() {
		if (server != null) {
			System.out.println("正在关闭h2...");
			server.stop();
			System.out.println("关闭成功.");
		}
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
