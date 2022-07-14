package com.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;


import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.DatabaseConnection.DBConnection;
import com.DatabaseTableFile.EmployeeDetails;

import com.DatabaseTableFile.EmployeeReports;

public class EmployeeMain {
		
	private static void insertValueIntoDB(String nextLine) {
		nextLine = nextLine.replaceAll(",,",",-,"); 		 
		if(nextLine.charAt(nextLine.length()-1)==',') 
		{ 
			nextLine = nextLine+"-";
		}			
		String records[] = nextLine.split(",");
		EmployeeDetails success_record = new EmployeeDetails();
		EmployeeDetails failed_record = new EmployeeDetails();
		success_record.setEmployee_Id(records[0]);
		failed_record.setEmployee_Id(records[0]);
				
		//Name
		if(isNameOnlyAlphabet(records[2]))
		{
			if(isNull(records[1]))
				{
						success_record.setEmployee_Name(records[2]);;
						failed_record.setEmployee_Name("-");
				}
			else
				{
						success_record.setEmployee_Name(records[1]+" "+records[2]);
						failed_record.setEmployee_Name("-");
				}
		}
		else
			{
				failed_record.setEmployee_Name(records[1]+" "+records[2]);
			}
		//Email
		String email = isValidEmail(records[3]);
		if(email.equalsIgnoreCase("false"))
		{
			failed_record.setEmail(records[3]);
			success_record.setEmail("-");
		}
		else
		{
			success_record.setEmail(email);
			failed_record.setEmail("-");
		}
					
		//PhoneNumber
		if(isValidPhoneNumber(records[4]))
		{
			success_record.setPhone_number(records[4]);
			failed_record.setPhone_number("-");
		}
		else
		{
			success_record.setPhone_number("-");
			failed_record.setPhone_number(records[4]);
		}
		//HireDate
		if(isValidDate(records[5]))
		{
			success_record.setHire_Date(records[5]);
			failed_record.setHire_Date("-");
		}	
		else
		{
			success_record.setHire_Date("-");
			failed_record.setHire_Date(records[5]);
		}
		//JOBID
		if(isNull(records[6]))
		{
			success_record.setJob_Id("-");
			failed_record.setJob_Id("-");
		}
		else
		{
		success_record.setJob_Id(records[6]);
		failed_record.setJob_Id(records[6]);
		}
		//Salary
		if(isValidSalary(records[7]))
		{
		success_record.setSalary(records[7]);
		failed_record.setSalary("-");
		}
		else
		{
		failed_record.setSalary(records[7]);
		success_record.setSalary("-");
		}
		//COMMISSION_PCT
		success_record.setCommission_Pct(records[8]);
		failed_record.setCommission_Pct("-");
		//MANAGER_ID
		if(isNull(records[9]))
		{
		success_record.setManager_Id(records[0]);
		failed_record.setManager_Id(records[0]);
		}
		else
		{
		success_record.setManager_Id(records[9]);
		failed_record.setManager_Id(records[9]);
		}
		//DEPARTMENT_ID
		if(isNull(records[10]))
		{
		success_record.setDepartment_Id("-");
		failed_record.setDepartment_Id("-");
		}
		else
		{
		success_record.setDepartment_Id(records[10]);
		failed_record.setDepartment_Id(records[10]);
		}
		if(!(failed_record.getEmployee_Name().equalsIgnoreCase("-") && failed_record.getEmail().equalsIgnoreCase("-")  && 
		failed_record.getPhone_number().equalsIgnoreCase("-") && failed_record.getHire_Date().equalsIgnoreCase("-")
		&& failed_record.getSalary().equalsIgnoreCase("-")))
		{
			DBConnection.insertIntoDB(failed_record, false);
		}
		
			DBConnection.insertIntoDB(success_record, true);
		}


	
	public static boolean isNull(String value)
	{
		if(value.trim().equalsIgnoreCase("")||value.equals("null")||value.equals(null)||value.trim().equalsIgnoreCase("-"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNameOnlyAlphabet(String value) 
	{
	    return ((!value.equals(""))
	            && (value != null)
	            && (value.matches("^[a-zA-Z ]*$")));
	}
	
	public static String isValidEmail(String value)
   {
		if(isNull(value))
		{
			return "false";
		}
		else
		{
			if(value.length()<=50)
			{
				if(isNameOnlyAlphabet(value))
				{
					return value+"@abc.com";
				}
				
				String emailRegex = "^(.+)@(.+)$";                        
		        Pattern pat = Pattern.compile(emailRegex);
		        if(pat.matcher(value).matches())
		        {
		        	return value;
		        }
		        else
		        {
		        	return "false";
		        }
			}
			else
			{
				return "false";
			}
		}
   }
	
	public static boolean isValidPhoneNumber(String value)
	{
		if(isNull(value))
		{
			return false;
		}
		else
		{
			if(value.length()==12)
			{
			
				if(value.charAt(3)=='.' && value.charAt(7)=='.')
				{
					String phoneRegex ="\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}";
					Pattern pat = Pattern.compile(phoneRegex);
					return pat.matcher(value).matches();
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}
	
	public static boolean isValidDate(String value)
   {
       String regex = "([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-((19|20)\\d\\d)";
       
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher((CharSequence)value);
       return matcher.matches();
   }
	
	public static boolean isValidSalary(String value)
   {
		if(isNull(value))
		{
			return false;
		}
		else
		{
		String regex = "^([0-9]*)(.[[0-9]+]?)?$";
		Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher((CharSequence)value);
       return matcher.matches();
		}
   }
	


public static void main(String args[]) throws FileNotFoundException
	{
			File file = new File("C:\\Users\\priyankalk\\OneDrive\\JavaProgram\\Employee_Assignment\\csvFile\\employee.csv");
			File success_data = new File("C:\\Users\\priyankalk\\OneDrive\\JavaProgram\\Employee_Assignment\\csvFile\\Success_data.csv");
			File failed_data = new File("C:\\Users\\priyankalk\\OneDrive\\JavaProgram\\Employee_Assignment\\csvFile\\Failed_data.csv");
			try 	{
						
						Scanner sc = new Scanner(file);
						sc.nextLine();
						while (sc.hasNextLine())
						{
							insertValueIntoDB(sc.nextLine());
						}
				
					List<EmployeeReports> success_file = DBConnection.getReport(true);
					
					BufferedWriter out = new BufferedWriter(new FileWriter(success_data));
					out.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
					out.newLine();
					for(EmployeeReports EmpReport : success_file)
						{
							out.write(EmpReport.getEmployee_Id()+","+EmpReport.getEmployee_Name()+","+EmpReport.getEmail()+","+EmpReport.getJob_description()+","+EmpReport.getDepartment_Name()+","+EmpReport.getManager_Name());
							out.newLine();
						}
					out.close();
					System.out.println("Wrote details into success_file and generated it..");
				
				
					List<EmployeeReports> failed_file = DBConnection.getReport(false);
					out = new BufferedWriter(new FileWriter(failed_data));
					out.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
					out.newLine();
					for(EmployeeReports EmpReport : failed_file)
						{
							out.write(EmpReport.getEmployee_Id()+","+EmpReport.getEmployee_Name()+","+EmpReport.getEmail()+","+EmpReport.getJob_description()+","+EmpReport.getDepartment_Name()+","+EmpReport.getManager_Name());
							out.newLine();
						}
					out.close();
					System.out.println("Wrote details into Failed_file and generated it..");
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		  }
}




