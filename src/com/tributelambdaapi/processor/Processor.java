package com.tributelambdaapi.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.tributelambdaapi.model.Martyr;

public class Processor {
	Map<String, Object> results = new HashMap<String, Object>();
	String bucketName = "";
	String key = "";
	public Map<String, Object> getAllMartyrs(String keyword, Context context) {
		results.put("martyr", getListFromFile(keyword,context) );
		return results;
	}
	
//	public Map<String, Object> getFilteredMartyrs(String keyword) {
//		results.put("martyr", getListFromFile(keyword));
//		return results;
//	}
	
	
	public Map<String,Object> getListFromFile(String keyword,Context context) {
		
		
		Map<String,Object> nameMap = new HashMap<String,Object>();
		
		AmazonS3 s3Client = new AmazonS3Client(
				DefaultAWSCredentialsProviderChain.getInstance());
		S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName,
				key));
		//String appendedNames = "";
		Set<Object> set = new HashSet<Object>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					s3object.getObjectContent()));
			
			int i = 0;
			while (true) {
				String line = reader.readLine();
				context.getLogger().log("In Processor.getListFromFile >> " + line);
				if (line == null) {
					break;
				}
				if("".equals(keyword) || keyword == null ||"*".equals(keyword)) {
					context.getLogger().log("In Processor.getListFromFile in first if" );
					if(i==0 ) {
						Martyr martyr = setMartyr(line,i);
						set.add(martyr);
						context.getLogger().log("In Processor.getListFromFile first URl i==0" );
						++i;
					} else if (i > 0 ) {
						Martyr martyr = setMartyr(line,i);
						set.add(martyr);
						context.getLogger().log("In Processor.getListFromFile first URl i>0" );
						++i;
					}
				} else {
					if(i ==0) {
						if (line.toLowerCase().contains(keyword)) {
							set.clear();
							nameMap.clear();
							context.getLogger().log("In Processor.getListFromFile keyword URl i=0 set clear >> " + line );
							Martyr martyr = setMartyr(line,i);
							set.add(martyr);
							context.getLogger().log("In Processor.getListFromFile keyword URl i=0" );
							++i;
						}
					} else if (i > 0 ) {
						if (line.toLowerCase().contains(keyword)) {
							Martyr martyr = setMartyr(line,i);
							set.add(martyr);
							context.getLogger().log("In Processor.getListFromFile keyword URl i>0" );
							++i;
						}
					}
				}
			}
		} catch (Exception ex) {
			context.getLogger().log("In Processor.getListFromFile Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		
		for(Object s: set) {
			context.getLogger().log(" Final set " + ((Martyr)s).getMartyrName());
			context.getLogger().log(" Final set " + ((Martyr)s).getMartyrYear());
			context.getLogger().log(" Final set " + ((Martyr)s).getMartyrImageName());
		}
		
		nameMap.put("martyr", set);
		return nameMap;
	}
	
	public Martyr setMartyr(String line,int i) {
		StringTokenizer st = new StringTokenizer(line,"~");
		String martyrName=null;
		String martyrYear=null;;
		String martyrImage=null;
		while(st.hasMoreTokens()) {
			martyrName = st.nextToken();
			martyrYear = st.nextToken();
			martyrImage = st.nextToken();
		}
		Martyr martyr = new Martyr();
		martyr.setMartyrId(i);
		martyr.setMartyrName(martyrName);
		martyr.setMartyrYear(martyrYear);
		martyr.setImage(martyrImage);
		
		return martyr;
	}
}
