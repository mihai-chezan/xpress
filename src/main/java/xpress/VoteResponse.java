/**
 * 
 */
package xpress;


/**
 * @author mcq
 *
 */
public class VoteResponse {
	private final long id;
	private final String status;
	public VoteResponse(long id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
}
