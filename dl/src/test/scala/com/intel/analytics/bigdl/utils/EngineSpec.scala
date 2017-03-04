/*
 * Licensed to the Intel Corporation under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Intel Corporation licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intel.analytics.bigdl.utils

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class EngineSpec extends FlatSpec with Matchers with BeforeAndAfter {
  before {
    Engine.reset
  }

  after {
    Engine.reset
  }

  "Engine" should "be inited correct under no spark environment" in {
    Engine.init
    Engine.onSpark should be(false)
    Engine.nodeNumber should be(1)
    Engine.coreNumber should be(1)
    Engine.reset
  }

  "Engine" should "be inited correct under spark local environment" in {
    TestUtils.sparkLocalEnv(core = 4) {
      Engine.init
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(1)
      Engine.coreNumber should be(4)
    }
    Engine.reset
  }

  "Engine" should "be inited with correct value under spark local environment" in {
    TestUtils.sparkLocalEnv(core = 4) {
      Engine.init(1, 4, true)
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(1)
      Engine.coreNumber should be(4)
    }
    Engine.reset
  }

  "Engine" should "be inited with specified value under spark local environment" in {
    TestUtils.sparkLocalEnv(core = 4) {
      Engine.init(2, 8, true)
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(2)
      Engine.coreNumber should be(8)
    }
    Engine.reset
  }

  "Engine" should "be inited correct under spark standalone environment" in {
    TestUtils.sparkStandaloneEnv(totalCore = 24, core = 4) {
      Engine.init
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(6)
      Engine.coreNumber should be(4)
    }
    Engine.reset
  }

  "Engine" should "be inited with specified value under spark standalone environment" in {
    TestUtils.sparkStandaloneEnv(totalCore = 24, core = 4) {
      Engine.init(7, 9, true)
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(7)
      Engine.coreNumber should be(9)
    }
    Engine.reset
  }

  "Engine" should "be inited correct under spark yarn environment" in {
    TestUtils.sparkYarnEnv(executors = 6, core = 4) {
      Engine.init
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(6)
      Engine.coreNumber should be(4)
    }
    Engine.reset
  }

  "Engine" should "be inited with specified value under spark yarn environment" in {
    TestUtils.sparkYarnEnv(executors = 6, core = 4) {
      Engine.init(7, 9, true)
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(7)
      Engine.coreNumber should be(9)
    }
    Engine.reset
  }


  "Engine" should "be inited correct under spark mesos environment" in {
    TestUtils.sparkMesosEnv(totalCore = 24, core = 4) {
      Engine.init
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(6)
      Engine.coreNumber should be(4)
    }
    Engine.reset
  }

  "Engine" should "be inited with specified value under spark mesos environment" in {
    TestUtils.sparkMesosEnv(totalCore = 24, core = 4) {
      Engine.init(7, 9, true)
      Engine.onSpark should be(true)
      Engine.nodeNumber should be(7)
      Engine.coreNumber should be(9)
    }
    Engine.reset
  }

  "sparkExecutorAndCore" should "parse local[*]" in {
    System.setProperty("spark.master", "local[*]")
    val (nExecutor, executorCore) = Engine.sparkExecutorAndCore
    nExecutor should be(1)
    executorCore should be(Engine.coreNumber())
    System.clearProperty("spark.master")
  }
}
