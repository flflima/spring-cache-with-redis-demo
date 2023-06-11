package springcachewithredisdemo.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration

@Configuration
class CacheConfiguration {

//    @Value("\${resolved.cache.ttl:30}")
//    private val ttl: Long = 0

//    @Bean
//    fun objectMapper(): ObjectMapper {
//        return jacksonObjectMapper()
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Any::class.java)
//                    .build(),
//                ObjectMapper.DefaultTyping.EVERYTHING,
//            )
//    }

//    @Bean
//    fun cacheConfig(): RedisCacheConfiguration {
//        val objectMapper =  jacksonObjectMapper()
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Any::class.java)
//                    .build(),
//                ObjectMapper.DefaultTyping.EVERYTHING,
//            )
//        return RedisCacheConfiguration.defaultCacheConfig()
//            .entryTtl(Duration.ofMinutes(3))
//            .disableCachingNullValues()
//            .serializeValuesWith(
//                SerializationPair.fromSerializer(
//                    GenericJackson2JsonRedisSerializer(objectMapper)
//                )
//            )
//    }

//    @Bean
//    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? {
//        val objectMapper: ObjectMapper = jacksonObjectMapper()
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Any::class.java)
//                    .build(),
//                ObjectMapper.DefaultTyping.EVERYTHING,
//            )
//        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
//            builder
//                .withCacheConfiguration(
//                    "friendsCache",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofSeconds(30))
//                        .serializeValuesWith(
//                            SerializationPair.fromSerializer(
//                                GenericJackson2JsonRedisSerializer(objectMapper)
//                            )
//                        )
//                )
//        }
//    }

    @Bean
    fun redisCacheManager(lettuceConnectionFactory: LettuceConnectionFactory): CacheManager {
        val objectMapper = jacksonObjectMapper()
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(Any::class.java)
                    .build(),
                ObjectMapper.DefaultTyping.EVERYTHING,
            )
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .entryTtl(Duration.ofSeconds(60))
//            .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.json()))
            .serializeValuesWith(
                SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(
                        objectMapper
                    )
                )
            )
//        redisCacheConfiguration.usePrefix()
        return RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
            .cacheDefaults(redisCacheConfiguration).build();
    }
}