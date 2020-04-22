trait ExampleRunner {
  val logger = (msg: String, in: Boolean) =>
    if (in) {
      println(s">>| $msg")
    } else {
      println(s"<<| $msg")
    }

  def run(): Unit

  def main(args: Array[String]): Unit = {
    run()

    Thread.sleep(10000)
  }
}