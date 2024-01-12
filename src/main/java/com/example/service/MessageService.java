package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

    @Transactional
    public long discardMessageById(Integer messageId) {
        long numOfRowsAffected = this.messageRepository.deleteByMessageId(messageId);

        return numOfRowsAffected;
    }

    @Transactional
    public Integer updateMessageById(Integer messageId, Message message) {
        Message existingMessage = this.retrieveMessageById(messageId);
    
        if(existingMessage == null)
            return null;
        if(message.getMessage_text().trim().length() <= 0 || message.getMessage_text().length() >= 255) 
            return null;

        return this.messageRepository.updateByMessageId(message.getMessage_text(), messageId);
    }

    public List<Message> retrieveMessagesByUserId(Integer accountId) {
        return this.messageRepository.findAllMessagesByAccountId(accountId);
    }
}
