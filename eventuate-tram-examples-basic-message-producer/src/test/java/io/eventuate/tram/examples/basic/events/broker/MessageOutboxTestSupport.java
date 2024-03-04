package io.eventuate.tram.examples.basic.events.broker;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import org.assertj.core.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MessageOutboxTestSupport {

    private final JdbcTemplate jdbcTemplate;

    public MessageOutboxTestSupport(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assertMessageProduced(String channel) {
        List<Message> messages = this.findMessagesSentToChannel(channel);
        Assertions.assertThat(messages).hasSize(1);
    }

    private List<Message> findMessagesSentToChannel(String channel) {
        return this.jdbcTemplate.query("select headers,payload from message where destination = ?", (rs, rowNum) -> {
            String headers = rs.getString("headers");
            String payload = rs.getString("payload");
            return MessageBuilder.withPayload(payload).withExtraHeaders("", (Map) JSonMapper.fromJson(headers, Map.class)).build();
        }, new Object[]{channel});
    }

}
