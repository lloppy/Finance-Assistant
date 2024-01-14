# MoneyCraft

## Версия: 1.2 [Google-диск](https://drive.google.com/drive/folders/1Jy1kMbPBR5o22QhU-c_1MuGSR9eUFC_h?usp=sharing)

### Описание

**MoneyCraft** - ваш надежный и удобный финансовый помощник, созданный для контроля над вашими финансами без затраты вашего времени. 
Приложение предоставляет удобные возможности:

- Отслеживание доходов и расходов
- Создание персональных бюджетов
- Анализ трат с выбором выгодной MCC-категории

- Полный функционал по [ссылке](https://mm.tt/app/map/3116855325?t=QiOMPbhAsV)

### Особенности

- Добавление расходов с использованием QR-кода чека
- Автоматическое считывание SMS-уведомлений от банков о транзакциях
- Загрузка выписок из банка (в формате CSV, например, из Тинькофф), с автоматическим созданием карточек
- Чат-бот, предоставляющий анализ трат и поступлений

### Установка

Чтобы установить приложение, выполните следующие шаги:

1. Откройте Google-диск по ссылке [Google-диск](https://drive.google.com/drive/folders/1Jy1kMbPBR5o22QhU-c_1MuGSR9eUFC_h?usp=sharing)
2. Скачайте файл "Moneycraft".
3. Нажмите «Установить».



#### Подробнее о технологиях

```

Приложение было написано на Kotlin Compose в Android Studio. Вот основные технологии, которые используются в данном приложении для мониторинга и управления личными финансами:
для внесений операций с помощью QR-кодов приложение использует библиотеку CameraX от андроид для запуска камеры и Zxing для QR-кодов: Zxing распознает изображение, преобразовав его в байтовый массив, и, декодируя бинарное изображения, извлекает данные о транзакции.

Когда пользователь сканирует чек или считывает данные из выписки CSV, автоматически добавляется и MCC-категория. MCC-код (Merchant category code) — это стандартизованный код услуги, которую предоставляет получатель. По этому коду банк определяет, товар какой категории приобрёл клиент. Это позволяет нам делать отчет на экране чата и отображать MCC-категории, которые наиболее выгодны для выбора кэшбека. MCC-категории считываются через регулярное выражение.

Для обработки отпечатка пальца и реализации биометрической аутентификации в приложении я использую стороннюю андроид библиотеку biometric. С помощью BiometricPrompt отображаю диалоговое окно и обрабатываю результаты в BiometricPrompt.AuthenticationCallback. После успешной обработки запускается основной экран приложения.
Для безопасности данных пользователя я использовала внутреннее хранилище андроид для хранение данных поступлений и трат и Shared Preferences для пароля и процента, который пользователь откладывает (spend goal).

Так как вся информация о данных по поступлениям и тратам хранится во внутреннем хранилище, мне потребовалась библиотека Coil для загрузки изображений в фоновом потоке. Она предоставляет удобный API для загрузки изображений из различных источников, включая сеть, локальное хранилище и ресурсы приложения. С помощью AsyncImage - виджета Jetpack Compose, который позволяет отображать изображения, загруженные с помощью coil – я могу загрузить картинку из URI в фоновом потоке, которая хранится во внутренней памяти телефона пользователя и отобразить её на экране.

Также, когда приложение запущено, оно считывает все входящие СМС (пользователь может не разрешать приложению считывание, по умолчанию функция выключена). Для извлечения данных были написаны регулярные выражения для обработки поступающих данных.

Для чтения выписки из банков я использую BufferedReader, открывая и считывая файл в новом потоке, благодаря чему приложение не останавливается и не лагает. Для чтения данных из CSV-файла я также использую регулярные выражения RegEx.

Также я сделала кастомный дизайн для календарей из библиотеки виджетов андроид, которые используются при создании карточек трат и поступлений, а для отображения статистики я использую анимацию из библиотеки compose animation.

```

### Поддержка

Если у вас возникли вопросы или проблемы с приложением, не стесняйтесь обращаться по адресу [ankudinovapol@gmail.com](mailto:ankudinovapol@gmail.com).

Спасибо, что выбрали приложение **MoneyCraft**!
