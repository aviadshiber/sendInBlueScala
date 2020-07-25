package utils

import Data.{Email, Person}

object JsonSerializer {
  sealed trait JsonValue{
    def stringify : String
  }

  //1 .intermediate data types - Int , String , Array , Object
  final case class JsonNumber(value:Int) extends JsonValue{
    override def stringify: String = String.valueOf(value)
  }
  final case class JsonString(value:String) extends JsonValue{
    override def stringify: String = "\""+value+"\""
  }
  final case class JsonArray(values:List[JsonValue]) extends JsonValue{
    override def stringify: String = values.map(_.stringify).mkString(sep = ",",start = "[",end = "]")
  }
  final case class JsonObject(values: Map[String,JsonValue]) extends JsonValue{
    override def stringify: String =
      values.map{ case (key,jsonValue) ⇒ "\""+ key +"\":"+jsonValue.stringify}
        .mkString(sep = ",",start = "{",end = "}")
  }

  trait JsonConverter[T]{
    def convert(value:T) : JsonValue
  }
  // serialize to JSON
  implicit class JsonEnrichment[T](value:T){
    def toJson(implicit jsonConverter: JsonConverter[T]): JsonValue = jsonConverter.convert(value)
  }

  //existing data types
  implicit object StringConverter extends JsonConverter[String]{
    override def convert(value: String): JsonValue = JsonString(value)
  }

  implicit object IntConverter extends JsonConverter[Int]{
    override def convert(value: Int): JsonValue = JsonNumber(value)
  }

  //custom data types
  implicit object PersonConverter extends JsonConverter[Person]{
    override def convert(p: Person): JsonValue = JsonObject(Map(
      "name" → p.name.toJson,
      "email" → p.emailAddress.toJson
    ))
  }

  implicit object EmailConverter extends JsonConverter[Email]{
    override def convert(e: Email): JsonValue = JsonObject(Map(
        "sender" → e.sender.toJson,
        "to" → JsonArray(e.to.map(p ⇒ p.toJson)),
        "replyTo" → JsonObject(Map("email" → e.replyTo.toJson)),
        "subject" → e.subject.toJson,
        "textContent" → e.textContent.toJson
    ))
  }


}
