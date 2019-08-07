package com.github.dnvriend

import com.typesafe.scalalogging.LazyLogging
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class Application

object Application {
  def main(args: Array[String]): Unit = {
      SpringApplication.run(classOf[Application], args:_*)
  }
}

@Component
class MyConsoleRunner extends CommandLineRunner with LazyLogging {
  override def run(args: String*): Unit = {
    logger.debug("This is very convenient ;-)")
  }
}