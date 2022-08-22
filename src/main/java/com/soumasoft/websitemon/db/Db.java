package com.soumasoft.websitemon.db;

/**
 * 
 * DB constants class
 * 
 * @author Thomas Juen, SoumaSoft
 *
 */
public class Db {

	public static class WEBSITES {
		public static final String TABLENAME = "websites";
		public static final String COL_ID = "id";
		public static final String COL_URL = "url";
		public static final String COL_EXPECTED_RETURNCODE = "expected_returncode";
	}
	
	public static class SETTINGS {
		public static final String TABLENAME = "settings";
		public static final String COL_NAME = "name";
		public static final String COL_VALUE = "value";
	}
	
}
