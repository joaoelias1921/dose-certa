# Dose Certa 💊 (PT-BR)

O **Dose Certa** é um aplicativo Android desenvolvido para a matéria de Web Services e MBaaS do curso de AppDev, e busca auxiliar usuários a gerenciar suas receitas de medicamentos de forma simples, garantindo o consumo correto dos mesmos através de lembretes/alarmes diários. O app traz uma interface moderna e limpa, que permite a adição, acompanhamento e organização dos tratamentos medicamentosos.

<br>

## ✨ Recursos

* **Gerenciamento de receitas:** adicione, visualize, edite e exclua receitas de forma detalhada;
* **Alarmes de medicamento:** usando integrações de forma nativa com o dispositivo, adicione facilmente alarmes em cada medicamento para não se perder nos tratamentos;
* **IU moderna:** aplicativo construído com **Jetpack Compose** para garantir uma interface responsiva, fluida e moderna;
* **Persistência de dados:** por meio do padrão Repository, o app conta com persistência de dados na Cloud e autenticação de usuário (Firebase Storage/Auth);
* **Notificações push:** embora ainda em evolução, o DoseCerta já utiliza recursos de notificação integrados ao Firebase Cloud Messaging e Cloud Functions.

<br>

## 🚀 Tecnologias

O projeto busca aplicar padrões modernos de desenvolvimento Android e em geral.

* **Linguagem principal:** Kotlin;
* **Ferramentas de IU:** Jetpack Compose (Material 3);
* **Gerenciamento de estado:** Kotlin Flow e Compose;
* **Injeção de dependências:** Uso de factory para criação e injeção de ViewModels/Repositories;
* **Dados/Backend:** Firebase Cloud Firestore, Auth, Cloud Messaging, Cloud Functions.

<br>

## 📐 Arquitetura

A aplicação usa o padrão arquitetural **MVVM (Model-View-ViewModel)** combinado com o padrão **Repository** para garantir separação de responsabilidades, testabilidade e manutenabilidade.

| Componente | Papel |
| :--- | :--- |
| **View (Screen)** | Renderiza a interface e coleta atualizações de estado |
| **ViewModel** | Gerencia o estado da interface e regras de negócio |
| **Repository** | Abstrai o acesso aos dados persistidos e orquestra operações de CRUD |
| **Alarm Utility** | Gerencia as interações com alarmes do aplicativo padrão do dispositivo |
| **Service** | Configura integrações com serviços externos, como o PushNotificationService |

<br>

## ⚙️ Como configurar e instalar

Para buildar e rodar o DoseCerta, você precisa do [Android Studio](https://developer.android.com/studio) e um dispositivo/emulador Android rodando.

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/joaoelias1921/dose-certa.git
    ```
2.  **Abra o projeto no Android Studio;**
3.  **Configurar Firebase**: você precisa de um projeto configurado no Firebase:
      - Crie um projeto no Firebase;
      - Registre um novo aplicativo Android e faça download do arquivo `google-services.json`;
      - Coloque o arquivo na pasta app/ do projeto;
4.  **Configurar Cloud Messaging/Cloud Functions**: para utilizar o serviço de mensageria do Firebase, é necessário configurar e fazer deploy do serviço no Cloud Functions:
      - Na raíz do repositório, copie a pasta `firebase-notifications` e cole em um local de fácil acesso;
      - Abra a pasta com o VSCode ou seu editor de código preferido;
      - No terminal, verifique se possui o Node e Firebase Tools instalados no seu CLI (use o comando `npm install -g firebase-tools` após instalar o [Node](https://nodejs.org/pt/download), para instalar o Firebase Tools);
      - No terminal, execute o comando `firebase login`, e efetue o login via browser (aberto automaticamente);
      - No terminal, execute o comando `npm i` para instalar as dependências;
      - Execute o comando `firebase deploy --only functions:androidPushNotification`. Isto fará deploy do serviço **androidPushNotification** no Firebase Cloud Functions (requer que o plano Blaze esteja assinado).
5.  **Verifique as configurações de build:** No arquivo `app/build.gradle`, a linha `targetSdk` deve ter o valor 30 ou maior;
6.  **Buildar e rodar:** Faça build do projeto e execute-o em seu emulador/dispositivo. Crie uma nova conta e verifique se receberá a notificação push de confirmação de criação.

<br>

### ⚠️ Configuração necessária do AndroidManifest (Android 11+/API 30+)

Motivado pelas restrições aplicadas no Android 11 (API 30), a aplicação requer uma declaração explícita no arquivo `AndroidManifest.xml` para que consiga encontrar e usar o app de alarmes/relógio padrão do seu dispositivo.
O bloco a seguir deve estar presente no arquivo mencionado acima (dentro da tag `<manifest>`:

```xml
<queries>
    <intent>
        <action android:name="android.intent.action.SET_ALARM" />
    </intent>
</queries>
```

<br>
<br>

# Dose Certa 💊 (EN)

**Dose Certa** (Portuguese for "Correct Dose") is an Android application developed for the Web Services and MBaaS course in AppDev, which aims to help users manage their medication prescriptions simply, ensuring the correct consumption through daily reminders/alarms. The app features a modern and clean interface that allows the addition, monitoring, and organization of medication treatments.

<br>

## ✨ Features

* **Prescription Management:** Add, view, edit, and delete prescriptions in detail.
* **Medication Alarms:** Using native device integrations, easily add alarms to each medicine to ensure treatments aren't missed.
* **Modern UI:** Application built with **Jetpack Compose** to ensure a responsive, fluid, and modern interface.
* **Data Persistence:** Through the Repository pattern, the app features Cloud data persistence and user authentication (Firebase Storage/Auth).
* **Push Notifications:** Although still under development, DoseCerta already uses notification features integrated with Firebase Cloud Messaging and Cloud Functions.

<br>

## 🚀 Technologies

The project aims to apply modern Android and general development patterns.

* **Main Language:** Kotlin.
* **UI Toolkit:** Jetpack Compose (Material 3).
* **State Management:** Kotlin Flow and Compose.
* **Dependency Injection:** Use of factory for creating and injecting ViewModels/Repositories.
* **Data/Backend:** Firebase Cloud Firestore, Auth, Cloud Messaging, Cloud Functions.

<br>

## 📐 Architecture

The application uses the **MVVM (Model-View-ViewModel)** architectural pattern combined with the **Repository** pattern to ensure separation of concerns, testability, and maintainability.

| Component | Role |
| :--- | :--- |
| **View (Screen)** | Renders the interface and collects state updates |
| **ViewModel** | Manages the interface state and business rules |
| **Repository** | Abstracts access to persisted data and orchestrates CRUD operations |
| **Alarm Utility** | Manages interactions with alarms from the device's default application |
| **Service** | Configures integrations with external services, such as PushNotificationService |

<br>

## ⚙️ How to Set Up and Install

To build and run DoseCerta, you need [Android Studio](https://developer.android.com/studio) and a running Android device/emulator.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/joaoelias1921/dose-certa.git](https://github.com/joaoelias1921/dose-certa.git)
    ```
2.  **Open the project in Android Studio;**
3.  **Configure Firebase**: you need a configured Firebase project:
    - Create a project on Firebase;
    - Register a new Android application and download the `google-services.json` file;
    - Place the file in the project's app/ folder;
4.  **Configure Cloud Messaging/Cloud Functions**: to use the Firebase messaging service, you must configure and deploy the service on Cloud Functions:
    - At the root of the repository, copy the `firebase-notifications` folder and paste it in an easily accessible location;
    - Open the folder with VSCode or your preferred code editor;
    - In the terminal, verify that you have Node and Firebase Tools installed in your CLI (use the command `npm install -g firebase-tools` after installing [Node](https://nodejs.org/pt/download), to install Firebase Tools);
    - In the terminal, execute the command `firebase login`, and log in via the browser (opened automatically);
    - In the terminal, execute the command `npm i` to install dependencies;
    - Execute the command `firebase deploy --only functions:androidPushNotification`. This will deploy the **androidPushNotification** service on Firebase Cloud Functions (requires the Blaze plan to be subscribed).
5.  **Check build configurations:** In the `app/build.gradle` file, the `targetSdk` line must have a value of 30 or greater;
6.  **Build and run:** Build the project and execute it on your emulator/device. Create a new account and check if you will receive the confirmation creation push notification.

<br>

### ⚠️ Required AndroidManifest Configuration (Android 11+/API 30+)

Driven by the restrictions applied in Android 11 (API 30), the application requires an explicit declaration in the `AndroidManifest.xml` file to be able to find and use the default alarm/clock app on your device. The following block must be present in the aforementioned file (inside the `<manifest>` tag):

```xml
<queries>
    <intent>
        <action android:name="android.intent.action.SET_ALARM" />
    </intent>
</queries>
