package Data

case class Email(sender:Person, to: List[Person],replyTo:String,subject:String,textContent:String)
