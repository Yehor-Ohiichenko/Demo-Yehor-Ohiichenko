# Demo QA Automation Project

Демонстраційний проєкт з автоматизації тестування веб-додатків, створений на ентузіазмі та натхненні

## 🛠 Технологічний стек
- **Мова програмування:** Java 21
- **Фреймворк для UI-тестів:** Selenide
- **Тестовий фреймворк:** TestNG
- **Звіти:** Allure Framework
- **Збірка проєкту:** Maven
- **Архітектурний патерн:** Page Object Model (POM) + Fluent Interface
- **Контроль версій:** Git Flow (`mai` + `develop` + `feature/` гілки)

## 📁 Структура проєкту
```text
Demo-Yehor-Ohiichenko/
├── src/
│   ├── main/java/myp/maven/pages/  # Page Object класи (MainPage.java)
│   └── test/java/test/             # Тестові класи (DemoTest.java)
├── pom.xml                         # Конфігурація Maven та залежності
└── README.md                       # Про збірку та запуск
```

## Локальний запуск збірки

1. Клонуйте репозиторій або відкрийте проєкт у вашому IDE.
2. Переконайтеся, що у вас встановлена **Java 21** та **Maven**.
3. Запустіть тести через термінал за допомогою команди:
   ```bash
   mvn clean test
   ```

## Генерація Allure-звіту
Для генераціїї HTML-звіту із детальними кроками використати команду:
```bash
mvn clean test allure:serve
```

---
*Автор: Єгор Огійченко