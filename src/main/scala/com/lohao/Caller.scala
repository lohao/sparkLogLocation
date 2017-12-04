package com.lohao

import org.slf4j.{Logger, LoggerFactory}

class Caller(str: String) extends Serializable {
  private final val LOG:Logger = LoggerFactory.getLogger(Caller.this.getClass)
  def fun(): Unit = {
    LOG.info("sub function be called " + str)
  }
}
