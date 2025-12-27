# Connect Four Game in Java

A Java implementation of the classic **Connect Four** strategy game.  
This project demonstrates object-oriented programming, abstract classes, and game logic design.

---

## 🛠️ Features
- **AbstractStrategyGame:** Abstract base class defining common behavior for strategy games, including methods for game state, moves, and determining the winner.  
- **ConnectFour:** Implements a 6x7 Connect Four game where two players take turns placing tokens (X and O).  
- **Client:** Provides a `main()` method to play the game in the console.  

---

## 🎯 Design & Inheritance
- `AbstractStrategyGame` is the abstract base class for all strategy games, defining essential methods such as:
  - `instructions()` — explains how to play the game
  - `toString()` — displays the current game state
  - `makeMove(Scanner input)` — executes a player's move
  - `getNextPlayer()` — returns whose turn it is
  - `getWinner()` — determines the winner or tie
- `ConnectFour` extends `AbstractStrategyGame` to implement the classic Connect Four rules, including horizontal, vertical, and diagonal win detection.  
- `Client` is a separate class containing the `main()` method to allow users to play the game interactively.

This hierarchy allows easy extension for other strategy games in the future while keeping the game logic modular and reusable.

---

## 🧩 Skills Practiced
- Java **abstract classes and inheritance**  
- Implementing game logic and win/tie detection  
- Handling user input and exceptions for invalid moves  
- Console-based game development

---

## 🔗 Usage
1. Clone the repository:
```bash
git clone https://github.com/amberli/connect-four-java.git
```
2. Open the project in your IDE (Visual Studio Code, IntelliJ, etc.).
3. Run the Client class to play Connect Four in the console.
4. Follow the on-screen instructions to place tokens and play until a player wins or the game ends in a tie.

---

## 📂 Repository Structure
The project is organized as follows:

```text
connect-four-java/
├── src/
│   ├── AbstractStrategyGame.java   # Abstract base class for strategy games
│   ├── Client.java                 # Contains main() method to play the game
│   └── ConnectFour.java            # Implements Connect Four game logic
├── README.md                       # Project overview and instructions
└── .gitignore                      # Ignore compiled files and IDE artifacts

```
