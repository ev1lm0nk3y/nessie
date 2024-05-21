/*
 * Copyright (C) 2024 Dremio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.projectnessie.versioned.storage.jdbctests;

import jakarta.annotation.Nonnull;
import org.projectnessie.versioned.storage.jdbc.JdbcBackendFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

public class MySQLBackendTestFactory extends ContainerBackendTestFactory {

  @Override
  public String getName() {
    return JdbcBackendFactory.NAME + "-MySQL";
  }

  @Nonnull
  @Override
  protected JdbcDatabaseContainer<?> createContainer() {
    String dockerImage = dockerImage("mysql");
    return new MariaDBDriverMySQLContainer(dockerImage);
  }

  private static class MariaDBDriverMySQLContainer
      extends MySQLContainer<MariaDBDriverMySQLContainer> {

    MariaDBDriverMySQLContainer(String dockerImage) {
      super(dockerImage);
    }

    @Override
    public String getDriverClassName() {
      return "org.mariadb.jdbc.Driver";
    }

    @Override
    public String getJdbcUrl() {
      return super.getJdbcUrl().replace("jdbc:mysql", "jdbc:mariadb");
    }
  }
}