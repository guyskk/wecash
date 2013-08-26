package org.wecash.io.model;

public interface IParser {
	/*static String ip = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
	static String mac = "\\w{4}\\.\\w{4}\\.\\w{4}";
	static String space = "\\s+";
	static String space_or_not = "\\s*";
	static String any = ".*";
	static String word = "\\w+";*/
	
	static String ip = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
    static String mask_as_bytes = ip;
	static String mask_as_size = "[0-9]+";
	
	static String mac = "\\w{4}\\.\\w{4}\\.\\w{4}";
	static String mac_raw = "\\w{12}";
	static String mac_oct = "(([0-9A-Fa-f]{2}):){5}[0-9A-Fa-f]{2}"; // 00:15:17:6d:56:96
	static String mac_nokia = "(([0-9A-Fa-f]{1,2}):){5}[0-9A-Fa-f]{1,2}"; // 0:15:17:6d:56:96

    static String mac_oct2 = "([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}"; // 00:15:17:6d:56:96

	
	static String as = "[0-9]+";
	
	static String space = "\\s+";
	static String space_or_not = "\\s*";
	static String non_space = "\\S+";
	static String any = ".*";
	static String no_greedy_any = ".*?";
    
	static String label = "[\\w| |\\.|'|_|&|?|!|\\-]+";
    static String path  = "[\\w| |'|_|&|?|!|\\-\\/|\\\\|]+";
	static String word = "\\w+";
    static String word_list = "[\\w|,]+";
	static String number = "[\\+|\\-]?[\\d|\\.]+";
	static String slash = "\\/";
	static String antislash = "\\\\";
    
	static String machine = "\\w+[\\-\\w+]*~*";
    static String machine_fullname = "[\\w|\\d|\\-|\\/|\\.|~]+";
	
    static String machinedns = "[\\|\\w|\\d]*";
	
	//static String intrface1 = "[\\w|\\d]+\\/\\d";
	//static String intrface2 = "[\\w|\\d]+\\/\\d";
	//static String intrface = "[\\w|\\d]+[\\/\\d]*[\\-\\w+]*"; // eth1/1 bla-truc
	static String intrface = "[\\w|\\d|\\-|\\/|\\.|\\:]+";
	static String intrface_containing_spaces = intrface + space + "\\S+";//"\\S+\\s+[\\S+\\s+s]*\\S+";
	
	static String port_slot = "\\w*\\d+\\/\\d+";
	static String port_slot_containing_spaces = "\\d+\\/" + space_or_not + "\\d+";
	
	static String port_slot_or_vlan = "\\w*\\d*\\/?\\d*";
	
	static String domain = "[\\w|\\d|\\-]+[\\.\\w|\\d|\\-]*";
	
	static String start_with = "^";
    static String end_with = "$";
}
