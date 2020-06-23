/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itexps.costcowebautomation2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *     THIS HAS ALL THE FUNCTIONALITY AND WE WILL CALL 
 * @author shruti
 */
public class FileUtil {
    public static LoginVO   getLoginData(){
    // LoginVO login=new LoginVO(); 
    LoginVO login=null;   //i changed login to loginChange//
    try{
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\shruti\\Documents\\NetBeansProjects\\CostcoProj\\costco.xlsx"));

       Workbook workbook = new XSSFWorkbook(inputStream);
       //getting first worksheet
       Sheet firstSheet = workbook.getSheetAt(0);
       //get first row
       Row r =  firstSheet.getRow(1);
       Cell c = r.getCell(0); //username value
       String username = c.getStringCellValue();
       c = r.getCell(1); //username value
       String password = c.getStringCellValue();
        
       login = new LoginVO(username, password);
        
       System.out.println(login);
       inputStream.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    return login;
        
    }
    public static void main(String arg[])
    {
        FileUtil.getLoginData();
    }
}

    
    
    
    
    
    
    
    
   