package domain;

public class Turnazione {

	private Long id;
	private enum fascia {
		mattina,
		pomeriggio,
		sera
	};
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	};
	
}
