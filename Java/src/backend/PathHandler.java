package backend;

public class PathHandler {
	public static String RESOURCE_PATH = "Java/res/";
	
	public PathHandler() {
		
	}
	
	public String getPathToResource(String filename) {
		String workingDirectory = System.getProperty("user.dir");
		int index = workingDirectory.indexOf("Million-Song-Data-Vis");
		String localPath = workingDirectory.substring(0,index);
		String out = localPath + "Million-Song-Data-Vis/" + RESOURCE_PATH + filename;
		
		return out;
	}
	
	public static void main(String[] args) {
		PathHandler ph = new PathHandler();
		System.out.println(ph.getPathToResource("MillionSongSubset.db"));
	}

}
