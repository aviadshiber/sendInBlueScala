import Data.Email
import com.squareup.okhttp.{
  MediaType,
  OkHttpClient,
  Request,
  RequestBody,
  Response
}
import JsonSerializer._
import scala.concurrent.{ExecutionContext, Future}

object SendInBlueServer{

  private val apiKey =YamlReader.readServerSecret().apiKey
  private val serverUrl = "https://api.sendinblue.com/v3/smtp/email"
  private val client = new OkHttpClient()
  private val mediaType = MediaType.parse("application/json");
  def sendEmail(email: Email)(implicit ctx: ExecutionContext) = {

    val body = RequestBody.create(mediaType, email.toJson.stringify)

    val request = new Request.Builder()
      .url(serverUrl)
      .post(body)
      .addHeader("accept", "application/json")
      .addHeader("content-type", "application/json")
      .addHeader("api-key", apiKey)
      .build()
    println(email.toJson.stringify)
    Future{client.newCall(request).execute()}
  }
}
