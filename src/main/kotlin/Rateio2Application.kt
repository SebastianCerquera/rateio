package com.rateio2.rateio2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import javax.persistence.Entity
import javax.persistence.Id


@EntityScan
@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Entity(name = "messages")
data class Message(@Id val id: String?, val text: String)

interface MessageRepository : CrudRepository<Message, String>{
	@Query("FROM messages")
	fun findMessages(): List<Message>
}

@Service
class MessageService(val db: MessageRepository) {

	fun findMessages(): List<Message> = db.findMessages()

	fun post(message: Message){
		db.save(message)
	}
}

@RestController
class MessageResource(val service: MessageService) {

	@PostMapping
	fun post(@RequestBody message: Message) {
		service.post(message)
	}

	@GetMapping
	fun index(): List<Message> = listOf(
		Message("1", "Hello!"),
		Message("2", "Bonjour!"),
		Message("3", "Privet!")
	)

}