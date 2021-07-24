package dto;

public class ErrorResponse {
	private String message;
	public String getMessage() {
		return message;
	}
	public ErrorResponse(String message) {
		super();
		this.message = message;
	}
}
