package com.team2.fsoft.Ecommerce.audit_log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.fsoft.Ecommerce.dto.UserLog;
import lombok.AllArgsConstructor;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserLogEventListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @EventListener
    public void handleSuccessful(UserLog userLog) throws JsonProcessingException {
        Request request = new Request(
                "POST",
                "/userlog/_create/" + userLog.getId());
        request.setJsonEntity(objectMapper.writeValueAsString(userLog));
        Cancellable cancellable = restClient.performRequestAsync(request,
                new ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        logger.info("SAVE_SUCCESS", response.toString());
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        logger.info("SAVE_FAILED", exception.getMessage());
                        exception.printStackTrace();
                    }
                });
    }
}