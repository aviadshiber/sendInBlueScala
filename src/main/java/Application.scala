import Data.{Email, Person}
import utils.{SendInBlueServer, YamlReader}

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
object Application extends  App{

  val from = Person("Adi Shiber","shiber222@gmail.com")
  val to = List(Person("Adi","shiber222@gmail.com"))
  val replyTo = "shiber222@gmail.com"
  val emailConfig = YamlReader.readEmailConfig()
  val subject = emailConfig.subject
  val content = emailConfig.content

  val result=Await result(SendInBlueServer.sendEmail(Email(from,to,replyTo,subject,content)),atMost = 10.seconds )
  println(s"was sent?: ${result.isSuccessful} , status code: ${result.code()}")

}
