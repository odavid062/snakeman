# 🐍 Snakeman — Jogo da Cobrinha em Java (arquitetura MVC)

Jogo da cobrinha em **Java/Swing** estruturado com separação de responsabilidades (**MVC**), com **recorde persistente** salvo em arquivo.

## ✨ Destaques
- **Arquitetura MVC** — `model` / `view` / `controller` + camada de jogo (`game`) para estado e pontuação
- **Recorde persistente** — salvo em `highscore.txt`, mantido entre execuções
- **Máquina de estados** — `MENU` → `RUNNING` → `PAUSED` → `GAME_OVER`
- **Controller desacoplado** — `InputHandler` recebe a cobra via `Supplier` e ações via callbacks (`Runnable`)

## 🎮 Funcionalidades
- Movimentação, comida aleatória, crescimento e pontuação
- Velocidade progressiva (a cada 5 pontos)
- Pausar e reiniciar
- Colisão com parede e com o próprio corpo, com prevenção de ré

## ⌨️ Controles
| Tecla | Ação |
|-------|------|
| ← ↑ → ↓ | Mover a cobra |
| P | Pausar / Continuar |
| R | Reiniciar (após Game Over) |

## ▶️ Como rodar

**IntelliJ IDEA:** abra o projeto e rode a classe `snakeGame.SnakeGame`.

**Linha de comando:**
```bash
# compilar (PowerShell)
javac -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
# executar
java -cp out snakeGame.SnakeGame
```

## 🗂️ Arquitetura
```
src/snakeGame/
├── SnakeGame.java            # ponto de entrada (JFrame)
├── model/                    # MODEL — dados e regras
│   ├── Snake.java
│   └── Food.java
├── view/                     # VIEW — renderização e loop
│   └── GamePanel.java
├── controller/               # CONTROLLER — entrada do usuário
│   └── InputHandler.java
├── game/                     # estado e pontuação
│   ├── GameStateManager.java # máquina de estados do jogo
│   └── ScoreManager.java     # recorde persistente (highscore.txt)
└── utils/
    └── Direction.java
```

## 🛠️ Tecnologias
- Java 17
- Swing (interface gráfica)
