# 🚀 Nauka Słówek - Aplikacja JavaFX

Aplikacja służy do nauki słówek, wykorzystując bazę danych PostgreSQL oraz framework Hibernate.  
Interfejs graficzny jest napisany w JavaFX.

---

## 🛠️ Wymagania

- ☕ Java 17
- 📦 Maven
- 🐳 Docker i Docker Compose (tylko do uruchomienia bazy danych PostgreSQL)

---

## 🐘 Jak uruchomić bazę danych PostgreSQL z Dockerem

1. 📁 Upewnij się, że masz w katalogu plik `backupSlowka2.sql` (backup bazy).
2. 📁 W tym samym katalogu powinien znajdować się plik `docker-compose.yml` z konfiguracją bazy.
3. 🖥️ W terminalu uruchom polecenie, które stworzy i uruchomi kontener z bazą danych:

   ```bash
   docker-compose up -d
   ```
   
4. ⏳ Po uruchomieniu bazy danych, uruchom aplikację za pomocą polecenia:

    ```bash
    mvn javafx:run
    ```