package recognition02;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;

public class Recognition02_main {

	public static void main(String[] args) {
		Recognition02_lib rlib = new Recognition02_lib();
		DetectedFaces result = 	rlib.getResult("img/97550.jpg");
		rlib.getJson(result);
		
	}


}