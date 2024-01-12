package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.lang.Integer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message persistMessage(Message message) {
        Optional<Account> userOptional = accountRepository.findById(message.getPosted_by()); 

        if(!userOptional.isPresent()) 
            return null;
        if(message.getMessage_text().trim().length() <= 0 || message.getMessage_text().length() >= 255) 
            return null;
        

        return this.messageRepository.save(message);
    }

    public List<Message> retrieveAllMessages() {
        return this.messageRepository.findAll();
    }

    public Message retrieveMessageById(Integer messageId) {
        Optional<Message> messageOptional = this.messageRepository.findById(messageId);

        if(messageOptional.isPresent())
            return messageOptional.get();
        else
            return null;
    }

    public Integer discardMessageById(int messageId) {
        Optional<Message> messageOptional = this.messageRepository.findById(messageId);

        if(messageOptional.isPresent()) {
            this.messageRepository.deleteById(messageId);
            return 1;
        }

        return 0;
    }

    public Message updateMessageById(Integer messageId) {
        Message retrievedMessage = this.retrieveMessageById(messageId);
        
        if(retrievedMessage.getMessage_text().trim().length() <= 0 || retrievedMessage.getMessage_text().length() >= 255) 
            return null;

        return this.messageRepository.save(retrievedMessage);
    }
}
