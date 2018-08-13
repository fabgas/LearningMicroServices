package org.alma.gamification.event;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

/**
 * création d'un topic 
 */
    @Bean
    public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    /**
     * Creation d'une queue pour lire les message
     */
    @Bean
    public Queue gamificationMultiplicationQueue(@Value("${multiplication.queue}") final String queueName) {
        return new Queue(queueName,true);
    }

    /**
     * Relie la queue et l'exchange
     */
    @Bean
    Binding binding(final Queue queue,
                    final TopicExchange exchange,
                    @Value("${multiplication.anything.routing-key}") final String routingKey) {
                        return BindingBuilder.bind(queue).to(exchange).with(routingKey);             
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2Converter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2Converter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}