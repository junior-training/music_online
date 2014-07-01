package com.music_online.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	 /** *//**
     * 获得数据库连接
     * @param driverClassName    连接数据库用到的驱动类的类名
     * @param dbURL        数据库的URL
     * @param userName    登陆数据库的用户名
     * @param password    登陆数据库的密码
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(String driverClassName,
            String dbURL, String userName, String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;

        // 加载连接数据库的驱动类
        Class.forName(driverClassName);
        // 用用户名、密码连接数据库
        con = DriverManager.getConnection(dbURL, userName, password);
        if(!con.isClosed())
            System.out.println("Succeeded connecting to the Database!");

        return con;
    }

    /**
     * 获得MySQL数据库的连接
     */
    public static Connection getMySQLConnection(String dricerClassName,
            String serverHost, String serverPort, String dbName,
            String userName, String password) throws ClassNotFoundException,
            SQLException {
        // 如果没有提供这些连接参数，则用默认值
        if (dricerClassName == null) {
            dricerClassName = "com.mysql.jdbc.Driver";
        }
        if (serverHost == null) {
            serverHost = "127.0.0.1";
        }
        if (serverPort == null) {
            serverPort = "3306";
        }
        // 构建访问SQL Server数据库的URL
        String dbURL = "jdbc:mysql://" + serverHost + ":" + serverPort
                + "/" + dbName+"?useUnicode=true&characterEncoding=utf-8";
        return getConnection(dricerClassName, dbURL, userName, password);
    }

   /* public static void main(String[] args)throws Exception {
	 Connection conn =	DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "123456");
     if(conn == null) {
    	 System.out.println("创建连接失败！");
     }else{
         System.out.println("创建连接成功！");    	 
     }
     
    }*/
    
    
}
