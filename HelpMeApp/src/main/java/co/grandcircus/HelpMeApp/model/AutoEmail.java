//package co.grandcircus.HelpMeApp.model;
//
//import com.sendgrid.*;
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Value;
//
//public class AutoEmail {
//
//	@Value("${sendGrid_KEY}")
//	private static String SENDGRID_KEY;
//
//	  public static void main(String[] args) throws IOException {
//	    Email from = new Email("test@example.com");
//	    String subject = "Sending with SendGrid is Fun";
//	    Email to = new Email("gbreitenbeck@gmail.com");
//	    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//	    Mail mail = new Mail(from, subject, to, content);
//
//	    SendGrid sg = new SendGrid(SENDGRID_KEY);
//	    Request request = new Request();
//	    try {
//	      request.setMethod(Method.POST);
//	      request.setEndpoint("mail/send");
//	      request.setBody(mail.build());
//	      Response response = sg.api(request);
//	      System.out.println(response.getStatusCode());
//	      System.out.println(response.getBody());
//	      System.out.println(response.getHeaders());
//	    } catch (IOException ex) {
//	      throw ex;
//	    }
//	  }
//	}
//	
