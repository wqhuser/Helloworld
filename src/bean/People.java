package bean;

import util.annotation.Foreign;

public class People {
	private String id;
	private String name;
	private String room_id;
	
	@Foreign(foreginKey = "room_id")
	private Room room;
}
