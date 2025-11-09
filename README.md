# Dose Certa 💊 (PT-BR)

O **Dose Certa** é um aplicativo Android desenvolvido para a matéria de Web Services e MBaaS do curso de AppDev, e busca auxiliar usuários a gerenciar suas receitas de medicamentos de forma simples, garantindo o consumo correto dos mesmos através de lembretes/alarmes diários. O app traz uma interface moderna e limpa, que permite a adição, acompanhamento e organização dos tratamentos medicamentosos.


## ✨ Recursos

* **Gerenciamento de receitas:** adicione, visualize, edite e exclua receitas de forma detalhada;
* **Alarmes de medicamento:** usando integrações de forma nativa com o dispositivo, adicione facilmente alarmes em cada medicamento para não se perder nos tratamentos;
* **IU moderna:** aplicativo construído com **Jetpack Compose** para garantir uma interface responsiva, fluida e moderna;
* **Persistência de dados:** por meio do padrão Repository, o app conta com persistência de dados na Cloud e autenticação de usuário (Firebase Storage/Auth);
* **Notificações push:** embora ainda em evolução, o DoseCerta já utiliza recursos de notificação integrados ao Firebase Cloud Messaging e Cloud Functions.


## 🚀 Tecnologias

O projeto busca aplicar padrões modernos de desenvolvimento Android e em geral.

* **Linguagem principal:** Kotlin;
* **Ferramentas de IU:** Jetpack Compose (Material 3);
* **Gerenciamento de estado:** Kotlin Flow e Compose;
* **Injeção de dependências:** Uso de factory para criação e injeção de ViewModels/Repositories;
* **Dados/Backend:** Firebase Cloud Firestore, Auth, Cloud Messaging, Cloud Functions.


## 📐 Arquitetura

A aplicação usa o padrão arquitetural **MVVM (Model-View-ViewModel)** combinado com o padrão **Repository** para garantir separação de responsabilidades, testabilidade e manutenabilidade.

| Componente | Papel |
| :--- | :--- |
| **View (Screen)** | Renderiza a interface e coleta atualizações de estado |
| **ViewModel** | Gerencia o estado da interface e regras de negócio |
| **Repository** | Abstrai o acesso aos dados persistidos e orquestra operações de CRUD |
| **Alarm Utility** | Gerencia as interações com alarmes do aplicativo padrão do dispositivo |
| **Service** | Configura integrações com serviços externos, como o PushNotificationService |


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
