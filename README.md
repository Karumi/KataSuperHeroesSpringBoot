# ![Karumi logo][karumilogo] Kata SuperHeroes with SpringBoot in Kotlin.

- We are here to practice the usage of [SpringBoot].
- We are going to use [Kotlin][kotlin].
- We are going to practice pair programming.

---

## Getting started

This repository contains an SpringBoot application.

## Tasks

Your task as Spring Boot Developer is to **create a rest application*. You will do so step by step and always using tests to verify everything is still working.

Our recommendation for this exercise is:

### Before starting
  1. Checkout `3-DDD` branch.

### Exercise
  1. Configure JPA and H2 as database.
  2. Create entity class that represent the SuperHero in the database.
  3. Create a DataSource that implements Crud operations through interface.
  4. Use datasource in the repository.
  5. Add Query Method to Datasource to find by name ignoring case.

- To verify the correct behavior of your code you can execute:

```shell
./gradlew ktlint test
```

## License

Copyright 2019 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
