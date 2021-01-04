package gov.gsa.form.service.exception;

public class SystemException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1619580616459081393L;

	protected final String message;

	public SystemException(Throwable t) {
		super(t);
		message = t.getMessage();
	}

	public SystemException(String message) {
		super();
		this.message = message;
	}

	public SystemException(String message, Throwable t) {
		super(message, t);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
