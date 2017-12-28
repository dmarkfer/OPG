package serverShell;

import java.util.HashMap;

public class Parser {

	public Parser() {}
	
	public static HashMap<String, String> parse(String line){
		if (!line.contains("#")) {
			return null;
		}
		String cutLine=line.substring(line.indexOf("##")+2, line.length());
		String[] arguments=cutLine.split("##");
		String[] tempArgs;
		HashMap<String, String> parsed=new HashMap<>();
		
		for (String string : arguments) {
			tempArgs=string.split("=");
			parsed.put(tempArgs[0], tempArgs[1]);
		}
		return parsed;
	}
}
