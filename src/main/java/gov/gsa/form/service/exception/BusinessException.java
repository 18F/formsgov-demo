package gov.gsa.form.service.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4764227254370539801L;

	public BusinessException(){}

	public BusinessException(String message)
	{
		super(message);
	}

	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BusinessException(Throwable cause)
	{
		super(cause);
	}
}
