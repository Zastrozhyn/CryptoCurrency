# CryptoCurrency watcher

REST Application for tracking cryptocurrency rates. According Richardson Maturity Model has level 2.

### Functionality:
- View all crypto rates: GET localhost:8080/cryptos
- Crypto search by code: GET localhost:8080/cryptos/search?code=
- The ability to register a user with subsequent tracking of the rate and notification when the rate changes by more than 1% : POST localhost:8080/users/notify
- The application every minute requests the rates of the specified crypto from the external API and then saves this data to DB

## Technology stack:
- Spring core
- Spring Boot
- Stream API
- Java 17
- Gradle
- Git
- PostgreSql
- Liquibase
- Mapstruct
- Lombok

