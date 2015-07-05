package util.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pager {
	private List<Map> data;
	private int begin;
	private int end;

	public Pager(int begin, int end) {
		data = new ArrayList<Map>();
		this.begin = begin;
		this.end = end;
	}

	public List<Map> getData() {
		return data;
	}

	public void setData(List<Map> data) {
		this.data = data;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
