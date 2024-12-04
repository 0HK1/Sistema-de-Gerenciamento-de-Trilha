# **Gerenciador de Trilhas**

Este projeto é uma aplicação Android desenvolvida em **Java**, que utiliza a API **Location Services** e a **API do Google Maps** para gerenciar trilha. A aplicação é projetada para registrar, exibir e salvar dados de localização durante percursos, oferecendo uma experiência interativa e funcional para monitoramento em tempo real.

## **Funcionalidades**
- **Registro de Trilhas:**
    - Salva pontos de localização (waypoints) com distância, velocidade média e timestamp.
    - Exibe o progresso da trilha em tempo real no mapa.

- **Visualização em Tempo Real:**
    - Integração com a API do Google Maps para exibir a localização atual e o trajeto percorrido.
    - Movimenta a câmera do mapa com base na localização do usuário.

- **Configurações Personalizadas:**
    - Opção para alterar o tipo do mapa (normal ou satélite).
    - Direção da câmera ajustada automaticamente (fixa no norte ou baseada na orientação do dispositivo).

- **Gerenciamento de Dados:**
    - Uso de banco de dados SQLite para armazenar as informações das trilhas.
    - Salvamento de preferências como tipo de mapa e última localização em `SharedPreferences`.

## **Tecnologias e APIs Utilizadas**
- **Java:** Linguagem principal de desenvolvimento.
- **Android SDK:** Para construir a interface e funcionalidades nativas.
- **API Location Services:** Coleta de dados de localização em tempo real.
- **API Google Maps:** Exibição interativa do mapa e marcação de waypoints.
- **SQLite:** Armazenamento local das trilhas registradas.
- **SharedPreferences:** Salvamento de configurações e preferências do usuário.

## **Pré-requisitos**
1. **Android Studio** instalado.
2. **Chave de API do Google Maps:**
    - Ative o serviço **Maps SDK for Android** no console do Google Cloud.
    - Obtenha uma chave de API e adicione no arquivo `local.properties`.

   ```xml
   GOOGLE_MAPS_API_KEY=SUACHAVE
