package oop;

public class UserTest {
	public void printUserType(User u) {
		u.printUserType();
	}

	public static void main(String[] args) {
		// Part 1
		User user = new User();
		User staff = new Staff();
		User editor = new Editor();

		UserTest ut = new UserTest();
		ut.printUserType(user);
		ut.printUserType(staff);
		ut.printUserType(editor);

		// Part 2
		// editor.approveReview();
		editor.postAReview();
		editor.saveWebLink();

	}
}