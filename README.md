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
5.  **Verifique as configurações de build:** No arquivo `app/build.gradle`, a linha `targetSdk` deve ter o valor 30 ou maior;
6.  **Buildar e rodar:** Faça build do projeto e execute-o em seu emulador/dispositivo.

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
2.  **Open the project in Android Studio**;
3.  **Configure Firebase**: You need a configured Firebase project:
    * Create a Firebase project;
    * Register a new Android application and download the `google-services.json` file;
    * Place the file in the project's `app/` folder;
5.  **Check build configurations:** In the `app/build.gradle` file, the `targetSdk` line must have a value of 30 or greater;
6.  **Build and Run:** Build the project and execute it on your emulator/device.

### ⚠️ Required AndroidManifest Configuration (Android 11+/API 30+)

Driven by the restrictions applied in Android 11 (API 30), the application requires an explicit declaration in the `AndroidManifest.xml` file to be able to find and use the default alarm/clock app on your device. The following block must be present in the aforementioned file (inside the `<manifest>` tag):

```xml
<queries>
    <intent>
        <action android:name="android.intent.action.SET_ALARM" />
    </intent>
</queries>
