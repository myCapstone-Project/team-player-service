package com.cricket.team_player_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import com.cricket.team_player_service.constant.AppConstant;

public class KafkaConfig {
	
	
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(AppConstant.TOPIC_DRIVER_LOCATION).build();
	}

}
