package secure;

public class AuthManager {
	public boolean hasReadPermission(String token) {
		return true;
	}
	public boolean hasWritePermission(String token) {
		return true;
	}
	public boolean correctClientToken(String token) {
		return true;
	}
}
