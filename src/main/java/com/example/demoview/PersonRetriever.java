package com.example.demoview;

import com.example.demoview.model.Person;
import com.example.demoview.model.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class PersonRetriever {
    private PersonRepository repository;
    private WebSocketHandler handler;

    @Bean
    public Consumer<List<Person>> retrievePeople() {
        return (list) -> {
            repository.deleteAll();
            repository.saveAll(list);
            sendPeople();
        };
    }

    private void sendPeople() {
        if (repository.count() > 0) {
            for (WebSocketSession session : handler.getSessionList()) {
                try {
                    session.sendMessage(new TextMessage(repository.findAll().toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
