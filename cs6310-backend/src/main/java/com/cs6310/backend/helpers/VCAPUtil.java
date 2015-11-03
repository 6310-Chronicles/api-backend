package com.cs6310.backend.helpers;

import com.ibm.json.java.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;

public class VCAPUtil {
	
	private static final String VCAP_ENV_VAR = "VCAP_SERVICES";
        private static Logger LOGGER = Logger.getLogger(VCAPUtil.class);

        public VCAPUtil() {
                // TODO Auto-generated constructor stub
	}
	
	public static JSONObject getVcapServices() {
        String envServices = System.getenv(VCAP_ENV_VAR);
        
        if (envServices == null) {
        	return null;
        }
        
        JSONObject sysEnv = null;
        try {        	
        	 sysEnv = JSONObject.parse(envServices);
        } catch (IOException e) {
        	// Do nothing, fall through to defaults
        	LOGGER.error("Error parsing VCAP_SERVICES", e);
        }
        
        return sysEnv;
    }
	
	

}
