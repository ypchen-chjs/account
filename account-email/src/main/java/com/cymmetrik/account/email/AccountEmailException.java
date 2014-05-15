package com.cymmetrik.account.email;

import javax.mail.MessagingException;

public class AccountEmailException extends Exception {

	public AccountEmailException(String string, MessagingException e) {
		super(string, e);
	}

}
