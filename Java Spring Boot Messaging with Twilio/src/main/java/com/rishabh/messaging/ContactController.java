package com.rishabh.messaging;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.twilio.rest.api.v2010.account.Message;
import com.google.gson.Gson;
import com.twilio.Twilio;

@Controller
public class ContactController {
	
	@Autowired
	private MessageData messageData;
	
	@Autowired
	private Messaging messaging;
	
	@Autowired
	public ContactController(Messaging messaging) {
		this.messaging=messaging;
	}
	
	@GetMapping("/")
	public String home(Model model) throws FileNotFoundException, IOException, ParseException, InterruptedException, ExecutionException {
		
		Contacts contacts = new Contacts();
        model.addAttribute("contacts", contacts);
	
        ArrayList<Contacts> list=messageData.getAllContacts();
		model.addAttribute("listContacts", list);
		
		
		ArrayList<ComposeMessage> listMessage=messageData.getAllMessage();
		model.addAttribute("listMessage", listMessage);
		
		return "home";
	}
	@GetMapping("/openContact")
	public String doStuffMethod(@RequestParam(name = "firstName") String firstName,
								@RequestParam(name = "lastName") String lastName,
								@RequestParam(name = "phoneNumber") String phoneNumber,
								Model model) {
	    System.out.println(firstName);
	    System.out.println(lastName);
	    System.out.println(phoneNumber);
	    Contacts contact=new Contacts(firstName, lastName, phoneNumber);
	    model.addAttribute("contact", contact);
	    return "SeparateContact";
	}
	
	@GetMapping("/composeMessage")
	public String ComposeMessage(@RequestParam(name = "firstName") String firstName,
								@RequestParam(name = "lastName") String lastName,
								@RequestParam(name = "phoneNumber") String phoneNumber,
								Model model) {
		Contacts contact=new Contacts(firstName, lastName, phoneNumber);
	    model.addAttribute("contact", contact);
	    
	    Random rnd = new Random();
	    
	    int number = rnd.nextInt(999999);
	    
	    String otp = String.format("%06d", number);
		ComposeMessage composeMessage=new ComposeMessage(firstName+" "+lastName,"Hi, your OTP is: "+otp, LocalDateTime.now().toString());
		
		model.addAttribute("composeMessage", composeMessage);
		return "ComposeMessage";
	
	}
	
	@PostMapping("/composeMessage")
	public String PostComposeMessage(@ModelAttribute("composeMessage") ComposeMessage composeMessage) throws InterruptedException, ExecutionException {
		String otp=composeMessage.getOtp();
		ComposeMessage cM=new ComposeMessage(composeMessage.getName(), otp.substring(otp.length()-6), composeMessage.getLocalDateTime());
		
		Twilio.init(messaging.getSid(), messaging.getToken());
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+917987567233"),
                new com.twilio.type.PhoneNumber(messaging.getFromPhoneNumber()),
                "Hi, your OTP is:"+composeMessage.getOtp())
            .create();
		
		String success=messageData.save(cM);
		return "OTPSuccess";
	}

}
