/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import com.ibm.agent.soliddb.catalog.RegionCatalog;
import rda.agent.client.AgentConnection;

/**
 * JDBCで対応するリージョンのレプリカ用solidDBにアクセスする．
 * エージェントエグゼキュータは，エージェント実行環境のサーバにて，
 * そのサーバのリージョンに対応するレプリカ用solidDBにJDBCで
 * 接続して，そのリージョンの顧客属性レコードの数を得る．最終的に
 * 全リージョンの顧客属性レコードの数を表示する．
 */
public class DBAccess implements AgentExecutor, Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = -8284826433740843048L;

    @Override
    /**
    * 各リージョンの顧客属性レコード数のコレクションを返す
    */
    public Object complete(Collection<Object> results) {
        return results;
    }

    @Override
    /**
    * エージェント実行環境のサーバにて，そのサーバのリージョンに対応する
    * レプリカ用solidDBにJDBCで接続して，顧客属性レコードの数を得る
    */
    public Object execute() {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            // このサーバのリージョン名を取得する
            AgentManager agentManager = AgentManager.getAgentManager();
            String regionName = agentManager.getRegionName();
			
            // JDBCドライバの接続パラメータにリージョン名を指定する
            Properties props = new Properties();
            props.put("region", regionName);
			
            // JDBC接続を得る
            con = DriverManager.getConnection("jdbc:ceta:rda", props);

            // 顧客属性レコード数を得るSQLを生成し，検索を行う．
            stmt = con.prepareStatement("select count(*) from useragent");
            ResultSet rs = stmt.executeQuery();

            // 顧客属性レコード数を得て，その数を処理結果とする
            rs.next();
            int numOfCustomers = rs.getInt(1);
            return numOfCustomers;
        } catch(Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
    public static void main(String[] args) {
        try {
            System.out.println("region names:" + RegionCatalog.getInstance("localhost:2809").getRegionNameList());
            AgentConnection con = AgentConnection.getInstance();
            AgentClient client = con.getConnection();
            
            DBAccess executor = new DBAccess();
            
            Object ret = client.execute(executor);
            Collection<Object> col = (Collection<Object>)ret;
            
            for(Object o : col) {
                int n = (Integer)o;
                System.out.println("num of agents = " + n);
            }
            
            con.returnConnection(client);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}