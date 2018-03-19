package model.connection;

import javax.swing.JOptionPane;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class User implements UserInfo, UIKeyboardInteractive {

	@Override
	public String[] promptKeyboardInteractive(String arg0, String arg1, String arg2, String[] arg3, boolean[] arg4) {
		return null;
	}

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return false;
	}

	@Override
	public boolean promptPassword(String arg0) {
		return false;
	}

	@Override
	public boolean promptYesNo(String message) {
		Object[] options = {"yes", "no"}; 
		int foo = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		return foo==0;
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
