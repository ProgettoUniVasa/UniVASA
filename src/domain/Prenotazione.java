package domain;

public class Prenotazione {
	
	private Long id;
	private enum tipologia {
		presenza,
		online
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	};

}