package springcachewithredisdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SpringCacheWithRedisDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringCacheWithRedisDemoApplication>(*args)
}
