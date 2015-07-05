package util.db;

import java.util.List;
import java.util.Map;

public class Query {
	
	private StringBuilder sb;
	
	public Query(BaseVO v) {
		sb = new StringBuilder();
		String tableName = v.getDBTableName();
		
		sb.append("select ");
	}
	
	public Query join(BaseVO vo, Map valueMap, boolean out) {
		return null;
	}
	
	public List<Map> query(Map valueMap){
		return null;
	}
}
