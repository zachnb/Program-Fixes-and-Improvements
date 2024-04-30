This repository showcases improved project design from a security standpoint using various libraries and coding techniques.

This program initially was one class file that contained the GUI and all of the backend coding/functions. However, this can be vulnerable to many secutiry threats. To mitigate many of these threats, the program was separated into a 3-tier architecture system. The files "Presentation," "Data," and "Logic" are all a part of the same program. The Presentation class contains the main GUI/menu while the logic class contains the programs  necessary functions. The data.java file is an interface that stores user data.

The Argon2 Password Hashing file demonstrates the use of the Argon2 hashing algorithm.
