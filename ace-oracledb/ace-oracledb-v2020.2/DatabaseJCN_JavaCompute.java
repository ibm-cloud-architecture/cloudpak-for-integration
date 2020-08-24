package com.ibm.example;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbPolicy;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbNode.JDBC_TransactionType;
public class DatabaseJCN_JavaCompute extends MbJavaComputeNode {
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbPolicy myUserPolicy = getPolicy("UserDefined","{MyPolicyProject}:MyPolicies");
		MbOutputTerminal out = getOutputTerminal("out");
		MbMessage inMessage = inAssembly.getMessage();
	    // create new message
	    MbMessage outMessage = new MbMessage(inMessage);
	    MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,outMessage);
		try {
			// Obtain a java.sql.Connection using a JDBC Type4 datasource
	        // This example uses a Policy of type JDBCProviders called "MyJDBCPolicy" in Policy Project "MyPolicies"  
	        Connection conn = getJDBCType4Connection("{MyPolicies}:MyJDBCPolicy", JDBC_TransactionType.MB_TRANSACTION_AUTO);
	        // Example of using the Connection to create a java.sql.Statement 
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	        
	        MbElement dataElement = inMessage.getRootElement().getLastChild().getFirstChild();	        
	        // Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
	        stmt.executeUpdate("INSERT INTO EMPLOYEES (PKEY, FIRSTNAME, LASTNAME, COUNTRY) VALUES("
	        		+ dataElement.getFirstElementByPath("PrimaryKey").getValueAsString() + ","
	        		+ "'" + dataElement.getFirstElementByPath("FirstName").getValueAsString() + "',"
	        		+ "'" + dataElement.getFirstElementByPath("LastName").getValueAsString() + "',"
	        		+ "'" + dataElement.getFirstElementByPath("Country").getValueAsString() + "'"
       		+")");
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (SQLException sqx) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", sqx.toString(), null);
		}
		out.propagate(outAssembly);
	}
}