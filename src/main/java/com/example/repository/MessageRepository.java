package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.Integer;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>  {
    @Modifying
    @Query("delete from Message m where m.message_id=:id")
    int deleteByMessageId(@Param("id") Integer id);
    
    @Modifying
    @Query("update Message m set m.message_text=:text where m.message_id=:id")
    int updateByMessageId(@Param("text") String text, @Param("id") Integer id);

         
    @Query("from Message m where m.posted_by = :id")
    List<Message> findAllMessagesByAccountId(@Param("id") Integer id);
}
