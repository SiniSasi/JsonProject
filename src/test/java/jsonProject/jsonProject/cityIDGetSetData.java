package jsonProject.jsonProject;

public class cityIDGetSetData {

	private String id, name, coord;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "cityIDGetSetData [id=" + id + ", name=" + name + ", coord=" + coord + "]";
	}


}
