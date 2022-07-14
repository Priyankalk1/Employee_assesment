package com.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DatabaseTableFile.EmployeeDetails;

import com.DatabaseTableFile.EmployeeReports;

public class DBConnection {
	
		static String url = "jdbc:sqlserver://MAVCHN0522404\\SQLEXPRESS;databaseName=EmployeeData;integratedSecurity=true;encrypt=false;";
		
		public static void insertIntoDB(EmployeeDetails EmpDetail,boolean b)
		{
			try
			{
				String query="";
				if(b==true)
				{
					query = "Insert into EmployeeDetails values('"+EmpDetail.getEmployee_Id()+"','"+EmpDetail.getEmployee_Name()+"','"
							+EmpDetail.getEmail()+"','"+EmpDetail.getPhone_number()+"','"+EmpDetail.getHire_Date()+"','"
							+EmpDetail.getJob_Id()+"','"+EmpDetail.getSalary()+"','"+EmpDetail.getCommission_Pct()+"','"+EmpDetail.getManager_Id()+"','"+EmpDetail.getDepartment_Id()
							+"',"+"getDate()"+")";
				}
				else
				{
					query = "Insert into EmployeeDetails_Failed values('"+EmpDetail.getEmployee_Id()+"','"+EmpDetail.getEmployee_Name()+"','"
							+EmpDetail.getEmail()+"','"+EmpDetail.getPhone_number()+"','"+EmpDetail.getHire_Date()+"','"
							+EmpDetail.getJob_Id()+"','"+EmpDetail.getSalary()+"','"+EmpDetail.getCommission_Pct()+"','"+EmpDetail.getManager_Id()+"','"
							+EmpDetail.getDepartment_Id()+"',"+"getDate()"+")";
				}
				
				System.out.println("Insert Query into Database :"+query);
				
				try(Connection conn = DriverManager.getConnection(url))
					{
					PreparedStatement stmt = conn.prepareStatement(query);
					stmt.executeUpdate();
					conn.close();
					}
				catch(SQLException e)
				{
				System.out.println("Connection failed!");
				e.printStackTrace();
				}
				
			}
			catch(Exception e)
			{
			System.out.println("Connection failed!");
			e.printStackTrace();
			}
			
			System.out.println("Data Inserted Successfully");	
		}
		
		public static List<EmployeeReports> getReport(boolean b) {
			List<EmployeeReports> EmpReportList = new ArrayList<EmployeeReports>();
			try
			{
				String query="";
				if(b==true)
				{
					query = "SELECT  emp.employee_Id,emp.employee_Name,emp.email,empj.job_description,[dbo].[get_manager_name](emp.manager_Id) as ManagerName,dept.department_description\r\n FROM EmployeeDetails emp"
						+ "   LEFT JOIN Employee_Job empj ON emp.job_Id = empj.job_Id"
						+ "   Left Join Employee_Department dept on emp.department_Id=dept.department_Id"
						+ "  where emp.employee_Id not in(128,129,1001)";
				}
				else
				{
					query = "SELECT  emp.employee_Id,[dbo].[get_emp_name](emp.employee_Id) as EmployeeName,emp.email,empj.job_description,[dbo].[get_manager_name](emp.manager_Id) as ManagerName,dept.department_description\r\n FROM EmployeeDetails_Failed emp\r\n left join EmployeeDetails empf on emp.employee_Id=empf.employee_Id"
							+ "   LEFT JOIN Employee_Job empj ON emp.job_Id = empj.job_Id"
							+ "   Left Join Employee_Department dept on emp.department_Id=dept.department_Id"
							+ " 	where emp.employee_Id in(128,129,1001)";
				}
				try(Connection conn = DriverManager.getConnection(url))
					{
					
					Statement stmt = conn.createStatement();
					ResultSet result = stmt.executeQuery(query);
					while(result.next())
					{
						EmployeeReports empReport = new EmployeeReports();
						empReport.setEmployee_Id(result.getString(1));
						empReport.setEmployee_Name(result.getString(2));
						empReport.setEmail(result.getString(3));
						empReport.setJob_description(result.getString(4));
						empReport.setManager_Name(result.getString(5));
						empReport.setDepartment_Name(result.getString(6));
						EmpReportList.add(empReport);
					}
					
					conn.close();
					}
			
			}
			catch(SQLException e)
			{
			System.out.println("Connection failed!");
			e.printStackTrace();
			}
			return EmpReportList;
		} 

}


