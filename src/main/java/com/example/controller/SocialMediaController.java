package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")         
    public ResponseEntity<Account> postAccount(@RequestBody Account account) {
        Account newAccount = this.accountService.persistAccount(account);

        if(newAccount == null)
            return ResponseEntity.status(400).body(newAccount);
        
         return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }

    @PostMapping("login")
    public ResponseEntity<Account> verifyAccount(@RequestBody Account account){
        Account existingAccount = this.accountService.loginAccount(account); 

        if(existingAccount == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(existingAccount);

        return ResponseEntity.status(HttpStatus.OK).body(existingAccount);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message newMessage = this.messageService.persistMessage(message);

        if(newMessage == null)
            return ResponseEntity.status(400).body(newMessage);
        
        return ResponseEntity.status(HttpStatus.OK).body(newMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> listOfMessages = this.messageService.retrieveAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(listOfMessages);
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable String message_id) {
        Message message = this.messageService.retrieveMessageById(Integer.parseInt(message_id));

        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable String message_id) {
        Integer rowsChanged = this.messageService.discardMessageById(Integer.parseInt(message_id));

        if(rowsChanged <= 0)
            return ResponseEntity.status(HttpStatus.OK).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(rowsChanged);
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable String message_id) {
        Message updatedMessage = this.messageService.updateMessageById(Integer.parseInt(message_id));

        if(updatedMessage == null)
            return ResponseEntity.status(400).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAllUncaughtException(Exception exception, WebRequest request){
        return exception.getMessage();
    }

    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.CONFLICT)
    // public String handleAllUncaughtException(Exception exception, WebRequest request){
    //         return exception.getMessage();

    // }

}
