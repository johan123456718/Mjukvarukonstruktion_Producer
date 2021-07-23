package com.example.demo;

import com.example.demo.model.JournalRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;


/*This is the controller that the JPA uses. @Controller makes this possible.
@Autowired is used to get the repository-beans, we will use it to handle the data.
The repositories inherits methods for CRUD-operations.
@CrossOrigin lets the React-App(from the specified IP-Address) connect to the server.
@Requestmapping tells what the URL starts with.*/

//REST Api for remote access to the producer
@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
public class RestController {

    //Send a record to the kafka stream.
    //RolesAllowed determines which keycloak roles are allowed to use this call. 
    //Takes @RequestParams category and content, that form the journal record.
    //@RequestHeader Authorization takes a token for authorizing the client making the call. 
    @RolesAllowed("user")
    @PostMapping(path="/produceRecord")
    public @ResponseBody String addRecord (@RequestParam String category, @RequestParam String content, @RequestHeader String Authorization) {
        String date = LocalDate.now().toString();
        JournalRecord jr = new JournalRecord(category, content, date);
        String recordString = Parser.RecordToString(jr);
        Producer.sendMessage("replication", "tmpKey", recordString);
        return "added record via kafka";
    }

}
