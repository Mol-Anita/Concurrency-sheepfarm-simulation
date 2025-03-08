🐑 Multithreaded Farm Simulation

A Java-based simulation using multithreading, concurrency, and synchronization.

📌 Overview

This project simulates a farm environment where sheep attempt to escape through gates while dogs patrol the area to keep them inside. The simulation utilizes Java multithreading, with each animal running on its own thread, ensuring independent movement and real-time interaction.

🛠 Technologies Used

    Java – Object-Oriented Programming (OOP) for structured class design
    Multithreading – Threads for independent movement of sheep and dogs
    Concurrency & Synchronization – Ensures thread safety, prevents race conditions and deadlocks
    Console-Based Visualization – Displays real-time farm updates

🔹 Features

✅ Thread-Based Movement: Each sheep and dog operates on its own thread.

✅ Sheep Behavior: Sheep move randomly unless a dog is nearby, then they escape in the opposite direction.

✅ Dog Behavior: Dogs patrol outer zones, preventing sheep from escaping.

✅ Escape Condition: If a sheep reaches a gate, the simulation ends.

✅ Thread Safety: Implemented synchronization with fine-grained locking at the farm cell level.

✅ Real-Time Console Updates: Displays farm layout with moving sheep (letters) and dogs (numbers).
📸 Example Console Output

    ################
    #   D    S    
    # S     D     #
    #      S      #
    #  D    S     #
     S         D  #
    ################

(S = Sheep, D = Dog, # = Wall, Spaces = Empty Fields)

📌 How It Works

1️⃣ The farm is divided into a grid with different zones.

2️⃣ Sheep start in the center and move randomly, avoiding nearby dogs.

3️⃣ Dogs patrol outer areas, preventing sheep from reaching the gates.

4️⃣ The simulation ends when a sheep escapes through a gate.

🚀 How to Run

Clone the repository:

    git clone https://github.com/yourusername/multithreaded-farm.git
    cd multithreaded-farm

Compile and run the program:

    javac FarmSimulation.java  
    java FarmSimulation  

🔧 Future Enhancements

📌 Improve AI movement to predict optimal escape paths for sheep.
📌 Add graphical visualization instead of console-based display.
📌 Implement configurable parameters (number of sheep, dogs, grid size).
