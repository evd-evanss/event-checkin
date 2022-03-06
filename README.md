# Event Checkin App    ![Cobertura de Testes](.github/badges/jacoco.svg) 

## Arquitetura
Este aplicativo está separado em dois módulos:

Modulo App -> Utilizado para abrigar o aplicativo no que diz respeito ao Android e Apresentação de Views, essa camada conhece as demais camadas, no entanto não é conhecida por ninguém.

Modulo Data -> Módulo utilizado para abrigar repositórios, datasource e model, este módulo é conhecido pelo app no entanto não conhece o módulo app.

Design Pattern -> MVI
Escolhi este design pattern estrutural devido a sua facilidade de legibilidade além de também facilitar os testes unitários

## Frameworks/Libraries
Retrofit, OkHttp, Gson -> Escolhido para manipulação de rede 

Dagger Hilt -> Escolhido para injeção de dependências

Mockk -> Escolhido para Testes Unitários

Kotlin Flow -> Escolhido para chamadas assincronas

Glide -> Escolhido para lidar com carregamento de imagens

SkyDovesAndroidVeil -> Escolhido para animação de shimmer 

Jacoco -> Utilizado para gerar relatórios de cobertura de testes
