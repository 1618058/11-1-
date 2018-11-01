package recognition02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions.Builder;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectFacesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;





public class Recognition02_lib {
	VisualRecognition service;
	IamOptions iamOptions = null;
	public Recognition02_lib() {
		service = new VisualRecognition("2018-03-19");
		
		IamOptions iamOptions = new IamOptions.Builder()
				  .apiKey("Y_DgzKGp6VUf0dyIgKpuKnBlTL8lDUUYiVuBJra2Jk6D")
				  .build();
				service.setIamCredentials(iamOptions);
	}
	public  DetectedFaces getResult(String image) {
		DetectFacesOptions detectFacesOptions = null;
		try {
			detectFacesOptions = new DetectFacesOptions.Builder()
			  .imagesFile(new File(image))
			  .build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DetectedFaces result = service.detectFaces(detectFacesOptions).execute();
		//ystem.out.println(result);
		return result;
	}
public void getJson(DetectedFaces result) {
	//===json
	ObjectMapper mapper = new ObjectMapper();
	JsonNode node = null;
	try {
		node = mapper.readTree(String.valueOf(result));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	int age_min = node.get("images").get(0).get("faces").get(0).get("age").get("min").asInt();
	int age_max = node.get("images").get(0).get("faces").get(0).get("age").get("max").asInt();
	double age_score = node.get("images").get(0).get("faces").get(0).get("age").get("score").doubleValue();
	int gender;
	if(node.get("images").get(0).get("faces").get(0).get("gender").get("gender").toString() == "MALE") {
		gender = 0;
	}else{
		gender = 1;
	}
	double gender_score = node.get("images").get(0).get("faces").get(0).get("gender").get("score").doubleValue();
	mySQL mysql = new mySQL();
	mysql.updateImage(age_min,age_max,age_score,gender,gender_score);
	
	 System.out.println("age_min : " + age_min);
	
}

}
