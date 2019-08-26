package co.grandcircus.HelpMeApp.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import co.grandcircus.HelpMeApp.Dao.MessageDao;
import co.grandcircus.HelpMeApp.Dao.OrgDao;

@Component
public class AutoEmail {

	@Value("${sendGrid_KEY}")
	private String SENDGRID_KEY;
	@Value("${email_ADDRESS}")
	private String EMAIL_ADDRESS;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private OrgDao orgDao;

	public void sendMailFromUserToOrg(User user, Org org, String userContent) throws Exception {

		Mail mail = getMail(user, org);
		saveMessageAndOrg(user, org);
		SendGrid sg = new SendGrid(SENDGRID_KEY);
		Request request = new Request();

		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	public Mail getMail(User user, Org org) {
		Email from = new Email(getUserEmail(user));
		String subject = getUserSubject(user);
		Email to = new Email(EMAIL_ADDRESS);
		Content content = new Content("text/plain", getUserTotalContent(user, org));
		return new Mail(from, subject, to, content);
	}

	public String getUserEmail(User user) {
		String userEmail = user.getFirstName().toLowerCase().substring(0, 1) + user.getLastName().toLowerCase()
				+ "@HelpMeApp.com";
		return userEmail;
	}

	public String getUserSubject(User user) {
		String subject = "Help Requested with " + user.getSelection() + " from " + getUserFullName(user) + " from "
				+ user.getCity();
		return subject;
	}

	public String getUserFullName(User user) {
		String fullName = user.getFirstName() + " " + user.getLastName();
		return fullName;
	}

	public String getUserTotalContent(User user, Org org) {
		String content = getUserTextContent(user, org) + getOrgToUserLink(org, user);
		return content;
	}

	public String getUserTextContent(User user, Org org) {
		String content = "Hello, I'm currently living in " + user.getCity() + " and I'm reaching out to "
				+ org.getName() + " because I'm looking for help with " + user.getSelection().toLowerCase() + ". ";
		return content;
	}

	public String getOrgToUserLink(Org org, User user) {
		String link = "To reply, please follow this link: " + "http://localhost:8080/org-message-detail?apiId="
				+ wrapApiIdToHtml(org.getApiId()) + "&userId=" + user.getId();
		return link;
	}

	public String wrapApiIdToHtml(String apiId) {
		String wrappedApi = apiId.replaceAll(" ", "_");
		return wrappedApi;
	}

	public void saveMessageAndOrg(User user, Org org) {
		Message message = getMessage(user, org);
		messageDao.save(message);
		orgDao.save(org);
	}

	public Message getMessage(User user, Org org) {
								//		Long userId, String userName, String orgId, String orgName, String apiId, String issue, Date date,
								//		String from, String to, String subject, String content
		Message message = new Message(user.getId(), getUserFullName(user), org.getOrgId(), org.getName(),
				org.getApiId(), user.getSelection(), getDate(), getUserFullName(user), org.getName(),
				getUserSubject(user), getUserTextContent(user, org));
		return message;
	}

	public LocalDateTime getDate() {
		LocalDateTime dateObj = LocalDateTime.now();
		return dateObj;
	}

	public Map<String, String> getUserMessageHistory(User user) {
		List<Message> messageHistory = messageDao.findAllByUserId(user.getId());
		Map<String, String> orgs = new HashMap<>();
		for (Message each : messageHistory) {
			orgs.put(each.getApiId(), each.getOrgName());
		}
		return orgs;
	}

	public void sendMailFromOrgToUser(Message orgMessage) {
		calcOrgResponseTime(orgMessage);
		messageDao.save(orgMessage);
	}

	public Message createOrgMessage(Long messageId, String content) {
		Message userMessage = messageDao.findByMessageId(messageId);
		String subject = "Re: " + userMessage.getIssue();
		String trimmedContent = content.trim();
		Message message = new Message(userMessage.getUserId(), userMessage.getUserName(), userMessage.getOrgId(),
				userMessage.getOrgName(), userMessage.getApiId(), userMessage.getIssue(), getDate(),
				userMessage.getTo(), userMessage.getFrom(), subject, trimmedContent);
		return message;
	}

	public Map<Long, String> getOrgMessageHistory(String apiId) {
		List<Message> messageHistory = messageDao.findAllByApiId(apiId);
		Map<Long, String> users = new HashMap<>();
		for (Message each : messageHistory) {
			users.put(each.getUserId(), each.getUserName());
		}
		return users;
	}

	public String getLastMessageIssue(List<Message> messages) {
		Message lastMessage = messages.get(messages.size() - 1);
		String issue = lastMessage.getIssue();
		return issue;
	}

	public Long calcOrgResponseTime(Message message) {
		List<Message> messageHistory = messageDao.findAllByApiId(message.getApiId());
		long diffTotal = 0L;
		LocalDateTime now = LocalDateTime.now();
		for (Message each : messageHistory) {
			    LocalDateTime messageSent = each.getDate();
			    long diff = ChronoUnit.HOURS.between(now, messageSent);
			    diffTotal += diff;
		}
		long averageResponseInHours = (diffTotal / messageHistory.size()); 
		System.out.println(averageResponseInHours);
		return averageResponseInHours;
		}
	}

