trait ExampleRunner {
  // Define basic logger printing out to console
  val logger: (String, Boolean) => Unit = (msg: String, in: Boolean) =>
    if (!in) {
      println(s"<<| $msg")
    } else {
      // Printed by event handler
    }

  def run(): Unit

  def main(args: Array[String]): Unit = {
    run()
  }
}
